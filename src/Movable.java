public abstract class Movable extends Drawable{
  private int nextX;
  private int nextY;
  
  public Movable(int x, int y, String sprite, int nextX, int nextY) {
	  super(x, y, sprite);
	  this.nextX = nextX;
	  this.nextY = nextY;
  }
  
  public void update(Board board, Player player, int keyboardIn) {
	  
  }
}
