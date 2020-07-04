package code;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DirectedUnweightedGraph<V> extends BaseGraph<V> {

	/*
	 * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
	 * 
	 */

	private HashMap<V, List<V>> edgeList = new HashMap<>();

	@Override
	public String toString() {
		String tmp = "Directed Unweighted Graph";
		return tmp;
	}

	@Override
	public void insertVertex(V v) {
		// TODO Auto-generated method stub
		if (!edgeList.containsKey(v)) {
			edgeList.put(v, new LinkedList<V>());
		}
	}

	@Override
	public V removeVertex(V v) {
		// TODO Auto-generated method stub
		if (edgeList.containsKey(v)) {
			for(V vertex : edgeList.keySet()) {
				if(edgeList.get(vertex).contains(v)) {
					edgeList.get(vertex).remove(v);				}
			}
			edgeList.remove(v);

			return v;
		} else return null;

	}

	@Override
	public boolean areAdjacent(V v1, V v2) {
		// TODO Auto-generated method stub
		return edgeList.containsKey(v1) && edgeList.get(v1).contains(v2);
	}

	@Override
	public void insertEdge(V source, V target) {
		// TODO Auto-generated method stub
		if (!edgeList.containsKey(target)) insertVertex(target);
		if (!edgeList.containsKey(source)) insertVertex(source);
		edgeList.get(source).add(target);
	}

	@Override
	public void insertEdge(V source, V target, float weight) {
		// TODO Auto-generated method stub
		insertEdge(source, target);
	}

	@Override
	public boolean removeEdge(V source, V target) {
		// TODO Auto-generated method stub
		return edgeList.get(source).remove(target);
	}

	@Override
	public float getEdgeWeight(V source, V target) {
		// TODO Auto-generated method stub
		if (edgeList.containsKey(source) && edgeList.containsKey(target) && areAdjacent(source,target)) {
			return 1;
		} else return 0;
	}

	@Override
	public int numVertices() {
		// TODO Auto-generated method stub
		return edgeList.size();
	}

	@Override
	public Iterable<V> vertices() {
		// TODO Auto-generated method stub
		return edgeList.keySet();
	}

	@Override
	public int numEdges() {
		// TODO Auto-generated method stub
		int size = 0;
		for (V vertex : edgeList.keySet()) {
			size += edgeList.get(vertex).size();
		}
		return size;

	}

	@Override
	public boolean isDirected() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isWeighted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int outDegree(V v) {
		// TODO Auto-generated method stub
		if (edgeList.containsKey(v)) {
			return edgeList.get(v).size();
		} else return -1;
	}

	@Override
	public int inDegree(V v) {
		// TODO Auto-generated method stub
		if (edgeList.containsKey(v)) {
			int size = 0;
			for (V vertex : edgeList.keySet()) {
				if(edgeList.get(vertex).contains(v)) size++;
			}
			return size;
		} else return -1;

	}

	@Override
	public Iterable<V> outgoingNeighbors(V v) {
		// TODO Auto-generated method stub
		if (edgeList.containsKey(v)) return edgeList.get(v);
		else return null;
	}

	@Override
	public Iterable<V> incomingNeighbors(V v) {
		// TODO Auto-generated method stub
		LinkedList<V> l = new LinkedList<V>();
		for (V vertex : edgeList.keySet()) {
			if(edgeList.get(vertex).contains(v)) l.add(vertex);
		}
		return l;
	}
}
