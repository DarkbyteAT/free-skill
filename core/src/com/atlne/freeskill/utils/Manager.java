package com.atlne.freeskill.utils;

import com.atlne.freeskill.Core;
import com.badlogic.gdx.utils.Disposable;

public abstract class Manager implements Creatable, Disposable {

    protected transient Core core;

    public Manager(Core core) {
        this.core = core;
        core.registerCreatable(this);
        core.registerDisposable(this);
    }
}