package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameControl {

	
	private GameLoad gameLoader;	
	private DataManager dataManager;
	private TouchManager touchManager;
	
	private Sprite spr_master;
	private Texture tex_teste;
	
	public GameControl() {
		this.gameLoader = new GameLoad();
		this.dataManager = new DataManager();
		this.touchManager = new TouchManager();
		
		this.tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		this.spr_master = new Sprite(tex_teste);
	}
	
	
	public Sprite LoadInterfaceCreate(String type) {		
		spr_master = gameLoader.LoadInterface(type);
		return spr_master;
	}
	
	public String TouchVerify(float x, float y, String ScreenPress) {
		
		
		
		return "";
	}
}
