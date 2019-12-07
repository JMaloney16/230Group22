import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

/**
 * SmartEnemy.java, implements the smart enemy behaviour
 * 
 * @version 0.1
 * @author Ewan Bradford
 */
public class SmartEnemy extends Enemy {
	/**
	 * Holds all the data required to perform a star in one object for convenience
	 * 
	 * @version 0.1
	 * @author Ewan Bradford
	 */
	private class Cell {
		public int x;
		public int y;
		public int f;
		public int g;
		public int h;
		public boolean blocking;
		public Cell parent = null;

		public Cell(int x, int y, boolean blocking) {
			this.x = x;
			this.y = y;
			this.blocking = blocking;
		}
	}

	Cell[][] cells;
	ArrayList<Cell> path = new ArrayList<Cell>();

	/**
	 * Creates a new smart enemy using just a starting position
	 * 
	 * @param x, x coordinate of the enemy
	 * @param y, y coordinate of the enemy
	 */
	public SmartEnemy(int x, int y) {
		super(x, y, "assets\\FlyEye.png", 1, 0);
	}

	/**
	 * Returns the cell with the minimum f value in the arraylist
	 * 
	 * @param cs, cells to be searched
	 * @return the cell with the lowest f value
	 */
	private Cell getMin(ArrayList<Cell> cs) {
		Cell minF = cs.get(0);
		for (int i = 1; i < cs.size(); i++) {
			if (cs.get(i).f < minF.f) {
				minF = cs.get(i);
			}
		}
		return minF;
	}

	/**
	 * Gets a cell that occupies a position
	 * 
	 * @param cs, arraylist to be searched
	 * @param x,  x position of the search cell
	 * @param y,  y position of the search cell
	 * @return the cell at the x,y position, null otherwise
	 */
	private Cell getCell(ArrayList<Cell> cs, int x, int y) {
		Cell r = null;
		for (Cell s : cs) {
			if (s.x == x && s.y == y) {
				r = s;
			}
		}
		return r;
	}

	/**
	 * Perform the a star algorithm on the cell data
	 * 
	 * @param sx, start x coordinate
	 * @param sy, start y coordinate
	 * @param gx, goal x coordinate
	 * @param gy, goal y coordinate
	 */
	private void doAStar(int sx, int sy, int gx, int gy) {
		ArrayList<Cell> unsearched = new ArrayList<Cell>();
		ArrayList<Cell> searched = new ArrayList<Cell>();

		Cell start = cells[sx][sy];
		start.g = 0;
		start.h = Math.abs(start.x - gx) + Math.abs(start.y - gy);
		unsearched.add(start);

		while (unsearched.size() > 0) {
			Cell current = getMin(unsearched);
			if (current.x == gx && current.y == gy) {
				searched.add(current);
				break;
			}

			searched.add(current);
			for (int y = Math.max(current.y - 1, 0); y <= Math.min(current.y + 1, cells[0].length - 1); y++) {
				for (int x = Math.max(current.x - 1, 0); x <= Math.min(current.x + 1, cells.length - 1); x++) {
					if ((x > current.x || x < current.x) ^ (y > current.y || y < current.y)) {
						Cell neig = new Cell(cells[x][y].x, cells[x][y].y, cells[x][y].blocking);
						neig.g = cells[x][y].g;
						// println(x, y, getCell(searched, neig.x, neig.y));
						if (getCell(searched, neig.x, neig.y) == null && neig.blocking == false) {
							if (current.g + 1 < neig.g || neig.g == -1) {
								neig.parent = current;
								neig.g = current.g + 1;
								neig.h = Math.abs(neig.x - gx) + Math.abs(neig.y - gy);
								neig.f = neig.g + neig.h;

								unsearched.remove(getCell(unsearched, neig.x, neig.y));
								unsearched.add(neig);
							}
						}
					}
				}
			}
			unsearched.remove(current);
		}

		this.path = new ArrayList<Cell>();
		if (getCell(searched, gx, gy) != null) {
			Cell node = getCell(searched, gx, gy);
			while (node.parent != null) {
				path.add(node);
				node = node.parent;
			}
		}
	}

	/**
	 * Does one step of the dumb enemy's behaviour
	 */
	public void update(Board board, Player player, int keyboardIn) {
		this.cells = new Cell[board.getBoard().length][board.getBoard()[0].length];
		for (int y = 0; y < board.getBoard()[0].length; y++) {
			for (int x = 0; x < board.getBoard().length; x++) {
				this.cells[x][y] = new Cell(x, y, board.getBlocking(x, y) > 0);
				this.cells[x][y].g = -1;
			}
		}

		this.doAStar(this.xCoord, this.yCoord, player.xCoord, player.yCoord);
		if (this.path.size() > 0) {
			Cell nextMove = this.path.get(this.path.size() - 1);
			this.xCoord = nextMove.x;
			this.yCoord = nextMove.y;
		} else {
			Random random = new Random();
			this.setDir(random.nextInt(3));
			this.normaliseDir();
			if (getDirBlocking(board) == false) {
				this.move();
			}
		}

		if (this.xCoord == player.getxCoord() && this.yCoord == player.getyCoord()) {
			if (player.getKatanna()) {
				SoundEffect.playSound("assets\\Sounds\\Clang.wav");
				board.removeMovable(this);
				player.removeKatanna();
			} else {
				player.kill();
			}
		}
	}
}
