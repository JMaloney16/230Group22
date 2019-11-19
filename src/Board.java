import java.util.ArrayList;

/**
 * Board.java
 * Stores and updates the playing board of the game
 * @version 0.1
 * @author ...
 */

public class Board {
  private Drawable[][] board;
  //private Movable[] movables = new ArrayList<Movable>;
  private ArrayList<Movable> movables = new ArrayList<Movable>();
  private ArrayList<Interactable> interactables = new ArrayList<Interactable>();

  public Board(Drawable[][] board, ArrayList<Movable> movables,
      ArrayList<Interactable> interactables) {
    this.board = board;
    this.movables = movables;
    this.interactables = interactables;
  }


}
