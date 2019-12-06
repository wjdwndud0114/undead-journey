import java.util.ArrayList;
public class Enemy extends MapObject {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean remove;
	protected ArrayList<Enemy> enemies;
	
	public Enemy(TileMap tm) {
		super(tm);
		remove = false;
	}
	
	public void updateEnemies(ArrayList<Enemy> enemies){
		this.enemies = enemies;
	}
	
	public boolean isDead() { return dead; }
	public boolean shouldRemove() { return remove; }
	
	public int getDamage() { return damage; }
	
	public void hit(int damage) {
		if(dead) return;
		health -= damage;
		if(health <= 0){
			health = 0;
			dead = true;
			remove = true;
		}
	}
	
	public void update() {}
	
}














