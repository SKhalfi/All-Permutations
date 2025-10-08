import random
import math
import enchant

while True:
    word = input("Enter a word: ")

    if len(word) == 0:
        print("Word must not be left empty.\n")

    else:
        break

word = word.strip()
permutations = {word}
letters = dict()
denominator = 1
d = enchant.Dict("en_UK")
english_words = []

if d.check(word.lower()):
    english_words.append(word)

for letter in word:
    if letter not in letters:
        letters[letter] = 1
    else:
        letters[letter] += 1

for value in letters.values():
    denominator *= math.factorial(value)

no_of_combinations = math.factorial(len(word)) / denominator

print("This word has", int(no_of_combinations), "combination(s).")

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

    if d.check(generated_word.lower()):
        english_words.append(generated_word)

print("All permutations:\n", permutations)
print("All english words:\n", english_words)