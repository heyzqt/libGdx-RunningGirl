package com.heyzqt.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
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

	//地图
	TiledMap mMap;
	//地图瓦片大小
	private float tileSize;
	//地图宽度
	private float mMapWidth;
	//地图高度
	private float mMapHeight;
	//地图编号
	private static int level;
	//地图渲染器
	private OrthoCachedTiledMapRenderer mCachedTiledMapRenderer;

	private Body mBody;

	private MainGame mGame;

	private SpriteBatch batch;

	public GameScreen(MainGame game) {
		super(game);

		mGame = game;
		batch = game.getSpriteBatch();

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

		//初始化刚体属性
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		mBody = mWorld.createBody(bodyDef);

		//创建正方体模型
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(200 / Constant.RATE, 350 / Constant.RATE);
		mBody = mWorld.createBody(bodyDef);
		shape.setAsBox(5 / Constant.RATE, 5 / Constant.RATE);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.BLOCK_GREEN;
		//创建夹具
		mBody.createFixture(fixtureDef).setUserData("box");

		//创建传感器
		shape.setAsBox(3 / Constant.RATE, 3 / Constant.RATE, new Vector2(0, -5 / Constant.RATE), 0);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.BLOCK_GREEN;
		fixtureDef.isSensor = true;
		mBody.createFixture(fixtureDef).setUserData("player");

		//创建地图
		createMap();
	}

	private void createMap() {
		try {
			mMap = new TmxMapLoader().load("maps/" + "level" + level + ".tmx");
		} catch (Exception e) {
			System.out.println("不能找到level" + level + ".tmx文件");
			Gdx.app.exit();
		}
		mCachedTiledMapRenderer = new OrthoCachedTiledMapRenderer(mMap);
		//赋值地图瓦片大小
		tileSize = mMap.getProperties().get("tilewidth", Integer.class);
		//地图宽度
		mMapWidth = mMap.getProperties().get("width", Integer.class);
		//地图长度
		mMapHeight = mMap.getProperties().get("height", Integer.class);
		TiledMapTileLayer layer;
		//绑定红色图层与刚体
		layer = (TiledMapTileLayer) mMap.getLayers().get("red");
		createMapLayer(layer, Constant.BLOCK_RED);
		//绑定蓝色图层与刚体
		layer = (TiledMapTileLayer) mMap.getLayers().get("blue");
		createMapLayer(layer, Constant.BLOCK_BLUE);
		//绑定绿色图层与刚体
		layer = (TiledMapTileLayer) mMap.getLayers().get("green");
		createMapLayer(layer, Constant.BLOCK_GREEN);
	}

	private void createMapLayer(TiledMapTileLayer layer, short bits) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		//遍历所有单元格
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				TiledMapTileLayer.Cell cell = layer.getCell(col, row);
				if (cell == null || cell.getTile() == null) {
					continue;
				}

				bodyDef.type = BodyDef.BodyType.StaticBody;
				bodyDef.position.set(
						(col + 0.5f) * tileSize / Constant.RATE,
						(row + 0.5f) * tileSize / Constant.RATE);
				//创建链式图形
				ChainShape chainShape = new ChainShape();
				Vector2[] vector2 = new Vector2[3];
				vector2[0] = new Vector2(-tileSize / 2 / Constant.RATE, -tileSize / 2 / Constant.RATE);
				vector2[1] = new Vector2(-tileSize / 2 / Constant.RATE, tileSize / 2 / Constant.RATE);
				vector2[2] = new Vector2(tileSize / 2 / Constant.RATE, tileSize / 2 / Constant.RATE);
				//创建链式图形
				chainShape.createChain(vector2);
				//设置恢复力为0
				fixtureDef.friction = 0;
				//绑定夹具与链式图形
				fixtureDef.shape = chainShape;
				fixtureDef.filter.categoryBits = bits;
				fixtureDef.filter.maskBits = Constant.PLAYER;
				//设置传感器
				fixtureDef.isSensor = false;
				mWorld.createBody(bodyDef).createFixture(fixtureDef);
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);

		batch.setProjectionMatrix(mCamera.combined);
		batch.begin();
		batch.draw(mGame.mAssetManager.get("images/tree.png", Texture.class), 0, 0, 1280, 720);
		batch.end();

		mCachedTiledMapRenderer.setView(mCamera);
		mCachedTiledMapRenderer.render();
		//渲染物理世界
		mDebugRenderer.render(mWorld, mBox2DCamera.combined);
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {

			System.out.println("click");
			mBody.applyForceToCenter(0, 200f, true);
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
