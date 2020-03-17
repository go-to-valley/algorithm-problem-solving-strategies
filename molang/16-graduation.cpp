#include<iostream>
#include<vector>
#include<bitset>
using namespace std;

unsigned int pre[13];
unsigned int sem[11];
int n, k, m, l;

int bitCount(int x) {
	if (x == 0) return 0;
	return x % 2 + bitCount(x / 2);
}

bool checker(unsigned int taken, unsigned int target) {
	int cnt = 0;
	while (target) {
		if ((target % 2) && (pre[cnt] != (pre[cnt] & taken))) return false;
		target /= 2;
		cnt++;
	}
	return true;
}

int simulate(unsigned int taken, int now) {
	if (bitCount(taken) >= k) return 0;
	if (now == m) return 21;
	int min = 20;
	unsigned int can = (sem[now] & (~taken));

	for (unsigned int cls = can; cls; cls = ((cls - 1)&can)) {
		if (bitCount(cls) <= l && (checker(taken, cls))) {
			int need = simulate(taken | cls, now + 1)+1;
			min = min < need ? min : need;
		}
	}
	int need = simulate(taken, now + 1);
	min = min < need ? min : need;
	return min;
}

int main() {
	int C;
	cin >> C;
	for (int c = 0; c < C; c++) {
		cin >> n >> k >> m >> l;
		for (int i = 0; i < n; i++) {
			int r;
			cin >> r;
			pre[i] = 0;
			for (int j = 0; j < r; j++) {
				int v;
				cin >> v;
				pre[i] |= (1 << v);
			}
		}
		for (int i = 0; i < m; i++) {
			int ci;
			cin >> ci;
			sem[i] = 0;
			for (int j = 0; j < ci; j++) {
				int v;
				cin >> v;
				sem[i] |= (1 << v);
			}
		}
		int rst = simulate(0, 0);
		if (rst >= 20) cout << "IMPOSSIBLE" << endl;
		else cout << rst << endl;
	}
}