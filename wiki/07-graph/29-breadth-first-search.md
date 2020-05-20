# 29. Breadth First Search

2020.05.16

Seungrok Lee



Breadth First Search는 Depth First Search와 함께 가장 널리 사용되는 그래프 탐색 알고리즘이다. BFS 는 시작점에서 가까운 정점부터 순서대로 방문하는 
탐색 알고리즘이다.

BFS는 각 정점을 방문할 때마다 모든 인접 정점들을 본 적이 있는지 검사하고, 처음 보는 정점을 발견할 경우 queue에 저장한다. 그리고 인접 정점들을 모두 
검사한 후에는, queue의 맨 앞의 정점을 꺼내서 방문하여 위의 과정을 반복한다.

단순한 BFS 구현은 아래와 같다. DFS와 다른 점은 발견 즉시 방문하는 것이 아니라 정점으로부터 깊이가 다 같은 노드들을 방문한 후에 방문하므로 queue를 
사용하여 구현한다.

```c++
vector<vector<int>> adj;
vector<int> bfs(int start){
  vetor<bool> discovered(adj.size(), false);
  queue<int> q;
  vector<int> order;
  discovered[start] = true;
  q.push(start);
  while(!q.empty()) {
    int here = q.front();
    q.pop();
    order.push_back(here);
    for(int i=0; i<adj[here].size; ++i) {
      int there = adj[here][i];
      if(!dixcovered[there]) {
        q.push(there);
        discovered[there] = true;
      }
    }
  }
  return order;
}
```

위와 같은 방법으로 BFS를 구하면 시간복잡도는 O(|V|+|E|)가 된다.



### 최단 거리

그래프의 구조와 관련된 다양한 문제를 푸는 DFS에 비해, BFS는 대개 최단 경로 문제를 푸는 하나의 용도로 사용된다. 위에서 구현했던 BFS 알고리즘을 간단히 변경하여 시작점으로부터의 거리 distance[]를 계산할 수 있다.

그리고 시작점으로부터 다른 모든 정점까지의 최단 경로를 BFS spanning tree에서 찾을 수 있다. 따라서 그래프의 정점과 정점 간의 최단 거리를 BFS spanning tree와 BFS 알고리즘을 변경하여 distance[]를 계산할 수 있는 알고리즘을 사용하여 구할 수 있다. 

```c++
void bfs2(int start, vetor<int>& distance, vector<int>& parent) {
    distance = vector<int>(adj.size(), -1);
    parent = vector<int>(adj.size(), -1);
    queue<int> q;
    distance[start] = 0;
    parent[start] = start;
    q.push(start);
    while(!q.empty()) {
        int here = q.front();
        q.pop();
        for(int i=0; i<adj[here].size(); ++i) {
            int there = adj[here][i];
            if(distance[there]==-1) {
                q.push(there);
                distance[there] = distance[here]+1;
                parent[there] = here;
            }
        }
    }
}

vector<int> shortestPath(int v, const vector<int>& parent) {
    vector<int> path(1,v);
    while(parent[v] != v) {
        v = parent[v];
        path.push_back(v);
    }
    reverse(path.begin(), path.end());
    return path;
}
```



### Problems

그래프 모델링

- [Sorting Game(ID: SORTGAME, 난이도: 중)](https://www.algospot.com/judge/problem/read/SORTGAME)

- [어린이날(ID: CHILDRENDAY, 난이도: 상)](https://www.algospot.com/judge/problem/read/CHILDRENDAY)




### 최단 경로 전략

BFS 알고리즘이 최단 경로 문제를 푸는 가장 직관적인 방법이지만, 항상 최적으로 문제를 풀 수는 없다. 그래서 이 절에서는 양방향 탐색(Bidirectional Search)과 점점 깊어지는 탐색(Iteratively Deepening Search, IDS)를 다룬다.


#### 양방향 탐색

일반 시작점에서 시작하는 BFS 알고리즘이 정방향 탐색이라면, 목표 정점에서 시작해 거꾸로 올라오는 것은 역방향 탐색이다. 그리고 정방향 탐색과 역방향 탐색을 동시에 하면서, 이 둘이 가운데서 만나면 종료하는 것이 양방향 탐색이다.  

너비 우선 탐색이 방문하는 정점의 개수에 가장 직접적인 영향을 주는 요소는 최단 거리 d이다. 그리고 또다른 요소로는 분기 수 b가 있다. 예를 들어 책에서 설명하는 문제인 15-퍼즐에서는 분기가 4이므로 경로가 1씩 늘 때마다 방문하는 정점의 개수는 4배로 증가할 수 있다. 물론 정점이 중복될 수 있어서 이 문제에서는 분기가 2에서 3 사이의 수일 것이다. 그래서 대략적으로 계산하면 O(b^d)가 된다.

즉 최단 거리가 20이고 분기가 2인 경우를 가정하면, 정방향 탐색으로 했을 경우에는 2^20이 될 것이고 양방향 탐색으로 했을 경우에는 2*2^10이 될 것이다. 공간 복잡도의 경우에도 똑같은 비율로 줄어든다. 물론 이 양방향 탐색의 경우, 역방향 간선이 아주 많아서 역방향 탐색의 분기수가 클 경우나 목표 정점까지의 최단 거리가 너무 클 경우에 사용하기에는 힘들다. 

#### IDS

프로그램이 요구하는 메모리가 커지고 컴퓨터가 가지고 있는 물리적 메모리의 양을 넘어가기 시작하면 시간보다 메모리가 중요해진다. 따라서 규모가 큰 탐색을 할 경우에는 DFS를 이용한 IDS를 사용한다. 임의의 깊이 제한 l을 정한 후 이 제한보다 짧은 경로가 존재하는지를 DFS로 확인한다. 답을 찾으면 성공이니 반환하고, 아닐 경우 l을 늘려서 다시 시도한다.

### Problems

- [하노이의 탑(ID: HANOI4, 난이도: 중)](https://www.algospot.com/judge/problem/read/HANOI4)
