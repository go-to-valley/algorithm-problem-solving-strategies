# 16. Bitmask

2020.03.18 Wed 

Byeongsu Kim



사람이 코딩을 하는데에는 직접 드러나지 않지만 컴퓨터 내부 연산에서는 2진수를 이용한 bitwise연산을 수행한다. 이를 적용하여 사람이 코딩을 할 때 bit단위의 연산을 진행하면 매우 빠른 속도로 연산을 수행할 수 있다. 매우 빠른 속도를 가진 bitwise 연산을 잘 활용하여 적은 용량만으로 빠르게 연산하기 위해 Bitmask를 공부할 필요가 있다. 외에도 장점들을 정리해보면 아래와 같다.

- 빠른 수행시간
- 더 간결한 코드
- 더 작은 메모리 사용량
- 배열의 대체



### Bitwise Operator

and, or, xor, not, logicalLeftShift, logicalRightShift, arithmeticLeftShift, arithmeticRightShift 등이 있다. Shift의 경우 1의 보수를 이용해 음수를 표현하는 현대의 OS에서 부호를 유지하거나, 유지하지 않는 선택지를 두기 위해 arithmetic과 logical이 구분되어 있다. OS나 compiler의 환경에 따라 default shift가 다를 수 있기 때문에 헷갈림을 방지하기 위해 unsigned data type을 주로 사용하길 권장한다.

c++에서 사용되는 bitwise Operator는 아래와 같다.

| Operand     | code   |
| ----------- | ------ |
| AND         | a & b  |
| OR          | a \| b |
| XOR         | a ^ b  |
| NOT         | ~a     |
| LEFT SHIFT  | a << b |
| RIGHT SHIFT | a >> b |

```

```

보통 bitwise 연산은 일반 연산에 비해 우선순위가 낮으므로 괄호를 잘 해주어야 원하는 결과가 나온다.



### Set Implementation with Bitmask

N개의 원소가 Set에 속했는지의 여부를 bit단위로 표현하여 Set을 표현하는 방법을 의미한다.

예를들어 숫자를 원소로 가질 수 있는 Set A = {1, 4, 5, 6, 7, 9}라고 하면 bitmask를 활용하여 아래와 같이 표현할 수 있다.
$$
2^1+2^4+2^5+2^6+2^7+2^9 = 10 1111 0010_{2} = 754
$$
위 표현법을 활용하여 다양한 Set 연산을 구현할 수 있다.

- Empty Set

```c++
Set = 0;
```

- Full Set

```c++
Set = (1 << #AvailableElement) - 1;
```

- Add Element

```c++
Set |= (1 << e);
```

- Check Element in Set

```C++
if(Set & (1 << e)) cout << "element is in" << end;
```

- Delete Element

```C++
Set &= ~(1 << e);
```

- Toggle Element

```C++
Set ^= (1 << e);
```

- Binary Operation for two Sets

```C++
int added = (a | b);
int intersection = (a & b);
int removed = (a & ~b);
int toggled = (a ^ b);
```

- Size of Set

```C++
int bitCount(int x){
    if(x == 0) return 0;
    return x%2 + bitCount(x/2);
}
```

- Find Minimum Element

```C++
int minimumElement = (Set & -Set);
```

- Delete Minimum Element

```C++
Set &= (Set - 1);
```

- Search All SubSet

```C++
for(int subSet = Set; subSet; subSet = ((subset-1) & Set)){ }
```



### Applications of Bitmask

효율적인 알고리즘인 Dynamic Programming 기법을 더 효율적으로 만들기 위해 bitmask를 이용하는 방법을 공부한다. 특히 memoization에 bitmask를 사용하면 메모리 사용을 획기적으로 줄일 수 있다.

- 에라토스테네스의 체

```c++
int n;
unsigned char sieve[(MAX_N + 7) / 8];

inline bool isPrime(int k) {
	return sieve[k >> 3] & (1 << (k & 7));
}

inline void setComposite(int k){
    sieve[k >> 3] &= ~(1 << (k & 7));
}

void eratosthenes() {
    memset(sieve,, 255, sizeof(sieve));
    setComposite(0);
    setComposite(1);
    int sqrtn = int(sqrt(n));
    for(int i = 2; i <= sqrtn; i++)
        if(isPrime(i))
            for(int j = i*i; j <= n; j += i)
                setComposite(j);
}
```

- 15 퍼즐 상태 표현하기

```c++
typedef unsigned long long uint64;

int get(unit64 mask, int index) {
    return (mask >> (index << 2)) & 15;
}

unit64 set(uint64 mask, int index, uint64 value){
    return mask & ~(15LL << (index << 2)) | (value << (index << 2));
}
```

- Priority Queue
- 극대 안정 집합

```c++
int n;
int explodes[MAXN];
bool isStable(int set){
    for(int i = 0; i < n; i++)
        if((set & (1 << i)) && (set & explodes[i]))
            return false;
    return true;
}

int countStableSet(){
    int ret = 0;
    for(int set = 1; set < (1<<n); set++){
        if(!isStable(set)) continue;
        bool canExtend = false;
        for(int add = 0; add < n; add++){
            if((set & (1 << add)) == 0 && (explodes[add] & set) == 0){
                canExtend = true;
                break;
            }
        }
        if(!canExtend) ret++;
    }
    return ret;
}
```



### Problems

- [졸업학기(ID: GRADUATION, 난이도: 중)](https://www.algospot.com/judge/problem/read/GRADUATION)