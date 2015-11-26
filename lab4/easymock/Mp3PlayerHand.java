/**
 * Excerpted from the book, "Pragmatic Unit Testing"
 * ISBN 0-9745140-1-2
 * Copyright 2003 The Pragmatic Programmers, LLC.  All Rights Reserved.
 * Visit www.PragmaticProgrammer.com
 */

import java.util.ArrayList;

public class Mp3PlayerHand implements Mp3Player {

    private ArrayList list;
    private int listPosition;
    private double songPosition;
    private boolean playing;

    public Mp3PlayerHand() {
        list = null;
        listPosition = 0;
        songPosition = 0.0;
        playing = false;
    }

    public void loadSongs(ArrayList names) {
        list = names;
        listPosition = 0;
    }

    public void play() {
        playing = list != null;
        songPosition = playing ? 2.0 : 0.0;
    }

    public boolean isPlaying() {
        return playing;
    }

    public double currentPosition() {
        return songPosition;
    }

    public void pause() {
        playing = false;
    }

    public void stop() {
        pause();
        songPosition = 0.0;
    }

    public String currentSong() {
        return (String) list.get(listPosition);
    }


    public void next() {
        listPosition = (list != null) ? Math.min(listPosition + 1, list.size() - 1) : 0;
        play();
    }

    public void prev() {
        listPosition = (list != null) ? Math.max(0, listPosition - 1) : 0;
        play();
    }
}
