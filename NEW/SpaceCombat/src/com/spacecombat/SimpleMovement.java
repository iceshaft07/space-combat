package com.spacecombat;

public class SimpleMovement extends Component {
	private float speedX = 0;
	private float speedY = 0;

	private RigidBody rigidBody;	
	
	public SimpleMovement (RigidBody r, float x, float y)
	{
		rigidBody = r;
		this.speedX = x;
		this.speedY = y;
	}
	
	public void setSpeed(float x, float y) {
		this.speedX = x;
		this.speedY = y;
	}

	@Override
	public void update() {
		
		if (this.rigidBody == null) {
			this.rigidBody = this.gameObject.getRigidBody();
			return;
		}

		this.rigidBody.speed.x = this.speedX;
		this.rigidBody.speed.y = this.speedY;
	}
}
