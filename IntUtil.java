
/**
 * File Name: IntUtil.java 
 * All common routines that operates on basic type 'int'
 * Java11
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
class IntUtil{

	IntUtil() {

	}

	public void myassert(boolean  x) {
		if (!x) {
			throw new IllegalArgumentException("Assert fail") ;
		}
	}
	//Print 0 to n
	public void pLn(int n) {
		for (int i = 0 ; i < n; ++i) {
			if (i != 0) {
				System.out.print(' ');
			}
			System.out.format("%3d", i);
		}
		System.out.println() ;
	}

	//Print a[i]
	public void p(int [] a, int pos) {
		System.out.print("a[" + pos +"] = " + a[pos] + " ");
	}
	//Print a[i] with title t
	public void p(String t, int [] a, int pos) {
		System.out.print(t) ;
		p(a,pos) ;
	}
	//Println a[i]
	public void pLn(int [] a, int pos) {
		System.out.println("a[" + pos +"] = " + a[pos]);
	}
	//Println a[i] with title t
	public void pLn(String t, int [] a, int pos) {
		System.out.print(t) ;
		pLn(a,pos) ;
	}
	//Print a part of the array from start to tend-1
	public void p(int [] a, int start, int tend) {
		for (int i = start ; i < tend; ++i) {
			int v = a[i] ;
			if (i != start) {
				System.out.print(' ');
			}
			System.out.format("%3d", v);
		}
	}  
	//Print a part of the array from start to tend-1 with title t
	public void p(String t, int [] a, int start, int tend) {
		System.out.print(t) ;
		p(a,start,tend);
	}

	//Print a part of the array from start to tend-1 with EOLN
	public void pLn(int [] a, int start, int tend) {
		p(a,start,tend);
		System.out.println() ;
	}
	//Print a part of the array from start to tend-1 with EOLN and Title
	public void pLn(String t,int [] a, int start, int tend) {
		System.out.print(t) ;
		p(a,start,tend);
		System.out.println() ;
	}
	//Print an array with EOLN
	public void pLn(int [] a) {
		pLn(a,0,a.length);
	}

	//Print an array with titleand EOLN
	public void pLn(String t, int [] a) {
		pLn(t,a,0,a.length);
	}

	public void swap(int [] a, int x, int y) {
		if (x != y) {
			int t = a[x] ;
			a[x] = a[y] ;
			a[y] = t ;
		}
	}

	public void assertAscending(int [] a, int start, int encludingend) {
		int f = a[start] ;
		for (int i = start+1; i <= encludingend; ++i) {
			if (a[i] < f ) {
				myassert(false);
			}
			f = a[i] ;
		}
	}

	public void assertAscending(int [] a) {
		if (a.length > 0){ 
			assertAscending(a,0,(a.length-1));
		}
	}

	public void assertDescending(int [] a) {
		int f = a[0] ;
		for (int i = 1; i < a.length; ++i) {
			if (a[i] > f ) {
				myassert(false);
			}
			f = a[i] ;
		}
	}

	public void reverse(int [] a) {
		int i = 0 ;
		int j = a.length - 1 ;
		while (i < j) {
			swap(a,i,j);
			i++;
			j-- ;
		}
	}

	public int[][] testArray() {
		int [] a0 = {} ;
		int [] a1 = {15} ;
		int [] a2 = {15,5} ;
		int [] a3 = {15,5,64,8,12,11,4,35} ;
		int [] a4 = {6, 5, 4, 3, 2, 1} ;
		int [] a5 = {1, 2, 3, 4, 5, 6} ;
		int [] a6 = {1, 1, 1, 1, 1, 1} ;
		//http://www.csie.ntu.edu.tw/~b93076/p847-sedgewick.pdf
		int [] a7 = {3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3} ;
		int[][] a = {a0,a1,a2,a3,a4,a5,a6,a7} ;
		return a ;
	}

	public int[] generateRandomNumber(int N, boolean onlypositive, int start, int end) {
		Random r = new Random() ;
		int [] a  = new int[N] ;
		for (int i = 0; i < N; ++i) {
			int v = RandomInt.getRandomInt(r,start,end+1); //This gives number between start to end
			if (onlypositive == false) {
				if ((v % 2) == 0) { //Even
					v = -v ;
				}
			}
			a[i] = v ;
		}
		return a ;
	}

	public int[] generateRandomNumber(int N, boolean onlypositive) {
		return generateRandomNumber(N,onlypositive,1,10000000) ;
	}

	public int[] generateNumberInIncreasingOrder(int N, int step) {
		int [] a  = new int[N] ;
		for (int i = 0; i < N; ++i) {
			int v = step * i ;
			a[i] = v ;
		}
		return a ;
	}

	public int[] generateNumberInDescendingOrder(int N, int step) {
		int [] a  = generateNumberInIncreasingOrder(N,step) ;
		reverse(a) ;
		return a ;
	}

	public int[] generateNumberInIncreasingOrder2(int N, int step) {
		int k = 0 ;
		int [] a  = new int[N*2] ;
		for (int i = 0; i < N; ++i) {
			int v = step * i ;
			a[k++] = v ;
		}
		for (int i = 0; i < N; ++i) {
			int v = step * i ;
			a[k++] = v ;
		}
		return a ;
	}

	public void printStatistics(int n, long numCompare, long numSwap, int numRecursion) {
		System.out.println("# n =      " + n) ;
		if (numRecursion != 0) {
			System.out.println("# num recursion  =     " + numRecursion) ;
		}
		System.out.println("# num compare(C)     =  " + numCompare) ;
		System.out.println("# num swap(S)     =  " + numSwap) ;
		long cs = numCompare + numSwap ;
		System.out.println("# C+S     =  " + cs) ;

		if (n > 0) {
			double n1 = (double)(cs)/n ;
			System.out.println("T(n)=(C+S)/(n)= " + n1 + "(n)") ;

			double nlogn = n * (Math.log(n)/Math.log(2)) ; //(log n to base 2 = log n to base 10/ log 2 to base 10)
			System.out.println("# nlogn    =  " + nlogn) ;
			if (nlogn > 0) {
				double x = (double)(cs)/nlogn ;
				System.out.println("T(n)=(C+S)/(nlogn)= " + x + "(n*logn)") ;
			}
			long n2 = n * n ;
			double y = (double)(cs)/n2 ;
			System.out.println("# n*n    =  " + n2) ;
			System.out.println("T(n)=(C+S)/(n^2)= " + y + "(n^2)") ;
		}else {
			System.out.println("Zero elements in array") ;
		}
	}

	public void printStatistics(String t, int n, long numCompare, long numSwap, int numRecursion) {
		System.out.println("------------------ " + t + " ---------------------------") ;
		printStatistics(n,numCompare,numSwap,numRecursion);
		System.out.println("-----------------------------------------") ;
	}

	public String toString(int[] a, int s, int e, char ch) {
		String r = new String() ;
		for (int i = s; i <= e; ++i) {
			r = r + a[i] ;
			if (i != e) {
				r = r + ch ;
			}
		}
		return r ;
	}

	public String toString(int[] a, char ch) {
		int s = 0 ;
		int e = a.length -  1;
		return toString(a,s,e,ch) ;
	}

	/*
	 * endTime and startTime are in nano seconds
	 */
	public double timeInSec(long endTime, long startTime) {
		long duration = (endTime - startTime);
		if (duration > 0) {
			double dm = (duration/1000000.0); //Milliseconds
			double d = dm/1000.0; //seconds
			return d ;
		}
		return 0.0 ;
	}

	/*
	 * Time: O(n) 
	 * Space: O(1)
	 */
	public int[] copyArray(int [] a) {
		int n = a.length ;
		int [] b = new int[n] ;
		for (int i = 0; i < n; ++i) {
			b[i] = a[i] ;
		}
		return b ;
	}

	/*
	 * Time: O(n) 
	 * Space: O(1)
	 */
	public boolean arrayEqual(int [] a, int [] b) {
		int n = a.length ;
		int m = b.length ;
		if (n != m) {
			return false ;
		}

		for (int i = 0; i < n; ++i) {
			if (a[i] != b[i]) {
				return false ;
			}
		}
		return true  ;
	}


	public void openDotFile(String s) {
		try {
			FileWriter o = new FileWriter(s);
			o.write("digraph g {\n");
			o.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void appendDotFile(String s, String t) {
		try {
			FileWriter o = new FileWriter(s,true);
			o.write(t + "\n")	;
			o.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void closeDotFile(String s) {
		try {
			FileWriter o = new FileWriter(s,true);
			o.write("}\n");
			o.close();
			System.out.println("You can see dot file at " + s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) {
		System.out.println("IntUtil.java");
	}
}