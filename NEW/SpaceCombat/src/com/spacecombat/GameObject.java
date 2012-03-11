package com.spacecombat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameObject extends Component {

	private static List<GameObject> gameObjects = new ArrayList<GameObject>();
	private static Vector2 distances = new Vector2();
	
	
	private static GameObject staticXGameObject;
	private GameObject XGameObject;
	private Component xComponent;

	public static void create(final GameObject gameObject) {
		GameObject.gameObjects.add(gameObject);
		// System.out.println("created " + gameObject.getName());
		gameObject.onCreate();
		gameObject.onStart();
	}

	public static void executeOnAllWithTags(final String[] search,
			final Function f) {
		for (int staticX = 0; staticX < GameObject.gameObjects.size(); staticX++) {
			staticXGameObject = GameObject.gameObjects.get(staticX);
			if (staticXGameObject.hasTag(search)) {
				f.execute(staticXGameObject);
			}
		}
	}

	public static List<GameObject> findAllByName(final String search) {
		final LinkedList<GameObject> gos = new LinkedList<GameObject>();
		for (int staticX = 0; staticX < GameObject.gameObjects.size(); staticX++) {
			staticXGameObject = GameObject.gameObjects.get(staticX); 
			if (staticXGameObject.getName().equals(search)) {
				gos.add(staticXGameObject);
			}
		}
		return gos;
	}

	public static List<GameObject> findAllByTags(final String[] search,
			final List<GameObject> gos) {
		gos.clear();

		float xMin = 800;
		float xMax = 0;
		float yMin = 800;
		float yMax = 0;

		for (int staticX = 0; staticX < GameObject.gameObjects.size(); staticX++) {
			staticXGameObject = GameObject.gameObjects.get(staticX);
			if (staticXGameObject.hasTag(search)) {
				xMax = Math.max(
						staticXGameObject.transform.position.x,
						xMax);
				xMin = Math.min(
						staticXGameObject.transform.position.x,
						xMin);
				yMax = Math.max(
						staticXGameObject.transform.position.y,
						yMax);
				yMin = Math.min(
						staticXGameObject.transform.position.y,
						yMin);

				gos.add(staticXGameObject);
			}
		}
		GameObject.distances.x = xMax - xMin;
		GameObject.distances.y = yMax - yMin;

		return gos;
	}

	public static GameObject findByName(final String search) {
		for (int staticX = 0; staticX < GameObject.gameObjects.size(); staticX++) {
			staticXGameObject = GameObject.gameObjects.get(staticX);
			if (staticXGameObject.getName().equals(search)) {
				return staticXGameObject;
			}
		}
		return null;
	}

	public static GameObject findRandomByTags(final String[] search) {
		
		final LinkedList<GameObject> gos = new LinkedList<GameObject>();		
		for (int staticX = 0; staticX < GameObject.gameObjects.size(); staticX++) {
			staticXGameObject = GameObject.gameObjects.get(staticX);
			if (staticXGameObject.hasTag(search)) {
				gos.add(staticXGameObject);
			}
		}
		if (gos.size() == 0) {
			return null;
		}
		return gos.get(Util.randomNumber(0, gos.size() - 1));
	}

	public static List<GameObject> getAllGameObjects() {
		return GameObject.gameObjects;
	}

	public static Vector2 getDistances() {
		return GameObject.distances;
	}

	public Transform transform;

	private float destroyTimeStamp;

	//
	private RigidBody rigidBody;

	private String name = "GameObject";

	private String[] tags;

	private boolean isDestroyed = false;

	private boolean isDestroying = false;

	private final List<Component> components;

	public GameObject() {
		this.name = "GameObject";
		this.tags = new String[] {};
		this.transform = new Transform();
		this.components = new LinkedList<Component>();
	}

	public void addComponent(final Component c) {
		c.setGameObject(this);
		// System.out.println("Added:" + c.getClass().getSimpleName());
		this.components.add(c);
	}

	@Override
	public void collide(final Collision collision) {
		if (this.isDestroyed) {
			return;
		}

		for (int x = 0; x < this.components.size(); x++) {
			this.components.get(x).collide(collision);
		}
	}

	@Override
	public void destroy() {
		for (int x = 0; x < this.components.size(); x++) {
			this.components.get(x).onBeforeDestroy();
		}

		this.isDestroyed = true;
		// System.out.println("destroyed " + getName());
	}

	public void destroyAfter(final float time) {
		this.destroyTimeStamp = Time.getTime();

		this.destroyTimeStamp += time;
		this.isDestroying = true;
	}

	@Override
	public void draw() {
		if (this.isDestroyed) {
			return;
		}

		if (this.rigidBody != null) {
			this.rigidBody.draw();
		}

		for (int x = 0; x < this.components.size(); x++) {
			xComponent = this.components.get(x);
			if (xComponent.isEnabled()) {
				xComponent.draw();
			}
		}
	}

	public GraphicAnimation getAnimation(final String name) {
		for (int x = 0; x < this.components.size(); x++) {
			xComponent = this.components.get(x);
			if (xComponent instanceof GraphicAnimation) {
				if (((GraphicAnimation) xComponent).getName()
						.equals(name)) {
					return (GraphicAnimation) xComponent;
				}
			}

		}
		return null;
	}

	public Component getComponent(final Class<? extends Component> getClass) {
		// String s = "";
		for (int x = 0; x < this.components.size(); x++) {
			// s += c.getClass().getSimpleName() + ",";

			xComponent = this.components.get(x);
			if (xComponent.getClass() == getClass) {
				// System.out.println(getName() + " found " +
				// getClass.getSimpleName());
				return xComponent;
			}
		}

		// System.out.println(getName() + " did not find " +
		// getClass.getSimpleName() + " in " + s);
		return null;
	}

	public GraphicAnimation getCurrentAnimation() {
		for (int x = 0; x < this.components.size(); x++) {
			xComponent = this.components.get(x);
			if (xComponent instanceof GraphicAnimation) {
				if (xComponent.isEnabled()) {
					return (GraphicAnimation) xComponent;
				}
			}
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public String getPrintableTags() {
		String s = "";
		if (this.tags != null) {
			for (int x = 0; x < this.tags.length; x++) {
				s += this.tags[x] + ",";
			}
		}
		return s;
	}

	public RigidBody getRigidBody() {
		return this.rigidBody;
	}

	public String[] getTags() {
		return this.tags;
	}

	public boolean hasTag(final String tag) {
		if (this.tags == null) {
			return false;
		}
		if (tag == null) {
			return false;
		}

		for (int x = 0; x < this.tags.length; x++) {
			if (this.tags[x].equalsIgnoreCase(tag)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasTag(final String[] otherTags) {
		if (this.tags == null) {
			return false;
		}
		if (otherTags == null) {
			return false;
		}

		for (int x = 0; x < this.tags.length; x++) {
			for (int y = 0; y < otherTags.length; y++) {
				if (this.tags[x].equalsIgnoreCase(otherTags[y])
						&& otherTags[y] != null) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	@Override
	public void onCreate() {
		if (this.rigidBody != null) {
			this.rigidBody.onCreate();
		}

		for (int x = 0; x < this.components.size(); x++) {
			this.components.get(x).onCreate();
		}
	}

	@Override
	public void onStart() {
		if (this.rigidBody != null) {
			this.rigidBody.onStart();
		}

		for (int x = 0; x < this.components.size(); x++) {
			this.components.get(x).onStart();
		}
	}

	public GraphicAnimation playAnimation(final String name) {
		GraphicAnimation animation = null;

		for (int x = 0; x < this.components.size(); x++) {
			xComponent = this.components.get(x);
			if (xComponent instanceof GraphicAnimation) {
				final GraphicAnimation gl = (GraphicAnimation) xComponent;

				if (gl.getName().equals(name)) {
					System.out.println("Playing " + name);
					gl.setEnabled(true);
					animation = gl;
				} else {
					gl.setEnabled(false);
				}
			}
		}

		return animation;
	}

	public void removeComponent(final Class<? extends Component> getClass) {
		for (int x = 0; x < this.components.size(); x++) {
			xComponent = this.components.get(x);
			if (xComponent.getClass() == getClass) {
				this.components.remove(xComponent);
				return;
			}

		}
	}

	public void removeComponent(final Component c) {
		this.components.remove(c);
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRigidBody(final RigidBody rigidBody) {
		// System.out.println("Created RigidBody:"+rigidBody);
		rigidBody.setGameObject(this);
		this.rigidBody = rigidBody;
		rigidBody.setGameObject(this);
	}

	public void setTags(final String[] tags) {
		this.tags = tags;
	}

	@Override
	public void update() {
		if (this.isDestroyed) {
			return;
		}

		// technically should happen somewhereElse
		if (this.rigidBody != null) {
			// System.out.println("Updating RigidBody");
			this.rigidBody.update();
		}

		for (int x = 0; x < this.components.size(); x++) {
			xComponent = this.components.get(x);			
			if (xComponent.isEnabled()) {
				// System.out.println("Updating:" +
				// c.getClass().getSimpleName());
				xComponent.update();
			}
		}

		if (this.isDestroying) {
			if (this.destroyTimeStamp < Time.getTime()) {
				destroy();
			}
		}
	}

}
