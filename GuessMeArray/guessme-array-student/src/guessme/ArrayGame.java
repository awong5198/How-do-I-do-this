package guessme;

/**
 * An Array-based implementation of the Guess-A-Number game
 */
public class ArrayGame {

	// stores the next number to guess
	private int guess;
	int[] prior;
	boolean[] flag;
	int numGuess = 0;
	int lastIndex = -1;
	// TODO: declare additional data members, such as arrays that store
	// prior guesses, eliminated candidates etc.

	// NOTE: only primitive type arrays are allowed, such as int[], boolean[] etc.
	// You MAY NOT use any Collection type (such as ArrayList) provided by Java.
	
	/********************************************************
	 * NOTE: you are allowed to add new methods if necessary,
	 * but DO NOT remove any provided method, otherwise your
	 * code will fail the JUnit tests!
	 * Also DO NOT create any new Java files, as they will
	 * be ignored by the autograder!
	 *******************************************************/
	
	// ArrayGame constructor method
	public ArrayGame() {
		// TODO
		guess = 1000;
		prior = new int[20];
		lastIndex = 0;
		flag = new boolean[9001];
		for (int i = 0; i < flag.length; i++){
			flag[i] = false;
		}
	}
	
	// Resets data members and game state so we can play again
	public void reset() {
		// TODO
		guess = 1000;
		prior = new int[20];
		flag = new boolean[9001];
		lastIndex = -1;
	}
	
	// Returns true if n is a prior guess; false otherwise.
	public boolean isPriorGuess(int n) {
		// TODO
		for (int i = 0; i < prior.length; i++){
			if(prior[i] == n){
				return true;
			}
		}
		return false;
		
	}
	
	// Returns the number of guesses so far.
	public int numGuesses() {
		// TODO
	
		return (lastIndex+1);
	}
	
	/**
	 * Returns the number of matches between integers a and b.
	 * You can assume that both are 4-digits long (i.e. between 1000 and 9999).
	 * The return value must be between 0 and 4.
	 * 
	 * A match is the same digit at the same location. For example:
	 *   1234 and 4321 have 0 match;
	 *   1234 and 1114 have 2 matches (1 and 4);
	 *   1000 and 9000 have 3 matches (three 0's).
	 */
	public static int numMatches(int a, int b) { // DO NOT remove the static qualifier
		// TODO
		int one = a;
		int two = b;
		int c;
		int d;
		int matches = 0;
	
		for (int i = 0; i < 4; i++){
			c = one%10;
			d = two%10;
			if (c == d){
				matches++;
			}
			one /= 10;
			two /= 10;
		}
		return matches;
	}
	
	/**
	 * Returns true if the game is over; false otherwise.
	 * The game is over if the number has been correctly guessed
	 * or if all candidates have been eliminated.
	 */
	public boolean isOver() {
		// TODO
		if (updateGuess(4) == true){
			return true;
		}
		return false;
	}
	
	// Returns the guess number and adds it to the list of prior guesses.
	public int getGuess() {
		// TODO: add guess to the list of prior guesses.
		prior[lastIndex+1] = guess;
		lastIndex++;
		numGuess++;
		return guess;
	}
	
	/**
	 * Updates guess based on the number of matches of the previous guess.
	 * If nmatches is 4, the previous guess is correct and the game is over.
	 * Check project description for implementation details.
	 * 
	 * Returns true if the update has no error; false if all candidates
	 * have been eliminated (indicating a state of error);
	 */
	//check if all candidates have been eliminated
	public boolean testArray(boolean[] a){
		for (int i = 0; i< a.length; i++){
			if (a[i] == false){
				return false;
			}
		}
		return true;
	}
	
	public boolean updateGuess(int nmatches) {
		// TODO
		
		int a = getGuess()/1000;
        int b = (getGuess()/100)%10;
        int c = (getGuess()/10)%10;
        int d = getGuess()%10; 
        
		if(nmatches == 2){
			for (int i = a; i <=9; i++){
				flag[(i*1000 +b*100+c*10+d)-1000] = true;
			}
			for (int i = b; i <=9; i++){
				flag[(a*1000 +i*100+c*10+d)-1000] = true;
			}
			for (int i = c; i <=9; i++){
				flag[(a*1000 +b*100+i*10+d)-1000] = true;
			}
			for (int i = d; i <=9; i++){
				flag[(a*1000 +b*100+c*10+i)-1000] = true;
			}
		}

		if (nmatches==1){
			//tens and ones
			for (int i = c; i<=9; i++){
				for (int j = 0; j <= 9; j++){
					flag[(a*1000 +b*100+i*10+j)-1000] = true;			
				}
			}
			
			// hundreds and ones
			for (int i = b; i<=9; i++){
				for (int j = 0; j <= 9; j++){
					flag[(a*1000 +i*100+c*10+j)-1000] = true;
				}
			}
			
			//hundred and tens
			for (int i = b; i<=9; i++){
				for (int j = 0; j <= 9; j++){
					flag[(a*1000 +j*100+i*10+d)-1000] = true;
				}
			}
			 
			// thousands and hundreds
			for (int i = a; i<=9; i++){
				for (int j = 0; j <= 9; j++){
					flag[(i*1000 +j*100+c*10+d)-1000] = true;
				}
			}
			
			//thousands and tens
			for (int i = a; i<=9; i++){
				for (int j = 0; j <= 9; j++){
					flag[(i*1000 +j*100+i*10+d)-1000] = true;
				}
			}
			
			//thousands and ones
			for (int i = a; i<=9; i++){
				for (int j = 0; j <= 9; j++){
					flag[(i*1000 +b*100+c*10+j)-1000] = true;
				}
			}
		}
		
		if (nmatches == 0){
			for (int i = getGuess(); i <= ((a+1)*1000+(b+1)*100+(c+1)*10+(d+1));i++){
				flag[(a*1000 +b*100+c*10+d)-1000] = true;
		    }  
			
		}
		
		if(testArray(flag) == true){
			return false;
		}
		return true;
		

}
	
	// Returns the list of guesses so far as an integer array.
	// The size of the array must be the number of prior guesses.
	// Returns null if there has been no prior guess
	public int[] priorGuesses() {
		// TODO
		int[] priorGuess = new int[lastIndex+1];
		if (numGuesses() == 0){
			return null;
		}
		for (int i = 0; i < lastIndex+1; i++){
			priorGuess[i] = prior[i];
		}
		return priorGuess;
	}
}
