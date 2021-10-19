package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class TestPlayer {

    @Test
    public void testGetName() {
        String expected = "Test";

        Player player = new Player("Test");
        String actual = player.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void testIsPlaying() {
        Player player = new Player("Test");
        boolean actual = player.isPlaying();
        assertFalse(actual);
    }

    @Test
    public void testSetPlaying() {
        Player player = new Player("Test");
        player.setPlaying(true);
        boolean actual = player.isPlaying();
        assertTrue(actual);
    }

    @Test
    public void testEqualsTrue() {
        Player aPlayer = new Player("Player-A");
        Player aPlayer2 = new Player("Player-A");
        boolean actual = aPlayer.equals(aPlayer2);
        assertTrue(actual);
    }

    @Test
    public void testEqualsFalse() {
        Player aPlayer = new Player("Player-A");
        Player bPlayer = new Player("Player-B");
        boolean actual = aPlayer.equals(bPlayer);
        assertFalse(actual);
    }

    @Test
    public void testToString() {
        String expected = "Test";

        Player player = new Player("Test");
        String actual = player.toString();

        assertEquals(expected, actual);
    }

}
