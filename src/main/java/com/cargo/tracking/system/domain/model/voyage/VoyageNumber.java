package com.cargo.tracking.system.domain.model.voyage;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class VoyageNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "voyage_number")
    @NotNull
    private String number;

    public VoyageNumber() {
    }

    public VoyageNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoyageNumber that = (VoyageNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    boolean sameValueAs(VoyageNumber other) {
        return other != null && this.number.equals(other.number);
    }

    @Override
    public String toString() {
        return number;
    }
}
