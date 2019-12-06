import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PassiveState extends GameState {
	
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private boolean eventFinish;
	private boolean eventDead;
	private ArrayList<Rectangle> tb;
	
	private BufferedImage ForestText;
	private Title title;
	private Title subtitle;
	private Teleport teleport;
	
	public PassiveState(GameStateManager gsm) {
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
		
		player = new Player(tileMap, 0);
		player.setPosition(53,57);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
		
		enemies = new ArrayList<Enemy>();
		populateEnemies();
		
		player.init(enemies);
		hud = new HUD(player);
		
		try {
			ForestText = ImageIO.read(
				getClass().getResourceAsStream("Title.gif")
			);
			title = new Title(ForestText.getSubimage(0, 0, 651, 50));
			title.sety(50);
			subtitle = new Title(ForestText.getSubimage(0, 54, 205, 43));
			subtitle.sety(95);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		teleport = new Teleport(tileMap);
		teleport.setPosition(1125,2760);
		
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
	}
	
	private void populateEnemies() {
		enemies.clear();
		
		Ripper r;
		
		r = new Ripper(tileMap, player);
		r.setPosition(450, 56);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(154,303);
		enemies.add(r);	
		r = new Ripper(tileMap, player);
		r.setPosition(160,354);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(376, 900);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(130,1181);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(319,1593);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(1041,1720);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(1238,1432);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(1000,920);
		enemies.add(r);
		r = new Ripper(tileMap, player);
		r.setPosition(1024,1411);
		enemies.add(r);
		
		for(Enemy i : enemies){
			i.updateEnemies(enemies);
		}
	}
	
	public void update() {
		
		handleInput();
		
		if(teleport.intersects(player)) eventFinish = blockInput = true;
		if(player.getHealth() == 0) eventDead = blockInput = true;
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		
		if(title != null) {
			title.update();
			if(title.shouldRemove()) title = null;
		}
		if(subtitle != null) {
			subtitle.update();
			if(subtitle.shouldRemove()) subtitle = null;
		}
		
		player.update();
		
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.update();
		tileMap.fixBounds();
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				//explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}
		teleport.update();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		tileMap.draw(g);
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		player.draw(g);
		hud.draw(g);
		teleport.draw(g);
		
		// draw title
		if(title != null) title.draw(g);
		if(subtitle != null) subtitle.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)){
			JukeBox.stop("rain");
			JukeBox.play("pause");
			gsm.setPaused(true);
		}
		if(blockInput || player.getHealth() == 0) return;
		player.setN(Keys.keyState[Keys.UP]);
		player.setW(Keys.keyState[Keys.LEFT]);
		player.setS(Keys.keyState[Keys.DOWN]);
		player.setE(Keys.keyState[Keys.RIGHT]);
		
		//enemies.get(0).setN(Keys.keyState[Keys.W]);
		//enemies.get(0).setW(Keys.keyState[Keys.A]);
		//enemies.get(0).setS(Keys.keyState[Keys.S]);
		//enemies.get(0).setE(Keys.keyState[Keys.D]);
	}

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
	
	// reset level
	private void reset() {
		player.reset();
		player.setPosition(70,70);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		eventStart = true;
		eventStart();
		title = new Title(ForestText.getSubimage(0, 0, 651, 50));
		title.sety(50);
		subtitle = new Title(ForestText.getSubimage(0, 54, 205, 43));
		subtitle.sety(95);
	}
	
	// level started
	private void eventStart() {
		//titles
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 30) title.begin();
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			subtitle.begin();
			tb.clear();
		}
	}
	
	// player has died
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}
	
	// finished level
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1){
			//JukeBox.play("teleport");
			player.stop();
		}
		else if(eventCount == 30){
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH/2,GamePanel.HEIGHT /2, 0, 0));
		}
		else if(eventCount > 30){
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			//JukeBox.stop("teleport");
		}
		if(eventCount == 80){
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			//gsm.setState(GameStateManager.PassiveState2);
		}
	}
}