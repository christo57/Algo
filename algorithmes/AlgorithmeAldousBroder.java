package algorithmes;

import java.util.ArrayList;

import structure.Edge;
import structure.Graph;

public class AlgorithmeAldousBroder implements Algorithme{
	
	private int sommetDeDepartInit;
	
	public AlgorithmeAldousBroder(int sommet) {
		this.sommetDeDepartInit = sommet;
	}
	
	public AlgorithmeAldousBroder(){
		this.sommetDeDepartInit = -1;
	}

	public Graph executer(Graph g) {
		Graph resultat = new Graph(g.getV());
		for (int i=0; i < g.getCoordX().length; i++) {
			resultat.setCoordinate(i, g.getCoordX()[i],  g.getCoordY()[i]);
		}
		
		ArrayList<Integer> visites = new ArrayList<Integer>();

		//sommet de depart aleatoire si sommetDeDepart n'est pas parametre
		int sommetDeDepart = sommetDeDepartInit;
		if(sommetDeDepart == -1) {
			sommetDeDepart = (int) (Math.random() * g.getV());
		}
		visites.add(sommetDeDepart);


		while(visites.size() != g.getV()) {
			//deplacement aleatoire
			ArrayList<Edge> edgesSommet = g.getAdj()[sommetDeDepart];
			int edgeAleatoire = (int) (Math.random() * edgesSommet.size());
			Edge aleatoire = edgesSommet.get(edgeAleatoire);

			//si sommet pas visite, ajout de l'arete
			if(aleatoire.getTo() == sommetDeDepart && aleatoire.getFrom() == sommetDeDepart) System.out.println("tous egaux");
			if(aleatoire.getTo() != sommetDeDepart && aleatoire.getFrom() != sommetDeDepart) System.out.println("tous different");
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

	//le nom de l'algorithme
	public String name() {
		return "Aldous-Broder";
	}
}
