
public class Player implements GameObject{

	//0 right
	//1 left
	//2 up
	//3 down
	private Rectangle playerRectangle;
	private int direction = 0;
	private int speed = 10;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	
	public Player(Sprite sprite) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		updateDirection();
		playerRectangle = new Rectangle(32,16,16,32);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
	}
	
	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimatedRange(direction * 8, (direction * 8) + 7);
		}
	}
	
	private void printCoords() {
		System.out.println(playerRectangle.x + "," + playerRectangle.y);
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
		renderer.renderSprite(animatedSprite, playerRectangle.x , playerRectangle.y, xZoom, yZoom,false);
	}else if(sprite != null){
		renderer.renderSprite(sprite, playerRectangle.x , playerRectangle.y, xZoom, yZoom,false);
	}
	else {
		renderer.renderRectangle(playerRectangle, xZoom, yZoom,false);
	}
	}
	
	public void update(Game game) {
		KeyBoardListener keyListener = game.getKeyListener();
		
		boolean didMove = false;
		int newDirection = direction;
		
		if(keyListener.left()) {
			newDirection = 1;
			playerRectangle.x -= speed;
			didMove = true;
		}
		
		if(keyListener.right()) {
			newDirection = 0;
			didMove = true;
			playerRectangle.x += speed;		}
		
		if(keyListener.up()) {
			didMove = true;
			playerRectangle.y -= speed;
			newDirection = 2;
		}
		
		if(keyListener.down()) {
			newDirection = 3;
			playerRectangle.y += speed;
			didMove = true;
		}
		if(keyListener.interact()) {
			newDirection = 4;
			didMove = true;
		}
		
		if(keyListener.Coord()) {
			printCoords();
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
	
		if(!didMove) {
			animatedSprite.reset();
		}
		
		updateCamera(game.getRenderer().getCamera());
		if(didMove) {
		animatedSprite.update(game);
		}
	}
		
		public void updateCamera(Rectangle camera) {
			camera.x = playerRectangle.x - (camera.w/2);
			camera.y = playerRectangle.y - (camera.h/2);
	}


		public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
			return false;
		}
}
