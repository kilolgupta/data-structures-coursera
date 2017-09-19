import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        NodeComparator nodeComparator = new NodeComparator();
        PriorityQueue<QueueNode> threadsPriorityQueue = new PriorityQueue<>(nodeComparator);
        for(int j=0;j<numWorkers;j++) {
            threadsPriorityQueue.add(new QueueNode(j, 0));
        }
        for(int i=0;i<jobs.length;i++) {
            QueueNode qn = threadsPriorityQueue.poll();
            assignedWorker[i] = qn.index;
            startTime[i] = qn.nextStartTime;
            qn.nextStartTime+=jobs[i];
            threadsPriorityQueue.add(qn);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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

    private class NodeComparator implements Comparator<QueueNode> {
        public int compare(QueueNode one, QueueNode two) {
            if(one.nextStartTime>two.nextStartTime) return 1;
            else if(one.nextStartTime <two.nextStartTime) return -1;
            else {
                if(one.index>two.index) return 1;
                else if(one.index<two.index) return -1;
                else return 0;
            }
        }
    }

    static class QueueNode {
        int index;
        long nextStartTime;

        QueueNode(int index, long nextStartTime) {
            this.index = index;
            this.nextStartTime = nextStartTime;
        }
    }
}
