# 20. 문자열

## 도입

문자열을 다루는 알고리즘은 매우 범위가 넓고 깊지만, 문제에서는 대부분 구현이 비교적 간단한 알고리즘이 주로 사용된다.

### 용어

- 문자열의 길이: |S|
- S의 i(0≤i<|S|)번 글자: S[i]
- S의 i번에서 j번 까지의 글자로 구성된 substring: S[i...j]
- S의 0번 글자부터 a번 글자까지 구성된 substring (prefix): S[...a]
- S의 b번 글자부터 끝 글자까지 구성된 substring (suffix): S[b...]

## 검색

문자열 H가 문자열 N을 substring으로 포함하는 지 확인하고, 포함한다면 해당 substring이 시작하는 모든 위치를 찾는 문제를 검색 문제라고 한다.

### 기본 알고리즘

N의 모든 위치를 다 시도해보는 방법

    List<Integer> naiveSearch(String H, String N) {
    	List<Interger> ret = new ArrayList<>();
    	for (int begin = 0; begin + N.length() <= H.length(); ++begin) {
    		boolean matched = true;
    		for (int i = 0; i < N.length(); ++i) {
    			if (H.charAt(begin + 1) != N.charAt(i)) {
    				matched = false;
    				break;
    		}
    		if (matched) ret.add(begin);
    	}
    	return ret;
    }

이 알고리즘은 구현도 쉽고 성능도 괜찮아서 일반적으로 사용하기에 문제 없기에 표준 라이브러리에 널리 쓰인다. 하지만 특정 경우의 인풋에 대해 굉장히 비효율적이다. 예를 들어 H, N이 모두 a로만 구성된 긴 문자열이라고 한다면, 모든 시작 위치가 답이 된다. 하지만 이 알고리즘은 이를 알 수 없으므로 |H| - |N| + 1개의 모든 시작위치에 대해 비교를 하고, 시간 복잡도는 O(|H| \* |N|)이 된다.

### KMP 알고리즘

문자열 N = "aabaabac"를 찾는 경우를 예로 들어보자. 시작 위치 i에서 N을 맞췄을 때 앞의 일곱 글자 "aabaaba"까지만 일치했다고 하였을 때, 일곱 글자가 일치하는지 체크하는 과정 동안 다음 시작 위치가 어디서 부터 시작되어야 할지 정보를 알아낼 수 있다. "aabaaba"내부에서 "aabaaba"라는 답이 될 가능성이 있는 시작 위치는 i+3과 i+ 6밖에 없다. 따라서 이를 이용한 최적화 알고리즘을 Knuth-Morris-Pratt 알고리즘이라고 한다.

### 다음 시작 위치 찾기

KMP알고리즘은 문자열 N을 전처리 해서 다음 시작 위치를 쉽게 찾을수 있는 정보들을 저장해 놓는다. H의 i번 인덱스부터 시작하여 N[...matched-1]까지 문자열이 일치했다고 한다면, i+k의 위치에서 답을 찾을 수 있기 위해서는 N[...matched-1]에서 길이가 matched-k 인 접두사와 접미사가 같아야한다. 따라서 전처리 과정에서 정의할 배열을 다음과 같이 계산할 수 있다

pi[i] = N[...i]의 접두사도 되고 접미사도 되는 문자열의 최대 길이

### 구현

```java
    List<Integer> kmpSearch(String H, String N) {
    	int n = H.length();
    	int m = N.length();
    	List<Integer> ret = new ArrayList<>();
    	List<Integer> pi = getPartialMatch(N); // 밑에서 구현

    	int begin = 0;
    	int matched = 0;
    	while(begin <= n - m) {
    		if(matched < m && H.get(begin + matched) == N.get(matched)) {
    			++matched;
    			if(matched == m) ret.add(begin);
    		} else {
    			if(matched == 0) {
    				++begin;
    			} else {
    				begin += matched - pi.get(matched - 1);
    				matched = pi.get(matched - 1);
    			}
    		}
    	}
    	return ret;
    }
```

### 부분 일치 테이블 생성

부분 일치 테이블을 만드는 가장 쉬운 방법은 길이 p인 접두사 N[...p-1]이 주어졌을 때, 길이 p-1,p-2,p-3,... 인 접두사를 순회하며 이들이 N[...p-1]의 접미사가 되는지 확인하는 방법이다. 이를 그대로 구현한다면 접두사 하나당 각 접두사 길이의 제곱에 비례하는 시간이 걸리기에 |N|개의 모든 접두사에 대해 수행하면 O(|N|^3)의 수행 시간이 걸린다.

이를 더 빠르게 최적화 시키는 방법은 N에서 N을 검색하는 것을 응용하는 방법이 있다. 비교의 시작 인덱스가 i라고 할 때, i+k까지 일치한다면, substring N[0...i+k]에서 길이 k만큼 접두사와 접미사가 일치한다고 볼 수 있기 때문이다. 이 경우에는 O(|N|^2)까지 시간을 단축할 수 있다.

    List<Integer> getPartialMatchNaive(String N) {
    	int m = N.length();
    	List<Integer> pi = new ArrayList<>(Collectors.nCopies(m, 0));

    	for(int begin = 0; i + begin < m; ++i) {
    		for(int i = 0; i + begin < m; ++i) {
    			if(N.get(begin + i) != N.geti)) break;
    			pi.set(begin + i, max(pi.get(begin + i), i + 1));
    		}
    	}
    	return pi;
    }

위의 알고리즘 역시 결국은 검색이기에, 이 자체도 다음과 같이 KMP 검색을 이용해 구현하는 방법이 있다.

```java
    List<Integer> getPartialMatch(String N) {
    	int m = N.length();
    	List<Integer> pi = new ArrayList<>(Collectors.nCopies(m, 0));

    	int begin = 1; // N을 N에서 찾는다, begin = 0 일 경우 자기 자신을 찾으니 안된다.
    	int matched = 0;
    	while(begin + matched < m) {
    		if(N.get(begin + matched) == N.get(matched)) {
    			++matched;
    			pi.set(begin + matched - 1, matched); // 부분 일치를 모두 기록
    		} else {
    			if(matched == 0) {
    				++begin;
    			} else {
    				begin += matched - pi.get(matched - 1);
    				matched = pi.get(matched - 1);
    			}
    		}
    	}
    	return pi;
    }
```

이 경우 시간 복잡도는 O(|N|)으로, 이를 이용해 KMP 알고리즘을 수행하면 전체 시간 복잡도는 O(|N| + |H|) 가 된다.

## 접미사 배열

접미사 배열은 어떤 문자열 S의 모든 접미사를 사전순으로 정렬해 둔 배열이다. 접미사 배열은 다양한 문자열 문제를 푸는데 쓰이며 메모리 절약을 위해서 접미사 전체를 담지 않고 각 접미사의 시작 위치를 담는 정수 배열로 구현된다.

### 접미사 배열을 이용한 검색

H가 N을 포함한다면, N은 항상 H의 어떤 접미사의 접두사이다. 따라서 H의 접미사 배열을 이진 탐색해서 N을 구할 수 있다. 이진 탐색에 O(log|H|), 문자열 비교에 O(|N|)의 시간이 소요되므로 시간복잡도는 O(|N|log|H|)이다.

### 접미사 배열의 생성

접미사 배열을 만드는 간단한 방법으로 일반적 정렬 알고리즘을 사용하는 방법이 있다. 통용되는 정렬 알고리즘의 시간복잡도는 O(|N|log|N|)에, 문자열 비교에 걸리는 시간 O(|N|)을 고려하면 전체 시간 복잡도는 O(|N|^2log|N|)이다.

### 맨버-마이어스 알고리즘

이 알고리즘은 접미사들의 목록을 여러 번 정렬하는데, 첫 정렬에는 접미사의 첫 글자, 두 번째에는 접미사의 첫 두 글자, 세 번째에는 네 글자와 같이 logN번 반복해 접미사 배열을 얻는다. 각 반복시마다, 비교하는 부분 문자열이 같은 경우를 그룹화하는데, 이를 통해 각 비교마다 두 문자열의 대소비교를 O(1)에 가능하다.

"mississipi"라는 문자열을 정렬할 때, 정렬 방식은 다음과 같다.

1. 모든 접미사를 첫 문자만 비교하여 정렬한다. (O(nlogn))
2. 정렬된 접미사들 중 첫 글자를 기준으로 그룹을 만들어준다(group[i] = S[i...]가 속한 그룹의 번호). 이 때 첫 글자가 가장 빠른 접미사 그룹이 가장 작은 값을 가진다. mississipi의 경우 i로 시작할 경우 가장 빠르므로 group[1]=group[4]=group[7]=group[9]=0, 그 다음 m이므로 group[0]=1 과 같은 식으로 값이 매겨진다.
3. group[]의 크기는 n + 1이며, group[n](길이 0인 접미사 표현)은 항상 -1이다. 이는 비교식을 짤 때 오류를 막기 위해 필요하다.
4. 이전 비교 글자 수의 2배를 하여 다시 반복한다.

첫 t글자를 이용해 만든 group[]이 있으면 두 접미사 S[i...], S[j...]의 우선순위는 group[i]와 group[j]의 값을 비교해 쉽게 알 수 있다. 첫 2글자를 기준으로 어느 쪽이 사전 순으로 빠른지 비교하기 위해서는 먼저 첫 t글자를 비교한다. 이들이 서로 다르다면 비교가 바로 가능하고, 같을 경우 S[i+t...]와 S[j+t...]의 첫 t글자를 서로 비교하면 된다. 즉 상수시간이 걸린다. 따라서 매 반복마다 비교하는 글자 수를 2배씩 늘리더라도 항상 O(nlogn)에 정렬할 수 있는 것이다.

```java
    public class GroupComparator implements Comparator<Integer>() {
    	private List<Interger> group;
    	private int t;

    	public GroupComparator(List<Interger> group, int t) {
    		this.group = group;
    		this.t = t;
    	}

    	@Override
    	public int compare(int a, int b) {
    		if (group.get(a) < group.get(b)) return -1;
    		else if (group.get(a) > group.get(b)) return 1;
    		else if (group.get(a + t) < group.get(b + t)) return -1;
    		else return 1;
    	}
    }

    public ArrayList<Integer> getSuffixArray(String s) {
    	int n = s.length();
    	int t = 1;
    	List<Integer> group = new ArrayList<>(n + 1);

    	for(int i = 0; i < n; ++i) {
    		group.set(i, s.charAt(i)); // t=1일 때는 S[i...]의 첫 글자로 그룹 번호를 정해도 같은 효과
    	}
    	group.set(n, -1);

    	ArrayList<Integer> perm = new ArrayList<Interger>(n); // 결과 접미사 배열
    	while(t < n) {
    		Comparator<Integer> comparator = new GroupComparator(group, t);
    		Collections.sort(perm, coparator);
    		t *= 2;
    		if(t >= n) break;
    		List<Integer> newGroup = new ArrayList<>(n + 1);
    		newGroup.set(n, -1);
    		newGroup.set(perm.get(0)) = 0;
    		for(int i = 1; i < n; ++i) {
    			if(comparator.compare(perm.get(i - 1), perm.get(i)) {
    				newGroup.set(perm.get(i), newGroup.get(perm.get(i-1)) + 1);
    			} else {
    				newGroup.set(perm.get(i), newGroup.get(perm.get(i-1));
    			}
    		}
    		group = newGroup;
    	}
    	return perm;
    }
```

while문 내부의 시간 복잡도가 O(nlogn)이므로 전체 시간 복잡도는 O(nlog^2n)이 된다.

### 예제 : 접두사

```java
    List<Integer> getPrefixSuffix(String s) {
    	List<Integer> ret = new ArrayList<>();
    	List<Integer> pi = getPartialMatch(s);
    	int k = s.length();
    	while (k > 0) {
    		// s[..k-1]은 답
    		ret.add(k);
    		k = pi.get(k-1);
    	}
    	return ret;
    }
```

S자신은 정답

부분 일치 테이블의 |s| - 1 값,

길이가 k이 이하인 S의 접미사는 S[...k-1]의 접미사이기도 함. → k 다음으로 긴 접미사는 pi[k-1]을 확인

### 예제 : 팰린드롬

```java
    int maxOverlap(String a, String b) {
    	int n = a.size();
    	int m = b.size();
    	List<Integer> pi = getPartialMatch(b);

    	int begin = 0;
    	int matched = 0;

    	while (begin < 0) {
    		if(matched < m && a.get(begin + matched) == b.get(matched) {
    			++matched;
    			if(begin + matched == n) {
    				return matched;
    			}
    		} else {
    			if(matched == 0) {
    				++begin;
    			else {
    				begin += matched - pi.get(matched - 1);
    				matched = pi.get(matched - 1);
    			}
    		}
    	}
    	return 0;
    }
```

S를 뒤집은 S'을 만들어, S안에서 검색하면서 S'이 앞부분이 S의 남은 부분과 전부 일치하는 위치를 찾는다.

### 예제 : 원형 문자열

```java
    String minShift(String s) {
    	String s2 = s + s;
    	List<Integer> a = getSuffixArray(s2);
    	for (int i = 0; i < a.size(); ++i) {
    		if(a.get(i) <= s.length()) {
    			return s2.substring(a.get(i), s.length());
    	}
    	return "___oops___";
    }
```

s를 두개 이어붙인 문자열의 접미사 배열에서 길이가 n이상인 접미사 중 가장 앞에오는 것

### 예제 : 서로 다른 부분 문자열 수

```java
    int commonPrefix(String s, int i, int j) {
    	int k = 0;
    	while(i < s.length() && j < s.length() && s.charAt(i) == s.charAt(j)) {
    		++i; ++j; ++k;
    	}
    	return k;
    }

    int countSubstrings(String s) {
    	List<Integer> a = getSuffixArray(s);
    	int ret = 0;
    	int n = s.length();
    	for (int i = 0; i < a.size(); ++i) {
    		int cp = 0;
    		if(i > 0) cp = commonPrefix(s, a[i-1], a[i]);
    		ret += s.length() - a[i] - cp;
    	}
    	return ret;
    }
```

접미사 배열을 순회하면서, 각 배열의 원소 이전의 원소에 포함된 접두사는 제외한 나머지 접미사만 세는 방법
