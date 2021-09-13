package com.atlne.freeskill.graphics.ui.labels;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.fonts.FontSize;
import com.atlne.freeskill.graphics.ui.SceneElement;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

public class Label extends SceneElement {

    @Getter
    protected String text;
    @Getter
    protected String fontName;
    @Getter
    protected FontSize fontSize;

    protected transient BitmapFont font;

    public Label(Core core, String text, String fontName, FontSize fontSize) {
        super(core);
        this.text = text;
        this.fontName = fontName;
        this.fontSize = fontSize;
        updateLabelData();
    }

    @Override
    public void draw(Batch batch) {
        var realPosition = getRealPosition();
        font.draw(batch, text, realPosition.x, realPosition.y);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public Rectangle getBounds() {
        var bounds = super.getBounds();
        bounds.y -= bounds.height;
        return bounds;
    }

    @Override
    public float getRealY() {
        return position.y + (size.y * alignment.getTextVerticalAdjustment()) + transform.y;
    }


    public void setText(String text) {
        this.text = text;
        updateLabelData();
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
        updateLabelData();
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
        updateLabelData();
    }

    private void updateLabelData() {
        font = core.getFontManager().getFont(fontName, fontSize);
        var layout = new GlyphLayout(font, text);
        setSize(new Vector2(layout.width, layout.height));
    }
}
