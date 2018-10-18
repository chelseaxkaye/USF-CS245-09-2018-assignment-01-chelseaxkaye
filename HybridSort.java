import java.util.*;
/*
* Class that uses a hybrid of insertion and merge to sort an array of random doubles
*/
public class HybridSort implements SortingAlgorithm{

	private int runs;
	private double[] arr;
	private int runsize;
	protected ArrayList<double[]> runslist = new ArrayList<double[]>();

	public void sort(double[] arr) {
		//calls makesRuns so that we have an array consisting of only runs
		runsize = 10;
		makesRuns(runsize);

		//now that there is an arraylist with mini temp arrays that are runs
		//merge them so that there is only one run
		for (int[] i: runslist) {
			merge(i[])
		}
	}

	//insertion sort
	public void insertionsort(double[] a) {
		for(int i = 1; i < a.length; i++) {
			double temp = a[i];
			int k = i - 1;
			while (k >= 0 && a[k] > temp) {
				a[k + 1] = a[k];
				--k;
			}
			a[k + 1] = temp;
		}
	}

	//merge sort
	public void merge(double[] a, double[] b, double[] c) {
		int left = 0;
		int right = 0; 
		int target = 0;

		//merge two arrays
		while (left < a.length && right < b.length) {
			if (a[left] <= b[right]) {
				c[target++] = a[left++];
			}
			else {
				c[target++] = b[right++];
			}
		}
		
		//copy left over elements
		while(left < a.length){
			c[target++] = a[left++];
		}
		while(right < b.length){
			c[target++] = b[right++];
		}
	}

	//iterate through the array and sort so that it only consists of runs of different sizes
	public void makesRuns(int runsize) {
		//iterate to find runs and runsizes
		for (int i = 0; i < arr.length; i++) {

			//restarts every run;
			int current = 0;

			//increments to populate temp array
			int start = i;

			//used to keep the index to put back values
			int back = i;

			boolean up = true;

			//find if there are runs or non-runs
			if (arr[i] <= arr[i + 1]) {
				while (arr[i] <= arr[i + 1]) {
					i++;
					current++;
				}
			}

			else if (arr[i] >= arr[i + 1]) {
				while (arr[i] >= arr[i + 1]) {
					i++;
					current++;
				}
				up = false;
			}	

			//create temp array for non-run values
			double[] temp = new double[start - current];

			//populate temp array
			for (int j = 0; start <= i; j++) {
				temp[j] = arr[start];
				start++;
			}

			//sort if not a run
			if (current - start <= runsize || up == false) {
				//sort temp array using insertion
				insertionsort(temp);
			}
			
			// replace with sorted values in the original array
			for (int k = 0; back <= i; k++) {
				arr[back++] = temp[k];	
			}
			

			//add mini arrays to arraylist
			runslist.add(temp);
		}
	}
}