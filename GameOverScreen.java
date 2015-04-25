package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameOverScreen implements Screen {
	
	private Stage mStage;
	
	private static com.mygdx.game.GameOverScreen mInstance;
	
	public static com.mygdx.game.GameOverScreen getInstance() {
		return mInstance;
	}

	private TextActor mScoreTextActor;
	
	public GameOverScreen() {
		mInstance = this;
		mStage = new Stage();

        /*
		TextActor gameOverTextActor;;
		gameOverTextActor = new TextActor();
		gameOverTextActor.setText("GAME OVER");
		gameOverTextActor.setFontHeight(Gdx.graphics.getWidth()/10);
		
		float x = Gdx.graphics.getWidth() / 2 - gameOverTextActor.getWidth()/2;
		float y = Gdx.graphics.getHeight() / 2 - gameOverTextActor.getHeight()/2;
		gameOverTextActor.setPosition(x, y);
		
		mStage.addActor(gameOverTextActor);
		
		mScoreTextActor = new TextActor();
		mScoreTextActor.setFontHeight(Gdx.graphics.getWidth()/30);
		y -= mScoreTextActor.getHeight() + Gdx.graphics.getWidth() / 25;
		
		mScoreTextActor.setY(y);
		
		mStage.addActor(mScoreTextActor);
		
		TextActor pressAnyTextActor = new TextActor();
		pressAnyTextActor.setText("PRESS ANY KEY TO CONTINUE");
		pressAnyTextActor.setFontHeight(Gdx.graphics.getWidth() / 40);
		
		x = Gdx.graphics.getWidth() /2 - pressAnyTextActor.getWidth()/2;
		y = Gdx.graphics.getWidth() / 30;
		pressAnyTextActor.setPosition(x, y);
		
		mStage.addActor(pressAnyTextActor);
		*/
		mStage.addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				GhostHunterGame.getInstance().setScreen(SplashScreen.getInstance());
				return true;
			}
			
		});
		
		Gdx.input.setInputProcessor(mStage);
	}
	
	public void setScore(int score) {
		
		mScoreTextActor.setText("SCORE : " + score);
		float x = Gdx.graphics.getWidth() / 2 - mScoreTextActor.getWidth()/2;
		mScoreTextActor.setX(x);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mStage.act(delta);
		mStage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
        mStage.getViewport().update(width, height);
		
	}
	
	@Override
	public void dispose() {
		mStage.dispose();
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(mStage);
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
	}



}
