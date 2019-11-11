import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * File Name: GraphTopologicalSort.java 
 * Topological Sort on a Graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphTopologicalSort.java
 */

class GraphTopologicalSort{
	private String t ;
	private Graph g ;
	//Output
	boolean [] cycle;
	int [] work ;
	int [] size ;
	int [] topoorder;
	//You can have any number of private variables and private funcions
	
	GraphTopologicalSort(String t, Graph g, boolean [] cycle, int [] work, int [] size, int [] topoorder) {
		this.t = t ;
		this.g = g ;
		this.cycle = cycle ;
		this.work = work ;
		this.size = size ;
		this.topoorder = topoorder ;
		
		//WRITE CODE
		for (int i = 0; i < topoorder.length; i++) topoorder[i] = -1;

		getTopoSort(g, work, size, topoorder);
		if (g.getnumV() != size[0]) cycle[0] = true;
		showTopoSort();
		System.out.println();
	}

	private void getTopoSort(Graph g, int [] work, int [] size, int [] topoorder){
		Deque<Integer> queue = new LinkedList<>();

		//fanin number of a node indicates how many rounds the node could be checked in a acyclic graph
		int[] round = new int[g.getnumV()];

		//sweep the nodes, if any of them has no Fanin, means it can be access directly, set as the first of the topoorder
		for (int i = 0; i < g.getnumV();i++){
			round[i] = g.numFanin( i );

			if (g.numFanin( i ) == 0) {
				queue.offerLast( i );
			}
		}

		while (!queue.isEmpty()) {
			int node = queue.pollFirst();

			if (countFanin(g, node, topoorder) != 0) {
				queue.offerLast(node);
				if (--round[node] < 0) break;
				continue;
			}

			topoorder[size[0]] = node;
			size[0]++;
			work[0]++;

			for(int i = 0; i < g.numFanout(node); i++) {
				int nextNode = g.getNodeName( g.getNodeFanout( node, i ) );
				work[0]++;
				if (!queue.contains(nextNode)) queue.offerLast(nextNode);
			}
		}
	}

	private int countFanin(Graph g, int node, int [] topoorder){
		if (g.numFanin(node) == 0) return 0;
		else {
			int count = 0;
			for (int i = 0 ; i < g.numFanin(node); i++){
				int fanIn = g.getNodeFanin(node, i);
				if (ordered(fanIn, topoorder)) continue;
				else count++;
			}
			return count;
		}
	}

	private boolean ordered(int target, int [] array){
		boolean ans = false;
		for (int i = 0 ; i< array.length; i++) {
			if (array[i] == target) return true;
		}
		return ans;
	}

	private void showTopoSort(){
		System.out.println(g.getType());
		System.out.println("Num Vertices = " + g.getnumV());
		System.out.println("Num Edges = " + g.getnumE());
		System.out.println("Work done = " + work[0]);
		arrayToString(topoorder, size);

		if (cycle[0] == true ){
			System.out.println("Topological Order Does not exists. G is not a DAG");
		} else {
			System.out.println("Topological Order exists. G is a DAG");
			checkTopoOrder(g, topoorder);
		}
	}

	private void arrayToString(int[] inp, int[] size){
		int l = inp.length;
		StringBuilder resl = new StringBuilder();
		for(int i = 0; i < size[0]; i++) {
			resl.append( g.getNodeRealName( inp[i] ) + " " );
		}
		System.out.println(resl);
	}

	private void checkTopoOrder(Graph g, int[] topoorder){
		boolean[] pre = new boolean[topoorder.length];

		//create a hashmap for node and its position in the topology order
		for (int i = 0; i < topoorder.length; i++) {
			int node = g.getNodeName( topoorder[i] );
			pre[node] = true;

			for (int j = 0; j < g.numFanin( node ); j++) {
				int fanin = g.getNodeFanin(node,j);
				if (pre[fanin] == false) System.out.println("TopologicalOrder assert not pass");
			}
		}

		System.out.println("TopologicalOrder assert passed");
	}

	public static void main(String[] args) {
		System.out.println("GraphTopologicalSort.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("GraphTopologicalSort.java Ends");
	}
}
