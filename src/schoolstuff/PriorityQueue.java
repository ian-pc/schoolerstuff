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
		String string = null;
		
		for (int i = 0; i < pqueue.size(); i++) {
			string += (String) pqueue.get(i).value + " " + String.valueOf(pqueue.get(i).priority) + " ";
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
		
	}

}
