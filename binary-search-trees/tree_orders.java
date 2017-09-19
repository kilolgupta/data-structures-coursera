import java.util.*;
import java.io.*;

public class tree_orders {
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

	private class TreeOrders {
		int n;
		int[] key, left, right;

		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			key = new int[n];
			left = new int[n];
			right = new int[n];
			for (int i = 0; i < n; i++) {
				key[i] = in.nextInt();
				left[i] = in.nextInt();
				right[i] = in.nextInt();
			}
		}
		void inOrder(int k, int l, int r, ArrayList<Integer> result) {
			if(l==-1 && r==-1) {
				result.add(k);
			}
			else {
				if(l!=-1)
					inOrder(key[l], left[l], right[l], result);
				result.add(k);
				if(r!=-1)
					inOrder(key[r], left[r], right[r], result);
			}
		}
		void preOrder(int k, int l, int r, ArrayList<Integer> result) {
			if(l==-1 && r==-1) {
				result.add(k);
			}
			else {
				result.add(k);
				if(l!=-1)
					preOrder(key[l], left[l], right[l], result);
				if(r!=-1)
					preOrder(key[r], left[r], right[r], result);
			}
		}
		void postOrder(int k, int l, int r, ArrayList<Integer> result) {
			if(l==-1 && r==-1) {
				result.add(k);
			}
			else {
				if(l!=-1)
					postOrder(key[l], left[l], right[l], result);
				if(r!=-1)
					postOrder(key[r], left[r], right[r], result);
				result.add(k);
			}
		}

		List<Integer> inOrder() {
			ArrayList<Integer> result = new ArrayList<>();
			inOrder(key[0], left[0], right[0], result);
			return result;
		}

		List<Integer> preOrder() {
			ArrayList<Integer> result = new ArrayList<>();
			preOrder(key[0], left[0], right[0], result);
			return result;
		}

		List<Integer> postOrder() {
			ArrayList<Integer> result = new ArrayList<>();
			postOrder(key[0], left[0], right[0], result);
			return result;
		}
	}

	static public void main(String[] args) throws IOException {
		new Thread(null, new Runnable() {
			public void run() {
				try {
					new tree_orders().run();
				} catch (IOException e) {
				}
			}
		}, "1", 1 << 26).start();
	}

	public void print(List<Integer> x) {
		for (Integer a : x) {
			System.out.print(a + " ");
		}
		System.out.println();
	}

	public void run() throws IOException {
		TreeOrders tree = new TreeOrders();
		tree.read();
		print(tree.inOrder());
		print(tree.preOrder());
		print(tree.postOrder());
	}
}
