package com.cargo.tracking.system.domain.model.cargo;

import com.cargo.tracking.system.domain.model.handling.HandlingEvent;
import com.cargo.tracking.system.domain.model.location.Location;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Itinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Date END_OF_DAYS = new Date(Long.MAX_VALUE);
    public static final Itinerary EMPTY_ITINERARY = new Itinerary();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cargo_id")
    @OrderBy("load_time")
    @Size(min = 1)
    private List<Leg> legs = Collections.emptyList();

    public Itinerary() {
    }

    public Itinerary(List<Leg> legs) {
        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(legs);
    }

    public boolean isExpected(HandlingEvent event) {
        if (legs.isEmpty()) {
            return true;
        }

        if (event.getType() == HandlingEvent.Type.RECEIVE) {
            Leg leg = legs.get(0);
            return leg.getLoadLocation().equals(event.getLocation());
        }

        if (event.getType() == HandlingEvent.Type.LOAD) {
            for (Leg leg : legs) {
                if (leg.getLoadLocation().sameIdentityAs(event.getLocation()) &&
                        leg.getVoyage().sameIdentityAs(event.getVoyage())) {
                    return true;
                }
            }
            return false;
        }

        if (event.getType() == HandlingEvent.Type.UNLOAD) {
            for (Leg leg : legs) {
                if (leg.getUnloadLocation().equals(event.getLocation())
                        && leg.getVoyage().equals(event.getVoyage())) {
                    return true;
                }
            }
            return false;
        }

        if (event.getType() == HandlingEvent.Type.CLAIM) {
            Leg leg = getLastLeg();
            return (leg.getUnloadLocation().equals(event.getLocation()));
        }
        return true;
    }

    Location getInitialDepartureLocation() {
        if (legs.isEmpty()) {
            return Location.UNKNOWN;
        } else {
            return legs.get(0).getLoadLocation();
        }
    }

    Location getFinalArrivalLocation() {
        if (legs.isEmpty()) {
            return Location.UNKNOWN;
        } else {
            return getLastLeg().getUnloadLocation();
        }
    }

    Date getFinalArrivalDate() {
        Leg lastLeg = getLastLeg();
        if (lastLeg == null) {
            return new Date(END_OF_DAYS.getTime());
        } else {
            return new Date(lastLeg.getUnloadTime().getTime());
        }
    }
    private Leg getLastLeg() {
        if (legs.isEmpty()) {
            return null;
        } else {
            return legs.get(legs.size() - 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return sameValueAs(itinerary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs);
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "legs=" + legs +
                '}';
    }

    private boolean sameValueAs(Itinerary other) {
        return other != null && legs.equals(other.legs);
    }

}
