package com.atlne.freeskill.graphics.scenes.menu;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.atlne.freeskill.graphics.ui.Label;
import com.atlne.freeskill.graphics.ui.buttons.TextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;

public class MenuScene extends Scene {

    private transient Label titleLabel;
    private transient TextButton startButton;
    private transient TextButton settingsButton;
    private transient TextButton exitButton;

    public MenuScene(Core core) {
        super(core, false);
    }

    @Override
    public void create() {
        super.create();

        titleLabel = new Label(core, "FreeSkill", "pixel", FontSize.HUGER);
        startButton = new TextButton(core, "Start", "standard", FontSize.LARGER);
        settingsButton = new TextButton(core, "Settings", "standard", FontSize.LARGER);
        exitButton = new TextButton(core, "Exit", "standard", FontSize.LARGER);

        this.setDebugAll(true);
        positionActors();
        addButtonBehaviours();

        addActor(titleLabel);
        addActor(startButton);
        addActor(settingsButton);
        addActor(exitButton);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        positionActors();
    }

    private void positionActors() {
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - (titleLabel.getHeight() * 1.25f), Align.center);
        startButton.setPosition(Gdx.graphics.getWidth() / 2, titleLabel.getY() - (startButton.getHeight() * 4), Align.center);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2, startButton.getY() - (settingsButton.getHeight() * 4), Align.center);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2, settingsButton.getY() - (exitButton.getHeight() * 4), Align.center);
    }

    private void addButtonBehaviours() {
        exitButton.setClickAction(Gdx.app::exit);
    }
}
