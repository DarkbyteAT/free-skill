package com.atlne.freeskill.graphics.ui.containers;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.ui.SceneElement;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Getter;

public class Container<T extends SceneElement> extends SceneElement{

    @Getter
    private transient T sceneElement;

    public Container(Core core, T sceneElement) {
        super(core);
        setSceneElement(sceneElement);
    }

    @Override
    public void draw(Batch batch) {
        sceneElement.draw(batch);
    }

    @Override
    public void update(float delta) {
        sceneElement.update(delta);
    }

    public void setSceneElement(T sceneElement) {
        this.sceneElement = sceneElement;
        sceneElement.setTransform(getRealPosition());
    }
}
