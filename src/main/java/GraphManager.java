import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
//import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class GraphManager {
    private Graph<Node, DefaultEdge> graph;
    enum Algorithm{
        bfs,
        dfs
    }


    public GraphManager() {
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
    }

    public void addNode(Node node) {
        graph.addVertex(node);
    }

    public void addEdge(Node src, Node dst) {
        graph.addEdge(src, dst);
    }

    public Path GraphSearch(Node src, Node dst, Algorithm algo) {
        if(algo == Algorithm.bfs)
        {
            BFSShortestPath<Node, DefaultEdge> bfs = new BFSShortestPath<>(graph);
            GraphPath<Node, DefaultEdge> path = bfs.getPath(src, dst);
            if (path == null) {
                return null;
            }
            List<Node> nodes = path.getVertexList();
            Path result = new Path();
            for (Node node : nodes) {
                result.addNode(node);
            }
            return result;
        }
        else if(algo == Algorithm.dfs) {

            DepthFirstIterator<Node, DefaultEdge> dfs = new DepthFirstIterator<>(graph, src);
            while (dfs.hasNext()) {
                Node currentNode = dfs.next();
                if (currentNode.equals(dst)) {
                    List<Node> pathNodes = new ArrayList<>();
                    Deque<Object> stack = dfs.getStack();
                    while (!stack.isEmpty()) {
                        pathNodes.add((Node) stack.pop());
                    }
                    return new Path(pathNodes);
                }
            }
        }
        else {
            return null;
        }
        return null;
    }
}