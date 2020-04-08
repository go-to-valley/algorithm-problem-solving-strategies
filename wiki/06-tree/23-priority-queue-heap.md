# 23.우선순위 큐와 힙

## 도입

우선순위 큐는 가장 우선순위가 높은 자료가 먼저 꺼내진다. 우선순위 큐를 연결 리스트 배열로 구현할 시, 원소를 추가하는 데 O(1)의 시간, 꺼내는 데 O(N)의 시간이 걸린다. 균형잡힌 BST를 쓴다면 삭제, 삽입 모두 O(logN)에 가능하다. 하지만 이를 사용하는 건 오버헤드이며, 실질적으로 우선순위 큐 구현에 가장 많이 쓰이는 구조는 힙(Heap)이다. 힙을 사용하면 가장 큰(작은) 원소를 찾는데, 새 원소를 삽입하는데 모두 O(logN)안에 가능하다.

힙 역시 대부분의 라이브러리에 구현돼 있기에 여기서는 구현 방식을 이해하는데 초점을 맞춘다.

## 힙의 정의와 구현

힙은 다음 규칙을 만족하는 이진트리이다.

**부모 노드가 가진 원소는 항상 자식 노드가 가진 원소 이상**(최소 힙일 경우 이하)

**마지막 레벨을 제외한 모든 레벨에 노드가 꽉 차 있어야 한다**(균형잡힌 트리를 만들기 위함)

**마지막 레벨에 노드가 있을 때 가장 왼쪽부터 순서대로 채워져 있어야 한다**

### 배열을 통한 힙의 구현

힙이 요구하는 엄격한 조건은 오히려 구현을 단순하게 도와준다. 배열을 통해 힙을 만들 때는 다음 규칙을 만족하면 된다.

- A[i]에 대응하는 노드의 왼쪽 자손은 A[2*i+1]에 대응한다.
- A[i]에 대응하는 노드의 오른쪽 자손은 A[2*i+2]에 대응한다.
- A[i]에 대응하는 노드의 부모는 A[(i-1)/2]에 대응한다.

따라서 힙은 다음처럼 평범하게 만들 수 있다.

```java
    List<Integer> heap = new ArrayList<Integer>();
```

### 새 원소의 삽입

힙에 원소를 삽입할 때, 먼저 고려해주면 편한 규칙은 바로 모양 규칙이다. 배열 힙에서 새 노드는 항상 배열의 끝에 추가하면 된다. 모양 규칙을 만족 시킨 이후 새로 추가한 노드를 부모와 비교하여, 규칙이 어긋나면 위치를 바꾸는 방식을 규칙이 적용될 때 까지 반복한다. 반복 한번에 트리의 한 레벨을 올라가게 되므로 전체 시간 복잡도는 O(logN)이다.

### 최대 원소 꺼내기

최대 원소를 꺼낼 경우에도 모양 규칙을 먼저 충족하도록 힙을 만들어 준다. 원소를 꺼낼 경우 힙의 마지막 인덱스는 어짜피 비게 되므로 해당 위치의 원소를 비어있는 루트에 넣어준다. 이후 루트부터 두 자식이 자신보다 클 경우 더 큰 자식과 바꿔주는 작업을 두 자식이 자신보다 작아질 때 까지 반복한다. 이 역시 한번 반복에 한 레벨만큼 내려가므로 O(logN)의 시간 복잡도를 가진다.

```java
    public class Heap {
    	private ArrayList<Integer> heap = new ArrayList<>();

    	public void pushBack(int newValue) {
    		heap.add(newValue);
    		int index = heap.size() - 1;
    		while(index > 0 && heap.get((index - 1) /2) < heap.get(index)) {
    			Collections.swap(heap, index, (index - 1) / 2);
    			index = (index - 1) / 2;
    		}
    	}

    	public int popHeap() {
    		int top = heap.get(0);
    		heap.set(0, heap.get(heap.size() - 1));
    		heap.remove(heap.size() - 1);
    		int here = 0;
    		while(true) {
    			int left = here * 2 + 1;
    			int right = here * 2 + 2;
    			//리프에 도달
    			if (left >= heap.size()) break;

    			int next = here;
    			if (heap.get(next) < heap.get(left)) {
    				next = left;
    			}
    			if (right < heap.size() && heap.get(next) < heap.get(right)) {
    				next = right;
    			}
    			if (next == here) break;
    			Collections.swap(heap, here, next);
    			here = next;
    		}
    	return top;
    }
```

###
