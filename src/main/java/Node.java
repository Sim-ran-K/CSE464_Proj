import java.util.Objects;

public class Node {
    String nodeName;
    public Node() {
        nodeName = new String("");
    }
    public Node(String name) {
        this.nodeName = name;
    }
    public String getNodeName() {
        return this.nodeName;
    }
    public void setNodeName(String name) {
        this.nodeName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.getNodeName().equals(((Node)o).getNodeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeName);
    }
}