package com.alinach120602.drcsimclient.control;

import com.alinach120602.drcsimclient.stage.StageControl;

/**
 * Created by Rolando on 2/6/2017.
 */
public interface Control {
	void init(StageControl stage);

	void update();

    void vibrate(int milliseconds);
}
