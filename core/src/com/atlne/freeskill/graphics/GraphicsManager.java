package com.atlne.freeskill.graphics;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.SceneController;
import com.atlne.freeskill.graphics.scenes.loading.LoadingScene;
import com.atlne.freeskill.utils.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.Getter;

import java.time.LocalDateTime;

public class GraphicsManager extends Manager {

    public static final String UI_SKIN_PATH = "/assets/ui/x2/tinted.json";
    public static final String SCREENSHOTS_PATH = "/screenshots";
    public static final int PIXELS_PER_METER = 32;

    @Getter
    private transient SceneController sceneController;

    public GraphicsManager(Core core) {
        super(core);
    }

    @Override
    public void create() {
        sceneController = new SceneController();
        sceneController.pushScene(new LoadingScene(core));
    }

    @Override
    public void dispose() {
        sceneController.dispose();
    }

    public void update() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
                | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        sceneController.update();
    }

    public void resize(int width, int height) {
        sceneController.resize(width, height);
    }

    public void takeScreenshot() {
        PixmapIO.writePNG(Gdx.files.local(String.format("%s/%s.png", SCREENSHOTS_PATH, LocalDateTime.now())),
                getScreenTexture().getTexture().getTextureData().consumePixmap());
    }

    public TextureRegion getScreenTexture() {
        return ScreenUtils.getFrameBufferTexture();
    }
}
