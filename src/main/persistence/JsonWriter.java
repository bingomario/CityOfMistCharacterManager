package persistence;

import model.Party;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

//Writes a JSON file. Code based on code provided in UBC CPSC 210 JsonSerializationDemo
//Demo Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String output;

    //EFFECTS: Creates a new writer to write to a given output file
    public JsonWriter(String output) {
        this.output = output;
    }

    //EFFECTS: Creates a new writer to write to parties.json
    public JsonWriter() {
        this.output = "./data/parties.json";
    }

    //MODIFIES: this
    //EFFECTS: Opens writer, throwing FileNotFoundException if writer's output file is invalid
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(output);
    }

    //MODIFIES: this
    //EFFECTS: Closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: Writes all parties to file
    public void writeToFile(List<Party> toSave) {
        JSONObject parties = new JSONObject();
        parties.put("name", "saveData");
        parties.put("parties", saveParties(toSave));
        writer.print(parties.toString(TAB));
    }

    //EFFECTS: Saves all Parties in JSONArray
    private JSONArray saveParties(List<Party> parties) {
        JSONArray partyArray = new JSONArray();
        for (Party party : parties) {
            partyArray.put(party.toJson());
        }
        return partyArray;
    }

}
