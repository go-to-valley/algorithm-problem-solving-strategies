import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt();
        for(int t = 0; t < c; t++){
            int n, k;
            k = scan.nextInt();
            n = scan.nextInt();
            Queue<Long> queue = new LinkedList<>();
            long sum = 0;
            int cnt = 0;
            long a = 1983;
            for(int i = 0; i < n; i++){
                long arr = (a%10000) + 1;
                a = (a*214013 + 2531011)%((long)Math.pow(2, 32));

                sum += arr;
                queue.offer(arr);
                while(sum > k && !queue.isEmpty()){
                    sum -= queue.poll();
                }
                if(sum == k) cnt++;
            }
            System.out.println(cnt);
        }
    }
}
