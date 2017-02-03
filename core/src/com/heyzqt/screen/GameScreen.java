package com.heyzqt.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.heyzqt.girl.MainGame;
import com.heyzqt.handle.Box2DContactListener;
import com.heyzqt.handle.Constant;

/**
 * Created by heyzqt on 2017/1/21.
 */
public class GameScreen extends GirlScreen {

	//声明世界变量
	World mWorld;
	//声明物理世界渲染器
	Box2DDebugRenderer mDebugRenderer;
	//声明物理世界渲染相机
	OrthographicCamera mBox2DCamera;

	//声明刚体监听器
	private Box2DContactListener mBox2DContactListener;

	Body mBody;

	public GameScreen(MainGame game) {
		super(game);

		mWorld = new World(new Vector2(0, -9.8f), true);    //模拟重力环境
		mDebugRenderer = new Box2DDebugRenderer();
		mBox2DCamera = new OrthographicCamera();
		mBox2DCamera.setToOrtho(false, MainGame.ViewPort_WIDTH / Constant.RATE, MainGame.ViewPort_HEIGHT / Constant.RATE);
		init();
		//设置物理世界刚体监听器
		mBox2DContactListener = new Box2DContactListener();
		mWorld.setContactListener(mBox2DContactListener);
	}

	//初始化
	private void init() {
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		//创建一个长方体刚体
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(200 / Constant.RATE, 200 / Constant.RATE);
		mBody = mWorld.createBody(bodyDef);
		//声明一个多边形
		shape.setAsBox(50 / Constant.RATE, 5 / Constant.RATE);
		//创建FixtureDef
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.GROUND;
		fixtureDef.filter.maskBits = Constant.PLAYER;
		mBody.createFixture(fixtureDef).setUserData("ground");

		//创建正方体模型
		BodyDef bodyDef1 = new BodyDef();
		bodyDef1.type = BodyDef.BodyType.DynamicBody;
		bodyDef1.position.set(200 / Constant.RATE, 350 / Constant.RATE);
		mBody = mWorld.createBody(bodyDef1);
		shape.setAsBox(5 / Constant.RATE, 5 / Constant.RATE);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.GROUND;
		mBody.createFixture(fixtureDef).setUserData("box");

		//设置传感器
		shape.setAsBox(3 / Constant.RATE, 3 / Constant.RATE, new Vector2(0, -5 / Constant.RATE), 0);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.GROUND;
		fixtureDef.isSensor = true;
		mBody.createFixture(fixtureDef).setUserData("player");

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
		//渲染物理世界
		mDebugRenderer.render(mWorld, mBox2DCamera.combined);
	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched()){

			System.out.println("click");
			mBody.applyForceToCenter(0,200f,true);
//			if(mBox2DContactListener.isOnPlatform()){
//				mBody.applyForceToCenter(0,200,true);
//			}
		}
	}

	@Override
	public void update(float delta) {

		handleInput();
		//更新物理世界状态
		mWorld.step(delta, 6, 2);
	}
}
