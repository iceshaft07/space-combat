package com.spacecombat.ai;

import com.spacecombat.RigidBody;
import com.spacecombat.Vector2;

public class AIScriptTwo extends AIScript {

	private RigidBody rigidBody;
	private final Vector2 maxSpeed = new Vector2(32, 32);
	// private int count = 0;
	// private int maxCount = 120;
	private boolean isReverse = false;

	private final Vector2 maxPosition = new Vector2(480 - 32, 400);

	public AIScriptTwo(final boolean isReverse) {
		this.isReverse = isReverse;
	}

	@Override
	public void onCreate() {
		this.rigidBody = this.gameObject.getRigidBody();
	}

	@Override
	public void update() {
		if (this.rigidBody == null) {
			this.rigidBody = this.gameObject.getRigidBody();
		}

		if (!this.isReverse) {
			if (this.gameObject.transform.position.y < this.maxPosition.y) {
				if (this.rigidBody.speed.x > 0) {
					this.rigidBody.speed.x = 0;
				}
				if (this.rigidBody.speed.y < this.maxSpeed.y) {
					this.rigidBody.speed.y = this.maxSpeed.y;
				}
			} else if (this.gameObject.transform.position.x < this.maxPosition.x) {
				if (this.rigidBody.speed.x < this.maxSpeed.x) {
					this.rigidBody.speed.x = this.maxSpeed.x;
				}
				if (this.rigidBody.speed.y > 0) {
					this.rigidBody.speed.y = 0;
				}
			} else {
				if (this.rigidBody.speed.x > 0) {
					this.rigidBody.speed.x = 0;
				}
				if (this.rigidBody.speed.y < this.maxSpeed.y) {
					this.rigidBody.speed.y = this.maxSpeed.y;
				}
			}
		} else if (this.isReverse) {
			if (this.gameObject.transform.position.y < this.maxPosition.y) {
				if (this.rigidBody.speed.x > 0) {
					this.rigidBody.speed.x = 0;
				}
				if (this.rigidBody.speed.y < this.maxSpeed.y) {
					this.rigidBody.speed.y = this.maxSpeed.y;
				}
			} else if (this.gameObject.transform.position.x > 0) {
				if (this.rigidBody.speed.x > -this.maxSpeed.x) {
					this.rigidBody.speed.x = -this.maxSpeed.x;
				}
				if (this.rigidBody.speed.y > 0) {
					this.rigidBody.speed.y = 0;
				}
			} else {
				if (this.rigidBody.speed.x < 0) {
					this.rigidBody.speed.x = 0;
				}
				if (this.rigidBody.speed.y < this.maxSpeed.y) {
					this.rigidBody.speed.y = this.maxSpeed.y;
				}
			}
		}
	}
}