package com.cargo.tracking.system.application;

import com.cargo.tracking.system.domain.model.cargo.Cargo;
import com.cargo.tracking.system.domain.model.handling.HandlingEvent;
import com.cargo.tracking.system.interfaces.handling.HandlingEventRegistrationAttempt;

public interface ApplicationEvents {

    void cargoWasHandled(HandlingEvent event);

    void cargoWasMisdirected(Cargo cargo);

    void cargoHasArrived(Cargo cargo);

    void receivedHandlingEventRegistrationAttempt(HandlingEventRegistrationAttempt attempt);
}
