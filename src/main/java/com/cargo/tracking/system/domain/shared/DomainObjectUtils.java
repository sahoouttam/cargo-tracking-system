package com.cargo.tracking.system.domain.shared;

public class DomainObjectUtils {

    public static <T> T nullSafe(T actual, T safe) {
        return actual == null ? safe : actual;
    }

    private DomainObjectUtils() {
    }

}
