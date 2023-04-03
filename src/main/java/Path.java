//import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Node> nodes;

    public Path(List<Node> pathNodes) {
        nodes = pathNodes;
    }

    public Path() {
        nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            sb.append(node.getNodeName()).append(" -> ");
        }
        sb.delete(sb.length() - 4, sb.length());
        return sb.toString();
    }
}