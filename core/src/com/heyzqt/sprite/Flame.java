package com.heyzqt.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.girl.MainGame;

/**
 * Created by heyzqt on 2017/2/4.
 */
public class Flame extends GameSprite {

	private Texture mTexture;

	public Flame(Body body) {
		super(body);

		mTexture = MainGame.mAssetManager.get("images/flame.png", Texture.class);

		TextureRegion[] regions = TextureRegion.split(mTexture,32,32)[0];

		setAnimation(regions,0.075f);
	}
}
