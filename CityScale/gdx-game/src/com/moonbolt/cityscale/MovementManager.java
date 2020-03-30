package com.moonbolt.cityscale;

public class MovementManager
{
	
	
	
	public MovementManager(){
		
	}
	
	
	
	public Sprite ReturnHairs(String hairName, String side, String walk, float posX, float posY) {			
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

	public Sprite MovChar(String set, String side,String walk, String type, float posX, float posY, int posInterject) {

		if(isCasting) {
			CastTime();

			if(set.equals("basic_set_male")) {
				if(text.equals("yes_Right")) {
					spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;					
				}
				if(text.equals("yes_Left")) {
					spr_master = atlas_basic_male_set.createSprite("basic_set_male_magician_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master;
				}
			}

			if(set.equals("basic_set_female")) {
				if(text.equals("yes_Right")) {
					spr_master = atlas_basic_female_set.createSprite("basic_set_female_magician_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;				
				}
				if(text.equals("yes_Left")) {
					spr_master = atlas_basic_female_set.createSprite("basic_set_female_magician_left2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master;
				}
			}

			return spr_master;
		}


		if(walk.equals("Walk") && side.equals("Left")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);
		}
		if(walk.equals("Walk") && side.equals("Right")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);
		}
		if(walk.equals("Walk") && side.equals("Front")) {
			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable - 1f;
			Character_Data.PY_A = String.valueOf(fUsable);
		}
		if(walk.equals("Walk") && side.equals("Back")) {
			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable + 1f;
			Character_Data.PY_A = String.valueOf(fUsable);
		}

		if(walk.equals("Walk") && side.equals("Left-Front")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Left";
		}
		if(walk.equals("Walk") && side.equals("Left-Back")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Left";
		}
		if(walk.equals("Walk") && side.equals("Back-Left")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Back";
		}
		if(walk.equals("Walk") && side.equals("Back-Right")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Back";
		}
		if(walk.equals("Walk") && side.equals("Right-Back")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Right";
		}
		if(walk.equals("Walk") && side.equals("Right-Front")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Right";
		}
		if(walk.equals("Walk") && side.equals("Front-Left")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Front";
		}
		if(walk.equals("Walk") && side.equals("Front-Right")) {
			fUsable = Float.parseFloat(Character_Data.PX_A);
			fUsable = fUsable + 0.8f;
			Character_Data.PX_A = String.valueOf(fUsable);

			fUsable = Float.parseFloat(Character_Data.PY_A);
			fUsable = fUsable - 0.8f;
			Character_Data.PY_A = String.valueOf(fUsable);
			side = "Front";
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

	//Recupera Position
	public int RecuperaPosition() {
		return playerbattleframe;
	}

	public Sprite ShowWeapon(String side,String walk, String type, float posX, float posY) {
		if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
			text = Character_Data.Battle_A;
			weapon = Character_Data.Weapon_A;
			if(Character_Data.Job_A.equals("Novice")) { 
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
			}

			if(Character_Data.Job_A.equals("Novice")) { 

			}
		}


		return null;
	}
	
}
