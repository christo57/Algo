package algorithmes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import structure.Edge;
import structure.Graph;

public class AlgorithmeKruskal implements Algorithme{

	//retourne le graphe de l'algorithme de kruskal
	public Graph executer(Graph g) {
		Graph graph = new Graph(g.getV());
		for (int i=0; i < g.getCoordX().length; i++) {
			graph.setCoordinate(i, g.getCoordX()[i],  g.getCoordY()[i]);
		}

		ArrayList<Edge> aleatoire = this.edgesAleatoire(g);
		
		/**key = numero de sommet
		 * value = partie du graphe
		**/
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int i = 0;
		while(i<=g.getV()) {
			map.put(i, i);
			i++;
		}
		
		for (Edge edge : aleatoire) {
			int valueFrom = map.get(edge.getFrom());
			int valueTo = map.get(edge.getTo());
			
			if(valueFrom != valueTo) {
				graph.addEdge(edge);
				
				for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				    if(entry.getValue() == valueTo) entry.setValue(valueFrom);
				}
			}
		}

		return graph;
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
