package com.atlne.freeskill.graphics.ui;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class SceneElement implements Drawable, Updatable {

    @NonNull
    protected transient Core core;

    @Getter @Setter
    protected boolean visible = true;
    @Getter @Setter
    protected Vector2 position = new Vector2();
    @Getter @Setter
    protected Vector2 size = new Vector2();
    @Getter @Setter
    protected Vector2 transform = new Vector2();
    @Getter @Setter
    protected Alignment alignment = Alignment.BOTTOM_LEFT;

    @Getter @Setter
    protected Scene scene;

    public Rectangle getBounds() {
        var realPosition = getRealPosition();
        return new Rectangle(realPosition.x, realPosition.y, size.x, size.y);
    }

    public boolean isHovering() {
        var mouseCoords = core.getInputManager().getMouseCoords();
        return core.getInputManager().shouldPoll() && getBounds().contains(mouseCoords);
    }

    public Vector2 getRealPosition() {
        return new Vector2(getRealX(), getRealY());
    }

    public float getRealX() {
        return position.x + (size.x * alignment.getHorizontalAdjustment()) + transform.x;
    }

    public float getRealY() {
        return position.y + (size.y * alignment.getVerticalAdjustment()) + transform.y;
    }
}
