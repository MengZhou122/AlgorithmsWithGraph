import java.util.ArrayList;
import java.util.HashMap;

/**
 * File Name: Graph.java 
 * Graph 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2018
 */
/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java
 */

/*
 * ----------------  YOU CANNOT CHANGE ANYTHING IN THIS FILE ------------------------
 */
class Graph {
	
	/*
	 * class node(vertex)
	 */
	private class node {
		private int name ;
		ArrayList<edge> fanins ;
		ArrayList<edge> fanouts ;
		node(int name) {
			this.name = name ;
			fanins =  new ArrayList<edge>() ;
			fanouts = new ArrayList<edge>() ;
		}
	}
	
	/*
	 * class edge
	 */
	private class edge {
		private int other ;
		private double cost ;
		edge(int other, double cost) {
			this.other = other ;
			this.cost = cost ;
		}
	}
	
	private int numV; // Number of nodes
	private int numE; // Number of edges
	private GraphTest.GraphType type ; //Graph type
  	private ArrayList<String> nodeNames ; //Node names given by the user
	private HashMap<String,Integer> hm; //String to Integer mapping
    private ArrayList<node> nodes ; //Array of all nodes
    public static final IntUtil u = new IntUtil();
    public final double INFINITY = Double.MAX_VALUE;
  
  Graph(GraphTest.GraphType t) {
  	numV = 0 ;
  	numE = 0 ;
  	type = t ;
  	nodeNames = new ArrayList<String>() ;
		hm = new HashMap<String,Integer>();
		nodes = new ArrayList<node>() ;
  }
  
  public String getGraphType() {
  	if (type == GraphTest.GraphType.UNDIRECTED) {
  		return ("UNDIRECTED GRAPH") ;
  	}
  	if (type == GraphTest.GraphType.DIRECTED) {
  		return ("DIRECTED GRAPH") ;
  	}
  	if (type == GraphTest.GraphType.WEIGHTED_UNDIRECTED) {
  		return ("WEIGHTED_UNDIRECTED GRAPH") ;
  	}
  	if (type == GraphTest.GraphType.WEIGHTED_DIRECTED) {
  		return ("WEIGHTED_DIRECTED GRAPH") ;
  	}
  	return ("NONE") ;
  }
  
  public boolean isUndirectedGraph() {
  	if (type == GraphTest.GraphType.UNDIRECTED) {
  		return true;
  	}
  	if (type == GraphTest.GraphType.WEIGHTED_UNDIRECTED) {
  		return true;
  	}
  	return false ;
  }
  
  public int getnumV() {
  	u.myassert(numV == nodes.size()) ;
  	return numV;
  }
  
  public int getnumE() {
  	return numE;
  }

  private node getNode(int i) {
  	u.myassert(i >= 0 && i < getnumV());
  	node n = nodes.get(i) ;
  	return n ;
  }
  
  public int getNodeName(int i) {
  	node n = getNode(i) ;
  	return n.name ;
  }
  
  /* Get name from integer */
  public String getRealName(int i) {
  	u.myassert(i >= 0 && i < nodeNames.size() );
  	return nodeNames.get(i) ;
  }
  
  public String getNodeRealName(int i) {
  	int x = getNodeName(i) ;
  	return getRealName(x) ;
  }
  
  public int numFanout(int i) {
  	node n = getNode(i) ;
  	int s = n.fanouts.size();
  	return s ;
  }
  
  public int numFanin(int i) {
  	node n = getNode(i) ;
  	int s = n.fanins.size();
  	return s ;
  }

  private edge getNodeFanoutEdge(int n, int e) {
  	node no = getNode(n) ;
  	int size = numFanout(n) ;
  	u.myassert(e >= 0 && e < size);
  	return no.fanouts.get(e) ;
  }
  
  /*
   * 2 -> {3,6,7}
   * getNodeFanoutEdgeE(2,6) returns edge that has 6
   * getNodeFanoutEdgeE(2,8) returns null and crashes
   * O(E)
   */
  private edge getNodeFanoutEdgeE(int n, int e, boolean check) {
  	node no = getNode(n) ;
  	int size = numFanout(n) ;
  	for (int i = 0; i < size; ++i) {
  		edge ed = no.fanouts.get(i) ;
  		if (ed.other == e) {
  			return ed ;
  		}
  	}
  	if (check == true) {
  		u.myassert(false);
  	}
  	return null ;
  }
  
  private edge getNodeFanInEdge(int n, int e) {
  	node no = getNode(n) ;
  	int size = numFanin(n) ;
  	u.myassert(e >= 0 && e < size);
  	return no.fanins.get(e) ;
  }
 
  public int getNodeFanout(int n, int e) {
  	edge ei = getNodeFanoutEdge(n,e) ;
  	return ei.other ;
  }
  
  public int getNodeFanoutE(int n, int e, boolean check) {
  	edge ei = getNodeFanoutEdgeE(n,e, check) ;
  	return ei.other ;
  }
 
  public int getNodeFanin(int n, int e) {
  	edge ei = getNodeFanInEdge(n,e) ;
  	return ei.other ;
  }

  public GraphTest.GraphType getType() {
  	return type ;
  }

  public double getNodeFanoutEdgeWeight(int n, int e) {
  	edge ei = getNodeFanoutEdge(n,e) ;
  	return ei.cost ;
  }
  
  public double getNodeFanoutEdgeWeightE(int n, int e) {
  	edge ei = getNodeFanoutEdgeE(n,e,true) ;
  	return ei.cost ;
  }
  
  public double getNodeFanoutEdgeWeightENoCheck(int n, int e) {
  	edge ei = getNodeFanoutEdgeE(n,e,false) ;
  	if (ei != null) {
  		return ei.cost ;
  	}
  	return INFINITY ;
  }

  public int graphHasNode(String s) {
		boolean f = hm.containsKey(s) ;
		if (f) {
			return(hm.get(s));
		}
		return -1 ;
	}
	
  public int insertOrFind(String name, boolean mustbethere) {
		boolean f = hm.containsKey(name) ;
		if (f) {
			return(hm.get(name));
		}
    if (mustbethere) {
      u.myassert(false);
    } 	
  	//Not in the hash. Insert in hash
  	hm.put(name,numV++) ;
  	//Given an unique number you can get name
  	nodeNames.add(name) ;
  	u.myassert(nodeNames.size() == numV);
  	//add node
  	node n = new node(numV-1);
  	nodes.add(n) ;
    return numV-1; //We inserted at pos numV-1
  }
  
  public void createEdge(int from, int to, double w, boolean fanout) {
  	node n = nodes.get(from) ;
  	u.myassert(n.name == from) ;
  	edge e = new edge(to,w) ;
  	if (fanout == true) {
  		numE++ ;
  		n.fanouts.add(e) ;
  	}else {
  		n.fanins.add(e) ;
  	}
  }
  /*
   * Use this routine to understand the grah data structure
   */
  public void dump(String name) {
  	System.out.println("------------ " + name + " --------------------");
  	System.out.println(getGraphType());
		System.out.println("Num Vertices = " + numV) ;
		System.out.println("Num Edges    = " + numE) ;
		int numedge = 0 ;
  	for (int i = 0; i < numV; ++i) {
  		String n = getRealName(i);
  		{
	  		System.out.print(n + " Fanouts: ") ;
	  		int f = numFanout(i);
	  		if (f == 0) {
	  			System.out.println("NONE");
	  		}else {
		  		for (int j = 0; j < f; ++j) {
		  			++numedge;
		  			int fo = getNodeFanout(i,j) ;
		  			String nf = getRealName(fo);
		  			if  (j < f - 1) {
		  				System.out.print(nf + ",") ;
		  			}else {
		  				System.out.println(nf) ;
		  			}			
		  		}
	  		}
  		}
  		{
	  		System.out.print(n + " FanIns: ") ;
	  		int f = numFanin(i);
	  		if (f == 0) {
	  			System.out.println("NONE");
	  		}else {
		  		for (int j = 0; j < f; ++j) {
		  			++numedge;
		  			int fi = getNodeFanin(i,j) ;
		  			String nf = getRealName(fi);
		  			if  (j < f - 1) {
		  				System.out.print(nf + ",") ;
		  			}else {
		  				System.out.println(nf) ;
		  			}			
		  		}
	  		}
  		}
  	}
  	u.myassert(numedge / 2 == numE) ;
  }
  
  public void buildGraph(String f) {
		GraphBuilder b = new GraphBuilder(this,f);
	}
  
  public void buildSnakeAndLadderGraph(int n, int[][] l, int [][] s) {
  	GraphSnakeandLadderBuilder b = new GraphSnakeandLadderBuilder(this,n,l,s);
  }

	public void writeDot(String s) {
		GraphDot b = new GraphDot(this,s);
	}
	
	public void dfs(String t, String start, boolean[] cycle, int [] work, int [] size, int [] dfsorder) {
		GraphDfs b = new GraphDfs(t,this,start,cycle,work,size,dfsorder);
	}
	
	public void bfs(String t, String start, int [] work, int[] size, int [] bfsorder, int [] bfspath) {
		GraphBfs b = new GraphBfs(t,this,start,work,size,bfsorder,bfspath);
	}
	
	public void topologicalSort(String t,boolean[] cycle, int [] work, int [] size, int [] topoorder) {
		GraphTopologicalSort b = new GraphTopologicalSort(t,this,cycle,work,size,topoorder);
	}
	
	public void DagLongestPath(String t,double [] w1) {
		DagLongestPath b = new DagLongestPath(t,this,w1);
	}
	
	public void dijkstra(String t, String dotFile, String start, String end, int [] work, double[] cost) {
		GraphDijkstra b = new GraphDijkstra(t, dotFile,this,start,end,work,cost);
	}
	
	public void DPShortestPath(String t, String dotFile, String start, String end, int [] work, double[] cost) {
		GraphDPShortestPath b = new GraphDPShortestPath(t, dotFile,this,start,end,work,cost);
	}
	
	public void BF(String t, String dotFile, String start, String end, int [] work, double[] cost) {
		GraphBF b = new GraphBF(t, dotFile,this,start,end,work,cost);
	}
	
	public void TSP(String t, String start, int [] work, double[] cost) {
		GraphTSP b = new GraphTSP(t, this,start,work,cost);
	}
	
	public void prim(String t, String dotFile, int [] work, double[] cost) {
		GraphPrim b = new GraphPrim(t, dotFile,this,work,cost);
	}
	
	public static void main(String[] args) {
    System.out.println("Graph.java starts");
    System.out.println("Use GraphTest.java to test");
    System.out.println("Graph.java Ends");
  }
}