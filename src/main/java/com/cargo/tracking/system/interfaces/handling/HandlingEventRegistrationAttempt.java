package com.cargo.tracking.system.interfaces.handling;

import com.cargo.tracking.system.domain.model.cargo.TrackingId;
import com.cargo.tracking.system.domain.model.handling.HandlingEvent;
import com.cargo.tracking.system.domain.model.location.UnLocode;
import com.cargo.tracking.system.domain.model.voyage.VoyageNumber;

import java.util.Date;

public class HandlingEventRegistrationAttempt {

    private final Date registrationTime;
    private final Date completionTime;
    private final TrackingId trackingId;
    private final VoyageNumber voyageNumber;
    private final HandlingEvent.Type type;
    private final UnLocode unLocode;

    public HandlingEventRegistrationAttempt(Date registrationTime,
                                            Date completionTime,
                                            TrackingId trackingId,
                                            VoyageNumber voyageNumber,
                                            HandlingEvent.Type type,
                                            UnLocode unLocode) {
        this.registrationTime = registrationTime;
        this.completionTime = completionTime;
        this.trackingId = trackingId;
        this.voyageNumber = voyageNumber;
        this.type = type;
        this.unLocode = unLocode;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public VoyageNumber getVoyageNumber() {
        return voyageNumber;
    }

    public HandlingEvent.Type getType() {
        return type;
    }

    public UnLocode getUnLocode() {
        return unLocode;
    }

    @Override
    public String toString() {
        return "HandlingEventRegistrationAttempt{" +
                "registrationTime=" + registrationTime +
                ", completionTime=" + completionTime +
                ", trackingId=" + trackingId +
                ", voyageNumber=" + voyageNumber +
                ", type=" + type +
                ", unLocode=" + unLocode +
                '}';
    }
}
