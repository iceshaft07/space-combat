package com.spacecombat;

public class HealthController extends Component {
	public int health;

	@Override
	public void collide(final Collision collision) {
		if (!collision.getWhatIHit().hasTag(collision.getMe().getTags())) {
			
			final ShotCollision sc = (ShotCollision) collision.getWhatIHit().getComponent(ShotCollision.class);					
			final GraphicAnimation animation = this.gameObject.getCurrentAnimation();
			
			if (sc != null)
			{
				health -= sc.getDamage();
			}
			
			if (animation != null)
			{
				if (this.health <= 0 && !(animation.getName().equals("death"))) {
					final GraphicAnimation death = this.gameObject
							.playAnimation("death");
					this.gameObject.destroyAfter((long) death.getDuration());

					this.gameObject.removeComponent(AIScript.class);
				}
			}
		}
	}

	public void setHealth(final int health) {
		this.health = health;
	}
}
