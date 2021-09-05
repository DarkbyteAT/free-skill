package com.atlne.freeskill.graphics.scenes;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.utils.Creatable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Scene extends Stage implements Creatable {

    protected transient Core core;
    @Getter private transient boolean displayPrevious = true;

    @Override
    public void create() {
        Gdx.app.log(getClass().getSimpleName(), "Initialising scene viewport...");
        setViewport(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void dispose() {
        Gdx.app.log(getClass().getSimpleName(), "Disposing scene assets...");
        super.dispose();
    }

    public void display() {
        act();
        draw();
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }
}
