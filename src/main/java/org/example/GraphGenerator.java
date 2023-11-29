package org.example;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import static org.graphstream.algorithm.Toolkit.density;

public class  GraphGenerator{

    
    /**
     * Generate a graph using the RandomGenerator with the given spécifications
     * @param order The number of nodes in the generated graph
     * @param maxWeight The maximal weight of edges
     * @return A graph with the given characteristics
     */
    public static Graph generate (int order, int avgDegree, int maxWeight, boolean directed){

        Graph graph = new SingleGraph("Random");
        RandomGenerator gen = new RandomGenerator(avgDegree, true, directed);
        gen.addSink(graph);
        gen.begin();
        while (graph.getNodeCount() < order && gen.nextEvents());
        graph.edges().forEach(
                e -> {  int weight = (int) (Math.random()* maxWeight);
                        e.setAttribute("length", weight);
                        e.setAttribute("label", "" + (int) e.getNumber("length"));
                }
                );
        graph.nodes().forEach(n-> n.setAttribute("label",""+n.getId()));
        gen.end();
        return graph;
    }

    public static Graph generateDensity (int order, double density, int maxWeight, boolean directed){
        Graph graph = new SingleGraph("Random");
        int maxEdges = directed ? order*(order-1) : order*(order-1)/2;
        System.out.println("Gm1");

        int avgDegree = (int) (2.0 * density * maxEdges / order);
        RandomGenerator gen = new RandomGenerator(avgDegree, true,directed);
        gen.addSink(graph);
        gen.begin();
        System.out.println("Gm2");

        while (graph.getNodeCount() < order && gen.nextEvents());
        System.out.println("Gm0");

        graph.edges().forEach(
                e -> {  int weight = (int) (Math.random()* maxWeight);
                    e.setAttribute("length", weight);
                    e.setAttribute("label", "" + (int) e.getNumber("length"));
                }
        );
        System.out.println("Gm3");

        graph.nodes().forEach(n-> n.setAttribute("label",""+n.getId()));
        System.out.println("Gm6");

        gen.end();
        System.out.println("Gm5");

        return graph;
    }

    /**
     * Style and display the given graph with the given color
     * @param g the graph you want to display
     * @param nodesColor the color you want the nodes to be colored in
     */
    public static void styleAndDisplay(Graph g, String nodesColor){
        g.nodes().forEach(n -> {
            String style = "fill-color: "+nodesColor+";";
            style += "text-size:15px;";
            style += "text-alignment:above;";
            style += "text-color:red;";
            n.setAttribute("ui.style", style);
        });
        System.setProperty("org.graphstream.ui", "swing");
        g.display();
    }


    public static void main(String[] args){

        //Graph firstEx = generate(10, 80, 20, false);
        Graph secondEx = generateDensity(100, 1, 100, true);
        System.out.println(density(secondEx));
        //Graph thirdEx = generate(50, 10, 10000,false);
        //styleAndDisplay(firstEx, "gray");
        styleAndDisplay(secondEx, "black");
        //styleAndDisplay(thirdEx, "cyan");
    }
}