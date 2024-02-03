package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MythosTest {
    private Mythos testMythos;
    private String testTag;
    private String testWeakness;


    @BeforeEach
    void setup(){
        this.testMythos = new Mythos("Man of Steel", "How can I protect them all?");
        this.testTag = "Invincible";
        this.testWeakness = "Kryptonite";
    }

    @Test
    void testConstructor() {
        assertEquals("Man of Steel", testMythos.getName());
        assertEquals("How can I protect them all?", testMythos.getCoreStatement());
        assertEquals(0, testMythos.getAttention());
        assertEquals(0, testMythos.getNumImprovements());
        List<String> emptyList = new ArrayList<>();
        assertEquals(emptyList, testMythos.getTags());
        assertEquals(emptyList, testMythos.getWeaknesses());
    }

    @Test
    void testSetMystery() {
        testMythos.setCoreStatement("Who is Lex Luthor?");
        assertEquals("Who is Lex Luthor?", testMythos.getCoreStatement());
    }

    @Test
    void testAddTagSingle() {
        testMythos.addTag(testTag);
        List<String> tags = testMythos.getTags();
        assertEquals(1, tags.size());
        assertEquals(testTag, tags.get(0));
    }

    @Test
    void testAddTagMultiple() {
        testMythos.addTag(testTag);
        testMythos.addTag("Flight");
        List<String> tags = testMythos.getTags();
        assertEquals(2, tags.size());
        assertEquals(testTag, tags.get(0));
        assertEquals("Flight", tags.get(1));
    }

    @Test
    void testRemoveTagSingle() {
        testMythos.addTag(testTag);
        testMythos.removeTag(0);
        List<String> tags = testMythos.getTags();
        assertEquals(0, tags.size());
    }

    @Test
    void testRemoveTagMultipleWithRemainingTag() {
        testMythos.addTag(testTag);
        testMythos.addTag("Flight");
        testMythos.addTag("Powered by the Sun");
        testMythos.removeTag(0);
        testMythos.removeTag(1);
        List<String> tags = testMythos.getTags();
        assertEquals(1, tags.size());
        assertEquals("Flight", tags.get(0));
    }

    @Test
    void testRemoveTagMultipleWithoutRemainingTag() {
        testMythos.addTag(testTag);
        testMythos.addTag("Flight");
        testMythos.addTag("Powered by the Sun");
        testMythos.removeTag(0);
        testMythos.removeTag(1);
        testMythos.removeTag(0);
        List<String> tags = testMythos.getTags();
        assertEquals(0, tags.size());
    }

    @Test
    void testAddWeaknessSingle() {
        testMythos.addWeakness(testWeakness);
        List<String> tags = testMythos.getWeaknesses();
        assertEquals(1, tags.size());
        assertEquals(testWeakness, tags.get(0));
    }

    @Test
    void testAddWeaknessMultiple() {
        testMythos.addWeakness(testWeakness);
        testMythos.addWeakness("Only One Man");
        List<String> tags = testMythos.getWeaknesses();
        assertEquals(2, tags.size());
        assertEquals(testWeakness, tags.get(0));
        assertEquals("Only One Man", tags.get(1));
    }

    @Test
    void testRemoveWeaknessSingle() {
        testMythos.addWeakness(testWeakness);
        testMythos.removeWeakness(0);
        List<String> tags = testMythos.getWeaknesses();
        assertEquals(0, tags.size());
    }

    @Test
    void testRemoveWeaknessMultipleWithRemainingWeakness() {
        testMythos.addWeakness(testWeakness);
        testMythos.addWeakness("Only One Man");
        testMythos.addWeakness("Collateral Damage");
        testMythos.removeWeakness(0);
        testMythos.removeWeakness(1);
        List<String> tags = testMythos.getWeaknesses();
        assertEquals(1, tags.size());
        assertEquals("Only One Man", tags.get(0));
    }

    @Test
    void testRemoveWeaknessMultipleWithoutRemainingWeakness() {
        testMythos.addWeakness(testWeakness);
        testMythos.addWeakness("Only One Man");
        testMythos.addWeakness("Collateral Damage");
        testMythos.removeWeakness(0);
        testMythos.removeWeakness(1);
        testMythos.removeWeakness(0);
        List<String> tags = testMythos.getWeaknesses();
        assertEquals(0, tags.size());
    }

    @Test
    void testMarkAttentionSingle() {
        assertFalse(testMythos.markAttention());
        assertEquals(1, testMythos.getAttention());
    }

    @Test
    void testMarkAttentionBoundary() {
        assertFalse(testMythos.markAttention());
        assertFalse(testMythos.markAttention());
        assertTrue(testMythos.markAttention());
        assertEquals(0, testMythos.getAttention());
        assertEquals(1, testMythos.getNumImprovements());
    }

    @Test
    void testMarkAttentionMany() {
        for (int i = 0; i < 5; i++){
            testMythos.markAttention();
        }
        assertTrue(testMythos.markAttention());
        assertEquals(0, testMythos.getAttention());
        assertFalse(testMythos.markAttention());
        assertEquals(1, testMythos.getAttention());
        assertEquals(2, testMythos.getNumImprovements());
    }

    @Test
    void testMarkFadeSingle() {
        assertFalse(testMythos.markLoss());
        assertEquals(1, testMythos.getLoss());
    }

    @Test
    void testMarkFadeMany() {
        assertFalse(testMythos.markLoss());
        assertFalse(testMythos.markLoss());
        assertTrue(testMythos.markLoss());
        assertEquals(3, testMythos.getLoss());
        assertTrue(testMythos.markLoss());
        assertEquals(3, testMythos.getLoss());
    }








}