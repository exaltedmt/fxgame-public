package cs1302.fxgame;

import com.michaelcotterell.game.Game;
import com.michaelcotterell.game.GameTime;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IntroScreen{

	private GridPane root = new GridPane();
	private Scene intro = new Scene(root,640,480);

	public IntroScreen(Stage primaryStage) {

		root.setAlignment(Pos.TOP_CENTER);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(5);
		root.getColumnConstraints().add(col1);

		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(90);
		root.getColumnConstraints().add(col2);

		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPercentWidth(5);
		root.getColumnConstraints().add(col3);

		for (int i = 0; i < 3 ; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100.0/3);
			root.getRowConstraints().add(row);         
		}   

		//set bg
		Image galBG = new Image("file:resources/galaxyBG.gif",640,480,false,false);
		Image invader = new Image("file:resources/explosion.gif",1260,1080,false,false);
		ImageView galaxyBG = new ImageView(invader);

		BackgroundImage bg = new BackgroundImage(
				galBG,
				BackgroundRepeat.REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT
				);

		root.setBackground(new Background(bg)); 

		Rectangle fadeR = new Rectangle(1260,1080){{setFill(Color.GHOSTWHITE);}};

		FadeTransition fade = new FadeTransition(Duration.millis(4000), fadeR);
		fade.setFromValue(1.0);
		fade.setToValue(0.0);
		fade.setAutoReverse(true);
		fade.setCycleCount(1);

		fade.play();

		root.add(fadeR,0,0);

		FadeTransition fadeBG = new FadeTransition(Duration.millis(2500), galaxyBG);
		fadeBG.setFromValue(1.0);
		fadeBG.setToValue(0.0);
		fadeBG.setAutoReverse(true);
		fadeBG.setCycleCount(1);

		fadeBG.play();

		root.add(galaxyBG,0,1);

		//fade out
		primaryStage.setScene(intro);

		//entry into game
		intro.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER){

					TestGame game = new TestGame(primaryStage);
					primaryStage.setTitle(game.getTitle());
					primaryStage.setScene(game.getScene());
					primaryStage.show();

					Label startIns = new Label("< Press Left or Right to start the battle! >\n"
							+"< or press Escape now to go back. >"){{

								setEffect(blend("#00FF4D","#A5EEBB"));
								setFont(Font.loadFont("file:resources/space_invaders.ttf", 22));
								setTextFill(Color.WHITESMOKE);
								setTextAlignment(TextAlignment.CENTER);
								setTranslateX(20);
								setTranslateY(215);
							}};

							flicker(startIns);

							game.getSceneNodes().getChildren().add(startIns);

							game.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent key) {

									if(key.getCode() == KeyCode.ESCAPE){

										Rectangle fadeGame = new Rectangle(1260,1080){{setFill(Color.GHOSTWHITE);}};

										FadeTransition ftGame = new FadeTransition(Duration.millis(2000), fadeGame);
										ftGame.setFromValue(1.0);
										ftGame.setToValue(0.0);
										ftGame.setAutoReverse(true);
										ftGame.setCycleCount(1);

										ftGame.play();
										root.add(fadeGame, 0, 0);
										primaryStage.setScene(intro);
										primaryStage.show();

									} else { 
										game.getSceneNodes().getChildren().remove(startIns);

										key.consume(); 
										game.run();
									}

								}

							});	

				}
			}
		});	

		String instructions =
				"In-Game Instructions:\n"
						+ "Move Left: < Key\n" + "Move Right: > Key\n" + "Fire: Spacebar\n"
						+ "Escape key to end the game!";

		Label welcome = new Label("Welcome to..."){{

			setEffect(blend("#F0E4F0","#F60CCF"));
			setFont(Font.loadFont("file:resources/space_invaders.ttf", 20));
			setTextFill(Color.WHITESMOKE);
			setTextAlignment(TextAlignment.CENTER);

		}};

		transition(root,welcome, -320, 0);

		Label title = new Label("Space Invaders"){{

			setEffect(blend("#F0E4F0","#F60CCF"));
			setFont(Font.loadFont("file:resources/space_invaders.ttf", 55));
			setTextFill(Color.WHITESMOKE);
			setTextAlignment(TextAlignment.CENTER);
			setLayoutX(640);
			setLayoutY(0);
		}};

		transition(root,title, 320, 0);

		Label play = new Label("< Press enter to begin the game! >"){{

			setEffect(blend("#00FF4D","#A5EEBB"));
			setFont(Font.loadFont("file:resources/space_invaders.ttf", 22));
			setTextFill(Color.WHITESMOKE);
			setTextAlignment(TextAlignment.CENTER);
		}};

		flicker(play);

		Label tutorial = new Label(){{

			setText(instructions);
			setEffect(blend("#F0E4F0","#F60CCF"));
			setFont(Font.loadFont("file:resources/space_invaders.ttf", 18));
			setTextFill(Color.WHITESMOKE);
			setTextAlignment(TextAlignment.CENTER);
			setWrapText(true);
		}};

		transition(root,tutorial, -320, 0);

		VBox titleBox = new VBox(){{

			setAlignment(Pos.TOP_CENTER);
			getChildren().addAll(welcome,title);
			setPadding(new Insets(5));
		}};

		VBox playBox = new VBox(){{

			setAlignment(Pos.TOP_CENTER);
			getChildren().add(play);
			setPadding(new Insets(30,0,0,0));
		}};

		VBox tutorialBox = new VBox(){{

			setAlignment(Pos.TOP_CENTER);
			getChildren().add(tutorial);
			setPadding(new Insets(0,10,0,0));
		}};

		root.add(titleBox, 1, 0);
		root.add(playBox, 1, 1);
		root.add(tutorialBox,1,2);

		primaryStage.setScene(intro);
		primaryStage.show();

	}

	/**
	 * text glow
	 * @param color1
	 * @param color2
	 * @return
	 */
	public Blend blend(String color1, String color2){

		Blend blend = new Blend();
		blend.setMode(BlendMode.MULTIPLY);

		DropShadow ds = new DropShadow();
		ds.setColor(Color.web(color1));
		ds.setOffsetX(0);
		ds.setOffsetY(0);
		ds.setRadius(5);
		ds.setSpread(0.2);

		blend.setBottomInput(ds);

		DropShadow ds1 = new DropShadow();
		ds1.setColor(Color.web(color2));
		ds1.setRadius(20);
		ds1.setSpread(0.2);

		blend.setTopInput(ds1);

		return blend;
	}

	/**
	 * text movement
	 * @param root
	 * @param node
	 * @param start
	 * @param dest
	 */
	public void transition(Pane root, Node node, double start, double dest){

		TranslateTransition tt = new TranslateTransition(Duration.millis(10000), node);

		tt.setFromX(start);
		tt.setToX(dest);
		tt.setCycleCount(1);
		tt.play();

		FadeTransition ft = new FadeTransition(Duration.millis(8000), node);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.setAutoReverse(true);
		ft.setCycleCount(1);

		ft.play();
	}

	/**
	 * text blink
	 * @param node
	 */
	public void flicker(Node node){

		FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.setAutoReverse(true);
		ft.setCycleCount(Timeline.INDEFINITE);

		ft.play();
	}

}
