# 11. Combinatorial Search

2020. 07. 23.

Seungrok Lee



지금까지 배웠던 디자인 패러다임은 모든 문제에 적용할 수는 없다. 적절한 분할 방법이 없는 문제를 분할 정복으로 풀 수도 없고, 중복되는 부분 문제가 전혀 없거나 메모리를 너무 많이 사용하는 문제에 동적 계획법을 사용할 수도 없다. 이 때는 완전 탐색으로 돌아와 다시 시작해야 하는데 완전 탐색의 경우, 수행시간이 탐색 공간의 크기에 직접적으로 비례하기 때문에 문제의 규모가 조금이라도 크면 사용하기 어렵다.

완전 탐색을 포함하여 유한한 크기의 탐색 공간을 뒤지면서 답을 찾아내는 알고리즘들을 이 책에서는 조합 탐색(Combinatorial Search)라고 부른다. 조합 탐색에는 여러가지 최적화 기법이 있으며, 접근 방식은 다르지만 모두 기본적으로 최적해가 될 가능성이 없는 답들을 탐색하는 것을 방지하여 탐색의 수를 줄이는 것을 목표로 한다. 이 장에서 다루는 조합 탐색 최적화 기법들은 크게 두 가지로 분류할 수 있다. 

* 가지치기 기법은 탐색 과정에서 최적해로 연결될 가능성이 없는 부분들을 잘라낸다. 가장 기초적인 예로는 지금까지 찾아낸 최적해보다 부분해가 이미 더 좋지 않다면 탐색을 종료한다.
* 탐색의 순서를 바꾸거나, 탐색 시작 전에 탐욕법을 이용해 적당히 좋은 답을 우선 찾아낸다. 그리고 가지치기와 함께 사용한다.



##예제

TSP
```c++
const double INF = 1e200;
const int MAX = 30;
int n;
double dist[MAX][MAX];
double best;

void search(vector<int>& path, vector<bool>& visited, dobule currentLength)
{
    int here = path.back();
    if(path.size()==n)
    {
        best=min(best, currentLength+dist[here][0]);
        return;
    }
    for(int next=0;next<n;++next)
    {
        if(visited[next]) continue;
        path.push_back(next);
        visited[next]=true;
        search(path, visited, currentLength+dist[here][next]);
        visited[next]=false;
        path.pop_back();
    }
}

double solve()
{
    best=INF;
    vector<vool> visited(n, false);
    vector<int> path(1.0);
    visited[0]=true;
    search(path, visited, 0);
    return best;
}

```

위는 완전 탐색으로 구현된 TSP의 코드이다. 
최적화를 하기 위해 가장 기초적인 가지치기 방법을 사용하면 현재 상태의 답이 지금까지 구한 최적해보다 좋지 않을 경우 탐색을 중지하는 방법이 될 것이고, search() 함수의 처음에 다음 코드를 추가하는 것으로 구현된다.

```c++
if(best<=currentLength) return;
```

입력 데이터를 해결하는 데 걸리는 시간이 30분의 1 정도로 줄어들지만 아직 동적계획법에 비하면 갈 길이 멀다.
이 때 휴리스틱을 이용해 답의 남은 부분을 어림짐작하는 가지치기를 이용하면 좀 더 효율적인 프로그램을 작성할 수 있다.
이 장에서 TSP에 적용하는 휴리스틱들은 다음과 같다.

* 인접하는 가장 가까운 도시까지의 거리들을 미리 계산해두고 best가 지금까지의 경로와 가장 가까운 도시까지의 거리의 합보다 작으면 탐색 종료
* 도시를 번호 순이 아닌 가장 가까운 순으로 방문
* 이전의 경로에서 더 짧게 가는 루트가 있으면 탐색 종료. 따라서 현재 도시 이전의 두 도시를 뒤집어서 경로가 작을 경우 탐색 종료
* MST를 이용하여 남은 마을의 MST + 현재 경로의 길이가 best보다 크면 탐색 종료



### Problems

- [게임판 덮기 2(ID: BOARDCOVER2, 난이도: 하)](https://www.algospot.com/judge/problem/read/BOARDCOVER2) 
- [알러지가 심한 친구들(ID: ALLERGY, 난이도: 중)](https://www.algospot.com/judge/problem/read/ALLERGY) 
- [카쿠로(ID: KAKURO2, 난이도: 중)](https://www.algospot.com/judge/problem/read/KAKURO2) 
