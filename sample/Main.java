package sample;

// <------------------------------------------ Class Imports -------------------------------------------> //
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
// <----------------------------------------------------------------------------------------------------> //


public class Main extends Application {

	public static AnchorPane mainLayout;
	public static Stage primaryStage;
	public static Scene scene;
	public static String mainMenuMusicFile;
	public static Media mainMenuMusic;
	public static MediaPlayer mainMenuMusicPlayer;
	public static Font font = Font.font("Century Gothic", 18) ;

	public void loadComponents() throws IOException{
		Sprite.makeViewPorts();
		MainMenuWindow.loadComponents();
		LeaderBoardWindow.loadComponents();
		CreditsWindow.loadComponents();
		HowToPlayFirstWindow.loadComponents();
		HowToPlaySecondWindow.loadComponents();
		HowToPlayThirdWindow.loadComponents();
		HowToPlayFourthWindow.loadComponents();
		InGameWindow.loadComponents();
	}

	public void start(Stage primaryStage) {
		try {
			mainMenuMusicFile = "src/sounds/mainMenuMusic.mp3";
			mainMenuMusic = new Media(new File(mainMenuMusicFile).toURI().toString());
			mainMenuMusicPlayer = new MediaPlayer(mainMenuMusic);
			loadComponents();
			mainLayout = new AnchorPane();
			Main.scene = new Scene(mainLayout);
			Main.primaryStage = primaryStage;
			Main.primaryStage.setScene(Main.scene);
			Main.primaryStage.centerOnScreen();
			Main.primaryStage.getIcons().add(new Image("images/logo_symbol.png"));
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setTitle("Cubetris");
			MainMenuWindow.enterMainMenu();
			Main.primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}