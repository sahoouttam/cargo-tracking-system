package com.cargo.tracking.system.infrastructure.persistence.jpa;

import com.cargo.tracking.system.domain.model.cargo.Cargo;
import com.cargo.tracking.system.domain.model.cargo.CargoRepository;
import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CargoJpaRepository implements CargoRepository {

    private static final Logger log = LoggerFactory.getLogger(CargoRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cargo findByTrackingId(TrackingId trackingId) {
        Cargo cargo = null;
        try {
            cargo = entityManager.createNamedQuery("Cargo.findByTrackingId", Cargo.class)
                    .setParameter("trackingId", trackingId)
                    .getSingleResult();
        } catch (NoResultException exception) {
            log.error("Find called on non-existant tracking ID.", exception);
        }
        return cargo;
    }

    @Override
    public List<Cargo> findAll() {
        return entityManager
                .createNamedQuery("Cargo.findAll", Cargo.class)
                .getResultList();
    }

    @Override
    public void store(Cargo cargo) {
        cargo.getItinerary().getLegs().forEach(leg -> entityManager.persist(leg));
        entityManager.persist(cargo);
    }

    @Override
    public TrackingId nextTrackingId() {
        String random = UUID.randomUUID().toString().toUpperCase();
        return new TrackingId(random.substring(0, random.indexOf("-")));
    }

    @Override
    public List<TrackingId> getAllTrackingIds() {
        List<TrackingId> trackingIds = new ArrayList<>();
        try {
            trackingIds = entityManager
                    .createNamedQuery("Cargo.getAllTrackingIds", TrackingId.class)
                    .getResultList();
        } catch (NoResultException exception) {
            log.error("Unable to get all tracking IDs", exception);
        }
        return trackingIds;
    }
}
