package com.cargo.tracking.system.domain.model.location;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    private UnLocode unLocode;
    @NotNull
    private String name;
    public static final Location UNKNOWN =
            new Location(new UnLocode("XXXXX"), "Unknown location");

    public Location() {
    }

    public Location(UnLocode unLocode, String name) {
        this.unLocode = unLocode;
        this.name = name;
    }

    public UnLocode getUnLocode() {
        return unLocode;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return sameIdentityAs(location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unLocode);
    }

    @Override
    public String toString() {
        return name + " [" + unLocode + "]";
    }


    public boolean sameIdentityAs(Location other) {
        return this.unLocode.sameValueAs(other.unLocode);
    }

}
