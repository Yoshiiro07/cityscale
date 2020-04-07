package com.moonbolt.cityscale;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MoveManager {
	
	//Primitives
	private boolean isCasting;
	private String text;
	private Sprite spr_master;
	private int castTime;
	
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
	
	public Sprite MovChar(String set, String side,String walk, String type, float posX, float posY, int posInterject) {
		
		//[Casting Animation]//
		if(isCasting) {
			CastTime();		
			return CastingAnimation(set, posX, posY);
		}
		
		
		
		
		
		if((walk.equals("Walk") && !inBattle) || (walk.equals("Walk")) && inBattle){
			frameMove++;
			if(frameMove >= 1 && frameMove <= 10) { 					
				pos = 1; 
			}
			
			if(frameMove >= 10 && frameMove <= 20) { 
				pos = 2; 
			}
			
			if(frameMove >= 20 && frameMove <= 30) { 
				pos = 1; 
			}
			
			if(frameMove >= 30 && frameMove <= 40) { 
				pos = 3; 
			}
			
			if(frameMove > 40) { 
				frameMove = 1; 
			}
		}
		
		if(!walk.equals("Walk") && inBattle){
			frameMove++;
			if(frameMove >= 1 && frameMove <= 10) { 					
				pos = 1; 
			}
			
			if(frameMove >= 10 && frameMove <= 20) { 
				pos = 2; 
			}
			
			if(frameMove >= 20 && frameMove <= 30) { 
				pos = 3; 
			}
			
			if(frameMove >= 30 && frameMove <= 40) { 
				pos = 4; 
			}
			
			if(frameMove >= 40 && frameMove <= 50) { 
				pos = 5; 
			}
			
			if(frameMove >= 50 && frameMove <= 60) { 
				pos = 6; 
			}
			
			if(frameMove > 60) { 
				frameMove = 1; 
			}
		}
		
		if(walk.equals("Stop") && !inBattle) {
			pos = 1;
		}
		
		if(walk.equals("Stop") && side.equals("Left-Front")) {
			side = "Left";
		}
		if(walk.equals("Stop") && side.equals("Left-Back")) {
			side = "Left";
		}
		if(walk.equals("Stop") && side.equals("Back-Left")) {
			side = "Back";
		}
		if(walk.equals("Stop") && side.equals("Back-Right")) {
			side = "Back";
		}
		if(walk.equals("Stop") && side.equals("Right-Back")) {
			side = "Right";
		}
		if(walk.equals("Stop") && side.equals("Right-Front")) {
			side = "Right";
		}
		if(walk.equals("Stop") && side.equals("Front-Left")) {
			side = "Front";
		}
		if(walk.equals("Stop") && side.equals("Front-Right")) {
			side = "Front";
		}
		
		if(posInterject > 0) { pos = posInterject; }
		//pos = 3;
		
		//attackFrame = false;
		
		//Para a arma
		playerbattleframe = pos;
		
		sidePlayer = side;
		
		//[MASC.] /////////////////////////////////////////////////  
		if(set.equals("basic_set_male")) {
			
			//SET
			//BATTLE
			if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
				text = Character_Data.Battle_A;
				
				if(Character_Data.Job_A.equals("Novice")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					
					if(pos == 1 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
				if(Character_Data.Job_A.equals("Swordman")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
					
					if(pos == 1 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atl_basicset_male.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				}
			}
			
			//Menu
			if(type.equals("Menu")) { 
				spr_master = atl_basicset_male.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 7.5f, posY - 10.5f);  spr_master.setSize(25, 36);
				return spr_master;
			}
			
			//Front
			if(side.equals("Front") && pos == 1) { spr_master = atl_basicset_male.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Front") && pos == 2) { spr_master = atl_basicset_male.createSprite("basic_set_male_front2"); spr_master.setPosition(posX - 0.1f, posY + 12.3f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Front") && pos == 3) { spr_master = atl_basicset_male.createSprite("basic_set_male_front3"); spr_master.setPosition(posX - 0.1f, posY + 12.4f); spr_master.setSize(25, 36); return spr_master; }
			
			//back
			if(side.equals("Back") && pos == 1) { spr_master = atl_basicset_male.createSprite("basic_set_male_back1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Back") && pos == 2) { spr_master = atl_basicset_male.createSprite("basic_set_male_back2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Back") && pos == 3) { spr_master = atl_basicset_male.createSprite("basic_set_male_back3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
			
			//right
			if(side.equals("Right") && pos == 1) { spr_master = atl_basicset_male.createSprite("basic_set_male_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Right") && pos == 2) { spr_master = atl_basicset_male.createSprite("basic_set_male_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Right") && pos == 3) { spr_master = atl_basicset_male.createSprite("basic_set_male_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master;}
			
			//left
			if(side.equals("Left") && pos == 1) { spr_master = atl_basicset_male.createSprite("basic_set_male_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);   spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Left") && pos == 2) { spr_master = atl_basicset_male.createSprite("basic_set_male_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
			if(side.equals("Left") && pos == 3) { spr_master = atl_basicset_male.createSprite("basic_set_male_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36);  return spr_master; }
			
		}
		
		//FEM. /////////////////////////////////////////////////
		if(set.equals("basic_set_female")) {
			//Battle
			if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
				text = Character_Data.Battle_A;
				if(Character_Data.Job_A.equals("Novice")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_meleeAttack_right"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
					
					if(pos == 1 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
				}
				
				if(Character_Data.Job_A.equals("Swordman")) {
					if(attackFrame && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_meleeAttack_right"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(attackFrame && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 1 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
					if(pos == 4 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Right")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
					
					if(pos == 1 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 2 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 3 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 4 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
					if(pos == 5 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					if(pos == 6 && text.equals("yes_Left")) { spr_master = atl_basicset_female.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
				}
			}
			
			//Menu
			if(type.equals("Menu")) { 
				spr_master = atl_basicset_female.createSprite("basic_set_female_front1"); spr_master.setPosition(posX - 7.5f, posY - 7);  spr_master.setSize(25, 33);
				return spr_master;
			}
			
			//Front
			if(side.equals("Front") && pos == 1) { spr_master = atl_basicset_female.createSprite("basic_set_female_front1"); spr_master.setPosition(posX - 5.5f, posY + 13.5f);  spr_master.setSize(25, 33); return spr_master; }
			if(side.equals("Front") && pos == 2) { spr_master = atl_basicset_female.createSprite("basic_set_female_front2"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }
			if(side.equals("Front") && pos == 3) { spr_master = atl_basicset_female.createSprite("basic_set_female_front3"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }
			
			//back
			if(side.equals("Back") && pos == 1) { spr_master = atl_basicset_female.createSprite("basic_set_female_back1"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }
			if(side.equals("Back") && pos == 2) { spr_master = atl_basicset_female.createSprite("basic_set_female_back2"); spr_master.setPosition(posX - 4.5f, posY + 15f); spr_master.setSize(23, 33); return spr_master; }
			if(side.equals("Back") && pos == 3) { spr_master = atl_basicset_female.createSprite("basic_set_female_back3"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }
			
			//right
			if(side.equals("Right") && pos == 1) { spr_master = atl_basicset_female.createSprite("basic_set_female_right1"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Right") && pos == 2) { spr_master = atl_basicset_female.createSprite("basic_set_female_right2"); spr_master.setPosition(posX - 3.8f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Right") && pos == 3) { spr_master = atl_basicset_female.createSprite("basic_set_female_right3"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master;}
			
			//left
			if(side.equals("Left") && pos == 1) { spr_master = atl_basicset_female.createSprite("basic_set_female_left1"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Left") && pos == 2) { spr_master = atl_basicset_female.createSprite("basic_set_female_left2"); spr_master.setPosition(posX - 4.5f, posY + 12.5f);  spr_master.setSize(24, 34); return spr_master; }
			if(side.equals("Left") && pos == 3) { spr_master = atl_basicset_female.createSprite("basic_set_female_left3"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }
			
		}
		
		return spr_master;
	}
	
	private Sprite WalkingAnimation() {
		return spr_master;
	}
	
	private Sprite CastingAnimation(String set, float posX, float posY) {
		if(set.equals("basic_set_male")) {
			if(text.equals("yes_Right")) {
				spr_master = atl_basicset_male.createSprite("basic_set_male_magician_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;					
			}
			if(text.equals("yes_Left")) {
				spr_master = atl_basicset_male.createSprite("basic_set_male_magician_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;
			}
		}
		
		if(set.equals("basic_set_female")) {
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