package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class SplashScreen implements Screen {

    private Stage mStage = new Stage();
    private static com.mygdx.game.SplashScreen mInstance;
    private BitmapFont mFont;
    private Color textColor;

    public static com.mygdx.game.SplashScreen getInstance() {
        return mInstance;
    }

    public SplashScreen(){
        mInstance = this;
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
        mFont = new BitmapFont();
        textColor = new Color();

        textColor.set(1,1,1,1);
        Label.LabelStyle textStyle = new Label.LabelStyle(mFont, textColor);
        TextActor title = new TextActor("Hello, World!", textStyle);

        mStage.addActor(title);
    }

    @Override
    public void hide() {
        dispose();
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
