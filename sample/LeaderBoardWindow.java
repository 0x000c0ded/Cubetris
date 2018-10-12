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

public class LeaderBoardWindow {

    private static boolean loadedComponents = false;
    private static AnimationTimer animator;
    private static FXMLLoader loader = new FXMLLoader();
    private static AnchorPane mainLayout;
    private static double titleX1layout = 168;
    private static double titleXflayout = 634;
    private static Text leaderboard[][] = new Text[10][3];
    private static Text title = new Text("LEADERBOARD");
    private static Image goldImage = new Image("images/gold.png");
    private static ImageView gold = new ImageView(goldImage);
    private static Image silverImage = new Image("images/silver.png");
    private static ImageView silver = new ImageView(silverImage);
    private static Image bronzeImage = new Image("images/bronze.png");
    private static ImageView bronze = new ImageView(bronzeImage);
    private static Image previousArrowImage = new Image("images/previous.png");
    private static ImageView previousArrow = new ImageView(previousArrowImage);

    public static void loadComponents() throws IOException {
        if (!loadedComponents) {
            // <-------------------------------------- Including the FXML file -------------------------------------> //
            loader.setLocation(Main.class.getResource("view/leaderboard.fxml"));
            mainLayout = new AnchorPane();
            mainLayout = loader.load();

            loadedComponents = true;
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------- Title Text positioning and font config -----------------------------> //
            title.setStyle("-fx-fill : white ;"
                    + "-fx-font-family : 'Perfect Dark (BRK)' ;"
                    + "-fx-text-alignment : center ;"
                    + "-fx-font-weight : bold ;"
                    + "-fx-font-size : 36 ;");
            title.setLayoutY(43);
            // <----------------------------------------------------------------------------------------------------> //
            // <------------------------------------------- Previous Arrow -----------------------------------------> //
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
            mainLayout.getChildren().addAll(title, previousArrow);
        }
    }

    public static void showLeaderBoard() throws IOException {

        title.setLayoutX(-320);

        Main.scene.setRoot(mainLayout);

        // <--------------------------------- Loading and displaying the scoreboard --------------------------------> //
        try {
            String[][] data = GameData.parseGameData();
            int xLayouts[] = {60, 130, 575};
            int spacing = 20;
            int i = 0;
            int j;
            if (data.length > 0 && !mainLayout.getChildren().contains(gold)) {
                gold.setFitHeight(15);
                gold.setFitWidth(15);
                gold.setLayoutX(110);
                gold.setLayoutY(99);
                gold.setOpacity(0);
                mainLayout.getChildren().add(gold);
            }
            else if (mainLayout.getChildren().contains(gold)) gold.setOpacity(0);

            if (data.length > 1 && !mainLayout.getChildren().contains(silver)) {
                silver.setFitHeight(15);
                silver.setFitWidth(15);
                silver.setLayoutX(110);
                silver.setLayoutY(119);
                silver.setOpacity(0);
                mainLayout.getChildren().add(silver);
            }
            else if (mainLayout.getChildren().contains(silver)) silver.setOpacity(0);

            if (data.length > 2 && !mainLayout.getChildren().contains(bronze)) {
                bronze.setFitHeight(15);
                bronze.setFitWidth(15);
                bronze.setLayoutX(110);
                bronze.setLayoutY(139);
                bronze.setOpacity(0);
                mainLayout.getChildren().add(bronze);
            }
            else if (mainLayout.getChildren().contains(bronze)) bronze.setOpacity(0);

            while (i < data.length && data[i][0] != null && data[i][1] != null) {
                leaderboard[i][0] = new Text(Integer.toString(i + 1));
                leaderboard[i][1] = new Text(data[i][0]);
                leaderboard[i][2] = new Text(data[i][1]);
                for (j = 0; j < 3; j++) {
                    leaderboard[i][j].setStyle("-fx-fill : white ;"
                            + "-fx-font-family : 'Century Gothic' ;"
                            + "-fx-text-alignment : center ;"
                            + "-fx-font-weight : bold ;"
                            + "-fx-font-size : 12 ;");
                    if (j == 0)
                        leaderboard[i][j].setLayoutX(xLayouts[j] - (Integer.toString(i + 1).length() - 1) * 3.2);
                    else if (j == 2) leaderboard[i][j].setLayoutX(xLayouts[j] - (data[i][1].length() - 1) * 3.2);
                    else leaderboard[i][j].setLayoutX(xLayouts[j]);
                    leaderboard[i][j].setLayoutY(110 + spacing * i);
                    leaderboard[i][j].setOpacity(0);
                    if (!mainLayout.getChildren().contains(leaderboard[i][j])) mainLayout.getChildren().add(leaderboard[i][j]);
                }
                i++;
            }
            while (i < data.length){
                for (j = 0; j < 3; j++){
                    if (mainLayout.getChildren().contains(leaderboard[i])) mainLayout.getChildren().remove(leaderboard[i][j]);
                }
                i++;
            }
        } catch (Exception e) {
        }
        // <--------------------------------------------------------------------------------------------------------> //
        animateEntry();
    }


    private static void animateEntry() {

        animator = new AnimationTimer() {

            private double epsilon = 1;
            private boolean animate = true;
            private int length = GameData.getScoreboardLength();
            private int i = 0;
            private int j;

            public void handle(long arg0) {
                // <----------------------------------------- Animating Title --------------------------------------> //
                if (title.getLayoutX() < titleX1layout) {
                    title.setLayoutX(Math.min(titleX1layout, title.getLayoutX() + epsilon));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    if (title.getLayoutX() < 70) epsilon += 1;
                    else epsilon = Math.max(1, epsilon - 5);
                }
                // <------------------------------------------------------------------------------------------------> //

                // <----------------------------------------- Animating Fade ---------------------------------------> //
                else if (animate && length != 0) {
                    for (i = 0; i < length; i++) {
                        for (j = 0; j < 3; j++) {
                            leaderboard[i][j].setOpacity(Math.min(1, leaderboard[i][j].getOpacity() + (10 - (double) i) / 300));
                        }
                        if (i == 0) gold.setOpacity(Math.min(0.5, gold.getOpacity() + (1.0 / 30.0)));
                        else if (i == 1) silver.setOpacity(Math.min(0.5, silver.getOpacity() + (1.0 / 30.0)));
                        else if (i == 2) bronze.setOpacity(Math.min(0.5, bronze.getOpacity() + (1.0 / 30.0)));
                    }
                    if (leaderboard[length - 1][0].getOpacity() == 1) animate = false;
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
            private int length = GameData.getScoreboardLength();
            private int i = 0;
            private int j;

            public void handle(long arg0) {
                // <----------------------------------------- Animating Fade ---------------------------------------> //
                if (animate && length != 0) {
                    for (i = 0; i < length; i++) {
                        for (j = 0; j < 3; j++) {
                            leaderboard[i][j].setOpacity(Math.max(0, leaderboard[i][j].getOpacity() - ((double) i + 5) / 300));
                        }
                        if (i == 0) gold.setOpacity(Math.max(0, gold.getOpacity() - (1.0 / 60.0)));
                        else if (i == 1) silver.setOpacity(Math.max(0, silver.getOpacity() - (1.0 / 60.0)));
                        else if (i == 2) bronze.setOpacity(Math.max(0, bronze.getOpacity() - (1.0 / 60.0)));
                    }
                    if (leaderboard[0][0].getOpacity() == 0) animate = false;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                // <------------------------------------------------------------------------------------------------> //

                // <----------------------------------------- Animating Title --------------------------------------> //
                else if (title.getLayoutX() < titleXflayout) {
                    title.setLayoutX(Math.min(titleXflayout, title.getLayoutX() + epsilon));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    if (title.getLayoutX() < titleX1layout + 70) epsilon += 1;
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
