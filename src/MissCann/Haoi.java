package MissCann;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Haoi {
	public Stack<Integer>[] haois; // an array of towers
	public int numTowers, numRings;

	public Haoi(int numRings, int numTowers, Stack<Integer>[] haois) {
		this.numTowers = numTowers;
		this.numRings = numRings;
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
		for (int i = 0; i < haois.length; i++) {
			for (int j = 1; j < haois[i].size(); j++) {
				if (haois[i].get(j) > haois[i].get(j - 1))
					return false;
			}
		}

		return true;
	}

	public boolean equals(Object c) {
		Haoi tempH = (Haoi) c;
		return (this.haois.equals(tempH.haois));
	}

	public Set<Haoi> nextState() {
		HashSet<Haoi> returnVal = new HashSet<>();
		
		for (int i = 0; i < haois.length - 1; i++) {
			if (haois[i].isEmpty()) continue;
			for (int j = 0; j < haois.length; j++) {
				if (j == i) continue;
				if (haois[i].isEmpty()) continue;
				Stack<Integer>[] tempH = haois;
				System.out.println(i + " " + j + " " + tempH[0] + " " + tempH[1] + " " + tempH[2]);
//				System.out.println(tempH[i].isEmpty());
				tempH[j].add(tempH[i].pop());
				Haoi tempH2 = new Haoi(numRings, numTowers, tempH);
				if (tempH2.isLegal()) returnVal.add(tempH2);
				System.out.println(i + " " + j + " " + tempH[0] + " " + tempH[1] + " " + tempH[2]);
			}
		}
		
		return returnVal;
	}

	public Boolean isSolved() {
		return (haois[haois.length - 1].size() == numRings);
	}

	public ArrayList<Haoi> solve(int depth, HashSet<Haoi> previous, ArrayList<Haoi> soln) {

		if (this.isSolved() == true) {
			soln.add(this);
			return soln;
		}

		if (depth > 1000) {
			return null;
		}

		for (Haoi tempCS : this.nextState()) {
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
//		System.out.println(tempH.isSolved());
		System.out.println(tempH.solve(0, tempHS, new ArrayList<Haoi>()));

	}
}
