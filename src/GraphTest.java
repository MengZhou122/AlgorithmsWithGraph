import java.util.ArrayList;

/**
 * File Name: GraphTest.java 
 * Tests Graph object
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2018
 */
/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.java
 */

class GraphTest{
	private static final String inputFileBase = "/Users/meng/Desktop/NEU-Courses/6205Algorithm/HW5/graphexamples/";
	private static final String outputFileBase = "/Users/meng/Desktop/NEU-Courses/6205Algorithm/HW5/fig/";
	/*----------YOU CANNOT CHANGE ANYTHING BELOW ----------------------------*/
	private static final IntUtil u = new IntUtil();
	public enum GraphType {
		NONE, UNDIRECTED , DIRECTED, WEIGHTED_UNDIRECTED, WEIGHTED_DIRECTED 
	}
	
	GraphTest() {
		testBed() ;
	}
	
	GraphTest.GraphType gtype(int x) {
		switch (x) {
		case 1: return GraphTest.GraphType.UNDIRECTED ;
		case 2: return GraphTest.GraphType.DIRECTED ;
		case 3: return GraphTest.GraphType.WEIGHTED_UNDIRECTED ;
		case 4: return GraphTest.GraphType.WEIGHTED_DIRECTED ;
		default: return GraphTest.GraphType.NONE ;
		}
	}

	private void u1() {
		String name = "13" ;
		String f = inputFileBase +  name + ".txt" ;
		GraphTest.GraphType type = GraphTest.GraphType.UNDIRECTED ;
		Graph g = new Graph(type) ;
		g.buildGraph(f);
		g.writeDot(outputFileBase + name + ".dot") ;
		g.dump(name) ;
		u.myassert(g.getnumV() == 7);
		u.myassert(g.getnumE() == 24);
	}

	private void uw1() {
		String name = "14" ;
		String f = inputFileBase +  name + ".txt" ;
		GraphTest.GraphType type = GraphTest.GraphType.WEIGHTED_UNDIRECTED ;
		Graph g = new Graph(type) ;
		g.buildGraph(f);
		g.writeDot(outputFileBase + name + ".dot") ;
		g.dump(name) ;
		u.myassert(g.getnumV() == 6);
		u.myassert(g.getnumE() == 20);
	}

	private void d1() {
		String name = "15" ;//15
		String f = inputFileBase +  name + ".txt" ;
		GraphTest.GraphType type = GraphTest.GraphType.DIRECTED ;
		Graph g = new Graph(type) ;
		g.buildGraph(f);
		g.writeDot(outputFileBase + name + ".dot") ;
		g.dump(name) ;
		u.myassert(g.getnumV() == 6);
		u.myassert(g.getnumE() == 6);
	}

	private void dw1() {
		String name = "16" ;
		String f = inputFileBase +  name + ".txt" ;
		GraphTest.GraphType type = GraphTest.GraphType.WEIGHTED_DIRECTED ;
		Graph g = new Graph(type) ;
		g.buildGraph(f);
		g.writeDot(outputFileBase + name + ".dot") ;
		g.dump(name) ;
		u.myassert(g.getnumV() == 5);
		u.myassert(g.getnumE() == 6);
	}
	
	private void testGraphRepresentation() {
		u1() ;
		uw1() ;
		d1() ;
		dw1() ;
	}

	private void testDFS() {
		{
			String [] n = {"2","3","7", "8", "1", "4","9","tinyDG", "mediumDG"} ; 
			int [] t = {2,2,4,1,1,2,2,2,2};
			String [] s = {"1","0", "0", "1", "1", "A","A","7","10"} ;
			int [] c = {0, 1, 0, 1, 0, 0,1,1,1};
			
			int nl = n.length ;
			int tl = t.length ;
			int sl = s.length ;
			int cl = c.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == sl);
			u.myassert(nl == cl);
			
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				String sc = s[i] ; //Starting city
				boolean[] cycle = {false} ;
				//dfsorder cannot be bigger than numV
				int [] dfsorder = new int[g.getnumV()] ;
				int[] work = {0} ;
				int[] size = {0} ;
				String name1 = name + ".txt" ;
				g.dfs(name1,sc,cycle,work,size,dfsorder) ;
				if (cycle[0] == false) {
					u.myassert(c[i] == 0) ;
				}
				if (cycle[0] == true) {
					u.myassert(c[i] == 1) ;
				}
			}	
		}
	}
	
	private void testBFS() {
		{
			String [] n = {"50","1","2","3","4","5","6","7"} ;
			int [] t = {1,1,2,2,2,1,1,4};
			String [] s = {"0","1","1","0","A","0","1","0"} ;
			
			//String [] n = {"50"} ;
			//int [] t = {1};
			//String [] s = {"0"} ;
			
			int nl = n.length ;
			int tl = t.length ;
			int sl = s.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == sl);
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				String sc = s[i] ; //Starting city
				//bfsorder cannot be bigger than numV
				int [] bfsorder = new int[g.getnumV()] ;
				int [] bfspath = new int[g.getnumV()] ;
				int[] work = {0} ;
				int[] size = {0} ;
				String name1 = name + ".txt" ;
				g.bfs(name1,sc,work,size,bfsorder,bfspath) ;
			}	
		}
	}
	
	private void testDijkstra() {
		{
			String [] n = {"7", "17"} ; 
			int [] t = {4,3};
			String [] s = {"0", "A"} ; //starting city
			String [] e = {"6", "F"} ; //ending city
			double [] w = {13,7} ;
			
			int nl = n.length ;
			int tl = t.length ;
			int sl = s.length ;
			int wl = w.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == sl);
			u.myassert(nl == wl);
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				String sc = s[i] ; //Starting city
				
				int [] work = {0} ;
				double [] cost = {0} ;//Propagate minimal cost
				String name1 = name + ".txt" ;
				String dotFile = outputFileBase + name + "tree.dot" ;
				g.dijkstra(name1,dotFile,sc,e[i],work,cost) ;
				//u.myassert(cost[0] == w[i]);
			}	
		}
	}
	
	private void testToplogicalSort() {
		{
			String [] n = {"7","2", "3"} ; 
			int [] t = {4,2,2};
			int [] c = {0,0,1};
			
			int nl = n.length ;
			int tl = t.length ;
			int cl = c.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == cl);
			
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				boolean[] cycle = {false} ;
				int [] topoorder = new int[g.getnumV()] ;
				int[] work = {0} ;
				int[] size = {0} ;
				String name1 = name + ".txt" ;
				g.topologicalSort(name1,cycle,work,size,topoorder) ;
				if (cycle[0] == false) {
					u.myassert(c[i] == 0) ;
				}
				if (cycle[0] == true) {
					u.myassert(c[i] == 1) ;
				}
			}	
		}
	}

	private void testDagLongestPath() {
		{
			String [] n = {"10", "7", "11", "12"} ; 
			int [] t = {2,4,4,4} ;
			double [] w = {4,26,18,40};
			
			
			int nl = n.length ;
			int tl = t.length ;
					
			u.myassert(nl == tl);
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				String name1 = name + ".txt" ;
				double [] w1 = {0} ;
				g.DagLongestPath(name1,w1) ;
				u.myassert(w1[0] == w[i]);	
			}	
		}
	}
	
	private void testDPShortestPath() {
		{
			String [] n = {"7", "17"} ; 
			int [] t = {4,3};
			String [] s = {"0", "A"} ; //starting city
			String [] e = {"6", "F"} ; //ending city
			double [] w = {13,0} ;
			
			int nl = n.length ;
			int tl = t.length ;
			int sl = s.length ;
			int wl = w.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == sl);
			u.myassert(nl == wl);
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				String sc = s[i] ; //Starting city
				
				int [] work = {0} ;
				double [] cost = {0} ;//Propagate minimal cost
				String name1 = name + ".txt" ;
				String dotFile = outputFileBase + name + "tree.dot" ;
				g.DPShortestPath(name1,dotFile,sc,e[i],work,cost) ;
				u.myassert(cost[0] == w[i]);
			}	
		}
	}
	
	private void testBF() {
		{
			String [] n = {"18", "18a", "19", "19a"} ; 
			int [] t = {4,4,4,4};
			String [] s = {"0", "0", "1", "1"} ; //starting city
			String [] e = {"5", "5", "5", "5"} ; //ending city
			double [] w = {1,1,9,9} ; //Expected shortest path
			
			int nl = n.length ;
			int tl = t.length ;
			int sl = s.length ;
			int el = e.length ;
			int wl = w.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == sl);
			u.myassert(nl == el);
			u.myassert(nl == wl);
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				String sc = s[i] ; //Starting city
				
				int [] work = {0} ;
				double [] cost = {0} ;//Propagate minimal cost
				String name1 = name + ".txt" ;
				String dotFile = outputFileBase + name + "tree.dot" ;
				g.BF(name1,dotFile,sc,e[i],work,cost) ;
				u.myassert(cost[0] == w[i]);
			}	
		}
	}
	
	private void testTSP() {
		{
			String [] n = {"20", "21", "22", "23"} ; 
			int [] t = {4,4,4,4};
			String [] s = {"A", "1", "4", "5"} ; //starting city
			double [] w = {21,35,20,9} ; //Expected minimum cost
			
			int nl = n.length ;
			int tl = t.length ;
			int sl = s.length ;
			int wl = w.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == sl);
			u.myassert(nl == wl);
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				String sc = s[i] ; //Starting city
				
				int [] work = {0} ;
				double [] cost = {0} ;//Propagate minimal cost
				g.TSP(name,sc,work,cost) ;
				u.myassert(cost[0] == w[i]);
			}	
		}
	}
	
	private void testPrim() {
		{
			String [] n = {"24"} ; 
			int [] t = {3};
			double [] w = {16} ;
			
			int nl = n.length ;
			int tl = t.length ;
			int wl = w.length ;
			
			u.myassert(nl == tl);
			u.myassert(nl == wl);
			for (int i = 0; i < nl; ++i) {
				String name = n[i] ;
				String f = inputFileBase +  name + ".txt" ;
				GraphTest.GraphType type = gtype(t[i]);
				Graph g = new Graph(type) ;
				g.buildGraph(f);
				g.writeDot(outputFileBase + name + ".dot") ;
				int [] work = {0} ;
				double [] cost = {0} ;//Propagate MST
				String name1 = name + ".txt" ;
				String dotFile = outputFileBase + name + "tree.dot" ;
				g.prim(name1,dotFile,work,cost) ;
				u.myassert(cost[0] == w[i]);
			}	
		}
	}
	
	private void SnakeAndLadder(String f, int n, int[][] l, int [][] s) {
		GraphTest.GraphType type = GraphTest.GraphType.DIRECTED ;
		Graph g = new Graph(type) ;
		g.buildSnakeAndLadderGraph(n,l,s);
		g.writeDot(outputFileBase + f + ".dot") ;
		g.dump(f) ;
		//bfsorder cannot be bigger than numV
		int [] bfsorder = new int[g.getnumV()] ;
		int [] bfspath = new int[g.getnumV()] ;
		int[] work = {0} ;
		int[] size = {0} ;
		String name1 = f + ".txt" ;
		String sc = "1" ;
		g.bfs(name1,sc,work,size,bfsorder,bfspath) ;
	}
	
	private void testSnakeAndLadder() {
		{
			int[][] l = {
					{3,16},
					{21,32},
					{15,25}
			};
			int[][] s = {
					{12,2},
					{30,4},
					{35,22}
			};
			SnakeAndLadder("Board36",36,l,s) ;
		}
		{
			int[][] l = {
					{32,62},
					{42,68},
					{12,98}
			};
			int[][] s = {
					{95,13},
					{97,25},
					{93,37},
					{79,27},
					{75,19},
					{49,47},
					{67,17}
			};
			SnakeAndLadder("Board100case1",100,l,s) ;
		}
		{
			int[][] l = {
					{8,52},
					{6,80},
					{42,26},//To trouble you
					{2,72}
			};
			int[][] s = {
					{51,19},
					{39,11},
					{29,37},//To trouble you
					{3,81},////To trouble you
					{59,5},
					{79,23},
					{7,53},
					{43,33},
					{21,77},
			};
			SnakeAndLadder("Board100case2",100,l,s) ;
		}
		{
			int[][] l = {
					{2,82},
					{64,99}
			};
			int[][] s = {
					{84,63},
			};
			SnakeAndLadder("SnakeisNotBad",99,l,s) ;
		}
	}
	
	private void testBed() {
	//	testGraphRepresentation() ;

	//	testDFS();
		
	//	testBFS() ;
	//	testSnakeAndLadder();
		
	//	testToplogicalSort() ;
		testDagLongestPath() ;
	
	// testDijkstra() ;
		
	//	testDPShortestPath() ;
	
	//	testBF() ;
	//	testTSP() ;
	//	testPrim() ;
	}
	
	public static void main(String[] args) {
		String version = System.getProperty("java.version");
		System.out.println("Java version used for this program is " + version);
    System.out.println("GraphTest.java starts");
    GraphTest g = new GraphTest() ;
    System.out.println("GraphTest.java Ends");
    System.out.println("goggle: grapviz online");
  }
}
