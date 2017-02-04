package com.heyzqt.sprite;

		import com.badlogic.gdx.graphics.Texture;
		import com.badlogic.gdx.graphics.g2d.SpriteBatch;
		import com.badlogic.gdx.graphics.g2d.TextureRegion;
		import com.heyzqt.girl.MainGame;
		import com.heyzqt.handle.Constant;

/**
 * Created by heyzqt on 2017/2/4.
 *
 * 钻石精灵类
 */
public class Diamond {

	private Protagonist mProtagonist;

	private TextureRegion[] mDiamonds;

	public Diamond(Protagonist pro) {
		this.mProtagonist = pro;
		Texture texture = MainGame.mAssetManager.get("images/diamond.png");
		mDiamonds = new TextureRegion[3];
		//遍历
		for (int i = 0; i < mDiamonds.length; i++) {
			mDiamonds[i] = new TextureRegion(texture, 16 * i, 0, 16, 16);
		}
	}

	public void render(SpriteBatch batch) {
		//获取主角目标碰撞属性
		short bits = mProtagonist.getBody().getFixtureList().first()
				.getFilterData().maskBits;

		if ((bits & Constant.BLOCK_RED) != 0) {
			batch.draw(mDiamonds[0], 780, 560);
		}
		if ((bits & Constant.BLOCK_GREEN) != 0) {
			batch.draw(mDiamonds[1], 780, 560);
		}
		if ((bits & Constant.BLOCK_BLUE) != 0) {
			batch.draw(mDiamonds[2], 780, 560);
		}
	}
}
