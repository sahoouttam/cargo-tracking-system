package com.cargo.tracking.system.domain.model.cargo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Itinerary implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Date END_OF_DAYS = new Date(Long.MAX_VALUE);
    public static final Itinerary EMPTY_ITINERARY = new Itinerary();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cargo_id")
    @OrderBy("load_time")
    @Size(min = 1)
    private List<Leg> legs = Collections.emptyList();

    public Itinerary() {
    }

    public Itinerary(List<Leg> legs) {
        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(legs);
    }


}
