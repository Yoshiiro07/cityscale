package com.moonbolt.cityscale;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class SaveManager {
	
	//Primitives
	private int count = 0;
	
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
