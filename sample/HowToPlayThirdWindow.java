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

public class HowToPlayThirdWindow {

    private static boolean loadedComponents = false;
    private static AnimationTimer animator;
    private static FXMLLoader loader = new FXMLLoader();
    private static AnchorPane mainLayout;
    private static Runnable goNext, goPrevious;
    private static double titleXlayout = 167;
    private static Text title = new Text("HOW TO PLAY");
    private static Image optionsImage = new Image("images/options.png");
    private static ImageView options = new ImageView(optionsImage);
    private static Image rightArrowImage = new Image("images/arrow.png");
    private static ImageView rightArrow = new ImageView(rightArrowImage);
    private static Image previousArrowImage = new Image("images/previous.png");
    private static ImageView previousArrow = new ImageView(previousArrowImage);
    private static Image nextArrowImage = new Image("images/next.png");
    private static ImageView nextArrow = new ImageView(nextArrowImage);
    private static Label textLabel;

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
            textLabel = new Label("Many options are avaiable for you:\n" +
                    "     [+] Clicking the 'Restart' button will restart the game, you can also press\n" +
                    "         the 'R' keyboard button.\n" +
                    "     [+] Clicking the 'Pause' button will pause the game and show the pause menu,\n" +
                    "         you can also press the 'P' keyboard button.\n" +
                    "     [+] You can toggle the sound ON/OFF by clicking the sound icon.\n" +
                    "     [+] The icon which is left to the sound icon is used to enable/disable the\n" +
                    "         piece's shadow, you can also use the 'S' keyboard button.\n" +
                    "     [+] The remaining images mean: 'Joker', 3 jokers are offered to the player,\n" +
                    "         but can only be used if the score is higher than 1000, press 'J' to use a joker.\n" +
                    "     [!] But be careful! Using a joker will reduce the score by 1000 points.\n");
            textLabel.setFont(new Font("Century Gothic", 12));
            textLabel.setTextFill(Color.web("#ffffff"));
            textLabel.setLayoutX(165);
            textLabel.setLayoutY(100);
            textLabel.setOpacity(0);
            // <----------------------------------------------------------------------------------------------------> //
            // <---------------------------------- Adding text labels to children ----------------------------------> //
            mainLayout.getChildren().addAll(textLabel);
            // <----------------------------------------------------------------------------------------------------> //
            // <---------------------------------------------- Images ----------------------------------------------> //
            options.setFitHeight(125);
            options.setFitWidth(130);
            options.setLayoutX(20);
            options.setLayoutY(120);
            options.setOpacity(0);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------ Adding Images to children ------------------------------------> //
            mainLayout.getChildren().addAll(options);
            // <----------------------------------------------------------------------------------------------------> //
            // <-------------------------------------------- Runnables ---------------------------------------------> //
            goNext = new Runnable() {
                public void run() {
                    try {
                        HowToPlayFourthWindow.showHowToPlay();
                    } catch (IOException except) {
                    }
                }
            };

            goPrevious = new Runnable() {
                public void run() {
                    try {
                        HowToPlaySecondWindow.showHowToPlay();
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
                    options.setOpacity(Math.min(1, options.getOpacity() + (1.0 / 30.0)));

                    if (textLabel.getOpacity() > 0.9) animate = false;
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
                    options.setOpacity(Math.max(0, options.getOpacity() - (1.0 / 30.0)));
                    rightArrow.setOpacity(Math.max(0, rightArrow.getOpacity() - (1.0 / 30.0)));

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