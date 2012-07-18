package com.spacecombat.game;


import com.spacecombat.Component;
import com.spacecombat.GameObject;
import com.spacecombat.Time;
import com.spacecombat.Util;

public class PowerUp extends Component  
{
	public float nextChange = 0.0f;
	public float changeTime = 2.0f;
	public boolean canChange = true;

	public float movementTime = 2.0f;
	public float nextMovementChange = 0.0f;

	public int type;
	public final int maxTypes = 2;

	public SimpleMovement sm;

	public boolean destroyMe = false;

	public PowerUp (final SimpleMovement sm, final int type, final boolean canChange)
	{
		this.sm = sm;
		this.type = type;
		this.canChange = canChange;
		
		if (Util.randomNumber(0, 1) == 1)
		{
			sm.setSpeed(-sm.getSpeedX(), sm.getSpeedY());
		}
	}

	public void change ()
	{
		this.nextChange = Time.getTime() + this.changeTime;		
		this.type++;

		if (this.type > this.maxTypes)
		{
			this.type = 0;
		}

		if (this.type == 0)
		{
			this.gameObject.playAnimation("PowerUp");
		}
		if (this.type == 1)
		{
			this.gameObject.playAnimation("Health");
		}
		if (this.type == 2)
		{
			this.gameObject.playAnimation("Missile");
		}
	}

	@Override
	public void collide (final GameObject whatIHit)
	{
		if (whatIHit.hasTag("player") && !whatIHit.hasTag("shot"))
		{
			this.destroyMe = true;
		}
	}

	public int getType ()
	{
		return this.type;
	}
	
	public void setType (int t)
	{
		this.type = t;
		this.type--;
		change();
		
	}

	@Override
	public void onAfterUpdate ()
	{
		if (this.destroyMe)
		{
			this.gameObject.destroy();
		}
	}

	@Override
	public void update ()
	{
		if (this.canChange && Time.getTime() > this.nextChange)
		{
			change();
		}

		if (Time.getTime() > this.nextMovementChange)
		{
			this.nextMovementChange = Time.getTime() + this.movementTime;

			this.sm.setSpeed(-this.sm.getSpeedX(), this.sm.getSpeedY());
		}
	}
}