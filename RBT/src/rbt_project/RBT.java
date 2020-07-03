package rbt_project;

public class RBT {
    Node root;

    RBT(){
            this.root=null;
    }

    // wstaw do drzewa
    void insert(int key){
        Node node = new Node(key);

        bstInsert(node);

        repairInsert(node);

        this.root = node;
        while (root.getParent() != null) {
            root = root.getParent();
        }
    }

    // wstaw jak w zwyklym drzewie binarnym
    void bstInsert(Node z){
        // wstawia węzeł z do drzewa T
        Node x=this.root;
        Node y=null; // y jest ojcem x
        while(x!=null)
        {
            y=x;
            if (z.getKey()<x.getKey())
                x=x.getLeftCH();
            else
                x=x.getRightCH();
        }
        z.setParent(y);
        if (y==null)
            this.root=z;
        else if (z.getKey()<y.getKey())
            y.setLeftCH(z);
        else
            y.setRightCH(z);
        //tu cos
    }

    // napraw strukture drzewa po wstawieniu
    void repairInsert(Node node){
        if (node.getParent() == null) { // node jest korzeniem
            InsCase1(node);
        } else if (node.getParent().getColor() == Color.BLACK) { // ojciec n jest czarny
            InsCase2(node);
        } else if (node.getUncle(node) != null && node.getUncle(node).getColor() == Color.RED) { // brat ojca(wujek) jest czerwony, zamien kolory
            InsCase3(node);
        } else {
            InsCase4(node); //brat ojca(wujek) jest czarny, oba przypadki pierw trojkat, potem linia
        }
    }

    //============== przypadki naprawiania wstawiania ==================

    // n jest korzeniem
    void InsCase1(Node n) {
        n.setColor(Color.BLACK);
    }

    // ojciec n jest czarny
    void InsCase2(Node n) {
        // Nic nie rob, wszystko jest ok
        return;
    }

    // brat ojca(wujek) jest czerwony, zamien kolory
    void InsCase3(Node n) {
        n.getParent().setColor(Color.BLACK);
        n.getUncle(n).setColor(Color.BLACK);
        n.getGrandParent().setColor(Color.RED);
        repairInsert(n.getGrandParent());
    }

    // brat ojca węzła x jest czarny, węzeł x i jego ojciec leżą w różnych kierunkach tworząc zakręt (trojkat).
    // Doprowadzamy do przypadku InsCase4Step2 czyli do lini (ten sam kierunek).
    void InsCase4(Node n) {
        Node p = n.getParent();
        Node g = n.getGrandParent();

        if (n == p.getRightCH() && p == g.getLeftCH()) {
            rotateLeft(p);
            n = n.getLeftCH();
        } else if (n == p.getLeftCH() && p == g.getRightCH()) {
            rotateRight(p);
            n = n.getRightCH();
        }

        InsCase4Step2(n);
    }

    //brat ojca węzła x jest czarny, węzeł x i jego
    //ojciec leżą w tym samym kierunku. Drzewo naprawione.
    void InsCase4Step2(Node n) {
        Node p = n.getParent();
        Node g = n.getGrandParent();

        if (n == p.getLeftCH()) {
            rotateRight(g);
        } else {
            rotateLeft(g);
        }
        p.setColor(Color.BLACK);
        g.setColor(Color.RED);
    }

    //===============================ROTACJE======================================
    //rotacja w lewo
    public static void rotateLeft(Node node) {
        Node nnew = node.getRightCH();
        Node p = node.getParent();
        assert(nnew != null);  // Skoro liscie rbt zawsze sa puste nie moga zostac wewnetrznymi wezlami, sprawdzenie na w razie bledu

        node.setRightCH(nnew.getLeftCH());
        nnew.setLeftCH(node);
        node.setParent(nnew);
        // Przepiecie podrzewa
        if (node.getRightCH() != null) {
            node.getRightCH().setParent(node);
        }

        // Poczatkowo n moglo byc korzeniem.
        if (p != null) {
            if (node == p.getLeftCH()) {
                p.setLeftCH(nnew);
            } else if (node == p.getRightCH()) {
                p.setRightCH(nnew);
            }
        }
        nnew.setParent(p);
    }

    //rotacja w prawo
    public static void rotateRight(Node node){
        Node nnew = node.getLeftCH();
        Node p = node.getParent();
        assert(nnew != null);  // Skoro liscie rbt zawsze sa puste nie moga zostac wewnetrznymi wezlami, sprawdzenie na w razie bledu

        node.setLeftCH(nnew.getRightCH());
        nnew.setRightCH(node);
        node.setParent(nnew);

        // Przepiecie podrzewa
        if (node.getLeftCH() != null) {
            node.getLeftCH().setParent(node);
        }

        // Poczatkowo n moglo byc korzeniem.
        if (p != null) {
            if (node == p.getLeftCH()) {
                p.setLeftCH(nnew);
            } else if (node == p.getRightCH()) {
                p.setRightCH(nnew);
            }
        }
        nnew.setParent(p);
    };
    //===================================================================================
    //drukowanie drzewa
    void printTree()
    {
        printingRec(this.getRoot(),"",true);
    }

    private void printingRec(Node rt, String indent, boolean last) {
        if (rt != null) {
            System.out.print(indent);
            if (last) {
                if(rt.getParent()==null)
                {
                    System.out.print("Korzeń--->");
                    indent += "          ";
                }
                else {
                    System.out.print("R--->");
                    indent += "     ";
                }
            } else {
                System.out.print("L--->");
                indent += "|   ";
            }

            if(rt.getColor()==Color.RED)
                System.out.println("\033[0;31m" + rt.getKey() + "\033[0m");
            else
                System.out.println("\033[0;30m" + rt.getKey() + "\033[0m");
            //System.out.println(root.getKey() + "(" + sColor + ")");
            printingRec(rt.getLeftCH(), indent, false);
            printingRec(rt.getRightCH(), indent, true);
        }
    }

    Node getRoot(){
        return root;
    }

    //=========================ROZNE FUNKCJE===================================

    //ile jest czerwonych wezlow
    int countRedNodes(Node n){
        //Node temp = n;
        int i=0;
        if(n==null)
            return 0;

        i+=countRedNodes(n.getLeftCH());
        i+=countRedNodes(n.getRightCH());

        if(n!=null && n.getColor()==Color.RED)
            i++;

        return i;
    }

    //najdluzsza sciezka
    int maxDepth(Node n){
        if(n==null){
            return 0;
        }
        else {

            int localL = maxDepth(n.getLeftCH());
            int localR = maxDepth(n.getRightCH());

            if (localL > localR)
                return (localL+1);
            else
                return (localR+1);
        }
    }

    //najkrotsza sciezka
    int minDepth(Node n){
        if(n==null){
            return 0;
        }
        else {

            int localL = maxDepth(n.getLeftCH());
            int localR = maxDepth(n.getRightCH());

            if (localL < localR)
                return (localL+1);
            else
                return (localR+1);
        }
    }
    //zbior informacji
    void statistics(){
        System.out.println("\u001B[34m"+"===========================================");
        System.out.println("Liczba czerwonych węzłów: "+this.countRedNodes(this.getRoot()));
        System.out.println("Maksymalna głębokość liści w drzewie:  "+this.maxDepth(this.getRoot()));
        System.out.println("Minimalna głębokość liści w drzewie: "+this.minDepth(this.getRoot()));
        System.out.println("==========================================="+"\u001B[0m");
        System.out.println();
    }

    //funkcj pomocnicze
    Node TreeMinimum(Node x){
    // zwraca skrajny lewy węzeł w poddrzewie o korzeniu x
    // czyli węzeł o najmniejszym kluczu w tym poddrzewie
        while (x.getLeftCH() != null) {
            x = x.getLeftCH();
        }
        return x;
    }

    Node search(Node x, int k){
        while (x!=null && k!=x.key) {
            if (k < x.key)
                x = x.getLeftCH();
            else
                x = x.getRightCH();
        }
        return x; // zwraca NIL gdy nie ma klucza k
    }

    //===============================================================================
}
