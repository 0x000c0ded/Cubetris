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

public class HowToPlayFirstWindow {

    private static boolean loadedComponents = false;
    private static AnimationTimer animator;
    private static FXMLLoader loader = new FXMLLoader();
    private static AnchorPane mainLayout;
    private static double titleX1layout = 167;
    private static double titleXflayout = 800;
    private static Text title = new Text("HOW TO PLAY");
    private static Image keyboardImage = new Image("images/keyboard.png");
    private static ImageView keyboard = new ImageView(keyboardImage);
    private static Image previousArrowImage = new Image("images/previous.png");
    private static ImageView previousArrow = new ImageView(previousArrowImage);
    private static Image nextArrowImage = new Image("images/next.png");
    private static ImageView nextArrow = new ImageView(nextArrowImage);
    private static Label textLabel;

    public static void setTitleLayout(double f){
        title.setLayoutX(f);
    }

    public static void loadComponents() throws IOException {
        if (!loadedComponents) {
            // <------------------------------------ Including the FXML file ---------------------------------------> //
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
            title.setLayoutY(43);
            mainLayout.getChildren().add(title);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------------ The text label ------------------------------------------> //
            textLabel = new Label("Use the arrow keys to move the tetriminoes left and right, the up\n" +
                                  "  arrow to rotate the piece and the down arrow to speed up its\n" +
                                  "                                                      fall.");
            textLabel.setFont(new Font("Century Gothic", 14));
            textLabel.setTextFill(Color.web("#ffffff"));
            textLabel.setPrefWidth(436);
            textLabel.setPrefHeight(89);
            textLabel.setLayoutX(101);
            textLabel.setLayoutY(225);
            textLabel.setOpacity(0);
            mainLayout.getChildren().add(textLabel);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------------ Keyboard image ------------------------------------------> //
            keyboard.setFitHeight(150);
            keyboard.setFitWidth(150);
            keyboard.setLayoutX(247);
            keyboard.setLayoutY(75);
            keyboard.setOpacity(0);
            mainLayout.getChildren().add(keyboard);
            // <----------------------------------------------------------------------------------------------------> //

            // <-------------------------------------------- Next Arrow --------------------------------------------> //
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
            // <------------------------------------------ Previous Arrow ------------------------------------------> //
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
            nextArrow.setOnMouseClicked((MouseEvent e) -> animateNext());
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

            private double epsilon = 1;
            private boolean animate = true;

            public void handle(long arg0) {
                // <---------------------------------------- Animating title ---------------------------------------> //
                if (title.getLayoutX() < titleX1layout) {
                    title.setLayoutX(Math.min(titleX1layout, title.getLayoutX() + epsilon));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    if (title.getLayoutX() < 110) epsilon += 1;
                    else epsilon = Math.max(1, epsilon - 5);
                }
                // <------------------------------------------------------------------------------------------------> //

                // <---------------------------------------- Animating Fade ----------------------------------------> //
                else if (animate) {
                    textLabel.setOpacity(Math.min(1, textLabel.getOpacity() + (1.0/30.0)));
                    keyboard.setOpacity(Math.min(1, keyboard.getOpacity() + (1.0/30.0)));

                    if (textLabel.getOpacity() > 0.8) animate = false;
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

            private double epsilon = 1;
            private boolean animate = true;

            public void handle(long arg0) {
                // <---------------------------------------- Animating Fade ----------------------------------------> //
                if (animate) {
                    textLabel.setOpacity(Math.max(0, textLabel.getOpacity() - (1.0/30.0)));
                    keyboard.setOpacity(Math.max(0, keyboard.getOpacity() - (1.0/30.0)));
                    if (textLabel.getOpacity() == 0) animate = false;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                // <------------------------------------------------------------------------------------------------> //

                // <---------------------------------------- Animating title ---------------------------------------> //
                else if (title.getLayoutX() < titleXflayout) {
                    title.setLayoutX(Math.min(titleXflayout, title.getLayoutX() + epsilon));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    if (title.getLayoutX() < titleX1layout + 110) epsilon += 1;
                    else epsilon += 5;
                }
                // <------------------------------------------------------------------------------------------------> //
                else {
                    animator.stop();
                    try {
                        MainMenuWindow.showMainMenu();
                    } catch (Exception except) {
                        except.printStackTrace();
                    }
                }
            }
        };
        animator.start();
    }

    private static void animateNext() {
        animator.stop();
        animator = new AnimationTimer() {

            private boolean animate = true;

            public void handle(long arg0) {
                // <---------------------------------------- Animating Fade ----------------------------------------> //
                if (animate) {
                    textLabel.setOpacity(Math.max(0, textLabel.getOpacity() - (1.0/30.0)));
                    keyboard.setOpacity(Math.max(0, keyboard.getOpacity() - (1.0/30.0)));
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
                        HowToPlaySecondWindow.showHowToPlay();
                    } catch (Exception except) {
                        except.printStackTrace();
                    }
                }
            }
        };
        animator.start();
    }
}
