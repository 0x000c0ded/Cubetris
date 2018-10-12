package sample;

public class PieceJ extends Piece {

	public PieceJ(){
		this.pieceType = "J";
		this.posX = 3;
		this.posY = 2;
		this.piecePlaced = false;
		this.createMatrix();
	}
	
	void createMatrix(){
		int m[][] = { {0, 4, 0}, {0, 4, 0}, {4, 4, 0} };
		this.matrix = new int[3][3];
		this.matrix = m;
	}

}