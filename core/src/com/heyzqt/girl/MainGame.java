package com.heyzqt.girl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {

	//画笔
	private SpriteBatch batch;

	//精灵相机
	private OrthographicCamera mCamera;

	//UI相机
	private OrthographicCamera mUICamera;

	public static String TAG = "mygame";

	// 视距宽度
	public static final int ViewPort_WIDTH = 320;

	// 视距高度
	public static final int ViewPort_HEIGHT = 240;

	@Override
	public void create () {
		batch = new SpriteBatch();

		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false, ViewPort_WIDTH, ViewPort_WIDTH);

		mUICamera = new OrthographicCamera();
		mUICamera.setToOrtho(false,ViewPort_WIDTH,ViewPort_HEIGHT);
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

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
