
public class Arrow implements GameObject{

	private Rectangle arrowRectangle;
	public int direction;
	private int newDir;
	private int speed = 5;
	private Sprite sprite;
	private int value = 0;
	private AnimatedSprite animatedSprite = null;
	private boolean shot = false;
	private int arrowCounter;
	private Player player;
	public int arrowX;
	public int arrowY;
	public int playerDirection;
	
	public Arrow(Sprite sprite) {
		
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		updateDirection();
		arrowRectangle = new Rectangle(1000,1000,16,16);
		arrowRectangle.generateGraphics(3, 0xFF00FF90);
	}
	
	private void shootArrow(int direction) {
		arrowRectangle.x += direction;
	}
	
	public void setArrowXY(int x, int y) {
		arrowX = x;
		arrowY = y;
		//System.out.println("Debugging settArrowXY" + x +","+ y);
		

	}
	
	public void setArrowDirection(int direction) {
		playerDirection = direction;
	}
	
	
	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimatedRange(direction*8, (direction * 8) + 7);
		}
	}
	
	public void setBoolShot(boolean shot) {
		this.shot = shot;
	}
	
	public Rectangle getRectangle() {
		return arrowRectangle;
	}
	public boolean getBoolShot() {
		return shot;
	}
	
	public int getX() {
		return arrowRectangle.x;
	}
	
	public int getY() {
		return arrowRectangle.y;
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			if(shot == true) {
		renderer.renderSprite(animatedSprite, arrowRectangle.x , arrowRectangle.y, xZoom, yZoom,false);
			}
	}else if(sprite != null){
		if(shot == true) {
		renderer.renderSprite(sprite, arrowRectangle.x , arrowRectangle.y, xZoom, yZoom,false);
	}
	}
	else {
		if(shot == true) {
		renderer.renderRectangle(arrowRectangle, xZoom, yZoom,false);
	}
	}
	}
	
	public void update(Game game) {
		
		KeyBoardListener e = game.getKeyListener();
		boolean didMove = false;
		int newDirection = direction;

		if(e.shoot()) {
			shot = true;
			arrowRectangle.x = arrowX+ 10;
			arrowRectangle.y = arrowY +30;
		}
		
		if(shot == true) {
				if(playerDirection == 0) {
					newDir = 0;
					arrowRectangle.x += speed;
					didMove = true;
				}else if(playerDirection == 1) {
					newDir = 1;
					arrowRectangle.x -= speed;
					didMove = true;
				}else if(playerDirection == 2) {
					newDir = 2;
					arrowRectangle.y -= speed;
					didMove = true;

				}else if(playerDirection == 3) {
					newDir = 3;
					arrowRectangle.y += speed;
					didMove = true;

				}
				
			animatedSprite.update(game);
			arrowCounter++;
			}
			
			if(arrowCounter >= 120) {
				arrowCounter = 0;
				didMove = false;
				shot = false;
			}	

		if(newDir != direction) {
			direction = newDir;
			updateDirection();
		}
		
		if(!didMove) {
			animatedSprite.reset();
		}
		
	}


		public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
			return false;
		}
}
