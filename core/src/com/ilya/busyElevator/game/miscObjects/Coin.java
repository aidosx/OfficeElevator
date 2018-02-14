package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class Coin extends AbstractMiscObjects {


    public Coin(){
        init();
    }

    private void init() {
        textureRegion = new TextureRegion(Assets.instance.coin.coin);
        textureRegion.flip(false,true);
        pos = new Vector2(480,910);
        dimension = new Vector2(32,32);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(textureRegion,pos.x,pos.y,dimension.x,dimension.y);
    }
}
