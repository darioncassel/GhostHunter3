package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Disposable;

public class ResourceManager implements Disposable {

	public static ResourceManager getInstance() {
		return mInstance;
	}

	private static ResourceManager mInstance;

	ResourceManager() {
		mInstance = this;
	}

    public Texture backgroundTexture;
	public Texture playerTexture;
	public Texture bombTexture;
	public Texture blank16Texture;
	public Texture ghostTexture;
    public Texture lifepackTexture;
	public final Texture[] ObstacleTextureArray = new Texture[4];

	public void loadResources() {

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
		playerTexture = new Texture(Gdx.files.internal("data/player.png"));
		bombTexture = new Texture(Gdx.files.internal("data/Bomb.png"));
		blank16Texture = new Texture(Gdx.files.internal("data/bulletFinal.png"));
		ghostTexture = new Texture(Gdx.files.internal("data/ghost.png"));
        lifepackTexture = new Texture(Gdx.files.internal("data/Lifepack.png"));

		for(int i = 0; i < 4; i++) {
			ObstacleTextureArray[i] = new Texture(Gdx.files.internal("data/obstacle" + i + ".png"));
		}

	}

	@Override
	public void dispose() {
        backgroundTexture.dispose();
		bombTexture.dispose();
		playerTexture.dispose();
		blank16Texture.dispose();
		ghostTexture.dispose();
        lifepackTexture.dispose();
		for(Texture texture : ObstacleTextureArray) {
            texture.dispose();
        }

	}

}
