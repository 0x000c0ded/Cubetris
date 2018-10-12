package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Rectangle2D;
import java.io.IOException;
import java.util.Random;

public class MainMenuWindow {

    public static double windowHeight = 434;
    public static double windowWidth = 650;
    private static AnimationTimer animator;
    private static boolean loadedComponents = false;
    private static double buttonXlayout = 221;
    private static int fallingCubeSize = 15;
    private static int fallingCubesNumber = 20;
    private static String[] cubeTypes = {"J", "I", "S", "Z", "O", "I", "L"};
    private static Image spritesImage = new Image("images/gameSprites.png");
    private static ImageView sprites[] = new ImageView[fallingCubesNumber];
    private static FXMLLoader loader = new FXMLLoader();
    public static AnchorPane mainLayout;
    private static Image logoImage = new Image("images/logo_symbol.png");
    private static ImageView logo = new ImageView(logoImage);
    private static Image logoTextImage = new Image("images/logo_text.png");
    private static ImageView logoText = new ImageView(logoTextImage);
    //private static Image frenchImage = new Image("application/Image/french.png");
    //private static ImageView french = new ImageView(frenchImage);
    //private static Image englishImage = new Image("application/Image/english.png");
    //private static ImageView english = new ImageView(englishImage);
    private static Image soundOnImage = new Image("images/soundON.png");
    private static ImageView soundOn = new ImageView(soundOnImage);
    private static Image soundOffImage = new Image("images/soundOFF.png");
    private static ImageView soundOff = new ImageView(soundOffImage);
    private static Rectangle2D viewPortRect;
    private static ColorAdjust colorAdjust;
    private static Button buttonStart;
    private static Button buttonHowToPlay;
    private static Button buttonLeaderboard;
    private static Button buttonCredits;
    private static Button buttonQuit;
    private static Runnable runGame, runHowToPlay, runLeaderBoard, runCredits, runQuit;

    public static void loadComponents() throws IOException {
        if (!loadedComponents) {
            // <------------------------------------- Including the FXML file -------------------------------------> //
            loader.setLocation(Main.class.getResource("view/MainView.fxml"));
            mainLayout = new AnchorPane();
            mainLayout = loader.load();
            for (int id = 0; id < sprites.length; id++){
                sprites[id] = new ImageView(spritesImage);
                sprites[id].setViewport(Sprite.getCubeViewPort(cubeTypes[id%cubeTypes.length]));
                sprites[id].setFitHeight(fallingCubeSize);
                sprites[id].setFitWidth(fallingCubeSize);
                sprites[id].setOpacity(0.1);
            }
            loadedComponents = true;
            // <----------------------------------------------------------------------------------------------------> //

            // <------------------------------------ Language buttons placement ------------------------------------> //
            /*french.setFitHeight(35);
            french.setFitWidth(35);
            french.setLayoutX(590);
            french.setLayoutY(15);
            french.setOpacity(0.3);

            english.setFitHeight(35);
            english.setFitWidth(35);
            english.setLayoutX(590);
            english.setLayoutY(15);
            english.setOpacity(0.3);*/
            // <----------------------------------------------------------------------------------------------------> //

            // <-------------------------------------- Sound on/off placement --------------------------------------> //
            soundOn.setFitHeight(35);
            soundOn.setFitWidth(35);
            soundOn.setLayoutY(20);
            soundOn.setLayoutX(20);
            soundOn.setOpacity(0.5);
            soundOn.setPickOnBounds(true);
            soundOn.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
            soundOn.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
            soundOn.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            soundOn.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            soundOn.setOnMouseClicked((MouseEvent e) ->  disableMainMenuMusic());

            soundOff.setFitHeight(35);
            soundOff.setFitWidth(35);
            soundOff.setLayoutY(20);
            soundOff.setLayoutX(20);
            soundOff.setOpacity(0.5);
            soundOff.setPickOnBounds(true);
            soundOff.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
            soundOff.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
            soundOff.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
            soundOff.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
            soundOff.setOnMouseClicked((MouseEvent e) -> enableMainMenuMusic());
            // <----------------------------------------------------------------------------------------------------> //

            // <------------------------------------------ Logo placement ------------------------------------------> //
            logo.setFitHeight(0.01);
            logo.setFitWidth(0.01);
            logo.setLayoutX(322);
            logo.setLayoutY(40);
            mainLayout.getChildren().add(logo);
            // <----------------------------------------------------------------------------------------------------> //

            // <---------------------------------------- Logo text placement ---------------------------------------> //
            colorAdjust = new ColorAdjust();
            viewPortRect = new Rectangle2D(0, 0, 0.01, 50);
            logoText.setViewport(viewPortRect);
            logoText.setFitHeight(20);
            logoText.setFitWidth(150);
            logoText.setLayoutX(246);
            logoText.setLayoutY(80);
            mainLayout.getChildren().add(logoText);
            // <----------------------------------------------------------------------------------------------------> //

            // <--------------------------------------------- Runnables --------------------------------------------> //
            runGame = new Runnable() {
                public void run() {
                    Main.mainMenuMusicPlayer.stop();
                    InGameWindow.showGame();
                }
            };
            runHowToPlay = new Runnable() {
                public void run() {
                    try {
                        HowToPlayFirstWindow.setTitleLayout(-320);
                        HowToPlayFirstWindow.showHowToPlay();
                    } catch (IOException e) {
                    }
                }
            };
            runLeaderBoard = new Runnable() {
                public void run() {
                    try {
                        LeaderBoardWindow.showLeaderBoard();
                    } catch (IOException e) {
                    }
                }
            };
            runCredits = new Runnable() {
                public void run() {
                    try {
                        CreditsWindow.showCredits();
                    } catch (IOException e) {
                    }
                }
            };
            runQuit = new Runnable() {
                public void run() {
                    Main.primaryStage.close();}
            };
            // <----------------------------------------------------------------------------------------------------> //
            // <--------------------------------------- Buttons configuration --------------------------------------> //
            buttonStart = new Button("Play");
            buttonStart.setLayoutX(650);
            buttonStart.setLayoutY(130);
            buttonStart.setPrefWidth(200);
            buttonStart.setPrefHeight(15);
            buttonStart.setStyle("-fx-background-color : #0e0f0f ;"
                    + "-fx-background-radius : 12 ;"
                    + "-fx-text-fill : white ;"
                    + "-fx-font-family : 'Century Gothic' ;"
                    + "-fx-font-size : 16 ;");

            buttonHowToPlay = new Button("How to play");
            buttonHowToPlay.setLayoutX(-250);
            buttonHowToPlay.setLayoutY(180);
            buttonHowToPlay.setPrefWidth(200);
            buttonHowToPlay.setPrefHeight(15);
            buttonHowToPlay.setStyle("-fx-background-color : #0e0f0f ;"
                    + "-fx-background-radius : 12 ;"
                    + "-fx-text-fill : white ;"
                    + "-fx-font-family : 'Century Gothic' ;"
                    + "-fx-font-size : 16 ;");

            buttonLeaderboard = new Button("Leaderboard");
            buttonLeaderboard.setLayoutX(650);
            buttonLeaderboard.setLayoutY(230);
            buttonLeaderboard.setPrefWidth(200);
            buttonLeaderboard.setPrefHeight(15);
            buttonLeaderboard.setStyle("-fx-background-color : #0e0f0f ;"
                    + "-fx-background-radius : 12 ;"
                    + "-fx-text-fill : white ;"
                    + "-fx-font-family : 'Century Gothic' ;"
                    + "-fx-font-size : 16 ;");

            buttonCredits = new Button("Credits");
            buttonCredits.setLayoutX(-250);
            buttonCredits.setLayoutY(280);
            buttonCredits.setPrefWidth(200);
            buttonCredits.setPrefHeight(15);
            buttonCredits.setStyle("-fx-background-color : #0e0f0f ;"
                    + "-fx-background-radius : 12 ;"
                    + "-fx-text-fill : white ;"
                    + "-fx-font-family : 'Century Gothic' ;"
                    + "-fx-font-size : 16 ;");

            buttonQuit = new Button("Quit");
            buttonQuit.setLayoutX(650);
            buttonQuit.setLayoutY(330);
            buttonQuit.setPrefWidth(200);
            buttonQuit.setPrefHeight(15);
            buttonQuit.setStyle("-fx-background-color : #0e0f0f ;"
                    + "-fx-background-radius : 12 ;"
                    + "-fx-text-fill : white ;"
                    + "-fx-font-family : 'Century Gothic' ;"
                    + "-fx-font-size : 16 ;");
            mainLayout.getChildren().addAll(
                    buttonStart, buttonHowToPlay, buttonLeaderboard, buttonCredits, buttonQuit
            );
            // <----------------------------------------------------------------------------------------------------> //
        }
    }

    public static void enterMainMenu() throws IOException {

        Main.scene.setRoot(mainLayout);
        animateEntry();

    }

    public static void showMainMenu() throws IOException {

        Main.scene.setRoot(mainLayout);
        animateAppear();
        enableButtons();

    }


    private static void animateEntry(){
        Main.mainMenuMusicPlayer.play();
        animator = new AnimationTimer() {

            private Button[] button = {buttonStart, buttonHowToPlay, buttonLeaderboard, buttonCredits, buttonQuit};
            private double epsilon = 18;
            private boolean animateText = true;
            private boolean animateLogo = true;
            private boolean animateFlash = true;
            private int i = 0;
            private int w = 8;
            private int f = 0;

            public void handle(long arg0) {
                // <--------------------------------------- Animating buttons --------------------------------------> //
                if (i < button.length){
                    if (i%2==0) button[i].setLayoutX(Math.max(buttonXlayout, button[i].getLayoutX() - epsilon));
                    else button[i].setLayoutX(Math.min(buttonXlayout, button[i].getLayoutX() + epsilon));
                    if (button[i].getLayoutX() == buttonXlayout) i++;
                    try{
                        Thread.sleep(10);
                    }catch(Exception e){
                    }
                }
                else if (animateLogo){
                    logo.setFitWidth(Math.min(logo.getFitWidth() + 2, 70));
                    logo.setFitHeight(Math.min(logo.getFitHeight() + 2, 70));
                    logo.setLayoutX(logo.getLayoutX() - 1);
                    logo.setLayoutY(logo.getLayoutY() - 1);
                    if (logo.getFitWidth() == 70) animateLogo = false;
                    try{
                        Thread.sleep(10);
                    }catch(Exception e){
                    }
                }
                else if (animateText){
                    viewPortRect = new Rectangle2D(0, 0, w, 50);
                    logoText.setViewport(viewPortRect);
                    w = Math.min(375, w + 8);
                    if (w == 375) animateText = false;
                    try{
                        Thread.sleep(20);
                    }catch(Exception e){
                    }
                }
                else if (animateFlash){
                    if (f%2==0) colorAdjust.setBrightness(50);
                    else colorAdjust.setBrightness(0);
                    logo.setEffect(colorAdjust);
                    logoText.setEffect(colorAdjust);
                    if (f > 6) animateFlash = false;
                    f++;
                    try{
                        Thread.sleep(120);
                    }catch(Exception e){
                    }
                }
                else{
                    // <-------------------------------- Configuring buttons ---------------------------------------> //
                    buttonStart.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
                    buttonStart.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonStart.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonStart.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
                    buttonStart.setOnAction(e -> animateFade(runGame));

                    buttonHowToPlay.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
                    buttonHowToPlay.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonHowToPlay.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonHowToPlay.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
                    buttonHowToPlay.setOnAction(e -> animateFade(runHowToPlay));

                    buttonLeaderboard.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
                    buttonLeaderboard.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonLeaderboard.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonLeaderboard.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
                    buttonLeaderboard.setOnAction(e -> animateFade(runLeaderBoard));

                    buttonCredits.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
                    buttonCredits.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonCredits.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonCredits.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
                    buttonCredits.setOnAction(e -> animateFade(runCredits));

                    buttonQuit.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
                    buttonQuit.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonQuit.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
                    buttonQuit.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
                    buttonQuit.setOnAction(e -> animateFade(runQuit));
                    // <--------------------------------------------------------------------------------------------> //

                    // <----------------------------- Adding language and sound button -----------------------------> //
                    //mainLayout.getChildren().add(english);
                    mainLayout.getChildren().add(soundOn);
                    // <--------------------------------------------------------------------------------------------> //

                    animator.stop();
                    animateFallingCubes();
                }
                // <------------------------------------------------------------------------------------------------> //
            }
        };
        animator.start();
    }

    private static void animateFade(Runnable show) {

        disableButtons();

        animator = new AnimationTimer() {

            private boolean animate = true;

            public void handle(long arg0) {
                // <---------------------------------------- Animating fade ----------------------------------------> //
                if (animate) {
                    logo.setOpacity(Math.max(0, logo.getOpacity() - (1.0 / 30.0)));
                    logoText.setOpacity(Math.max(0, logoText.getOpacity() - (1.0 / 30.0)));
                    buttonStart.setOpacity(Math.max(0, buttonStart.getOpacity() - (1.0 / 30.0)));
                    buttonHowToPlay.setOpacity(Math.max(0, buttonHowToPlay.getOpacity() - (1.0 / 30.0)));
                    buttonLeaderboard.setOpacity(Math.max(0, buttonLeaderboard.getOpacity() - (1.0 / 30.0)));
                    buttonCredits.setOpacity(Math.max(0, buttonCredits.getOpacity() - (1.0 / 30.0)));
                    buttonQuit.setOpacity(Math.max(0, buttonQuit.getOpacity() - (1.0 / 30.0)));
                    //english.setOpacity(Math.max(0, english.getOpacity() - (1.0/30.0)));
                    //french.setOpacity(Math.max(0, french.getOpacity() - (1.0/30.0)));
                    soundOn.setOpacity(Math.max(0, soundOn.getOpacity() - (1.0/30.0)));
                    soundOff.setOpacity(Math.max(0, soundOff.getOpacity() - (1.0/30.0)));

                    for (int i = 0; i < sprites.length; i++){
                        sprites[i].setOpacity(Math.max(0, sprites[i].getOpacity() - (0.1 / 7.5)));
                    }


                    if (logo.getOpacity() == 0) animate = false;
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

    private static void animateAppear() {
        animator = new AnimationTimer() {

            private boolean animate = true;

            public void handle(long arg0) {
                // <---------------------------------------- Animating fade ----------------------------------------> //
                if (animate) {
                    logo.setOpacity(Math.min(1, logo.getOpacity() + (1.0 / 30.0)));
                    logoText.setOpacity(Math.min(1, logoText.getOpacity() + (1.0 / 30.0)));
                    buttonStart.setOpacity(Math.min(1, buttonStart.getOpacity() + (1.0 / 30.0)));
                    buttonHowToPlay.setOpacity(Math.min(1, buttonHowToPlay.getOpacity() + (1.0 / 30.0)));
                    buttonLeaderboard.setOpacity(Math.min(1, buttonLeaderboard.getOpacity() + (1.0 / 30.0)));
                    buttonCredits.setOpacity(Math.min(1, buttonCredits.getOpacity() + (1.0 / 30.0)));
                    buttonQuit.setOpacity(Math.min(1, buttonQuit.getOpacity() + (1.0 / 30.0)));
                    //english.setOpacity(Math.min(0.3, english.getOpacity() + (1.0/30.0)));
                    //french.setOpacity(Math.min(0.3, french.getOpacity() + (1.0/30.0)));
                    soundOn.setOpacity(Math.min(0.5, soundOn.getOpacity() + (1.0/30.0)));
                    soundOff.setOpacity(Math.min(0.5, soundOff.getOpacity() + (1.0/30.0)));

                    for (int i = 0; i < sprites.length; i++){
                        sprites[i].setOpacity(Math.min(0.1, sprites[i].getOpacity() + (0.1 / 7.5)));
                    }

                    if (logo.getOpacity() == 1) animate = false;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                // <------------------------------------------------------------------------------------------------> //
                else {
                    animator.stop();
                }
            }
        };
        animator.start();
    }

    private static void animateFallingCubes(){
        animator = new AnimationTimer() {

            int[] yLayouts = shuffleInitialPositions();
            int i;
            public void handle(long arg0) {
                for (i = 0; i < sprites.length; i++){
                    if (sprites[i].getLayoutY() > 400) sprites[i].setLayoutY(-fallingCubeSize);
                    sprites[i].setLayoutY(sprites[i].getLayoutY() + 1.5);
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            }
        };
        animator.start();
    }

    private static int[] shuffleInitialPositions()
    {
        int array[] = new int[fallingCubesNumber];
        for (int i = 0; i < array.length; i++){
            array[i] = -i*40 - fallingCubeSize;
        }

        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
        for (int i = 0; i < array.length; i++){
            sprites[i].setLayoutY(array[i]);
            sprites[i].setLayoutX(25 + i*30);
            mainLayout.getChildren().add(sprites[i]);
        }
        return array;
    }

    public static void disableButtons(){
        buttonStart.setOnAction(null);
        buttonHowToPlay.setOnAction(null);
        buttonLeaderboard.setOnAction(null);
        buttonCredits.setOnAction(null);
        buttonQuit.setOnAction(null);
    }

    public static void enableButtons(){
        buttonStart.setOnAction(e -> animateFade(runGame));
        buttonHowToPlay.setOnAction(e -> animateFade(runHowToPlay));
        buttonLeaderboard.setOnAction(e -> animateFade(runLeaderBoard));
        buttonCredits.setOnAction(e -> animateFade(runCredits));
        buttonQuit.setOnAction(e -> animateFade(runQuit));
    }

    /*public static void setLanguageToFrench(){
        buttonStart.setText("Jouer");
        buttonHowToPlay.setText("Comment jouer");
        buttonLeaderboard.setText("Tableau des scores");
        buttonCredits.setText("Crédits");
        buttonQuit.setText("Quitter");
    }

    public static void setLanguageToEnglish(){
        buttonStart.setText("Play");
        buttonHowToPlay.setText("How to play");
        buttonLeaderboard.setText("Leaderboard");
        buttonCredits.setText("Credits");
        buttonQuit.setText("Quit");
    }*/

    public static void disableMainMenuMusic(){
        Main.mainMenuMusicPlayer.pause();
        mainLayout.getChildren().remove(soundOn);
        mainLayout.getChildren().add(soundOff);
    }

    public static void enableMainMenuMusic(){
        Main.mainMenuMusicPlayer.play();
        mainLayout.getChildren().remove(soundOff);
        mainLayout.getChildren().add(soundOn);
    }
}