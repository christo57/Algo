package structure;
import java.util.ArrayList;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class Graph{
	private ArrayList<Edge>[] adj;
	private int[] coordX;
	private int[] coordY;
	private static int V;
	private int E;
	public static final int TAILLE = 30;

	@SuppressWarnings("unchecked")
	public Graph(int N){
		Graph.V = N;
		this.E = 0;
		adj = (ArrayList<Edge>[]) new ArrayList[N];
		for (int v= 0; v < N; v++)
			adj[v] = new ArrayList<Edge>();
		coordX = new int[N];
		coordY = new int[N];
		for (int v= 0; v < N; v++)
			coordX[v] = 0;
		for (int v= 0; v < N; v++)
			coordY[v] = 0;
	}

	public int vertices(){
		return V;
	}

	public void setCoordinate(int i, int x, int y){
		coordX[i] = x;
		coordY[i] = y;
	}


	public void addEdge(Edge e){
		int v = e.getFrom();
		int w = e.getTo();
		adj[v].add(e);
		adj[w].add(e);
	}

	public ArrayList<Edge> adj(int v){
		return new ArrayList<Edge>(adj[v]);
	}

	public ArrayList<Edge> edges(){
		ArrayList<Edge> list = new ArrayList<Edge>();
		for (int v = 0; v < V; v++)
			for (Edge e : adj(v)) {
				if (e.getFrom() == v)
					list.add(e);
			}
		return list;
	}

	public static Graph G0(){
		Graph g = new Graph(7);
		g.setCoordinate(0, 150,50);
		g.setCoordinate(1, 100,100);
		g.setCoordinate(2, 150,100);
		g.setCoordinate(3, 50,150);
		g.setCoordinate(4, 100,150);
		g.setCoordinate(5, 150,150);
		g.setCoordinate(6, 200,150);
		
		g.addEdge(new Edge(0,1));
		g.addEdge(new Edge(0,2));
		g.addEdge(new Edge(1,2));
		g.addEdge(new Edge(0,6));
		g.addEdge(new Edge(1,3));
		g.addEdge(new Edge(1,4));
		g.addEdge(new Edge(2,5));
		g.addEdge(new Edge(2,6));
		g.addEdge(new Edge(3,4));
		g.addEdge(new Edge(4,5));
		g.addEdge(new Edge(5,6));
		
		return g;
	}
	
	public static Graph G1(){
		Graph g = new Graph(4);
		g.setCoordinate(0, 50,50);
		g.setCoordinate(1, 100,50);
		g.setCoordinate(2, 50,100);
		g.setCoordinate(3, 100,100);

		g.addEdge(new Edge(0,1));
		g.addEdge(new Edge(0,2));
		g.addEdge(new Edge(0,3));
		g.addEdge(new Edge(1,3));
		g.addEdge(new Edge(2,3));

		return g;
	}
	
	public static Graph G2() {
		return Grid(20);
	}

	static Graph Grid(int n){
		Graph g = new Graph(n*n);
		int i,j;
		for (i = 0 ; i < n; i ++) 
			for (j = 0 ; j < n; j ++) 
				g.setCoordinate(n*i+j, 25+(1000*i)/n,25+(1000*j)/n);

		for (i = 0 ; i < n; i ++) 
			for (j = 0 ; j < n; j ++){
				if (i < n-1) 
					g.addEdge(new Edge(n*i+j,n*(i+1)+j));
				if (j < n-1) 
					g.addEdge(new Edge(n*i+j,n*i+j+1));
			}
		return g;
	}


	public BufferedImage toImage(){
		BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, 1000, 1000);
		g2d.setColor(Color.BLACK);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		// dessine les arêtes 
		for (Edge e: edges())
		{
			int i = e.getFrom();
			int j = e.getTo();
			if (e.isUsed())
				g2d.setColor(Color.RED);
			else
				g2d.setColor(Color.GRAY);

			g2d.drawLine(coordX[i], coordY[i], coordX[j], coordY[j]);
		}
		// dessine les sommets 
		for (int i = 0; i < V; i++)
		{
			g2d.setColor(Color.WHITE);
			g2d.fillOval(coordX[i]-TAILLE/2, coordY[i]-TAILLE/2,TAILLE,TAILLE);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(coordX[i]-TAILLE/2, coordY[i]-TAILLE/2,TAILLE,TAILLE);
			g2d.drawString(Integer.toString(i), coordX[i], coordY[i]);
		}

		return image;
	}
	
	public BufferedImage toLabyrinthe(){
		BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, 1000, 1000);
		g2d.setColor(Color.BLACK);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		// dessine les arêtes 
		for (Edge e: edges())
		{
			int i = e.getFrom();
			int j = e.getTo();
			if (e.isUsed())
				g2d.setColor(Color.RED);
			else
				g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(10));
			g2d.drawLine(coordX[i], coordY[i], coordX[j], coordY[j]);
		}
		// dessine les sommets 
		for (int i = 0; i < V; i++)
		{
			g2d.setColor(Color.BLACK);
			g2d.fillRect(coordX[i]-TAILLE/2, coordY[i]-TAILLE/2,TAILLE,TAILLE);
			if(i==0) {
				g2d.setColor(Color.WHITE);
				g2d.drawString("Entree", coordX[i]-TAILLE/2, coordY[i]);
			}
			if(i==V-1) {
				g2d.setColor(Color.WHITE);
				g2d.drawString("Sortie", coordX[i]-TAILLE/2, coordY[i]);
			}
		}

		return image;
	}
	
	public void writeFile(String s){
		try{                      
			PrintWriter writer = new PrintWriter(s, "UTF-8");
			writer.println("digraph G{");
			for (Edge e: edges())
				writer.println(e.getFrom() + "->" + e.getTo() +";");
			writer.println("}");
			writer.close();
		}
		catch (IOException e){
		}                                             
	}
	
	
	//getters and setters
	

	public ArrayList<Edge>[] getAdj() {
		return adj;
	}

	public void setAdj(ArrayList<Edge>[] adj) {
		this.adj = adj;
	}

	public int[] getCoordX() {
		return coordX;
	}

	public void setCoordX(int[] coordX) {
		this.coordX = coordX;
	}

	public int[] getCoordY() {
		return coordY;
	}

	public void setCoordY(int[] coordY) {
		this.coordY = coordY;
	}

	public int getE() {
		return E;
	}

	public void setE(int e) {
		E = e;
	}

	public int getV() {
		return V;
	} 
}
