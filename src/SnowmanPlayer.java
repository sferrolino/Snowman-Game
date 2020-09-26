import java.util.*;
import java.util.Iterator;
import java.io.*;
import java.util.ArrayList;
public class SnowmanPlayer 
{

    /**
     *This method returns the name of author at end of code
     *@return name of author
     */
    public static String getAuthor() 
    { 
  	return "Ferrolino, Sarah"; 
    }

    //Global variables I can refer to in all methods
    private static String[] wordArray; 
    private static String alphabet;
    private static String[] badGuesses;
    private static String prevGuesses;
    private static ArrayList<String> secretArray = new ArrayList<String>();
    private static ArrayList<String> arrayList = new ArrayList<String>();
    private static int counterAll;


    public static void startGame(String[] words, int min, int max, String letters) 
    { 
  		wordArray = words; //assign the list of words to a global varaible
  		alphabet = letters; //assign alphabet to a global variable
    }

    public static void startNewWord(int length) 
    {  

	  	//This creates a temp array containing the list of words matching the secret word length
	 	if(arrayList == null)
		    {
		  	ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(wordArray));

			for(int i = 0; i < arrayList.size(); i++)
			    {
			  	if(arrayList.get(i).length() == length) //if secret word length equals length of word in array,
				    {
			  		secretArray.add(arrayList.get(i)); //add word to new array
				    }
			    }
		    }

	  	//if the arraylist contains elements(i.e. from previous word) clear all global var lists and add elements again
	  	else if(arrayList != null)
		    {
	  		arrayList.clear();
	  		secretArray.clear();
	  		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(wordArray));

			for(int i = 0; i < arrayList.size(); i++)
			    {
			  	if(arrayList.get(i).length() == length) //if secret word length equals length of word in array,
				    {
			  		secretArray.add(arrayList.get(i)); //add word to new array
				    }
			    }
		    }

    }

    /**
     *This method finds the most frequent letter in the ArrayList that has not yet been guessed.
     *@param it takes an ArrayList that that the method looks over and a string of all previous guesses.
     *@return the most frequent character in ArrayList that hasn't been guessed.
     */
    private static char findFreq(ArrayList<String> a, String previousGuesses)
    {
	  	int highestFreq = 0; //initialize highestFreq to 0
	  	char most = '.'; //default mostFreq character

	  	//iterating through the whole alphabet
		for(int i = 0; i < alphabet.length(); i++)
		    {
	  		String check = ""+alphabet.charAt(i);
		  	counterAll = 0;
		  	if(!prevGuesses.contains(check)) //check if prevGuess is not in alphabet (avoid guessing same char)
			    {
		  		for(int j = 0; j < a.size(); j++)
				    {
		  			if(a.get(j).contains(check))
					    {
						counterAll++;
					    }
				    }
			    }
		  	if(counterAll > highestFreq) //if a letter has a higher count than the highestFreq, replace it.
			    {
		  		highestFreq = counterAll;
		  		most = alphabet.charAt(i);
			    } 		
		    }
		return most;
    }

    /**
     * This method, based on previous guesses and pattern decides which char is suitable to be guessed next
     * @param pattern of the secretWord filtered by '*' until char is guessed correctly and 
     * string of pervious guessed characters.
     * @return suitable character in terms of previousGuesses and length of secretWord 
     */
    public static char guessLetter(String pattern, String previousGuesses) 
    {
	  	prevGuesses = previousGuesses;

	  	//if a guess has not been made yet, simply return the highest freq letter
		if(prevGuesses.length() == 0)
		    {
				counterAll = 0;
				return findFreq(secretArray, previousGuesses);
		    }

		char c = previousGuesses.charAt(previousGuesses.length()-1);

		//if latest guess is WRONG, implement badChar method to get updated arraylist
		if(!pattern.contains(Character.toString(c)))
		    {
				badChar(secretArray, prevGuesses, badGuesses, pattern);
		    }

		//if latest guess is RIGHT, implement goodChar method to get updated arraylist
		if(pattern.contains(Character.toString(c)))
		    {
				goodChar(secretArray, prevGuesses, pattern);
		    }

		return findFreq(secretArray,previousGuesses);
    }

    /**
     *This method is used when previous guess was INCORRECT, thus filtering out list accordingly
     *@param filtered length arraylist, previous guesses string, array of bad guesses, pattern string
     *@return updated list with removed elements corresponding to badGuesses
     */
    private static ArrayList<String> badChar(ArrayList<String> b, String previousGuesses, String[] badGuesses, String pattern)
    {
		char c = previousGuesses.charAt(previousGuesses.length()-1);
		if(!pattern.contains(Character.toString(c)))
		    {
			badGuesses = new String[] {Character.toString(c)};
			for(int i = b.size() - 1; i > - 1 ; i--)
			    {
				for(int x = 0; x < badGuesses.length; x++)
				    {	
					String bad = badGuesses[x];
					if(b.get(i).contains(bad))
					    {
						b.remove(b.get(i));
						break;
					    }
				    }
			    }  		
		    }
		return b;			
    }

    /**
     *This method is used when previous guess was CORRECT, thus filtering out list accordingly based on correct index
     *@param filtered length arraylist, previous guesses string, pattern string
     *@return updated list with removed elements corresponding to pattern index
     */
    private static ArrayList<String> goodChar(ArrayList<String> b, String previousGuesses, String pattern)
    {
	  	char c = previousGuesses.charAt(previousGuesses.length()-1);
		if(pattern.contains(Character.toString(c)))
		    {
			//check each element in temp array
			for(int i = b.size() - 1; i > - 1 ; i--)
			    {
				//check each element of list
				for(int k = b.get(i).length() - 1; k > - 1; k--)
				    {
					if(pattern.charAt(k) != '*' && b.get(i).charAt(k) != pattern.charAt(k))
					    {
						b.remove(b.get(i));
						break;
					    }

				    }
			    }
		    }
		return b;	
    }		

}


