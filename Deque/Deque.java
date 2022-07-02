
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author Ayush Chachan
 */
public class Deque<Item> implements Iterable<Item> {

    //------Nested Node class ------------
    private static class Node<Item> {

        private Item elem;
        private Node<Item> next;
        private Node<Item> prev;

        public Node(Item val, Node<Item> prevNode, Node<Item> nextNode) {
            elem = val;
            next = nextNode;
            prev = prevNode;
        }

        public Node(Item val) {
            this(val, null, null);
        }

        public Item getElement() {
            return elem;
        }

        public Node<Item> getPrev() {
            return prev;
        }

        public Node<Item> getNext() {
            return next;
        }

        public void setElement(Item newE) {
            this.elem = newE;
        }

        public void setPrev(Node<Item> p) {
            this.prev = p;
        }

        public void setNext(Node<Item> n) {
            this.next = n;
        }
    }
    //------end of nested Node class---------

    private final Node<Item> header;
    private final Node<Item> trailer;
    private int size;

    // construct an empty deque
    public Deque() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;

    }

    // private utilities
    /**
     * Adds an element e between given nodes
     */
    private Node<Item> addBetween(Item elem, Node<Item> pred, Node<Item> succ) {
        if (elem == null) {
            throw new IllegalArgumentException("Invalid Arguments: null passed");
        }
        Node<Item> newest = new Node<>(elem, pred, succ);
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
        return newest;
    }

    // add the item to the front
    public void addFirst(Item item) {
        addBetween(item, header, header.getNext());
    }

    // add the item to the back
    public void addLast(Item item) {
        addBetween(item, trailer.getPrev(), trailer);
    }

    private Item remove(Node<Item> n) {
        if (isEmpty()) {
            throw new NoSuchElementException("sequence is empty.");
        }

        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());

        n.setNext(null);
        n.setPrev(null);

        size--;
        Item elem = n.getElement();
        n.setElement(null);
        return elem;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        return this.remove(header.getNext());
    }

    // remove and return the item from the back
    public Item removeLast() {
        return this.remove(trailer.getPrev());
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ElementIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> d = new Deque<>();
        d.addFirst(2);
        d.addLast(4);
        d.addFirst(5);

        for (int x : d) {
            System.out.println("x = " + x);
        }

    }

    private class ElementIterator implements Iterator<Item> {

        Node<Item> current;

        public ElementIterator() {
            current = header.getNext();
        }

        @Override
        public boolean hasNext() {
            return current != trailer;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements! Have reached the end");
            }
            Item answer = current.getElement();
            current = current.getNext();
            return answer;
        }

    }

}
