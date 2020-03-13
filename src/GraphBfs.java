import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;


/**
 * File Name: GraphBfs.java 
 * Breadth First Search on a graph
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphBfs.java
 */

class GraphBfs{
	private String t ;
	private Graph g ;
	String start;
	//Output
	private int [] work ;
	private int [] size ;
	private int [] bfsorder;
	private int [] bfspath ;
	//You can have any number of private variables and private funcions
	
	GraphBfs(String t, Graph g, String start, int [] work, int[] size, int [] bfsorder, int [] bfspath) {
		this.t = t ;
		this.g = g ;
		this.start = start;
		this.work = work ;
		this.size = size ;
		this.bfsorder = bfsorder ;
		this.bfspath = bfspath ;
    	//WRITE YOUR CODE

		//set default value of bfsprder and bfspath as -1
		for (int i = 0; i < bfsorder.length; i++){
			bfsorder[i] = -1;
			bfspath[i] = -1;
		}
		graphbfs(start, work, size, bfsorder, bfspath);
		showBSF();
	}

	private void graphbfs(String start, int [] work, int[] size, int [] bfsorder, int [] bfspath){
		int snode = g.insertOrFind( start, true );
		int l = bfsorder.length;
		boolean[] ordered = new boolean[l];

		bfsorder[size[0]] = snode;
		bfspath[size[0]] = snode;
		ordered[snode] = true;
		size[0]++;

		Deque<Integer> queue = new LinkedList<>();
		queue.offerLast( snode );
		work[0]++;

		while (!queue.isEmpty()){
			int node = queue.pollFirst();

			for(int i = 0; i < g.numFanout(node); i++){
				int nextNode = g.getNodeName(g.getNodeFanout(node, i));
				work[0]++;

				if (ordered[nextNode]){
					continue;
				} else {
					bfsorder[size[0]] = nextNode;
					bfspath[size[0]] = node;
					ordered[nextNode] = true;
					size[0]++;
					work[0]++;
					queue.offerLast( nextNode );
				}
			}
		}
	}


	private void showBSF(){
		System.out.println("Title : " + t);
		System.out.println(g.getType());
		System.out.println("Num Vertices = " + g.getnumV());
		System.out.println("Num Edges = " + g.getnumE());
		System.out.println("Work done = " + work[0]);
		arrayToString( bfsorder , size);
		arrayToString( bfspath , size);
		printAllPaths();
		System.out.println();
	}

	private void arrayToString(int[] inp, int [] size){
		int l = inp.length;
		StringBuilder resl = new StringBuilder();
		for(int i = 0; i < size[0]; i++) {
			resl.append( g.getNodeRealName( inp[i] ) + " " );
		}
		System.out.println(resl);
	}

	private void printAllPaths(){
		for (int i : bfsorder) printPath(i);
	}

	private void printPath(int i){
		StringBuilder path = new StringBuilder();
		int start = bfsorder[0];
		path = getPath(i, start, path);
		System.out.println(path);
	}

	private StringBuilder getPath(int i, int start,StringBuilder path) {
		path.insert(0, g.getNodeRealName(i));
		if (i == start) return path;

		path.insert( 0, "->" );
		int index = 0;
		for (; index < bfsorder.length; index++) {
			if (bfsorder[index] == i) break;
		}
		getPath( bfspath[index], start, path );
		return path;
	}

	public static void main(String[] args) {
		System.out.println("GraphBfs.java starts");
		System.out.println("Use GraphTest.java to test");
		System.out.println("GraphBfs.java Ends");
	}
}
