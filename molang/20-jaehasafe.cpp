#include<iostream>
#include<string>
#include<vector>
using namespace std;

string haystack, needle;
int pi[20003];

void makePartialMatch(){
    for(int i = 0; i < needle.size(); i++) pi[i] = 0;
    int j = 0;
    for(int i = 1; i < needle.size(); i++){
        while(j>0 && needle[i] != needle[j]) j = pi[j-1];
        if(needle[i] == needle[j]) pi[i] = ++j;
    }
}

int countMovement(){
    makePartialMatch();
    int j = 0;
    for(int i = 0; i < haystack.size(); i++){
        while(j > 0 && haystack[i] != needle[j]) j = pi[j-1];
        if(haystack[i] == needle[j]){
            if(j == needle.size()-1) return i-j;
            else j++;
        }
    }
}

int main(){
    int c;
    cin >> c;
    for(int t = 0; t < c; t++){
        int n;
        cin >> n;
        string _last, _now;
        cin >> _last;
        int rst = 0;
        for(int i = 0;i < n; i++){
            cin >> _now;
            if(i%2) {
                haystack = _last+_last;
                needle = _now;
            }
            else{
                haystack = _now+_now;
                needle = _last;
            }
                rst += countMovement();
            _last = _now;
        }
        cout << rst << endl;
    }
}