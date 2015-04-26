package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

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
        if(mBulletActorList.size()<40) {
            mActorList.add(bullet);
        }
		return bullet;
	}

	public ObstacleActor addObstacle() {
		ObstacleActor obstacle = new ObstacleActor();
        if(mActorList.size()<50) {
            mActorList.add(obstacle);
        }
		return obstacle;
	}

	public GhostActor addGhost() {
		GhostActor ghost = new GhostActor();
        int instGhostCount = 0;
        for(BaseActor a : mActorList){
            if(a instanceof GhostActor) {
                instGhostCount++;
            }
        }
        if(instGhostCount<5) {
            mActorList.add(ghost);
        }
		return ghost;
	}

    public BombActor addBomb() {
        BombActor bomb = new BombActor();
        int instBombCount = 0;
        for(BaseActor a : mActorList){
            if(a instanceof BombActor) {
                instBombCount++;
            }
        }
        if(instBombCount<4) {
            mActorList.add(bomb);
        }
        return bomb;
    }

    public LifepackActor addLifepack() {
        LifepackActor lifepack = new LifepackActor();
        int instLifepackCount = 0;
        for(BaseActor a : mActorList){
            if(a instanceof LifepackActor) {
                instLifepackCount++;
            }
        }
        if(instLifepackCount<1) {
            mActorList.add(lifepack);
        }
        return lifepack;
    }

	private int mScore;
	public int mNumLives;
    private BitmapFont mFont;
    private Color textColor;

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Touchpad touchpad2;
    private Touchpad.TouchpadStyle touchpadStyle2;
    private Skin touchpadSkin2;
    private Drawable touchBackground2;
    private Drawable touchKnob2;

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

    public void updateLives() {
        for(int i = 0; i < mNumLives; i ++) {
            PlayerActor life = mHudLivesArray[i];
            life.setVisible(true);
        }
    }

	public void killPlayer() {

		mNumLives -= 1;

		for(PlayerActor life : mHudLivesArray) {
			life.setVisible(false);
		}

		updateLives();

		if(mNumLives != 0) {
			queuePlayerSpawn();

		} else {
			queueGameOver();
		}
	}

    public void playerInvincible(Integer x) {
        if(mPlayer!=null) {
            mPlayer.mIsDamageable = false;
            mPlayer.addAction(Actions.sequence(Actions.delay(x), Actions.run(new Runnable() {
                @Override
                public void run() {
                    if(mPlayer!=null) {
                        mPlayer.mIsDamageable = true;
                    }
                }
            })));
        }
    }

	public void incrementScore() {
		mScore += 25;
		mHudScoreActor.setText("SCORE : " + mScore);
        if(mScore%100==0) {
            addLifepack();
        }
	}

    private void setupTouchControlAreas() {
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(15, touchpadStyle);
        touchpad.setBounds(15, 15, 400, 400);

        touchpadSkin2 = new Skin();
        touchpadSkin2.add("touchBackground", new Texture("touchBackground.png"));
        touchpadSkin2.add("touchKnob", new Texture("touchKnob.png"));
        touchpadStyle2 = new Touchpad.TouchpadStyle();
        touchBackground2 = touchpadSkin2.getDrawable("touchBackground");
        touchKnob2 = touchpadSkin2.getDrawable("touchKnob");
        touchpadStyle2.background = touchBackground2;
        touchpadStyle2.knob = touchKnob2;
        touchpad2 = new Touchpad(15, touchpadStyle2);
        touchpad2.setBounds(1400, 15, 400, 400);

        mHudStage.addActor(touchpad);
        mHudStage.addActor(touchpad2);

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

        setupTouchControlAreas();

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

    private float fireDelay = 0;
    private void handleInput() {
        if(touchpad.isTouched()){
            float x = touchpad.getKnobPercentX()*5;
            float y = touchpad.getKnobPercentY()*5;
            if(mPlayer!=null) {
                mPlayer.moveBy(x, y);
            }
        }
        if(touchpad2.isTouched()){
            Vector2 vec = new Vector2();
            vec.set(-touchpad2.getKnobPercentX(), touchpad2.getKnobPercentY());
            if(mPlayer!=null) {
                mPlayer.setAngle((float)3.14/2 - vec.angleRad());
                fireDelay++;
                if (fireDelay == 15) {
                    mPlayer.fireBullet();
                    fireDelay = 0;
                }

            }
        }
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

    private void handleAI() {
        for(DamageableActor actor : mDamageableActorList) {
            if(actor instanceof ObstacleActor) {
                if(mPlayer!=null) {
                    Vector2 playerPos = new Vector2(mPlayer.getX(), mPlayer.getY());
                    actor.setAngle(playerPos.angleRad());
                    Vector2 path = new Vector2(mPlayer.getX()-actor.getX(), mPlayer.getY()-actor.getY());
                    actor.moveBy(path.x/500, path.y/500);
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
	}



	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        handleAI();
		handleKillableActors();
		handleCollision();
		handleBullets();

		mStage.act(delta);
		mStage.draw();
		mHudStage.draw();

	}


	@Override
	public void show() {
		Gdx.input.setInputProcessor(mHudStage);

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
