import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import java.util.Stack;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class DFS extends GraphSearch {

    private Stack<String> stack;
    private Set<String> visited;
    private List<String> searchResult;

    public  DFS(Graph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    protected void initializeSearch() {
        stack = new Stack<>();
        visited = new HashSet<>();
        searchResult = new ArrayList<>();
    }

    @Override
    protected void addInitialNode(String startNode) {
        stack.push(startNode);
    }

    @Override
    protected boolean isSearchFinished() {
        return stack.isEmpty();
    }

    @Override
    protected String getNextNode() {
        return stack.pop();
    }

    @Override
    protected void markVisited(String currentNode) {
        visited.add(currentNode);
    }

    @Override
    protected void processNeighbors(String currentNode) {
        for (String neighbor : Graphs.neighborListOf(graph, currentNode)) {
            if (!visited.contains(neighbor) && !stack.contains(neighbor)) {
                stack.push(neighbor);
            }
        }
        searchResult.add(currentNode);
    }

    @Override
    protected List<String> getSearchResult() {
        return searchResult;
    }
}


