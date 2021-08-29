package com.atlne.freeskill.graphics;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.utils.AssetHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kotcrab.vis.ui.VisUI;

public class GraphicsManager extends AssetHandler {

    public static final String UI_SKIN_PATH = "assets/ui/x2/tinted.json";
    public static final Color SCREEN_CLEAR_COLOUR = Color.BLACK;
    public static final int PIXELS_PER_METER = 32;

    public GraphicsManager(Core core) {
        super(core);
    }

    @Override
    public void create() {
        VisUI.load(Gdx.files.local(UI_SKIN_PATH));
        VisUI.setDefaultTitleAlign(Align.center);
        VisUI.getSkin().getFont("default-font").getData().markupEnabled = true;
        VisUI.getSkin().getFont("small-font").getData().markupEnabled = true;
    }

    @Override
    public void dispose() {
        VisUI.dispose(true);
    }

    public void update() {
        ScreenUtils.clear(SCREEN_CLEAR_COLOUR);
    }
}
