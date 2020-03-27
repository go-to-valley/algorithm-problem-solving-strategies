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