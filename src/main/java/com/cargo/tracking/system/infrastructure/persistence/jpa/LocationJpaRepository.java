package com.cargo.tracking.system.infrastructure.persistence.jpa;

import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.model.location.LocationRepository;
import com.cargo.tracking.system.domain.model.location.UnLocode;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LocationJpaRepository implements LocationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Location findByUnLocode(UnLocode unLocode) {
        return entityManager.createNamedQuery("Location.findByUnLocode", Location.class)
                .setParameter("unLocode", unLocode)
                .getSingleResult();
    }

    @Override
    public List<Location> findAll() {
        return entityManager
                .createNamedQuery("Location.findAll", Location.class)
                .getResultList();
    }
}
