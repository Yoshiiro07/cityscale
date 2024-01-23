package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
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
	
	//Server Credentials
    public String lservername = "cityserver.mysql.uhserver.com";
    public String lusername = "citymaster";
    public String lpassword = "City@key90";
    public String ldbname = "cityserver";
    
    //Variables
    private String warning = "";
    private String retornoOnline ="";
    
    //Sprite
    public Sprite spr_master;
    
  //Textures
    private TextureAtlas atlas_gameUI;
    private TextureAtlas atlas_generic;
    private TextureAtlas atlas_basicset;
    
    private TextureAtlas atlas_cards;
     
    private TextureAtlas atlas_hairs;
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
	
	private Player player;
	private Json json;
	private FileHandle file;
	private Random randnumber;
	
	public GameControl() {
		json = new Json();
		randnumber = new Random();
		
		//Atlas
		atlas_gameUI = new TextureAtlas(Gdx.files.internal("data/assets/UI/UI.txt"));
		
		atlas_hair1 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs1.txt"));
		atlas_hair2 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs2.txt"));
		atlas_hair3 = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/hairs/hairs3.txt"));
		
		atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/sets/basic/basic.txt"));
		atlas_generic = new TextureAtlas(Gdx.files.internal("data/assets/chars/player/sets/basic/basic.txt"));
		
		atlas_cards = new TextureAtlas(Gdx.files.internal("data/assets/cards/cards.txt"));
		
		atlas_ui = new TextureAtlas(Gdx.files.internal("data/assets/UI/uirenew.txt"));
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
	
	//[INTERFACE]//
	public Sprite GetInterface(String item) {
		
		//Title Screen elements
		if(item.equals("mainmenu")) {
			spr_master = atlas_ui.createSprite("mainmenu");
			spr_master.setSize(50, 50);
			spr_master.setPosition(18, -65);
			return spr_master;
		}
		if(item.equals("selecionepersonagem")) { spr_master = atlas_ui.createSprite("mainmenu"); return spr_master;}		
		if(item.equals("btncriar")) { spr_master = atlas_ui.createSprite("btncriar"); return spr_master;}
		if(item.equals("btnexcluir")) { spr_master = atlas_ui.createSprite("btnexcluir"); return spr_master;}
		
		return spr_master;
	}
	
	
	//[COLISIONS]//
	
	
	//[BATTLE]//
	
	
	//[CHARACTER]//
	public void PutActiveSet(String setactive){
		if(setactive.equals("basic")){ atlas_generic = atlas_basicset; }
	}
	
	
	public Sprite CharacterMoveUpper(String type, int charnum) {
			
		if(type.equals("Menu")) {
			if(charnum == 1) {
				if(player.Sex_1.equals("M")) { 
				   spr_master = atlas_generic.createSprite("u_male_front1");
				   spr_master.setPosition(-49.2f, -25.2f);
				   spr_master.setSize(15, 30);
				}
				if(player.Sex_1.equals("F")) { 
					spr_master = atlas_generic.createSprite("u_female_front1");
					spr_master.setPosition(-49f, -24.2f);
					spr_master.setSize(15, 29);
				}
			}
			if(charnum == 2) {
				if(player.Sex_2.equals("M")) { 
					spr_master = atlas_generic.createSprite("u_male_front1");
				    spr_master.setPosition(-10.3f, -25.2f);
				    spr_master.setSize(15, 30);
				}
				if(player.Sex_2.equals("F")) { 
					spr_master = atlas_generic.createSprite("u_female_front1");
					spr_master.setPosition(-10.2f, -24.2f);
					spr_master.setSize(15, 29);
				}
			}
			if(charnum == 3) {
				if(player.Sex_3.equals("M")) { 
				   spr_master = atlas_generic.createSprite("u_male_front1");
				   spr_master.setPosition(28.5f, -25.2f);
				   spr_master.setSize(15, 30);
				}
				if(player.Sex_3.equals("F")) { 
					spr_master = atlas_generic.createSprite("u_female_front1");
					spr_master.setPosition(28.6f, -24.2f);
					spr_master.setSize(15, 29);
				}
			}
		}
		
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
		
		
		int frame = player.Frame_A;
		
		
		
		
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
