package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 * Created by heyzqt on 2017/2/3.
 *
 * Box2D碰撞监听器
 */
public class Box2DContactListener implements ContactListener {

	/**
	 * 碰撞计数器
	 */
	private int platformNum;

	/**
	 * 要被移除的刚体
	 */
	private Array<Body> removeBodies;

	/**
	 * 是否碰撞火焰变量
	 */
	private boolean flameContact;

	public Box2DContactListener() {
		super();
		removeBodies = new Array<Body>();
	}

	@Override
	public void beginContact(Contact contact) {
		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();
		//打印刚体夹具用户数据
		System.out.println("begin : " + fixtureA.getUserData() + "-->" + fixtureB.getUserData());

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("foot")) {
			platformNum++;
		}

		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("foot")) {
			platformNum++;
		}

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("heart")) {
			//移除夹具A的刚体
			removeBodies.add(fixtureA.getBody());
		}

		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("heart")) {
			//移除夹具B的刚体
			removeBodies.add(fixtureB.getBody());
		}

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("flame")) {
			//碰到火焰则为true
			flameContact = true;
		}

		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("flame")) {
			//碰到火焰则为true
			flameContact = true;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		System.out.println("end : " + contact.getFixtureA().getUserData() + "-->" + contact.getFixtureB().getUserData());
		//夹具为空时 不执行
		if (fixtureA == null || fixtureB == null) {
			return;
		}

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("foot")) {
			platformNum--;
		}

		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("foot")) {
			platformNum--;
		}
	}

	/**
	 * 判断主角是否在地面上
	 * @return
	 */
	public boolean isOnPlatform() {
		return platformNum > 0;
	}

	/**
	 * 判断主角是否碰撞到火焰
	 * @return
	 */
	public boolean isFlameContact(){
		return flameContact;
	}

	/**
	 * 返回要被移除的刚体数组
	 * @return
	 */
	public Array<Body> getRemoveBodies(){
		return removeBodies;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
