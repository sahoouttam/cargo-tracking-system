package com.cargo.tracking.system.domain.model.voyage;

import com.cargo.tracking.system.domain.model.location.Location;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Voyage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private VoyageNumber voyageNumber;
    @NotNull
    private Schedule schedule;
    public static final Voyage NONE = new Voyage(new VoyageNumber(""),
            Schedule.EMPTY);

    public Voyage() {
    }

    public Voyage(VoyageNumber voyageNumber, Schedule schedule) {
        this.voyageNumber = voyageNumber;
        this.schedule = schedule;
    }

    public VoyageNumber getVoyageNumber() {
        return voyageNumber;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voyage voyage = (Voyage) o;
        return Objects.equals(voyageNumber, voyage.voyageNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyageNumber);
    }

    @Override
    public String toString() {
        return "voyageNumber=" + voyageNumber;
    }

    public static class Builder {

        private List<CarrierMovement> carrierMovements = new ArrayList<>();
        private VoyageNumber voyageNumber;
        private Location departureLocation;

        public Builder(VoyageNumber voyageNumber, Location departureLocation) {
            this.voyageNumber = voyageNumber;
            this.departureLocation = departureLocation;
        }

        public Builder addMovement(Location arrivalLocation,
                                   Date departureTime, Date arrivalTime) {
            carrierMovements.add(new CarrierMovement(departureLocation,
                    arrivalLocation, departureTime, arrivalTime));
            this.departureLocation = arrivalLocation;
            return this;
        }

        public Voyage build() {
            return new Voyage(voyageNumber, new Schedule(carrierMovements));
        }
    }
}
