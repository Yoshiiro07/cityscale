package com.moonbolt.cityscale;

public class Player {
	
	public Player() {}
	
	//Player Character
	public String AccountID;
	
	public String Name_1;
	public String Sex_1;
	public int Level_1;
	public String Map_1;
	public int Exp_1;
	public String Job_1;
	public int Hp_1;
	public int Mp_1;
	public int Money_1;
	public int HpMax_1;
	public int MpMax_1;
	public int regenTime_1;
	public int regenTimeMax_1;
	public float PosX_1;
	public float PosY_1;
	public String Walk_1;
	public int Frame_1;
	public String Target_1;
	public int AtkTimer_1;
	public int AtkTimerMax_1;
	public String Casting_1;
	public int Atk_1;
	public int Def_1;
	public int Evasion_1;
	public String Side_1;
	public String SetUpper_1;
	public String SetBottom_1;
	public String Hair_1;
	public String Color_1;
	public String Hat_1;
	public String Weapon_1;
	public String Crystal1_1;
	public String Crystal2_1;
	public String Crystal3_1;
	public String Crystal4_1;
	public String Crystal5_1;
	public int StatusPoint_1;
	public int Str_1;
	public int Agi_1;
	public int Vit_1;
	public int Dex_1;
	public int Wis_1;
	public int Stamina_1;
	public int StaminaMax_1;
	public int StaminaTimer_1;
	public String Itens_1;
	public String Quests_1;
	public String hotkey1_1;
	public String hotkey2_1;
	public String buffA_1;
	public int BuffTimeA_1;
	public String buffB_1;
	public int BuffTimeB_1;
	public String buffC_1;
	public int BuffTimeC_1;
	public String party_1;
	public String playerInBattle_1;
	public String playerInAttack_1;
	public String playerInCast_1;
	
	public String hungry_1;
	public String bath_1;
	public String sleep_1;
	public String fun_1;
	public String health_1;
	public String social_1;
	public String cousine_1;
	public String mechanic_1;
	public String charisma_1;
	public String physic_1;
	public String talent_1;
	public String mental_1;
	public String socialjob_1;
	public String sociallevel_1;
	public String socialmoney_1;
	
	public String Name_2;
	public String Sex_2;
	public int Level_2;
	public String Map_2;
	public int Exp_2;
	public String Job_2;
	public int Hp_2;
	public int Mp_2;
	public int Money_2;
	public int HpMax_2;
	public int MpMax_2;
	public int regenTime_2;
	public int regenTimeMax_2;
	public float PosX_2;
	public float PosY_2;
	public String Walk_2;
	public int Frame_2;
	public String Target_2;
	public int AtkTimer_2;
	public int AtkTimerMax_2;
	public String Casting_2;
	public int Atk_2;
	public int Def_2;
	public int Evasion_2;
	public String Side_2;
	public String SetUpper_2;
	public String SetBottom_2;
	public String Hair_2;
	public String Color_2;
	public String Hat_2;
	public String Weapon_2;
	public String Crystal1_2;
	public String Crystal2_2;
	public String Crystal3_2;
	public String Crystal4_2;
	public String Crystal5_2;
	public int StatusPoint_2;
	public int Str_2;
	public int Agi_2;
	public int Vit_2;
	public int Dex_2;
	public int Wis_2;
	public int Stamina_2;
	public int StaminaMax_2;
	public int StaminaTimer_2;
	public String Itens_2;
	public String Quests_2;
	public String hotkey1_2;
	public String hotkey2_2;
	public String buffA_2;
	public int BuffTimeA_2;
	public String buffB_2;
	public int BuffTimeB_2;
	public String buffC_2;
	public int BuffTimeC_2;
	public String party_2;
	public String playerInBattle_2;
	public String playerInAttack_2;
	public String playerInCast_2;
	
	public String hungry_2;
	public String bath_2;
	public String sleep_2;
	public String fun_2;
	public String health_2;
	public String social_2;
	public String cousine_2;
	public String mechanic_2;
	public String charisma_2;
	public String physic_2;
	public String talent_2;
	public String mental_2;
	public String socialjob_2;
	public String sociallevel_2;
	public String socialmoney_2;
	
	
	public String pet;
	public String pethungry;
	public String petcare;
	public String petTraining;
	public String petBath;
	public String petLevel;
		
		
	public void CreateNewChar(String name, String sex, String hair, String color, int slot) {
		if(slot == 1) {
			Name_1 = name;
			Sex_1 = sex;
			Level_1 = 1;
			Exp_1 = 0;
			Job_1 = "Aprendiz";
			Map_1 = "MetroStation";
			Hp_1 = 100;
			Mp_1 = 100;
			HpMax_1 = 100;
			MpMax_1 = 100;
			regenTime_1 = 6000;
			regenTimeMax_1 = 6000;
			PosX_1 = -0.5f;
			PosY_1 = -4;
			Walk_1 = "no";
			Frame_1 = 1;
			Money_1 = 0;
			AtkTimer_1 = 200;
			AtkTimerMax_1 = 200;
			Casting_1 = "no";
			Target_1 = "none";
			Atk_1 = 9;
			Def_1 = 1;
			Evasion_1 = 1;
			Side_1 = "front";
			SetUpper_1 = "basic";
			SetBottom_1 = "basic";
			Hair_1 = hair;
			Color_1 = color;
			Hat_1 = "none";
			Weapon_1 = "basicknife";
			Crystal1_1 = "none";
			Crystal2_1 = "none";
			Crystal3_1 = "none";
			Crystal4_1 = "none";
			Crystal5_1 = "none";
			StatusPoint_1 = 0;
			Str_1 = 1;
			Agi_1 = 1;
			Vit_1 = 1;
			Dex_1 = 1;
			Wis_1 = 1;
			Stamina_1 = 100;
			StaminaMax_1 = 100;
			StaminaTimer_1 = 1200;
			buffA_1 = "none";
			buffB_1 = "none";
			buffC_1 = "none";
			BuffTimeA_1 = 0;
			BuffTimeB_1 = 0;
			BuffTimeC_1 = 0;
			party_1 = "none";
			playerInBattle_1 = "no";
			playerInAttack_1 = "no";
			playerInCast_1 = "no";
			hotkey1_1 = "none";
			hotkey2_1 = "none";
			hungry_1 = "100";
			bath_1 = "100";
			sleep_1 = "100";
			fun_1 = "100";
			health_1= "100";
			social_1= "100";
			cousine_1= "100";
			mechanic_1= "100";
			charisma_1= "100";
			physic_1= "100";
			talent_1= "100";
			mental_1= "100";
			socialjob_1= "100";
			sociallevel_1= "100";
			socialmoney_1= "100";
				
	        String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[hpcan#30]-"; } 
	            if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
	            if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
	            if(i > 2) { itensList = itensList + "[NONE]-"; }          
	        }
	        Itens_1 = itensList;
		}
		
		if(slot == 2) {
			Name_2 = name;
			Sex_2 = sex;
			Level_2 = 1;
			Exp_2 = 0;
			Job_2 = "Aprendiz";
			Map_2 = "MetroStation";
			Hp_2 = 100;
			Mp_2 = 100;
			HpMax_2 = 100;
			MpMax_2 = 100;
			regenTime_2 = 6000;
			regenTimeMax_2 = 6000;
			PosX_2 = -0.5f;
			PosY_2 = -4;
			Walk_2 = "no";
			Frame_2 = 1;
			Money_2 = 0;
			AtkTimer_2 = 200;
			AtkTimerMax_2 = 200;
			Casting_2 = "no";
			Target_2 = "none";
			Atk_2 = 9;
			Def_2 = 1;
			Evasion_2 = 1;
			Side_2 = "front";
			SetUpper_2 = "basic";
			SetBottom_2 = "basic";
			Hair_2 = hair;
			Color_2 = color;
			Hat_2 = "none";
			Weapon_2 = "basicknife";
			Crystal1_2 = "none";
			Crystal2_2 = "none";
			Crystal3_2 = "none";
			Crystal4_2 = "none";
			Crystal5_2 = "none";
			StatusPoint_2 = 0;
			Str_2 = 1;
			Agi_2 = 1;
			Vit_2 = 1;
			Dex_2 = 1;
			Wis_2 = 1;
			Stamina_2 = 100;
			StaminaMax_2 = 100;
			StaminaTimer_2 = 1200;
			buffA_2 = "none";
			buffB_2 = "none";
			buffC_2 = "none";
			BuffTimeA_2 = 0;
			BuffTimeB_2 = 0;
			BuffTimeC_2 = 0;
			party_2 = "none";
			playerInBattle_2 = "no";
			playerInAttack_2 = "no";
			playerInCast_2 = "no";
			hotkey1_2 = "none";
			hotkey2_2 = "none";
				
	        String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[hpcan#30]-"; } 
	            if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
	            if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
	            if(i > 2) { itensList = itensList + "[NONE]-"; }          
	        }
	        Itens_2 = itensList;
		}
	
	}
}
