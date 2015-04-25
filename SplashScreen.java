package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SplashScreen implements Screen {

	private Stage mStage;

	private static com.mygdx.game.SplashScreen mInstance;

	public static com.mygdx.game.SplashScreen getInstance() {
		return mInstance;
	}

	public SplashScreen() {
		mInstance = this;

		mStage = new Stage();

		for(int i = 0; i < 10; i ++) {
			ObstacleActor obstacle = new ObstacleActor();
			obstacle.calcStartParams();
			mStage.addActor(obstacle);
		}

		TextActor obstaclesTextActor = new TextActor();
		obstaclesTextActor.setText("Ghost Hunter");

		obstaclesTextActor.setFontHeight(Gdx.graphics.getWidth() / 10);


		float x = Gdx.graphics.getWidth() /2 - obstaclesTextActor.getWidth()/2;
		float y = Gdx.graphics.getHeight() /2 - obstaclesTextActor.getHeight()/2;
		obstaclesTextActor.setPosition(x, y);

		mStage.addActor(obstaclesTextActor);		

		TextActor pressAnyTextActor = new TextActor();
		pressAnyTextActor.setText("A DEMO BY DEBDATTA BASU. PRESS ANY KEY TO START!");
		pressAnyTextActor.setFontHeight(Gdx.graphics.getWidth() / 40);

		x = Gdx.graphics.getWidth() /2 - pressAnyTextActor.getWidth()/2;
		y = Gdx.graphics.getWidth() / 30;
		pressAnyTextActor.setPosition(x, y);

		mStage.addActor(pressAnyTextActor);

		mStage.addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {

				MainGameScreen.getInstance().initializeGame();
				GhostHunterGame.getInstance().setScreen(MainGameScreen.getInstance());
				return true;

			}

		});

		Gdx.input.setInputProcessor(mStage);

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
		//mStage.setViewport(width, height);

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
