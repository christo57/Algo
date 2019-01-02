//package graphe;
import java.io.*;


import java.util.*;
public class Test{


	public static void printLaby(Graph G, int size, String file){
		{
			/* suppose que G est une grille de taille size x size et 
           crée un .tex qui contient le labyrinthe correspondant */

			try
			{                      
				PrintWriter writer = new PrintWriter(file, "UTF-8");
				writer.println("\\documentclass{article}\\usepackage{tikz}\\begin{document}");
				writer.println("\\begin{tikzpicture}");

				for (int i = 0; i < size; i++)
					for (int j = 0; j < size; j++)
					{			
						writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , j));
						writer.println("\\draw (0.1,0.1) -- (0.4,0.1);");
						writer.println("\\draw (0.6,0.1) -- (0.9,0.1);");
						writer.println("\\draw (0.1,0.9) -- (0.4,0.9);");
						writer.println("\\draw (0.6,0.9) -- (0.9,0.9);");
						writer.println("\\draw (0.1,0.1) -- (0.1, 0.4);");
						writer.println("\\draw (0.1,0.6) -- (0.1, 0.9);");
						writer.println("\\draw (0.9,0.1) -- (0.9,0.4);");
						writer.println("\\draw (0.9,0.6) -- (0.9,0.9);");
						writer.println("\\end{scope}");
					}

				/* bord */
				for (int i = 0; i < size; i++)
				{
					writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , 0));
					writer.println("\\draw(0.4,0.1) -- (0.6, 0.1);");
					writer.println("\\end{scope}");			
					writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , size-1));
					writer.println("\\draw(0.4,0.9) -- (0.6, 0.9);");
					writer.println("\\end{scope}");
					if (i > 0)
					{
						writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", 0 , i));
						writer.println("\\draw(0.1,0.4) -- (0.1, 0.6);");
						writer.println("\\end{scope}");

					}
					if (i < size - 1)
					{
						writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", size -1 , i));
						writer.println("\\draw(0.9,0.4) -- (0.9, 0.6);");
						writer.println("\\end{scope}");

					}
					writer.println("\\draw (0,0.4) -- (0.1, 0.4);");
					writer.println("\\draw (0,0.6) -- (0.1, 0.6);");
					writer.println(String.format(Locale.US, "\\draw (%d, %d) ++ (0, 0.4)  -- ++ (-0.1, 0); ", size , size -1));
					writer.println(String.format(Locale.US, "\\draw (%d, %d) ++ (0, 0.6)  -- ++ (-0.1, 0); ", size , size -1));

				}


				for (Edge e: G.edges())
				{
					int i = e.from % size;
					int j = e.from / size;
					writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , j));
					if (e.to == e.from + size){
						/* arête verticale */
						if (!e.used)
						{
							writer.println("\\draw (0.4,0.9) -- (0.6,0.9);");
							writer.println("\\draw (0.4,1.1) -- (0.6,1.1);");			    			    
						}
						else
						{
							writer.println("\\draw (0.4,0.9) -- (0.4,1.1);");
							writer.println("\\draw (0.6,0.9) -- (0.6,1.1);");			    			    
						}
					}
					else{
						/* arête horizontale */

						if (!e.used)
						{
							writer.println("\\draw (0.9,0.4) -- (0.9,0.6);");
							writer.println("\\draw (1.1,0.4) -- (1.1,0.6);");			    			    
						}
						else
						{
							writer.println("\\draw (0.9,0.4) -- (1.1,0.4);");
							writer.println("\\draw (0.9,0.6) -- (1.1,0.6);");			    			    
						}
					}
					writer.println("\\end{scope}");
				}
				writer.println("\\end{tikzpicture}");
				writer.println("\\end{document}");
				writer.close();
			}
			catch (IOException e){
			}                                             
		}    



	}	

	//test les 1Millions de possibilit� pour le graphe G1
	public static void main(String[] args) {
		Graph graph1 = Graph.G1();
		Display display1 = new Display("graphe");
		display1.setImage(graph1.toImage());

		HashMap<Graph, Integer> graphes = new HashMap<Graph, Integer>();
		
		for(int i=0; i<1000000; i++) {
			Graph graph2 = graph1.kruskal();
			boolean sameGraph = false;

			for (Map.Entry<Graph, Integer> entryGraphes : graphes.entrySet()) {
				Graph graph = entryGraphes.getKey();
				
				HashMap<Edge, Boolean> edges = new HashMap<Edge, Boolean>();

				//creation de la hashmap
				for (Edge edge : graph.edges()) {
					edges.put(edge, false);
				}

				//comparaison entre les 2 graphes
				for (Edge edge : graph2.edges()) {
					for (Map.Entry<Edge, Boolean> entry : edges.entrySet()) {
					    if(edge.from == entry.getKey().from && edge.to == entry.getKey().to) {
					    	entry.setValue(true);
					    }
					}
				}
				
				if(!edges.containsValue(false)) {
					sameGraph = true;
					graphes.put(graph, graphes.get(graph) + 1);
					break;
				}
			}
			if(!sameGraph) graphes.put(graph2, 1);
		}

		int num = 1;
		for (Map.Entry<Graph, Integer> entryGraphes : graphes.entrySet()) {
			Graph g = entryGraphes.getKey();
			Display d = new Display(String.valueOf(num));
			d.setImage(g.toImage());
			System.out.println(num + " : " + entryGraphes.getValue());
			num++;
		}
	}

	/**
	//test de base
	public static void main(String[] args) {
		int size = 4;
		Graph G = Graph.example();
		Display d = new Display();
		d.setImage(G.toImage());
		System.out.println("appuyez sur une touche");
		new Scanner(System.in).nextLine();
		d.close();
		printLaby(G,size, "toto.tex");


	}
	 **/
} 
