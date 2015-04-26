package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class ObstacleActor extends DamageableActor {

	private static final Random mTextureRandomizer = new Random(23);
	private static final Random mSizeRandomizer = new Random(95);
	private static final Random mExplosionRandomizer = new Random(196);

	private static int mNumObstaclesKilled = 0;
	private static int mNextObstacleSize = 3;

	private Texture mTexture;
	private int mSizeType = 0;

	public void setSizeType(int sizeType) {
		mSizeType = sizeType;
		float floatSize = sizeType * Gdx.graphics.getWidth() / 35f;
		setSize(floatSize, floatSize);
	}

	public void calcStartParams() {

		setSizeType(mNextObstacleSize);
		mNextObstacleSize = 1 + mSizeRandomizer.nextInt(3);
		randomizeAngle();
		randomizeSpeed();
		randomizePositionOnEdge();

	}


	@Override
	public void damage(float angle) {

		super.damage(angle);
		int targetSizeType = mSizeType - 1;

		MainGameScreen.getInstance().incrementScore();

		if(targetSizeType != 0) {


			float explosionAngle = angle + 3.14f / 2;

			float minIntensity = Gdx.graphics.getWidth() * 0.01f;
			float intensityFactor = Gdx.graphics.getWidth() * 0.02f;

			float explosionIntensity = minIntensity + intensityFactor * mExplosionRandomizer.nextFloat();
			float bulletIntensity = explosionIntensity * 1.5f;

			float speed0X = (float) (-mSpeed * Math.sin(mAngle));
			float speed0Y = (float) (mSpeed * Math.cos(mAngle));

			float speed1X = speed0X;
			float speed1Y = speed0Y;


			float pos0X = getX();
			float pos0Y = getY();

			float pos1X = pos0X;
			float pos1Y = pos0Y;

			float speedVarX = (float) (-explosionIntensity * Math.sin(explosionAngle) + bulletIntensity * Math.sin(angle));
			float speedVarY = (float) (explosionIntensity * Math.cos(explosionAngle) - bulletIntensity * Math.cos(angle));

			float posVarX = (float) (-getWidth() / 2.5 * Math.sin(explosionAngle));
			float posVarY = (float)  (getWidth() / 2.5 * Math.cos(explosionAngle));

			pos0X += posVarX;
			pos0Y += posVarY;



			speed0X += speedVarX;
			speed0Y += speedVarY;

			float speed0 = (float) Math.sqrt(speed0X * speed0X + speed0Y * speed0Y);
			float angle0 = (float) Math.atan(-speed0X / speed0Y);


			pos1X -= posVarX;
			pos1Y -= posVarY;

			speed1X -= speedVarX;
			speed1Y -= speedVarY;

			float speed1 = (float) Math.sqrt(speed1X * speed1X + speed1Y * speed1Y);
			float angle1 = (float) Math.atan(-speed1X / speed1Y);

			if(angle > 3.14f) {
				angle0 += 3.14f;
				angle1 += 3.14f;
			}
            /*
			ObstacleActor obstacle0 = MainGameScreen.getInstance().addObstacle();
			obstacle0.setSizeType(targetSizeType);
			obstacle0.setParams(angle0, speed0);
			obstacle0.setPosition(pos0X, pos0Y);

			ObstacleActor obstacle1 = MainGameScreen.getInstance().addObstacle();
			obstacle1.setSizeType(targetSizeType);
			obstacle1.setParams(angle1, speed1);
			obstacle1.setPosition(pos1X, pos1Y);
            */
            ObstacleActor obstacle1 = MainGameScreen.getInstance().addObstacle();
            obstacle1.calcStartParams();
            ObstacleActor obstacle2 = MainGameScreen.getInstance().addObstacle();
            obstacle2.calcStartParams();
		} else {

			mNumObstaclesKilled ++;

			if(mNumObstaclesKilled >= mNextObstacleSize) {
				mNumObstaclesKilled = 0;
				ObstacleActor obstacle = MainGameScreen.getInstance().addObstacle();
				obstacle.calcStartParams();
			}

		}

	}

	public ObstacleActor() {
		mTexture = ResourceManager.getInstance().ObstacleTextureArray[mTextureRandomizer.nextInt(4)];
		setWrapEdge(true);
	}


	@Override
	public void act(float deltaTime) {
		super.act(deltaTime);
/*
		float x = getX();
		float y = getY();
		x -= deltaTime * mSpeed  * Math.sin(mAngle);
		y += deltaTime * mSpeed  * Math.cos(mAngle);
		setPosition(x, y);
	*/

	}

       @Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.setColor(getColor().r, getColor().g, getColor().b,
				getColor().a * parentAlpha);

		float angleDegrees = mAngle * 360 / 2 / 3.14f;

		batch.draw(mTexture, getX(), getY(), getWidth()/2, getHeight()/2,
				getWidth(), getHeight(), 1, 1, angleDegrees, 0, 0,
				mTexture.getWidth(), mTexture.getHeight(), false, false);

	}

}
