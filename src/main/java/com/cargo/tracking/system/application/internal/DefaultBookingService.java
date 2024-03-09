package com.cargo.tracking.system.application.internal;

import com.cargo.tracking.system.application.BookingService;
import com.cargo.tracking.system.domain.model.cargo.*;
import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.model.location.LocationRepository;
import com.cargo.tracking.system.domain.model.location.UnLocode;
import com.cargo.tracking.system.domain.service.RoutingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DefaultBookingService implements BookingService {

    private final CargoRepository cargoRepository;
    private final LocationRepository locationRepository;
    private final RoutingService routingService;

    private static final Logger log = LoggerFactory.getLogger(DefaultBookingService.class);

    public DefaultBookingService(CargoRepository cargoRepository,
                                 LocationRepository locationRepository,
                                 RoutingService routingService) {
        this.cargoRepository = cargoRepository;
        this.locationRepository = locationRepository;
        this.routingService = routingService;
    }

    @Override
    public TrackingId bookNewCargo(UnLocode originUnLocode, UnLocode destinationUnLocode, Date arrivalDeadline) {
        TrackingId trackingId = cargoRepository.nextTrackingId();
        Location origin = locationRepository.findByUnLocode(originUnLocode);
        Location destination = locationRepository.findByUnLocode(destinationUnLocode);
        RouteSpecification routeSpecification = new RouteSpecification(origin, destination, arrivalDeadline);
        Cargo cargo = new Cargo(trackingId, routeSpecification);
        log.info("Booking cargo with tracking id {}", cargo.getTrackingId().getId());
        return cargo.getTrackingId();
    }

    @Override
    public List<Itinerary> requestPossibleRoutesForCargo(TrackingId trackingId) {
        Cargo cargo = cargoRepository.findByTrackingId(trackingId);
        if (cargo == null) return Collections.emptyList();
        return routingService.fetchRoutesForSpecification(cargo.getRouteSpecification());
    }

    @Override
    public void assignCargoToRoute(Itinerary itinerary, TrackingId trackingId) {
        Cargo cargo = cargoRepository.findByTrackingId(trackingId);
        cargo.assignToRoute(itinerary);
        cargoRepository.store(cargo);
        log.info("Assigned cargo {} to new route", trackingId);
    }

    @Override
    public void changeDestination(TrackingId trackingId, UnLocode unLocode) {
        Cargo cargo = cargoRepository.findByTrackingId(trackingId);
        Location newDestination = locationRepository.findByUnLocode(unLocode);
        RouteSpecification routeSpecification = new RouteSpecification(
                cargo.getOrigin(),
                newDestination,
                cargo.getRouteSpecification().getArrivalDeadline()
        );
        cargo.specifyNewRoute(routeSpecification);
        cargoRepository.store(cargo);
        log.info("Changed destination for cargo {} to {}", trackingId, newDestination);
    }
}
