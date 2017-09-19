import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static long longRandomPrime()
    {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    private static long multiplier = 236;
    private static long prime = longRandomPrime();

    private static long polyHash(String s) {
        long h = 0;
        for (int j = 0; j < s.length(); j++)
            h = (multiplier * h + s.charAt(j)) % prime;
        return h;
    }

    private static long[] preComputedHashes;

    private static void preCompute(String text, int patternLength) {
        preComputedHashes[0] = polyHash(text.substring(0, patternLength));
        long y = 1;
        for(int j=0;j<patternLength;j++) {
            y = (y*multiplier)%prime;
        }
        for(int i=0;i<text.length()-patternLength;i++) {
            preComputedHashes[i+1] = ((multiplier*preComputedHashes[i])%prime + text.charAt(i+patternLength)%prime - (y*text.charAt(i))%prime + prime)%prime;
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String pat = input.pattern, tex = input.text;
        int p = pat.length(), t = tex.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        long patternHash = polyHash(pat);
        preComputedHashes = new long[t-p+1];
        preCompute(tex, p);
        for(int i=0;i<=t-p;i++) {
            if(patternHash==preComputedHashes[i]) {
                if(pat.equals(tex.substring(i, i+p))) occurrences.add(i);
            }
        }

        return occurrences;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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