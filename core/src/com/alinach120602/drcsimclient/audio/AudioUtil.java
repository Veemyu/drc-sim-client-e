package com.alinach120602.drcsimclient.audio;

import com.alinach120602.drcsimclient.Client;

/**
 * Created by Rolando on 2/10/2017.
 */
public class AudioUtil {
	private final AudioDevice audioDevice;

	public AudioUtil() {
		audioDevice = Client.audio.newAudioDevice();
		audioDevice.setVolume(.2f);
	}

	public void addData(byte[] data) {
		audioDevice.write(data);
	}

	public void dispose() {
		audioDevice.dispose();
	}
}
