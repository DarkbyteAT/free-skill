package com.atlne.freeskill.graphics.scenes;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.utils.Creatable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Scene extends Stage implements Creatable {

    @NonNull protected transient Core core;

    private transient FrameBuffer frameBuffer;
    private transient SpriteBatch bufferBatch;
    @Getter @Setter protected float alpha = 1;
    @Getter protected float time;

    @Getter private transient final boolean displayPrevious;

    @Override
    public void create() {
        Gdx.app.log(getClass().getSimpleName(), "Initialising scene...");
        setViewport(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        bufferBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        Gdx.app.log(getClass().getSimpleName(), "Disposing scene assets...");
        super.dispose();
    }

    public void display() {
        act();

        frameBuffer.begin();
        draw();
        frameBuffer.end(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextureRegion frameTexture = new TextureRegion(frameBuffer.getColorBufferTexture());
        frameTexture.flip(false, true);
        frameTexture.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        bufferBatch.begin();
        bufferBatch.setColor(1, 1, 1, alpha);
        bufferBatch.draw(frameTexture, 0, 0);
        bufferBatch.end();

        time += Gdx.graphics.getDeltaTime();
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }
}
