import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Teleport extends MapObject {
	
	private BufferedImage[] sprites;
	
	public Teleport(TileMap tm) {
		super(tm);
		facingN = true;
		width = height = 50;
		cwidth = 50;
		cheight = 50;
		// try {
			// BufferedImage spritesheet = ImageIO.read(
				// getClass().getResourceAsStream("Teleport.gif")
			// );
			// sprites = new BufferedImage[9];
			// for(int i = 0; i < sprites.length; i++) {
				// sprites[i] = spritesheet.getSubimage(
					// i * width, 0, width, height
				// );
			// }
			// animation.setFrames(sprites);
			// animation.setDelay(1);
		// }
		// catch(Exception e) {
			// e.printStackTrace();
		// }
	}
	
	public void update() {
		//animation.update();
	}
	
	public void draw(Graphics2D g) {
		// Rectangle r = getRectangle();
		// r.x += xmap;
		// r.y += ymap;
		// g.draw(r);
		// g.fillRect(r.x,r.x,r.width,r.height);
		// super.draw(g);
	}
	
}
