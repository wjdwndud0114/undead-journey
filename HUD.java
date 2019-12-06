import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	
	// private BufferedImage life;
	private Rectangle HealthBar;
	private Rectangle Health;
	
	public HUD(Player p) {
		player = p;
		// try {
			// life = ImageIO.read(
				// getClass().getResourceAsStream(
					// "Hud.gif"
				// )
			// );
		// }
		// catch(Exception e) {
			// e.printStackTrace();
		// }
	}
	
	public void draw(Graphics2D g) {
		Health = new Rectangle(11,6,(int)(1.5*player.getHealth())-1,14);
		g.setColor(new Color(6,252,6));
		g.fillRect(Health.x,Health.y,Health.width,Health.height);
		HealthBar = new Rectangle(10,5,(int)(1.5*player.getMaxHealth()),15);
		g.setColor(java.awt.Color.BLACK);
		g.draw(HealthBar);
		g.drawString("HP: "+player.getHealth()+"/"+player.getMaxHealth(),(int)(1.5*player.getMaxHealth()/2)-25,16);
		// for(int i = 0; i < player.getHealth(); i++) {
			// g.drawImage(heart, 10 + i * 15, 10, null);
		// }
		// for(int i = 0; i < player.getLives(); i++) {
			// g.drawImage(life, 10 + i * 15, 25, null);
		// }
		g.setColor(java.awt.Color.WHITE);
		g.drawString(player.getTimeToString(), 290, 15);
	}
	
}













