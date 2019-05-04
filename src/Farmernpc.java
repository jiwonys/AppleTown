public class Farmernpc implements GameObject {

	private Rectangle farmernpcRectangle;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	int x;
	int y;
	
	public Farmernpc(Sprite sprite) {
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		farmernpcRectangle = new Rectangle(32, 32, 32, 32);
		farmernpcRectangle.generateGraphics(3, 0xFF00FF90);
	}
	
	
	public void setcoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
		renderer.renderSprite(animatedSprite, x,y , xZoom, yZoom,false);
	}else if(sprite != null){
		renderer.renderSprite(sprite, farmernpcRectangle.x , farmernpcRectangle.y, xZoom, yZoom,false);
	}
	else {
		renderer.renderRectangle(farmernpcRectangle, xZoom, yZoom,false);
	}
	}
	
	public void update(Game game) {
		animatedSprite.update(game);
	}


		public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
			return false;
		}

}