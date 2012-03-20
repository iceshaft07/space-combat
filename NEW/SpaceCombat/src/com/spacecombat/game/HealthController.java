package com.spacecombat.game;

import com.spacecombat.Collision;
import com.spacecombat.Component;
import com.spacecombat.GraphicAnimation;
import com.spacecombat.ai.AIScript;

public class HealthController extends Component {
	public int health;
	public boolean dying = false;

	@Override
	public void collide(final Collision collision) {
		if (!collision.getWhatIHit().hasTag(collision.getMe().getTags())) {
			
			final ShotCollision sc = (ShotCollision) collision.getWhatIHit().getComponent(ShotCollision.class);					
			
			if (sc != null)
			{
				health -= sc.getDamage();
			}
			
			if (this.health <= 0 && !dying) 
			{
				gameObject.playAnimation("death");
				this.gameObject.destroyAfter(gameObject.getCurrentAnimation().getDuration());
			}
		}
	}

	public void setHealth(final int health) {
		this.health = health;
	}
}
