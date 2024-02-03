package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartyTest {
    private Party testParty;
    private Character testCharacter1;
    private List<Character> checkList;

    @BeforeEach
    void setup() {
        testParty = new Party("Misters");
        testCharacter1 = new Character("Connor Briggs");
        checkList = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(checkList, testParty.getMembers());
        assertEquals(0, testParty.getNumMembers());
        assertEquals("Misters", testParty.getName());
    }

    @Test
    void testAddMemberSingle() {
        testParty.addMember(testCharacter1);
        checkList.add(testCharacter1);
        assertEquals(1, testParty.getNumMembers());
        assertEquals(checkList, testParty.getMembers());
    }

    @Test
    void testAddMemberMultiple() {
        Character testCharacter2 = new Character("Kala Yavan");
        Character testCharacter3 = new Character("Dominic Eros");
        testParty.addMember(testCharacter1);
        testParty.addMember(testCharacter2);
        testParty.addMember(testCharacter3);
        assertEquals(3, testParty.getNumMembers());
        checkList.add(testCharacter1);
        checkList.add(testCharacter2);
        checkList.add(testCharacter3);
        assertEquals(checkList, testParty.getMembers());
    }

    @Test
    void testRemoveMemberSingle() {
        testParty.addMember(testCharacter1);
        testParty.removeMember(0);
        assertEquals(0, testParty.getNumMembers());
        assertEquals(checkList, testParty.getMembers());
    }

    @Test
    void testRemoveMemberSeveralNotAll() {
        Character testCharacter2 = new Character("Kala Yavan");
        Character testCharacter3 = new Character("Dominic Eros");
        testParty.addMember(testCharacter1);
        testParty.addMember(testCharacter2);
        testParty.addMember(testCharacter3);
        testParty.removeMember(1);
        testParty.removeMember(0);
        assertEquals(1, testParty.getNumMembers());
        checkList.add(testCharacter3);
        assertEquals(checkList, testParty.getMembers());
    }
}
