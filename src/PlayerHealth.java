public class PlayerHealth implements GameObject {

	private Rectangle heartRectangle;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	private int direction = 0;
	private int x = -200;
	private int y = -200;
	private int counter =0;
	
	public PlayerHealth(Sprite sprite) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		heartRectangle = new Rectangle(9, 9, 9, 9);
		heartRectangle.generateGraphics(3, 0xFF00FF90);
	}
	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimatedRange(direction*8, (direction * 8) + 7);
		}
	}
	
	public void setcoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
		renderer.renderSprite(animatedSprite, x,y , xZoom, yZoom,false);
	}else if(sprite != null){
		renderer.renderSprite(sprite, heartRectangle.x , heartRectangle.y, xZoom, yZoom,false);
	}
	else {
		renderer.renderRectangle(heartRectangle, xZoom, yZoom,false);
	}
	}
	
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public void update(Game game) {
		counter++;
		if (counter == 70) {
		updateDirection();
		counter = 0;
		}
		animatedSprite.update(game);
	}


		public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
			return false;
		}

}
