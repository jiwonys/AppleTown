
public class Chick implements GameObject {

	private Rectangle chickRectangle;
	private int direction = 1;
	private int speed = 1;
	private Sprite sprite;
	private int counter = 120;
	private int value = 0;
	private int health = 50;
	private boolean dead = false;
	private boolean hurt = false;
	private AnimatedSprite animatedSprite = null;
	
	public Chick(Sprite sprite) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		updateDirection();
		chickRectangle = new Rectangle(0,0,16,16);
		chickRectangle.generateGraphics(3, 0xFF00FF90);
	}
	
	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimatedRange(direction*8, (direction * 8) + 7);
		}
	}
	
	public Rectangle getRectangle() {
		return chickRectangle;
	}
	public int randomDirection() {
		return (int)(Math.random() * 7 + 1);
	}
	public void hurt() {
		health = health - 20;
		hurt = true;
		health = health - 20;
		//hurt 20
		if (health <= 0) {
			dead = true;
		}
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(dead == false) {
		if(animatedSprite != null) {
		renderer.renderSprite(animatedSprite, chickRectangle.x , chickRectangle.y, xZoom, yZoom,false);
	}else if(sprite != null){
		renderer.renderSprite(sprite, chickRectangle.x , chickRectangle.y, xZoom, yZoom,false);
	}
	else {
		renderer.renderRectangle(chickRectangle, xZoom, yZoom,false);
	}
	}
	}
	
	public int getX() {
		return chickRectangle.x;
	}
	public int getY() {
		return chickRectangle.y;
	}
	
	public void update(Game game) {
		if(counter == 120) {
			value = randomDirection();
			counter = 0;
			if(hurt == true) {
				value = 8;
			}
		}
		
		boolean didMove = false;
		int newDirection = direction;
		
		if(value == 1) {//left
			newDirection = 0;
			chickRectangle.x -= speed;
			didMove = true;
		}
		
		if(value == 2) {//right
			newDirection = 1;
			didMove = true;
			chickRectangle.x += speed;	
			}
		
		if(value == 3) {//up
			didMove = true;
			chickRectangle.y -= speed;
			newDirection = 3;
		}
		
		if(value == 4) {//down
			newDirection = 2;
			chickRectangle.y += speed;
			didMove = true;
		}
		
		if(value == 5) {//blink
			newDirection = 4;
			didMove = true;
		}
		
		if(value == 6) {//eatright
			newDirection = 5;
			didMove = true;
		}
		
		if(value == 7) {//eatleft
			newDirection = 6;
			didMove = true;
		}
		
		if(value == 8) {//eatleft
			newDirection = 7;
			didMove = true;
			hurt = false;
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
	
		
		if(didMove) {
			counter++;
		animatedSprite.update(game);
		}
	}


		public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
			return false;
		}

}
