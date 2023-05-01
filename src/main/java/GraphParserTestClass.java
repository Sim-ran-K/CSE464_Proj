import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;


import java.util.Set;
import java.io.*;
import java.nio.file.*;


import static org.junit.jupiter.api.Assertions.*;

class GraphParserTest {

    @Test
    void parseGraph() {
        GraphParser parser = new GraphParser();
        try {
            parser.parseGraph("test1.dot");
            assertNotNull(parser.toString());
        }
        catch(Exception e) {
        }
    }

    @Test
    void testToString() {
        GraphParser parser = new GraphParser();
        try {
            parser.parseGraph("test1.dot");
            StringBuilder sb = new StringBuilder();
            sb.append("Number of nodes: ").append("2").append("\n");
            sb.append("Nodes: ").append("[0, 1]").append("\n");
            sb.append("Number of edges: ").append("0").append("\n");
            sb.append("Edges: \n");
            assertEquals(sb.toString(),parser.toString());
        }
        catch(Exception e) {
        }
    }

    @Test
    void outputGraph() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test1.dot");
            parser.outputGraph("test1_out.dot");
            assertTrue(Files.exists(Paths.get("test1_out.dot")));
            assertTrue(Files.exists(Paths.get("test1_expected.dot")));
            assertArrayEquals(Files.readAllBytes(Paths.get("test1_out.dot")),Files.readAllBytes(Paths.get("test1_expected.dot")));

        }
        catch(Exception e) {
        }
    }

    // Feature2
    @Test
    void addNode() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test2.dot");
            parser.addNode("firstSourceNode");
            parser.addNode("firstDestNode");
            StringBuilder sb = new StringBuilder();
            sb.append("Number of nodes: ").append("4").append("\n");
            sb.append("Nodes: ").append("[0, 1, firstSourceNode, firstDestNode]").append("\n");
            sb.append("Number of edges: ").append("0").append("\n");
            sb.append("Edges: \n");
            assertEquals(sb.toString(),parser.toString());
        }
        catch(Exception e) {
        }
    }

    @Test
    void removeNode() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test2.dot");
            parser.addNode("firstSourceNode");
            parser.addNode("firstDestNode");
            parser.removeNode("firstDestNode");
            StringBuilder sb = new StringBuilder();
            sb.append("Number of nodes: ").append("3").append("\n");
            sb.append("Nodes: ").append("[0, 1, firstSourceNode]").append("\n");
            sb.append("Number of edges: ").append("0").append("\n");
            sb.append("Edges: \n");
            assertEquals(sb.toString(),parser.toString());
        }
        catch(Exception e) {
        }
    }

    @Test
    void addNodes() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test2.dot");
            String[] nodes = new String[2];
            nodes[0] = "firstSourceNode";
            nodes[1] = "firstDestNode";
            parser.addNodes(nodes);
            StringBuilder sb = new StringBuilder();
            sb.append("Number of nodes: ").append("4").append("\n");
            sb.append("Nodes: ").append("[0, 1, firstSourceNode, firstDestNode]").append("\n");
            sb.append("Number of edges: ").append("0").append("\n");
            sb.append("Edges: \n");
            assertEquals(sb.toString(),parser.toString());
        }
        catch(Exception e) {
        }
    }

    @Test
    void removeNodes() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test2.dot");
            String[] nodes = new String[2];
            nodes[0] = "firstSourceNode";
            nodes[1] = "firstDestNode";
            parser.addNodes(nodes);
            StringBuilder sb = new StringBuilder();
            sb.append("Number of nodes: ").append("4").append("\n");
            sb.append("Nodes: ").append("[0, 1, firstSourceNode, firstDestNode]").append("\n");
            sb.append("Number of edges: ").append("0").append("\n");
            sb.append("Edges: \n");
            assertEquals(sb.toString(),parser.toString());
            parser.removeNodes(nodes);
            StringBuilder sb1 = new StringBuilder();
            sb1.append("Number of nodes: ").append("2").append("\n");
            sb1.append("Nodes: ").append("[0, 1]").append("\n");
            sb1.append("Number of edges: ").append("0").append("\n");
            sb1.append("Edges: \n");
            assertEquals(sb1.toString(),parser.toString());
        }
        catch(Exception e) {
        }
    }

    //feature3
    @Test
    void addEdge() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test2.dot");
            String[] nodes = new String[2];
            nodes[0] = "firstSourceNode";
            nodes[1] = "firstDestNode";
            parser.addNodes(nodes);
            parser.addEdge(nodes[0],nodes[1]);
            StringBuilder sb = new StringBuilder();
            sb.append("Number of nodes: ").append("4").append("\n");
            sb.append("Nodes: ").append("[0, 1, firstSourceNode, firstDestNode]").append("\n");
            sb.append("Number of edges: ").append("0").append("\n");
            sb.append("Edges: \n");
            sb.append("firstSourceNode -> firstDestNode").append("\n");
            assertEquals(sb.toString(),parser.toString());
        }
        catch(Exception e) {
        }
    }

    @Test
    void removeEdge() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test2.dot");
            String[] nodes = new String[2];
            nodes[0] = "firstSourceNode";
            nodes[1] = "firstDestNode";
            parser.addNodes(nodes);
            parser.addEdge(nodes[0],nodes[1]);
            StringBuilder sb = new StringBuilder();
            sb.append("Number of nodes: ").append("4").append("\n");
            sb.append("Nodes: ").append("[0, 1, firstSourceNode, firstDestNode]").append("\n");
            sb.append("Number of edges: ").append("0").append("\n");
            sb.append("Edges: \n");
            sb.append("firstSourceNode -> firstDestNode").append("\n");
            assertEquals(sb.toString(),parser.toString());
            parser.removeEdge(nodes[0],nodes[1]);
            StringBuilder sb1 = new StringBuilder();
            sb1.append("Number of nodes: ").append("4").append("\n");
            sb1.append("Nodes: ").append("[0, 1, firstSourceNode, firstDestNode]").append("\n");
            sb1.append("Number of edges: ").append("0").append("\n");
            sb1.append("Edges: \n");
            assertEquals(sb1.toString(),parser.toString());
        }
        catch(Exception e) {
        }
    }

    @Test
    void outputDOTGraph() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test1.dot");
            parser.outputDOTGraph("test1_dot.dot");
            assertTrue(Files.exists(Paths.get("test1_dot.dot")));
            assertArrayEquals(Files.readAllBytes(Paths.get("test1_dot.dot")),Files.readAllBytes(Paths.get("test1.dot")));
        }
        catch(Exception e) {
        }
    }

    @Test
    void outputGraphics() {
        GraphParser parser = new GraphParser();
        try{
            parser.parseGraph("test1.dot");
            parser.outputGraphics("test1.png","PNG");
            assertTrue(Files.exists(Paths.get("test1.png")));
            assertTrue(Files.exists(Paths.get("test1_expected.png")));
            assertArrayEquals(Files.readAllBytes(Paths.get("test1_expected.png")),Files.readAllBytes(Paths.get("test1.png")));
        }
        catch(Exception e) {
        }
    }

}