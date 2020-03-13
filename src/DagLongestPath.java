import java.util.ArrayList;
import java.util.List;

/**
 * File Name: DagLongestPath.java 
 * Longest path in a DAG
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphTopologicalSort.java DagLongestPath.java
 */

class DagLongestPath {
	//inputs
	private String t;
	private Graph g;
	//output
	double[] w;
	//You can have any numbers of private variables and function

	DagLongestPath(String t, Graph g, double[] w) {
		this.t = t;
		this.g = g;
		this.w = w;
		//WRITE CODE

		boolean[] cycle = {false};
		int[] topoorder = new int[g.getnumV()];
		int[] work = {0};
		int[] size = {0};
		List<List<Integer>> levels = new ArrayList<>();
		int[] parents = new int[g.getnumV()];
		double[] weights = new double[g.getnumV()];

		g.topologicalSort( t, cycle, work, size, topoorder );
		getLevel(g, topoorder, levels);

		showlevel(levels);
		showTopoOrder(topoorder);

		updateWeight(topoorder, parents, weights, w);
		showParents(parents);
		showDistances(weights);
		showCriticalPath(parents, topoorder);
		System.out.println();
	}

	private void getLevel(Graph g, int[] topoorder, List<List<Integer>> levels) {
		List<Integer> torder= new ArrayList<>();

		//convert the int array to ArrayList
		for(int i = 0; i< topoorder.length; i++) {
			torder.add(topoorder[i]);
		}

		//divide the ArrayList of topoorder by level
		while(!torder.isEmpty()){
			List<Integer> curlevel = new ArrayList<>();
			int asize = torder.size();

			for (int i = 0; i < asize; i++){
				int curnode = torder.get(i);

				if (hasUnorderdFanin(curnode, torder) == false) curlevel.add(curnode);
			}

			levels.add(curlevel);
			for (int curnode : curlevel){
				torder.remove((Integer)curnode);
			}
		}

	}

	private boolean hasUnorderdFanin(int node, List<Integer> torder){
		for (int i = 0; i < g.numFanin(node); i++){
			int faninNode = g.getNodeFanin(node,i);
			if (torder.contains( faninNode )) return true;
		}
		return false;
	}

	private void updateWeight(int[] topoorder, int[] parents, double[] weights, double[] w){
		int l = topoorder.length;
		//update parents and weights one by one
		for(int i = 0; i < l; i++){
			int curnode = topoorder[i];

			int lastNode = curnode;
			double curNodeWeight = 0.0;

			//get the weight of each fanin node and the weight of edge, compare and choose the maximum one
			for(int j = 0; j < g.numFanin(curnode); j++){
				int faninNode = g.getNodeFanin(curnode,j);

				double faninNodeWeight = nodeWeight(faninNode, topoorder, weights);
				double edgeWeight = g.getNodeFanoutEdgeWeightE(faninNode, curnode);

				if (faninNodeWeight + edgeWeight > curNodeWeight){
					lastNode = faninNode;
					curNodeWeight = faninNodeWeight + edgeWeight;
				}
			}
			parents[i] = lastNode;
			weights[i] = curNodeWeight;
		}

		//update total weight of the longest path
		w[0] = weights[l-1];
	}

	private double nodeWeight(int node, int[] topoorder, double[] weights){
		double resl = 0.0;
		for(int i = 0; i < topoorder.length; i++){
			if (topoorder[i] == node) {
				resl = weights[i];
				break;
			}
		}
		return resl;
	}

	private void showlevel(List<List<Integer>> levels){
		//show levels
		System.out.println("Levels are as follows:");
		for(int level = 0; level < levels.size(); level++){
			List<Integer> curlevel = levels.get(level);
			for (int i = 0; i < curlevel.size(); i++) {
				System.out.print(level + " ");
			}
		}
		System.out.println();

		//show actions
		System.out.println("You can execute the following actions:");
		for(int level = 0; level < levels.size(); level++){
			System.out.print("Action " + (level+1) + " : ");
			List<Integer> curlevel = levels.get(level);
			for (int i = 0; i < curlevel.size(); i++) {
				int curnode = curlevel.get(i);
				System.out.print(g.getNodeRealName( curnode ) + " ");
			}
			System.out.println();
		}

		System.out.println("It takes " + levels.size() + " steps to finish the task");
	}

	private void showTopoOrder(int[] topoorder){
		System.out.println("Topological nodes are as follows:");
		for (int i = 0; i < topoorder.length; i++) System.out.print(g.getNodeRealName(topoorder[i])+ " ");
		System.out.println();
	}

	private void showParents(int[] parents){
		System.out.println("Parents are as follows:");
		for (int i = 0; i < parents.length; i++) System.out.print(g.getNodeRealName(parents[i]) + " ");
		System.out.println();
	}

	private void showDistances(double[] weights){
		int l = weights.length;
		System.out.println("Distances are as follows:");
		for (int i = 0; i < l; i++) System.out.print(weights[i] + " ");
		System.out.println();
		System.out.println("It takes "+ weights[l-1]+" to finish.");
	}

	private void showCriticalPath(int[] parents,int[] topoorder) {
		List<Integer> longestPath = getLP(parents,topoorder);

		StringBuilder critical = new StringBuilder();
		int i = 0;
		for(; i < longestPath.size()-1; i++){
			int node = longestPath.get(i);
			critical.append(g.getNodeRealName(node) + "->");
		}
		critical.append(g.getNodeRealName(longestPath.get(i)));

		System.out.println("The critical path is as follows:");
		System.out.println(critical);
	}

	private List<Integer> getLP(int[] parents,int[] topoorder){
		List<Integer> longestPath = new ArrayList<>();
		int l = parents.length;

		int node = topoorder[l-1];
		longestPath.add(node);

		while (node != topoorder[0]){
			int index = nodeIndex(node,topoorder);
			node = parents[index];
			longestPath.add(0, node);
		}
		return longestPath;
	}

	private int nodeIndex(int node,int[] topoorder){
		int index = 0;
		for(int i = 0; i < topoorder.length; i++){
			if(topoorder[i] == node){
				index = i;
				break;
			}
		}
		return index;
	}

	public static void main(String[] args) {
		System.out.println("DagLongestPath.java starts");
		System.out.println("Use GraphTester.java to test");
		System.out.println("DagLongestPath.java Ends");
	}
}
