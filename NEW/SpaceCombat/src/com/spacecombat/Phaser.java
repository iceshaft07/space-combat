package com.spacecombat;

public class Phaser extends Weapon //
{
	private static final String name = "phaser";
	private static final float damage = 10;
	private static final float shotSpeed = 128;
	private static final float life = 5;
	private static final float accuracy = 0;

	public Phaser(final Vector2 direction, final float reloadTime) {
		super(Phaser.name, Phaser.damage, Phaser.accuracy, reloadTime, 0,
				reloadTime, Phaser.life, Phaser.shotSpeed, direction, false);
	}

	public Phaser(final Vector2 direction, final float reloadTime,
			final int magazineSize, final float magazineReloadTime) {
		super(Phaser.name, Phaser.damage, 0, reloadTime, magazineSize,
				magazineReloadTime, Phaser.life, Phaser.shotSpeed, direction,
				false);
	}

	@Override
	protected void fire(final Vector2 position) {
		// TODO Auto-generated method stub
		GameObject.create(PrefabFactory.createShot("phaser", position,
				this.shotSpeedVector, this.gameObject.getTags(),
				this.baseDamage, this.powerLevel, Phaser.life));
	}
}
