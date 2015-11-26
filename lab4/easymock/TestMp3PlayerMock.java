/**
 * Excerpted from the book, "Pragmatic Unit Testing"
 * ISBN 0-9745140-1-2
 * Copyright 2003 The Pragmatic Programmers, LLC.  All Rights Reserved.
 * Visit www.PragmaticProgrammer.com
 */

import junit.framework.*;
import java.util.ArrayList;
import static org.easymock.EasyMock.*;

public class TestMp3PlayerMock extends TestCase {

    private ArrayList list = new ArrayList();
    private Mp3Player mp3;
    private Mp3Player mockMp3Player;

    public void setUp() {
        list = new ArrayList();
        list.add("Bill Chase -- Open Up Wide");
        list.add("Jethro Tull -- Locomotive Breath");
        list.add("The Boomtown Rats -- Monday");
        list.add("Carl Orff -- O Fortuna");
    }

    public void testPlay() {
        mockMp3Player = createMock(Mp3Player.class);
        mp3 = mockMp3Player;
        mockMp3Player.loadSongs(list);
        expect(mockMp3Player.isPlaying()).andReturn(false);
        mockMp3Player.play();
        expect(mockMp3Player.isPlaying()).andReturn(true);
        expect(mockMp3Player.currentPosition()).andReturn(1.0);
        mockMp3Player.pause();
        expect(mockMp3Player.currentPosition()).andReturn(1.0);
        mockMp3Player.stop();
        expect(mockMp3Player.currentPosition()).andReturn(0.0);
        replay(mp3);

        mp3.loadSongs(list);
        assertFalse(mp3.isPlaying());
        mp3.play();
        assertTrue(mp3.isPlaying());
        assertTrue(mp3.currentPosition() != 0.0);
        mp3.pause();
        assertTrue(mp3.currentPosition() != 0.0);
        mp3.stop();
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
    }

    public void testPlayNoList() {
        mockMp3Player = createMock(Mp3Player.class);
        mp3 = mockMp3Player;
        expect(mockMp3Player.isPlaying()).andReturn(false);
        mockMp3Player.play();
        expect(mockMp3Player.isPlaying()).andReturn(false);
        expect(mockMp3Player.currentPosition()).andReturn(0.0);
        mockMp3Player.pause();
        expect(mockMp3Player.currentPosition()).andReturn(0.0);
        expect(mockMp3Player.isPlaying()).andReturn(false);
        mockMp3Player.stop();
        expect(mockMp3Player.currentPosition()).andReturn(0.0);
        expect(mockMp3Player.isPlaying()).andReturn(false);
        replay(mp3);

        // Don't set the list up
        assertFalse(mp3.isPlaying());
        mp3.play();
        assertFalse(mp3.isPlaying());
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
        mp3.pause();
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
        assertFalse(mp3.isPlaying());
        mp3.stop();
        assertEquals(mp3.currentPosition(), 0.0, 0.1);
        assertFalse(mp3.isPlaying());
    }

    public void testAdvance() {
        mockMp3Player = createMock(Mp3Player.class);
        mp3 = mockMp3Player;
        mockMp3Player.loadSongs(list);
        mockMp3Player.play();
        expect(mockMp3Player.isPlaying()).andReturn(true);
        mockMp3Player.prev();
        expect(mockMp3Player.currentSong()).andReturn((String)list.get(0));
        expect(mockMp3Player.isPlaying()).andReturn(true);
        mockMp3Player.next();
        expect(mockMp3Player.currentSong()).andReturn((String)list.get(1));
        mockMp3Player.next();
        expect(mockMp3Player.currentSong()).andReturn((String)list.get(2));
        mockMp3Player.prev();
        expect(mockMp3Player.currentSong()).andReturn((String)list.get(1));
        mockMp3Player.next();
        expect(mockMp3Player.currentSong()).andReturn((String)list.get(2));
        mockMp3Player.next();
        expect(mockMp3Player.currentSong()).andReturn((String)list.get(3));
        mockMp3Player.next();
        expect(mockMp3Player.currentSong()).andReturn((String)list.get(3));
        expect(mockMp3Player.isPlaying()).andReturn(true);
        replay(mp3);

        mp3.loadSongs(list);

        mp3.play();

        assertTrue(mp3.isPlaying());

        mp3.prev();
        assertEquals(mp3.currentSong(), list.get(0));
        assertTrue(mp3.isPlaying());

        mp3.next();
        assertEquals(mp3.currentSong(), list.get(1));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(2));
        mp3.prev();

        assertEquals(mp3.currentSong(), list.get(1));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(2));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(3));
        mp3.next();
        assertEquals(mp3.currentSong(), list.get(3));
        assertTrue(mp3.isPlaying());
    }
}
