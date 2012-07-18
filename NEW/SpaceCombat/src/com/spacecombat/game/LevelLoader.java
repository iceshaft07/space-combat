package com.spacecombat.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;

import com.spacecombat.BoxCollider;
import com.spacecombat.Collider;
import com.spacecombat.FixedJoint;
import com.spacecombat.GameObject;
import com.spacecombat.Level;
import com.spacecombat.R;
import com.spacecombat.RigidBody;
import com.spacecombat.Vector2;

public class LevelLoader {

	private static Context context;

	public static InputStream getLevel (final String name)
	{
		System.out.println("Loading " + name);
		if (name.equalsIgnoreCase("level1"))
		{
			return LevelLoader.context.getResources().openRawResource(R.raw.level1);
		}
		if (name.equalsIgnoreCase("level2"))
		{
			return LevelLoader.context.getResources().openRawResource(R.raw.level2);
		}

		System.out.println("ERROR, NEED TO UPDATE LevelLoader.java WITH LEVEL NAME:" + name);
		return null;
	}

	public static void loadLevel (final String name) 
	{
		final List<GameObject> gos = GameObject.getAllGameObjects();
		for (int x = 0; x < gos.size(); x++)
		{
			if (gos.get(x).getDestroyOnLevelLoad())
			{
				gos.remove(x);
				x--;
			}
		}

		System.out.println("-START-");
		for (final GameObject o : GameObject.getAllGameObjects())
		{
			System.out.println(o.getName());
		}
		System.out.println("------");


		final InputStream is = LevelLoader.getLevel(name);
		final java.io.InputStreamReader br = new java.io.InputStreamReader(is);

		final StringBuffer sb = new StringBuffer();


		GameObject level = null;
		while (true)
		{
			int ascii;

			try {
				ascii = br.read();
			} catch (final IOException e) {
				try {
					br.close();
				} catch (final IOException e1) {
					throw new RuntimeException(e1);
				}
				throw new RuntimeException(e);
			}

			if (ascii == -1)
			{
				break;
			}

			final char letter = (char)ascii;
			sb.append(letter);
		}

		final String file = sb.toString();
		final String[] lines = file.split("\\n");

		boolean ignoring = false;
		for (int x = 0; x < lines.length; x++)
		{
			if (ignoring)
			{
				if (lines[x].startsWith("*/"))
				{
					ignoring = false;
				}
				continue;
			}			
			if (lines[x].startsWith("//"))
			{
				continue;
			}			
			if (lines[x].startsWith("/*"))
			{
				ignoring = true;
				continue;
			}

			if (lines[x].startsWith("createLevel"))
			{
				x++;
				final String wadName = lines[x].trim();
				x++;
				final String smapWidth = lines[x].trim();
				x++;
				final String smapHeight = lines[x].trim();

				final int mapWidth = Integer.parseInt(smapWidth);
				final int mapHeight = Integer.parseInt(smapHeight);
				final int [] map = new int[mapWidth*mapHeight];

				for (int y = 0; y < mapHeight; y++)
				{
					x++;
					final String [] parts = lines[x].split(",");
					for (int z = 0; z < parts.length && z < mapWidth; z++)
					{
						map[y*mapWidth + z] = Integer.parseInt(parts[z].trim());
					}
					System.out.println();
				}

				level = PrefabFactory.createLevel("level " + name, map, mapWidth, mapHeight, wadName);
				//level.addComponent(new RandomSpawner());

				System.out.println("Created Level!");
				GameObject.create(level);
			}

			if (lines[x].startsWith("createEnemy"))
			{
				x++;
				final String sspawnX = lines[x].trim();
				final int spawnX = Integer.parseInt(sspawnX);
				x++;
				final String sspawnY = lines[x].trim();
				final int spawnY = Integer.parseInt(sspawnY);
				x++;
				final String senemyX = lines[x].trim();
				final int enemyX = Integer.parseInt(senemyX);
				x++;
				final String senemyY = lines[x].trim();
				final int enemyY = Integer.parseInt(senemyY);
				x++;
				final String senemyType = lines[x].trim();
				final int enemyType = Integer.parseInt(senemyType);
				x++;
				final String sscriptType = lines[x].trim();
				final int scriptType = Integer.parseInt(sscriptType);
				x++;
				final String sisReversed = lines[x].trim();
				final boolean isReversed = Boolean.parseBoolean(sisReversed);

				System.out.println("CREATED ENEMY:" + new Vector2(enemyX,enemyY) + " " + enemyType + " " + scriptType + " " + isReversed);

				final GameObject objectToCreate = PrefabFactory.createEnemy("enemy", new Vector2(enemyX,enemyY), enemyType, scriptType, isReversed);
				final GameObject spawner = PrefabFactory.createSpawner(spawnX, spawnY, objectToCreate);

				GameObject.create(spawner);
			}

			if (lines[x].startsWith("createPowerUp"))
			{
				x++;
				final int spawnX = Integer.parseInt(lines[x].trim());
				x++;
				final int spawnY = Integer.parseInt(lines[x].trim());
				x++;
				final int powerupX = Integer.parseInt(lines[x].trim());
				x++;
				final int powerupY = Integer.parseInt(lines[x].trim());
				x++;
				final int powerupType = Integer.parseInt(lines[x].trim());
				x++;
				final boolean canChange = Boolean.parseBoolean(lines[x].trim());

				System.out.println("CREATED POWER UP:" + new Vector2(powerupX,powerupY) + " " + powerupType + " " + " " + canChange);

				final GameObject objectToCreate = PrefabFactory.createPowerUp(new Vector2(powerupX,powerupY), powerupType, canChange);
				final GameObject spawner = PrefabFactory.createSpawner(spawnX, spawnY, objectToCreate);

				GameObject.create(spawner);
			}
			//
			if (lines[x].startsWith("createAlly"))
			{
				x++;
				final int spawnX = Integer.parseInt(lines[x].trim());
				x++;
				final int spawnY = Integer.parseInt(lines[x].trim());
				x++;
				final int allyX = Integer.parseInt(lines[x].trim());
				x++;
				final int allyY = Integer.parseInt(lines[x].trim());
				x++;
				final String allyType = lines[x].trim();
				x++;
				final String weapon = lines[x].trim();

				System.out.println("CREATED ALLY:" + new Vector2(allyX,allyY) + " " + allyType + " " + " " + weapon);

				final GameObject objectToCreate = PrefabFactory.createAlly("Ally - " + allyType, new Vector2(allyX, allyY), allyType, weapon);
				final GameObject spawner = PrefabFactory.createSpawner(spawnX, spawnY, objectToCreate);

				GameObject.create(spawner);
			}

			if (lines[x].startsWith("createPlayer"))
			{
				x++;
				final int spawnX = Integer.parseInt(lines[x].trim());
				x++;
				final int spawnY = Integer.parseInt(lines[x].trim());
				x++;
				final int playerX = Integer.parseInt(lines[x].trim());
				x++;
				final int playerY = Integer.parseInt(lines[x].trim());
				x++;
				final String playerType = lines[x].trim();

				System.out.println("CREATED PLAYER:" + new Vector2(playerX,playerY) + " " + playerType + " ");

				final GameObject objectToCreate = PrefabFactory.createPlayer("Player1", new Vector2(playerX,playerY), playerType);
				objectToCreate.setDestroyOnLevelLoad(false);
				final GameObject spawner = PrefabFactory.createSpawner(spawnX, spawnY, objectToCreate);

				GameObject.create(spawner);
								
				GameObject top = PrefabFactory.createTopOfScreen();
				GameObject.create(top);

				GameObject cam = PrefabFactory.createCamera("Camera", new Vector2(playerX,playerY), objectToCreate);

				GameObject hud = PrefabFactory.createHUD(cam);
				GameObject.create(hud);
				
				hud.transform.position.x = cam.transform.position.x + 100;
				hud.transform.position.y = cam.transform.position.y + 100;
				
				top.transform.position.x = cam.transform.position.x;
				top.transform.position.y = cam.transform.position.y;
				
				
				GameObject.create(cam);
			}

			if (lines[x].startsWith("loadLevelOnCollision"))
			{
				x++;
				final String nextLevel = lines[x].trim();
				x++;
				final int spawnX = Integer.parseInt(lines[x].trim());
				x++;
				final int spawnY = Integer.parseInt(lines[x].trim());

				final LoadLevelOnCollision lloc = new LoadLevelOnCollision(nextLevel);
				//final FixedJoint fj = new FixedJoint(level);

				final GameObject spawner = new GameObject();
				final RigidBody r = new RigidBody();
				final Collider c = new BoxCollider(new Vector2(800,32));
				r.setCollider(c);
				spawner.setRigidBody(r);				
				spawner.transform.position.x = spawnX;
				spawner.transform.position.y = spawnY;
				spawner.addComponent(lloc);				
				//spawner.addComponent(fj);
				GameObject.create(spawner);
				System.out.println("CREATED LEVEL LOADER:" + new Vector2(spawnX,spawnY) + " " + nextLevel);
			}
		}

		System.out.println("-END-");
		for (final GameObject o : GameObject.getAllGameObjects())
		{
			System.out.println(o.getName());
		}
		System.out.println("------");

		try {
			br.close();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void setContext (final Context c)
	{
		LevelLoader.context = c;
	}
}
