package com.heyzqt.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.heyzqt.girl.MainGame;

/**
 * Created by heyzqt on 2017/1/21.
 */
public class GameScreen extends GirlScreen {

	BitmapFont mBitmapFont;

	public GameScreen(MainGame game) {
		super(game);
		mBitmapFont = new BitmapFont();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBitmapFont.draw(mBatch,"hello libgdx",200,200);
		mBatch.end();
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float delta) {

	}
}
