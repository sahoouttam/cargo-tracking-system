package com.cargo.tracking.system.infrastructure.persistence.jpa;

import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import com.cargo.tracking.system.domain.model.handling.HandlingEvent;
import com.cargo.tracking.system.domain.model.handling.HandlingEventRepository;
import com.cargo.tracking.system.domain.model.handling.HandlingHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class HandlingEventJpaRepository implements HandlingEventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void store(HandlingEvent event) {
        entityManager.persist(event);
    }

    @Override
    public HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId) {
        List<HandlingEvent> handlingEvents = entityManager
                .createNamedQuery("HandlingEvent.findByTrackingId", HandlingEvent.class)
                .setParameter("trackingId", trackingId)
                .getResultList();
        return new HandlingHistory(handlingEvents);
    }
}
