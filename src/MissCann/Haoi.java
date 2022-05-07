package MissCann;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;


public class Haoi {
	public Stack<Integer>[] haois; //an array of towers
	public int numTowers, numRings;
	
	
	public Haoi(int numRings, int numTowers, Stack<Integer>[] haois) {
		this.numTowers = numTowers;
		this.numRings = numRings;
//		this.haois = (Stack<Integer>[]) new Stack[numTowers];
		this.haois = haois;
	}
	
	public String toString() {
		String returnVal = "";
		for (int i = 0; i < haois.length; i++) {
			for (int j = 0; j < haois[i].size(); j++) {
				returnVal += ((j > haois[i].size()) ? 0 : (haois[i].get(j))) + " ";
			}
			returnVal += "| ";
		}
		return returnVal;
	}
	
	public Boolean isLegal() {
		for (int i = 0; i < haois.length - 1; i++) {
			int prev = haois[i].get(haois[i].size() - 1);
			for (int j = 0; j < haois[i].size(); j++) {
				System.out.println(haois[i].get(j));
				if (haois[i].get(j) > prev) return false;
				prev = haois[i].get(j);
			}
		}
		return true;
	}

	public boolean equals(Object c) {
		 Haoi tempH = (Haoi) c;
		 return (this == tempH);
	}
	
	public HashSet<Haoi> nextState() {
		HashSet<Haoi> returnVal = new HashSet<>();
		
		for (int i = 0; i < haois.length - 1; i++) {
			if (haois[i].size() == 0) continue;
			for (int j = 0; j < haois.length - 1; j++) {
				if (j == i) continue;
				Stack<Integer>[] tempH = haois;
				tempH[j].add(tempH[i].pop());
				Haoi tempH2 = new Haoi(numRings, numTowers, tempH);
				System.out.println(tempH2.isLegal());
				if (tempH2.isLegal()) returnVal.add(tempH2);
//				returnVal.add(tempH2);
			}
		}
		
		return returnVal;
	}

	public Boolean isSolved() {
		return (haois[numTowers - 1].size() == numRings);
	}
	
	public ArrayList<Haoi> solve(int depth, HashSet<Haoi> previous, ArrayList<Haoi> soln) {
		
		
		if (this.isSolved() == true) {
			soln.add(this);
			return soln;
		}
		
		if (depth > 1000) {
			return null;
		}

		System.out.println(this.nextState() + "k");
		for (Haoi tempCS : this.nextState()) {
			System.out.println(tempCS + "j");
			if (!previous.contains(tempCS)) {
				previous.add(tempCS);
				ArrayList<Haoi> x = tempCS.solve(depth + 1, previous, soln);
				if (x != null) {
					soln.add(this);
					return soln;
				}
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		// TODO Auto-generated method stub
		int numTowersTemp = 3;
		int numRingsTemp = 4;
		Stack<Integer>[] temp = (Stack<Integer>[]) new Stack[numTowersTemp];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = new Stack<>();
		}
		temp[0].add(4);
		temp[0].add(3);
		temp[0].add(2);
		temp[0].add(1);
		Haoi tempH = new Haoi(numRingsTemp, numTowersTemp, temp);
		
		HashSet<Haoi> tempHS = new HashSet<>();
		tempHS.add(tempH);
		System.out.println(tempH.solve(0, tempHS, new ArrayList<Haoi>()));
		
	}
}
