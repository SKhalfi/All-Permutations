#include <iostream>
#include <random>
#include <unordered_map>
#include <vector>
#include <algorithm>
#include <unordered_set>
#include <thread>
#include <mutex>

using namespace std;

mutex m;
random_device rd;
mt19937 gen(rd());

unsigned long long random(unsigned long long lower_bound, unsigned long long upper_bound) {
    uniform_int_distribution<unsigned long long> dist(lower_bound, upper_bound);
    return dist(gen);
}

unsigned long long factorial(unsigned long long number) {
    unsigned long long total = 1;

    while (number != 1) {
        total *= number;
        number -= 1;
    }
    return total;
}

string inputFromUser() {
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
    return word;
}

void permutationGenerator(const string *word, unordered_set<string> *AllPermutations, const unsigned long long *no_of_permutations, const unordered_map<char, int> *letters, const vector<char> *unique_keys) {

    while (AllPermutations->size() < *no_of_permutations) {
        int random_index = 0;
        char random_letter = '\0';
        unsigned long long number_pos = 0;
        unsigned long long letter_pos = 0;
        vector<int> indexes = {};
        vector<char> generated_vector = {};
        string generated_word;
        unordered_map<char, int> letters_copy = *letters;
        vector<char> unique_keys_copy = *unique_keys;

        for (int i = 0; i < word->length(); i++) {
            generated_vector.push_back('\0');
            indexes.push_back(i);
        }

        for (int counter = 0; counter < word->length(); counter++) {

            number_pos = random(0, (indexes.size() - 1));
            random_index = indexes.at(number_pos);
            indexes.at(number_pos) = indexes.back();
            indexes.pop_back();

            letter_pos = random(0, (unique_keys_copy.size() - 1));
            random_letter = unique_keys_copy.at(letter_pos);
            letters_copy.at(random_letter) -= 1;

            if (letters_copy.at(random_letter) == 0) {
                letters_copy.erase(random_letter);
                unique_keys_copy.at(letter_pos) = unique_keys_copy.back();
                unique_keys_copy.pop_back();
            }

            generated_vector.at(random_index) = random_letter;
        }

        for (char letter : generated_vector) {
            generated_word += letter;
        }

        m.lock();
        AllPermutations->insert(generated_word);
        m.unlock();
    }
}

int main() {

    vector<thread> threads;
    string word = inputFromUser();
    unordered_set<string> AllPermutations = {word};
    unordered_map<char, int> letters = {};
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

    unsigned long long no_of_permutations = factorial(word.length()) / denominator;

    cout << "This word has " + to_string(no_of_permutations) + " permutations(s)." << endl;

    for (int i = 0; i < thread::hardware_concurrency(); i++) {
        threads.emplace_back(permutationGenerator, &word, &AllPermutations, &no_of_permutations, &letters, &unique_keys);
    }

    for (auto &t : threads) {
        t.join();
    }

    cout << "All permutations: " << endl;

    for (const string &permutation: AllPermutations) {
        cout << permutation + ", ";
    }
}