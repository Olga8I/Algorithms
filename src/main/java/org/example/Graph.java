package org.example;

import java.util.*;

public class Graph {

    private List<List<Integer>> v;

    public Graph() {
        v = new ArrayList<>();
    }

    public static Graph fromEdgeList(List<Edge> edges, boolean oriented) {
        Graph graph = new Graph();
        Edge maxEdge = edges.get(0);
        for (Edge edge : edges) {
            if (Math.max(maxEdge.getFrom(), maxEdge.getTo()) < Math.max(edge.getFrom(), edge.getTo())) {
                maxEdge = edge;
            }
        }


        int max = Math.max(maxEdge.getFrom(), maxEdge.getTo());

        ArrayList<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i <= max; i++) {
            list.add(new ArrayList<>());
        }

        for (Edge edge : edges) {
            list.get(edge.getFrom()).add(edge.getTo());
            if (!oriented) {
                list.get(edge.getTo()).add(edge.getFrom());
            }
        }

        graph.v = list;
        return graph;
    }

    public List<List<Integer>> getV() {
        return v;
    }

    public int[][] getAdjacencyMatrix() {
        int[][] adjMatrix = new int[v.size()][];
        for (int i = 0; i < adjMatrix.length; i++) {
            adjMatrix[i] = new int[v.size()];
        }

        for (int i = 0; i < v.size(); i++) {
            List<Integer> neighbours = v.get(i);
            for (Integer neighbour : neighbours) {
                adjMatrix[i][neighbour] = 1;
            }
            adjMatrix[i][i] = 1;
        }

        return adjMatrix;
    }

    public void print() {

        // adjacency list
        System.out.println("Adjacency list:");
        for (int i = 0; i < v.size(); i++) {
            System.out.println(i + ": " + v.get(i));
        }

        // adjacency matrix
        System.out.println("Adjacency matrix:");
        int[][] adjMatrix = getAdjacencyMatrix();

        for (int[] row : adjMatrix) {
            System.out.println(Arrays.toString(row));
        }

        // incidence matrix
        System.out.println("Incidence matrix:");
        int[][] incMatrix = new int[v.size()][];
        for (int i = 0; i < incMatrix.length; i++) {
            incMatrix[i] = new int[v.size() * v.size()];
        }

        int k = -1;
        for (int i = 0; i < v.size(); i++) {
            int t = -1;
            for (int j = i; j < v.size(); j++) {
                if (adjMatrix[i][j] == 1) {
                    t = j;
                    k++;
                    for (int l = 0; l < v.size(); l++) {
                        if (l == t) {
                            incMatrix[l][k] = 1;
                        } else {
                            incMatrix[l][k] = 0;
                        }
                        if (l == i) {
                            incMatrix[l][k] = 1;
                        }
                    }
                }
            }
        }

        for (int[] row : incMatrix) {
            System.out.println(Arrays.toString(row));
        }

    }

    public Map<Integer, Integer> paintGreedy() {

        /*
        (1, 2)
        (2, 3)
        (1, 3)

        [
        [1, 2],
        [2]
        ]
         */

        ArrayList<Pair<Integer, List<Integer>>> sorted = new ArrayList<>();
        int bound = v.size();
        for (int i1 = 0; i1 < bound; i1++) {
            Pair<Integer, List<Integer>> integerListPair = new Pair<>(i1, v.get(i1));
            sorted.add(integerListPair);
        }
        sorted.sort(Comparator.comparingInt(pair -> pair.right().size()));
        Collections.reverse(sorted);

        HashMap<Integer, List<Integer>> prohibitedColors = new HashMap<>();
        HashMap<Integer, Integer> output = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            prohibitedColors.put(i, new ArrayList<>());
        }

        for (Pair<Integer, List<Integer>> vertexNeighbour : sorted) {
            int color = 1;
            Integer curr = vertexNeighbour.left();
            while (prohibitedColors.get(curr).contains(color)) {
                color++;
            }
            output.put(curr, color);

            List<Integer> neighbours = vertexNeighbour.right();
            for (Integer neighbour : neighbours) {
                prohibitedColors.get(neighbour).add(color);
            }
        }

        return output;
    }

    public Map<Integer, Integer> paintUsingMatrix() {
        int[][] adj = getAdjacencyMatrix();
        HashMap<Integer, Integer> colors = new HashMap<>();
        int currColor = 1;
        for (int i = 0; i < adj.length; i++) {
            if (colors.containsKey(i)) continue;

            colors.put(i, currColor);

            int ind = -1;
            while ((ind = indexOfZero(adj, i, colors)) != -1) {
                sumRows(adj[i], adj[ind]);
                colors.put(ind, currColor);
            }

            currColor++;
        }

        return colors;
    }

    public List<Pair<Integer, Integer>> dfsWithPath() {
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        dfsWithPath(0, result, new boolean[v.size()]);
        return result;
    }

    public List<Pair<Integer, Integer>> bfsWithPath() {
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        bfsWithPath(result, new int[v.size()]);
        return result;
    }

    private static int indexOfZero(int[][] adj, int i, HashMap<Integer, Integer> colors) {
        int ind = -1;
        for (int j = 0; j < adj[i].length; j++) {
            if (adj[i][j] == 0 && !colors.containsKey(j)) {
                ind = j;
                break;
            }
        }
        return ind;
    }

    private void sumRows(int[] row1, int[] row2) {
        for (int i = 0; i < row1.length; i++) {
            row1[i] = (row1[i] == 1 || row2[i] == 1) ? 1 : 0;
        }
    }

    private void dfsWithPath(int curr, List<Pair<Integer, Integer>> tracing, boolean[] visited) {
        if (visited[curr]) return;
        visited[curr] = true;
        List<Integer> neighbours = v.get(curr);
        for (Integer neighbour : neighbours) {
            if (!visited[neighbour]) {
                tracing.add(new Pair<>(curr, neighbour));
                dfsWithPath(neighbour, tracing, visited);
            }
        }
    }

    private void bfsWithPath(List<Pair<Integer, Integer>> tracing, int[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            Integer v = queue.poll();
            if (visited[v] == 2) continue;

            visited[v] = 2;

            List<Integer> neighbours = this.v.get(v);
            for (Integer neighbour : neighbours) {
                if (visited[neighbour] == 0) {
                    tracing.add(new Pair<>(v, neighbour));
                    queue.add(neighbour);
                    visited[neighbour] = 1;
                }
            }
        }
    }
}
