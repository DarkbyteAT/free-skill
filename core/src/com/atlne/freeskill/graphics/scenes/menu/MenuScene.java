package com.atlne.freeskill.graphics.scenes.menu;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.atlne.freeskill.graphics.scenes.settings.SettingsScene;
import com.atlne.freeskill.graphics.shaders.Shader;
import com.atlne.freeskill.graphics.ui.Alignment;
import com.atlne.freeskill.graphics.ui.buttons.TextButton;
import com.atlne.freeskill.graphics.ui.containers.ShaderContainer;
import com.atlne.freeskill.graphics.ui.labels.Label;
import com.atlne.freeskill.utils.collections.MapUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.github.czyzby.kiwi.util.tuple.immutable.Pair;

public class MenuScene extends Scene {

    private transient Texture background;
    private transient Label titleLabel;
    private transient ShaderContainer<Label> titleLabelContainer;
    private transient TextButton startButton;
    private transient TextButton settingsButton;
    private transient TextButton exitButton;

    private transient Shader verticalGradientShader;

    public MenuScene(Core core) {
        super(core, false);
    }

    @Override
    public void create() {
        super.create();

        generateBackgroundTexture();

        titleLabel = new Label(core, "FreeSkill", "pixel", FontSize.HUGER);
        titleLabelContainer = new ShaderContainer<>(core, titleLabel, "vertical_wave",
                MapUtils.pairsToMap(
                        Pair.of("u_intensity", 0.04f),
                        Pair.of("u_frequency", 0.25f)
                )
        );

        startButton = new TextButton(core, "Start", "pixel", FontSize.LARGER);
        settingsButton = new TextButton(core, "Settings", "pixel", FontSize.LARGER);
        exitButton = new TextButton(core, "Exit", "pixel", FontSize.LARGER);

        verticalGradientShader = core.getShaderLibrary().getShader("vertical_gradient");

        positionElements();
        addButtonBehaviours();
        
        addSceneElement(titleLabelContainer);
        addSceneElement(startButton);
        addSceneElement(settingsButton);
        addSceneElement(exitButton);
    }

    @Override
    public void dispose() {
        background.dispose();
        super.dispose();
    }

    @Override
    public void draw(Batch batch) {
        updateShaders();

        batch.setShader(verticalGradientShader.getShaderProgram());
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        batch.setShader(null);

        super.draw(batch);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        positionElements();
    }

    private void generateBackgroundTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        background = new Texture(pixmap);
    }

    private void positionElements() {
        titleLabel.setPosition(new Vector2(
                Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() - (titleLabel.getSize().y * 1.25f)
        ));

        titleLabel.setAlignment(Alignment.CENTER);

        startButton.setPosition(new Vector2(
                Gdx.graphics.getWidth() / 2,
                titleLabel.getPosition().y - (titleLabel.getSize().y * 2)
        ));

        startButton.setAlignment(Alignment.CENTER);

        settingsButton.setPosition(new Vector2(
                Gdx.graphics.getWidth() / 2,
                startButton.getPosition().y - (startButton.getSize().y * 3)
        ));

        settingsButton.setAlignment(Alignment.CENTER);

        exitButton.setPosition(new Vector2(
                Gdx.graphics.getWidth() / 2,
                settingsButton.getPosition().y - (settingsButton.getSize().y * 3)
        ));

        exitButton.setAlignment(Alignment.CENTER);
    }

    private void addButtonBehaviours() {
        settingsButton.setClickAction(() -> core.getGraphicsManager().getSceneController().pushScene(new SettingsScene(core)));

        exitButton.setClickAction(Gdx.app::exit);
    }

    private void updateShaders() {
        titleLabelContainer.getShaderParameters().put("u_time", time);
        verticalGradientShader.bind(core, MapUtils.pairsToMap(
                Pair.of("u_startColour", Color.ORANGE),
                Pair.of("u_endColour", Color.PURPLE),
                Pair.of("u_intensity", 0.875f)
        ));
    }
}
