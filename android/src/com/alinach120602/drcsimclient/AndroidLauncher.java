package com.alinach120602.drcsimclient;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.alinach120602.drcsimclient.Client;

import com.alinach120602.drcsimclient.android.audio.Audio;
import com.alinach120602.drcsimclient.control.Control;
import com.alinach120602.drcsimclient.control.ControlController;
import com.alinach120602.drcsimclient.control.ControlTouch;
import com.alinach120602.drcsimclient.data.ArgumentParser;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        config.useWakelock = true;
        Control[] controls = new Control[] {new ControlTouch(), new ControlController()};
        ArgumentParser args = new ArgumentParser(new String[]{"-f"});
        initialize(new Client(controls, new Audio(), args), config);
	}
}
