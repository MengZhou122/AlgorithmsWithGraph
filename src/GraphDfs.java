import java.util.Arrays;
import java.util.HashSet;

/**
 * File Name: GraphDfs.java 
 * Depth First Search on a graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDfs.java
 */

class GraphDfs{
	//You can have any number of private variables and private functions
	private String t ;
	private Graph g ;
	String start;
	//Output
	boolean [] cycle;
	int [] work ;
	int [] size ;
	int [] dfsorder;

	HashSet<Integer> blue = new HashSet<>();
	HashSet<Integer> red = new HashSet<>();
	
	GraphDfs(String t, Graph g, String start, boolean [] cycle, int [] work, int [] size, int [] dfsorder) {
		this.t = t ;
		this.g = g ;
		this.start = start;
		this.cycle = cycle ;
		this.work = work ;
		this.size = size ;
		this.dfsorder = dfsorder ;

		//WRITE YOUR CODE
		graphdfs( start, cycle, work, size, dfsorder );
		showdfs();
	}

	private void graphdfs(String start, boolean [] cycle, int [] work, int [] size, int [] dfsorder){

		int node = g.insertOrFind( start, true );

		if (blue.contains(node)){
			cycle[0] = true;
		}
		if (!red.contains(node) && !blue.contains(node)){
			//System.out.println("this node name in int:" + node);
			blue.add(node);
			work[0]++;
			if (g.numFanout(node) == 0) {
				red.add( node );
				dfsorder[size[0]] = node;
				size[0]++;
				blue.remove( node );
				red.add( node );
			} else {
				for(int i = 0; i < g.numFanout(node); i++){
					String nextNode = g.getNodeRealName(g.getNodeFanout(node, i));
					work[0]++;
					graphdfs(nextNode, cycle, work, size, dfsorder);
				}
				//System.out.println(node);
				dfsorder[size[0]] = node;
				size[0]++;
				blue.remove(node);
				red.add(node);

			}

		}
	}
	private void showdfs() {
		System.out.println( "Title : " + t );
		System.out.println( g.getType() );
		System.out.println( "Num Vertices = " + g.getnumV() );
		System.out.println( "Num Edges = " + g.getnumE() );
		System.out.println( "Work done = " + work[0] );

		String graphType = g.getGraphType();
		if (graphType == "UNDIRECTED GRAPH" || graphType == "WEIGHTED_UNDIRECTED GRAPH")
			cycle[0] = g.getnumE() >= g.getnumV() * 2;
		System.out.println( "Has Cycle = " + cycle[0] );

		String[] dfsorderString = dfsorderToString( dfsorder );
		System.out.println( "DFS topological order = " + Arrays.toString( dfsorderString ) );
		System.out.println();
	}

	private String[] dfsorderToString(int[] nums){
		int l = nums.length;
		String[] resl = new String[l];

		for(int i = 0; i < l; i++){
			resl[i] = g.getNodeRealName(nums[l-i-1]);
		}
		return resl;
	}

	public static void main(String[] args) {
		System.out.println("GraphDfs.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphDfs.java Ends");
	}
}
