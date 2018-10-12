package sample;

public class PieceS extends Piece {

	public PieceS(){
		this.pieceType = "S";
		this.posX = 3;
		this.posY = 3;
		this.piecePlaced = false;
		this.createMatrix();
	}
	
	void createMatrix(){
		int m[][] = { {0, 5, 5}, {5, 5, 0}, {0, 0, 0} };
		this.matrix = new int[3][3];
		this.matrix = m;
	}

}