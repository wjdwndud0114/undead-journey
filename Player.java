import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends MapObject {

	private ArrayList<Enemy> enemies;

	private int lives;
	private int health;
	private int maxHealth;
	private int score;
	private long time;
	
	private boolean knocked;
	
	private Rectangle ar;
	private Rectangle aur;
	private Rectangle cr;
	
	private static final int XIDLE = 0;
	private static final int NIDLE = 1;
	private static final int SIDLE = 2;
	private static final int XWALKING = 3;
	private static final int NWALKING = 4;
	private static final int SWALKING = 5;
	private static final int XKNOCKBACK = 6;
	private static final int NKNOCKBACK = 7;
	private static final int SKNOCKBACK = 8;
	
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUMFRAMES = {
		5, 4, 5, 6, 4, 4, 4, 4, 4
	};
	private final int[] FRAMEWIDTHS = {
		40, 40, 40, 40, 40, 40, 40, 40, 40
	};
	private final int[] FRAMEHEIGHTS = {
		40, 40, 40, 40, 40, 40, 40, 40, 40
	};
	private final int[] SPRITEDELAYS = {
		6, 6, 6, 3, 3, 3, 4, 4, 4
	};
	
	public Player(TileMap tm, int mode){
		super(tm);
		
		ar = new Rectangle(0, 0, 0, 0);
		ar.width = 30;
		ar.height = 20;
		aur = new Rectangle((int)x - 15, (int)y - 45, 30, 30);
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;
		
		width = 30;
		height = 30;
		cwidth = 15;
		cheight = 38;
		
		moveSpeed = maxSpeed = stopSpeed = 1.7;
		
		facingS = true;
		
		lives = 1;
		health = maxHealth = 100;
		
		try {
			BufferedImage spritesheet = null;
			if(mode == 1){
				spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
						"PlayerSprites.gif"
					)
				);
			}
			else if(mode == 0){
				spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
						"PassivePlayer.gif"
					)
				);
			}
			int count = 0;
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(
						j * FRAMEWIDTHS[i],
						count,
						FRAMEWIDTHS[i],
						FRAMEHEIGHTS[i]
					);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		setAnimation(NIDLE);
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	
	public void setDead() {
		health = 0;
		stop();
	}
	
	public void hit(int damage, boolean n, boolean s, boolean w, boolean e){
		//JukeBox.play("hit");
		stop();
		health -= damage;
		if(health < 0) health = 0;
		knocked = true;
		if(n) dy = -3;
		else if(s) dy = 3;
		else if(w) dx = -3;
		else if(e) dx = 3;
	}
	
	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}
	
	public long getTime() { return time; }
	public void setTime(long t) { time = t; }
	public void setHealth(int i) { health = i; }
	public void setLives(int i) { lives = i; }
	public void gainLife() { lives++; }
	public void loseLife() { lives--; }
	public int getLives() { return lives; }
	public double getMoveSpeed() { return moveSpeed; }
	public double getStopSpeed() { return stopSpeed; }
	public double getMaxSpeed(){ return maxSpeed; }
	public void setSpeed(double i) { moveSpeed = maxSpeed = stopSpeed = i;}
	public void addSpeed(double i) { moveSpeed += i; maxSpeed = stopSpeed = moveSpeed;}
	
	public void increaseScore(int score) {
		this.score += score; 
	}
	
	public int getScore() { return score; }
	
	public void reset() {
		health = maxHealth;
		facingN = true;
		currentAction = -1;
		stop();
	}
	
	public void stop(){
		N = W = E = S = false;
	}
	
	private void getNextPosition() {
		if(knocked){
			return;
		}
		
		double maxSpeed = this.maxSpeed;
		
		// movement
		if(W) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(E) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		if(N) {
			dy -= moveSpeed;
			if(dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		}
		else if(S) {
			dy += moveSpeed;
			if(dy > maxSpeed) {
				dy = maxSpeed;
			}
		}
		else {
			if(dy > 0) {
				dy -= stopSpeed;
				if(dy < 0) {
					dy = 0;
				}
			}
			else if(dy < 0) {
				dy += stopSpeed;
				if(dy > 0) {
					dy = 0;
				}
			}
		}
	}
	
	public void init(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	private void setAnimation(int i) {
		currentAction = i;
		animation.setFrames(sprites.get(currentAction));
		animation.setDelay(SPRITEDELAYS[currentAction]);
		width = FRAMEWIDTHS[currentAction];
		height = FRAMEHEIGHTS[currentAction];
	}
	
	public boolean isKnocked(){ return knocked; }
	
	public void update(){
		time++;
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if(dx == 0) x = (int)x;
		
		if(currentAction == XKNOCKBACK || currentAction == NKNOCKBACK || currentAction == SKNOCKBACK){
			if(animation.hasPlayedOnce()) {
				knocked = false;
			}
		}
		
		if(knocked) {
			if(currentAction != XKNOCKBACK && currentAction != NKNOCKBACK && currentAction != SKNOCKBACK) {
				if(facingE || facingW) setAnimation(XKNOCKBACK);
				else if(facingN) setAnimation(NKNOCKBACK);
				else if(facingS) setAnimation(SKNOCKBACK);
			}
		}
		else if(W || E){
			if(currentAction != XWALKING){
				setAnimation(XWALKING);
			}
		}
		else if(N){
			if(currentAction != NWALKING){
				setAnimation(NWALKING);
			}
		}
		else if(S){
			if(currentAction != SWALKING){
				setAnimation(SWALKING);
			}
		}
		else if((facingE || facingW) && currentAction != XIDLE) {
				setAnimation(XIDLE);
		}
		else if(facingN && currentAction != NIDLE) {
				setAnimation(NIDLE);
		}
		else if(facingS && currentAction != SIDLE) {
				setAnimation(SIDLE);
		}
		animation.update();
		
		if(N) setDirection("N");
		if(S) setDirection("S");
		if(E) setDirection("E");
		if(W) setDirection("W");
	}
	
	public void setDirection(String dir){
		facingN = facingS = facingE = facingW = false;
		switch (dir){
			case "N": 
				facingN = true;
				break;
			case "S":
				facingS = true;
				break;
			case "E":
				facingE = true;
				break;
			case "W":
				facingW = true;
				break;
		}
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
	}
}