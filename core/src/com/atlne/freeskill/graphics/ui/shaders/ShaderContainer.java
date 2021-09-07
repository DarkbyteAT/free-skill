package com.atlne.freeskill.graphics.ui.shaders;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.ui.Resizable;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class ShaderContainer<T extends Actor> extends Container<T> implements Resizable {

    private transient Core core;
    @Getter @Setter private transient String shaderName;
    @Getter @Setter private transient Map<String, Object> shaderParameters;

    public ShaderContainer(Core core, T actor, String shaderName, Map<String, Object> shaderParameters) {
        super(actor);
        this.core = core;
        this.shaderName = shaderName;
        this.shaderParameters = shaderParameters;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        var shader = core.getShaderLibrary().getShader(shaderName);
        shader.bind(core, shaderParameters);

        batch.end();

        batch.begin();
        batch.setShader(shader.getShaderProgram());
        super.draw(batch, parentAlpha);
        batch.setShader(null);
        batch.end();

        batch.begin();
    }

    @Override
    public void resize(int width, int height) {
        if(getActor() instanceof Resizable) {
            ((Resizable) getActor()).resize(width, height);
        }
    }
}
