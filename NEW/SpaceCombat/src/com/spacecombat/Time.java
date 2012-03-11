package com.spacecombat;

public class Time {
	private static float currentTime = 0;
	private static float deltaTime = 0;
	private static float lastTime = 0;

	public static float getDeltaTime() {
		return Time.deltaTime;
	}

	public static float getTime() {
		return Time.currentTime;
	}

	public static void tick() {
		Time.currentTime = System.nanoTime() / 1000000000.0f;

		Time.deltaTime = Time.currentTime - Time.lastTime;
		Time.lastTime = Time.currentTime;
	}

}
