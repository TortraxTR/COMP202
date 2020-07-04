package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 * 
 * Feel free to add any addition methods and fields as you like
 */
public class GraphAlgorithms<V extends Comparable<V>> {

	/*
	 * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
	 * 
	 */

	public static boolean usageCheck = false;

	/*
	 * WARNING: MUST USE THIS FUNCTION TO SORT THE 
	 * NEIGHBORS (the adjacent call in the pseudocodes)
	 * FOR DFS AND BFS
	 * 
	 * THIS IS DONE TO MAKE AUTOGRADING EASIER
	 */
	public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
		usageCheck = true;
		List<V> sorted = new ArrayList<>();
		for (V i : inIterable) {
			sorted.add(i);
		}
		Collections.sort(sorted);
		return sorted;
	}

	/*
	 * Runs depth first search on the given graph G and
	 * returns a list of vertices in the visited order, 
	 * starting from the startvertex.
	 * 
	 */



	public List<V> DFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		LinkedList<V> visited = new LinkedList<V>();
		Stack<V> s = new Stack<V>();
		s.push(startVertex);
		while(!s.isEmpty()) {
			V u = s.pop();
			if(!visited.contains(u)) {
				visited.add(u);
				Iterator<V> i = G.outgoingNeighbors(u).iterator();
				while(i.hasNext()) {
					V w = i.next();
					if(!visited.contains(w)) {
						s.push(w);
					}
				}
			}
		}

		return visited;
	}

	/*
	 * Runs breadth first search on the given graph G and
	 * returns a list of vertices in the visited order, 
	 * starting from the startvertex.
	 * 
	 */
	public List<V> BFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		LinkedList<V> visited = new LinkedList<V>();
		Queue<V> q = new LinkedList<V>();
		q.offer(startVertex);
		while(!q.isEmpty()) {
			V u = q.poll();
			if(!visited.contains(u)) {
				visited.add(u);
				Iterator<V> i = G.outgoingNeighbors(u).iterator();
				while(i.hasNext()) {
					V w = i.next();
					if(!visited.contains(w)) {
						q.offer(w);
					}
				}
			}
		}

		return visited;
	}

	/*
	 * Runs Dijkstras single source all-destinations shortest path 
	 * algorithm on the given graph G and returns a map of vertices
	 * and their associated minimum costs, starting from the startvertex.
	 * 
	 */

	class DEdge {
		V source;
		V target;
		float weight;

		public DEdge(V source, V target, float weight) {
			this.source = source;
			this.target = target;
			this.weight = weight;
		}
	}

	public HashMap<V,Float> Dijkstras(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		HashMap<V,Float> distances = new HashMap<V,Float>();
		distances.put(startVertex, 0f);
		Iterator<V> nodes = G.vertices().iterator();
		while (nodes.hasNext()) {
			V u = nodes.next();
			Iterator<V> i = G.outgoingNeighbors(u).iterator();
			while(i.hasNext()) {
				V n = i.next();
				if(!distances.containsKey(n)) {
					distances.put(n, G.getEdgeWeight(u, n));
				} else {
					for (V node : distances.keySet()) {
						if (distances.get(n) + G.getEdgeWeight(node, n) < distances.get(node)) {
							distances.replace(node, distances.get(n) + G.getEdgeWeight(node, n));
						}
					}
				}
			}
			
		}

		return distances;
	}

	/*
	 *  Returns true if the given graph is cyclic, false otherwise
	 */
	public boolean isCyclic(BaseGraph<V> G) {
		//TODO
		LinkedList<V> visited = new LinkedList<V>();
		Stack<V> s = new Stack<V>();
		Iterator<V>t = G.vertices().iterator();
		s.push(t.next());
		while(!s.isEmpty()) {
			V u = s.pop();
			if(!visited.contains(u)) {
				visited.add(u);
				Iterator<V> i = G.outgoingNeighbors(u).iterator();
				while(i.hasNext()) {
					V w = i.next();
					if(!visited.contains(w)) {
						s.push(w);
					} else return true;
				}
			} 
		}



		return false;
	}

}
