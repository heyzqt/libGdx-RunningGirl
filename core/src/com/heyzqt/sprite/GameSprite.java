package com.heyzqt.sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.handle.Constant;

/**
 * Created by heyzqt on 2017/2/3.
 *
 * 联系精灵动画与物理世界刚体
 */
public class GameSprite {

	protected Body mBody;

	protected Animation mAnimation;

	protected float mWidth;

	protected float mHeight;

	public GameSprite(Body body) {
		this.mBody = body;
	}

	/**
	 * 设置主角动画
	 * @param regions
	 * @param delta
	 */
	public void setAnimation(TextureRegion[] regions, float delta) {
		mAnimation = new Animation(delta, regions);
		mWidth = regions[0].getRegionWidth();
		mHeight = regions[0].getRegionHeight();
	}

	public void update(float delta) {
	}

	public void render(SpriteBatch batch, float delta) {
		batch.begin();
		batch.draw(mAnimation.getKeyFrame(delta, true),
				mBody.getPosition().x * Constant.RATE - mWidth / 2,
				mBody.getPosition().y * Constant.RATE - mHeight / 2);
		batch.end();
	}

	public Body getBody(){
		return mBody;
	}

	public Vector2 getPosition(){
		return mBody.getPosition();
	}

	public float getWidth(){
		return mWidth;
	}

	public float getHeight(){
		return mHeight;
	}
}
