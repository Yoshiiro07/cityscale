package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {
	
	//Server Credentials
    public String lservername = "cityserver.mysql.uhserver.com";
    public String lusername = "citymaster";
    public String lpassword = "City@key90";
    public String ldbname = "cityserver";
    
    //Variables
    private String warning = "";
    private String retornoOnline ="";
    private int countFrame = 0;
    private boolean charHasToMove = true;
    
    //Player
    private float playerCoordX = 0;
    private float playerCoordY = 0;
    
    //Sprite
    public Sprite spr_master;
    
  //Textures
    private TextureAtlas atlas_gameUI;
    private TextureAtlas atlas_generic;
    private TextureAtlas atlas_basicset;
    
    private TextureAtlas atlas_cards;
     
    private TextureAtlas atlas_hairsgeneric;
    private TextureAtlas atlas_hair1;
    private TextureAtlas atlas_hair2;
    private TextureAtlas atlas_hair3;
    
    private TextureAtlas atlas_ui;
    
    private TextureAtlas atlas_shop1;
    
    private TextureAtlas atlas_npcs1;
    
    private TextureAtlas atlas_weapongeneric;
    private TextureAtlas atlas_axes;
    private TextureAtlas atlas_daggers;
    private TextureAtlas atlas_nknifes;
    private TextureAtlas atlas_pistols;
    private TextureAtlas atlas_rods;
    private TextureAtlas atlas_swords;
    
    private TextureAtlas atlas_itensSet;
    private TextureAtlas atlas_itensDrop;
    private TextureAtlas atlas_itensHat;
    private TextureAtlas atlas_itensUtil;
    private TextureAtlas atlas_itensWeapon;
    private TextureAtlas atlas_hatFrame;
    
    private TextureAtlas atlas_mobSewers;
    private TextureAtlas atlas_mobGeneric;
    
    private TextureAtlas atlas_tripleattack;
    private TextureAtlas atlas_rockbound;
    private TextureAtlas atlas_regen;
    private TextureAtlas atlas_steal;
    private TextureAtlas atlas_soulclash;
    private TextureAtlas atlas_ravenblade;
    private TextureAtlas atlas_ragebound;
    private TextureAtlas atlas_thundercloud;
    private TextureAtlas atlas_lockshot;
    private TextureAtlas atlas_mine;
    private TextureAtlas atlas_overpower;
    private TextureAtlas atlas_poisonhit;
    private TextureAtlas atlas_precision;
    private TextureAtlas atlas_protect;
    private TextureAtlas atlas_healthboost;
    private TextureAtlas atlas_holyprism;
    private TextureAtlas atlas_icecrystal;
    private TextureAtlas atlas_impound;
    private TextureAtlas atlas_invisibility;
    private TextureAtlas atlas_ironshield;
    private TextureAtlas atlas_doublehit;
    private TextureAtlas atlas_fastshot;
    private TextureAtlas atlas_fireball;
    private TextureAtlas atlas_flysword;
    private TextureAtlas atlas_heal;
    private TextureAtlas atlas_boost;
    private TextureAtlas atlas_berserk;
    private TextureAtlas atlas_bulletrain;
    private TextureAtlas atlas_dashkick;

    //[SUMMARY]//
	//[DATA CONTROL]//
    //[INTERFACE]//
	//[ONLINE MANAGER]//
    //[BATTLE]//
    //[COLISIONS]//
    //[CHARACTER]//
    //[MONSTERS]//
	
	private Player player;
	private Json json;
	private FileHandle file;
	private Random randnumber;
	
	public GameControl() {
		json = new Json();
		randnumber = new Random();
		
		//Atlas
		atlas_gameUI = new TextureAtlas(Gdx.files.internal("data/assets/interface/uirenew.txt"));
		
		atlas_hair1 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs1.txt"));
		atlas_hair2 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs2.txt"));
		atlas_hair3 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs3.txt"));
		
		atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/sets/basic/basic.txt"));
		atlas_generic = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/sets/basic/basic.txt"));
		
		atlas_cards = new TextureAtlas(Gdx.files.internal("data/assets/cards/cards.txt"));
		
		atlas_ui = new TextureAtlas(Gdx.files.internal("data/assets/interface/uirenew.txt"));
		
		
		atlas_npcs1 = new TextureAtlas(Gdx.files.internal("data/assets/chars/npcs/npcs1.txt"));
	}
	
	//[DATA CONTROL]//
	
	public void CreateNewChar(String name, String sex, String hair, String color) {
		player = new Player();
		
		FileHandle file = Gdx.files.local("SaveData/svdt.json");		
		player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
		
		if(player.Name_1.equals("none")) { player.CreateNewChar(name, sex, hair, color, 1);  SaveData(player); return; }
		if(player.Name_2.equals("none")) { player.CreateNewChar(name, sex, hair, color, 2);  SaveData(player); return; }
		if(player.Name_3.equals("none")) { player.CreateNewChar(name, sex, hair, color, 3);  SaveData(player); return; } 
		
		SaveData(player);
	}

	public void SaveData(Player player) {
		file = Gdx.files.local("SaveData/svdt.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(player)), false);
	}
	
	public void CheckData() {
		file = Gdx.files.local("SaveData/svdt.json");
		
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
					
				} catch (Exception e) 
				{
					String test = e.getMessage();
				}
		}
		
		else 
		{
			FileHandle file = Gdx.files.local("SaveData/svdt.json");		
			player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
		}
	}
	
	public void LoadDownloadData(String hash) {
		FileHandle file = Gdx.files.local("SaveData/svdt.json");
		Player player = json.fromJson(Player.class,Base64Coder.decodeString(hash));			
		file.writeString(Base64Coder.encodeString(json.prettyPrint(player)),false);
	}
	
	public void DeleteData() {
		FileHandle file = Gdx.files.local("SaveData/svdt.json");
		file.delete();
	}
	
	public Player LoadData() {
		FileHandle file = Gdx.files.local("SaveData/svdt.json");		
		player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));	
		return player;
	}
	
	public void DeleteChar(int num) {
		FileHandle file = Gdx.files.local("SaveData/svdt.json");		
		player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));	
		
		if(num == 1) { player.Name_1 = "none"; SaveData(player);}
		if(num == 2) { player.Name_2 = "none"; SaveData(player);}
		if(num == 3) { player.Name_3 = "none"; SaveData(player);}	
	}
	
	public Player GetPlayer() {
		return player;
	}
	
	public void SetPlayer(Player _player) {
		player = _player;
	}
	
	//[INTERFACE]//
	public Sprite GetInterface(String item) {
		
		//Title Screen elements
		if(item.equals("mainmenu")) {
			spr_master = atlas_ui.createSprite("mainmenu");
			spr_master.setSize(50, 50);
			spr_master.setPosition(18, -65);
			return spr_master;
		}
		
		if(item.equals("tagplayer")) {
			spr_master = atlas_ui.createSprite("tagplayer");
			return spr_master;
		}
		
		if(item.equals("cardaction")) {
			spr_master = atlas_cards.createSprite("cardaction");
			return spr_master;
		}
		
		if(item.equals("charmenu")) {
			spr_master = atlas_ui.createSprite("charmenu");
			return spr_master;
		}
		
		if(item.equals("ballonguard")) {
			spr_master = atlas_ui.createSprite("bartext");
			return spr_master;
		}
		
		if(item.equals("dungeonselector")) {
			spr_master = atlas_ui.createSprite("menudungeon");
			return spr_master;
		}
		
		if(item.equals("selecionepersonagem")) { spr_master = atlas_ui.createSprite("mainmenu"); return spr_master;}		
		if(item.equals("btncriar")) { spr_master = atlas_ui.createSprite("btncriar"); return spr_master;}
		if(item.equals("btnexcluir")) { spr_master = atlas_ui.createSprite("btnexcluir"); return spr_master;}
		
		if(item.equals("touchpad")) { spr_master = atlas_ui.createSprite("pad"); return spr_master; }
		if(item.equals("arrowmove")) { spr_master = atlas_ui.createSprite("arrowmove"); return spr_master; }
		
		return spr_master;
	}
	
	
	public void UseItem(int num) {
		
	}
	
	
	public Sprite GetItemIcon(String itemname) {
		
		
		
		return spr_master;
	}
	
	
	//[COLISIONS]//
	
	
	
	//[BATTLE]//
	
	
	
	//[MONSTERS]//
	public ArrayList<Monster> LoadMonsters(String map){
		Monster mobplaceholder = new Monster();
		return mobplaceholder.LoadMonsters(map);
	}
	
	public Sprite GetSpriteMonster(String map, String mobdata) {
		
		if(map.equals("Sewers")) { atlas_mobGeneric = atlas_mobSewers; }		
		spr_master = atlas_mobGeneric.createSprite(mobdata);
		
		return spr_master;
	}
	
	//[CHARACTER]//
	public void PutActiveSet(String setactive){
		if(setactive.equals("basic")){ atlas_generic = atlas_basicset; }
	}
	
	public void SetCharSide(float touchposX, float touchposY) {
		//Check MovePosition
		playerCoordX = player.PosX_A;
		playerCoordY = player.PosY_A;
		
		if(touchposY < playerCoordY && touchposX < player.PosX_A + 14 && touchposX > player.PosX_A - 3) {
			player.Side_A = "front";
		}
		if(touchposY > playerCoordY && touchposX < player.PosX_A + 14 && touchposX > player.PosX_A - 3) {
			player.Side_A = "back";
		}
		if(touchposY > playerCoordY && touchposX > player.PosX_A + 14) {
			player.Side_A = "right";
		}
		if(touchposY > playerCoordY && touchposX < player.PosX_A - 3) {
			player.Side_A = "left";
		}
		if(touchposY < playerCoordY && touchposX > player.PosX_A + 14) {
			player.Side_A = "right";
		}
		if(touchposY < playerCoordY && touchposX < player.PosX_A - 3) {
			player.Side_A = "left";
		}	
	}
	
	public void SetCharMovement(String breakwalk, float touchposX, float touchposY) {
		
		if(breakwalk.equals("yes")) { return; }
		
		//Check MovePosition
		playerCoordX = player.PosX_A;
		playerCoordY = player.PosY_A;
		
		if(playerCoordX < touchposX + 0.5 && playerCoordX > touchposX - 0.5 &&
				playerCoordY > touchposY - 0.5 && playerCoordY < touchposY + 0.5
				) { charHasToMove = false; player.Walk_A = "no"; player.Frame_A = 1; } 
		
		if(charHasToMove) {	
			if(playerCoordX > touchposX) { player.PosX_A -= 0.4f; player.Walk_A = "yes";}
			if(playerCoordX < touchposX) { player.PosX_A += 0.4f; player.Walk_A = "yes";}
			if(playerCoordY > touchposY) { player.PosY_A -= 0.4f; player.Walk_A = "yes";}
			if(playerCoordY < touchposY) { player.PosY_A += 0.4f; player.Walk_A = "yes";}
		}
		
		charHasToMove = true;
		
		//Check Frames
		if(player.Walk_A.equals("yes")) { countFrame++; }
		if(player.Walk_A.equals("no")) { player.Frame_A = 1; }
		if(countFrame > 1 && countFrame <= 15) { player.Frame_A = 2; }
		if(countFrame >= 15 && countFrame <= 30) { player.Frame_A = 1; }
		if(countFrame >= 30 && countFrame <= 45) { player.Frame_A = 3; }
		if(countFrame >= 45 && countFrame <= 60) { player.Frame_A = 1; }
		if(countFrame >= 60) { countFrame = 1; }
	}
	
	
	public Sprite CharacterMoveUpper(String type, int charnum) {
			
		if(type.equals("Menu")) {
			if(charnum == 1) {
				if(player.Sex_1.equals("M")) { 
				   spr_master = atlas_generic.createSprite("u_male_front1");
				   spr_master.setPosition(-49.2f, -25.2f);
				   spr_master.setSize(15, 30);
				   return spr_master;
				}
				if(player.Sex_1.equals("F")) { 
					spr_master = atlas_generic.createSprite("u_female_front1");
					spr_master.setPosition(-49f, -24.2f);
					spr_master.setSize(15, 29);
					return spr_master;
				}
			}
			if(charnum == 2) {
				if(player.Sex_2.equals("M")) { 
					spr_master = atlas_generic.createSprite("u_male_front1");
				    spr_master.setPosition(-10.3f, -25.2f);
				    spr_master.setSize(15, 30);
				    return spr_master;
				}
				if(player.Sex_2.equals("F")) { 
					spr_master = atlas_generic.createSprite("u_female_front1");
					spr_master.setPosition(-10.2f, -24.2f);
					spr_master.setSize(15, 29);
					return spr_master;
				}
			}
			if(charnum == 3) {
				if(player.Sex_3.equals("M")) { 
				   spr_master = atlas_generic.createSprite("u_male_front1");
				   spr_master.setPosition(28.5f, -25.2f);
				   spr_master.setSize(15, 30);
				   return spr_master;
				}
				if(player.Sex_3.equals("F")) { 
					spr_master = atlas_generic.createSprite("u_female_front1");
					spr_master.setPosition(28.6f, -24.2f);
					spr_master.setSize(15, 29);
					return spr_master;
				}
			}
		}
		
		String sex = player.Sex_A;
		if(sex.equals("M")) { sex = "male"; }
		if(sex.equals("F")) { sex = "female"; }
		
		//Create Sprite
		if(player.Walk_A.equals("no")) {
			if(player.Side_A.equals("front")) { spr_master = atlas_generic.createSprite("u_" + sex + "_front1");}
			if(player.Side_A.equals("back"))  { spr_master = atlas_generic.createSprite("u_" + sex + "_back1"); }
			if(player.Side_A.equals("right")) { spr_master = atlas_generic.createSprite("u_" + sex + "_right1");}
			if(player.Side_A.equals("left"))  { spr_master = atlas_generic.createSprite("u_" + sex + "_left1"); }
		}
		if(player.Walk_A.equals("yes")) {
			if(player.Side_A.equals("front")) { spr_master = atlas_generic.createSprite("u_" + sex + "_front" + player.Frame_A);}
			if(player.Side_A.equals("back")) { spr_master = atlas_generic.createSprite("u_" + sex + "_back" + player.Frame_A);  }
			if(player.Side_A.equals("left")) { spr_master = atlas_generic.createSprite("u_" + sex + "_left" + player.Frame_A);  }
			if(player.Side_A.equals("right")) { spr_master = atlas_generic.createSprite("u_" + sex + "_right" + player.Frame_A);}
			
			if(player.Side_A.equals("left-front") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1");  }
			if(player.Side_A.equals("left-back") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1");   }
			if(player.Side_A.equals("right-back") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1");  }
			if(player.Side_A.equals("right-front") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1"); }
			if(player.Side_A.equals("back-right") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1");  }
			if(player.Side_A.equals("back-left") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1");   }
			if(player.Side_A.equals("front-right") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1"); }
			if(player.Side_A.equals("front-left") ) {spr_master = atlas_generic.createSprite("u_" + sex + "_front1");  }			
		}
		
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 11.2f); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 11.2f); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 11.2f); } 
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 10.8f); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 10.8f); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 10.8f); } 
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.2f, player.PosY_A + 10.8f); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.2f, player.PosY_A + 10.8f); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.2f, player.PosY_A + 10.8f); } 
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 11.2f); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 11.2f); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 11.2f); } 
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A + 11.2f); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.9f, player.PosY_A + 11.2f); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.6f, player.PosY_A + 11.2f); } 
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 10.8f); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.5f, player.PosY_A + 10.8f); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.2f, player.PosY_A + 10.8f); } 
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.2f, player.PosY_A + 10.8f); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.2f, player.PosY_A + 10.8f); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.2f, player.PosY_A + 10.8f); } 
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A + 11.2f); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.9f, player.PosY_A + 11.2f); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.6f, player.PosY_A + 11.2f); }
		
		
		spr_master.setSize(10, 18);
		
		return spr_master;
	}
	
	public Sprite CharacterMoveBottom(String type,int charnum) {
		
		if(type.equals("Menu")) {
			if(charnum == 1) {
				if(player.Sex_1.equals("M")) { 
					spr_master = atlas_generic.createSprite("b_male_front1");
					spr_master.setPosition(-48.5f, -40f);
					spr_master.setSize(14, 25);
					return spr_master;
				}
				if(player.Sex_1.equals("F")) { 
					spr_master = atlas_generic.createSprite("b_female_front1");
					spr_master.setPosition(-48.9f, -40f);
					spr_master.setSize(15, 30);
					return spr_master;
				}
			}
			if(charnum == 2) {
				if(player.Sex_2.equals("M")) { 
					spr_master = atlas_generic.createSprite("b_male_front1");
					spr_master.setPosition(-9.5f, -40f);
					spr_master.setSize(14, 25);
					return spr_master;
				}
				if(player.Sex_2.equals("F")) { 
					spr_master = atlas_generic.createSprite("b_female_front1");
					spr_master.setPosition(-10.2f, -40f);
					spr_master.setSize(15, 30);
					return spr_master;
				}
			}
			if(charnum == 3) {
				if(player.Sex_3.equals("M")) { 
					spr_master = atlas_generic.createSprite("b_male_front1");
					spr_master.setPosition(29.2f, -40f);
					spr_master.setSize(14, 25);
					return spr_master;
				}
				if(player.Sex_3.equals("F")) { 
					spr_master = atlas_generic.createSprite("b_female_front1");
					spr_master.setPosition(28.5f, -40f);
					spr_master.setSize(15, 30);
					return spr_master;
				}
			}
		}
		
		String sex = player.Sex_A;
		if(sex.equals("M")) { sex = "male"; }
		if(sex.equals("F")) { sex = "female"; }
		
		
		//player.Frame_A = 1;
		//player.Walk_A = "yes";
		
		//Create Sprite
		if(player.Walk_A.equals("no")) {
			if(player.Side_A.equals("front")) { spr_master = atlas_generic.createSprite("b_" + sex + "_front1"); }
			if(player.Side_A.equals("back"))  { spr_master = atlas_generic.createSprite("b_" + sex + "_back1"); }
			if(player.Side_A.equals("right")) { spr_master = atlas_generic.createSprite("b_" + sex + "_right1");}
			if(player.Side_A.equals("left"))  { spr_master = atlas_generic.createSprite("b_" + sex + "_left1"); }
		}
		if(player.Walk_A.equals("yes")) {
			if(player.Side_A.equals("front")) { spr_master = atlas_generic.createSprite("b_" + sex + "_front" + player.Frame_A); }
			if(player.Side_A.equals("back")) { spr_master = atlas_generic.createSprite("b_" + sex + "_back" + player.Frame_A);  } //b_male_front2
			if(player.Side_A.equals("left")) { spr_master = atlas_generic.createSprite("b_" + sex + "_left" + player.Frame_A);  }
			if(player.Side_A.equals("right")) { spr_master = atlas_generic.createSprite("b_" + sex + "_right" + player.Frame_A);}
			
			if(player.Side_A.equals("left-front") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1");  }
			if(player.Side_A.equals("left-back") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1");   }
			if(player.Side_A.equals("right-back") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1");  }
			if(player.Side_A.equals("right-front") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1"); }
			if(player.Side_A.equals("back-right") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1");  }
			if(player.Side_A.equals("back-left") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1");   }
			if(player.Side_A.equals("front-right") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1"); }
			if(player.Side_A.equals("front-left") ) {spr_master = atlas_generic.createSprite("b_" + sex + "_front1");  }			
		}
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.6f, player.PosY_A + 0.9f); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.6f, player.PosY_A + 0.9f); } 
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		
		if(sex.equals("male") && player.Frame_A == 1 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("male") && player.Frame_A == 2 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.6f, player.PosY_A + 0.9f); } 
		if(sex.equals("male") && player.Frame_A == 3 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.6f, player.PosY_A + 0.9f); }
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("front")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("left")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("right")) {spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 0.2f); } 
		
		if(sex.equals("female") && player.Frame_A == 1 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("female") && player.Frame_A == 2 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); } 
		if(sex.equals("female") && player.Frame_A == 3 && player.Side_A.equals("back")) {spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A); }
				
		spr_master.setSize(10, 18);
		
		return spr_master;
	}
	
	
	public Sprite CharacterMoveHair() {
		if(player.Hair_A.equals("hair1")) { atlas_hairsgeneric = atlas_hair1;}
		
		
		if(player.Sex_A.equals("M")) {
			if(player.Side_A.equals("front")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Front"); //hair1brownFFront  
				spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A + 19.5f);
				spr_master.setSize(10, 18);
			}	
			if(player.Side_A.equals("back")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Back"); //hair1brownFBack	  
				spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A + 19f);
				spr_master.setSize(10, 18);
			}
			if(player.Side_A.equals("right")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Right"); //hair1brownMRight	  
				spr_master.setPosition(player.PosX_A + 1f, player.PosY_A + 19f);
				spr_master.setSize(9, 18);
			}
			if(player.Side_A.equals("left")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Left"); //hair1brownMRight	  
				spr_master.setPosition(player.PosX_A + 0.4f, player.PosY_A + 19f);
				spr_master.setSize(9, 18);
			}
		}
		
		if(player.Sex_A.equals("F")) {
			if(player.Side_A.equals("front")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Front"); //hair1brownFFront  
				spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A + 19f);
				spr_master.setSize(10, 18);
			}	
			if(player.Side_A.equals("back")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Back"); //hair1brownFBack	  
				spr_master.setPosition(player.PosX_A + 0.7f, player.PosY_A + 19f);
				spr_master.setSize(10, 18);
			}
			if(player.Side_A.equals("right")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Right"); //hair1brownMRight	  
				spr_master.setPosition(player.PosX_A + 1, player.PosY_A + 18.6f);
				spr_master.setSize(9, 18);
			}
			if(player.Side_A.equals("left")) { 
				spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Left"); //hair1brownMRight	  
				spr_master.setPosition(player.PosX_A + 0.8f, player.PosY_A + 18.6f);
				spr_master.setSize(9, 18);
			}
		}
		
		
		
		return spr_master;
	}
	
	public Sprite MenuHair(float cameraX, float cameraY) {
		
		if(player.Hair_A.equals("hair1")) { atlas_hairsgeneric = atlas_hair1;}
		
		
		if(player.Sex_A.equals("M")) {
			spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Front"); 
			spr_master.setPosition(cameraX - 103f, cameraY + 96f);
			spr_master.setSize(20, 35);				
		}
		if(player.Sex_A.equals("F")) {
			spr_master = atlas_hairsgeneric.createSprite("hair1" + player.Color_A + player.Sex_A + "Front");
			spr_master.setPosition(cameraX - 103f, cameraY + 96f);
			spr_master.setSize(20, 35);	
		}
		
		return spr_master;
	}
	
	public Sprite CharacterMoveHat() {
		
		return spr_master;
	}
	
	
	
	public Sprite ShowNPC(String npctype) {
	
		if(npctype.equals("guard")) {
			spr_master = atlas_npcs1.createSprite("guard1");
		}
		
		return spr_master;
		
	}
	
	
	
	//[ONLINE MANAGER]//
	public String GerenciamentoOnline(String tipoRequisicao, String subData, String extraData) throws IOException {
    	
		String linhaLida = "";
		
		/*countCleanOnline--;
		if(countCleanOnline < 0) {
			countCleanOnline = 500;
			lstOnlinePlayers.clear();
			lstOnlinePlayers.add(player);
			countCleanOnline = 800;
		}*/
			
		try {
		
		if(tipoRequisicao.equals("CheckVersion")){
			// Construct data
			
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("CheckVersion", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
	        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("1A", "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
	        //URL url = new URL("http://localhost/default.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        line = "";
	        retornoOnline = "retry";
	        while ((line = rd.readLine()) != null) {
	        	linhaLida = line;   
	        	//Resultado: - Logado -. <br>done
		        if (linhaLida.contains("Autorizado")) {            	
	        		retornoOnline = "Autorizado";       		
	            }	
		        else {
		        	retornoOnline = "Probido"; 
		        }
    		}	        
	        wr.close();
	        rd.close();
    
	        return retornoOnline;		        
		}
		
		if(tipoRequisicao.equals("Chat")){
			
			// Construct data	
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Chat", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
	        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("1A", "UTF-8");
	        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lChat", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        line = "";
	        retornoOnline = "retry";
	        while ((line = rd.readLine()) != null) {
	        	linhaLida = line;   
    		}	        
	        wr.close();
	        rd.close();
    
	        return retornoOnline;		        
		}
		
		if(tipoRequisicao.equals("Atk")){
			int numMob = Integer.parseInt(subData);
			
			String mobLetter = "A";
			if(numMob == 0) { mobLetter = "A"; }
			if(numMob == 1) { mobLetter = "B"; }
			if(numMob == 2) { mobLetter = "C"; }
			if(numMob == 3) { mobLetter = "D"; }
			
			// Construct data	
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Atk", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
	        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lHpMobAtual", "UTF-8") + "=" + URLEncoder.encode(extraData, "UTF-8"); 
	        data += "&" + URLEncoder.encode("lMobLetter", "UTF-8") + "=" + URLEncoder.encode(mobLetter, "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rdd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        line = "";
	        retornoOnline = "retry";
	        while ((line = rdd.readLine()) != null) {
	        	linhaLida = line;   
    		}	        
	        wr.close();
	        rdd.close();
	        
	        return retornoOnline;		        
		}
		
		
		if(tipoRequisicao.equals("SyncChats")){
			
			// Construct data				
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("SyncChats", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
	        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lChat", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
	        
	        // Send data
	        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        String linechat = "";
	        line = "";
	        
	        retornoOnline = "retry";
	        while ((line = rd.readLine()) != null) {
	        	linhaLida = line;  
	        	//Resultado: - Logado -. <br>done
	        	if (linhaLida.contains("SYSTEMCHAT")) { 
	        		String[] lineSplit = line.split(":");
	        		linechat = lineSplit[2] + "=" + lineSplit[4];
	    			//UpdateListOnlineChats(linechat);
	            }
    		}	        
	        wr.close();
	        rd.close();
    
	        return retornoOnline;		        
		}
		
		if(tipoRequisicao.equals("SyncPlayer")){
			// Construct data
			String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
			data += "&" + URLEncoder.encode("lIDLocal", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("SyncPlayer", "UTF-8");
	        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
	        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
	        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
	        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
	        //Sync Data
	        data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
	        data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lLevel", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Level_A), "UTF-8");
	        data += "&" + URLEncoder.encode("lMap", "UTF-8") + "=" + URLEncoder.encode(player.Map_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lHp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Hp_A), "UTF-8");
	        data += "&" + URLEncoder.encode("lMp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Mp_A), "UTF-8");
	        data += "&" + URLEncoder.encode("lPosX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.PosX_A), "UTF-8");
	        data += "&" + URLEncoder.encode("lPosY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.PosY_A), "UTF-8");
	        data += "&" + URLEncoder.encode("lWalk", "UTF-8") + "=" + URLEncoder.encode(player.Walk_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lWeapon", "UTF-8") + "=" + URLEncoder.encode(player.Weapon_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lFrame", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Frame_A), "UTF-8");
	        data += "&" + URLEncoder.encode("lParty", "UTF-8") + "=" + URLEncoder.encode(player.party_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lPlayerSet", "UTF-8") + "=" + URLEncoder.encode(player.SetUpper_A, "UTF-8");   
	        data += "&" + URLEncoder.encode("lPlayerSet", "UTF-8") + "=" + URLEncoder.encode(player.SetBottom_A, "UTF-8");
	        data += "&" + URLEncoder.encode("lHair", "UTF-8") + "=" + URLEncoder.encode(player.Hair_A, "UTF-8");    
	        data += "&" + URLEncoder.encode("lSex", "UTF-8") + "=" + URLEncoder.encode(player.Sex_A, "UTF-8");  
	        data += "&" + URLEncoder.encode("lColor", "UTF-8") + "=" + URLEncoder.encode(player.Color_A, "UTF-8");  
	        data += "&" + URLEncoder.encode("lHat", "UTF-8") + "=" + URLEncoder.encode(player.Hat_A, "UTF-8"); 
	        data += "&" + URLEncoder.encode("lSide", "UTF-8") + "=" + URLEncoder.encode(player.Side_A, "UTF-8"); 
	        data += "&" + URLEncoder.encode("lJob", "UTF-8") + "=" + URLEncoder.encode(player.Job_A, "UTF-8"); 
	        data += "&" + URLEncoder.encode("lplayerInBattle", "UTF-8") + "=" + URLEncoder.encode(player.playerInBattle_A, "UTF-8"); 
	        data += "&" + URLEncoder.encode("lplayerInAttack", "UTF-8") + "=" + URLEncoder.encode(player.playerInAttack_A, "UTF-8"); 
	        data += "&" + URLEncoder.encode("lplayerInCast", "UTF-8") + "=" + URLEncoder.encode(player.playerInCast_A, "UTF-8"); 
	        
	        // Send data
	        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        retornoOnline = "retry";
	        while ((line = rd.readLine()) != null) {
	        	linhaLida = line;   
	        	if (linhaLida.contains("SYSTEMPLAYERS")) {            	
		        	//UpdateListOnlinePlayers(line);     		
	            }	
    		}	        
	        wr.close();
	        rd.close();
	
	        return retornoOnline;		        
		}
		
		if(tipoRequisicao.equals("Upload")) {
				try {
				
				//Edite dada
				FileHandle file = Gdx.files.local("SaveData/save.json");	
				String arq = file.readString();
				
		        // Construct data
				//Instrucoes para Conexao
		        String data = URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(arq, "UTF-8");
		        data += "&" + URLEncoder.encode("lAccountID", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
		        data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Upload", "UTF-8");
		        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
		        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
		        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
		        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
		   	        
		        // Send data
		        URL url = new URL("http://moonboltprojects.online/Conector/Online.php");
		        URLConnection conn = url.openConnection();
		        conn.setDoOutput(true);
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(data);
		        wr.flush();
		 
		        // Get the response
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line;
		        while ((line = rd.readLine()) != null) {
		            if(line.contains("Sucesso")){
		            	warning = "Upload feito com sucesso";
		            }
		        	//System.out.println(line);
		        }		        
		        wr.close();
		        rd.close();
		        return retornoOnline;
		    } 
			
			catch (Exception e) { return "retry";}
		}
		
		return "";
		}
		
		catch(Exception ex) {
			return "retry";
		}
	}
	
	
}
