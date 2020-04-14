import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Traversal {
	public static class TestCase {
		int lengthOfTree;
		List<Integer> preOrderList;
		List<Integer> inOrderList;

		public TestCase(int lengthOfTree) {
			this.lengthOfTree = lengthOfTree;
		}

		public void setPreOrderList(String[] preOrderArray) {
			preOrderList = Arrays.asList(preOrderArray).stream().map(Integer::valueOf).collect(Collectors.toList());
		}

		public void setInOrderList(String[] inOrderArray) {
			inOrderList = Arrays.asList(inOrderArray).stream().map(Integer::valueOf).collect(Collectors.toList());
		}

		public void solve() {
			this.printPostOrder(0, lengthOfTree - 1);
			System.out.print("\n");
		}

		public int findRoot() {
			return this.preOrderList.remove(0);
		}

		public void printPostOrder(int start, int end) {
			if (start > end) {
				return;
			}
			int root = this.findRoot();
			int rootIndex = this.inOrderList.indexOf(root);

			printPostOrder(start, rootIndex - 1);
			printPostOrder(rootIndex + 1, end);
			System.out.print(root + " ");
		}
	}

	public static void main(String[] args) throws IOException {
		int numberOfTestCase;
		List<TestCase> testCaseList = new ArrayList<>();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		numberOfTestCase = Integer.valueOf(stdin.readLine());

		for (int caseCount = 0; caseCount < numberOfTestCase; caseCount++) {
			TestCase testCase = new TestCase(Integer.valueOf(stdin.readLine()));
			testCase.setPreOrderList(stdin.readLine().split("\\s"));
			testCase.setInOrderList(stdin.readLine().split("\\s"));

			testCaseList.add(testCase);
		}

		testCaseList.forEach(TestCase::solve);

	}
}