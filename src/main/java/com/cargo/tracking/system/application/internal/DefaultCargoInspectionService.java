package com.cargo.tracking.system.application.internal;

import com.cargo.tracking.system.application.ApplicationEvents;
import com.cargo.tracking.system.application.CargoInspectionService;
import com.cargo.tracking.system.domain.model.cargo.Cargo;
import com.cargo.tracking.system.domain.model.cargo.CargoRepository;
import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import com.cargo.tracking.system.domain.model.handling.HandlingEventRepository;
import com.cargo.tracking.system.domain.model.handling.HandlingHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Event;

public class DefaultCargoInspectionService implements CargoInspectionService {

    private final ApplicationEvents applicationEvents;
    private final CargoRepository cargoRepository;
    private final HandlingEventRepository handlingEventRepository;
    private final Event<Cargo> cargoInspected;

    private static final Logger log = LoggerFactory.getLogger(DefaultCargoInspectionService.class);

    public DefaultCargoInspectionService(ApplicationEvents applicationEvents,
                                         CargoRepository cargoRepository,
                                         HandlingEventRepository handlingEventRepository,
                                         Event<Cargo> cargoInspected) {
        this.applicationEvents = applicationEvents;
        this.cargoRepository = cargoRepository;
        this.handlingEventRepository = handlingEventRepository;
        this.cargoInspected = cargoInspected;
    }


    @Override
    public void inspectCargo(TrackingId trackingId) {
        Cargo cargo = cargoRepository.findByTrackingId(trackingId);
        if (cargo == null) {
            log.info("Cannot inspected non existing cargo {}", trackingId);
            return;
        }
        HandlingHistory handlingHistory = handlingEventRepository.lookupHandlingHistoryOfCargo(trackingId);
        cargo.deriveDeliveryProgress(handlingHistory);
        if (cargo.getDelivery().isMisdirected()) {
            applicationEvents.cargoWasMisdirected(cargo);
        }
        if (cargo.getDelivery().isUnloadAtDestination()) {
            applicationEvents.cargoHasArrived(cargo);
        }
        cargoRepository.store(cargo);
        cargoInspected.fire(cargo);
    }
}
