package structure;
public class Edge{
	
	private int from;
	private int to;
	private boolean used;
	
	Edge(int x, int y){
		this.from = x;
		this.to = y;
		this.used = false;
	}

	final int other(int v){
		if (this.from == v) return this.to; else return this.from;
	}
	
	//getter and setter

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}