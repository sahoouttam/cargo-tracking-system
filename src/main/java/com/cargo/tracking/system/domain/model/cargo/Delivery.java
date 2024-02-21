package com.cargo.tracking.system.domain.model.cargo;

import com.cargo.tracking.system.domain.model.handling.HandlingEvent;
import com.cargo.tracking.system.domain.model.handling.HandlingHistory;
import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.model.voyage.Voyage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Date ETA_UNKNOWN = null;
    private static final HandlingActivity NO_ACTIVITY = new HandlingActivity();
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_status")
    @NotNull
    private TransportStatus transportStatus;
    @ManyToOne
    @JoinColumn(name = "last_known_location_id")
    private Location lastKnownLocation;
    @ManyToOne
    @JoinColumn(name = "current_voyage")
    private Voyage currentVoyage;
    @NotNull
    private boolean misdirected;
    @Temporal(TemporalType.DATE)
    private Date eta;
    private HandlingActivity nextExpectedActivity;
    @Column(name = "is_unload_at_dest")
    @NotNull
    private boolean isUnloadAtDestination;
    @Column(name = "routing_status")
    @NotNull
    private RoutingStatus routingStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "calculated_at")
    @NotNull
    private Date calculatedAt;
    @ManyToOne
    @JoinColumn(name = "last_event_id")
    private HandlingEvent lastEvent;

    public Delivery() {
    }

    public Delivery(HandlingEvent lastEvent, Itinerary itinerary, RouteSpecification routeSpecification) {
        this.calculatedAt = new Date();
        this.lastEvent = lastEvent;
        this.misdirected = calculateMisdirectionStatus(itinerary);
        this.routingStatus = calculateRoutingStatus(itinerary, routeSpecification);
        this.transportStatus = calculateTransportStatus();
        this.lastKnownLocation = calculateLastKnownLocation();
        this.currentVoyage = calculateCurrentVoyage();
        this.eta = calculateEta(itinerary);
        this.nextExpectedActivity = calculateNextExpectedActivity(routeSpecification, itinerary);
        this.isUnloadAtDestination = calculateUnloadAtDestination(routeSpecification);
    }

    private boolean calculateMisdirectionStatus(Itinerary itinerary) {
        if (lastEvent == null) {
            return false;
        } else {
            return !itinerary.isExpected(lastEvent);
        }
    }

    private RoutingStatus calculateRoutingStatus(Itinerary itinerary, RouteSpecification routeSpecification) {
        if (itinerary == null || itinerary == Itinerary.EMPTY_ITINERARY) {
            return RoutingStatus.NOT_ROUTED;
        } else {
            if (routeSpecification.isSatisfiedBy(itinerary)) {
                return RoutingStatus.ROUTED;
            } else {
                return RoutingStatus.MISROUTED;
            }
        }
    }

    private TransportStatus calculateTransportStatus() {
        if (lastEvent == null) {
            return TransportStatus.NOT_RECEIVED;
        }

        switch (lastEvent.getType()) {
            case LOAD:
                return TransportStatus.ONBOARD_CARRIER;
            case UNLOAD:
            case RECEIVE:
            case CUSTOMS:
                return TransportStatus.IN_PORT;
            case CLAIM:
                return TransportStatus.CLAIMED;
            default:
                return TransportStatus.UNKNOWN;
        }
    }

    private Location calculateLastKnownLocation() {
        if (lastEvent != null) {
            return lastEvent.getLocation();
        } else {
            return null;
        }
    }

    private Voyage calculateCurrentVoyage() {
        if (getTransportStatus().equals(TransportStatus.ONBOARD_CARRIER) && lastEvent != null) {
            return lastEvent.getVoyage();
        } else {
            return null;
        }
    }

    private Date calculateEta(Itinerary itinerary) {
        if (onTrack()) {
            return itinerary.getFinalArrivalDate();
        } else {
            return ETA_UNKNOWN;
        }
    }

    private boolean onTrack() {
        return routingStatus.equals(RoutingStatus.ROUTED) && !misdirected;
    }

    private HandlingActivity calculateNextExpectedActivity(RouteSpecification routeSpecification, Itinerary itinerary) {
        if (!onTrack()) {
            return NO_ACTIVITY;
        }
        if (lastEvent == null) {
            return new HandlingActivity(HandlingEvent.Type.RECEIVE, routeSpecification.getOrigin());
        }

        switch (lastEvent.getType()) {
            case LOAD:
                for (Leg leg : itinerary.getLegs()) {
                    if (leg.getLoadLocation().sameIdentityAs(lastEvent.getLocation())) {
                        return new HandlingActivity(HandlingEvent.Type.UNLOAD, leg.getUnloadLocation(), leg.getVoyage());
                    }
                }
                return NO_ACTIVITY;

            case UNLOAD:
                for (Iterator<Leg> iterator = itinerary.getLegs().iterator(); iterator.hasNext(); ) {
                    Leg leg = iterator.next();
                    if (leg.getLoadLocation().sameIdentityAs(lastEvent.getLocation())) {
                        if (iterator.hasNext()) {
                            Leg nextLeg = iterator.next();
                            return new HandlingActivity(HandlingEvent.Type.LOAD,
                                    nextLeg.getUnloadLocation(), nextLeg.getVoyage());
                        } else {
                            return new HandlingActivity(HandlingEvent.Type.CLAIM, leg.getUnloadLocation());
                        }
                    }
                }
                return NO_ACTIVITY;

            case RECEIVE:
                Leg firstLeg = itinerary.getLegs().iterator().next();
                return new HandlingActivity(HandlingEvent.Type.LOAD, firstLeg.getLoadLocation(), firstLeg.getVoyage());

            case CLAIM:
            default:
                return NO_ACTIVITY;

        }
    }

    private boolean calculateUnloadAtDestination(RouteSpecification routeSpecification) {
        return lastEvent != null && HandlingEvent.Type.UNLOAD.sameValueAs(lastEvent.getType())
                && routeSpecification.getDestination().sameIdentityAs(lastEvent.getLocation());
    }

    Delivery updateOnRouting(RouteSpecification routeSpecification, Itinerary itinerary) {
        return new Delivery(this.lastEvent, itinerary, routeSpecification);
    }

    static Delivery derivedFrom(RouteSpecification routeSpecification, Itinerary itinerary, HandlingHistory handlingHistory) {
        HandlingEvent lastEvent = handlingHistory.getMostRecentlyCompletedEvent();
        return new Delivery(lastEvent, itinerary, routeSpecification);
    }

    public TransportStatus getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(TransportStatus transportStatus) {
        this.transportStatus = transportStatus;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public Voyage getCurrentVoyage() {
        return currentVoyage;
    }

    public void setCurrentVoyage(Voyage currentVoyage) {
        this.currentVoyage = currentVoyage;
    }

    public boolean isMisdirected() {
        return misdirected;
    }

    public void setMisdirected(boolean misdirected) {
        this.misdirected = misdirected;
    }

    public Date getEstimatedTimeOfArrival() {
        if (eta != ETA_UNKNOWN) {
            return new Date(eta.getTime());
        } else {
            return ETA_UNKNOWN;
        }
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public HandlingActivity getNextExpectedActivity() {
        return nextExpectedActivity;
    }

    public void setNextExpectedActivity(HandlingActivity nextExpectedActivity) {
        this.nextExpectedActivity = nextExpectedActivity;
    }

    public boolean isUnloadAtDestination() {
        return isUnloadAtDestination;
    }

    public void setUnloadAtDestination(boolean unloadAtDestination) {
        isUnloadAtDestination = unloadAtDestination;
    }

    public RoutingStatus getRoutingStatus() {
        return routingStatus;
    }

    public void setRoutingStatus(RoutingStatus routingStatus) {
        this.routingStatus = routingStatus;
    }

    public Date getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(Date calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    public HandlingEvent getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(HandlingEvent lastEvent) {
        this.lastEvent = lastEvent;
    }
}
