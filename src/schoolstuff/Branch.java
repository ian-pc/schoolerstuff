package schoolstuff;

public class Branch <E>{
	E info;
	Branch right, left;
	boolean isLeaf;
	boolean isRoot = true;
	
	public Branch(E info) {
		this.info = info;
		isLeaf = true;
		isRoot = false;
	}
	
	public Branch(Branch right, Branch left) {
		this.info = info;
		this.right = right;
		this.left = left;
		isLeaf = false;
		this.right.isRoot = false;
		this.left.isRoot = false;
	}
	
	

}
