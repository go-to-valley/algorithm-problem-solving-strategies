import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static SortedSet<Integer> prevSide;
    static SortedSet<Integer> postSide;
    static long value;
    static int rst;
    static int mod = 20090711;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int c = Integer.parseInt(br.readLine());

        for(int t = 0; t < c; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            rst = 0;
            prevSide = new TreeSet<>();
            postSide = new TreeSet<>();
            value = 1983;
            prevSide.add(Math.toIntExact(value));
            rst += value;
            for(int i = 1; i < n; i++){
                value = ((value*a)%mod+b)%mod;

                if(prevSide.size() > postSide.size())
                    postSide.add(Math.toIntExact(value));
                else
                    prevSide.add(Math.toIntExact(value));

                int minMax = prevSide.last();
                int maxMin = postSide.first();
                if(minMax > maxMin){
                    postSide.remove(maxMin);
                    prevSide.remove(minMax);
                    postSide.add(minMax);
                    prevSide.add(maxMin);
                }
                rst = (rst+prevSide.last())%mod;
            }
            System.out.println(rst);
        }
    }
}
