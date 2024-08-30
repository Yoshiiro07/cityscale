package com.moonbolt.cityscale;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
////import java.io.OutputStreamWriter;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.moonbolt.cityscale.models.Monster;
import com.moonbolt.cityscale.models.Player;
import com.badlogic.gdx.files.FileHandle;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.core.client.RunAsyncCallback;
//import com.google.gwt.core.client.Scheduler;
//import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class GameControl {
	
	//Manager
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private Player player;
	private int FrameAtkPlayer = 0;
	private int charNumber = 0;
	
	private ArrayList<Monster> lstMonsters;
	private Monster placeholderMonster;
	
	//Sprite
	private Sprite spr_master;
	private Sprite spr_skill;
	
	//Online
	private ArrayList<Player> lstOnlinePlayers;
	private Player playerOnline = new Player();
    private int countCleanOnline = 800;
	private String retornoOnline = "";
	private int threahCountSyncPlayer = 0;
	private int threahCountSyncChat = 0;
	private int threahCountSyncMob = 0;
	private Thread thrOnlineSyncPlayer;
	private Thread thrOnlineSyncChat;
	private Thread thrOnlineSyncMob;
	private boolean onlineAuth = false;
    private boolean versionDif = false; 
    private boolean uploadDone = false;
    private String lservername = "cityserver.mysql.uhserver.com";
    private String lusername = "citymaster";
    private String lpassword = "P@titos07";
    private String ldbname = "cityserver";
    private String onlineresponse = "";
    private ArrayList<String> lstChats;
    private int ExpSharedOnline = 0;
	
	//Texture Atlas
	private TextureAtlas atlas_hairs1;
	private TextureAtlas atlas_hairs2;
	private TextureAtlas atlas_hairs3;
	private TextureAtlas atlas_hairs4;
	private TextureAtlas atlas_basicset;
	private TextureAtlas atlas_hats;
	private TextureAtlas atlas_ux;
	private TextureAtlas atlas_shops;
	private TextureAtlas atlas_genericset;
	private TextureAtlas atlas_generichair;
	private TextureAtlas atlas_npcs;
	private TextureAtlas atlas_cards;
	private TextureAtlas atlas_mobSewers;
	
	//private TextureAtlas atlas_items;
	private TextureAtlas atlas_weapon;
	private TextureAtlas atlas_cloth;
	private TextureAtlas atlas_cristais;
	private TextureAtlas atlas_food;
	private TextureAtlas atlas_hatsitem;
	private TextureAtlas atlas_lootmob;
	
	private TextureAtlas atlas_weapongeneric;
	private TextureAtlas atlas_nknifes;
	private TextureAtlas atlas_swords;
	private TextureAtlas atlas_rods;
	private TextureAtlas atlas_axes;
	private TextureAtlas atlas_pistols;
	private TextureAtlas atlas_daggers;
	
	
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
    private TextureAtlas atlas_defboost;
    private TextureAtlas atlas_berserk;
    private TextureAtlas atlas_bulletrain;
    private TextureAtlas atlas_dashkick;
	
	
	
	
		
	public GameControl() {
		
		json = new Json();
		randnumber = new Random();
		
		//Chats
		lstChats = new ArrayList<String>();
		lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add("");
		
		//Online player
		lstOnlinePlayers = new ArrayList<Player>();

		//Monster
		placeholderMonster = new Monster();
		lstMonsters = new ArrayList<Monster>();
		
		//Textures
		atlas_hairs1 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair1.txt"));
		atlas_hairs2 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair2.txt"));
		atlas_hairs3 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair3.txt"));
		atlas_hairs4 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair4.txt"));
		atlas_hats = new TextureAtlas(Gdx.files.internal("data/assets/characters/hats/hats.txt"));
		

		atlas_genericset = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_ux = new TextureAtlas(Gdx.files.internal("data/assets/ux/ux.txt"));

		atlas_mobSewers = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobsewers.txt"));
		
		atlas_npcs = new TextureAtlas(Gdx.files.internal("data/assets/characters/npcs/npcs.txt"));
		atlas_cards = new TextureAtlas(Gdx.files.internal("data/assets/skills/cards.txt"));
		
		//atlas_items = new TextureAtlas(Gdx.files.internal("data/assets/itens/itens/itens.txt"));
		atlas_weapon = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapon.txt"));
		atlas_cloth = new TextureAtlas(Gdx.files.internal("data/assets/itens/cloth.txt"));
		atlas_cristais = new TextureAtlas(Gdx.files.internal("data/assets/itens/cristais.txt"));
		atlas_food = new TextureAtlas(Gdx.files.internal("data/assets/itens/food.txt"));
		atlas_hatsitem = new TextureAtlas(Gdx.files.internal("data/assets/itens/hats.txt"));
		atlas_lootmob = new TextureAtlas(Gdx.files.internal("data/assets/itens/lootmob.txt"));
		
		atlas_weapongeneric = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/nknifes.txt"));
		atlas_nknifes = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/nknifes.txt"));
		atlas_swords = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/swords.txt"));
		atlas_rods = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/rods.txt"));
		atlas_axes = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/axes.txt"));
		atlas_pistols = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/pistols.txt"));
		atlas_daggers = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/daggers.txt"));
		
		atlas_shops = new TextureAtlas(Gdx.files.internal("data/assets/ux/shops.txt"));
		
		atlas_tripleattack = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/tripleattack.txt"));
		atlas_rockbound = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/rockbound.txt"));
		atlas_regen = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/regen.txt"));
		atlas_steal = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/steal.txt"));
		atlas_soulclash = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/soulclash.txt"));
		atlas_ravenblade = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/ravenblade.txt"));
		atlas_ragebound = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/ragebound.txt"));
		atlas_thundercloud = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/thundercloud.txt"));
		atlas_lockshot = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/lockshot.txt"));
		atlas_mine = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/mine.txt"));
		atlas_overpower = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/overpower.txt"));
		atlas_poisonhit = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/poisonhit.txt"));
		atlas_precision = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/precision.txt"));
		atlas_protect = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/protect.txt"));
		atlas_healthboost = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/healthboost.txt"));
		atlas_holyprism = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/holyprism.txt"));
		atlas_icecrystal = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/icecrystal.txt"));
		atlas_impound = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/impound.txt"));
		atlas_invisibility = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/invisibility.txt"));
		atlas_ironshield = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/ironshield.txt"));
		atlas_doublehit = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/doublehit.txt"));
		atlas_fastshot = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/fastshot.txt"));
		atlas_fireball = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/fireball.txt"));
		atlas_flysword = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/flysword.txt"));
		atlas_defboost = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/defboost.txt"));
		atlas_berserk = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/berserk.txt"));
		atlas_bulletrain = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/bulletrain.txt"));
		atlas_dashkick = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/dashkick.txt"));

	}
	
	/////////////////////// [ SUMMARY ] ///////////////////
	//[Account]//
	//[Interface]//
	//[Character]//
	//[Monsters]//
	//[Skills]//
	//[Online]//
	//[Exp/Drop]//
			
			//[online]//
			public String OnlineManager(String operation, String subData, String extraData) {
				try {
					if(operation.equals("CheckVersion")) {  
						TipoOperacaoOnline("CheckVersion", subData);
					}
					if(operation.equals("Chat")) {  
						TipoOperacaoOnline("Chat", subData);
					}
					if(operation.equals("Upload")) {  
						onlineresponse = TipoOperacaoOnline("Upload", subData);
					}
					if(operation.equals("Download")) {  
						TipoOperacaoOnline("Download", subData);
					}
					if(operation.equals("SyncChats")) {
						//ThreadsSyncStartChat();	
						//SyncChatGWT();
					}
					if(operation.equals("SyncPlayer")) {
						//ThreadsSyncStartPlayer();
						//SyncPlayersGWT();
					}
					if(operation.equals("ExpSharedSend")){
						TipoOperacaoOnline("ExpSharedSend", subData);
					}
					if(operation.equals("ExpGiver")){
						TipoOperacaoOnline("ExpGiver", subData);
					}
					return onlineresponse;
				}
				
				catch(Exception ex) {
					return onlineresponse;
				}
			}
		
			public String TipoOperacaoOnline(String nomeOperacao, String subData) {
				
				try {
					if(nomeOperacao.equals("CheckVersion")) {
						onlineresponse = GerenciamentoOnline("CheckVersion","","");		
					}
					if(nomeOperacao.equals("Chat")) {
						onlineresponse = GerenciamentoOnline("Chat",subData,"");		
					}
					if(nomeOperacao.equals("Upload")) {
						onlineresponse = GerenciamentoOnline("Upload","","");		
					}
					if(nomeOperacao.equals("Download")) {
						onlineresponse = GerenciamentoOnline("Download",subData,"");		
					}
					if(nomeOperacao.equals("ExpSharedSend")) {
						onlineresponse = GerenciamentoOnline("ExpSharedSend",subData,"");		
					}
					if(nomeOperacao.equals("ExpGiver")) {
						onlineresponse = GerenciamentoOnline("ExpGiver","","");		
					}
					if(nomeOperacao.equals("SyncChats")) {
						
						//SyncChatGWT();
						//ThreadsSyncStartChat();	
						
					}	
					if(nomeOperacao.equals("SyncPlayer")) {
						
						//ThreadsSyncStartPlayer();		
						//SyncPlayersGWT();
					}
					return onlineresponse;
				}
				
				catch(Exception ex) { return "none"; }	
			}
			
			
			//[USING THREADS]//
			/*private void ThreadsSyncStartChat() {
				thrOnlineSyncChat = new Thread(t1);
				thrOnlineSyncChat.start();
			}
			
			private void ThreadsSyncStartPlayer() {
				thrOnlineSyncChat = new Thread(t2);
				thrOnlineSyncChat.start();
			}
			
			private Runnable t1 = new Runnable() {
				public void run() {
					try{    
						threahCountSyncChat = 1;
						while(threahCountSyncChat == 1) {
							GerenciamentoOnline("SyncChats","","");            	
						}
					}
					catch(Exception ex) {
						Thread.currentThread().interrupt();	
					}	
				}
			};
			
			private Runnable t2 = new Runnable() {
				public void run() {
					try{   
						threahCountSyncPlayer = 1;
						while(threahCountSyncPlayer == 1) {
							GerenciamentoOnline("SyncPlayer","","");            	
						}
					}
					catch(Exception ex) {
						Thread.currentThread().interrupt();	
					}	
				}
			};
			*/
			
			//[USING GWT SCHEDULER]//
			/*public void SyncChatGWT() {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable reason) {
						// Show the error
					}

					public void onSuccess() {
						try {
							GerenciamentoOnline("SyncChats", "", "");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			
			public void SyncPlayersGWT() {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable reason) {
						// Show the error
					}

					public void onSuccess() {
						try {
							GerenciamentoOnline("SyncPlayer","","");  
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}*/
			
			public String GerenciamentoOnline(String tipoRequisicao, String subData, String extraData){
				return "";
			}
			
			
			/*public String GerenciamentoOnline(String tipoRequisicao, String subData, String extraData) throws IOException {
		    	
				String linhaLida = "";
				try {
				
				if(tipoRequisicao.equals("CheckVersion")){
					// Construct data
					
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("CheckVersion", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        data += "&" + URLEncoder.encode("lversion", "UTF-8") + "=" + URLEncoder.encode("1A", "UTF-8");
			        
			        // Send data
			        //URL url = new URL("http://moonboltprojects.online/conector/online.php");	        
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
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
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Chat", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        data += "&" + URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
			        data += "&" + URLEncoder.encode("lchat", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
			            
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
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
				
				if(tipoRequisicao.equals("SyncChats")){
					
					// Construct data
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("SyncChats", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			            
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
			        URLConnection conn = url.openConnection();
			        conn.setDoOutput(true);
			        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			        wr.write(data);
			        wr.flush();
			        
			        // Get the response
			        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        String line;
			        String linechat = "";
			        int chatnum = 1;
			        line = "";
			        lstChats.clear();
			        retornoOnline = "retry";
			        while ((line = rd.readLine()) != null) {
			        	linhaLida = line;   
			        	if (linhaLida.contains("SYSTEMCHAT")) {  
			        		String[] lineSplit = line.split(":");
			        		linechat = lineSplit[2] + "=" + lineSplit[4];
			        		
			        		if(chatnum == 1) { lstChats.add(0, linechat); }
			        		if(chatnum == 2) { lstChats.add(1, linechat); }
			        		if(chatnum == 3) { lstChats.add(2, linechat); }
			        		if(chatnum == 4) { lstChats.add(3, linechat); }
			        		if(chatnum == 5) { lstChats.add(4, linechat); }
			        		chatnum++;
			            }	
		    		}	        
			        wr.close();
			        rd.close();
		    
			        return retornoOnline;		        
				}
				
				
				if(tipoRequisicao.equals("SyncPlayer")){
					
					// Construct data
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("SyncPlayer", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        //Sync Data
			        data += "&" + URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
			        data += "&" + URLEncoder.encode("llevel", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Level_A), "UTF-8");
			        data += "&" + URLEncoder.encode("lmap", "UTF-8") + "=" + URLEncoder.encode(player.Map_A, "UTF-8");
			        data += "&" + URLEncoder.encode("lhp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Hp_A), "UTF-8");
			        data += "&" + URLEncoder.encode("lmp", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Mp_A), "UTF-8");
			        data += "&" + URLEncoder.encode("lposX", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.PosX_A), "UTF-8");
			        data += "&" + URLEncoder.encode("lposY", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.PosY_A), "UTF-8");
			        data += "&" + URLEncoder.encode("lwalk", "UTF-8") + "=" + URLEncoder.encode(player.Walk_A, "UTF-8");
			        data += "&" + URLEncoder.encode("lweapon", "UTF-8") + "=" + URLEncoder.encode(player.Weapon_A, "UTF-8");
			        data += "&" + URLEncoder.encode("lframe", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.Frame_A), "UTF-8");
			        data += "&" + URLEncoder.encode("lsyncPlayerMob", "UTF-8") + "=" + URLEncoder.encode("none", "UTF-8");
			        data += "&" + URLEncoder.encode("lsetUpper", "UTF-8") + "=" + URLEncoder.encode(player.SetUpper_A, "UTF-8");  
			        data += "&" + URLEncoder.encode("lsetBottom", "UTF-8") + "=" + URLEncoder.encode(player.SetBottom_1, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lsetFooter", "UTF-8") + "=" + URLEncoder.encode(player.SetFooter_1, "UTF-8");
			        data += "&" + URLEncoder.encode("lhair", "UTF-8") + "=" + URLEncoder.encode(player.Hair_A, "UTF-8");    
			        data += "&" + URLEncoder.encode("lsex", "UTF-8") + "=" + URLEncoder.encode(player.Sex_A, "UTF-8");  
			        data += "&" + URLEncoder.encode("lcolor", "UTF-8") + "=" + URLEncoder.encode(player.Color_A, "UTF-8");  
			        data += "&" + URLEncoder.encode("lhat", "UTF-8") + "=" + URLEncoder.encode(player.Hat_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lside", "UTF-8") + "=" + URLEncoder.encode(player.Side_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("ljob", "UTF-8") + "=" + URLEncoder.encode(player.Job_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lplayerInBattle", "UTF-8") + "=" + URLEncoder.encode(player.playerInBattle_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lplayerInAttack", "UTF-8") + "=" + URLEncoder.encode(player.playerInAttack_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lplayerInCast", "UTF-8") + "=" + URLEncoder.encode(player.playerInCast_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lplayerSit", "UTF-8") + "=" + URLEncoder.encode(player.playerSit_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lparty", "UTF-8") + "=" + URLEncoder.encode(player.party_A, "UTF-8"); 
			        data += "&" + URLEncoder.encode("lexpshared", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8"); 
			        
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
			        URLConnection conn = url.openConnection();
			        conn.setDoOutput(true);
			        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			        wr.write(data);
			        wr.flush();
			        
			        // Get the response
			        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			        String line;
			        line = "";
			        String retornoSync = "";
			        retornoOnline = "retry";
			        while ((line = rd.readLine()) != null) {
			        	linhaLida = line;   
			        	if (linhaLida.contains("SYSTEMPLAYERS")) {  
			        		retornoSync = linhaLida;
			        		UpdateOnlinePlayers(retornoSync);
			            }	
		    		}	        
			        wr.close();
			        rd.close();
		    
			        return retornoOnline;		        
				}
				
				
				if(tipoRequisicao.equals("Upload")){
					
					FileHandle file = Gdx.files.local("SaveData/save.json");	
					String arq = file.readString();
					
					// Construct data
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.AccountID) + ".txt", "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Upload", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        data += "&" + URLEncoder.encode("ldata", "UTF-8") + "=" + URLEncoder.encode(arq, "UTF-8");
			        
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
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
				        if (linhaLida.contains("Atualizado")) {            	
			        		retornoOnline = "Atualizado";   		
			            }	
				        else {
				        	retornoOnline = "Negado"; 
				        }
		    		}	        
			        wr.close();
			        rd.close();
		    
			        return retornoOnline;		        
				}
				
				if(tipoRequisicao.equals("Download")){
					// Construct data
					
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(subData + ".txt", "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Download", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
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
				        if (linhaLida.contains("Atualizado")) {            	
			        		retornoOnline = "Atualizado";
			        		SaveDataFromServer(linhaLida);
			            }	
				        else {
				        	retornoOnline = "Negado"; 
				        }
		    		}	        
			        wr.close();
			        rd.close();
		    
			        return retornoOnline;		        
				}
				
				if(tipoRequisicao.equals("ExpSharedSend")){
					
					// Construct data
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("ExpSharedSend", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        data += "&" + URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
			        data += "&" + URLEncoder.encode("lexpsend", "UTF-8") + "=" + URLEncoder.encode(subData, "UTF-8");
			            
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
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
				
				
				if(tipoRequisicao.equals("ExpGiver")){
					
					// Construct data
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(player.AccountID, "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("ExpGiver", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        data += "&" + URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(player.Name_A, "UTF-8");
			        data += "&" + URLEncoder.encode("lplayerIDEXP", "UTF-8") + "=" + URLEncoder.encode(player.PlayerExpGet_A, "UTF-8");
			            
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/server/index.php");
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
			        	if (linhaLida.contains("Recuperado")) {            	
			        		retornoOnline = "Atualizado";       		
			            }	
			        	if (linhaLida.contains("SYSTEMEXP")) {  
			        		UpdateExpGet(linhaLida);
			            }	
		    		}	        
			        wr.close();
			        rd.close();
		    
			        return retornoOnline;		        
				}
				
				
				}
				catch(Exception ex) {
					linhaLida = ex.getMessage();
				}
				return linhaLida;
				
				
			}
			
			*/
			
			/*public void SaveDataFromServer(String line) {
				String[] lineSplit = line.split(":");
				file = Gdx.files.local("SaveData/save.json");
				file.writeString(lineSplit[0], false);
			}
			
			public void UpdateExpGet(String line) {
				int expserver = 0;
				String[] lineSplit = line.split(":");
				expserver = Integer.parseInt(lineSplit[4]);
				player.PlayerExpGet_A = lineSplit[6];
				int dv = expserver / 5;
				GiveExp(dv);
				SetSave(charNumber);
				SaveData(player);
			}
			
			public void UpdateListOnlineChats(String line) {
				lstChats.add(line);		
			}
			
			public ArrayList<String> GetChatList() {
				return lstChats;
			}
			
			public ArrayList<Player> GetListOnlinePlayers(){
				return lstOnlinePlayers;
			}
			
			public void UpdateOnlinePlayers(String line) {
				
				String[] lineSplit = line.split(":");
				playerOnline = new Player();
				playerOnline.AccountID = lineSplit[4];
				playerOnline.Name_A = lineSplit[2];
				playerOnline.Level_A = Integer.parseInt(lineSplit[6]);
				playerOnline.Map_A = lineSplit[8];
				playerOnline.Hp_A = Integer.parseInt(lineSplit[10]);
				playerOnline.Mp_A = Integer.parseInt(lineSplit[12]);
				playerOnline.PosX_A = Float.parseFloat(lineSplit[14]);
				playerOnline.PosY_A = Float.parseFloat(lineSplit[16]);
				playerOnline.Walk_A = lineSplit[18];
				playerOnline.Weapon_A = lineSplit[20];
				playerOnline.Frame_A = Integer.parseInt(lineSplit[22]);
				playerOnline.SyncPlayerMob_A = lineSplit[24];
				playerOnline.SetUpper_A = lineSplit[26];
				playerOnline.SetBottom_A = lineSplit[28];
				playerOnline.SetFooter_A = lineSplit[30];
				playerOnline.Hair_A = lineSplit[32];
				playerOnline.Sex_A = lineSplit[34];
				playerOnline.Color_A = lineSplit[36];
				playerOnline.Hat_A = lineSplit[38];
				playerOnline.Side_A = lineSplit[40];
				playerOnline.Job_A = lineSplit[42];
				playerOnline.playerInBattle_A = lineSplit[44];
				playerOnline.playerInAttack_A = lineSplit[46];
				playerOnline.playerInCast_A = lineSplit[48];
				playerOnline.playerSit_A = lineSplit[50];
				playerOnline.party_A = lineSplit[52];
				playerOnline.Exp_A = Integer.parseInt(lineSplit[54]);
				
				if(!player.AccountID.equals(playerOnline.AccountID)) { 
					lstOnlinePlayers.add(playerOnline); 
				}

				Map<String, Player> playersMap = new HashMap<String, Player>();

				for (Player player : lstOnlinePlayers) {
				    playersMap.put(player.getAccountID(), player);
				}

				lstOnlinePlayers.clear();
				lstOnlinePlayers.addAll(playersMap.values());
			}*/
			
			
			
}