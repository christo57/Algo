package algorithmes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import structure.Edge;
import structure.Graph;

public class Labyrinthe {
	private int distance;
	private int impasses;
	private ArrayList<Edge> tmp;
	private Graph base;
	private int size;


	public Labyrinthe(Algorithme al) {
		this.size = 20;
		this.base = al.executer(Graph.G2(this.size));
		for(Edge e : base.edges()) { //on selectionne seulement les arrete de l'arbre couvrant et on met used a false
			if(!e.isUsed())
				base.supprimerEdge(e);
			e.setUsed(false);
		}
		this.tmp = new ArrayList<Edge>();
		distance = 0;
		impasses = 0;
		base.setEntree(0);
		base.setSortie(base.getV()-1);
	}

	public BufferedImage toImage(){
		return base.toLabyrinthe();
	}

	public void chercheSortie() {
		boolean b = false;
		for(int i=0;i<this.base.getV();i++) { //boucle qui supprime les premieres impasses et les compte
			if(base.adj(i).size()==1){
				if( i!=base.getEntree() && i!=base.getSortie() ){
					tmp.add(base.adj(i).get(0));
					base.supprimerEdge(base.adj(i).get(0));
					impasses++;
				}
			}
		}
		while(!b) {
			b=true;
			for(int i=0;i<this.base.getV();i++) { //boucle qui verifie s'il retse des impasses
				if(base.adj(i).size()==1){
					if( i!=base.getEntree() && i!=base.getSortie() ){
						b=false;
					}
				}

			}						

			for(int i=0;i<this.base.getV();i++) { //boucle qui supprime les impasses
				if(base.adj(i).size()==1){
					if( i!=base.getEntree() && i!=base.getSortie() ){
						tmp.add(base.adj(i).get(0));
						base.supprimerEdge(base.adj(i).get(0));

					}
				}
			}
		}

			for(Edge e : base.edges()) {//on marque le chemin de l'entree vers la sortie en true
				distance++;
				e.setUsed(true);
			}

			for(Edge e : tmp) {	//on remet toutes les arretes
				base.addEdge(e);
			}
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

		public int getImpasses() {
			return impasses;
		}

		public int getDistance() {
			return distance;
		}
	}