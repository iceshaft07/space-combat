package com.spacecombat;

public class MachineGun extends Weapon {

	private static final String name = "MachineGun";
	private static final float damage = 10;
	private static final float reloadTime = 0.2f;
	private static final float shotSpeed = 128;
	private static final float life = 5;
	private static final float accuracy = 40;
	private static final int magazineSize = 5;
	private static final float magazineReloadTime = 1;

	public MachineGun(final Vector2 direction) {
		super(MachineGun.name, MachineGun.damage, MachineGun.accuracy,
				MachineGun.reloadTime, MachineGun.magazineSize,
				MachineGun.magazineReloadTime, MachineGun.life,
				MachineGun.shotSpeed, direction, true);
	}

	@Override
	protected void fire(final Vector2 position) {
		GameObject.create(PrefabFactory.createShot("bullet", position,
				this.shotSpeedVector, this.gameObject.getTags(),
				this.baseDamage, this.powerLevel, MachineGun.life));
	}

}
