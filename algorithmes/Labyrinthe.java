package algorithmes;

import java.awt.image.BufferedImage;
import structure.Graph;

public class Labyrinthe {
	
	private Graph base;
	private int size;
	
	public Labyrinthe(Algorithme al) {
		this.size = 20;
		this.base = al.executer(Graph.G2(this.size));
	}
	
	public BufferedImage toImage(){
		return base.toLabyrinthe();
	}
	
	public int chercheSortie() {
		int compteur = 0;
		
		return compteur;
	}

	//getters and setters
	
	public Graph getBase() {
		return base;
	}

	public void setBase(Graph base) {
		this.base = base;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}