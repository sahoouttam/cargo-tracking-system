package com.cargo.tracking.system.domain.model.cargo;

import java.util.List;

public interface CargoRepository {

    Cargo findByTrackingId(TrackingId trackingId);

    List<Cargo> findAll();

    void store(Cargo cargo);

    TrackingId nextTrackingId();

    List<TrackingId> getAllTrackingIds();
}
