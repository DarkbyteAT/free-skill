package com.atlne.freeskill.graphics.scenes.menu;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;

public class MenuScene extends Scene {

    private transient VisLabel title;

    public MenuScene(Core core) {
        super(core, false);
    }

    @Override
    public void create() {
        super.create();

        title = new VisLabel("FreeSkill", new Label.LabelStyle(
                core.getFontManager().getFont("standard", FontSize.HUGE),
                Color.WHITE)
        );

        positionActors();
        addActor(title);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        positionActors();
    }

    private void positionActors() {
        title.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - FontSize.HUGE.getSize(), Align.center);
    }
}
