import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class MenuState extends GameState {

	private Background bg;
	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Font font2;
	
	public MenuState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			//bg
			bg = new Background("menubg.gif", 1);
			bg.setVector(5, 0);
			
			// load gunz
			head = ImageIO.read(
				getClass().getResourceAsStream("Hud.gif")
			);//.getSubimage(0, 0, 15, 9);
			
			// titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 34);
			font = new Font("Arial", Font.PLAIN, 16);
			font2 = new Font("Arial", Font.PLAIN, 12);
			
			// load sound fx
			JukeBox.load("menuoption.wav", "menuoption");
			JukeBox.load("menuselect.wav", "menuselect");
			JukeBox.load("menusong.wav", "menusong");
			JukeBox.loop("menusong");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
		// check keys
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("U n d e a d  J o u r n e y", 180, 100);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(options[0], 327, 165);
		g.drawString(options[1], 329, 185);
		
		// draw floating head
		if(currentChoice == 0){
			g.drawImage(head, 365, 156, head.getWidth(), head.getHeight(), null);
			g.drawImage(head, 324, 156, -head.getWidth(), head.getHeight(), null);
		}
		else if(currentChoice == 1){
			g.drawImage(head, 359, 178, head.getWidth(), head.getHeight(), null);
			g.drawImage(head, 325, 178, -head.getWidth(), head.getHeight(), null);
		}
		
		// other
		g.setFont(font2);
		g.drawString("2015 Kevin Jeong", 575, 350);
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(GameStateManager.LEVELSTATE);
		}
		else if(currentChoice == 1) {
			System.exit(0);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		/*if(Keys.isPressed(Keys.ESCAPE)){
			gsm.setPaused(true);
			JukeBox.play("pause");
		}*/
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










