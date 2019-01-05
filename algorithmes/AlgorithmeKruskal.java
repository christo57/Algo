package algorithmes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import structure.Edge;
import structure.Graph;

public class AlgorithmeKruskal implements Algorithme{

	//retourne le graphe de l'algorithme de kruskal
	public Graph executer(Graph g) {
		Graph res = g.copier();

		ArrayList<Edge> aleatoire = this.edgesAleatoire(res);
		
		/**key = numero de sommet
		 * value = partie du graphe
		**/
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int i = 0;
		while(i<=res.getV()) {
			map.put(i, i);
			i++;
		}
		
		for (Edge edge : aleatoire) {
			int valueFrom = map.get(edge.getFrom());
			int valueTo = map.get(edge.getTo());
			
			if(valueFrom != valueTo) {
				edge.setUsed(true);
				
				for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				    if(entry.getValue() == valueTo) entry.setValue(valueFrom);
				}
			}
		}

		return res;
	}
	
	//retourne une liste d'arete aleatoire
	public ArrayList<Edge> edgesAleatoire(Graph g){
		ArrayList<Edge> edges = g.edges();
		int taille = edges.size();

		ArrayList<Edge> aleatoire = new ArrayList<Edge>();
		for(int i=0; i<taille;i++) {
			aleatoire.add(null);
		}

		for (Edge e : edges) {
			int i = (int) (Math.random()* taille);
			while(aleatoire.get(i) != null) {
				i = (int) (Math.random()* taille);
			}
			aleatoire.set(i, e);
		}
		return aleatoire;
	}
	
	//le nom de l'algorithme
	public String name() {
		return "Kruskal";
	}
}
