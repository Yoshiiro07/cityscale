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
	
	
	public String pet;
	public String pethungry;
	public String petcare;
	public String petTraining;
	public String petBath;
	public String petLevel;
		
		
		public void CreateNewChar(String name, String sex, String hair, String color) {
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
				
	        String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[hpcan#30]-"; } 
	            if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
	            if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
	            if(i > 2) { itensList = itensList + "[NONE]-"; }          
	        }
	        Itens_1 = itensList;
		}

}
