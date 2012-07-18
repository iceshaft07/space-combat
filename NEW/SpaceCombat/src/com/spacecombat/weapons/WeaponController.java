package com.spacecombat.weapons;

import com.spacecombat.ClickListener;
import com.spacecombat.Component;
import com.spacecombat.GameObject;
import com.spacecombat.Input;
import com.spacecombat.Time;
import com.spacecombat.game.PowerUp;

public class WeaponController extends Component implements ClickListener {
	public Weapon [] weapons;
	public int selectedWeapon = 0;

	public float nextWeaponSelect = 0.0f;
	public float weaponSelectTime = 1.0f;


	public WeaponController (final int x, final Weapon [] weapons)
	{
		this.selectedWeapon = x;
		this.weapons = weapons;
		Input.subscribeListener(this);
	}
	
	private final static String powerUpTag = "PowerUp";
	@Override
	public void collide(final GameObject whatIHit) {
		if (whatIHit.hasTag(powerUpTag)) {
			final PowerUp powerUp = (PowerUp) whatIHit.getComponent(PowerUp.class);

			if (powerUp != null && this.weapons != null && powerUp.type == this.weapons[this.selectedWeapon].getPowerUpType())
			{
				this.weapons[this.selectedWeapon].powerUp();
			}
		}
	}

	public Weapon getSelectedWeapon()
	{
		return this.weapons[this.selectedWeapon];
	}	

	@Override
	public void onClick(final float x, final float y) 
	{		
		if (Time.getTime() < this.nextWeaponSelect)
		{
			return;
		}

		if (y > 700)
		{
			this.nextWeaponSelect = this.weaponSelectTime + Time.getTime(); 

			this.selectedWeapon++;
			if (this.selectedWeapon >= this.weapons.length)
			{
				this.selectedWeapon = 0;
			}

			for (int z = 0; z < this.weapons.length; z++)
			{
				this.weapons[z].setEnabled(false);
			}

			this.weapons[this.selectedWeapon].setEnabled(true);
		}
	}
}