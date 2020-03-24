import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static String checker(String str){
        Stack<Integer> st = new Stack<>();
        for(int j = 0; j < str.length(); j++){
            switch (str.charAt(j)){
                case '(':
                    st.push(1);
                    break;
                case '{':
                    st.push(2);
                    break;
                case '[':
                    st.push(3);
                    break;
                case ')':
                    if(st.empty() || st.pop() != 1) return "NO";
                    break;
                case '}':
                    if(st.empty() || st.pop() != 2) return "NO";
                    break;
                case ']':
                    if(st.empty() || st.pop() != 3) return "NO";
                    break;
            }
        }
        if(st.empty()) return "YES";
        else return "NO";
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.nextLine();
        for(int i = 0 ;i < n; i++){
            String str = scan.nextLine();
            System.out.println(checker(str));
        }
    }
}
