package com.alinach120602.drcsimclient;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.alinach120602.drcsimclient.audio.Audio;
import com.alinach120602.drcsimclient.control.Control;
import com.alinach120602.drcsimclient.data.ArgumentParser;
import com.alinach120602.drcsimclient.data.Constants;
import com.alinach120602.drcsimclient.graphics.TextUtil;
import com.alinach120602.drcsimclient.net.Sockets;
import com.alinach120602.drcsimclient.stage.Stage;
import com.alinach120602.drcsimclient.stage.StageLoad;
import com.alinach120602.drcsimclient.util.logging.Logger;

public class Client extends ApplicationAdapter {
	public static ArgumentParser args;
	public static Audio audio;
	private static Stage stage;
	public static Sockets sockets;
	public static Control[] controls;

	public Client(Control[] controls, Audio audio, ArgumentParser argumentParser) {
		Client.controls = controls;
		Client.audio = audio;
		Client.args = argumentParser;
		Logger.info("Starting %s version %s", Constants.NAME, Constants.VERSION);
	}

	public Client(Control[] controls, Audio audio) {
		this(controls, audio, new ArgumentParser());
	}

	public static Stage getStage() {
		return stage;
	}

    @Override
	public void create () {
		sockets = new Sockets();
		stage = new Stage();
		setStage(new StageLoad(true));
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		stage.dispose();
		sockets.dispose();
	}

	@Override
	public void pause() {
		super.pause();
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			setStage(new StageLoad(false));
			TextUtil.dispose();
		}
	}

	@Override
	public void resume() {
		super.resume();
		if (Gdx.app.getType() == Application.ApplicationType.Android)
			create();
	}

	public static void setStage(Stage stage) {
		Logger.debug("Setting stage to %s", stage.getClass().getSimpleName());
		try {
			Client.stage.dispose();
		}
		catch (IllegalArgumentException e) {
			Logger.exception(e);
		}
		Client.stage = stage;
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
	}

	/**
	 * Attempts to connect to a server at a given IP.
	 * @param ip ip or hostname
	 * @return empty string or error message
	 */
	public static String connect(String ip) {
		sockets.dispose();
		sockets.setIp(ip);
		try {
			sockets.connect();
		} catch (Exception e) {
			Logger.info("Failed to connect to host \"%1$s\"", ip);
			Logger.info(e.getMessage());
			Logger.exception(e);
			return e.getMessage();
		}
		return "";
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Client.stage.resize(width, height);
	}
}
