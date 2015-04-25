package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class MainGameScreen implements Screen {

	private Stage mStage;
	private Stage mHudStage;
	private TextActor mHudScoreActor;
    private BitmapFont mFont;
    private Color textColor;
    private World world;

    private static com.mygdx.game.MainGameScreen mInstance;
    public static com.mygdx.game.MainGameScreen getInstance() {
        return mInstance;
    }

	public MainGameScreen() {
		mInstance = this;
		mStage = new Stage();
		mHudStage = new Stage();

        mFont = new BitmapFont();
        textColor = new Color();
        textColor.set(1,1,1,1);
        Label.LabelStyle textStyle = new Label.LabelStyle(mFont, textColor);

		float padding = Gdx.graphics.getHeight() / 50;
		float x = padding;
		float y = Gdx.graphics.getHeight() - padding;
		mHudScoreActor = new TextActor("SCORE : 0", textStyle);
		mHudScoreActor.setFontScale(3);
		mHudScoreActor.setPosition(x, y);

		mHudStage.addActor(mHudScoreActor);

	}

	public void initializeGame() {
        world = new World(new Vector2(0, -0), true);
        BaseActor player = new BaseActor(new Texture("data/player.png"), world);
        mStage.addActor(player);
	}


	@Override
	public void dispose() {
        mStage.dispose();
        world.dispose();
    }


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1f/60f, 6, 2);

		mStage.act(delta);
		mStage.draw();
		mHudStage.draw();
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
