package com.atlne.freeskill.graphics.scenes.loading;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.atlne.freeskill.graphics.scenes.menu.MenuScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

public class LoadingScene extends Scene {

    public static final String SPLASH_TEXTURE_PATH = "assets/textures/scenes/loading/splash.jpg";
    public static final String SPLASH_SOUND_EFFECT_NAME = "scenes/loading/splash";
    public static final float SPLASH_ALPHA_THRESHOLD = 0.05f;
    public static final float SPLASH_DURATION = 0.5f;

    private transient Image splash;
    private transient Color splashColour = Color.CLEAR;
    private transient boolean loaded = false;

    public LoadingScene(Core core) {
        super(core, false);
    }

    @Override
    public void create() {
        super.create();

        splash = new Image(new Texture(Gdx.files.local(SPLASH_TEXTURE_PATH)));
        splash.setColor(splashColour);
        addActor(splash);
    }

    @Override
    public void draw() {
        splashColour = loaded
                ? splashColour.lerp(Color.BLACK, Gdx.graphics.getDeltaTime() / SPLASH_DURATION)
                : splashColour.lerp(Color.WHITE, Gdx.graphics.getDeltaTime() / SPLASH_DURATION);
        splash.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, Align.center);
        splash.setColor(splashColour);

        super.draw();
    }

    @Override
    public void act() {
        if(!loaded && splashColour.r > 1f - SPLASH_ALPHA_THRESHOLD) {
            core.runCreatables();
            core.getAudioPlayer().playSoundEffect(SPLASH_SOUND_EFFECT_NAME);
            loaded = true;
        } else if(loaded && splashColour.r < SPLASH_ALPHA_THRESHOLD) {
            core.getGraphicsManager().getSceneManager().popScene(this);
            core.getGraphicsManager().getSceneManager().pushScene(new MenuScene(core));
        }

        super.act();
    }
}
