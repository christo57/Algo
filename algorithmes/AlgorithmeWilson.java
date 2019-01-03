package algorithmes;

import java.lang.reflect.Array;
import java.util.ArrayList;

import structure.Edge;
import structure.Graph;

public class AlgorithmeWilson implements Algorithme{

	//sommet de depart de l'algorithme
	private int sommetDeDepart;
	//sommet pas encore visite
	private int sommetNonVisite;
	//list de sommet deja visite
	ArrayList<Integer> visites;

	public AlgorithmeWilson(int sommet) {
		this.sommetDeDepart = sommet;
	}

	public AlgorithmeWilson(){
		this.sommetDeDepart = -1;
	}

	public Graph executer(Graph g) {
		Graph resultat = new Graph(g.getV());
		for (int i=0; i < g.getCoordX().length; i++) {
			resultat.setCoordinate(i, g.getCoordX()[i],  g.getCoordY()[i]);
		}

		visites = new ArrayList<Integer>();

		//au depart, on visite un sommet
		if(sommetDeDepart == -1) {
			sommetDeDepart = (int) (Math.random() * g.getV());
			visites.add(sommetDeDepart);
		}

		//on choisi un sommet non visite
		sommetNonVisite = (int) (Math.random() * g.getV());
		while(visites.contains(sommetNonVisite)) {
			sommetNonVisite = (int) (Math.random() * g.getV());
		}

		//on effectue une marche aleatoire juqu'a un sommet deja visite
		ArrayList<Edge> marcheAleatoire = this.marcheAleatoire(g);

		marcheAleatoire = this.supprimerDoublons(marcheAleatoire);


		return null;
	}
	
	private ArrayList<Edge> supprimerDoublons(ArrayList<Edge> marcheAleatoire){
		for (Edge edge : marcheAleatoire) {
			for(int i = marcheAleatoire.size()-1; i>marcheAleatoire.indexOf(edge); i--) {
				if(marcheAleatoire.get(i) == edge) {
					for(int j = marcheAleatoire.indexOf(edge); j<=i; j++) {
						marcheAleatoire.remove(marcheAleatoire.get(j));
					}
				}
			}
		}
		
		
		return marcheAleatoire;
	}

	//methode qui execute la marche aleatoire depuis le sommet non visite
	private ArrayList<Edge> marcheAleatoire(Graph g){
		ArrayList<Edge> marcheAleatoire = new ArrayList<Edge>();

		//dernier element de la liste de la marche aleatoire
		Edge lastElem;
		//booleen qui determine si la marche aleatoire contient un sommet visite
		boolean containVisite = false;

		//on continue jusqu'a tombe sur un sommet deja visite
		while(marcheAleatoire.size() == 0 || !containVisite) {

			ArrayList<Edge> edgesSommet = g.getAdj()[sommetNonVisite];
			int edgeAleatoire = (int) (Math.random() * edgesSommet.size());
			Edge aleatoire = edgesSommet.get(edgeAleatoire);

			marcheAleatoire.add(aleatoire);


			//mise a jour du dernier element et du booleen
			lastElem = marcheAleatoire.get(marcheAleatoire.size()-1);
			containVisite = visites.contains(lastElem.getFrom()) || visites.contains(lastElem.getTo());

			//mise a jour du prochain sommet a visite
			if(sommetNonVisite == lastElem.getFrom()) sommetNonVisite = lastElem.getTo();
			else sommetNonVisite = lastElem.getFrom();
		}
		return marcheAleatoire;
	}
}
