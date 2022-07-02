/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inverted_index_part_1;

import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Ayush Chachan
 */
public class PageEntry {
    
    private String name;                //the name of web page as stored in disk (eg. electromagnetic waves, black hole, surface_tension, friction, etc.)
    
    private PageIndex index;        //stores the index of current web page
    ArrayList<String> wordList;
    Scanner doc;
    
    HashMap<String, Integer> connectorsSet = new HashMap<>();
    
    /**create an index for page,
        page name is given as an argument to constructor*/
    public PageEntry(String pageName) {
        //add all connector words to connector set
        for (String s: CONNECTOR_WORDS) {
            connectorsSet.put(s, 1);
        }
        
        this.name = pageName;
        this.index = new PageIndex();
        this.wordList = new ArrayList<>();
        
        try {
            doc = new Scanner(new FileReader(pageName)).useDelimiter("[^a-zA-Z+]+");
            
            int i = 0;      //index for position of words
            
            while (doc.hasNext()) {
                String w = doc.next().toLowerCase();      //word at index i
                
                if (connectorsSet.get(w) == null) {
                    w = PageEntry.toSingular(w);                                //singular form of word w
                    index.addPositionForWord(w, new Position(this, i++));       //adding every word , which is not connector, to the word index
                    wordList.add(w);                                               
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
        
//        System.out.println("---------start of Debugging----------");
//        System.out.println("page index for " + name + ":- " + "\n" + index);
//        System.out.println("---------end of debugging------------");

//        System.out.print("page = " + this + " : ");
//        String[] ph1 = {"abstract", "data", "type"};
//        
//        System.out.println("this.containsPhrase([\"abstract\", \"data\", \"type\"]) = " + this.containsPhrase(ph1));
    }
    
    /**this method returns the page index of this web page*/
    public PageIndex getPageIndex() {
        return (PageIndex)this.index.clone();
    }
    
    /**return true if the page contains word w*/
    public boolean containsWord(String w) {
        WordEntry e = this.index.getWordEntryFor(w);
        return e != null;
    }
    
    /**returns the name of page entry object*/
    public String getPageName() {return this.name;}
    
    /**Returns true if the page contains the phrase*/
    public boolean containsPhrase(String[] phrase) {
        //System.out.println("containsPhrase() is called with argument " + Arrays.toString(phrase));
        int i = 0;
        
        String word = phrase[i];
        WordEntry e = this.index.getWordEntryFor(word);
        
        if (e == null) return false;
        
        for (Position p : e.getAllPositions()) {
            int j = p.getWordIndex();
            
            boolean containsPhrase = true;
            
            for (int k = 0; k < phrase.length; k++) {
                if (!wordList.get(j++).equals(phrase[k])) {
                    containsPhrase = false;
                    break;
                }
            }
            if (containsPhrase) {
                //System.out.println("------exiting containsPhrase()-----------");
                return true;
            }
        }        
        return false;
    }
    
    
    /**return the singular form of a word*/
    public static String toSingular(String w) {
        int n = w.length();     //length of word w
        
        if (n >= 2) {
            if ((w.charAt(n - 1) == 's') && (w.charAt(n - 2) != 's')) {
                return w.substring(0, n - 1);
            }
        }
        return w;
    }
    
    /**Return the i th word in the page, i starts from 0 */
    public String getWordAt(int i) {
        return wordList.get(i);
    }
    
    /**Return a word entry associated with word w, returns null if there is no word entry with w as word*/
    public WordEntry getWordEntryFor(String w) {
        return this.index.getWordEntryFor(w);
    }

    /**Returns the name of web page*/
    @Override
    public String toString() {return this.name; }
    
    /**returns true if other page has same name*/
    @Override
    public boolean equals(Object other) {
        PageEntry otherPage = (PageEntry)other;
        return this.getPageName().equals(otherPage.getPageName());
    }
    
    @Override
    public int hashCode() {
        return this.hashCode();
    }
    
    /*private constants*/
    private static final String[] CONNECTOR_WORDS = {"a", "an", "the", "they", "these", "this", "for", "is", "are",     //connector words which will 
                            "was", "as", "of", "or", "and", "does", "will", "whose"};                                   //be ignored by search engine
    //private static final String PUNCTUATION_MARKS = "(){}<>[]=.,;:!#?-'\"";       //punctuation which will be replaced by space
    
}
