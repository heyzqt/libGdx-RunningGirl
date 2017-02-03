package com.heyzqt.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

	ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("badlogic.jpg"))));
	Stage mStage;
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
		button.setPosition(300,300);
		mStage = new Stage();
		mStage.addActor(button);
		Gdx.input.setInputProcessor(mStage);
		button.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello");
				return true;
			}
		});
	}

	//初始化
	private void init() {
		//创建一个长方体刚体
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(200 / Constant.RATE, 200 / Constant.RATE);
		Body rectangle = mWorld.createBody(bodyDef);
		//声明一个多边形
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50 / Constant.RATE, 5 / Constant.RATE);
		//创建FixtureDef
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.GROUND;
		fixtureDef.filter.maskBits = Constant.BALL | Constant.BOX;
		rectangle.createFixture(fixtureDef).setUserData("ground");

		//创建正方体模型
		BodyDef bodyDef1 = new BodyDef();
		bodyDef1.type = BodyDef.BodyType.DynamicBody;
		bodyDef1.position.set(200 / Constant.RATE, 250 / Constant.RATE);
		Body square = mWorld.createBody(bodyDef1);
		shape.setAsBox(5 / Constant.RATE, 5 / Constant.RATE);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.BOX;
		fixtureDef.filter.maskBits = Constant.GROUND;
		square.createFixture(fixtureDef).setUserData("box");

		//创建圆形刚体
		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.type = BodyDef.BodyType.DynamicBody;
		bodyDef2.position.set(230 / Constant.RATE, 250 / Constant.RATE);
		Body circle = mWorld.createBody(bodyDef2);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(5/Constant.RATE);
		fixtureDef.shape = circleShape;
		fixtureDef.filter.categoryBits = Constant.BALL;
		fixtureDef.filter.maskBits = Constant.GROUND;
		circle.createFixture(fixtureDef).setUserData("ball");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
		//渲染物理世界
		mDebugRenderer.render(mWorld, mBox2DCamera.combined);

		mStage.act();
		mStage.draw();
	}

	@Override
	public void handleInput() {
	}

	@Override
	public void update(float delta) {
		//更新物理世界状态
		mWorld.step(delta, 6, 2);
	}
}
