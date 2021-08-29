package com.atlne.freeskill.utils;

import com.atlne.freeskill.Core;
import com.badlogic.gdx.utils.Disposable;

public abstract class AssetHandler implements Creatable, Disposable {

    protected transient Core core;

    public AssetHandler(Core core) {
        this.core = core;
        core.registerCreatable(this);
        core.registerDisposable(this);
    }
}