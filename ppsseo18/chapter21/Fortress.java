import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Fortress {
	public static class TreeNode {
		int height;
		int x;
		int y;
		int radius;
		TreeNode parent = null;
		List<TreeNode> childrenNodeList;

		public TreeNode(int x, int y, int radius) {
			this.height = 0;
			this.x = x;
			this.y = y;
			this.radius = radius;
			this.childrenNodeList = new ArrayList<>();
		}

		private boolean isContain(TreeNode node) {
			double d = Math.sqrt(Math.pow(this.x - node.x, 2) + Math.pow(this.y - node.y, 2));
			return d < this.radius - node.radius;
		}

		public void add(TreeNode node) {
			if (this.isContain(node)) {
				for (TreeNode child : childrenNodeList) {
					int childNodeHeight = child.height;
					child.add(node);
					if (node.parent != null) { // case that node is inserted into tree
						if (childNodeHeight != child.height && child.height == this.height) {
							this.height++;
						}
						break;
					}
				}
				if (node.parent == null) {
					if (this.childrenNodeList.isEmpty()) {
						this.height++;
					}
					node.parent = this;
					this.childrenNodeList.add(node);
				}
			} else if (node.isContain(this)) {
				node.add(this);
			}
		}
	}

	public static class TestCase {
		TreeNode root;

		public TestCase(TreeNode root) {
			this.root = root;
		}

		public void solve() {
			System.out.println(cal(root));
		}

		public int cal(TreeNode node) {
			if (node.childrenNodeList.isEmpty()) {
				return 0;
			} else if (node.childrenNodeList.size() == 1) {
				return node.height;
			} else {
				int max = 0;
				int first = node.height - 1;
				int second = 0;
				for (TreeNode child : node.childrenNodeList) {
					if (child.height < first && child.height >= second) {
						second = child.height;
					}
					max = Math.max(cal(child), second + first + 2);
				}
				return max;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int numberOfTestCase;
		List<TestCase> testCaseList = new ArrayList<>();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		numberOfTestCase = Integer.parseInt(stdin.readLine());

		for (int caseCount = 0; caseCount < numberOfTestCase; caseCount++) {
			int totalNodeNumber = Integer.parseInt(stdin.readLine());
			TreeNode root = null;
			for (int i = 0; i < totalNodeNumber; i++) {
				String[] tokens = stdin.readLine().split("\\s");
				TreeNode node = new TreeNode(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
						Integer.parseInt(tokens[2]));
				if (root == null) {
					root = node;
				} else {
					root.add(node);
				}
			}
			testCaseList.add(new TestCase(root));
		}

		testCaseList.forEach(TestCase::solve);
	}
}
