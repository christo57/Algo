package algorithmes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import structure.Edge;
import structure.Graph;

public class Labyrinthe {
	private int distance;
	private int impasses;

	private Graph base;
	private ArrayList<Edge> tmp;


	public Labyrinthe(Algorithme al) {
		this.base = al.executer(Graph.G2());
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
		for(int i=0;i<this.base.getV();i++) { //boucle qui supprime les impasses
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
		
		for(Edge e : base.edges()) {
			distance++;
			e.setUsed(true);
		}
		
			for(Edge e : tmp) {
				
					base.addEdge(e);
			}
	}


	//getter
	public Graph getBase() {
		return this.base;
	}
	
	public int getImpasses() {
		return impasses;
	}
	
	public int getDistance() {
		return distance;
	}



}
