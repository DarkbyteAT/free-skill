package com.atlne.freeskill.graphics.ui.labels;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Getter;
import lombok.Setter;

public class WaveLabel extends Label {

    @Getter @Setter private transient float intensity;
    @Getter @Setter private transient float frequency;
    private transient float time;

    public WaveLabel(Core core, String text, String fontName, FontSize fontSize, float intensity, float frequency) {
        super(core, text, fontName, fontSize);
        this.intensity = intensity;
        this.frequency = frequency;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        time += Gdx.graphics.getDeltaTime();

        var verticalWaveShader = core.getShaderLibrary().getShader("vertical_wave");
        verticalWaveShader.bind();
        verticalWaveShader.setUniformf("u_intensity", intensity);
        verticalWaveShader.setUniformf("u_frequency", frequency);
        verticalWaveShader.setUniformf("u_time", time);

        batch.end();

        batch.begin();
        batch.setShader(verticalWaveShader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
        batch.end();

        batch.begin();
    }
}
