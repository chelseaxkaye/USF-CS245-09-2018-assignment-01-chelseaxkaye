public class MergeSort implements SortingAlgorithm {

	public void sort(int[] a) {
		mergesort(a, 0, a.length - 1);
	}

	public void merge(int[] a, int l, int m, int r) {
		int leftlength = m - l + 1;
		int rightlength = r - m;

		//create new arrays
		int[] L = new int[leftlength];
		int[] R = new int[rightlength];

		//copy to temp arrays
		for (int k = 0; k < leftlength; k++) {
			L[k] = a[l + k];
		}
		for (int j = 0; j < rightlength; j++) {
			R[j] = a[m + 1 + j];
		}

		int left = 0;
		int right = 0; 
		int target = l;

		//actual merge
		while (left < leftlength && right < rightlength) {
			if (L[left] <= R[right]) {
				a[target++] = L[left++];
			}
			else {
				a[target++] = R[right++];
			}
		}
		
		//copy left over elements
		while(left < leftlength){
			a[target++] = L[left++];
		}
		while(right < rightlength){
			a[target++] = R[right++];
		}
	}

	public void mergesort(int[] a, int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;
			mergesort(a, left, mid);
			mergesort(a, mid + 1, right);
			merge(a, left, mid, right);
		}
		

		
	}
}