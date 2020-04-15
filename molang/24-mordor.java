import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static class IntPair{
        public int first, second;

        IntPair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public static class RMQ{
        int n;
        int[] rangeMin;
        int[] rangeMax;
        RMQ(int[] notices){
            this.n = notices.length;
            rangeMin = new int[n*4];
            rangeMax = new int[n*4];
            init(notices, 1, 0, n-1);
        }

        IntPair init(int[] notices, int at, int left, int right){
            if(left == right){
                rangeMin[at] = notices[left];
                rangeMax[at] = notices[left];
                return new IntPair(rangeMin[at], rangeMax[at]);
            }
            int mid = (left+right)/2;
            IntPair leftValues = init(notices, at*2, left, mid);
            IntPair rightValues = init(notices, at*2+1, mid+1, right);
            rangeMin[at] = Math.min(leftValues.first, rightValues.first);
            rangeMax[at] = Math.max(leftValues.second, rightValues.second);
            return new IntPair(rangeMin[at], rangeMax[at]);
        }

        public IntPair query(int from, int to, int at, int left, int right){
            if(to < left || right < from) return new IntPair(Integer.MAX_VALUE, Integer.MIN_VALUE);
            if(from <= left && right <= to) return new IntPair(rangeMin[at], rangeMax[at]);
            int mid = (left+right)/2;
            IntPair leftValues = query(from, to, at*2, left, mid);
            IntPair rightValues = query(from, to, at*2+1, mid+1, right);
            int min = Math.min(leftValues.first, rightValues.first);
            int max = Math.max(leftValues.second, rightValues.second);
            return new IntPair(min, max);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int c = Integer.parseInt(br.readLine());

        for(int t = 0; t < c; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int[] notices = new int[n];
            for(int i = 0;i < n; i++){
                notices[i] = Integer.parseInt(st.nextToken());
            }
            RMQ rmq = new RMQ(notices);
            for(int i = 0; i < q; i++){
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                if(from > to){
                    int tmp = from;
                    from = to;
                    to = tmp;
                }
                IntPair rst = rmq.query(from, to, 1, 0, n-1);
                System.out.println(rst.second - rst.first);
            }
        }
    }
}
