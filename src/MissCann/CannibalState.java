package MissCann;

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

		if (mLeft > cLeft || mRight > mLeft)
			return false;

		return true;
	}

	public boolean isSolved() {
		if (mLeft == 0 && cLeft == 0)
			return true;
		return false;
	}

	public int HashCode(int cLeft, int cRight, int mLeft, int mRight, Boolean boat) {
		int val = 1000000000;
		val += (boat ? 0 : 100000000);
		val += cLeft * 1000000;
		val += cRight * 10000;
		val += mLeft * 100;
		val += mRight;

		return val;
	}

	public boolean equals(CannibalState temp) {
		return (temp.boat == boat && temp.mLeft == mLeft && temp.mRight == mRight && temp.cLeft == cLeft
				&& temp.cRight == cRight);
	}

	public HashSet<CannibalState> nextState() {

		HashSet<CannibalState> returnVal = new HashSet<>();

		for (int i = 0; i < (!boat ? mLeft : mRight); i++) {
			for (int j = 0; j < (!boat ? cLeft : cRight); j++) {
				CannibalState tempNext;
				
				if (i + j > 2) continue;
				
				if (!boat) {
					tempNext = new CannibalState(mLeft - i, mRight + i, cLeft - i, cRight + i, boat);
				} else {
					tempNext = new CannibalState(mLeft + i, mRight - i, cLeft + i, cRight - i, !boat);
				}
				
				if (tempNext.isLegal()) returnVal.add(tempNext);
					
			}
		}
		return returnVal;
	}

}
