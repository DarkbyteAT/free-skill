package com.atlne.freeskill.graphics.scenes.menu;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.atlne.freeskill.graphics.ui.buttons.TextButton;
import com.atlne.freeskill.graphics.ui.labels.Label;
import com.atlne.freeskill.graphics.ui.labels.WaveLabel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;

public class MenuScene extends Scene {

    private transient Texture background;
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

        generateBackgroundTexture();
        titleLabel = new WaveLabel(core, "FreeSkill", "pixel", FontSize.HUGER, 0.025f, 1f);
        startButton = new TextButton(core, "Start", "pixel", FontSize.LARGER);
        settingsButton = new TextButton(core, "Settings", "pixel", FontSize.LARGER);
        exitButton = new TextButton(core, "Exit", "pixel", FontSize.LARGER);

        positionActors();
        addButtonBehaviours();

        addActor(titleLabel);
        addActor(startButton);
        addActor(settingsButton);
        addActor(exitButton);
    }

    @Override
    public void dispose() {
        background.dispose();
        super.dispose();
    }

    @Override
    public void draw() {
        var verticalGradientShader = core.getShaderLibrary().getShader("vertical_gradient");
        verticalGradientShader.bind();
        verticalGradientShader.setUniformf("u_startColour", Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b, Color.ORANGE.a);
        verticalGradientShader.setUniformf("u_endColour", Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
        verticalGradientShader.setUniformf("u_intensity", 0.8f);

        getBatch().setShader(verticalGradientShader);
        getBatch().begin();
        getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getBatch().end();
        getBatch().setShader(null);

        super.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        positionActors();
    }

    private void generateBackgroundTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        background = new Texture(pixmap);
    }

    private void positionActors() {
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - (titleLabel.getHeight() * 1.25f), Align.center);
        startButton.setPosition(Gdx.graphics.getWidth() / 2, titleLabel.getY() - (startButton.getHeight() * 3), Align.center);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2, startButton.getY() - (settingsButton.getHeight() * 3), Align.center);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2, settingsButton.getY() - (exitButton.getHeight() * 3), Align.center);
    }

    private void addButtonBehaviours() {
        exitButton.setClickAction(Gdx.app::exit);
    }
}
