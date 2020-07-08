# 08-2. Dynamic Programming

2020.07.08.Wed

Byeongsu Kim



DP(Dynamic Programming)는 애초에 최적화 문제를 풀기 위해 고안되었지만, 경우의 수를 세거나 확률을 계산하는 문제에도 흔히 사용된다. 확통에 사용되는 많은 문제들은 재귀적인 특징이 있기 때문이다.

여기서 주의해야 할 부분은 우리가 작성한 프로그램을 실행하는 대상이 binary밖에 모르는 컴퓨터라는 점이다. 재귀적인 연산을 계속해서 수행하다 보면 생각보다 어마어마하게 작거나 큰 수가 나오는 경우가 많다. 따라서 overflow가 나지 않도록 계산될 값을 예측하여 적절한 data type을 선택하는 것이 필수이다.



전체적으로 문제를 해결하는 흐름은 이전 DP에서와 동일하게 완전 탐색에서 부터 시작한다. 이후 전체 상태에서 의미 없는 상태를 제거하고, 불필요한 변수를 제거함으로써 추상화와 정규화 과정을 거친다. 마지막으로 점화식을 세워 문제를 해결할 수 있는 간단한 식을 얻어낸다. 이 과정이 DP의 전체이자 의미라고 볼 수 있다. 필요하다면 메모이제이션까지 사용하여 시간 복잡도를 줄여나가는 것이 좋다.



### Examples

- [타일링 방법의 수 세기(ID: TILING2, 난이도: 하)](https://www.algospot.com/judge/problem/read/TILING2)

  - 2Xn크기의 사각형을 2X1크기의 타일로 채우는 방법의 수를 계산하는 문제이다.

  - 아주 유명한 DP 문제이기 때문에 잘 알아두는 것이 좋다.

  - 부분문제를 정의하고, 그 부분문제를 연결하는 점화식을 만들면 아래와 같다.

  - $$
    tiling(n) = tiling(n-1)+tiling(n-2)
    $$

  - 위 식을 bottom up으로 해결하면 된다.

- [삼각형 위의 최대 경로 개수 세기(ID: TRIPATHCNT, 난이도: 중)](https://www.algospot.com/judge/problem/read/TRIPATHCNT)

  - 지난주에 다뤘던 내용에서 경로의 개수를 세는 것이 추가된 문제이다.

  - count(y, x) = (y, x)에서 시작해 맨 아래줄 까지 내려가는 최대 경로수로 놓으면 점화식은 아래와 같다.

  - $$
    count(y, x) = max(
    $$

    $$
    count(y+1, x)(path2(y+1, x) > path2(y+1, x+1)),
    $$

    $$
    count(y+1, x+1)(path2(y+1, x) < path2(y+1, x+1)),
    $$

    $$
    count(y+1, x)+count(y+1, x+1) (path2(y+1, x) == path2(y+1, x+1)))
    $$

- 우물을 기어오르는 달팽이

  - 앞으로 m일간 각 날짜에 비가 올 확률이 50%일 때 m일 안에 n미터를 올라갈 수 있는 확률을 계산하는 문제이다.
  - 완전 탐색 알고리즘으로 시작한다.
  - 이 문제는 여기서 해결된다.

- [장마가 찾아왔다(ID: SNAIL, 난이도: 하)](https://www.algospot.com/judge/problem/read/SNAIL)

  - 비가 올 확률이 달라질 때 위의 우물을 기어오르는 달팽이 문제를 푸는 문제이다.

  - 부분 문제와 점화식은 아래와 같다.

  - $$
    chilmb2(days, climbed) = 달팽이가 지금까지 days일 동안 climbed 미터를 기어올라왔을 때 m일 전까지 n미터 이상 기어올라갈 수 있을 확률
    $$

    $$
    climb2(days, climbed) = 0.75*climb2(days+1, climed+1) + 0.25*climb2(days+1, climed+2)
    $$



### 경우의 수 계산하기 레시피

위의 예제들로 살펴본 것을 바탕으로 틀을 제공하면 아래와 같다.

1. 모든 답을 직접 만들어서 세어 보는 완전 탐색 알고리즘을 설게한다. 이 때 경우의 수를 제대로 세기 위해 재귀 호출의 각 단계에서 고르는 각 선택지에 다음과 같은 속성이 성립해야 한다.
   1. 모든 경우는 이 선택지들에 포함된다.
   2. 어떤 경우도 두 개 이상의 선택지에 포함되지 않는다.
2. 최적화 문제를 해결할 때처럼 이전 조각에서 결정한 요소들에 대한 입력을 없애거나 변형해서 줄인다. 재귀 함수는 앞으로 남아있는 조각들을 고르는 경우의 수만을 반환해야 한다.
3. 메모이제이션을 적용한다.



### Problems

- [비대칭 타일링(ID: ASYMTILING, 난이도: 하)](https://www.algospot.com/judge/problem/read/ASYMTILING)

- [폴리오미노(ID: POLY, 난이도: 중)](https://www.algospot.com/judge/problem/read/POLY)
- [두니발 박사의 탈옥(ID: NUMB3RS, 난이도: 중)](https://www.algospot.com/judge/problem/read/NUMB3RS)
