package sample;

public class PieceI extends Piece {
	
	public PieceI(){
		this.pieceType = "I";
		this.posX = 4;
		this.posY = 1;
		this.piecePlaced = false;
		this.createMatrix();
	}
	
	void createMatrix(){
		int m[][] = { {0, 2, 0, 0}, {0, 2, 0, 0}, {0, 2, 0, 0}, {0, 2, 0, 0} };
		this.matrix = new int[4][4];
		this.matrix = m;
	}

}