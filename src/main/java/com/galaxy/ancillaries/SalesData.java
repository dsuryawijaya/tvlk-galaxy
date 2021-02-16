package com.galaxy.ancillaries;

import com.galaxy.flight.FlightGroups;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SalesData {
    private FlightGroups groups;
    private List<Ancillary> ancillaries;

    public SalesData() {
        groups = new FlightGroups();
        ancillaries = new ArrayList<>();
    }

    public void add(SalesData toAdd){
        this.groups.upsert(toAdd.getGroups());
        this.ancillaries.addAll(toAdd.getAncillaries());
    }

    public FlightGroups getGroups() {
        return groups;
    }

    public void setGroups(FlightGroups groups) {
        this.groups = groups;
    }

    public List<Ancillary> getAncillaries() {
        return ancillaries;
    }

    public void setAncillaries(List<Ancillary> ancillaries) {
        this.ancillaries = ancillaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesData salesData = (SalesData) o;
        return Objects.equals(groups, salesData.groups) &&
                Objects.equals(ancillaries, salesData.ancillaries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups, ancillaries);
    }
}
