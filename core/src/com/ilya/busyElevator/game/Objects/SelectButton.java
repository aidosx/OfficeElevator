package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.screens.GameScreen;
import com.ilya.busyElevator.util.Assets;
import com.ilya.busyElevator.util.Constants;
import com.ilya.busyElevator.util.GamePreferences;

public class SelectButton extends AbstractButton {
    TextureRegion buttonUp;
    TextureRegion buttonDown;
    TextureRegion currentReg;
    private Vector2 dimension;
    public boolean isActive;

    public SelectButton(float x,float y){
        init(x,y,false);
    }

    @Override
    void init(float x, float y, boolean flip) {
        sound = Assets.instance.buttonPress;
        buttonUp = new TextureRegion(Assets.instance.button.selectButton);
        buttonDown = new TextureRegion(Assets.instance.button.selectButtonPressed);
        currentReg = new TextureRegion(buttonUp);
        pos = new Vector2(x,y);
        width = 300f;
        height = 80f;
        dimension = new Vector2(width,height);
        hitbox = new Rectangle(pos.x,pos.y,dimension.x,dimension.y);
        isActive = true;


    }
    public Rectangle getHitbox(){
        return hitbox;
    }

    @Override
    protected void onClicked() {
        currentReg = buttonDown;
        Constants.SKIN_USING = GameScreen.indexForPreviewElevators;
        GameScreen.indexForPreviewElevators = 0;
        Constants.GAME_STATE = Constants.GAME_STATE.IN_MENU;

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
        if (isActive)
        super.update();
        hitbox.x = pos.x;
        hitbox.y = pos.y;
    }

    public Vector2 getPos(){
        return pos;
    }
}
