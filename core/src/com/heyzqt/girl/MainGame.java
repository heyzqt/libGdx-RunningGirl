package com.heyzqt.girl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heyzqt.screen.GameScreen;
import com.heyzqt.screen.MainScreen;

public class MainGame extends Game {

	//画笔
	private SpriteBatch batch;

	//精灵相机
	private OrthographicCamera mCamera;

	//UI相机
	private OrthographicCamera mUICamera;

	public static String TAG = "mygame";

	// 视距宽度
	public static final int ViewPort_WIDTH = 1280;

	// 视距高度
	public static final int ViewPort_HEIGHT = 720;

	private GameScreen mGameScreen;

	private MainScreen mMainScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();

		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false, ViewPort_WIDTH, ViewPort_HEIGHT);

		mUICamera = new OrthographicCamera();
		mUICamera.setToOrtho(false,ViewPort_WIDTH,ViewPort_HEIGHT);

		mMainScreen = new MainScreen(this);
		mGameScreen = new GameScreen(this);
		this.setScreen(mGameScreen);
	}

	public SpriteBatch getSpriteBatch(){
		return batch;
	}

	public OrthographicCamera getCamera(){
		return mCamera;
	}

	public OrthographicCamera getUICamera(){
		return  mUICamera;
	}
}
