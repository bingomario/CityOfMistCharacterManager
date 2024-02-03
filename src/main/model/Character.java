package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents a Character with a name and a set of Themes and Statuses
public class Character implements Writable {
    private String name;
    private List<Theme> themes;
    private List<Status> statuses;
    private List<String> storyTags;
    private int buildUp;
    private int numEvolutions;

    //EFFECTS: Creates a new Character with the given name and no themes, statuses, or storyTags,
    //and 0 buildUp or evolutions
    public Character(String name) {
        this.name = name;
        this.themes = new ArrayList<>();
        this.statuses = new ArrayList<>();
        this.storyTags = new ArrayList<>();
        this.buildUp = 0;
        this.numEvolutions = 0;
    }

    //MODIFIES: EventLog
    //EFFECTS: Logs the selection (or viewing of character information) to the eventlog
    public void logSelect() {
        Event selectEvent = new Event(this.name + " selected");
        EventLog.getInstance().logEvent(selectEvent);
    }

    //MODIFIES: this
    //EFFECTS: Adds given theme to list of themes
    public void addTheme(Theme theme) {
        this.themes.add(theme);
    }

    //REQUIRES: 0 <= index < themes.size()
    //MODIFIES: this
    //EFFECTS: Removes the theme at the given index from themes
    public void removeTheme(int index) {
        this.themes.remove(index);
    }

    //MODIFIES: this
    //EFFECTS: Adds given status to list of statuses
    public void addStatus(Status status) {
        this.statuses.add(status);
    }

    //REQUIRES: 0 <= index < statuses.size()
    //MODIFIES: this
    //EFFECTS: Removes the status at the given index from statuses
    public void removeStatus(int index) {
        this.statuses.remove(index);
    }

    //REQUIRES: tag is not an empty string
    //MODIFIES: this
    //EFFECTS: Adds given tag to list of story tags
    public void addStoryTag(String tag) {
        this.storyTags.add(tag);
    }

    //REQUIRES: 0 <= index < storyTags.size()
    //MODIFIES: this
    //EFFECTS: Removes the tag at the given index from storyTags
    public void removeStoryTag(int index) {
        this.storyTags.remove(index);
    }

    //REQUIRES: buildUp > 0
    //MODIFIES: this
    //EFFECTS: adds the given amount to build up,
    //For every 5 build up adds one to numEvolutions and reduces build up by 5,
    //returns true if at least one was added to numEvolutions, false otherwise
    public boolean addBuildUp(int buildUp) {
        boolean evolution = false;
        this.buildUp = this.buildUp + buildUp;
        while (this.buildUp >= 5) {
            evolution = true;
            this.numEvolutions = this.numEvolutions + 1;
            this.buildUp = this.buildUp - 5;
        }
        return evolution;
    }

    public String getName() {
        return this.name;
    }

    public List<Theme> getThemes() {
        return this.themes;
    }

    public List<Status> getStatuses() {
        return this.statuses;
    }

    public List<String> getStoryTags() {
        return this.storyTags;
    }

    public int getBuildUp() {
        return this.buildUp;
    }

    public int getNumEvolutions() {
        return this.numEvolutions;
    }

    //EFFECTS: returns a JSON object representation of the character and all contained elements
    @Override
    public JSONObject toJson() {
        JSONObject character = new JSONObject();
        character.put("name", name);
        character.put("buildUp", buildUp);
        character.put("numEvolutions", numEvolutions);
        character.put("themes", themesToJson());
        character.put("statuses", statusesToJson());
        character.put("storyTags", tagsToJson());
        return character;
    }

    //EFFECTS: Returns a JSON array of all character themes
    private JSONArray themesToJson() {
        JSONArray memberArray = new JSONArray();
        for (Theme theme : this.themes) {
            memberArray.put(theme.toJson());
        }
        return memberArray;
    }

    //EFFECTS: Returns a JSON array of all character statuses
    private JSONArray statusesToJson() {
        JSONArray memberArray = new JSONArray();
        for (Status status : this.statuses) {
            memberArray.put(status.toJson());
        }
        return memberArray;
    }

    //EFFECTS: Returns a JSON array of all character story tags
    private JSONArray tagsToJson() {
        JSONArray memberArray = new JSONArray();
        for (String tag : this.storyTags) {
            memberArray.put(tag);
        }
        return memberArray;
    }

}
