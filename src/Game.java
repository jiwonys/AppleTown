import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import java.lang.Runnable;
import java.lang.Thread;

import javax.swing.JFrame;
import javax.imageio.*;

public class Game extends JFrame implements Runnable{
	
	public static int alpha = 0xFFFF00DC;
	
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	
	private SpriteSheet sheet;
	private SpriteSheet playerSheet;
	private SpriteSheet chickSheet;	
	private SpriteSheet cowSheet;
	private SpriteSheet farmernpcSheet;
	private Tiles tiles;
	
	private Map map;
	private GameObject[] objects;	
	private Player player;
	private Chick chick;
	private Cow cow;
	private Chick chick2;
	private Cow cow2;
	private Farmernpc farmernpc;
	
	private KeyBoardListener keyListener = new KeyBoardListener(this);
	private MouseEventListener mouseListener = new MouseEventListener(this);
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int xZoom = 3;
	private int yZoom = 3;
	private int selectedTileID = 2;
	private int SCREENX = (int) screenSize.getWidth();
	private int SCREENY = (int) screenSize.getHeight() - 100;
	
	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("APPLE TOWN Version:Alpha 1.1.1");
		setBounds(0,0, SCREENX, SCREENY);
		setLocationRelativeTo(null);

		add(canvas);
		setVisible(true);
		canvas.createBufferStrategy(3);
		renderer = new RenderHandler(getWidth(), getHeight());
		BufferedImage sheetImage = loadImage("spritesheet.png");
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(16,16);
		
		
		BufferedImage playerSheetImage = loadImage("player.png");
		BufferedImage chickSheetImage = loadImage("chickwalk.png");
		BufferedImage cowSheetImage = loadImage("cowwalk.png");
		BufferedImage farmernpcImage = loadImage("farmer.png");
		
		playerSheet = new SpriteSheet(playerSheetImage);
		chickSheet = new SpriteSheet(chickSheetImage);
		cowSheet = new SpriteSheet(cowSheetImage);
		farmernpcSheet = new SpriteSheet(farmernpcImage);
		playerSheet.loadSprites(20,26);
		chickSheet.loadSprites(16, 16);
		cowSheet.loadSprites(32,32);
		farmernpcSheet.loadSprites(16,32);
		
		
		AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 8);
		AnimatedSprite chickAnimations = new AnimatedSprite(chickSheet, 10);
		AnimatedSprite cowAnimations = new AnimatedSprite(cowSheet, 20);
		AnimatedSprite chickAnimations2 = new AnimatedSprite(chickSheet, 10);
		AnimatedSprite cowAnimations2 = new AnimatedSprite(cowSheet, 20);
		AnimatedSprite farmernpcAnimations = new AnimatedSprite(farmernpcSheet, 5);
		
		tiles = new Tiles(new File("./src/tilefile.txt"), sheet);
		map = new Map(new File("./src/Map.txt"), tiles);
	
		
		GUIButton[] buttons = new GUIButton[tiles.size()];
		Sprite[] tileSprites = tiles.getSprites();
		
		for(int i = 0; i < buttons.length; i++){
			Rectangle tileRectangle = new Rectangle(0, i*(16*xZoom + 2), 16*xZoom, 16*yZoom);
			buttons[i] = new SDKButton(this, i, tileSprites[i], tileRectangle);
		}

		
		GUI gui = new GUI(buttons, 0,0, true);
		objects = new GameObject[7];
		player = new Player(playerAnimations);
		chick = new Chick(chickAnimations);
		chick2 = new Chick(chickAnimations2);
		cow = new Cow(cowAnimations);
		cow2 = new Cow(cowAnimations2);
		farmernpc = new Farmernpc(farmernpcAnimations);
		
		objects[6] = player;
		objects[5] = gui;
		objects[2] = chick;
		objects[3] = cow;
		objects[4] = chick2;
		objects[1] = cow2;
		objects[0] = farmernpc;
		
		
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
	}

	
	public void update() {
		for(int i = 0; i < objects.length; i++) {
			objects[i].update(this);
		}
	}

	private BufferedImage loadImage(String path) {
		try {
		BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
		BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
		return formattedImage;
		
		} catch(IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	public void handleCTRL(boolean[] keys) {
		if(keys[KeyEvent.VK_S]) {
			map.saveMap();
			System.out.println("Map has been saved!");
		}
	}

	public void leftClick(int x, int y) {

		Rectangle mouseRectangle = new Rectangle(x,y, 1,1);
		boolean stoppedChecking = false;
		for(int i = 0; i < objects.length; i++)
			if(!stoppedChecking) {
				stoppedChecking = objects[i].handleMouseClick(mouseRectangle, renderer.getCamera(), xZoom, yZoom);
			}
		
		if(!stoppedChecking) 
		{
			x = (int) Math.floor((x + renderer.getCamera().x)/(16.0 * xZoom));
			y = (int) Math.floor((y + renderer.getCamera().y)/(16.0 * yZoom));
			map.setTile(x, y, selectedTileID);
		}

	}
	
	public void rightClick(int x, int y) {
		x = (int) Math.floor((x + renderer.getCamera().x)/(16.0 *xZoom));
		y = (int) Math.floor((y + renderer.getCamera().y)/(16.0 *yZoom));
		map.removeTile(x, y);
	}
	
	
	
	public void render() {
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);
			
			map.render(renderer, xZoom, yZoom);
			for(int i = 0; i < objects.length; i++) {
				objects[i].render(renderer, xZoom, yZoom);
			}

			renderer.render(graphics);

			graphics.dispose();
			bufferStrategy.show();
			renderer.clear();

	}

	public void changeTile(int tileID) 
	{
		selectedTileID = tileID;
	}

	public int getSelectedTile()
	{
		return selectedTileID;
	}
	
	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;

		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
		double changeInSeconds = 0;

		while(true) {
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				update();
				changeInSeconds--;
			}

			render();
			lastTime = now;
		}

	}

	public static void main(String[] args) 
	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
	
	public KeyBoardListener getKeyListener() {
		return keyListener;
	}
	
	public RenderHandler getRenderer() {
		return renderer;
	}
	
	public MouseEventListener getMouseListener() {
		return mouseListener;
	}


}