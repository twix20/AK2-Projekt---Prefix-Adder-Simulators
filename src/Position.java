
public class Position{

	int x;
	int y;
	int index;
	int toConnect;
	
	public Position(int x, int y, int index, int toConnect){
		this.x = x;
		this.y = y;
		this.index = index;
		this.toConnect = toConnect;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getParent()
	{
		return toConnect;
	}
}
