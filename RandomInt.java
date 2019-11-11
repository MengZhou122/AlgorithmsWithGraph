import java.util.Random;

/**
 * File Name: RandomInt.java 
 * Generates random integers
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2019
 */

class RandomInt {
	//max = 100
	//Gives a number between 0(inclusive) and 100(exclusive) 0...99
	static public int getRandomInt(Random r, int max) {
		int x = r.nextInt(max) ;
		return x ;
	}

	//max = 80, min = 65
	//This gives a random integer between 65 (inclusive) and 80 (exclusive), one of 65,66,...,78,79
	static public int getRandomInt(Random r, int min, int max) {
		if (max < min) {
			int t = min ;
			min = max ;
			max = t ;  
		}
		assert(max > min) ;
		int range = max - min ; //15
		int x = RandomInt.getRandomInt(r,range); //0 to 14
		int y = min + x ; //min is 65 + 0 = 65,  max is 65 + 14 = 79
		return y ;
	}

	private static void testBench() {
		Random r = new Random() ;
		for (int i = 0 ; i < 3; ++i) {
			int x = getRandomInt(r,900) ;
			System.out.println(x + " must between 0 and 899");
		}
		for (int i = 0 ; i < 3; ++i) {
			int x = getRandomInt(r,50,150) ;
			System.out.println(x + " must between 50 and 149");
		}
	}

	public static void main(String[] args) {
		System.out.println("RandomInt.java");
		testBench();
		System.out.println("done");
	}
}