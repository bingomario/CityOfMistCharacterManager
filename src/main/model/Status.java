package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a status with a Name and a Tier
public class Status implements Writable {
    private String name;
    private int tier;

    // EFFECTS: Creates a new Status with the given name and tier
    public Status(String name, int tier) {
        this.name = name;
        this.tier = tier;
    }

    public String getName() {
        return this.name;
    }

    public int getTier() {
        return this.tier;
    }

    //EFFECTS: returns a JSON object representation of the status
    @Override
    public JSONObject toJson() {
        JSONObject status = new JSONObject();
        status.put("name", name);
        status.put("tier", tier);
        return status;
    }
}
