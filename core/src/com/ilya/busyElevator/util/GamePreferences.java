package com.ilya.busyElevator.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.ilya.busyElevator.screens.GameScreen;


public class GamePreferences {

    public static final String TAG = GamePreferences.class.getName();

    public static final GamePreferences instance = new GamePreferences();

    private Preferences prefs;
    public int score;
    public int coins;
    public int elevatorChoosed;
    public Array<Boolean> boughtElevators;
    public int skin;

    public boolean input;

    private GamePreferences() {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);

    }

    public void load(){
        score = prefs.getInteger("score", 0);
        input = prefs.getBoolean("input", true);
        coins = prefs.getInteger("coins", 0);
        skin = prefs.getInteger("skin",0);
        elevatorChoosed = prefs.getInteger("elevatorChoosed", 0);



    }

    public void save(int score){
        if (score>this.score) {
            prefs.putInteger("score", score);
        }
        prefs.putBoolean("input", input);
        prefs.putInteger("coins", Constants.COINS_COUNT);
        prefs.putInteger("skin",Constants.SKIN_USING);
        prefs.flush();
    }

    public void loadElevators(){
        boughtElevators = new Array<Boolean>();
        boughtElevators.add(true);
        for (int i = 1;i<Assets.instance.previewElevators.size;i++){
            boughtElevators.add(prefs.getBoolean("boughtElevators"+i, false));
        }
    }

    public void saveBuying(int index){
        prefs.putBoolean("boughtElevators"+index,true);
    }


}
