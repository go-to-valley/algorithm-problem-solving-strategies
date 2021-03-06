# 4. Time Complexity Analysis

2020.06.27 Wed 

Byeongsu Kim



알고리즘에 대해 평가하고, 판단하기 위해 가장 중요하게 판단되는 기준 중 하나는 그 알고리즘을 실행하는 데 들어가는 시간, 즉 속도이다. 두 알고리즘의 속도를 비교하는 가장 단순한 방법은 구 구현된 알고리즘에 대한 수행 시간을 측정하여 비교하는 것이다. 그러나 이는 컴퓨터의 성능, 운영체제, 컴파일러, 프로그래밍 언어, 기온 등 다양한 요소에 영향을 많이 받기 때문에 적절한 기준이라고 보기 어렵다. 따라서 시간 복잡도를 분석하여 Big - O notation을 활용하는 방법을 주로 사용한다. 물론 이 방법 또한 모든 부분을 알려주지는 못하기 때문에 참고용으로 고려하면 매우 유용한 방법이다. 

Big-O notation에사용되는 가장 중요한 아이디어는 고려해야 할 범위가 매우 커지는 상황에서 어느정도로 속도가 느려지는지를 알아본다는 것이다. 프로그램이 처리하는 내용은 수십억개가 될 수도 있기 때문에 상상하는 이상의 고려 범위에 대해 시간 복잡도를 분석하는 것이다. 여기서 알 수 있듯 반복문에 대해 고려함으로써 간단하게 시간복잡도를 예측할 수 있다. 이에 따라 몇가지로 알고리즘들을 분류해 볼 수 있다. 

### Linear time algorithm

선형 시간복잡도를 가진 알고리즘을 통칭하는 내용으로 평균값을 구하는 문제가 대표적이다. 아래 표현법으로 나타내어지며, 범위가 커짐에 따라 선형으로 시간이 오래걸리는 알고리즘들이다. 실제 대회에서는 값을 입력받는데만 선형 시간이 걸리므로 보통의 경우 최적의 알고리즘인 경우가 많다.
$$
O(n)
$$


### Under Linear time algorithm

그러나 세상에는 다양한 알고리즘이 존재하므로 선형 시간보다 시간이 적게 걸리는 알고리즘들도 존재한다. 입력을 받지 않거나, 상수개의 입력에 대한 문제의 경우 시간복잡도는 더 낮아질 수 있다. 대표적으로 이분탐색과 같은 알고리즘이 이에 해당한다. 이는 로그 연산을 통한 시간복잡도를 갖게 되는데, 이 외에도 아래와 같은 표현법으로 나타내어지는 알고리즘들이 이에 속한다.
$$
O(log n), O(1)
$$


### Polynomial time algorithm

다항 시간 알고리즘의 경우 n에 대한 거듭제곱의 선형 결합, 즉 합으로 이루져 있는 시간복잡도를 갖는 알고리즘을 의미한다. 위에서 언급한 두 경우 모두 이 범위에 속한다. 그러나 이들보다 훨씬 느리게 동작하는 알고리즘들도 존재한다. 더 느리게 동작하는 알고리즘들은 아래에서 살펴보자. 다항 시간 알고리즘도 충분히 느리게 동작하는 경우가 많으므로 보통 알고리즘 대회에서는 O(n^3)까지 취급한다고 생각하면 편하다. 아래의 표현법으로 나타내어지는 알고리즘들이 이에 속한다.
$$
O(1), O(log n), O(n), O(n^2), ... , O(n^100), ...
$$


### Exponential time algorithm

지수시간 알고리즘의 경우 어떠한 set의 모든 subset구하기가 대표적인데, 아래와 같이 표현되는 알고리즘들이 이에 속한다. 이 알고리즘은 다항 시간 알고리즘보다 훨씬 느리게 동작하므로 느리다는 것을 인지하고 프로그램을 최적화 시켜야 한다. 물론 이 방법으로 푸는 문제들은 최적화 시켜도 느리다.
$$
O(2^n), O(M^n)
$$

### Time complexity analysis

책의 예제를 보면서 시간 복잡도 분석을 연습해보면 좋다. 이외에도 다양한 구현체에 대해 시간복잡도를 분석하면서 연습하면 도움이 많이 된다. 이후에는 문제를 보고 구현법을 떠올리며 시간복잡도를 계산하다 보면 어느 순간 문제에 대한 시간복잡도를 예측하고, 알고리즘을 구현하는 본인을 발견할지도 모르겠다.

- 시간 복잡도의 분할 상환 분석

  분할 상환이라는 단어가 매우 어색하게 느껴질 수 있지만, 간단하게 생각하면 전체를 실행하는데 걸리는 시간을 분석함으로써 분할된 알고리즘의 실행속도를 각각 계산하지 않고 바로 손쉽게 전체 알고리즘의 시간 복잡도를 분석하는 방법이다.

  BFS알고리즘의 시간복잡도를 분석해 보자. 각각의 상황에서 edge가 몇 개인지에 따라 각 vertex에서의 시간 복잡도는 달라질 것이다. 따라서 각 상황에 대한 시간 복잡도를 분석하게 되면 O(EV)가 나오게 된다. 그러나 모든 vertex가 모든 edge를 갖는 경우는 존재하지 않기 때문에 분할 상환 분석을 사용하면 좀 더 정확한 예측이 가능하다. 물론 big-O notation의 정의에 따라 위에서 분석한 시간복잡도도 타당하다. 그러나 조금 더 정확히 연산해 보면 각 vertex와 edge를 한 번씩 탐색하므로 O(E+V)라는 시간복잡도를 얻을 수 있다.

- 수행시간 짐작하기

  수행 시간 짐작이라는 단어에서 느껴지듯 정확한 수행시간을 구하는 것이 아니라 입력값의 범위가 주어질 때 대략적인 수행 시간을 짐작함으로써 알고리즘이 시간 내에 수행될 수 있는지 고려해 보는 것이다. 

  프로그래밍 언어, 프로그래밍 기법, 컴퓨터 사양 등 다양한 변수가 있지만 c++, 최적화 complier를 사용하는 경우를 가정했을 때 보통 10^8의 연산을 하는데 1초 정도 소요된다고 고려하면 거의 비슷하다. 구현법이나 사용하는 라이브러리에 따라 시간복잡도의 10-100배 차이는 쉽게 일어나므로 충분히 작은지로 판단해야 한다. 다행히도 10^8또한 1초가 채 안걸리는 연산량이기 때문에 적절히 변환하여 계산하면 좋다. 여기서 말하는 연산량은 +, -, *, /, = 등을 포함하여 아주 단순한 연산들을 의미하는 것이므로 함수 호출을 한번으로 세면 곤란하다. 즉, 반복문 내부가 복잡하거나, 메모리 접근이 많은 등 오래걸리는 연산의 경우 더 오래걸린다는 것을 알고 있어야 한다. 알고리즘 문제에서 주로 나타나는 시간복잡도에 대한 n의 범위는 아래와 같다.

  | 시간복잡도       | n 범위      |
  | ---------------- | ----------- |
  | $$ O(n^3) $$     | 2,560       |
  | $$ O(n^2) $$     | 40,960      |
  | $$ O(n log n) $$ | 20,000,000  |
  | $$ O(n) $$       | 160,000,000 |



### Calculation Complexity Class: P, NP, NP-complete

여기서 부터는 크게 알고리즘 문제를 푸는데 중요한 내용은 아니다. 

- P Problem : polynomial time complexity algorithm

  답을 구하는데 polynomial time complexity를 갖는 문제

- NP Problem : non-polynomial time complexity algorithm

  답이 주어졌을 때 정답인지 확인하는데 polynomial time complexity를 갖는 문제

- NP - hard Problem : hard problem

  SAT(Satisfiability problem)보다 어려운 문제

- NP - complete  Problem : NP problem && NP-hard problem

현재 NP problem을 정의할 때 어떤 문제를 잘 알려진 NP problem문제로 변환하여 동일하므로 NP problem이라고 정의하고, NP-hard problem을 정의할 때 잘 알려진 SAT문제로 변환하여 더 어려움을 증명한다. 따라서 NP problem중 하나라도 P problem이거나 아님이 증명되면 이 problem을 구분하는 set은 의미가 없어진다. 그러나 아직 아무도 모른다.



