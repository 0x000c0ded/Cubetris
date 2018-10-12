package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class InGameWindow {
    public static AnimationTimer animator;
    public static double windowHeight = 630;
    public static double windowWidth = 474;
    public static double arenaHeight = 595.5;
    public static double arenaWidth = 271.5;
    public static double arenaXLayout = 20;
    public static double arenaYLayout = 20;
    public static Rectangle rectCoverArena;
    public static boolean loadedComponents = false;
    public static boolean allowedRotation = true;
    private static FXMLLoader loader;
    public static AnchorPane mainLayout;
    public static String explosionFile;
    public static AudioClip explosionSound;
    public static String gameOverSoundFile;
    public static AudioClip gameOverSound;
    public static Text scoreValueText;
    public static Text textScore;
    public static ImageView scoreBackground ;
    public static Text levelValueText;
    public static Text levelText;
    public static ImageView levelBackground;
    public static Text completedLinesValueText;
    public static Text completedLinesText;
    public static Text gameOverText;
    public static Text gamePausedText;
    public static Text successRegister;
    public static Text failureRegister;
    public static ImageView completedLinesBackground;
    public static Arena arena;
    public static Piece piece;
    public static Piece nextPiece;
    public static Score widgets;
    public static GraphicalArena arenaShape;
    public static GraphicalPiece expectedPiecePosition;
    public static GraphicalPiece currentPieceShape;
    public static GraphicalPiece nextPieceShape;
    public static String musicFile;
    public static Media gameMusic;
    public static MediaPlayer gameMusicPlayer;
    public static Button pauseButton;
    public static Button restartButton;
    public static Image logoImageFile;
    public static ImageView logoImage;
    private static Button buttonResume;
    private static Button buttonRestart;
    private static Button buttonBackToMainMenu;
    private static Button saveButton;
    public static TextField nicknameEntry;
    private static boolean gameIsPaused = false;
    private static boolean gameMusicIsPaused = false;
    private static Scene scene;
    public static boolean allowedJoker = true ;
    public static Joker joker ;
    public static boolean shadowEnabled;
    private static Image shadowImage = new Image("images/shadow.png");
    private static ImageView shadowConf = new ImageView(shadowImage);
    private static Image soundOnImage = new Image("images/soundON.png");
    private static ImageView soundOn = new ImageView(soundOnImage);
    private static Image soundOffImage = new Image("images/soundOFF.png");
    private static ImageView soundOff = new ImageView(soundOffImage);

    public static Font font;

    public static void loadComponents() throws IOException{
        if (!loadedComponents) {
            loadedComponents = true;
            // <---------------------------------------- Loading music files ---------------------------------------> //
            musicFile = "src/sounds/gamemusic.mp3";
            gameMusic = new Media(new File(musicFile).toURI().toString());
            gameMusicPlayer = new MediaPlayer(gameMusic);
            gameMusicPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    gameMusicPlayer.seek(Duration.ZERO);
                }
            });

            explosionFile = "src/sounds/explosion.mp3";
            explosionSound = new AudioClip(new File(explosionFile).toURI().toString());

            gameOverSoundFile = "src/sounds/gameover.mp3";
            gameOverSound = new AudioClip(new File(gameOverSoundFile).toURI().toString());
            // <----------------------------------------------------------------------------------------------------> //
            // <---------------------------------------- Text font -------------------------------------------------> //
            font = Font.font("Century Gothic", 17) ;
            // <----------------------------------------------------------------------------------------------------> //

        }
    }

    public static void showGame() {

        // <-------------------------------------- Including the FXML file -------------------------------------> //
        loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/inGame.fxml"));
        mainLayout = new AnchorPane();
        try{
            mainLayout = loader.load();
        }catch(IOException except){
        }
        scene = new Scene(mainLayout, windowWidth, windowHeight);
        // <--------------------------------------------------------------------------------------------------------> //

        // <------------------------------------------- Graphical Piece --------------------------------------------> //
        expectedPiecePosition = new GraphicalPiece(0.35);
        currentPieceShape = new GraphicalPiece();
        nextPieceShape = new GraphicalPiece();
        // <--------------------------------------------------------------------------------------------------------> //

        // <------------------------------------------- Graphical Arena --------------------------------------------> //
        arenaShape = new GraphicalArena(currentPieceShape.tiles);
        // <--------------------------------------------------------------------------------------------------------> //

        // <--------------------------------------- Game objects configuration -------------------------------------> //
        arena = new Arena(10, 26);

        widgets = new Score();
        joker = new Joker(340,500);

        nextPiece = generatePiece();
        nextPieceShape.setPieceType(nextPiece.pieceType);

        piece = generatePiece();
        currentPieceShape.setPieceType(piece.pieceType);

        expectedPiecePosition.setPieceType(piece.pieceType);
        // <--------------------------------------------------------------------------------------------------------> //

        // <-------------------------------------------- Logo Image ------------------------------------------------> //
        logoImageFile = new Image("images/logo_symbol.png");
        logoImage = new ImageView(logoImageFile);
        logoImage.setFitWidth(95);
        logoImage.setFitHeight(95);
        logoImage.setLayoutX(109);
        logoImage.setLayoutY(150);
        logoImage.setOpacity(0.5);
        // <--------------------------------------------------------------------------------------------------------> //

        // <----------------------------------------- Game Paused text ----------------------------------------------> //
        gamePausedText = new Text("GAME PAUSED");
        gamePausedText.setStyle("-fx-fill : white ;"
                + "-fx-font-family : 'Perfect Dark (BRK)' ;"
                + "-fx-text-alignment : center ;"
                + "-fx-font-weight : bold ;"
                + "-fx-font-size : 20 ;");
        gamePausedText.setLayoutY(265);
        gamePausedText.setLayoutX(72);
        gamePausedText.setOpacity(0.8);
        // <--------------------------------------------------------------------------------------------------------> //

        // <------------------------------------------ Game Over text ----------------------------------------------> //
        gameOverText = new Text("GAMEOVER");
        gameOverText.setStyle("-fx-fill : white ;"
                + "-fx-font-family : 'Perfect Dark (BRK)' ;"
                + "-fx-text-alignment : center ;"
                + "-fx-font-weight : bold ;"
                + "-fx-font-size : 20 ;");
        gameOverText.setLayoutY(265);
        gameOverText.setLayoutX(90);
        gameOverText.setOpacity(0.75);
        // <--------------------------------------------------------------------------------------------------------> //

        // <----------------------------------------- Success/Failure Text -----------------------------------------> //
        successRegister = new Text("Your nickname has been saved.");
        successRegister.setLayoutY(300);
        successRegister.setLayoutX(45);
        successRegister.setOpacity(0.7);
        successRegister.setStyle("-fx-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-text-alignment : center ;"
                + "-fx-font-weight : bold ;"
                + "-fx-font-size : 14 ;");

        failureRegister = new Text("No nickname was provided.");
        failureRegister.setLayoutY(300);
        failureRegister.setLayoutX(59);
        failureRegister.setOpacity(0.7);
        failureRegister.setStyle("-fx-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-text-alignment : center ;"
                + "-fx-font-weight : bold ;"
                + "-fx-font-size : 14 ;");
        // <--------------------------------------------------------------------------------------------------------> //

        // <-------------------------------------------- Nickname input --------------------------------------------> //
        nicknameEntry = new TextField();
        nicknameEntry.setPromptText("Your nickname...");
        nicknameEntry.setLayoutY(280);
        nicknameEntry.setLayoutX(65);
        nicknameEntry.setPrefWidth(115);
        nicknameEntry.setStyle("-fx-background-color : #151515 ;"
                + "-fx-background-radius : 12 ;"
                + "-fx-text-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-font-size : 12 ;");
        nicknameEntry.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (nicknameEntry.getText().length() > 48) {
                    String s = nicknameEntry.getText().substring(0, 48);
                    nicknameEntry.setText(s);
                }
            }
        });

        saveButton = new Button("Save!");
        saveButton.setLayoutY(279);
        saveButton.setLayoutX(182);
        saveButton.setStyle("-fx-background-color : #151515 ;"
                + "-fx-background-radius : 12 ;"
                + "-fx-text-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-font-size : 12 ;");
        saveButton.setPrefWidth(60);
        saveButton.setOnMousePressed(e -> scene.setCursor(Cursor.CLOSED_HAND));
        saveButton.setOnMouseReleased(e -> scene.setCursor(Cursor.HAND));
        saveButton.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        saveButton.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        saveButton.setOnAction(e -> manageUserInput());
        // <--------------------------------------------------------------------------------------------------------> //

        // <----------------------------------------- Pause/Restart button -----------------------------------------> //
        pauseButton = new Button("Pause");
        pauseButton.setLayoutX(370 + arenaXLayout);
        pauseButton.setLayoutY(420 + arenaYLayout);
        pauseButton.setStyle("-fx-background-color : #151515 ;"
                + "-fx-background-radius : 12 ;"
                + "-fx-text-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-font-size : 16 ;");
        pauseButton.setOnMousePressed(e -> scene.setCursor(Cursor.CLOSED_HAND));
        pauseButton.setOnMouseReleased(e -> scene.setCursor(Cursor.HAND));
        pauseButton.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        pauseButton.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        pauseButton.setOnAction(e -> pause());

        restartButton = new Button("Restart");
        restartButton.setLayoutX(290 + arenaXLayout);
        restartButton.setLayoutY(420 + arenaYLayout);
        restartButton.setStyle("-fx-background-color : #151515 ;"
                + "-fx-background-radius : 12 ;"
                + "-fx-text-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-font-size : 16 ;");
        restartButton.setOnMousePressed(e -> scene.setCursor(Cursor.CLOSED_HAND));
        restartButton.setOnMouseReleased(e -> scene.setCursor(Cursor.HAND));
        restartButton.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        restartButton.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        restartButton.setOnAction(e -> restart());
        // <--------------------------------------------------------------------------------------------------------> //

        // <--------------------------------------------- Sound ON/OFF ---------------------------------------------> //
        soundOn.setFitHeight(35);
        soundOn.setFitWidth(35);
        soundOn.setLayoutY(520 + arenaYLayout);
        soundOn.setLayoutX(375 + arenaXLayout);
        soundOn.setOpacity(0.5);
        soundOn.setPickOnBounds(true);
        soundOn.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
        soundOn.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
        soundOn.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        soundOn.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        soundOn.setOnMouseClicked((MouseEvent e) ->  disableInGameMusic());

        soundOff.setFitHeight(35);
        soundOff.setFitWidth(35);
        soundOff.setLayoutY(520 + arenaYLayout);
        soundOff.setLayoutX(375 + arenaXLayout);
        soundOff.setOpacity(0.5);
        soundOff.setPickOnBounds(true);
        soundOff.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
        soundOff.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
        soundOff.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        soundOff.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        soundOff.setOnMouseClicked((MouseEvent e) -> enableInGameMusic());
        // <--------------------------------------------------------------------------------------------------------> //

        // <------------------------------------------ Pause menu buttons ------------------------------------------> //
        buttonResume = new Button("Resume");
        buttonResume.setLayoutX(80);
        buttonResume.setLayoutY(280);
        buttonResume.setPrefWidth(150);
        buttonResume.setPrefHeight(11.25);
        buttonResume.setStyle("-fx-background-color : #151515 ;"
                + "-fx-background-radius : 12 ;"
                + "-fx-text-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-font-size : 16 ;");

        buttonRestart = new Button("Restart");
        buttonRestart.setLayoutX(80);
        buttonRestart.setLayoutY(320);
        buttonRestart.setPrefWidth(150);
        buttonRestart.setPrefHeight(11.25);
        buttonRestart.setStyle("-fx-background-color : #151515 ;"
                + "-fx-background-radius : 12 ;"
                + "-fx-text-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-font-size : 16 ;");

        buttonBackToMainMenu = new Button("Main menu");
        buttonBackToMainMenu.setLayoutX(80);
        buttonBackToMainMenu.setLayoutY(360);
        buttonBackToMainMenu.setPrefWidth(150);
        buttonBackToMainMenu.setPrefHeight(11.25);
        buttonBackToMainMenu.setStyle("-fx-background-color : #151515 ;"
                + "-fx-background-radius : 12 ;"
                + "-fx-text-fill : white ;"
                + "-fx-font-family : 'Century Gothic' ;"
                + "-fx-font-size : 16 ;");
        // <--------------------------------------------------------------------------------------------------------> //

        // <------------------------------------- Pause buttons configuration --------------------------------------> //
        buttonResume.setOnMousePressed(e -> scene.setCursor(Cursor.CLOSED_HAND));
        buttonResume.setOnMouseReleased(e -> scene.setCursor(Cursor.HAND));
        buttonResume.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        buttonResume.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        buttonResume.setOnAction(e -> resume());

        buttonRestart.setOnMousePressed(e -> scene.setCursor(Cursor.CLOSED_HAND));
        buttonRestart.setOnMouseReleased(e -> scene.setCursor(Cursor.HAND));
        buttonRestart.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        buttonRestart.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        buttonRestart.setOnAction(e -> restart());

        buttonBackToMainMenu.setOnMousePressed(e -> scene.setCursor(Cursor.CLOSED_HAND));
        buttonBackToMainMenu.setOnMouseReleased(e -> scene.setCursor(Cursor.HAND));
        buttonBackToMainMenu.setOnMouseEntered(e -> scene.setCursor(Cursor.HAND));
        buttonBackToMainMenu.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
        buttonBackToMainMenu.setOnAction(e -> {
            try {
                goMainMenu();
            } catch (IOException except) {
            }
        });
        // <--------------------------------------------------------------------------------------------------------> //

        // <------------------------------------------ Widgets rectangles ------------------------------------------> //
        Rectangle rectNewPiece = new Rectangle();
        rectNewPiece.setX(309 + arenaXLayout);
        rectNewPiece.setY(19.5 + arenaYLayout);
        rectNewPiece.setWidth(120);
        rectNewPiece.setHeight(120);
        rectNewPiece.setFill(Color.web("#ffffff"));
        rectNewPiece.setArcWidth(20);
        rectNewPiece.setArcHeight(20);
        rectNewPiece.setOpacity(0.7) ;


        Rectangle rectWidgets = new Rectangle();
        rectWidgets.setX(294 + arenaXLayout);
        rectWidgets.setY(169.5 + arenaYLayout);
        rectWidgets.setWidth(150);
        rectWidgets.setHeight(225);
        rectWidgets.setFill(Color.web("#ffffff"));
        rectWidgets.setArcWidth(20);
        rectWidgets.setArcHeight(20);
        rectWidgets.setOpacity(0.7);

        rectCoverArena = new Rectangle();
        rectCoverArena.setX(arenaXLayout);
        rectCoverArena.setY(arenaYLayout);
        rectCoverArena.setWidth(arenaWidth);
        rectCoverArena.setHeight(arenaHeight);
        rectCoverArena.setFill(Color.web("#101010"));
        rectCoverArena.setArcWidth(5);
        rectCoverArena.setArcHeight(5);
        rectCoverArena.setOpacity(1);
        // <--------------------------------------------------------------------------------------------------------> //

        // <-------------------------------------- Widgets background loading --------------------------------------> //
        Image img = new Image("images/space2.jpg") ;

        scoreBackground = new ImageView(img);
        scoreBackground.setOpacity(0.9);

        levelBackground = new ImageView(img);
        levelBackground.setOpacity(0.9);

        completedLinesBackground = new ImageView(img);
        completedLinesBackground.setOpacity(0.9);
        // <--------------------------------------------------------------------------------------------------------> //
        // <----------------------------------- Widgets background configuration -----------------------------------> //
        scoreBackground.setX(315 + arenaXLayout);
        scoreBackground.setY(199.5 + arenaYLayout);
        scoreBackground.setFitHeight(36);
        scoreBackground.setFitWidth(108);

        levelBackground.setX(315 + arenaXLayout);
        levelBackground.setY(267 + arenaYLayout);
        levelBackground.setFitHeight(36);
        levelBackground.setFitWidth(108);

        completedLinesBackground.setX(315 + arenaXLayout);
        completedLinesBackground.setY(334.5 + arenaYLayout);
        completedLinesBackground.setFitHeight(36);
        completedLinesBackground.setFitWidth(108);
        // <--------------------------------------------------------------------------------------------------------> //

        // <-------------------------------- Initialization of drawScore() objects ---------------------------------> //
        textScore = new Text("Score");
        levelText = new Text("Level");
        completedLinesText = new Text("Completed Lines");

        scoreValueText = new Text(String.valueOf(widgets.getScore()));
        levelValueText = new Text(String.valueOf(widgets.getActuelLevel() + 1));
        completedLinesValueText = new Text(String.valueOf(widgets.getNbLinceC()));
        // <--------------------------------------------------------------------------------------------------------> //

        // <-------------------------------------------- Text placement --------------------------------------------> //
        textScore.setX(346.5 + arenaXLayout);
        textScore.setY(192 + arenaYLayout);
        textScore.setFont(font);
        textScore.setFill(Color.BLACK);
        scoreValueText.setX(324 + arenaXLayout);
        scoreValueText.setY(226.5 + arenaYLayout);
        scoreValueText.setFont(Font.font("Verdana", FontWeight.BOLD, 21));
        scoreValueText.setFill(Color.WHITE);

        levelText.setX(348 + arenaXLayout);
        levelText.setY(259.5 + arenaYLayout);
        levelText.setFont(font);
        levelText.setFill(Color.BLACK);
        levelValueText.setX(324 + arenaXLayout);
        levelValueText.setY(292.5 + arenaYLayout);
        levelValueText.setFont(Font.font("Verdana", FontWeight.BOLD, 21));
        levelValueText.setFill(Color.WHITE);

        completedLinesText.setX(298 + arenaXLayout);
        completedLinesText.setY(327 + arenaYLayout);
        completedLinesText.setFont(font);
        completedLinesText.setFill(Color.BLACK);
        completedLinesValueText.setX(324 + arenaXLayout);
        completedLinesValueText.setY(360 + arenaYLayout);
        completedLinesValueText.setFont(Font.font("Verdana", FontWeight.BOLD, 21));
        completedLinesValueText.setFill(Color.WHITE);
        // <----------------------------------------------------------------------------------------------------> //

        // <-------------------------------------------- Shadow config ---------------------------------------------> //
        shadowEnabled = true;
        shadowConf.setLayoutX(330 + arenaXLayout);
        shadowConf.setLayoutY(520 + arenaYLayout);
        shadowConf.setFitHeight(35);
        shadowConf.setFitWidth(35);
        shadowConf.setOpacity(0.4);
        shadowConf.setOnMousePressed(e -> Main.scene.setCursor(Cursor.CLOSED_HAND));
        shadowConf.setOnMouseReleased(e -> Main.scene.setCursor(Cursor.HAND));
        shadowConf.setOnMouseEntered(e -> Main.scene.setCursor(Cursor.HAND));
        shadowConf.setOnMouseExited(e -> Main.scene.setCursor(Cursor.DEFAULT));
        shadowConf.setOnMouseClicked((MouseEvent e) -> manageShadow());
        // <--------------------------------------------------------------------------------------------------------> //

        mainLayout.getChildren().addAll(rectNewPiece, rectWidgets, scoreBackground, levelBackground, completedLinesBackground,
                joker, pauseButton, restartButton, soundOn, shadowConf);

        Main.primaryStage.setScene(scene);
        Main.primaryStage.centerOnScreen();
        Main.primaryStage.show();
        drawNextPiece();
        drawScore();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        piece.moveLeft(arena);
                        break;
                    case RIGHT:
                        piece.moveRight(arena);
                        break;
                    case DOWN:
                        if (!arena.succeeded) piece.moveDown(arena, widgets);
                        break;
                    case UP:
                        if (allowedRotation) {
                            piece.rotatePiece(arena);
                            allowedRotation = false;
                        }
                        break;
                    case P:
                        if (gameIsPaused && arena.resume) resume();
                        else pause();
                        break;
                    case R:
                        if (!gameIsPaused && arena.resume) restart();
                        break;
                    case ENTER:
                        if (mainLayout.getChildren().contains(nicknameEntry) && mainLayout.getChildren().contains(saveButton))
                            manageUserInput();
                        break;
                    case J :
                        if ( allowedJoker) {
                            joker.jokerActivated(widgets, arena);
                            allowedJoker = false ;
                        }
                        break;
                    case S:
                        manageShadow();
                        break;
                    default:
                        break;
                }

            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        allowedRotation = true;
                        break;
                    case J :
                        allowedJoker = true ;
                        break ;
                    default:
                        break;
                }
            }
        });
        gameMusicPlayer.play();
        animateGamePlay();
    }

    private static void animateGamePlay(){

        animator = new AnimationTimer() {

            long timeStart = System.currentTimeMillis();
            long IntervalTime = widgets.speed;
            long refreshTime = 80;
            long spriteTimeStart = 0;
            int spriteId = 0;
            boolean addedCurrentPieceShapeToArenaShape = false;

            public void handle(long arg0) {

                if (arena.resume) {

                    if (!arena.succeeded && System.currentTimeMillis() - timeStart > IntervalTime) {
                        timeStart = System.currentTimeMillis();
                        piece.moveDown(arena, widgets);
                    }
                    if (!arena.succeeded && piece.getPieceState()) {
                        arenaShape.addGraphicalPieceToGraphicalArena(mainLayout, piece, currentPieceShape);
                        currentPieceShape.remove(mainLayout);
                        expectedPiecePosition.remove(mainLayout);
                        piece = nextPiece ;
                        if (!piece.ifCanBeGenerated(arena)){
                            arena.resume = false;
                        }
                        currentPieceShape.setPieceType(piece.pieceType);
                        expectedPiecePosition.setPieceType(piece.pieceType);
                        nextPiece = generatePiece();
                        nextPieceShape.setPieceType(nextPiece.pieceType);
                        drawNextPiece();
                    }

                    if (arena.succeeded){
                        if (!addedCurrentPieceShapeToArenaShape){
                            if(!joker.jokerUsed) {
                                currentPieceShape.remove(mainLayout);
                                expectedPiecePosition.remove(mainLayout);
                                arenaShape.addGraphicalPieceToGraphicalArena(mainLayout, piece, currentPieceShape);
                            }
                            addedCurrentPieceShapeToArenaShape = true;
                            explosionSound.play();
                        }
                        if (System.currentTimeMillis() - spriteTimeStart > refreshTime) {
                            spriteTimeStart = System.currentTimeMillis();
                            if (spriteId < 6) {
                                arenaShape.animateDestruction(mainLayout, arena, spriteId);
                                spriteId++;
                            } else {
                                addedCurrentPieceShapeToArenaShape = false;
                                spriteTimeStart = 0;
                                spriteId = 0;
                                if (!joker.jokerUsed) {
                                    piece = nextPiece;
                                    nextPiece = generatePiece();
                                    nextPieceShape.setPieceType(nextPiece.pieceType);
                                    currentPieceShape.setPieceType(piece.pieceType);
                                    expectedPiecePosition.setPieceType(piece.pieceType);
                                }
                                if (!piece.ifCanBeGenerated(arena)){
                                    arena.resume = false;
                                }
                                arena.updateArena();
                                arenaShape.updateGraphicalArena(mainLayout, arena);
                                arena.resetCompletedLines();
                                arena.succeeded = false;
                                joker.jokerUsed = false;
                                drawNextPiece();
                                drawScore();
                            }
                        }
                    }

                    if (!arena.succeeded && arena.resume)
                    {
                        if (shadowEnabled) drawExpectedPiecePosition();
                        drawCurrentPiece();
                    }
                }
                else if(!arena.resume){
                    gameMusicPlayer.stop();
                    gameOverSound.play();
                    showGameOver();
                }
            }
        };
        animator.start();
    }


    public static void drawScore() {
        scoreValueText.setText(String.valueOf(widgets.getScore()));
        levelValueText.setText(String.valueOf(widgets.getActuelLevel() + 1));
        completedLinesValueText.setText(String.valueOf(widgets.getNbLinceC()));
        mainLayout.getChildren().removeAll(scoreValueText, textScore, levelValueText, levelText, completedLinesText, completedLinesValueText);
        mainLayout.getChildren().addAll(scoreValueText, textScore, levelValueText, levelText, completedLinesText, completedLinesValueText);
    }

    public static void drawNextPiece() {
        int id = 0;
        nextPieceShape.remove(mainLayout);
        for (int i = 0; i < nextPiece.matrix.length; i++) {
            for (int j = 0; j < nextPiece.matrix[i].length; j++) {
                if (nextPiece.matrix[i][j] != 0) {
                    nextPieceShape.cube[id].setX(331.5 + arenaXLayout + j * Sprite.getCubeSize());
                    nextPieceShape.cube[id].setY(27 + arenaYLayout + i * Sprite.getCubeSize());
                    mainLayout.getChildren().add(nextPieceShape.cube[id]);
                    id++;
                }
            }
        }
    }

    public static void drawCurrentPiece() {
        int id = 0;
        currentPieceShape.remove(mainLayout);
        for (int i = 0; i < piece.matrix.length; i++) {
            for (int j = 0; j < piece.matrix[i].length; j++) {
                if (piece.matrix[i][j] != 0 && i + piece.posY >= 4) {
                    currentPieceShape.cube[id].setX(Sprite.getCubeSize()*(piece.getPosX() + j) + arenaXLayout);
                    currentPieceShape.cube[id].setY(Sprite.getCubeSize()*(piece.getPosY() + i - 4) + arenaYLayout);
                    mainLayout.getChildren().add(currentPieceShape.cube[id]);
                    id++;
                }
            }
        }
    }

    public static void drawExpectedPiecePosition() {

        int id = 0;
        int originalPosY = piece.getPosY();
        int virtualPosY;
        while (!piece.collision(arena)){
            piece.posY++;
        }
        virtualPosY = piece.posY - 1;
        piece.posY = originalPosY;
        expectedPiecePosition.remove(mainLayout);
        for (int i = 0; i < piece.matrix.length; i++) {
            for (int j = 0; j < piece.matrix[i].length; j++) {
                if (piece.matrix[i][j] != 0 && i + virtualPosY >= 4) {
                    expectedPiecePosition.cube[id].setX(Sprite.getCubeSize()*(piece.getPosX() + j) + arenaXLayout);
                    expectedPiecePosition.cube[id].setY(Sprite.getCubeSize()*(i + virtualPosY - 4) + arenaYLayout);
                    mainLayout.getChildren().add(expectedPiecePosition.cube[id]);
                    id++;
                }
            }
        }
    }

    public static Piece generatePiece(){
        Piece generatedPiece = new PieceI() ;
        Random rand = new Random() ;
        Piece level0to2 [] = {
                new PieceI(), new PieceJ() , new PieceL() , new PieceS()
                , new PieceO()
                , new PieceZ()
                , new PieceT()
        };
        Piece level3to6 [] = {
                new PieceI(), new PieceJ() , new PieceL() , new PieceS()
                , new PieceO()
                , new PieceZ()
                , new PieceT(), new PieceJ() , new PieceL() , new PieceS(),new PieceZ()
                , new PieceT()
        };
        Piece level7to9 [] = {
                new PieceI(), new PieceJ() , new PieceL() , new PieceS()
                , new PieceO()
                , new PieceZ()
                , new PieceT(), new PieceJ() , new PieceL() , new PieceS(),new PieceZ()
                , new PieceT(), new PieceT(), new PieceJ() , new PieceL() , new PieceS(),new PieceZ()
        };
        if ( widgets.actuelLevel>=0 && widgets.actuelLevel<=2) {
            int i = rand.nextInt(7) ;
            generatedPiece = level0to2[i];
        }

        else if ( widgets.actuelLevel>=3 && widgets.actuelLevel<=6) {
            int i = rand.nextInt(12) ;
            generatedPiece = level3to6[i];
        }
        else if ( widgets.actuelLevel>=7 && widgets.actuelLevel<=9) {
            int i = rand.nextInt(17) ;
            generatedPiece = level7to9[i];
        }

        return generatedPiece;
    }

    public static void restart(){

        animator.stop();

        gameIsPaused = false;
        nicknameEntry.setText("");
        removePauseMenuObjects();
        pauseButton.setOnAction(e -> pause());
        pauseButton.setText("Pause");

        InGameWindow.arena = new Arena(10, 26);
        InGameWindow.widgets = new Score();
        mainLayout.getChildren().remove(joker);
        joker = new Joker(340,500);
        mainLayout.getChildren().add(joker);
        InGameWindow.drawScore();
        InGameWindow.nextPiece = InGameWindow.generatePiece();
        InGameWindow.nextPieceShape.setPieceType(nextPiece.pieceType);
        InGameWindow.drawNextPiece();
        InGameWindow.piece = InGameWindow.generatePiece();
        InGameWindow.currentPieceShape.setPieceType(piece.pieceType);
        InGameWindow.expectedPiecePosition.remove(mainLayout);
        InGameWindow.expectedPiecePosition = new GraphicalPiece(0.35);
        InGameWindow.expectedPiecePosition.setPieceType(InGameWindow.piece.pieceType);
        InGameWindow.arenaShape.updateGraphicalArena(mainLayout, InGameWindow.arena);
        InGameWindow.gameMusicPlayer.stop();
        gameMusicPlayer = new MediaPlayer(gameMusic);
        gameMusicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                gameMusicPlayer.seek(Duration.ZERO);
            }
        });
        if (!gameMusicIsPaused) InGameWindow.gameMusicPlayer.play();

        animateGamePlay();

    }

    public static void pause(){
        if (InGameWindow.arena.resume){
            gameIsPaused = true;
            pauseButton.setOnAction(e -> resume());
            pauseButton.setText("Resume");
            restartButton.setOnAction(null);
            InGameWindow.gameMusicPlayer.pause();
            showPause();
        }
    }

    public static void showPause(){

        animator.stop();
        //pauseImage.setOnMouseClicked(null);
        rectCoverArena.setOpacity(1);
        mainLayout.getChildren().add(rectCoverArena);
        mainLayout.getChildren().add(logoImage);
        mainLayout.getChildren().add(gamePausedText);
        mainLayout.getChildren().add(buttonResume);
        mainLayout.getChildren().add(buttonRestart);
        mainLayout.getChildren().add(buttonBackToMainMenu);

        animator = new AnimationTimer() {

            public void handle(long now) {

            }
        };
        animator.start();
    }

    public static void removePauseMenuObjects(){

        mainLayout.getChildren().remove(rectCoverArena);
        mainLayout.getChildren().remove(logoImage);
        mainLayout.getChildren().remove(gamePausedText);
        mainLayout.getChildren().remove(gameOverText);
        mainLayout.getChildren().remove(buttonResume);
        mainLayout.getChildren().remove(buttonRestart);
        mainLayout.getChildren().remove(buttonBackToMainMenu);
        mainLayout.getChildren().remove(successRegister);
        mainLayout.getChildren().remove(failureRegister);
        mainLayout.getChildren().remove(saveButton);
        mainLayout.getChildren().remove(nicknameEntry);
    }

    public static void resume(){

        gameIsPaused = false;
        animator.stop();
        removePauseMenuObjects();
        pauseButton.setOnAction(e -> pause());
        pauseButton.setText("Pause");
        restartButton.setOnAction(e -> restart());
        if (!gameMusicIsPaused) InGameWindow.gameMusicPlayer.play();
        animateGamePlay();

    }

    public static void goMainMenu() throws IOException{
        gameIsPaused = false;
        animator.stop();
        gameMusicPlayer.stop();
        explosionSound.stop();
        removePauseMenuObjects();
        Main.primaryStage.setScene(Main.scene);
        Main.primaryStage.centerOnScreen();
        Main.primaryStage.show();
        Main.mainMenuMusicPlayer.play();
        MainMenuWindow.showMainMenu();
    }

    public static void showGameOver(){

        animator.stop();
        gameOverSound.play();
        rectCoverArena.setOpacity(0.9);
        mainLayout.getChildren().add(rectCoverArena);
        mainLayout.getChildren().add(logoImage);
        mainLayout.getChildren().add(gameOverText);
        mainLayout.getChildren().add(buttonRestart);
        mainLayout.getChildren().add(buttonBackToMainMenu);
        if (GameData.avaiablePlace()){
            mainLayout.getChildren().addAll(nicknameEntry, saveButton);
        }

        animator = new AnimationTimer() {

            public void handle(long now) {

            }
        };
        animator.start();

    }

    public static void manageUserInput(){
        mainLayout.getChildren().removeAll(nicknameEntry, saveButton);
        if (!nicknameEntry.getText().equals("")) {
            GameData.updateGameDataContent(nicknameEntry.getText(), widgets.getScore());
            mainLayout.getChildren().add(successRegister);
        }else{
            mainLayout.getChildren().add(failureRegister);
        }
        nicknameEntry.setPromptText("Your nickname...");
    }

    public static void disableInGameMusic(){
        gameMusicIsPaused = true;
        gameMusicPlayer.pause();
        mainLayout.getChildren().remove(soundOn);
        mainLayout.getChildren().add(soundOff);
    }

    public static void enableInGameMusic(){
        gameMusicIsPaused = false;
        gameMusicPlayer.play();
        mainLayout.getChildren().remove(soundOff);
        mainLayout.getChildren().add(soundOn);
    }

    public static void manageShadow(){
        if (mainLayout.getChildren().contains(expectedPiecePosition.cube[0])) {
            expectedPiecePosition.remove(mainLayout);
            shadowEnabled = false;
        }
        else{
            for (int i = 0; i < 4; i++) {
                mainLayout.getChildren().add(expectedPiecePosition.cube[i]);
            }
            shadowEnabled = true;
        }
    }

}