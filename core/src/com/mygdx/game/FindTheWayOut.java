package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindTheWayOut extends ApplicationAdapter {
	Ball ball;
	Stage stage;
	boolean accelerometerAvail;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
//	TiledMapTileSet tileset;
//	Map<String,TiledMapTile> wallTiles;
	TiledMapTileLayer layer;
	int dx, dy;
	
	@Override
	public void create () {
		ball = new Ball();
		stage = new Stage();
		stage.addActor(ball);
		accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w,h);
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("test.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
//		tileset =  tiledMap.getTileSets().getTileSet("wood floor");
//		wallTiles = new HashMap<>();
//		for(TiledMapTile tile:tileset){
//			Object property = tile.getProperties().get("Wall");
//			if(property != null)
//				wallTiles.put((String)property,tile);
//		}

//		ArrayList<TiledMapTileLayer.Cell> waterCellsInScene = new ArrayList<>();
		layer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
//		System.out.println(layer.getName());
//		for(int x = 0; x < layer.getWidth();x++){
//			for(int y = 0; y < layer.getHeight();y++){
//				TiledMapTileLayer.Cell cell = layer.getCell(x,y);
//				Object property = cell.getTile().getProperties().get("Wall");
//				if(property != null){
//					waterCellsInScene.add(cell);
//				}
//			}
//		}
//		System.out.println(waterCellsInScene.size());
		dx = 0;
		dy = 0;
	}

	@Override
	public void render () {
		ScreenUtils.clear(135/255f, 206/255f, 235/255f, 1);

		changeGravity();

		checkCollision();

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		stage.act();
		stage.draw();
	}

	// change the speed of ball with the change of phone's rotation
	private void changeGravity() {
		if (accelerometerAvail){
			float[] mat = new float[4 * 4];
			Gdx.input.getRotationMatrix(mat);

			Matrix4 m = new Matrix4(mat);

			Quaternion q = m.getRotation(new Quaternion());
			dy = ((int)((-1)*(q.y*10)))%10;
			dx = ((int)((-1)*(q.x*10)))%10;
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
		}
	}

	// check the collision between ball and walls, not finished
	private void checkCollision(){
		int tx = (int)ball.x/Gdx.graphics.getWidth()*layer.getWidth();
		int ty = (int)ball.y/Gdx.graphics.getHeight()*layer.getHeight();
		if (layer.getCell(tx, ty)!=null && layer.getCell(tx, ty).getTile().getProperties().get("Wall")!=null){
			if (tx + 1 >= layer.getCell(tx, ty).getTile().getOffsetX()
				&& tx <= layer.getCell((int)tx, (int)ty).getTile().getOffsetX()+layer.getCell((int)tx, (int)ty).getTile().getTextureRegion().getRegionWidth()){
				ball.x = (ball.x - dx) % Gdx.graphics.getWidth();
			}
			if (ty + 1 >= layer.getCell((int)tx, (int)ty).getTile().getOffsetY()
					&& ty <= layer.getCell((int)tx, (int)ty).getTile().getOffsetY()+layer.getCell((int)tx, (int)ty).getTile().getTextureRegion().getRegionHeight()){
				ball.y = (ball.y - dy) % Gdx.graphics.getHeight();
			}
		}
	}

	@Override
	public void dispose () {
		stage.dispose();
	}
}
