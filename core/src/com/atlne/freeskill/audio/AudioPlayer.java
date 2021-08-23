package com.atlne.freeskill.audio;

import com.atlne.freeskill.Core;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

import java.util.HashMap;

public class AudioPlayer implements Disposable {

    public static final String TAG = "AudioPlayer";
    public static final String[] SUPPORTED_EXTENSIONS = {"mp3", "wav", "ogg"};
    public static final String MUSIC_FOLDER_PATH = "/assets/music";
    public static final String SOUND_EFFECTS_FOLDER_PATH = "/assets/sfx";
    public static final String VOLUME_FILE_LOCATION = "/config/volume.json";

    private final transient HashMap<String, Music> music = new HashMap<>();
    private final transient HashMap<String, Sound> soundEffects = new HashMap<>();

    @Getter private transient VolumeInformation volumeInformation;
    @Getter private transient float fadeMultiplier = 1;
    @Getter private transient String currentTrack = "";

    private transient Core core;

    public AudioPlayer(Core core) {
        this.core = core;
        core.registerDisposable(this);

        Gdx.app.log(TAG, "Loading volume information JSON...");
        FileHandle localVolumeFile = Gdx.files.local(VOLUME_FILE_LOCATION);

        if(localVolumeFile.exists()) {
            Gdx.app.log(TAG, "Volume information JSON found!");
            volumeInformation = core.getJsonHelper().deserialise(localVolumeFile.readString(), VolumeInformation.class);
        } else {
            Gdx.app.log(TAG, "Volume information JSON not found!");
            volumeInformation = new VolumeInformation();
            localVolumeFile.writeString(core.getJsonHelper().serialise(volumeInformation, VolumeInformation.class), false);
        }
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "Disposing all loaded music files...");
        music.values().forEach(Music::dispose);
        Gdx.app.log(TAG, "Disposing all loaded sound effect files...");
        soundEffects.values().forEach(Sound::dispose);
        music.clear();
        soundEffects.clear();
    }

    public void playMusic(String musicName) {
        loadMusicIfNotLoaded(musicName);
        handleTrackChange(musicName);
        music.get(currentTrack).setVolume(fadeMultiplier * volumeInformation.getMasterVolume() * volumeInformation.getMusicVolume());
        music.get(currentTrack).play();
    }

    public void playSoundEffect(String soundEffectName) {
        loadSoundEffectIfNotLoaded(soundEffectName);
        soundEffects.get(soundEffectName).play(volumeInformation.getMasterVolume() * volumeInformation.getSoundEffectVolume());
    }

    private void loadMusicIfNotLoaded(String musicName) {
        if(!music.containsKey(musicName)) {
            for(String extension : SUPPORTED_EXTENSIONS) {
                FileHandle file = Gdx.files.local(String.format("%s/%s.%s", MUSIC_FOLDER_PATH, musicName, extension));

                if(file.exists()) {
                    music.put(musicName, Gdx.audio.newMusic(file)).setLooping(true);
                } else {
                    throw new RuntimeException(String.format("Music '%s' does not exist!", musicName));
                }
            }
        }
    }

    private void loadSoundEffectIfNotLoaded(String soundEffectName) {
        if(!soundEffects.containsKey(soundEffectName)) {
            for(String extension : SUPPORTED_EXTENSIONS) {
                FileHandle file = Gdx.files.local(String.format("%s/%s.%s", SOUND_EFFECTS_FOLDER_PATH, soundEffectName, extension));

                if(file.exists()) {
                    soundEffects.put(soundEffectName, Gdx.audio.newSound(file));
                } else {
                    throw new RuntimeException(String.format("Sound effect '%s' does not exist!", soundEffectName));
                }
            }
        }
    }

    private void handleTrackChange(String musicName) {
        if(!currentTrack.equals(musicName)) {
            fadeMultiplier = Math.max(0f, fadeMultiplier - Gdx.graphics.getDeltaTime());

            if(fadeMultiplier == 0) {
                music.get(currentTrack).stop();
                currentTrack = musicName;
            }
        } else {
            fadeMultiplier = Math.min(1f, fadeMultiplier + Gdx.graphics.getDeltaTime());
        }
    }
}
