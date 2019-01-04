import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import algorithmes.Algorithme;
import algorithmes.AlgorithmeAldousBroder;
import algorithmes.AlgorithmeKruskal;
import algorithmes.AlgorithmeWilson;
import algorithmes.Labyrinthe;
import structure.Display;
import structure.Edge;
import structure.Graph;

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
					int i = e.getFrom() % size;
					int j = e.getFrom() / size;
					writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i , j));
					if (e.getTo() == e.getFrom() + size){
						/* arête verticale */
						if (!e.isUsed())
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

						if (!e.isUsed())
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

	//methode lancer par le programme
	public static void main(String[] args) {
		int occurence = 1000000;
		Graph graph1 = Graph.G1();
		Display display1 = new Display("graphe");
		display1.setImage(graph1.toImage());
		JFrame choix = new JFrame("Choix");
		JButton krusk = new JButton("Algorithme de Kruskal");
		krusk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainAlgo1Million(new AlgorithmeKruskal(), graph1, occurence);

			}
		});
		JButton ald = new JButton("Algorithme d'Aldous-Broder");
		ald.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainAlgo1Million(new AlgorithmeAldousBroder(), graph1, occurence);				
			}
		});
		JButton wil = new JButton("Algorithme de Wilson");
		wil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainAlgo1Million(new AlgorithmeWilson(), graph1, occurence);				
			}
		});

		JButton labWil = new JButton("Creation Labyrinthe avec algorithme d'Aldous-Broder");
		labWil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainLabyrinthe(new AlgorithmeAldousBroder());
			}
		});
		JButton labKrusk = new JButton("Creation Labyrinthe avec algorithme de Kruskal");
		labKrusk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainLabyrinthe(new AlgorithmeKruskal());

			}
		});
		JPanel pane = new JPanel();
		pane.add(krusk);
		pane.add(wil);
		pane.add(ald);
		pane.add(labKrusk);
		pane.add(labWil);
		choix.add(pane);
		choix.pack();
		choix.setVisible(true);
		//Q6 |		mainAlgo1Million(new AlgorithmeWilson(), graph1);

		//Q5 |		mainAlgo1Million(new AlgorithmeAldousBroder(), graph1);

		//Q4 |		mainAlgo1Million(new AlgorithmeKruskal(), graph1);


		//main de base |	mainDeBase();

	}

	//test les 1Millions de possibilit� pour le graphe G1 avec un algorithme donne
	public static void mainAlgo1Million(Algorithme algo,Graph graph1,int occurence){
		System.out.println("execution de l'algorithme "+ algo.name() + " sur " + occurence + " occurence :");
		long debut = System.currentTimeMillis();
		HashMap<Graph, Integer> graphes = new HashMap<Graph, Integer>();

		for(int i=0; i<occurence; i++) {
			//System.out.println(i);
			Graph graph2 = algo.executer(graph1);
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
						if(edge.getFrom() == entry.getKey().getFrom() &&
								edge.getTo() == entry.getKey().getTo()) {
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
			if(!sameGraph)	graphes.put(graph2, 1);
		}

		int num = 1;
		for (Map.Entry<Graph, Integer> entryGraphes : graphes.entrySet()) {
			Graph g = entryGraphes.getKey();
			Display d = new Display(String.valueOf(num));
			d.setImage(g.toImage());
			System.out.println("grpahe " + num + " : " + entryGraphes.getValue() + " occurences");
			num++;
		}
		

		//affiche la duree d'execution en millisecondes
		System.out.println("temps en millsecondes de l'execution : " + (System.currentTimeMillis()-debut));
		System.out.println();
	}



	//test de base
	public static void mainLabyrinthe(Algorithme al) {
		Labyrinthe l = new Labyrinthe(al);
		Display display1 = new Display("graphe");
		display1.setImage(l.toImage());

	}
} 
