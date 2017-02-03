package com.heyzqt.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.girl.MainGame;

/**
 * Created by heyzqt on 2017/2/3.
 *
 * 游戏主角类 创建收集金币方法
 */
public class Protagonist extends GameSprite {

	/**
	 * 星星数目
	 */
	private int starsCount;

	/**
	 * 星星总数
	 */
	private int allStarsCount;

	public Texture texture;

	public Protagonist(Body body) {
		super(body);
		texture = MainGame.mAssetManager.get("images/girl.png", Texture.class);
		TextureRegion[] regions = TextureRegion.split(texture, 32, 32)[0];
		//初始化主角动画
		setAnimation(regions, 0.075f);
	}

	//收集星星
	public void collectStars(){
		starsCount++;
	}

	//返回星星数
	public int getStarsCount(){
		return starsCount;
	}

	//返回星星总数
	public int getAllStarsCount(){
		return allStarsCount;
	}

	//设置星星数目
	public void setStarsCount(int starsCount){
		this.starsCount = starsCount;
	}

	//设置所有星星数目
	public void setAllStarsCount(int allStarsCount){
		this.allStarsCount = allStarsCount;
	}
}
