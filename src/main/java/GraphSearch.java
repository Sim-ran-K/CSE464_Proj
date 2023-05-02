import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;

public abstract class GraphSearch {
    protected Graph<String, DefaultEdge> graph;

    public GraphSearch(Graph<String, DefaultEdge> graph) {
        this.graph = graph;
    }

    // Template method
    public final List<String> search(String startNode) {
        // Initialize search
        initializeSearch();

        // Add the starting node to the data structure
        addInitialNode(startNode);

        // Main search loop
        while (!isSearchFinished()) {
            // Fetch the next node to search
            String currentNode = getNextNode();

            // Mark the current node as visited
            markVisited(currentNode);

            // Process the current node's neighbors
            processNeighbors(currentNode);
        }

        // Return the result
        return getSearchResult();
    }

    protected abstract void initializeSearch();

    protected abstract void addInitialNode(String startNode);

    protected abstract boolean isSearchFinished();

    protected abstract String getNextNode();

    protected abstract void markVisited(String currentNode);

    protected abstract void processNeighbors(String currentNode);

    protected abstract List<String> getSearchResult();
}
