public class StaticEntity extends Drawable{
  private int blockable;
  
  public StaticEntity(int x, int y, String sprite, int blockable) {
	  super(x, y, sprite);
	  this.blockable = blockable;
  }
}
