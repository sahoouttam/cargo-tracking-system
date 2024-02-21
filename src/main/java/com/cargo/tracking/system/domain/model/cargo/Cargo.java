package com.cargo.tracking.system.domain.model.cargo;

import com.cargo.tracking.system.domain.model.location.Location;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private TrackingId trackingId;
    @ManyToOne
    @JoinColumn(name = "origin_id", updatable = false)
    private Location location;
    private RouteSpecification routeSpecification;
    private Itinerary itinerary;
    private Delivery delivery;

    public TrackingId getTrackingId() {
        return trackingId;
    }
}
