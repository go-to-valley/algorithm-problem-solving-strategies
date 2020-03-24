import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int c;
        Scanner scan = new Scanner(System.in);
        c = scan.nextInt();
        for(int t = 0; t < c ;t++){
            int n, k;
            n = scan.nextInt();
            k = scan.nextInt();
            ArrayList<Integer> arr = new ArrayList<Integer>();
            for(int i = 0 ;i < n; i++){
                arr.add(i+1);
            }
            int pos = 0;
            for(int i = 0; i <n-2; i++){
                arr.remove(pos);
                pos = (k+pos-1)%arr.size();
            }
            for(int i = 0; i < 2; i++){
                System.out.print(String.valueOf(arr.get(i))+" ");
            }
            System.out.print('\n');
        }
    }
}
