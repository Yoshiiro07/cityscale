package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

	public class DataManager {

	private TextureAtlas atlas_basic_male_set;
	private TextureAtlas atlas_basic_female_set;
	private TextureAtlas atlas_hairs;
	private TextureAtlas atlas_nKnifes;
	private TextureAtlas atlas_swords;	
	private TextureAtlas atlas_rods;
	private TextureAtlas atlas_pistols;
	private TextureAtlas atlas_axes;
	private TextureAtlas atlas_daggers;
	private Sprite spr_master;
	private Texture tex_master;
	private int pos = 1;
	private int posOnline = 1;
	private String text;
	private String weapon;
	
	public DataManager() {
	
		//Placeholder
		tex_master = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_master = new Sprite(tex_master);
		spr_master.setSize(1,1);	
		
		atlas_basic_male_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_male/basic_set_male.txt"));
		atlas_basic_female_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_female/basic_female.txt"));
	    atlas_hairs = new TextureAtlas(Gdx.files.internal("data/characters/hair/hairs.txt"));
	    
	    //Armas
		atlas_nKnifes = new TextureAtlas(Gdx.files.internal("data/itens/weapons/nknifes.txt"));
		atlas_swords = new TextureAtlas(Gdx.files.internal("data/itens/weapons/swords.txt"));
		atlas_rods = new TextureAtlas(Gdx.files.internal("data/itens/weapons/rods.txt"));
		atlas_pistols = new TextureAtlas(Gdx.files.internal("data/itens/weapons/pistols.txt"));
		atlas_axes = new TextureAtlas(Gdx.files.internal("data/itens/weapons/axes.txt"));
		atlas_daggers = new TextureAtlas(Gdx.files.internal("data/itens/weapons/daggers.txt"));
		
		
		
	}
	
	
	//Load Sets
	public Sprite CastingSpriteSet(float posX, float posY, String set, String side) {
		
		if(side == null) { side = "yes_Right"; }
		
		if(set.equals("basic_set_male")) {
			if(side.equals("yes_Right")) {
				spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;				
			}
			if(side.equals("yes_Left")) {
				spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;
			}
		}
		
		if(set.equals("basic_set_female")) {
			if(side.equals("yes_Right")) {
				spr_master = atlas_basic_female_set.createSprite("basic_set_female_magician_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;				
			}
			if(side.equals("yes_Left")) {
				spr_master = atlas_basic_female_set.createSprite("basic_set_female_magician_left1"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;
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
				
				if(Character_Data.Job_A.equals("Medic") || Character_Data.Job_A.equals("Magician")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magicAttackSpell_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magicAttackSpell_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
				
				if(Character_Data.Job_A.equals("Thief")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_thief_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
				
				if(Character_Data.Job_A.equals("Beater")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_merchant_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
				
				if(Character_Data.Job_A.equals("Gunner")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	

					if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_gunner_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
			}

			//Menu
			if(type.equals("Menu")) { 
				spr_master = atlas_basic_male_set.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 7.5f, posY - 10.5f);  spr_master.setSize(25, 36);
				return spr_master;
			}

			//Front basic_set_male_front3
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
		
		public Sprite ShowWeapon(String side,String walk, String type, float posX, float posY, Player Character_Data, boolean inBattle, boolean attackFrame, int playerbattleframe) {
			if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
				text = Character_Data.Battle_A;
				weapon = Character_Data.Weapon_A;
				
				weapon = "star_rod";
				//Character_Data.Job_A = "Swordman";
				
					//Novice
					if(Character_Data.Job_A.equals("Novice")) {
							
						/// Basic Knife ///
							if(weapon.equals("basic_knife")) {
								
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
							
						
						/// Double Edge Knife ///
							
							if(weapon.equals("doubleedge_knife")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_right_attack");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 8.9f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 5f, posY + 30);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_left_attack");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_right_attack");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_left_attack");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_nKnifes.createSprite("doubleedge_knife_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							
					}
					
					if(Character_Data.Job_A.equals("Swordman")) { 
						
						/// Basic Sword ///
						if(weapon.equals("wood_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("wood_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("wood_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("wood_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("wood_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("wood_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("wood_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("wood_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("wood_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("wood_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("wood_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("wood_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("wood_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("wood_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("wood_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("wood_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("wood_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Knight Sword ///
						if(weapon.equals("knight_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("knight_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("knight_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("knight_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("knight_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("knight_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("knight_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("knight_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("knight_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("knight_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("knight_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("knight_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("knight_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("knight_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("knight_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("knight_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("knight_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Edge Sword ///
						if(weapon.equals("edge_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("edge_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("edge_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("edge_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("edge_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("edge_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("edge_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("edge_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("edge_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("edge_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("edge_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("edge_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("edge_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("edge_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("edge_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("edge_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("edge_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Curved Sword ///
						if(weapon.equals("curved_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("curved_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("curved_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("curved_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("curved_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("curved_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("curved_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("curved_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("curved_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("curved_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("curved_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("curved_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("curved_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("curved_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("curved_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("curved_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("curved_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// flame Sword ///
						if(weapon.equals("flame_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("flame_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("flame_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("flame_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("flame_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("flame_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("flame_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("flame_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("flame_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("flame_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("flame_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("flame_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("flame_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("flame_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("flame_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("flame_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("flame_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// saber Sword ///
						if(weapon.equals("saber_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("saber_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("saber_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("saber_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("saber_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("saber_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("saber_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("saber_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("saber_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("saber_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("saber_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("saber_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("saber_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("saber_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("saber_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("saber_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("saber_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Cristal Sword ///
						if(weapon.equals("cristal_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("cristal_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("cristal_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("cristal_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("cristal_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("cristal_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("cristal_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("cristal_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("cristal_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("cristal_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("cristal_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("cristal_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("cristal_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("cristal_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("cristal_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("cristal_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("cristal_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Serpent Sword ///
						if(weapon.equals("serpent_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("serpent_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("serpent_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("serpent_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("serpent_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("serpent_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("serpent_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("serpent_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("serpent_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("serpent_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("serpent_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("serpent_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("serpent_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("serpent_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("serpent_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("serpent_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("serpent_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Venom Sword ///
						if(weapon.equals("venom_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("venom_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("venom_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("venom_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("venom_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("venom_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("venom_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("venom_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("venom_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("venom_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("venom_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("venom_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("venom_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("venom_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("venom_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("venom_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("venom_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Rage Sword ///
						if(weapon.equals("rage_sword")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("rage_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("rage_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("rage_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("rage_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("rage_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("rage_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("rage_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("rage_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("rage_sword_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("rage_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("rage_sword_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("rage_sword_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_swords.createSprite("rage_sword_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_swords.createSprite("rage_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_swords.createSprite("rage_sword_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_swords.createSprite("rage_sword_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
					}
					
					
					if(Character_Data.Job_A.equals("Magician") || Character_Data.Job_A.equals("Medic")) { 
						
						/// Stick Rod ///
						if(weapon.equals("stick_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("stick_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("stick_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// gloom Rod ///
						if(weapon.equals("gloom_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gloom_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gloom_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// clerig Rod ///
						if(weapon.equals("clerig_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("clerig_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("clerig_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// Death Rod ///
						if(weapon.equals("death_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("death_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("death_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// butterfly rod ///
						if(weapon.equals("butterfly_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("butterfly_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("butterfly_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// gem rod ///
						if(weapon.equals("gem_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gem_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("gem_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// sacred rod ///
						if(weapon.equals("sacred_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("sacred_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("sacred_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// serpent rod ///
						if(weapon.equals("serpent_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("serpent_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("serpent_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
						/// lightwieldrod ///
						if(weapon.equals("lightwield_rod")) {
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 7.3f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 2f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("lightwield_rod_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_rods.createSprite("lightwield_rod_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
						
					
						if(Character_Data.Job_A.equals("Thief")) { 
							
							/// Basic Dagger ///
							if(weapon.equals("basic_dagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("basic_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("basic_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("basic_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("basic_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("basic_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("basic_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("basic_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("basic_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// colisseum dagger ///
							if(weapon.equals("colisseum_dagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("colisseum_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// edge dagger ///
							if(weapon.equals("edge_dagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("edge_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("edge_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("edge_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("edge_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("edge_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("edge_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("edge_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("edge_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("edge_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("edge_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("edge_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("edge_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("edge_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("edge_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("edge_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("edge_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// flameberg dagger ///
							if(weapon.equals("flameberg_dagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("flameberg_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// poison dagger ///
							if(weapon.equals("poison_dagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("poison_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("poison_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("poison_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("poison_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("poison_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("poison_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("poison_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("poison_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("poison_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("poison_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("poison_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("poison_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("poison_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("poison_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("poison_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("poison_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// wind dagger ///
							if(weapon.equals("wind_dagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("wind_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("wind_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("wind_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("wind_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("wind_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("wind_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("wind_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("wind_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("wind_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("wind_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("wind_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("wind_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("wind_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("wind_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("wind_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("wind_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// triplo dagger ///
							if(weapon.equals("triplodagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("triplo_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// thunder dagger ///
							if(weapon.equals("thunderdagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("thunder_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// marine dagger ///
							if(weapon.equals("marinedagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("marine_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("marine_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("marine_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("marine_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("marine_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("marine_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("marine_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("marine_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("marine_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("marine_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("marine_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("marine_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("marine_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("marine_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("marine_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("marine_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// black dagger ///
							if(weapon.equals("blackdagger")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("black_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("black_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("black_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("black_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("black_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("black_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("black_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("black_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("black_dagger_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("black_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("black_dagger_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("black_dagger_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_daggers.createSprite("black_dagger_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_daggers.createSprite("black_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_daggers.createSprite("black_dagger_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_daggers.createSprite("black_dagger_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
						}
						
						if(Character_Data.Job_A.equals("Gunner")) {
							/// basic pistol ///
							if(weapon.equals("basicpistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("basic_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("basic_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("basic_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("basic_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("basic_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("basic_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("basic_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("basic_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("basic_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("basic_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("basic_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("basic_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("basic_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("basic_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("basic_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("basic_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// light pistol ///
							if(weapon.equals("lightpistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("lightpistol_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// revolver pistol ///
							if(weapon.equals("revolverpistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("revolver_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// shooter pistol ///
							if(weapon.equals("shooterpistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shooter_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// shark pistol ///
							if(weapon.equals("sharkpistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shark_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shark_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shark_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shark_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shark_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shark_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shark_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shark_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shark_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shark_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shark_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shark_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("shark_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("shark_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("shark_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("shark_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// rifle pistol ///
							if(weapon.equals("riflepistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("rifle_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// heavymachine pistol ///
							if(weapon.equals("heavymachinepistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("heavymachine_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// cannon pistol ///
							if(weapon.equals("cannonpistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("cannon_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// turret pistol ///
							if(weapon.equals("turretpistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("turret_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("turret_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("turret_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("turret_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("turret_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("turret_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("turret_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("turret_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("turret_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("turret_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("turret_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("turret_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("turret_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("turret_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("turret_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("turret_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// machine pistol ///
							if(weapon.equals("machinepistol")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("machine_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("machine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("machine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("machine_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("machine_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("machine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("machine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("machine_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("machine_pistol_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("machine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("machine_pistol_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("machine_pistol_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_pistols.createSprite("machine_pistol_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_pistols.createSprite("machine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_pistols.createSprite("machine_pistol_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_pistols.createSprite("machine_pistol_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
						} 
						
						if(Character_Data.Job_A.equals("Beater")) {
							/// scythe axe ///
							if(weapon.equals("scytheaxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("scythe_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("scythe_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("scythe_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("scythe_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("scythe_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("scythe_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("scythe_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("scythe_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("scythe_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("scythe_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("scythe_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("scythe_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("scythe_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("scythe_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("scythe_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("scythe_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// wrenck axe ///
							if(weapon.equals("wrenckaxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("wrenck_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("wrenck_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("wrenck_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("wrenck_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("wrenck_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("wrenck_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("wrenck_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("wrenck_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("wrenck_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("wrenck_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("wrenck_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("wrenck_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("wrenck_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("wrenck_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("wrenck_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("wrenck_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// Anchor axe ///
							if(weapon.equals("anchoraxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("anchor_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("anchor_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("anchor_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("anchor_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("anchor_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("anchor_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("anchor_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("anchor_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("anchor_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("anchor_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("anchor_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("anchor_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("anchor_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("anchor_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("anchor_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("anchor_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// bloodteeth axe ///
							if(weapon.equals("bloodteethaxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("bloodteeth_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// hammer axe ///
							if(weapon.equals("hammeraxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("hammer_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("hammer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("hammer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("hammer_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("hammer_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("hammer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("hammer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("hammer_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("hammer_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("hammer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("hammer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("hammer_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("hammer_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("hammer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("hammer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("hammer_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							
							/// guitar axe ///
							if(weapon.equals("guitaraxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("guitar_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("guitar_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("guitar_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("guitar_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("guitar_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("guitar_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("guitar_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("guitar_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("guitar_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("guitar_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("guitar_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("guitar_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("guitar_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("guitar_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("guitar_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("guitar_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// pick axe ///
							if(weapon.equals("pickaxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("pick_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("pick_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("pick_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("pick_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("pick_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("pick_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("pick_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("pick_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("pick_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("pick_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("pick_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("pick_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("pick_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("pick_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("pick_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("pick_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// basicaxe ///
							if(weapon.equals("basicaxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("basic_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("basic_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("basic_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("basic_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("basic_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("basic_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("basic_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("basic_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("basic_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("basic_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("basic_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("basic_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("basic_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("basic_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("basic_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("basic_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// killeraxe ///
							if(weapon.equals("killeraxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("killer_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("killer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("killer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("killer_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("killer_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("killer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("killer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("killer_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("killer_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("killer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("killer_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("killer_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("killer_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("killer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("killer_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("killer_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
							/// sharpaxe ///
							if(weapon.equals("sharpaxe")) {
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("sharp_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 7.3f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("sharp_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("sharp_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("sharp_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 2f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("sharp_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 15f, posY + 19);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("sharp_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("sharp_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 5f, posY + 30);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("sharp_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 30);
										return spr_master;
									}							
								}
								
								if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("sharp_axe_attack_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 4f, posY + 15f);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("sharp_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 11.9f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("sharp_axe_right");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 12f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("sharp_axe_side_right");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX - 7f, posY + 24);
										return spr_master;
									}
								}
								if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
									
									if(attackFrame) {
										spr_master = atlas_axes.createSprite("sharp_axe_attack_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX - 14f, posY + 17);
										return spr_master;
									}
									
									if(playerbattleframe == 1 || playerbattleframe == 6) {
										spr_master = atlas_axes.createSprite("sharp_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26);
										return spr_master;
									}
									
									if(playerbattleframe == 2 || playerbattleframe == 5) {
										spr_master = atlas_axes.createSprite("sharp_axe_left");
										spr_master.setSize(30,37);
										spr_master.setPosition(posX + 2f, posY + 26.2f);
										return spr_master;
									}
									
									if(playerbattleframe == 3 || playerbattleframe == 4) {
										spr_master = atlas_axes.createSprite("sharp_axe_side_left");
										spr_master.setSize(25,39);
										spr_master.setPosition(posX + 2f, posY + 24);
										return spr_master;
									}							
								}
							}
							
						}
						
						//new job from here
					
					} // End jobs
				} //
					
			
			return null;
		}
}
