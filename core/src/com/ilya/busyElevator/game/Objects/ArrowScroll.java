package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class ArrowScroll  extends AbstractButton {
    public boolean canBePressed;

    public ArrowScroll (float x,float y,boolean flip){
        init(x,y,flip);
    }

    void init(float x, float y, boolean flip) {
        canBePressed = true;
        sound = Assets.instance.buttonPress;
        pressed = false;
        width = 100f;
        height = 100f;
        buttonUp = new TextureRegion(Assets.instance.button.arrScroll);
        buttonDown = new TextureRegion(Assets.instance.button.arrScrollPressed);

        if (flip){
            buttonUp.flip(true,false);
            buttonDown.flip(true,false);
        }


        currentReg = new TextureRegion(buttonUp);

        pos = new Vector2(x,y);

        hitbox = new Rectangle();

        hitbox.x = pos.x;
        hitbox.y = pos.y;
        hitbox.width = width;
        hitbox.height = height;

        command = false;

    }

    @Override
    protected void onClicked(){
        super.onClicked();
    }


    @Override
    public void update(){
        if (canBePressed)
        super.update();
    }

    protected void release() {
        super.release();
    }

    public void setHitbox(Rectangle hitbox){
        this.hitbox = hitbox;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (canBePressed)
            batch.draw(currentReg, pos.x, pos.y,width, height);
        else
            batch.draw(buttonDown,pos.x,pos.y,width,height);
    }

    @Override
    public void setElevator(Elevator elevator) {

    }

    public TextureRegion getReg(){
        return currentReg;
    }

    public Vector2 getPos(){
        return pos;
    }

    public void setPos(float x,float y){
        this.pos.x = x;
        this.pos.y = y;
        hitbox.x = pos.x;
        hitbox.y = pos.y;
    }

    public boolean getPressed(){
        return pressed;
    }
}