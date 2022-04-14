package com.alinach120602.drcsimclient.ios.audio;

/**
 * Created by rolando on 3/24/17.
 */
public class Audio implements com.alinach120602.drcsimclient.audio.Audio {
    @Override
    public AudioDevice newAudioDevice() {
        return new AudioDevice();
    }
}
