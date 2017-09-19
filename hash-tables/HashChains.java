import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;

    private Hashtable<Integer, List<String>> hashChain = new Hashtable<>();
    private static int bucketCount;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long multiplier = 263;
        long prime = 1000000007;
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = ((hash * multiplier)%prime + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int index = in.nextInt();
            return new Query(type, index);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
    }

    private void processQuery(Query query) {
        if(query.type.equals("check")) {
            List<String> elems = hashChain.get(query.ind);
            if(elems!=null) {
                for(String cur : elems) {
                    out.print(cur + " ");
                }
            }
            out.println();
        }
        else {
            int hash = hashFunc(query.s);
            List<String> elems = hashChain.get(hash);
            if(query.type.equals("add")) {
                if (elems!=null && !elems.contains(query.s)) {
                    hashChain.get(hash).add(0, query.s);
                }
                else {
                    List<String> newRow = new ArrayList<String>();
                    newRow.add(query.s);
                    hashChain.put(hash, newRow);
                }
            }
            else if(query.type.equals("del")) {
                if (elems!=null && elems.contains(query.s)) {
                    hashChain.get(hash).remove(query.s);
                }
            }
            else if(query.type.equals("find")) {
                if(elems==null) out.println("no");
                else writeSearchResult(elems.contains(query.s));
            }
            else {
                throw new RuntimeException("Unknown query: " + query.type);
            }
        }
    }

    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        System.out.print("");
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
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