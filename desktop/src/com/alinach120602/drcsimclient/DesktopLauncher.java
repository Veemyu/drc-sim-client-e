package com.alinach120602.drcsimclient;

//import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
//import com.alinach120602.drcsimclient.Client;

import com.alinach120602.drcsimclient.control.Control;
import com.alinach120602.drcsimclient.control.ControlController;
import com.alinach120602.drcsimclient.control.ControlKeyboard;
import com.alinach120602.drcsimclient.control.ControlTouch;
import com.alinach120602.drcsimclient.data.ArgumentParser;
//import com.alinach120602.drcsimclient.data.Constants;
import com.alinach120602.drcsimclient.desktop.audio.Audio;
/*
TODO LWJGL O
Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
https://jvm-gaming.org/t/starting-jvm-on-mac-with-xstartonfirstthread-programmatically/57547
*/
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.title = Constants.NAME;
        config.setForegroundFPS(60);
		config.setWindowedMode(854, 480);
		//if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
		//	config.addIcon("image/icon-512.png", Files.FileType.Internal);
		//	config.addIcon("image/icon-256.png", Files.FileType.Internal);
		//}
		//config.addIcon("image/icon-32.png", Files.FileType.Internal);
		//config.addIcon("image/icon-16.png", Files.FileType.Internal);
		ArgumentParser argParser = new ArgumentParser(arg);
		Control[] controls = new Control[argParser.touchControl ? 3 : 2];
		controls[0] = new ControlKeyboard();
		controls[1] = new ControlController();
		if (argParser.touchControl)
			controls[2] = new ControlTouch();
		new Lwjgl3Application(new Client(controls, new Audio(), argParser), config);
	}

    
}
