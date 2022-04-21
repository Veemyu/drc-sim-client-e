package com.alinach120602.drcsimclient;

import com.alinach120602.drcsimclient.Client;
//import com.alinach120602.drcsimclient.control.Control;
//import com.alinach120602.drcsimclient.control.ControlTouch;
import com.alinach120602.drcsimclient.ios.audio.Audio;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        //Control[] controls = new Control[] {new ControlTouch()};
        return new IOSApplication(new Client(null, new Audio()), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
