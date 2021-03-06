package com.spacecombat;

public class AIScriptOne extends AIScript 
{
	private boolean isReverse = false;

	private RigidBody rigidBody;	
	private Vector3 accel = new Vector3(2,2,0);
	private Vector3 maxSpeed = new Vector3(32,32,0);
	private Vector3 startPos = new Vector3(0,0,0);
	private int maxWidth = 150;
	private int count = 0;

	public void onCreate ()
	{
		rigidBody = gameObject.getRigidBody();
		startPos = new Vector3(gameObject.transform.position);
		
		if (isReverse)
		{
			rigidBody.speed.x = -rigidBody.speed.x;
		}
	}
	
	public void update ()
	{
		if (rigidBody == null)
		{
			return;
		}
		
		if (rigidBody.speed.y < maxSpeed.y)
		{
			rigidBody.speed.y+=accel.y;
		}
		
		if (!isReverse && gameObject.transform.position.x < startPos.x + maxWidth)
		{
			if (rigidBody.speed.x < maxSpeed.x)
				rigidBody.speed.x+=accel.x;
		}
		else if (gameObject.transform.position.x > startPos.x - maxWidth)
		{
			isReverse = true;
			if (rigidBody.speed.x > -maxSpeed.x)
				rigidBody.speed.x-=accel.x;
		}
		else
		{
			isReverse = false;
		}						
	}
}
