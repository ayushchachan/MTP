/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Ayush Chachan
 */
public class MyGraph<V, E> {
    AdjacencyMapGraph<V, E> G;
    
    public MyGraph(AdjacencyMapGraph<V, E> graph) {
        this.G = graph;
    }
    
    /**
     * Return the shortest path tree rooted at s for an undirected graph
     * @param s = the source vertex
     * @param d = d[u] is shortest distance of each vertex u reachable from s, d[u] = -1
     *            if not reachable
     */
    public HashMap<Vertex<V>, Vertex<V>> bfs(Vertex<V> s, HashMap<Vertex<V>, Integer> d) {
        d.clear();
        
        LinkedList<Vertex<V>> Q = new LinkedList<>();
        
        HashMap<Vertex<V>, Vertex<V>> parent = new HashMap<>();
        
        Q.add(s);
        d.put(s, 0);
        parent.put(s, null);
        
        while (!Q.isEmpty()) {
            Vertex<V> c = Q.removeFirst();
            
            for (Edge<E> e : G.outgoingEdges(c)) {
                Vertex<V> v = G.opposite(c, e);
                
                if (!parent.containsKey(v)) {
                    parent.put(v, c);
                    d.put(v, d.get(c) + 1);
                    Q.add(v);
                }
            }
        }
        return parent;
        
    }
    
    
}
