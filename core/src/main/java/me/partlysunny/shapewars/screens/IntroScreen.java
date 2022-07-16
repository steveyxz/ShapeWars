package me.partlysunny.shapewars.screens;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.kotcrab.vis.ui.widget.VisImage;
import me.partlysunny.shapewars.ShapeWars;
import me.partlysunny.shapewars.effects.sound.SoundEffectManager;
import me.partlysunny.shapewars.util.utilities.TextureManager;
import me.partlysunny.shapewars.util.utilities.Util;

public class IntroScreen extends BasicGuiScreen {

    private float loadingTime = 4f;


    public IntroScreen(ShapeWars game) {
        super(game);
    }

    @Override
    protected void additionalActs(float delta) {
        if (loadingTime > 0f) {
            loadingTime -= delta;
            if (loadingTime < 0f) {
                //Fade into main menu
                game.getScreenManager().pushScreen("mainMenu", "blending");
            }
        }
    }

    @Override
    public void show() {
        super.show();
        SoundEffectManager.play("startup", 1);
    }

    @Override
    protected void createGui() {
        Util.loadVisUI();

        VisImage logo = new VisImage(TextureManager.getTexture("logo"));
        Container<VisImage> logoContainer = new Container<>(logo);
        logoContainer.setSize(512, 512);
        float bottomLine = -128;
        logoContainer.setPosition(camera.viewportWidth / 2f - logoContainer.getWidth() / 2f, 800);

        int bounceTimes = 4;
        float bounceLength = 0.5f;
        float delay = 0.4f;

        SequenceAction sequence = new SequenceAction();
        sequence.addAction(new DelayAction(delay));

        float bounceHeight = 400;
        float bounceFade = 0.7f;

        for (int i = 0; i < bounceTimes; i++) {
            MoveToAction down = new MoveToAction();
            down.setPosition(camera.viewportWidth / 2f - logoContainer.getWidth() / 2f, bottomLine);
            down.setInterpolation(Interpolation.pow2);
            down.setDuration(bounceLength / 2f);
            sequence.addAction(down);
            if (i != bounceTimes - 1) {
                MoveToAction up = new MoveToAction();
                up.setPosition(camera.viewportWidth / 2f - logoContainer.getWidth() / 2f, bottomLine + bounceHeight);
                up.setInterpolation(Interpolation.pow2);
                up.setDuration(bounceLength / 2f);
                sequence.addAction(up);
            }

            bounceHeight *= bounceFade;
        }

        logoContainer.addAction(sequence);

        stage.addActor(logoContainer);
    }
}