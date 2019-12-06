public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int LEVELSTATE = 1;
	public static final int AGGRESSIVESTATE = 2;
	public static final int PASSIVESTATE = 3;
	
	public GameStateManager() {
		
		JukeBox.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	public int getCurrentState(){
		return currentState;
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if(state == LEVELSTATE)
			gameStates[state] = new LevelState(this);
		else if(state == AGGRESSIVESTATE){
			JukeBox.stop("menusong");
			gameStates[state] = new AggressiveState(this);
		}
		else if(state == PASSIVESTATE){
			JukeBox.stop("menusong");
			gameStates[state] = new PassiveState(this);
		}
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[currentState] != null){
			gameStates[currentState].draw(g);
		}
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}