package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class LifepackActor extends DamageableActor {

    @Override
    public void damage(float angle) {
        super.damage(angle);
        if(MainGameScreen.getInstance().mNumLives<3) {
            MainGameScreen.getInstance().mNumLives++;
            MainGameScreen.getInstance().updateLives();
        }
    }

    public LifepackActor() {
        float size = Gdx.graphics.getWidth() / 20;
        setWrapEdge(true);
        setSize(size, size);
        randomizePositionOnEdge();
        randomizeSpeed();
        randomizeAngle();
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        float x = getX();
        float y = getY();
        x -= deltaTime * mSpeed  * Math.sin(mAngle);
        y += deltaTime * mSpeed  * Math.cos(mAngle);
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(getColor().r, getColor().g, getColor().b,
                getColor().a * parentAlpha);

        Texture lifepackTexture = ResourceManager.getInstance().lifepackTexture;

        batch.draw(lifepackTexture, getX(), getY(), getWidth(), getHeight());

    }
}
