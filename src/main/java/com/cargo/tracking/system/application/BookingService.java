package com.cargo.tracking.system.application;

import com.cargo.tracking.system.domain.model.cargo.Itinerary;
import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import com.cargo.tracking.system.domain.model.location.UnLocode;

import java.util.Date;
import java.util.List;

public interface BookingService {

    TrackingId bookNewCargo(UnLocode origin, UnLocode destination, Date arrivalDeadline);

    List<Itinerary> requestPossibleRoutesForCargo(TrackingId trackingId);

    void assignCargoToRoute(Itinerary itinerary, TrackingId trackingId);

    void changeDestination(TrackingId trackingId, UnLocode unLocode);
}
