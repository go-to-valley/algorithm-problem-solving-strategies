#include<iostream>
using namespace std;

int subSum[100003];
int rst2[100003];
long long cnt[100003];
int last[100003];
int T, n, k;

int max(int a, int b){
    return a > b ? a: b;
}

int main(){
	ios_base :: sync_with_stdio(false); 
    cin.tie(NULL); 
    cout.tie(NULL);

    cin >> T;
    for(int t = 0; t < T; t++){
        cin >> n >> k;
        for(int i = 0; i < n; i++){
            int v;
            cin >> v;
            subSum[i+1] = (subSum[i]+(v%k))%k;
        }
        for(int i = 0; i < k; i++) {
            cnt[i] = 0;
            last[i] = -1;
        }
        int rst1 = 0;
        for(int i = 0; i <= n; i++)
            cnt[subSum[i]]++;
        for(int i = 0; i < k; i++) 
            if(cnt[i] > 1) rst1 = (rst1+((cnt[i]*(cnt[i]-1))/2)%20091101)%20091101;
            
        rst2[0] = 0;
        last[0] = 0;
        for(int i = 1; i <= n; i++){
            rst2[i] = rst2[i-1];
            if(last[subSum[i]] != -1) rst2[i] = max(rst2[i], rst2[last[subSum[i]]]+1);
            last[subSum[i]] = i;
        }
        cout << rst1 << ' ' << rst2[n] << '\n';
    }
}