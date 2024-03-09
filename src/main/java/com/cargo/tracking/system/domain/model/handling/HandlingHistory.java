package com.cargo.tracking.system.domain.model.handling;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HandlingHistory {

    private final List<HandlingEvent> handlingEvents;
    public static final HandlingHistory EMPTY =
            new HandlingHistory(Collections.emptyList());

    public HandlingHistory(List<HandlingEvent> handlingEvents) {
        this.handlingEvents = handlingEvents;
    }

    public List<HandlingEvent> getAllHandlingEvents() {
        return handlingEvents;
    }

    public List<HandlingEvent> getDistinctEventsByCompletionTime() {
        return handlingEvents.stream()
                .distinct()
                .sorted(Comparator.comparing(HandlingEvent::getCompletionTime))
                .collect(Collectors.toList());
    }

    public HandlingEvent getMostRecentlyCompletedEvent() {
        List<HandlingEvent> distinctEvents = getDistinctEventsByCompletionTime();
        if (distinctEvents.isEmpty()) {
            return null;
        } else {
            return distinctEvents.get(distinctEvents.size() - 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlingHistory that = (HandlingHistory) o;
        return Objects.equals(handlingEvents, that.handlingEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handlingEvents);
    }
}
