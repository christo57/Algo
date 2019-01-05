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
		Graph res = Graph.G1();

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
			ArrayList<Edge> marcheAleatoire = this.marcheAleatoire(res);

			//on supprime les doublons
			marcheAleatoire = this.supprimerDoublons(marcheAleatoire,sommetDeDepart);

			for (Edge edge : marcheAleatoire) {
				if(!visites.contains(edge.getFrom())) visites.add(edge.getFrom());
				if(!visites.contains(edge.getTo())) visites.add(edge.getTo());
				edge.setUsed(true);
			}
		}
		return res;
	}

	//methode qui supprime tous les doublons
	private ArrayList<Edge> supprimerDoublons(ArrayList<Edge> marcheAleatoire, int sommetDepart){

		//creation de la liste
		ArrayList<Integer> liste = new ArrayList<Integer>();
		liste.add(this.sommetNonVisite);
		for (Edge edge : marcheAleatoire) {
			if(edge.getFrom() == liste.get(liste.size()-1)) liste.add(edge.getTo());
			else liste.add(edge.getFrom());
		}

		//suppression des doublons
		int doublon = this.getDoublon(liste);
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

		//reconstruction des aretes
		ArrayList<Edge> sansDoublons = new ArrayList<Edge>();
		for(int i=0; i < liste.size()-1; i++) {
			int from = liste.get(i);
			int to = liste.get(i+1);
			
			for (Edge edge : marcheAleatoire) {
				if((edge.getFrom() == from && edge.getTo() == to) ||
						(edge.getFrom() == to && edge.getTo() == from)) {
					sansDoublons.add(edge);
					break;
				}
			}
		}
		
		return sansDoublons;
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
	private ArrayList<Edge> marcheAleatoire(Graph g){
		ArrayList<Edge> marcheAleatoire = new ArrayList<Edge>();
		int sommetActuel = sommetNonVisite;

		//dernier element de la liste de la marche aleatoire
		Edge lastElem;
		//booleen qui determine si la marche aleatoire contient un sommet visite
		boolean containVisite = false;

		//on continue jusqu'a tombe sur un sommet deja visite
		while(marcheAleatoire.size() == 0 || !containVisite) {

			ArrayList<Edge> edgesSommet = g.getAdj()[sommetActuel];
			int edgeAleatoire = (int) (Math.random() * edgesSommet.size());
			Edge aleatoire = edgesSommet.get(edgeAleatoire);

			marcheAleatoire.add(aleatoire);


			//mise a jour du dernier element et du booleen
			lastElem = marcheAleatoire.get(marcheAleatoire.size()-1);
			containVisite = visites.contains(lastElem.getFrom()) || visites.contains(lastElem.getTo());

			//mise a jour du prochain sommet a visite
			if(sommetActuel == lastElem.getFrom()) sommetActuel = lastElem.getTo();
			else sommetActuel = lastElem.getFrom();
		}
		return marcheAleatoire;
	}

	//le nom de l'algorithme
	public String name() {
		return "Wilson";
	}
}
