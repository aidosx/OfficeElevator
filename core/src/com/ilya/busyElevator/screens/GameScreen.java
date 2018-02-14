package com.ilya.busyElevator.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.ilya.busyElevator.ad.AdInterface;
import com.ilya.busyElevator.game.Objects.AbstractButton;
import com.ilya.busyElevator.game.Objects.ArrowScroll;
import com.ilya.busyElevator.game.Objects.ElevatorsButton;
import com.ilya.busyElevator.game.Objects.EmptyButton;
import com.ilya.busyElevator.game.Objects.PlayButton;
import com.ilya.busyElevator.game.Objects.SelectButton;
import com.ilya.busyElevator.game.Objects.ToggleButton;
import com.ilya.busyElevator.game.WorldController;
import com.ilya.busyElevator.game.WorldRenderer;
import com.ilya.busyElevator.game.miscObjects.PreviewElevator;
import com.ilya.busyElevator.util.Assets;
import com.ilya.busyElevator.util.Constants;
import com.ilya.busyElevator.util.GamePreferences;


public class GameScreen extends InputAdapter implements Screen {

    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean showMenu;
    private Vector2 animation;
    float alpha;
    private Vector2 bgDimension;
    private PlayButton playButton;
    private ElevatorsButton elevatorsButton;
    private ToggleButton toggleButton;
    private Vector3 coords;
    private boolean pause;
    private boolean showChoosingMenu;
    private Game game;
    private Array<AbstractButton> listOfButtons;
    private TextureRegion miscButtons;
    private TextureRegion miscTouchHand;
    private AdInterface adController;
    private Array<PreviewElevator> previewElevatorsArray;
    private ArrowScroll arrButtonLeft;
    private ArrowScroll arrButtonRight;
    public static int indexForPreviewElevators;
    private SelectButton selectButton;
    private EmptyButton emptyButton;


    public GameScreen(boolean showMenu, Game game, AdInterface adController) {
        this.adController = adController;
        Constants.NEW_GAME = false;
        Constants.GAME_STATE = Constants.GAME_STATE.IN_MENU;
        this.showMenu = showMenu;
        this.game = game;
        init();
    }

    private void init() {
        GamePreferences.instance.load();
        Constants.COINS_COUNT = GamePreferences.instance.coins;
        Constants.SKIN_USING = GamePreferences.instance.skin;
        animation = new Vector2();
        bgDimension = new Vector2(300, 300);
        Assets.instance.init(new AssetManager());
        GamePreferences.instance.loadElevators();
        alpha = 0.2f;
        animation.x = 125;
        animation.y = 0 - bgDimension.y;
        playButton = new PlayButton(animation.x + bgDimension.x / 2, animation.y);
        coords = new Vector3();
        pause = false;
        indexForPreviewElevators = 0;
        miscButtons = new TextureRegion(Assets.instance.misc.miscButtons);
        miscTouchHand = new TextureRegion(Assets.instance.misc.miscTouchHand);
        listOfButtons = new Array<AbstractButton>();
        elevatorsButton = new ElevatorsButton(playButton.getPos().x,playButton.getPos().y-20);
        listOfButtons.add(playButton);
        listOfButtons.add(elevatorsButton);
        adController.showOrLoadInterstital(true);
        previewElevatorsArray = new Array<PreviewElevator>();
        arrButtonLeft = new ArrowScroll(50,810,true);
        arrButtonRight = new ArrowScroll(480,810,false);
        selectButton = new SelectButton((480+50)/2-100,810);
        emptyButton = new EmptyButton(selectButton.getPos().x,selectButton.getPos().y);
        listOfButtons.add(arrButtonLeft);
        listOfButtons.add(arrButtonRight);
        listOfButtons.add(selectButton);
        listOfButtons.add(emptyButton);
        for (int i = 0;i<Assets.instance.previewElevators.size;i++){
            previewElevatorsArray.add(new PreviewElevator(i));
            previewElevatorsArray.get(i).wasBought=GamePreferences.instance.boughtElevators.get(i);
        }
        previewElevatorsArray.get(1).price = 1000;
        previewElevatorsArray.get(2).price = 5000;
        emptyButton.setArrayOfElevators(previewElevatorsArray);

    }


    @Override
    public void show() {
        worldController = new WorldController(this);
        worldRenderer = new WorldRenderer(worldController);
        toggleButton = new ToggleButton(playButton.getPos().x, playButton.getPos().y + 150, worldController);
        listOfButtons.add(toggleButton);
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {

        worldRenderer.render(delta);
        switch (Constants.GAME_STATE) {
            case IN_MENU:
                showMenuStart();
                worldController.updatePeopleBehavior();
                worldController.changeSkinForElevator();
                worldRenderer.renderCoinsCount();
                break;
            case IN_GAME:
                if (pause) return;
                worldRenderer.showGUI = GamePreferences.instance.input;
                showMenuFinish();
                Gdx.input.setInputProcessor(worldController);
                if (GamePreferences.instance.input || !worldRenderer.showTutorial) {
                    worldController.update(delta);
                } else {
                    if (worldRenderer.showTutorial)
                        worldRenderer.renderTutorialArrows();
                    if (Gdx.input.isTouched())
                        worldRenderer.showTutorial = false;
                }
                break;
            case ELEVATOR_CHOOSING:
                worldController.updatePeopleBehavior();
                showMenuFinish();
                elevatorChoosingMenu();
                break;

            case NEW_GAME:
                this.dispose();
                //adController.showOrLoadInterstital(true);
                game.setScreen(new GameScreen(true, game, adController));
                break;
        }
        update();



    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        coords.set(screenX, screenY, 0);
        WorldRenderer.cameraGUI.unproject(coords);

        for (AbstractButton b : listOfButtons) {
            if (coords.x >= b.getHitbox().x) {
                if (coords.x <= b.getHitbox().x + b.getHitbox().width) {
                    if (coords.y >= b.getHitbox().y) {
                        if (coords.y <= b.getHitbox().y + b.getHitbox().height) {
                            b.setPressed(true);
                            b.update();
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        for (AbstractButton b : listOfButtons) {
            if (b.getPressed()) {
                if (playButton.getPressed()) {
                    showMenu = false;
                    Constants.GAME_STATE = Constants.GAME_STATE.IN_GAME;
                }
                if (elevatorsButton.getPressed()) {
                    showMenu = false;
                    Constants.GAME_STATE = Constants.GAME_STATE.ELEVATOR_CHOOSING;
                }
                if (arrButtonLeft.getPressed()){
                    if (indexForPreviewElevators>0){
                        arrButtonLeft.canBePressed = true;
                        indexForPreviewElevators--;
                    }
                    else arrButtonLeft.canBePressed = false;
                }
                if (arrButtonRight.getPressed()){
                    if (indexForPreviewElevators < Assets.instance.previewElevators.size-1)
                        indexForPreviewElevators++;
                }

                b.setPressed(false);
                b.update();
            }

        }
        return false;
    }

    private void showMenuStart() {
        worldRenderer.renderBounceScreen(animation, Interpolation.exp10.apply(alpha), bgDimension, playButton,elevatorsButton,toggleButton,
                miscButtons,miscTouchHand);
        if (alpha>=1) return;
        alpha+=0.01f;

    }

    private void showMenuFinish(){
        if (alpha<=0) {
            showChoosingMenu = true;
            return;
        }
        worldRenderer.renderBounceScreen(animation, Interpolation.exp10.apply(alpha), bgDimension, playButton,elevatorsButton,toggleButton,
                miscButtons,miscTouchHand);
        alpha-=0.1;
        if(!GamePreferences.instance.input)
        worldRenderer.showTutorial = true;
    }

    private void elevatorChoosingMenu(){
        worldRenderer.showElevatorChoosingMenu(previewElevatorsArray,arrButtonLeft,arrButtonRight,selectButton,emptyButton,indexForPreviewElevators);
    }


    private void update(){
        arrButtonLeft.canBePressed = indexForPreviewElevators > 0;
        arrButtonRight.canBePressed = indexForPreviewElevators < Assets.instance.previewElevators.size - 1;
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        pause = true;
    }

    @Override
    public void resume() {
        pause = false;
    }

    @Override
    public void hide() {
        GamePreferences.instance.save(worldController.score);
    }

    @Override
    public void dispose() {
        System.out.println(Constants.COINS_COUNT);
        GamePreferences.instance.save(worldController.score);
        worldRenderer.dispose();
    }

    public void gameOver(){
        if (Constants.adReady) {
            adController.showOrLoadInterstital(false);
        }
    }


}
