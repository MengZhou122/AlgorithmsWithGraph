/**
 * File Name: SFSU.java 
 * UnionFind problem that works on basic data type int
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */
import java.util.Random;
class SUSF {
	private int[] id ; //id array. Can be changed
	private long  length ;
     //IT IS OK IF YOU DONT KNOW HOW TO UPDATE 3 Variables below
	private long numFind ;
	private long numUnion;
	private int maxHeight ; //max height of tree while chasing 
	private static final IntUtil u = new IntUtil();

	SUSF(int n) {
		length = n ;
		id = new int[n] ;
		//The true identity of parent is in a node that has negative weight
		for (int i = 0; i < n; ++i) {
			id[i] = -1 ;
		}
		numFind = 0 ;
		numUnion = 0 ;
		maxHeight = 0 ;
	}

	public void P() {
		System.out.println("n = " + (length-1)) ;
		System.out.println("U = " + numUnion + " F = " + numFind + " H = " + maxHeight ) ;
	}


	/*
	 * smart Find
	 * Who is parent of a
	 * Almost Constant
	 * Number of chase is stored on k
	 * Max Chase per problem is stored in maxHeight
	 */
	

	private int F(int a) {
		//WRITE CODE
		numFind++;
		//System.out.println("Find parent of " + a + ": a, current ida is : " + id[a]);
		if (id[a] < 0) {
			//numFind++;
			return a;
		} else {
			int topParent=  F(id[a]);
			id[a] = topParent;
			return id[a];
		}
	}

	/*
	 * smart Find
	 * Is a connected to b?
	 * Almost constant
	 */
	private boolean F(int a, int b) {
		//WRITE CODE
		if (id[a] == -1 && id[b] == -1){
			return false;
		}
//		System.out.println("check connected or not " + a + " and " + b );
//		System.out.println("parens are " + F(a) + " and  "+ F(b));
		return F(a) == F(b);
	}

	/*
	 * smart Union
	 * Make union of a and b
	 * b is now purchased by a
	 * Almost constant algorithm
	 */
	public boolean U(int a, int b) {
		//WRITE CODE
		int ap = F(a);
		int bp = F(b);

		if (ap == bp && ap != -1) return false;

//		System.out.println("union " + a + " and " + b);
//		System.out.println("ap " + ap + " and  bp " + bp);
//		System.out.println("idap " + id[ap] + " and  idbp " + id[bp]);

		//default set b's parent has less child than a's parent
		if (id[ap] <= id[bp]) {
			id[ap] += id[bp];
			id[bp] = ap;
			id[b] = ap;
		} else {
			id[bp] += id[ap];
			id[ap] = bp;
			id[a] = bp;
		}

//		System.out.println("after union:");
//		System.out.println("ap " + id[a] + " and  new bp " + id[b]);
//		for(int i = 0; i < id.length; i++) System.out.print(id[i] + " ");

		numUnion++;
		return true;
	}

	public void calHeight(){
		for(int i  = 0; i < length -1 ; i++){
			int parent = id[i];
			//System.out.println(i + ", " + length + ", " + parent);
			int height = 1;
			while (parent >= 0){
				parent = id[parent];
				height++;
			}
			maxHeight = Math.max( maxHeight, height );
		}
	}

      //NOTHING CAN BE CHANGED BELOW
	
	private void testBasic() {
		int [][] e1 = { 
				{4,3}, 
				{3,8},
				{6,5},
				{9,4},
				{2,1},  
		} ;
		int [][] e2 = { 
				{5,0}, 
				{7,2},
				{6,1},
				{7,3}, 
		} ;
		int n = 10 ;
		SUSF uf = new SUSF(n) ;
		for (int i = 0; i < e1.length; ++i) {
			int[] p = e1[i] ;
			u.myassert(p.length == 2);
			uf.U(p[0],p[1]) ;
		}
		u.myassert(uf.F(8,9)) ; //8->9 must be connected
		u.myassert(uf.F(5,4) == false) ; //5->4 must not be connected

		for (int i = 0; i < e2.length; ++i) {
			int[] p = e2[i] ;
			u.myassert(p.length == 2);
			uf.U(p[0],p[1]) ;
		} 
		uf.P();
		System.out.println("Test basic passed");
	}
	
	private void test1(int n) {
		System.out.println("--------------------test1---------------");
		SUSF uf = new SUSF(n) ;
		for (int i = 0; i < n-1; ++i) {
			uf.U(i,i+1) ;
		}
		System.out.println("id length : "+ id.length);
		uf.P();
		System.out.println("Test1passed");
	}
	
	private void test2(int n) {
		System.out.println("--------------------test2---------------");
		SUSF uf = new SUSF(n) ;
		uf.U(0,1) ;
		for (int i = 2; i < n; ++i) {
      uf.U(i,0) ;
    }
		uf.P();
		System.out.println("Test2passed");
	}
	
	private void testRandom(int n) {
		System.out.println("--------------------testRandom---------------");
		int k = n * 10 ;
		System.out.println("Union on " + n + " random data performed " + k + " times") ;
		SUSF uf = new SUSF(n) ;
    	Random r = new Random() ;
    	for (int i = 0; i < k; ++i) {
      		int v1 = RandomInt.getRandomInt(r,0,n); //This gives number between 0 to n-1
      		int v2 = RandomInt.getRandomInt(r,0,n); //This gives number between 0 to n-1
      		if (v1 != v2) {
      			if (i % 5 == 0) {
          		boolean z = uf.F(v1,v2) ;
      			}else {
          		uf.U(v1,v2) ;
      			}
      		}
    	}
    	uf.P();
		System.out.println("testRandompassed");
	}
	
	private void testBench() {
		testBasic() ;
		test1(1000);
		test2(1000);
		testRandom(100000) ;
	}

	public static void main(String[] args) {
		String version = System.getProperty("java.version");
		System.out.println("Java version used for this program is " + version);
		System.out.println("SUSF.java Starts");
		SUSF u = new SUSF(5) ;
		u.testBench() ;
		System.out.println("SUSF.java Ends");
	}  
}