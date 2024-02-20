package com.cargo.tracking.system.domain.model.cargo;

import com.cargo.tracking.system.domain.model.location.Location;
import com.cargo.tracking.system.domain.model.voyage.Voyage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Leg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "voyage_id")
    @NotNull
    private Voyage voyage;
    @ManyToOne
    @JoinColumn(name = "load_location_id")
    @NotNull
    private Location loadLocation;
    @ManyToOne
    @JoinColumn(name = "unload_location_id")
    @NotNull
    private Location unloadLocation;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "load_time")
    private Date loadTime;
    @Column(name = "unload_time")
    private Date unloadTime;

    public Leg() {
    }

    public Leg(Voyage voyage, Location loadLocation, Location unloadLocation, Date loadTime, Date unloadTime) {
        this.voyage = voyage;
        this.loadLocation = loadLocation;
        this.unloadLocation = unloadLocation;
        this.loadTime = loadTime;
        this.unloadTime = unloadTime;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public Location getLoadLocation() {
        return loadLocation;
    }

    public Location getUnloadLocation() {
        return unloadLocation;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public Date getUnloadTime() {
        return unloadTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leg leg = (Leg) o;
        return Objects.equals(voyage, leg.voyage) && Objects.equals(loadLocation, leg.loadLocation) && Objects.equals(unloadLocation, leg.unloadLocation) && Objects.equals(loadTime, leg.loadTime) && Objects.equals(unloadTime, leg.unloadTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyage, loadLocation, unloadLocation, loadTime, unloadTime);
    }

    @Override
    public String toString() {
        return "Leg{" +
                "id=" + id +
                ", voyage=" + voyage +
                ", loadLocation=" + loadLocation +
                ", unloadLocation=" + unloadLocation +
                ", loadTime=" + loadTime +
                ", unloadTime=" + unloadTime +
                '}';
    }
}
