package com.ilya.busyElevator.util;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.game.miscObjects.AbstractMiscObjects;

public class Animation {

    float alpha;

    public Animation(){
        init();
    }

    private void init() {
        alpha = 0;
    }

    public void animDown(AbstractMiscObjects object) {
       object.pos.lerp(new Vector2(500,500),alpha);
        update();
    }

    private void update(){
        Interpolation.exp10.apply(alpha);
    }

}
