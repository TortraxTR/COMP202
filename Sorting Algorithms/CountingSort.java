package code;


import given.AbstractArraySort;

/*
 * Implement the c algorithm here. You can look at the slides for the pseudo-codes.
 * You do not have to use swap or compare from the AbstractArraySort here
 * 
 * You may need to cast any K to Integer
 * 
 */

public class CountingSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  //Add any fields here
  
  public CountingSort()
  {
    name = "Countingsort";
  }
  
  @Override
  public void sort(K[] inputArray) {
    
    if(inputArray == null)
    {
      System.out.println("Null array");
      return;
    }
    if(inputArray.length < 1)
    {
      System.out.println("Empty array");
      return;
    }   
    
    if(!(inputArray[0] instanceof Integer)) {
      System.out.println("Can only sort integers!");
      return;
    }
    
    //TODO:: Implement the counting-sort algorithm here
    
    K minKey = inputArray[0];
    K maxKey = inputArray[0];
    
    for (int i = 0; i < inputArray.length;i++) {
    	if(compare(inputArray[i],minKey) == -1) {
    		minKey = inputArray[i];
    	}
    	if(compare(inputArray[i],maxKey) == 1) {
    		maxKey = inputArray[i];
    	}
    }
    
    int k = (int) maxKey - (int) minKey + 1;
    
    int[] counts = new int[k];
    
    for(int i = 0; i < counts.length; i++) {
    	counts[i] = 0;
    }
    
    for (int i = 0; i < inputArray.length; i++) {
    	counts[(int) inputArray[i] - (int) minKey]++;
    }
    
    int c = 0;
    
    for (int i = 0; i < k; i++) {
    	for (int j = 0; j < (int) counts[i]; j++) {
    		Integer k2 = new Integer(i+(int) minKey);
			inputArray[c] = (K) k2;
			c++;
    	}
    }
    
  }

}
