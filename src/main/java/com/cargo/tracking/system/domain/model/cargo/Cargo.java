package com.cargo.tracking.system.domain.model.cargo;

import com.cargo.tracking.system.domain.model.handling.HandlingHistory;
import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.shared.DomainObjectUtils;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private TrackingId trackingId;
    @ManyToOne
    @JoinColumn(name = "origin_id", updatable = false)
    private Location origin;
    private RouteSpecification routeSpecification;
    private Itinerary itinerary;
    private Delivery delivery;

    public Cargo() {
    }

    public Cargo(TrackingId trackingId, RouteSpecification routeSpecification) {
        this.trackingId = trackingId;
        this.routeSpecification = routeSpecification;
        this.origin = routeSpecification.getOrigin();
        this.delivery = Delivery.derivedFrom(
                this.routeSpecification, this.itinerary, HandlingHistory.EMPTY);
        this.itinerary = Itinerary.EMPTY_ITINERARY;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getOrigin() {
        return origin;
    }

    public RouteSpecification getRouteSpecification() {
        return routeSpecification;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Itinerary getItinerary() {
        return DomainObjectUtils.nullSafe(this.itinerary, Itinerary.EMPTY_ITINERARY);
    }

    public void specifyNewRoute(RouteSpecification routeSpecification) {
        this.routeSpecification = routeSpecification;
        this.delivery = delivery.updateOnRouting(routeSpecification, this.itinerary);
    }

    public void assignToRoute(Itinerary itinerary) {
        this.itinerary = itinerary;
        this.delivery = delivery.updateOnRouting(this.routeSpecification, this.itinerary);
    }

    public void deriveDeliveryProgress(HandlingHistory handlingHistory) {
        this.delivery = Delivery.derivedFrom(getRouteSpecification(), getItinerary(), handlingHistory);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Cargo other = (Cargo) object;
        return sameIdentityAs(other);
    }

    private boolean sameIdentityAs(Cargo other) {
        return other != null && trackingId.sameValueAs(other.trackingId);
    }

    @Override
    public int hashCode() {
        return trackingId.hashCode();
    }

    @Override
    public String toString() {
        return trackingId.toString();
    }

}
