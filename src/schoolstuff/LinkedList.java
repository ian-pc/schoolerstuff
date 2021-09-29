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
			if (currcount == null || currcount.next == null) {
				break;
			}
			size++;
		}
		
		return size;
	}
	
	public E get(int index) {
		Node curr = first;
		try {
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		return curr.data;
		
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
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
	
	public void add(E info) {
		
		if (first == null) {
			first = new Node(info);
			return;
		} else {
			Node curr = first;
			while (curr.next != null) {
				curr = curr.next;
			}
			curr.next = new Node(info);
		}
	}
	
	public E remove(int i) {

		Node curr = first;
		if (i == 0) {
			first = curr.next;
		}
		
		try {
			for (int j = 0; j < i-1; j++) { 
				curr = curr.next;
			}
			
			E temp = curr.next.data;
			
			curr.next = curr.next.next;
			
			return temp;
		}
		catch(NullPointerException e) {
			
			throw new IndexOutOfBoundsException();
		}
		
	}
	
	public static void main(String[] args) {
		
		LinkedList<Integer> test = new LinkedList();
		test.add(3);
		test.add(5);
		test.add(8);
		System.out.println(test.get(0));
		System.out.println(test.get(1));
		System.out.println(test.get(2));
		
		test.remove(0);
		System.out.println(test.get(0));
		System.out.println(test.get(1));
	}

	
}
