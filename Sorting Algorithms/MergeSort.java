package code;

import java.lang.reflect.Array;

import given.AbstractArraySort;


/*
 * Implement the merge-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 * 
 * You may need to create an Array of K (Hint: the auxiliary array). 
 * Look at the previous assignments on how we did this!
 * 
 */

public class MergeSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  // Add any fields here
	K[] auxArray;
  public MergeSort() {
    name = "Mergesort";

    // Initialize anything else here
  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the merge-sort algorithm
	  auxArray = (K[]) Array.newInstance(inputArray[0].getClass(), inputArray.length);
	  midSort(inputArray, 0, inputArray.length-1);
  }

  // Public since we are going to check its output!
  public void merge(K[] inputArray, int lo, int mid, int hi) {
    // TODO: Implement the merging algorithm
	  for(int i = lo; i < hi+1; i++) {
		  auxArray[i] = inputArray[i];
	  }
	  
	  int i = lo;
	  int k = lo;
	  int j = mid+1;
	  
	  while (k <= hi) {
		  if(j > hi || (i <= mid && compare(auxArray[i],auxArray[j]) < 1)) {
			  inputArray[k] = auxArray[i];
			  k++;
			  i++;
		  } else {
			  inputArray[k] = auxArray[j];
			  k++;
			  j++;
		  }
	  }
	  
	  
  }
  
  public void midSort(K[] A, int lo, int hi) {
	if (lo < hi) {
		int mid = (lo+hi)/2;
		midSort(A,lo,mid);
		midSort(A,mid+1,hi);
		merge(A,lo,mid,hi);
	}
  }
  
  
  // Feel free to add more methods
}
