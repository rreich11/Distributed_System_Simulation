package finalAssignment;

public class SlaveCounter {
	private int counter;
	
	public SlaveCounter(int counter ) {
		this.counter = counter;
	}

	public int getCount() {
		return counter;
	}

	public void setCount(int counter) {
		this.counter = counter;
	}
	
	public void addToCounter(int num) {
		this.counter += num;
	}
	
	public void removeFromCounter(int num) {
		this.counter -= num;
	}
}