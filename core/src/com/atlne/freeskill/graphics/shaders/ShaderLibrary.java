package com.atlne.freeskill.graphics.shaders;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.utils.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShaderLibrary extends Manager {

    public static final String TAG = "ShaderLibrary";
    public static final String SHADER_LIBRARY_PATH = "shaders";

    private transient Map<String, Shader> shaderMap = new HashMap<>();

    public ShaderLibrary(Core core) {
        super(core);
    }

    @Override
    public void create() {
        ShaderProgram.pedantic = false;

        var shaderLibraryFolder = Gdx.files.local(SHADER_LIBRARY_PATH);
        Arrays.stream(shaderLibraryFolder.list())
                .filter(FileHandle::isDirectory)
                .forEach(this::compileShader);
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "Disposing all compiled shaders...");
        shaderMap.values().forEach(Shader::dispose);
        shaderMap.clear();
    }

    public Shader getShader(String shaderName) {
        return shaderMap.get(shaderName);
    }

    private void compileShader(FileHandle shaderFolder) {
        var shaderName = shaderFolder.name();
        var vertexShaderFile = shaderFolder.child(String.format("vertex.glsl", shaderName));
        var fragmentShaderFile = shaderFolder.child(String.format("fragment.glsl", shaderName));
        var shaderScriptFile = shaderFolder.child(String.format("script.java"));
        Gdx.app.log(TAG, String.format("Found shader '%s'!", shaderName));

        if(vertexShaderFile.exists() && fragmentShaderFile.exists()) {
            var shaderProgram = new ShaderProgram(vertexShaderFile, fragmentShaderFile);
            var shaderScript = new ShaderScript(shaderScriptFile);
            shaderScript.create();
            shaderMap.put(shaderName, new Shader(shaderProgram, shaderScript));

            if(!shaderProgram.isCompiled()) {
                throw new RuntimeException(String.format("Shader '%s' did not compile correctly! See below for log:\n%s",
                        shaderName,
                        shaderProgram.getLog()));
            }
        } else {
            throw new RuntimeException(String.format("Vertex or fragment shader missing for '%s'!", shaderName));
        }
    }
}
