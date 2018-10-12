package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class CreditsWindow {

    private static boolean loadedComponents = false;
    private static AnimationTimer animator;
    private static FXMLLoader loader = new FXMLLoader();
    private static AnchorPane mainLayout;
    private static double titleX1layout = 217;
    private static double titleXflayout = 634;
    private static Text title = new Text("CREDITS");
    private static Image previousArrowImage = new Image("images/previous.png");
    private static ImageView previousArrow = new ImageView(previousArrowImage);
    private static Text textArray[];

    public static void loadComponents() throws IOException {
        if (!loadedComponents) {
            // <-------------------------------------- Including the FXML file -------------------------------------> //
            loader.setLocation(Main.class.getResource("view/credits.fxml"));
            mainLayout = new AnchorPane();
            mainLayout = loader.load();
            loadedComponents = true;
            // <-------------------------------------- Including the FXML file -------------------------------------> //
            // <------------------------------------------------ Title ---------------------------------------------> //
            title.setStyle("-fx-fill : #ffffff ;"
                    + "-fx-font-family : 'Perfect Dark (BRK)' ;"
                    + "-fx-text-alignment : center ;"
                    + "-fx-font-weight : bold ;"
                    + "-fx-font-size : 36 ;");
            title.setLayoutX(-320);
            title.setLayoutY(43);
            mainLayout.getChildren().add(title);
            // <-------------------------------------- Including the FXML file -------------------------------------> //
            // <------------------------------------------------ Text ----------------------------------------------> //
            textArray = new Text[11];
            textArray[0] = new Text("CUBETRIS");
            textArray[1] = new Text("is a remake of the well known game: Tetris.");
            textArray[2] = new Text("Game Developers:");
            textArray[3] = new Text("Songs:");
            textArray[4] = new Text("MITICHE Mehdi (Chief Project)");
            textArray[5] = new Text("BENREKIA Mohamed Ali");
            textArray[6] = new Text("BOUKAIS Sara Narimene");
            textArray[7] = new Text("YAHI Mohamed Seghir");
            textArray[8] = new Text("ZOUAHI Hafidh");
            textArray[9] = new Text("Main menu: Korobeiniki");
            textArray[10] = new Text("InGame: Katyusha");
            // <----------------------------------------------------------------------------------------------------> //
            // <-------------------------------- Text positionning and fonts config --------------------------------> //
            textArray[0].setStyle("-fx-fill : #b2adad ;"
                    + "-fx-font-family : 'Perfect Dark (BRK)' ;"
                    + "-fx-text-alignment : center ;"
                    + "-fx-font-weight : bold ;"
                    + "-fx-font-size : 18 ;");
            textArray[0].setLayoutX(93);
            textArray[0].setLayoutY(99);

            textArray[1].setStyle("-fx-fill : #ffffff ;"
                    + "-fx-font-family : 'Century Gothic' ;"
                    + "-fx-text-alignment : center ;"
                    + "-fx-font-weight : bold ;"
                    + "-fx-font-size : 16 ;");
            textArray[1].setLayoutX(211);
            textArray[1].setLayoutY(98);

            for (int k = 2; k < 4; k++) {
                textArray[k].setStyle("-fx-fill : #ffffff ;"
                        + "-fx-font-family : 'Century Gothic' ;"
                        + "-fx-text-alignment : center ;"
                        + "-fx-font-weight : bold ;"
                        + "-fx-font-size : 14 ;");
            }

            for (int k = 4; k < 11; k++) {
                textArray[k].setStyle("-fx-fill : #ffffff ;"
                        + "-fx-font-family : 'Century Gothic' ;"
                        + "-fx-text-alignment : center ;"
                        + "-fx-font-weight : bold ;"
                        + "-fx-font-size : 12 ;");
            }

            textArray[2].setLayoutX(100);
            textArray[2].setLayoutY(148);

            for (int k = 4; k < 9; k++) {
                textArray[k].setLayoutX(100);
                textArray[k].setLayoutY(170 + 15*(k-4));
            }

            textArray[3].setLayoutX(386);
            textArray[3].setLayoutY(148);

            textArray[9].setLayoutX(386);
            textArray[9].setLayoutY(169);

            textArray[10].setLayoutX(386);
            textArray[10].setLayoutY(208);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------ Setting the opacity to 0 --------------------------------------> //
            for (int k = 0; k < textArray.length; k++) {
                textArray[k].setOpacity(0);
                mainLayout.getChildren().add(textArray[k]);
            }
            // <----------------------------------------------------------------------------------------------------> //

            // <------------------------------------ Go to previous page arrow -------------------------------------> //
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
            mainLayout.getChildren().add(previousArrow);
            // <----------------------------------------------------------------------------------------------------> //
        }
    }

    public static void showCredits() throws IOException {

        title.setLayoutX(-320);
        Main.scene.setRoot(mainLayout);
        animateEntry();

    }


    private static void animateEntry() {
        animator = new AnimationTimer() {

            private double epsilon = 1;
            private boolean animate = true;
            private int i = 0;

            public void handle(long arg0) {
                // <---------------------------------------- Animating Title ---------------------------------------> //
                if (title.getLayoutX() < titleX1layout) {
                    title.setLayoutX(Math.min(titleX1layout, title.getLayoutX() + epsilon));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    if (title.getLayoutX() < 115) epsilon += 1;
                    else epsilon = Math.max(1, epsilon - 5);
                }
                // <------------------------------------------------------------------------------------------------> //

                // <---------------------------------------- Animating Fade ----------------------------------------> //
                else if (animate) {
                    for (i = 0; i < textArray.length; i++) {
                        textArray[i].setOpacity(Math.min(1, textArray[i].getOpacity() + (1.0/30.0)));
                    }
                    if (textArray[0].getOpacity() > 0.7) animate = false;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                // <------------------------------------------------------------------------------------------------> //
                else{
                    animator.stop();
                }
            }
        };
        animator.start();
    }

    private static void animatePrevious() {
        animator.stop();
        animator = new AnimationTimer() {

            private double epsilon = 1;
            private boolean animate = true;
            private int i = 0;

            public void handle(long arg0) {
                // <---------------------------------------- Animating Fade ----------------------------------------> //
                if (animate) {
                    for (i = 0; i < textArray.length; i++) {
                            textArray[i].setOpacity(Math.max(0, textArray[i].getOpacity() - (1.0/30.0)));
                        }
                    if (textArray[0].getOpacity() == 0) animate = false;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                // <------------------------------------------------------------------------------------------------> //

                // <---------------------------------------- Animating Title ---------------------------------------> //
                else if (title.getLayoutX() < titleXflayout) {
                    title.setLayoutX(Math.min(titleXflayout, title.getLayoutX() + epsilon));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    if (title.getLayoutX() < titleX1layout + 115) epsilon += 1;
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
}
