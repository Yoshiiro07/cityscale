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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {

	//Variables
	private Json json;
	private FileHandle file;
	private Player player;
	private Random random;
	
	//Player
	private int countFrame = 1;
	private String breakwalk = "";
	
	private TextureAtlas atlas_placeholder;
	private TextureAtlas atlas_basicset;
	
	private Sprite spr_master;
	
	public GameControl() {	
		json = new Json();
		player = new Player();
		random = new Random();
		
		atlas_placeholder = new TextureAtlas(Gdx.files.internal("data/assets/chars/basic/basic.txt"));
		atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/chars/basic/basic.txt"));
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
	public Sprite LoadChar(Player player,String type) {	
		
		//CreateMenu male
		if(type.equals("malecreate")) {
			spr_master = atlas_basicset.createSprite("u_basic_male_front");
			spr_master.setPosition(18, 49);
			spr_master.setSize(10, 20);	
			return spr_master;
		}
		//CreateMenu female
		if(type.equals("femalecreate")) {
			spr_master = atlas_basicset.createSprite("u_basic_female_front");
			spr_master.setPosition(1, 1);
			spr_master.setSize(10, 10);	
			return spr_master;
		}
		
		//Set Atlas Set
		if(player.set_A.equals("basic")) { atlas_placeholder = atlas_basicset; }
		
		//Check MovePosition
		if(player.walk_A.equals("walk")) {
		
			float posX = Float.parseFloat(player.coordX_A);
			float posY = Float.parseFloat(player.coordY_A);
		
			if(player.pos_A.equals("front") && !breakwalk.equals("front")) { posY = posY - 0.5f;  }
			if(player.pos_A.equals("back") && !breakwalk.equals("back")) { posY = posY - 0.5f;  }
			if(player.pos_A.equals("left") && !breakwalk.equals("left")) { posX = posX - 0.5f; }
			if(player.pos_A.equals("right") && !breakwalk.equals("right")) { posX = posX + 0.5f; }
			
			player.coordX_A = String.valueOf(posX);
			player.coordY_A = String.valueOf(posY);
			
		}
			
		//Check Frames
		int framepl = Integer.parseInt(player.frame_A);
		if(type.equals("player")) {
			if(!player.walk_A.equals("no")) { countFrame++; }
			if(player.walk_A.equals("no")) { player.frame_A = "1"; } 
			if(countFrame > 1 && countFrame <= 15) { player.frame_A = "2"; }
			if(countFrame >= 15 && countFrame <= 30) { player.frame_A = "1"; }
			if(countFrame >= 30 && countFrame <= 45) { player.frame_A = "3"; }
			if(countFrame >= 45 && countFrame <= 60) { player.frame_A = "1"; }
			if(countFrame >= 60) { countFrame = 1; }		
		}
		
		return spr_master;
	}
}
