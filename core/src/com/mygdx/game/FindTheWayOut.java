package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;

public class FindTheWayOut extends ApplicationAdapter {
	SpriteBatch batch;
	boolean gyroscopeAvail;
	Texture img;
	int x, y;
	int dx, dy;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
		x = Gdx.graphics.getWidth()/2;
		y = Gdx.graphics.getHeight()/2;
		dx = 0;
		dy = 0;
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		if (gyroscopeAvail){
			float gyroX = Gdx.input.getGyroscopeX();
			System.out.println(gyroX);
			float gyroY = Gdx.input.getGyroscopeY();
			System.out.println(gyroY);
			dx += (int)(gyroX) % 10;
			dy += (int)(gyroY) % 10;
			x = (x + dx) % Gdx.graphics.getWidth();
			y = (y + dy) % Gdx.graphics.getHeight();
			batch.draw(img, x, y);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
