package algorithmes;

import java.util.ArrayList;

import structure.Edge;
import structure.Graph;

public class AlgorithmeAldousBroder implements Algorithme{

	public Graph executer(Graph g) {
		Graph resultat = new Graph(g.getV());
		for (int i=0; i < g.getCoordX().length; i++) {
			resultat.setCoordinate(i, g.getCoordX()[i],  g.getCoordY()[i]);
		}
		
		ArrayList<Integer> visites = new ArrayList<Integer>();

		//sommet de depart aleatoire
		int sommet = (int) (Math.random() * g.getV());
		visites.add(sommet);

		while(visites.size() != g.getV()) {
			//deplacement aleatoire
			ArrayList<Edge> edgesSommet = g.getAdj()[sommet];
			int edgeAleatoire = (int) (Math.random() * edgesSommet.size());
			Edge aleatoire = edgesSommet.get(edgeAleatoire);

			//si sommet pas visite, ajout de l'arete
			int autreSommet = aleatoire.getTo();
			if(autreSommet == sommet) autreSommet = aleatoire.getFrom();
			
			if(!visites.contains(autreSommet)) {
				visites.add(autreSommet);
				resultat.addEdge(aleatoire);
			}

			sommet = autreSommet;
		}
		
		return resultat;
	}
}
