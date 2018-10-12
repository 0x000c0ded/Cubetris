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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class HowToPlaySecondWindow {

    private static boolean loadedComponents = false;
    private static AnimationTimer animator;
    private static FXMLLoader loader = new FXMLLoader();
    private static AnchorPane mainLayout;
    private static Runnable goNext, goPrevious;
    private static double titleXlayout = 167;
    private static Text title = new Text("HOW TO PLAY");
    private static Image noExplosionImage = new Image("images/noExplosion.png");
    private static ImageView noExplosion = new ImageView(noExplosionImage);
    private static Image explosionImage = new Image("images/explosion.png");
    private static ImageView explosion = new ImageView(explosionImage);
    private static Image rightArrowImage = new Image("images/arrow.png");
    private static ImageView rightArrow = new ImageView(rightArrowImage);
    private static Image previousArrowImage = new Image("images/previous.png");
    private static ImageView previousArrow = new ImageView(previousArrowImage);
    private static Image nextArrowImage = new Image("images/next.png");
    private static ImageView nextArrow = new ImageView(nextArrowImage);
    private static Label textLabel;
    private static Label detailsLabel;

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
            textLabel = new Label("Complete lines to gain points and raise the game's difficulty level.");
            textLabel.setFont(new Font("Century Gothic", 14));
            textLabel.setTextFill(Color.web("#ffffff"));
            textLabel.setPrefWidth(450);
            textLabel.setPrefHeight(89);
            textLabel.setLayoutX(100);
            textLabel.setLayoutY(42);
            textLabel.setOpacity(0);
            // <----------------------------------------------------------------------------------------------------> //
            // <----------------------------------------- The details label ----------------------------------------> //
            detailsLabel = new Label("Destroying 1 line: 40 points.\n" +
                    "Destroying 2 lines in a row: 100 points.\n" +
                    "Destroying 3 lines in a row: 300 points.\n" +
                    "Destroying 4 lines in a row: 1200 points.\n");
            detailsLabel.setFont(new Font("Century Gothic", 12));
            detailsLabel.setTextFill(Color.web("#ffffff"));
            detailsLabel.setLayoutX(210);
            detailsLabel.setLayoutY(225);
            detailsLabel.setOpacity(0);
            // <----------------------------------------------------------------------------------------------------> //
            // <---------------------------------- Adding text labels to children ----------------------------------> //
            mainLayout.getChildren().addAll(textLabel, detailsLabel);
            // <----------------------------------------------------------------------------------------------------> //
            // <---------------------------------------------- Images ----------------------------------------------> //
            noExplosion.setFitHeight(80);
            noExplosion.setFitWidth(200);
            noExplosion.setLayoutX(75);
            noExplosion.setLayoutY(95);
            noExplosion.setOpacity(0);

            explosion.setFitHeight(80);
            explosion.setFitWidth(200);
            explosion.setLayoutX(360);
            explosion.setLayoutY(95);
            explosion.setOpacity(0);

            rightArrow.setFitHeight(35);
            rightArrow.setFitWidth(41);
            rightArrow.setLayoutX(301);
            rightArrow.setLayoutY(131);
            rightArrow.setOpacity(0);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------ Adding Images to children ------------------------------------> //
            mainLayout.getChildren().addAll(noExplosion, explosion, rightArrow);
            // <----------------------------------------------------------------------------------------------------> //
            // <-------------------------------------------- Runnables ---------------------------------------------> //
            goNext = new Runnable() {
                public void run() {
                    try {
                        HowToPlayThirdWindow.showHowToPlay();
                    } catch (IOException except) {
                    }
                }
            };

            goPrevious = new Runnable() {
                public void run() {
                    try {
                        HowToPlayFirstWindow.showHowToPlay();
                    } catch (IOException except) {
                    }

                }
            };
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
            previousArrow.setOnMouseClicked((MouseEvent e) -> animateFade(goPrevious));
            // <----------------------------------------------------------------------------------------------------> //
            // <-------------------------------------------- Next Arrow --------------------------------------------> //
            nextArrow.setFitWidth(25);
            nextArrow.setFitHeight(25);
            nextArrow.setLayoutX(600);
            nextArrow.setLayoutY(360);
            nextArrow.setOpacity(0.4);
            nextArrow.setPickOnBounds(true);
            nextArrow.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
            nextArrow.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
            nextArrow.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            nextArrow.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            nextArrow.setOnMouseClicked((MouseEvent e) -> animateFade(goNext));
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------ Adding arrows to children -------------------------------------> //
            mainLayout.getChildren().addAll(nextArrow, previousArrow);
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
                // <---------------------------------------- Animating Fade ----------------------------------------> //
                if (animate) {
                    textLabel.setOpacity(Math.min(1, textLabel.getOpacity() + (1.0 / 30.0)));
                    detailsLabel.setOpacity(Math.min(1, detailsLabel.getOpacity() + (1.0 / 30.0)));
                    noExplosion.setOpacity(Math.min(1, noExplosion.getOpacity() + (1.0 / 30.0)));
                    explosion.setOpacity(Math.min(1, explosion.getOpacity() + (1.0 / 30.0)));
                    rightArrow.setOpacity(Math.min(1, rightArrow.getOpacity() + (1.0 / 30.0)));

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

    private static void animateFade(Runnable show) {
        animator.stop();
        animator = new AnimationTimer() {

            private boolean animate = true;

            public void handle(long arg0) {
                // <---------------------------------------- Animating Fade ----------------------------------------> //
                if (animate) {
                    textLabel.setOpacity(Math.max(0, textLabel.getOpacity() - (1.0 / 30.0)));
                    detailsLabel.setOpacity(Math.max(0, detailsLabel.getOpacity() - (1.0 / 30.0)));
                    noExplosion.setOpacity(Math.max(0, noExplosion.getOpacity() - (1.0 / 30.0)));
                    explosion.setOpacity(Math.max(0, explosion.getOpacity() - (1.0 / 30.0)));
                    rightArrow.setOpacity(Math.max(0, noExplosion.getOpacity() - (1.0 / 30.0)));

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
                        show.run();
                    } catch (Exception except) {
                        except.printStackTrace();
                    }
                }
            }
        };
        animator.start();
    }
}