public class PlayerSave {
	
	private static int lives = 1;
	private static int health = 100;
	private static double speed = 1.7;
	private static double maxSpeed = 1.7;
	private static double stopSpeed = 1.7;
	private static long time = 0;
	
	public static void init() {
		lives = 1;
		health = 100;
		speed = maxSpeed = stopSpeed = 1.7;
		time = 0;
	}
	public static double getSpeed() { return speed; }
	public static void setSpeed(double i) { speed = maxSpeed = stopSpeed = i; }
	
	public static int getLives() { return lives; }
	public static void setLives(int i) { lives = i; }
	
	public static int getHealth() { return health; }
	public static void setHealth(int i) { health = i; }
	
	public static long getTime() { return time; }
	public static void setTime(long t) { time = t; }
	
}
