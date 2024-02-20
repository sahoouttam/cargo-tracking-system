package com.cargo.tracking.system.domain.model.handling;

import com.cargo.tracking.system.domain.model.voyage.VoyageNumber;

public class UnknownVoyageException extends CannotCreateHandlingEventException {

    private static final long serialVersionUID = 1L;
    private final VoyageNumber voyageNumber;

    public UnknownVoyageException(VoyageNumber voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    @Override
    public String getMessage() {
        return "No voyage with number " + voyageNumber.getNumber() + " exists in the system";
    }
}
