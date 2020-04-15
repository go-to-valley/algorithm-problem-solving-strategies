# 24. Segment Tree

2020.04.15 Wed

Byeongsu Kim



아주 유용한 자료구조 중 하나인 Tree는 자료를 저장하는 용도 이외에도 빠르게 전처리를 진행해 둠으로써 수많은 query들에 대한 처리를 빠르게 하는데에도 사용할 수 있다. Segment Tree가 대표적으로 전처리를 빠르게 진행할 수 있게 도입한 자료구조로, 기본적인 아이디어는 주어진 배열의 구간들을 표현하는 이진 트리를 만들어두어 기존에 O(n)의 시간이 걸리는 문제를 O(log n)만에 해결할 수 있도록 만드는 것이다. 

구간트리의 루트는 항상 배열의 전체 구간을 표현하며, 한 트리의 왼쪽 자식과 오른쪽 자식은 각각 해당 구간의 왼쪽 반과 오른쪽 반을 표현한다. 이러한 특징을 응용해 보면 길이가 1인 구간을 표현하는 노드들이 리프가 됨을 쉽게 알 수 있다.



대표적인 예제인 특정 구간의 최소치를찾는 문제(Range Minimum Query, RMQ)를 이용해 설명해 보겠다.

### Expression of Segment Tree

Segment Tree는 그 특성상 거의 꽉 찬 트리이다. 이러한 형태의 트리를 표현할 때는 배열로 표현하는 것이 메모리를 절약할 수 있다. 각 노드에 접근하는 index는 아래와 같이 정의할 수 있다.

- root node -> 1
- ith node's left child -> 2*i
- ith node's right child -> 2*i+1

위 규칙을 사용하여 segment tree를 나타내면 길이 n의 array에 대해 가장 가까운 2의 거듭제곱으로 n을 올림한 뒤 2를 곱해야 하는 복잡함이 있기 때문에 요즘과 같이 메모리가 여유로운 세상에서는 그냥 4n만큼의 공간을 할당하면 편하다.

### Initialization of Segment Tree

각 노드마다 해당 구간의 최소치를 계산하는 함수 init()을 아래 코드와 같이 구현할 수 있다. 눈여겨 볼 점은 아래와 같다.

- 구간트리의 각 노드에 대해 위치를 저장하지 않는다.
- 두 구간으로 나누어 재귀호출 한 뒤 원하는 값으로 처리한다.

``` C++
struct RMQ{
    int n;
    vector<int> rangeMin;
    RMQ(const vector<int>& array){
        n = array.size();
        rangeMin.resize(n*4);
        init(array, 0, n-1, 1);
    }
    
    int init(const vector<int>& array, int left, int right, int node){
        if(left == right) return;
        int mid = (left+right)/2;
        int leftMin = init(array, left, mid, node*2);
        int rightMin = init(array, mid+1, right, node*2+1);
        return rangeMin[node] = min(leftMin, rightMin);
    }
}
```

위 과정에 대한 시간복잡도는 O(n)이다.

### Handling of Query Request

함수 query(left, right, node, nodeLeft, nodeRight)를 다음과 같이 정의하자.

node가 표현하는 범위 [nodeLeft, nodeRight]와 우리가 최소치를 찾기 원하는 구간 [left, right]의 교집합의 최소 원소를 return.

root node는 배열 전체 범위를 표현하므로 구간 [i, j]의 최소치는 query(i, j, 1, 0, n-1)로 구할 수 있다. 이 상황에서 method query는 세가지 경우의 수로 나누어 해결할 수 있다.

- 교집합이 공집합인 경우

  두 구간은 서로 겹치지 않으므로 최소값에 해당되지 않을 INF를 return;

- 교집합이 [nodeLeft, nodeRight]인 경우

  [left, right]가 node가 표현하는 집합을 완전히 포함하므로 미리 계산해둔 최소치를 return;

- 이외의 모든 경우

  두 child node에 query()를 재귀 호출 하고 두 값중 더 작은 값을 return;

```c++
const int INT_MAX = numeric_limits<int>::max();
struct RMQ {
    ...
       
    int query(int left, int right, int node, int nodeLeft, int nodeRight){
        if(right < nodeLeft || nodeRight < left) return INT_MAX;
        if(left <= nodeLeft && nodeRight <= right) return rangeMin[node];
        int mid = (nodeLeft+nodeRight)/2;
        return min(query(left, right, node*2, nodeLeft, mid),
                   query(left, right, node*2+1, mid+1, nodeRight));
    }
    
    int query(int left, int right){
        return query(left, right, 1, 0, n-1);
    }
}
```

위 과정에 대한 시간복잡도는 O(log n)이다.

### Update of Segment Tree

전처리를 통해 segment tree를 생성한 후 원래의 배열 값이 바뀐다면 segment tree를 갱신함으로써 빠르게 해결할 수 있다.

원래 배열의 index 위치의 값이 newValue로 바뀌었다면 이 위치를 포함하는 segment tree의 node는 O(log n)개 있을 것이므로 O(log n)만에 segment tree를 갱신할 수 있다. 마치 query()와 init()이 합쳐진 것 처럼 구현되는 update()는 아래와 같이 구현된다.

```c++
struct RMQ{
    ...
    
    int update(int index, int newValue, int node, int nodeLeft, int nodeRight){
        if(index < nodeLeft || nodeRight < index) return rangeMin[node];
        if(nodeLeft == nodeRight) return rangeMin[node] = newValue;
        int mid = (nodeLeft+nodeRight)/2;
        return rangeMin[node] = min(update(index, newValue, node*2, nodeLeft, mid),
                                   update(index, newValue, node*2+1, mid+1, nodeRight));
    }
    
    int update(int index, int newValue){
        return update(index, newValue, 1, 0, n-1);
    }
}
```

위 과정에 대한 시간복잡도는 O(log n)이다.



### Examples

Segment Tree는 이외에도 다양한 문제에 적용할 수 있다.

- 특정 구간에서 최소치 두 개 찾기
- 정렬된 수열의 특정 구간에서 최대 출현 빈도 계산

```c++
struct RangeResult {
    int size;
    int mostFrequent;
    int leftNumber, leftFreq;
    int rightNumber, rightFreq;
};

RangeResult merge(const RangeResult& a, const RangeResult& b){
    RangeResult ret;
    ret.size = a.size+b.size;
    ret.leftNumber = a.leftNumber;
    ret.leftFreq = a.leftFreq;
    if(a.size == a.leftFreq && a.leftNumber == b.leftNumber)
        ret.leftFreq += b.leftFreq;
    ret.rightNumber = b.rightNumber;
    ret.rightFreq = b.rightFreq;
    if(b.size == b.rightFreq && a.rightNumber == b.rightNumber)
        ret.rightFreq += a.rightFreq;
    ret.mostFrequent = max(a.mostFrequent, b.mostFrequent);
    if(a.rightNumber == b.leftNumber)
        ret.mostFrequent = max(ret.mostFrequent, a.rightFreq+b.leftFreq);
    return ret;
}
```



### Problems

- [등산로(ID: MORDOR, 난이도: 중)](https://www.algospot.com/judge/problem/read/MORDOR)
- [족보 탐험(ID: FAMILYTREE, 난이도: 상)](https://www.algospot.com/judge/problem/read/FAMILYTREE)



### Fenwick Tree

Binary Indexed Tree라고도 불리는 Fenwick Tree는 구간합 대신 부분 합 만을 빠르게 계산할 수 있는 자료구조를 만들어도 구간 합을 빠르게 계산할 수 있다는 아이디어에서 만들어 졌다. 그래서 수시로 변화하는 값에 대한 부분합을 계산하기 위해서라면 fenwick tree를 자주 사용한다. 부분합만을 계산한다고 생각하면 segment tree에서 미리 계산하는 정보가 상당수 필요없기 때문이다.

Fenwick tree에서는 segment tree에서 부분합을 구하는데 필요한 부분만 남겨서 사용하는데, 정확히 n개의 공간을 사용하면 된다. 이를 활용하여 A[pos]까지의 구간합 psum[pos]를 구하려면 이에 해당하는 구간을 fenwick tree에서 찾아 모두 더하면 된다. 이를 쉽게 하기 위해 A[]와 tree[]의 모든 원소들의 index에 1을 더해 index를 1-n으로 만든다. 이렇게 하면 이진수 표현에서 각 구간들간의 관계를 쉽게 포착할 수 있다.

부분합을 구할 때는 원하는 위치 pos의 이진수 형태에서 마지막 비트를 지워면 더해야 할 다음 구간을 찾을 수 있다.

배열의 값을 변경하는 상황에서는 맨 오른쪽 1 비트를 스스로에게 더해주는 연산을 반복하면 변화시켜야 할 다음 구간을 찾을 수 있다.

위 두 방법을 이용해 코드를 작성하면 아래와 같이 매우 짧고 간단하게 구현할 수 있다.

```c++
struct FenwickTree{
    vector<int> tree;
    FenwickTree(int n) : tree(n+1){}
    
    int sum(int pos){
        ++pos;
        int ret = 0;
        while(pos > 0){
            ret+= tree[pos];
            pos &= (pos-1);
        }
        return ret;
    }
    
    void add(int pos, int val){
        ++pos;
        while(pos < tree.size()){
            tree[pos] += val;
            pos += (pos&-pos);
        }
    }
};
```

두 연산 모두 시간복잡도는 O(log n)이다. 매우 간결한 코드이므로 사용하기에 매우 편리하다.

### Problem

- [삽입 정렬 시간 재기(ID: MEASURETIME, 난이도: 중)](https://www.algospot.com/judge/problem/read/MEASURETIME)

  이 문제는 Fenwick Tree, Segment Tree, Binary Search Tree, Merge Sort를 이용해 풀 수 있다.

