package sample;

public class PieceT extends Piece {
	
	public PieceT(){
		this.pieceType = "T";
		this.posX = 4;
		this.posY = 3;
		this.piecePlaced = false;
		this.createMatrix();
	}

	void createMatrix(){
		int m[][] = { {7, 7, 7}, {0, 7, 0}, {0, 0, 0} };
		this.matrix = new int[3][3];
		this.matrix = m;
	}

}