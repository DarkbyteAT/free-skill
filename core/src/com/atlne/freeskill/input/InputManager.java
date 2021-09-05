package com.atlne.freeskill.input;

import com.atlne.freeskill.Core;
import com.atlne.freeskill.graphics.scenes.Scene;
import com.atlne.freeskill.utils.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.google.common.reflect.TypeToken;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InputManager extends Manager {

    public static final String INPUT_BINDS_PATH = "config/binds.json";
    public static final long DOUBLE_DELAY = 100;
    public static final String TAG = "InputManager";

    private Map<String, Integer> binds = new LinkedHashMap<>();
    private transient Map<String, Integer> stringKeys = new HashMap<>();
    private transient Map<Integer, String> keyStrings = new HashMap<>();
    private transient int[] clickDuration = new int[16];
    private transient int[] keyPressDuration = new int[256];
    private transient long[] lastClickTime = new long[16];
    private transient long[] lastPressTime = new long[256];
    private transient boolean[] doubleClicked = new boolean[16];
    private transient boolean[] doublePressed = new boolean[256];

    @Setter private transient Scene inputScene;

    public InputManager(Core core) {
        super(core);
    }

    @Override
    public void create() {
        Gdx.app.log(TAG, "Loading input binds JSON...");
        FileHandle inputBindsFile = Gdx.files.local(INPUT_BINDS_PATH);

        if(inputBindsFile.exists()) {
            Gdx.app.log(TAG, "Input binds JSON found!");
            binds = core.getJsonHelper().deserialise(inputBindsFile.readString(),
                    new TypeToken<LinkedHashMap<String, Integer>>(){}.getType());
        } else {
            Gdx.app.log(TAG, "Input binds JSON not found!");
            inputBindsFile.writeString(core.getJsonHelper().serialise(binds,
                    new TypeToken<LinkedHashMap<String, Integer>>(){}.getType()), false);
        }

        for(int key = 0; key < 256; key++) {
            String value = Input.Keys.toString(key);
            if(value != null && !value.equals("?")) {
                stringKeys.put(value, key);
                keyStrings.put(key, value);
            }
        }
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "Saving input binds JSON...");
        Gdx.files.local(INPUT_BINDS_PATH).writeString(core.getJsonHelper().serialise(binds,
                new TypeToken<LinkedHashMap<String, Integer>>(){}.getType()), false);
    }

    public void update() {
        /**Tests mouse clicks for mouse buttons.*/
        for(int button = 0; button < 16; button++) {
            if(Gdx.input.isButtonPressed(button)) {
                clickDuration[button]++;

                /**Checks if button just clicked.*/
                if(clickDuration[button] == 1) {
                    /**Gets current time in milliseconds.*/
                    long now = System.currentTimeMillis();
                    /**Checks if difference between last click times is less than the delay in milliseconds.
                     * If so, registers a double click.*/
                    doubleClicked[button] = now - lastClickTime[button] <= DOUBLE_DELAY;
                    lastClickTime[button] = now;
                } else {
                    doubleClicked[button] = false;
                }
            } else {
                clickDuration[button] = 0;
            }
        }

        /**Tests key presses for all keys to check for.*/
        for(int key = 0; key < keyPressDuration.length; key++) {
            if(Gdx.input.isKeyPressed(key)) {
                keyPressDuration[key]++;

                /**Checks if key just pressed.*/
                if(keyPressDuration[key] == 1) {
                    /**Gets current time in milliseconds.*/
                    long now = System.currentTimeMillis();
                    /**Checks if difference between last press times is less than the delay in milliseconds.
                     * If so, registers a double press.*/
                    doublePressed[key] = now - lastPressTime[key] <= DOUBLE_DELAY;
                    lastPressTime[key] = now;
                } else {
                    doublePressed[key] = false;
                }
            } else {
                keyPressDuration[key] = 0;
            }
        }
    }

    /**Returns true if the bound scene is the input processor, false otherwise.*/
    public boolean shouldPoll() {
        return inputScene == null || inputScene == Gdx.input.getInputProcessor();
    }

    /**Returns true if a given bind is a key-bind, false if it is a mouse bind, or throws an exception if it doesn't exist.
     *
     * @throws IllegalArgumentException Bind not found.*/
    public boolean isBindKey(String bind) {
        if(binds.containsKey(bind)) {
            return shouldPoll() && binds.get(bind) >= 0;
        } else throw new IllegalArgumentException("Bind \"" + bind + "\" was not found!");
    }

    /**Returns true if a given bind is pressed, or throws an exception if the bind doesn't exist.
     *
     * @throws IllegalArgumentException Bind not found.*/
    public boolean bindPressed(String bind) {
        if(binds.containsKey(bind)) {
            int bindCode = binds.get(bind);
            return shouldPoll() && (bindCode < 0 ? clicked(-(bindCode + 1)) : keyPressed(bindCode));
        } else throw new IllegalArgumentException("Bind \"" + bind + "\" was not found!");
    }

    /**Returns true if a given bind was just pressed, or throws an exception if the bind doesn't exist.
     *
     * @throws IllegalArgumentException Bind not found.*/
    public boolean bindJustPressed(String bind) {
        if(binds.containsKey(bind)) {
            int bindCode = binds.get(bind);
            return shouldPoll() && (bindCode < 0 ? justClicked(-(bindCode + 1)) : keyJustPressed(bindCode));
        } else throw new IllegalArgumentException("Bind \"" + bind + "\" was not found!");
    }

    /**Returns true if a given bind was double pressed, or throws an exception if the bind doesn't exist.
     *
     * @throws IllegalArgumentException Bind not found.*/
    public boolean bindDoublePressed(String bind) {
        if(binds.containsKey(bind)) {
            int bindCode = binds.get(bind);
            return shouldPoll() && (bindCode < 0 ? doubleClicked(-(bindCode + 1)) : keyDoublePressed(bindCode));
        } else throw new IllegalArgumentException("Bind \"" + bind + "\" was not found!");
    }

    public boolean clicked(int button) {
        return shouldPoll() && clickDuration[button] > 0;
    }

    public boolean justClicked(int button) {
        return shouldPoll() && clickDuration[button] == 1;
    }

    public boolean doubleClicked(int button) {
        return shouldPoll() && doubleClicked[button];
    }

    public boolean keyPressed(int key) {
        return shouldPoll() && keyPressDuration[key] > 0;
    }

    public boolean keyJustPressed(int key) {
        return shouldPoll() && keyPressDuration[key] == 1;
    }

    public boolean keyDoublePressed(int key) {
        return shouldPoll() && doublePressed[key];
    }

    public int getKeyCode(String key) {
        return stringKeys.get(key);
    }

    public String getKeyName(int keyCode) {
        return keyStrings.get(keyCode);
    }

    public int getMouseX() {
        return Gdx.input.getX();
    }

    public int getMouseY() {
        return Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    public Vector2 getMouseCoords() {
        return new Vector2(getMouseX(), getMouseY());
    }

    /**Gets a list of all key-codes of the keys pressed just this frame.*/
    public ArrayList<Integer> getKeysJustPressed() {
        ArrayList<Integer> keysPressed = new ArrayList<Integer>();
        for(String key : stringKeys.keySet())
            if(keyJustPressed(stringKeys.get(key)))
                keysPressed.add(stringKeys.get(key));
        return keysPressed;
    }

    public Map<String, Integer> getBinds() {
        return binds;
    }

    public int[] getClickDuration() {
        return clickDuration;
    }

    public int[] getKeyPressDuration() {
        return keyPressDuration;
    }
}
