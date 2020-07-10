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
	private TextureAtlas atlas_hairs;
	
	private TextureAtlas atlas_basicset_m;
	private TextureAtlas atlas_basicset_f;
	
	
	//etc

	
	public GameLoad() {
		tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_master = new Sprite(tex_teste);
		
		//InterfaceCreate
		atlas_InterfaceCreate = new TextureAtlas(Gdx.files.internal("data/assets/interfaceCreate.txt"));	
		
		//Character Assets
		atlas_hairs = new TextureAtlas(Gdx.files.internal("data/characters/players/hair/hairs.txt"));
				
		atlas_basicset_m = new TextureAtlas(Gdx.files.internal("data/characters/players/basicset_m/basicset_m.txt"));
		atlas_basicset_f = new TextureAtlas(Gdx.files.internal("data/characters/players/basicset_f/basicset_f.txt"));
		
	}
	
	
	public Sprite LoadCharactersMenu(String sex) {
		//Masc.
		if(sex.equals("M")) {
			spr_master = atlas_basicset_m.createSprite("front1");
			spr_master.setSize(25, 40);
			spr_master.setPosition(11, 20);
		}
		
		//Fem.
		if(sex.equals("F")) {
			spr_master = atlas_basicset_f.createSprite("front1");
			spr_master.setSize(6.9f, 21.1f);
			spr_master.setPosition(20.1f, 29.5f);	
		}
		
		return spr_master;
	}
	
	public Sprite LoadCharactersHairMenu(String hair) {
		if(hair.equals(hair)) {
			spr_master = atlas_hairs.createSprite(hair);
			spr_master.setSize(8, 12);
			spr_master.setPosition(19.5f, 47.5f);
		}		
		return spr_master;
	}
	
	
	
	
	public Sprite LoadAllHairsMenu(int num, String sex) {
		if(sex.equals("M")) {
			spr_master = atlas_hairs.createSprite("hair" + num);
			spr_master.setSize(8, 12);
			
			if(num == 1) { spr_master.setPosition(35.8f,38); }
			if(num == 2) { spr_master.setPosition(42f,38); }
			if(num == 3) { spr_master.setPosition(48.5f,38); }
			if(num == 4) { spr_master.setPosition(54.7f,38); }
			if(num == 5) { spr_master.setPosition(60.9f,38); }
			if(num == 6) { spr_master.setPosition(67f,38); }
			if(num == 7) { spr_master.setPosition(35.8f,25); }
			if(num == 8) { spr_master.setPosition(42f,25); }
			if(num == 9) { spr_master.setPosition(48.5f,25); }
			if(num == 10) { spr_master.setPosition(54.7f,25); }
			if(num == 11) { spr_master.setPosition(60.9f,25); }		
		}
		
		if(sex.equals("F")) {
			spr_master = atlas_hairs.createSprite("hair" + num + "_f");
			spr_master.setSize(8, 11);

			if(num == 1) { spr_master.setPosition(35.8f,38); }
			if(num == 2) { spr_master.setPosition(42f,38); }
			if(num == 3) { spr_master.setPosition(48.5f,38); }
			if(num == 4) { spr_master.setPosition(54.7f,38); }
			if(num == 5) { spr_master.setPosition(60.9f,38); }
			if(num == 6) { spr_master.setPosition(67f,38); }
			if(num == 7) { spr_master.setPosition(35.8f,25); }
			if(num == 8) { spr_master.setPosition(42f,25); }
			if(num == 9) { spr_master.setPosition(48.5f,25); }
			if(num == 10) { spr_master.setPosition(54.7f,25); }
			if(num == 11) { spr_master.setPosition(60.9f,25); }	
			
		}
		
		
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
	
	public Sprite LoadElements(String type) {
		
		if(type.equals("light1")) {
			spr_master = atlas_InterfaceCreate.createSprite("luzclara");
			spr_master.setSize(20,50);
			spr_master.setPosition(12,20);
		}
		
		if(type.equals("light2")) {
			spr_master = atlas_InterfaceCreate.createSprite("luzclara");
			spr_master.setSize(20,50);
			spr_master.setPosition(40,20);
		}
		
		if(type.equals("light3")) {
			spr_master = atlas_InterfaceCreate.createSprite("luzclara");
			spr_master.setSize(20,50);
			spr_master.setPosition(72,20);
		}
		
		return spr_master;
	}
}
