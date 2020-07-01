# 08-1. Dynamic Programming

2020.07.01.

Seungrok Lee



DP(Dynamic Programming)은 큰 의미에서는 분할 정복과 같은 접근 방식을 의미한다. 다만 문제를 나누는 방식에서 차이가 있는데, DP에서는 어떤 부분 문제는 두 개 이상의 문제를 푸는 데 사용될 수 있기 때문에, 문제의 답을 한번만 계산하고 계산 결과를 재활용함으로써 속도의 향상을 노린다. 이 때 계산해 둔 값을 저장하는 메모리 장소는 Cache라고 부르며, 두 번 이상 계산되는 부분 문제를 Overlapping subproblems라고 부른다.



### Memoization

Memoization이란 함수의 결과를 저장하는 장소를 마련해 두고, 한 번 계산한 값을 저장해 뒀다 재활용하는 최적화 기법을 말한다. Memoization이 적용될 수 있는 곳은 참조적 투명 함수의 경우에만 가능한데, 참조적 투명성이란 함수의 반환값이 입력값에 따라서만 결정되는지를 의미한다. Memoization의 구현 방법은 다음과 같다.
* Cache를 초기화한다.
* 기저 사례를 제일 먼저 처리한다.
* 부분 값을 구한 적이 있으면 곧장 반환한다.
* 부분 값들을 통하여 구하고자 하는 값을 구하여 반환한다.

Memoization의 시간 복잡도는 일반적으로는 다음식을 이용해 구한다.
* (존재하는 부분 문제의 수)X(한 부분 문제를 풀 때 필요한 반복문의 수행 횟수)



### Problems

- [와일드카드(ID: WILDCARD, 난이도: 중)](https://www.algospot.com/judge/problem/read/WILDCARD)



### 최적화 문제

DP의 가장 일반적인 사용처는 최적화 문제의 해결이다. 최적화 문제를 풀 경우 특정 성질이 성립할 경우에는 단순히 memoization을 적용하기보다 좀 더 효율적으로 DP를 구현할 수 있다. 문제가 최적 부분 구조를 가질 경우 보다 효율적으로 DP를 구현할 수 있는데, 최적 부분 구조는 각 부분 문제를 최적으로 풀면 전체 문제의 최적해도 알 수 있는 구조를 의미한다. 최적화 문제를 동적 계획법으로 설계할 경우 일반적으로 다음과 같은 과정으로 푼다.
* 모든 답을 만들어 보고 최적해의 점수를 반환하는 완전 탐색 알고리즘을 설계한다.
* 전체 답의 점수를 반환하는 것이 아니라, 앞으로 남은 선택들에 해당하는 점수만을 반환하도록 부분 문제 정의를 바꾼다.
* 재귀 호출 입력에 이전의 선택에 관련된 정보가 있다면 꼭 필요한 것만 남기고 줄인다. 입력의 종류가 줄어들면 줄어들 수록 많은 부분 문제가 중복되고, memoization을 최대로 활용할 수 있다.
* 입력이 배열이거나 문자열인 경우 변환을 통해 memoization을 할 수 있게 한다.
* memoization을 적용한다.



### Problems

- [합친 LIS(ID: JLIS, 난이도: 하)](https://www.algospot.com/judge/problem/read/JLIS)
- [원주율 외우기(ID: PI, 난이도: 하)](https://www.algospot.com/judge/problem/read/PI)
- [Quantization(ID: QUANTIZE, 난이도: 중)](https://www.algospot.com/judge/problem/read/QUANTIZE)
