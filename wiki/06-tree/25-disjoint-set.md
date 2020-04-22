# 25. Disjoint Set

2020.04.22

Seungrok Lee

이전에 설명했었던 구간 트리가 아닌 또 다른 형태의 독특한 트리로 상호 배타적 집합을 표현할 때 쓰는 Union-Find 자료 구조가 있다. Union-Find 자료 구조는 
공통 원소가 없는, 다시 말해 상호 배타적인 부분 집합들로 나눠진 원소들에 대한 정보를 저장하고 조작하는 자료 구조이다.

이 자료 구조를 만들기 위해서는 세 가지 연산(초기화, 합치기, 찾기)이 필요하다. 그리고 일반적으로 트리를 사용해 상호 배타적 집합을 표현한다.

```C++
struct NaiveDisjointSet{
  vector<int> parent;
  NaiveDisjointSet(int n) : parent(n) {
    for(int i=0; i<n; ++i) parent[i]=i;
  }
  //u가 속한 트리의 루트의 번호를 반환한다.
  int find(int u) const {
    if(u==parent[u]) return u;
    return find(parent[u]);
  }
  //u가 속한 트리와 v가 속한 트리를 합친다.
  void mere(int u, int v){
    u=find(u); v=find(v);
    if(u==v) return;
    parent[u] = v;
  }
};
```

여기서 find()와 merge() 함수 모두 해당 트리의 depth에 비례하는 시간이 걸린다.

### Optimization of Disjoint Set

배열을 사용할 때보다 트리를 이용하면 합치기 연산을 할 때 루트 하나의 정보만 바꾸면 되지만 여전히 트리가 한쪽으로 기울어진다는 문제가 생길 수 있다. 
이 문제는 간단하게 두 트리를 합칠 때 높이가 더 낮은 트리를 높이가 더 높은 트리 밑에 집어넣음으로써 트리의 높이가 높아지는 상황을 방지할 수 있다.

```C++
struct OptimizedDisjointSet {
  vector<int> parent, rank;
  OptimizedDisjointSet(int n) : parent(n), rank(n,1) {
    for(int i=0;i<n;i++) parent[i]=i;
  }
  //u가 속한 트리의 루트의 번호를 반환한다.
  int find(int u) {
    int(u==parent[u]) return u;
    return parent[u] = find(parent[u]);
  }
  //u가 속한 트리와 v가 속한 트리를 합친다.
  void merge(int u, int v) {
    u=find(u); v=find(v);
    if(u==v) return;
    if(rank[u]>rank[v]) swap(u,v);
    //이제 rank[v]가 항상 rank[u] 이상이므로 u를 v의 자식으로 넣는다.
    parent[u]=v;
    if(rank[u]==rank[v]) rank[v]++;
  }
}
```
이렇게 Disjoint Set을 구현하게 되면 합치기와 찾기 연산에 드는 시간 모두 O(logN)이 된다.
또다른 최적화로는 find(u)를 통해 u가 속하는 트리의 루트를 찾아내어 parent[u]를 아예 찾아낸 루트로 바꿔 버리면 다음부터 find(u)가 호출될 경우 계산 
시간이 적게 걸린다.


### Examples

- 그래프의 연결성 확인하기
- 가장 큰 집합 추적하기

### Problems

- 에디터 전쟁

```C++
struct BipartiteUnionFind{
//parent[i]=i의 부모 노드. 루트라면 i
//rank[i]=i가 루트인 경우, i를 루트로 하는 트리의 랭크
//enemy[i]=i가 루트인 경우, 해당 집합과 적대 관계인 집합의 루트의 번호. (없으면 -1)
//size[i]=i가 루트인 경우, 해당 집합의 크기
  vector<int> parent, rank, enemy, size;
  BipartiteUnionFind(int n) : parent(n), rank(n,0), enemy(n,-1), size(n,1) {
  for(int i=0;i<n;i++) parent[i]=i;
  }
  int find(int u) {
    if(parent[u]==u) return;
    return parent[u]=find(parent[u]);
  }
  int merge(int u, int v) {
    //u나 v가 공집합인 경우 나머지 하나를 반환한다.
    if(u==-1 || v==-1) return max(u,v);
    u=find(u); v=find(v);
    //이미 둘이 같은 트리에 속한 경우
    if(u==v) return u;
    if(rank[u]>rank[v]) swap(umv);
    //이제 항상 rank[v]가 더 크므로 u를 v의 자식으로 넣는다.
    if(rank[u]==rank[v]) rank[v]++;
    parent[u]=v;
    size[v] += size[u];
    return v;
  }
  //u와 v가 서로 적이다. 모순이 일어났다면 false, 아니면 true를 반환한다.
  bool dis(int u, int v) {
    //우선 루트를 찾는다
    u=find(u); v=find(v);
    //같은 집합에 속해 있으면 모순
    if(u==v) return false;
    //적의 적은 나의 동지
    int a = merge(u, enemy[v]), b = merge(v, enemy[u]);
    enemy[a]=b; enemy[b]=a;
    return true;
  }
  //u와 v가 서로 동지다. 모순이 일어났다면 false, 아니면 true를 반환한다.
  bool ack(int u, int v) {
    //우선 루트를 찾는다
    u=find(u); v=find(v);
    //두 집합이 서로 적대 관계라면 모순
    if(enemy[u]==v) return false;
    //동지의 적은 나의 적
    int a = merge(u,v), b = merge(enemy[u], enemy[v]);
    enemy[a]=b;
    //두 집합 다 적대하는 집합이 없으면 b는 -1일 수도 있다.
    if(b!=-1) enemy[b]=a;
    return true;
  }
};

int maxParty(const BipartiteUnionFind& buf) {
  int ret=0;
  for(int node=0;node<n;node++) {
    if(buf.parent[node]==node) {
      int enemy = buf.enemy[node];
      //같은 모임 쌍을 두번 세지 않기 위해, enemy<node인 경우만 센다.
      //enemy==-1인 경우도 정확히 한번만 세게 된다.
      if(enemy > node) continue;
      int mySize = buf.size[node];
      int enemySize = (enemy==-1?0:buf.size[enemy]);
      //두 집합 중 큰 집합을 더한다.
      ret += max(mySize, enemySize);
    }
  }
  return ret;
}
```
