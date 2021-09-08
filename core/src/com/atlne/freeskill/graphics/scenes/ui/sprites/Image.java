package com.atlne.freeskill.graphics.scenes.ui.sprites;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.ui.SceneElement;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

public class Image extends SceneElement implements Disposable {

    @Getter
    private transient Texture texture;

    public Image(Core core, Texture texture) {
        super(core);
        setTexture(texture);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void draw(Batch batch) {
        var realPosition = getRealPosition();
        batch.draw(texture, realPosition.x, realPosition.y, size.x, size.y);
    }

    @Override
    public void update(float delta) {

    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        setSize(new Vector2(texture.getWidth(), texture.getHeight()));
    }
}
