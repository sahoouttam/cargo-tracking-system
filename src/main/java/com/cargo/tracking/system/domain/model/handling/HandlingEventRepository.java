package com.cargo.tracking.system.domain.model.handling;

import com.cargo.tracking.system.domain.model.cargo.TrackingId;

public interface HandlingEventRepository {

    void  store(HandlingEvent event);

    HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId);
}
