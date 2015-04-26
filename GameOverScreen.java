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

	private TextActor score;
	
	public GameOverScreen() {
		mInstance = this;
		mStage = new Stage();
        mInstance = this;
        mFont = new BitmapFont();
        textColor = new Color();
        textColor.set(1,1,1,1);
        Label.LabelStyle textStyle = new Label.LabelStyle(mFont, textColor);

		TextActor gameOverText;;
		gameOverText = new TextActor("GAME OVER", textStyle);
		gameOverText.setFontScale(5);
        float y = Gdx.graphics.getWidth() /2 - gameOverText.getWidth()/4;
        float x = Gdx.graphics.getHeight() /2 +100;
		gameOverText.setPosition(x, y);
		
		mStage.addActor(gameOverText);
		
		score = new TextActor("", textStyle);
		score.setFontScale(5);
        x = Gdx.graphics.getWidth() / 2 - score.getWidth()/4;
		y -= score.getHeight() + Gdx.graphics.getWidth() / 25;
		score.setPosition(x, y);
		
		mStage.addActor(score);
		
		TextActor touchContinueText = new TextActor("TOUCH THE SCREEN TO CONTINUE", textStyle);
		touchContinueText.setFontScale(5);
        x = Gdx.graphics.getWidth() / 2 - touchContinueText.getWidth()-300;
        y = Gdx.graphics.getHeight() / 2;
		touchContinueText.setPosition(x, y);
		
		mStage.addActor(touchContinueText);

        mStage.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                GhostHunterGame.getInstance().setScreen(SplashScreen.getInstance());
                return true;
            }
        });

	}
	
	public void setScore(int score) {
		
		this.score.updateText("SCORE : " + score);
		float x = Gdx.graphics.getWidth() / 2 - this.score.getWidth()/2;
		this.score.setX(x);
		
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
