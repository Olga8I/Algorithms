package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab3_2 {

    public static void main(String[] args) {
        Graph graph = read();

        List<Pair<Integer, Integer>> dfsTracing = graph.dfsWithPath();
        List<Pair<Integer, Integer>> bfsTracing = graph.bfsWithPath();
        for (Pair<Integer, Integer> traceElement : dfsTracing) {
            System.out.println((traceElement.left() + 1) + " -> " + (traceElement.right() + 1));
        }

        System.out.println();
        for (Pair<Integer, Integer> traceElement : bfsTracing) {
            System.out.println((traceElement.left() + 1) + " -> " + (traceElement.right() + 1));
        }
    }

    protected static Graph read() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("2.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Edge> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            List<Integer> edge = new ArrayList<>();
            String[] line = scanner.nextLine().split(" ");
            for (String s : line) {
                Integer parseInt = Integer.parseInt(s);
                edge.add(parseInt);
            }
            list.add(new Edge(edge.get(0) - 1, edge.get(1) - 1));
        }
        return Graph.fromEdgeList(list, true);
    }
}
