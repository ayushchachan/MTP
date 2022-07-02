/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inverted_index_part_1;

import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author Ayush Chachan
 */
public class WordEntry {

    private final String word;                  //the word of wordEntry object
    private LinkedList<Position> positionList;

    /**
     * Constructor method. The argument is the word for which we are creating
     * the word entry
     */
    public WordEntry(String s) {
        word = s;
        positionList = new LinkedList<>();
    }

    /**
     * Adds a position p to the word entry
     */
    public void addPosition(Position p) {
        positionList.add(p);
    }

    /**
     * add multiple positions in wordEntry
     */
    public void addPositions(LinkedList<Position> L1) {
        for (Position p : L1) {
            positionList.add(p);
        }
    }

    /**
     * Returns a linked list of all positions entries for str
     */
    public LinkedList<Position> getAllPositions() {
        return positionList;
    }

    /**
     * Returns the word which is associated with this wordEntry object
     */
    public String getWord() {
        return word;
    }

    /**
     * Tells whether two word entries are equal or not
     */
    public boolean equals(WordEntry other) {
        return word.equals(other.getWord());
    }

    /**
     * Returns a string representation of word entry
     */
    public String toString() {
        String s = "(" + word + " : ";

        Iterator<Position> iter = positionList.iterator();
        while (iter.hasNext()) {
            s = s + iter.next();
            if (iter.hasNext()) {
                s = s + ", ";
            }
        }
        return s + ")";
    }
}
