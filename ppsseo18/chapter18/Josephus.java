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