package com.spacecombat;

public class PulseCannon extends Weapon {

	private static final String name = "FlameThrower";
	private static final float damage = 10;
	private static final float reloadTime = 1f;
	private static final float shotSpeed = 0;
	private static final float life = .3f;
	private static final float accuracy = 0;
	private static final int magazineSize = 0;
	private static final float magazineReloadTime = 1;

	public PulseCannon(final Vector2 direction) {
		super(PulseCannon.name, PulseCannon.damage, PulseCannon.accuracy,
				PulseCannon.reloadTime, PulseCannon.magazineSize,
				PulseCannon.magazineReloadTime, PulseCannon.life,
				PulseCannon.shotSpeed, direction, true);
		this.powerLevel = 9;
	}

	@Override
	protected void fire(Vector2 position) {
		
		int draw = 4;
		int count = 0;
		
		this.shotSpeedVector.x = 0;
		this.shotSpeedVector.y = -32;
		
		for (float y = gameObject.transform.position.y; y > 0; y-=32)
		{
			if (this.powerLevel > 2 * count && count >= 1 && draw > 0)
			{
				draw--;
			}
			
			count++;
			position.y -= 32;
			GameObject.create(PrefabFactory.createShot("pulse", position,
				this.shotSpeedVector, this.gameObject.getTags(),
				this.baseDamage, draw, PulseCannon.life));
		}
	}

}