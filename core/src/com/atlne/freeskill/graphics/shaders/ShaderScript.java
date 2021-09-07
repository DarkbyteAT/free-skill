package com.atlne.freeskill.graphics.shaders;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.utils.Creatable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ScriptEvaluator;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ShaderScript implements Creatable {

    public static final String TAG = "ShaderScript";

    private transient ScriptEvaluator scriptEvaluator;
    private transient FileHandle scriptFile;

    public ShaderScript(FileHandle scriptFile) {
        this.scriptEvaluator = new ScriptEvaluator();
        this.scriptFile = scriptFile;
    }

    @Override
    public void create() {
        try {
            scriptEvaluator.setParameters(
                    new String[] {
                            "core",
                            "shaderProgram",
                            "parameters"
                    },
                    new Class<?>[] {
                            Core.class,
                            ShaderProgram.class,
                            Map.class
                    }
            );

            scriptEvaluator.cook(scriptFile.readString());
        } catch (CompileException e) {
            Gdx.app.log(TAG, "Error compiling shader script! see log below for details:");
            e.printStackTrace();
        }
    }

    void run(Core core, ShaderProgram shaderProgram, Map<String, Object> parameters) {
        try {
            scriptEvaluator.evaluate(new Object[] {
                    core,
                    shaderProgram,
                    parameters
            });
        } catch (InvocationTargetException e) {
            Gdx.app.log(TAG, "Error invoking shader script! see log below for details:");
            e.printStackTrace();
        }
    }
}
