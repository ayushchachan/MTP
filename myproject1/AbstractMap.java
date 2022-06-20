/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author Chankit Chachan
 */
public abstract class AbstractMap<K, V> implements Map<K, V>{
    public boolean isEmpty() {return size() == 0;}
    
    //----------------nested MapEntry class-------------
    protected static class MapEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;
        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {return this.key;}
        public V getValue() {return this.value;}
        
        protected void setKey(K newKey) {this.key = newKey;}
        public V setValue(V newVal) {
            V old = this.value;
            this.value = newVal;
            return old;
        }
    }
    //----------------end of nested MapEntry class-----------------
    
    // keySet() and values() implementation
    
    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K, V>> entryIter;
        
        public KeyIterator() {entryIter = entrySet().iterator();}
        
        public boolean hasNext() {return entryIter.hasNext();}
        public K next() {return entryIter.next().getKey();}
    }
    
    private class KeyIterable implements Iterable<K> {
        public Iterator<K> iterator() {return new KeyIterator();}
    }
    
    public Iterable<K> keySet() {return new KeyIterable();}
    
    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K, V>> entryIter;
        
        public ValueIterator() {entryIter = entrySet().iterator();}
        
        public boolean hasNext() {return entryIter.hasNext();}
        public V next() {return entryIter.next().getValue();}
    }
    
    private class ValueIterable implements Iterable<V> {
        public Iterator<V> iterator() {return new ValueIterator();}
    }
    
    public Iterable<V> values() {return new ValueIterable();}
    
}
