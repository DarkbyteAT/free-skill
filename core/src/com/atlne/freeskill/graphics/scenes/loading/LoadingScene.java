package com.atlne.freeskill.graphics.scenes.loading;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.atlne.freeskill.graphics.scenes.menu.MenuScene;
import com.atlne.freeskill.graphics.scenes.ui.Alignment;
import com.atlne.freeskill.graphics.scenes.ui.sprites.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class LoadingScene extends Scene {

    public static final String SPLASH_TEXTURE_PATH = "assets/textures/scenes/loading/splash.jpg";
    public static final String SPLASH_SOUND_EFFECT_NAME = "scenes/loading/splash";
    public static final float FADE_IN_DURATION = 0.5f;
    public static final float FADE_OUT_DURATION = 1.5f;

    private transient Image splash;
    private transient boolean loaded = false;

    public LoadingScene(Core core) {
        super(core, false);
    }

    @Override
    public void create() {
        super.create();
        alpha = 0;
        splash = new Image(core, new Texture(Gdx.files.local(SPLASH_TEXTURE_PATH)));
        splash.setPosition(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
        splash.setAlignment(Alignment.CENTER);
        addSceneElement(splash);
    }

    @Override
    public void update(float delta) {
        if(loaded) {
            alpha -= Gdx.graphics.getDeltaTime() / FADE_OUT_DURATION;
        } else {
            alpha += Gdx.graphics.getDeltaTime() / FADE_IN_DURATION;
        }

        if(!loaded && alpha >= 1f) {
            core.runCreatables();
            core.getAudioPlayer().playSoundEffect(SPLASH_SOUND_EFFECT_NAME);
            loaded = true;
        } else if(loaded && alpha <= 0) {
            core.getGraphicsManager().getSceneController().popScene(this);
            core.getGraphicsManager().getSceneController().pushScene(new MenuScene(core));
        }

        super.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        splash.setPosition(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
    }
}
