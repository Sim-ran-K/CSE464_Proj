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

// Refactoring 3: Replace Conditional with Polymorphism
// Command line file path handler implementation
interface FilePathHandler {
    String getInputFilePath();
    String getOutputFilePath();
}

// Refactoring 3: Replace Conditional with Polymorphism
// Command line file path handler implementation

class DefaultFilePathHandler implements FilePathHandler {
    public String getInputFilePath() {
        return "input.dot";
    }

    // Refactoring 3: Replace Conditional with Polymorphism
    // Command line file path handler implementation
    public String getOutputFilePath() {
        return "output.dot";
    }
}

// Refactoring 3: Replace Conditional with Polymorphism
// Command line file path handler implementation
class CommandLineFilePathHandler implements FilePathHandler {
    private String[] args;

    public CommandLineFilePathHandler(String[] args) {
        this.args = args;
    }

    public String getInputFilePath() {
        return args[0];
    }

    public String getOutputFilePath() {
        return args[1];
    }
}
public class GraphParser {
    private org.jgrapht.Graph<String, DefaultEdge> graph;

    // Add the constant SCALE_FACTOR
    private static final double SCALE_FACTOR = 2.0;

    public void parseGraph(String filepath) throws FileNotFoundException {
        // Create a new directed graph using the JGraphT library
        this.graph = new SimpleDirectedGraph(SupplierUtil.createStringSupplier(), SupplierUtil.createDefaultEdgeSupplier(),false);

        // Read in the DOT file using the JGraphT DOTImporter
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<String, DefaultEdge>();//((label, attributes) -> label);
        importer.importGraph(this.graph, new File(filepath));

    }


    // Extract method refactoring
    // we can extract two separate methods to handle the vertices and edges information.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVerticesInfo());
        sb.append(getEdgesInfo());
        return sb.toString();
    }

    private String getVerticesInfo() {
        StringBuilder sb = new StringBuilder();
        Set<String> vertices = this.graph.vertexSet();
        sb.append("Number of nodes: ").append(vertices.size()).append("\n");
        sb.append("Nodes: ").append(vertices.toString()).append("\n");
        return sb.toString();
    }

    private String getEdgesInfo() {
        StringBuilder sb = new StringBuilder();
        Set<DefaultEdge> edges = this.graph.edgeSet();
        sb.append("Number of edges: ").append(edges.size()).append("\n");
        sb.append("Edges: \n");
        for (DefaultEdge edge : edges) {
            String source = this.graph.getEdgeSource(edge);
            String target = this.graph.getEdgeTarget(edge);
            sb.append(source).append(" -> ").append(target).append("\n");
        }
        return sb.toString();
    }

    public void outputGraph(String filepath) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(filepath)) {
            out.println(this.graph);
        }
    }

    //Feature 2:
    public boolean addNode(String label) {
        boolean isSuccess = false;
        if(!this.graph.containsVertex(label)) {
            isSuccess = this.graph.addVertex(label);
        }
        else {
            System.out.println("Node :"+label+" already exists");
        }
        return isSuccess;
    }

    public boolean removeNode(String label) {
        boolean isSuccess = false;
        if(this.graph.containsVertex(label)) {
            isSuccess = this.graph.removeVertex(label);
        }
        else {
            System.out.println("Node :"+label+" does not exist");
        }
        return isSuccess;
    }

    public boolean addNodes(String[] label) {
        boolean isSuccess = true;
        for(String nl:label) {
            if(!this.addNode(nl)) {
                System.out.println("Node :"+nl+" already exists");
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    public boolean removeNodes(String[] label) {
        boolean isSuccess = this.graph.removeAllVertices(java.util.List.of(label));
        return isSuccess;
    }

    //Feature 3:
    public boolean addEdge(String srcLabel, String dstLabel) {
        boolean isSuccess = false;
        if(!this.graph.containsEdge(srcLabel,dstLabel)) {
            DefaultEdge defEdge =  this.graph.addEdge(srcLabel,dstLabel);
            isSuccess = true;
        }
        else {
            System.out.println("Edge :"+srcLabel+"->"+dstLabel+" already exists");
        }
        return isSuccess;
    }

    public boolean removeEdge(String srcLabel, String dstLabel) {
        boolean isSuccess = false;
        if(this.graph.containsEdge(srcLabel,dstLabel)) {
            this.graph.removeEdge(srcLabel,dstLabel);
            isSuccess = true;
        }
        else {
            System.out.println("Edge :"+srcLabel+"->"+dstLabel+" does not exist");
        }
        return isSuccess;
    }
    //Feature 4:

    public void outputDOTGraph(String filePath) {
        try {
            DOTExporter<String, DefaultEdge> exporter = new DOTExporter<String, DefaultEdge>();
            exporter.exportGraph(this.graph, new File(filePath));
        }
        catch(Exception e) {
            System.out.println("Exception in outputDOTGraph :"+e);
            e.printStackTrace();
        }
    }

    public void outputGraphics(String filePath, String format) {
        try {
            mxGraphComponent component = new mxGraphComponent(new JGraphXAdapter<>(this.graph));
            //Refactoring 5: Replace Magic number with constant
            // Use the constant SCALE_FACTOR instead of the magic number 2
            BufferedImage image = mxCellRenderer.createBufferedImage(component.getGraph(), null, SCALE_FACTOR, Color.WHITE, component.isAntiAlias(), null, component.getCanvas());
            ImageIO.write(image, format, new File(filePath));
        } catch (Exception e) {
            System.out.println("Exception in outputGraphics :" + e);
            e.printStackTrace();
        }
    }




    // Refactoring 3: Replace Conditional with Polymorphism
    // Instantiate the appropriate file path handler based on the length of the args array
    public static void main(String[] args) throws FileNotFoundException {
        FilePathHandler filePathHandler = (args.length > 1) ? new CommandLineFilePathHandler(args) : new DefaultFilePathHandler();

        String inFilepath = filePathHandler.getInputFilePath();
        String outFilepath = filePathHandler.getOutputFilePath();

        GraphParser parser = new GraphParser();
        parser.parseAndPrintGraph(inFilepath);
        parser.outputGraph(outFilepath);

        parser.addAndPrintNodes();
        parser.exportGraphs();
        parser.removeAndPrintNodes();
    }
    //Refactoring 2: Extract method
    private void parseAndPrintGraph(String filepath) throws FileNotFoundException {
        parseGraph(filepath);
        System.out.println(toString());
    }
    //Refactoring 2: Extract Method
    private void addAndPrintNodes() {
        addNode("firstSourceNode");
        addNode("firstDestNode");
        addEdge("firstSourceNode", "firstDestNode");
        System.out.println(toString());
    }
    //Refactoring 2: Extract Method
    private void removeAndPrintNodes() {
        removeEdge("firstSourceNode", "firstDestNode");
        removeNode("firstSourceNode");
        removeNode("firstDestNode");
        System.out.println(toString());
    }
    //Refactoring 2: Extract Method
    private void exportGraphs() throws FileNotFoundException {
        outputGraph("output_updated1.dot");
        outputGraph("output_removed1.dot");
        outputDOTGraph("outputDOTGraph.dot");
        outputGraphics("outputpngGraph", "PNG");
    }
}