package com.cargo.tracking.system.application;

import com.cargo.tracking.system.domain.model.cargo.TrackingId;

public interface CargoInspectionService {

    void inspectCargo(TrackingId trackingId);
}
