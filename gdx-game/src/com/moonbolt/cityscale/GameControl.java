package com.moonbolt.cityscale;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.files.FileHandle;

public class GameControl {
	
	//Manager
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private GameObject player;
		
	public GameControl() {
		
		GameObject player = new GameObject();
	}
	
	
	
	/////////////////////// [ SUMMARY ] ///////////////////
	
	//[Account]//
	
	
	
	
	
	//[Account]//
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");
		
		//Creating a new one
		if (!file.exists()) {
				try {
					GameObject player = new GameObject();
					int accNumber = randnumber.nextInt(999999);
					player.AccountID = String.valueOf(accNumber);
					player.Name_1 = "none";
					player.Name_2 = "none";
					player.Name_3 = "none";
					file.writeString(Base64Coder.encodeString(json.prettyPrint(player)), false);
				} 
				catch (Exception e) 
				{
					String test = e.getMessage();
				}
		}
		
		else 
		{
			FileHandle file = Gdx.files.local("SaveData/save.json");		
			player = json.fromJson(GameObject.class, Base64Coder.decodeString(file.readString()));
		}
	}
	
	private void CreateNewChar(String name, String sex, String hair, String color) {
		boolean created = false;
		player = new GameObject();
		
		FileHandle file = Gdx.files.local("SaveData/save.json");		
		player = json.fromJson(GameObject.class, Base64Coder.decodeString(file.readString()));
		
		if(player.Name_1.equals("none") && !created) {
			player.Name_1 = name;
			player.Sex_1 = sex;
			player.Level_1 = 1;
			player.Exp_1 = 0;
			player.Job_1 = "Aprendiz";
			player.Map_1 = "StreetsA";
			player.Hp_1 = 100;
			player.Mp_1 = 100;
			player.HpMax_1 = 100;
			player.MpMax_1 = 100;
			player.regenTime_1 = 6000;
			player.regenTimeMax_1 = 6000;
			player.PosX_1 = -0.5f;
			player.PosY_1 = -4;
			player.Walk_1 = "no";
			player.Frame_1 = 1;
			player.Money_1 = 0;
			player.AtkTimer_1 = 200;
			player.AtkTimerMax_1 = 200;
			player.Casting_1 = "no";
			player.Target_1 = "none";
			player.Atk_1 = 2;
			player.Def_1 = 1;
			player.Evasion_1 = 1;
			player.Side_1 = "front";
			player.Set_1 = "basic";
			player.Hair_1 = hair;
			player.Color_1 = color;
			player.Hat_1 = "none";
			player.Weapon_1 = "basicknife";
			player.Crystal1_1 = "none";
			player.Crystal2_1 = "none";
			player.Crystal3_1 = "none";
			player.Crystal4_1 = "none";
			player.Crystal5_1 = "none";
			player.StatusPoint_1 = 0;
			player.Str_1 = 1;
			player.Agi_1 = 1;
			player.Vit_1 = 1;
			player.Dex_1 = 1;
			player.Wis_1 = 1;
			player.Stamina_1 = 100;
			player.StaminaMax_1 = 100;
			player.StaminaTimer_1 = 1200;
			player.buffA_1 = "none";
			player.buffB_1 = "none";
			player.buffC_1 = "none";
			player.BuffTimeA_1 = 0;
			player.BuffTimeB_1 = 0;
			player.BuffTimeC_1 = 0;
			player.party_1 = "none";
			player.playerInBattle_1 = "no";
			player.playerInAttack_1 = "no";
			player.playerInCast_1 = "no";
			player.hotkey1_1 = "none";
			player.hotkey2_1 = "none";
			
			String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[blue_crystal_intextra_3#4]-"; } 
	            if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
	            if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
	            if(i > 2) { itensList = itensList + "[NONE]-"; }          
	        }
	        player.Itens_1 = itensList;
	        created = true;
		}
		
		if(player.Name_2.equals("none") && !created) {
			player.Name_2 = name;
			player.Sex_2 = sex;
			player.Level_2 = 1;
			player.Exp_2 = 0;
			player.Job_2 = "Aprendiz";
			player.Map_2 = "StreetsA";
			player.Hp_2 = 100;
			player.Mp_2 = 100;
			player.HpMax_2 = 100;
			player.MpMax_2 = 100;
			player.regenTime_2 = 6000;
			player.regenTimeMax_2 = 6000;
			player.PosX_2 = -0.5f;
			player.PosY_2 = -4;
			player.Walk_2 = "no";
			player.Frame_2 = 1;
			player.Money_2 = 0;
			player.AtkTimer_2 = 200;
			player.AtkTimerMax_2 = 200;
			player.Casting_2 = "no";
			player.Target_2 = "none";
			player.Atk_2 = 2;
			player.Def_2 = 1;
			player.Evasion_2 = 1;
			player.Side_2 = "front";
			player.Set_2 = "basic";
			player.Hair_2 = hair;
			player.Color_2 = color;
			player.Hat_2 = "none";
			player.Weapon_2 = "basicknife";
			player.Crystal1_2 = "none";
			player.Crystal2_2 = "none";
			player.Crystal3_2 = "none";
			player.Crystal4_2 = "none";
			player.Crystal5_2 = "none";
			player.StatusPoint_2 = 0;
			player.Str_2 = 1;
			player.Agi_2 = 1;
			player.Vit_2 = 1;
			player.Dex_2 = 1;
			player.Wis_2 = 1;
			player.Stamina_2 = 100;
			player.StaminaMax_2 = 100;
			player.StaminaTimer_2 = 1200;
			player.buffA_2 = "none";
			player.buffB_2 = "none";
			player.buffC_2 = "none";
			player.BuffTimeA_2 = 0;
			player.BuffTimeB_2 = 0;
			player.BuffTimeC_2 = 0;
			player.party_2 = "none";
			player.playerInBattle_2 = "no";
			player.playerInAttack_2 = "no";
			player.playerInCast_2 = "no";
			player.hotkey1_2 = "none";
			player.hotkey2_2 = "none";
			
			String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[blue_crystal_intextra_3#4]-"; } 
	            if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
	            if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
	            if(i > 2) { itensList = itensList + "[NONE]-"; }          
	        }
	        player.Itens_2 = itensList;
	        created = true;
		}
		
		if(player.Name_3.equals("none") && !created) {
			player.Name_3 = name;
			player.Sex_3 = sex;
			player.Level_3 = 1;
			player.Exp_3 = 0;
			player.Job_3 = "Aprendiz";
			player.Map_3 = "StreetsA";
			player.Hp_3 = 100;
			player.Mp_3 = 100;
			player.HpMax_3 = 100;
			player.MpMax_3 = 100;
			player.regenTime_3 = 6000;
			player.regenTimeMax_3 = 6000;
			player.PosX_3 = -0.5f;
			player.PosY_3 = -4;
			player.Walk_3 = "no";
			player.Frame_3 = 1;
			player.Money_3 = 0;
			player.AtkTimer_3 = 200;
			player.AtkTimerMax_3 = 200;
			player.Casting_3 = "no";
			player.Target_3 = "none";
			player.Atk_3 = 2;
			player.Def_3 = 1;
			player.Evasion_3 = 1;
			player.Side_3 = "front";
			player.Set_3 = "basic";
			player.Hair_3 = hair;
			player.Color_3 = color;
			player.Hat_3 = "none";
			player.Weapon_3 = "basicknife";
			player.Crystal1_3 = "none";
			player.Crystal2_3 = "none";
			player.Crystal3_3 = "none";
			player.Crystal4_3 = "none";
			player.Crystal5_3 = "none";
			player.StatusPoint_3 = 0;
			player.Str_3 = 1;
			player.Agi_3 = 1;
			player.Vit_3 = 1;
			player.Dex_3 = 1;
			player.Wis_3 = 1;
			player.Stamina_3 = 100;
			player.StaminaMax_3 = 100;
			player.StaminaTimer_3 = 1200;
			player.buffA_3 = "none";
			player.buffB_3 = "none";
			player.buffC_3 = "none";
			player.BuffTimeA_3 = 0;
			player.BuffTimeB_3 = 0;
			player.BuffTimeC_3 = 0;
			player.party_3 = "none";
			player.playerInBattle_3 = "no";
			player.playerInAttack_3 = "no";
			player.playerInCast_3 = "no";
			player.hotkey1_3 = "none";
			player.hotkey2_3 = "none";
			
			String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[blue_crystal_intextra_3#4]-"; } 
	            if(i == 1) {  if(sex.equals("M")) {itensList = itensList + "[basicset_m#1]-"; } else { itensList = itensList + "[basicset_f#1]-"; }}
	            if(i == 2) {  itensList = itensList + "[basicknife#1]-"; } 
	            if(i > 2) { itensList = itensList + "[NONE]-"; }          
	        }
	        player.Itens_3 = itensList;
	        created = true;
		}
        
		SaveData(player);		
	}
	
	public void SaveData(GameObject acPlayer) {
		file = Gdx.files.local("SaveData/save.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(acPlayer)), false);
	}
	
	public void SetCharacter(int charnum) {
		if(charnum == 1) {
			player.Name_A = player.Name_1;
			player.Sex_3 = player.Sex_1;
			player.Level_3 = player.Level_1;
			player.Exp_3 = player.Exp_1;
			player.Job_3 = player.Job_1;
			player.Map_3 = "StreetsA";
			player.Hp_3 = 100;
			player.Mp_3 = 100;
			player.HpMax_3 = 100;
			player.MpMax_3 = 100;
			player.regenTime_3 = 6000;
			player.regenTimeMax_3 = 6000;
			player.PosX_3 = -0.5f;
			player.PosY_3 = -4;
			player.Walk_3 = "no";
			player.Frame_3 = 1;
			player.Money_3 = 0;
			player.AtkTimer_3 = 200;
			player.AtkTimerMax_3 = 200;
			player.Casting_3 = "no";
			player.Target_3 = "none";
			player.Atk_3 = 2;
			player.Def_3 = 1;
			player.Evasion_3 = 1;
			player.Side_3 = "front";
			player.Set_3 = "basic";
			player.Hair_3 = hair;
			player.Color_3 = color;
			player.Hat_3 = "none";
			player.Weapon_3 = "basicknife";
			player.Crystal1_3 = "none";
			player.Crystal2_3 = "none";
			player.Crystal3_3 = "none";
			player.Crystal4_3 = "none";
			player.Crystal5_3 = "none";
			player.StatusPoint_3 = 0;
			player.Str_3 = 1;
			player.Agi_3 = 1;
			player.Vit_3 = 1;
			player.Dex_3 = 1;
			player.Wis_3 = 1;
			player.Stamina_3 = 100;
			player.StaminaMax_3 = 100;
			player.StaminaTimer_3 = 1200;
			player.buffA_3 = "none";
			player.buffB_3 = "none";
			player.buffC_3 = "none";
			player.BuffTimeA_3 = 0;
			player.BuffTimeB_3 = 0;
			player.BuffTimeC_3 = 0;
			player.party_3 = "none";
			player.playerInBattle_3 = "no";
			player.playerInAttack_3 = "no";
			player.playerInCast_3 = "no";
			player.hotkey1_3 = "none";
			player.hotkey2_3 = "none";
		}
	}
				
}