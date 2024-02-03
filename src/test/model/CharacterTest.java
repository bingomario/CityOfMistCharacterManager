package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    private Character testCharacter;
    private Status testStatus1;
    private String testTag1;
    private Theme testTheme1;

    @BeforeEach
    void setup() {
        testCharacter = new Character("Connor Briggs");
        testStatus1 = new Status("Bruised", 1);
        testTag1 = "Pistol";
        testTheme1 = new Mythos("Werewolf", "How did I become this creature?");
    }

    @Test
    void testConstructor() {
        assertEquals("Connor Briggs", testCharacter.getName());
        assertEquals(new ArrayList<Theme>(), testCharacter.getThemes());
        assertEquals(new ArrayList<String>(), testCharacter.getStoryTags());
        assertEquals(new ArrayList<Status>(), testCharacter.getStatuses());
        assertEquals(0, testCharacter.getBuildUp());
        assertEquals(0, testCharacter.getNumEvolutions());
    }

    @Test
    void testAddThemeSingle() {
        testCharacter.addTheme(testTheme1);
        List<Theme> list = new ArrayList<>();
        list.add(testTheme1);
        assertEquals(list, testCharacter.getThemes());
    }

    @Test
    void testAddThemeMultiple() {
        testCharacter.addTheme(testTheme1);
        List<Theme> list = new ArrayList<>();
        list.add(testTheme1);
        Theme testTheme2 = new Logos("Conspiracy Theorist", "Wake up sheeple!");
        testCharacter.addTheme(testTheme2);
        list.add(testTheme2);
        assertEquals(list, testCharacter.getThemes());
    }

    @Test
    void testRemoveThemeOne() {
        testCharacter.addTheme(testTheme1);
        List<Theme> list = new ArrayList<>();
        testCharacter.removeTheme(0);
        assertEquals(list, testCharacter.getThemes());
    }

    @Test
    void testAddMultipleThemesRemoveSome() {
        testCharacter.addTheme(testTheme1);
        List<Theme> list = new ArrayList<>();
        Theme testTheme2 = new Logos("Conspiracy Theorist", "Wake up Sheeple!");
        testCharacter.addTheme(testTheme2);
        Theme testTheme3 = new Logos("Goldie's Bike", "I'm Always to Late to Help");
        testCharacter.addTheme(testTheme3);
        testCharacter.removeTheme(1);
        testCharacter.removeTheme(0);
        list.add(testTheme3);
        assertEquals(list, testCharacter.getThemes());
    }
    
    @Test
    void testAddStatusSingle() {
        testCharacter.addStatus(testStatus1);
        List<Status> list = new ArrayList<>();
        list.add(testStatus1);
        assertEquals(list, testCharacter.getStatuses());
    }

    @Test
    void testAddStatusMultiple() {
        testCharacter.addStatus(testStatus1);
        List<Status> list = new ArrayList<>();
        list.add(testStatus1);
        Status testStatus2 = new Status("Irrationally Angry", 2);
        testCharacter.addStatus(testStatus2);
        list.add(testStatus2);
        assertEquals(list, testCharacter.getStatuses());
    }

    @Test
    void testRemoveStatusOne() {
        testCharacter.addStatus(testStatus1);
        List<Status> list = new ArrayList<>();
        testCharacter.removeStatus(0);
        assertEquals(list, testCharacter.getStatuses());
    }

    @Test
    void testAddMultipleStatusesRemoveSome() {
        testCharacter.addStatus(testStatus1);
        List<Status> list = new ArrayList<>();
        Status testStatus2 = new Status("Irrationally Angry", 2);
        testCharacter.addStatus(testStatus2);
        Status testStatus3 = new Status("Power of the Full Moon", 3);
        testCharacter.addStatus(testStatus3);
        testCharacter.removeStatus(1);
        testCharacter.removeStatus(0);
        list.add(testStatus3);
        assertEquals(list, testCharacter.getStatuses());
    }

    @Test
    void testAddStoryTagSingle() {
        testCharacter.addStoryTag(testTag1);
        List<String> list = new ArrayList<>();
        list.add(testTag1);
        assertEquals(list, testCharacter.getStoryTags());
    }

    @Test
    void testAddStoryTagMultiple() {
        testCharacter.addStoryTag(testTag1);
        List<String> list = new ArrayList<>();
        list.add(testTag1);
        String testTag2 = "Pack";
        testCharacter.addStoryTag(testTag2);
        list.add(testTag2);
        assertEquals(list, testCharacter.getStoryTags());
    }

    @Test
    void testRemoveStoryTagOne() {
        testCharacter.addStoryTag(testTag1);
        List<String> list = new ArrayList<>();
        testCharacter.removeStoryTag(0);
        assertEquals(list, testCharacter.getStoryTags());
    }

    @Test
    void testAddMultipleStoryTagsRemoveSome() {
        testCharacter.addStoryTag(testTag1);
        List<String> list = new ArrayList<>();
        String testTag2 = "Pack";
        testCharacter.addStoryTag(testTag2);
        String testTag3 = "Lab Coat";
        testCharacter.addStoryTag(testTag3);
        testCharacter.removeStoryTag(1);
        testCharacter.removeStoryTag(0);
        list.add(testTag3);
        assertEquals(list, testCharacter.getStoryTags());
    }

    @Test
    void testAddBuildUpSingle() {
        assertFalse(testCharacter.addBuildUp(1));
        assertEquals(0, testCharacter.getNumEvolutions());
        assertEquals(1, testCharacter.getBuildUp());
    }

    @Test
    void testAddBuildUpFive() {
        assertTrue(testCharacter.addBuildUp(5));
        assertEquals(1, testCharacter.getNumEvolutions());
        assertEquals(0, testCharacter.getBuildUp());
    }

    @Test
    void testAddBuildUpManySmall() {
        assertFalse(testCharacter.addBuildUp(3));
        assertTrue(testCharacter.addBuildUp(3));
        assertFalse(testCharacter.addBuildUp(3));
        assertTrue(testCharacter.addBuildUp(3));
        assertEquals(2, testCharacter.getNumEvolutions());
        assertEquals(2, testCharacter.getBuildUp());
    }

    @Test
    void testAddBuildUpOneLarge() {
        assertTrue(testCharacter.addBuildUp(5559));
        assertEquals(5559 / 5, testCharacter.getNumEvolutions());
        assertEquals(5559 % 5, testCharacter.getBuildUp());
    }

    @Test
    void testLogSelect() {
        testCharacter.logSelect();

        List<Event> l = new ArrayList<Event>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertEquals("Connor Briggs selected", l.get(l.size() - 1).getDescription());
    }
}

