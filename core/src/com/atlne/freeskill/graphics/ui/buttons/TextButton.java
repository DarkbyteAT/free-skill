package com.atlne.freeskill.graphics.ui.buttons;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.atlne.freeskill.graphics.ui.labels.Label;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Setter;

public class TextButton extends Label {

    private transient float time;

    @Setter
    private transient Runnable clickAction = () -> {};

    public TextButton(Core core, String text, String fontName, FontSize fontSize) {
        super(core, text, fontName, fontSize);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void update(float delta) {
        if(isHovering() && core.getInputManager().justClicked(0)) {
            clickAction.run();
        }
        super.update(delta);
    }
}
