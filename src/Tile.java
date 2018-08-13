
public class Tile {
	
	 int x;
	 int y;
	int mineCount=0;
	boolean isMine;
	boolean disarmed=false;
	
	public Tile(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public int addCount(int count) {
		
		mineCount += count;
		
		return mineCount;
	}
	public int getCount() {
		return mineCount;
	}
}
