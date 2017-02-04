package com.heyzqt.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.heyzqt.girl.MainGame;
import com.heyzqt.handle.Box2DContactListener;
import com.heyzqt.handle.Constant;
import com.heyzqt.sprite.Diamond;
import com.heyzqt.sprite.Flame;
import com.heyzqt.sprite.Protagonist;
import com.heyzqt.sprite.Star;

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
	private static int level = 0;
	//地图渲染器
	private OrthoCachedTiledMapRenderer mCachedTiledMapRenderer;

	private Body mBody;

	private MainGame mGame;

	private SpriteBatch batch;

	/**
	 * 游戏主角
	 */
	private Protagonist mProtagonist;

	/**
	 * 星星数组
	 */
	private Array<Star> mStars;

	/**
	 * 火焰数组
	 */
	private Array<Flame> mFlames;

	/**
	 * 钻石精灵
	 */
	private Diamond mDiamond;

	/**
	 * 游戏渲染时间
	 */
	private float stateTime;

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

		//创建主角
		createActor();
		//创建地图
		createMap();
		//创建星星
		createStar();
		//创建火焰
		createFlame();
		//初始化钻石
		mDiamond = new Diamond(mProtagonist);
	}

	/**
	 * 创建游戏地图
	 */
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
				mWorld.createBody(bodyDef).createFixture(fixtureDef).setUserData("block");
			}
		}
	}

	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * 创建主角
	 */
	private void createActor() {
		//初始化刚体属性
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		mBody = mWorld.createBody(bodyDef);

		//创建正方体模型
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(200 / Constant.RATE, 350 / Constant.RATE);
		//设置水平方向速度
		bodyDef.linearVelocity.set(0.3f, 0);
		mBody = mWorld.createBody(bodyDef);
		shape.setAsBox(15 / Constant.RATE, 20 / Constant.RATE);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.BLOCK_GREEN | Constant.BLOCK_RED | Constant.BLOCK_BLUE
				| Constant.STAR | Constant.FLAME;
		//创建夹具
		mBody.createFixture(fixtureDef).setUserData("player");

		//创建传感器 foot
		shape.setAsBox(15 / Constant.RATE, 3 / Constant.RATE, new Vector2(0, -18 / Constant.RATE), 0);
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = Constant.PLAYER;
		fixtureDef.filter.maskBits = Constant.BLOCK_GREEN | Constant.BLOCK_RED | Constant.BLOCK_BLUE;
		fixtureDef.isSensor = true;
		mBody.createFixture(fixtureDef).setUserData("foot");

		mProtagonist = new Protagonist(mBody);
	}

	/**
	 * 创建星星
	 */
	private void createStar() {
		mStars = new Array<Star>();

		//获取萝卜所在对象层
		MapLayers mapLayers = mMap.getLayers();
		MapLayer mapLayer = mapLayers.get("heart");
		if (mapLayer == null) return;

		//初始化萝卜形状
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		//实例化萝卜属性
		FixtureDef fixtureDef = new FixtureDef();
		//实例化圆形
		CircleShape circleShape = new CircleShape();
		//设置圆半径
		circleShape.setRadius(8 / Constant.RATE);
		fixtureDef.shape = circleShape;
		//设置传感器
		fixtureDef.isSensor = true;
		//设置刚体碰撞属性
		fixtureDef.filter.categoryBits = Constant.STAR;
		fixtureDef.filter.maskBits = Constant.PLAYER;

		//遍历对象
		for (MapObject object : mapLayer.getObjects()) {
			//星星x，y轴坐标
			float x = 0;
			float y = 0;
			//获取对象x，y坐标
			if (object instanceof EllipseMapObject) {
				EllipseMapObject ellipseMapObject = (EllipseMapObject) object;
				x = ellipseMapObject.getEllipse().x / Constant.RATE;
				y = ellipseMapObject.getEllipse().y / Constant.RATE;
			}
			//设置刚体位置
			bodyDef.position.set(x, y);
			Body body = mWorld.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData("heart");

			Star star = new Star(body);
			mStars.add(star);
			body.setUserData(star);
		}

	}

	/**
	 * 创建火焰
	 */
	private void createFlame() {
		mFlames = new Array<Flame>();

		//火焰对象层
		MapLayer mapLayer = mMap.getLayers().get("flame");
		if (mapLayer == null) return;

		//初始化火焰刚体形状
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(8 / Constant.RATE);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.isSensor = true;
		fixtureDef.filter.categoryBits = Constant.FLAME;
		fixtureDef.filter.maskBits = Constant.PLAYER;

		//遍历flame对象层
		for (MapObject object : mapLayer.getObjects()) {
			//火焰坐标
			float x = 0;
			float y = 0;
			//获取对象坐标
			if (object instanceof EllipseMapObject) {
				EllipseMapObject ellipseMapObject = (EllipseMapObject) object;
				x = ellipseMapObject.getEllipse().x / Constant.RATE;
				y = ellipseMapObject.getEllipse().y / Constant.RATE;
			}

			//设置火焰位置
			bodyDef.position.set(x, y);
			Body body = mWorld.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData("flame");

			Flame flame = new Flame(body);
			mFlames.add(flame);

			body.setUserData(flame);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
		stateTime += delta;

		batch.setProjectionMatrix(mCamera.combined);
		batch.begin();
		//batch.draw(mGame.mAssetManager.get("images/tree.png", Texture.class), 0, 0, 1280, 720);
		//画出主角
		mProtagonist.render(batch, stateTime);
		//画出钻石
		mDiamond.render(batch);
		//画出星星
		for (Star star : mStars) {
			star.render(batch, stateTime);
		}
		//画出火焰
		for (Flame flame : mFlames) {
			flame.render(batch, stateTime);
		}
		batch.end();

		mCachedTiledMapRenderer.setView(mCamera);
		mCachedTiledMapRenderer.render();
		//渲染物理世界
		mDebugRenderer.render(mWorld, mBox2DCamera.combined);
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			//竖直方向上给一个200N的力
			mBody.applyForceToCenter(0, 200f, true);
		}
	}

	/**
	 * 切换钻石颜色
	 */
	public void switchDiamond() {
		//获取主角foot目标碰撞属性
		Filter filter = mProtagonist.getBody().getFixtureList().get(1).getFilterData();
		short bits = filter.maskBits;

		//设置foot过滤器属性
		if (bits == Constant.BLOCK_RED) {
			bits = Constant.BLOCK_GREEN;
		} else if (bits == Constant.BLOCK_GREEN) {
			bits = Constant.BLOCK_BLUE;
		} else if (bits == Constant.BLOCK_BLUE) {
			bits = Constant.BLOCK_RED;
		}
		filter.maskBits = bits;
		mProtagonist.getBody().getFixtureList().get(1).setFilterData(filter);

		//设置主角player过滤器属性
		bits |= Constant.STAR | Constant.FLAME;
		filter.maskBits = bits;
		mProtagonist.getBody().getFixtureList().get(0).setFilterData(filter);

	}

	@Override
	public void update(float delta) {

		handleInput();
		//更新物理世界状态
		mWorld.step(delta, 6, 2);

		//获取被移除的刚体数组
		Array<Body> removeBodies = mBox2DContactListener.getRemoveBodies();
		//遍历刚体移除刚体
		for (Body body : removeBodies) {
			mStars.removeValue((Star) body.getUserData(), true);
			//世界销毁刚体
			mWorld.destroyBody(body);
			//收集星星
			mProtagonist.collectStars();
		}
		removeBodies.clear();

		//判断刚体是否碰撞火焰
		if (mBox2DContactListener.isFlameContact()) {
			System.out.println("碰撞火焰");
		}
	}
}
