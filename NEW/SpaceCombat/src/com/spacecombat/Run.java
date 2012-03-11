package com.spacecombat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Run extends Activity {

	private class DemoView extends View implements View.OnTouchListener {
		private final Engine e;
		private final Paint paint;

		public DemoView(final Context context) {
			super(context);
			this.paint = new Paint();
			PrefabFactory.setContext(context);
			CanvasGraphic.setPaint(this.paint);
			this.e = new Engine();
			this.e.createGameObjects();
		}

		@Override
		protected void onDraw(final Canvas canvas) {

			super.onDraw(canvas);
			CanvasGraphic.setCanvas(canvas);
			this.e.drawLoop();
			this.invalidate();			
		}

		@Override
		public boolean onTouch(final View v, final MotionEvent event) {
			
			Input.setX(event.getX());
			Input.setY(event.getY());
			
			if (event.getAction() == event.ACTION_DOWN)
			{
				Input.setClickDown(true);
			}
			if (event.getAction() == event.ACTION_UP)
			{
				Input.setClickDown(false);
			}			
			return true;
		}
	}

	DemoView demoview;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.demoview = new DemoView(this);
		this.demoview.setOnTouchListener(this.demoview);
		setContentView(this.demoview);
	}

}
