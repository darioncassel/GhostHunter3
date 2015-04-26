package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GhostActor extends DamageableActor {

	private static final Random mRandomizeTime = new Random(158);
	private static final Random mRandomizeFireAngle = new Random(170);

	private float getNextDirectionChangeTime() {
		return 8 + 8 * mRandomizeTime.nextFloat();
	}

	private float getNextShootTime() {
		return 0.2f + 0.6f * mRandomizeTime.nextFloat();
	}

	public void fireBullet() {

		BulletActor bullet = MainGameScreen.getInstance().addBullet();

		float fireAngle = 2 * 3.14f * mRandomizeFireAngle.nextFloat();

		float x = getX() + getWidth() * 0.5f;
		float y = getY() + getHeight() * 0.5f;


		x -= getWidth()* 0.75f * Math.sin(fireAngle);
		y += getHeight() * 0.75f * Math.cos(fireAngle);

		bullet.setPosition(x, y);
		bullet.setAngle(fireAngle);
	}

	public void queueFireBullet() {
		addAction(Actions.sequence(Actions.delay(getNextShootTime()), Actions.run(new Runnable() {

			@Override
			public void run() {
				fireBullet();
				queueFireBullet();
			}
		})));
	}

	public void queueDirectionChange() {
		addAction(Actions.sequence(Actions.delay(getNextDirectionChangeTime()), Actions.run(new Runnable() {

			@Override
			public void run() {
				randomizeAngle();
				queueDirectionChange();
			}
		})));
	}

	@Override
	public void damage(float angle) {
		super.damage(angle);
        MainGameScreen.getInstance().incrementScore();
        MainGameScreen.getInstance().queueGhostSpawn();
        MainGameScreen.getInstance().addBomb();
	}


	public GhostActor() {
		float size = Gdx.graphics.getWidth() / 15;

		setWrapEdge(true);
		setSize(size, size);
		randomizePositionOnEdge();
		randomizeSpeed();
		randomizeAngle();

		queueDirectionChange();
		queueFireBullet();
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

		Texture ghostTexture = ResourceManager.getInstance().ghostTexture;


		batch.draw(ghostTexture, getX(), getY(), getWidth(), getHeight());

	}

}
