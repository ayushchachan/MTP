/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inverted_index_part_1;


/**
 * the class represents a tuple <page p, position i> for a word
 */
public class Position implements Comparable<Position>{
    PageEntry page;     //reference to page object
    int wordIndex;              //index of word in page
    
    
    /**Constructor method*/
    public Position(PageEntry p, int i) {
        page = p;
        wordIndex = i;
    }
    
    /**returns page object*/
    public PageEntry getPageEntry() {
        return page;
    }
    
    /**returns wordIndex*/
    public int getWordIndex() {
        return wordIndex; 
    }
    
    /**Returns a string representation of position*/
    @Override
    public String toString() {
        return "<" + page + ", " + wordIndex + ">";
    }
    
    /**compare this position object to other position object*/
    @Override
    public int compareTo(Position other) {
        if (this.wordIndex == other.wordIndex) {
            return 0;
        } else if (this.wordIndex > other.wordIndex) {
            return 1;
        } else return -1;
    }
}
