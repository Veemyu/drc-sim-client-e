package com.alinach120602.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.alinach120602.drcsimclient.Client;
import com.alinach120602.drcsimclient.graphics.TextUtil;
import com.alinach120602.drcsimclient.util.logging.Logger;

import java.util.ArrayList;

/**
 * Created by Rolando on 2/7/2017.
 */
public class StageList extends Stage {
	private final List<String> list;
	private final Label title;
	private final SelectBox<String> dropdown;
	private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();

	public StageList(boolean enableDropdown) {
		// List
		Skin listSkin = new Skin();
		listSkin.add("selection", new Texture("image/textfield-selection.png"));
		List.ListStyle listStyle = new List.ListStyle();
		listStyle.font = TextUtil.generateScaledFont(1f);
		listStyle.fontColorUnselected = new Color(1, 1, 1, 1);
		listStyle.fontColorSelected = new Color(.5f, .5f, .5f, 1);
		listStyle.selection = listSkin.getDrawable("selection");
		list = new List<String>(listStyle);
		list.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		list.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (list.getSelectedIndex() > -1)
					listeners.get(list.getSelectedIndex()).changed(new ChangeListener.ChangeEvent(), list);
			}
		});
		// Scroll Pane
		ScrollPane scrollPane = new ScrollPane(list);
		scrollPane.setBounds(0, enableDropdown ? Gdx.graphics.getHeight() * .1f : 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight() * (enableDropdown ? .8f : .9f));
		addActor(scrollPane);
		// Title
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = TextUtil.generateScaledFont(1f);
		labelStyle.fontColor = listStyle.fontColorSelected;
		labelStyle.background = listStyle.selection;
		title = new Label("", labelStyle);
		title.setBounds(0, Gdx.graphics.getHeight() * .9f, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight() * .1f);
		title.setAlignment(Align.center);
		addActor(title);
		// Controller Dropdown
		Skin selectBoxSkin = new Skin();
		selectBoxSkin.add("background", new Texture("image/textfield-selection.png"));
		SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
		selectBoxStyle.font = TextUtil.generateScaledFont(1f);
		selectBoxStyle.fontColor = new Color(0, 0, 0, 1);
		selectBoxStyle.disabledFontColor = selectBoxStyle.fontColor;
		selectBoxStyle.background = selectBoxSkin.getDrawable("background");
		selectBoxStyle.backgroundDisabled = selectBoxStyle.background;
		selectBoxStyle.backgroundOpen = selectBoxStyle.background;
		selectBoxStyle.backgroundOver = selectBoxStyle.background;
		selectBoxStyle.listStyle = new List.ListStyle();
		selectBoxStyle.listStyle.font = selectBoxStyle.font;
		selectBoxStyle.listStyle.fontColorSelected = selectBoxStyle.fontColor;
		selectBoxStyle.listStyle.fontColorUnselected = selectBoxStyle.fontColor;
		selectBoxStyle.listStyle.background = selectBoxStyle.background;
		selectBoxStyle.listStyle.selection = selectBoxStyle.background;
		selectBoxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
		selectBoxStyle.scrollStyle.background = selectBoxStyle.background;
		dropdown = new SelectBox<String>(selectBoxStyle);
		dropdown.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .1f);
		for (Controller controller : Controllers.getControllers()) {
			dropdown.getItems().add(controller.getName());
			dropdown.setItems(dropdown.getItems().toArray());
		}
		if (enableDropdown)
			addActor(dropdown);
	}

	public StageList() {
		this(false);
	}

	void addItem(String name, ChangeListener changeListener) {
		list.getItems().add(name);
		listeners.add(changeListener);
	}

	void addStageChangeItem(String itemName, final Class stage) {
		addItem(itemName, new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				try {
					Client.setStage((Stage) stage.newInstance());
				} catch (InstantiationException e) {
					Logger.exception(e);
				} catch (IllegalAccessException e) {
					Logger.exception(e);
				}
			}
		});
	}

	void setTitle(String title) {
		this.title.setText(title);
	}

	List<String> getList() {
		return list;
	}

	SelectBox<String> getDropdown() {
		return dropdown;
	}
}
