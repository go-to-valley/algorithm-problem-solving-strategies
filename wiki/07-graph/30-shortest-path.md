# 30. Shortest Path

2020.05.19 Wed 

Byeongsu Kim



Shortest path problem은 주어진 그래프에서 주어진 두 정점을 연결하는 가장 짧은 경로의 길이를 찾는 문제로 가장 유용하고 널리 사용된다. 이번에 중점적으로 다룰 문제는 가중치가 있는 그래프에 대한 최단 경로를 찾는 알고리즘들을 알아볼 것이다.

Shortest path problem을 해결하기 위해 가장 먼저 중요하게 확인해야 할 부분은 음수간선의 유무이다. 특히 음수간선으로 인해 생성되는 음수 사이클이 있는 경우 shortest path를 절대 찾을 수 없다. 음수 cycle을 무한히 반복하면 shortest path가 음의 무한대로 발산하기 때문이다. 단, 앞으로 살펴볼 알고리즘들을 잘 확인하면 음수 사이클이 있다는 것을 확인할 수는 있다.

Dijkstra algorithm, Bellman-Ford Algorithm, Floyd-Warshall Algorithm으로 불리는 shortest path algorithm중 가장 유명한 세가지에 대해 알아볼 것이다. 전체 shortest path algorithm은 크게 단일 시작점 알고리즘과 모든 쌍 알고리즘으로 나뉘는데, Floyd-Warshall Algorithm이 대표적인 모든 쌍 알고리즘이다.

Shortest Path를 찾을 때는 기본적으로 Directed graph에서 탐색을 진행하는데, Undirected graph의 경우에는 양방향의 directed edge가 있다고 가정하고 문제를 해결하면 된다. 위에서 음수 cycle이 존재하면 shortest path를 찾을 수 없었듯 Undirected graph에서는 음수 간선 자체가 음수 cycle을 형성하기 때문에 음수 가중치가 존재하면 shortest path가 존재하지 않는다.



### Dijkstra Algorithm

Shortest path를 찾는 알고리즘 중 가장 유명하고 간단한 알고리즘이다. 그 구현 또한 매우 단순하기 때문에 적절한 변형으로 많은 shortest path를 찾는 문제를 해결할 수 있지만 음수 간선이 존재하는 경우 문제를 해결할 수 없다는 단점이 있다. 

BFS와 유사한 형태로 진행되는 알고리즘으로 시작점에서부터 가까운 순서대로 정점을 방문한다. 그러나 BFS에서처럼 depth를 priority로 두는 것이 아니라 현재까지 이동하는데 사용된 edge의 가중치 합을 priority로 두어 priority 값이 작은 순서대로 탐색함으로써 최단거리를 계산할 수 있다. 간단히 정리한 내용을 바탕으로 실제 구현 코드는 아래와 같다.

```c++
int V;

vector<parit<int, int> > adj[MAX_V];
vector<int> dijkstra(int src){
    vector<int> dist(V, INF);
    dist[src] = 0;
    priority_queue<pair<int, int> > pq;
    pq.push(make_pair(0, src));
    while(!pq.empty()){
        int cost = -pq.top().first;
        int here = pq.top().second;
        pq.pop();
        if(dist[here] < cost) continue;
        for(int i = 0; i < adj[here].size(); i++){
            int there = adj[here][i].first;
            int nextDist = cost+adj[here][i].second;
            if(dist[there] > nextDist){
                dist[there] = nextDist;
                pq.push(make_pair(-nextDist, there));
            }
        }
    }
    return dist;
}
```

위와 같은 방법으로 Dijkstra algorithm을 구현하면 시작점을 기준으로 모든 vertex에 도달하는 최단거리 vector를 얻을 수 있고, 시간복잡도는 O(Elog(V))이다.

- 실제 경로 찾기

  위에서 작성한 코드는 최단 거리만을 계산할 수 있다. 최단 경로를 찾기 위해서는 어떤 vertex에서 들어왔는지에 대한 정보를 함께 저장함으로써 최단경로들로 구성된 spanning tree를 역으로 올라가면 경로를 확인할 수 있다.

- O(Vlog(V))에 다익스트라 구현하기는 하지 않는다.

- 우선순위 큐를 사용하지 않는 다익스트라 알고리즘

  정점의 수가 적거나 간선의 수가 매우 많은 경우 우선순위 큐에 값을 넣고, 빼는 과정의 시간이 bottle neck이 될 수 있으므로 array를 이용하여 매 반복마다 array내의 모든 값을 확인하도록 구현하는 방법도 있다.

  

### Bellman-Ford Algorithm

음수 간선이 있는 그래프에 대해 문제를 해결할 수 없는 Dijkstra Algorithm에 비해 조금 더 느리고 복잡하지만 Bellman-Ford Algorithm을 활용하면 음수 간선이 있는 그래프에 대해서도 문제를 해결할 수 있다. 또한 음수 cycle이 존재할 경우 이를 알아낼 수도 있기 때문에 그래프 전체에 음수 간선이 존재한다면 Bellman-Ford Algorithm을 활용하여 문제를 해결해야 한다.

기본적인 아이디어는 BFS와는 달리 최단거리의 상한선을 적당히 예측하고, 오차를 줄여나간다면 결국 정확한 답을 찾을 수 있다는 것이다. 오차를 줄여나가는 방식은 아래 수식에 근거하여 이루어진다.
$$
dist[v] \leq dist[u] + w(u, v)
$$
위 수식은 항상 참이기 때문에 초기 모든 vertex에 도달하는 dist를 INF로 잡고, upper vector를 활용하여 값을 실제 최단거리에 가깝게 보정해 나간다. 위 작업을 n번 진행하면 start point로부터 n번 edge를 활용하여 이동할 수 있는 모든 vertex에 대한 최단경로를 얻을 수 있다. 음수 사이클이 존재하지 않는다면 한 번 지난 정점을 다시 지나지 않아야 하므로 최단 경로가 포함하는 간선 수는 최대 |V|-1이고, 따라서 최대 |V|-1번 반복하면 된다.

이처럼 위 수식을 기반으로 문제를 해결하기 때문에 음수 간선이 존재하더라도 문제를 해결할 수 있다. 또한 |V|번 upper vector를 update하면 마지막 이동에서 적어도 하나의 update가 성공한다면 음수 cycle이 존재한다는 것도 쉽게 알 수 있다.

Bellman-Ford Algorithm의 구현은 아래와 같다.

```c++
int v;
vector<pair<int, int> > adj[MAX_V];

vector<int> bellmanFord(int src){
    vector<int> upper(V, INF);
    upper[src] = 0;
    bool updated;
    
    for(int iter = 0;iter < V; iter++){
        updated = false;
        for(int here = 0; i < adj[here].size(); i++){
            int there = adj[here][i].first;
            int cost = adj[here][i].second;
            if(upper[there] > upper[here] + cost){
                upper[there] = upper[here] + cost;
                updated = true;
            }
        }
        if(!updated) break;
    }
    if(updated) upper.clear();
    return upper;
}
```

시간복잡도는 O(|V||E|) 이다.

- 실제 경로 계산하기

  이번 알고리즘에서도 비슷하게 각 정점을 마지막으로 update한 간선들을 모은 스패닝트리를 역으로 올라가면서 vertex들을 확인하면 최단경로를 얻을 수 있다.

- 빠지기 쉬운 함정

  s로부터 u로 가는 경로가 존재하는지 확인하고 싶을 경우 맨 처음 초기화한 값이 그대로 있는지 확인하는 것만 할 수 있다. 그러나 음수 가중치가 있다면 여전히 의미 없는 값이기는 하지만 초기화한 INF와 값이 다를 수 있기 때문에 적당히 큰 M을 설정하고 아래 수식을 통해 확인해야 한다.
  $$
  upper[u] < \inf - M
  $$
  



###  Floyd-Warshall Algorithm

지금까지 알아본 Dijkstra Algorithm과 Bellman-Ford Algorithm의 경우 한 시작점으로 부터 다른 모든 vertex까지의 거리를 구하는데 최적화 되어 있다. 그러나 세상에 존재하는 문제 중에는 모든 정점 쌍에 대해 최단 거리를 구해야 할 경우도 있다. 단순히 Dijkstra나 Bellman-Ford를 모든 vertex에 대해 반복해서 문제를 해결할 수도 있지만 이번에 알아볼 알고리즘이 더 빠르고 간결하게 문제를 해결할 수 있다. 책에서 언급하기를 '바로 이 책에서 다루는 가장 간단한 알고리즘 중 하나인'이라고 한다. 실제로 구현이나 아이디어가 복잡하지 않으니 알아두면 좋을것 같다.

이번 알고리즘이 cover하는 영역은 모든 pair of vertex에 대한 shortest path를 구해야 하기 때문에 이전과는 다른 아이디어를 사용한다. 경유점이라는 아이디어를 적용하는데, 두 정점 u, v를 잇는 어떤 경로가 존재한다면 이 경로는 항상 시작점 u와 끝점 v를 지난다는 아주 당연한 내용을 바탕으로 이루어 진다. 시작점과 끝점 사이에 거쳐가는 vertex들을 경유점이라고 생각하고 문제를 해결한다.

경유점의 개념에 대해 다음과 같이 정의한다. 시작점 u, 끝점 v에 대해 가능한 경유점 set이 S라고 한다면 u에서 v로 가는 경로 중 고려하는 모든 경로의 경유점은 S에 포함되어 있고, S의 모든 vertex가 경유점이 되지 않아도 된다. 이 때 S에 포함된 정점만을 경유점으로 갖는 u에서 v로 가는 최단경로를 알고 있다고 하면 S중에 정점을 하나 골라 x라고 한다면 최단 경로는 x를 경유하는 경우와 경유하지 않는 경우로 나눌 수 있다. 

1. x를 경유하지 않는 경우: 이 shortest path는 S-{x}에 포함된 정점들 만을 경유점으로 사용한다.
2. x를 경유하는 경우: 해당 경로를 u에서 x로 가는 구간, x에서 v로 가는 구간으로 나눌 수 있고, 이 때의 경로는 u에서 x로 가는 shortest path, x에서 v로 가는 shortest path이며 각 경로는 S-{x}에 포함된 vertex만을 경유점으로 사용한다.

위 두가지에 대해 수식으로 나타내면 아래와 같다.
$$
D_S(u, v) = min(D_{S-\{x\}}(u, x) + D_{S-\{x\}}(x, v), D_{S-\{x\}}(u, v), x \in S
$$
위 수식을 점화식의 형태로 생각하고 Dynamic Programming의 memoization기법을 활용하여 코드를 작성하면 아래와 같다.

```c++
int V;
//adj[u][v] = u에서 v로 가는 간선의 가중치 -> 간선이 없으면 아주 큰 값을 넣는다.
int adj[MAX_V][MAX_V];
int C[MAX_V][MAX_V][MAX_V];
void allPairShortestPath1(){
    for(int i = 0; i < V; i++)
        for(int j = 0; j < V; j++){
            if(i != j)
                C[0][i][j] = min(adj[i][j], adj[i][0] + adj[0][j]);
            else
                C[0][i][j] = 0;
        }
    for(int k = 1; k < V; k++)
        for(int i = 0; i < V; i++)
            for(int j = 0; j < V; j++)
                C[k][i][j] = min(C[k-1][i][j], C[k-1][i][k] + C[k-1][k][j]);
}
```

위 코드의 시간복잡도는 O(|V|^3)이다.

- 메모리 사용량 줄이기

  대부분의 경우에서 |V|번 Dijkstra나 Bellman-Ford를 수행하는 것 보다 효율적이다. 그러나 3차원의 배열을 사용하기 때문에 메모리 사용량이 어마어마하다. Sliding window기법을 활용하면 memoization에 사용되는 memory용량을 줄일 수 있기 때문에 |V|^2만큼으로 줄일 수 있다. 더 나아가 인접행렬에서의 바로 옆 이동이 무의미하다는 것을 활용해 더 메모리 용량을 줄일 수 있지만 O(|V|^2)의 공간복잡도를 갖기 때문에 생략하겠다.

- 알고리즘 최적화

  시간복잡도를 바꾸지는 못하지만 두번째 for문에서 i에서 k로 가는 실제 경로가 있는지 확인하여 있는 경우에만 for문을 돌린다면 실제 그래프의 간선이 적을수록 더 효율적으로 작동할 수 있다.

- 실제 경로 계산하기

  실제 경로를 계산하는 방법도 이전 알고리즘들과는 조금 다르지만 맥락은 비슷하다. 각 쌍 u, v에 대해 마지막으로 갱신됐던 k값을 저장해 두어 각 경로에서 사용되었던 vertex를 기록해두면 된다. 실제 구현은 아래 코드와 같다.

  ```c++
  int V;
  int adj[MAX_V][MAX_V];
  int via[MAX_V][MAX_V];
  
  void floyd2(){
      for(int i = 0; i < V; i++) adj[i][i] = 0;
      memset(via, -1, sizeof(via));
      for(int k = 0; k < V; k++)
          for(int i = 0; i < V; i++)
              for(int j = 0; j < V; j++)
                  if(adj[i][j] > adj[i][k] + adj[k][j]){
                      via[i][j] = k;
                      adj[i][j] = adj[i][k]+adj[k][j];
                  }
  }
  
  void reconstruct(int u, int v, vector<int>& path){
      if(via[u][v] == -1){
          path.push_back(u);
          if(u!=v) path.push_back(v);
      }
     	else {
          int w = via[u][v];
          reconstruct(u, w, path);
          path.pop_back();
          reconstruct(w, v, path);
      }
  }
  ```

- 도달 가능성 확인하기

  가중치 없는 그래프에서 각 정점간의 도달 가능성 여부를 계싼하는데 Floyd-Warshall Algorithm을 사용할 수 있다. 이 문제는 아래와 같은 점화식을 활용하여 동일한 알고리즘에서 +, min을 and, or 연산으로 바꾸어 해결할 수 있다.
  $$
  C_k(u, v) = C_{k-1}(u, v) || (C_{k-1}(u, k) \&\& C_{k-1}(k, v))
  $$
  



### Problems

- [신호 라우팅(ID: ROUTING, 난이도: 하)](https://www.algospot.com/judge/problem/read/ROUTING)
- [소방차(ID: FIRETRUCKS, 난이도: 중)](https://www.algospot.com/judge/problem/read/FIRETRUCKS)
- [철인 N종 경기(ID: NTHLON, 난이도: 상)](https://www.algospot.com/judge/problem/read/NTHLON)
- [시간여행(ID: TIMETRIP, 난이도: 중)](https://www.algospot.com/judge/problem/read/TIMETRIP)
- [음주 운전 단속(ID: DRUNKEN, 난이도: 중)](https://www.algospot.com/judge/problem/read/DRUNKEN)
- [선거 공약(ID: PROMISES, 난이도: 중)](https://www.algospot.com/judge/problem/read/PROMISES)