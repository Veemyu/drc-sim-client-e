package com.alinach120602.drcsimclient.stage;

import com.alinach120602.drcsimclient.Client;
import com.alinach120602.drcsimclient.control.Control;
import com.alinach120602.drcsimclient.control.ControlController;
import com.alinach120602.drcsimclient.control.ControlKeyboard;
import com.alinach120602.drcsimclient.control.ControlTouch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;


/**
 * Created by Rolando on 2/7/2017.
 */
public class StageSettings extends StageList {
	public StageSettings() {
		// Title
		setTitle("Settings");
		// Control settings//TODO
		if (Gdx.app.getType() != Application.ApplicationType.iOS) {
			//Do awesome stuff for iOS here -alina
			for (Control control : Client.controls) {
				if (control instanceof ControlKeyboard)
					addStageChangeItem("Keyboard Settings", StageConfigKeyboard.class);
				if (control instanceof ControlController)
					addStageChangeItem("Controller Settings", StageConfigController.class);
				if (control instanceof ControlTouch)
					addStageChangeItem("Touch Settings", StageConfigTouch.class);
			}
		}
		addStageChangeItem("General Settings", StageConfigGeneral.class);
		// Info
		addStageChangeItem("Info", StageInfo.class);
		// Back
		addStageChangeItem("Back", StageConnect.class);
	}

	@Override
	public void onBackButtonPressed() {
		Client.setStage(new StageConnect());
	}
}
