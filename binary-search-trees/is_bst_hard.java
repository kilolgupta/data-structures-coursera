import java.util.*;
import java.io.*;

public class is_bst_hard {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        void inOrder(int k, int l, int r, ArrayList<Integer> result) {
            if(l==-1 && r==-1) {
                result.add(k);
            }
            else {
                if(l!=-1)
                    inOrder(tree[l].key, tree[l].left, tree[l].right, result);
                result.add(k);
                if(r!=-1)
                    inOrder(tree[r].key, tree[r].left, tree[r].right, result);
            }
        }

        boolean isBinarySearchTree() {
            if(tree.length>0) {
                ArrayList<Integer> result = new ArrayList<>();
                inOrder(tree[0].key, tree[0].left, tree[0].right, result);
                for(int i=0;i<result.size()-1; i++) {
                    if(result.get(i+1)<result.get(i)) {
                        return false;
                    }
                }
                for(int j =0; j<tree.length;j++) {
                    if(tree[j].left!=-1 && tree[tree[j].left].key >= tree[j].key) return false;
                }
                return true;
            }
            else
                return true;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
