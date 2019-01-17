public interface GameObject {
	public void render(RenderHandler renderer, int xZoom, int yZoom);
	public void update(Game game);
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);
}