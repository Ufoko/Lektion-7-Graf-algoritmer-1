package adjacencymatrixgraph;

import java.util.*;

/**
 * Adjacency matrix implementation of the graph interface.
 */
public class AdjacencyMatrixGraph<V> implements Graph<V> {
    // List with all the vertices in the graph.
    private Map<V, Integer> vertices;
    // 2-dim array with all the edges in the graph.
    private Edge<V>[][] matrix;
    private final int matrixCapacity;
    private int matrixSize; // equal to vertices.size()

    //-----------------------------------------------------

    /** Construct an empty AdjacencyMatrixGraph. */
    public AdjacencyMatrixGraph(int matrixCapacity) {
        vertices = new LinkedHashMap<>();
        @SuppressWarnings("unchecked")
        Edge<V>[][] emptyMatrix = new Edge[matrixCapacity][matrixCapacity];
        matrix = emptyMatrix;
        this.matrixCapacity = matrixCapacity;
        matrixSize = 0;
    }

    /** Return a list with the vertices in the graph. */
    @Override
    public List<V> vertices() {
        return new ArrayList<>(vertices.keySet());
    }

    /** Return a list with the edges in the graph. */
    @Override
    public List<Edge<V>> edges() {
        Set<Edge<V>> edges = new HashSet();
        for (int i = 0; i < matrixCapacity; i++) {

            for (int j = 0; j < matrixCapacity; j++) {
                Edge<V> curEdge = matrix[i][0];
                if(curEdge != null){
                  edges.add(curEdge);
                }
            }
        }
        return edges.stream().toList();
    }

    /**
     * Return a list with the neighbors of the specified vertex.
     * Pre: The vertex is in the graph.
     */
    @Override
    public List<V> neighbors(V v) {
        assert vertices.containsKey(v);
        int index = vertices.get(v);
        List<V> neighbourList = new ArrayList<>();
        for (int i = 0; i < matrixCapacity; i++) {
           if( matrix[index][i] != null){
               neighbourList.add(getVerticie(i));
           }
        }

        return null;
    }

    private V getVerticie(int index){
        for (Map.Entry<V, Integer> vIntegerEntry : vertices.entrySet()) {
            if(vIntegerEntry.getValue().equals(index)){
                return vIntegerEntry.getKey();
            }
        }
        return null;
    }

    /**
     * Return the degree for the specified vertex.
     * Pre: The vertex is in the graph.
     */
    @Override
    public int degree(V v) {
        assert vertices.containsKey(v);
        int degree = 0;
        for (int i = 0; i < matrixCapacity; i++) {
            if(matrix[vertices.get(v)][i] != null){
                degree++;
            }
        }
        return degree;
    }

    /**
     * Return the incident edges to the specified vertex.
     * Pre: The vertex is in the graph.
     */
    @Override
    public List<Edge<V>> incidentEdges(V v) {
        assert vertices.containsKey(v);

        List<Edge<V>> edges = new ArrayList<>();
        for (int i = 0; i < matrixCapacity; i++) {
            if(matrix[vertices.get(v)][i] != null){
                edges.add(matrix[vertices.get(v)][i]);
            }
        }


        return edges;
    }

    /**
     * Return true, if the specified vertices are neighbors.
     * Pre: The vertices are vertices in the graph.
     */
    @Override
    public boolean areAdjacent(V u, V v) {
        assert vertices.containsKey(v) && vertices.containsKey(u);
        for (int i = 0; i < matrixCapacity; i++) {
            if(matrix[vertices.get(v)][vertices.get(u)] != null){
                return true;
            }
        }
        return false;
    }

    /** Print the vertices and the edges. */
    @Override
    public void printGraph() {
        for (V v : vertices.keySet()) {
            System.out.print("Vertex: " + v + "   ");
            int vRow = vertices.get(v);
            for (int col = 0; col < matrixSize; col++) {
                System.out.printf("%-18s", matrix[vRow][col]);
            }
            System.out.println();
        }
    }

    /**
     * Add a vertex to the graph.
     * Pre: The vertex is not in the graph before this addition.
     */
    @Override
    public void addVertex(V v) {
        assert !vertices.containsKey(v);
        vertices.put(v,matrixSize);
        matrixSize++;
    }

    private void extendMatrix() {
        // NOT implemented!
    }

    /**
     * Add an edge with the specified weight between the specified vertices to the graph.
     * Pre: Before addition, the vertices are in the graph, and the edge is not in the graph.
     * Pre: The weight is not negative.
     */
    @Override
    public Edge<V> addEdge(V u, V v, int weight) {
        assert vertices.containsKey(v) && vertices.containsKey(u);
        assert weight >= 0;
        Edge<V> tempEdge = new Edge<>(u,v,weight);

        assert matrix[vertices.get(u)][vertices.get(v)] == tempEdge;
        assert matrix[vertices.get(v)][vertices.get(u)] == tempEdge;


        matrix[vertices.get(u)][vertices.get(v)] = tempEdge;
        matrix[vertices.get(v)][vertices.get(u)] = tempEdge;
        return tempEdge;
    }

    /**
     * Add an edge with weight 0 between the specified vertices to the graph.
     * Pre: Before addition, the vertices are in the graph, and the edge is not in the graph.
     */
    @Override
    public Edge<V> addEdge(V u, V v) {
        return addEdge(u,v,0);
    }

    /**
     * Remove the specified vertex from the graph.
     * Pre: The vertex is in the graph, and the vertex has no incident edges.
     */
    @Override
    public void removeVertex(V v) {
        assert vertices.containsKey(v);
            int row = vertices.get(v); // Get the row index from vertices
            for (int i = 0; i < matrixCapacity; i++) {
                assert matrix[row][i] == null : "Matrix element at [" + row + "][" + i + "] is not null";
            }

        vertices.remove(v);
    }

    /**
     * Remove the edge between the specified vertices from the graph.
     * Pre: The vertices are vertices in the graph,
     *   and The graph has an edge between the vertices.
     */
    @Override
    public void removeEdge(V u, V v) {
        assert vertices.containsKey(v) && vertices.containsKey(u);
        assert matrix[vertices.get(v)][vertices.get(u)] != null;

        matrix[vertices.get(u)][vertices.get(v)] = null;
        matrix[vertices.get(v)][vertices.get(u)] = null;
    }
}
