#include<iostream>
#include<map>
using namespace std;

map<int, int> persons;

void removeInvalidValues(int& p, int& q){
    map<int, int>::iterator it = persons.lower_bound(p);
    if(it == persons.begin()) return;
    --it;
    while(1){
        if(it->second > q) break;
        if(it == persons.begin()) {
            persons.erase(it);
            return;
        }
        else{
            map<int, int>::iterator jt = it;
            --jt;
            persons.erase(it);
            it = jt;
        }
    }
}

bool checkValidInput(int& p, int& q){
    map<int, int>::iterator it = persons.lower_bound(p);
    if(it == persons.end())
        return true;
    return q > it->second;
}

int main(){
	ios_base :: sync_with_stdio(false); 
    cin.tie(NULL); 
    cout.tie(NULL);
    int c;
    cin >> c;
    while(c--){
        int n;
        cin >> n;
        persons.clear();
        int rst = 0;
        for(int i = 0; i < n; i++){
            int p, q;
            cin >> p >> q;
            if(checkValidInput(p, q)){
                persons.insert(make_pair(p, q));
                removeInvalidValues(p, q);
            }
            rst += persons.size();
        }
        cout << rst << endl;
    }
}