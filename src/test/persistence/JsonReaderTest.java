package persistence;

import model.Character;
import model.Party;
import model.Status;
import model.Theme;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Tests for JSON file reading, based on tests provided in UBC CPSC 210 JsonSerializationDemo
//Demo Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest{
    List<Party> partyList;

    @Test
    void testNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            reader.readFile();
            fail("Expected IOException");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testDefaultFile() {
        JsonReader reader = new JsonReader();
        try {
            reader.readFile();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void testReaderGenericFile() {
        JsonReader reader = new JsonReader("./data/testReaderMisters.json");
        try {
            partyList = reader.readFile();
        } catch(IOException e) {
            fail("IOException");
        }
        assertEquals(1, partyList.size());
        Character dominic = partyList.get(0).getMembers().get(0);
        assertEquals("Dominic Eros", dominic.getName());
        assertEquals(4, dominic.getBuildUp());
        assertEquals(1, dominic.getNumEvolutions());
        List<Theme> themes = dominic.getThemes();
        assertEquals(2, themes.size());
        Theme theme = themes.get(1);
        assertEquals("What Needed To Be Done", theme.getName());
        assertEquals("The truth is whatever I need it to be.", theme.getCoreStatement());
        assertEquals(0, theme.getAttention());
        assertEquals(0, theme.getLoss());
        assertEquals(0, theme.getNumImprovements());
        List<String> tags = theme.getTags();
        assertEquals(3, tags.size());
        assertEquals("Calm Under Pressure", tags.get(1));
        List<String> weaknesses = theme.getWeaknesses();
        assertEquals(1, weaknesses.size());
        assertEquals("Fear of Blood", weaknesses.get(0));
        theme = themes.get(0);
        assertEquals("Heightened Senses", theme.getName());
        assertEquals("How much can I experience myself?", theme.getCoreStatement());
        assertEquals(2, theme.getAttention());
        assertEquals(1, theme.getLoss());
        assertEquals(2, theme.getNumImprovements());
        tags = theme.getTags();
        assertEquals(5, tags.size());
        assertEquals("Uncanny Reflexes", tags.get(3));
        weaknesses = theme.getWeaknesses();
        assertEquals(1, weaknesses.size());
        assertEquals("Overwhelmed Senses", weaknesses.get(0));
        List<Status> statuses = dominic.getStatuses();
        assertEquals(1, statuses.size());
        assertEquals("Bruised", statuses.get(0).getName());
        assertEquals(1, statuses.get(0).getTier());
        List<String> storyTags = dominic.getStoryTags();
        assertEquals(1, storyTags.size());
        assertEquals("Gun", storyTags.get(0));
    }

    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            List<Party> partyList = reader.readFile();
            assertEquals(0, partyList.size());
        } catch(IOException e) {
            fail("IOException");
        }
    }
}
