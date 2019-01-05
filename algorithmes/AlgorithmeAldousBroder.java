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
		Graph res = g.copier();
		
		ArrayList<Integer> visites = new ArrayList<Integer>();

		//sommet de depart aleatoire si sommetDeDepart n'est pas parametre
		int sommetDeDepart = sommetDeDepartInit;
		if(sommetDeDepart == -1) {
			sommetDeDepart = (int) (Math.random() * g.getV());
		}
		visites.add(sommetDeDepart);


		while(visites.size() != res.getV()) {
			//deplacement aleatoire
			ArrayList<Edge> edgesSommet = res.getAdj()[sommetDeDepart];
			int edgeAleatoire = (int) (Math.random() * edgesSommet.size());
			Edge aleatoire = edgesSommet.get(edgeAleatoire);

			//si sommet pas visite, ajout de l'arete
			int autreSommet = aleatoire.getTo();
			if(autreSommet == sommetDeDepart) autreSommet = aleatoire.getFrom();
			
			if(!visites.contains(autreSommet)) {
				visites.add(autreSommet);
				aleatoire.setUsed(true);
			}

			sommetDeDepart = autreSommet;
		}

		return res;
	}

	//le nom de l'algorithme
	public String name() {
		return "Aldous-Broder";
	}
}
