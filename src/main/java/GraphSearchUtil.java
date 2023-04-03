//import org.w3c.dom.Node;

public class GraphSearchUtil {
    public static void main(String[] args) {
        GraphManager graphMgr = new GraphManager();
        Node n1 = new Node("Phoenix");
        Node n2 = new Node("Tempe");
        Node n3 = new Node("Los Angeles");
        Node n4 = new Node("San Francisco");
        Node n5 = new Node("Seattle");
        Node n6 = new Node("Denver");
        Node n7 = new Node("Las Vegas");
        Node n8 = new Node("Dallas");
        Node n9 = new Node("Chicago");
        Node n10 = new Node("New York");

        graphMgr.addNode(n1);
        graphMgr.addNode(n2);
        graphMgr.addNode(n3);
        graphMgr.addNode(n4);
        graphMgr.addNode(n5);
        graphMgr.addNode(n6);
        graphMgr.addNode(n7);
        graphMgr.addNode(n8);
        graphMgr.addNode(n9);
        graphMgr.addNode(n10);

        graphMgr.addEdge(n1, n2);
        graphMgr.addEdge(n1, n3);
        graphMgr.addEdge(n1, n4);
        graphMgr.addEdge(n1, n5);
        graphMgr.addEdge(n1, n7);
        graphMgr.addEdge(n3, n4);
        graphMgr.addEdge(n4, n9);
        graphMgr.addEdge(n3, n8);
        graphMgr.addEdge(n7, n6);
        graphMgr.addEdge(n8, n10);
        graphMgr.addEdge(n6, n10);
        graphMgr.addEdge(n9, n8);

        Node srcNode = null;
        Node destinationNode = null;
        GraphManager.Algorithm algo = null;

        if (args.length > 0) {
            srcNode = new Node(args[0]);
        }
        if (args.length > 1) {
            destinationNode = new Node(args[1]);
        }
        if (args.length > 2) {
            if (args[2].equalsIgnoreCase("bfs")) algo = GraphManager.Algorithm.bfs;
            else if (args[2].equalsIgnoreCase("bfs")) algo = GraphManager.Algorithm.dfs;
            else System.out.println("Invalid Algorithm");
        }
        if (srcNode != null && destinationNode != null && algo != null) {
            Path result = graphMgr.GraphSearch(srcNode, destinationNode, algo);
            System.out.println("Result : " + result);
        } else System.out.println("Insufficient Input Arguments");
    }

}