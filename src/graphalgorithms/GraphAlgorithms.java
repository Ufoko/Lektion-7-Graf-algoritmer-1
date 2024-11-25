package graphalgorithms;

import java.util.*;
import java.util.stream.Collectors;

public class GraphAlgorithms {
    public static void main(String[] args) {
        Graph<Integer> g = new EdgeListGraph<>();

        g.addVertex(15);
        g.addVertex(38);
        g.addVertex(6);
        g.addVertex(123);
        g.addVertex(66);

        g.addEdge(15, 38, 10);
        g.addEdge(15, 6, 23);
        g.addEdge(15, 66, 90);
        g.addEdge(38, 123, 55);
        g.addEdge(38, 66, 2);
        g.addEdge(6, 123, 7);
        g.addEdge(6, 66, 8);
        g.addEdge(123, 66, 76);

        g.printGraph();
        System.out.println();

        System.out.println("DFS: Depth-First traversal starting in 123:");
        System.out.println(dfs(g, 123));
        System.out.println();

        System.out.println("DFS traversal using a stack starting in 123:");
        System.out.println(dfsStack(g, 123));
        System.out.println();

        System.out.println("BFS: Breth-First traversal starting in 123:");
        System.out.println(bfs(g, 123));
        System.out.println();

        System.out.println("Graph is connected?");
        System.out.println(isGraphConnected(g));
        System.out.println();

        System.out.println("Vertex 123 and 15 are connected:");
        System.out.println(hasGraphPath(g, 123, 15));
        System.out.println();

        System.out.println("Minimum spanning tree:");
        System.out.println(mst(g));
        System.out.println();

        System.out.println("Dijkstra starting in 123:");
        System.out.println(dijkstra(g, 123));
        System.out.println();
    }

    /**
     * Return true if the graph has a path between the specified vertices.
     * Throw exception if the vertices are not in the graph.
     */
    private static <V> boolean hasGraphPath(Graph<V> graph, V v1, V v2) {
        if (!graph.vertices().contains(v1) || !graph.vertices().contains(v2)) throw new NoSuchElementException();
        return dfs(graph, v1).contains(v2);
    }

    /**
     * Return true if the graph is connected, otherwise false.
     */
    public static <V> boolean isGraphConnected(Graph<V> graph) {
        int dfsSize = dfs(graph, graph.vertices().getFirst()).size();
        int graphSize = graph.vertices().size();

        return dfsSize == graphSize;
    }

    /**
     * Return a list with the vertices of the specified graph
     * found by a Depth-First traversal (DFS) of the graph starting at the specified vertex.
     * Throw exception if the vertex is not in the graph.
     */
    public static <V> List<V> dfs(Graph<V> graph, V v) {
        if (!graph.vertices().contains(v)) throw new NoSuchElementException();
        return dfs(graph, v, new ArrayList<>());
    }

    private static <V> List<V> dfs(Graph<V> graph, V v, List<V> visited) {
        visited.add(v);
        for (V neighbor : graph.neighbors(v)) {
            if (!visited.contains(neighbor)) {
                return dfs(graph, neighbor, visited);
            }
        }
        return visited;
    }


    /**
     * Return a list with the vertices of the specified graph
     * found by a Depth-First traversal (DFT) of the graph starting at the specified vertex.
     * Throw exception if the vertex is not in the graph.
     */
    public static <V> List<V> dfsStack(Graph<V> graph, V v) {
        if (!graph.vertices().contains(v)) throw new NoSuchElementException();
        List<V> finalList = new ArrayList<>();
        List<V> stackList = new ArrayList<>();

        stackList.addLast(v);

        while (!stackList.isEmpty()) {
            V curElement = stackList.getLast();
            stackList.removeLast();
            hihihuhu(graph, finalList, curElement, stackList);
        }

        return finalList;
    }

    /**
     * Return a list with the vertices of the specified graph
     * found by a Breath-First traversal (BFT) of the graph stating at the specified vertex.
     * Throw exception if the vertex is not in the graph.
     */
    public static <V> List<V> bfs(Graph<V> graph, V v) {
        if (!graph.vertices().contains(v)) throw new NoSuchElementException();
        List<V> finalList = new ArrayList<>();
        List<V> stackList = new ArrayList<>();

        stackList.addLast(v);

        while (!stackList.isEmpty()) {
            V curElement = stackList.getFirst();
            stackList.removeFirst();
            hihihuhu(graph, finalList, curElement, stackList);
        }

        return finalList;
    }

    private static <V> void hihihuhu(Graph<V> graph, List<V> finalList, V curElement, List<V> stackList) {
        if (!finalList.contains(curElement)) {
            finalList.add(curElement);

            for (V neighbor : graph.neighbors(curElement)) {
                if (!finalList.contains(neighbor)) {
                    stackList.addFirst(neighbor);
                }
            }
        }
    }

    /**
     * Return a minimum spanning tree (MST).
     */
    public static <V> List<Edge<V>> mst(Graph<V> graph) {


        List<LinkedHashSet<V>> CSets = new ArrayList<>();

        for (V vertex : graph.vertices()) {
            LinkedHashSet<V> set = new LinkedHashSet<>();
            set.add(vertex);
            CSets.add(set);
        }


        Queue<Edge<V>> queue = new PriorityQueue<Edge<V>>((Edge<V> e1, Edge<V> e2) -> e1.getWeight() - e2.getWeight());
        queue.addAll(graph.edges());

        List<Edge<V>> bestTree = new ArrayList<>();
        while (bestTree.size() < graph.vertices().size() - 1) {
            Edge<V> element = queue.poll();
            V vertexU = element.getU();
            V vertexV = element.getV();
            Set tas = new LinkedHashSet();
            Set sat = new LinkedHashSet();
            for (LinkedHashSet<V> cSet : CSets) {
                if (cSet.contains(vertexU)) {
                    tas = cSet;
                }
                if (cSet.contains(vertexV)) {
                    sat = cSet;
                }
            }
            if (!tas.equals(sat)) {
                bestTree.add(element);
                tas.addAll(sat);
                CSets.remove(sat);
            }


        }


        return bestTree;
    }

    /**
     * Return a map containing (vertex, weight) pairs,
     * where weight is the total weight of the shortest path
     * from the specified vertex v to the vertex in the pair.
     */
    public static <V> Map<V, Integer> dijkstra(Graph<V> graph, V v) {
        Map<V, Integer> D = new HashMap<>();
        for (V vertex : graph.vertices()) {
            D.put(vertex, Integer.MAX_VALUE);
        }
        D.put(v, 0);

        List<V> Q = new ArrayList<>();
        Q.addAll(graph.vertices());
        Q.sort((V a, V b) -> D.get(a) - D.get(b));

        while (!Q.isEmpty()) {
            V u = Q.remove(0);
            for (V z : graph.neighbors(u)) {
                if (Q.contains(z)) {
                    int weightOfEdge = 0;
                    for (Edge<V> incidentEdge : graph.incidentEdges(u)) {
                        if (incidentEdge.getU().equals(z)) weightOfEdge = incidentEdge.getWeight();
                        if (incidentEdge.getV().equals(z)) weightOfEdge = incidentEdge.getWeight();
                    }
                    if (D.get(u) + weightOfEdge < D.get(z)) {
                        D.put(z, D.get(u) + weightOfEdge);
                    }


                }
            }
            Q.sort((V a, V b) -> D.get(a) - D.get(b));
        }

        return D;
    }
}
