package com.cargo.tracking.system.domain.service;

import com.cargo.tracking.system.domain.model.cargo.Itinerary;
import com.cargo.tracking.system.domain.model.cargo.RouteSpecification;

import java.util.List;

public interface RoutingService {

    List<Itinerary> fetchRoutesForSpecification(RouteSpecification routeSpecification);
}
