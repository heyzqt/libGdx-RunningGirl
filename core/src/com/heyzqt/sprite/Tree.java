package com.heyzqt.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heyzqt.girl.MainGame;

/**
 * Created by heyzqt on 2017/2/4.
 */
public class Tree {
	private Texture mTexture;

	public Tree(){
		mTexture = MainGame.mAssetManager.get("images/tree.png");
	}

	public void render(SpriteBatch batch){
		batch.begin();
		batch.draw(mTexture,0,0);
		batch.end();

	}
}
