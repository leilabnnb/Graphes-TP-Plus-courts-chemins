package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

import static org.example.GraphGenerator.generate;
import static org.example.GraphGenerator.styleAndDisplay;

public class MyDijkstra {
    private Graph g;
    private Node s;

    private PriorityQueue<Node> q;

    private Set<Node> visitedNodes;

    /**
     * Creates an instance of MyDijkstra with the given graph and source
     * Sets the attribute "distance" between each node and the source node to infinity
     * Sets the attribute "distance" to zero for the source node (initial node)
     * @param graph
     * @param source
     */
    public MyDijkstra(Graph graph, Node source) {
        this.g = graph;
        this.s = source;
        this.q = new PriorityQueue<>(g.getNodeCount(), Comparator.comparingInt(n-> (Integer) n.getAttribute("distance")));
        this.visitedNodes = new HashSet<>(g.getNodeCount());

        for (Node node : g) {
            node.setAttribute("distance", Integer.MAX_VALUE);
            node.setAttribute("previous", null);
        }
        source.setAttribute("distance",0);
    }


    /**
     * Applies Dijkstra algorithm to this graph
     */
    public void dijkstra(){
        q.add(s);
        while (!q.isEmpty()) {
            Node u = q.poll();
            if(!visitedNodes.contains(u)) {

                for (Edge edge : u.leavingEdges().toList()) {
                    Node v = edge.getOpposite(u);
                    int edgeWeight = (Integer) edge.getAttribute("length");
                    int distanceThroughU = (Integer) u.getAttribute("distance") + edgeWeight;
                    if(distanceThroughU < (Integer) v.getAttribute("distance")){
                        v.setAttribute("distance", distanceThroughU);
                        v.setAttribute("previous", u);
                        q.add(v);
                    }
                }

            }
        }
    }

    public static void main(String[] args) {
        // Generate  graph
        Graph graph = generate(6, 2, 10);

        // Choose source node
        Node source = graph.getNode(0);

        // Create an instance of MyDijkstra
        MyDijkstra dijkstra = new MyDijkstra(graph, source);

        // Calculer les chemins les plus courts depuis le nœud source
        dijkstra.dijkstra();

        // Display  distances between source node and all other nodes
        for (Node node : graph) {
            System.out.println("The distance between " + source.getId() + " and " + node.getId() + " is " + node.getAttribute("distance"));
        }
        styleAndDisplay(graph,"red");
    }

}
