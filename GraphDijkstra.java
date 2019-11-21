import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * File Name: GraphDijkstra.java 
 * Implements Dijkstra's algorithms
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDijkstra.java
 */

class GraphDijkstra{
	//You can have any number of private variables and private functions
	//DATA GIVEN
	private String t ; //Title
	private String dotFile; //Write tree as a dot file
	private Graph g ;
	String start;
	String end;
	int[] work ;
	double[] cost ;

	class nodeCost{
		private int node;
		private double cost;
		private boolean ordered;
		private int parent;

		public nodeCost(int node){
			this.node = node;
			this.cost = Double.MAX_VALUE;
			this.ordered = false;
			this.parent = node;
		}

		public int getNode() { return this.node; }

		public double getCost(){ return this.cost; }

		public boolean checkOrdered(){ return this.ordered; }

		public void updateOrdered(){ this.ordered = true; }

		public int getParent(){ return this.parent; }

		public void updateParent(int newparent){ this.parent = newparent; }

		public void updateCost(double newcost){ this.cost = newcost; }
	}

	public class MyComparator implements Comparator<nodeCost> {
		@Override
		public int compare(GraphDijkstra.nodeCost n1, nodeCost n2) {
			double nw1 = n1.getCost();
			double nw2 = n2.getCost();
			if (nw1 == nw2) return 0;
			else if (nw1 > nw2) return 1;
			else return -1;
		}
	}

	GraphDijkstra(String t, String dotFile, Graph g, String start,String end, int[] work, double[] cost) {
		this.t = t ;
		this.dotFile = dotFile ;
		this.g = g ;
		this.start = start;
		this.end = end ;
		this.work = work ;
		this.cost = cost ;
		//WRITE YOUR CODE

		System.out.println("------" + t + "------");

		//reset all need arrays to default value
		int[] bfsorder = new int[g.getnumV()];
		nodeCost[] ncOrder = new nodeCost[g.getnumV()];

		//get the bfs order of the node
		updateBFSOrder(start, bfsorder, ncOrder);

		//create a heap and keep pop out the node with lowest cost
		int[] countin = {0};
		implementGDijkstra(start, dotFile, work, bfsorder, ncOrder, countin);
		System.out.println();

		showPaths(bfsorder, ncOrder);
		System.out.println();

		int endnode = g.insertOrFind(end ,true);
		nodeCost endnc = nodemap( endnode, bfsorder, ncOrder );
		cost[0]= endnc.getCost();
		showStatic(start,end, work, cost,countin);
		System.out.println();

	}

	private void updateBFSOrder(String start, int[] bfsorder, nodeCost[] nwBFSOrder){
		boolean[] ordered = new boolean[g.getnumV()];
		int snode = g.insertOrFind( start, true );
		int index = 0;

		bfsorder[index] = snode;
		nwBFSOrder[index] = new nodeCost(snode);
		ordered[snode] = true;
		index++;

		Deque<Integer> queue = new LinkedList<>();
		queue.offerLast(0);

		while (!queue.isEmpty()){
			int node = queue.pollFirst();

			for(int i = 0; i < g.numFanout(node); i++){
				int nextNode = g.getNodeName(g.getNodeFanout(node, i));

				if (ordered[nextNode]){
					continue;
				} else {
					bfsorder[index] = nextNode;
					nwBFSOrder[index] = new nodeCost(nextNode);
					ordered[nextNode] = true;
					index++;
					queue.offerLast( nextNode );
				}
			}
		}
	}

	private void implementGDijkstra(String start, String filename, int[] work, int[] bfsorder, nodeCost[] nodeCosts, int[] countin){

		try {
			File dotFile = new File( filename );
			BufferedWriter input = new BufferedWriter( new FileWriter( dotFile ) );

			input.write( "digraph g {" );
			input.newLine();

			if (g.isUndirectedGraph()) {
				input.write( "edge [dir = none, color = black]" );
			} else {
				input.write( "edge [color = black]" );
			}
			input.newLine();

			//set the cost of start node as 0
			int snode = g.insertOrFind( start, true );
			nodemap( snode, bfsorder, nodeCosts ).updateCost( 0.0 );

			PriorityQueue<nodeCost> minheap = new PriorityQueue<>( g.getnumV(), new MyComparator() );

			for (nodeCost nc : nodeCosts) {
				minheap.offer( nc );
			}
			countin[0] += g.getnumV();

			showGDijkstra( nodeCosts );

			while (!minheap.isEmpty()) {
				nodeCost cur = minheap.poll();
				if (cur.checkOrdered()) {
					minheap.poll();
					continue;
				}

				int curnode = cur.getNode();
				System.out.println( "Working on Vertex: " + g.getNodeRealName( cur.getNode() ) );
				//System.out.println( "cost: " + cur.getCost() );

				for (int i = 0; i < g.numFanout( curnode ); i++) {
					work[0]++;
					int outnode = g.getNodeFanout( curnode, i );
					double curcost = cur.getCost() + g.getNodeFanoutEdgeWeight( curnode, i );
					if (curcost < nodemap( outnode, bfsorder, nodeCosts ).getCost()) {
						nodemap( outnode, bfsorder, nodeCosts ).updateCost( curcost );
						nodemap( outnode, bfsorder, nodeCosts ).updateParent( curnode );
						minheap.remove( nodemap( outnode, bfsorder, nodeCosts ) );
						minheap.offer( nodemap( outnode, bfsorder, nodeCosts ) );
						input.write(addNewLine(nodemap( outnode, bfsorder, nodeCosts)));
						input.newLine();
//					countin[0]++;
					}
					countin[0]++;
				}
				nodemap( curnode, bfsorder, nodeCosts ).updateOrdered();
				showGDijkstra( nodeCosts );
			}
			input.write( "}" );
			input.close();

		} catch (IOException e) {
		e.printStackTrace();
		}
	}

	private String addNewLine(nodeCost nc){
		StringBuffer egdeDetail = new StringBuffer("  ");
		String node1 = g.getNodeRealName(nc.getParent());
		String node2 = g.getNodeRealName(nc.getNode());
		double weight = nc.getCost();
		egdeDetail.append( node1 + " -> " + node2);
		egdeDetail.append( " [label = " + weight +"]" );
		return egdeDetail.toString();
	}

	private nodeCost nodemap(int node, int[] bfsorder, nodeCost[] nodeCosts){
		for (int i = 0; i < g.getnumV(); i++){
			if(bfsorder[i] == node){
				return nodeCosts[i];
			}
		}
		return new nodeCost(100000);
	}

	private void showGDijkstra(nodeCost[] nodeCosts){
		printBFSOrder(nodeCosts);
		printVisited(nodeCosts);
		printCosts(nodeCosts);
		printParent(nodeCosts);
	}

	private void printBFSOrder(nodeCost[] nodeCosts){
		for (nodeCost i : nodeCosts) System.out.print(g.getNodeRealName(i.getNode()) + "  ");
		System.out.println();
	}

	private void printVisited(nodeCost[] nodeCosts){
		for (nodeCost i : nodeCosts) {
			if(i.checkOrdered()) System.out.print("T  ");
			else System.out.print("F  ");
		}
		System.out.println();
	}

	private void printCosts(nodeCost[] nodeCosts){
		for (nodeCost i : nodeCosts) {
			if (i.getCost() == Double.MAX_VALUE) System.out.print("L  ");
			else System.out.print(i.getCost() + " ");
		}
		System.out.println();
	}

	private void printParent(nodeCost[] nodeCosts){
		for (nodeCost i : nodeCosts) System.out.print(g.getNodeRealName(i.getParent()) + "  ");
		System.out.println();
	}

	private void showPaths(int[] bfsorder, nodeCost[] ncOrder){
		for (nodeCost nc: ncOrder) {
			int snode = g.insertOrFind(start, true);
			if (nc.getNode() == snode) continue;
			System.out.println( "The best way to go from " + start + " to city " + g.getNodeRealName(nc.getNode())+ " is follows" );
			showpath(nc,bfsorder, ncOrder);
		}
	}

	private void showpath(nodeCost dist,int[] bfsorder, nodeCost[] ncOrder){
		int snode = g.insertOrFind(start, true);
		StringBuilder path = new StringBuilder();
		StringBuilder pathcost = new StringBuilder();

		nodeCost temp = dist;
		while(temp.getNode() != snode){
			path.insert(0, " -> " + g.getNodeRealName(temp.getNode()));
			double thiscost = temp.getCost();
			temp = nodemap(temp.getParent(), bfsorder, ncOrder);
			double lastcost = temp.getCost();
			pathcost.insert(0, " + " + (thiscost - lastcost));
		}

		path.insert(0, start);
		pathcost.replace( 0,2, "Cost =" );
		//pathcost.insert(0, "Cost = ");
		pathcost.append(" = " +dist.getCost());
		System.out.println(path + " " + pathcost);
	}

	private void showStatic( String start,String end, int[] work, double[] cost, int[] countin){
		System.out.println(g.getType() + "GRAPH");
		System.out.println("Num Vertices = " + g.getnumV());
		System.out.println("Num Edges = " + g.getnumE());
		System.out.println("Work done = " + work[0]);
		System.out.println("number of Node Added to heap " + countin[0]);
		System.out.println("Shortest path form city " + start + " to " + end + " = " + cost[0]);
	}
	
	public static void main(String[] args) {
		System.out.println("GraphDijkstra.java starts");
		System.out.println("Use GraphTest.java to test");
		System.out.println("GraphDijkstra.java Ends");
	}
}