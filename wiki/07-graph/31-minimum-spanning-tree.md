# 31. 최소 스패닝 트리

## 도입

무향 그래프의 스패닝 트리는 원래 그래프의 정점 전부와 간선의 부분 집합으로 구성된 부분 그래프이며, 이 때 스패닝 트리에 포함된 간선들은 정점들을 전부 트리 형태로 연결, 즉 사이클을 이루지 않아야 한다.

한 그래프에서 스패닝 트리는 여러개가 있을 수 있고, 지금부터 다루는 내용은 가중치 그래프의 스패닝 트리에서 가중치의 값이 가장 작은 최소 스패닝 트리를(MST) 찾는 방법이다.

## 두 알고리즘

MST를 찾는 유명한 알고리즘은 다음 두가지가 있다.

- 크루스칼 알고리즘 - 상호 배타적 집합의 유용한 사용처
- 프림 알고리즘 - 다익스트라 알고리즘과 유사

중요한 점으로는 두 알고리즘 모두 탐욕법인데, 같은 방법으로 증명이 가능하다는 것이다.

## 크루스칼 알고리즘

크루스칼 알고리즘은 그래프의 모든 간선을 오름차순으로 정렬한 후, 이를 최소부터 스패닝 트리에 추가하는 알고리즘이다. 중요한 건 추가 도중 사이클을 구성하는 간선의 경우 트리에 포함시키지 않는다는 점이다.

### 자료구조

크루스칼 알고리즘 구현의 핵심은 간선을 추가할 때, 사이클을 이루는지 여부를 확인하는 것이다. 이를 구현하는 한가지 방법은 단순하게 간선을 추가했을 때, 트리를 DFS를 통해 역방향 간선이 생기는지 탐색하는 것이다. 이렇게 했을 때 전체 알고리즘의 시간 복잡도는 O(V+E) 에 O(E)를 곱해 O(E^2)이 된다.

어떤 간선을 추가해서 그래프에 사이클이 생기려면 간선의 양 끝 점은 같은 컴포넌트에 속해 있어야 한다. 따라서 상호 배타적 집합을 이용하면 한 간선을 추가할 때, 간선의 양 끝이 같은 컴포넌트에 속해있는지 쉽게 확인할 수 있다.

### 구현

```java
@Getter
@AllArgsConstructor
public class Vertex {
	private int index;
	private int weight;
}

@Getter
@AllArgsConstructor
public class Edge {
	private int left;
	private int right;
	private int weight;
}

public class MinimumSpanningTree {
	private	List<Edge> selected;
	private	int ret;

	// 인접 리스트를 담은 맵의 key는 정점 번호, value는 해당 정점에 인접한 정점(정점 번호, 가중치) 리스트
	public void initializeByKruskal(Map<Integer, List<Vertex>> adjMap, int V) {
		List<Edge> edges = new ArrayList<>();
		selected = new ArrayList<>();
		ret = 0;

		adjMap.forEach((index, list) -> {
			list.forEach(vertex -> {
				int target = vertex.getIndex();
				int cost = vertex.getWeight();
				edges.add(new Edge(index, target, cost));
			}
		});


		list.sort((a,b) -> a.getWeight() > b.getWeight() ? 1 : -1);
		DisjointSet set = new DisjointSet(V);

		edges.forEach(edge -> {
			if(set.find(edge.getLeft()) == set.find(edge.getRight()) {
				set.merge(edge.getLeft(), edge.getRight());
				selected.add(edge);
				ret += edge.getWeight();
			}
		});
}
```

DisjointSet에서 연산의 시간 복잡도는 상수 시간과 같으므로 실제 트리를 만드는 for문의 복잡도는 O(E), 전체 복잡도는 O(ElogE)

### 정당성 증명

크루스칼 알고리즘은 탐욕적 알고리즘이며, 탐욕법의 정당성 증명은 두 부분으로 나뉜다. 첫 번째는 탐욕적 선택으로 손해보지 않는다는 것을 증명하는 것이며, 두 번째는 항상 최적의 선택을 내리는 것이 문제의 최적해를 찾을 수 있다는 것을 증명하는 것이다. MST에서 후자는 쉽게 증명이 가능하므로 전자를 증명하는 것이 중요하다.

탐욕적 선택에 대한 증명은 귀류법으로 증명이 가능하다. 크루스칼 알고리즘이 선택하는 간선 중 MST인 T에 포함되지 않는 간선이 있다고 하자. 이 중 첫번째로 선택되는 간선을 (u,v)라고 하면, T가 이를 포함하지 않으니, u와 v는 T상에서 (u,v)가 아닌 다른 경로로 연결되어 있을 것이다. 이 경로를 이루는 간선 중 하나는 반드시 (u,v)보다 가중치가 같거나 커야한다. 만약 작다면 이미 크루스칼 알고리즘이 간선들을 모두 선택해서 (u,v)를 연결해 버렸을 것이기 때문이다. 따라서 방금 경로에서 (u,v)이상의 가중치를 가지는 간선을 지우고, (u,v)를 넣어도 해당 트리는 여전히 MST이며, 가중치의 총합은 늘지 않는다. 따라서 (u,v)를 선택한다고 해도 항상 MST를 얻을 수 있게 된다.

## 프림 알고리즘

프림 알고리즘은 시작점을 정해 트리에 인접한 간선들 중 가장 가중치가 작은 간선을 트리에 포함시켜 가는 알고리즘이다. 따라서 항상 선택된 간선들은 중간 과정에서도 항상 연결된 트리를 이루게 된다.

### 구현

프림 알고리즘을 구현하는 가장 간단한 방법은 각 정점이 트리에 포함됐는지 여부를 저장하는 불린 배열을 이용해, 각 차례마다 모든 간선을 순회하며 다음으로 트리에 추가할 간선을 찾는 것이다. 이 경우 정점을 하나 추가하는 데 O(E)의 시간이 걸리므로 전체 시간 복잡도는 O(VE)가 된다.

이를 더 최적화하는 방법은 한 정점에 닿는 간선이 두 개 이상일 경우 이들을 하나하나 검사하는 과정이 시간 낭비임을 깨닿는 것이다. 트리에 속하지 않는 각 정점에 대해, 트리와 이 정점을 연결하는 가장 짧은 간선에 대한 정보를 저장하고, 각 정점을 순회하면서 다음에 추가할 정점을 찾으면 O(V)만에 추가할 간선을 찾을 수 있다.

프림 알고리즘에서는 이를 위해 트리와 해당 정점을 연결하는 간선의 최소 가중치를 저장하는 배열 minWeight를 유지한다. 이 배열은 트리에 정점을 추가할 때마다 이 정점에 인접한 간선들을 순회하면서 갱신한다. 이 경우 추가할 정점을 찾는데 O(V), 간선의 검사는 두 번씩으로 총 O(V^2+E)의 시간 복잡도를 가진다.

```java

public class MinimumSpanningTree {
	private	List<Edge> selected;
	private	int ret;

	// 인접 리스트를 담은 맵의 key는 정점 번호, value는 해당 정점에 인접한 정점(정점 번호, 가중치) 리스트
	public void initializeByPrim(Map<Integer, List<Vertex>> adjMap, int V) {
		final int INF = Integer.MAX_VALUE;
		List<Edge> edges = new ArrayList<>();
		List<Boolean> added = new ArrayList<Integer>(Collections.nCopies(V, false));
		List<Integer> minWeight = new ArrayList<Integer>(Collections.nCopies(V, INF));
		List<Integer> parent = new ArrayList<Integer>(Collections.nCopies(V, -1));

		selected = new ArrayList<>();
		ret = 0;

		parent.set(0, 0);
		minWeight.set(0, 0);

		for (int iter = 0; iter < V; iter++) {
			int u = -1;
			for (int v = 0; v < V; v++) {
				if (!added.get(v) && (u == -1 || minWeight.get(u) > minWeight.get(v)) {
						u = v;
				}
			}

			if (parent.get(u) != u) {
				selected.add(new Edge(parent.get(u), u, minWeight.get(u));
				ret += minWeight.get(u);
				added.set(u, true);
			}
			adgMap.get(u).forEach(vertex -> {
				int v = vertex.getIndex();
				if (!added.get(v) && minWeight.get(v) > vertex.getWeight()) {
					parent.set(v, u);
					minWeight.set(v, vertex.getWeight());
				}
			});
		}
}
```

### 다른 구현

다익스트라 알고리즘 처럼, 우선순위 큐에 각 정점의 번호를 minWeight[]이 증가하는 순으로 정렬해 담으면 O(ElogV)에 프림 알고리즘의 구현이 가능하다.

### 정당성 증명

크루스칼 알고리즘과 같은 방식으로 정당성의 증명이 가능하다.

## 문제: 근거리 네트워크 (LAN)

MST 찾기 문제.

케이블이 이미 놓여 이어진 건물들을 정점 하나로 압축한 후 MST를 구하는 문제.

이미 이어진 건물들 간 간선을 가중치 0으로 두고 MST를 구하면 됨

## 문제: 여행 경로 정하기 (TPATH)

### 문제 나누기

- 0 → v-1 으로 가는 경로를 찾으면서 동시에 경로에 조건이 걸려있음.
- 조건에서 고려해야할 변수가 두가지(경로중 가중치가 최소인 간선, 최대인 간선) 꼴이 된다. (|x-y|)가 최소
- y값을 고정시킨 상태에서 해가 되는 x값을 다 찾아보면서 해결

경로에서 가중치 최소인 간선y를 고정시켰을 때 여러 경로중 x가 최소인 경우를 찾는 알고리즘을 구현해야함.

→ y이상인 가중치를 기중으로 MST를 찾다가 0, v-1이 모두 포함되는 경우 종료시키면 됨
