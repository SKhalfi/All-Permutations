import random
import math

print("\n\033[4mRejection Sampling Permutation Generator (Python)\033[0m")

while True: # Continues looping until valid word is retrieved from user
    word = input("\nEnter a word (all characters are acceptable): ")

    if len(word) == 0:
        print("\nWord must not be left empty.")

    else:
        break

word = word.strip()
permutations = {word} # Permutations are served in a set which automatically handle repeated values
letters = dict() # All letters within the user's word are handled through a dictionary (map)
denominator = 1

for letter in word:
    if letter not in letters: # Add a new entry to letters if one does not exist
        letters[letter] = 1
    else:
        letters[letter] += 1 # Increment the existing entry in letters

for value in letters.values(): # Loop through all values in letters to account for characters that appear more that once and update the denominator accordingly
    denominator *= math.factorial(value)

no_of_combinations = math.factorial(len(word)) / denominator # Calculate number of permutations

print("\nThis word has", int(no_of_combinations), "combination(s).")

while len(permutations) < no_of_combinations: # Begin rejection sampling loop
    letters_copy = letters.copy()
    generated_word = []
    index_exceptions = []

    for i in range(len(word)): # Initialise generated_word list with placeholder values
        generated_word.append(None)

    for counter in range(len(word)):

        # Find a random location to place a letter in the generated_word list
        random_index = random.choice([i for i in range(0, len(word)) if i not in index_exceptions])
        index_exceptions.append(random_index)

        # Select a random letter from the dictionary to place into the generated_word list
        random_letter = random.choice([key for key in letters_copy.keys()])
        letters_copy[random_letter] -= 1

        if letters_copy[random_letter] == 0: # Remove character from letters_copy if its value is 0
            del letters_copy[random_letter]

        generated_word[random_index] = random_letter # Add the random letter to the generated_word list at a random index

    generated_word = "".join(generated_word) # Convert the list to a string

    permutations.add(generated_word)

print("\nAll permutations:\n", permutations)