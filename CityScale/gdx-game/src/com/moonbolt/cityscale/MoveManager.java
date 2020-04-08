package com.moonbolt.cityscale;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MoveManager {
	
	//Primitives
	private boolean isCasting;
	private String text;
	private Sprite spr_master;
	private int castTime;
	private int pos;
	private boolean inBattle = false;
	
	//Objects
	private Player Character_Data;
	
	//Textures
	TextureAtlas atl_basicset_male;
	TextureAtlas atl_basicset_female;
	
	public MoveManager() {}
	
	private void CastTime() {
		castTime--;	
		if(castTime <= 0) { isCasting = false; }
	}
	
	public Sprite MovChar(String type, float posX, float posY, int posInterject) {
		
		if(posInterject > 0) { pos = posInterject; }
		
		//[Casting Animation]//
		if(isCasting) {
			CastTime();		
			return CastingAnimation(posX, posY);
		}
		
		//[Walking Animation]//
		
		
		//[Battle Animation]//
		
		
		//[Static Animation]//
		
		return spr_master;
	}
	
	private Sprite WalkingAnimation() {
		//[MASC.] /////////////////////////////////////////////////  
		if(Character_Data.Set_A.equals("basic_male")) {
		}
		
		//[FEM.] /////////////////////////////////////////////////
		if(Character_Data.Set_A.equals("basic_female")) {
		}
		
		return spr_master;
	}
	
	private Sprite MenuAnimation(float posX, float posY) {
		//Menu
		if(Character_Data.Set_A.equals("basic_male")) {
		spr_master = atl_basicset_male.createSprite("basic_male_front1"); spr_master.setPosition(posX - 7.5f, posY - 10.5f);  spr_master.setSize(25, 36);
		}
		
		if(Character_Data.Set_A.equals("basic_female")) {
		spr_master = atl_basicset_female.createSprite("basic_female_front1"); spr_master.setPosition(posX - 7.5f, posY - 7);  spr_master.setSize(25, 33);
		}
		
		
		return spr_master;
	}
	
	private Sprite CastingAnimation(float posX, float posY) {
		if(Character_Data.Set_A.equals("basic_male")) {
			if(text.equals("yes_Right")) {
				spr_master = atl_basicset_male.createSprite("basic_set_male_magician_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;					
			}
			if(text.equals("yes_Left")) {
				spr_master = atl_basicset_male.createSprite("basic_set_male_magician_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;
			}
		}
		
		if(Character_Data.Set_A.equals("basic_female")) {
			if(text.equals("yes_Right")) {
				spr_master = atl_basicset_female.createSprite("basic_set_female_magician_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;				
			}
			if(text.equals("yes_Left")) {
				spr_master = atl_basicset_female.createSprite("basic_set_female_magician_left2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;
			}
		}
		
		return spr_master;
	}
	
}
