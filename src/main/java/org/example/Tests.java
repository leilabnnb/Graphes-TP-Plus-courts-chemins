package org.example;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.GraphGenerator.generate;


public class Tests {

    public static void main(String[] args){
        try{

            int [] order = {50, 100, 250, 500, 1000, 1500, 2000, 2500, 5000, 7500, 10000, 50000, 100000};
            int avgDegree = 100;
            int maxWeight = 10;

            String path1 = System.getProperty(("user.dir")) + File.separator + "dataFiles/myDijkstra" + avgDegree+".dat";
            String path2 = System.getProperty(("user.dir")) + File.separator + "dataFiles/theDijkstra" + avgDegree+".dat";
            FileWriter myDijkstraFile = new FileWriter(path1);
            FileWriter theDijkstraFile = new FileWriter(path2);


            for(int o : order) {

                // generate graph and choose source node
                Graph g1 = generate(o, avgDegree, maxWeight);
                Node s = g1.getNode(0);

                // Test MyDijkstra algo
                long start = System.nanoTime();
                MyDijkstra my_dijkstra = new MyDijkstra(g1, s);
                my_dijkstra.dijkstra();
                long end = System.nanoTime();

                /// duration in miliseconds
                double durationMyDijkstra = (end - start) / 1_000_000.0;
                myDijkstraFile.write(o + " " + durationMyDijkstra + "\n");
                // End test MyDijkstra algo


                // Test GS's algo

                Dijkstra theDijkstra = new Dijkstra(Dijkstra.Element.EDGE, "result", "length");
                theDijkstra.init(g1);
                theDijkstra.setSource(s);
                start = System.nanoTime();
                theDijkstra.compute();
                end = System.nanoTime();

                /// duration in miliseconds
                double durationTheDijkstra = (end - start) / 1_000_000.0;

                theDijkstraFile.write(o + " " + durationTheDijkstra + "\n");

                // End test GS's algo

            }


            myDijkstraFile.close();
            theDijkstraFile.close();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
