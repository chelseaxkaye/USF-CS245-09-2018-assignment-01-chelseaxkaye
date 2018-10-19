import java.util.Random;

/*
* A class derived from David Guy Brizan's PracticeTest05.java
* Tests the algorithm 
*/

public class Test {

	protected int[] arr;
	protected String populationStrategy;
	protected SortingFactory factory;      // This creates different sorting algorithms.
	protected String [] algorithms = {"mergesort", "hybridsort"}; // Algos to use.

	public Test() {
		populationStrategy = "random";
		createArray(10000);
		factory = new SortingFactory(false);
	}

	public Test(int arraySize) {
		populationStrategy = "random";
		createArray(arraySize);
		factory = new SortingFactory(false);
	}

	public Test(int arraySize, String populationStrategy) {
		this.populationStrategy = populationStrategy;
		createArray(arraySize);
		factory = new SortingFactory(false);
	}

	//creates test array
	protected void createArray(int size) {
		arr = new int[size];
		populateArray();
	}

	//change array size of new array
	public void changeArraySize(int newSize) {
		createArray(newSize);
	}

	//populates array with random integers
	protected void populateArray() {
		if (populationStrategy.contains("increasing")) {
			populateArrayIncreasing();
		}
		populateArrayRandomly();
	}

	protected void populateArrayIncreasing() {
		Random r = new Random();
		int limit = (Integer.MAX_VALUE) / arr.length;
		arr[0] = Integer.MIN_VALUE + r.nextInt(limit);
		for (int i = 1; i < arr.length; i++) {
			arr[i] = arr[i-1] + r.nextInt(limit);
		}
	}

	protected void populateArrayRandomly() {
		Random r = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt(10);
	
		}
	}
	//checks if array is sorted
	protected boolean isSorted(int[] arr) {
		for (int i = 0; i < arr.length-1; i++) {
			if (arr[i] > arr[i+1])
				return false;
		}
		return true;
	}

	//prints the array size and if sorted
	public void printStatus() {
		System.out.print(arr.length + "\t");
		if (isSorted(arr))
			System.out.println("[OK]");
		else
			System.out.println("[XX] -- not sorted");
	}

	//prints the array and if sorted; accepts parameter
	public void printStatus(int[] arr) {
		System.out.print(arr.length + "\t");
		if (isSorted(arr))
			System.out.println("[OK]");
		else
			System.out.println("[XX] -- not sorted");
	}

	//makes a copy of array
	public int[] copyArray() {
		int[] copy = new int[arr.length];
		System.arraycopy(arr, 0, copy, 0, arr.length);
		return copy;
	}
	

	public void printSortingTiming() {

		for (String algo : algorithms) {
			try {
				SortingAlgorithm sort = factory.getSortingAlgorithm(algo);
				// System.out.println("----------------------------------------------------");
				// System.out.println("algorithm: " + algo);
				System.out.print(algo + "\t");
				// For each algorithm:
				// a) Copy the array
				int[] copy = copyArray();
				// b) Have the algorithm sort the copy ... while timing it.
				long start = System.currentTimeMillis();
				sort.sort(copy);
				// System.out.println("Sorting took: " + (System.currentTimeMillis() - start) + " ms.");
				long total_time = System.currentTimeMillis() - start;
				System.out.print(total_time + " ms.\t");
				if (total_time < 1000) {
					System.out.print("\t");
				}
				// c) Check for correctness
				printStatus(copy);
				// System.out.println("----------------------------------------------------");
			}
			catch (Exception e) {
				System.out.println("Unable to instantiate sorting algorithm " + algo);
			}
		}
	}


	public static void main(String[] args) {
		
		Test timing = new Test();
		int [] sizes = {50000, 100000};
		// int[] sizes = {20, 40, 50};

		for (int size : sizes) {
			timing.changeArraySize(size);
			timing.printSortingTiming();
			System.out.println("----------------------------------------------------");
		}
	}
}