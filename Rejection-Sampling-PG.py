import random
import math

def input_from_user() -> str:

    while True: # Continues looping until valid word is retrieved from user
        word : str = input("\nEnter a word (all characters are acceptable): ")
        word = word.strip()

        if len(word) == 0:
            print("\nWord must not be left empty.")

        else:
            break

    return word

def initalise_letters (
        letters: dict[str, int],
        word : str
    ) -> None:

    for letter in word:

        if letter not in letters: # Add a new entry to letters if one does not exist
            letters[letter] = 1
        else:
            letters[letter] += 1 # Increment the existing entry in letters

def calculate_num_of_permutations (
        letters: dict[str, int],
        word : str
    ) -> int:

    denominator : int = 1

    for value in letters.values(): # Loop through all values in letters to account for characters that appear more that once and update the denominator accordingly
        denominator *= math.factorial(value)
    
    num_of_permutations : int = math.factorial(len(word)) // denominator # Calculate number of permutations

    return num_of_permutations

def permutation_generator (
        permutations : set[str],
        num_of_permutations : int,
        letters : dict[str, int],
        word : str
    ) -> None:

    while len(permutations) < num_of_permutations: # Begin rejection sampling loop

        letters_copy : dict[str, int] = letters.copy()
        generated_word : list[str] = [""] * len(word) # Initialise generated_word list with placeholder empty strings

        generating_word(generated_word, letters_copy, word)

        permutations.add("".join(generated_word)) # Convert the generated_word list to a string and append the string to permutations

def generating_word (
        generated_word : list[str],
        letters_copy : dict[str, int],
        word : str
    ) -> None:

    used_indexes : list[int] = []

    for letter in word:
    
        # Find a random location to place a letter in the generated_word list
        random_index : int = random.choice([i for i in range(0, len(word)) if i not in used_indexes])
        used_indexes.append(random_index)

        # Select a random letter from the dictionary to place into the generated_word list
        random_letter : str = random.choice([key for key in letters_copy.keys()])
        letters_copy[random_letter] -= 1
    
        if letters_copy[random_letter] == 0: # Remove character from letters_copy if its value is 0
            del letters_copy[random_letter]
    
        generated_word[random_index] = random_letter # Add the random letter to the generated_word list at a random index

def main():

    print("\n\033[4mRejection Sampling Permutation Generator (Python)\033[0m")

    word : str = input_from_user()

    permutations : set[str] = {word} # Permutations are served in a set which automatically handle repeated values
    
    letters : dict[str, int] = dict() # All letters within the user's word are handled through a dictionary (map)

    initalise_letters(letters, word)

    num_of_permutations : int = calculate_num_of_permutations(letters, word)

    print(f"\nThis word has {num_of_permutations} permutation(s).")

    permutation_generator(permutations, num_of_permutations, letters, word)

    print(f"\nAll permutations:\n {permutations}")

if __name__ == "__main__":
    main()