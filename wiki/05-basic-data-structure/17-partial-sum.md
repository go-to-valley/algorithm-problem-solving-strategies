# 17. Partial Sum

2020.03.18 Wed 

Byeongsu Kim



다양한 방법으로 배열을 많이 사용해 왔는데, 배열 내부 연속된 값들의 합을 여러 번 계산해야 하는 상황이 있을 수 있다. 이러한 상황에서 매우 유용하게 사용할 수 있는 알고리즘이 partial sum이다.
$$
psum[i] = \Sigma_{j = 0}^i array[j]
$$
위와 같이 partial sum을 정의해 둔다면 배열의 특정 구간 합은 아래와 같이 O(1)에 계산할 수 있다.
$$
psum[b] - psum[a - 1]
$$
다양한 활용방법에 대해 코드로 나타내면 아래와 같다.

- 부분 합 계산하기

```C++
vector<int> partialSum(const vector<int>& a){
    vector<int> ret(a.size());
    ret[0] = 0;
    for(int i = 0; i < a.size(); i++){
        ret[i+1] = ret[i1]+a[i];
    }
}

int rangeSum(const vector<int>& psum, int a, int b){
    return psum[b+1] - psum[a];
}
```

- 부분합으로 분산 계산하기

  분산을 계산하는 것과 같이 구간합을 빠르고 쉽게 계산할 때 매우 유용하게 적용할 수 있다. 

```C++
double variance(const vector<int>& sqpsum, const vector<int>& psum, int a, int b){
    double mean = rangeSum(psum, a, b) / double(b - a + 1);
    double ret = rangeSum(sqpsum, a, b) 
        - 2 * mean * rangeSum(psu, a, b)
        + (b - a + 1) * mean * mean;
    return ret / (b - a + 1);
}
```

- 2차원으로의 확장

  다차원으로의 확장도 쉽게 할 수 있는데, 아래 코드는 2차원으로의 확장을 구현해 두었다. 

```C++
int gridSum(const vector<vector<int> >& psum, int y1, int x1, int y2, int x2){
    int ret = psum[y2+1][x2+1];
    ret -= psum[y1][x2+1];
    ret -= psum[y2+1][x1];
    ret += psum[y1][x1];
    return ret;
}
```

- 합이 0에 가장 가까운 구간

  양수와 금수가 모두 포함된 배열에서 그 합이 0에 가장 가까운 구간을 찾는 문제가 있을 때 이를 찾기 위해서는 psum[i]를 정렬하고 인접한 원소들을 확인함으로써 기존의 완전탐색 O(n^2)보다 훨씬 빠른 O(nlogn)으로 수행할 수 있다.



### Problems

- [크리스마스 인형(ID: CHRISTMAS, 난이도: 중)](https://www.algospot.com/judge/problem/read/CHRISTMAS)