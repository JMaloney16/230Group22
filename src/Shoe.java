
public class Shoe extends Collectable {
	private String type;

	public Shoe(int x, int y, String sprite, int blockable, String type) {
		super(x, y, sprite, blockable);
		this.type = type;
	}

}
