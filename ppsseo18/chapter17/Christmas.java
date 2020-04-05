import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Christmas {
	public static class TestCase {
		int numberOfGitfBoxes;
		int numberOfChildren;
		List<Integer> partialSumListOfGiftBoxes;

		Map<Integer, List<Integer>> possibleIndexPairMap = new HashMap<Integer, List<Integer>>();

		int answerOfFirstQuestion;
		int answerOfSecondQuestion;

		public TestCase(String[] firstLineTokens, String[] secondLineTokens) {
			this.numberOfGitfBoxes = Integer.valueOf(firstLineTokens[0]);
			this.numberOfChildren = Integer.valueOf(firstLineTokens[1]);

			// calculate and save partial sums
			this.partialSumListOfGiftBoxes = new ArrayList<Integer>(this.numberOfGitfBoxes);
			for (int i = 0; i < secondLineTokens.length; i++) {
				int parsedToken = Integer.valueOf(secondLineTokens[i]);
				this.partialSumListOfGiftBoxes.add(i == 0 ? parsedToken : parsedToken + partialSumListOfGiftBoxes.get(i - 1));
			}

			answerOfFirstQuestion = 0;
			answerOfSecondQuestion = 0;
		}

		public void resolveFirstQuestion() {
			for (int i = 0; i < numberOfGitfBoxes; i++) {
				for (int j = i; j < numberOfGitfBoxes; j++) {
					int partialSum = i == 0 ? partialSumListOfGiftBoxes.get(j)
							: partialSumListOfGiftBoxes.get(j) - partialSumListOfGiftBoxes.get(i - 1);
					boolean isSatisfied = testDoesPartialSumSatisfyCondition(partialSum);
					if (isSatisfied) {
						if (possibleIndexPairMap.get(i) == null) {
							possibleIndexPairMap.put(i, new ArrayList<Integer>());
						}
						possibleIndexPairMap.get(i).add(j);
						answerOfFirstQuestion++;
					}
				}
			}
		}

		public void resolveSecondQuestion() {
			for (int startIndex : possibleIndexPairMap.keySet()) {
				DFS(startIndex, 1);
			}
		}

		private void DFS(int startIndex, int depth) {
			List<Integer> endIndexList = possibleIndexPairMap.get(startIndex);
			for (int endIndex : endIndexList) {
				for (int nextStartIndex : possibleIndexPairMap.keySet()) {
					if (nextStartIndex > endIndex) {
						DFS(nextStartIndex, depth + 1);
					}
				}
			}
			if (depth > answerOfSecondQuestion) {
				answerOfSecondQuestion = depth;
			}
		}

		private boolean testDoesPartialSumSatisfyCondition(int partialSum) {
			return partialSum % numberOfChildren == 0;
		}
	}

	public static void main(String[] args) throws Exception {
		int numberOfTestCase;
		List<TestCase> testCaseList = new ArrayList<TestCase>();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		numberOfTestCase = Integer.valueOf(stdin.readLine());
		for (int t = 0; t < numberOfTestCase; t++) {

			String[] firstLineTokens = stdin.readLine().split("\\s");
			String[] secondLineTokens = stdin.readLine().split("\\s");
			testCaseList.add(new TestCase(firstLineTokens, secondLineTokens));
		}

		for (TestCase testCase : testCaseList) {

			testCase.resolveFirstQuestion();
			testCase.resolveSecondQuestion();

			System.out.print(Integer.toString(testCase.answerOfFirstQuestion) + " "
					+ Integer.toString(testCase.answerOfSecondQuestion) + "\n");
		}
	}

}