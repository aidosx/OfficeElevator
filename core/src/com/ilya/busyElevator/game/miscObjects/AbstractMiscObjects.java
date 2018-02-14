package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractMiscObjects {

    public Vector2 pos;
    public Vector2 dimension;
    public TextureRegion textureRegion;

    public abstract void render(SpriteBatch batch);
}
