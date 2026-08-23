#include <iostream>
#include <random>
#include <unordered_map>
#include <vector>
#include <algorithm>
#include <unordered_set>

using namespace std;

random_device rd;
mt19937 gen(rd());

unsigned long long random(unsigned long long lower_bound, unsigned long long upper_bound) {
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

void input_from_user(string &word) {

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

        number_pos = random(0, (indexes.size() - 1));
        random_index = indexes.at(number_pos);
        indexes.erase(indexes.begin() + number_pos);

        letter_pos = random(0, (unique_keys_copy.size() - 1));
        random_letter = unique_keys_copy.at(letter_pos);
        letters_copy.at(random_letter) -= 1;

        if (letters_copy.at(random_letter) == 0) {
            letters_copy.erase(random_letter);
            unique_keys_copy.erase(unique_keys_copy.begin() + letter_pos);
        }

        generated_vector.at(random_index) = random_letter;
    }
}

void permutation_generator (
    unordered_set<string> &permutations,
    const unsigned long long &no_of_combinations,
    unordered_map<char, int> &letters,
    vector<char> &unique_keys,
    string &word
    ) {

    while (permutations.size() < no_of_combinations) {

        unordered_map<char, int> letters_copy = letters;
        vector<char> unique_keys_copy = unique_keys;
        vector<char> generated_vector = {};
        string generated_word;
        vector<int> indexes = {};

        for (int i = 0; i < word.length(); i++) {
            generated_vector.push_back('\0');
            indexes.push_back(i);
        }

        generating_word(word, indexes, unique_keys_copy, letters_copy, generated_vector);

        for (char letter : generated_vector) {
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

    unordered_set<string> permutations = {word};
    unordered_map<char, int> letters;
    vector<char> unique_keys = {};
    unsigned long long denominator = 1;

    for (char letter: word) {
        if (letters.count(letter) == 0) {
            letters[letter] = 1;
            unique_keys.push_back(letter);
        }
        else {
            letters[letter] += 1;
        }
    }

    for (auto value: letters) {
        denominator *= factorial(value.second);
    }

    const unsigned long long no_of_combinations = factorial( word.length()) / denominator;

    cout << "This word has " + to_string(no_of_combinations) + " combination(s).\n";

    permutation_generator(permutations, no_of_combinations, letters, unique_keys, word);

    cout << "\nAll permutations:\n";

    for (string permutation: permutations) {
        cout << permutation + ", ";
    }
}
