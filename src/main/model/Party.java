package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


//Represents a Party with a List of Characters
public class Party implements Writable {
    private final String name;
    private List<Character> members;

    //EFFECTS: Creates a new Party with no members
    public Party(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Adds given character to the party
    public void addMember(Character character) {
        this.members.add(character);
        Event addEvent = new Event("Added " + character.getName() + " to party " + this.name);
        EventLog.getInstance().logEvent(addEvent);
    }

    //REQUIRES: 0 <= index <= members.size()
    //MODIFIES: this
    //EFFECTS: Removes the character at the given index from the party
    public void removeMember(int index) {
        Event removeEvent = new Event("Removed " + members.get(index).getName() + " from party " + this.name);
        EventLog.getInstance().logEvent(removeEvent);
        this.members.remove(index);
    }

    public String getName() {
        return this.name;
    }

    public List<Character> getMembers() {
        return members;
    }

    public int getNumMembers() {
        return members.size();
    }

    //EFFECTS: returns a JSON object representation of the party and all contained elements
    @Override
    public JSONObject toJson() {
        JSONObject party = new JSONObject();
        party.put("name", this.name);
        party.put("members", membersToJson());
        return party;
    }

    //EFFECTS: Returns a JSON array of all party members
    private JSONArray membersToJson() {
        JSONArray memberArray = new JSONArray();
        for (Character character : this.members) {
            memberArray.put(character.toJson());
        }
        return memberArray;
    }
}
