package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class MyDijkstra {
    private Graph g;
    private Node s;

    /**
     * Creates an instance of MyDijkstra with the given graph and source
     * Sets the attribute "distance" between each node(except the source) and the source node to infinity
     * Sets the attribute "shortestPath" with a PriorityQueue
     * Sets the attribute "distance" to zero for the source node (initial node)
     * @param graph
     * @param source
     */
    public MyDijkstra(Graph graph, Node source) {
        this.g = graph;
        this.s = source;
        graph.nodes().forEach(noeud -> noeud.setAttribute("distance", Integer.MAX_VALUE));
        graph.nodes().forEach(noeud -> noeud.setAttribute("shortestPath", new PriorityQueue<>()));
        source.setAttribute("distance",0);
    }
}
