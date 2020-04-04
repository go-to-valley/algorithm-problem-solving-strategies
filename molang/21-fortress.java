import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static class Fortress{
        int x, y, r;
        ArrayList<Fortress> children;
        Fortress parent;
        int depth;
        static int move;

        public static int getMax(int a, int b){
            return a>b?a:b;
        }

        public Fortress(int x, int y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
            depth = 0;
            move = 0;
            children = new ArrayList<>();
        }

        public int getDepth(){
            if(children.isEmpty()) return depth = 0;
            int[] depths = new int[children.size()];
            int i = 0;
            for(Fortress child: children) {
                depths[i++] = child.getDepth();
            }
            Arrays.sort(depths);
            if(depths.length >= 2)
                move = getMax(move, depths[depths.length-2]+depths[depths.length-1]+2);
            return depth = depths[depths.length-1]+1;
        }

        public void printFortresses(){
            for(int i = 0; i < depth; i++) System.out.print(" ");
            System.out.println(x+" "+y+" "+r);
            for(Fortress child: children) child.printFortresses();
        }

        public boolean putFortress(Fortress newFortress){
            switch (includeFortress(newFortress)){
                case 1:
                    ArrayList<Fortress> tmpArr = new ArrayList<>();
                    for(Fortress child: children) tmpArr.add(child);
                    for(Fortress tmp: tmpArr)
                        if(tmp.putFortress(newFortress))
                            return true;
                    if(!children.contains(newFortress)) children.add(newFortress);
                    newFortress.parent = this;
                    return true;
                case -1:
                    newFortress.children.add(this);
                    this.parent.children.remove(this);
                    if(!this.parent.children.contains(newFortress)) this.parent.children.add(newFortress);
                    newFortress.parent = this.parent;
                    this.parent = newFortress;
                    return false;
            }
            return false;
        }

        public int includeFortress(Fortress target){
            int dst = squareOfDistance(target);
            return (dst < r*r) ? (target.r < r ? 1 : -1) : ((dst < (target.r*target.r)) ? -1 : 0);
        }

        public int squareOfDistance(Fortress target){
            return (x-target.x)*(x-target.x) + (y-target.y)*(y-target.y);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt();
        for(int t = 0; t < c; t++){
            int n;
            n = scan.nextInt();
            Fortress rootFortress = new Fortress(scan.nextInt(), scan.nextInt(), scan.nextInt());
            for(int i = 1; i < n; i++){
                Fortress newFortress = new Fortress(scan.nextInt(), scan.nextInt(), scan.nextInt());
                rootFortress.putFortress(newFortress);
            }
            rootFortress.getDepth();
            System.out.println(Fortress.getMax(Fortress.move, rootFortress.depth));
        }
    }
}
