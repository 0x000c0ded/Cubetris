package sample;

public class PieceL extends Piece {

	public PieceL(){
		this.pieceType = "L";
		this.posX = 3;
		this.posY = 2;
		this.piecePlaced = false;
		this.createMatrix();
	}
	
	void createMatrix(){
		int m[][] = { {0, 3, 0}, {0, 3, 0}, {0, 3, 3} };
		this.matrix = new int[3][3];
		this.matrix = m;
	}

}