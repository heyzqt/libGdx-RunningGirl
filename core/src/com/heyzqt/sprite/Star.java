package com.heyzqt.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.girl.MainGame;

/**
 * Created by heyzqt on 2017/2/4.
 *
 * 星星类
 */
public class Star extends GameSprite{
	private TextureRegion[] mRegions;

	private Texture mTexture;

	public Star(Body body) {
		super(body);

		mTexture = MainGame.mAssetManager.get("images/heart.png", Texture.class);

		mRegions = TextureRegion.split(mTexture,32,32)[0];

		setAnimation(mRegions,0.075f);
	}
}
