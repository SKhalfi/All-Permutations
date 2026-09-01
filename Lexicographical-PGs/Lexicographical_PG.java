import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Lexicographical_PG {

    public static BigInteger factorial(long number) {
        
        BigInteger total = BigInteger.ONE;

        while (number > 1) {
            total = total.multiply(BigInteger.valueOf(number));
            number -= 1;
        }

        return total;
    }

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

    public static void main(String[] args) {
        
        System.out.println("%n\u001B[4mRejection Sampling Permutation Generator (Java)\u001B[0m".formatted());

        String word = inputFromUser();

        HashSet<String> permutations = new HashSet<>(); // Permutations are served in a set which automatically handle repeated values

        permutations.add(word);

        HashMap<Character, Integer> letters = initialiseLetters(word); // All letters within the user's word are handled through a dictionary (map)

        BigInteger numOfPermutations = calculateNumOfPermutations(letters, word);

        System.out.println("%nThis word has %d permutation(s).".formatted(numOfPermutations));

    }
}
