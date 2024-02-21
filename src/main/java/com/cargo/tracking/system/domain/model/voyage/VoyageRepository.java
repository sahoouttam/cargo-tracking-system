package com.cargo.tracking.system.domain.model.voyage;

import java.util.List;

public interface VoyageRepository {

    Voyage findByVoyageNumber(VoyageNumber voyageNumber);

    List<Voyage> findAll();
}
