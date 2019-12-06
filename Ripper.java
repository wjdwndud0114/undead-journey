import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ripper extends Enemy {
	
	private Player player;

	private boolean attacking;
	
	private static final int XIDLE = 0;
	private static final int NIDLE = 1;
	private static final int SIDLE = 2;
	private static final int XWALKING = 3;
	private static final int NWALKING = 4;
	private static final int SWALKING = 5;
	private static final int XATTACKING = 6;
	private static final int NATTACKING = 7;
	private static final int SATTACKING = 8;
	
	
	private int attackTick;
	private int attackDelay = 60;
	
	public Ripper(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health = maxHealth = 4;
		
		width = 30;
		height = 30;
		cwidth = 26;
		cheight = 26;
		
		damage = 6;
		moveSpeed = maxSpeed = stopSpeed = 1.0;
		
		attackTick = 0;
		facingS = true;
		setAnimation(SIDLE, 15);
	}
	
	private void setAnimation(int i, int delay) {
		currentAction = i;
		animation.setFrames(Content.Ripper[i]);
		animation.setDelay(delay);
	}
	
	private void getNextPosition() {
		E = W = S = N = false;
		
		if(player.x > x+player.getCWidth()) E = true;
		else if(player.x < x-player.getCWidth()) W = true;
		if(player.y > y+player.getCHeight()-10) S = true;
		else if(player.y < y-player.getCHeight()+10) N = true;
	
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
		
		if(attacking){
			dx = dy = 0;
		}
	}
	
	public void update() {
		
		getNextPosition();
		checkMapObjectCollision(enemies);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// update animation
		if(dx == 0) x = (int)x;
		
		attackTick++;
		if(getRectangle().intersects(player.getRectangle()) && attackTick >= attackDelay){
			attacking = true;
			if(!player.isKnocked()){
				player.hit(damage, facingN, facingS, facingW, facingE);
			}
			attackTick = 0;
		}
		
		if(currentAction == XATTACKING || currentAction == NATTACKING || currentAction == SATTACKING) {
			if(animation.hasPlayedOnce()) {
				attacking = false;
			}
		}
		
		if(attacking){
			if(currentAction != XATTACKING && currentAction != NATTACKING && currentAction != SATTACKING) {
				//JukeBox.play("attack");
				if(facingW || facingE) setAnimation(XATTACKING, 6);
				if(facingN) setAnimation(NATTACKING, 6);
				if(facingS) setAnimation(SATTACKING, 6);
			}
		}
		else if(W || E){
			if(currentAction != XWALKING){
				setAnimation(XWALKING, 5);
			}
		}
		else if(N){
			if(currentAction != NWALKING){
				setAnimation(NWALKING, 5);
			}
		}
		else if(S){
			if(currentAction != SWALKING){
				setAnimation(SWALKING, 5);
			}
		}
		else if((facingE || facingW) && currentAction != XIDLE) {
				setAnimation(XIDLE, 15);
		}
		else if(facingN && currentAction != NIDLE) {
				setAnimation(NIDLE, 15);
		}
		else if(facingS && currentAction != SIDLE) {
				setAnimation(SIDLE, 15);
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
	
	public void draw(Graphics2D g) {
		super.draw(g);
		
	}
	
}
