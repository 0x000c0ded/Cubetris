package sample;

public class Arena {

	public boolean succeeded = false;
	private int width; // Nombre de cases en largeure
	private int height; // Nombre de cases en hauteur
	private int matrix[][]; // La matrix Arena
	private int completedLines[];
	public boolean resume;

	public Arena(int width ,int height){
		this.width = width;
		this.height = height;
		this.resume = true;
		this.matrix = new int[this.height][this.width];
		this.completedLines = new int[4];
		this.resetCompletedLines();
	}

	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	}

	public int[][] getMatrix(){
		return this.matrix;
	}

	public void setWidth(int newWidth){
		this.width = newWidth;
	}
	public void setHeight(int newHeight){
		height=newHeight;
	}

	public int getMatrixValueAt(int y , int x){
		return this.matrix[y][x];
	}

	public void setMatrixValueAt(int y , int x , int value){
		this.matrix[y][x] = value;
	}

	public int[] getCompletedLines(){
		return this.completedLines;
	}

	public void resetCompletedLines(){
		for (int i = 0; i < this.completedLines.length; i++){
			this.completedLines[i] = -1;
		}
	}

	public void editCompletedLines(int newCompletedLines[]) {
		this.completedLines = newCompletedLines ;

	}

	public void addToArena(Piece shape){

		for (int y = 0; y < shape.getMatrix().length; y++){
			for (int x = 0; x < shape.getMatrix()[y].length; x++){
				if (shape.getMatrix()[y][x] != 0){
					this.matrix[shape.posY + y][shape.posX + x] = shape.getMatrix()[y][x];
				}
			}
		}

	}

	public void checkSuccess(){

		int id = 0;
		int x;
		boolean success;

		for (int y = 4; y < this.matrix.length; y++){
			success = true;
			x = 0;
			while (success && x < this.matrix[y].length){
				if (this.matrix[y][x] == 0){
					success = false;
				}
				x++;
			}
			if (success){
				this.completedLines[id] = y;
				id++;
			}
		}

		if (Utils.len(this.completedLines)!=0) this.succeeded = true;

	}

	public void updateArena(){

		for (int i = 0; i < Utils.len(this.completedLines); i++){
			for (int y = this.completedLines[i]; y >= 4; y--){
				for (int x = 0; x < this.matrix[y].length; x++){
					this.matrix[y][x] = this.matrix[y - 1][x];
				}
			}
		}

	}

	public void gameOver(){
		for (int i = 0; i < this.width; i++){
			if ((this.matrix[4][i]!=0) && (this.matrix[3][i]!=0)){
				this.resume = false;
				break;
			}
		}
	}

}