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

public class GraphParser {
    private org.jgrapht.Graph<String, DefaultEdge> graph;

    public void parseGraph(String filepath) throws FileNotFoundException {
        // Create a new directed graph using the JGraphT library
        this.graph = new SimpleDirectedGraph(SupplierUtil.createStringSupplier(), SupplierUtil.createDefaultEdgeSupplier(),false);

        // Read in the DOT file using the JGraphT DOTImporter
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<String, DefaultEdge>();//((label, attributes) -> label);
        importer.importGraph(this.graph, new File(filepath));

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<String> vertices = this.graph.vertexSet();
        sb.append("Number of nodes: ").append(vertices.size()).append("\n");
        sb.append("Nodes: ").append(vertices.toString()).append("\n");

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
        try{
            mxGraphComponent component = new mxGraphComponent(new JGraphXAdapter<>(this.graph));
            BufferedImage image = mxCellRenderer.createBufferedImage(component.getGraph(),null,2, Color.WHITE,component.isAntiAlias(),null,component.getCanvas());
            ImageIO.write(image,format,new File(filePath));
        }
        catch(Exception e) {
            System.out.println("Exception in outputGraphics :"+e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String inFilepath = "input.dot";
        if(args.length>0) inFilepath = args[0];

        GraphParser parser = new GraphParser();
        parser.parseGraph(inFilepath);

        System.out.println(parser.toString());
        String outFilepath = "output.dot";
        if(args.length>1) outFilepath = args[1];
        parser.outputGraph(outFilepath);


        //Add Node
        parser.addNode("firstSourceNode");
        parser.addNode("firstDestNode");
        parser.addEdge("firstSourceNode","firstDestNode");
        System.out.println(parser.toString());
        String updFilepath = "output_updated1.dot";
        parser.outputGraph(updFilepath);
        parser.removeEdge("firstSourceNode","firstDestNode");
        parser.removeNode("firstSourceNode");
        parser.removeNode("firstDestNode");
        System.out.println(parser.toString());
        String rmFilepath = "output_removed1.dot";
        parser.outputGraph(rmFilepath);

        //outputDOTGraph
        parser.outputDOTGraph("outputDOTGraph.dot");
        parser.outputGraphics("outputpngGraph","PNG");

    }
}