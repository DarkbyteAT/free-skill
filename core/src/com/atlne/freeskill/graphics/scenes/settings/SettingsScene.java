package com.atlne.freeskill.graphics.scenes.settings;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.atlne.freeskill.graphics.ui.sprites.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class SettingsScene extends Scene {

    public static final float FADE_IN_DURATION = 0.5f;

    private transient Image obfuscator;

    public SettingsScene(Core core) {
        super(core, true);
    }

    @Override
    public void create() {
        super.create();
        alpha = 0;
        generateBackgroundTexture();
        positionActors();
        addSceneElement(obfuscator);
    }

    @Override
    public void update(float delta) {
        alpha = Math.min(1, alpha + Gdx.graphics.getDeltaTime() / FADE_IN_DURATION);
    }

    private void generateBackgroundTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f);
        pixmap.fillRectangle(0, 0, 1, 1);
        obfuscator = new Image(core, new Texture(pixmap));
    }

    private void positionActors() {
        obfuscator.setSize(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }
}
