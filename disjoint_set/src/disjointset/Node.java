package disjointset;

public class Node {
    Node p;
    int rank;
    int key;

    public static Node makeSet(int k){
        Node x = new Node();
        x.key=k;
        x.p=x;
        x.rank=0;
        return x;
    }

    public static Node findSet(Node x){
        if(x != x.p) {
            x.p = findSet(x.p);
        }
        return x.p;
    }

    public static void union(Node x, Node y){
        if(x.rank < y.rank){
            x.p = y;
        }
        else{
            y.p = x;
            if(x.rank == y.rank){
                y.rank++;
            }
        }
    }

    void print(Node x, String str){
        if(x.p==x){ //jesli x to root
            System.out.println(str+x.key);
        }
        else{
            str+=""+x.key+" ---> ";
            print(x.p,str);
        }
    }
}
