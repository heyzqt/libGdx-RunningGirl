package com.heyzqt.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heyzqt.girl.MainGame;

/**
 * Created by heyzqt on 2017/1/21.
 *
 * 建立抽象的GirlScreen父类
 */
public abstract class GirlScreen implements Screen {

	protected OrthographicCamera mCamera;
	protected OrthographicCamera mUICamera;
	protected SpriteBatch mBatch;
	private MainGame mGame;

	public GirlScreen(MainGame game){
		this.mGame = game;
	}

	@Override
	public void show() {
		mBatch = mGame.getSpriteBatch();
		mCamera = mGame.getCamera();
		mUICamera = mGame.getUICamera();
	}

	public abstract void handleInput();

	public abstract void update(float delta);

	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
