package com.cargo.tracking.system.domain.model.handling;

import com.cargo.tracking.system.domain.model.cargo.Cargo;
import com.cargo.tracking.system.domain.model.cargo.CargoRepository;
import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.model.location.LocationRepository;
import com.cargo.tracking.system.domain.model.location.UnLocode;
import com.cargo.tracking.system.domain.model.voyage.Voyage;
import com.cargo.tracking.system.domain.model.voyage.VoyageNumber;
import com.cargo.tracking.system.domain.model.voyage.VoyageRepository;

import java.io.Serializable;
import java.util.Date;

public class HandlingEventFactory implements Serializable {

    private static final long serialVersionUID = 1L;
    private final CargoRepository cargoRepository;
    private final VoyageRepository voyageRepository;
    private final LocationRepository locationRepository;

    public HandlingEventFactory(CargoRepository cargoRepository, VoyageRepository voyageRepository, LocationRepository locationRepository) {
        this.cargoRepository = cargoRepository;
        this.voyageRepository = voyageRepository;
        this.locationRepository = locationRepository;
    }

    public HandlingEvent createHandlingEvent(Date registrationTime,
                                             Date completionTime,
                                             TrackingId trackingId,
                                             VoyageNumber voyageNumber,
                                             UnLocode unLocode,
                                             HandlingEvent.Type type) throws CannotCreateHandlingEventException {
        Cargo cargo = findCargo(trackingId);
        Voyage voyage = findVoyage(voyageNumber);
        Location location = findLocation(unLocode);

        try {
            if (voyage == null) {
                return new HandlingEvent(cargo, completionTime, registrationTime, type, location);
            } else {
                return new HandlingEvent(cargo, completionTime, registrationTime, type, location, voyage);
            }
        } catch (Exception exception) {
            throw new CannotCreateHandlingEventException(exception);
        }
    }

    private Cargo findCargo(TrackingId trackingId) throws UnknownCargoException {
        Cargo cargo = cargoRepository.findByTrackingId(trackingId);
        if (cargo == null) {
            throw new UnknownCargoException(trackingId);
        }
        return cargo;
    }

    private Voyage findVoyage(VoyageNumber voyageNumber) throws UnknownVoyageException {
        if (voyageNumber == null) return null;
        Voyage voyage = voyageRepository.findByVoyageNumber(voyageNumber);
        if (voyage == null) {
            throw new UnknownVoyageException(voyageNumber);
        }
        return voyage;
    }

    private Location findLocation(UnLocode unLocode) throws UnknownLocationException {
        Location location = locationRepository.findByUnLocode(unLocode);
        if (location == null) {
            throw  new UnknownLocationException(unLocode);
        }
        return location;
    }

}
