package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GraphicalArena {

    private AnimationTimer animator;
    public ImageView[][] graphicalMatrix;
    String idToPieceType[] = {"", "O", "I", "L", "J", "S", "Z", "T"};


    private int height = 26;
    private int width = 10;


    public GraphicalArena(Image tiles) {
        graphicalMatrix = new ImageView[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                graphicalMatrix[i][j] = new ImageView(tiles);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addGraphicalPieceToGraphicalArena(AnchorPane mainLayout, Piece piece, GraphicalPiece shape) {
        for (int y = 0; y < piece.getMatrix().length; y++) {
            for (int x = 0; x < piece.getMatrix()[y].length; x++) {
                if (piece.getMatrix()[y][x] != 0 && y + piece.posY >= 4) {
                    graphicalMatrix[piece.posY + y][piece.posX + x].setViewport(shape.viewportRect);
                    graphicalMatrix[piece.posY + y][piece.posX + x].setFitHeight(Sprite.getCubeSize());
                    graphicalMatrix[piece.posY + y][piece.posX + x].setFitWidth(Sprite.getCubeSize());
                    graphicalMatrix[piece.posY + y][piece.posX + x].setX(InGameWindow.arenaXLayout + Sprite.getCubeSize() * (piece.getPosX() + x));
                    graphicalMatrix[piece.posY + y][piece.posX + x].setY(InGameWindow.arenaYLayout + Sprite.getCubeSize() * (y + piece.getPosY() - 4));

                    mainLayout.getChildren().remove(graphicalMatrix[piece.posY + y][piece.posX + x]);
                    mainLayout.getChildren().add(graphicalMatrix[piece.posY + y][piece.posX + x]);
                }
            }
        }
    }

    public void updateGraphicalArena(AnchorPane mainLayout, Arena arena) {
        for (int i = 4; i < arena.getMatrix().length; i++) {
            for (int j = 0; j < arena.getMatrix()[i].length; j++) {
                mainLayout.getChildren().remove(graphicalMatrix[i][j]);
                if (arena.getMatrixValueAt(i, j) != 0) {
                    graphicalMatrix[i][j].setViewport(Sprite.getCubeViewPort(idToPieceType[arena.getMatrixValueAt(i, j)]));
                    graphicalMatrix[i][j].setFitHeight(Sprite.getCubeSize());
                    graphicalMatrix[i][j].setFitWidth(Sprite.getCubeSize());
                    graphicalMatrix[i][j].setX(InGameWindow.arenaXLayout + j * Sprite.getCubeSize());
                    graphicalMatrix[i][j].setY(InGameWindow.arenaYLayout + (i - 4) * Sprite.getCubeSize());
                    mainLayout.getChildren().add(graphicalMatrix[i][j]);
                }
            }
        }
    }

    public void animateDestruction(AnchorPane mainLayout, Arena arena, int spriteId){
        int completedLines[] = arena.getCompletedLines();
        for (int i = 0; i < completedLines.length; i++) {
            if (completedLines[i] == -1) continue;
            else
            {
                for (int j = 0; j < arena.getMatrix()[completedLines[i]].length; j++) {
                    mainLayout.getChildren().remove(graphicalMatrix[completedLines[i]][j]);
                    if (arena.getMatrix()[completedLines[i]][j] != 0){
                        graphicalMatrix[completedLines[i]][j].setViewport(Sprite.getSpriteViewPort(spriteId));
                        graphicalMatrix[completedLines[i]][j].setFitHeight(Sprite.getSpriteSize());
                        graphicalMatrix[completedLines[i]][j].setFitWidth(Sprite.getSpriteSize());
                        graphicalMatrix[completedLines[i]][j].setX(InGameWindow.arenaXLayout + j * Sprite.getSpriteSize());
                        graphicalMatrix[completedLines[i]][j].setY(InGameWindow.arenaYLayout + (completedLines[i] - 4) * Sprite.getSpriteSize());
                        mainLayout.getChildren().add(graphicalMatrix[completedLines[i]][j]);
                    }
                }
            }
        }

    }
}