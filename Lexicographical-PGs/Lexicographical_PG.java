import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Lexicographical_PG {

    /**
     * Calculates the factorial of a given non-negative number
     * @param number A non-negative number of type long
     * @return The result of (number * number - 1 * number - 2)
     */
    public static BigInteger factorial(long number) {
        
        assert (number > -1) : "Number must be non-negative.";

        BigInteger total = BigInteger.ONE;

        while (number > 1) {
            total = total.multiply(BigInteger.valueOf(number));
            number -= 1;
        }

        return total;
    }

    /**
     * Uses a scanner to retrieve a valid string input from the user, checking if the input is empty, and removing leading and trailing whitespace
     * @return The validated string input from the user
     */
    public static String inputFromUser() {

        String word;
        Scanner input = new Scanner(System.in);

        while (true) { // Continues looping until valid word is retrieved from user

            System.out.printf("%nEnter a word (all characters are acceptable): ");
            word = input.nextLine().strip();

            if (word.isEmpty()) {
                System.out.printf("%nWord must not be left empty.%n");
            }

            else {
                break;
            }
        }

        input.close();

        return word;
    }

    /**
     * Initialises the letters dictionary with a character as the key and an integer as the value
     * @param word A valid word string from the inputFromUser() function
     * @return The letters dictionary containing the count of each letter from the word string
     */
    public static HashMap<Character, Integer> initialiseLetters (String word) {

        HashMap<Character, Integer> letters = new HashMap<>();
        
        for (char letter : word.toCharArray()) {

            if (!letters.containsKey(letter)) { // Add a new entry to letters if one does not exist
                letters.put(letter, 1);
            }
            else {
                letters.put(letter, letters.get(letter) + 1); // Increment the existing entry in letters
            }
        }

        return letters;
    }

    /**
     * Calculates the number of permutations that can be generated from a given word
     * @param letters A dictionary that contains the count of each letter in the 'word' variable
     * @param word A valid word from the user
     * @return A number of type BigInteger that represents the number of permutations that can be generated from a given word
     */
    public static BigInteger calculateNumOfPermutations (
        HashMap<Character, Integer> letters,
        String word
    ) {
        
        BigInteger denominator = BigInteger.ONE;

        for (int value: letters.values()) { // Loop through all values in letters to account for characters that appear more than once and update the denominator accordingly
            denominator = denominator.multiply(factorial(value));
        }

        BigInteger numOfPermutations = factorial(word.length()).divide(denominator); // Calculate number of permutations

        return numOfPermutations;

    }

    /**
     * Reads the wordList from right to left checking if the next element is lexicographically smaller than the current element,
     * if that is the case, then the next element is the pivot
     * @param wordList A list of containing the characters of the user's word
     * @return The index of a new pivot location in wordList
     */
    public static Integer findNewPivot(List<Character> wordList) {

        for (int counter = wordList.size() - 1; counter > -1; counter--) {

            if (counter != 0) {
                if (wordList.get(counter - 1) < wordList.get(counter)) {
                    return counter - 1;
                }
            }
            else {
                return null;
            }

        }
        
        return null;
    }

    /**
     * Finds a character that is lexicographically greater than the pivot in a sublist of wordList
     * @param suffix A sublist of wordList containing all characters before the pivot (reading from right to left)
     * @param pivotLetter The element at the location of the pivot point
     * @return The location of the successor as an offset from the pivot index
     */
    public static Integer findNewSuccessorOffset (
        List<Character> suffix,
        char pivotLetter
    ) {

        for (int counter = suffix.size() - 1; counter > -1; counter--) {

            if (suffix.get(counter) > pivotLetter) {
                return counter + 1;
            }
        }

        return null;
    }

    /**
     * Swaps the element at the pivot with the element at the successor in wordList
     * @param pivot Index location of the pivot
     * @param successor Index location of the successor
     * @param wordList A list of containing the characters of the user's word
     */
    public static void swapPivotAndSuccessor (
        int pivot,
        int successor,
        List<Character> wordList
    ) {

        char temp = wordList.get(pivot);

        wordList.set(pivot, wordList.get(successor));

        wordList.set(successor, temp);
    }

    /**
     * A function that generates permutations lexicographically
     * @param word A valid word from the user
     * @param permutations A list which is appended to with new permutations
     * @param numOfPermutations The number of permutations that can be created with the user's word
     */
    public static void permutationGenerator (
        String word,
        List<String> permutations,
        BigInteger numOfPermutations
    ) {

        List<Character> wordList = new ArrayList<>();

        for (char letter : word.toCharArray()) {
            wordList.add(letter);
        }

        wordList.sort(null);

        while (BigInteger.valueOf(permutations.size()).compareTo(numOfPermutations) < 0) {

            Integer pivot = findNewPivot(wordList);

            if (pivot != null) {
                
                Integer successorOffset = findNewSuccessorOffset(wordList.subList(pivot + 1, wordList.size()), wordList.get(pivot));

                int successor = pivot + successorOffset;

                swapPivotAndSuccessor(pivot, successor, wordList);

                Collections.reverse(wordList.subList(pivot + 1, wordList.size()));

            }
            else {
                Collections.reverse(wordList);
            }

            StringBuilder newWord = new StringBuilder();

            for (char letter : wordList) {
                newWord.append(letter);
            }

            permutations.add(newWord.toString());
            
        }
    }

    public static void main(String[] args) {
        
        System.out.printf("%n\u001B[4mLexicographical Permutation Generator (Java)\u001B[0m%n");

        System.out.printf("%nWarning: Words with many unique characters grow permutation counts factorially. Very long or highly varied input may take a long time and user significant memory.%n");

        String word = inputFromUser();

        List<String> permutations = new ArrayList<>(); // Permutations are served as a list

        HashMap<Character, Integer> letters = initialiseLetters(word); // All letters within the user's word are handled through a dictionary (map)

        BigInteger numOfPermutations = calculateNumOfPermutations(letters, word);

        System.out.printf("%nThis word has %d permutation(s).%n", numOfPermutations);

        long startTime = System.nanoTime();

        permutationGenerator(word, permutations, numOfPermutations);

        long endTime = System.nanoTime();

        System.out.printf("%nAll permutations:%n%s%n", permutations);

        double durationInSecond = (endTime - startTime) / 1000000000.0;

        System.out.printf("%nThe Lexicographical PG took %.5f seconds to find all permutations.%n%n", durationInSecond);
    }
}
