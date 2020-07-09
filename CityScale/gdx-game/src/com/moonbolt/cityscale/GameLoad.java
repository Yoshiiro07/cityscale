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
	
	//Characters Assets
	private TextureAtlas atlas_basicset_m;
	
	//etc

	
	public GameLoad() {
		tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_master = new Sprite(tex_teste);
		
		//InterfaceCreate
		atlas_InterfaceCreate = new TextureAtlas(Gdx.files.internal("data/assets/interfaceCreate.txt"));	
		
		//Character Assets
		atlas_basicset_m = new TextureAtlas(Gdx.files.internal("data/characters/players/basicset_m/basicset_m.txt"));
		
	}
	
	
	public Sprite LoadCharactersMenu(String sex) {
		//Masc.
		if(sex.equals("M")) {
			spr_master = atlas_basicset_m.createSprite("front1");
			spr_master.setSize(24, 20);
			spr_master.setPosition(50, 50);
		}
		
		//Fem.
		if(sex.equals("F")) {
			spr_master = atlas_basicset_m.createSprite("front1");
			spr_master.setSize(24, 20);
			spr_master.setPosition(70, 10);	
		}
		
		return spr_master;
	}
	
	public Sprite LoadCharactersHairMenu(String hair) {
		
		return spr_master;
	}
	
	
	
	public Sprite LoadCharactersModel(String set, String state, String sex) {
		
		//Masc.
		if(sex.equals("M")) {
			if(set.equals("basicset_m")) {
				if(state.equals("front1")) {
					spr_master = atlas_basicset_m.createSprite("front1");
					spr_master.setSize(24, 20);
					spr_master.setPosition(50, 50);
				}
			}
		}
		
		//Fem.
		if(sex.equals("F")) {
			if(set.equals("basicset_m")) {
				if(state.equals("front1")) {
					spr_master = atlas_basicset_m.createSprite("front1");
					spr_master.setSize(24, 20);
					spr_master.setPosition(70, 10);
				}
			}
		}
		
		return spr_master;
	}
	
	
	public Sprite LoadInterface(String type) {
		
		if(type.equals("barAccess")) {
			spr_master = atlas_InterfaceCreate.createSprite("barAccess");
			spr_master.setSize(24, 20);
			spr_master.setPosition(70, 10);
		}
		
		if(type.equals("titleTopSelect")) {
			spr_master = atlas_InterfaceCreate.createSprite("selecionepersonagem");
			spr_master.setSize(45, 7);
			spr_master.setPosition(2, 87); 
		}
		
		if(type.equals("titleTopCreate")) {
			spr_master = atlas_InterfaceCreate.createSprite("criandopersonagem");
			spr_master.setSize(45, 7);
			spr_master.setPosition(2, 87); 
		}
		
		if(type.equals("titleTopDelete")) {
			spr_master = atlas_InterfaceCreate.createSprite("deletandopersonagem");
			spr_master.setSize(45, 7);
			spr_master.setPosition(2, 87); 
		}
		
		if(type.equals("boardCreate")) {
			spr_master = atlas_InterfaceCreate.createSprite("barcreatechar");
			spr_master.setSize(80, 80);
			spr_master.setPosition(9.8f, 5); 
		}
		
		
		if(type.equals("btnCreate")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnCriarNovo");
			spr_master.setSize(10,14);
			spr_master.setPosition(85,2);
		}
		
		if(type.equals("btnDelete")) {
			spr_master = atlas_InterfaceCreate.createSprite("btnDeletar");
			spr_master.setSize(10,14);
			spr_master.setPosition(5,2);
		}
		
		
		
		return spr_master;
	}
}
