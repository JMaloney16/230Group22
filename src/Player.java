import java.util.ArrayList;

public class Player extends Movable {
	ArrayList<Integer> keys = new ArrayList<Integer>();
	int tokens = 0;
	boolean flippers = false;
	boolean boots = false;
	
	public Player(int x, int y, String sprite) {
		super(x, y, sprite, x, y);
	}
	
	public int update(Board board, int keyboardIn) {
		return 0;
	}
	
	public boolean removeKey(int key) {
		Integer removed = this.keys.remove(key);
		if (removed == null) {
			return false;
		}
		else {
			return true;
		}
	}
}
