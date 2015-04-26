package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;


public class BombActor extends DamageableActor {

    public void fireBullets() {
        for(int i=0; i<50; i++) {
            BulletActor bullet = MainGameScreen.getInstance().addBullet();
            float fireAngle = 2 * 3.14f + 5*i;
            float x = getX() + getWidth() * 0.5f;
            float y = getY() + getHeight() * 0.5f;
            x -= getWidth() * 0.75f * Math.sin(fireAngle);
            y += getHeight() * 0.75f * Math.cos(fireAngle);
            bullet.setPosition(x, y);
            bullet.setAngle(fireAngle);
        }
    }

    @Override
    public void damage(float angle) {
        super.damage(angle);
        fireBullets();
        MainGameScreen.getInstance().playerInvincible(10);
    }

    public BombActor() {
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

        Texture bombTexture = ResourceManager.getInstance().bombTexture;

        batch.draw(bombTexture, getX(), getY(), getWidth(), getHeight());

    }
}
