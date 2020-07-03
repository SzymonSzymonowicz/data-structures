package rbt_project;

public class Main {

    public static void main(String[] args) {
	    RBT rbt = new RBT();
/*	    rbt.Insert(10);
	    rbt.Insert(4);
	    rbt.Insert(5);
	    rbt.Insert(6);
		rbt.Insert(1);
		rbt.Insert(3);*/

		//zad AL8.1
		rbt.insert(38);// przypadek 0 (korzen)
		rbt.insert(31);// zwykle dodanie
		rbt.insert(22);// przypadek 3 w linii
		rbt.insert(8); // przypadek 1 (zmiana koloru) + zmiana koloru korzenia
		rbt.insert(20);// przypadek 2 (zakret/trojkat)
		rbt.insert(5); // itd
		rbt.insert(10);
		rbt.insert(9);
		rbt.insert(21);
		rbt.insert(27);
		rbt.insert(29);
		rbt.insert(25);
		rbt.insert(28);


		rbt.printTree();
		rbt.statistics();

		RBT tr2 = new RBT();
		for(int i=1;i<1001;i++)
		{
			tr2.insert(i);//(int)(Math.random()*2000));
		}
		//tr2.printTree();
		tr2.statistics();

	}
}
