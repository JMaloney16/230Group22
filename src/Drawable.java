public abstract class Drawable {
  private int xCoord;
  private int yCoord;
  private String sprite;
  
  public Drawable(int x, int y, String sprite) {
	  this.xCoord = x;
	  this.yCoord = y;
	  this.sprite = sprite;
  }
}
