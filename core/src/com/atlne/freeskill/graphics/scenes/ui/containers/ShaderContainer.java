package com.atlne.freeskill.graphics.scenes.ui.containers;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.ui.SceneElement;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class ShaderContainer<T extends SceneElement> extends Container<T> {

    @Getter @Setter
    private transient String shaderName;
    @Getter @Setter
    private transient Map<String, Object> shaderParameters;

    public ShaderContainer(Core core, T sceneElement, String shaderName, Map<String, Object> shaderParameters) {
        super(core, sceneElement);
        this.shaderName = shaderName;
        this.shaderParameters = shaderParameters;
    }

    @Override
    public void draw(Batch batch) {
        var shader = core.getShaderLibrary().getShader(shaderName);
        shader.bind(core, shaderParameters);

        batch.end();

        batch.begin();
        batch.setShader(shader.getShaderProgram());
        super.draw(batch);
        batch.setShader(null);
        batch.end();

        batch.begin();
    }
}
