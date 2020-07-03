package rbt_project;

public class Node {


    Node leftCH;
    Node rightCH;
    Node parent;
    int key;
    Color color;

    Node(int key){
        this.key = key;
        this.color = Color.RED;
        this.leftCH = this.rightCH = this.parent = null;
    }


    public Node getLeftCH() { //lewe dziecko
        return leftCH;
    }

    public Node getRightCH() { //prawe dziecko
        return rightCH;
    }

    public Node getParent() { //ojciec
        return parent;
    }

    public Node getGrandParent(){
        return parent.getParent();
    }

    //sibling, uncle mozna przerobic na statica

    public  Node getSibling(Node n) { //rodzenstwo n
        Node p = n.getParent();

        if(p == null){
            return null;// brak ojca, wiec brak rodzenstwa
        }

        if(n == p.getLeftCH()) { //jesli n jest lewym dzieckiem zwroc prawy
            return p.getRightCH();
        }
        else {
            return p.getLeftCH(); //zwroc prawy
        }
    }

    public Node getUncle(Node n){ //wujek n
        Node p = n.getParent();

        //brak ojca, wiec brak wujka
        return getSibling(p);
    }

    public int getKey() {
        return key;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setLeftCH(Node leftCH) {
        this.leftCH = leftCH;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setRightCH(Node rightCH) {
        this.rightCH = rightCH;
    }
}
