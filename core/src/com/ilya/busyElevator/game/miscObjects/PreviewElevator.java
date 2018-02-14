package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class PreviewElevator extends AbstractMiscObjects {



    TextureRegion textureRegion;
    private Vector2 pos;
    private Vector2 dimension;
    private boolean down;
    private float originY;
    public boolean wasBought;
    public int price;


    public PreviewElevator(int index){
        textureRegion = Assets.instance.previewElevators.get(index);
        wasBought = false;
        pos = new Vector2(320-260/2,480-200);
        dimension = new Vector2(260,400);
        originY = pos.y;
        down = false;

    }
    @Override
    public void render(SpriteBatch batch) {
        if (!wasBought){
            batch.setColor(0,0,0,0.9f);
        }
        batch.draw(textureRegion,pos.x,pos.y,dimension.x,dimension.y);
        batch.setColor(1,1,1,1);
        update();
    }

    public void update() {
        if (down)
            pos.y-=0.3;
        else pos.y+=0.3;
        if (pos.y>=originY+10)
            down = true;
        if (pos.y<=originY)
            down = false;
    }


}
