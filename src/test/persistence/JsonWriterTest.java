package persistence;

import model.Party;
import model.Character;

import model.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//Tests for JSON file writing, based on tests provided in UBC CPSC 210 JsonSerializationDemo
//Demo Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {
    private List<Party> parties;

    @BeforeEach
    void setup() {
        parties = new ArrayList<>();
    }

    @Test
    void testIncorrectFile() {
        JsonWriter writer = new JsonWriter("./data/invalid\0File!!!;;_.json");
        try {
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            //pass
        }
    }

    @Test
    void testDefaultFile() {
        JsonWriter writer = new JsonWriter();
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException");
        }
    }

    @Test
    void testWriteEmptyFile() {
        JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException");
        }
        writer.writeToFile(parties);
        writer.close();
        JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
        try {
            parties = reader.readFile();
            assertEquals(0, parties.size());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void testReadThenWriteFile() {
        JsonReader setupReader = new JsonReader("./data/testReaderMisters.json");
        List<Party> testParties = null;
        try {
            parties = setupReader.readFile();
            JsonWriter writer = new JsonWriter("./data/testWriterFromFile.json");
            writer.open();
            writer.writeToFile(parties);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterFromFile.json");
            testParties = reader.readFile();
        }catch (FileNotFoundException e) {
            fail("FileNotFound");
        }catch (IOException e) {
            fail("IOException");
        }
        assertEquals(parties.size(), testParties.size());
        Party party = parties.get(0);
        Party testParty = testParties.get(0);
        assertEquals(party.getName(), testParty.getName());
        assertEquals(party.getNumMembers(), testParty.getNumMembers());
        Character member = party.getMembers().get(0);
        Character testMember = testParty.getMembers().get(0);
        assertEquals(member.getName(), testMember.getName());
        assertEquals(member.getBuildUp(), testMember.getBuildUp());
        assertEquals(member.getNumEvolutions(), testMember.getNumEvolutions());
        assertEquals(member.getThemes().size(), testMember.getThemes().size());
        Theme theme = member.getThemes().get(0);
        Theme testTheme = testMember.getThemes().get(0);
        assertEquals(theme.getName(), testTheme.getName());
        assertEquals(theme.getAttention(), testTheme.getAttention());
        assertEquals(theme.getLoss(), testTheme.getLoss());
        assertEquals(theme.getTags().size(), testTheme.getTags().size());
        assertEquals(theme.getTags().get(0), testTheme.getTags().get(0));
        assertEquals(theme.getWeaknesses().get(0), testTheme.getWeaknesses().get(0));
    }
}
