package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ball extends Actor {
    float x;
    float y;
    int iWidth, iHeight;
    Texture image;
    TextureRegion currentFrame;
    Animation aniMove;

    enum STATE{
        Moving, Idle
    }
    STATE state;

    public Ball(){
        this.x = 0;
        this.y = 0;
        init();
    }

    public Ball(float x, float y) {
        this.x = x;
        this.y = y;
        init();
    }

    public void init(){
        iWidth = 100;
        iHeight = 100;
        state = STATE.Idle;
        image = new Texture("Ball.png");
        currentFrame = new TextureRegion(image);
        TextureRegion[] ballMovement = new TextureRegion[2];
        ballMovement[0] = currentFrame;
        ballMovement[1] = currentFrame;
        aniMove = new Animation(0.1f, ballMovement);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, x, y, iWidth, iHeight);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
