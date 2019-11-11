import java.util.ArrayList;

/**
 * File Name: GraphDPShortestPath.java 
 * Implements single source shortest path using dynamic programming
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */


/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDPShortestPath.java
 */

class GraphDPShortestPath{
	//DATA GIVEN
	private String t ; //Title
	private String dotFile; //Write tree as a dot file
	private Graph g ;
	String start;
	String end;
	int [] work ;
	double [] cost ;
	//You can have any number of private variables and private funcions
	
	GraphDPShortestPath(String t, String dotFile, Graph g, String start,String end, int [] work, double [] cost) {
		this.t = t ;
		this.dotFile = dotFile ;
		this.g = g ;
		this.start = start;
		this.end = end ;
		this.work = work ;
		this.cost = cost ;
		
		//WRITE CODE
	}

	public static void main(String[] args) {
		System.out.println("GraphDPShortestPath.java starts");
		System.out.println("Use GraphTest.java to test");
		System.out.println("GraphDPShortestPath.java Ends");
	}
}