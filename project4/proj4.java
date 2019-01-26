import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;

/**
 * main method for project 4
 * @author Joey Schauer
 */
public class proj4 {
    /** the capacity constant for the hash table */
	public static final int HASH_TABLE_CAPACITY = 31469;
    /** gets the total number of words to be spell checked */
    public static int wordTotal;
    /** gets the total number of misspelled words */
    public static int misspelledWordTotal;
    /** gets the total number of lookups taken */
    public static int lookupTotal;
	
	public static void main(String[] args) {
		/** creates a scanner for system input */
		Scanner input = new Scanner(System.in);
		/** prompts for dictionary file and stores it's value */
		System.out.print("Enter dictionary file name: ");
		String dictionaryFileName = input.nextLine();
        /** prompts for input file name and stores it's value */
		System.out.print("Enter input file name: ");
		String inputFileName = input.nextLine();
        /** prompts for output file name and stores it's value */
		System.out.print("Enter output file name: ");
		String outputFileName = input.nextLine();
        /** initializes the dictionary */
		HashTable dict = new HashTable(HASH_TABLE_CAPACITY);
		try {
            /** creates a scanner for the dictionary file */
			Scanner dictionaryScanner = new Scanner(new File(dictionaryFileName));
            /** creates the output file and makes a printstream for it */
            PrintStream output = new PrintStream (new File(outputFileName));
            /** inserts all the words into the dictionary */
			while (dictionaryScanner.hasNext()) {
				dict.insert(dictionaryScanner.next());
			}
            /** creates a scanner for the input file */
            Scanner inputScanner = new Scanner(new File(inputFileName));
            /** spell checks all of the words in the input file */
            while (inputScanner.hasNext()) {
                String lookup = inputScanner.next();
                boolean spellCheck = spellCheck(lookup, dict);
                if (!spellCheck) {
                    if (lookup.length() > 1 && !Character.isLetter(lookup.charAt(lookup.length() - 1))) {
                        lookup = lookup.substring(0, lookup.length() - 1);
                    }
                    output.println(lookup);
                    misspelledWordTotal++;
                }
                wordTotal++;
            }
            /** prints out all the relevant data to the output file */
            output.println("Words in dictionary = " + dict.getSize());
            output.println("Words spell checked = " + wordTotal);
            output.println("Misspelled words = " + misspelledWordTotal);
            output.println("Total probes = " + dict.getProbes());
            output.println("Average probes per word = " + ((double) dict.getProbes() / wordTotal));
            output.println("Average probes per lookup = " + ((double) dict.getProbes() / lookupTotal));
			dictionaryScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist.");
		}		
		input.close();
	}
    
    /**
     * method used to spell check a given string against a given dictionary
     * @param lookup is the string to spell check
     * @param dict is the dictionary to check against
     * @return true if found in the dictionary or false if not
     */
    public static boolean spellCheck(String lookup, HashTable dict) {
        String spellCheck = null;
        String wordCheck = lookup;
        String lowerWordCheck = lookup.toLowerCase();
        /** the inital spell check */
        spellCheck = dict.lookup(wordCheck);
        lookupTotal++;
        if (spellCheck != null) {
            return true;
        }
        spellCheck = dict.lookup(lowerWordCheck);
        lookupTotal++;
        if (spellCheck != null) {
            return true;
        }
        if (!Character.isLetter(wordCheck.charAt(wordCheck.length() - 1))) {
            wordCheck = wordCheck.substring(0, wordCheck.length() - 1);
        }
        if (wordCheck.length() == 0) {
            return false;
        }
        /** spell check after removal of end punctuation */
        spellCheck = dict.lookup(wordCheck);
        lookupTotal++;
        if (spellCheck != null) {
            return true;
        }
        lowerWordCheck = wordCheck.toLowerCase();
        spellCheck = dict.lookup(lowerWordCheck);
        lookupTotal++;
        if (spellCheck != null) {
            return true;
        }
        /** spell check after removal of ending with 's */
        if (wordCheck.charAt(wordCheck.length() - 1) == 's' && wordCheck.charAt(wordCheck.length() - 2) == '\'') {
            wordCheck = wordCheck.substring(0, wordCheck.length() - 2);
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
        }
        /** spell check after removal of ending with s */
        if (wordCheck.charAt(wordCheck.length() - 1) == 's') {
            wordCheck = wordCheck.substring(0, wordCheck.length() - 1);
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            if (wordCheck.charAt(wordCheck.length() - 1) == 'e') {
                wordCheck = wordCheck.substring(0, wordCheck.length() - 1);
                lowerWordCheck = wordCheck.toLowerCase();
                spellCheck = dict.lookup(wordCheck);
                lookupTotal++;
                if (spellCheck != null) {
                    return true;
                }
                spellCheck = dict.lookup(lowerWordCheck);
                lookupTotal++;
                if (spellCheck != null) {
                    return true;
                }
                wordCheck += 'e';
                lowerWordCheck = wordCheck.toLowerCase();
                spellCheck = dict.lookup(wordCheck);
                lookupTotal++;
                if (spellCheck != null) {
                    return true;
                }
                spellCheck = dict.lookup(lowerWordCheck);
                lookupTotal++;
                if (spellCheck != null) {
                    return true;
                }
            }
        }
        /** spell check after removal of ending with ed */
        if (wordCheck.charAt(wordCheck.length() - 1) == 'd' && wordCheck.charAt(wordCheck.length() - 2) == 'e') {
            wordCheck = wordCheck.substring(0, wordCheck.length() - 2);
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            wordCheck += 'e';
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
        }
        /** spell check after removal of ending with er */
        if (wordCheck.charAt(wordCheck.length() - 1) == 'r' && wordCheck.charAt(wordCheck.length() - 2) == 'e') {
            wordCheck = wordCheck.substring(0, wordCheck.length() - 2);
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            wordCheck += 'e';
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
        }
        /** spell check after removal of ending with ing */
        if (wordCheck.charAt(wordCheck.length() - 1) == 'g' && wordCheck.charAt(wordCheck.length() - 2) == 'n' && wordCheck.charAt(wordCheck.length() - 3) == 'i') {
            wordCheck = wordCheck.substring(0, wordCheck.length() - 3);
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            wordCheck += 'e';
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
        }
        /** spell check after removal of ending with ly */
        if (wordCheck.charAt(wordCheck.length() - 1) == 'y' && wordCheck.charAt(wordCheck.length() - 2) == 'l') {
            wordCheck = wordCheck.substring(0, wordCheck.length() - 2);
            lowerWordCheck = wordCheck.toLowerCase();
            spellCheck = dict.lookup(wordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
            spellCheck = dict.lookup(lowerWordCheck);
            lookupTotal++;
            if (spellCheck != null) {
                return true;
            }
        }
        return false;
    }
}
