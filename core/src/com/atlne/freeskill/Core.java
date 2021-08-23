package com.atlne.freeskill;

import com.atlne.freeskill.audio.AudioPlayer;
import com.atlne.freeskill.utils.json.JsonHelper;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

public final class Core extends ApplicationAdapter {

	private transient List<Disposable> disposables = Collections.emptyList();

	@Getter private transient JsonHelper jsonHelper;
	@Getter private transient AudioPlayer audioPlayer;

	@Override
	public void create() {
		Gdx.app.log("Startup", "Initialising core objects...");
		jsonHelper = new JsonHelper();
		audioPlayer = new AudioPlayer(this);
		Gdx.app.log("Startup", "Core objects initialised!");
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
	}
	
	@Override
	public void dispose() {
		Gdx.app.log("Cleanup", "Running asset disposal methods...");
		disposables.forEach(Disposable::dispose);
		Gdx.app.log("Cleanup", "Asset disposal methods complete!");
	}

	public void registerDisposable(Disposable disposable) {
		disposables.add(disposable);
	}
}
