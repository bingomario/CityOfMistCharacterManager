package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {
    private Status testStatus;

    @Test
    void testConstructor() {
        testStatus = new Status("Bruised", 1);
        assertEquals("Bruised", testStatus.getName());
        assertEquals(1, testStatus.getTier());
    }
}
