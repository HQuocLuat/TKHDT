package tiles;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage img;
	private boolean collision = false;
	
	public Tile() {
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public BufferedImage getImg() {
		return img;
	}
	
}
