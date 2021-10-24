package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;

public class FindTheWayOut extends ApplicationAdapter {
	SpriteBatch batch;
	boolean accelerometerAvail;
	Texture img;
	int x, y;
	int dx, dy;
	int bWidth, bHeight;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("Ball.png");
		accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
		x = 0;
		y = 0;
		dx = 0;
		dy = 0;
		bWidth = 100;
		bHeight = 100;
	}

	@Override
	public void render () {
		ScreenUtils.clear(135/255f, 206/255f, 235/255f, 1);

		changeGravity();

		batch.begin();
		batch.draw(img, x, y, bWidth,bHeight);
		batch.end();
	}

	private void changeGravity() {
		if (accelerometerAvail){
			float[] mat = new float[4 * 4];
			Gdx.input.getRotationMatrix(mat);

			Matrix4 m = new Matrix4(mat);

			Quaternion q = m.getRotation(new Quaternion());
			dy += ((int)((-1)*(q.y*10)))%10;
			dx += ((int)((-1)*(q.x*10)))%10;
			if (( x > Gdx.graphics.getWidth() - bWidth && dx > 0) || ( x < 0 && dx < 0) ){
				dx = 0;
				if (x > Gdx.graphics.getWidth() - bWidth){
					x =  Gdx.graphics.getWidth() - bWidth;
				}else {
					x = 0;
				}
			}
			if (( y > Gdx.graphics.getHeight() - bHeight && dy > 0) || ( y < 0 && dy < 0) ){
				dy = 0;
				if (y > Gdx.graphics.getHeight() - bHeight){
					y =  Gdx.graphics.getHeight() - bHeight;
				}else {
					y = 0;
				}
			}
			x = (x + dx) % Gdx.graphics.getWidth();
			y = (y + dy) % Gdx.graphics.getHeight();


			System.out.println(q);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
