# 19 스택, 큐, 데크

- 스택 - 한쪽 끝에서만 자료를 넣고, 뺄 수 있다.(LIFO)
- 큐 - 한쪽 끝에서 자료를 넣고 반대편 끝에서만 뺄 수 있다. (FIFO)
- 데크 - 양방향 끝에서 자료를 넣고 뺄 수 있다.

이들 셋 자료구조는 데이터를 넣는 `push`연산과, 빼는 `pop`연산을 모두 상수시간에 처리 가능해야한다.

## 구현

### 연결리스트

연결리스트로 구현은 쉬우나, 노드의 할당, 삭제, 포인터를 따라가는데 드는 시간으로 인해 가장 효율적인 구현이 아니다.

### 동적 배열

동적 배열로 스택은 단순하게 구현하면 되나 큐, 데크의 구현이 복잡한데, 맨 앞에서 데이터를 꺼낸다고 생각하면 O(n)의 시간이 걸리기 때문이다. 따라서 이 경우에는 head나 tail이 가리키는 인덱스를 옮기는 방식으로 처리한다. 또 이 경우에는 버려지는 공간이 생긴다는 단점이 있기에, 버려지는 공간을 재활용하기 위해 데이터를 삽입할 때, 배열이 꽉 찬게 아니라면 재할당을 하지 않고 버려지는 공간에 먼저 삽입한 후 더 이상 원소를 삽입할 곳이 없을 경우에만 재할당 하는 방법이 있다.

## 표준라이브러리

이들 셋 자료구조는 모두 대부분의 언어의 표준 라이브러리에 구현되어있다.

## 짝이 맞지 않는 괄호

```java
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Brackets2 {
	public static void main(String[] args) {
		int totalTestNumber;
		Scanner scanner = new Scanner(System.in);
		totalTestNumber = scanner.nextInt();
		scanner.nextLine();

		for (int testNum = 0; testNum < totalTestNumber; testNum++) {
			String nextLine = scanner.nextLine();
			boolean result = true;
			Stack<Character> openBracketStack = new Stack<>();
			char[] brackets = nextLine.toCharArray();
			for (char bracket : brackets) {
				if (bracket == '(' || bracket == '{' || bracket == '[') {
					openBracketStack.push(bracket);
				} else {
					if (openBracketStack.empty()) {
						result = false;
						break;
					}
					char openBracket = openBracketStack.pop();
					if (!((bracket == ')' && openBracket == '(') || (bracket == '}' && openBracket == '{')
							|| (bracket == ']' && openBracket == ']'))) {
						result = false;
						break;
					}
				}
			}
			if (!openBracketStack.empty()) {
				result = false;
			}
			System.out.println(result ? "YES" : "NO");
		}
		scanner.close();
	}
}
```
