import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * File Name: GraphBuilder.java 
 * All routines that builds Graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphBuilder.java
 */

class GraphBuilder{
	private Graph g ;
	//You can have any number of private variables and private functions
	
	GraphBuilder(Graph g, String f) {
		this.g = g ;
		//WRITE YOUR CODE
		createGraph( f );
	}

	private void createGraph(String filename) {
		//input is the path of the txt document and the name of the file, we should read the content of the file

		try {
			BufferedReader input = new BufferedReader( new FileReader(filename) );

			String curLine;
			while((curLine = input.readLine()) != null){
				String[] inputNodes = curLine.trim().split( "\\s+" );
				//System.out.println( Arrays.toString(inputNodes));

				if(inputNodes.length == 1) continue;

				//first str is the node1 of current line, and second str is node2
				//if the length of inoutNodes is three, then the third one is weight info


				//get node num of two input nodes; if they are not in the hashmap, insert into the node array and hashmap
				int node1 = g.graphHasNode(inputNodes[0]);
				if (node1 == -1) node1 = g.insertOrFind( inputNodes[0], false );

				int node2 = g.graphHasNode(inputNodes[1]);
				if (node2  == -1) node2 = g.insertOrFind( inputNodes[1],false);

				//set default weight value w as 1; if found weight, assign it to w
				double w = inputNodes.length == 3 ? Double.valueOf( inputNodes[2] ): 1;

				//add the edge info as fanout for node1 and fanin for node 2
				g.createEdge( node1, node2, w,true);
				g.createEdge( node2, node1, w,false);

				//if the graph is undirected, add the edge info as fanout for node2 and fanin for node 1
				if(g.isUndirectedGraph()){
					g.createEdge( node1, node2, w,false);
					g.createEdge( node2, node1, w,true);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void main(String[] args) {
		System.out.println("GraphBuilder.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphBuilder.java Ends");
	}
}
