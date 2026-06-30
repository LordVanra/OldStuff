#include <iostream>
#include <string>
#include <queue>
#include <vector>
#include <set>
#include <map>
#include <algorithm>

using namespace std;

struct State {
    long long remainder;
    string number;
};

long long gcd(long long a, long long b) {
    while (b) {
        long long temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

vector<string> findBinaryMultiples(long long S, int maxResults = 50) {
    vector<string> results;
    queue<State> q;
    map<pair<long long, int>, bool> visited;  // Track (remainder, depth) pairs
    
    q.push({1 % S, "1"});
    visited[{1 % S, 1}] = true;
    
    while (!q.empty() && results.size() < maxResults) {
        State curr = q.front();
        q.pop();
        
        if (curr.remainder == 0) {
            results.push_back(curr.number);
        }
        
        int nextDepth = curr.number.length() + 1;
        
        long long nr0 = (curr.remainder * 10) % S;
        if (visited.find({nr0, nextDepth}) == visited.end()) {
            visited[{nr0, nextDepth}] = true;
            q.push({nr0, curr.number + "0"});
        }
        
        long long nr1 = (curr.remainder * 10 + 1) % S;
        if (visited.find({nr1, nextDepth}) == visited.end()) {
            visited[{nr1, nextDepth}] = true;
            q.push({nr1, curr.number + "1"});
        }
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
    //Config variables
    int MAXDEPTH = 7;
    int MAX_MULT = 70;

    for (int maxLen = 4; maxLen <= MAXDEPTH; maxLen++) {
    vector<long long> binaryS;
    
    for (int len = 2; len <= maxLen; len++) {
        for (int mask = 0; mask < (1 << len); mask++) {
            string s = "";
            for (int i = len - 1; i >= 0; i--) {
                s += ((mask >> i) & 1) ? '1' : '0';
            }
            long long num = stoll(s);
            long long rev = reverseDigits(num);
            if (num > 0 && gcd(num, 10) == 1 && num != rev) {
                binaryS.push_back(num);
            }
        }
    }
    
    int totalK = 0;
    int specialK = 0;
    int nonSpecialK = 0;
    
    for (long long S : binaryS) {
        long long R = reverseDigits(S);
        string R_str = to_string(R);
        
        vector<string> products = findBinaryMultiples(S, MAX_MULT);
        
        for (const string& kS_str : products) {
            totalK++;
            
            string k_str = divideString(kS_str, S);
            string kR = multiplyStrings(k_str, R_str);
            
            if (hasBinaryDigits(kR)) {
                specialK++;
            } else {
                nonSpecialK++;
            }
        }
    }

    cout << "Max length: " << maxLen << endl;
    cout << "Total multipliers k tested: " << totalK << endl;
    cout << "SPECIAL k: " << specialK << endl;
    cout << "NON-SPECIAL k: " << nonSpecialK << endl;
    cout << "Percentage SPECIAL: " << (100.0 * specialK / totalK) << "%" << endl;
    cout << "Percentage NON-SPECIAL: " << (100.0 * nonSpecialK / totalK) << "%" << endl;
}
    
    return 0;
}