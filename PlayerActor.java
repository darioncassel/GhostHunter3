package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PlayerActor extends DamageableActor  {

	public boolean mIsDamageable = false;
	private boolean mIsAccelerating = false;
	private float mElapsedTime = 0;
	float mAngularSpeed = 0;

	public Vector2 getTipPosition() {

		Vector2 tip = new Vector2();

		float startX = getX() + getWidth()/2;
		float startY = getY() + getHeight()/2;

		float bulletSize = Gdx.graphics.getWidth()/250;

		startY += (getHeight() * 1.5 + bulletSize * 2f) * 0.5f * Math.cos(mAngle);
		startX -= (getHeight() * 1.5 + bulletSize * 2f) * 0.5f * Math.sin(mAngle);

		startX -= Math.abs(bulletSize * 0.5f * Math.cos(mAngle));
		startY -= Math.abs(bulletSize * 0.5f * Math.sin(mAngle));

		tip.set(startX, startY);
		return tip;
	}

	public void fireBullet() {

        if(!MainGameScreen.getInstance().mPlayer.isDead()) {
            BulletActor bullet = MainGameScreen.getInstance().addBullet(100);
            Vector2 tip = getTipPosition();

            bullet.setPosition(tip.x, tip.y);
            bullet.setAngle(mAngle);
        }

	}

	@Override
	protected void setStage(Stage stage) {
		super.setStage(stage);
	}

	@Override
	public void damage(float angle) {
		if(mIsDamageable) {
			super.damage(angle);
			MainGameScreen.getInstance().killPlayer();
		}
	}


	public PlayerActor() {

		setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		setWrapEdge(true);

		float playerSize = Gdx.graphics.getWidth() / 12;
		setSize(playerSize, playerSize);

		addAction(Actions.sequence(Actions.delay(1), Actions.run(new Runnable() {

			@Override
			public void run() {
				mIsDamageable = true;

			}
		})));

	}



	@Override
	public void act(float deltaTime) {
		super.act(deltaTime);
		mElapsedTime += deltaTime;

		float force = 0;

		if(mSpeed > 0.2) {
			force = getWidth() * mSpeed * 0.05f;
			force = Math.max(force, getWidth() * 4);
			force = -force;
		}

		mSpeed = Math.max(mSpeed, 0);

		if(mIsAccelerating) {
			force += getWidth() * 20;
		}

		mSpeed += force * deltaTime;

		float y = getY();
		float x = getX();
		y += mSpeed * deltaTime * Math.cos(mAngle);
		x -= mSpeed * deltaTime * Math.sin(mAngle);

		setPosition(x, y);

		mAngle += mAngularSpeed * deltaTime;

	}

    @Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		float alpha = 1;

		if(!mIsDamageable) {
			alpha = (float) (Math.cos((mElapsedTime * 40f )));
		}

		batch.setColor(getColor().r, getColor().g, getColor().b,
				alpha * getColor().a * parentAlpha);

		Texture playerTexture = ResourceManager.getInstance().playerTexture;


		float angleDegrees = mAngle * 360 / 2 / 3.14f;
		batch.draw(playerTexture, getX(), getY(), getWidth()/2, getHeight()/2,
				getWidth(), getHeight(), 1, 1, angleDegrees, 0, 0,
				playerTexture.getWidth(), playerTexture.getHeight(), false, false);


	}
}
