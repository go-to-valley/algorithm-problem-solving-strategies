# 28. DFS(Depth-First Search)

2020.05.06 Wed 

Byeongsu Kim



트리에서의 순회와 같이 그래프의 모든 node를 특정 순서에 따라 방문하는 알고리즘을 search 알고리즘이라고 한다. 그래프는 트리보다 훨씬 복잡한 구조를 가질 수 있기 때문에 search를 통해 얻는 다양한 정보들이 매우 중요하다. search에 사용된 edge나 node의 방문 순서 등을 이용해 graph의 구조를 알 수 있다. 가장 기본이 되는 DFS와 BFS중 이번 chapter에서는 DFS에 대해 알아본다.

기본적인 아이디어는 현재 node에서 인접한 edge들을 검사하며 아직 방문하지 않은 node로 이동하고, 모든 edge를 검사한 뒤에는 이전 node로 돌아가면 모든 node를 검사할 수 있다는 것이다.

단순한 dfs 구현은 아래와 같다. graph의 특성상 한 node로부터 도달할 수 없는 node가 존재할 수 있으므로 dfsAll()을 이용해 모든 node를 방문하도록 만들었다는 점에 주목하자.

```c++
vector<vector<int> > adj;
vector<bool> visited;

void dfs(int here){
    cout << "DFS visits" << here << endl;
    visited[here] = true;
    for(int i = 0; i < adj[here].size(); i++){
        int there = adj[here][i];
        if(!visited[there])
            dfs(there);
    }
}

void dfsAll(){
    visited = vector<bool>(adj.size(), false);
    for(int i = 0; i < adj.size(); i++)
        if(!visited[i])
            dfs(i);
}
```

위와 같은 방법으로 dfs를 구하면하면 시간복잡도는 O(|V|+|E|)가 된다.

#### Examples

- 두 정점이 서로 연결되어 있는가 확인하기
- 연결된 부분집합의 개수 세기
- 위상 정렬
  - 의존성이 있는 작업들이 주어질 때 어떤 순서로 수행할 수 있는지 계산
  - Directed Acyclic graph임을 이용하여 dfs로 간단히 해결 가능



### Euler Circuit

그래프의 모든 간선을 정확히 한 번씩 지나 시작점으로 돌아오는 경로를 찾는 문제를 Euler Circuit이라고 한다. 잘 알려져 있는 '한 붓 그리기'의 특수한 경우를 칭하는 것으로 시작점과 끝점이 같은 한 붓 그리기를 말한다. 한 붓 그리기에 대한 연구는 Graph Theory중에서 가정 먼저 연구된 분야에 속하기 때문에 수학적으로 많은 부분들이 증명되어 있다. Euler Circuit은 위의 증명 내용들을 활용하여 Graph의 모든 node가 하나의 component에 포함되고, 모든 node에 연결된 edge가 짝수개라면 존재한다는 것을 알 수 있다.

DFS의 원리에 대해 고민해 보면 Euler Circuit이 자연스럽게 구해진다는 것을 알 수 있다. 구현은 아래와 같다.

```c++
vector<vector<int> > adj; // 각 node 간 Edge 개수

void getEulerCircuit(int here, vector<int> &circuit){
    for(int there = 0; there < adj.size(); there++){
        while(adj[here][there] > 0){
            adj[here][there]--;
            adj[there][here]--;
                getEulerCircuit(there, circuit);
        }
    }
    circuit.push_back(here);
}
```

시간복잡도는 O(|V||E|) 이다.

- Euler Trail

  그래프의 모든 간선을 정확히 한 번씩 지나지만, 시작점과 끝점이 다른 경로를 Euler Trail이라고 하며, '한 붓 그리기'와 같다. Euler Trail은 시작과 끝 node에 연결된 edge는 홀수개이고, 나머지 node에 연결된 edge는 짝수개이고, 모든 node가 하나의 component에 포함되면 존재한다. 이 문제는 시작과 끝 node를 잇는 edge를 하나 추가 함으로써 Euler Circuit과 동일한 문제로 치환할 수 있다.



###  Theoretical background & Applications

DFS를 활용하여 그래프의 구조에 관련된 많은 문제를 풀 수 있다. 이를 위해 DFS에 대해 더 자세하게 알아보자.



#### Depth First Search & Classification of Edges

DFS에서 탐색을 진행한 Edge들을 모으면 Tree의 형태를 띄는 것을 알 수 있다. 이러한 형태의 트리를 DFS Spanning Tree라고 부르는데, 이후 Chapter에서 등장하는 Spanning Tree의 일종이다. 여기서 실제 탐색에 사용되지 않는 간선들을 아래와 같이 분류할 수 있다. DFS 내에서 Edge를 탐색하는 순서에 따라 DFS Spanning Tree는 달라질 수 있고, 각 Edge들도 다른 Set으로 분류될 수 있다.

- Tree Edge
- Forward Edge
- Back Edge
- Cross Edge

#### Classification of Edges at Undirected Graph

Undirected Graph의 특성에 따라 위의 DFS Spanning Tree를 구성했을 때 Edge들을 두 가지로 분류 할 수 있다.

- Tree Edge
- Forward Edge ( == Back Edge)



위 내용들에 대한 구현은 아래와 같다.

```c++
vector<vector<int> > adj; //Adjacent List
vector<int> discovered; // ith node의 발견 순서
vector<bool> finished; // dfs(i)가 종료했으면 true
int counter; //지금까지 발견한 node 개수

void dfs2(int here){
    discovered[here] = counter++;
    
    for(int i = 0; i < adj[here].size(); i++){
        int there = adj[here][i];
        cout << "(" << here << ", " << there << ") is a ";

        if(discovered[there] == -1){
            cout << "tree edge" << endl;
            dfs2(there);
        }
       	else if(discovered[here] < discovered[there])
            cout << "forward edge" << endl;
        else if(!finished[there])
            cout << "back edge" << endl;
        else
            cout << "cross edge" << endl;
    }
    finished[here] = true;
}
```



#### Examples

- 위상 정렬의 타당성 증명
- 사이클 존재 여부 확인(Cycle)
- 절단점 찾기 알고리즘(Cut Vertex)
  - 어떤 Undirected Graph의 절단점(cut vertex)이란 이 node와 인접한 edge를 모두 지웠을 때 해당 component가 두 개 이상으로 나뉘어지는 node를 뜻한다.
  - 각 node의 subtree에서 back edge가 존재하는지 확인하는 방법으로 한번의 dfs를 통해 모두 찾을 수 있다.
  - root node의 경우 child의 개수가 1, 0인 경우는 cut vertex가 아니다.
- 다리 찾기(Bridge)
  - cut vertex와 비슷하지만, bridge란 이 edge를 삭제했을 때 해당  component가 두 개로 나뉘어지는 edge를 뜻한다.
  - bridge는 항상 tree edge라는 점을 고려하여 back edge를 통해 접근 가능한 가장 높은 DFS spanning tree에서의 조상을 확인하면 찾을 수 있다.
- 강결합 컴포넌트 분리(Strongly Connected Components, SCC)



### Strongly Connected Components

Directed Graph에서 두 node u와 v사이에 양방향으로 가는 경로가 모두 있을 때 두 node는 같은 SCC에 속해 있다고 말한다. SCC사이를 연결하는 간선들을 모으면 SCC를 정점으로 하는 DAG를 만들 수 있다. 또한 SCC는 사이클과도 밀접하게 관련되어 있다. SCC는 현실세계에서도 중요한 의미를 갖기 때문에 이를 활용하여 다양한 문제를 해결할 수 있다.

원래 그래프의 정점들을 SCC별로 분리하고 각 SCC를 표현하는 정점들을 갖는 새로운 그래프를 만드는 과정을 Graph Condensation이라고 부른다.

DFS Spanning Tree를 그리고, 적절한 Edge를 자르면 SCC로 나눌 수 있다. 재귀 호출이 반환될 때마다 해당 간선을 자를지 여부를 판단함으로써 SCC를 구할 수 있는데 이 내용을 쉽고, 빠르게 이해하려면 똑똑해야 한다. 즉, 결론까지 이해하기 어렵다. 시간을 들여 책을 읽기 바라며, 결론은 아래와 같다.

Edge (u, v)는 v를 루트로 하는 sub tree에서 v보다 먼저 발견된 정점으로 가는 back edge가 있거나, v보다 먼저 발견되었음에도 아직 SCC로 묶여있지 않은 정점으로 가는 cross edge가 있다면 자르면 안된다.

Edge를 자르기로 한 경우 하나의 SCC를 새로 만든다. 재귀호출을 반환하면서 자를지를 결정하기 때문에 잘라야 하는 경우라면 sub tree내의 Edge들은 모두 판단이 끝났을 것이므로 아직 잘리지 않은 간선으로 v와 연결된 정점들을 모두 모아 하나의 SCC로 묶어준다.

위 결론의 구현은 아래와 같다.

```c++
vector<vector<int> > adj; //adjacent list
vector<int> sccID; //각 node의 SCC component번호(component 번호는 0부터 시작)
vector<int> discovere; //각 node의 발견 순서
stack<int> st; //node의 번호를 담는 stack;
int sccCounter; //SCC component번호 counter
int vertexCounter; //node의 발견 순서 counter

int scc(int here){
    int ret = discovered[here] = vertexCounter++;
    st.push(here);
    for(int i = 0; i < adj[here].size(); i++){
        int there = adj[here][i];
        if(discovered[there] == -1)
            ret = min(ret, scc(there));
        else if(sccId[there] == -1)
            ret = min(ret, discovered[there]);
    }
    if(ret == discovered[here]){
        while(true){
            int t = st.top();
            st.pop();
            sccId[t] = sccCounter;
            if(t == here) break;
        }
        ++sccCounter;
    }
    return ret;
}

vector<int> tarjanSCC(){
    sccId = discovered = vector<int>(adj.size(), -1);
    sccCounter = vertexCounter = 0;
    for(int i = 0; i < adj.size(); i++)
        if(discovered[i] == -1) scc(i);
    return sccId;
}
```

위 코드의 시간복잡도는 O(|V|+|E|)이다.

### Problems

- [고대어 사전(ID: DICTIONARY, 난이도: 하)](https://www.algospot.com/judge/problem/read/DICTIONARY)

- [단어 제한 끝말잇기(ID: WORDCHAIN, 난이도: 하)](https://www.algospot.com/judge/problem/read/WORDCHAIN)
- [감시 카메라 설치(ID: GALLERY, 난이도: 중)](https://www.algospot.com/judge/problem/read/GALLERY)

- [단어 제한 끝말잇기(ID: MEETINGROOM, 난이도: 상)](https://www.algospot.com/judge/problem/read/MEETINGROOM)