package MissCann;

public class CannibalState {
	
	public int mLeft, mRight, cLeft, cRight;
	public Boolean boat;
	
	public CannibalState(int mLeft, int mRight, int cLeft, int cRight, Boolean boat) {
		this.mLeft = mLeft;
		this.mRight = mRight;
		this.cRight = cRight;
		this.cLeft = cLeft;
		this.boat = boat;
	}
	
	public boolean isLegal() {
		
		if (mLeft > cLeft || mRight > mLeft) return false;
		
		return true;
	}
	
	public HashSet<CannibalState> nextState() {
		
		for(int i = 0; i < 1) {
			for () {
				CannibalState next = new CannibalState();
				if (next.isLegal()) {
					next.add(next);
				}
			}
		}
		
		return next;
	}
	
}
