#include <stdio.h>
#include <stdlib.h>

typedef struct List {
  struct List *next;
  struct List *prev;
  char *key;
} List;


//inicjalizacja listy, stworzenie wartownika
List* sentry(){
	List *ls=(List*)calloc(1, sizeof(List));
	ls->next=ls;
	ls->prev=ls;
	ls->key=NULL;
	return ls;
}

//wartownik ostatni element, w prev ma prawdziwy ostatni element w next ma head

//==== WSTAW(L, s) ====
void insert(List* L, char* x)//wstaw na poczatek
{
	List *new=(List*)calloc(1, sizeof(List));
	new->key = x;
		
	new->next = L->next;
	new->prev = L;

	L->next->prev = new;
	L->next = new;
}

//==== DRUKUJ(L)  ====
void Print(List* L)
{
	List* wsk = L->next;
	char* x = wsk->key;
	
	printf("List(");
	while(x != NULL)
	{
		printf("%s",wsk->key);
		wsk = wsk->next;
		x = wsk->key;
		if(x != NULL)
			printf(", ");
	}
	printf(")\n");	
}

//==== SZUKAJ(s, L) ====
List* search(List* L, char* s)
{
	List* wsk = L->next;
	char* x = wsk->key;
	
	while(x != NULL)
	{
		if(x == s)
		{
			//printf("Sukces\n");
			return wsk;
		}
		wsk = wsk->next;
		x = wsk->key;
	}
	//printf("Niepowodzonko!\n");
	return NULL;//moze byc wsk(bo i tak ma nulla)
}

//==== USUN(s, L) ====
void delete(List* L, char* s)
{
	List *wsk = search(L,s);
	
	if(wsk == NULL)//jesli lista jest pusta
	{
		return;
	}
	
	wsk->next->prev = wsk->prev;
	wsk->prev->next = wsk->next;
	
	free(wsk);
}

//==== KASUJ(L) ====
void deleteAll(List* L)
{
	List* wsk = L->next;
	char* x = wsk->key;
	
	while(x != NULL)//zwolnienie pamieci
	{
		List* cop = wsk;
		wsk = wsk->next;
		free(cop);
		x = wsk->key;
	}
	
	L->next = L;
	L->prev = L;
}

//==== BEZPOWTORZEN(L) ====
List* noRepeats(List* L)
{
	List* N = sentry();
	
	if(L->next == L)	//lista pusta tj. sam wartownik
		return N;
	
	List* wsk = L->prev;
	char* x = wsk->key;
	insert(N,x);	//pierwszy element zawsze jest unikalny
	
	while(x != NULL)
	{
		//sprawdzamy czy w zrobionej kopii nie ma przypadkiem już klucza, jak go nie ma 			
		//to dodajemy do listy NULL=nie ma
		if (search(N, x) == NULL)
		{
			insert(N,x);
		}
		wsk = wsk->prev;
		x = wsk->key;
	}	
	
	return N;
}

//==== SCAL(L1, L2) ====
List* merge(List* L1, List* L2)//listy rozlaczne
{
	List* L3 = sentry();
	
	//"usun" wartownikow z L1 i L2 polacz ostatnia wartosc L1 z pierwsza L2 wartownik L3 next z L1 prev z L2

	//ustawianie wartownika L3
	L3->next = L1->next;// head L3 = head L1
	L3->prev = L2->prev;// head(tail) L3= head(tail) L2
	
	//ustawienie punktu zlaczenia L1 i L2
	L1->prev->next = L2->next;// next ostatniego el L1 wskazuje na poczatek L2
	L2->next->prev = L1->prev;// prev pierwszego el L2 wskazuje na koniec L1
	
	//ustawianie pierwszego el L1 i ostatniego L2, na wartownika L3
	L1->next->prev = L3;// prev pierwszego el L1 wskazuje na wartownika L3
	L2->prev->next = L3;// next ostatniego el L2 wskazuje na wartownika L3
	
	
	//listy L1 i L2 zawieraja tylko wartownika, czyli sa puste
	L1->next = L1;
	L1->prev = L1;
	
	L2->next = L2;
	L2->prev = L2;
	
	return L3;
}




int main() {
	List* test = sentry();
	
	char* napisy[] = {"napis","kot","pies","lol","hehe","napis","kot","pies","lol","hehe"};
	
	for(int i = 0; i < sizeof(napisy)/sizeof(napisy[0]); i++)
		insert(test,napisy[i]);
	
	
	Print(test);
	printf("\n");
	
	printf("Wynik wskaznik: %p \n", search(test,"kot"));
	printf("\n");
	
	char* usun = "kot";
	printf("Usuwam \"%s\" z listy\n", usun);
	delete(test, usun);
	Print(test);
	printf("\n");
	
	printf("Kopia Listy bez powtorzen\n");
	List* kopia = noRepeats(test);
	Print(kopia);
	printf("\n");
	
	
	List* l1 = sentry();
	char* nap1[] = {"ciekawe","i","pomyslowe","napisy"};
	for(int i = 0; i < sizeof(nap1)/sizeof(nap1[0]); i++)
		insert(l1,nap1[i]);
		
	List* l2 = sentry();
	char* nap2[] = {"ala","ma","czarnego","kota"};
	for(int i = 0; i < sizeof(nap2)/sizeof(nap2[0]); i++)
		insert(l2,nap2[i]);
	
	printf("Test Scalania:\n");
	printf("Lista 1:\n");
	Print(l1);
	printf("\n");
	
	printf("Lista 2:\n");
	Print(l2);
	printf("\n");
	
	List* scal = merge(l1,l2);
	printf("Wynik scalenia:\n");
	Print(scal);
	printf("\n");
	
	
	printf("Kasuje wszystko w test\n");
	deleteAll(test);
	Print(test);
	printf("\n");
	
	printf("Czy po usunieciu oryginalu nie usunela sie kopia?\n");
	Print(kopia);
	printf("\n");
	
}
