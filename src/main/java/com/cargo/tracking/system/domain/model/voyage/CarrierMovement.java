package com.cargo.tracking.system.domain.model.voyage;

import com.cargo.tracking.system.domain.model.location.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "carrier_movement")
public class CarrierMovement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "departure_location_id")
    @NotNull
    private Location departureLocation;
    @ManyToOne
    @JoinColumn(name = "arrival_location_id")
    @NotNull
    private Location arrivalLocation;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_time")
    @NotNull
    private Date departureTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_time")
    @NotNull
    private Date arrivalTime;
    public static final CarrierMovement NONE = new CarrierMovement(
            Location.UNKNOWN, Location.UNKNOWN, new Date(0), new Date(0));

    public CarrierMovement() {

    }

    public CarrierMovement(Location departureLocation, Location arrivalLocation, Date departureTime, Date arrivalTime) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarrierMovement that = (CarrierMovement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
