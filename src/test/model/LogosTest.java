package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogosTest {
    private Logos testLogos;
    private String testTag;
    private String testWeakness;


    @BeforeEach
    void setup(){
        this.testLogos = new Logos("Clark Kent", "The Daily Planet");
        this.testTag = "Mild-Mannered";
        this.testWeakness = "Hidden Identity";
    }

    @Test
    void testConstructor() {
        assertEquals("Clark Kent", testLogos.getName());
        assertEquals("The Daily Planet", testLogos.getCoreStatement());
        assertEquals(0, testLogos.getAttention());
        assertEquals(0, testLogos.getNumImprovements());
        List<String> emptyList = new ArrayList<>();
        assertEquals(emptyList, testLogos.getTags());
        assertEquals(emptyList, testLogos.getWeaknesses());
    }

    @Test
    void testSetMystery() {
        testLogos.setCoreStatement("Orphan with a Good Heart");
        assertEquals("Orphan with a Good Heart", testLogos.getCoreStatement());
    }

    @Test
    void testAddTagSingle() {
        testLogos.addTag(testTag);
        List<String> tags = testLogos.getTags();
        assertEquals(1, tags.size());
        assertEquals(testTag, tags.get(0));
    }

    @Test
    void testAddTagMultiple() {
        testLogos.addTag(testTag);
        testLogos.addTag("Nerd Facts");
        List<String> tags = testLogos.getTags();
        assertEquals(2, tags.size());
        assertEquals(testTag, tags.get(0));
        assertEquals("Nerd Facts", tags.get(1));
    }

    @Test
    void testRemoveTagSingle() {
        testLogos.addTag(testTag);
        testLogos.removeTag(0);
        List<String> tags = testLogos.getTags();
        assertEquals(0, tags.size());
    }

    @Test
    void testRemoveTagMultipleWithRemainingTag() {
        testLogos.addTag(testTag);
        testLogos.addTag("Nerd Facts");
        testLogos.addTag("Journalist");
        testLogos.removeTag(0);
        testLogos.removeTag(1);
        List<String> tags = testLogos.getTags();
        assertEquals(1, tags.size());
        assertEquals("Nerd Facts", tags.get(0));
    }

    @Test
    void testRemoveTagMultipleWithoutRemainingTag() {
        testLogos.addTag(testTag);
        testLogos.addTag("Nerd Facts");
        testLogos.addTag("Journalist");
        testLogos.removeTag(0);
        testLogos.removeTag(1);
        testLogos.removeTag(0);
        List<String> tags = testLogos.getTags();
        assertEquals(0, tags.size());
    }

    @Test
    void testAddWeaknessSingle() {
        testLogos.addWeakness(testWeakness);
        List<String> tags = testLogos.getWeaknesses();
        assertEquals(1, tags.size());
        assertEquals(testWeakness, tags.get(0));
    }

    @Test
    void testAddWeaknessMultiple() {
        testLogos.addWeakness(testWeakness);
        testLogos.addWeakness("Dropping Glasses");
        List<String> tags = testLogos.getWeaknesses();
        assertEquals(2, tags.size());
        assertEquals(testWeakness, tags.get(0));
        assertEquals("Dropping Glasses", tags.get(1));
    }

    @Test
    void testRemoveWeaknessSingle() {
        testLogos.addWeakness(testWeakness);
        testLogos.removeWeakness(0);
        List<String> tags = testLogos.getWeaknesses();
        assertEquals(0, tags.size());
    }

    @Test
    void testRemoveWeaknessMultipleWithRemainingWeakness() {
        testLogos.addWeakness(testWeakness);
        testLogos.addWeakness("Dropping Glasses");
        testLogos.addWeakness("Shy");
        testLogos.removeWeakness(0);
        testLogos.removeWeakness(1);
        List<String> tags = testLogos.getWeaknesses();
        assertEquals(1, tags.size());
        assertEquals("Dropping Glasses", tags.get(0));
    }

    @Test
    void testRemoveWeaknessMultipleWithoutRemainingWeakness() {
        testLogos.addWeakness(testWeakness);
        testLogos.addWeakness("Dropping Glasses");
        testLogos.addWeakness("Shy");
        testLogos.removeWeakness(0);
        testLogos.removeWeakness(1);
        testLogos.removeWeakness(0);
        List<String> tags = testLogos.getWeaknesses();
        assertEquals(0, tags.size());
    }

    @Test
    void testMarkAttentionSingle() {
        assertFalse(testLogos.markAttention());
        assertEquals(1, testLogos.getAttention());
    }

    @Test
    void testMarkAttentionBoundary() {
        assertFalse(testLogos.markAttention());
        assertFalse(testLogos.markAttention());
        assertTrue(testLogos.markAttention());
        assertEquals(0, testLogos.getAttention());
        assertEquals(1, testLogos.getNumImprovements());
    }

    @Test
    void testMarkAttentionMany() {
        for (int i = 0; i < 5; i++){
            testLogos.markAttention();
        }
        assertTrue(testLogos.markAttention());
        assertEquals(0, testLogos.getAttention());
        assertFalse(testLogos.markAttention());
        assertEquals(1, testLogos.getAttention());
        assertEquals(2, testLogos.getNumImprovements());
    }

    @Test
    void testMarkFadeSingle() {
        assertFalse(testLogos.markLoss());
        assertEquals(1, testLogos.getLoss());
    }

    @Test
    void testMarkFadeMany() {
        assertFalse(testLogos.markLoss());
        assertFalse(testLogos.markLoss());
        assertTrue(testLogos.markLoss());
        assertEquals(3, testLogos.getLoss());
        assertTrue(testLogos.markLoss());
        assertEquals(3, testLogos.getLoss());
    }








}