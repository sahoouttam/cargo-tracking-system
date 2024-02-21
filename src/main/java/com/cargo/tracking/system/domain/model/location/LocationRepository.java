package com.cargo.tracking.system.domain.model.location;

import java.util.List;

public interface LocationRepository {

    Location findByUnLocode(UnLocode unLocode);

    List<Location> findAll();
}
