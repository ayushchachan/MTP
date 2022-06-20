package myproject;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Comparator;

/**
 *
 * @author Chankit Chachan
 */
public class DefaultComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return ((Comparable<T>)o1).compareTo(o2);
    }
    
}
