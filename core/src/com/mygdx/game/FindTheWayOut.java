package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;

public class FindTheWayOut extends ApplicationAdapter {
	Ball ball;
	Stage stage;
	boolean accelerometerAvail;
	
	@Override
	public void create () {
		ball = new Ball();
		stage = new Stage();
		stage.addActor(ball);
		accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
	}

	@Override
	public void render () {
		ScreenUtils.clear(135/255f, 206/255f, 235/255f, 1);

		changeGravity();

		stage.act();
		stage.draw();
	}

	private void changeGravity() {
		if (accelerometerAvail){
			float[] mat = new float[4 * 4];
			Gdx.input.getRotationMatrix(mat);

			Matrix4 m = new Matrix4(mat);

			Quaternion q = m.getRotation(new Quaternion());
			int dy = ((int)((-1)*(q.y*10)))%10;
			int dx = ((int)((-1)*(q.x*10)))%10;
			if (( ball.x > Gdx.graphics.getWidth() - ball.iWidth && dx > 0) || ( ball.x < 0 && dx < 0) ){
				dx = 0;
				if (ball.x > Gdx.graphics.getWidth() - ball.iWidth){
					ball.x =  Gdx.graphics.getWidth() - ball.iWidth;
				}else {
					ball.x = 0;
				}
			}
			if (( ball.y > Gdx.graphics.getHeight() - ball.iHeight && dy > 0) || ( ball.y < 0 && dy < 0) ){
				dy = 0;
				if (ball.y > Gdx.graphics.getHeight() - ball.iHeight){
					ball.y =  Gdx.graphics.getHeight() - ball.iHeight;
				}else {
					ball.y = 0;
				}
			}
			ball.x = (ball.x + dx) % Gdx.graphics.getWidth();
			ball.y = (ball.y + dy) % Gdx.graphics.getHeight();


			System.out.println(q);
		}
	}

	@Override
	public void dispose () {
	}
}
