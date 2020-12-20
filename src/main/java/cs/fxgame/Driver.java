package cs1302.fxgame;

import com.michaelcotterell.game.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception { 

		IntroScreen introGame = new IntroScreen(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	} // main


} // Driver

