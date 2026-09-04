import java.math.BigInteger;
import java.util.*;

public class Rejection_Sampling_PG {

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

            System.out.print("%nEnter a word (all characters are acceptable): ".formatted());
            word = input.nextLine().strip();

            if (word.isEmpty()) {
                System.out.println("%nWord must not be left empty.".formatted());
            }

            else {
                break;
            }
        }

        input.close();

        return word;
    }

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
     * Initialises the letters dictionary with a character as the key and an integer as the value
     * @param word A valid word string from the inputFromUser() function
     * @return The letters dictionary containing the count of each letter from the word string
     */
    public static BigInteger calculateNumOfPermutations (
        HashMap<Character, Integer> letters,
        String word
    ) {
        
        BigInteger denominator = BigInteger.ONE;

        for (long value: letters.values()) { // Loop through all values in letters to account for characters that appear more that once and update the denominator accordingly
            denominator = denominator.multiply(factorial(value));
        }

        BigInteger numOfPermutations = factorial(word.length()).divide(denominator); // Calculate number of permutations

        return numOfPermutations;

    }

    /**
     * A function that generates permutations by using random sampling
     * @param permutations A set which is appended to with new permutations
     * @param numOfPermutations The number of permutations that can be created with the user's word
     * @param letters A dictionary that contains the count of each letter in word
     * @param uniqueKeys A list that contains letters that only appear once in the user's word
     * @param word A valid word from the user
     * @return The permutations set with all unique permutations
     */
    public static HashSet<String> permutationGenerator (
        HashSet<String> permutations,
        BigInteger numOfPermutations,
        HashMap<Character, Integer> letters,
        Set<Character> uniqueKeys,
        String word
    ) {

        while (BigInteger.valueOf(permutations.size()).compareTo(numOfPermutations) < 0) { // Begin rejection sampling loop

            HashMap<Character, Integer> lettersCopy = new HashMap<>(letters);
            List<Character> uniqueKeysCopy = new ArrayList<Character>(uniqueKeys);
            ArrayList<Character> generatedList = new ArrayList<>();
            StringBuilder generatedWord = new StringBuilder();
            List<Integer> indexes = new ArrayList<>();

            for (int i = 0; i < word.length(); i++) { // Initialise generatedList with placeholder values
                generatedList.add(null);
                indexes.add(i);
            }

            generatedList = generatingWord(word, indexes, uniqueKeysCopy, lettersCopy, generatedList);

            for (char letter: generatedList) { // Append all letters in generatedList to generatedWord
                generatedWord.append(letter);
            }

            permutations.add(generatedWord.toString());
        }

        return permutations;
    }

    /**
     * Generates permutations by randoming selecting an index in a list and inserting a random letter into that location
     * @param word A valid word from the user
     * @param indexes A list containing the indexes that are free in generatedList
     * @param uniqueKeysCopy A copy of the uniqueKeys list
     * @param lettersCopy A copy of the letters dictionary
     * @param generatedList A list containing null values at the start and is filled with random letters from the user's word
     * @return generatedList
     */
    public static ArrayList<Character> generatingWord (
        String word,
        List<Integer> indexes,
        List<Character> uniqueKeysCopy,
        HashMap<Character, Integer> lettersCopy,
        ArrayList<Character> generatedList
    ) {

        Random random = new Random();

        int randomIndex;
        char randomLetter;
        int numberPosition;
        int letterPosition;

        for (int counter = 0; counter < word.length(); counter++) {

            // Find a random location to place a letter in generatedList
            numberPosition = random.nextInt(indexes.size());
            randomIndex = indexes.get(numberPosition);
            indexes.remove(numberPosition);

            // Select a random letter from the map to place into generatedList
            letterPosition = random.nextInt(uniqueKeysCopy.size());
            randomLetter = uniqueKeysCopy.get(letterPosition);
            lettersCopy.put(randomLetter, lettersCopy.get(randomLetter) - 1);

            if (lettersCopy.get(randomLetter) == 0) { // Remove character from lettersCopy and uniqueKeysCopy if its value is 0
                lettersCopy.remove(randomLetter);
                uniqueKeysCopy.remove(letterPosition);
            }

            generatedList.set(randomIndex, randomLetter); // Add the random letter to the generatedList at a random index
        }

        return generatedList;
    }

    public static void main(String[] args) {

        System.out.println("%n\u001B[4mRejection Sampling Permutation Generator (Java)\u001B[0m".formatted());
        
        String word = inputFromUser();

        HashSet<String> permutations = new HashSet<>(); // Permutations are served in a set which automatically handle repeated values

        permutations.add(word);

        HashMap<Character, Integer> letters = initialiseLetters(word); // All letters within the user's word are handled through a dictionary (map)

        BigInteger numOfPermutations = calculateNumOfPermutations(letters, word);

        System.out.println("%nThis word has %d permutation(s).".formatted(numOfPermutations));

        long startTime = System.nanoTime();

        permutations = permutationGenerator(permutations, numOfPermutations, letters, letters.keySet(), word);

        long endTime = System.nanoTime();

        System.out.println("%nAll permutations:%n%s".formatted(permutations));

        double durationInSecond = (endTime - startTime) / 1000000000.0;

        System.out.println("%nThe Rejection Sampling PG took %.3f seconds to find all permutations.%n".formatted(durationInSecond));
    }
}