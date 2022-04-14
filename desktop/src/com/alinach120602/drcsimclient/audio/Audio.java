package com.alinach120602.drcsimclient.desktop.audio;

/**
 * Created by rolando on 2/11/17.
 */
public class Audio implements com.alinach120602.drcsimclient.audio.Audio {
    @Override
    public AudioDevice newAudioDevice() {
        return new AudioDevice();
    }
}
