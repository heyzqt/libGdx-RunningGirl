package com.heyzqt.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.heyzqt.girl.MainGame;

/**
 * Created by heyzqt on 2017/1/21.
 *
 * 开始界面与选关界面
 */
public class MainScreen extends GirlScreen {

	private MainGame mGame;

	private Stage mStage;

	private SpriteBatch mBatch;

	//游戏开始界面背景
	private Image mBackground;

	//选关背景
	private Image mChooseBG;

	//选关按钮
	private Image[] mImages;

	//图集
	private TextureAtlas mAtlas;

	//开始按钮
	private Image mStartBtn;

	//判断当前场景
	public static boolean isStart = true;

	public MainScreen(MainGame game) {
		super(game);
		this.mGame = game;
		init();
	}

	public void init() {
		mStage = new Stage(new StretchViewport(MainGame.ViewPort_WIDTH, MainGame.ViewPort_HEIGHT));
		Gdx.input.setInputProcessor(mStage);
		mBatch = new SpriteBatch();

		//初始化各个控件
		mBackground = new Image((Texture) MainGame.mAssetManager.get("images/start.png"));
		mChooseBG = new Image((Texture) MainGame.mAssetManager.get("images/bg.png"));
		mAtlas = MainGame.mAssetManager.get("images/select.atlas");
		mImages = new Image[4];
		//获取按钮图片
		for (int i = 0; i < mImages.length; i++) {
			mImages[i] = new Image(mAtlas.findRegion(i + ""));
			mImages[i].setScale(0.4f);
		}
		mImages[0].setPosition(20,140);
		mImages[1].setPosition(170,140);
		mImages[2].setPosition(20,20);
		mImages[3].setPosition(170,20);


		mStartBtn = new Image(new Texture("badlogic.jpg"));
		mStartBtn.setPosition(220, 130);
		mStartBtn.setScale(0.15f);

		initListener();
	}

	//初始化开始按钮，选关按钮监听事件
	private void initListener() {
		//开始按钮监听事件
		mStartBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MainGame.mAssetManager.get("audio/diamond.wav");
				sound.play();
				isStart = false;
				return true;
			}
		});

		mImages[0].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MainGame.mAssetManager.get("audio/select.wav");
				sound.play();
				//进入第一关
				GameScreen.level = 0;
				//实例化第一关游戏场景
				GameScreen game = new GameScreen(mGame);
				//将当前场景置于栈顶
				mGame.setScreen(game);
				return true;
			}
		});

		mImages[1].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MainGame.mAssetManager.get("audio/select.wav");
				sound.play();
				//第二关
				GameScreen.level = 1;
				GameScreen game = new GameScreen(mGame);
				mGame.setScreen(game);
				return true;
			}
		});

		mImages[2].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MainGame.mAssetManager.get("audio/select.wav");
				sound.play();
				//第三关
				GameScreen.level = 2;
				GameScreen game = new GameScreen(mGame);
				mGame.setScreen(game);
				return true;
			}
		});

		mImages[3].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MainGame.mAssetManager.get("audio/select.wav");
				sound.play();
				//第四关
				GameScreen.level = 3;
				GameScreen game = new GameScreen(mGame);
				mGame.setScreen(game);
				return true;
			}
		});
	}


	@Override
	public void render(float delta) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
		mBatch.setProjectionMatrix(mCamera.combined);

		mStage.act();
		mStage.draw();
	}

	@Override
	public void handleInput() {
	}

	@Override
	public void update(float delta) {
		//先清空舞台
		mStage.getActors().clear();
		if (isStart){
			mStage.addActor(mBackground);
			mStage.addActor(mStartBtn);
		}else{
			mStage.addActor(mChooseBG);
			mStage.addActor(mImages[0]);
			mStage.addActor(mImages[1]);
			mStage.addActor(mImages[2]);
			mStage.addActor(mImages[3]);
		}
	}

	@Override
	public void hide() {
		//当前场景隐藏时，清空舞台演员
		mStage.clear();
		//销毁舞台
		mStage.dispose();
	}
}
