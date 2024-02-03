package persistence;

import org.json.JSONObject;

//Interface to determine if something can be written to json file
//Interface credit goes to UBC CPSC 210 JsonSerializationDemo
//Demo Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
