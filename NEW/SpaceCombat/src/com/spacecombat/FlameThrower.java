package com.spacecombat;

public class FlameThrower extends Weapon {

	private static final String name = "FlameThrower";
	private static final float damage = 10;
	private static final float reloadTime = 0.2f;
	private static final float shotSpeed = 100;
	private static final float life = 5;
	private static final float accuracy = 32;
	private static final int magazineSize = 3;
	private static final float magazineReloadTime = 1;

	public FlameThrower(final Vector2 direction) {
		super(FlameThrower.name, FlameThrower.damage, FlameThrower.accuracy,
				FlameThrower.reloadTime, FlameThrower.magazineSize,
				FlameThrower.magazineReloadTime, FlameThrower.life,
				FlameThrower.shotSpeed, direction, true);
	}

	@Override
	protected void fire(final Vector2 position) {
		GameObject.create(PrefabFactory.createShot("flame", position,
				this.shotSpeedVector, this.gameObject.getTags(),
				this.baseDamage, this.powerLevel, FlameThrower.life));
	}

}
