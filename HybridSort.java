import java.util.*;
import java.lang.*;
/*
* Class that uses a hybrid of insertion and merge to sort an array of random integers
*/
public class HybridSort implements SortingAlgorithm {

	public void sort(int[] a) {
		// for (int i : a) {
		// 	System.out.println(i);
		// }
		int runsize = 3;
		hybrid(a, runsize);
	}

	public void insertionsort(int[] a) {
		for(int i = 1; i < a.length; i++) {
			int temp = a[i];
			int k = i - 1;
			while (k >= 0 && a[k] > temp) {
				a[k + 1] = a[k];
				--k;
			}
			a[k + 1] = temp;
		}
	}

	public void insertionhelp(int[] a, int l, int h){
		int curr = l;
        int len = h - l + 1;
        int[] temp = new int[len];

        for(int i = 0; i < len; i++){
            temp[i] = a[l++];
        }

        insertionsort(temp);

        for(int k = 0; curr <= h; k++){
            a[curr++] = temp[k];
        }

    }
	//merge sort
	public void merge(int[] a, int[] l, int[] r, int i) {

		int left = 0;
		int right = 0; 

		//merge two arrays
		while (left < l.length && right < r.length) {
			if (l[left] <= r[right]) {
				a[i++] = l[left++];
			}
			else {
				a[i++] = r[right++];
			}
		}
		
		//copy left over elements
		while(left < l.length){
			a[i++] = l[left++];
		}
		while(right < r.length){
			a[i++] = r[right++];
		}
	}

	public int[] sub(int[] a, int l, int h){
        int len = h - l + 1;
        int[] temp = new int[len];

        for(int i = 0; i < len; i++){
            temp[i] = a[l++];
        }

        return temp;
    }

	//iterate through the array and sort so that it only consists of runs of different sizes
	public void hybrid(int[] a, int runsize) {

		HashMap<Integer, Integer> rl = new HashMap<>();

		int start = 0;
		int end = 0;

		//iterate to find runs and runsizes
		for (int i = 0; i < a.length; i++) {

			if (i == 0) {
				start = 0;
			}
		
			else if (i != a.length - 1) {
				if (a[i] <= a[i + 1] && a[i] <= a[i - 1]) {
					start = i;
				}
			}
			

			//find if there are runs or non-runs
			if (i == 0) {
				if (a[i] >= a[i + 1]) {
					end = 0;
				}
			}
			if (i == a.length - 1) {
				if (a[i] >= a[i - 1]) {
					end = i;
				}
			}
			else if (i != 0) {
				if((a[i] >= a[i + 1] && a[i] >= a[i - 1])) {
					end = i;
				}
			}
			if (end - start + 1 >= runsize) {
				rl.put(start, end);
			}
		}

		if (rl.size() != 0) {

			int l = 0;
			int h;

			for (int j = 0; j < a.length; j++) {
        		if(j == 0 && rl.containsKey(j)){
                    l = rl.get(j) + 1;
                }

                else if (rl.containsKey(j) && ((j != 0) || j != a.length - 1)) {
                    h = j - 1;                          
                    rl.put(l, h);
                    insertionhelp(a, l, h);
                    l = rl.get(j) + 1;
                    if (rl.containsKey(l)) {                 
                        j = rl.get(l);  
                    } else {
                        j = l;                               
                    }
                } 
                else if (j == a.length - 1) {
                    rl.put(l, j);
                    insertionhelp(a, l, j);
                }
            }
		}
		else if (rl.size() == 0){
			for (int k = 0; k < a.length; k++)
				rl.put(k, k);

		}

		if (!rl.containsKey(a.length - 1) && !rl.containsValue(a.length - 1)) {
			rl.put(a.length - 1, a.length - 1);
		}

		while (rl.size() != 1) {
			for (int p = 0; p < a.length; p++) {

				int current = p;
				int fL = 0;
				int lL = 0;
				int fR = 0; 
				int lR = 0;

				int[] first;
				int[] second;

				if (rl.size() != 1 && rl.size() <= 3) {
					fL = 0;
					lL = rl.get(fL) + 1;
					first = sub(a, fL, lL - 1);

					fR = lL;
					lR = rl.get(fR) + 1;
					second = sub(a, fR, lR - 1);

					rl.remove(fR);
					rl.put(fL, lR - 1);
					merge(a, first, second, 0);

				}
				else if (rl.size() != 1) {
					if (rl.containsKey(p)) {
						fL = p;
						lL = rl.get(p);
						p = rl.get(p) + 1;
					}

					first = sub(a, fL, lL);

					if (rl.containsKey(p)) {
						fR = p;
						lR = rl.get(p);
						p = rl.get(p);

						second = sub(a, fR, lR);

						rl.remove(fR);
						rl.put(fL, lR);
						merge(a, first, second, current);
					}
				}

			}
		}
	}
}