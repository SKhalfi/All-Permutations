import java.util.*;

public class all_permutations {

    public static long factorial(long number) {
        long total  = 1;

        while (number != 1) {
            total *= number;
            number -= 1;
        }

        return total;
    }

    public static void main(String[] args) {
        String word;
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        System.out.println(System.lineSeparator() + "\u001B[4mRejection Sampling Permutation Generator\u001B[0m");

        while (true) {
            System.out.print(System.lineSeparator() + "Enter a word (all characters are acceptable): ");
            word = input.nextLine();

            if (word.isEmpty()) {
                System.out.println(System.lineSeparator() + "Word must not be left empty.");
            }
            else {
                break;
            }
        }

        input.close();

        HashSet<String> permutations = new HashSet<>();
        HashMap<Character, Integer> letters = new HashMap<>();
        ArrayList<Character> uniqueKeys = new ArrayList<>();
        long denominator = 1;

        for (char letter : word.toCharArray()) {
            if (!letters.containsKey(letter)) {
                letters.put(letter, 1);
                uniqueKeys.add(letter);
            }
            else {
                letters.put(letter, letters.get(letter) + 1);
            }
        }

        for (int value: letters.values()) {
            denominator *= factorial(value);
        }

        long numOfCombinations = factorial(word.length()) /  denominator;

        System.out.println(System.lineSeparator() + "This word has " +  numOfCombinations + " combination(s).");

        while (permutations.size() < numOfCombinations) {
            HashMap<Character, Integer> lettersCopy = (HashMap<Character, Integer>) letters.clone();
            List<Character> uniqueKeysCopy = (ArrayList<Character>) uniqueKeys.clone();
            List<Character> generatedList = new ArrayList<>();
            StringBuilder generatedWord = new StringBuilder();
            List<Integer> indexes = new ArrayList<>();
            int randomIndex;
            char randomLetter;
            int numberPosition;
            int letterPosition;

            for (int i = 0; i < word.length(); i++) {
                generatedList.add(null);
                indexes.add(i);
            }

            for (int counter = 0; counter < word.length(); counter++) {

                numberPosition = random.nextInt(indexes.size());
                randomIndex = indexes.get(numberPosition);
                indexes.remove(numberPosition);

                letterPosition = random.nextInt(uniqueKeysCopy.size());
                randomLetter = uniqueKeysCopy.get(letterPosition);
                lettersCopy.put(randomLetter, lettersCopy.get(randomLetter) - 1);

                if (lettersCopy.get(randomLetter) == 0) {
                    lettersCopy.remove(randomLetter);
                    uniqueKeysCopy.remove(letterPosition);
                }

                generatedList.set(randomIndex, randomLetter);
            }

            for (char letter: generatedList) {
                generatedWord.append(letter);
            }

            permutations.add(generatedWord.toString());
        }

        System.out.println(System.lineSeparator() + "All permutations: " + System.lineSeparator() + permutations);
    }
}