package com.cargo.tracking.system.domain.model.cargo;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class TrackingId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "tracking_id", unique = true, updatable = false)
    private String id;

    public TrackingId() {
    }

    public TrackingId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingId that = (TrackingId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    boolean sameValueAs(TrackingId other) {
        return other != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return id;
    }
}
