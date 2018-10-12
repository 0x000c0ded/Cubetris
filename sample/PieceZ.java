package sample;

public class PieceZ extends Piece {

	public PieceZ(){
		this.pieceType = "Z";
		this.posX = 3;
		this.posY = 3;
		this.piecePlaced = false;
		this.createMatrix();
	}
	
	void createMatrix(){
		int m[][] = { {6, 6, 0}, {0, 6, 6}, {0, 0, 0} };
		this.matrix = new int[3][3];
		this.matrix = m;
	}
	
}