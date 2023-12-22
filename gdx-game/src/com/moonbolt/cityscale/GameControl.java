package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {

	//Variables
	private Json json;
	private FileHandle file;
	private Player player;
	private Random random;
	
	private Sprite spr_master;
	
	public GameControl() {	
		json = new Json();
		player = new Player();
		random = new Random();
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
		int accountnumber = random.nextInt(999999);
		while (accountnumber < 100000) {
			accountnumber = random.nextInt(999999);
		}
		player.accountID = String.valueOf(accountnumber);
	}
	
	public void SaveData(Player playerInfo) {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		player = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));	
	}
	
	
	
	//[Char Mov] //
	public Sprite LoadCharUp(String Side, ) {
		
	}
	public Sprite LoadCharBottom() {
		
	}
	public Sprite LoadCharHair() {
		
	}
	public Sprite LoadCharHat() {
		
	}
	
	
}
