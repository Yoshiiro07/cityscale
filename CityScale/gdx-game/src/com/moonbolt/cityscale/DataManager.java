package com.moonbolt.cityscale;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class DataManager {

	private Json json;
	private FileHandle file;
	private Random randnumber;
	private Player player;
	private int ActiveCharNumber;
	
	public DataManager() {
		json = new Json();
		player = new Player();
		ActiveCharNumber = 0;
		CheckData();
	}
	
	public void CheckData() {
		file = Gdx.files.local("SaveData/SvDT.json");		
		if(file == null) { CreateNewData(); }
	}
	
	public void CreateNewData(){
		try {
			//Variaveis
			int count;
			Player player = new Player();
			
			//método
			count = 0;	   	    
			randnumber = new Random();
			FileHandle file = Gdx.files.local("SaveData/SvDT.json");
			while(count <= 100000){
				count = randnumber.nextInt(999999);
			}
					
				if (!file.exists()) {
					player = new Player();
					player.accountID = String.valueOf(count);
					
					player.name_1 = "none";
					player.name_2 = "none";
					player.name_3 = "none";
					
					file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);
				}
		}
		
		catch(Exception e) {}		
	}
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		player = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));	
	}
	
	public void CreateNewCharacter(String name, String 	hair, String sex) {
		//Carrega Save
		LoadData();
		
		//Variaveis
		int charNumber = 0;
		boolean created = false;
		
		//Processamento
		if(player.name_1.equals("none") && !created) { charNumber = 1; created = true;}
		if(player.name_2.equals("none") && !created) { charNumber = 2; created = true;}
		if(player.name_3.equals("none") && !created) { charNumber = 3; created = true;}
		
		if(charNumber == 1) {
			player.name_1 = name;
			player.sex_1 = sex;
			player.job_1 = "Novice";
			player.weapon_1 = "basic_knife";
			player.level_1 = "1";
			player.stats_1 = "|str:1|vit:1|agi:1|dex:1|luk:1|wis:1|res:1|";
			if(sex.equals("M")) { player.sex_1 = "basicset_m"; } else { player.sex_1 = "basicset_f"; }
			player.hat_1 = "none";
			player.exp_1 = "0";
			player.hp_1 = "100";
			player.mp_1 = "100";
			player.maxhp_1 = "100";
			player.maxmp_1 = "100";
			player.money_1 = "50";
			player.cooldown_1 = "";
			player.statusPoint_1 = "0";
			player.skillPoint_1 = "0";
			player.skills_1 = "none";
			player.coordX_1 = "";
			player.coordY_1 = "";
			player.inBattle_1 = "no";
			player.target_1 = "none";
			player.itens_1 = "none";
			player.map_1 = "MetroStation";
			player.party_1 = "none";
			player.inCasting_1 = "no";
			player.quest_1 = "Starting";
			player.state_1 = "stop";
		}
		
		if(charNumber == 2) {
			player.name_2 = name;
			player.sex_2 = sex;
			player.job_2 = "Novice";
			player.weapon_2 = "basic_knife";
			player.level_2 = "1";
			player.stats_2 = "|str:1|vit:1|agi:1|dex:1|luk:1|wis:1|res:1|";
			if(sex.equals("M")) { player.sex_2 = "basicset_m"; } else { player.sex_2 = "basicset_f"; }
			player.hat_2 = "none";
			player.exp_2 = "0";
			player.hp_2 = "100";
			player.mp_2 = "100";
			player.maxhp_2 = "100";
			player.maxmp_2 = "100";
			player.money_2 = "50";
			player.cooldown_2 = "";
			player.statusPoint_2 = "0";
			player.skillPoint_2 = "0";
			player.skills_2 = "none";
			player.coordX_2 = "";
			player.coordY_2 = "";
			player.inBattle_2 = "no";
			player.target_2 = "none";
			player.itens_2 = "none";
			player.map_2 = "MetroStation";
			player.party_2 = "none";
			player.inCasting_2 = "no";
			player.quest_2 = "Starting";
			player.state_2 = "stop";
		}
		
		if(charNumber == 3) {
			player.name_3 = name;
			player.sex_3 = sex;
			player.job_3 = "Novice";
			player.weapon_3 = "basic_knife";
			player.level_3 = "1";
			player.stats_3 = "|str:1|vit:1|agi:1|dex:1|luk:1|wis:1|res:1|";
			if(sex.equals("M")) { player.sex_3 = "basicset_m"; } else { player.sex_3 = "basicset_f"; }
			player.hat_3 = "none";
			player.exp_3 = "0";
			player.hp_3 = "100";
			player.mp_3 = "100";
			player.maxhp_3 = "100";
			player.maxmp_3 = "100";
			player.money_3 = "50";
			player.cooldown_3 = "";
			player.statusPoint_3 = "0";
			player.skillPoint_3 = "0";
			player.skills_3 = "none";
			player.coordX_3 = "";
			player.coordY_3 = "";
			player.inBattle_3 = "no";
			player.target_3 = "none";
			player.itens_3 = "none";
			player.map_3 = "MetroStation";
			player.party_3 = "none";
			player.inCasting_3 = "no";
			player.quest_3 = "Starting";
			player.state_3 = "stop";
		}
		
		file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);		
	}
	
	public void UpdateMapSwitch(String map) {
		if(map.equals("MetroStation")) {
			player.map_A = "MetroStation";
			player.coordX_A = "12";
			player.coordY_A = "66";
		}
		if(map.equals("Streets305")) {
			player.map_A = "Streets305";
			player.coordX_A = "12";
			player.coordY_A = "66";
		}
	}
	
	public void SaveData() {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);
	}
	
	public void SetActiveCharacter(int num) {
		ActiveCharNumber = num;
	}
	
	public void SetActivePlayerData(int num) {
		
		if(num == 1) {
			player.name_A = player.name_1;
			player.sex_A = player.sex_1;
			player.job_A = player.job_1;
			player.weapon_A = player.weapon_1;
			player.level_A = player.level_1;
			player.stats_A = player.stats_1;
			player.sex_A = player.sex_1; 
			player.hat_A = player.hat_1;
			player.exp_A = player.exp_1;
			player.hp_A = player.hp_1;
			player.mp_A = player.mp_1;
			player.maxhp_A = player.maxhp_1;
			player.maxmp_A = player.maxmp_1;
			player.money_A = player.money_1;
			player.cooldown_A = player.cooldown_1;
			player.statusPoint_A = player.statusPoint_1;
			player.skillPoint_A = player.skillPoint_1;
			player.skills_A = player.skills_1;
			player.coordX_A = player.coordX_1;
			player.coordY_A = player.coordY_1;
			player.inBattle_A = player.inBattle_1;
			player.target_A = player.target_1;
			player.itens_A = player.itens_1;
			player.map_A = player.map_1;
			player.party_A = player.party_1;
			player.inCasting_A = player.party_1;
			player.quest_A = player.quest_1;
			player.state_A = player.state_1;
		}
		if(num == 2) {
			player.name_A = player.name_2;
			player.sex_A = player.sex_2;
			player.job_A = player.job_2;
			player.weapon_A = player.weapon_2;
			player.level_A = player.level_2;
			player.stats_A = player.stats_2;
			player.sex_A = player.sex_2; 
			player.hat_A = player.hat_2;
			player.exp_A = player.exp_2;
			player.hp_A = player.hp_2;
			player.mp_A = player.mp_2;
			player.maxhp_A = player.maxhp_2;
			player.maxmp_A = player.maxmp_2;
			player.money_A = player.money_2;
			player.cooldown_A = player.cooldown_2;
			player.statusPoint_A = player.statusPoint_2;
			player.skillPoint_A = player.skillPoint_2;
			player.skills_A = player.skills_2;
			player.coordX_A = player.coordX_2;
			player.coordY_A = player.coordY_2;
			player.inBattle_A = player.inBattle_2;
			player.target_A = player.target_2;
			player.itens_A = player.itens_2;
			player.map_A = player.map_2;
			player.party_A = player.party_2;
			player.inCasting_A = player.party_2;
			player.quest_A = player.quest_2;
			player.state_A = player.state_2;
		}
		if(num == 3) {
			player.name_A = player.name_3;
			player.sex_A = player.sex_3;
			player.job_A = player.job_3;
			player.weapon_A = player.weapon_3;
			player.level_A = player.level_3;
			player.stats_A = player.stats_3;
			player.sex_A = player.sex_3; 
			player.hat_A = player.hat_3;
			player.exp_A = player.exp_3;
			player.hp_A = player.hp_3;
			player.mp_A = player.mp_3;
			player.maxhp_A = player.maxhp_3;
			player.maxmp_A = player.maxmp_3;
			player.money_A = player.money_3;
			player.cooldown_A = player.cooldown_3;
			player.statusPoint_A = player.statusPoint_3;
			player.skillPoint_A = player.skillPoint_3;
			player.skills_A = player.skills_3;
			player.coordX_A = player.coordX_3;
			player.coordY_A = player.coordY_3;
			player.inBattle_A = player.inBattle_3;
			player.target_A = player.target_3;
			player.itens_A = player.itens_3;
			player.map_A = player.map_3;
			player.party_A = player.party_3;
			player.inCasting_A = player.party_3;
			player.quest_A = player.quest_3;
			player.state_A = player.state_3;
		}
	}
}
