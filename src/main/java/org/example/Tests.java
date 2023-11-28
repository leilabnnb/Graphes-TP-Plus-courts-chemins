package org.example;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.FileWriter;
import java.io.IOException;

import static org.example.GraphGenerator.generate;


public class Tests {

    public static void main(String[] args){
        try{

            FileWriter myDijkstraFile = new FileWriter("myDijkstra.dat");
            FileWriter theDijkstraFile = new FileWriter("theDijkstra.dat");

            int order = 50;
            int avgDegree = 4;
            int maxWeight = 10;

            // generate graph and choose source node
            Graph g1 = generate(order, avgDegree, maxWeight);
            Node s = g1.getNode(19);

            // Test MyDijkstra algo
            long start = System.nanoTime();
            MyDijkstra my_dijkstra = new MyDijkstra(g1, s);
            my_dijkstra.dijkstra();
            long end = System.nanoTime();

            /// duration in miliseconds
            double durationMyDijkstra = (end - start )/ 1_000_000.0;

            myDijkstraFile.write(order + " " + durationMyDijkstra + "\n");

            // End test MyDijkstra algo


            // Test GS's algo

            Dijkstra theDijkstra = new Dijkstra(Dijkstra.Element.EDGE, "result", "length");
            theDijkstra.init(g1);
            theDijkstra.setSource(s);
            start = System.nanoTime();
            theDijkstra.compute();
            end = System.nanoTime();

            /// duration in miliseconds
            double durationTheDijkstra = (end - start )/ 1_000_000.0;

            theDijkstraFile.write(order + " " + durationTheDijkstra + "\n");

            // End test GS's algo








        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
