package com.spacecombat;

public class GraphicAnimation extends Component {
	private final GenericGraphic graphic;
	private final Animation animation;
	private float lastDraw = 0;

	public GraphicAnimation(final GenericGraphic graphic,
			final Animation animation) {
		this.graphic = graphic;
		this.animation = animation;
		setEnabled(false);
	}

	@Override
	public void draw() {
		if (this.graphic == null || this.animation == null) {
			return;
		}

		this.graphic.draw(this.animation.getX(), this.animation.getY(),
				this.animation.getWidth(), this.animation.getHeight(),
				(int) this.gameObject.transform.position.x,
				(int) this.gameObject.transform.position.y,
				(int) this.gameObject.transform.rotation.x,
				(int) this.gameObject.transform.rotation.y,
				(int) this.gameObject.transform.scale.x,
				(int) this.gameObject.transform.scale.y);

		if (Time.getTime() - this.lastDraw > (1.0 / this.animation.getFps())) {
			this.lastDraw = Time.getTime();
			this.animation.nextFrame();
		}
	}

	public float getDuration() {

		return (1.0f / this.animation.getFps())
				* (this.animation.getEndFrame() - this.animation
						.getStartFrame());
	}

	public String getName() {
		return this.animation.getName();
	}

	public boolean isDone() {
		return this.animation.isDone();
	}//

	public void play() {
		setEnabled(true);
	}
}
