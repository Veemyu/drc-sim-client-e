package com.alinach120602.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.alinach120602.drcsimclient.Client;
import com.alinach120602.drcsimclient.graphics.TextUtil;
import com.alinach120602.drcsimclient.util.PreferencesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Rolando on 2/14/2017.
 */
public class StageLoad extends Stage {
	private final Image icon;
	private final Label loading;
	private final boolean doLoad;
	private int skipTicks = 1;
	private ArrayList<Float> fontSizes = new ArrayList<Float>(Arrays.asList(1f, 1.5f, 2f));
	private int percent = 0;
	private boolean doGenerate;
	private boolean loadControllers = false;

	/**
	 * Loading stage
	 * @param doLoad should resources be loaded? It acts as a splash screen otherwise.
	 */
	public StageLoad(boolean doLoad) {
		this.doLoad = doLoad;
		// Create icon
		icon = new Image(new Texture("image/icon-512.png"));
		int size = Gdx.graphics.getHeight();
		if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth())
			size = Gdx.graphics.getWidth();
		icon.setSize(size, size);
		icon.setPosition(Gdx.graphics.getWidth() / 2 - size / 2,
				Gdx.graphics.getHeight() / 2 - icon.getHeight() / 2);
		addActor(icon);
		// Loading Text
		Label.LabelStyle loadingStyle = new Label.LabelStyle();
		loadingStyle.font = new BitmapFont(Gdx.files.internal("font/collvetica.fnt"));
		loadingStyle.font.getData().setScale(Gdx.graphics.getHeight() * 2f / 720f);
		loading = new Label("DRC Sim", loadingStyle);
		loading.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .2f);
		loading.setAlignment(Align.center);
		addActor(loading);
	}

	@SuppressWarnings("unused")
	public StageLoad() {
		this(true);
	}

	@Override
	public void act(float delta) {
		if (!doLoad)
			return;
		if (this.skipTicks > 0) {
			this.skipTicks--;
			return;
		}
		if (doGenerate) {
			TextUtil.generateScaledFont(fontSizes.get(0));
			fontSizes.remove(0);
			doGenerate = false;
			skipTicks++;
			percent += 25;
			return;
		}
		else if (fontSizes.size() > 0) {
			loading.setText(String.format(Locale.US,"Generating Fonts %d%%", percent));
			doGenerate = true;
			skipTicks++;
			return;
		}
		else if (!loadControllers) {
			loading.setText(String.format(Locale.US,"Loading Controllers %d%%", percent));
			skipTicks++;
			loadControllers = true;
			return;
		}
		// Load Controllers - A Windows issue causes this to hangs occasionally.
		Controllers.getControllers();
		//loading.setText(String.format(Locale.US,"Launching %d%%", 100));

		Preferences preferences = PreferencesUtil.get("general");
		if (preferences.getBoolean("firstRun", true)) {
			preferences.putBoolean("firstRun", false);
			preferences.flush();
			Client.setStage(new StageInfo());
		}
		else
			Client.setStage(new StageConnect());
	}

	@Override
	public void onBackButtonPressed() {
		Gdx.app.exit();
	}
}
