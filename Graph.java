import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

class Graph{
	private ArrayList<Edge>[] adj;
	private int[] coordX;
	private int[] coordY;
	private final int V;
	private int E;

	@SuppressWarnings("unchecked")
	public Graph(int N){
		this.V = N;
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
		int v = e.from;
		int w = e.to;
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
				if (e.from == v)
					list.add(e);
			}
		return list;
	}

	static Graph G0(){
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
	
	static Graph G1(){
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
	
	

	public ArrayList<Edge> edgesAleatoire(){
		ArrayList<Edge> edges = this.edges();
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

	public Graph kruskal() {
		Graph graph = new Graph(this.V);
		for (int i=0; i<this.coordX.length; i++) {
			graph.setCoordinate(i, this.coordX[i], this.coordY[i]);
		}

		ArrayList<Edge> aleatoire = this.edgesAleatoire();
		
		/**key = numero de sommet
		 * value = partie du graphe
		**/
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int i = 0;
		while(i<=V) {
			map.put(i, i);
			i++;
		}
		
		for (Edge edge : aleatoire) {
			int valueFrom = map.get(edge.from);
			int valueTo = map.get(edge.to);
			
			if(valueFrom != valueTo) {
				graph.addEdge(edge);
				
				for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				    if(entry.getValue() == valueTo) entry.setValue(valueFrom);
				}
			}
		}

		return graph;
	}


	static Graph Grid(int n){
		Graph g = new Graph(n*n);
		int i,j;
		for (i = 0 ; i < n; i ++) 
			for (j = 0 ; j < n; j ++) 
				g.setCoordinate(n*i+j, 50+(300*i)/n,50+(300*j)/n);

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
		BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, 400, 400);
		g2d.setColor(Color.BLACK);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		// dessine les arêtes 
		for (Edge e: edges())
		{
			int i = e.from;
			int j = e.to;
			if (e.used)
				g2d.setColor(Color.RED);
			else
				g2d.setColor(Color.GRAY);

			g2d.drawLine(coordX[i], coordY[i], coordX[j], coordY[j]);
		}
		// dessine les sommets 
		for (int i = 0; i < V; i++)
		{
			g2d.setColor(Color.WHITE);
			g2d.fillOval(coordX[i]-15, coordY[i]-15,30,30);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(coordX[i]-15, coordY[i]-15,30,30);
			g2d.drawString(Integer.toString(i), coordX[i], coordY[i]);
		}

		return image;
	}


	public void writeFile(String s){
		try{                      
			PrintWriter writer = new PrintWriter(s, "UTF-8");
			writer.println("digraph G{");
			for (Edge e: edges())
				writer.println(e.from + "->" + e.to+";");
			writer.println("}");
			writer.close();
		}
		catch (IOException e){
		}                                             
	}    
}
