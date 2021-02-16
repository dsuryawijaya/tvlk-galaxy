package com.galaxy.flight;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FlightGroups {

    private Map<String, FlightGroup> groups;

    public FlightGroups() {
        this.groups = new HashMap<>();
    }

    public void upsert(FlightGroup group){
        String hash = group.hash();
        FlightGroup flightGroup = groups.get(hash);
        if(flightGroup == null){
            groups.put(hash, group);
        } else {
            flightGroup.setAmount(flightGroup.getAmount().add(group.getAmount()));
            groups.put(hash, flightGroup);
        }
    }

    public void upsert(FlightGroups upsertGroup){
        for(String hash : upsertGroup.getGroups().keySet()){
            FlightGroup group = upsertGroup.getGroups().get(hash);
            this.upsert(group);
        }
    }

    public Map<String, FlightGroup> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, FlightGroup> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightGroups that = (FlightGroups) o;
        return Objects.equals(groups, that.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups);
    }
}
