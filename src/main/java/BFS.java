import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.GraphExporter;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;
import org.jgrapht.util.SupplierUtil;
import org.jgrapht.ext.JGraphXAdapter;

import com.mxgraph.util.*;
import com.mxgraph.layout.*;
import com.mxgraph.view.*;
import com.mxgraph.canvas.*;
import com.mxgraph.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.Set;

public class BFS extends GraphSearch implements GraphSearchStrategy{

    private Queue<String> queue;
    private Set<String> visited;
    private List<String> searchResult;

    public BFS (Graph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    public List<String> search(Graph<String, DefaultEdge> graph, String src, String dst) {
        // Implement BFS logic and return the path between src and dst nodes
        return performBFS(src, dst);
    }
    

    @Override
    protected void initializeSearch() {
        queue = new LinkedList<>();
        visited = new HashSet<>();
        searchResult = new ArrayList<>();
    }

    @Override
    protected void addInitialNode(String startNode) {
        queue.add(startNode);
    }

    @Override
    protected boolean isSearchFinished() {
        return queue.isEmpty();
    }

    @Override
    protected String getNextNode() {
        return queue.poll();
    }

    @Override
    protected void markVisited(String currentNode) {
        visited.add(currentNode);
    }

    @Override
    protected void processNeighbors(String currentNode) {
        for (String neighbor : Graphs.neighborListOf(graph, currentNode)) {
            if (!visited.contains(neighbor) && !queue.contains(neighbor)) {
                queue.add(neighbor);
            }
        }
        searchResult.add(currentNode);
    }

    @Override
    protected List<String> getSearchResult() {
        return searchResult;
    }
}

