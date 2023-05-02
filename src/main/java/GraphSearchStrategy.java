import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import java.util.List;

public interface GraphSearchStrategy {
    List<String> search(Graph<String, DefaultEdge> graph, String src, String dst);
}
