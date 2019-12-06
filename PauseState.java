import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
public class PauseState extends GameState {
	
	private Font font;
	private int currentChoice = 0;
	private BufferedImage head;
	private String[] options = {
		"Resume",
		"Main Menu"
	};
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		try{
			// load gunz
			head = ImageIO.read(
				getClass().getResourceAsStream("Hud.gif")
			);
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
		// fonts
		font = new Font("Century Gothic", Font.PLAIN, 14);
		JukeBox.load("pause.wav", "pause");
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(.9f,.2f,.2f,.9f));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", 291, 90);
		g.drawString(options[0], 310, 165);
		g.drawString(options[1], 298, 185);
		if(currentChoice == 0){
			g.drawImage(head, 366, 156, head.getWidth(), head.getHeight(), null);
			g.drawImage(head, 306, 156, -head.getWidth(), head.getHeight(), null);
		}
		else if(currentChoice == 1){
			g.drawImage(head, 380, 178, head.getWidth(), head.getHeight(), null);
			g.drawImage(head, 294, 178, -head.getWidth(), head.getHeight(), null);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			if(gsm.getCurrentState() == 2 || gsm.getCurrentState() == 3){
				JukeBox.loop("rain");
			}
			JukeBox.play("pause");
			gsm.setPaused(false);
		}
		else if(currentChoice == 1) {
			gsm.setPaused(false);
			if(gsm.getCurrentState() == 2 || gsm.getCurrentState() == 3){
				JukeBox.close("rain");
			}
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)){
			if(gsm.getCurrentState() == 2 || gsm.getCurrentState() == 3){
				JukeBox.loop("rain");
			}
			JukeBox.play("pause");
			gsm.setPaused(false);
		}
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
		if(Keys.isPressed(Keys.ENTER)){
			select();
			JukeBox.play("pause");
		}
	}

}
