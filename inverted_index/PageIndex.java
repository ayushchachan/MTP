  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inverted_index_part_1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


/**A class which stores one word entry of each unique word in the document*/
public class PageIndex implements Cloneable {
    protected Map<String, WordEntry> wordEntryList;        //a list which stores word entries
    
    
    /**Constructor*/
    public PageIndex() {
        wordEntryList = new HashMap<>();  
    }
    
    /**Add position p to the word entry for str.
     * If a word entry for str is already present in the page index, then add p to the existing word entry.
     * otherwise, it creates a new word entry for str and add p to it.
     */
    public void addPositionForWord(String str, Position p) {
        WordEntry e = wordEntryList.get(str);
        
        if (e != null) {
            e.addPosition(p);
        } else {
            e = new WordEntry(str);
            e.addPosition(p);
            wordEntryList.put(str, e);
        }
    }
    
    
    /**returns a list of all word entries stored in the page index*/
    public LinkedList<WordEntry> getWordEntries() {
        LinkedList<WordEntry> entries = new LinkedList<>();
        
        for (WordEntry e : wordEntryList.values()) {
            entries.add(e);
        }
        return entries;
    }
    
    /**It adds an entry to the hash table
     * if no word entry exist then it creates a new word entry
     * if there is existing word entry then it merge w with it.
     */
    protected void addPositionsForWord(WordEntry w) {
        String word = w.getWord();
        WordEntry oldEntry = wordEntryList.get(word);     //existing entry associated with word 
        
        if (oldEntry == null) {
            wordEntryList.put(word, w);
        } else {
            oldEntry.addPositions(w.getAllPositions());
        }
    }
    
    
    /**return the word entry associated with word w*/
    public WordEntry getWordEntryFor(String w) {
        return wordEntryList.get(w);
    }
    
    /**Returns a string representation of page index*/
    @Override
    public String toString() {
        String s = "{";
        
        Iterator<WordEntry> iter = wordEntryList.values().iterator();
        
        while (iter.hasNext()) {
            s = s + iter.next();
            if (iter.hasNext()) {
                s = s + ",\n";
            }
        }
        return s + "}";
    }
    
    /**returns a copy of page Index object*/
    @Override
    public Object clone() {
        PageIndex copy = new PageIndex();
        
        for (WordEntry we : this.getWordEntries()) {
            copy.addPositionsForWord(we);
        }
        return copy;
    }
    
}
