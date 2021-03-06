package code;

import java.lang.reflect.Array;
import java.util.ArrayList;

import given.AbstractHashMap;
import given.HashEntry;

/*
 * The file should contain the implementation of a hashmap with:
 * - Open addressing for collision handling
 * - Double hashing for probing. The double hash function should be of the form: q - (k mod q)
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 * 
 * Some helper functions are provided to you. We suggest that you go over them.
 * 
 * You are not allowed to use any existing java data structures other than for the keyset method
 */

public class HashMapDH<Key, Value> extends AbstractHashMap<Key, Value> {

	// The underlying array to hold hash entries (see the HashEntry class)
	private HashEntry<Key, Value>[] buckets;
	//Do not forget to call this when you need to increase the size!
	@SuppressWarnings("unchecked")
	protected void resizeBuckets(int newSize) {
		// Update the capacity
		N = nextPrime(newSize);
		buckets = (HashEntry<Key, Value>[]) Array.newInstance(HashEntry.class, N);
	}

	// The threshold of the load factor for resizing
	protected float criticalLoadFactor;

	// The prime number for the secondary hash
	int dhP;

	/*
	 * ADD MORE FIELDS IF NEEDED
	 * 
	 */

	/*
	 * ADD A NESTED CLASS IF NEEDED
	 * 
	 */


	// Default constructor
	public HashMapDH() {
		this(101);
	}

	public HashMapDH(int initSize) {
		this(initSize, 0.6f);
	}

	public HashMapDH(int initSize, float criticalAlpha) {
		N = initSize;
		criticalLoadFactor = criticalAlpha;
		resizeBuckets(N);
		this.n = 0;
		// Set up the MAD compression and secondary hash parameters
		updateHashParams();

		/*
		 * ADD MORE CODE IF NEEDED
		 * 
		 */
	}

	/*
	 * ADD MORE METHODS IF NEEDED
	 * 
	 */


		


		/*if (k != null && keyList.contains(k)) {
			int iter = 0;
			int hashVal = hashValue(k,iter);
			if (buckets[hashVal] == null || buckets[hashVal].getKey() != k) {
				iter++;
				hashVal = hashValue(k, iter);
			}
			return buckets[hashVal];
		} else {
			return null;
		}*/



	/**
	 * Calculates the hash value by compressing the given hashcode. Note that you
	 * need to use the Multiple-Add-Divide method. The class variables "a" is the
	 * scale, "b" is the shift, "mainP" is the prime which are calculated for you.
	 * Do not include the size of the array here
	 * 
	 * Make sure to include the absolute value since there maybe integer overflow!
	 */
	protected int primaryHash(int hashCode) {
		// TODO: Implement MAD compression given the hash code, should be 1 line
		return Math.abs((a*Math.abs(hashCode) + b) % P);
	}

	/**
	 * The secondary hash function. Remember you need to use "dhP" here!
	 * 
	 */
	protected int secondaryHash(int hashCode) {
		// TODO: Implement the secondary hash function taught in the class
		return dhP - (hashCode % dhP);
	}

	@Override
	public int hashValue(Key key, int iter) {
		int k = Math.abs(key.hashCode());
		return Math.abs(primaryHash(k) + iter * secondaryHash(k)) % N;
	}

	/**
	 * checkAndResize checks whether the current load factor is greater than the
	 * specified critical load factor. If it is, the table size should be increased
	 * to 2*N and recreate the hash table for the keys (rehashing). Do not forget to
	 * re-calculate the hash parameters and do not forget to re-populate the new
	 * array!
	 */
	protected void checkAndResize() {
		if (loadFactor() > criticalLoadFactor) {
			// TODO: Fill this yourself
			HashEntry<Key, Value>[] tmp = buckets.clone();
			resizeBuckets(2*N);
			updateHashParams();
			for(int i = 0; i < tmp.length; i++) {
				if (tmp[i] != null) {
					put(tmp[i].getKey(),tmp[i].getValue());
				}				
			}
		}
	}

	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		if (k == null) return null;
		int iter = 0;
		int hashVal = hashValue(k,iter);
		while (iter < N) {
			if(buckets[hashVal] == null) {
				iter++;
				hashVal = hashValue(k,iter);
			} else if (buckets[hashVal].getKey().equals(k)) {
				return buckets[hashVal].getValue();
			}
			else {
				iter++;
				hashVal = hashValue(k,iter);
			}
		} 
		return null;


		/*if (k != null && keyList.contains(k)) {
			int iter = 0;
			int hashVal = hashValue(k,iter);
			if (buckets[hashVal] == null || buckets[hashVal].getKey() != k) {
				iter++;
				hashVal = hashValue(k, iter);
			}
			return buckets[hashVal].getValue();
		} else {
			return null;
		}*/

	}

	@Override
	public Value put(Key k, Value v) {
		// TODO Auto-generated method stub
		// Do not forget to resize if needed!
		if (k == null) return null;
		int iter = 0;
		int hashVal = hashValue(k, iter);

		while (iter < N) {
			if (buckets[hashVal] == null) {				
				buckets[hashVal] = new HashEntry<Key,Value>(k,v);
				this.n++;
				checkAndResize();
				break;
			}
			else if (buckets[hashVal].getKey().equals(k)) {
				Value val = buckets[hashVal].getValue();
				buckets[hashVal].setValue(v);
				return val;
			} else {
				iter++;
				hashVal = hashValue(k, iter);
			}
		}
		return null;
	}

	@Override
	public Value remove(Key k) {
		// TODO Auto-generated method stub
		if (k == null) return null;
		int iter = 0;
		int hashVal = hashValue(k,iter);
		while (iter<N) {
			if (buckets[hashVal] == null || buckets[hashVal].getKey() != k) {
				iter++;
				hashVal = hashValue(k,iter);
			} else {
				Value v = buckets[hashVal].getValue();
				buckets[hashVal] = null;
				this.n--;
				return v;
			}
		}

		return null;
	}

	// This is the only function you are allowed to use an existing Java data
	// structure!
	@Override
	public Iterable<Key> keySet() {
		// TODO Auto-generated method stub
		ArrayList<Key> keyClone = new ArrayList<Key>();
		for (int i = 0; i < N; i++) {
			if(buckets[i] != null) keyClone.add(buckets[i].getKey());
		}
		return keyClone;
	}

	@Override
	protected void updateHashParams() {
		super.updateHashParams();
		dhP = nextPrime(N / 2);
	}

}
