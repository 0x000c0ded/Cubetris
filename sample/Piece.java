package sample;

public abstract class Piece {

	public String pieceType;
	protected int posX; // coordonne X de la matrice qui contient La Piece
	protected int posY; // coordonne Y de la matrice qui contient la Piece
	protected int matrix[][]; // la matrice qui contient la Piece
	protected boolean piecePlaced; // Vrai si la piece a fini la descente

	public int getPosX(){
		return this.posX;
	}

	public int getPosY(){
		return this.posY;
	}

	public int[][] getMatrix(){
		return matrix;
	}

	public void setMatrix(int matrix[][]){
		this.matrix = matrix;
	}

	public void setPosX(int posX){
		this.posX = posX;
	}

	public void setPosY(int posY){
		this.posY = posY;
	}

	public boolean getPieceState()
	{
		return piecePlaced;
	}

	public void setPieceState(boolean state){
		this.piecePlaced = state;
	}

	abstract void createMatrix();


	public int[][] rotateMatrix(){  // retourne la matrice de la piece apr?s Rotation
		int height = this.matrix.length;
		int width = this.matrix[0].length;
		int RotatedMatrix[][] = new int[height][width];

		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				RotatedMatrix[j][width-i-1] = this.matrix[i][j];
			}
		}
		return RotatedMatrix;
	}

	public void rotatePiece(Arena arena){ // Si la rotation ne provoque pas de collision on fait la rotation
		int originalMatrix[][] = this.getMatrix();
		int rotatedMatrix[][] = this.rotateMatrix();

		this.setMatrix(rotatedMatrix);
		if (this.collision(arena)){
			this.setMatrix(originalMatrix);
		}
	}


	public boolean collision(Arena arena){
		for (int y = 0; y < this.matrix.length; y++){
			for (int x = 0; x < this.matrix[y].length; x++){
				if (this.matrix[y][x]!= 0){
					if ((y + this.posY) >= arena.getHeight() || (x + this.posX) >= arena.getWidth() || (x + this.posX) < 0){
						return true;
					}
					else{
						if (arena.getMatrixValueAt(y + this.posY, x + this.posX) != 0){
							return true ;
						}
					}
				}
			}
		}
		return false;
	}


	public void moveDown(Arena arena, Score widgets){
		this.posY++;
		if (this.collision(arena)){ // Collision ==> la Piece a fini la descente
			this.posY--;
			arena.addToArena(this);
			arena.checkSuccess();
			if (arena.succeeded){
				widgets.manageWidgets(arena);
				//arena.updateArena();
			}
			else {
				this.setPieceState(true);
			}
			arena.gameOver();
		}
	}

	public void moveRight(Arena arena){
		this.posX ++;
		if (this.collision(arena)){
			this.posX--;
		}
	}

	public void moveLeft(Arena arena){
		this.posX--;
		if (this.collision(arena)){
			this.posX++;
		}
	}
	
	public boolean ifCanBeGenerated(Arena arena){
		boolean canBeGenerated = true;
		int length = this.matrix.length - 1;
		int i = this.posX;
		int j = this.posX + this.matrix.length;
		while ( i < j && canBeGenerated){
			if (arena.getMatrix()[4][Math.min(i, 9)] != 0 && this.matrix[length][i - this.posX] != 0){
				canBeGenerated = false;
			}
			i++;
		}
		return canBeGenerated;
	}

}