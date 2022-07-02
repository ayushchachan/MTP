/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inverted_index_part_1;
import java.util.HashSet;

/**
 *
 * @author Ayush Chachan
 */
public class MySet<E> extends HashSet<E> {
    
    public MySet<E> intersection(MySet<E> otherSet) {
        
        MySet<E> newSet = new MySet<>();
        
        MySet<E> smallSet, largeSet;
        
        if (otherSet.size() > this.size()) {
            smallSet = this;
            largeSet = otherSet;
        } else {
            smallSet = otherSet;
            largeSet = this;
        }
        
        for (E elem : smallSet) {
            if (largeSet.contains(elem)) {
                newSet.add(elem);
            }
        }
        return newSet;
    }
    
    public MySet<E> union(MySet<E> otherSet) {
        MySet<E> newSet = new MySet<>();
        
        for (E elem : this) {
            newSet.add(elem);
        }
        
        for (E elem : otherSet) {
            newSet.add(elem);
        }
        
        return newSet;
    }
        
    
}
