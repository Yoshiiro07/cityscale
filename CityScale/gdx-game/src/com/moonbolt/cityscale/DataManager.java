package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

	public class DataManager {

	private TextureAtlas atlas_basic_male_set;
	private TextureAtlas atlas_basic_female_set;
	private TextureAtlas atlas_hairs;
	private Sprite spr_master;
	private Texture tex_master;
	private int pos = 1;
	private int posOnline = 1;
	
	public DataManager() {
	
		//Placeholder
		tex_master = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_master = new Sprite(tex_master);
		spr_master.setSize(1,1);	
		
		atlas_basic_male_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_male/basic_set_male.txt"));
		atlas_basic_female_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_female/basic_female.txt"));
	    atlas_hairs = new TextureAtlas(Gdx.files.internal("data/characters/hair/hairs.txt"));
		
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
	
	// Movimento padrão
	public Sprite MovBase(String set, boolean inBattle, String walk, String side, String type, String text, int position, float posX, float posY, boolean attackFrame, Player Character_Data){
		
		pos = position;
		
		//MASC. /////////////////////////////////////////////////  
		if(set.equals("basic_set_male")) {			
			//SET
			//BATTLE
			if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
				text = Character_Data.Battle_A;

				if(Character_Data.Job_A.equals("Novice")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
				if(Character_Data.Job_A.equals("Swordman")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
			}

			//Menu
			if(type.equals("Menu")) { 
				spr_master = atlas_basic_male_set.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 7.5f, posY - 10.5f);  spr_master.setSize(25, 36);
				return spr_master;
			}

			//Front
			if(side.equals("Front") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Front") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front2"); spr_master.setPosition(posX - 0.1f, posY + 12.3f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Front") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front3"); spr_master.setPosition(posX - 0.1f, posY + 12.4f); spr_master.setSize(25, 36); return spr_master; }

			//back
			if(side.equals("Back") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Back") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Back") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }

			//right
			if(side.equals("Right") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Right") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Right") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master;}

			//left
			if(side.equals("Left") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);   spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Left") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Left") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36);  return spr_master; }
			
			}
			
		//FEM. /////////////////////////////////////////////////
		if(set.equals("basic_set_female")) {
			//Battle
			if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
				text = Character_Data.Battle_A;
				if(Character_Data.Job_A.equals("Novice")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_meleeAttack_right"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
				}

				if(Character_Data.Job_A.equals("Swordman")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_meleeAttack_right"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
				}
			}

			//Menu
			if(type.equals("Menu")) { 
				spr_master = atlas_basic_female_set.createSprite("basic_set_female_front1"); spr_master.setPosition(posX - 7.5f, posY - 7);  spr_master.setSize(25, 33);
				return spr_master;
			}

			//Front
			if(side.equals("Front") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front1"); spr_master.setPosition(posX - 5.5f, posY + 13.5f);  spr_master.setSize(25, 33); return spr_master; }
			if(side.equals("Front") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front2"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }
			if(side.equals("Front") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front3"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }

			//back
			if(side.equals("Back") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back1"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }
			if(side.equals("Back") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back2"); spr_master.setPosition(posX - 4.5f, posY + 15f); spr_master.setSize(23, 33); return spr_master; }
			if(side.equals("Back") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back3"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }

			//right
			if(side.equals("Right") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right1"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Right") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right2"); spr_master.setPosition(posX - 3.8f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Right") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right3"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master;}

			//left
			if(side.equals("Left") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left1"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Left") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left2"); spr_master.setPosition(posX - 4.5f, posY + 12.5f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Left") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left3"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }

		}
		
			return spr_master;
		}
		
	public Sprite MovBaseOnline(String set, String side, String walk, String battle, int position, float posX, float posY){
		
		try {
			
		posOnline = position;
		
		if(battle.equals("Battle")) {
			posOnline++;
			
			if(posOnline > 3) { posOnline = 1; }
		}
		
		//MASC. /////////////////////////////////////////////////  
		if(set.equals("basic_set_male")) {			
			//SET
			
			if(posOnline == 1 && battle.equals("Battle")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(posOnline == 2 && battle.equals("Battle")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			if(posOnline == 3 && battle.equals("Battle")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
			
			//Front
			if(side.equals("Front") && posOnline == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Front") && posOnline == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front2"); spr_master.setPosition(posX - 0.1f, posY + 12.3f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Front") && posOnline == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front3"); spr_master.setPosition(posX - 0.1f, posY + 12.4f); spr_master.setSize(25, 36); return spr_master; }

			//back
			if(side.equals("Back") && posOnline == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Back") && posOnline == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Back") && posOnline == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }

			//right
			if(side.equals("Right") && posOnline == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Right") && posOnline == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Right") && posOnline == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master;}

			//left
			if(side.equals("Left") && posOnline == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);   spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Left") && posOnline == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Left") && posOnline == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36);  return spr_master; }

		}

		//FEM. /////////////////////////////////////////////////
		if(set.equals("basic_set_female")) {
			
			if(posOnline == 1 && battle.equals("Battle")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
			if(posOnline == 2 && battle.equals("Battle")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
			if(posOnline == 3 && battle.equals("Battle")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
			
			
			//Front
			if(side.equals("Front") && posOnline == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front1"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }
			if(side.equals("Front") && posOnline == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front2"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }
			if(side.equals("Front") && posOnline == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front3"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }

			//back
			if(side.equals("Back") && posOnline == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back1"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }
			if(side.equals("Back") && posOnline == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back2"); spr_master.setPosition(posX - 4.5f, posY + 15f); spr_master.setSize(23, 33); return spr_master; }
			if(side.equals("Back") && posOnline == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back3"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }

			//right
			if(side.equals("Right") && posOnline == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right1"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Right") && posOnline == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right2"); spr_master.setPosition(posX - 3.8f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Right") && posOnline == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right3"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master;}

			//left
			if(side.equals("Left") && posOnline == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left1"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Left") && posOnline == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left2"); spr_master.setPosition(posX - 4.5f, posY + 12.5f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Left") && posOnline == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left3"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }

		}
		
		return spr_master;
		
		}
		
		catch(Exception ex) {
			return null;
		}
	}
	
	public Sprite MovCreatingChar(String set,String walk, int pos) {
		
		if(walk.equals("Stop")) {
			pos = 1;
		}
		
		//Masc.
		if(set.equals("basic_set_male")) {
			if(pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front1"); return spr_master; }
			if(pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front2"); return spr_master; }
			if(pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front3"); return spr_master; }
		}
		//Fem.
		if(set.equals("basic_set_female")) {
			if(pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front1"); return spr_master; }
			if(pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front2"); return spr_master; }
			if(pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front3"); return spr_master; }
		}
		return spr_master;
	}
	
	public Sprite ReturnHairs(String hairName, String side, String walk, String text, boolean inBattle, boolean isCasting, Player Character_Data, float posX, float posY){
		if(!hairName.contains("_")) {
			for(int i = 1; i <= 11; i++) {

				if(side.equals("Menu")) {
					if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i); spr_master.setPosition(posX, posY + 20.7f); spr_master.setSize(10, 15); return spr_master; }
				}

				if((inBattle && walk.equals("Stop")) || isCasting) {
					text = Character_Data.Battle_A;
					if(text.equals("yes_Right") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_right"); spr_master.setPosition(posX  + 6.5f, posY + 44f); spr_master.setSize(10, 15); return spr_master; } }
					if(text.equals("yes_Right") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_right"); spr_master.setPosition(posX  + 6.5f, posY + 44.2f); spr_master.setSize(10, 15); return spr_master; } }
					if(text.equals("yes_Left") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_left"); spr_master.setPosition(posX + 6.3f, posY + 44f); spr_master.setSize(10, 15); return spr_master; } }
					if(text.equals("yes_Left") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_left"); spr_master.setPosition(posX + 6.3f, posY + 44.2f); spr_master.setSize(10, 15); return spr_master; } }
				}

				if(side.equals("Front") || side.equals("Front-Left") || side.equals("Front-Right")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i); spr_master.setPosition(posX + 7f, posY + 43.5f); spr_master.setSize(10, 15); return spr_master; } }
				if(side.equals("Right") || side.equals("Right-Front") || side.equals("Right-Back")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "right"); spr_master.setPosition(posX + 5.5f, posY + 45f); spr_master.setSize(12, 15); return spr_master; } }
				if(side.equals("Left") || side.equals("Left-Front") || side.equals("Left-Back")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "left"); spr_master.setPosition(posX + 7f, posY + 45f); spr_master.setSize(12, 15); return spr_master; } }
				if(side.equals("Back")|| side.equals("Left-Back") || side.equals("Left-Front") || side.equals("Back-Left") || side.equals("Back-Right")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "up"); spr_master.setPosition(posX + 7f, posY + 44f); spr_master.setSize(10, 14); return spr_master; } }	

			}
		}
		if(hairName.contains("_f")) {
			for(int i = 1; i <= 11; i++) {

				if(side.equals("Menu")) {
					if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "_f"); spr_master.setPosition(posX - 0.2f, posY + 19f); spr_master.setSize(10, 15); return spr_master; }
				}

				if((inBattle && walk.equals("Stop")) || isCasting) {
					text = Character_Data.Battle_A;
					if(text.equals("yes_Right") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_right"); spr_master.setPosition(posX  + 3.8f, posY + 38.3f); spr_master.setSize(10, 15); return spr_master; } }
					if(text.equals("yes_Right") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_right"); spr_master.setPosition(posX  + 3.8f, posY + 38.1f); spr_master.setSize(10, 15); return spr_master; } }					
					if(text.equals("yes_Left") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_left"); spr_master.setPosition(posX + 5.9f, posY + 38.3f); spr_master.setSize(10, 15); return spr_master; } }
					if(text.equals("yes_Left") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_left"); spr_master.setPosition(posX + 5.9f, posY + 38.3f); spr_master.setSize(10, 15); return spr_master; } }
				}

				if(side.equals("Front") || side.equals("Front-Left") || side.equals("Front-Right")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "_f"); spr_master.setPosition(posX + 1.7f, posY + 39.5f); spr_master.setSize(10, 15); return spr_master; } }
				if(side.equals("Right") || side.equals("Right-Front") || side.equals("Right-Back")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "right" + "_f"); spr_master.setPosition(posX + 1.7f, posY + 39.6f); spr_master.setSize(12, 14); return spr_master; } }
				if(side.equals("Left") || side.equals("Left-Front") || side.equals("Left-Back")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "left" + "_f"); spr_master.setPosition(posX + 0.9f, posY + 39.6f); spr_master.setSize(12, 14); return spr_master; } }
				if(side.equals("Back")|| side.equals("Left-Back") || side.equals("Left-Front") || side.equals("Back-Left") || side.equals("Back-Right")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "up" + "_f"); spr_master.setPosition(posX + 1.3f, posY + 40f); spr_master.setSize(10, 14); return spr_master; } }	
			}
	    }
		
		return spr_master;
		}
	
		public Sprite ReturnHairsOnline(String hairName, String side, String walk, String battle, float posX, float posY) {
			
			//Masculino
			if(!hairName.contains("_")) {
				for(int i = 1; i <= 11; i++) {		
					if(battle.equals("Battle")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_right"); spr_master.setPosition(posX  + 6.5f, posY + 44f); spr_master.setSize(10, 15); return spr_master; } }
					if(side.equals("Front") || side.equals("Front-Left") || side.equals("Front-Right")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i); spr_master.setPosition(posX + 7f, posY + 43.5f); spr_master.setSize(10, 15); return spr_master; } }
					if(side.equals("Right") || side.equals("Right-Front") || side.equals("Right-Back")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "right"); spr_master.setPosition(posX + 5.5f, posY + 45f); spr_master.setSize(12, 15); return spr_master; } }
					if(side.equals("Left") || side.equals("Left-Front") || side.equals("Left-Back")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "left"); spr_master.setPosition(posX + 7f, posY + 45f); spr_master.setSize(12, 15); return spr_master; } }
					if(side.equals("Back")|| side.equals("Left-Back") || side.equals("Left-Front") || side.equals("Back-Left") || side.equals("Back-Right")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "up"); spr_master.setPosition(posX + 7f, posY + 44f); spr_master.setSize(10, 14); return spr_master; } }	
				}
			}
			//Feminina
			if(hairName.contains("_f")) {		
			    for(int i = 1; i <= 11; i++) {
					if(battle.equals("Battle")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_right"); spr_master.setPosition(posX  + 3.8f, posY + 37.8f); spr_master.setSize(10, 15); return spr_master; } }	
					if(side.equals("Front") || side.equals("Front-Left") || side.equals("Front-Right") || battle.equals("Battle")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "_f"); spr_master.setPosition(posX + 1.7f, posY + 39.5f); spr_master.setSize(10, 15); return spr_master; } }
					if(side.equals("Right") || side.equals("Right-Front") || side.equals("Right-Back")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "right" + "_f"); spr_master.setPosition(posX + 1.7f, posY + 39.6f); spr_master.setSize(12, 14); return spr_master; } }
					if(side.equals("Left") || side.equals("Left-Front") || side.equals("Left-Back")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "left" + "_f"); spr_master.setPosition(posX + 0.9f, posY + 39.6f); spr_master.setSize(12, 14); return spr_master; } }
					if(side.equals("Back")|| side.equals("Left-Back") || side.equals("Left-Front") || side.equals("Back-Left") || side.equals("Back-Right")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "up" + "_f"); spr_master.setPosition(posX + 1.3f, posY + 40f); spr_master.setSize(10, 14); return spr_master; } }		
				}
			}
			return spr_master;
		}
		
		public Sprite GetHair(String hair){
			spr_master = atlas_hairs.createSprite(hair);
			return spr_master;
		}
}
