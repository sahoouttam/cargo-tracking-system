package com.cargo.tracking.system.application.internal;

import com.cargo.tracking.system.application.ApplicationEvents;
import com.cargo.tracking.system.application.HandlingEventService;
import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import com.cargo.tracking.system.domain.model.handling.*;
import com.cargo.tracking.system.domain.model.location.UnLocode;
import com.cargo.tracking.system.domain.model.voyage.VoyageNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class DefaultHandlingEventService implements HandlingEventService {

    private final ApplicationEvents applicationEvents;
    private final HandlingEventRepository handlingEventRepository;
    private final HandlingEventFactory handlingEventFactory;

    private static final Logger log = LoggerFactory.getLogger(DefaultHandlingEventService.class);

    public DefaultHandlingEventService(ApplicationEvents applicationEvents,
                                       HandlingEventRepository handlingEventRepository,
                                       HandlingEventFactory handlingEventFactory) {
        this.applicationEvents = applicationEvents;
        this.handlingEventRepository = handlingEventRepository;
        this.handlingEventFactory = handlingEventFactory;
    }

    @Override
    public void registerHandlingEvent(Date completionTime, TrackingId trackingId, VoyageNumber voyageNumber, UnLocode unLocode, HandlingEvent.Type type) throws CannotCreateHandlingEventException {
        Date registrationTime = new Date();
        HandlingEvent handlingEvent = handlingEventFactory
                .createHandlingEvent(registrationTime, completionTime, trackingId, voyageNumber, unLocode, type);
        handlingEventRepository.store(handlingEvent);
        applicationEvents.cargoWasHandled(handlingEvent);
        log.info("Registered handling event");
    }
}
