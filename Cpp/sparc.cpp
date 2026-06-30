#include <iostream>
#include <string>
#include <queue>
#include <vector>
#include <algorithm>

using namespace std;

struct State {
    long long remainder;
    string number;
};

vector<string> findBinaryMultiples(long long S, int maxResults = 50) {
    vector<string> results;
    queue<State> q;
    
    q.push({1 % S, "1"});
    
    // BFS
    while (!q.empty() && results.size() < maxResults) {
        State curr = q.front();
        q.pop();
        
        // Divisible Solution
        if (curr.remainder == 0) {
            results.push_back(curr.number);
            continue; 
        }
        
        // Left Side
        long long nr0 = (curr.remainder * 10) % S;
        q.push({nr0, curr.number + "0"});
        
        // Right Side
        long long nr1 = (curr.remainder * 10 + 1) % S;
        q.push({nr1, curr.number + "1"});
    }
    
    return results;
}

long long reverseDigits(long long x) {
    string s = to_string(x);
    reverse(s.begin(), s.end());
    return stoll(s);
}

string multiplyStrings(const string& num1, const string& num2) {
    int n1 = num1.size(), n2 = num2.size();
    if (n1 == 0 || n2 == 0) return "0";
    
    vector<int> result(n1 + n2, 0);
    
    for (int i = n1 - 1; i >= 0; i--) {
        for (int j = n2 - 1; j >= 0; j--) {
            int mul = (num1[i] - '0') * (num2[j] - '0');
            int p1 = i + j, p2 = i + j + 1;
            int sum = mul + result[p2];
            
            result[p2] = sum % 10;
            result[p1] += sum / 10;
        }
    }
    
    string str = "";
    for (int i : result) str += to_string(i);
    
    size_t startpos = str.find_first_not_of("0");
    if (startpos != string::npos) {
        return str.substr(startpos);
    }
    return "0";
}

string divideString(const string& number, long long divisor) {
    string result = "";
    long long temp = 0;
    
    for (char digit : number) {
        temp = temp * 10 + (digit - '0');
        if (temp >= divisor) {
            result += to_string(temp / divisor);
            temp = temp % divisor;
        } else if (!result.empty()) {
            result += '0';
        }
    }
    
    return result.empty() ? "0" : result;
}

bool hasBinaryDigits(const string& s) {
    for (char c : s) {
        if (c != '0' && c != '1') return false;
    }
    return true;
}

int main() {
    long long S;
    cin >> S;
    
    long long R = reverseDigits(S);
    string R_str = to_string(R);
    
    // Check first 50 multiples
    vector<string> multiples = findBinaryMultiples(S, 50);
    
    // Check
    for (const string& P_str : multiples) {
        string k_str = divideString(P_str, S);
        string N_times_M = multiplyStrings(k_str, to_string(S));
        string N_times_R = multiplyStrings(k_str, R_str);
        
        if (N_times_M == P_str && hasBinaryDigits(N_times_M) && !hasBinaryDigits(N_times_R)) {
            cout << "S = " << S << endl;
            cout << "k = " << k_str << endl;
            cout << "P = k*S = " << N_times_M << endl;
            cout << "R = " << R << endl;
            cout << "P (reverse) = k*R = " << N_times_R << endl;
            return 0;
        }
    }
    
    cout << "No valid solution found in first " << multiples.size() << " multiples" << endl;
    
    return 0;
}