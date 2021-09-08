package com.atlne.freeskill.graphics.shaders;

import com.atlne.freeskill.Core;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data @AllArgsConstructor
public class Shader implements Disposable {

    private transient ShaderProgram shaderProgram;
    private transient ShaderScript shaderScript;

    @Override
    public void dispose() {
        shaderProgram.dispose();
    }

    public void bind(Core core, Map<String, Object> parameters) {
        shaderProgram.bind();
        shaderScript.run(core, shaderProgram, parameters);
    }
}
