package algorithmes;

import java.util.ArrayList;

import structure.Edge;
import structure.Graph;

public class AlgorithmeAldousBroder implements Algorithme{
	
	private int sommetDeDepart;
	
	public AlgorithmeAldousBroder(int sommet) {
		this.sommetDeDepart = sommet;
	}
	
	public AlgorithmeAldousBroder(){
		this.sommetDeDepart = -1;
	}

	public Graph executer(Graph g) {
		Graph resultat = new Graph(g.getV());
		for (int i=0; i < g.getCoordX().length; i++) {
			resultat.setCoordinate(i, g.getCoordX()[i],  g.getCoordY()[i]);
		}
		
		ArrayList<Integer> visites = new ArrayList<Integer>();

		//sommet de depart aleatoire si sommetDeDepart n'est pas parametre
		if(sommetDeDepart == -1) {
			sommetDeDepart = (int) (Math.random() * g.getV());
			visites.add(sommetDeDepart);
		}


		while(visites.size() != g.getV()) {
			//deplacement aleatoire
			ArrayList<Edge> edgesSommet = g.getAdj()[sommetDeDepart];
			int edgeAleatoire = (int) (Math.random() * edgesSommet.size());
			Edge aleatoire = edgesSommet.get(edgeAleatoire);

			//si sommet pas visite, ajout de l'arete
			int autreSommet = aleatoire.getTo();
			if(autreSommet == sommetDeDepart) autreSommet = aleatoire.getFrom();
			
			if(!visites.contains(autreSommet)) {
				visites.add(autreSommet);
				resultat.addEdge(aleatoire);
			}

			sommetDeDepart = autreSommet;
		}
		
		return resultat;
	}
}
