package GPSstue;

public class Location {
	int x, y;
	String name;

	public Location(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public String toString() {
		String out = "";
		out += this.x + " " + this.y + " " + this.name;
		return out;
	}
}