package com.cargo.tracking.system.domain.model.location;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Objects;

public class UnLocode implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    @Pattern(regexp = "[a-zA-Z]{2}[a-zA-Z2-9]{3}")
    private String unlocode;
    private static final java.util.regex.Pattern VALID_PATTERN
            = java.util.regex.Pattern.compile("[a-zA-Z]{2}[a-zA-Z2-9]{3}");

    public UnLocode() {
    }

    public UnLocode(String countryAndLocation) {
        this.unlocode = countryAndLocation.toUpperCase();
    }

    public String getId() {
        return unlocode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnLocode unLocode = (UnLocode) o;
        return Objects.equals(unlocode, unLocode.unlocode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unlocode);
    }

    boolean sameValueAs(UnLocode other) {
        return other != null && this.unlocode.equals(other.unlocode);
    }

}
