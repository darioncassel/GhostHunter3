package com.mygdx.game;


import com.badlogic.gdx.Game;


public class GhostHunterGame extends Game {
	
	private GameOverScreen mGameOverScreen;
	private SplashScreen mIntroScreen;
	private MainGameScreen mGameScreen;
	private ResourceManager mResources;
	
	private static com.mygdx.game.GhostHunterGame mInstance;
	
	public static com.mygdx.game.GhostHunterGame getInstance() {
		return mInstance;
	}

	@Override
	public void create() {

		mInstance = this;
		
		mResources = new ResourceManager();
		mResources.loadResources();
		
		mIntroScreen = new SplashScreen();
		mGameScreen = new MainGameScreen();
		mGameOverScreen = new GameOverScreen();
		
		
		setScreen(mIntroScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		mGameScreen.dispose();
		mResources.dispose();
		mGameOverScreen.dispose();
	
	}
	
}
