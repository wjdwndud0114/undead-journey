import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class LevelState extends GameState {

	private Background bg;
	private BufferedImage pistol;
	private BufferedImage doubleak;
	private BufferedImage shoes;
	
	private int currentChoice = 0;
	private String[] options = {
		"Aggressive",
		"Passive",
		"Back"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Font font2;
	
	public LevelState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			//bg
			bg = new Background("menubg.gif", 1);
			bg.setVector(5, 0);
			
			// load gunz
			pistol = ImageIO.read(
				getClass().getResourceAsStream("Hud.gif")
			);
			
			doubleak = ImageIO.read(
				getClass().getResourceAsStream("doubleak.gif")
			);
			
			shoes = ImageIO.read(
				getClass().getResourceAsStream("shoes.gif")
			);
			
			// titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 34);
			font = new Font("Arial", Font.PLAIN, 16);
			font2 = new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Choose Style", 80, 100);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(options[0], 100, 165); 
		g.drawString(options[1], 100, 185);
		g.drawString(options[2], 100, 213);
		
		// draw gun pointers
		if(currentChoice == 0){
			g.drawImage(doubleak, 390, 80, 200, 200, null);
			g.drawImage(pistol, 181, 156, pistol.getWidth(), pistol.getHeight(), null);
			g.drawImage(pistol, 98, 156, -pistol.getWidth(), pistol.getHeight(), null);
		}
		else if(currentChoice == 1){
			g.drawImage(shoes, 390, 80, 230, 230, null);
			g.drawImage(pistol, 158, 178, pistol.getWidth(), pistol.getHeight(), null);
			g.drawImage(pistol, 98, 178, -pistol.getWidth(), pistol.getHeight(), null);
		}
		else if(currentChoice == 2){
			g.drawImage(pistol, 137, 204, pistol.getWidth(), pistol.getHeight(), null);
			g.drawImage(pistol, 98, 204, -pistol.getWidth(), pistol.getHeight(), null);
		}
		
		// other
		g.setFont(font2);
		g.drawString("2015 Kevin Jeong", 575, 350);
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(GameStateManager.AGGRESSIVESTATE);
		}
		else if(currentChoice == 1){
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(GameStateManager.PASSIVESTATE);
		}
		else if(currentChoice == 2) {
			JukeBox.play("menuselect");
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				JukeBox.play("menuoption", 0);
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) {
				JukeBox.play("menuoption", 0);
				currentChoice++;
			}
		}
	}
	
}










