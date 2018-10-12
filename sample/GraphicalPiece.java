package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Rectangle2D;

public class GraphicalPiece {

    public double cubeSizeInSprite = 18;
    public int pixelSpacing = 5;
    public double opacity = 1;
    public Image tiles;
    public Rectangle2D viewportRect;
    public ImageView cube[] = new ImageView[4];

    public GraphicalPiece(){
        tiles = new Image("images/gameSprites.png");

        // <-- The piece cubes ( each piece represented with 4 cubes ) --> //
        for (int i = 0; i < 4; i++){
            cube[i] = new ImageView(tiles);
        }
        // <-------------------------------------------------------------> //
    }

    public GraphicalPiece(double opacity) {
        tiles = new Image("images/gameSprites.png");

        // <-- The piece cubes ( each piece represented with 4 cubes ) --> //
        for (int i = 0; i < 4; i++) {
            cube[i] = new ImageView(tiles);
        }
        // <-------------------------------------------------------------> //
        this.opacity = opacity;
    }

    public void setPieceType(String pieceType) {

        Sprite sprite = new Sprite();

        viewportRect = sprite.getCubeViewPort(pieceType);

        for (int i = 0; i < 4; i++) {
            cube[i].setViewport(viewportRect);
            cube[i].setFitHeight(Sprite.getCubeSize());
            cube[i].setFitWidth(Sprite.getCubeSize());
            cube[i].setOpacity(this.opacity);
        }

    }

    public void remove(AnchorPane mainLayout){
        for (int i = 0; i < 4; i++){
            mainLayout.getChildren().remove(cube[i]);
        }
    }

}