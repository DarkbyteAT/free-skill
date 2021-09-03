package com.atlne.freeskill.graphics.scenes;

import com.atlne.freeskill.utils.Creatable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Scene extends Stage implements Creatable {

    @Getter private transient boolean displayPrevious = true;

    @Override
    public void create() {
        setViewport(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
    }

    public void display() {
        act();
        draw();
    }
}