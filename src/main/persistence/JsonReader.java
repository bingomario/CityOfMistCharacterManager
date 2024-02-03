package persistence;

import model.Party;
import model.Character;
import model.Theme;
import model.Logos;
import model.Mythos;
import model.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//Loads a JSON File. Code based upon provided code in UBC CPSC 210 JsonSerializationDemo file
//Demo Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String input;

    //EFFECTS: Constructs a reader with the given file location as input
    public JsonReader(String input) {
        this.input = input;
    }

    //EFFECTS: Constructs a reader with parties.json as input
    public JsonReader() {
        this.input = "./data/parties.json";
    }

    //EFFECTS: Returns a list of parties from objects Json file,
    // throws IOException if anything goes wrong in the file reading process
    public List<Party> readFile() throws IOException {
        String jsonData = fileToString(input);
        JSONObject jsonParties = new JSONObject(jsonData);
        return buildParties(jsonParties);
    }

    // EFFECTS: reads source file as string and returns it
    //Method from UBC CPSC 210 JsonSerializationDemo
    private String fileToString(String input) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(input), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    //EFFECTS: Converts JSON object into list of Parties
    private List<Party> buildParties(JSONObject jsonData) {
        List<Party> parties = new ArrayList<>();
        JSONArray jsonParties = jsonData.getJSONArray("parties");
        for (Object object : jsonParties) {
            JSONObject party = (JSONObject) object;
            parties.add(buildParty(party));
        }
        return parties;
    }

    //EFFECTS: Converts JSON object into Party
    private Party buildParty(JSONObject jsonParty) {
        String name = jsonParty.getString("name");
        Party party = new Party(name);
        JSONArray jsonCharacters = jsonParty.getJSONArray("members");
        for (Object object : jsonCharacters) {
            JSONObject character = (JSONObject) object;
            party.addMember(buildCharacter(character));
        }
        return party;
    }

    //EFFECTS: Converts JSON object into Character
    private Character buildCharacter(JSONObject jsonCharacter) {
        String name = jsonCharacter.getString("name");
        Character character = new Character(name);
        int buildUp = jsonCharacter.getInt("buildUp");
        character.addBuildUp(buildUp);
        int evolutions = jsonCharacter.getInt("numEvolutions");
        character.addBuildUp(evolutions * 5);
        JSONArray jsonThemes = jsonCharacter.getJSONArray("themes");
        JSONArray jsonStatuses = jsonCharacter.getJSONArray("statuses");
        JSONArray jsonTags = jsonCharacter.getJSONArray("storyTags");
        for (Object object : jsonThemes) {
            JSONObject theme = (JSONObject) object;
            character.addTheme(buildTheme(theme));
        }
        for (Object object : jsonStatuses) {
            JSONObject status = (JSONObject) object;
            character.addStatus(buildStatus(status));
        }
        for (Object object : jsonTags) {
            String tag = object.toString();
            character.addStoryTag(tag);
        }
        return character;
    }

    //EFFECTS: Converts JSON object into Theme
    private Theme buildTheme(JSONObject jsonTheme) {
        String type = jsonTheme.getString("type");
        String name = jsonTheme.getString("name");
        String core = jsonTheme.getString("core");
        Theme theme;
        if (type.equals("m")) {
            theme = new Mythos(name, core);
        } else {
            theme = new Logos(name, core);
        }
        int attention = jsonTheme.getInt("attention");
        int improvements = jsonTheme.getInt("improvements");
        int loss = jsonTheme.getInt("loss");
        for (int i = 0; i < attention + improvements * 3; i++) {
            theme.markAttention();
        }
        for (int i = 0; i < loss; i++) {
            theme.markLoss();
        }
        addTags(theme, jsonTheme);
        return theme;
    }

    //EFFECTS: Adds tags from JSONObject to given Theme
    private void addTags(Theme theme, JSONObject jsonTheme) {
        JSONArray jsonTags = jsonTheme.getJSONArray("tags");
        JSONArray jsonWeaknesses = jsonTheme.getJSONArray("weaknesses");
        for (Object object : jsonTags) {
            String tag = object.toString();
            theme.addTag(tag);
        }
        for (Object object : jsonWeaknesses) {
            String tag = object.toString();
            theme.addWeakness(tag);
        }
    }

    //EFFECTS: Converts JSONObject to Status
    private Status buildStatus(JSONObject jsonStatus) {
        String name = jsonStatus.getString("name");
        int tier = jsonStatus.getInt("tier");
        return new Status(name, tier);
    }


}
