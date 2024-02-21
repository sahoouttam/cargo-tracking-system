package com.cargo.tracking.system.domain.model.cargo;

import com.cargo.tracking.system.domain.model.handling.HandlingEvent;
import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.model.voyage.Voyage;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class HandlingActivity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Enumerated(EnumType.STRING)
    @Column(name = "next_expected_handling_event_type")
    private HandlingEvent.Type type;
    @ManyToOne
    @JoinColumn(name = "next_expected_location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "next_expected_voyage_id")
    private Voyage voyage;

    public HandlingActivity() {
    }

    public HandlingActivity(HandlingEvent.Type type, Location location) {
        this.type = type;
        this.location = location;
    }

    public HandlingActivity(HandlingEvent.Type type, Location location, Voyage voyage) {
        this.type = type;
        this.location = location;
        this.voyage = voyage;
    }

    public HandlingEvent.Type getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlingActivity that = (HandlingActivity) o;
        return type == that.type && Objects.equals(location, that.location) && Objects.equals(voyage, that.voyage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, location, voyage);
    }

    public boolean isEmpty() {
        if (type != null) {
            return false;
        }

        if (location != null) {
            return false;
        }

        return voyage == null;
    }

    private boolean sameValueAs(HandlingActivity other) {
        return this.equals(other);
    }

}
