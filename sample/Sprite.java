package sample;

import javafx.geometry.Rectangle2D;

public class Sprite {

    private static double cubeSizeInImage = 18;
    private static double cubeSize = 27;
    private static double spriteSizeInImage = 24;
    private static double spriteSize = 27;
    private static double pixelSpacing = 5;
    private static Rectangle2D cubeViewports[];
    private static Rectangle2D spriteViewports[];

    public static void makeViewPorts(){
        cubeViewports = new Rectangle2D[7];
        for (int i = 0; i < 7; i++){
            cubeViewports[i] = new Rectangle2D((cubeSizeInImage + pixelSpacing) * i, 0, cubeSizeInImage, cubeSizeInImage);
        }
        spriteViewports = new Rectangle2D[6];
        for (int i = 0; i < 6; i++){
            spriteViewports[i] = new Rectangle2D((spriteSizeInImage + pixelSpacing) * i, cubeSizeInImage + pixelSpacing, spriteSizeInImage, spriteSizeInImage);
        }
    }

    public static double getSpriteSize(){
        return spriteSize;
    }

    public static double getCubeSize(){
        return cubeSize;
    }

    public static Rectangle2D getSpriteViewPort(int id) {
        return spriteViewports[id];
    }

    public static Rectangle2D getCubeViewPort(String pieceType) {

        Rectangle2D viewport = new Rectangle2D(0, 0, 0, 0);

        switch (pieceType) {

            case "J":
                viewport = cubeViewports[0];
                break;

            case "T":
                viewport = cubeViewports[1];
                break;

            case "S":
                viewport = cubeViewports[2];
                break;

            case "Z":
                viewport = cubeViewports[3];
                break;

            case "O":
                viewport = cubeViewports[4];
                break;

            case "I":
                viewport = cubeViewports[5];
                break;

            case "L":
                viewport = cubeViewports[6];
                break;
        }
        return viewport;
    }

}