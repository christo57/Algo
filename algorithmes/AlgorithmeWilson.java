package algorithmes;

import java.util.ArrayList;
import structure.Edge;
import structure.Graph;

public class AlgorithmeWilson implements Algorithme{

	//sommet de depart de l'algorithme
	private int sommetDeDepartInit;
	//sommet pas encore visite
	private int sommetNonVisite;
	//list de sommet deja visite
	ArrayList<Integer> visites;

	public AlgorithmeWilson(int sommet) {
		this.sommetDeDepartInit = sommet;
	}

	public AlgorithmeWilson(){
		this.sommetDeDepartInit = -1;
	}

	public Graph executer(Graph g) {
		Graph res = g.copier();

		visites = new ArrayList<Integer>();

		//au depart, on visite un sommet
		int sommetDeDepart = sommetDeDepartInit;
		if(sommetDeDepart == -1) {
			sommetDeDepart = (int) (Math.random() * res.getV());
		}
		visites.add(sommetDeDepart);

		while(visites.size() != res.getV()) {
			//on choisi un sommet non visite
			sommetNonVisite = (int) (Math.random() * res.getV());
			while(visites.contains(sommetNonVisite)) {
				sommetNonVisite = (int) (Math.random() * res.getV());
			}

			//on effectue une marche aleatoire juqu'a un sommet deja visite
			ArrayList<Integer> marcheAleatoireInt = this.marcheAleatoire(res);

			//on supprime les doublons et on creer les aretes
			marcheAleatoireInt = this.supprimerDoublons(marcheAleatoireInt);
			
			//ajout des elements de la marche dans les sommet visites
			for(int i = 0; i < marcheAleatoireInt.size()-1; i++) {
				int elem = marcheAleatoireInt.get(i);
				visites.add(elem);
				
				//ajout de l'arete dans le graphe couvrant
				int elemSuivant = marcheAleatoireInt.get(i+1);
				for (Edge e : res.edges()) {
					if((e.getFrom() == elem && e.getTo() == elemSuivant) ||
							(e.getFrom() == elemSuivant && e.getTo() == elem)) {
						e.setUsed(true);
					}
				}
			}
		}
		return res;
	}

	//methode qui supprime tous les doublons
	private ArrayList<Integer> supprimerDoublons(ArrayList<Integer> liste){

		//premier doublon
		int doublon = this.getDoublon(liste);
		
		//suppressions des doublons jusqu'a ce qu'il n'y en ai plus
		while(doublon != -1) {
			
			ArrayList<Integer> nouvelleListe = new ArrayList<Integer>();
			
			boolean trouverPremierDoublon = false;
			boolean trouverSecondDoublon = false;
			
			for (Integer elem : liste) {
				if(!trouverPremierDoublon) {
					if(elem == doublon) trouverPremierDoublon = true;
					nouvelleListe.add(elem);
				}
				else if(trouverPremierDoublon && !trouverSecondDoublon) {
					if(elem == doublon) trouverSecondDoublon = true;
				}
				else {
					nouvelleListe.add(elem);
				}
			}
			liste = nouvelleListe;

			doublon = this.getDoublon(liste);
		}
		return liste;
	}

	//retourne le premier doublons de la liste, -1 si aucun doublon
	private int getDoublon(ArrayList<Integer> liste) {
		for(int i=0; i<liste.size()-1; i++) {
			int elem = liste.get(i);
			for(int j = liste.size()-1; j>i; j--) {
				if(liste.get(j) == elem) return elem;
			}
		}

		return -1;
	}

	//methode qui execute la marche aleatoire depuis le sommet non visite
	private ArrayList<Integer> marcheAleatoire(Graph g){
		ArrayList<Integer> marcheAleatoire = new ArrayList<Integer>();
		int sommetActuel = sommetNonVisite;
		marcheAleatoire.add(sommetActuel);

		//booleen qui determine si la marche aleatoire contient un sommet visite
		boolean containVisite = false;

		//on continue jusqu'a tombe sur un sommet deja visite
		while(!containVisite) {

			ArrayList<Edge> edgesSommet = g.getAdj()[sommetActuel];
			int edgeAleatoire = (int) (Math.random() * edgesSommet.size());
			Edge aleatoire = edgesSommet.get(edgeAleatoire);
			
			//mise a jour du sommet actuel
			if(aleatoire.getFrom() == sommetActuel) sommetActuel = aleatoire.getTo();
			else sommetActuel = aleatoire.getFrom();
			
			//ajout du sommet dans la liste
			marcheAleatoire.add(sommetActuel);


			//mise a jour du dernier element et du booleen
			containVisite = visites.contains(sommetActuel);
		}
		return marcheAleatoire;
	}

	//le nom de l'algorithme
	public String name() {
		return "Wilson";
	}
}
