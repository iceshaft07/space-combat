package com.spacecombat;

public class WeaponController extends Component {
	public Weapon weapon;
	
	public WeaponController (Weapon weapon)
	{
		this.weapon = weapon;
	}

	@Override
	public void collide(final Collision collision) {
		
		if (collision.getWhatIHit().hasTag(new String [] {"PowerUp"})) {
			
			final PowerUp powerUp = (PowerUp) collision.getWhatIHit().getComponent(PowerUp.class);
			
			if (powerUp != null && weapon != null)
			{
				weapon.powerUp();					
			}

			collision.getWhatIHit().destroy();
		}
		else
		{
			System.out.println("Bad Collision " + collision.getWhatIHit().getName());
		}
	}
}
