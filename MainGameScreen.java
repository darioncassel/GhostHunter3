package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MainGameScreen implements Screen {

	private Stage mStage;

	private PlayerActor mPlayer;

	private final ArrayList<BaseActor> mActorList =
			new ArrayList<BaseActor>();

	private final ArrayList<DamageableActor> mDamageableActorList =
			new ArrayList<DamageableActor>();

	private final ArrayList<BulletActor> mBulletActorList =
			new ArrayList<BulletActor>();

	private static MainGameScreen mInstance;


	public static MainGameScreen getInstance() {
		return mInstance;
	}

	public SparkEffectActor addSparkEffect() {
		SparkEffectActor spark = new SparkEffectActor();
		mActorList.add(spark);
		return spark;
	}


	public PlayerActor addPlayer() {
		PlayerActor player = new PlayerActor();
		mActorList.add(player);
		return player;
	}

	public BulletActor addBullet() {
		BulletActor bullet = new BulletActor();
		mActorList.add(bullet);
		return bullet;

	}

	public ObstacleActor addObstacle() {
		ObstacleActor obstacle = new ObstacleActor();
		mActorList.add(obstacle);
		return obstacle;
	}

	public GhostActor addGhost() {
		GhostActor saucer = new GhostActor();
		mActorList.add(saucer);
		return saucer;
	}

	private int mScore;
	private int mNumLives;
    private BitmapFont mFont;
    private Color textColor;

	private Stage mHudStage;
	private TextActor mHudScoreActor;
	private final PlayerActor[] mHudLivesArray = new PlayerActor[3];

	private void doGameOver() {
		GameOverScreen.getInstance().setScore(mScore);
		GhostHunterGame.getInstance().setScreen(GameOverScreen.getInstance());
	}

	public void queueGameOver() {

		mStage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {

			@Override
			public void run() {
				doGameOver();
			}
		})));
	}

	public void killPlayer() {

		mNumLives -= 1;

		for(PlayerActor life : mHudLivesArray) {
			life.setVisible(false);
		}

		for(int i = 0; i < mNumLives; i ++) {
			PlayerActor life = mHudLivesArray[i];
			life.setVisible(true);
		}

		if(mNumLives != 0) {
			queuePlayerSpawn();

		} else {
			queueGameOver();
		}
	}

	public void incrementScore() {
		mScore += 25;
		mHudScoreActor.updateText("SCORE : " + mScore);
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
		float fontHeight = Gdx.graphics.getHeight() / 20;
		float x = padding;
		float y = Gdx.graphics.getHeight() - padding - fontHeight;
		mHudScoreActor = new TextActor("Score : 0", textStyle);
		mHudScoreActor.setFontScale(2);
		mHudScoreActor.setPosition(x, y);

		mHudStage.addActor(mHudScoreActor);

		float size =  Gdx.graphics.getHeight() / 20;

		y -= size + padding;
		x -= size / 3;

		for(int i = 0; i < 3; i ++) {
			PlayerActor life = new PlayerActor();
			life.setPosition(x, y);


			life.setSize(size, size);

			x += 0.25 * padding + life.getWidth();

			mHudLivesArray[i] = life;
			mHudStage.addActor(life);
		}

		Gdx.input.setInputProcessor(mStage);
	}

	public void initializeGame() {

		mActorList.clear();

		addPlayer();
		for(int i = 0; i < 10; i ++) {
			ObstacleActor obstacle = addObstacle();
			obstacle.calcStartParams();
		}

		for(int i = 0; i < 2; i ++) {
			addGhost();
		}

		mScore = 0;
		mNumLives = 3;
		mHudScoreActor.updateText("SCORE : 0");
		for(PlayerActor life : mHudLivesArray) {
			life.setVisible(true);
		}
	}


	@Override
	public void dispose() {
		mStage.dispose();

	}

	private void handleKillableActors() {

		mStage.getActors().clear();
		mBulletActorList.clear();
		mDamageableActorList.clear();

		for(BaseActor actor : mActorList) {

			if(!actor.isDead()) {
				 mStage.addActor(actor);

				 if(PlayerActor.class.isInstance(actor)) {
					 mPlayer = (PlayerActor) actor;
				 }

				 if(DamageableActor.class.isInstance(actor)) {
					 mDamageableActorList.add((DamageableActor) actor);
				 }

				 if(BulletActor.class.isInstance(actor)) {
					 mBulletActorList.add((BulletActor) actor);
				 }

			}
		}

		mActorList.clear();

		for(Actor actor : mStage.getActors()) {

			mActorList.add((BaseActor) actor);
		}
	}

	private void handleBullets() {

		for(BulletActor bullet : mBulletActorList) {
			for(DamageableActor actor : mDamageableActorList) {

				if(bullet.intersects(actor)) {
					bullet.kill();
					actor.damage(bullet.getAngle());

					SparkEffectActor spark = addSparkEffect();
					spark.setPosition(bullet.getX(), bullet.getY());
				}
			}
		}
	}



	public void queuePlayerSpawn() {
		mStage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {

			@Override
			public void run() {
				addPlayer();

			}
		})));
	}

	public void queueGhostSpawn() {
		mStage.addAction(Actions.sequence(Actions.delay(2f), Actions.run(new Runnable() {

			@Override
			public void run() {
				addGhost();

			}
		})));
	}


	private void handleCollision() {

		for(DamageableActor actor: mDamageableActorList) {


			if(mPlayer != null && actor!= mPlayer && actor.intersects(mPlayer) ) {
				mPlayer.damage(actor.getAngle());
				actor.damage(mPlayer.getAngle());
				Vector2 sparkPos = mPlayer.getTipPosition();
				SparkEffectActor spark = addSparkEffect();
				spark.setPosition(sparkPos.x, sparkPos.y);
				mPlayer = null;

			}
		}

	}


	@Override
	public void resize(int width, int height) {
        mStage.getViewport().update(width, height);

	}



	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleKillableActors();
		handleCollision();
		handleBullets();

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
