package schoolstuff;

import java.util.Iterator;

public class LinkedList<E> {

	private class Node {
		
		Node next;
		E data;
		
		public Node(E data) {
			this.data = data;
		}
	}
	
	private Node first;
	
	public int size() {
		int size = 1;
		Node currcount = first;
		
		if (first == null) {
			return 0;
		}
		
		while (true) {
			currcount = currcount.next;
			if (currcount.next == null) {
				break;
			}
			size++;
		}
		
		return size;
	}
	
	public void add(E info) {
		
		Node newNode = new Node(info);
		
		if (first == null) {
			first = new Node(info);
			return;
		}
		
		Node curr = null;
		curr.next = newNode;
	}
	
	public void add(E info, int i) {
		
		Node newNode = new Node(info);
		
		if (first == null) {
			first = new Node(info);
			return;
		}

		if (i == 0) {
			newNode.next = first;
			first = newNode;
			return;
		}
		
		try {
			Node curr = first;
			for (int j = 0; j < i; j++) { 
				curr = curr.next;
			}
			
			newNode.next = curr.next;
			curr.next = newNode;
			
		}
		catch(NullPointerException e) {
			
			throw new IndexOutOfBoundsException();
		}
		
	}
	
}
