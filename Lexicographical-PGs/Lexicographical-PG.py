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

def initialise_letters (
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

def main() -> None:

    print("\n\033[4mLexicographical Permutation Generator (Python)\033[0m")

    word : str = input_from_user()

    permutations : set[str] = {word} # Permutations are served in a set which automatically handle repeated values
        
    letters : dict[str, int] = dict() # All letters within the user's word are handled through a dictionary (map)
    
    initialise_letters(letters, word)
    
    num_of_permutations : int = calculate_num_of_permutations(letters, word)
    
    print(f"\nThis word has {num_of_permutations} permutation(s).")
    

if __name__ == "__main__":
    main()