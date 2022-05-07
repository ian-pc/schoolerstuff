package MissCann;

import java.util.ArrayList;
import java.util.HashSet;

public class CannibalState {

	public int mLeft, mRight, cLeft, cRight;
	public Boolean boat; // false == left, true == right

	public CannibalState(int mLeft, int mRight, int cLeft, int cRight, Boolean boat) {
		this.mLeft = mLeft;
		this.mRight = mRight;
		this.cRight = cRight;
		this.cLeft = cLeft;
		this.boat = boat;
	}

	public boolean isLegal() {

		if (this.mLeft > this.cLeft || this.mRight > this.mLeft)
			return false;

		return true;
	}

	public boolean isSolved() {
		return (this.mLeft == 0 && this.cLeft == 0);
	}

	public int hashCode() {
		int val = 1000000000;
		val += (this.boat ? 100000000 : 0);
		val += this.cLeft * 1000000;
		val += this.cRight * 10000;
		val += this.mLeft * 100;
		val += this.mRight;
		return val;
	}

	public boolean equals(Object temp2) {
		CannibalState temp = (CannibalState) temp2;
		return (temp.boat == this.boat && temp.mLeft == this.mLeft && temp.mRight == this.mRight && temp.cLeft == this.cLeft
				&& temp.cRight == this.cRight);
		
	}

	public String toString() {
		return this.mLeft + " " + this.mRight + " " + this.cLeft + " " + this.cRight + " " + this.boat;
	}
	
//	public HashSet<CannibalState> nextState() {
//
//		HashSet<CannibalState> returnVal = new HashSet<>();
//
//		for (int i = 0; i < (!boat ? mLeft : mRight); i++) {
//			for (int j = 0; j < (!boat ? cLeft : cRight); j++) {
//				CannibalState tempNext;
//				
//				if (i + j > 2 || i + j == 0) continue;
//				
//				if (!boat) {
//					tempNext = new CannibalState(mLeft - i, mRight + i, cLeft - j, cRight + j, boat);
//				} else {
//					tempNext = new CannibalState(mLeft + i, mRight - i, cLeft + j, cRight - j, !boat);
//				}
//				
//				if (tempNext.isLegal()) returnVal.add(tempNext);
//					
//			}
//		}
//		return returnVal;
//	}
	
	public HashSet<CannibalState> nextState() {
		HashSet<CannibalState> nexts = new HashSet<CannibalState>();

		for (int nm = 0; nm <= Math.min(2, !this.boat ? this.mLeft : this.mRight); nm++) {
			for (int nc = (nm==0 ? 1 : 0); nc <= Math.min(2 - nm, !this.boat ? this.cLeft : this.cRight); nc++) {
			 
				CannibalState next;
				if (!this.boat)
					next = new CannibalState(this.mLeft-nm, this.cLeft - nc, this.mRight + nm, this.cRight + nc, true);
				else
					next = new CannibalState(this.mLeft+nm, this.cLeft + nc, this.mRight - nm, this.cRight - nc, false);
				if (next.isLegal())
					nexts.add(next);
			}
		}
		return nexts;	
	}

	public ArrayList<CannibalState> solve(int depth, HashSet<CannibalState> previous, ArrayList<CannibalState> soln) {
		
		
		if (this.isSolved() == true) {
			soln.add(this);
			return soln;
		}
		
		if (depth > 1000) {
			return null;
		}

		System.out.println(this.nextState());
		for (CannibalState tempCS : this.nextState()) {
			if (!previous.contains(tempCS)) {
				previous.add(tempCS);
				ArrayList<CannibalState> x = tempCS.solve(depth + 1, previous, soln);
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
		CannibalState cannibalS = new CannibalState(3, 0, 3, 0, false);
		
//		CannibalState cannibalS2 = new CannibalState(3, 0, 3, 0, false);
//		if (cannibalS.equals(cannibalS2)) System.out.println("EY");
		
		HashSet<CannibalState> tempHS = new HashSet<>();
		tempHS.add(cannibalS);
		System.out.println(cannibalS.solve(0, tempHS, new ArrayList<CannibalState>()));
	}
	
}
