package com.cargo.tracking.system.domain.model.cargo;

public enum RoutingStatus {
    NOT_ROUTED, ROUTED, MISROUTED;

    public boolean sameValueAs(RoutingStatus routingStatus) {
        return this.equals(routingStatus);
    }
}
