package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Constants;

public class adButton extends AbstractButton {
    TextureRegion buttonUp;
    TextureRegion buttonDown;
    TextureRegion currentReg;
    private Vector2 dimension;

    public adButton(float x,float y){
        init(x,y,false);
    }

    @Override
    void init(float x, float y, boolean flip) {
        buttonUp = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.button.addbuttonUp);
        buttonDown = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.button.addbuttonDown);
        currentReg = new TextureRegion(buttonUp);
        pos = new Vector2(x,y);
        dimension = new Vector2(128,128);
        hitbox = new Rectangle(pos.x,pos.y,dimension.x,dimension.y);


    }
    public Rectangle getHitbox(){
        return hitbox;
    }

    @Override
    protected void onClicked() {
        currentReg = buttonDown;
        System.out.println("Есть контакт");
        Constants.NEW_GAME = true;
    }

    protected void release() {
        currentReg = buttonUp;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(currentReg,pos.x,pos.y,dimension.x,dimension.y);
    }

    @Override
    public void setElevator(Elevator elevator) {

    }

    public void setPos(float x,float y) {
        pos.x = x;
        pos.y = y;
    }

    public void update(){
        super.update();
        hitbox.x = pos.x;
        hitbox.y = pos.y;
    }

    public Vector2 getPos(){
        return pos;
    }
}
