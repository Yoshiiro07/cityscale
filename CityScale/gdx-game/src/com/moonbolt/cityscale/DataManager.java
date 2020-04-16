package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

	public class DataManager {

	private TextureAtlas atlas_basic_male_set;
	private TextureAtlas atlas_basic_female_set;
	private Sprite spr_master;
	
	public DataManager() {
	
		atlas_basic_male_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_male/basic_set_male.txt"));
		atlas_basic_female_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_female/basic_female.txt"));
		
	}
	
	
	//Load Sets
	public Sprite CastingSpriteSet(float posX, float posY, String set, String side) {
		
		if(set.equals("basic_set_male")) {
			if(side.equals("yes_Right")) {
				spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;				
			}
			if(side.equals("yes_Left")) {
				spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;
			}
		}
		
		if(set.equals("basic_set_female")) {
			if(side.equals("yes_Right")) {
				spr_master = atlas_basic_female_set.createSprite("basic_set_female_magician_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;				
			}
			if(side.equals("yes_Left")) {
				spr_master = atlas_basic_female_set.createSprite("basic_set_female_magician_left2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;
			}
		}
		
		return spr_master;
	}
}
