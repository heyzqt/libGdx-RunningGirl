package com.heyzqt.girl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
	public static final int ViewPort_WIDTH = 320;

	// 视距高度
	public static final int ViewPort_HEIGHT = 240;

	private GameScreen mGameScreen;

	private MainScreen mMainScreen;

	//声明资源加载器
	public static AssetManager mAssetManager;

	@Override
	public void create() {
		batch = new SpriteBatch();
		mAssetManager = new AssetManager();
		//预加载游戏纹理
		mAssetManager.load("images/bg.png", Texture.class);
		mAssetManager.load("images/diamond.png", Texture.class);
		mAssetManager.load("images/flame.png", Texture.class);
		mAssetManager.load("images/girl.png", Texture.class);
		mAssetManager.load("images/heart.png", Texture.class);
		mAssetManager.load("images/input1.png", Texture.class);
		mAssetManager.load("images/select.atlas", TextureAtlas.class);
		mAssetManager.load("images/start.png", Texture.class);
		mAssetManager.load("images/tree.png", Texture.class);

		//预加载游戏音效
		mAssetManager.load("audio/music.ogg", Music.class);
		mAssetManager.load("audio/contact.wav", Sound.class);
		mAssetManager.load("audio/diamond.wav", Sound.class);
		mAssetManager.load("audio/jump.wav", Sound.class);
		mAssetManager.load("audio/select.wav", Sound.class);
		mAssetManager.load("audio/switch.wav", Sound.class);

		//加载游戏资源
		mAssetManager.finishLoading();

		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false, ViewPort_WIDTH, ViewPort_HEIGHT);

		mUICamera = new OrthographicCamera();
		mUICamera.setToOrtho(false, ViewPort_WIDTH, ViewPort_HEIGHT);

		mMainScreen = new MainScreen(this);
		mGameScreen = new GameScreen(this);
		this.setScreen(mMainScreen);
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return mCamera;
	}

	public OrthographicCamera getUICamera() {
		return mUICamera;
	}
}
