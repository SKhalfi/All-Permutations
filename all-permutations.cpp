#include <iostream>
#include <random>
#include <unordered_map>
#include <vector>
#include <algorithm>
#include <unordered_set>

using namespace std;

random_device rd;
mt19937 gen(rd());

long long random(long long lower_bound, long long upper_bound) {
    uniform_int_distribution<long long> dist(lower_bound, upper_bound);
    return dist(gen);
}

long long factorial(long long number) {
    long long total = 1;

    while (number != 1) {
        total *= number;
        number -= 1;
    }
    return total;
}

int main() {
    string word;

    while (true) {
        cout << "Enter a word: ";
        getline(cin, word);

        if (word.empty()) {
            cout << "Word must not be left empty.\n" << endl;
        }

        else {
            break;
        }
    }

    // Remove trailing and leading spaces from word later
    unordered_set<string> permutations = {word};
    unordered_map<char, int> letters;
    vector<char> unique_keys = {};
    long long denominator = 1;

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

    long long no_of_combinations = factorial(word.length()) / denominator;

    cout << "This word has " + to_string(no_of_combinations) + " combination(s)." << endl;


    while (permutations.size() < no_of_combinations) {
        unordered_map<char, int> letters_copy = letters;
        vector<char> unique_keys_copy = unique_keys;
        vector<char> generated_vector = {};
        string generated_word;
        vector<int> indexes = {};
        int random_index = 0;
        char random_letter = '\0';
        int number_pos = 0;
        int letter_pos = 0;

        for (int i = 0; i < word.length(); i++) {
            generated_vector.push_back('\0');
            indexes.push_back(i);
        }

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

        for (char letter : generated_vector) {
            generated_word += letter;
        }

        permutations.insert(generated_word);
    }

    cout << "All permutations: " << endl;

    for (string word: permutations) {
        cout << word + ", ";
    }
}
