package com.cargo.tracking.system.domain.model.handling;

import com.cargo.tracking.system.domain.model.location.UnLocode;

public class UnknownLocationException extends CannotCreateHandlingEventException {

    private static final long serialVersionUID = 1L;
    private final UnLocode unlocode;

    public UnknownLocationException(UnLocode unlocode) {
        this.unlocode = unlocode;
    }

    @Override
    public String getMessage() {
        return "No location with UN locode " + unlocode.getId() + " exists in the system";
    }
}
