package com.atlne.freeskill.graphics.ui.buttons;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.atlne.freeskill.graphics.ui.Resizable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class TextButton extends VisTextButton implements Resizable {

    protected transient Core core;

    public TextButton(Core core, String text, String fontName, FontSize fontSize) {
        super(text, new VisTextButtonStyle(null, null, null,
                core.getFontManager().getFont(fontName, fontSize)
            )
        );

        this.core = core;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void setClickAction(Runnable action) {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                action.run();
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        var layout = new GlyphLayout();
        layout.setText(getStyle().font, getText());
        setWidth(layout.width);
        setHeight(layout.height);
    }
}
