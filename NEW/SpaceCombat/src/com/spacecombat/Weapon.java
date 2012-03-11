package com.spacecombat;

public abstract class Weapon extends Component {
	protected float baseDamage;
	protected int powerLevel;
	protected int maxPowerLevel;
	protected float reloadTime;
	protected float nextShotTime;
	protected String name;
	protected float shotSpeed;
	protected float life;
	protected Vector2 shotSpeedVector;
	protected float accuracy;
	protected boolean usePhysics = true;
	protected int magazineSize;
	protected int shots;
	protected float magazineReloadTime;

	public Weapon(final String name, final float damage, final float accuracy,
			final float reloadTime, final int magazineSize,
			final float magazineReloadTime, final float life,
			final float shotSpeed, final Vector2 shootDirection,
			final boolean usePhysics) {
		this.name = name;
		this.baseDamage = damage;
		this.nextShotTime = 0;
		this.powerLevel = 1;
		this.maxPowerLevel = 9;
		this.reloadTime = reloadTime;
		this.accuracy = accuracy;
		this.shotSpeed = shotSpeed;
		this.life = life;
		this.usePhysics = usePhysics;
		this.shots = 0;
		this.magazineSize = magazineSize;
		this.magazineReloadTime = magazineReloadTime;
		setShootDirection(shootDirection);
	}

	public boolean canShoot() {
		if (Time.getTime() > this.nextShotTime) {
			return true;
		}
		return false;
	}

	protected abstract void fire(Vector2 position);

	public Vector2 getShotDirection() {
		return this.shotSpeedVector;
	}

	public void setShootDirection(final Vector2 v) {
		v.normalize();

		if (this.shotSpeedVector == null) {
			this.shotSpeedVector = new Vector2();
		}

		this.shotSpeedVector.x = v.x * this.shotSpeed;
		this.shotSpeedVector.y = v.y * this.shotSpeed;
	}

	public void shoot() {
		if (!canShoot()) {
			return;
		}

		this.shots++;

		if (this.shots > this.magazineSize) {
			this.nextShotTime = Time.getTime() + this.magazineReloadTime;
			this.shots = 0;
		} else {
			this.nextShotTime = Time.getTime() + this.reloadTime;
		}

		if (this.usePhysics) {
			this.shotSpeedVector.x += this.gameObject.getRigidBody().speed.x;
			this.shotSpeedVector.y += this.gameObject.getRigidBody().speed.y;
		}

		float randomX = Util.randomNumber(0, this.accuracy);
		float randomY = Util.randomNumber(0, this.accuracy);

		final int upDown = Util.randomNumber(0, 1);
		final int leftRight = Util.randomNumber(0, 1);

		if (upDown == 1) {
			randomY = -randomY;
		}
		if (leftRight == 1) {
			randomX = -randomX;
		}

		this.shotSpeedVector.x += randomX;
		this.shotSpeedVector.y += randomY;

		fire(this.gameObject.transform.position);

		this.shotSpeedVector.x -= randomX;
		this.shotSpeedVector.y -= randomY;

		if (this.usePhysics) {
			this.shotSpeedVector.x -= this.gameObject.getRigidBody().speed.x;
			this.shotSpeedVector.y -= this.gameObject.getRigidBody().speed.y;
		}

	}
}
