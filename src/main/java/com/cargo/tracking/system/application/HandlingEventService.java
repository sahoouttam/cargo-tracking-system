package com.cargo.tracking.system.application;

import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import com.cargo.tracking.system.domain.model.handling.CannotCreateHandlingEventException;
import com.cargo.tracking.system.domain.model.handling.HandlingEvent;
import com.cargo.tracking.system.domain.model.location.UnLocode;
import com.cargo.tracking.system.domain.model.voyage.VoyageNumber;

import java.util.Date;

public interface HandlingEventService {

    void registerHandlingEvent(Date completionTime,
                               TrackingId trackingId,
                               VoyageNumber voyageNumber,
                               UnLocode unLocode,
                               HandlingEvent.Type type) throws CannotCreateHandlingEventException;
}
