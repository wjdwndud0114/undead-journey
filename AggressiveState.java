import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AggressiveState extends GameState {
	
	private Player player;
	private TileMap tileMap;
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	
	public AggressiveState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		JukeBox.load("rain.wav","rain");
		JukeBox.loop("rain");
		
		tileMap = new TileMap(50);
		tileMap.loadTiles("grass.gif");
		tileMap.loadMap("grass.map");
		tileMap.setPosition(140,0);
		tileMap.setBounds(
			tileMap.getWidth()-1 * tileMap.getTileSize() + tileMap.getTileSize(),
			tileMap.getHeight()-1 * tileMap.getTileSize() + tileMap.getTileSize(),
			0, 0
		);
		tileMap.setTween(1);
		
		player = new Player(tileMap, 1);
		player.setPosition(100, 100);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
	}
	
	private void populateEnemies() {
		
	}
	
	public void update() {
		
		handleInput();
		player.update();
		
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		//tileMap.update();
		tileMap.fixBounds();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		tileMap.draw(g);
		player.draw(g);
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)){
			JukeBox.stop("rain");
			JukeBox.play("pause");
			gsm.setPaused(true);
		}
		player.setN(Keys.keyState[Keys.UP]);
		player.setW(Keys.keyState[Keys.LEFT]);
		player.setS(Keys.keyState[Keys.DOWN]);
		player.setE(Keys.keyState[Keys.RIGHT]);
	}

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
	
	// reset level
	private void reset() {
		
	}
	
	// level started
	private void eventStart() {
		
	}
	
	// player has died
	private void eventDead() {
		
	}
	
	// finished level
	private void eventFinish() {
	
	}
}