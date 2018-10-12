package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class HowToPlayFourthWindow {

    private static boolean loadedComponents = false;
    private static AnimationTimer animator;
    private static FXMLLoader loader = new FXMLLoader();
    private static AnchorPane mainLayout;
    private static double titleXlayout = 167;
    private static Text title = new Text("HOW TO PLAY");
    private static Image arenaImage = new Image("images/example.jpg");
    private static ImageView arena = new ImageView(arenaImage);
    private static Image previousArrowImage = new Image("images/previous.png");
    private static ImageView previousArrow = new ImageView(previousArrowImage);
    private static Label textLabel;
    private static Rectangle rectangle;

    public static void loadComponents() throws IOException {
        if (!loadedComponents) {
            // <------------------------------------- Including the FXML file --------------------------------------> //
            loader.setLocation(Main.class.getResource("view/HowToPlay.fxml"));
            mainLayout = new AnchorPane();
            mainLayout = loader.load();
            loadedComponents = true;
            // <----------------------------------------------------------------------------------------------------> //
            // <-------------------------------------------- The title ---------------------------------------------> //
            title.setStyle("-fx-fill : #ffffff ;"
                    + "-fx-font-family : 'Perfect Dark (BRK)' ;"
                    + "-fx-text-alignment : center ;"
                    + "-fx-font-weight : bold ;"
                    + "-fx-font-size : 36 ;");
            title.setLayoutX(titleXlayout);
            title.setLayoutY(43);
            mainLayout.getChildren().add(title);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------------ The text label ------------------------------------------> //
            textLabel = new Label("The game is over when a collision happens on\n" +
                                  "                             the top line.");
            textLabel.setFont(new Font("Century Gothic", 14));
            textLabel.setTextFill(Color.web("#ffffff"));
            textLabel.setPrefWidth(330);
            textLabel.setPrefHeight(89);
            textLabel.setLayoutX(307);
            textLabel.setLayoutY(129);
            textLabel.setOpacity(0);
            // <----------------------------------------------------------------------------------------------------> //
            // <---------------------------------- Adding text label to children -----------------------------------> //
            mainLayout.getChildren().add(textLabel);
            // <----------------------------------------------------------------------------------------------------> //
            // <---------------------------------------------- Images ----------------------------------------------> //
            arena.setFitHeight(300);
            arena.setFitWidth(289);
            arena.setLayoutX(-1);
            arena.setLayoutY(47);
            arena.setOpacity(0);

            rectangle = new Rectangle();
            rectangle.setHeight(30);
            rectangle.setWidth(167);
            rectangle.setX(2);
            rectangle.setY(46);
            rectangle.setArcWidth(5);
            rectangle.setArcHeight(5);
            rectangle.setStroke(Color.RED);
            rectangle.setOpacity(0);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------ Adding Images to children -------------------------------------> //
            mainLayout.getChildren().addAll(arena, rectangle);
            // <----------------------------------------------------------------------------------------------------> //

            // <------------------------------------------ Previous Arrow ------------------------------------------> //
            previousArrow.setFitWidth(25);
            previousArrow.setFitHeight(25);
            previousArrow.setLayoutX(20);
            previousArrow.setLayoutY(360);
            previousArrow.setOpacity(0.4);
            previousArrow.setPickOnBounds(true);
            previousArrow.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
            previousArrow.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
            previousArrow.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            previousArrow.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            previousArrow.setOnMouseClicked((MouseEvent e) -> animatePrevious());
            // <----------------------------------------------------------------------------------------------------> //
            // <-------------------------------- Adding previous arrow to children ---------------------------------> //
            mainLayout.getChildren().addAll(previousArrow);
            // <----------------------------------------------------------------------------------------------------> //
        }
    }

    public static void showHowToPlay() throws IOException {

        Main.scene.setRoot(mainLayout);
        animateEntry();

    }


    private static void animateEntry() {
        animator = new AnimationTimer() {

            private boolean animate = true;

            public void handle(long arg0) {
                // <----------------------------------------- Animate Fade -----------------------------------------> //
                if (animate) {
                    textLabel.setOpacity(Math.min(1, textLabel.getOpacity() + (1.0/30.0)));
                    arena.setOpacity(Math.min(1, arena.getOpacity() + (1.0/30.0)));
                    rectangle.setOpacity(Math.min(1, rectangle.getOpacity() + (1.0/30.0)));

                    if (textLabel.getOpacity() > 0.6) animate = false;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                // <------------------------------------------------------------------------------------------------> //
            }
        };
        animator.start();
    }

    private static void animatePrevious() {
        animator.stop();
        animator = new AnimationTimer() {

            private boolean animate = true;

            public void handle(long arg0) {
                // <----------------------------------------- Animate Fade -----------------------------------------> //
                if (animate) {
                    textLabel.setOpacity(Math.max(0, textLabel.getOpacity() - (1.0/30.0)));
                    arena.setOpacity(Math.max(0, arena.getOpacity() - (1.0/30.0)));
                    rectangle.setOpacity(Math.max(0, rectangle.getOpacity() - (1.0/30.0)));

                    if (textLabel.getOpacity() == 0) animate = false;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                // <------------------------------------------------------------------------------------------------> //
                else {
                    animator.stop();
                    try {
                        HowToPlayThirdWindow.showHowToPlay();
                    } catch (Exception except) {
                        except.printStackTrace();
                    }
                }
            }
        };
        animator.start();
    }
}
