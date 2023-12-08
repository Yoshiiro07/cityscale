package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {

	//Variables
	private Json json;
	private FileHandle file;
	private Player player;
	
	public GameControl() {	
		json = new Json();
		player = new Player();
	}
	
	//[A] [ DATA MANAGER ] //
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");		
		if(!file.exists()) { 
			CreateNewData(); 
		}
	}
	
	public void CreateNewData() {
		player = new Player();
		player.accountID = "";
		player.charnumber = 1;	
		player.name = "";
		player.sex = "";
		 job;
		 weapon;
		 level;
		 stats;
		 stamina;
		 staminamax;
		 set;
		 hair;
		 hat;
		 exp;
		 hp;
		 mp;
		 maxhp;
		 maxmp;
		 atk;
		 def;
		 money;
		 cooldown;
		 statusPoint;
		 skillPoint;
		 skills;
		 buffsA;
		 buffsB;
		 buffsC;
		 coordX;
		 coordY;
		 pos;
		 side;
		 walk;
		 inBattle;
		 target;
		 itens;
		 map;
		 party;
		 inCasting;
		 quest;
		 state;
		 hotkey1;
		 hotkey2;
		 heal;
		 expshared;
		 crystalA;
		 crystalB;
		 crystalC;
		 crystalD;
		 itensHousing;
	}
	
	public void SaveData(Player playerInfo) {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		player = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));	
	}	
}
