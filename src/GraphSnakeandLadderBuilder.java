import java.util.HashMap;

/**
 * File Name: GraphSnakeandLadderBuilder.java 
 * All routines that builds SnakeandLadder Graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2018
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphSnakeandLadderBuilder.java
 */

class GraphSnakeandLadderBuilder{
	//given data
	private Graph g ;
	private int n ; //Max number on board
	private int[][] l; //ladder
	private int[][] s; //snakes
	//You can have any number of private variables
	private HashMap<Integer, Integer> lsmap;
	
	
	GraphSnakeandLadderBuilder(Graph g, int n, int[][] l, int [][] s) {
		this.g = g ;
		this.n = n ;
		this.l = l ;
		this.s = s;
		buildGraph(n) ;
	}
	
	private void buildGraph(int n) {
		//WRITE YOUR CODE
		lsmap = getmap(l, s); //create the mapping of ladders and snakes

		//create node for each square
		for (int sq = 1; sq <= n; sq++) g.insertOrFind( String.valueOf(sq), false);

		//connect all nodes
		for (int sq = 1; sq <= n; sq++) connectNode(sq, n);
	}

	private void connectNode(int cur, int max){
		for(int dice = 1; dice < 7; dice++){
			int target = cur + dice;
			if (target > max) return;
			if (lsmap.containsKey( target )) target = lsmap.get( target );

			int node1 = g.graphHasNode(String.valueOf(cur));
			int node2 = g.graphHasNode(String.valueOf(target));

			g.createEdge( node1, node2, 0,true);
			g.createEdge( node2, node1, 0,false);
		}
		return;
	}

	private HashMap<Integer, Integer> getmap(int[][] l, int [][] s ){
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int[] pair: l) {
			if (pair[0] < pair[1]){
				map.put(pair[0], pair[1]);
			} else {
				map.put(pair[1], pair[0]);
			}
		}
		for (int[] pair: s) {
			if (pair[0] > pair[1]){
				map.put(pair[0], pair[1]);
			} else {
				map.put(pair[1], pair[0]);
			}
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println("GraphSnakeandLadderBuilder starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphSnakeandLadderBuilder Ends");
	}
}
