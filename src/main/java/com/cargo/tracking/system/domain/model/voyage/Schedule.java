package com.cargo.tracking.system.domain.model.voyage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Schedule EMPTY = new Schedule();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "voyage_id")
    @NotNull
    @Size(min = 1)
    private List<CarrierMovement> carrierMovements = Collections.emptyList();

    public Schedule() {
    }

    public Schedule(List<CarrierMovement> carrierMovements) {
        this.carrierMovements = carrierMovements;
    }

    public List<CarrierMovement> getCarrierMovements() {
        return Collections.unmodifiableList(carrierMovements);
    }

    private boolean sameValueAs(Schedule other) {
        return other != null && this.carrierMovements.equals(other.carrierMovements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(carrierMovements, schedule.carrierMovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrierMovements);
    }
}
