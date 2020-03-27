# 18 선형자료구조

## 동적배열

동적배열은 크기를 변경할 수 있는 배열로 기존 배열의 특성 다음 두가지를 그대로 지원한다.

- 원소들은 메모리의 연속된 위치에 저장
- 원소 조회, 수정의 연산에 소요되는 시간이 O(1)

여기에 동적배열은 추가적으로 `resize()` 연산, O(1)의 수행시간을 가지는 `append()`연산을 지원한다. `resize()`연산은 새로 메모릴를 할당한 후 기존 내용을 복사하기에 O(N)의 수행시간이 걸린다. 일반적으로 배열은 크기가 고정돼있기에 꽉 찬 배열에 `append()` 를 할 경우, `resize()` 가 필요하기에 `append()` 연산이 O(1)의 수행시간을 보장하기 위해서는 특별한 전략이 필요하다.

### 동적 배열의 재할당 전략

재할당을 할 때 M만큼의 용량을 추가로 할당할 때, M번 `append` 를 실행하면 재할당이 한번 실행된다. 텅빈 배열로 시작할 때 N번 `append()` 를 실행하면 재할당의 수 K = O(N/M)으로, N이 아주 커진다면 K=O(N) 이라 할 수 있다. 재할당시마다 복사하는 배열 원소의 수는 M, 2M, 3M ... KM개로 증가하므로 전체 복사하는 원소의 수는 (K+1)KM/2 = O(K^2) = O(N^2)이고, N번의 연산의 경우이므로 1번의 `append()` 에 드는 수행시간은 O(N)이 된다.

따라서 재할당 시에는 고정된 용량을 추가로 할당하는 것 보다 현재 용량의 크기에 비례햐여 할당하는 것이 낫다. 재할당 시마다 2배의 용량으로 늘리는 전략을 취했을 때는, K번 재할당 시점에 복사하는 원소의 수는 2^(K-1)으로써, K번 재할당을 할 경우 총 2^K이고, 이것이 결과적으로 O(N)이기에, 한번의 `append()`에 드는 시간은 O(1)이라고 할 수 있다.

### 표준 라이브러리

동적 배열은 c++의 `vector`나, Java의 `ArrayList` 등으로 이미 구현이 돼있다.

## 연결 리스트

연결리스트는 배열과 특징이 다른데 다음과 같다.

- 원소들이 메모리의 불연속적 위치에 저장
- 원소 조회, 수정에 소요되는 시간이 O(N)
- 리스트 끝이나 처음에 원소를 추가하거나 삭제하는데 드는 시간이 상수

### 표준라이브러리

연결리스트는 c++ 의 `list` Java의 `LinkedList` 등으로 이미 구현돼있다.

### 응용

#### 잘라 붙이기

원소의 포인터를 조작해 다른 리스트를 통째로 삽입하는 연산을 상수시간만에 가능하다. 그러나 추가되는 원소의 개수를 모르기에 리스트의 크기를 구하기 위해 선형시간이 필요하고, 이 때문에 Java에서는 잘라 붙이기 연산을 지원하지 않고 리스트의 크기를 구하는 연산을 상수시간으로 지원하고, 반면에 C++에서는 잘라 붙이기를 지원하는 대신 리스트의 크기를 구하기 위해 선형시간의 반복이 필요하다.

#### 삭제했던 원소 돌려놓기

원소를 삭제할 때, 삭제할 원소의 앞뒤 원소의 포인터만 조작하고 삭제한 원소의 포인터는 조작하지 않는 점을 이용해 필요할 때 원소를 원래대로 돌려놓을 수 있다.

## 동적 배열과 연결 리스트 비교

|                        | 동적배열 | 연결리스트 |
| ---------------------- | -------- | ---------- |
| 이전/다음 원소 조회    | 1        | 1          |
| 맨 뒤에 원소 추가/삭제 | 1        | 1          |
| 랜덤 위치에 추가/삭제  | N        | 1          |
| 랜덤 위치 원소 조회    | 1        | N          |
| 크기                   | 1        | N or 1     |

## 조세푸스 문제

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Josephus {

    public static class TestCase {
        public final int n;
        public final int k;

        public TestCase(int n, int k) {
            this.n = n;
            this.k = k;
        }
    }

    public static void main(String[] args) {
        int totalTestNumber;
        List<TestCase> testCaseList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        totalTestNumber = scanner.nextInt();

        for (int testNum = 0; testNum < totalTestNumber; testNum++) {
            int n, k;
            n = scanner.nextInt();
            k = scanner.nextInt();
            testCaseList.add(new TestCase(n, k));
        }

        testCaseList.forEach(testCase -> {
            int n = testCase.n;
            int k = testCase.k;

            int target = 0;

            LinkedList<Integer> survivors = new LinkedList<Integer>();
            for (int i = 0; i < n; i++) {
                survivors.add(i + 1);
            }

            while (survivors.size() > 2) {
                survivors.remove(target);
                for (int i = 0; i < k - 1; k++) {
                    target++;
                    if (target == survivors.size()) {
                        target = 0;
                    }
                }
            }

            survivors.stream().forEach(survivor -> {
                System.out.print(Integer.toString(survivor) + " ");
            });

            System.out.print('\n');
        });
        scanner.close();
    }
}
```
