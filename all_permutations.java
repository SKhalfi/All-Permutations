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

        while (true) {
            System.out.print("Enter a word: ");
            word = input.nextLine();

            if (word.isEmpty()) {
                System.out.println("Word must not be left empty." + System.lineSeparator());
            }
            else {
                break;
            }
        }

        HashSet<String> permutations = new HashSet<>();
        HashMap<Character, Integer> letters = new HashMap<>();
        ArrayList<Character> unique_keys = new ArrayList<>();
        long denominator = 1;

        for (char letter : word.toCharArray()) {
            if (!letters.containsKey(letter)) {
                letters.put(letter, 1);
                unique_keys.add(letter);
            }
            else {
                letters.put(letter, letters.get(letter) + 1);
            }
        }

        for (int value: letters.values()) {
            denominator *= factorial(value);
        }

        long no_of_combinations = factorial(word.length()) /  denominator;

        System.out.println("This word has " +  no_of_combinations + " combination(s).");

        while (permutations.size() < no_of_combinations) {
            HashMap<Character, Integer> letters_copy = (HashMap<Character, Integer>) letters.clone();
            ArrayList<Character> unique_keys_copy = (ArrayList<Character>) unique_keys.clone();
            ArrayList<Character> generated_arraylist = new ArrayList<>();
            StringBuilder generated_word = new StringBuilder();
            ArrayList<Integer> indexes = new ArrayList<>();
            int random_index;
            char random_letter;
            int number_pos;
            int letter_pos;

            for (int i = 0; i < word.length(); i++) {
                generated_arraylist.add(null);
                indexes.add(i);
            }

            for (int counter = 0; counter < word.length(); counter++) {

                number_pos = random.nextInt(indexes.size());
                random_index = indexes.get(number_pos);
                indexes.remove(number_pos);

                letter_pos = random.nextInt(unique_keys_copy.size());
                random_letter = unique_keys_copy.get(letter_pos);
                letters_copy.put(random_letter, letters_copy.get(random_letter) - 1);

                if (letters_copy.get(random_letter) == 0) {
                    letters_copy.remove(random_letter);
                    unique_keys_copy.remove(letter_pos);
                }

                generated_arraylist.set(random_index, random_letter);
            }

            for (char letter: generated_arraylist) {
                generated_word.append(letter);
            }

            permutations.add(generated_word.toString());
        }

        System.out.println("All permutations: " + System.lineSeparator() + permutations);
    }
}