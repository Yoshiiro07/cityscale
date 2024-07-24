package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

public class GameControl {
	
	//Manager
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private Player player;
	private int FrameAtkPlayer = 0;
	
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
    private String lservername = "cityserver.mysql.uhserver.com";  //"cityscale.mysql.uhserver.com", "UTF-8"
    private String lusername = "citymaster";
    private String lpassword = "P@titos07";
    private String ldbname = "cityserver";
    private String onlineresponse = "";
    private ArrayList<String> lstChats;
	
	//Texture Atlas
	private TextureAtlas atlas_hairs1;
	private TextureAtlas atlas_hairs2;
	private TextureAtlas atlas_hairs3;
	private TextureAtlas atlas_hairs4;
	private TextureAtlas atlas_basicset;
	private TextureAtlas atlas_ux;
	private TextureAtlas atlas_shops;
	private TextureAtlas atlas_genericset;
	private TextureAtlas atlas_generichair;
	private TextureAtlas atlas_npcs;
	private TextureAtlas atlas_cards;
	private TextureAtlas atlas_mobSewers;
	
	private TextureAtlas atlas_items;
	
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
		

		atlas_genericset = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_ux = new TextureAtlas(Gdx.files.internal("data/assets/ux/ux.txt"));

		atlas_mobSewers = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobsewers.txt"));
		
		atlas_npcs = new TextureAtlas(Gdx.files.internal("data/assets/characters/npcs/npcs.txt"));
		atlas_cards = new TextureAtlas(Gdx.files.internal("data/assets/skills/cards.txt"));
		
		atlas_items = new TextureAtlas(Gdx.files.internal("data/assets/itens/itens/itens.txt"));
		
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
	
	//[Account]//
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");
		
		//Creating a new one
		if (!file.exists()) {
				try {
					Player player = new Player();
					int accNumber = randnumber.nextInt(999999);
					while(accNumber < 10000){
						accNumber = randnumber.nextInt(999999);
					}
					player.AccountID = String.valueOf(accNumber);
					player.Name_1 = "none";
					player.Name_2 = "none";
					player.Name_3 = "none";
					file.writeString(Base64Coder.encodeString(json.prettyPrint(player)), false);
				} 
				catch (Exception e) 
				{
					String test = e.getMessage();
				}
		}
		
		else 
		{
			FileHandle file = Gdx.files.local("SaveData/save.json");		
			player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
		}
	}

	public void SaveData(Player acPlayer) {
		file = Gdx.files.local("SaveData/save.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(acPlayer)), false);
		this.player = acPlayer;
	}

	public Player LoadData(){
		FileHandle file = Gdx.files.local("SaveData/save.json");		
		player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
		return player;
	}
	
	public void DeleteChar(int num) {
		if(num == 1) { player.Name_1 = "none"; SaveData(player); }
		if(num == 2) { player.Name_2 = "none"; SaveData(player); }
		if(num == 3) { player.Name_3 = "none"; SaveData(player); }
	}
	
	public void CreateNewChar(String name, String sex, String hair, String color) {
		boolean created = false;
		player = new Player();
		
		FileHandle file = Gdx.files.local("SaveData/save.json");		
		player = json.fromJson(Player.class, Base64Coder.decodeString(file.readString()));
		
		if(player.Name_1.equals("none") && !created) {
			player.Name_1 = name;
			player.Sex_1 = sex;
			player.Hair_1 = hair;
			player.Color_1 = color;
			player.Hat_1 = "none";
			player.Job_1 = "Aprendiz";
			player.SetUpper_1 = "basictop";
			player.SetBottom_1 = "basicbottom";
			player.SetFooter_1 = "basicfooter";
			player.Level_1 = 1;
			player.Exp_1 = 0;
			player.Map_1 = "MetroStation";
			player.Hp_1 = 30;
			player.Mp_1 = 20;
			player.Money_1 = 50;
			player.HpMax_1 = 30;
			player.MpMax_1 = 20;
			player.regenTime_1 = 6000;
			player.regenTimeMax_1 = 6000;
			player.PosX_1 = 0;
			player.PosY_1 = 0;
			player.Walk_1 = "no";
			player.Frame_1 = 1;
			player.countFrame_1 = 1;
			player.breakwalk_1 = "";
			player.Target_1 = "none";
			player.AtkTimer_1 = 300;
			player.AtkTimerMax_1 = 300;
			player.Casting_1 = "no";
			player.Atk_1 = 5;
			player.Def_1 = 1;
			player.Evasion_1 = 0;
			player.Side_1 =	"front";
			player.Weapon_1 = "basicknife";
			player.Crystal1_1 = "none";
			player.Crystal2_1 = "none";
			player.Crystal3_1 = "none";
			player.Crystal4_1 = "none";
			player.StatusPoint_1 = 0;
			player.Str_1 = 1;
			player.Agi_1 = 1;
			player.Vit_1 = 1;
			player.Dex_1 = 1;
			player.Wis_1 = 1;
			player.Stamina_1 = 100;
			player.StaminaMax_1 = 100;
			player.Quests_1 = "none";
			player.hotkey1_1 = "none";
			player.hotkey2_1 = "none";
			player.buffA_1 = "none";
			player.buffB_1 = "none";
			player.buffC_1 = "none";
			player.BuffTimeA_1 = 0;
			player.BuffTimeB_1 = 0;
			player.BuffTimeC_1 = 0;
			player.party_1 = "none";
			player.playerInBattle_1 = "none";
			player.playerInAttack_1 = "none";
			player.playerInCast_1 = "none";
			player.playerSit_1 = "none";
			player.SyncPlayerMob_1 = "none";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			player.PlayerExpGet_1 = dtf.format(LocalDateTime.now());
			
			String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[hpcan#20]-"; } 
	            if(i > 0) { itensList = itensList + "[NONE]-"; }          
	        }
	        player.Itens_1 = itensList;
	        created = true;
		}
		
		if(player.Name_2.equals("none") && !created) {
			player.Name_2 = name;
			player.Sex_2 = sex;
			player.Hair_2 = hair;
			player.Color_2 = color;
			player.Hat_2 = "none";
			player.Job_2 = "Aprendiz";
			player.SetUpper_2 = "basictop";
			player.SetBottom_2 = "basicbottom";
			player.SetFooter_2 = "basicfooter";
			player.Level_2 = 1;
			player.Exp_2 = 0;
			player.Map_2 = "MetroStation";
			player.Hp_2 = 30;
			player.Mp_2 = 20;
			player.Money_2 = 50;
			player.HpMax_2 = 30;
			player.MpMax_2 = 20;
			player.regenTime_2 = 6000;
			player.regenTimeMax_2 = 6000;
			player.PosX_2 = 0;
			player.PosY_2 = 0;
			player.Walk_2 = "no";
			player.Frame_2 = 1;
			player.countFrame_2 = 1;
			player.breakwalk_2 = "";
			player.Target_2 = "none";
			player.AtkTimer_2 = 300;
			player.AtkTimerMax_2 = 300;
			player.Casting_2 = "no";
			player.Atk_2 = 5;
			player.Def_2 = 1;
			player.Evasion_2 = 0;
			player.Side_2 =	"front";
			player.Weapon_2 = "basicknife";
			player.Crystal1_2 = "none";
			player.Crystal2_2 = "none";
			player.Crystal3_2 = "none";
			player.Crystal4_2 = "none";
			player.StatusPoint_2 = 0;
			player.Str_2 = 1;
			player.Agi_2 = 1;
			player.Vit_2 = 1;
			player.Dex_2 = 1;
			player.Wis_2 = 1;
			player.Stamina_2 = 100;
			player.StaminaMax_2 = 100;
			player.Quests_2 = "none";
			player.hotkey1_2 = "none";
			player.hotkey2_2 = "none";
			player.buffA_2 = "none";
			player.buffB_2 = "none";
			player.buffC_2 = "none";
			player.BuffTimeA_2 = 0;
			player.BuffTimeB_2 = 0;
			player.BuffTimeC_2 = 0;
			player.party_2 = "none";
			player.playerInBattle_2 = "none";
			player.playerInAttack_2 = "none";
			player.playerInCast_2 = "none";
			player.playerSit_2 = "none";
			player.SyncPlayerMob_2 = "none";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			player.PlayerExpGet_2 = dtf.format(LocalDateTime.now());
			
			String itensList = "";
			for(int i = 0; i < 16; i++) {
				if(i == 0) { itensList = itensList + "[hpcan#20]-"; } 
	            if(i > 0) { itensList = itensList + "[NONE]-"; }        
	        }
	        player.Itens_2 = itensList;
	        created = true;
		}
		
		if(player.Name_3.equals("none") && !created) {
			player.Name_3 = name;
			player.Sex_3 = sex;
			player.Hair_3 = hair;
			player.Color_3 = color;
			player.Hat_3 = "none";
			player.Job_3 = "Aprendiz";
			player.SetUpper_3 = "basictop";
			player.SetBottom_3 = "basicbottom";
			player.SetFooter_3 = "basicfooter";
			player.Level_3 = 1;
			player.Exp_3 = 0;
			player.Map_3 = "MetroStation";
			player.Hp_3 = 30;
			player.Mp_3 = 20;
			player.Money_3 = 50;
			player.HpMax_3 = 30;
			player.MpMax_3 = 20;
			player.regenTime_3 = 6000;
			player.regenTimeMax_3 = 6000;
			player.PosX_3 = 0;
			player.PosY_3 = 0;
			player.Walk_3 = "no";
			player.Frame_3 = 1;
			player.countFrame_3 = 1;
			player.breakwalk_3 = "";
			player.Target_3 = "none";
			player.AtkTimer_3 = 300;
			player.AtkTimerMax_3 = 300;
			player.Casting_3 = "no";
			player.Atk_3 = 5;
			player.Def_3 = 1;
			player.Evasion_3 = 0;
			player.Side_3 =	"front";
			player.Weapon_3 = "basicknife";
			player.Crystal1_3 = "none";
			player.Crystal2_3 = "none";
			player.Crystal3_3 = "none";
			player.Crystal4_3 = "none";
			player.StatusPoint_3 = 0;
			player.Str_3 = 1;
			player.Agi_3 = 1;
			player.Vit_3 = 1;
			player.Dex_3 = 1;
			player.Wis_3 = 1;
			player.Stamina_3 = 100;
			player.StaminaMax_3 = 100;
			player.Quests_3 = "none";
			player.hotkey1_3 = "none";
			player.hotkey2_3 = "none";
			player.buffA_3 = "none";
			player.buffB_3 = "none";
			player.buffC_3 = "none";
			player.BuffTimeA_3 = 0;
			player.BuffTimeB_3 = 0;
			player.BuffTimeC_3 = 0;
			player.party_3 = "none";
			player.playerInBattle_3 = "none";
			player.playerInAttack_3 = "none";
			player.playerInCast_3 = "none";
			player.playerSit_3 = "none";
			player.SyncPlayerMob_3 = "none";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			player.PlayerExpGet_3 = dtf.format(LocalDateTime.now());
			
			String itensList = "";
			for(int i = 0; i < 16; i++) {
				if(i == 0) { itensList = itensList + "[hpcan#20]-"; } 
	            if(i > 0) { itensList = itensList + "[NONE]-"; }          
	        }
	        player.Itens_3 = itensList;
	        created = true;
		}
		
		SaveData(player);		
	}

	public Player SendPlayer(){
		return this.player;
	}

	public void UpdatePlayer(Player viewplayer){
		this.player = viewplayer;
	}
	
	

	public void SetCharacter(int charnum) {
		if(charnum == 1) {
			player.Name_A = player.Name_1;
			player.Sex_A = player.Sex_1;
			player.Hair_A = player.Hair_1;
			player.Color_A = player.Color_1;
			player.Hat_A = player.Hat_1;
			player.Job_A = player.Job_1;
			player.SetUpper_A = player.SetUpper_1;
			player.SetBottom_A = player.SetBottom_1;
			player.SetFooter_A = player.SetFooter_1;
			player.Level_A = player.Level_1;
			player.Exp_A = player.Exp_1;
			player.Map_A = player.Map_1;
			player.Hp_A = player.Hp_1;
			player.Mp_A = player.Mp_1;
			player.Money_A = player.Money_1;
			player.HpMax_A = player.HpMax_1;
			player.MpMax_A = player.MpMax_1;
			player.regenTime_A = player.regenTime_1;
			player.regenTimeMax_A = player.regenTimeMax_1;
			player.PosX_A = player.PosX_1;
			player.PosY_A = player.PosY_1;
			player.Walk_A = player.Walk_1;
			player.Frame_A = player.Frame_1;
			player.countFrame_A = player.countFrame_1;
			player.breakwalk_A = player.breakwalk_1;
			player.Target_A = player.Target_1;
			player.AtkTimer_A = player.AtkTimer_1;
			player.AtkTimerMax_A = player.AtkTimerMax_1;
			player.Casting_A = player.Casting_1;
			player.Atk_A = player.Atk_1;
			player.Def_A = player.Def_1;
			player.Evasion_A = player.Evasion_1;
			player.Side_A =	player.Side_1;
			player.Weapon_A = player.Weapon_1;
			player.Crystal1_A = player.Crystal1_1;
			player.Crystal2_A = player.Crystal2_1;
			player.Crystal3_A = player.Crystal3_1;
			player.Crystal4_A = player.Crystal4_1;
			player.StatusPoint_A = player.StatusPoint_1;
			player.Str_A = player.Str_1;
			player.Agi_A = player.Agi_1;
			player.Vit_A = player.Vit_1;
			player.Dex_A = player.Dex_1;
			player.Wis_A = player.Wis_1;
			player.Stamina_A = player.Stamina_1;
			player.StaminaMax_A = player.StaminaMax_1;
			player.Quests_A = player.Quests_1;
			player.hotkey1_A = player.hotkey1_1;
			player.hotkey2_A = player.hotkey2_1;
			player.buffA_A = player.buffA_1;
			player.buffB_A = player.buffB_1;
			player.buffC_A = player.buffC_1;
			player.BuffTimeA_A = player.BuffTimeA_1;
			player.BuffTimeB_A = player.BuffTimeB_1;
			player.BuffTimeC_A = player.BuffTimeC_1;
			player.party_A = player.party_1;
			player.playerInBattle_A = player.playerInBattle_1;
			player.playerInAttack_A = player.playerInAttack_1;
			player.playerInCast_A = player.playerInCast_1;
			player.playerSit_A = player.playerSit_1;
			player.Itens_A = player.Itens_1;
			player.PlayerExpGet_A = player.PlayerExpGet_1;
		}

		if(charnum == 2) {
			player.Name_A = player.Name_2;
			player.Sex_A = player.Sex_2;
			player.Hair_A = player.Hair_2;
			player.Color_A = player.Color_2;
			player.Hat_A = player.Hat_2;
			player.Job_A = player.Job_2;
			player.SetUpper_A = player.SetUpper_2;
			player.SetBottom_A = player.SetBottom_2;
			player.SetFooter_A = player.SetFooter_2;
			player.Level_A = player.Level_2;
			player.Exp_A = player.Exp_2;
			player.Map_A = player.Map_2;
			player.Hp_A = player.Hp_2;
			player.Mp_A = player.Mp_2;
			player.Money_A = player.Money_2;
			player.HpMax_A = player.HpMax_2;
			player.MpMax_A = player.MpMax_2;
			player.regenTime_A = player.regenTime_2;
			player.regenTimeMax_A = player.regenTimeMax_2;
			player.PosX_A = player.PosX_2;
			player.PosY_A = player.PosY_2;
			player.Walk_A = player.Walk_2;
			player.Frame_A = player.Frame_2;
			player.countFrame_A = player.countFrame_2;
			player.breakwalk_A = player.breakwalk_2;
			player.Target_A = player.Target_2;
			player.AtkTimer_A = player.AtkTimer_2;
			player.AtkTimerMax_A = player.AtkTimerMax_2;
			player.Casting_A = player.Casting_2;
			player.Atk_A = player.Atk_2;
			player.Def_A = player.Def_2;
			player.Evasion_A = player.Evasion_2;
			player.Side_A = player.Side_2;
			player.Weapon_A = player.Weapon_2;
			player.Crystal1_A = player.Crystal1_2;
			player.Crystal2_A = player.Crystal2_2;
			player.Crystal3_A = player.Crystal3_2;
			player.Crystal4_A = player.Crystal4_2;
			player.StatusPoint_A = player.StatusPoint_2;
			player.Str_A = player.Str_2;
			player.Agi_A = player.Agi_2;
			player.Vit_A = player.Vit_2;
			player.Dex_A = player.Dex_2;
			player.Wis_A = player.Wis_2;
			player.Stamina_A = player.Stamina_2;
			player.StaminaMax_A = player.StaminaMax_2;
			player.Quests_A = player.Quests_2;
			player.hotkey1_A = player.hotkey1_2;
			player.hotkey2_A = player.hotkey2_2;
			player.buffA_A = player.buffA_2;
			player.buffB_A = player.buffB_2;
			player.buffC_A = player.buffC_2;
			player.BuffTimeA_A = player.BuffTimeA_2;
			player.BuffTimeB_A = player.BuffTimeB_2;
			player.BuffTimeC_A = player.BuffTimeC_2;
			player.party_A = player.party_2;
			player.playerInBattle_A = player.playerInBattle_2;
			player.playerInAttack_A = player.playerInAttack_2;
			player.playerInCast_A = player.playerInCast_2;
			player.playerSit_A = player.playerSit_2;
			player.Itens_A = player.Itens_2;
			player.PlayerExpGet_A = player.PlayerExpGet_2;
		}

		if(charnum == 3) {
			player.Name_A = player.Name_3;
			player.Sex_A = player.Sex_3;
			player.Hair_A = player.Hair_3;
			player.Color_A = player.Color_3;
			player.Hat_A = player.Hat_3;
			player.Job_A = player.Job_3;
			player.SetUpper_A = player.SetUpper_3;
			player.SetBottom_A = player.SetBottom_3;
			player.SetFooter_A = player.SetFooter_3;
			player.Level_A = player.Level_3;
			player.Exp_A = player.Exp_3;
			player.Map_A = player.Map_3;
			player.Hp_A = player.Hp_3;
			player.Mp_A = player.Mp_3;
			player.Money_A = player.Money_3;
			player.HpMax_A = player.HpMax_3;
			player.MpMax_A = player.MpMax_3;
			player.regenTime_A = player.regenTime_3;
			player.regenTimeMax_A = player.regenTimeMax_3;
			player.PosX_A = player.PosX_3;
			player.PosY_A = player.PosY_3;
			player.Walk_A = player.Walk_3;
			player.Frame_A = player.Frame_3;
			player.countFrame_A = player.countFrame_3;
			player.breakwalk_A = player.breakwalk_3;
			player.Target_A = player.Target_3;
			player.AtkTimer_A = player.AtkTimer_3;
			player.AtkTimerMax_A = player.AtkTimerMax_3;
			player.Casting_A = player.Casting_3;
			player.Atk_A = player.Atk_3;
			player.Def_A = player.Def_3;
			player.Evasion_A = player.Evasion_3;
			player.Side_A = player.Side_3;
			player.Weapon_A = player.Weapon_3;
			player.Crystal1_A = player.Crystal1_3;
			player.Crystal2_A = player.Crystal2_3;
			player.Crystal3_A = player.Crystal3_3;
			player.Crystal4_A = player.Crystal4_3;
			player.StatusPoint_A = player.StatusPoint_3;
			player.Str_A = player.Str_3;
			player.Agi_A = player.Agi_3;
			player.Vit_A = player.Vit_3;
			player.Dex_A = player.Dex_3;
			player.Wis_A = player.Wis_3;
			player.Stamina_A = player.Stamina_3;
			player.StaminaMax_A = player.StaminaMax_3;
			player.Quests_A = player.Quests_3;
			player.hotkey1_A = player.hotkey1_3;
			player.hotkey2_A = player.hotkey2_3;
			player.buffA_A = player.buffA_3;
			player.buffB_A = player.buffB_3;
			player.buffC_A = player.buffC_3;
			player.BuffTimeA_A = player.BuffTimeA_3;
			player.BuffTimeB_A = player.BuffTimeB_3;
			player.BuffTimeC_A = player.BuffTimeC_3;
			player.party_A = player.party_3;
			player.playerInBattle_A = player.playerInBattle_3;
			player.playerInAttack_A = player.playerInAttack_3;
			player.playerInCast_A = player.playerInCast_3;
			player.playerSit_A = player.playerSit_3;
			player.Itens_A = player.Itens_3;
			player.PlayerExpGet_A = player.PlayerExpGet_3;
		}	
	}

	//[Interface]//
	public Sprite GetUXSmall(String element) {
		if(element.equals("bannerselect")) {
			spr_master = atlas_ux.createSprite("bannerselect");
			spr_master.setPosition(-60, 50);
			spr_master.setSize(50,10);
			return spr_master;
		}
		if(element.equals("bannercreate")) {
			spr_master = atlas_ux.createSprite("bannercreate");
			spr_master.setPosition(-60, 50);
			spr_master.setSize(50,10);
			return spr_master;
		}
		if(element.equals("create")) {
			spr_master = atlas_ux.createSprite("create");
			spr_master.setPosition(-60, -60);
			spr_master.setSize(120,120);
			return spr_master;
		}
		if(element.equals("confirmtab")) {
			spr_master = atlas_ux.createSprite("confirmtab");
			spr_master.setPosition(15, 10);
			spr_master.setSize(50,50);
			return spr_master;
		}
		if(element.equals("bannerdelete")) {
			spr_master = atlas_ux.createSprite("bannerdelete");
			spr_master.setPosition(-60, 50);
			spr_master.setSize(50,10);
			return spr_master;
		}
		if(element.equals("btnvoltar")) {
			spr_master = atlas_ux.createSprite("btnback");
			spr_master.setPosition(40, -60);
			spr_master.setSize(20,10);
			return spr_master;
		}
		if(element.equals("btncreatenew")) {
			spr_master = atlas_ux.createSprite("btncreatenew");
			spr_master.setPosition(-60, -60);
			spr_master.setSize(20,10);
			return spr_master;
		}
		if(element.equals("btnexclude")) {
			spr_master = atlas_ux.createSprite("btnexclude");
			spr_master.setPosition(40, -60);
			spr_master.setSize(20,10);
			return spr_master;
		}
		if(element.equals("playertag")) {
			spr_master = atlas_ux.createSprite("playertag");
			spr_master.setPosition(-69, 36);
			spr_master.setSize(42,32);
			return spr_master;
		}
		if(element.equals("innerpad")) {
			spr_master = atlas_ux.createSprite("innerpad");
			spr_master.setSize(10,20);
			return spr_master;
		}
		
		if(element.equals("textbar")){
			spr_master = atlas_ux.createSprite("textbar");
		}
		
		return spr_master;
	}
	
	public Sprite GetUX(String element, float cameraCoordsX, float cameraCoordsY) {
		if(element.equals("playertag")) {
			spr_master = atlas_ux.createSprite("playertag");
			spr_master.setPosition(cameraCoordsX -99,cameraCoordsY + 56);
			spr_master.setSize(52,42);
			return spr_master;
		}
		if(element.equals("innerpad")) {
			spr_master = atlas_ux.createSprite("innerpad");
			spr_master.setSize(20,35);
			return spr_master;
		}
		if(element.equals("battlezoneA")) {
			spr_master = atlas_ux.createSprite("battlezoneA");
			spr_master.setPosition(cameraCoordsX -48,cameraCoordsY - 45);
			spr_master.setSize(90,100);
			return spr_master;
		}
		if(element.equals("target")) {
			spr_master = atlas_ux.createSprite("target");
			return spr_master;
		}
		if(element.equals("menu")){
			spr_master = atlas_ux.createSprite("inventory");
			spr_master.setPosition(cameraCoordsX -85,cameraCoordsY -80);
			spr_master.setSize(170,170);
		}
		if(element.equals("textbar")){
			spr_master = atlas_ux.createSprite("textbar");
		}
		
		return spr_master;
	}
	
	public Sprite GetShops(String shopname, float cameraCoordsX, float cameraCoordsY) {
		if(shopname.equals("refrishop")){
			spr_master = atlas_shops.createSprite("lojamaquina");
			spr_master.setPosition(cameraCoordsX -65,cameraCoordsY -80);
			spr_master.setSize(130,160);
		}
		
		return spr_master;
	}
	
	public Sprite GetCard(String cardname) {
		if(cardname.equals("cardempty")) {
			spr_master = atlas_cards.createSprite("cardempty");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		
		if(cardname.equals("cardhp")) {
			spr_master = atlas_cards.createSprite("cardhp");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardaction")) {
			spr_master = atlas_cards.createSprite("cardaction");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		if(cardname.equals("cardactionON")) {
			spr_master = atlas_cards.createSprite("cardactionON");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		if(cardname.equals("cardblock")) {
			spr_master = atlas_cards.createSprite("cardblock");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		if(cardname.equals("cardtarget")) {
			spr_master = atlas_cards.createSprite("cardtarget");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardblock")) {
			spr_master = atlas_cards.createSprite("cardblock");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardsit")) {
			spr_master = atlas_cards.createSprite("cardsit");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardcutbreak")) {
			spr_master = atlas_cards.createSprite("cardcutbreak");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardrockbound")) {
			spr_master = atlas_cards.createSprite("cardrockbound");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		return spr_master;
	}
	
	
	
	//[Character]//
	public Sprite MenuCreateSprite(String sex, String type) {
		if(sex.equals("M")) {
			if(type.equals("upper")) { 
				spr_master = atlas_basicset.createSprite("basictopM_front1");
				spr_master.setPosition(-67, -8);
				spr_master.setScale(-0.3f,0.5f);
				return spr_master;
			}
			if(type.equals("bottom")) { 
				spr_master = atlas_basicset.createSprite("basicbottomM_front1");
				spr_master.setPosition(-67, -20);
				spr_master.setScale(-0.3f,0.5f);
				return spr_master;
			}
			if(type.equals("footer")) { 
				spr_master = atlas_basicset.createSprite("basicfooterM_front1");
				spr_master.setPosition(-67.2f, -27f);
				spr_master.setScale(-0.3f,0.5f);
				return spr_master;
			}		
		}
		
		if(sex.equals("F")) {
			if(type.equals("upper")) { 
				spr_master = atlas_basicset.createSprite("basictopF_front1");
				spr_master.setPosition(-67, -8);
				spr_master.setScale(-0.3f,0.5f);
				return spr_master;
			}
			if(type.equals("bottom")) { 
				spr_master = atlas_basicset.createSprite("basicbottomF_front1");
				spr_master.setPosition(-67, -20);
				spr_master.setScale(-0.3f,0.5f);
				return spr_master;
			}
			if(type.equals("footer")) { 
				spr_master = atlas_basicset.createSprite("basicfooterF_front1");
				spr_master.setPosition(-67.5f, -31f);
				spr_master.setScale(-0.3f,0.5f);
				return spr_master;
			}		
		}
		
		return spr_master;
	}
	
	public Sprite MenuHairCreateSprite(String sex, String hair,String color, int num) {  
		if(hair.equals("hair1")) {  spr_master = atlas_hairs1.createSprite(hair + "_front_" + color + "_" + sex); }
		if(hair.equals("hair2")) {  spr_master = atlas_hairs2.createSprite(hair + "_front_" + color + "_" + sex); }
		if(hair.equals("hair3")) {  spr_master = atlas_hairs3.createSprite(hair + "_front_" + color + "_" + sex); }
		if(hair.equals("hair4")) {  spr_master = atlas_hairs4.createSprite(hair + "_front_" + color + "_" + sex); }
				
		spr_master.setScale(-0.3f,0.5f);
		if(num == 1) {spr_master.setPosition(-59, -18);}
		if(num == 2) {spr_master.setPosition(-20, -18);}
		if(num == 3) {spr_master.setPosition(15, -18);}
		if(num == 99) {spr_master.setPosition(-62, 10);} //Block hair
		return spr_master;
	}
	
	public Sprite MenuHairsSelect(int num, String sex, String color) {
		if(sex.equals("M")) {
			if(num == 1) { spr_master = atlas_hairs1.createSprite("hair1_front_" + color + "_" + sex); spr_master.setPosition(-43, -21); spr_master.setScale(-0.3f,0.5f);  }
			if(num == 2) { spr_master = atlas_hairs2.createSprite("hair2_front_" + color + "_" + sex); spr_master.setPosition(-33, -21); spr_master.setScale(-0.3f,0.5f);  }
			if(num == 3) { spr_master = atlas_hairs3.createSprite("hair3_front_" + color + "_" + sex); spr_master.setPosition(-23.7f, -21); spr_master.setScale(-0.3f,0.5f);  }
			if(num == 4) { spr_master = atlas_hairs4.createSprite("hair4_front_" + color + "_" + sex); spr_master.setPosition(-13.9f, -21); spr_master.setScale(-0.3f,0.5f);  }
		}
		if(sex.equals("F")) {
			if(num == 1) { spr_master = atlas_hairs1.createSprite("hair1_front_" + color + "_" + sex); spr_master.setPosition(-43, -21); spr_master.setScale(-0.3f,0.5f);  }
			if(num == 2) { spr_master = atlas_hairs2.createSprite("hair2_front_" + color + "_" + sex); spr_master.setPosition(-33, -21); spr_master.setScale(-0.3f,0.5f);  }
			if(num == 3) { spr_master = atlas_hairs3.createSprite("hair3_front_" + color + "_" + sex); spr_master.setPosition(-23.7f, -21); spr_master.setScale(-0.3f,0.5f);  }
			if(num == 4) { spr_master = atlas_hairs4.createSprite("hair4_front_" + color + "_" + sex); spr_master.setPosition(-13.9f, -21); spr_master.setScale(-0.3f,0.5f);  }
		}	
		return spr_master;	
	}
	
	public Sprite SelectShowCharacterSprite(Player player, String type, int num) {
		//basictopM_front1
		if(type.equals("upper")) {
			if(num == 1) {
				if(player.SetUpper_1.equals("basictop")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetUpper_1 + player.Sex_1 + "_front1");
				if(player.Sex_1.equals("M")) { spr_master.setPosition(-64, -36); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex_1.equals("F")) { spr_master.setPosition(-64, -36); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;
			}
			if(num == 2) {
				if(player.SetUpper_2.equals("basictop")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetUpper_2 + player.Sex_2 + "_front1");
				if(player.Sex_2.equals("M")) { spr_master.setPosition(-25, -36); spr_master.setScale(-0.3f,0.5f);  }
				if(player.Sex_2.equals("F")) { spr_master.setPosition(-25, -36); spr_master.setScale(-0.3f,0.5f);  }
				return spr_master;
			}
			if(num == 3) {
				if(player.SetUpper_3.equals("basictop")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetUpper_3 + player.Sex_3 + "_front1");
				if(player.Sex_3.equals("M")) { spr_master.setPosition(10, -36); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex_3.equals("F")) { spr_master.setPosition(10, -36); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;
			}
		}
		
		
		//basicbottomM_front1
		if(type.equals("bottom")) { 
			if(num == 1) {
				if(player.SetUpper_1.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetBottom_1 + player.Sex_1 + "_front1");
				if(player.Sex_1.equals("M")) { spr_master.setPosition(-64, -48); spr_master.setScale(-0.3f,0.5f);}
				if(player.Sex_1.equals("F")) { spr_master.setPosition(-64, -48); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 2) {
				if(player.SetUpper_2.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetBottom_2 + player.Sex_2 + "_front1");
				if(player.Sex_2.equals("M")) { spr_master.setPosition(-25f, -48); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex_2.equals("F")) { spr_master.setPosition(-25f, -48); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 3) {
				if(player.SetUpper_3.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetBottom_3 + player.Sex_3 + "_front1");
				if(player.Sex_3.equals("M")) { spr_master.setPosition(10, -48); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex_3.equals("F")) { spr_master.setPosition(10, -48); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
		}
		
		
		//basicfooterM_front1
		if(type.equals("footer")) { 
			if(num == 1) {
				if(player.SetUpper_1.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetFooter_1 + player.Sex_1 + "_front1");
				if(player.Sex_1.equals("M")) { spr_master.setPosition(-64, -55); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex_1.equals("F")) { spr_master.setPosition(-64.5f, -60); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 2) {
				if(player.SetUpper_2.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetFooter_2 + player.Sex_2 + "_front1");
				if(player.Sex_2.equals("M")) { spr_master.setPosition(-25f, -55); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex_2.equals("F")) { spr_master.setPosition(-25.5f, -60); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 3) {
				if(player.SetUpper_3.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetFooter_3 + player.Sex_3 + "_front1");
				if(player.Sex_3.equals("M")) { spr_master.setPosition(10, -55); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex_3.equals("F")) { spr_master.setPosition(10, -60); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
		}		
		return spr_master;
	}
	
	public Sprite GetHairCharTagStation(Player player) {
		
		
		//hair1_front_green_M
		if(player.Hair_A.equals("hair1")){ spr_master = atlas_hairs1.createSprite(player.Hair_A + "_" + "front" + "_" + player.Color_A + "_" + player.Sex_A);}
		if(player.Hair_A.equals("hair2")){ spr_master = atlas_hairs2.createSprite(player.Hair_A + "_" + "front" + "_" + player.Color_A + "_" + player.Sex_A);}
		if(player.Hair_A.equals("hair3")){ spr_master = atlas_hairs3.createSprite(player.Hair_A + "_" + "front" + "_" + player.Color_A + "_" + player.Sex_A);}
		if(player.Hair_A.equals("hair4")){ spr_master = atlas_hairs4.createSprite(player.Hair_A + "_" + "front" + "_" + player.Color_A + "_" + player.Sex_A);}
		
		if(player.Sex_A.equals("M")) {
			spr_master.setPosition(-86, 42); 
			spr_master.setScale(0.2f,0.4f);		
		}
		if(player.Sex_A.equals("F")) {
			spr_master.setPosition(-86, 42); 
			spr_master.setScale(0.2f,0.4f);		
		}
		
		return spr_master;
	}

	public Sprite GetHairCharTag(Player player,float cameraCoordsX,float cameraCoordsY) {
		
		if(player.Hair_A.equals("hair1")) { atlas_generichair = atlas_hairs1;}
		if(player.Hair_A.equals("hair2")) { atlas_generichair = atlas_hairs2;}
		if(player.Hair_A.equals("hair3")) { atlas_generichair = atlas_hairs3;}
		if(player.Hair_A.equals("hair4")) { atlas_generichair = atlas_hairs4;}
		//if(player.Hair_A.equals("hair5")) { atlas_generichair = atlas_hairs5;}
		
		//hair1_front_green_M
		if(player.Sex_A.equals("M")) {
			spr_master = atlas_generichair.createSprite(player.Hair_A + "_" + "front" + "_" + player.Color_A + "_" + player.Sex_A);
			spr_master.setPosition(cameraCoordsX -115,cameraCoordsY + 71); 
			spr_master.setScale(0.2f,0.4f);		
		}
		if(player.Sex_A.equals("F")) {
			spr_master = atlas_generichair.createSprite(player.Hair_A + "_" + "front" + "_" + player.Color_A + "_" + player.Sex_A);
			spr_master.setPosition(cameraCoordsX -115,cameraCoordsY + 71); 
			spr_master.setScale(0.2f,0.4f);		
		}
		
		return spr_master;
	}
	
	
	//[Char movement]//
	public void SetAttackFrame() {
		FrameAtkPlayer = 30;
	}
	
	public Player SetCharMov(Player playerUse, String type) {			
		//Check MovePosition
		if(playerUse.Walk_A.equals("walk")) {
			if(playerUse.Side_A.equals("front") && !player.breakwalk_A.equals("front")) { playerUse.PosY_A = playerUse.PosY_A - 0.5f; }
			if(playerUse.Side_A.equals("back") && !player.breakwalk_A.equals("back")) { playerUse.PosY_A = playerUse.PosY_A + 0.5f; }
			if(playerUse.Side_A.equals("left") && !player.breakwalk_A.equals("left")) { playerUse.PosX_A = playerUse.PosX_A - 0.5f; }
			if(playerUse.Side_A.equals("right") && !player.breakwalk_A.equals("right")) { playerUse.PosX_A = playerUse.PosX_A + 0.5f; }
		}
		
		//Check Frames
		if(!playerUse.Walk_A.equals("no")) { player.countFrame_A = player.countFrame_A + 1; }
		if(playerUse.playerInBattle_A.equals("yes")) { player.countFrame_A = player.countFrame_A + 1; }
		if(playerUse.Walk_A.equals("no")) { playerUse.Frame_A = 1; }
		if(player.countFrame_A > 1 && player.countFrame_A <= 10) { playerUse.Frame_A = 2; }
		if(player.countFrame_A >= 10 && player.countFrame_A <= 20) { playerUse.Frame_A = 1; }
		if(player.countFrame_A >= 20 && player.countFrame_A <= 30) { playerUse.Frame_A = 3; }
		if(player.countFrame_A >= 30 && player.countFrame_A <= 40) { playerUse.Frame_A = 1; }
		if(player.countFrame_A >= 40) { player.countFrame_A = 1; }
				
		return player;
	}
	
	public Sprite GetHairChar(Player player, String menu, float cameraX, float cameraY) {
		
		//hair1_front_green_M
		float posX = player.PosX_A;
		float posY = player.PosY_A;
		if(player.Sex_A.equals("M")) {
			if(player.Hair_A.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			
			if(player.Side_A.equals("front")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side_A.equals("left")) {  spr_master.setPosition(posX -21, posY + 10); }
			if(player.Side_A.equals("right")) { spr_master.setPosition(posX -19.5f, posY + 10);  }
			spr_master.setScale(0.2f,0.4f);
		}
		if(player.Sex_A.equals("F")) {
			if(player.Hair_A.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			
			if(player.Side_A.equals("front")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side_A.equals("left")) {  spr_master.setPosition(posX -20.7f, posY + 8.8f); }
			if(player.Side_A.equals("right")) { spr_master.setPosition(posX -19.5f, posY + 8.8f);  }
			spr_master.setScale(0.2f,0.4f);
		}

		if(menu.equals("yes")){
			player.Side_A = "front";
			if(player.Hair_A.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			
			if(player.Sex_A.equals("M")) {
				spr_master.setPosition(posX -20, posY + 10);
				spr_master.setScale(0.2f,0.4f);
			}
			if(player.Sex_A.equals("F")) {
				spr_master.setPosition(posX -20, posY + 10);
				spr_master.setScale(0.2f,0.4f);
			}
		}

		if(menu.equals("Show")){
			player.Side_A = "front";
			if(player.Hair_A.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair_A + "_" + player.Side_A + "_" + player.Color_A + "_" + player.Sex_A); }
			
			if(player.Sex_A.equals("M")) {
				spr_master.setPosition(cameraX -84.4f, cameraY + 34.8f);
				spr_master.setScale(0.4f,0.7f);
			}
			if(player.Sex_A.equals("F")) {
				spr_master.setPosition(cameraX -84.4f, cameraY + 34.8f);
				spr_master.setScale(0.4f,0.7f);
			}
		}

		if(player.playerInBattle_A.equals("yes")){
			if(player.Hair_A.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair_A + "_attack_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair_A + "_attack_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair_A + "_attack_" + player.Color_A + "_" + player.Sex_A); }
			if(player.Hair_A.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair_A + "_attack_" + player.Color_A + "_" + player.Sex_A); }
			
			spr_master.setPosition(posX -20, posY + 10);
			spr_master.setScale(0.2f,0.4f);
		}
		
		return spr_master;
	}
	
	public Sprite GetTopChar(Player player , String menu, float cameraX, float cameraY) {
		//Top 1
		if(player.SetUpper_A.equals("basictop")) { atlas_genericset = atlas_basicset; }
		
		float posX = player.PosX_A;
		float posY = player.PosY_A;
		//[MALE]
		if(player.Sex_A.equals("M")) {
			spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_" + player.Side_A + player.Frame_A);
			if(player.Side_A.equals("front")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side_A.equals("left")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side_A.equals("right")) { spr_master.setPosition(posX -25,posY -5);  }
			spr_master.setScale(0.2f,0.4f);	
			
			if(player.playerInBattle_A.equals("yes")){
				if(player.Frame_A == 3) { player.Frame_A = 2; }
				spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_attack" + player.Frame_A);
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);							
			}
			
			if(player.playerInAttack_A.equals("yes")){
				if(FrameAtkPlayer > 0) {
				if(player.Frame_A == 3) { player.Frame_A = 1; }
					spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_attack4");
					spr_master.setPosition(posX -25,posY -5);
					spr_master.setScale(0.2f,0.4f);	
					FrameAtkPlayer--;
				}
				if(FrameAtkPlayer <= 0) {
					FrameAtkPlayer = 0;
					player.playerInAttack_A = "no";
				}
			}
			
			//basictopM_sit
			if(player.playerSit_A.equals("yes")) {
				player.Frame_A = 1;
				player.Side_A = "front";
				spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_sit");
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		//[FEMALE]//
		if(player.Sex_A.equals("F")) {
			spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_" + player.Side_A + player.Frame_A);
			if(player.Side_A.equals("front")) { spr_master.setPosition(posX -25,posY -4.5f);  }
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side_A.equals("left")) { 
				spr_master.setPosition(posX -25,posY -5);  
				if(player.Frame_A == 2) { spr_master.setPosition(posX -25,posY -4.5f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -26,posY -5.2f);   }
			}
			if(player.Side_A.equals("right")) { spr_master.setPosition(posX -25,posY -5);  }
			spr_master.setScale(0.2f,0.35f);	
			
			if(player.playerInBattle_A.equals("yes")){
				if(player.Frame_A == 3) { player.Frame_A = 2; }
				spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_attack" + player.Frame_A);
				spr_master.setPosition(posX -25,posY -4); 
				spr_master.setScale(0.2f,0.4f);
			}
			
			//basictopM_sit
			if(player.playerSit_A.equals("yes")) {
				player.Frame_A = 1;
				player.Side_A = "front";
				spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_sit");
				spr_master.setPosition(posX -25.2f,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		if(menu.equals("yes")){
			player.Side_A = "front";
			spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_" + player.Side_A + 1);
			if(player.Side_A.equals("front")) { 
				spr_master.setPosition(cameraX + 8, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side_A = "front";
			spr_master = atlas_genericset.createSprite(player.SetUpper_A + player.Sex_A + "_" + player.Side_A + 1);
			if(player.Side_A.equals("front")) { 
				spr_master.setPosition(cameraX -89, cameraY + 11.5f);
				spr_master.setScale(0.4f,0.7f);
			}
		}

		return spr_master;
	}
	/// [BOTTOM ] //////
	public Sprite GetBottomChar(Player player , String menu, float cameraX, float cameraY) {
		//Top 1
		if(player.SetBottom_A.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
		
		float posX = player.PosX_A;
		float posY = player.PosY_A;
		
		/// [BOTTOM MALE] //////
		if(player.Sex_A.equals("M")) {
			spr_master = atlas_basicset.createSprite(player.SetBottom_A + player.Sex_A + "_" + player.Side_A + player.Frame_A);
			if(player.Side_A.equals("front")) { spr_master.setPosition(posX -25,posY -15); }
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -25,posY -14.6f); }
			if(player.Side_A.equals("right")) { spr_master.setPosition(posX -25,posY -15); }
			if(player.Side_A.equals("left")) { 	spr_master.setPosition(posX -25,posY -15); }	
			spr_master.setScale(0.2f,0.4f);

			if(player.playerInBattle_A.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetBottom_A + player.Sex_A + "_attack1");
				spr_master.setPosition(posX -24.5f,posY -15);
				spr_master.setScale(0.2f,0.4f);					
			}
			
			//basictopM_sit
			if(player.playerSit_A.equals("yes")) {
				player.Frame_A = 1;
				player.Side_A = "front";
				spr_master = atlas_genericset.createSprite(player.SetBottom_A + player.Sex_A + "_sit");
				spr_master.setPosition(posX -25,posY -15);
				spr_master.setScale(0.2f,0.4f);	
			}
		}
		
		/// [BOTTOM FEMALE] //////
		if(player.Sex_A.equals("F")) {
			spr_master = atlas_basicset.createSprite(player.SetBottom_A + player.Sex_A + "_" + player.Side_A + player.Frame_A);
			if(player.Side_A.equals("front")) { spr_master.setPosition(posX -25.2f,posY -13f); }
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -25,posY -14.6f); }
			if(player.Side_A.equals("right")) { 
				spr_master.setPosition(posX -25f,posY -13);
				if(player.Frame_A == 2) { spr_master.setPosition(posX -25f,posY -13f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -25.5f,posY -14f);   }
			}
			if(player.Side_A.equals("left")) { 	
				spr_master.setPosition(posX -25f,posY -13);
				if(player.Frame_A == 2) { spr_master.setPosition(posX -24.6f,posY -13f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -24.8f,posY -14f);   }
			}
			
			if(player.playerInBattle_A.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetBottom_A + player.Sex_A + "_attack1");
				spr_master.setPosition(posX -25.2f,posY -13f);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			//basictopM_sit
			if(player.playerSit_A.equals("yes")) {
				player.Frame_A = 1;
				player.Side_A = "front";
				spr_master = atlas_genericset.createSprite(player.SetBottom_A + player.Sex_A + "_sit");
				spr_master.setPosition(posX -25.2f,posY -12);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			spr_master.setScale(0.2f,0.4f);
		}

		if(menu.equals("yes")){
			player.Side_A = "front";
			spr_master = atlas_basicset.createSprite(player.SetBottom_A + player.Sex_A + "_" + player.Side_A + 1);
			if(player.Side_A.equals("front")) { 
				spr_master.setPosition(cameraX - 5.5f, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side_A = "front";
			spr_master = atlas_basicset.createSprite(player.SetBottom_A + player.Sex_A + "_" + player.Side_A + 1);
			if(player.Sex_A.equals("M")) { 
				spr_master.setPosition(cameraX -89, cameraY - 5);
				spr_master.setScale(0.4f,0.7f);
			}
			if(player.Sex_A.equals("F")) { 
				spr_master.setPosition(cameraX -89, cameraY - 5);
				spr_master.setScale(0.4f,0.7f);
			}
		}
		
		return spr_master;
	}
	
	/// [FOOTER ] //////
	public Sprite GetFooterChar(Player player , String menu, float cameraX, float cameraY) {
		//Top 1
		if(player.SetFooter_A.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
		
		float posX = player.PosX_A;
		float posY = player.PosY_A;
		/// [FOOTER MALE ] //////
		if(player.Sex_A.equals("M")) {
			spr_master = atlas_basicset.createSprite(player.SetFooter_A + player.Sex_A + "_" + player.Side_A + player.Frame_A);
			if(player.Side_A.equals("front")) { spr_master.setPosition(posX -25,posY -21); }
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -25,posY -21); }	
			if(player.Side_A.equals("left")) { 
				spr_master.setPosition(posX -25.4f,posY -19.5f); 
				if(player.Frame_A == 2) { spr_master.setPosition(posX -24.8f,posY -18f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -24.8f,posY -18f);   }
			}	
			if(player.Side_A.equals("right")) { 
				spr_master.setPosition(posX -24.8f,posY -19.2f); 
				if(player.Frame_A == 2) { spr_master.setPosition(posX -24.8f,posY -18f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -24.8f,posY -18f);   }
			}
			spr_master.setScale(0.2f,0.4f);	
			
			if(player.playerInBattle_A.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetFooter_A + player.Sex_A + "_front2");
				spr_master.setPosition(posX -25,posY -21);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			//basictopM_sit
			if(player.playerSit_A.equals("yes")) {
				player.Frame_A = 1;
				player.Side_A = "front";
				spr_master = atlas_genericset.createSprite(player.SetFooter_A + player.Sex_A + "_sit");
				spr_master.setPosition(posX -25,posY -18);
				spr_master.setScale(0.2f,0.4f);	
			}
		}
		
		/// [FOOTER FEMALE ] //////
		if(player.Sex_A.equals("F")) {
			spr_master = atlas_basicset.createSprite(player.SetFooter_A + player.Sex_A + "_" + player.Side_A + player.Frame_A);
			if(player.Side_A.equals("front")) { 
				spr_master.setPosition(posX -25f,posY -22.5f);
				if(player.Frame_A == 2) { spr_master.setPosition(posX -25f,posY -18.5f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -25f,posY -18.5f);   }
			
			}
			if(player.Side_A.equals("back")) { spr_master.setPosition(posX -25,posY -21); }	
			if(player.Side_A.equals("left")) { 
				spr_master.setPosition(posX -25.4f,posY -21.5f); 
				if(player.Frame_A == 2) { spr_master.setPosition(posX -25f,posY -22f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -25.5f,posY -23f);   }
			}	
			if(player.Side_A.equals("right")) { 
				spr_master.setPosition(posX -24.5f,posY -21.5f); 
				if(player.Frame_A == 2) { spr_master.setPosition(posX -24.6f,posY -22f);   }
				if(player.Frame_A == 3) { spr_master.setPosition(posX -25.6f,posY -23f);  }
			}
			spr_master.setScale(0.2f,0.4f);
			
			if(player.playerInBattle_A.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetFooter_A + player.Sex_A + "_attack1");
				spr_master.setPosition(posX -25f,posY -19.5f);
				spr_master.setScale(0.2f,0.4f);								
			}
			
			//basictopM_sit
			if(player.playerSit_A.equals("yes")) {
				player.Frame_A = 1;
				player.Side_A = "front";
				spr_master = atlas_genericset.createSprite(player.SetFooter_A + player.Sex_A + "_sit");
				spr_master.setPosition(posX -25.2f,posY -12);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		if(menu.equals("yes")){
			player.Side_A = "front";
			spr_master = atlas_basicset.createSprite(player.SetFooter_A + player.Sex_A + "_" + player.Side_A + 1);
			if(player.Side_A.equals("front")) { 
				spr_master.setPosition(cameraX + 22, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side_A = "front";
			spr_master = atlas_basicset.createSprite(player.SetFooter_A + player.Sex_A + "_" + player.Side_A + 1);
			if(player.Sex_A.equals("M")) { 
				spr_master.setPosition(cameraX -89, cameraY - 15);
				spr_master.setScale(0.4f,0.7f);
			}
			if(player.Sex_A.equals("F")) { 
				spr_master.setPosition(cameraX -88.6f, cameraY - 21);
				spr_master.setScale(0.4f,0.7f);
			}
		}
		
		return spr_master;
	}
	
	public Sprite GetNPC(String npcname, int frame) {
		
		if(npcname.equals("DungeonMaster")) {  
			spr_master = atlas_npcs.createSprite("NPCP");
			spr_master.setSize(12, 40);
			spr_master.setPosition(106, -130.5f);
			return spr_master;
		}
		
		if(npcname.equals("ExpGiver")) {  
			spr_master = atlas_npcs.createSprite("NPCB2");
			spr_master.setSize(9, 33);
			spr_master.setPosition(-5, -123.5f);
			return spr_master;
		}
		
		return spr_master;
	}

	public Sprite GetWeapon(Player player , String menu, float cameraX, float cameraY) {
		return spr_master;
	}
	
	//[Monsters]//
	public ArrayList<Monster> LoadMonsters(String Map) {
		if(Map.equals("Sewers")){
			lstMonsters = placeholderMonster.LoadMonsterData(Map);
		}

		return lstMonsters;
	}

	public Sprite GetMonster(String mob, int mobframe, String side) {
		if(mob.equals("slime")){
			spr_master = atlas_mobSewers.createSprite("slime" + mobframe);
			return spr_master;
		}
		if(mob.equals("oikplant")){
			spr_master = atlas_mobSewers.createSprite("oikplant" + mobframe);
			return spr_master;
		}
		if(mob.equals("rat")){
			spr_master = atlas_mobSewers.createSprite("rat" + mobframe);
			return spr_master;
		}
		if(mob.equals("sapod")){
			spr_master = atlas_mobSewers.createSprite("sapod" + mobframe);
			return spr_master;
		}
		

		return spr_master;
	}
	
	
	public Sprite GetItem(String nameItem) {
		
		if(player.Sex_A.equals("M")) {
			if(nameItem.equals("basictop")) { spr_master = atlas_items.createSprite("basictopM");}
			if(nameItem.equals("basicbottom")) { spr_master = atlas_items.createSprite("basicbottomM"); }
			if(nameItem.equals("basicfooter")) { spr_master = atlas_items.createSprite("basicfooterM"); }
		}
		
		if(player.Sex_A.equals("F")) {
			if(nameItem.equals("basictop")) { spr_master = atlas_items.createSprite("basictopF"); }
			if(nameItem.equals("basicbottom")) { spr_master = atlas_items.createSprite("basicbottomF"); }
			if(nameItem.equals("basicfooter")) { spr_master = atlas_items.createSprite("basicfooterF"); }
		}
		
		//Facas Aprendiz
		if(nameItem.equals("basicknife")) { spr_master = atlas_items.createSprite("basicknife"); }
		if(nameItem.equals("doubleedgeknife")) { spr_master = atlas_items.createSprite("doubleedgeknife"); }
		
		//Espadas
		if(nameItem.equals("woodsword")) { spr_master = atlas_items.createSprite("woodsword"); }
		if(nameItem.equals("sabersword")) { spr_master = atlas_items.createSprite("sabersword"); }
		if(nameItem.equals("venomsword")) { spr_master = atlas_items.createSprite("venomsword"); }
		if(nameItem.equals("edgesword")) { spr_master = atlas_items.createSprite("edgesword"); }
		if(nameItem.equals("knightsword")) { spr_master = atlas_items.createSprite("knightsword"); }
		if(nameItem.equals("ragesword")) { spr_master = atlas_items.createSprite("ragesword"); }
		//if(nameItem.equals("curved_sword")) { spr_master = atlas_items.createSprite("curvedsword"); }
		//if(nameItem.equals("cristal_sword")) { spr_master = atlas_items.createSprite("cristalsword"); }
		//if(nameItem.equals("serpent_sword")) { spr_master = atlas_items.createSprite("serpentsword"); }
		//if(nameItem.equals("flame_sword")) { spr_master = atlas_items.createSprite("flamesword"); }
		
		
		//Cajados
		if(nameItem.equals("stickrod")) { spr_master = atlas_items.createSprite("stickrod"); }
		if(nameItem.equals("gloomrod")) { spr_master = atlas_items.createSprite("gloomrod"); }
		if(nameItem.equals("gemrod")) { spr_master = atlas_items.createSprite("gemrod"); }
		if(nameItem.equals("lightwieldrod")) { spr_master = atlas_items.createSprite("lightwieldrod"); }
		if(nameItem.equals("serpentrod")) { spr_master = atlas_items.createSprite("serpentrod"); }
		if(nameItem.equals("clerigrod")) { spr_master = atlas_items.createSprite("clerigrod"); }
		//if(nameItem.equals("death_rod")) { spr_master = atlas_items.createSprite("deathrod"); }
		//if(nameItem.equals("butterfly_rod")) { spr_master = atlas_items.createSprite("butterflyrod"); }
		//if(nameItem.equals("star_rod")) { spr_master = atlas_items.createSprite("starrod"); }
		
		//Pistolas
		if(nameItem.equals("basicpistol")) { spr_master = atlas_items.createSprite("basicpistol"); }
		if(nameItem.equals("revolverpistol")) { spr_master = atlas_items.createSprite("revolverpistol"); }
		if(nameItem.equals("lightpistol")) { spr_master = atlas_items.createSprite("lightpistol"); }
		if(nameItem.equals("turretpistol")) { spr_master = atlas_items.createSprite("turretpistol"); }
		if(nameItem.equals("riflepistol")) { spr_master = atlas_items.createSprite("riflepistol"); }
		if(nameItem.equals("shooterpistol")) { spr_master = atlas_items.createSprite("shooterpistol"); }
		//if(nameItem.equals("heavymachine_pistol")) { spr_master = atlas_items.createSprite("heavymachinepistol"); }
		//if(nameItem.equals("cannon_pistol")) { spr_master = atlas_items.createSprite("cannonpistol"); }
		//if(nameItem.equals("shark_pistol")) { spr_master = atlas_items.createSprite("sharkpistol"); }
		
		//Facas
		if(nameItem.equals("basicdagger")) { spr_master = atlas_items.createSprite("basicdagger"); }
		if(nameItem.equals("revolverpistol")) { spr_master = atlas_items.createSprite("revolverpistol"); }
		if(nameItem.equals("lightpistol")) { spr_master = atlas_items.createSprite("lightpistol"); }
		if(nameItem.equals("turretpistol")) { spr_master = atlas_items.createSprite("turretpistol"); }
		if(nameItem.equals("riflepistol")) { spr_master = atlas_items.createSprite("riflepistol"); }
		if(nameItem.equals("shooterpistol")) { spr_master = atlas_items.createSprite("shooterpistol"); }
		//if(nameItem.equals("poison_dagger")) { spr_master = atlas_items.createSprite("poisondagger"); }
		//if(nameItem.equals("marine_dagger")) { spr_master = atlas_items.createSprite("marinedagger"); }
		//if(nameItem.equals("thunder_dagger")) { spr_master = atlas_items.createSprite("thunderdagger"); }
		
		//Machados
		if(nameItem.equals("basicaxe")) { spr_master = atlas_items.createSprite("basicaxe"); }
		if(nameItem.equals("pickaxe")) { spr_master = atlas_items.createSprite("pickaxe"); }		
		if(nameItem.equals("killeraxe")) { spr_master = atlas_items.createSprite("killeraxe"); }
		if(nameItem.equals("hammeraxe")) { spr_master = atlas_items.createSprite("hammeraxe"); }		
		if(nameItem.equals("scytheaxe")) { spr_master = atlas_items.createSprite("scytheaxe"); }
		if(nameItem.equals("anchoraxe")) { spr_master = atlas_items.createSprite("anchoraxe"); }
		//if(nameItem.equals("guitar_axe")) { spr_master = atlas_items.createSprite("guitaraxe"); }
		//if(nameItem.equals("bloodteeth_axe")) { spr_master = atlas_items.createSprite("bloodteethaxe"); }
		
		
		
		if(nameItem.equals("hpcan")) { spr_master = atlas_items.createSprite("hpcan"); }
		if(nameItem.equals("mpcan")) { spr_master = atlas_items.createSprite("mpcan"); }
		if(nameItem.equals("stcan")) { spr_master = atlas_items.createSprite("stcan"); }
		
		if(nameItem.equals("hatbear")) { spr_master = atlas_items.createSprite("hatbear"); }
		if(nameItem.equals("hatblackglass")) { spr_master = atlas_items.createSprite("hatblackglass"); }
		if(nameItem.equals("hatheadset")) { spr_master = atlas_items.createSprite("hatheadset"); }
		if(nameItem.equals("hatmagician")) { spr_master = atlas_items.createSprite("hatmagician"); }
		if(nameItem.equals("hatpirate")) { spr_master = atlas_items.createSprite("hatpirate"); }
		if(nameItem.equals("hatsanta")) { spr_master = atlas_items.createSprite("hatsanta"); }
		if(nameItem.equals("hatslime")) { spr_master = atlas_items.createSprite("hatslime"); }
		if(nameItem.equals("hatsunglasses")) { spr_master = atlas_items.createSprite("hatsunglasses"); }
		if(nameItem.equals("hatslime")) { spr_master = atlas_items.createSprite("hatslime"); }
		if(nameItem.equals("hattimer")) { spr_master = atlas_items.createSprite("hattimer"); }
		if(nameItem.equals("hatbutterfly")) { spr_master = atlas_items.createSprite("hatbutterfly"); }
		if(nameItem.equals("hatcapoult")) { spr_master = atlas_items.createSprite("hatcapoult"); }
		if(nameItem.equals("hatcooker")) { spr_master = atlas_items.createSprite("hatcooker"); }
		if(nameItem.equals("hatfashionglasses")) { spr_master = atlas_items.createSprite("hatfashionglasses"); }
		
		
		if(nameItem.equals("lootfragmentoamarelo")) { spr_master = atlas_items.createSprite("lootfragmentoamarelo"); }
		if(nameItem.equals("lootfragmentoazul")) { spr_master = atlas_items.createSprite("lootfragmentoazul"); }
		if(nameItem.equals("lootfragmentoroxo")) { spr_master = atlas_items.createSprite("lootfragmentoroxo"); }
		if(nameItem.equals("lootfragmentoverde")) { spr_master = atlas_items.createSprite("lootfragmentoverde"); }
		if(nameItem.equals("lootfragmentovermelho")) { spr_master = atlas_items.createSprite("lootfragmentovermelho"); }
		
		
		if(nameItem.equals("lootblop")) { spr_master = atlas_items.createSprite("lootblop"); }
		if(nameItem.equals("lootpoisonleaf")) { spr_master = atlas_items.createSprite("lootpoisonleaf"); }
		if(nameItem.equals("lootmushroom")) { spr_master = atlas_items.createSprite("lootmushroom"); }
		if(nameItem.equals("lootpoisonleaf")) { spr_master = atlas_items.createSprite("lootpoisonleaf"); }
		if(nameItem.equals("lootfang")) { spr_master = atlas_items.createSprite("lootfang"); }
		
		
		return spr_master;
	}
	
			//[BAG]
			public void UseItem(int numItem) {
				String[] lstItem = player.Itens_A.split("-");
				String[] itemSplit;
				String item = "";
				String itemName = "";
				String lstitensFinal;
				int qtd;
				boolean equipable = false;  
				String crystalUse = "no";
				
				item = lstItem[numItem];
				if(!item.equals("[NONE]")) {
					itemSplit = item.split("#");
					itemName = itemSplit[0].replace("[", "");
					qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
					
					if(itemName.equals("lootblop")) { return; }
					
					
					//Consumable
					if(itemName.equals("hpcan")) { player.Hp_A = player.Hp_A + 10; if(player.Hp_A > player.HpMax_A) { player.Hp_A = player.HpMax_A; } equipable = false;}	
					if(itemName.equals("garrafadrink")) { player.Hp_A = player.Hp_A + 100; if(player.Hp_A > player.HpMax_A) { player.Hp_A = player.HpMax_A; }equipable = false;}			
					if(itemName.equals("mpcan")) { player.Mp_A = player.Mp_A + 25; if(player.Mp_A > player.MpMax_A) { player.Mp_A = player.MpMax_A; } equipable = false;}	
					if(itemName.equals("garrafamagica")) { player.Mp_A = player.Mp_A + 50; if(player.Mp_A > player.MpMax_A) { player.Mp_A = player.MpMax_A; } equipable = false;}
					if(itemName.equals("stcan")) { player.Stamina_A = player.Stamina_A + 5; if(player.Stamina_A > player.StaminaMax_A) { player.Stamina_A = player.StaminaMax_A; } equipable = false;}	
					if(itemName.equals("garrafasuco")) { player.Stamina_A = player.Stamina_A + 30; if(player.Stamina_A > player.StaminaMax_A) { player.Stamina_A = player.StaminaMax_A; } equipable = false;}
					if(itemName.equals("fries")) { player.Stamina_A = player.Stamina_A + 15; if(player.Stamina_A > player.StaminaMax_A) { player.Stamina_A = player.StaminaMax_A; } equipable = false;}	
					if(itemName.equals("pizza")) { player.Stamina_A = player.Stamina_A + 5; if(player.Stamina_A > player.StaminaMax_A) { player.Stamina_A = player.StaminaMax_A; } } player.Hp_A = player.Hp_A + 30; if(player.Hp_A > player.HpMax_A) { player.Hp_A = player.HpMax_A; equipable = false;}	
					
					if(itemName.equals("basictop")) {  if(player.SetUpper_A.equals("basictop")){ return; } else { AddItemBag(player.SetUpper_A); player.SetUpper_A = "basictop"; lstItem = player.Itens_A.split("-"); }}
					if(itemName.equals("basicbottom")) {  if(player.SetUpper_A.equals("basicbottom")){ return; } else { AddItemBag(player.SetUpper_A); player.SetUpper_A = "basicbottom"; lstItem = player.Itens_A.split("-"); }}
					if(itemName.equals("basicfooter")) {  if(player.SetUpper_A.equals("basicfooter")){ return; } else { AddItemBag(player.SetUpper_A); player.SetUpper_A = "basicfooter"; lstItem = player.Itens_A.split("-"); }}
						
					
					//aprendiz
					if(itemName.equals("basicknife")) {  
						if(player.Weapon_A.equals("basicknife")){ return; } 
						if(!player.Weapon_A.equals("basicknife")) { AddItemBag(player.Weapon_A); player.Weapon_A = "basic_knife"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("doubleedgeknife")) {  
						if(player.Weapon_A.equals("doubleedgeknife")){ return; } 
						if(!player.Weapon_A.equals("doubleedgeknife")) { AddItemBag(player.Weapon_A); player.Weapon_A = "doubleedgeknife"; lstItem = player.Itens_A.split("-"); }
					}
					//espadachim
					if(itemName.equals("woodsword")) {  
						if(!player.Job_A.equals("Espadachim")){ return; } 
						if(player.Weapon_A.equals("woodsword")){ return; } 
						if(!player.Weapon_A.equals("woodsword")) { AddItemBag(player.Weapon_A); player.Weapon_A = "woodsword"; lstItem = player.Itens_A.split("-"); }					
					}
					if(itemName.equals("sabersword")) {  
						if(!player.Job_A.equals("Espadachim")){ return; } 
						if(player.Weapon_A.equals("sabersword")){ return; } 
						if(!player.Weapon_A.equals("sabersword")) { AddItemBag(player.Weapon_A); player.Weapon_A = "sabersword"; lstItem = player.Itens_A.split("-"); }		
					}
					if(itemName.equals("venomsword")) {  
						if(!player.Job_A.equals("Espadachim")){ return; } 
						if(player.Weapon_A.equals("venomsword")){ return; } 
						if(!player.Weapon_A.equals("venomsword")) { AddItemBag(player.Weapon_A); player.Weapon_A = "venomsword"; lstItem = player.Itens_A.split("-"); }		
					}
					if(itemName.equals("edgesword")) {  
						if(!player.Job_A.equals("Espadachim")){ return; } 
						if(player.Weapon_A.equals("edgesword")){ return; } 
						if(!player.Weapon_A.equals("edgesword")) { AddItemBag(player.Weapon_A); player.Weapon_A = "edgesword"; lstItem = player.Itens_A.split("-"); }		
					}
					if(itemName.equals("knightsword")) {  
						if(!player.Job_A.equals("Espadachim")){ return; } 
						if(player.Weapon_A.equals("knightsword")){ return; } 
						if(!player.Weapon_A.equals("knightsword")) { AddItemBag(player.Weapon_A); player.Weapon_A = "knightsword"; lstItem = player.Itens_A.split("-"); }		
					}
					if(itemName.equals("ragesword")) {  
						if(!player.Job_A.equals("Espadachim")){ return; } 
						if(player.Weapon_A.equals("ragesword")){ return; } 
						if(!player.Weapon_A.equals("ragesword")) { AddItemBag(player.Weapon_A); player.Weapon_A = "ragesword"; lstItem = player.Itens_A.split("-"); }		
					}
					
					//Feiticeiro
					if(itemName.equals("stickrod")) { 
						if(!player.Job_A.equals("Feiticeiro") || !player.Job_A.equals("Medico")){ return; } 
						if(player.Weapon_A.equals("stickrod")){ return; } 
						if(!player.Weapon_A.equals("stickrod")) { AddItemBag(player.Weapon_A); player.Weapon_A = "stickrod"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("gloomrod")) { 
						if(!player.Job_A.equals("Feiticeiro") || !player.Job_A.equals("Medico")){ return; } 
						if(player.Weapon_A.equals("gloomrod")){ return; } 
						if(!player.Weapon_A.equals("gloomrod")) { AddItemBag(player.Weapon_A); player.Weapon_A = "gloomrod"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("gemrod")) { 
						if(!player.Job_A.equals("Feiticeiro") || !player.Job_A.equals("Medico")){ return; } 
						if(player.Weapon_A.equals("gemrod")){ return; } 
						if(!player.Weapon_A.equals("gemrod")) { AddItemBag(player.Weapon_A); player.Weapon_A = "gemrod"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("lightwieldrod")) { 
						if(!player.Job_A.equals("Feiticeiro") || !player.Job_A.equals("Medico")){ return; } 
						if(player.Weapon_A.equals("lightwieldrod")){ return; } 
						if(!player.Weapon_A.equals("lightwieldrod")) { AddItemBag(player.Weapon_A); player.Weapon_A = "lightwieldrod"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("serpentrod")) { 
						if(!player.Job_A.equals("Feiticeiro") || !player.Job_A.equals("Medico")){ return; } 
						if(player.Weapon_A.equals("serpentrod")){ return; } 
						if(!player.Weapon_A.equals("serpentrod")) { AddItemBag(player.Weapon_A); player.Weapon_A = "serpentrod"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("clerigrod")) { 
						if(!player.Job_A.equals("Feiticeiro") || !player.Job_A.equals("Medico")){ return; } 
						if(player.Weapon_A.equals("clerigrod")){ return; } 
						if(!player.Weapon_A.equals("clerigrod")) { AddItemBag(player.Weapon_A); player.Weapon_A = "clerigrod"; lstItem = player.Itens_A.split("-"); }
					}
					
					//Pistoleiro
					if(itemName.equals("basicpistol")) { 
						if(!player.Job_A.equals("Pistoleiro")){ return; } 
						if(player.Weapon_A.equals("basicpistol")){ return; } 
						if(!player.Weapon_A.equals("basicpistol")) {  AddItemBag(player.Weapon_A); player.Weapon_A = "basicpistol"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("revolverpistol")) { 
						if(!player.Job_A.equals("Pistoleiro")){ return; } 
						if(player.Weapon_A.equals("revolverpistol")){ return; } 
						if(!player.Weapon_A.equals("revolverpistol")) {  AddItemBag(player.Weapon_A); player.Weapon_A = "revolverpistol"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("lightpistol")) { 
						if(!player.Job_A.equals("Pistoleiro")){ return; } 
						if(player.Weapon_A.equals("lightpistol")){ return; } 
						if(!player.Weapon_A.equals("lightpistol")) {  AddItemBag(player.Weapon_A); player.Weapon_A = "lightpistol"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("turretpistol")) { 
						if(!player.Job_A.equals("Pistoleiro")){ return; } 
						if(player.Weapon_A.equals("turretpistol")){ return; } 
						if(!player.Weapon_A.equals("turretpistol")) {  AddItemBag(player.Weapon_A); player.Weapon_A = "turretpistol"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("riflepistol")) { 
						if(!player.Job_A.equals("Pistoleiro")){ return; } 
						if(player.Weapon_A.equals("riflepistol")){ return; } 
						if(!player.Weapon_A.equals("riflepistol")) {  AddItemBag(player.Weapon_A); player.Weapon_A = "riflepistol"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("shooterpistol")) { 
						if(!player.Job_A.equals("Pistoleiro")){ return; } 
						if(player.Weapon_A.equals("shooterpistol")){ return; } 
						if(!player.Weapon_A.equals("shooterpistol")) {  AddItemBag(player.Weapon_A); player.Weapon_A = "shooterpistol"; lstItem = player.Itens_A.split("-"); }
					}
					
					//Ladrao
					if(itemName.equals("basicdagger")) { 
						if(!player.Job_A.equals("Ladrao")){ return; } 
						if(player.Weapon_A.equals("basicdagger")){ return; } 
						if(!player.Weapon_A.equals("basicdagger")) { AddItemBag(player.Weapon_A); player.Weapon_A = "basicdagger"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("edgedagger")) { 
						if(!player.Job_A.equals("Ladrao")){ return; } 
						if(player.Weapon_A.equals("edgedagger")){ return; } 
						if(!player.Weapon_A.equals("edgedagger")) { AddItemBag(player.Weapon_A); player.Weapon_A = "edgedagger"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("poisondagger")) { 
						if(!player.Job_A.equals("Ladrao")){ return; } 
						if(player.Weapon_A.equals("poisondagger")){ return; } 
						if(!player.Weapon_A.equals("poisondagger")) { AddItemBag(player.Weapon_A); player.Weapon_A = "poisondagger"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("marinedagger")) { 
						if(!player.Job_A.equals("Ladrao")){ return; } 
						if(player.Weapon_A.equals("marinedagger")){ return; } 
						if(!player.Weapon_A.equals("marinedagger")) { AddItemBag(player.Weapon_A); player.Weapon_A = "marinedagger"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("triplodagger")) { 
						if(!player.Job_A.equals("Ladrao")){ return; } 
						if(player.Weapon_A.equals("triplodagger")){ return; } 
						if(!player.Weapon_A.equals("triplodagger")) { AddItemBag(player.Weapon_A); player.Weapon_A = "triplodagger"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("thunderdagger")) { 
						if(!player.Job_A.equals("Ladrao")){ return; } 
						if(player.Weapon_A.equals("thunderdagger")){ return; } 
						if(!player.Weapon_A.equals("thunderdagger")) { AddItemBag(player.Weapon_A); player.Weapon_A = "thunderdagger"; lstItem = player.Itens_A.split("-"); }
					}
					
					//Batedor
					if(itemName.equals("basicaxe")) { 
						if(!player.Job_A.equals("Batedor")){ return; } 
						if(player.Weapon_A.equals("basicaxe")){ return; } 
						if(!player.Weapon_A.equals("basicaxe")) { AddItemBag(player.Weapon_A); player.Weapon_A = "basicaxe"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("pickaxe")) { 
						if(!player.Job_A.equals("Batedor")){ return; } 
						if(player.Weapon_A.equals("pickaxe")){ return; } 
						if(!player.Weapon_A.equals("pickaxe")) { AddItemBag(player.Weapon_A); player.Weapon_A = "pickaxe"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("killeraxe")) { 
						if(!player.Job_A.equals("Batedor")){ return; } 
						if(player.Weapon_A.equals("killeraxe")){ return; } 
						if(!player.Weapon_A.equals("killeraxe")) { AddItemBag(player.Weapon_A); player.Weapon_A = "killeraxe"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("hammeraxe")) { 
						if(!player.Job_A.equals("Batedor")){ return; } 
						if(player.Weapon_A.equals("hammeraxe")){ return; } 
						if(!player.Weapon_A.equals("hammeraxe")) { AddItemBag(player.Weapon_A); player.Weapon_A = "hammeraxe"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("scytheaxe")) { 
						if(!player.Job_A.equals("Batedor")){ return; } 
						if(player.Weapon_A.equals("scytheaxe")){ return; } 
						if(!player.Weapon_A.equals("scytheaxe")) { AddItemBag(player.Weapon_A); player.Weapon_A = "scytheaxe"; lstItem = player.Itens_A.split("-"); }
					}
					if(itemName.equals("anchoraxe")) { 
						if(!player.Job_A.equals("Batedor")){ return; } 
						if(player.Weapon_A.equals("anchoraxe")){ return; } 
						if(!player.Weapon_A.equals("anchoraxe")) { AddItemBag(player.Weapon_A); player.Weapon_A = "anchoraxe"; lstItem = player.Itens_A.split("-"); }
					}
								
					//Hats
					if(itemName.equals("pirate_hat")) {  
						if(player.Hat_A.equals("pirate_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "pirate_hat"; lstItem = player.Itens_A.split("-");  }
						if(player.Hat_A.equals("none")) { player.Hat_A = "pirate_hat";}
					}
							
					if(itemName.equals("magician_hat")) {  
						if(player.Hat_A.equals("magician_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "magician_hat";  lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "magician_hat";}
					}
					
					if(itemName.equals("bunny_hat")) {  
						if(player.Hat_A.equals("bunny_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "bunny_hat";  lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "bunny_hat";}
					}
					
					if(itemName.equals("slime_hat")) {  
						if(player.Hat_A.equals("slime_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "slime_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "slime_hat";}
					}
					if(itemName.equals("bear_hat")) {  
						if(player.Hat_A.equals("bear_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "bear_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "bear_hat";}
					}
					if(itemName.equals("santa_hat")) {  
						if(player.Hat_A.equals("santa_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "santa_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "santa_hat";}
					}
					if(itemName.equals("beachglass_hat")) {  
						if(player.Hat_A.equals("beachglass_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "beachglass_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "beachglass_hat";}
					}
					if(itemName.equals("capoult_hat")) {  
						if(player.Hat_A.equals("capoult_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "capoult_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "capoult_hat";}
					}
					if(itemName.equals("clock_hat")) {  
						if(player.Hat_A.equals("clock_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "clock_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "clock_hat";}
					}
					if(itemName.equals("brazilflag_hat")) {  
						if(player.Hat_A.equals("brazilflag_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "brazilflag_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "brazilflag_hat";}
					}
					
					if(itemName.equals("headphone_hat")) {  
						if(player.Hat_A.equals("headphone_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "headphone_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "headphone_hat";}
					}
					if(itemName.equals("sunglass_hat")) {  
						if(player.Hat_A.equals("sunglass_hat")){ return; } 
						if(!player.Hat_A.equals("none")) { AddItemBag(player.Hat_A); player.Hat_A = "sunglass_hat"; lstItem = player.Itens_A.split("-"); }
						if(player.Hat_A.equals("none")) { player.Hat_A = "sunglass_hat";}
					}
					
					//orbs
					if(itemName.equals("blue_orb")) { return; }
					if(itemName.equals("orange_orb")) { return; }
					if(itemName.equals("pink_orb")) { return; }
					if(itemName.equals("purple_orb")) { return; }
					if(itemName.equals("red_orb")) { return; }
					if(itemName.equals("green_orb")) { return; }
					if(itemName.equals("yellow_orb")) { return; }
					if(itemName.equals("gray_orb")) { return; }
					if(itemName.equals("green_orb")) { return; }
					
					//Crystals
					if(itemName.equals("blue_crystal_intextra_1") && !equipable) { UseCrystal(itemName); equipable = true;  }
					if(itemName.equals("blue_crystal_intextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("blue_crystal_intextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("green_crystal_lukextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("green_crystal_lukextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("green_crystal_lukextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
								
					if(itemName.equals("purple_crystal_vitextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("purple_crystal_vitextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("purple_crystal_vitextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("yellow_crystal_agiextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("yellow_crystal_agiextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("yellow_crystal_agiextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("red_crystal_strextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("red_crystal_strextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("red_crystal_strextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("grey_crystal_dexextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("grey_crystal_dexextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("grey_crystal_dexextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("orange_crystal_resextra_1") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("orange_crystal_resextra_2") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("orange_crystal_resextra_3") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("green_orb")) { return; }
					if(itemName.equals("yellow_orb")) { return; }
					if(itemName.equals("gray_orb")) { return; }
					if(itemName.equals("green_orb")) { return; }
					if(itemName.equals("red_orb")) { return; }
					if(itemName.equals("blue_orb")) { return; }
								
					qtd = qtd - 1;
					
					if(qtd == 0) {
						itemName = "[NONE]";
						lstItem[numItem] = itemName;
						lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
						lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
						player.Itens_A = lstitensFinal;
					}
					else {
						itemName = "[" + itemName + "#" + qtd + "]"; 
						lstItem[numItem] = itemName;
						lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
						lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
						player.Itens_A = lstitensFinal;
					}			
				}
			}
			
			public void UnequipHat() {
				String nameHat = player.Hat_A;
				if(nameHat.equals("none")) { return; }
				player.Hat_A = "none";
				AddItemBag(nameHat);
			}
			
			
			public void UseCrystal(String item) {
				if(player.Crystal1_A.equals("none")) { player.Crystal1_A = item; ApplyCrystals(item); return; }
				if(player.Crystal2_A.equals("none")) { player.Crystal2_A = item; ApplyCrystals(item); return; }
				if(player.Crystal3_A.equals("none")) { player.Crystal3_A = item; ApplyCrystals(item); return; }
				if(player.Crystal4_A.equals("none")) { player.Crystal4_A = item; ApplyCrystals(item); return; }
			}
			
			public void ApplyCrystals(String item) {
				
				if(item.equals("blue_crystal_intextra_1")) { player.Wis_A = player.Wis_A + 2; player.MpMax_A = player.MpMax_A + 20; }	
				if(item.equals("blue_crystal_intextra_2")) { player.Wis_A = player.Wis_A + 5; player.MpMax_A = player.MpMax_A + 50; }
				if(item.equals("blue_crystal_intextra_3")) { player.Wis_A = player.Wis_A + 10; player.MpMax_A = player.MpMax_A + 100; }
				
				if(item.equals("green_crystal_lukextra_1")) { player.Luk_A = player.Luk_A + 2;  }	
				if(item.equals("green_crystal_lukextra_2")) { player.Luk_A = player.Luk_A + 5;  }
				if(item.equals("green_crystal_lukextra_3")) { player.Luk_A = player.Luk_A + 10; }
				
				if(item.equals("purple_crystal_vitextra_1")) { player.Vit_A = player.Vit_A + 2; player.MpMax_A = player.HpMax_A + 20; }	
				if(item.equals("purple_crystal_vitextra_2")) { player.Vit_A = player.Vit_A + 5; player.MpMax_A = player.HpMax_A + 50; }
				if(item.equals("purple_crystal_vitextra_3")) { player.Vit_A = player.Vit_A + 10; player.MpMax_A = player.HpMax_A + 100; }
				
				if(item.equals("yellow_crystal_agiextra_1")) { player.Agi_A = player.Agi_A + 2; player.AtkTimerMax_A = player.AtkTimerMax_A - 2; }	
				if(item.equals("yellow_crystal_agiextra_2")) { player.Agi_A = player.Agi_A + 5; player.AtkTimerMax_A = player.AtkTimerMax_A - 4; }
				if(item.equals("yellow_crystal_agiextra_3")) { player.Agi_A = player.Agi_A + 10; player.AtkTimerMax_A = player.AtkTimerMax_A - 6; }
				
				if(item.equals("red_crystal_strextra_1")) { player.Str_A = player.Str_A + 2; }	
				if(item.equals("red_crystal_strextra_2")) { player.Str_A = player.Str_A + 5; }
				if(item.equals("red_crystal_strextra_3")) { player.Str_A = player.Str_A + 10; }
				
				if(item.equals("grey_crystal_dexextra_1")) { player.Dex_A = player.Dex_A + 2; }	
				if(item.equals("grey_crystal_dexextra_2")) { player.Dex_A = player.Dex_A + 5; }
				if(item.equals("grey_crystal_dexextra_3")) { player.Dex_A = player.Dex_A + 10; }
				
				if(item.equals("orange_crystal_resextra_1")) { player.Res_A = player.Res_A + 2; player.StaminaMax_A = player.StaminaMax_A + 20; player.regenTimeMax_A = player.regenTimeMax_A - 300; }	
				if(item.equals("orange_crystal_resextra_2")) { player.Res_A = player.Res_A + 5; player.StaminaMax_A = player.StaminaMax_A + 50; player.regenTimeMax_A = player.regenTimeMax_A - 500; }
				if(item.equals("orange_crystal_resextra_3")) { player.Res_A = player.Res_A + 10; player.StaminaMax_A = player.StaminaMax_A + 100; player.regenTimeMax_A = player.regenTimeMax_A - 700; }
				
			}
			
			public void RemoveCrystals(int num) {
				
				if(num == 1 && player.Crystal1_A.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis_A = player.Wis_A - 2; player.MpMax_A = player.MpMax_A - 20; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis_A = player.Wis_A - 2; player.MpMax_A = player.MpMax_A - 20; player.Crystal2_A = "none";return; }	
				if(num == 3 && player.Crystal3_A.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis_A = player.Wis_A - 2; player.MpMax_A = player.MpMax_A - 20; player.Crystal3_A = "none"; return; }	
				if(num == 4 && player.Crystal4_A.equals("blue_crystal_intextra_1")) { AddItemBag("blue_crystal_intextra_1"); player.Wis_A = player.Wis_A - 2; player.MpMax_A = player.MpMax_A - 20; player.Crystal4_A = "none"; return; }	
				
				if(num == 1 && player.Crystal1_A.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis_A = player.Wis_A - 5; player.MpMax_A = player.MpMax_A - 50; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis_A = player.Wis_A - 5; player.MpMax_A = player.MpMax_A - 50; player.Crystal2_A = "none"; return; }	
				if(num == 3 && player.Crystal3_A.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis_A = player.Wis_A - 5; player.MpMax_A = player.MpMax_A - 50; player.Crystal3_A = "none"; return; }	
				if(num == 4 && player.Crystal4_A.equals("blue_crystal_intextra_2")) { AddItemBag("blue_crystal_intextra_2"); player.Wis_A = player.Wis_A - 5; player.MpMax_A = player.MpMax_A - 50; player.Crystal4_A = "none"; return; }
				
				if(num == 1 && player.Crystal1_A.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis_A = player.Wis_A - 10; player.MpMax_A = player.MpMax_A - 100; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis_A = player.Wis_A - 10; player.MpMax_A = player.MpMax_A - 100; player.Crystal2_A = "none"; return; }	
				if(num == 3 && player.Crystal3_A.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis_A = player.Wis_A - 10; player.MpMax_A = player.MpMax_A - 100; player.Crystal3_A = "none"; return; }	
				if(num == 4 && player.Crystal4_A.equals("blue_crystal_intextra_3")) { AddItemBag("blue_crystal_intextra_3"); player.Wis_A = player.Wis_A - 10; player.MpMax_A = player.MpMax_A - 100; player.Crystal4_A = "none"; return; }
				
				if(num == 1 && player.Crystal1_A.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk_A = player.Luk_A - 2; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk_A = player.Luk_A - 2; player.Crystal2_A = "none"; return; }	
				if(num == 3 && player.Crystal3_A.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk_A = player.Luk_A - 2; player.Crystal3_A = "none"; return; }	
				if(num == 4 && player.Crystal4_A.equals("green_crystal_lukextra_1")) { AddItemBag("green_crystal_lukextra_1"); player.Luk_A = player.Luk_A - 2; player.Crystal4_A = "none"; return; }	
				
				if(num == 1 && player.Crystal1_A.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk_A = player.Luk_A - 5; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk_A = player.Luk_A - 5; player.Crystal2_A = "none"; return; }	
				if(num == 3 && player.Crystal3_A.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk_A = player.Luk_A - 5; player.Crystal3_A = "none"; return; }	
				if(num == 4 && player.Crystal4_A.equals("green_crystal_lukextra_2")) { AddItemBag("green_crystal_lukextra_2"); player.Luk_A = player.Luk_A- 5;  player.Crystal4_A = "none"; return; }
				
				if(num == 1 && player.Crystal1_A.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk_A = player.Luk_A - 10; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk_A = player.Luk_A - 10; player.Crystal2_A = "none"; return; }	
				if(num == 3 && player.Crystal3_A.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk_A = player.Luk_A - 10; player.Crystal3_A = "none"; return; }	
				if(num == 4 && player.Crystal4_A.equals("green_crystal_lukextra_3")) { AddItemBag("green_crystal_lukextra_3"); player.Luk_A = player.Luk_A - 10; player.Crystal4_A = "none"; return; }
				
				if(num == 1 && player.Crystal1_A.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit_A = player.Vit_A - 2; player.Crystal1_A = "none"; player.MpMax_A = player.HpMax_A - 20; return; }	
				if(num == 2 && player.Crystal2_A.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit_A = player.Vit_A - 2; player.Crystal2_A = "none";  player.MpMax_A = player.HpMax_A - 20; return; }	
				if(num == 3 && player.Crystal3_A.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit_A = player.Vit_A - 2; player.Crystal3_A = "none";  player.MpMax_A = player.HpMax_A - 20; return; }	
				if(num == 4 && player.Crystal4_A.equals("purple_crystal_vitextra_1")) { AddItemBag("purple_crystal_vitextra_1"); player.Vit_A = player.Vit_A - 2; player.Crystal4_A = "none";  player.MpMax_A = player.HpMax_A - 20; return; }	
				
				if(num == 1 && player.Crystal1_A.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit_A = player.Vit_A - 5; player.Crystal1_A = "none";  player.MpMax_A = player.HpMax_A - 50; return; }	
				if(num == 2 && player.Crystal2_A.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit_A = player.Vit_A - 5; player.Crystal2_A = "none";  player.MpMax_A = player.HpMax_A - 50; return; }	
				if(num == 3 && player.Crystal3_A.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit_A = player.Vit_A - 5; player.Crystal3_A = "none";  player.MpMax_A = player.HpMax_A - 50; return; }	
				if(num == 4 && player.Crystal4_A.equals("purple_crystal_vitextra_2")) { AddItemBag("purple_crystal_vitextra_2"); player.Vit_A = player.Vit_A - 5; player.Crystal4_A = "none";  player.MpMax_A = player.HpMax_A - 50; return; }
				
				if(num == 1 && player.Crystal1_A.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit_A = player.Vit_A - 10; player.Crystal1_A = "none";  player.MpMax_A = player.HpMax_A - 100; return; }	
				if(num == 2 && player.Crystal2_A.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit_A = player.Vit_A - 10; player.Crystal2_A = "none";  player.MpMax_A = player.HpMax_A - 100; return; }	
				if(num == 3 && player.Crystal3_A.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit_A = player.Vit_A - 10; player.Crystal3_A = "none";  player.MpMax_A = player.HpMax_A - 100; return; }	
				if(num == 4 && player.Crystal4_A.equals("purple_crystal_vitextra_3")) { AddItemBag("purple_crystal_vitextra_3"); player.Vit_A = player.Vit_A - 10; player.Crystal4_A = "none";  player.MpMax_A = player.HpMax_A - 100; return; }
				
				if(num == 1 && player.Crystal1_A.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi_A = player.Agi_A - 2; player.Crystal1_A = "none";  player.AtkTimerMax_A = player.AtkTimerMax_A + 2; return; }	
				if(num == 2 && player.Crystal2_A.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi_A = player.Agi_A - 2; player.Crystal2_A = "none";  player.AtkTimerMax_A = player.AtkTimerMax_A + 2; return; }	
				if(num == 3 && player.Crystal3_A.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi_A = player.Agi_A - 2; player.Crystal3_A = "none";  player.AtkTimerMax_A = player.AtkTimerMax_A + 2; return; }	
				if(num == 4 && player.Crystal4_A.equals("yellow_crystal_agiextra_1")) { AddItemBag("yellow_crystal_agiextra_1"); player.Agi_A = player.Agi_A - 2; player.Crystal4_A = "none";  player.AtkTimerMax_A = player.AtkTimerMax_A + 2; return; }	
				
				if(num == 1 && player.Crystal1_A.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi_A = player.Agi_A - 5; player.AtkTimerMax_A = player.AtkTimerMax_A + 4;  player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi_A = player.Agi_A - 5; player.AtkTimerMax_A = player.AtkTimerMax_A + 4;  player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi_A = player.Agi_A - 5; player.AtkTimerMax_A = player.AtkTimerMax_A + 4;  player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("yellow_crystal_agiextra_2")) { AddItemBag("yellow_crystal_agiextra_2"); player.Agi_A = player.Agi_A - 5; player.AtkTimerMax_A = player.AtkTimerMax_A + 4;  player.Crystal4_A = "none";  return; }
				
				if(num == 1 && player.Crystal1_A.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi_A = player.Agi_A - 10; player.AtkTimerMax_A= player.AtkTimerMax_A + 6; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi_A = player.Agi_A - 10; player.AtkTimerMax_A = player.AtkTimerMax_A + 6; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi_A = player.Agi_A - 10; player.AtkTimerMax_A = player.AtkTimerMax_A + 6; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("yellow_crystal_agiextra_3")) { AddItemBag("yellow_crystal_agiextra_3"); player.Agi_A = player.Agi_A - 10; player.AtkTimerMax_A = player.AtkTimerMax_A + 6; player.Crystal4_A = "none";  return; }
				
				if(num == 1 && player.Crystal1_A.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str_A = player.Str_A - 2; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str_A = player.Str_A - 2; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str_A = player.Str_A - 2; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("red_crystal_strextra_1")) { AddItemBag("red_crystal_strextra_1"); player.Str_A = player.Str_A - 2; player.Crystal4_A = "none";  return; }	
				
				if(num == 1 && player.Crystal1_A.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str_A = player.Str_A - 5; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str_A = player.Str_A - 5; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str_A = player.Str_A - 5; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("red_crystal_strextra_2")) { AddItemBag("red_crystal_strextra_2"); player.Str_A = player.Str_A - 5; player.Crystal4_A = "none";  return; }
				
				if(num == 1 && player.Crystal1_A.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str_A = player.Str_A - 10; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str_A = player.Str_A - 10; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str_A = player.Str_A - 10; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("red_crystal_strextra_3")) { AddItemBag("red_crystal_strextra_3"); player.Str_A = player.Str_A - 10; player.Crystal4_A = "none";  return; }
				
				if(num == 1 && player.Crystal1_A.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex_A = player.Dex_A - 2; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex_A = player.Dex_A - 2; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex_A = player.Dex_A - 2; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("grey_crystal_dexextra_1")) { AddItemBag("grey_crystal_dexextra_1"); player.Dex_A = player.Dex_A - 2; player.Crystal4_A = "none";  return; }	
				
				if(num == 1 && player.Crystal1_A.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex_A = player.Dex_A - 5; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex_A = player.Dex_A - 5; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex_A = player.Dex_A - 5; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("grey_crystal_dexextra_2")) { AddItemBag("grey_crystal_dexextra_2"); player.Dex_A = player.Dex_A - 5; player.Crystal4_A = "none";  return; }
				
				if(num == 1 && player.Crystal1_A.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex_A = player.Dex_A - 10; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex_A = player.Dex_A - 10; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex_A = player.Dex_A - 10; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("grey_crystal_dexextra_3")) { AddItemBag("grey_crystal_dexextra_3"); player.Dex_A = player.Dex_A - 10; player.Crystal4_A = "none";  return; }
				
				if(num == 1 && player.Crystal1_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res_A = player.Res_A - 2; player.StaminaMax_A = player.StaminaMax_A - 10; player.regenTimeMax_A = player.regenTimeMax_A + 300; player.Crystal1_A = "none"; return; }	
				if(num == 2 && player.Crystal2_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res_A = player.Res_A - 2; player.StaminaMax_A = player.StaminaMax_A - 10; player.regenTimeMax_A = player.regenTimeMax_A + 300; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res_A = player.Res_A - 2; player.StaminaMax_A = player.StaminaMax_A - 10; player.regenTimeMax_A = player.regenTimeMax_A + 300; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_1"); player.Res_A = player.Res_A - 2; player.StaminaMax_A = player.StaminaMax_A - 10; player.regenTimeMax_A = player.regenTimeMax_A + 300; player.Crystal4_A = "none";  return; }	
				
				if(num == 1 && player.Crystal1_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res_A = player.Res_A - 5; player.StaminaMax_A = player.StaminaMax_A - 50; player.regenTimeMax_A = player.regenTimeMax_A + 500; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res_A = player.Res_A - 5; player.StaminaMax_A = player.StaminaMax_A - 50; player.regenTimeMax_A = player.regenTimeMax_A + 500; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res_A = player.Res_A - 5; player.StaminaMax_A = player.StaminaMax_A - 50; player.regenTimeMax_A = player.regenTimeMax_A + 500; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_2"); player.Res_A = player.Res_A - 5; player.StaminaMax_A = player.StaminaMax_A - 50; player.regenTimeMax_A = player.regenTimeMax_A + 500; player.Crystal4_A = "none";  return; }
				
				if(num == 1 && player.Crystal1_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res_A = player.Res_A - 10; player.StaminaMax_A = player.StaminaMax_A - 100; player.regenTimeMax_A = player.regenTimeMax_A + 700; player.Crystal1_A = "none";  return; }	
				if(num == 2 && player.Crystal2_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res_A = player.Res_A - 10; player.StaminaMax_A = player.StaminaMax_A - 100; player.regenTimeMax_A = player.regenTimeMax_A + 700; player.Crystal2_A = "none";  return; }	
				if(num == 3 && player.Crystal3_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res_A = player.Res_A - 10; player.StaminaMax_A = player.StaminaMax_A - 100; player.regenTimeMax_A = player.regenTimeMax_A + 700; player.Crystal3_A = "none";  return; }	
				if(num == 4 && player.Crystal4_A.equals("orange_crystal_resextra_1")) { AddItemBag("orange_crystal_resextra_3"); player.Res_A = player.Res_A - 10; player.StaminaMax_A = player.StaminaMax_A - 100; player.regenTimeMax_A = player.regenTimeMax_A + 700; player.Crystal4_A = "none";  return; }
			}
			
			
			public Sprite SetWeapon(Player playerUse) {   
				
				//player.playerInBattle = "no";
				//playerInAttack = true;
				
				if(playerUse.Job_A.equals("Aprendiz")) { atlas_weapongeneric = atlas_nknifes; }
				if(playerUse.Job_A.equals("Espadachim")) { atlas_weapongeneric = atlas_swords; }
				if(playerUse.Job_A.equals("Feiticeiro")) { atlas_weapongeneric = atlas_rods; }
				if(playerUse.Job_A.equals("Batedor")) { atlas_weapongeneric = atlas_axes; }
				if(playerUse.Job_A.equals("Pistoleiro")) { atlas_weapongeneric = atlas_pistols; }
				if(playerUse.Job_A.equals("Medico")) { atlas_weapongeneric = atlas_rods; }
				if(playerUse.Job_A.equals("Ladrao")) { atlas_weapongeneric = atlas_daggers; }
					
				if(playerUse.playerInBattle_A.equals("yes")) {
					if(playerUse.Job_A.equals("Aprendiz")) {
						if(playerUse.Weapon_A.equals("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 13.5f, playerUse.PosY_A + 11f); }
						if(playerUse.Weapon_A.equals("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
					}
					if(playerUse.Job_A.equals("Espadachim")) {
						if(playerUse.Weapon_A.equals("woodsword")) { spr_master = atlas_nknifes.createSprite("wood_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("sabersword")) { spr_master = atlas_nknifes.createSprite("saber_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("venomsword")) { spr_master = atlas_nknifes.createSprite("venom_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("edgesword")) { spr_master = atlas_nknifes.createSprite("edge_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("knightsword")) { spr_master = atlas_nknifes.createSprite("knight_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("ragesword")) { spr_master = atlas_nknifes.createSprite("rage_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }						
					}
					
					if(playerUse.Job_A.equals("Feiticeiro")) {
						if(playerUse.Weapon_A.equals("stickrod")) { spr_master = atlas_nknifes.createSprite("stick_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 13.5f, playerUse.PosY_A + 11f); }
						if(playerUse.Weapon_A.equals("gloomrod")) { spr_master = atlas_nknifes.createSprite("gloom_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("gemrod")) { spr_master = atlas_nknifes.createSprite("gem_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("lightwieldrod")) { spr_master = atlas_nknifes.createSprite("lightwield_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("serpentrod")) { spr_master = atlas_nknifes.createSprite("serpent_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("clerigrod")) { spr_master = atlas_nknifes.createSprite("clerig_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }										
					}
					
					if(playerUse.Job_A.equals("Pistoleiro")) {
						if(playerUse.Weapon_A.equals("basicpistol")) { spr_master = atlas_nknifes.createSprite("basic_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 13.5f, playerUse.PosY_A + 11f); }
						if(playerUse.Weapon_A.equals("revolverpistol")) { spr_master = atlas_nknifes.createSprite("revolver_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("lightpistol")) { spr_master = atlas_nknifes.createSprite("light_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("turretpistol")) { spr_master = atlas_nknifes.createSprite("turret_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("riflepistol")) { spr_master = atlas_nknifes.createSprite("rifle_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("shooterpistol")) { spr_master = atlas_nknifes.createSprite("shooter_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }										
					}
					
					if(playerUse.Job_A.equals("Ladrao")) {
						if(playerUse.Weapon_A.equals("basicdagger")) { spr_master = atlas_nknifes.createSprite("basic_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 13.5f, playerUse.PosY_A + 11f); }
						if(playerUse.Weapon_A.equals("edgedagger")) { spr_master = atlas_nknifes.createSprite("edge_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("poisondagger")) { spr_master = atlas_nknifes.createSprite("poison_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("marinedagger")) { spr_master = atlas_nknifes.createSprite("marine_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("triplodagger")) { spr_master = atlas_nknifes.createSprite("triplo_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }
						if(playerUse.Weapon_A.equals("thunderdagger")) { spr_master = atlas_nknifes.createSprite("thunder_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 2.9f, playerUse.PosY_A + 6f); }										
					}
				}
				
				if(playerUse.playerInAttack_A.equals("yes")) {
					if(playerUse.Job_A.equals("Aprendiz")) {
						if(playerUse.Weapon_A.equals("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knife_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
					}
					
					if(playerUse.Job_A.equals("Espadachim")) {
						if(playerUse.Weapon_A.equals("woodsword")) { spr_master = atlas_nknifes.createSprite("wood_sword_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("sabersword")) { spr_master = atlas_nknifes.createSprite("saber_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("venomsword")) { spr_master = atlas_nknifes.createSprite("venom_sword_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("edgesword")) { spr_master = atlas_nknifes.createSprite("edge_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("knightsword")) { spr_master = atlas_nknifes.createSprite("knight_sword_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("ragesword")) { spr_master = atlas_nknifes.createSprite("rage_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
					}
					
					if(playerUse.Job_A.equals("Feiticeiro")) {
						if(playerUse.Weapon_A.equals("stickrod")) { spr_master = atlas_nknifes.createSprite("stick_rod_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("gloomrod")) { spr_master = atlas_nknifes.createSprite("gloom_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("gemrod")) { spr_master = atlas_nknifes.createSprite("gem_rod_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("lightwieldrod")) { spr_master = atlas_nknifes.createSprite("lightwield_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("serpentrod")) { spr_master = atlas_nknifes.createSprite("serpent_rod_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("clerigrod")) { spr_master = atlas_nknifes.createSprite("clerig_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
					}
					
					if(playerUse.Job_A.equals("Pistoleiro")) {
						if(playerUse.Weapon_A.equals("basicpistol")) { spr_master = atlas_nknifes.createSprite("basic_pistol_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("revolverpistol")) { spr_master = atlas_nknifes.createSprite("revolver_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("lightpistol")) { spr_master = atlas_nknifes.createSprite("light_pistol_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("turretpistol")) { spr_master = atlas_nknifes.createSprite("turret_pistol_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("riflepistol")) { spr_master = atlas_nknifes.createSprite("rifle_pistol_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("shooterpistol")) { spr_master = atlas_nknifes.createSprite("shooter_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
					}
					
					if(playerUse.Job_A.equals("Ladrao")) {
						if(playerUse.Weapon_A.equals("basicdagger")) { spr_master = atlas_nknifes.createSprite("basic_dagger_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("edgedagger")) { spr_master = atlas_nknifes.createSprite("edge_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("poisondagger")) { spr_master = atlas_nknifes.createSprite("poison_dagger_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("marinedagger")) { spr_master = atlas_nknifes.createSprite("marine_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("triplodagger")) { spr_master = atlas_nknifes.createSprite("triplo_dagger_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
						if(playerUse.Weapon_A.equals("thunderdagger")) { spr_master = atlas_nknifes.createSprite("thunder_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX_A - 4.8f, playerUse.PosY_A + 2f);  }
					}
				}
				
				return spr_master;
				
			}
			
			public void AddItemBag(String itemName) {
				String[] lstItem = player.Itens_A.split("-");
				String[] itemSplit;
				boolean exist = false;
				int qtd = 0;
				int posicaoItem = 0;
				String listaItemFinal;
				
				for(int i = 0; i < lstItem.length; i++) {
					if(lstItem[i].contains(itemName) && !exist) {
						posicaoItem = i;
						exist = true;
					}
				}
				
				if(exist) {
					itemSplit = lstItem[posicaoItem].split("#");
					qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
					qtd++;
					if(qtd >= 99) { return;}
						lstItem[posicaoItem] = "[" + itemSplit[0].replace("[", "") + "#" + String.valueOf(qtd) + "]";
						listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
						listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
						player.Itens_A = listaItemFinal;
				}
				else {
					for(int i = 0; i < lstItem.length; i++) {
						if(lstItem[i].contains("[NONE]") && !exist) {
							posicaoItem = i;
							exist = true;
						}
					}
					
					if(exist) {
						lstItem[posicaoItem] = "[" + itemName + "#" + "1" + "]";
						listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
						listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
						player.Itens_A = listaItemFinal;
					}
				}
			}
			
			public void DiscartItem(int itemNum) {
				String[] lstItem = player.Itens_A.split("-");
				String listaItemFinal = "";
				String[] itemSplit;
				String item;
				String itemName;
				int qtd;
				int money = 0;
				
				//Get Item
				item = lstItem[itemNum];
				if(item.equals("[NONE]")) { return; }
					
				//Check quantity
				itemSplit = item.split("#");
				itemName = itemSplit[0].replace("[", "");
				qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
				
				//Give Money
				money = player.Money_A;
				if(money > 5000) { return; }
				int moneygave = randnumber.nextInt(5);
				while(moneygave < 2) { moneygave = randnumber.nextInt(5); }
				money = money + (moneygave * 2);
				player.Money_A = money;
				
				//Clean Item placebox
				lstItem[itemNum] = "[NONE]";
				listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
				listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
				player.Itens_A = listaItemFinal;	
			}
			
			//Shops
			//SHOP
			public String CheckBuyItemStreetsA(String shop, int num) {
				String SysMsg = "";
				if(shop.equals("refrishop")) {
					if(num == 1) {  
						if(player.Money_A >= 2) {  
							AddItemBag("hpcan"); 
							player.Money_A = player.Money_A - 2; 
							if(player.Money_A < 0) { player.Money_A = 0; } 	
							SysMsg = "Comprado!";
						}
						else {
							SysMsg = "Dinheiro insuficiente"; 
						}		
					}
				}
				
				return SysMsg;
			}
			
			public Sprite GetSpriteSkill(String skillname, int num) {
				
				if(skillname.equals("tripleattack")) { spr_skill = atlas_tripleattack.createSprite("tripleattack"+num); }
				if(skillname.equals("steal")) { spr_skill = atlas_steal.createSprite("steal"+num); }
				if(skillname.equals("soulclash")) { spr_skill = atlas_soulclash.createSprite("soulclash"+num); }
				if(skillname.equals("ravenblade")) { spr_skill = atlas_ravenblade.createSprite("ravenblade"+num); }
				if(skillname.equals("ragebound")) { spr_skill = atlas_ragebound.createSprite("ragebound"+num); }
				if(skillname.equals("thundercloud")) { spr_skill = atlas_thundercloud.createSprite("thundercloud"+num); }
				if(skillname.equals("lockshot")) { spr_skill = atlas_lockshot.createSprite("lockshot"+num); }
				if(skillname.equals("mine")) { spr_skill = atlas_mine.createSprite("mine"+num); }
				if(skillname.equals("overpower")) { spr_skill = atlas_overpower.createSprite("overpower"+num); }
				if(skillname.equals("poisonhit")) { spr_skill = atlas_poisonhit.createSprite("poisonhit"+num); }
				if(skillname.equals("precision")) { spr_skill = atlas_precision.createSprite("precision"+num); }
				if(skillname.equals("protect")) { spr_skill = atlas_protect.createSprite("protect"+num); }
				if(skillname.equals("healthboost")) { spr_skill = atlas_healthboost.createSprite("healthboost"+num); }
				if(skillname.equals("holyprism")) { spr_skill = atlas_holyprism.createSprite("holyprism"+num); }
				if(skillname.equals("icecrystal")) { spr_skill = atlas_icecrystal.createSprite("icecrystal"+num); }
				if(skillname.equals("impound")) { spr_skill = atlas_impound.createSprite("impound"+num); }
				if(skillname.equals("invisibility")) { spr_skill = atlas_invisibility.createSprite("invisibility"+num); }
				if(skillname.equals("ironshield")) { spr_skill = atlas_ironshield.createSprite("ironshield"+num); }
				if(skillname.equals("doublehit")) { spr_skill = atlas_doublehit.createSprite("doublehit"+num); }
				if(skillname.equals("fastshot")) { spr_skill = atlas_fastshot.createSprite("fastshot"+num); }
				if(skillname.equals("fireball")) { spr_skill = atlas_fireball.createSprite("fireball"+num); }
				if(skillname.equals("flysword")) { spr_skill = atlas_flysword.createSprite("flysword"+num); }
				if(skillname.equals("heal")) { spr_skill = atlas_heal.createSprite("heal"+num); }
				if(skillname.equals("defboost")) { spr_skill = atlas_defboost.createSprite("defboost"+num); }
				if(skillname.equals("berserk")) { spr_skill = atlas_berserk.createSprite("berserk"+num); }
				if(skillname.equals("bulletrain")) { spr_skill = atlas_bulletrain.createSprite("bulletrain"+num); }
				if(skillname.equals("dashkick")) { spr_skill = atlas_dashkick.createSprite("dashkick"+num); }
				if(skillname.equals("regen")) { spr_skill = atlas_regen.createSprite("regen"+num); }
				if(skillname.equals("rockbound")) { spr_skill = atlas_rockbound.createSprite("rockbound"+num); }
				return spr_skill;
			}
			
			
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
						ThreadsSyncStartChat();				
					}
					if(operation.equals("SyncPlayer")) {
						ThreadsSyncStartPlayer();				
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
						onlineresponse = GerenciamentoOnline("Download","","");		
					}
					if(nomeOperacao.equals("ExpSharedSend")) {
						onlineresponse = GerenciamentoOnline("ExpSharedSend",subData,"");		
					}
					if(nomeOperacao.equals("ExpGiver")) {
						onlineresponse = GerenciamentoOnline("ExpGiver","","");		
					}
					if(nomeOperacao.equals("SyncChats")) {
						
						ThreadsSyncStartChat();		
					}	
					if(nomeOperacao.equals("SyncPlayer")) {
						
						ThreadsSyncStartPlayer();		
					}
					return onlineresponse;
				}
				
				catch(Exception ex) { return "none"; }	
			}
			
			private void ThreadsSyncStartChat() {
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
			
			public String GerenciamentoOnline(String tipoRequisicao, String subData, String extraData) throws IOException {
		    	
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
			        URL url = new URL("http://moonboltprojects.online/index.php");
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
			        URL url = new URL("http://moonboltprojects.online/index.php");
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
			        URL url = new URL("http://moonboltprojects.online/index.php");
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
			        URL url = new URL("http://moonboltprojects.online/index.php");
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
			        URL url = new URL("http://moonboltprojects.online/index.php");
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
					
					String data = URLEncoder.encode("ldataaccount", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(player.AccountID) + ".txt", "UTF-8");
					data += "&" + URLEncoder.encode("lrequest", "UTF-8") + "=" + URLEncoder.encode("Download", "UTF-8");
			        data += "&" + URLEncoder.encode("lservername", "UTF-8") + "=" + URLEncoder.encode(lservername, "UTF-8");
			        data += "&" + URLEncoder.encode("lusername", "UTF-8") + "=" + URLEncoder.encode(lusername, "UTF-8");
			        data += "&" + URLEncoder.encode("lpassword", "UTF-8") + "=" + URLEncoder.encode(lpassword, "UTF-8");
			        data += "&" + URLEncoder.encode("ldbname", "UTF-8") + "=" + URLEncoder.encode(ldbname, "UTF-8");
			        
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/online.php");
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
			        URL url = new URL("http://moonboltprojects.online/index.php");
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
			        data += "&" + URLEncoder.encode("ldateExp", "UTF-8") + "=" + URLEncoder.encode(player.PlayerExpGet_A, "UTF-8");
			            
			        // Send data
			        URL url = new URL("http://moonboltprojects.online/index.php");
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
			
			public void UpdateExpGet(String line) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				player.PlayerExpGet_1 = dtf.format(LocalDateTime.now());
				
				float expserver = 0;
				String[] lineSplit = line.split(":");
				expserver = Float.parseFloat(lineSplit[4]);
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
			}
			
			
			
}