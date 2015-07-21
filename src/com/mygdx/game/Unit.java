package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hairuo on 7/18/2015.
 */
public class Unit extends Sprite implements InputProcessor{

    private Vector2 velocity = new Vector2();
    private float speed = 60*2 , gravity = 60*1.8f;
    private int[][] collisionLayer;

    public Unit(Sprite sprite, int[][] collisionLayer){

        super(sprite);
        this.collisionLayer = collisionLayer;
        setX(500);
        setY(500);


    }
    public void move(){

    }
    public void draw(Batch spriteBatch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);

    }

    public void update(float delta){
        velocity.y -= gravity*delta;

        if (velocity.y>speed){
            velocity.y = speed;
        }else if (velocity.y<speed){
            velocity.y = -speed;
        }
        if(collisionLayer[(int)(getX() + velocity.x * delta)/16][(int)getY()/16]==1){

        }else{
            setX(getX() + velocity.x * delta);
        }

        if(collisionLayer[(int)(getX())/16][(int)(getY() + velocity.y * delta)/16]==1){
            gravity = 0;
        }else {
            setY(getY() + velocity.y * delta);
        }
        //System.out.println(collisionLayer.getCell(31,31).getTile().getId());
       // System.out.println(collisionLayer.getCell((int)(getX()/16),(int)(getY()/16)).getTile().getId());
        System.out.println((int)getX()/16+","+(int)getY()/16);

    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.W:
                velocity.y= speed*1000;
                break;
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.D:
                velocity.x= speed;
                break;
            case Input.Keys.S:
                velocity.y= -speed;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public float getGravity() {
        return gravity;
    }
}
