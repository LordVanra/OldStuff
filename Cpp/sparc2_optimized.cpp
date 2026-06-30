#include <iostream>
#include <string>
#include <queue>
#include <vector>
#include <set>
#include <map>
#include <algorithm>

using namespace std;

struct Node {
    int parent;
    char digit; // '0' or '1'
};

struct State {
    long long remainder;
    int nodeIndex; // Index in the nodes vector
    int len;
};

// Global pool to avoid reallocation if possible, though distinct searches run sequentially
vector<Node> nodes; 

long long gcd(long long a, long long b) {
    while (b) {
        long long temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

string constructString(int nodeIndex) {
    string s = "";
    while (nodeIndex != -1) {
        s += nodes[nodeIndex].digit;
        nodeIndex = nodes[nodeIndex].parent;
    }
    reverse(s.begin(), s.end());
    return s;
}

vector<string> findBinaryMultiples(long long S, int maxResults = 50) {
    vector<string> results;
    
    // Reset nodes for this search
    nodes.clear();
    // Allow nodes to grow as needed; reserve some to prevent frequent reallocs
    nodes.reserve(10000); 

    // Visited array: visited[remainder] = depth
    // Max S is around 1,111,111 for len=7, so ~1.2MB is fine.
    // We use a vector with -1 initialization.
    // If S was huge, we'd need a different approach (e.g. bitset or iterative ID),
    // but for the given constraints, this is optimal.
    static vector<int> visited;
    if (visited.size() < S) {
        visited.resize(S + 1, -1);
    } else {
        // We only clear indices we touch or just use a generation counter?
        // A full fill is fast enough for ~1MB? 
        // Actually, for multiple calls, let's just re-fill or use a specialized reset.
        // However, standard fill is safe.
        // Optimization: Use a "generation" array to avoid memset?
        // visited_gen[rem] == current_gen.
        // But fill is probably fast enough given the rest of the work.
        // Let's stick to fill for simplicity and safety first.
        fill(visited.begin(), visited.begin() + S, -1);
    }

    queue<State> q;
    
    // Initial state: "1"
    nodes.push_back({-1, '1'});
    long long startRem = 1 % S;
    q.push({startRem, 0, 1});
    visited[startRem] = 1;
    
    while (!q.empty() && results.size() < maxResults) {
        State curr = q.front();
        q.pop();
        
        if (curr.remainder == 0) {
            results.push_back(constructString(curr.nodeIndex));
            // Don't continue/return here, we want more results
        }
        
        // If we found enough results, we can stop early?
        // The condition is in the while loop, but we just added one.
        if (results.size() >= maxResults) break;
        
        int nextDepth = curr.len + 1;
        
        // Try '0'
        long long nr0 = (curr.remainder * 10) % S;
        if (visited[nr0] != nextDepth) {
            visited[nr0] = nextDepth;
            nodes.push_back({curr.nodeIndex, '0'});
            q.push({nr0, (int)nodes.size() - 1, nextDepth});
        }
        
        // Try '1'
        long long nr1 = (curr.remainder * 10 + 1) % S;
        if (visited[nr1] != nextDepth) {
            visited[nr1] = nextDepth;
            nodes.push_back({curr.nodeIndex, '1'});
            q.push({nr1, (int)nodes.size() - 1, nextDepth});
        }
    }
    
    return results;
}

long long reverseDigits(long long x) {
    string s = to_string(x);
    reverse(s.begin(), s.end());
    return stoll(s);
}

// Optimized string multiplication to reduce allocations
string multiplyStrings(const string& num1, const string& num2) {
    int n1 = num1.size(), n2 = num2.size();
    if (n1 == 0 || n2 == 0) return "0";
    
    vector<int> result(n1 + n2, 0);
    
    for (int i = n1 - 1; i >= 0; i--) {
        int d1 = num1[i] - '0';
        for (int j = n2 - 1; j >= 0; j--) {
            int mul = d1 * (num2[j] - '0');
            int p1 = i + j, p2 = i + j + 1;
            int sum = mul + result[p2];
            
            result[p2] = sum % 10;
            result[p1] += sum / 10;
        }
    }
    
    // Pre-calculate size to reserve string buffer?
    // Max size is n1+n2.
    string str;
    str.reserve(n1 + n2);
    
    // Skip leading zeros
    bool leading_zeros = true;
    for (int i : result) {
        if (i != 0) leading_zeros = false;
        if (!leading_zeros) {
            str += to_string(i);
        }
    }
    
    if (str.empty()) return "0";
    return str;
}

string divideString(const string& number, long long divisor) {
    string result;
    result.reserve(number.size()); // Opt: reserve
    
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
    // Optimization: Sync off
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    //Config variables
    int MAXDEPTH = 7;
    int MAX_MULT = 70;

    for (int maxLen = 4; maxLen <= MAXDEPTH; maxLen++) {
        vector<long long> binaryS;
        
        for (int len = 2; len <= maxLen; len++) {
            for (int mask = 0; mask < (1 << len); mask++) {
                // Optimize string construction? Not critical here but easy to do
                // Actually this part is N=2^7 ~ 128 iters, negligible.
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
        
        long long totalK = 0;
        long long specialK = 0;
        long long nonSpecialK = 0;
        
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
        // Avoid division by zero if totalK is 0, though unlikely
        if (totalK > 0) {
            cout << "Percentage SPECIAL: " << (100.0 * specialK / totalK) << "%" << endl;
            cout << "Percentage NON-SPECIAL: " << (100.0 * nonSpecialK / totalK) << "%" << endl;
        } else {
            cout << "Percentage SPECIAL: 0%" << endl;
            cout << "Percentage NON-SPECIAL: 0%" << endl;
        }
    }
    
    return 0;
}
