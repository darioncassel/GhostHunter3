package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor {
	
	private String mText = "";
	private float mFontHeight;
	private BitmapFont mFont;
	
	public TextActor() {
		
		setFontHeight(Gdx.graphics.getWidth() / 15);
		setText("");
	}
	
	private void recalcBounds() {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(mFont, "k");
		setSize(layout.width, layout.height);
	}
	
	public void setText(String text) {
		mText = text;
		recalcBounds();
		
	}
	
	public void setFontHeight(float height) {
		
		mFontHeight = height;
		mFont = ResourceManager.getInstance().getFontForHeight(mFontHeight);
		recalcBounds();
		
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		CharSequence str = this.mText;
		
		float x = this.getX();
		float y = this.getY() + getHeight();
		
		
		mFont.setColor(this.getColor().r, this.getColor().g, this.getColor().b,
				this.getColor().a * parentAlpha);
		mFont.draw(batch, str, x, y);
		
	}
}
