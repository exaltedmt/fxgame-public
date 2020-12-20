package cs1302.fxgame;

import com.michaelcotterell.game.Game;
import com.michaelcotterell.game.GameTime;

import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestGame extends Game {

	private Stage newStage = new Stage();
	private int selection = 0;
	private Pane pauseRoot = new Pane();
	private Scene pauseScene = new Scene(pauseRoot,640,480);
	private Image arrowImg = new Image("file:resources/selector.gif",50,50,false,false);
	private Image invaderImg = new Image("file:resources/invader.gif",20,20,false,false);
	private Image bunkerlvl1 = new Image("file:resources/barrel1.png",50,40,false,false);
	private Image bunkerlvl2 = new Image("file:resources/barrel2.png",50,40,false,false);
	private Image bunkerlvl3 = new Image("file:resources/barrel3.png",50,40,false,false);
	private Image bunkerlvl4 = new Image("file:resources/barrel4.png",60,50,false,false);
	private Image shipImg = new Image("file:resources/Galaga_ship.png",20,20,false,false);
	private Image heartImg = new Image("file:resources/hearts.gif",30,30,false,false);
	private ImageView[] livelihood = new ImageView[3];
	private Image respawn = new Image("file:resources/respawn.gif",20,20,false,false);
	private Random rand = new Random();
	private Image explosiveImg = new Image("file:resources/explosion.gif",30,30,false,false);
	private double accel = 1;
	private Image lazer = new Image("file:resources/lazor.gif",15,15,false,false);
	private Image enemyLazer = new Image("file:resources/enemyLazor.gif",15,15,false,false);
	private Timeline moveRight = new Timeline();
	private int lifeCounter = 3;
	private Timeline moveLeft = new Timeline();
	private int[] bunkerlives = new int[8];
	private ImageView[] bunkers = new ImageView[8];
	private Timeline shot = new Timeline();
	private Timeline enemyShot = new Timeline();
	private ImageView[][] invaders = new ImageView[5][11];
	private boolean[][] dead = new boolean[5][11];
	private int r = 0;
	private int c = 0;
	private boolean shipCD = false;
	private boolean invCD = false;
	private ImageView shipLazor = new ImageView(lazer);
	private ImageView enemyLazor = new ImageView(enemyLazer);
	private int score = 0;
	private int shipLives = 3;
	private Label home = new Label(){{

		setText("< Exit to main menu >");
		setEffect(blendTG("#F0E4F0","#F60CCF"));
		setFont(Font.loadFont("file:resources/space_invaders.ttf", 18));
		setTextFill(Color.WHITESMOKE);
		setTextAlignment(TextAlignment.CENTER);
		setWrapText(true);
	}};

	private Label qq = new Label(){{

		setText("< Cry some more! >");
		setEffect(blendTG("#F0E4F0","#F60CCF"));
		setFont(Font.loadFont("file:resources/space_invaders.ttf", 18));
		setTextFill(Color.WHITESMOKE);
		setTextAlignment(TextAlignment.CENTER);
		setWrapText(true);
	}};

	private Label paused = new Label(){{

		setText("< The fight is over! >");
		setEffect(blendTG("#F0E4F0","#F60CCF"));
		setFont(Font.loadFont("file:resources/space_invaders.ttf", 40));
		setTextFill(Color.WHITESMOKE);
		setTextAlignment(TextAlignment.CENTER);
		setWrapText(true);
	}};

	private Image galBG = new Image("file:resources/gameBG.gif",640,480,false,false);
	private ImageView gameBG = new ImageView(galBG);

	// some text to display the time

	private Label timer = new Label(){{

		setEffect(blendTG("#00E6CF","#CBFFFB"));
		setFont(Font.loadFont("file:resources/space_invaders.ttf", 18));
		setTextFill(Color.FLORALWHITE);
		setTextAlignment(TextAlignment.CENTER);
		setWrapText(true);
		setTranslateX(5);
		setTranslateY(3);
	}};

	private VBox pauseBox = new VBox(){{

		setTranslateX(75);
		setTranslateY(50);
		setAlignment(Pos.TOP_CENTER);
		getChildren().add(paused);
		setPadding(new Insets(30,0,0,0));
	}};

	private VBox selectBox = new VBox(){{

		setTranslateX(205);
		setTranslateY(300);
		setAlignment(Pos.TOP_CENTER);
		getChildren().addAll(qq,home);
		setPadding(new Insets(0,10,0,0));
	}};

	private ImageView arrowIV = new ImageView(arrowImg){{
		setTranslateX(205-60);
		setTranslateY(290);
	}};

	private Label lifeCount = new Label(){{

		setText("Lives: ");
		setEffect(blendTG("#00E6CF","#CBFFFB"));
		setFont(Font.loadFont("file:resources/space_invaders.ttf", 18));
		setTextFill(Color.FLORALWHITE);
		setTextAlignment(TextAlignment.CENTER);
		setWrapText(true);
		setTranslateX(480);
		setTranslateY(3);
	}};

	private ImageView playerShip = new ImageView(shipImg){{	
		setTranslateX(302);
		setTranslateY(433);
	}};

	/**
	 * Constructs a new test game.
	 * @param stage the primary stage
	 */
	public TestGame(Stage primaryStage) {
		super(primaryStage, "Space Invader Battleground", 60, 640, 480);

		makePause();//makes pause screen

		Rectangle fadeGame = new Rectangle(1260,1080){{setFill(Color.GHOSTWHITE);}};

		FadeTransition ftGame = new FadeTransition(Duration.millis(2000), fadeGame);
		ftGame.setFromValue(1.0);
		ftGame.setToValue(0.0);
		ftGame.setAutoReverse(true);
		ftGame.setCycleCount(1);

		ftGame.play();

		getSceneNodes().getChildren().addAll(gameBG,timer,playerShip,fadeGame,lifeCount);

		battleground();

		//makes live images
		for(int i = 0; i<livelihood.length;i++){
			livelihood[i] = new ImageView(heartImg);
			getSceneNodes().getChildren().add(livelihood[i]);
			livelihood[i].setTranslateX(lifeCount.getTranslateX()+45+(25*(i+1)));
			livelihood[i].setTranslateY(lifeCount.getTranslateY());
		}

		newStage = primaryStage;
		newStage.show(); //allows for game reset
		primaryStage.close();


	}

	/**
	 * Sets up invaders bunkers deaths and lives
	 */
	public void battleground(){

		int iYPos = 250;
		int iXPos = 540;

		//filling arrays
		for(int i=0;i<invaders.length;i++){
			for(int j=0;j<invaders[i].length;j++){
				iYPos = 250-(25*i);
				iXPos = 540-(35*j);
				invaders[i][j] = new ImageView(invaderImg);
				getSceneNodes().getChildren().add(invaders[i][j]);
				invaders[i][j].setTranslateX(iXPos);
				invaders[i][j].setTranslateY(iYPos);
			}
		}

		for(int i=0;i<dead.length;i++)
			for(int j=0;j<dead[i].length;j++){
				dead[i][j] = false;
			}

		int bXPos = 15;
		int bYPos = 370;

		for(int i=0;i<bunkers.length;i++){
			bXPos = 15 + (i*80);
			bunkers[i] = new ImageView(bunkerlvl1);
			bunkerlives[i] = 10;
			getSceneNodes().getChildren().add(bunkers[i]);
			bunkers[i].setTranslateX(bXPos);
			bunkers[i].setTranslateY(bYPos);

		}
	}

	/**
	 * makes the animated background for the pause/death scene
	 */
	public void makePause(){

		Image invaderBG = new Image("file:resources/pause.gif",640,480,false,false);

		BackgroundImage bg = new BackgroundImage(
				invaderBG,
				BackgroundRepeat.REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT
				);

		pauseRoot.setBackground(new Background(bg)); 

		flicker(paused);

		pauseRoot.getChildren().addAll(pauseBox,selectBox,arrowIV);
	}

	@Override
	public void update(Game game, GameTime gameTime){

		timer.setText("Score: " + score);

		//bunker health and image change
		for(int i = 0; i<bunkerlives.length;i++){

			if(bunkerlives[i] <= 5 && bunkerlives[i] > 2)
				bunkers[i].setImage(bunkerlvl2);
			if(bunkerlives[i] <= 2 && bunkerlives[i] > 0)
				bunkers[i].setImage(bunkerlvl3);
			if(bunkerlives[i] == 0){
				bunkers[i].setImage(bunkerlvl4);
				Timeline broken = new Timeline(
						new KeyFrame(Duration.ZERO, new KeyValue(bunkers[i].translateYProperty(), bunkers[i].getTranslateY())),
						new KeyFrame(Duration.millis(500), new KeyValue(bunkers[i].translateYProperty(), 10000))
						);
				broken.play();
			}

			if(shipLives == 0)
				gameOver(game);
		}

		if(!invCD)
			invLazerz();

		pause(game);
		shipMove(game); 
	} // update

	/**
	 * Moving the galaga ship and shooting
	 * @param game
	 */
	public void shipMove(Game game){

		//boundaries
		if((playerShip.getTranslateX() - 4) > 15){
			if (game.getKeyManager().isKeyPressed(KeyCode.LEFT)){
				playerShip.setTranslateX(playerShip.getTranslateX() - 4);
			}
		}

		if((playerShip.getTranslateX() + 4) < 600){			
			if (game.getKeyManager().isKeyPressed(KeyCode.RIGHT)) {
				playerShip.setTranslateX(playerShip.getTranslateX() + 4);
			}
		}

		if (game.getKeyManager().isKeyPressed(KeyCode.SPACE)&& !shipCD) {
			shipLazerz(game);
		}


	}

	/**
	 * Creates invaders lazers
	 */
	public void invLazerz(){

		invCD = true;

		//creates a cooldown between shots
		Timeline cooldown = new Timeline(new KeyFrame(
				Duration.millis(1000),
				cd -> invCD=false));

		cooldown.play();

		for(int i=0;i<invaders.length;i++)
			for(int j=0;j<invaders[i].length;j++)
				if(invaders[i][j].getTranslateX() == playerShip.getTranslateX()){
					enemyLazor = new ImageView(enemyLazer);
					enemyLazor.setTranslateX(invaders[i][j].getTranslateX());
					enemyLazor.setTranslateY(invaders[i][j].getTranslateY());

					//moves lazer to bottom of screen and explodes if collides with something.
					enemyLazor.getTransforms().add(new Rotate(90));
					enemyShot = new Timeline();

					enemyShot.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(enemyLazor.translateYProperty(), enemyLazor.getTranslateY())));
					invKeyer(enemyLazor,enemyShot);
					enemyShot.getKeyFrames().addAll(new KeyFrame(Duration.millis(750), new KeyValue(enemyLazor.translateYProperty(), 460)),
							new KeyFrame(Duration.millis(750),ae -> nuked(enemyLazor)));

					enemyShot.play();
					getSceneNodes().getChildren().add(enemyLazor);	
				}
	}

	/**
	 * Creates ship lazers
	 * @param game
	 */
	public void shipLazerz(Game game){

		//same as enemy design
		shipCD = true;

		Timeline cooldown = new Timeline(new KeyFrame(
				Duration.millis(1000),
				cd -> shipCD=false));

		cooldown.play();

		shipLazor = new ImageView(lazer);

		shipLazor.setTranslateX(playerShip.getTranslateX());
		shipLazor.setTranslateY(playerShip.getTranslateY());

		shipLazor.getTransforms().add(new Rotate(270));
		shot = new Timeline();

		shot.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(shipLazor.translateYProperty(), shipLazor.getTranslateY())));
		shipKeyer(shipLazor,shot,enemyLazor);
		shot.getKeyFrames().addAll(new KeyFrame(Duration.millis(750), new KeyValue(shipLazor.translateYProperty(), 30)),
				new KeyFrame(Duration.millis(750),ae -> nuked(shipLazor)));

		shot.play();

		getSceneNodes().getChildren().add(shipLazor);	
	}

	/**
	 * Detects hit collision based on the following parameters:
	 * 
	 * @param enemyLazor
	 * @param shot
	 * @param r
	 * @param c
	 */
	public void collision(ImageView enemyLazor, Timeline shot, int r, int c){

		if(r > 0){
			if(enemyLazor.getBoundsInParent().intersects(invaders[r-1][c].getBoundsInParent())){
				enemyLazor.setImage(null);
				shot.stop();
				getSceneNodes().getChildren().remove(enemyLazor);
			} 
		} else if(enemyLazor.getBoundsInParent().intersects(invaders[r][c].getBoundsInParent())){
			enemyLazor.setImage(null);
			shot.stop();
			getSceneNodes().getChildren().remove(enemyLazor);
		}

	}

	/**
	 * Detects hit collision based on the following parameters:
	 * 
	 * @param lazor
	 * @param shot
	 * @param invaders
	 */
	public void collision(ImageView lazor, Timeline shot, ImageView[][] invaders){

		boolean stopSearch = false;

		for(int i = 0; i < invaders.length; i++){
			if(stopSearch)
				break;
			for(int j = 0; j < invaders[i].length; j++){

				if(lazor.getBoundsInParent().intersects(invaders[i][j].getBoundsInParent())&& !dead[i][j]){
					invaders[i][j].setY(10000);
					dead[i][j] = true;
					score+=10;
					nuked(lazor);
					shot.stop();
					stopSearch = true;
					accel-=.05;
					break;
				}

			}
		}
	}

	/**
	 * Detects hit collision based on the following parameters:
	 * 
	 * @param lazor
	 * @param shot
	 * @param bunkers
	 */
	public void collision(ImageView lazor, Timeline shot, ImageView[] bunkers){

		for(int i = 0; i < bunkers.length; i++){
			if(lazor.getBoundsInParent().intersects(bunkers[i].getBoundsInParent())){
				if(bunkerlives[i] > 0)
					bunkerlives[i]--;

				nuked(lazor);
				shot.stop();
				break;
			}

		}
	}

	/**
	 * Detects hit collision based on the following parameters:
	 * 
	 * @param lazor
	 * @param shot
	 * @param enemyLazor
	 */
	public void collision(ImageView lazor, Timeline shot, ImageView enemyLazor){

		if(lazor.getBoundsInParent().intersects(enemyLazor.getBoundsInParent())){
			nuked(enemyLazor);
			nuked(lazor);
			shot.stop();
		}
	}

	/**
	 * Detects hit collision based on the following parameters:
	 * 
	 * @param playerShip
	 * @param lazor
	 * @param shot
	 */
	public void collision(ImageView playerShip,ImageView lazor, Timeline shot){

		if(lazor.getBoundsInParent().intersects(playerShip.getBoundsInParent())){
			Timeline spawner = new Timeline(
					new KeyFrame(Duration.ZERO, ae -> playerShip.setImage(respawn)),
					new KeyFrame(Duration.millis(1000), ae -> playerShip.setImage(shipImg))
					);

			spawner.play();

			if(shipLives > 0){
				shipLives--;
				Timeline lifer = new Timeline(
						new KeyFrame(Duration.ZERO, ae -> livelihood[lifeCounter].setImage(explosiveImg)),
						new KeyFrame(Duration.millis(500), ae -> livelihood[lifeCounter].setImage(null))
						);
				lifer.play();
				lifeCounter--;
			}

			nuked(lazor);
			shot.stop();
		}
	}

	public void shipKeyer(ImageView lazor, Timeline shot, ImageView enemyLazor){

		for(int i = 0; i<751;i++){
			shot.getKeyFrames().add(new KeyFrame(Duration.millis(i),ae -> collision(lazor,shot,invaders)));
			shot.getKeyFrames().add(new KeyFrame(Duration.millis(i),ae -> collision(lazor,shot,bunkers)));
			shot.getKeyFrames().add(new KeyFrame(Duration.millis(i),ae -> collision(lazor,shot,enemyLazor)));
		}
	}

	public void invKeyer(ImageView lazor, Timeline shot){

		for(int i = 0; i<751;i++){
			shot.getKeyFrames().add(new KeyFrame(Duration.millis(i),ae -> collision(playerShip,lazor,shot)));
			shot.getKeyFrames().add(new KeyFrame(Duration.millis(i),ae -> collision(lazor,shot,bunkers)));
			shot.getKeyFrames().add(new KeyFrame(Duration.millis(i),ae -> collision(lazor,shot,enemyLazor)));
			shot.getKeyFrames().add(new KeyFrame(Duration.millis(i),ae -> collision(lazor,shot,r,c)));
		}
	}

	public void nuked(ImageView exploded){

		Timeline nuke = new Timeline(
				new KeyFrame(Duration.ZERO, ae -> exploded.setImage(explosiveImg)),
				new KeyFrame(Duration.millis(500),ae -> exploded.setImage(null)),
				new KeyFrame(Duration.millis(500), die -> getSceneNodes().getChildren().remove(exploded))
				);
		nuke.play();

	}

	/**
	 * Makes the aliens move right at first, shifts down after reaching bounds and goes left.
	 */
	public void alienMove(){

		for(int i=0;i<invaders.length;i++)
			for(int j=0;j<invaders[i].length;j++){
				if(!dead[i][j])
					moveLeft(invaders[i][j]);

				if(invaders[i][j].getTranslateX() <=10 && !dead[i][j]){
					shiftDown();
					moveRight(invaders[i][j]);
					moveLeft.stop();


					if(invaders[i][j].getTranslateX() >=605 && !dead[i][j]){
						shiftDown();
						moveLeft(invaders[i][j]);
						moveRight.stop();
					}
				}	

			}

	}

	/**
	 * Shifts aliens in row downwards
	 */
	public void shiftDown(){

		for(int i = 0; i<invaders.length;i++)
			for(int j=0; j<invaders[i].length;j++){
				invaders[i][j].setTranslateY(invaders[i][j].getTranslateY()+30);
			}
	}

	/**
	 * Incrementally moves an invader to the right every second
	 * @param invader
	 */
	public void moveRight(ImageView invader){

		moveRight = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(invader.translateXProperty(), invader.getTranslateX())),
				new KeyFrame(Duration.millis(1000*accel), new KeyValue(invader.translateXProperty(), invader.getTranslateX()+25))
				);

		moveRight.play();
	}

	/**
	 * Incrementally moves the invader to the left every second
	 * 
	 * @param invader
	 */
	public void moveLeft(ImageView invader){

		moveLeft = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(invader.translateXProperty(), invader.getTranslateX())),
				new KeyFrame(Duration.millis(1000*accel), new KeyValue(invader.translateXProperty(), invader.getX()-25))
				);

		moveLeft.play();
	}

	/**
	 * Show the same pause screen when lives are gone
	 * 
	 * @param game
	 */
	public void gameOver(Game game){

		game.stop();

		selection = 0;
		newStage.setScene(pauseScene);

		arrowIV.setTranslateX(205-60);
		arrowIV.setTranslateY(290);
		qq.setEffect(blendTG("#68FFFF","#C3EFEF"));

		newStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.UP){
					if(selection == 1){
						selection = 0;
						arrowIV.setTranslateX(205-60);
						arrowIV.setTranslateY(290);
						qq.setEffect(blendTG("#68FFFF","#C3EFEF"));
						home.setEffect(blendTG("#F0E4F0","#F60CCF"));
					}
				}

				if (key.getCode() == KeyCode.DOWN){
					if(selection == 0){
						selection = 1;
						arrowIV.setTranslateX(205-60);
						arrowIV.setTranslateY(290+30);
						home.setEffect(blendTG("#68FFFF","#C3EFEF"));
						qq.setEffect(blendTG("#F0E4F0","#F60CCF"));
					}
				}

				if (key.getCode() == KeyCode.ENTER){
					if(selection == 0){

						Rectangle fadeGame = new Rectangle(1260,1080){{setFill(Color.GHOSTWHITE);}};

						FadeTransition ftGame = new FadeTransition(Duration.millis(2000), fadeGame);
						ftGame.setFromValue(1.0);
						ftGame.setToValue(0.0);
						ftGame.setAutoReverse(true);
						ftGame.setCycleCount(1);

						ftGame.play();

						Image qqBG = new Image("file:resources/qq.gif",640,480,false,false);

						BackgroundImage qqbg = new BackgroundImage(
								qqBG,
								BackgroundRepeat.REPEAT, 
								BackgroundRepeat.NO_REPEAT, 
								BackgroundPosition.DEFAULT,
								BackgroundSize.DEFAULT
								);

						pauseRoot.setBackground(new Background(qqbg)); 

						flicker(paused);

						pauseRoot.getChildren().addAll(fadeGame);
					}

					if(selection == 1){
						IntroScreen newIntro = new IntroScreen(newStage);
					}
				}

			}
		});	
	}

	/**
	 * Pauses but really ends the game
	 * 
	 * @param game
	 */
	public void pause(Game game){

		if (game.getKeyManager().isKeyPressed(KeyCode.ESCAPE)){
			game.stop();

			selection = 0;
			newStage.setScene(pauseScene);

			arrowIV.setTranslateX(205-60);
			arrowIV.setTranslateY(290);
			qq.setEffect(blendTG("#68FFFF","#C3EFEF"));

			newStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent key) {
					if(key.getCode() == KeyCode.UP){
						if(selection == 1){
							selection = 0;
							arrowIV.setTranslateX(205-60);
							arrowIV.setTranslateY(290);
							qq.setEffect(blendTG("#68FFFF","#C3EFEF"));
							home.setEffect(blendTG("#F0E4F0","#F60CCF"));
						}
					}

					if (key.getCode() == KeyCode.DOWN){
						if(selection == 0){
							selection = 1;
							arrowIV.setTranslateX(205-60);
							arrowIV.setTranslateY(290+30);
							home.setEffect(blendTG("#68FFFF","#C3EFEF"));
							qq.setEffect(blendTG("#F0E4F0","#F60CCF"));
						}
					}

					if (key.getCode() == KeyCode.ENTER){
						if(selection == 0){

							Rectangle fadeGame = new Rectangle(1260,1080){{setFill(Color.GHOSTWHITE);}};

							FadeTransition ftGame = new FadeTransition(Duration.millis(2000), fadeGame);
							ftGame.setFromValue(1.0);
							ftGame.setToValue(0.0);
							ftGame.setAutoReverse(true);
							ftGame.setCycleCount(1);

							ftGame.play();

							Image qqBG = new Image("file:resources/qq.gif",640,480,false,false);

							BackgroundImage qqbg = new BackgroundImage(
									qqBG,
									BackgroundRepeat.REPEAT, 
									BackgroundRepeat.NO_REPEAT, 
									BackgroundPosition.DEFAULT,
									BackgroundSize.DEFAULT
									);

							pauseRoot.setBackground(new Background(qqbg)); 

							flicker(paused);

							pauseRoot.getChildren().addAll(fadeGame);
						}

						if(selection == 1){
							IntroScreen newIntro = new IntroScreen(newStage);
						}
					}

				}
			});	

		} 
	}

	/**
	 * Glow effect for text
	 * 
	 * @param color1
	 * @param color2
	 * @return
	 */
	public Blend blendTG(String color1, String color2){

		Blend blend = new Blend();
		blend.setMode(BlendMode.MULTIPLY);

		DropShadow ds = new DropShadow();
		ds.setColor(Color.web(color1));
		ds.setOffsetX(0);
		ds.setOffsetY(0);
		ds.setRadius(5);
		ds.setSpread(0.4);

		blend.setBottomInput(ds);

		DropShadow ds1 = new DropShadow();
		ds1.setColor(Color.web(color2));
		ds1.setRadius(20);
		ds1.setSpread(0.4);

		blend.setTopInput(ds1);

		return blend;
	}

	/**
	 * Blinks text using transitions
	 * 
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

} // TestGame

