package com.spacecombat;

public interface GenericText {
		public void create(String text);

		public void draw(int x, int y, int width, int height, int offsetx,
				int offsety, int rotx, int roty, int scalex, int scaley);

		public int getHeight();

		public int getWidth();
}
