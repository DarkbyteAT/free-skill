package com.atlne.freeskill.graphics.ui.labels;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.kotcrab.vis.ui.widget.VisLabel;

public class Label extends VisLabel {

    protected transient Core core;

    public Label(Core core, String text, String fontName, FontSize fontSize) {
        super(text, new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(
                core.getFontManager().getFont(fontName, fontSize),
                null
            )
        );

        this.core = core;
        updateBounds();
    }

    @Override
    public void setText(CharSequence newText) {
        super.setText(newText);
        updateBounds();
    }

    private void updateBounds() {
        var layout = new GlyphLayout();
        layout.setText(getStyle().font, getText());
        setWidth(layout.width);
        setHeight(layout.height);
    }
}
