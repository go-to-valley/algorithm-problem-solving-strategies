import java.util.*;

public class Main {
    static int[] preOrder;
    static int[] inOrder;

    public static void printPostOrder(int preStart, int inStart, int size){
        for(int i = 0;i < size; i++){
            if(preOrder[preStart] == inOrder[inStart+i]){
                printPostOrder(preStart+1, inStart, i);
                printPostOrder(preStart+i+1, inStart+i+1, size-i-1);
                System.out.print(inOrder[inStart+i]+" ");
                return;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt();
        for(int t = 0; t < c; t++){
            int n;
            n = scan.nextInt();
            preOrder = new int[n];
            inOrder = new int[n];
            for(int i = 0; i < n; i++) preOrder[i] = scan.nextInt();
            for(int i = 0; i < n; i++) inOrder[i] = scan.nextInt();
            printPostOrder(0, 0, n);
            System.out.println();
        }
    }
}