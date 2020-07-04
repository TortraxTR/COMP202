package code;

import given.AbstractHashMap;
import given.iPrintable;
import given.iSet;

/*
 * A set class implemented with hashing. Note that there is no "value" here 
 * 
 * You are free to implement this however you want. Two potential ideas:
 * 
 * - Use a hashmap you have implemented with a dummy value class that does not take too much space
 * OR
 * - Re-implement the methods but tailor/optimize them for set operations
 * 
 * You are not allowed to use any existing java data structures
 * 
 */

public class HashSet<Key>  implements iSet<Key>, iPrintable<Key>{

	AbstractHashMap<Key,Integer> A;

	// A default public constructor is mandatory!
	public HashSet() {
		A = new HashMapSC<Key, Integer>();
	}


	public HashSet(Key[] keys) {
		A = new HashMapSC<Key, Integer>();
		for(int i = 0; i < keys.length; i++) {
			this.put(keys[i]);
		}
	}


	@Override
	public int size() {
		// TODO Auto-generated method stud
		return A.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return A.isEmpty();
	}

	@Override
	public boolean contains(Key k) {
		// TODO Auto-generated method stub
		for(Key key : keySet()) {
			if (key.equals(k)) return true;
		}
		return false;
	}

	@Override
	public boolean put(Key k) {
		// TODO Auto-generated method stub
		if (contains(k)) return true;
		else {
			A.put(k, 0);
			return false;
		}	
	}

	@Override
	public boolean remove(Key k) {
		// TODO Auto-generated method stub
		if(contains(k)) {
			A.remove(k);
			return true;
		}
		return false;
	}

	@Override
	public Iterable<Key> keySet() {
		// TODO Auto-generated method stub
		return A.keySet();
	}

	@Override
	public Object get(Key key) {
		// Do not touch
		return null;
	}

}
