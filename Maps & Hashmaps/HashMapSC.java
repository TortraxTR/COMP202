package code;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

import given.AbstractHashMap;
import given.HashEntry;

/*
 * The file should contain the implementation of a hashmap with:
 * - Separate Chaining for collision handling
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Java's own linked lists for the secondary containers
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 *   Note that for this type of hashmap, load factor can be higher than 1
 * 
 * Some helper functions are provided to you. We suggest that you go over them.
 * 
 * You are not allowed to use any existing java data structures other than for the buckets (which have been 
 * created for you) and the keyset method
 */

public class HashMapSC<Key, Value> extends AbstractHashMap<Key, Value> {

	// The underlying array to hold hash entry Lists
	private LinkedList<HashEntry<Key, Value>>[] buckets;

	// Note that the Linkedlists are still not initialized!
	@SuppressWarnings("unchecked")
	protected void resizeBuckets(int newSize) {
		// Update the capacity
		N = nextPrime(newSize);
		buckets = (LinkedList<HashEntry<Key, Value>>[]) Array.newInstance(LinkedList.class, N);
	}

	/*
	 * ADD MORE FIELDS IF NEEDED
	 * 
	 */



	// The threshold of the load factor for resizing
	protected float criticalLoadFactor;

	/*
	 * ADD A NESTED CLASS IF NEEDED
	 * 
	 */



	public int hashValue(Key key, int iter) {
		return hashValue(key);
	}

	public int hashValue(Key key) {
		// TODO: Implement the hashvalue computation with the MAD method. Will be almost
		// the same as the primaryHash method of HashMapDH

		return Math.abs(((a*Math.abs(key.hashCode()) + b) % P) % N);
	}

	// Default constructor
	public HashMapSC() {
		this(101);
	}

	public HashMapSC(int initSize) {
		// High criticalAlpha for representing average items in a secondary container
		this(initSize, 10f);
	}

	public HashMapSC(int initSize, float criticalAlpha) {
		N = initSize;
		criticalLoadFactor = criticalAlpha;
		resizeBuckets(N);

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

	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		if (k != null) {
			LinkedList<HashEntry<Key, Value>> test = buckets[hashValue(k)];
			if (test != null) {
				for (HashEntry<Key, Value> test2 : test) {
					if (test2.getKey().equals(k)) return test2.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public Value put(Key k, Value v) {
		// TODO Auto-generated method stub
		// Do not forget to resize if needed!
		// Note that the linked lists are not initialized
		if (k == null) return null;

		int hashCode = hashValue(k);
		if(buckets[hashCode] != null) {
			for(HashEntry<Key,Value> entry : buckets[hashCode]) {
				if (entry.getKey().equals(k)) {
					Value val = entry.getValue();
					entry.setValue(v);
					return val;
				}
			}
			n++;
			checkAndResize();
			HashEntry<Key,Value> newEntry = new HashEntry<Key, Value>(k,v); 
			buckets[hashCode].add(newEntry);
		} else {
			buckets[hashCode] = new LinkedList<HashEntry<Key, Value>>();
			n++;
			checkAndResize();
			HashEntry<Key,Value> newEntry = new HashEntry<Key, Value>(k,v); 
			buckets[hashCode].add(newEntry);
		}
		return null;
	}

	@Override
	public Value remove(Key k) {
		// TODO Auto-generated method stub
		if (k != null) {
			int hashCode = hashValue(k);
			if(buckets[hashCode] == null) return null;		
			for(HashEntry<Key,Value> entry : buckets[hashCode]) {
				if (entry.getKey().equals(k)) {
					Value val = entry.getValue();
					buckets[hashCode].remove(entry);
					n--;
					return val;
				}
			}
		}
		return null;
	}

	@Override
	public Iterable<Key> keySet() {
		// TODO Auto-generated method stub
		ArrayList<Key> keyClone = new ArrayList<Key>();
		for (int i = 0; i < N; i++) {
			if(buckets[i] != null) 
				for (HashEntry<Key,Value> entry : buckets[i]) {
					keyClone.add(entry.getKey());
				}
		}
		return keyClone;
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
			LinkedList<HashEntry<Key, Value>>[] tmp = buckets.clone();
			resizeBuckets(2*N);
			updateHashParams();
			for(int i = 0; i < tmp.length; i++) {
				buckets[i] = tmp[i];
			}

		}
	}
}
