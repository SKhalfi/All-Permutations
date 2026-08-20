import random
import math

print("\n\033[4mRejection Sampling Permutation Generator\033[0m")

while True:
    word = input("\nEnter a word: ")

    if len(word) == 0:
        print("\nWord must not be left empty.")

    else:
        break

word = word.strip()
permutations = {word}
letters = dict()
denominator = 1

for letter in word:
    if letter not in letters:
        letters[letter] = 1
    else:
        letters[letter] += 1

for value in letters.values():
    denominator *= math.factorial(value)

no_of_combinations = math.factorial(len(word)) / denominator

print("\nThis word has", int(no_of_combinations), "combination(s).")

while len(permutations) < no_of_combinations:
    letters_copy = letters.copy()
    generated_word = []
    index_exceptions = []

    for i in range(len(word)):
        generated_word.append(None)

    for counter in range(len(word)):

        # Finding a random location to place a letter in the generated_word list
        random_index = random.choice([i for i in range(0, len(word)) if i not in index_exceptions])
        index_exceptions.append(random_index)

        # Selecting a random letter from the dictionary to place into the generated_word list
        random_letter = random.choice([key for key in letters_copy.keys()])
        letters_copy[random_letter] -= 1

        if letters_copy[random_letter] == 0:
            del letters_copy[random_letter]

        generated_word[random_index] = random_letter

    generated_word = "".join(generated_word)

    permutations.add(generated_word)

print("\nAll permutations:\n", permutations)