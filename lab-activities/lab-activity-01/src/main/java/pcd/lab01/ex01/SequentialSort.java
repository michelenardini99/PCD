package pcd.lab01.ex01;

import java.util.*;
import java.util.stream.IntStream;

public class SequentialSort {

	static final int VECTOR_SIZE = 400_000_000;
	
	public static void main(String[] args) throws InterruptedException {

		List<SortThread> threads = new ArrayList<>();

		int numOfProcessors = Runtime.getRuntime().availableProcessors();

		log("Number of processors: " + numOfProcessors);
	
		log("Num elements to sort: " + VECTOR_SIZE);
		log("Generating array.");
		var v = genArray(VECTOR_SIZE);
		
		log("Array generated.");

		log("Sorting.");

		long t0 = System.nanoTime();

		while (numOfProcessors >= 1){
			threads.clear();

			int chunkSize = VECTOR_SIZE / numOfProcessors;

			int remainder = VECTOR_SIZE % numOfProcessors;

			IntStream.range(0, numOfProcessors)
					.forEach(i -> {
						int start = i * chunkSize + (Math.min(i, remainder));
						int end = start + chunkSize + (i < remainder ? 1 : 0);
						threads.add(new SortThread("Thread" + i, v, start, end));
					});

			for(SortThread t : threads){
				t.start();
			}

			for(SortThread t : threads){
				t.join();
			}

			numOfProcessors /= 2;
		}

		long t1 = System.nanoTime();
		log("Done. Time elapsed: " + ((t1 - t0) / 1000000) + " ms");

		log("The array is sorted? " + isSorted(v));
	}

	private static boolean isSorted(int[] array) {
		if (array == null || array.length <= 1) return true;

		return IntStream.range(0, array.length - 1)
				.allMatch(i -> array[i] <= array[i + 1]);
	}


	private static int[] genArray(int n) {
		Random gen = new Random(System.currentTimeMillis());
		var v = new int[n];
		for (int i = 0; i < v.length; i++) {
			v[i] = gen.nextInt();
		}
		return v;
	}

	private static void dumpArray(int[] v) {
		for (var l:  v) {
			System.out.print(l + " ");
		}
		System.out.println();
	}

	private static void log(String msg) {
		System.out.println(msg);
	}
}
