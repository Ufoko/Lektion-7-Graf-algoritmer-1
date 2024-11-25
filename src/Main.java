import adjacencymatrixgraph.AdjacencyMatrixGraph;
import adjacencymatrixgraph.Edge;

public class Main {
    public static void main(String[] args) {

        AdjacencyMatrixGraph<Integer> graf =  new AdjacencyMatrixGraph(20);

        graf.addVertex(55);
        graf.addVertex(66);
        graf.addVertex(77);
        graf.addVertex(88);
        graf.addVertex(99);
        graf.addVertex(500);
        graf.addVertex(5511);
        graf.addVertex(5522);


        graf.addEdge(55,66,3);
        graf.addEdge(55,77,43);
        graf.addEdge(55,500,43);
        graf.addEdge(66,88,43);
        graf.addEdge(5522,88);
        graf.addEdge(5511,500);
        graf.addEdge(99,500,5);

    //    graf.printGraph();

      //  System.out.println(graf.neighbors(77));
//        System.out.println(graf.incidentEdges(77));
        System.out.println(graf.areAdjacent(77, 55));


    }
}