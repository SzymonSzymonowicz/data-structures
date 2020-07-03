package disjointset;

public class Main {
    public static void main(String[] args) {
        Node[] nodes = new Node[10];

        for(int i=0;i<10;i++)
        {
            nodes[i] = Node.makeSet(i);
        }
        
        Node.union(Node.findSet(nodes[0]),Node.findSet(nodes[1]));
        Node.union(Node.findSet(nodes[2]),Node.findSet(nodes[3]));
        Node.union(Node.findSet(nodes[1]),Node.findSet(nodes[2]));
        Node.union(Node.findSet(nodes[5]),Node.findSet(nodes[6]));
        Node.union(Node.findSet(nodes[7]),Node.findSet(nodes[8]));
        Node.union(Node.findSet(nodes[3]),Node.findSet(nodes[5]));
        Node.union(Node.findSet(nodes[0]),Node.findSet(nodes[7]));

        for(int i=0;i<10;i++){
            nodes[i].print(nodes[i],"");
        }
    }
}
