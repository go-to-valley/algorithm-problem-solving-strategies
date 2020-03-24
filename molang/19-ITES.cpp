#include<iostream>
#include<queue>
#include<cmath>
using namespace std;

int main(){
    int c;
    cin >> c;
    for(int t = 0; t < c; t++){
        int n, k;
        cin >> k >> n;
        queue<long long> queue;
        int sum = 0;
        int cnt = 0;
        long long a = 1983;
        for(int i = 0; i < n ;i++){
            long long arr = (a%10000) + 1;
            a = (a*214013 + 2531011)%((long long)pow(2, 32));
            sum += arr;
            queue.push(arr);
            while(sum > k && !queue.empty()){
                sum -= queue.front();
                queue.pop();
            }
            if(sum == k) cnt++;
        }
        cout << cnt << '\n';
    }
}