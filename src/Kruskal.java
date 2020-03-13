/**
 * File Name: Kruskal.java 
 * Implementation of Kruskal algorithm
 * 
 * To Compile: IntUtil.java RandomInt.java SFSU.java KruskalBase.java Kruskal.java
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Kruskal extends KruskalBase{
	//YOU CAN HAVE ANY NUMBER OF DATA STRUCTURES HERE
	Kruskal() {
		super() ;
		testBench();
	}

	class CLink{
		int start;
		int end;
		double cost;

		//constructor
		public CLink(int s, int e, double c){
			this.start = s;
			this.end = e;
			this.cost = c;
		}
	}

	class MyComparetor implements Comparator<CLink>{
		@Override
		public int compare(CLink l1,  CLink l2) {
			if(l1.cost == l2.cost){
				return 0;
			} else if(l1.cost > l2.cost){
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	@Override
  	String inputFileBase() {
    //Change to directory where U have input graph files
    	return "/Users/meng/Desktop/NEU-Courses/6205Algorithm/HW5/graphexamples/";
  	}



	@Override
	protected double getMSTCost() {
		//WRITE CODE HERE
		inputFileBase();
		String filename = this.d.inFileName;
		//System.out.println(this.d.inFileName);
		double[] totalCost = {0.0};
		int[] numNodes = new int[1];
		PriorityQueue<CLink> minheap = new PriorityQueue<>(new MyComparetor());
		readFile( filename, numNodes, minheap );

		//sort the input links and print out them
		List<CLink> sorted = new ArrayList<>();
		getSorted(minheap, sorted,this.display());

		//System.out.println("numNodes : " + numNodes[0] + " firstnode :" +minheap.peek().start);
		connectNodes(sorted,numNodes, totalCost, this.display());

		System.out.println("MST Cost = " + totalCost[0]);
		return totalCost[0];
	}

	private void readFile(String filename, int[] numNodes, PriorityQueue<CLink> minheap) {
		try {
			BufferedReader input = new BufferedReader( new FileReader( filename ) );

			String curLine;
			while ((curLine = input.readLine()) != null) {
				String[] inputLink = curLine.trim().split( "\\s+" );
				//System.out.println( Arrays.toString(inputNodes));

				if (inputLink.length == 1) continue;

				//first str is the start of current line, and second str is end node, the third one is cost
				int s = Integer.valueOf(inputLink[0]);
				int e = Integer.valueOf(inputLink[1]);
				double c = Double.valueOf(inputLink[2]);

				//System.out.println(s + " " + e + " " + c);
				numNodes[0] = Math.max(numNodes[0], Math.max(s, e));

				//create a new Clink instance and add into the PQ
				CLink cl = new CLink(s, e, c);
				minheap.offer( cl );
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getSorted(PriorityQueue<CLink> minheap, List<CLink> sorted, boolean show){
		if (show) System.out.println("Links after sorting");
		while(!minheap.isEmpty()){
			CLink curLink = minheap.poll();
			if (show) System.out.println(curLink.start + " " + curLink.end + " " + curLink.cost);
			sorted.add(curLink);
		}
	}

	private void connectNodes(List<CLink> sorted, int[] numNodes, double[] totalCost, boolean show){
		SUSF connection = new SUSF(numNodes[0] + 1);
		System.out.println();
		int[] numRoad = {0};
		for(int i = 0; i < sorted.size(); i++){
			CLink curLink = sorted.get(i);
			connect(curLink, connection, totalCost, numRoad, show);
		}

		System.out.println("Num cities = " + numNodes[0]);
		System.out.println("Num Road built = " + numRoad[0]);
		connection.calHeight();
		connection.P();
	}

	private  void connect(CLink curlink, SUSF connection, double[] totalCost, int[] numRoad, boolean show){
		int s = curlink.start;
		int e = curlink.end;
		double curcost = curlink.cost;
		if (show) System.out.print(s + "->" + e + " ");
		if (connection.U(s,e) == false){
			if (show) System.out.println("creates a loop");
		} else {
			numRoad[0]++;
			totalCost[0] += curcost;
			if (show) System.out.println("Total Cost " + totalCost[0]);
		}
	}


	//CANNOT CHANGE ANYTHING BELOW
	public static void main(String[] args) {
		String version = System.getProperty("java.version");
		System.out.println("Java version used for this program is " + version);
		System.out.println("Kruskal problem STARTS");
		Kruskal m = new Kruskal() ;
		System.out.println("All tests passed");
		System.out.println("If you have enjoyed solving this interview problem write a review for me on linkedln");
		System.out.println("Kruskal problem ENDS");
	}
}
