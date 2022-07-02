/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inverted_index_part_1;

import inverted_index_part_1.MySet;
import inverted_index_part_1.PageEntry;

/**
 *
 * @author Ayush Chachan
 */
public class SearchEngine {
    
    InvertedPageIndex ipi;      //an inverted page index of search engine
    
    /**Constructor*/
    public SearchEngine() {
        ipi = new InvertedPageIndex();
    }
    
    /**main stub method that will perform every task*/
    public void performAction(String actionMessage) {
        String[] command = actionMessage.split(" ");
        command[1] = command[1].toLowerCase();
       
        switch (command[0]) {
            case ("addPage"):
                ipi.addPage(new PageEntry(command[1]));        //new page entry is added to inverted page index of search engine
                break;
            
            case ("queryFindPagesWhichContainWord"):
                String word = PageEntry.toSingular(command[1]);                      //String(or word) for which we will find all web pages which contains it
                
                MySet<PageEntry> pageSet = ipi.getPagesWhichContainWord(word);        //set of pages which contain the word given in action message
                
                if (pageSet.isEmpty()) {
                    System.out.print("No webpage contains word " + word);
                } else {
                    System.out.print(pageSet);
                }
                break;
                
            case ("queryFindPositionsOfWordInAPage"):
                String x = command[1].toLowerCase();               //string(word) whose indices we need to find
                String y = command[2];               //page name in which we will find indices
                
                for (PageEntry p : ipi.getPageSet()) {          //for every page p in inverted page index
                    if (p.toString().equals(y)) {             //if p is page entry with name 'page'
                        WordEntry w = p.getWordEntryFor(x);  //word entry for string 'word' stored in page entry 'page'
                        
                        if (w == null) {                                                            //if there is no word entry for x
                            System.out.print("Webpage " + "'" + y + " '" + 
                                    " does not contain word " + "'" + x + " '");                    //that is web page y do not contain x
                        } else {
                            for (Position pos : w.getAllPositions()) {                              //else
                                System.out.print(pos.getWordIndex() + ", ");                        //it will print all the indices of x in web page y
                            }
                        }
                        System.out.println();
                        return;
                    }
                }
                System.out.print("No webpage " + y + " found");        //there is no page entry with name page
                break;
                
            default:
                break;
        }
        System.out.println();
    }
}
