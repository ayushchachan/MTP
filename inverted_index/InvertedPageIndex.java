/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inverted_index_part_1;
import inverted_index_part_1.PageEntry;

import inverted_index_part_1.MySet;
import java.util.LinkedList;
import java.util.Set;
import myproject.MyHashTable;

/**
 *
 * @author Ayush Chachan
 */
public class InvertedPageIndex {
    
    private Set<PageEntry> pageSet;             // a collection of all web pages
    private MyHashTable wordEntryTable;          //hash table which maps word to its word entry
    
    /**Constructor*/
    public InvertedPageIndex() {
        pageSet = new MySet<>();
        wordEntryTable = new MyHashTable();
    }
    
    /**Adds a new page entry to the inverted page index*/
    public void addPage(PageEntry p) {
        pageSet.add(p);
        LinkedList<WordEntry> wordEntries = p.getPageIndex().getWordEntries();        //word entry list of page p
        for (WordEntry e : wordEntries) {
            wordEntryTable.addPositionsForWord(e);
        }
    }
    
    /**Returns a set of page entries which contains the word w*/
    public MySet<PageEntry> getPagesWhichContainWord(String w) {
        MySet<PageEntry> s = new MySet<>();             //an empty set s that will have pages which contain word w
        
        WordEntry we = wordEntryTable.getWordEntryForWord(w);          //word entry object associated with word w
        
        if (we == null) {return s;}                                 //if word entry is null then there is no page which contains the word w
                                                                    //and we return an empty set
        for (Position p : we.getAllPositions()) {
            s.add(p.getPageEntry());
        }
        return s;
    }
    
    
    /**return a set of page - entries for web pages which contain a sequence of non-connector
     * words (phrases[0] phrases[1] phrases[2] .......phrases[phrases.length - 1])   
     */
    public MySet<PageEntry> getPagesWhichContainPhrase(String[] phrase) {
        int i = 0;
        int end = phrase.length;
        
        MySet<PageEntry> current = this.getPagesWhichContainWord(phrase[i++]);
        MySet<PageEntry> answer = current;
        
        while ( (i != end) && (!answer.isEmpty()) ) {
            current = this.getPagesWhichContainWord(phrase[i++]);
            answer = answer.intersection(current);
        }
        
        for (PageEntry p : answer) {
            if (!p.containsPhrase(phrase)) {
                answer.remove(p);
            }
        }
        return answer;
    }
    
    
    /**Return the set of all pages(or PageEntries) stored in inverted index*/
    public Set<PageEntry> getPageSet() {
        return this.pageSet;
    }
    

}
