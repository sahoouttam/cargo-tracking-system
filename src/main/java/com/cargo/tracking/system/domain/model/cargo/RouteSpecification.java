package com.cargo.tracking.system.domain.model.cargo;

import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.shared.AbstractSpecification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class RouteSpecification extends AbstractSpecification<Itinerary> implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "spec_origin_id", updatable = false)
    private Location origin;
    @ManyToOne
    @JoinColumn(name = "spec_destination_id", updatable = false)
    private Location destination;
    @Temporal(TemporalType.DATE)
    @Column(name = "spec_arrival_time")
    @NotNull
    private Date arrivalDeadline;

    public RouteSpecification() {
    }

    public RouteSpecification(Location origin, Location destination, Date arrivalDeadline) {
        this.origin = origin;
        this.destination = destination;
        this.arrivalDeadline = (Date) arrivalDeadline.clone();
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

    public Date getArrivalDeadline() {
        return new Date(arrivalDeadline.getTime());
    }

    @Override
    public boolean isSatisfiedBy(Itinerary itinerary) {
        return itinerary != null && getOrigin().sameIdentityAs(
                itinerary.getInitialDepartureLocation())
                && getDestination().sameIdentityAs(itinerary.getFinalArrivalLocation())
                && getArrivalDeadline().after(itinerary.getFinalArrivalDate());
    }

    private boolean sameValueAs(RouteSpecification other) {
        return other != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteSpecification that = (RouteSpecification) o;
        return Objects.equals(origin, that.origin) && Objects.equals(destination, that.destination) && Objects.equals(arrivalDeadline, that.arrivalDeadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, arrivalDeadline);
    }
}
