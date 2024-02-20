package com.cargo.tracking.system.domain.model.handling;

import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import org.hibernate.boot.model.internal.CannotForceNonNullableException;

public class UnknownCargoException extends CannotCreateHandlingEventException {

    private static final long serialVersionUID = 1L;
    private final TrackingId trackingId;

    public UnknownCargoException(TrackingId trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String getMessage() {
        return "No cargo with tracking id " + trackingId.getId() + " exists in the system";
    }
}
