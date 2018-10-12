package sample;

public class PieceO extends Piece {
	
	public PieceO(){
		this.pieceType = "O";
		this.posX = 4;
		this.posY = 3;
		this.piecePlaced = false;
		this.createMatrix();
	}

	void createMatrix(){
		int m[][] = { {1, 1}, {1, 1} };
		this.matrix = new int[2][2];
		this.matrix = m;
	}

}