package com.cargo.tracking.system.infrastructure.persistence.jpa;

import com.cargo.tracking.system.domain.model.voyage.Voyage;
import com.cargo.tracking.system.domain.model.voyage.VoyageNumber;
import com.cargo.tracking.system.domain.model.voyage.VoyageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class VoyageJpaRepository implements VoyageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Voyage findByVoyageNumber(VoyageNumber voyageNumber) {
        return entityManager
                .createNamedQuery("Voyage.findByVoyageNumber", Voyage.class)
                .getSingleResult();
    }

    @Override
    public List<Voyage> findAll() {
        return entityManager.createNamedQuery("Voyage.findAll", Voyage.class)
                .getResultList();
    }
}
