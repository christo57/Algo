package algorithmes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import structure.Edge;
import structure.Graph;

public class Labyrinthe {
	
	private Graph base;
	
	public Labyrinthe(Algorithme al) {
		this.base = al.executer(Graph.G2());
	}
	
	public BufferedImage toImage(){
		return base.toLabyrinthe();
	}
	
	public int chercheSortie() {
		int compteur = 0;
		
		return compteur;
	}
	
	
	
	
	
}
