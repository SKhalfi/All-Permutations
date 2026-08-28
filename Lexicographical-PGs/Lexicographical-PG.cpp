#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <format>
#include <algorithm>
#include <chrono>

using namespace std;

void strip(string &str) {

    const string whitespace = " \t\n\r\f\v"; // ASCII representation of whitespace

    const size_t start = str.find_first_not_of(whitespace); // If this method fails to find a character that is not whitespace, then it will return npos

    if (start == string::npos) { // Check if the string is completely empty or full of spaces
        str = "";
    }

    else {
        const size_t end = str.find_last_not_of(whitespace);

        str = str.substr(start, end - start + 1); // // Extract the word from the whitespaces
    }
}

unsigned long long factorial(unsigned long long number) {
    unsigned long long total = 1;

    while (number > 1) {
        total *= number;
        number -= 1;
    }
    return total;
}

void input_from_user(string &word) { // Continues looping until valid word is retrieved from user

    while (true) {

        cout << "\nEnter a word (all characters are acceptable): ";
        getline(cin, word);
        strip(word);

        if (word.empty()) {
            cout << "Word must not be left empty.\n";
        }

        else {
            break;
        }
    }
}

void initialise_letters (
    unordered_map<char, int> &letters,
    string &word
    ) {

    for (char letter: word) {

        if (letters.count(letter) == 0) { // Add a new entry to letters if one does not exist
            letters[letter] = 1;
        }
        else {
            letters[letter] += 1; // Increment the existing entry in letters
        }
    }
}

void calculate_num_of_permutations (
    unordered_map<char, int> &letters,
    string &word,
    unsigned long long &num_of_permutations
    ) {

    unsigned long long denominator = 1;

    for (const auto &value: letters) { // Loop through all values in letters to account for characters that appear more that once and update the denominator accordingly
        denominator *= factorial(value.second);
    }

    num_of_permutations = factorial( word.length()) / denominator; // Calculate number of permutations
}

void permutation_generator (
    string &word,
    unordered_set<string> &permutations
    ) {

    ranges::sort(word);

    permutations.insert(word);

    while (ranges::next_permutation(word).found) {
        permutations.insert(word);
    }

}

int main() {

    cout << "\nLexicographical Permutation Generator (C++)\n";
    cout << "-------------------------------------------\n";

    string word;

    input_from_user(word);

    unordered_set<string> permutations = {}; // Permutations are served as a set

    unordered_map<char, int> letters; // All letters within the user's word are handled through a dictionary (map)

    initialise_letters(letters, word);

    unsigned long long num_of_permutations;

    calculate_num_of_permutations(letters, word, num_of_permutations);

    cout << format("This word has {} permutation(s).\n", num_of_permutations);

    const auto start_time = chrono::high_resolution_clock::now();

    permutation_generator(word, permutations);

    const auto end_time = chrono::high_resolution_clock::now();

    cout << "\nAll permutations:\n";

    for (const string &permutation: permutations) {
        cout << format("{}, ", permutation);
    }

    chrono::duration<double, milli> duration_in_seconds = end_time - start_time;

    duration_in_seconds /= 1000;

    cout << format("\nThe Lexicographical PG took {:.5f} seconds to find all permutations.\n", duration_in_seconds.count());

}