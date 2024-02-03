package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Interface for a Theme
public class Theme implements Writable {
    private List<String> tags;
    private List<String> weaknesses;
    private int attention;
    private int numImprovements;
    private int loss;
    private final String name;
    private String core;

    //EFFECTS: Creates a new Mythos theme, with the given name and mystery,
    //0 attention, improvements, and fade, as well as no tags and weaknesses
    public Theme(String name, String core) {
        this.name = name;
        this.core = core;
        this.tags = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.attention = 0;
        this.numImprovements = 0;
        this.loss = 0;
    }


    //MODIFIES: this
    //EFFECTS: Changes mystery to given string
    public void setCoreStatement(String core) {
        this.core = core;
    }

    //REQUIRES: tag is not an empty string
    //MODIFIES: this
    //EFFECTS: adds the given tag to list of tags

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    //REQUIRES: 0 <= index < tags.size()
    //MODIFIES: this
    //EFFECTS: removes the tag at given index from list of tags

    public void removeTag(int index) {
        this.tags.remove(index);
    }

    //REQUIRES: weakness is not an empty string
    //MODIFIES: this
    //EFFECTS: adds the given weakness to list of weaknesses

    public void addWeakness(String weakness) {
        this.weaknesses.add(weakness);
    }

    //REQUIRES: 0 <= index < weaknesses.size()
    //MODIFIES: this
    //EFFECTS: removes the weakness at given index from list of weaknesses

    public void removeWeakness(int index) {
        this.weaknesses.remove(index);
    }

    //MODIFIES: this
    //EFFECTS: adds 1 to current attention (xp),
    //if attention is at 3 resets to 0, adds 1 to improvements, and returns true
    //Returns false otherwise

    public boolean markAttention() {
        boolean improved = false;
        this.attention = this.attention + 1;
        if (this.attention >= 3) {
            this.attention = 0;
            this.numImprovements = this.numImprovements + 1;
            improved = true;
        }
        return improved;
    }

    //MODIFIES: this
    //EFFECTS: adds 1 to current fade if less than 3,
    //if fade is at 3, returns true, returns false otherwise

    public boolean markLoss() {
        boolean faded = false;
        this.loss = Math.min(3, this.loss + 1);
        if (this.loss == 3) {
            faded = true;
        }
        return faded;
    }


    public List<String> getTags() {
        return this.tags;
    }


    public List<String> getWeaknesses() {
        return this.weaknesses;
    }


    public int getAttention() {
        return this.attention;
    }


    public int getNumImprovements() {
        return this.numImprovements;
    }


    public int getLoss() {
        return this.loss;
    }


    public String getName() {
        return this.name;
    }


    public String getCoreStatement() {
        return this.core;
    }

    //EFFECTS: returns a JSON object representation of the theme and all contained elements
    @Override
    public JSONObject toJson() {
        JSONObject theme = new JSONObject();
        theme.put("type", "m");
        theme.put("name", name);
        theme.put("core", core);
        theme.put("attention", attention);
        theme.put("improvements", numImprovements);
        theme.put("loss", loss);
        theme.put("tags", tagsToJson(this.tags));
        theme.put("weaknesses", tagsToJson(this.weaknesses));
        return theme;
    }

    //EFFECTS: returns a JSON Array of given list of tags (Strings)
    private JSONArray tagsToJson(List<String> tags) {
        JSONArray tagArray = new JSONArray();
        for (String tag : tags) {
            tagArray.put(tag);
        }
        return tagArray;
    }
}
