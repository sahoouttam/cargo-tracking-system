package com.cargo.tracking.system.domain.model.handling;

public class CannotCreateHandlingEventException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CannotCreateHandlingEventException(Exception exception) {
        super(exception);
    }

    public CannotCreateHandlingEventException() {
        super();
    }
}
