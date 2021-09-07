package com.atlne.freeskill.graphics.ui.labels;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class ShaderLabel extends Label {

    @Getter @Setter private transient String shaderName;
    @Getter @Setter private transient Map<String, Object> parameters;

    public ShaderLabel(Core core, String text, String fontName, FontSize fontSize, String shaderName, Map<String, Object> parameters) {
        super(core, text, fontName, fontSize);
        this.shaderName = shaderName;
        this.parameters = parameters;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        var shader = core.getShaderLibrary().getShader(shaderName);
        shader.bind(core, parameters);

        batch.end();

        batch.begin();
        batch.setShader(shader.getShaderProgram());
        super.draw(batch, parentAlpha);
        batch.setShader(null);
        batch.end();

        batch.begin();
    }
}
