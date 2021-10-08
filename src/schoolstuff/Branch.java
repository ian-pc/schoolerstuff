package schoolstuff;

public class Branch <E>{
	E info;
	Branch right, left;
	boolean isLeaf;
	
	public Branch(E info) {
		this.info = info;
		isLeaf = true;
	}
	
	public Branch(Branch right, Branch left) {
		this.info = info;
		this.right = right;
		this.left = left;
		isLeaf = false;
	}
	
	public E getInfo() {
		return this.info;
	}
	

}
