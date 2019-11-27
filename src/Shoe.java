
public class Shoe extends Collectable {
	private String type;

	public Shoe(int x, int y, String sprite, String type) {
		super(x, y, sprite, 0);
		this.type = type;
	}

}
