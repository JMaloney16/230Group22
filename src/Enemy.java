public abstract class Enemy extends Movable {
	private int dir;
	public Enemy(int x, int y, String sprite, int blockable, int direction) {
		super(x, y, sprite, blockable);
		this.dir = direction;
	}
	
	protected boolean getDirBlocking(Board board) {
		return this.getDirBlocking(this.dir, board);
	}

	protected boolean getDirBlocking(int dir, Board board) {
		dir = normaliseDir(dir);
		if (this.xCoord < 1 || this.xCoord > board.getBoard().length - 1 || this.yCoord < 1 || this.yCoord > board.getBoard()[0].length - 1) {
			return true;
		}
		
		switch (dir) {
		case 0:
			return board.getBlocking(this.xCoord, this.yCoord - 1) > 0;
		case 1:
			return board.getBlocking(this.xCoord + 1, this.yCoord) > 0;
		case 2:
			return board.getBlocking(this.xCoord, this.yCoord + 1) > 0;
		case 3:
			return board.getBlocking(this.xCoord - 1, this.yCoord) > 0;
		default:
			return true;
		}
	}
	
	protected void move() {
		switch (dir) {
		case 0:
			this.yCoord -= 1;
			break;
		case 1:
			this.xCoord += 1;
			break;
		case 2:
			this.yCoord += 1;
			break;
		case 3:
			this.xCoord -= 1;
			break;
		}
	}
	
	protected void changeDir(int delta) {
		this.dir += delta;
		normaliseDir();
	}
	
	protected void setDir(int dir) {
		this.dir = dir;
		normaliseDir();
	}

	protected int normaliseDir(int dir) {
		if (dir < 0) {
			return 3;
		}
		if (dir > 3) {
			return dir & 3;
		}
		return dir;
	}
	
	protected void normaliseDir() {
		this.dir = this.normaliseDir(this.dir);
	}
	
	protected int getDir() {
		return this.dir;
	}
}
