package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class WaitBar extends AbstractMiscObjects {
    private Vector2 pos;
    private Vector2 dimension;
    TextureRegion reg;
    TextureRegion peace;
    BitmapFont font;
    float timer = 10;
    public WaitBar(){
        pos = new Vector2(70,0);
        dimension = new Vector2(192,42);
        reg = new TextureRegion(Assets.instance.misc.miscProgressBar);
        font = new BitmapFont();
        font = Assets.instance.endgameScoreFonts;
    }

    @Override
    public void render(SpriteBatch batch)
    {
        timer-= Gdx.graphics.getDeltaTime();
        font.draw(batch,(int)timer+"",pos.x+40,pos.y);
    }

    public void setPos(float x,float y){
        pos.x = x;
        pos.y = y;
    }

    public Vector2 getPos(){
        return pos;
    }

    public Vector2 getDimension() {
        return dimension;
    }

    public int getTimer(){
        return (int) timer;
    }
}
