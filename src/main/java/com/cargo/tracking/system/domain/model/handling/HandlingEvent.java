package com.cargo.tracking.system.domain.model.handling;

import com.cargo.tracking.system.domain.model.cargo.Cargo;
import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.model.voyage.Voyage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

public class HandlingEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;
    @ManyToOne
    @JoinColumn(name = "voyage_id")
    private Voyage voyage;
    @ManyToOne
    @JoinColumn(name = "location_id")
    @NotNull
    private Location location;
    @Temporal(TemporalType.DATE)
    @Column(name = "completion_time")
    @NotNull
    private Date completionTime;
    @Temporal(TemporalType.DATE)
    @Column(name = "registration_time")
    @NotNull
    private Date registrationTime;
    @ManyToOne
    @JoinColumn(name = "cargo_id")
    @NotNull
    private Cargo cargo;
    @Transient
    private String summary;

    public enum Type {
        LOAD(true),
        UNLOAD(true),
        RECEIVE(false),
        CLAIM(false),
        CUSTOMS(false);

        private final boolean voyageRequired;

        private Type(boolean voyageRequired) {
            this.voyageRequired = voyageRequired;
        }

        public boolean requiresVoyage() {
            return voyageRequired;
        }

        public boolean prohibitsVoyage() {
            return !requiresVoyage();
        }

        public boolean sameValueAs(Type other) {
            return other != null && this.equals(other);
        }
    }

    public HandlingEvent() {
    }

    public HandlingEvent(Cargo cargo, Date completionTime,
                         Date registrationTime, Type type, Location location, Voyage voyage) {
        this.voyage = voyage;
        this.completionTime = (Date) completionTime.clone();
        this.registrationTime = (Date) registrationTime.clone();
        this.type = type;
        this.location = location;
        this.cargo = cargo;
    }

    public HandlingEvent(Cargo cargo, Date completionTime,
                         Date registrationTime, Type type, Location location) {

        this.completionTime = (Date) completionTime.clone();
        this.registrationTime = (Date) registrationTime.clone();
        this.type = type;
        this.location = location;
        this.cargo = cargo;
        this.voyage = null;
    }


    public Type getType() {
        return type;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public Location getLocation() {
        return location;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public String getSummary() {
        StringBuilder builder = new StringBuilder(location.getName()).append("\n")
                .append(completionTime).append("\n")
                .append("Type: ").append(type).append("\n")
                .append("Reg.: ").append(registrationTime)
                .append("\n");

        if (voyage != null) {
            builder.append("Voyage: ").append(voyage.getVoyageNumber());
        }
        return builder.toString();

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n--- Handling event ---\n")
                .append("Cargo: ").append(cargo.getTrackingId()).append("\n")
                .append("Type: ").append(type).append("\n")
                .append("Location: ").append(location.getName()).append("\n")
                .append("Completed on: ").append(completionTime).append("\n")
                .append("Registered on: ").append(registrationTime)
                .append("\n");

        if (voyage != null) {
            builder.append("Voyage: ").append(voyage.getVoyageNumber())
                    .append("\n");
        }

        return builder.toString();
    }
}
