# 06. Brute Force

2020.06.03

Seungrok Lee



Brute Force는 컴퓨터의 빠른 계산 능력을 이용해 가능한 경우의 수를 일일이 나열하면서 답을 찾는 방법을 의미한다. 이러한 알고림들을 가리켜 흔히 완전 탐색(exclusive search)이라고 부르며, 컴퓨터가 빠른 계산 속도를 가졌다는 장점을 가장 잘 이용하는 방법이다.



### 재귀 호출과 완전 탐색

작업들 중에서 우리가 들여다보는 범위가 작아지면 작아질수록 각 조각들의 형태가 유사해지는 작업들을 많이 볼 수 있다. 이런 작업을 구현할 때 유용하게 사용되는 개념이 바로 재귀 호출(recursion)이다. 다음은 일반 반복문을 사용했을 때와 재귀 호출을 이용한 함수를 사용했을 때의 차이점을 보여주는 코드이다.

```c++
int sum(int n) {
    int ret=0;
    for(int i=1;i<=n;i++)   ret+=i;
    return ret;
}

int recursiveSum(int n) {
    if(n==1) return 1;
    return n+recursiveSum(n-1);
}
```

recursiveSum에서 if문은 더이상 쪼개지지 않는 최소한의 작업에 도달했을 때의 상태를 표현한다. 이 작업들을 가리켜 재귀 호출의 기저 사례(base case)라고 한다.
이처럼 재귀 호출은 기존에 반복문을 사용해 작성하던 코드를 다르게 작성할 수 있는 방법을 제공해 주며, 문제의 특성에 따라 코딩을 훨씬 간편하게 해줄 수 있다.



### Problems

- [소풍(ID: PICNIC, 난이도: 하)](https://www.algospot.com/judge/problem/read/PICNIC)
- [게임판 덮기(ID: BOARDCOVER, 난이도: 하)](https://www.algospot.com/judge/problem/read/BOARDCOVER)



### 최적화 문제

지금까지 다뤘던 재귀 호출을 이용한 문제는 전부 여러 개의 답을 찾아내는 문제였고, 어떤 기준에 따라 가장 '좋은' 답을 찾아 내는 문제들을 최적화 문제(Optimization problem)이라고 부른다. 최적화 문제를 해결하는 방법은 여러가지가 있으나 가장 기초적인 것이 완전 탐색이다. 가장 유명한 문제인 여행하는 외판원 문제(Traveling Salesman Proble, TSP)를 살펴보자.

```c++
int n;
double dist[MAX][MAX];

double shortestPath(vector<int>& path, vector<bool>& visited, double currentLength) {
    if(path.size()==n) return currentLength+dist[path[0]][path.back()];
    double ret=INF;
    
    for(int next=0;next<n;next++) {
        if(visited[next] continue;
        int here=path.next();
        path.push_back(next);
        visited[next]=true;
        double cand=shortestPath(path, visitied, currentLength+dist[here][next]);
        ret=min(ret, cand);
        visited[next]=false;
        path.pop_back();
    }
    return ret;
}
```



### Problems

-[시계 맞추기(ID: CLOCKSYNC, 난이도: 중)](https://www.algospot.com/judge/problem/read/CLOCKSYNC)



### 많이 등장하는 완전 탐색 유형

- 모든 순열 만들기
- 모든 조합 만들기
- 2^n가지 경우의 수 만들기
