import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
            data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
            out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void checkAndSwap(int i) {
        int leftChildIndex = 2*i+1;
        int rightChildIndex = 2*i+2;
        if(leftChildIndex >= data.length) {
            if(rightChildIndex >= data.length) {
                //No children, leaf node
                return;
            }
            else {
                //Only right child present
                if(data[i]>data[rightChildIndex]) {
                    swaps.add(new Swap(i, rightChildIndex));
                    int tmp = data[i];
                    data[i] = data[rightChildIndex];
                    data[rightChildIndex] = tmp;
                    checkAndSwap(rightChildIndex);
                }
            }
        }
        else {
            if(rightChildIndex >= data.length) {
                //Only left child present
                if(data[i]>data[leftChildIndex]) {
                    swaps.add(new Swap(i, leftChildIndex));
                    int tmp = data[i];
                    data[i] = data[leftChildIndex];
                    data[leftChildIndex] = tmp;
                    checkAndSwap(leftChildIndex);
                }
            }
            else {
                //both children present
                int min = data[leftChildIndex] < data[rightChildIndex]?data[leftChildIndex]:data[rightChildIndex];
                int minIndex = data[leftChildIndex] < data[rightChildIndex]?leftChildIndex:rightChildIndex;
                if(data[i] > min) {
                    swaps.add(new Swap(i, minIndex));
                    int tmp = data[i];
                    data[i] = data[minIndex];
                    data[minIndex] = tmp;
                    checkAndSwap(minIndex);
                }
            }
        }
    }
    private void generateSwaps() {
        swaps = new ArrayList<Swap>();
        for(int i = data.length-1;i>=0;i--) {
            checkAndSwap(i);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
