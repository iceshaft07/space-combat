package com.spacecombat;

import java.io.InputStream;

public class Laser extends Weapon
{
	private static final String name = "laser";
	private static final float damage = 10;	
	private static final float reloadTime = 1;
	private static final float shotSpeed = 128;
	private static final float life = 5;
	
	public Laser(Vector3 direction,InputStream shotImage) 
	{
		super(name, damage, shotImage, reloadTime, life, shotSpeed, direction);
	}
	
	protected void fire (Vector3 position, InputStream is) 
	{
		lastShotTime = Time.getTime();
		GameObject.create(PrefabFactory.createLaser(position, shotSpeedVector, gameObject.getTags(), shotImage, baseDamage, powerLevel, life));			
	}	
}
