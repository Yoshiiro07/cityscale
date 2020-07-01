package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameLoad {

	
	private Sprite spr_master;
	private Texture tex_teste;
	
	//Interface Create 
	private TextureAtlas atlas_InterfaceCreate;
	
	//etc

	
	public GameLoad() {
		tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_master = new Sprite(tex_teste);
		
		//InterfaceCreate
		atlas_InterfaceCreate = new TextureAtlas(Gdx.files.internal("data/assets/interfaceCreate.txt"));		
	}
	
	
	public Sprite LoadInterface(String type) {
		
		if(type.equals("barAccess")) {
			spr_master = atlas_InterfaceCreate.createSprite("barAccess");
			spr_master.setSize(24, 20);
			spr_master.setPosition(70, 10);
		}
		
		return spr_master;
	}
}
