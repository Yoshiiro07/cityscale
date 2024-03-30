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
	private Player player;
		
	public GameControl() {
		
		
	}
	
	
	
	/////////////////////// [ SUMMARY ] ///////////////////
	//[Account]//
	//[Character]//
	
	//[Account]//
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");
		
		//Creating a new one
		if (!file.exists()) {
				try {
					Player player = new Player();
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
			player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
		}
	}

	public void SaveData(Player acPlayer) {
		file = Gdx.files.local("SaveData/save.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(acPlayer)), false);
	}

	public void LoadData(){
		FileHandle file = Gdx.files.local("SaveData/save.json");		
		player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
	}
	
	private void CreateNewChar(String name, String sex, String hair, String color) {
		boolean created = false;
		player = new Player();
		
		FileHandle file = Gdx.files.local("SaveData/save.json");		
		player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
		
		if(player.Name_1.equals("none") && !created) {
			player.Name_1 = name;
			player.Sex_1 = sex;
			player.Hair_1 = hair;
			player.Color_1 = color;
			player.Hat_1 = "none";
			player.Job_1 = "Aprendiz";
			player.SetUpper_1 = "basic";
			player.SetBottom_1 = "basic";
			player.SetFooter_1 = "basic";
			player.Level_1 = 1;
			player.Exp_1 = 0;
			player.Map_1 = "MetroStation";
			player.Hp_1 = 50;
			player.Mp_1 = 20;
			player.Money_1 = 0;
			player.HpMax_1 = 50;
			player.MpMax_1 = 20;
			player.regenTime_1 = 6000;
			player.regenTimeMax_1 = 6000;
			player.PosX_1 = 0;
			player.PosY_1 = 0;
			player.Walk_1 = "no";
			player.Frame_1 = 1;
			player.Target_1 = "none";
			player.AtkTimer_1 = 300;
			player.AtkTimerMax_1 = 300;
			player.Casting_1 = "no";
			player.Atk_1 = 3;
			player.Def_1 = 1;
			player.Evasion_1 = 0;
			player.Side_1 =	
			player.Weapon_1 = "basic_sword";
			player.Crystal1_1 = "none";
			player.Crystal2_1 = "none";
			player.Crystal3_1 = "none";
			player.Crystal4_1 = "none";
			player.StatusPoint_1 = 0;
			player.Str_1 = 1;
			player.Agi_1 = 1;
			player.Vit_1 = 1;
			player.Dex_1 = 1;
			player.Wis_1 = 1;
			player.Stamina_1 = 100;
			player.Quests_1 = "none";
			player.hotkey1_1 = "none";
			player.hotkey2_1 = "none";
			player.buffA_1 = "none";
			player.buffB_1 = "none";
			player.buffC_1 = "none";
			player.BuffTimeA_1 = 0;
			player.BuffTimeB_1 = 0;
			player.BuffTimeC_1 = 0;
			player.party_1 = "none";
			player.playerInBattle_1 = "none";
			player.playerInAttack_1 = "none";
			player.playerInCast_1 = "none";
			
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
			player.Hair_2 = hair;
			player.Color_2 = color;
			player.Hat_2 = "none";
			player.Job_2 = "Aprendiz";
			player.SetUpper_2 = "basic";
			player.SetBottom_2 = "basic";
			player.SetFooter_2 = "basic";
			player.Level_2 = 1;
			player.Exp_2 = 0;
			player.Map_2 = "MetroStation";
			player.Hp_2 = 50;
			player.Mp_2 = 20;
			player.Money_2 = 0;
			player.HpMax_2 = 50;
			player.MpMax_2 = 20;
			player.regenTime_2 = 6000;
			player.regenTimeMax_2 = 6000;
			player.PosX_2 = 0;
			player.PosY_2 = 0;
			player.Walk_2 = "no";
			player.Frame_2 = 1;
			player.Target_2 = "none";
			player.AtkTimer_2 = 300;
			player.AtkTimerMax_2 = 300;
			player.Casting_2 = "no";
			player.Atk_2 = 3;
			player.Def_2 = 1;
			player.Evasion_2 = 0;
			player.Side_2 =	
			player.Weapon_2 = "basic_sword";
			player.Crystal1_2 = "none";
			player.Crystal2_2 = "none";
			player.Crystal3_2 = "none";
			player.Crystal4_2 = "none";
			player.StatusPoint_2 = 0;
			player.Str_2 = 1;
			player.Agi_2 = 1;
			player.Vit_2 = 1;
			player.Dex_2 = 1;
			player.Wis_2 = 1;
			player.Stamina_2 = 100;
			player.Quests_2 = "none";
			player.hotkey1_2 = "none";
			player.hotkey2_2 = "none";
			player.buffA_2 = "none";
			player.buffB_2 = "none";
			player.buffC_2 = "none";
			player.BuffTimeA_2 = 0;
			player.BuffTimeB_2 = 0;
			player.BuffTimeC_2 = 0;
			player.party_2 = "none";
			player.playerInBattle_2 = "none";
			player.playerInAttack_2 = "none";
			player.playerInCast_2 = "none";
			
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
			player.Hair_3 = hair;
			player.Color_3 = color;
			player.Hat_3 = "none";
			player.Job_3 = "Aprendiz";
			player.SetUpper_3 = "basic";
			player.SetBottom_3 = "basic";
			player.SetFooter_3 = "basic";
			player.Level_3 = 1;
			player.Exp_3 = 0;
			player.Map_3 = "MetroStation";
			player.Hp_3 = 50;
			player.Mp_3 = 20;
			player.Money_3 = 0;
			player.HpMax_3 = 50;
			player.MpMax_3 = 20;
			player.regenTime_3 = 6000;
			player.regenTimeMax_3 = 6000;
			player.PosX_3 = 0;
			player.PosY_3 = 0;
			player.Walk_3 = "no";
			player.Frame_3 = 1;
			player.Target_3 = "none";
			player.AtkTimer_3 = 300;
			player.AtkTimerMax_3 = 300;
			player.Casting_3 = "no";
			player.Atk_3 = 3;
			player.Def_3 = 1;
			player.Evasion_3 = 0;
			player.Side_3 =	
			player.Weapon_3 = "basic_sword";
			player.Crystal1_3 = "none";
			player.Crystal2_3 = "none";
			player.Crystal3_3 = "none";
			player.Crystal4_3 = "none";
			player.StatusPoint_3 = 0;
			player.Str_3 = 1;
			player.Agi_3 = 1;
			player.Vit_3 = 1;
			player.Dex_3 = 1;
			player.Wis_3 = 1;
			player.Stamina_3 = 100;
			player.Quests_3 = "none";
			player.hotkey1_3 = "none";
			player.hotkey2_3 = "none";
			player.buffA_3 = "none";
			player.buffB_3 = "none";
			player.buffC_3 = "none";
			player.BuffTimeA_3 = 0;
			player.BuffTimeB_3 = 0;
			player.BuffTimeC_3 = 0;
			player.party_3 = "none";
			player.playerInBattle_3 = "none";
			player.playerInAttack_3 = "none";
			player.playerInCast_3 = "none";
			
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
	
	

	public void SetCharacter(int charnum) {
		if(charnum == 1) {
			player.Name_A = player.Name_1;
			player.Sex_A = player.Sex_1;
			player.Hair_A = player.Hair_1;
			player.Color_A = player.Color_1;
			player.Hat_A = player.Hat_1;
			player.Job_A = player.Job_1;
			player.SetUpper_A = player.SetUpper_1;
			player.SetBottom_A = player.SetBottom_1;
			player.SetFooter_A = player.SetFooter_1;
			player.Level_A = player.Level_1;
			player.Exp_A = player.Exp_1;
			player.Map_A = player.Map_1;
			player.Hp_A = player.Hp_1;
			player.Mp_A = player.Mp_1;
			player.Money_A = player.Money_1;
			player.HpMax_A = player.HpMax_1;
			player.MpMax_A = player.MpMax_1;
			player.regenTime_A = player.regenTime_1;
			player.regenTimeMax_A = player.regenTimeMax_1;
			player.PosX_A = player.PosX_1;
			player.PosY_A = player.PosY_1;
			player.Walk_A = player.Walk_1;
			player.Frame_A = player.Frame_1;
			player.Target_A = player.Target_1;
			player.AtkTimer_A = player.AtkTimer_1;
			player.AtkTimerMax_A = player.AtkTimerMax_1;
			player.Casting_A = player.Casting_1;
			player.Atk_A = player.Atk_1;
			player.Def_A = player.Def_1;
			player.Evasion_A = player.Evasion_1;
			player.Side_A =	player.Side_1;
			player.Weapon_A = player.Weapon_1;
			player.Crystal1_A = player.Crystal1_1;
			player.Crystal2_A = player.Crystal2_1;
			player.Crystal3_A = player.Crystal3_1;
			player.Crystal4_A = player.Crystal4_1;
			player.StatusPoint_A = player.StatusPoint_1;
			player.Str_A = player.Str_1;
			player.Agi_A = player.Agi_1;
			player.Vit_A = player.Vit_1;
			player.Dex_A = player.Dex_1;
			player.Wis_A = player.Wis_1;
			player.Stamina_A = player.Stamina_1;
			player.Quests_A = player.Quests_1;
			player.hotkey1_A = player.hotkey1_1;
			player.hotkey2_A = player.hotkey2_1;
			player.buffA_A = player.buffA_1;
			player.buffB_A = player.buffB_1;
			player.buffC_A = player.buffC_1;
			player.BuffTimeA_A = player.BuffTimeA_1;
			player.BuffTimeB_A = player.BuffTimeB_1;
			player.BuffTimeC_A = player.BuffTimeC_1;
			player.party_A = player.party_1;
			player.playerInBattle_A = player.playerInBattle_1;
			player.playerInAttack_A = player.playerInAttack_1;
			player.playerInCast_A = player.playerInCast_1;
		}

		if(charnum == 2) {
			player.Name_A = player.Name_2;
			player.Sex_A = player.Sex_2;
			player.Hair_A = player.Hair_2;
			player.Color_A = player.Color_2;
			player.Hat_A = player.Hat_2;
			player.Job_A = player.Job_2;
			player.SetUpper_A = player.SetUpper_2;
			player.SetBottom_A = player.SetBottom_2;
			player.SetFooter_A = player.SetFooter_2;
			player.Level_A = player.Level_2;
			player.Exp_A = player.Exp_2;
			player.Map_A = player.Map_2;
			player.Hp_A = player.Hp_2;
			player.Mp_A = player.Mp_2;
			player.Money_A = player.Money_2;
			player.HpMax_A = player.HpMax_2;
			player.MpMax_A = player.MpMax_2;
			player.regenTime_A = player.regenTime_2;
			player.regenTimeMax_A = player.regenTimeMax_2;
			player.PosX_A = player.PosX_2;
			player.PosY_A = player.PosY_2;
			player.Walk_A = player.Walk_2;
			player.Frame_A = player.Frame_2;
			player.Target_A = player.Target_2;
			player.AtkTimer_A = player.AtkTimer_2;
			player.AtkTimerMax_A = player.AtkTimerMax_2;
			player.Casting_A = player.Casting_2;
			player.Atk_A = player.Atk_2;
			player.Def_A = player.Def_2;
			player.Evasion_A = player.Evasion_2;
			player.Side_A = player.Side_2;
			player.Weapon_A = player.Weapon_2;
			player.Crystal1_A = player.Crystal1_2;
			player.Crystal2_A = player.Crystal2_2;
			player.Crystal3_A = player.Crystal3_2;
			player.Crystal4_A = player.Crystal4_2;
			player.StatusPoint_A = player.StatusPoint_2;
			player.Str_A = player.Str_2;
			player.Agi_A = player.Agi_2;
			player.Vit_A = player.Vit_2;
			player.Dex_A = player.Dex_2;
			player.Wis_A = player.Wis_2;
			player.Stamina_A = player.Stamina_2;
			player.Quests_A = player.Quests_2;
			player.hotkey1_A = player.hotkey1_2;
			player.hotkey2_A = player.hotkey2_2;
			player.buffA_A = player.buffA_2;
			player.buffB_A = player.buffB_2;
			player.buffC_A = player.buffC_2;
			player.BuffTimeA_A = player.BuffTimeA_2;
			player.BuffTimeB_A = player.BuffTimeB_2;
			player.BuffTimeC_A = player.BuffTimeC_2;
			player.party_A = player.party_2;
			player.playerInBattle_A = player.playerInBattle_2;
			player.playerInAttack_A = player.playerInAttack_2;
			player.playerInCast_A = player.playerInCast_2;
		}

		if(charnum == 3) {
			player.Name_A = player.Name_3;
			player.Sex_A = player.Sex_3;
			player.Hair_A = player.Hair_3;
			player.Color_A = player.Color_3;
			player.Hat_A = player.Hat_3;
			player.Job_A = player.Job_3;
			player.SetUpper_A = player.SetUpper_3;
			player.SetBottom_A = player.SetBottom_3;
			player.SetFooter_A = player.SetFooter_3;
			player.Level_A = player.Level_3;
			player.Exp_A = player.Exp_3;
			player.Map_A = player.Map_3;
			player.Hp_A = player.Hp_3;
			player.Mp_A = player.Mp_3;
			player.Money_A = player.Money_3;
			player.HpMax_A = player.HpMax_3;
			player.MpMax_A = player.MpMax_3;
			player.regenTime_A = player.regenTime_3;
			player.regenTimeMax_A = player.regenTimeMax_3;
			player.PosX_A = player.PosX_3;
			player.PosY_A = player.PosY_3;
			player.Walk_A = player.Walk_3;
			player.Frame_A = player.Frame_3;
			player.Target_A = player.Target_3;
			player.AtkTimer_A = player.AtkTimer_3;
			player.AtkTimerMax_A = player.AtkTimerMax_3;
			player.Casting_A = player.Casting_3;
			player.Atk_A = player.Atk_3;
			player.Def_A = player.Def_3;
			player.Evasion_A = player.Evasion_3;
			player.Side_A = player.Side_3;
			player.Weapon_A = player.Weapon_3;
			player.Crystal1_A = player.Crystal1_3;
			player.Crystal2_A = player.Crystal2_3;
			player.Crystal3_A = player.Crystal3_3;
			player.Crystal4_A = player.Crystal4_3;
			player.StatusPoint_A = player.StatusPoint_3;
			player.Str_A = player.Str_3;
			player.Agi_A = player.Agi_3;
			player.Vit_A = player.Vit_3;
			player.Dex_A = player.Dex_3;
			player.Wis_A = player.Wis_3;
			player.Stamina_A = player.Stamina_3;
			player.Quests_A = player.Quests_3;
			player.hotkey1_A = player.hotkey1_3;
			player.hotkey2_A = player.hotkey2_3;
			player.buffA_A = player.buffA_3;
			player.buffB_A = player.buffB_3;
			player.buffC_A = player.buffC_3;
			player.BuffTimeA_A = player.BuffTimeA_3;
			player.BuffTimeB_A = player.BuffTimeB_3;
			player.BuffTimeC_A = player.BuffTimeC_3;
			player.party_A = player.party_3;
			player.playerInBattle_A = player.playerInBattle_3;
			player.playerInAttack_A = player.playerInAttack_3;
			player.playerInCast_A = player.playerInCast_3;
		}	
	}

	//[Character]//
	
	
	
				
}