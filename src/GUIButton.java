import java.awt.Dimension;
import java.awt.Toolkit;

public abstract class GUIButton implements GameObject
{
	protected Sprite sprite;
	protected Rectangle rect;
	protected boolean fixed;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int SCREENX = (int) screenSize.getWidth();
	private int SCREENY = (int) screenSize.getHeight();

	public GUIButton(Sprite sprite, Rectangle rect, Boolean fixed)
	{
		this.sprite = sprite;
		this.rect = rect;
		this.fixed = fixed;
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {}

	public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect){
		renderer.renderSprite(sprite, rect.x + interfaceRect.y, rect.y + interfaceRect.y, xZoom, yZoom, fixed);
		
	}

	public void update(Game game) {}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom)
	{
		if(mouseRectangle.intersects(rect)) {
			activate();
			return true;
		}

		return false;
	}

	public abstract void activate();

}