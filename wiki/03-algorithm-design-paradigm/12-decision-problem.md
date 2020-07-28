# 12. Decision Problem

2020.07.29.Wed

Byeongsu Kim



오늘 다룰 주제는 딱히 이름이 붙어 있지 않은 알고리즘이라고 한다. 그러나 굉장히 유용한 디자인 원칙 중 하나로, 최적화 문제를 결정 문제(decision problem)으로 변환하여 이분법을 이용해 해결하는 방법에 대해 알아 볼 것이다. 여기서 언급하는 이분법이란 이진 탐색과 비슷하게 수치 해석 문제를 해결하는 기법을 말한다. 또한 결정 문제란 예 혹은 아니오 형태의 답만이 나오는 문제를 가리킨다. 즉 analog처럼 무한에 이르는 경우의 수를 고려해야 하는 최적화 문제를 digital signal 처럼 두 가지 답만이 있을 수 있는 decision problem으로 변환하여 쉽게 해결해 보고자 함이다.



외판원 문제에 대해 최적화(optimize) 문제와 결정(decision) 문제로 표현하면 아래와 같다.

optimize(G) = 그래프 G의 모든 정점을 한 번씩 방문하고 시작점으로 돌아오는 최단 경로의 길이를 반환한다.

decision(G, x) = 그래프 G의 모든 정점을 한 번씩 방문하고 시작점으로 돌아오면서 길이가 x이하인 경로의 존재 여부를 반환한다.



### Relation of Optimize problem and Decision problem

결정 문제는 최적화 문제보다 어려울 수 없다. 최적화 문제를 푸는 optimize()가 있으면 아래와 같이 결정문제를 해결할 수 있기 때문이다.

```c++
double optimize(const Graph& g);
bool decision(const Graph% g, double x) {
    return optimize(g) <= x;
}
```

따라서 decision()이 optimize()보다 시간 복잡도가 클 일은 없다. 물론 결정 문제와 최적화 문제가 비슷하게 어려운 문제도 많기 때문에 모든 문제를 결정 문제로 변경하여 해결할 필요는 없다. 최적화 문제보다 결정 문제가 풀기 쉬운 문제에 대해 적절히 변환하여 적용하면 좋다.



### Examples

- [DARPA Grand Challenge(ID: DARPA, 난이도: 중)](https://www.algospot.com/judge/problem/read/DARPA)
- 240km 도로에서 진행되는 경기를 n개의 카메라를 통해 중계하고자 한다. 카메라를 설치할 수 있는 위치는 m군데로 제한되며 이 중 n군데에 카메라를 설치하여 가장 가까운 두 카메라 사이의 간격을 최대 간격을 알고자 한다.
  - optimize(locations, cameras) = 카메라를 설치할 수 있는 위치 locations와 카메라의 수 cameras가 주어질 때, 카메라 간 최소 간격의 최대치를 return;
- decision(locations, cameras, gap) = 카메라를 설치할 수 있는 위치 locations와 카메라의 수 cameras가 주어질 때, 이들을 적절히 배치해 모든 카메라의 간격이 gap 이상이 되도록 하는 방법이 있는가?
  - 이 문제에서는 decision과 이분법을 활용하면 훨씬 쉽게 해결할 수 있다.
- gap이상의 위치가 되면 camera를 설치한다고 가정하여 설치된 camera의 개수가 n개 이상이라면 true, 적다면 false로 하여 [0,240]의 범위에서 gap에 대해 이분법 하면 O(n)안에 문제를 해결할 수 있다.
  



이분법을 사용한다는 부분에서 함정이 존재한다. 컴퓨터이기 때문에 생기는 연산 중 수치적 오차(ex. floating number calculation)에 대해 범위가 커질 수 있다.



### Recipe of Transport from Optimize problem to Decision problem

위의 예제들로 살펴본 것을 바탕으로 틀을 제공하면 아래와 같다.

1. "가장 좋은 답은 무엇인가?"라는 최적화 문제를 "x 혹은 그보다 좋은 답이 있는가?"라는 결정 문제 형태로 바꾼다.
2. 결정 문제를 쉽게 풀 수 있는 방법이 있는지 찾아본다.
3. 결정 문제를 내부적으로 이용하는 이분법 알고리즘을 작성한다.



### Problems

- [남극기지(ID: ARCTIC, 난이도: 하)](https://www.algospot.com/judge/problem/read/ARCTIC)

- [캐나다 여행(ID: CANADATRIP, 난이도: 중)](https://www.algospot.com/judge/problem/read/CANADATRIP)
- [수강 철회(ID: WITHDRAWAL, 난이도: 상)](https://www.algospot.com/judge/problem/read/WITHDRAWAL)
