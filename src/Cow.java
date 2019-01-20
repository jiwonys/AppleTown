
public class Cow implements GameObject {

	private Rectangle cowRectangle;
	private int direction = 1;
	private int speed = 1;
	private Sprite sprite;
	private int counter = 160;
	private int value = 0;
	private int health = 100;
	private boolean dead = false;
	private boolean hurt = false;
	private AnimatedSprite animatedSprite = null;
	
	public Cow(Sprite sprite) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		updateDirection();
		cowRectangle = new Rectangle(0,0,32,32);
		cowRectangle.generateGraphics(3, 0xFF00FF90);
	}
	
	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimatedRange(direction*8, (direction * 8) + 7);
		}
	}
	
	public int randomDirection() {
		return (int)(Math.random() * 5 + 1);
	}
	public Rectangle getRectangle() {
		return cowRectangle;
	}
	public void hurt() {
		hurt = true;
		direction = 5;
		health = health - 20;
		if (health == 0) {
			dead = true;
		}
		//hurt 20
	}
	
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(dead == false) {
		if(animatedSprite != null) {
		renderer.renderSprite(animatedSprite, cowRectangle.x , cowRectangle.y, xZoom, yZoom,false);
	}else if(sprite != null){
		renderer.renderSprite(sprite, cowRectangle.x , cowRectangle.y, xZoom, yZoom,false);
	}
	else {
		renderer.renderRectangle(cowRectangle, xZoom, yZoom,false);
	}
	}
	}
	
	public int getX() {
		return cowRectangle.x;
	}
	public int getY() {
		return cowRectangle.y;
	}
	
	public void update(Game game) {
		if(counter == 160) {
			
			value = randomDirection();
			counter = 0;
			if(hurt == true) {	
			value = 6;
			}
		}
		
		if(health == 0) {
			dead = true;
		}
		
		boolean didMove = false;
		int newDirection = direction;
		
		if(value == 1) {//left
			newDirection = 0;
			cowRectangle.x -= speed;
			didMove = true;
		}
		
		if(value == 2) {//right
			newDirection = 1;
			didMove = true;
			cowRectangle.x += speed;	
			}
		
		if(value == 3) {//up
			didMove = true;
			cowRectangle.y -= speed;
			newDirection = 3;
		}
		
		if(value == 4) {//down
			newDirection = 2;
			cowRectangle.y += speed;
			didMove = true;
		}
		
		if(value == 5) {//blink
			newDirection = 4;
			didMove = true;
		}
		
		if(value == 6) {
			newDirection = 5;
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