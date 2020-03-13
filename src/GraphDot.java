import java.io.*;


/**
 * File Name: GraphDot.java 
 * Writes graph as a dot file
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019s
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDot.java
 */

class GraphDot{
	private Graph g ;
	private String fname;
	//You can have any number of private variables

	GraphDot(Graph g, String s) {
		this.g = g ;
		this.fname = s ;
		//WRITE CODE
		createDotFile(s);
	}

	private void createDotFile(String filename){
		try {
			File dotFile = new File( filename );

			BufferedWriter input = new BufferedWriter( new FileWriter(dotFile) );

			input.write( "digraph g {" );
			input.newLine();

			String graphType = g.getGraphType();

			if(g.isUndirectedGraph()){
				input.write("edge [dir = none, color = red]" );
			} else{
				input.write("edge [color = red]" );
			}
			input.newLine();

			for (int i = 0; i < g.getnumV(); i++){
				for (int j = 0; j < g.numFanout( i );j++){

					StringBuffer egdeDetail = new StringBuffer("  ");

					if (graphType == "UNDIRECTED GRAPH" && g.getNodeName( g.getNodeFanout( i, j )) < g.getNodeName( i )){
						continue;
					} else if (graphType == "WEIGHTED_UNDIRECTED GRAPH" && g.getNodeName( g.getNodeFanout( i, j )) < g.getNodeName( i )) {
						continue;
					}

					//get the real name of node1 and node2, add into the string buffer
					String node1 = g.getNodeRealName( i );
					String node2 = g.getNodeRealName( g.getNodeFanout( i, j));
					egdeDetail.append( node1 + " -> " + node2);

					//if the graph is weighted, add the weight as the label
					if (graphType == "WEIGHTED_DIRECTED GRAPH" || graphType == "WEIGHTED_UNDIRECTED GRAPH") {
						String w = String.valueOf( g.getNodeFanoutEdgeWeight( i, j ) );
						egdeDetail.append( " [label = " + w +"]" );
					}

					input.write( new String(egdeDetail) );
					input.newLine();
				}

			}

			input.write( "}" );
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		System.out.println("GraphDot.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphDot.java Ends");
	}
}
