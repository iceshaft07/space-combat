package com.spacecombat;

import java.io.InputStream;
import java.util.List;

public class Engine implements ClickListener {
	// this is da master list of all components

	// private int fps = 60;
	// private int sleepTime; //calculated

	private class DrawLoop extends Thread {
		@Override
		public void run() {
			while (true) {
				drawLoop();
			}
		}
	}

	private class UpdateLoop extends Thread {
		@Override
		public void run() {
			while (true) {
				updateLoop();
			}
		}
	}

	private boolean useInputStep = false;
	private boolean canRun = true;

	private final boolean useMultithreadedDrawLoop = false;
	private final boolean runDrawLoop = true;

	private final boolean useFrameRate = false;
	private final int drawFrameRate = 32;
	private final boolean useMultithreadedGameLoop = true;
	private final boolean runGameLoop = true;

	private final boolean useGameFrameRate = true;
	private final int updateFrameRate = 20;
	private final boolean printFrameRate = false;

	private float lastTimeCheck = 0;

	private int frames = 0;
	private final Object lock = new Object();

	private GameObject xGameObject;
	private GameObject yGameObject;
	private int x = 0;
	private int y = 0;

	/** The Activity Context ( NEW ) */

	List<GameObject> gameObjects = GameObject.getAllGameObjects();

	/**
	 * * Instance the Cube object and set the Activity Context handed over
	 */
	public Engine() {
		if (this.useMultithreadedDrawLoop) {
			final Thread drawLoop = new DrawLoop();
			drawLoop.start();
		}

		if (this.useMultithreadedGameLoop) {
			final Thread gameLoop = new UpdateLoop();
			gameLoop.start();
		}

		Input.subscribeListener(this);
	}

	public void createGameObjects() {
		int [] map = new int [] {
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
				0,0,0,0,1,0,0,0,
		};

		synchronized (this.lock)
		{
			GameObject.create(PrefabFactory.createLevel(map, 8, 24, "wad3"));
			
			GameObject.create(PrefabFactory.createPlayer("Player1", new Vector2(200, 600), "hunter"));
			
			
			GameObject.create(PrefabFactory.createAlly("Ally1", new Vector2(400,
					700), "sentinel", "laser"));
			GameObject.create(PrefabFactory.createAlly("Ally2",
					new Vector2(0, 700), "pariah", "machinegun"));
			GameObject.create(PrefabFactory.createAlly("Ally3", new Vector2(400,
					500), "renegade", "flamethrower"));
			GameObject.create(PrefabFactory.createAlly("Ally4",
					new Vector2(0, 500), "calumniator", "pulsecannon"));

			/*
			GameObject.create(PrefabFactory.createEnemy("E1", new Vector2(0, 0), 13, 0, false));
			GameObject.create(PrefabFactory.createEnemy("E2", new Vector2(300, 0), 13, 0, false));
			GameObject.create(PrefabFactory.createEnemy("E3", new Vector2(200, 400), 13, 0, false));
			GameObject.create(PrefabFactory.createEnemy("E4", new Vector2(300, 0), 13, 0, false));
			GameObject.create(PrefabFactory.createEnemy("E5", new Vector2(300, 700), 13, 0, false));

			GameObject.create(PrefabFactory.createPowerUp(new Vector2(200, 200), 0, true));
			*/
			GameObject.create(PrefabFactory.createRandomLevel());
		}

		// GameObject.create(PrefabFactory.createEnemy("E2",new
		// Vector2(0,0),1,5,true));

		/*
		 * GameObject.create(PrefabFactory.createEnemy("E3",new
		 * Vector2(80,0),3,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E4",new
		 * Vector2(120,0),4,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E5",new
		 * Vector2(160,0),5,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E6",new
		 * Vector2(200,0),6,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E7",new
		 * Vector2(240,0),7,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E8",new
		 * Vector2(280,0),8,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E7",new
		 * Vector2(320,0),9,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E8",new
		 * Vector2(360,0),10,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E7",new
		 * Vector2(0,200),11,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E8",new
		 * Vector2(200,200),12,0,false));
		 * GameObject.create(PrefabFactory.createEnemy("E8",new
		 * Vector2(360,200),13,0,false));
		 */
	}

	/**
	 * Here we do our drawing
	 */
	public void drawLoop() {
		if (this.useFrameRate) {
			try {
				Thread.sleep(1000 / this.drawFrameRate);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (this.printFrameRate) {
			if (this.lastTimeCheck == 0) {
				this.lastTimeCheck = Time.getTime();
			}

			this.frames++;
			if (Time.getTime() - this.lastTimeCheck > 1) {
				System.out.println("FPS:" + this.frames);
				this.frames = 0;
				this.lastTimeCheck = Time.getTime();
			}
		}

		if (!this.runDrawLoop) {
			return;
		}

		int x = 0;
		synchronized (this.lock) {		
			for (x = 0; x < this.gameObjects.size(); x++) {
				this.gameObjects.get(x).draw();
			}
		}
	}



	public void updateLoop() {
		Input.update();
		if (useInputStep)
		{
			if (!this.canRun) {
				return;
			}			
			this.canRun = false;
		}
		Time.tick();

		if (!this.runGameLoop) {
			return;
		}


		synchronized (this.lock) {
			for (x = 0; x < this.gameObjects.size(); x++) {
				xGameObject = this.gameObjects.get(x);

				xGameObject.update();

				if (xGameObject.getRigidBody() == null
						|| xGameObject.getRigidBody().getCollider() == null) {
					continue;
				}//

				for (y = x + 1; y < this.gameObjects.size(); y++) {
					yGameObject = this.gameObjects.get(y);

					if (yGameObject.getRigidBody() == null
							|| yGameObject.getRigidBody().getCollider() == null) {
						continue;
					}
					
					boolean collision = xGameObject
					.getRigidBody()
					.getCollider()
					.collidesWith(
							yGameObject.getRigidBody().getCollider());
					
					if (collision) {
							xGameObject.collide(new Collision(xGameObject,yGameObject));
							yGameObject.collide(new Collision(yGameObject,
									xGameObject));
					}
				}
			}
		}


		int size = this.gameObjects.size();

		synchronized (this.lock) {
			for (x = 0; x < size; x++) {
				if (this.gameObjects.get(x).isDestroyed()) {
					this.gameObjects.remove(x);
					x--;
					size = this.gameObjects.size();
				}
			}
		}

		if (this.useMultithreadedGameLoop && this.useGameFrameRate) {
			try {
				Thread.currentThread();
				Thread.sleep(1000 / this.updateFrameRate);
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(float x, float y) 
	{
		canRun = true;
	}
}
