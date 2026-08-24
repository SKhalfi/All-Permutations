#include <iostream>
#include <random>
#include <unordered_map>
#include <vector>
#include <algorithm>
#include <unordered_set>
#include <format>

using namespace std;

random_device rd;
mt19937 gen(rd());

unsigned long long random (
    unsigned long long lower_bound,
    unsigned long long upper_bound
    ) {
    uniform_int_distribution<unsigned long long> dist(lower_bound, upper_bound);
    return dist(gen);
}

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

        cout << "\nEnter a word: ";
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

void initalise_letters (
    unordered_map<char, int> &letters,
    string &word,
    vector<char> &unique_keys
    ) {

    for (char letter: word) {

        if (letters.count(letter) == 0) { // Add a new entry to letters if one does not exist
            letters[letter] = 1;
            unique_keys.push_back(letter);
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

    for (auto value: letters) { // Loop through all values in letters to account for characters that appear more that once and update the denominator accordingly
        denominator *= factorial(value.second);
    }

    num_of_permutations = factorial( word.length()) / denominator; // Calculate number of permutations
}

void generating_word (
    string &word,
    vector<int> &indexes,
    vector<char> &unique_keys_copy,
    unordered_map<char, int> &letters_copy,
    vector<char> &generated_vector
    ) {

    int random_index = 0;
    char random_letter = '\0';
    unsigned long long number_pos = 0;
    unsigned long long letter_pos = 0;

    for (int counter = 0; counter < word.length(); counter++) {

        // Find a random location to place a letter in generated_vector
        number_pos = random(0, (indexes.size() - 1));
        random_index = indexes.at(number_pos);
        indexes.erase(indexes.begin() + number_pos);

        // Select a random letter from the map to place into generated_vector
        letter_pos = random(0, (unique_keys_copy.size() - 1));
        random_letter = unique_keys_copy.at(letter_pos);
        letters_copy.at(random_letter) -= 1;

        if (letters_copy.at(random_letter) == 0) { // Remove character from letters_copy and unique_keys_copy if its value is 0
            letters_copy.erase(random_letter);
            unique_keys_copy.erase(unique_keys_copy.begin() + letter_pos);
        }

        generated_vector.at(random_index) = random_letter; // Add the random letter to the generated_vector at a random index
    }
}

void permutation_generator (
    unordered_set<string> &permutations,
    const unsigned long long &no_of_combinations,
    unordered_map<char, int> &letters,
    vector<char> &unique_keys,
    string &word
    ) {

    while (permutations.size() < no_of_combinations) { // Begin rejection sampling loop

        unordered_map<char, int> letters_copy = letters;
        vector<char> unique_keys_copy = unique_keys;
        vector<char> generated_vector = {};
        string generated_word;
        vector<int> indexes = {};

        for (int i = 0; i < word.length(); i++) { // Initialise generated_vector with placeholder values
            generated_vector.push_back('\0');
            indexes.push_back(i);
        }

        generating_word(word, indexes, unique_keys_copy, letters_copy, generated_vector);

        for (char letter : generated_vector) { // Append all letters in generated_vector to generated_word
            generated_word += letter;
        }

        permutations.insert(generated_word);
    }
}

int main() {

    cout << "\nRejection Sampling Permutation Generator (C++)\n";
    cout << "----------------------------------------------\n";

    string word;

    input_from_user(word);

    unordered_set<string> permutations = {word}; // Permutations are served in a set which automatically handle repeated values

    unordered_map<char, int> letters; // All letters within the user's word are handled through a dictionary (map)

    vector<char> unique_keys = {};

    initalise_letters(letters, word, unique_keys);

    unsigned long long num_of_permutations;

    calculate_num_of_permutations(letters, word, num_of_permutations);

    cout << format("This word has {} permutation(s).\n", num_of_permutations);

    permutation_generator(permutations, num_of_permutations, letters, unique_keys, word);

    cout << "\nAll permutations:\n";

    for (string permutation: permutations) {
        cout << format("{}, ", permutation);
    }
}
