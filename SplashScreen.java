package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class SplashScreen implements Screen {

    private Stage mStage = new Stage();
    private static SplashScreen mInstance;
    private BitmapFont mFont;
    private Color textColor;
    private SpriteBatch batch;
    private Sprite splash;

    public static SplashScreen getInstance() {
        return mInstance;
    }

    public SplashScreen(){
        mInstance = this;
        mFont = new BitmapFont();
        
        textColor = new Color();
        textColor.set(1,1,1,1);
        Label.LabelStyle textStyle = new Label.LabelStyle(mFont, textColor);



        TextActor title = new TextActor("GHOST HUNTER", textStyle);
        title.setFontScale(5);
        float y = Gdx.graphics.getWidth() /2 - title.getWidth()/4;
        float x = Gdx.graphics.getHeight() /2 +100;
        title.setPosition(x, y);

        TextActor touchStart = new TextActor("TOUCH THE SCREEN TO START!", textStyle);
        touchStart.setFontScale(5);
        x = Gdx.graphics.getWidth() / 2 - touchStart.getWidth()-300;
        y = Gdx.graphics.getHeight() / 2;
        touchStart.setPosition(x, y);

        TextActor names = new TextActor("Darion Cassel, Gopal Hari, Zane Laughlin, Yun Nam", textStyle);
        names.setFontScale(5);
        x = Gdx.graphics.getWidth()/2 - touchStart.getWidth()-600;
        y = (Gdx.graphics.getHeight()/20) + 50;
        names.setPosition(x,y);

        TextActor subtitle = new TextActor("UVA CS2110 Spring 2015", textStyle);
        subtitle.setFontScale(4);
        x = Gdx.graphics.getWidth()/2 - touchStart.getWidth()-115;
        y = Gdx.graphics.getHeight()/5;
        subtitle.setPosition(x,y);

        mStage.addActor(title);
        mStage.addActor(touchStart);
        mStage.addActor(names);
        mStage.addActor(subtitle);

        mStage.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            MainGameScreen.getInstance().initializeGame();
            GhostHunterGame.getInstance().setScreen(MainGameScreen.getInstance());
            return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mStage.act();
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
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

    @Override
    public void dispose() {
        mStage.dispose();
    }

}
