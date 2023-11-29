package org.example;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.GraphGenerator.generate;
import static org.example.GraphGenerator.generateDensity;


public class Tests {

    public static void main(String[] args){
        try{

            // 0 < density <= 1
            double density = 0.1;
            int avgDegree = 600;
            int maxWeight = 10;

            int numTests = 5;



            // Small graphs
            //int [] order = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

            // Medium sized graphs
            //int [] order = {200, 400, 600, 800, 1000};

            // Large graphs
            int [] order = {1000, 2000, 3000, 4000,5000,6000,7000, 8000, 9000, 10000};
            //int [] order = {20000, 40000, 60000, 80000, 100000};

            String suffixToFileName1 = "_S_density"+ density;
            String suffixToFileName2 = "_S_degree"+ avgDegree;

            String path1 = System.getProperty(("user.dir")) + File.separator + "dataFiles/myDijkstra" + suffixToFileName1+".dat";
            String path2 = System.getProperty(("user.dir")) + File.separator + "dataFiles/theDijkstra" + suffixToFileName1+".dat";
            FileWriter myDijkstraFile = new FileWriter(path1);
            FileWriter theDijkstraFile = new FileWriter(path2);

            System.out.println("m1");

            for(int o : order) {
                // generate graph and choose source node
                //Graph g1 = generateDensity(o, density, maxWeight, false);
                Graph g1 = generate(o, avgDegree, maxWeight, true);
                Node s = g1.getNode(0);
                System.out.println("m2");

                //for (int i = 0; i < numTests; i++) {

                    System.out.println("m3");

                    // Test MyDijkstra algo
                    long start = System.currentTimeMillis();
                    MyDijkstra my_dijkstra = new MyDijkstra(g1, s);
                    my_dijkstra.dijkstra();
                    long end = System.currentTimeMillis();

                    /// duration in miliseconds
                    double durationMyDijkstra = (end - start);
                    myDijkstraFile.write(o + " " + durationMyDijkstra + "\n");
                    // End test MyDijkstra algo

                    System.out.println("m4");

                    // Test GS's algo

                    Dijkstra theDijkstra = new Dijkstra(Dijkstra.Element.EDGE, "result", "length");
                    theDijkstra.init(g1);
                    theDijkstra.setSource(s);
                    System.out.println("m5");

                    start = System.currentTimeMillis();
                    theDijkstra.compute();
                    end = System.currentTimeMillis();

                    /// duration in miliseconds
                    double durationTheDijkstra = (end - start);
                    System.out.println("m6");

                    theDijkstraFile.write(o + " " + durationTheDijkstra + "\n");

                    // End test GS's algo
                }
            //}
            System.out.println("m7");

            myDijkstraFile.close();
            theDijkstraFile.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
