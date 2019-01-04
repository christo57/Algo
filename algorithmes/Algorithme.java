package algorithmes;

import structure.Graph;

public interface Algorithme {
	public Graph executer(Graph g);
	public String name();
}
