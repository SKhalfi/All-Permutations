import java.util.*;

public class Rejection_Sampling_PG {

    public static long factorial(long number) {
        long total = 1;

        while (number > 1) {
            total *= number;
            number -= 1;
        }

        return total;
    }

    public static String inputFromUser() {

        String word;
        Scanner input = new Scanner(System.in);

        while (true) { // Continues looping until valid word is retrieved from user
            System.out.print(System.lineSeparator() + "Enter a word (all characters are acceptable): ");
            word = input.nextLine().strip();

            if (word.isEmpty()) {
                System.out.println(System.lineSeparator() + "Word must not be left empty.");
            }

            else {
                break;
            }
        }

        input.close();

        return word;
    }

    public static HashSet<String> permutationGenerator (
        HashSet<String> permutations,
        long numOfCombinations,
        HashMap<Character, Integer> letters,
        ArrayList<Character> uniqueKeys,
        String word
    ) {

        while (permutations.size() < numOfCombinations) { // Begin rejection sampling loop

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

        System.out.println(System.lineSeparator() + "\u001B[4mRejection Sampling Permutation Generator (Java)\u001B[0m");
        
        String word = inputFromUser();
        HashSet<String> permutations = new HashSet<>(); // Permutations are served in a set which automatically handle repeated values
        HashMap<Character, Integer> letters = new HashMap<>(); // All letters within the user's word are handled through a dictionary (map)
        ArrayList<Character> uniqueKeys = new ArrayList<>(); // A list of all unique keys in the letters map
        long denominator = 1;

        for (char letter : word.toCharArray()) {
            if (!letters.containsKey(letter)) { // Add a new entry to letters if one does not exist
                letters.put(letter, 1);
                uniqueKeys.add(letter);
            }
            else {
                letters.put(letter, letters.get(letter) + 1); // Increment the existing entry in letters
            }
        }

        for (int value: letters.values()) { // Loop through all values in letters to account for characters that appear more that once and update the denominator accordingly
            denominator *= factorial(value);
        }

        long numOfCombinations = factorial(word.length()) /  denominator; // Calculate number of permutations

        System.out.println(System.lineSeparator() + "This word has " +  numOfCombinations + " combination(s).");

        permutations = permutationGenerator(permutations, numOfCombinations, letters, uniqueKeys, word);

        System.out.println(System.lineSeparator() + "All permutations: " + System.lineSeparator() + permutations);
    }
}