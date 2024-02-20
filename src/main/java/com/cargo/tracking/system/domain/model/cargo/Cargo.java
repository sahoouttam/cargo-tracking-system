package com.cargo.tracking.system.domain.model.cargo;

import jakarta.persistence.*;

@Entity
public class Cargo {

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
}
