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
    public static final String SHADER_LIBRARY_PATH = "assets/shaders/";

    private transient Map<String, ShaderProgram> shaderMap = new HashMap<>();

    public ShaderLibrary(Core core) {
        super(core);
    }

    @Override
    public void create() {
        var shaderLibraryFolder = Gdx.files.local(SHADER_LIBRARY_PATH);
        Arrays.stream(shaderLibraryFolder.list())
                .filter(FileHandle::isDirectory)
                .forEach(this::compileShader);
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "Disposing all compiled shaders...");
        shaderMap.values().forEach(ShaderProgram::dispose);
        shaderMap.clear();
    }

    private void compileShader(FileHandle shaderFolder) {
        var shaderName = shaderFolder.name();
        var vertexShader = shaderFolder.child(String.format("%s.vert", shaderName));
        var fragmentShader = shaderFolder.child(String.format("%s.frag", shaderName));
        Gdx.app.log(TAG, String.format("Found shader '%s'!", shaderName));

        if(vertexShader.exists() && fragmentShader.exists()) {
            shaderMap.put(shaderName, new ShaderProgram(vertexShader, fragmentShader));
            if(!shaderMap.get(shaderName).isCompiled()) {
                throw new RuntimeException(String.format("Shader '%s' did not compile correctly! See below for log:\n%s",
                        shaderName,
                        shaderMap.get(shaderName).getLog()));
            }
        } else {
            throw new RuntimeException(String.format("Vertex or fragment shader missing for '%s'!", shaderName));
        }
    }
}
