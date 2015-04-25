package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameOverScreen implements Screen {
	
	private Stage mStage;
    private BitmapFont mFont;
    private Color textColor;
	
	private static GameOverScreen mInstance;
	
	public static GameOverScreen getInstance() {
		return mInstance;
	}

	private TextActor mScoreTextActor;
	
	public GameOverScreen() {
		mInstance = this;
		mStage = new Stage();
        mInstance = this;
        mFont = new BitmapFont();
        textColor = new Color();
        textColor.set(1,1,1,1);
        Label.LabelStyle textStyle = new Label.LabelStyle(mFont, textColor);

		TextActor gameOverTextActor;;
		gameOverTextActor = new TextActor("GAME OVER", textStyle);
		gameOverTextActor.setFontScale(5);
		float x = Gdx.graphics.getWidth() / 2 - gameOverTextActor.getWidth()/2;
		float y = Gdx.graphics.getHeight() / 2 - gameOverTextActor.getHeight()/2;
		gameOverTextActor.setPosition(x, y);
		
		mStage.addActor(gameOverTextActor);
		
		mScoreTextActor = new TextActor("", textStyle);
		mScoreTextActor.setFontScale(5);
		y -= mScoreTextActor.getHeight() + Gdx.graphics.getWidth() / 25;
		mScoreTextActor.setY(y);
		
		mStage.addActor(mScoreTextActor);
		
		TextActor pressAnyTextActor = new TextActor("TOUCH THE SCREEN TO CONTINUE", textStyle);
		pressAnyTextActor.setFontScale(5);
		
		x = Gdx.graphics.getWidth() /2 - pressAnyTextActor.getWidth()/2;
		y = Gdx.graphics.getWidth() / 30;
		pressAnyTextActor.setPosition(x, y);
		
		mStage.addActor(pressAnyTextActor);

        mStage.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                GhostHunterGame.getInstance().setScreen(SplashScreen.getInstance());
                return true;
            }
        });

	}
	
	public void setScore(int score) {
		
		mScoreTextActor.updateText("SCORE : " + score);
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
