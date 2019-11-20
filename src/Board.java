import java.util.ArrayList;

/**
 * Board.java
 * Stores and updates the playing board of the game
 * @version 0.1
 * @author ...
 */

public class Board {
  private Drawable[][] board;
  private ArrayList<Movable> movables = new ArrayList<Movable>();
  private ArrayList<Interactable> interactables = new ArrayList<Interactable>();

  public Board(Drawable[][] board, ArrayList<Movable> movables,
      ArrayList<Interactable> interactables) {
    this.board = board;
    this.movables = movables;
    this.interactables = interactables;
  }
  
  public void updateMovable(Player player, int keyboardIn) {
	  for (Movable m : this.movables) {
		  m.update(this, player, keyboardIn);
	  }
  }
  
  public void updateInteractables(Player player, int keyboardIn) {
	  for (Interactable i : this.interactables) {
		  i.update(this, player, keyboardIn);
	  }
  }
  
  public void removeMovable(Movable m) {
	  this.movables.remove(m);
  }
  
  public void removeInteractable(Interactable i) {
	  this.interactables.remove(i);
  }
  
  public void addMovable(Movable m) {
	  this.movables.add(m);
  }
  
  public void addInteractable(Interactable i) {
	  this.interactables.add(i);
  }
  
  public int getBlocking(int x, int y) {
//	  if (x < this.board.length && y < this.board[0].length) {
//		  return this.board[x][y].getBlocking();
//	  }
	  return 0;
  }
}
