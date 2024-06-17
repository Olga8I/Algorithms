package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lab3_3 {

    public static void main(String[] args) {
        Pair<Pair<Graph, Graph>, List<EdgeMeta>> input = read();

        Graph inputGraph = input.left().left();
        Graph builtGraph = input.left().right();
        List<EdgeMeta> edgeMeta = input.right();

        Map<Integer, Integer> colors = builtGraph.paintGreedy();

        for (int i = 0; i < builtGraph.getV().size(); i++) {
            EdgeMeta meta = findEdgeMeta(edgeMeta, i);

            System.out.print("f" + meta.v1() + "t" + meta.v2() + "C" + colors.get(i) + " ->");
            for (Integer neighbour : builtGraph.getV().get(i)) {
                EdgeMeta nMeta = findEdgeMeta(edgeMeta, neighbour);
                System.out.print(" " + "f" + nMeta.v1() + "t" + nMeta.v2() + "C" + colors.get(neighbour));
            }
            System.out.println();
        }
    }

    protected static Pair<Pair<Graph, Graph>, List<EdgeMeta>> read() {

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("3.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Edge> list1 = new ArrayList<>();
        Set<Edge> list2 = new HashSet<>();
        List<EdgeMeta> edgeMetaList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            List<Integer> edgeList = new ArrayList<>();
            String[] line = scanner.nextLine().split(" ");
            for (String s : line) {
                Integer integer = Integer.valueOf(s);
                edgeList.add(integer);
            }
            list1.add(new Edge(edgeList.get(0), edgeList.get(1)));
        }

        for (int i = 0; i < list1.size(); i++) {
            int from = list1.get(i).getFrom();
            int to = list1.get(i).getTo();
            EdgeMeta edgeMeta = new EdgeMeta(from, to, i);
            for (int j = 0; j < list1.size(); j++) {
                if (j == i) continue;

                if (list1.get(j).getFrom() == from || list1.get(j).getFrom() == to ||
                        list1.get(j).getTo() == from || list1.get(j).getTo() == to) {
                    list2.add(new Edge(Math.min(i, j), Math.max(i, j)));
                }
            }
            edgeMetaList.add(edgeMeta);

        }

        return new Pair<>(
                new Pair<>(Graph.fromEdgeList(list1, true), Graph.fromEdgeList(new ArrayList<>(list2), false)),
                edgeMetaList);
    }

    private static EdgeMeta findEdgeMeta(List<EdgeMeta> edgeMeta, Integer neighbour) {
        EdgeMeta nMeta = null;
        for (EdgeMeta em : edgeMeta) {
            if (em.ind() == neighbour) {
                nMeta = em;
                break;
            }
        }
        return nMeta;
    }
}

