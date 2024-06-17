package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lab3_1 {

    public static void main(String[] args) {
        var graph = read();

        graph.print();

        var colors1 = graph.paintGreedy();
        var colors2 = graph.paintUsingMatrix();

        printColors(colors1, graph.getV());
        printColors(colors2, graph.getV());
    }

    static void printColors(Map<Integer, Integer> colors, List<List<Integer>> vertices) {
        System.out.println();
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(i + "(" + colors.get(i) + ") ->");
            for (var neighbour : vertices.get(i)) {
                System.out.print(" " + neighbour + "(" + colors.get(neighbour) + ")");
            }
            System.out.println();
        }
    }

    static protected Graph read() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("1.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        var list = new ArrayList<Edge>();
        while (scanner.hasNextLine()) {
            var edge = new ArrayList<Integer>();
            var line = scanner.nextLine().split(" ");
            for (String s : line) {
                Integer parseInt = Integer.parseInt(s);
                edge.add(parseInt);
            }
            list.add(new Edge(edge.get(0) - 1, edge.get(1) - 1));
        }
        return Graph.fromEdgeList(list, false);
    }
}
