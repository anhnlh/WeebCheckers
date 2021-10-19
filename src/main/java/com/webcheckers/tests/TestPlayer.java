package com.webcheckers.tests;

import static org.junit.Assert.*;

import com.webcheckers.model.Player;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
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

        assertFalse("Result should be false.", actual);
    }

    @Test
    public void testSetPlaying() {
        Player player = new Player("Test");
        player.setPlaying(true);
        boolean actual = player.isPlaying();
        assertTrue("Result should be true.", actual);
    }

    @Test
    public void testEqualsTrue() {
        Player aPlayer = new Player("Player-A");
        Player aPlayer2 = new Player("Player-A");
        boolean actual = aPlayer.equals(aPlayer2);
        assertTrue("Players should be equal.", actual);
    }

    @Test
    public void testEqualsFalse() {
        Player aPlayer = new Player("Player-A");
        Player bPlayer = new Player("Player-B");
        boolean actual = aPlayer.equals(bPlayer);
        assertTrue("Players should not be equal.", actual);
    }

    @Test
    public void testToString() {
        String expected = "Test";

        Player player = new Player("Test");
        String actual = player.toString();

        assertEquals(expected, actual);
    }

}
