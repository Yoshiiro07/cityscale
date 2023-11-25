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
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {

	//Variables
	private Json json;
	private FileHandle file;
	private GameObject playerInfo;
	private String request;
	private String line;
	private String returnFromServer;
	private String subdata;
	private String OnlineRequest;
	private Random randnumber;
	
	public GameControl() {	
		json = new Json();
		playerInfo = new GameObject();
		randnumber = new Random();
	}
	
	//[A] DATA MANAGER
	public String CheckData() {
		file = Gdx.files.local("SaveData/save.json");		
		if(!file.exists()) { 
			try {
				GameObject player = new GameObject();
				int accNumber = randnumber.nextInt(999999);
				player.accountID = String.valueOf(accNumber);
				player.Name = "none";
				file.writeString(Base64Coder.encodeString(json.prettyPrint(player)), false);
				return "CreateNew";
				
			} catch (Exception e) 
			{
				return "Error";
			}
		}
		else {
			return "HasData";
		}
	}
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		playerInfo = json.fromJson(GameObject.class,Base64Coder.decodeString(file.readString()));	
	}
	
	public void SaveData(GameObject playerInfo) {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public GameObject RetornaPlayer() {
		return playerInfo;
	}

	public void LoadDownloadData(String hash) {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		playerInfo = json.fromJson(GameObject.class,Base64Coder.decodeString(hash));			
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public String LoadDataText() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		String dataStr = file.readString();
		return dataStr;
	}
}
