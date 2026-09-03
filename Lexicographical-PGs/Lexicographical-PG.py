import math
import time

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

def find_new_pivot(word_list: list[str]) -> int:

    for counter in range(-1, -len(word_list) - 1, -1): # Read the list from right to left

        if counter != -len(word_list): # If it has reached the last element from right to left, stop looping to avoid out of range exception
            if word_list[counter - 1] < word_list[counter]: # Check if the next element is less than the current element, if it is then that is the new pivot 
                pivot : int = counter - 1
                return pivot
        else:
            return None # None indicates that the list is in reverse order

def find_new_successor (
        suffix : list[str],
        pivot_letter : str
    ) -> int:

    for counter in range(-1, -len(suffix) - 1, -1): # Read the list from right to left

        if suffix[counter] > pivot_letter: # Find the next element in suffix that is greater than the pivot element
            return counter

def swap_pivot_and_successor (
        pivot : int,
        successor : int,
        word_list : list[str]
    ) -> None:

    temp : str = word_list[pivot]
    
    word_list[pivot] = word_list[successor]
    
    word_list[successor] = temp

def permutation_generator (
        word : str,
        permutations : list[str],
        num_of_permutations : int
    ) -> None:

    word_list : list[str] = sorted(word)

    while len(permutations) < num_of_permutations: # Starting permutation loop

        pivot : int = find_new_pivot(word_list) # find_new_pivot can return None if word_list is in descending order

        if pivot != None:
            
            successor : int = find_new_successor(word_list[pivot + 1:], word_list[pivot])
            
            swap_pivot_and_successor(pivot, successor, word_list)
            
            word_list[pivot + 1:] = word_list[pivot + 1:][::-1] # Reverse the suffix (suffix is word_list[pivot + 1:])
        else: # If word_list is in descending order, reverse it
            word_list = word_list[::-1]
            
        permutations.append("".join(word_list))

def main() -> None:

    print("\n\033[4mLexicographical Permutation Generator (Python)\033[0m")

    word : str = input_from_user()

    permutations : list[str] = [] # Permutations are served in a set which automatically handle repeated values
        
    letters : dict[str, int] = dict() # All letters within the user's word are handled through a dictionary (map)
    
    initialise_letters(letters, word)
    
    num_of_permutations : int = calculate_num_of_permutations(letters, word)
    
    print(f"\nThis word has {num_of_permutations} permutation(s).")

    start_time : float = time.perf_counter()

    permutation_generator(word, permutations, num_of_permutations)

    end_time : float = time.perf_counter()

    print(f"\nAll permutations:\n {permutations}")

    print(f"\nThe Rejection Sampling PG took {(end_time - start_time):.5f} seconds to find all permutations.\n")

if __name__ == "__main__":
    main()