# All-Permutations

A console-based project of two different types of Permutation Generator algorithms performed on a given word.

Both algorithms are written in Python, Java, and C++ to allow users to compare the performance in these programming languages.

## What is a Permutation Generator?

A permutation generator is an algorithm that lists every possible order for a group of items.

In the case of this project, the items that are being used for this process are letters from a user-given word.

## The algorithm types that are being compared:

- [Random sampling](#how-the-random-sampling-algorithm-works)

- [Lexicographical ordering](#how-the-lexicographical-algorithm-works)

## General overview of how both algorithms work

The algorithm begins by prompting the user to enter a word of their choice and calculates the number of permutations that can be produced using the word, factoring in repeated letters before starting the main loop inside the `permutation_generator()` function which is the most taxing section of the whole algorithm.

Within the main loop it starts to generate permutations of the word provided and ends when all unique permutations have been discovered.

When this is the case, a timer displays how long the algorithm took to finish.

This timer strictly measures the `permutation_generator()` function and does not include the time it takes for the program to calculate the number of permutations or how long it takes for all permutations to be printed to the console.

## How the Random Sampling algorithm works

The Random Sampling algorithm starts with an empty list with the same number of elements as there are in the user's word.

The algorithm then uses a dictionary (map) to keep track of the characters in the user's word as well as the number of times each character appears.

On each loop, elements are randomly selected from the dictionary and inserted into random indexes in the empty list.

When the empty list is completely full, it is converted into a string and inserted into a set as a new permutation. 

See the flowchart below for a visual example of the Random Sampling algorithm.

### Random Sampling example:

```mermaid
flowchart TD
    Input["user_input = 'abcdef'"] --> |"start with an empty list of size user_input"| A["[_, _, _, _, _, _]"]
    A --> |"place 'c' at index 1"| B["[_, c, _, _, _, _]"]
    B --> |"place 'b' at index 4"| C["[_, c, _, _, b, _]"]
    C --> |"place 'f' at index 2"| D["[_, c, f, _, b, _]"]
    D --> |"place 'd' at index 5"| E["[_, c, f, _, b, d]"]
    E --> |"place 'e' at index 0"| F["[e, c, f, _, b, d]"]
    F --> |"place 'a' at index 3\n(no other choice)"| G["[e, c, f, a, b, d]"]
    G --> |"final result"| Output["new_permutation = 'ecfabd'"]

    classDef endpoint fill:#d4edda, stroke:#28a745, stroke-width:2px
    class Input,Output endpoint
```

## How the Lexicographical algorithm works

From a given word, the Lexicographical algorithm converts the string into a main list of characters. This main list is then scanned from right to left to locate a pivot.

A pivot is found if the element currently being scanned is lexicographically smaller than its right neighbour.

Using this pivot element a suffix can be defined as a sublist of elements from the main list. This suffix consists of all elements after the pivot.

From the suffix, a successor element can be determined by scanning the suffix from right to left and finding the first element that is greater than the pivot element.

When a successor element has been found, it is swapped with the pivot element and the suffix is reversed, resulting in the new permutation.

Before the main loop begins, the user's word must be sorted in ascending order otherwise all permutations will not be discovered.

This is due to the nature of the algorithm using all elements being sorted in ascending order as the starting permutation, and all elements being sorted in descending order as the ending permutation.

This also means that the Lexicographical algorithm does not produce duplicate permutations as the random sampling algorithm does.

As a result, all files in "All-Permutations/Lexicographical-PGs/" use a Tim sort algorithm on the user's word before discovering all permutations.

See the flowchart below for a visual example of the Lexicographical algorithm. In the example, the user input is not sorted in ascending order for illustrative purposes.

### Lexicographical example:

```mermaid
flowchart TD
    Input["user_input = '13542'"] --> |"convert the string into a list of characters"| A["[1, 3, 5, 4, 2]"]
    A --> |"`scan from right to left to find the ***pivot*** which is the first element smaller than its right neighbour`"| B["`[1, ***3***, 5, 4, 2]`"]
    B --> |"`The ***suffix*** is everything after the pivot (3)`"| C["`[1, 3, ***5, 4, 2***]`"]
    C --> |"`isolate the suffix and scan from right to left for the ***successor*** which is the first element greater than the pivot`"| D["`[5, ***4***, 2]`"]
    D --> |"Swap the pivot (3) and the successor (4)"| E["[1, 4, 5, 3, 2]"]
    E --> |"`reverse the ***suffix***`"| F["`[1, 4, ***2, 3, 5***]`"]
    F --> |"final result"| Output["new_permutation = '14235'"]

    classDef endpoint fill:#d4edda, stroke:#28a745, stroke-width:2px
    class Input,Output endpoint
```

## License

This project is licensed under the MIT License — see [LICENSE](LICENSE) for details.

Credit is appreciated but not required.
