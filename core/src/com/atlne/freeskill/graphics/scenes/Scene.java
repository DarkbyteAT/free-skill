package com.atlne.freeskill.graphics.scenes;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.ui.Drawable;
import com.atlne.freeskill.graphics.ui.SceneElement;
import com.atlne.freeskill.graphics.ui.Updatable;
import com.atlne.freeskill.utils.Creatable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class Scene extends InputAdapter implements Creatable, Disposable, Drawable, Updatable {

    @NonNull
    protected transient Core core;

    @Getter
    private transient OrthographicCamera camera;
    @Getter
    private transient SpriteBatch batch;
    private transient FrameBuffer frameBuffer;
    private transient SpriteBatch bufferBatch;
    private transient ShapeRenderer debugRenderer;

    @Getter
    private transient List<SceneElement> sceneElements = new ArrayList<>();
    @Getter @Setter
    protected float alpha = 1;
    @Getter
    protected float time;
    @Getter
    private transient final boolean displayPrevious;
    @Getter @Setter
    private transient boolean debugEnabled;

    @Override
    public void create() {
        Gdx.app.log(getClass().getSimpleName(), "Initialising scene...");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        bufferBatch = new SpriteBatch();
        debugRenderer = new ShapeRenderer();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        Gdx.app.log(getClass().getSimpleName(), "Disposing scene assets...");
        batch.dispose();
        frameBuffer.dispose();
        bufferBatch.dispose();
        debugRenderer.dispose();
        sceneElements.stream()
                .filter(Disposable.class::isInstance)
                .map(Disposable.class::cast)
                .forEach(Disposable::dispose);
    }

    @Override
    public void draw(Batch batch) {
        batch.begin();
        sceneElements.stream()
                .filter(SceneElement::isVisible)
                .forEach(sceneElement -> sceneElement.draw(batch));
        batch.end();
    }

    @Override
    public void update(float delta) {
        core.getInputManager().setInputScene(this);
        sceneElements.forEach(sceneElement -> sceneElement.update(delta));
    }

    public void display() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        frameBuffer.begin();
        draw(batch);
        frameBuffer.end(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextureRegion frameTexture = new TextureRegion(frameBuffer.getColorBufferTexture());
        frameTexture.flip(false, true);

        bufferBatch.begin();
        bufferBatch.setColor(1, 1, 1, alpha);
        bufferBatch.draw(frameTexture, 0, 0);
        bufferBatch.end();

        if(debugEnabled) {
            drawDebug(debugRenderer);
        }

        update(Gdx.graphics.getDeltaTime());
        time += Gdx.graphics.getDeltaTime();
    }

    public void drawDebug(ShapeRenderer debugRenderer) {
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.LIME);
        sceneElements.forEach(sceneElement -> {
            var bounds = sceneElement.getBounds();
            debugRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        });
        debugRenderer.end();
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    public void addSceneElement(SceneElement sceneElement) {
        sceneElements.add(sceneElement);
        sceneElement.setScene(this);
    }

    public void removeSceneElement(SceneElement sceneElement) {
        sceneElements.remove(sceneElement);
        sceneElement.setScene(null);
    }
}
