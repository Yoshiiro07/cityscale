package com.moonbolt.cityscale;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class SaveManager {
	
	//Primitives
	private int count = 0;
	private int charNumActive = 0;
	
	//Objects
	private Random randnumber;
	private FileHandle file;
	private Player Character_Data;
	private Json json;

	public SaveManager() {
		randnumber = new Random();
	}
	
	public void UpdateCharacterData(Player chardata) {
		this.Character_Data = chardata;
	}
	
	public void SaveData() {		
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(Character_Data)),false);
	}
	
	public void WriteDataCharacterActive() {
		if(charNumActive == 1) {
			Character_Data.Name_1 = Character_Data.Name_A;
			Character_Data.Job_1 = Character_Data.Job_A;
			Character_Data.Level_1 = Character_Data.Level_A;
			Character_Data.Sex_1 = Character_Data.Sex_A;
			Character_Data.Atk_1 = Character_Data.Atk_A;
			Character_Data.Def_1 = Character_Data.Def_A;
			Character_Data.HP_1 = Character_Data.HP_A;
			Character_Data.MP_1 = Character_Data.MP_A;
			Character_Data.HPMAX_1 = Character_Data.HPMAX_A;
			Character_Data.MPMAX_1 = Character_Data.MPMAX_A;
			Character_Data.StatusPoint_1 = Character_Data.StatusPoint_A;
			Character_Data.SkillPoint_1 = Character_Data.SkillPoint_A;
			Character_Data.Exp_1 = Character_Data.Exp_A;
			Character_Data.PX_1 = Character_Data.PX_A;
			Character_Data.PY_1 = Character_Data.PY_A;
			Character_Data.Jump_1 = Character_Data.Jump_A;
			Character_Data.Couch_1 = Character_Data.Couch_A;
			Character_Data.Money_1 = Character_Data.Money_A;
			Character_Data.Stamina_1 = Character_Data.Stamina_A;
			Character_Data.Starve_1 = Character_Data.Starve_A;
			Character_Data.Speed_1 = Character_Data.Speed_A;
			Character_Data.State_1 = Character_Data.State_A;
			Character_Data.Walk_1 = Character_Data.Walk_A;
			Character_Data.Battle_1 = Character_Data.Battle_A;
			Character_Data.Target_1 = Character_Data.Target_A;
			Character_Data.Party_1 = Character_Data.Party_A;
			Character_Data.Map_1 = Character_Data.Map_A;
			
			Character_Data.Weapon_1 = Character_Data.Weapon_A;
			Character_Data.Hat_1 = Character_Data.Hat_A;
			Character_Data.Hair_1 = Character_Data.Hair_A;
			Character_Data.Set_1 = Character_Data.Set_A;
			Character_Data.AcessoryA_1 = Character_Data.AcessoryA_A;
			Character_Data.AcessoryB_1 = Character_Data.AcessoryB_A;
			
			Character_Data.Strengh_1 = Character_Data.Strengh_A;
			Character_Data.Agility_1 = Character_Data.Agility_A;
			Character_Data.Resistence_1 = Character_Data.Resistence_A;
			Character_Data.Vitality_1 = Character_Data.Vitality_A;
			Character_Data.Dextery_1 = Character_Data.Dextery_A;
			Character_Data.Mind_1 = Character_Data.Mind_A;
			Character_Data.Lucky_1 = Character_Data.Lucky_A;
			
			Character_Data.Status_1 = Character_Data.Status_A;
			Character_Data.Skills_1 = Character_Data.Skills_A;
			Character_Data.Itens_1 = Character_Data.Itens_A;
			Character_Data.Crystals_1 = Character_Data.Crystals_A;
			Character_Data.Quests_1 = Character_Data.Quests_A;
			Character_Data.ActiveQuest_1 = Character_Data.ActiveQuest_A;
			Character_Data.Pet_1 = Character_Data.Pet_A;
			Character_Data.Online_1 = Character_Data.Online_A;
		}
		if(charNumActive == 2) {
			Character_Data.Name_2 = Character_Data.Name_A;
			Character_Data.Job_2 = Character_Data.Job_A;
			Character_Data.Level_2 = Character_Data.Level_A;
			Character_Data.Sex_2 = Character_Data.Sex_A;
			Character_Data.Atk_2 = Character_Data.Atk_A;
			Character_Data.Def_2 = Character_Data.Def_A;
			Character_Data.HP_2 = Character_Data.HP_A;
			Character_Data.MP_2 = Character_Data.MP_A;
			Character_Data.HPMAX_2 = Character_Data.HPMAX_A;
			Character_Data.MPMAX_2 = Character_Data.MPMAX_A;
			Character_Data.StatusPoint_2 = Character_Data.StatusPoint_A;
			Character_Data.SkillPoint_2 = Character_Data.SkillPoint_A;
			Character_Data.Exp_2 = Character_Data.Exp_A;
			Character_Data.PX_2 = Character_Data.PX_A;
			Character_Data.PY_2 = Character_Data.PY_A;
			Character_Data.Jump_2 = Character_Data.Jump_A;
			Character_Data.Couch_2 = Character_Data.Couch_A;
			Character_Data.Money_2 = Character_Data.Money_A;
			Character_Data.Stamina_2 = Character_Data.Stamina_A;
			Character_Data.Starve_2 = Character_Data.Starve_A;
			Character_Data.Speed_2 = Character_Data.Speed_A;
			Character_Data.State_2 = Character_Data.State_A;
			Character_Data.Walk_2 = Character_Data.Walk_A;
			Character_Data.Battle_2 = Character_Data.Battle_A;
			Character_Data.Target_2 = Character_Data.Target_A;
			Character_Data.Party_2 = Character_Data.Party_A;
			Character_Data.Map_2 = Character_Data.Map_A;
			
			Character_Data.Weapon_2 = Character_Data.Weapon_A;
			Character_Data.Hat_2 = Character_Data.Hat_A;
			Character_Data.Hair_2 = Character_Data.Hair_A;
			Character_Data.Set_2 = Character_Data.Set_A;
			Character_Data.AcessoryA_2 = Character_Data.AcessoryA_A;
			Character_Data.AcessoryB_2 = Character_Data.AcessoryB_A;
			
			Character_Data.Strengh_2 = Character_Data.Strengh_A;
			Character_Data.Agility_2 = Character_Data.Agility_A;
			Character_Data.Resistence_2 = Character_Data.Resistence_A;
			Character_Data.Vitality_2 = Character_Data.Vitality_A;
			Character_Data.Dextery_2 = Character_Data.Dextery_A;
			Character_Data.Mind_2 = Character_Data.Mind_A;
			Character_Data.Lucky_2 = Character_Data.Lucky_A;
			
			Character_Data.Status_2 = Character_Data.Status_A;
			Character_Data.Skills_2 = Character_Data.Skills_A;
			Character_Data.Itens_2 = Character_Data.Itens_A;
			Character_Data.Crystals_2 = Character_Data.Crystals_A;
			Character_Data.Quests_2 = Character_Data.Quests_A;
			Character_Data.ActiveQuest_2 = Character_Data.ActiveQuest_A;
			Character_Data.Pet_2 = Character_Data.Pet_A;
			Character_Data.Online_2 = Character_Data.Online_A;
		}
		if(charNumActive == 3) {
			Character_Data.Name_3 = Character_Data.Name_A;
			Character_Data.Job_3 = Character_Data.Job_A;
			Character_Data.Level_3 = Character_Data.Level_A;
			Character_Data.Sex_3 = Character_Data.Sex_A;
			Character_Data.Atk_3 = Character_Data.Atk_A;
			Character_Data.Def_3 = Character_Data.Def_A;
			Character_Data.HP_3 = Character_Data.HP_A;
			Character_Data.MP_3 = Character_Data.MP_A;
			Character_Data.HPMAX_3 = Character_Data.HPMAX_A;
			Character_Data.MPMAX_3 = Character_Data.MPMAX_A;
			Character_Data.StatusPoint_3 = Character_Data.StatusPoint_A;
			Character_Data.SkillPoint_3 = Character_Data.SkillPoint_A;
			Character_Data.Exp_3 = Character_Data.Exp_A;
			Character_Data.PX_3 = Character_Data.PX_A;
			Character_Data.PY_3 = Character_Data.PY_A;
			Character_Data.Jump_3 = Character_Data.Jump_A;
			Character_Data.Couch_3 = Character_Data.Couch_A;
			Character_Data.Money_3 = Character_Data.Money_A;
			Character_Data.Stamina_3 = Character_Data.Stamina_A;
			Character_Data.Starve_3 = Character_Data.Starve_A;
			Character_Data.Speed_3 = Character_Data.Speed_A;
			Character_Data.State_3 = Character_Data.State_A;
			Character_Data.Walk_3 = Character_Data.Walk_A;
			Character_Data.Battle_3 = Character_Data.Battle_A;
			Character_Data.Target_3 = Character_Data.Target_A;
			Character_Data.Party_3 = Character_Data.Party_A;
			Character_Data.Map_3 = Character_Data.Map_A;
			
			Character_Data.Weapon_3 = Character_Data.Weapon_A;
			Character_Data.Hat_3 = Character_Data.Hat_A;
			Character_Data.Hair_3 = Character_Data.Hair_A;
			Character_Data.Set_3 = Character_Data.Set_A;
			Character_Data.AcessoryA_3 = Character_Data.AcessoryA_A;
			Character_Data.AcessoryB_3 = Character_Data.AcessoryB_A;
			
			Character_Data.Strengh_3 = Character_Data.Strengh_A;
			Character_Data.Agility_3 = Character_Data.Agility_A;
			Character_Data.Resistence_3 = Character_Data.Resistence_A;
			Character_Data.Vitality_3 = Character_Data.Vitality_A;
			Character_Data.Dextery_3 = Character_Data.Dextery_A;
			Character_Data.Mind_3 = Character_Data.Mind_A;
			Character_Data.Lucky_3 = Character_Data.Lucky_A;
			
			Character_Data.Status_3 = Character_Data.Status_A;
			Character_Data.Skills_3 = Character_Data.Skills_A;
			Character_Data.Itens_3 = Character_Data.Itens_A;
			Character_Data.Crystals_3 = Character_Data.Crystals_A;
			Character_Data.Quests_3 = Character_Data.Quests_A;
			Character_Data.ActiveQuest_3 = Character_Data.ActiveQuest_A;
			Character_Data.Pet_3 = Character_Data.Pet_A;
			Character_Data.Online_3 = Character_Data.Online_A;
		}
	}
	
	public void CreateNewData(){			
		count = 0;	   	    
		randnumber = new Random();
		file = Gdx.files.local("SaveData/SvDT.json");
		while(count <= 100000){
			count = randnumber.nextInt(999999);
		}
				
		if (!file.exists()) {
			Character_Data = new Player();
			Character_Data.setAccount(String.valueOf(count));
			Character_Data.setSituation("Valid");
			
			Character_Data.setName_1("none");
			Character_Data.setName_2("none");
			Character_Data.setName_3("none");
			
			file.writeString(Base64Coder.encodeString(json.prettyPrint(Character_Data)),false);
		}			
	}
	
	public void LoadDownloadData(String hash) {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		Character_Data = json.fromJson(Player.class,Base64Coder.decodeString(hash));			
		file.writeString(Base64Coder.encodeString(json.prettyPrint(Character_Data)),false);
	}
	
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		Character_Data = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));
	}
	
	public String LoadDataText() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		String dataStr = file.readString();
		return dataStr;
	}
	
	public String GetAccount() {
		return Character_Data.Account;
	}
	
	public void CreateNewCharacter(String[] configChar) {
		String name = configChar[0];
		String hair = configChar[1];
		String sex = configChar[2];
		String lstItens = "";
		int charNumber = 0;
		boolean checkCreated = false;
		
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		LoadData();
		
		if(Character_Data.Name_1.equals("none") && !checkCreated){ charNumber = 1; checkCreated = true; }
		if(Character_Data.Name_2.equals("none") && !checkCreated){ charNumber = 2; checkCreated = true; }
		if(Character_Data.Name_3.equals("none") && !checkCreated){ charNumber = 3; checkCreated = true; }
		
		//GeraItens
		for(int i = 1; i < 61; i++) {
			if(i == 60) { 
			lstItens = lstItens + "[None]";
			}
			else {
			lstItens = lstItens + "[None]-";
			}
		}
		
		if(charNumber == 1) {
			Character_Data.setName_1(name);
			Character_Data.setJob_1("Novice");
			Character_Data.setLevel_1("1");
			Character_Data.setSex_1(sex);
			Character_Data.setAtk_1("2");
			Character_Data.setDef_1("2");
			Character_Data.setHP_1("100");
			Character_Data.setMP_1("100");
			Character_Data.setHPMAX_1("100");
			Character_Data.setMPMAX_1("100");
			Character_Data.setStatusPoint_1("0"); 
			Character_Data.setSkillPoint_1("0");
			Character_Data.setExp_1("0");
			Character_Data.setPX_1("11");
			Character_Data.setPY_1("12"); 
			Character_Data.setJump_1("off"); 
			Character_Data.setCouch_1("off"); 
			Character_Data.setMoney_1("0"); 
			Character_Data.setStamina_1("100");
			Character_Data.setStarve_1("no");
			Character_Data.setSpeed_1("250"); 
			Character_Data.setState_1("Front"); 
			Character_Data.setWalk_1("Stop"); 
			Character_Data.setBattle_1("off");
			Character_Data.setTarget_1("none");
			Character_Data.setParty_1("none");
			Character_Data.setMap_1("MetroStation");
			
			if(sex.equals("M")) {
			Character_Data.setWeapon_1("basic_knife"); 
			Character_Data.setHat_1("none"); 
			Character_Data.setHair_1(hair);
			Character_Data.setSet_1("basic_set_male"); 
			Character_Data.setAcessoryA_1("none");
			Character_Data.setAcessoryB_1("none");
			}
			else {
			Character_Data.setWeapon_1("basic_knife"); 
			Character_Data.setHat_1("none"); 
			Character_Data.setHair_1(hair);
			Character_Data.setSet_1("basic_set_female"); 
			Character_Data.setAcessoryA_1("none");
			Character_Data.setAcessoryB_1("none");
			}
			
			Character_Data.setStrengh_1("1");
			Character_Data.setAgility_1("1"); 
			Character_Data.setResistence_1("1"); 
			Character_Data.setVitality_1("1"); 
			Character_Data.setDextery_1("1");
			Character_Data.setMind_1("1"); 
			Character_Data.setLucky_1("1");
			
			Character_Data.setStatus_1("");
			Character_Data.setSkills_1("");
			Character_Data.setItens_1(lstItens); //-[Item#Qtd]
			Character_Data.setCrystals_1("");
			Character_Data.setQuests_1("");
			Character_Data.setActiveQuest_1("");
			Character_Data.setPet_1("");
			Character_Data.setOnline_1("");				
		}
		if(charNumber == 2) {
			Character_Data.setName_2(name);
			Character_Data.setJob_2("Novice");
			Character_Data.setLevel_2("1");
			Character_Data.setSex_2(sex);
			Character_Data.setAtk_2("2");
			Character_Data.setDef_2("2");
			Character_Data.setHP_2("100");
			Character_Data.setMP_2("100");
			Character_Data.setHPMAX_2("100");
			Character_Data.setMPMAX_2("100");
			Character_Data.setStatusPoint_2("0"); 
			Character_Data.setSkillPoint_2("0");
			Character_Data.setExp_2("0");
			Character_Data.setPX_2("11");
			Character_Data.setPY_2("12"); 
			Character_Data.setJump_2("off"); 
			Character_Data.setCouch_2("off"); 
			Character_Data.setMoney_2("0"); 
			Character_Data.setStamina_2("100");
			Character_Data.setStarve_2("no");
			Character_Data.setSpeed_2("250"); 
			Character_Data.setState_2("Front"); 
			Character_Data.setWalk_2("Stop");
			Character_Data.setBattle_2("off");
			Character_Data.setTarget_2("none");
			Character_Data.setParty_2("none");
			Character_Data.setMap_2("MetroStation");
			
			if(sex.equals("M")) {
			Character_Data.setWeapon_2("basic_knife"); 
			Character_Data.setHat_2("none"); 
			Character_Data.setHair_2(hair);
			Character_Data.setSet_2("basic_set_male"); 
			Character_Data.setAcessoryA_2("none");
			Character_Data.setAcessoryB_2("none");
			}
			else {
			Character_Data.setWeapon_2("basic_knife"); 
			Character_Data.setHat_2("none"); 
			Character_Data.setHair_2(hair);
			Character_Data.setSet_2("basic_set_female"); 
			Character_Data.setAcessoryA_2("none");
			Character_Data.setAcessoryB_2("none");
			}
			
			Character_Data.setStrengh_2("1");
			Character_Data.setAgility_2("1"); 
			Character_Data.setResistence_2("1"); 
			Character_Data.setVitality_2("1"); 
			Character_Data.setDextery_2("1");
			Character_Data.setMind_2("1"); 
			Character_Data.setLucky_2("1");
			
			Character_Data.setStatus_2("");
			Character_Data.setSkills_2("");
			Character_Data.setItens_2(lstItens);
			Character_Data.setCrystals_2("");
			Character_Data.setQuests_2("");
			Character_Data.setActiveQuest_2("");
			Character_Data.setPet_2("");
			Character_Data.setOnline_2("");				
		}
		if(charNumber == 3) {
			Character_Data.setName_3(name);
			Character_Data.setJob_3("Novice");
			Character_Data.setLevel_3("1");
			Character_Data.setSex_3(sex);
			Character_Data.setAtk_3("2");
			Character_Data.setDef_3("2");
			Character_Data.setHP_3("100");
			Character_Data.setMP_3("100");
			Character_Data.setHPMAX_3("100");
			Character_Data.setMPMAX_3("100");
			Character_Data.setStatusPoint_3("0"); 
			Character_Data.setSkillPoint_3("0");
			Character_Data.setExp_3("0");
			Character_Data.setPX_3("11");
			Character_Data.setPY_3("12"); 
			Character_Data.setJump_3("off"); 
			Character_Data.setCouch_3("off"); 
			Character_Data.setMoney_3("0"); 
			Character_Data.setStamina_3("100");
			Character_Data.setStarve_3("no");
			Character_Data.setSpeed_3("250"); 
			Character_Data.setState_3("Front");
			Character_Data.setWalk_3("Stop");
			Character_Data.setBattle_3("off");
			Character_Data.setTarget_3("none");
			Character_Data.setParty_3("none");
			Character_Data.setMap_3("MetroStation");
			
			if(sex.equals("M")) {
			Character_Data.setWeapon_3("basic_knife"); 
			Character_Data.setHat_3("none"); 
			Character_Data.setHair_3(hair);
			Character_Data.setSet_3("basic_set_male"); 
			Character_Data.setAcessoryA_3("none");
			Character_Data.setAcessoryB_3("none");
			}
			else {
			Character_Data.setWeapon_3("basic_knife"); 
			Character_Data.setHat_3("none"); 
			Character_Data.setHair_3(hair);
			Character_Data.setSet_3("basic_set_female"); 
			Character_Data.setAcessoryA_3("none");
			Character_Data.setAcessoryB_3("none");
			}
			
			Character_Data.setStrengh_3("1");
			Character_Data.setAgility_3("1"); 
			Character_Data.setResistence_3("1"); 
			Character_Data.setVitality_3("1"); 
			Character_Data.setDextery_3("1");
			Character_Data.setMind_3("1"); 
			Character_Data.setLucky_3("1");
			
			Character_Data.setStatus_3("");
			Character_Data.setSkills_3("");
			Character_Data.setItens_3(lstItens);
			Character_Data.setCrystals_3("");
			Character_Data.setQuests_3("");
			Character_Data.setActiveQuest_3("");
			Character_Data.setPet_3("");
			Character_Data.setOnline_3("");				
		}
		
		file.writeString(Base64Coder.encodeString(json.prettyPrint(Character_Data)),false);
	}
}
