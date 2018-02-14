package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ilya.busyElevator.game.miscObjects.PreviewElevator;
import com.ilya.busyElevator.screens.GameScreen;
import com.ilya.busyElevator.util.Assets;
import com.ilya.busyElevator.util.Constants;
import com.ilya.busyElevator.util.GamePreferences;

public class EmptyButton  extends AbstractButton {
    TextureRegion buttonUp;
    TextureRegion buttonDown;
    TextureRegion currentReg;
    private Vector2 dimension;
    public boolean isActive;
    Array<PreviewElevator> previewElevators;

    public EmptyButton (float x,float y){
        init(x,y,false);
    }

    @Override
    void init(float x, float y, boolean flip) {
        isActive = false;
        sound = Assets.instance.buttonPress;
        pressed = false;
        width = 300f;
        height = 80f;
        buttonUp = new TextureRegion(Assets.instance.button.emptyButton);
        buttonDown = new TextureRegion(Assets.instance.button.emptyButtonClosed);

        currentReg = new TextureRegion(buttonUp);

        pos = new Vector2(x,y);
        dimension = new Vector2(width,height);

        hitbox = new Rectangle();

        hitbox.x = pos.x;
        hitbox.y = pos.y;
        hitbox.width = width;
        hitbox.height = height;

        command = false;


    }
    public Rectangle getHitbox(){
        return hitbox;
    }

    @Override
    protected void onClicked() {
        currentReg = buttonDown;
        buyElevator();
    }

    protected void release() {
        currentReg = buttonUp;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isActive) {
            batch.draw(currentReg, pos.x, pos.y, dimension.x, dimension.y);
        }
        else batch.draw(buttonDown,pos.x,pos.y,dimension.x,dimension.y);
    }

    @Override
    public void setElevator(Elevator elevator) {

    }

    public void setPos(float x,float y) {
        pos.x = x;
        pos.y = y;
    }

    public void update(){
        if (isActive) {
            super.update();
            hitbox.x = pos.x;
            hitbox.y = pos.y;
        }
    }

    public Vector2 getPos(){
        return pos;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void buyElevator(){
        if (Constants.COINS_COUNT>=previewElevators.get(GameScreen.indexForPreviewElevators).price) {
            Constants.COINS_COUNT-=previewElevators.get(GameScreen.indexForPreviewElevators).price;
            previewElevators.get(GameScreen.indexForPreviewElevators).wasBought = true;
            GamePreferences.instance.saveBuying(GameScreen.indexForPreviewElevators);
        }
    }

    public void setArrayOfElevators(Array<PreviewElevator> previewElevators){
        this.previewElevators = previewElevators;

    }
}