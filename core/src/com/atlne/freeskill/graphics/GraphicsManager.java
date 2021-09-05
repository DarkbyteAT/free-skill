package com.atlne.freeskill.graphics;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.SceneManager;
import com.atlne.freeskill.graphics.scenes.loading.LoadingScene;
import com.atlne.freeskill.utils.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kotcrab.vis.ui.VisUI;
import lombok.Getter;

import java.time.LocalDateTime;

public class GraphicsManager extends Manager {

    public static final String UI_SKIN_PATH = "/assets/ui/x2/tinted.json";
    public static final String SCREENSHOTS_PATH = "/screenshots";
    public static final Color SCREEN_CLEAR_COLOUR = Color.BLACK;
    public static final int PIXELS_PER_METER = 32;

    @Getter private transient SceneManager sceneManager;

    public GraphicsManager(Core core) {
        super(core);
    }

    @Override
    public void create() {
        VisUI.load(Gdx.files.local(UI_SKIN_PATH));
        VisUI.setDefaultTitleAlign(Align.center);
        VisUI.getSkin().getFont("default-font").getData().markupEnabled = true;
        VisUI.getSkin().getFont("small-font").getData().markupEnabled = true;

        sceneManager = new SceneManager();
        sceneManager.pushScene(new LoadingScene(core));
    }

    @Override
    public void dispose() {
        VisUI.dispose(true);
        sceneManager.dispose();
    }

    public void update() {
        ScreenUtils.clear(SCREEN_CLEAR_COLOUR);
        sceneManager.update();
    }

    public void resize(int width, int height) {
        sceneManager.resize(width, height);
    }

    public void takeScreenshot() {
        PixmapIO.writePNG(Gdx.files.local(String.format("%s/%s.png", SCREENSHOTS_PATH, LocalDateTime.now())),
                getScreenTexture().getTexture().getTextureData().consumePixmap());
    }

    public TextureRegion getScreenTexture() {
        return ScreenUtils.getFrameBufferTexture();
    }
}
