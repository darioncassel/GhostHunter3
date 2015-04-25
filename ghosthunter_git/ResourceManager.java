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

	public Texture playerTexture;
	public Texture playerJetTexture;
	public Texture blank16Texture;
	public Texture ghostTexture;
	public final Texture[] ObstacleTextureArray = new Texture[4];


	private final HashMap<Float, BitmapFont> mFontMap = new HashMap<Float, BitmapFont>();

	public BitmapFont getFontForHeight(float height) {
		BitmapFont ret;

		if(mFontMap.containsKey(height)) {
			ret = mFontMap.get(height);
		} else {

			ret = new BitmapFont(Gdx.files.internal("data/bebas_neue.fnt"), false);
            GlyphLayout layout = new GlyphLayout();
            layout.setText(ret, "k");
			float scaleFactor = height/layout.height;
			mFontMap.put(height, ret);
		}

		return ret;
	}

	public void loadResources() {

		playerTexture = new Texture(Gdx.files.internal("data/player.png"));
		playerJetTexture = new Texture(Gdx.files.internal("data/player_jet.png"));
		blank16Texture = new Texture(Gdx.files.internal("data/blank16.png"));
		ghostTexture = new Texture(Gdx.files.internal("data/ghost.png"));

		for(int i = 0; i < 4; i++) {
			ObstacleTextureArray[i] = new Texture(Gdx.files.internal("data/obstacle" + i + ".png"));
		}

	}

	@Override
	public void dispose() {
		playerJetTexture.dispose();
		playerTexture.dispose();
		blank16Texture.dispose();
		ghostTexture.dispose();
		for(Texture texture : ObstacleTextureArray) {
			texture.dispose();
		}

		for(BitmapFont font : mFontMap.values()) {
			font.dispose();
		}

	}

}
