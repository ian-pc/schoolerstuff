package schoolstuff;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class PriorityQueue<E> {
	
	private class Node {
		E value;
		int priority;
		
		public Node(E value, int priority) {
			this.value = value;
			this.priority = priority;
		}
		
		public int getprior() {
			return this.priority;
		}
	}
	
	private ArrayList<Node> pqueue = new ArrayList<Node>();
	
	public int getFirstPrior() {
		return pqueue.get(0).getprior();
	}
	
	public int get(E value) { //gets the value and return's it's priority
		int location = 0;
		while (true) {
			if (pqueue.get(location) == value) {
				break;
			}
			location++;
		}
		
		return pqueue.get(location).priority;
	}
	
	public String toString() {
		String string = "";
		
		for (Node n: pqueue) {
			string += "(" + n.value.toString() + ", " + n.priority + ")";
		}
		
		return string;
	}
	
	public void add(E value, int priority) {
//		int location = (int) pqueue.size()/2;
//		if (pqueue.size() % 2 == 1) {
//			if (pqueue.get(pqueue.size()).priority == priority) {
//				pqueue.add(new Node(value, priority));
//			} else pqueue.remove(pqueue.size());
//		}
//		while (true) {
//			if (pqueue.get(location).priority == priority) {
//				pqueue.add(new Node(value, priority));
//				break;
//			} else if (pqueue.get(location).priority > priority) {
//				location /=2;	
//			} else {
//				location += location/2;
//			}
//		}
		
		pqueue.add(new Node(value, priority));
		pqueue.sort(Comparator.comparingInt(Node::getprior));
	}
	
	public E pop() {
		E temp = pqueue.get(0).value;
		pqueue.remove(0);
		
		return temp;
	}
	
	public int size() {
		return pqueue.size();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		PriorityQueue<Integer> a = new PriorityQueue<>();
//		a.add(1, 0);
//		a.add(2, 0);
//		System.out.println(a.toString());
//		a.pop();
//		System.out.println(a.toString());
//		a.pop();
//		System.out.println(a.toString());
//		a.pop();
	}

}
