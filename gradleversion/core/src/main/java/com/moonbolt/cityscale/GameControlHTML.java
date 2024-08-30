package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import java.util.concurrent.CountDownLatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Net;
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

public class GameControlHTML {
	
	//Manager
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private Player player;
	private int FrameAtkPlayer = 0;
	private int charNumber = 0;
	private String accountID = "";
	
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
	private TextureAtlas atlasxes;
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
	
	public GameControlHTML() {
		
		json = new Json();
		randnumber = new Random();
		player = new Player();
		
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
		atlasxes = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapons/axes.txt"));
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
	
	
	//[Account]//
	public String CreateNewAccount() {
		try {
			Player player = new Player();
			int accNumber = randnumber.nextInt(99999999);
			while(accNumber < 1000000){
				accNumber = randnumber.nextInt(99999999);
			}
			player.AccountID = String.valueOf(accNumber);
			accountID = String.valueOf(accNumber);
			String result = OnlineManager("NewAccount", String.valueOf(accNumber),"");
			return result;
		} 
		catch (Exception e) 
		{
			String test = e.getMessage();
			return "fail";
		}
	}

	public Player LoadDataHTML(String accountNumber) {
		String result = OnlineManager("LoadData", accountNumber,"");
		if(result.equals("fail")) {return null;}
		return player;
	}
	
	public String GetAccount() {
		return accountID;
	}
	
	public void CreateNewCharHTML(String name, String sex, String hair, String color) {
		boolean created = false;
		player = new Player();
		
		if(player.Name.equals("none")) {
			player.Name = name;
			player.Sex = sex;
			player.Hair = hair;
			player.Color = color;
			player.Hat = "none";
			player.Job = "Aprendiz";
			player.SetUpper = "basictop";
			player.SetBottom = "basicbottom";
			player.SetFooter = "basicfooter";
			player.Level = 1;
			player.Exp = 0;
			player.Map = "MetroStation";
			player.Hp = 30;
			player.Mp = 20;
			player.Money = 50;
			player.HpMax = 30;
			player.MpMax = 20;
			player.regenTime = 6000;
			player.regenTimeMax = 6000;
			player.PosX = 0;
			player.PosY = 0;
			player.Walk = "no";
			player.Frame = 1;
			player.countFrame = 1;
			player.breakwalk = "";
			player.Target = "none";
			player.AtkTimer = 300;
			player.AtkTimerMax = 300;
			player.Casting = "no";
			player.Atk = 5;
			player.Def = 1;
			player.Evasion = 0;
			player.Side =	"front";
			player.Weapon = "basicknife";
			player.Crystal1 = "none";
			player.Crystal2 = "none";
			player.Crystal3 = "none";
			player.Crystal4 = "none";
			player.StatusPoint = 0;
			player.Str = 1;
			player.Agi = 1;
			player.Vit = 1;
			player.Dex = 1;
			player.Wis = 1;
			player.Stamina = 100;
			player.StaminaMax = 100;
			player.Quests = "none";
			player.hotkey1 = "none";
			player.hotkey2 = "none";
			player.buffA = "none";
			player.buffB = "none";
			player.buffC = "none";
			player.BuffTimeA = 0;
			player.BuffTimeB = 0;
			player.BuffTimeC = 0;
			player.party = "none";
			player.playerInBattle = "none";
			player.playerInAttack = "none";
			player.playerInCast = "none";
			player.playerSit = "none";
			player.SyncPlayerMob = "none";
			player.PlayerExpGet = "0";
			
			String itensList = "";
	        for(int i = 0; i < 16; i++) {
	            if(i == 0) { itensList = itensList + "[hpcan#20]-"; } 
	            if(i > 0) { itensList = itensList + "[NONE]-"; }          
	        }
	        player.Itens = itensList;
	        created = true;
		}
	}

	public Player SendPlayer(){
		return this.player;
	}

	public void UpdatePlayer(Player viewplayer){
		this.player = viewplayer;
	}
	
	public void SetCharNumber(int num) {
		charNumber = num;
	}
	
	public void SetSave(int charnum) {
		
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
		
		if(element.equals("btnsit")) {
			spr_master = atlas_ux.createSprite("sentar");
			spr_master.setSize(20,10);
			spr_master.setPosition(cameraCoordsX - 45,cameraCoordsY + 87);
			return spr_master;
		}
		
		if(element.equals("hotkey1")) {
			spr_master = atlas_ux.createSprite("placebox1");
			spr_master.setSize(20,15);
			spr_master.setPosition(cameraCoordsX - 45,cameraCoordsY + 87);
			return spr_master;
		}
		
		if(element.equals("hotkey2")) {
			spr_master = atlas_ux.createSprite("placebox1");
			spr_master.setSize(20,10);
			spr_master.setPosition(cameraCoordsX - 45,cameraCoordsY + 87);
			return spr_master;
		}
		
		if(element.equals("descartar")) {
			spr_master = atlas_ux.createSprite("placebox2");
			spr_master.setSize(30,16);
			spr_master.setPosition(cameraCoordsX - 45,cameraCoordsY + 87);
			return spr_master;
		}
		
		if(element.equals("controlPC")) {
			spr_master = atlas_ux.createSprite("off");
			spr_master.setSize(30,16);
			spr_master.setPosition(cameraCoordsX - 45,cameraCoordsY + 87);
			return spr_master;
		}
		
		if(element.equals("login")) {
			spr_master = atlas_ux.createSprite("login");
			spr_master.setSize(30,16);
			spr_master.setPosition(cameraCoordsX - 45,cameraCoordsY + 87);
			return spr_master;
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
				if(player.SetUpper.equals("basictop")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(-64, -36); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex.equals("F")) { spr_master.setPosition(-64, -36); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;
			}
			if(num == 2) {
				if(player.SetUpper.equals("basictop")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(-25, -36); spr_master.setScale(-0.3f,0.5f);  }
				if(player.Sex.equals("F")) { spr_master.setPosition(-25, -36); spr_master.setScale(-0.3f,0.5f);  }
				return spr_master;
			}
			if(num == 3) {
				if(player.SetUpper.equals("basictop")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(10, -36); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex.equals("F")) { spr_master.setPosition(10, -36); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;
			}
		}
		
		
		//basicbottomM_front1
		if(type.equals("bottom")) { 
			if(num == 1) {
				if(player.SetUpper.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetBottom + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(-64, -48); spr_master.setScale(-0.3f,0.5f);}
				if(player.Sex.equals("F")) { spr_master.setPosition(-64, -48); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 2) {
				if(player.SetUpper.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetBottom + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(-25f, -48); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex.equals("F")) { spr_master.setPosition(-25f, -48); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 3) {
				if(player.SetUpper.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetBottom + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(10, -48); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex.equals("F")) { spr_master.setPosition(10, -48); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
		}
		
		
		//basicfooterM_front1
		if(type.equals("footer")) { 
			if(num == 1) {
				if(player.SetUpper.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetFooter + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(-64, -55); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex.equals("F")) { spr_master.setPosition(-64.5f, -60); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 2) {
				if(player.SetUpper.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetFooter + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(-25f, -55); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex.equals("F")) { spr_master.setPosition(-25.5f, -60); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
			if(num == 3) {
				if(player.SetUpper.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
				spr_master = atlas_genericset.createSprite(player.SetFooter + player.Sex + "_front1");
				if(player.Sex.equals("M")) { spr_master.setPosition(10, -55); spr_master.setScale(-0.3f,0.5f); }
				if(player.Sex.equals("F")) { spr_master.setPosition(10, -60); spr_master.setScale(-0.3f,0.5f); }
				return spr_master;	
			}
		}		
		return spr_master;
	}
	
	public Sprite GetHairCharTagStation(Player player) {
		
		//hair1_front_green_M
		if(player.Hair.equals("hair1")){ spr_master = atlas_hairs1.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);}
		if(player.Hair.equals("hair2")){ spr_master = atlas_hairs2.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);}
		if(player.Hair.equals("hair3")){ spr_master = atlas_hairs3.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);}
		if(player.Hair.equals("hair4")){ spr_master = atlas_hairs4.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);}
		
		if(player.Sex.equals("M")) {
			spr_master.setPosition(-86, 42); 
			spr_master.setScale(0.2f,0.4f);		
		}
		if(player.Sex.equals("F")) {
			spr_master.setPosition(-86, 42); 
			spr_master.setScale(0.2f,0.4f);		
		}
		
		return spr_master;
	}

	public Sprite GetHairCharTag(Player player,float cameraCoordsX,float cameraCoordsY) {
		
		if(player.Hair.equals("hair1")) { atlas_generichair = atlas_hairs1;}
		if(player.Hair.equals("hair2")) { atlas_generichair = atlas_hairs2;}
		if(player.Hair.equals("hair3")) { atlas_generichair = atlas_hairs3;}
		if(player.Hair.equals("hair4")) { atlas_generichair = atlas_hairs4;}
		//if(player.Hair.equals("hair5")) { atlas_generichair = atlas_hairs5;}
		
		//hair1_front_green_M
		if(player.Sex.equals("M")) {
			spr_master = atlas_generichair.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);
			spr_master.setPosition(cameraCoordsX -115,cameraCoordsY + 71); 
			spr_master.setScale(0.2f,0.4f);		
		}
		if(player.Sex.equals("F")) {
			spr_master = atlas_generichair.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);
			spr_master.setPosition(cameraCoordsX -115,cameraCoordsY + 71); 
			spr_master.setScale(0.2f,0.4f);		
		}
		
		return spr_master;
	}
	
	public Sprite GetHatChar(Player player,String type,float cameraX,float cameraY) {
		
		String side = player.Side;
		
		if(side.equals("front")) { 
			if(player.Hat.equals("hatbunny")) { 
				spr_master = atlas_hats.createSprite("bunnyhat_front");
				spr_master.setPosition(player.PosX - 20,player.PosY + 15);
				spr_master.setScale(0.23f,0.5f);
			}		
		}
		
		if(side.equals("right")) { 
			if(player.Hat.equals("hatbunny")) { 
				spr_master = atlas_hats.createSprite("bunnyhat_right");
				spr_master.setPosition(player.PosX - 20,player.PosY + 10);
				spr_master.setScale(0.21f,0.5f);	
			}		
		}
		
		if(side.equals("left")) { 
			if(player.Hat.equals("hatbunny")) { 
				spr_master = atlas_hats.createSprite("bunnyhat_left");
				spr_master.setPosition(player.PosX - 20,player.PosY + 10);
				spr_master.setScale(0.21f,0.5f);	
			}		
		}
		
		if(side.equals("back")) { 
			if(player.Hat.equals("hatbunny")) { 
				spr_master = atlas_hats.createSprite("bunnyhat_up");
				spr_master.setPosition(player.PosX - 20,player.PosY + 12);
				spr_master.setScale(0.23f,0.5f);
			}		
		}
		
		return spr_master;
	}
	
	public Sprite GetHatItem(Player player, float cameraX, float cameraY) {
		if(player.Hat.equals("hatbunny")) {
			spr_master = atlas_hatsitem.createSprite("hatbunny");
			return spr_master;
		}
		
		return spr_master;
	}
	
	
	
	//[Char movement]//
	public void SetAttackFrame() {
		FrameAtkPlayer = 30;
	}
	
	public Player SetCharMov(Player playerUse, String type) {			
		//Check MovePosition
		if(playerUse.Walk.equals("walk")) {
			if(playerUse.Side.equals("front") && !player.breakwalk.equals("front")) { playerUse.PosY = playerUse.PosY - 0.5f; }
			if(playerUse.Side.equals("back") && !player.breakwalk.equals("back")) { playerUse.PosY = playerUse.PosY + 0.5f; }
			if(playerUse.Side.equals("left") && !player.breakwalk.equals("left")) { playerUse.PosX = playerUse.PosX - 0.5f; }
			if(playerUse.Side.equals("right") && !player.breakwalk.equals("right")) { playerUse.PosX = playerUse.PosX + 0.5f; }
		}
		
		//Check Frames
		if(!playerUse.Walk.equals("no")) { player.countFrame = player.countFrame + 1; }
		if(playerUse.playerInBattle.equals("yes")) { player.countFrame = player.countFrame + 1; }
		if(playerUse.Walk.equals("no")) { playerUse.Frame = 1; }
		if(player.countFrame > 1 && player.countFrame <= 10) { playerUse.Frame = 2; }
		if(player.countFrame >= 10 && player.countFrame <= 20) { playerUse.Frame = 1; }
		if(player.countFrame >= 20 && player.countFrame <= 30) { playerUse.Frame = 3; }
		if(player.countFrame >= 30 && player.countFrame <= 40) { playerUse.Frame = 1; }
		if(player.countFrame >= 40) { player.countFrame = 1; }
				
		return player;
	}
	
	public Sprite GetHairChar(Player player, String menu, float cameraX, float cameraY) {
		
		//hair1_front_green_M
		float posX = player.PosX;
		float posY = player.PosY;
		if(player.Sex.equals("M")) {
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			
			if(player.Side.equals("front")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side.equals("left")) {  spr_master.setPosition(posX -21, posY + 10); }
			if(player.Side.equals("right")) { spr_master.setPosition(posX -19.5f, posY + 10);  }
			spr_master.setScale(0.2f,0.4f);
		}
		if(player.Sex.equals("F")) {
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			
			if(player.Side.equals("front")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -20, posY + 10);  }
			if(player.Side.equals("left")) {  spr_master.setPosition(posX -20.7f, posY + 8.8f); }
			if(player.Side.equals("right")) { spr_master.setPosition(posX -19.5f, posY + 8.8f);  }
			spr_master.setScale(0.2f,0.4f);
		}

		if(menu.equals("yes")){
			player.Side = "front";
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			
			if(player.Sex.equals("M")) {
				spr_master.setPosition(posX -20, posY + 10);
				spr_master.setScale(0.2f,0.4f);
			}
			if(player.Sex.equals("F")) {
				spr_master.setPosition(posX -20, posY + 10);
				spr_master.setScale(0.2f,0.4f);
			}
		}

		if(menu.equals("Show")){
			player.Side = "front";
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			
			if(player.Sex.equals("M")) {
				spr_master.setPosition(cameraX -84.4f, cameraY + 34.8f);
				spr_master.setScale(0.4f,0.7f);
			}
			if(player.Sex.equals("F")) {
				spr_master.setPosition(cameraX -84.4f, cameraY + 34.8f);
				spr_master.setScale(0.4f,0.7f);
			}
		}

		if(player.playerInBattle.equals("yes")){
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "ttack_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "ttack_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "ttack_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "ttack_" + player.Color + "_" + player.Sex); }
			
			spr_master.setPosition(posX -20, posY + 10);
			spr_master.setScale(0.2f,0.4f);
		}
		
		return spr_master;
	}
	
	public Sprite GetTopChar(Player player , String menu, float cameraX, float cameraY) {
		//Top 1
		if(player.SetUpper.equals("basictop")) { atlas_genericset = atlas_basicset; }
		
		float posX = player.PosX;
		float posY = player.PosY;
		//[MALE]
		if(player.Sex.equals("M")) {
			spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("left")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("right")) { spr_master.setPosition(posX -25,posY -5);  }
			spr_master.setScale(0.2f,0.4f);	
			
			if(player.playerInBattle.equals("yes")){
				if(player.Frame == 3) { player.Frame = 2; }
				spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "ttack" + player.Frame);
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);							
			}
			
			if(player.playerInAttack.equals("yes")){
				if(FrameAtkPlayer > 0) {
				if(player.Frame == 3) { player.Frame = 1; }
					spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "ttack4");
					spr_master.setPosition(posX -25,posY -5);
					spr_master.setScale(0.2f,0.4f);	
					FrameAtkPlayer--;
				}
				if(FrameAtkPlayer <= 0) {
					FrameAtkPlayer = 0;
					player.playerInAttack = "no";
				}
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = 1;
				player.Side = "front";
				spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_sit");
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		//[FEMALE]//
		if(player.Sex.equals("F")) {
			spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { spr_master.setPosition(posX -25,posY -4.5f);  }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("left")) { 
				spr_master.setPosition(posX -25,posY -5);  
				if(player.Frame == 2) { spr_master.setPosition(posX -25,posY -4.5f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -26,posY -5.2f);   }
			}
			if(player.Side.equals("right")) { spr_master.setPosition(posX -25,posY -5);  }
			spr_master.setScale(0.2f,0.35f);	
			
			if(player.playerInBattle.equals("yes")){
				if(player.Frame == 3) { player.Frame = 2; }
				spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "ttack" + player.Frame);
				spr_master.setPosition(posX -25,posY -4); 
				spr_master.setScale(0.2f,0.4f);
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = 1;
				player.Side = "front";
				spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_sit");
				spr_master.setPosition(posX -25.2f,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		if(menu.equals("yes")){
			player.Side = "front";
			spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_" + player.Side + 1);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(cameraX + 8, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side = "front";
			spr_master = atlas_genericset.createSprite(player.SetUpper + player.Sex + "_" + player.Side + 1);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(cameraX -89, cameraY + 11.5f);
				spr_master.setScale(0.4f,0.7f);
			}
		}

		return spr_master;
	}
	/// [BOTTOM ] //////
	public Sprite GetBottomChar(Player player , String menu, float cameraX, float cameraY) {
		//Top 1
		if(player.SetBottom.equals("basicbottom")) { atlas_genericset = atlas_basicset; }
		
		float posX = player.PosX;
		float posY = player.PosY;
		
		/// [BOTTOM MALE] //////
		if(player.Sex.equals("M")) {
			spr_master = atlas_basicset.createSprite(player.SetBottom + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { spr_master.setPosition(posX -25,posY -15); }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -14.6f); }
			if(player.Side.equals("right")) { spr_master.setPosition(posX -25,posY -15); }
			if(player.Side.equals("left")) { 	spr_master.setPosition(posX -25,posY -15); }	
			spr_master.setScale(0.2f,0.4f);

			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetBottom + player.Sex + "ttack1");
				spr_master.setPosition(posX -24.5f,posY -15);
				spr_master.setScale(0.2f,0.4f);					
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = 1;
				player.Side = "front";
				spr_master = atlas_genericset.createSprite(player.SetBottom + player.Sex + "_sit");
				spr_master.setPosition(posX -25,posY -15);
				spr_master.setScale(0.2f,0.4f);	
			}
		}
		
		/// [BOTTOM FEMALE] //////
		if(player.Sex.equals("F")) {
			spr_master = atlas_basicset.createSprite(player.SetBottom + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { spr_master.setPosition(posX -25.2f,posY -13f); }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -14.6f); }
			if(player.Side.equals("right")) { 
				spr_master.setPosition(posX -25f,posY -13);
				if(player.Frame == 2) { spr_master.setPosition(posX -25f,posY -13f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -25.5f,posY -14f);   }
			}
			if(player.Side.equals("left")) { 	
				spr_master.setPosition(posX -25f,posY -13);
				if(player.Frame == 2) { spr_master.setPosition(posX -24.6f,posY -13f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -24.8f,posY -14f);   }
			}
			
			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetBottom + player.Sex + "ttack1");
				spr_master.setPosition(posX -25.2f,posY -13f);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = 1;
				player.Side = "front";
				spr_master = atlas_genericset.createSprite(player.SetBottom + player.Sex + "_sit");
				spr_master.setPosition(posX -25.2f,posY -12);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			spr_master.setScale(0.2f,0.4f);
		}

		if(menu.equals("yes")){
			player.Side = "front";
			spr_master = atlas_basicset.createSprite(player.SetBottom + player.Sex + "_" + player.Side + 1);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(cameraX - 5.5f, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side = "front";
			spr_master = atlas_basicset.createSprite(player.SetBottom + player.Sex + "_" + player.Side + 1);
			if(player.Sex.equals("M")) { 
				spr_master.setPosition(cameraX -89, cameraY - 5);
				spr_master.setScale(0.4f,0.7f);
			}
			if(player.Sex.equals("F")) { 
				spr_master.setPosition(cameraX -89, cameraY - 5);
				spr_master.setScale(0.4f,0.7f);
			}
		}
		
		return spr_master;
	}
	
	/// [FOOTER ] //////
	public Sprite GetFooterChar(Player player , String menu, float cameraX, float cameraY) {
		//Top 1
		if(player.SetFooter.equals("basicfooter")) { atlas_genericset = atlas_basicset; }
		
		float posX = player.PosX;
		float posY = player.PosY;
		/// [FOOTER MALE ] //////
		if(player.Sex.equals("M")) {
			spr_master = atlas_basicset.createSprite(player.SetFooter + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { spr_master.setPosition(posX -25,posY -21); }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -21); }	
			if(player.Side.equals("left")) { 
				spr_master.setPosition(posX -25.4f,posY -19.5f); 
				if(player.Frame == 2) { spr_master.setPosition(posX -24.8f,posY -18f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -24.8f,posY -18f);   }
			}	
			if(player.Side.equals("right")) { 
				spr_master.setPosition(posX -24.8f,posY -19.2f); 
				if(player.Frame == 2) { spr_master.setPosition(posX -24.8f,posY -18f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -24.8f,posY -18f);   }
			}
			spr_master.setScale(0.2f,0.4f);	
			
			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetFooter + player.Sex + "_front2");
				spr_master.setPosition(posX -25,posY -21);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = 1;
				player.Side = "front";
				spr_master = atlas_genericset.createSprite(player.SetFooter + player.Sex + "_sit");
				spr_master.setPosition(posX -25,posY -18);
				spr_master.setScale(0.2f,0.4f);	
			}
		}
		
		/// [FOOTER FEMALE ] //////
		if(player.Sex.equals("F")) {
			spr_master = atlas_basicset.createSprite(player.SetFooter + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(posX -25f,posY -22.5f);
				if(player.Frame == 2) { spr_master.setPosition(posX -25f,posY -18.5f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -25f,posY -18.5f);   }
			
			}
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -21); }	
			if(player.Side.equals("left")) { 
				spr_master.setPosition(posX -25.4f,posY -21.5f); 
				if(player.Frame == 2) { spr_master.setPosition(posX -25f,posY -22f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -25.5f,posY -23f);   }
			}	
			if(player.Side.equals("right")) { 
				spr_master.setPosition(posX -24.5f,posY -21.5f); 
				if(player.Frame == 2) { spr_master.setPosition(posX -24.6f,posY -22f);   }
				if(player.Frame == 3) { spr_master.setPosition(posX -25.6f,posY -23f);  }
			}
			spr_master.setScale(0.2f,0.4f);
			
			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_basicset.createSprite(player.SetFooter + player.Sex + "ttack1");
				spr_master.setPosition(posX -25f,posY -19.5f);
				spr_master.setScale(0.2f,0.4f);								
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = 1;
				player.Side = "front";
				spr_master = atlas_genericset.createSprite(player.SetFooter + player.Sex + "_sit");
				spr_master.setPosition(posX -25.2f,posY -12);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		if(menu.equals("yes")){
			player.Side = "front";
			spr_master = atlas_basicset.createSprite(player.SetFooter + player.Sex + "_" + player.Side + 1);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(cameraX + 22, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side = "front";
			spr_master = atlas_basicset.createSprite(player.SetFooter + player.Sex + "_" + player.Side + 1);
			if(player.Sex.equals("M")) { 
				spr_master.setPosition(cameraX -89, cameraY - 15);
				spr_master.setScale(0.4f,0.7f);
			}
			if(player.Sex.equals("F")) { 
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
		
		if(player.Sex.equals("M")) {
			if(nameItem.equals("basictop")) { spr_master = atlas_cloth.createSprite("basictopM");}
			if(nameItem.equals("basicbottom")) { spr_master = atlas_cloth.createSprite("basicbottomM"); }
			if(nameItem.equals("basicfooter")) { spr_master = atlas_cloth.createSprite("basicfooterM"); }
		}
		
		if(player.Sex.equals("F")) {
			if(nameItem.equals("basictop")) { spr_master = atlas_cloth.createSprite("basictopF"); }
			if(nameItem.equals("basicbottom")) { spr_master = atlas_cloth.createSprite("basicbottomF"); }
			if(nameItem.equals("basicfooter")) { spr_master = atlas_cloth.createSprite("basicfooterF"); }
		}
		
		//Facas Aprendiz
		if(nameItem.equals("basicknife")) { spr_master = atlas_weapon.createSprite("basicknife"); }
		if(nameItem.equals("doubleedgeknife")) { spr_master = atlas_weapon.createSprite("doubleedgeknife"); }
		
		//Espadas
		if(nameItem.equals("woodsword")) { spr_master = atlas_weapon.createSprite("woodsword"); }
		if(nameItem.equals("sabersword")) { spr_master = atlas_weapon.createSprite("sabersword"); }
		if(nameItem.equals("venomsword")) { spr_master = atlas_weapon.createSprite("venomsword"); }
		if(nameItem.equals("edgesword")) { spr_master = atlas_weapon.createSprite("edgesword"); }
		if(nameItem.equals("knightsword")) { spr_master = atlas_weapon.createSprite("knightsword"); }
		if(nameItem.equals("ragesword")) { spr_master = atlas_weapon.createSprite("ragesword"); }
		//if(nameItem.equals("curved_sword")) { spr_master = atlas_items.createSprite("curvedsword"); }
		//if(nameItem.equals("cristal_sword")) { spr_master = atlas_items.createSprite("cristalsword"); }
		//if(nameItem.equals("serpent_sword")) { spr_master = atlas_items.createSprite("serpentsword"); }
		//if(nameItem.equals("flame_sword")) { spr_master = atlas_items.createSprite("flamesword"); }
		
		
		//Cajados
		if(nameItem.equals("stickrod")) { spr_master = atlas_weapon.createSprite("stickrod"); }
		if(nameItem.equals("gloomrod")) { spr_master = atlas_weapon.createSprite("gloomrod"); }
		if(nameItem.equals("gemrod")) { spr_master = atlas_weapon.createSprite("gemrod"); }
		if(nameItem.equals("lightwieldrod")) { spr_master = atlas_weapon.createSprite("lightwieldrod"); }
		if(nameItem.equals("serpentrod")) { spr_master = atlas_weapon.createSprite("serpentrod"); }
		if(nameItem.equals("clerigrod")) { spr_master = atlas_weapon.createSprite("clerigrod"); }
		//if(nameItem.equals("death_rod")) { spr_master = atlas_items.createSprite("deathrod"); }
		//if(nameItem.equals("butterfly_rod")) { spr_master = atlas_items.createSprite("butterflyrod"); }
		//if(nameItem.equals("star_rod")) { spr_master = atlas_items.createSprite("starrod"); }
		
		//Pistolas
		if(nameItem.equals("basicpistol")) { spr_master = atlas_weapon.createSprite("basicpistol"); }
		if(nameItem.equals("revolverpistol")) { spr_master = atlas_weapon.createSprite("revolverpistol"); }
		if(nameItem.equals("lightpistol")) { spr_master = atlas_weapon.createSprite("lightpistol"); }
		if(nameItem.equals("turretpistol")) { spr_master = atlas_weapon.createSprite("turretpistol"); }
		if(nameItem.equals("riflepistol")) { spr_master = atlas_weapon.createSprite("shooterpistol"); }
		//if(nameItem.equals("heavymachine_pistol")) { spr_master = atlas_items.createSprite("heavymachinepistol"); }
		//if(nameItem.equals("cannon_pistol")) { spr_master = atlas_items.createSprite("cannonpistol"); }
		//if(nameItem.equals("shark_pistol")) { spr_master = atlas_items.createSprite("sharkpistol"); }
		
		//Facas
		if(nameItem.equals("basicdagger")) { spr_master = atlas_weapon.createSprite("basicdagger"); }
		if(nameItem.equals("revolverpistol")) { spr_master = atlas_weapon.createSprite("revolverpistol"); }
		if(nameItem.equals("lightpistol")) { spr_master = atlas_weapon.createSprite("lightpistol"); }
		if(nameItem.equals("turretpistol")) { spr_master = atlas_weapon.createSprite("turretpistol"); }
		if(nameItem.equals("riflepistol")) { spr_master = atlas_weapon.createSprite("riflepistol"); }
		if(nameItem.equals("shooterpistol")) { spr_master = atlas_weapon.createSprite("shooterpistol"); }
		//if(nameItem.equals("poison_dagger")) { spr_master = atlas_items.createSprite("poisondagger"); }
		//if(nameItem.equals("marine_dagger")) { spr_master = atlas_items.createSprite("marinedagger"); }
		//if(nameItem.equals("thunder_dagger")) { spr_master = atlas_items.createSprite("thunderdagger"); }
		
		//Machados
		if(nameItem.equals("basicaxe")) { spr_master = atlas_weapon.createSprite("basicaxe"); }
		if(nameItem.equals("pickaxe")) { spr_master = atlas_weapon.createSprite("pickaxe"); }		
		if(nameItem.equals("killeraxe")) { spr_master = atlas_weapon.createSprite("killeraxe"); }
		if(nameItem.equals("hammeraxe")) { spr_master = atlas_weapon.createSprite("hammeraxe"); }		
		if(nameItem.equals("scytheaxe")) { spr_master = atlas_weapon.createSprite("scytheaxe"); }
		if(nameItem.equals("anchoraxe")) { spr_master = atlas_weapon.createSprite("anchoraxe"); }
		//if(nameItem.equals("guitarxe")) { spr_master = atlas_items.createSprite("guitaraxe"); }
		//if(nameItem.equals("bloodteethxe")) { spr_master = atlas_items.createSprite("bloodteethaxe"); }
		
		
		
		if(nameItem.equals("hpcan")) { spr_master = atlas_food.createSprite("hpcan"); }
		if(nameItem.equals("mpcan")) { spr_master = atlas_food.createSprite("mpcan"); }
		if(nameItem.equals("stcan")) { spr_master = atlas_food.createSprite("stcan"); }
		
		if(nameItem.equals("hatbear")) { spr_master = atlas_hatsitem.createSprite("hatbear"); }
		if(nameItem.equals("hatblackglass")) { spr_master = atlas_hatsitem.createSprite("hatblackglass"); }
		if(nameItem.equals("hatheadset")) { spr_master = atlas_hatsitem.createSprite("hatheadset"); }
		if(nameItem.equals("hatmagician")) { spr_master = atlas_hatsitem.createSprite("hatmagician"); }
		if(nameItem.equals("hatpirate")) { spr_master = atlas_hatsitem.createSprite("hatpirate"); }
		if(nameItem.equals("hatsanta")) { spr_master = atlas_hatsitem.createSprite("hatsanta"); }
		if(nameItem.equals("hatslime")) { spr_master = atlas_hatsitem.createSprite("hatslime"); }
		if(nameItem.equals("hatsunglasses")) { spr_master = atlas_hatsitem.createSprite("hatsunglasses"); }
		if(nameItem.equals("hatslime")) { spr_master = atlas_hatsitem.createSprite("hatslime"); }
		if(nameItem.equals("hattimer")) { spr_master = atlas_hatsitem.createSprite("hattimer"); }
		if(nameItem.equals("hatbutterfly")) { spr_master = atlas_hatsitem.createSprite("hatbutterfly"); }
		if(nameItem.equals("hatcapoult")) { spr_master = atlas_hatsitem.createSprite("hatcapoult"); }
		if(nameItem.equals("hatcooker")) { spr_master = atlas_hatsitem.createSprite("hatcooker"); }
		if(nameItem.equals("hatfashionglasses")) { spr_master = atlas_hatsitem.createSprite("hatfashionglasses"); }
		if(nameItem.equals("hatbunny")) { spr_master = atlas_hatsitem.createSprite("hatbunny"); }
		
		
		if(nameItem.equals("lootfragmentoamarelo")) { spr_master = atlas_cristais.createSprite("lootfragmentoamarelo"); }
		if(nameItem.equals("lootfragmentoazul")) { spr_master = atlas_cristais.createSprite("lootfragmentoazul"); }
		if(nameItem.equals("lootfragmentoroxo")) { spr_master = atlas_cristais.createSprite("lootfragmentoroxo"); }
		if(nameItem.equals("lootfragmentoverde")) { spr_master = atlas_cristais.createSprite("lootfragmentoverde"); }
		if(nameItem.equals("lootfragmentovermelho")) { spr_master = atlas_cristais.createSprite("lootfragmentovermelho"); }
		
		
		if(nameItem.equals("lootblop")) { spr_master = atlas_lootmob.createSprite("lootblop"); }
		if(nameItem.equals("lootpoisonleaf")) { spr_master = atlas_lootmob.createSprite("lootpoisonleaf"); }
		if(nameItem.equals("lootmushroom")) { spr_master = atlas_lootmob.createSprite("lootmushroom"); }
		if(nameItem.equals("lootpoisonleaf")) { spr_master = atlas_lootmob.createSprite("lootpoisonleaf"); }
		if(nameItem.equals("lootfang")) { spr_master = atlas_lootmob.createSprite("lootfang"); }
		
		
		return spr_master;
	}
	
				//[BAG]
				public void UseItem(int numItem) {
				String[] lstItem = player.Itens.split("-");
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
					if(itemName.equals("hpcan")) { player.Hp = player.Hp + 10; if(player.Hp > player.HpMax) { player.Hp = player.HpMax; } equipable = false;}	
					if(itemName.equals("garrafadrink")) { player.Hp = player.Hp + 100; if(player.Hp > player.HpMax) { player.Hp = player.HpMax; }equipable = false;}			
					if(itemName.equals("mpcan")) { player.Mp = player.Mp + 25; if(player.Mp > player.MpMax) { player.Mp = player.MpMax; } equipable = false;}	
					if(itemName.equals("garrafamagica")) { player.Mp = player.Mp + 50; if(player.Mp > player.MpMax) { player.Mp = player.MpMax; } equipable = false;}
					if(itemName.equals("stcan")) { player.Stamina = player.Stamina + 5; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } equipable = false;}	
					if(itemName.equals("garrafasuco")) { player.Stamina = player.Stamina + 30; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } equipable = false;}
					if(itemName.equals("fries")) { player.Stamina = player.Stamina + 15; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } equipable = false;}	
					if(itemName.equals("pizza")) { player.Stamina = player.Stamina + 5; if(player.Stamina > player.StaminaMax) { player.Stamina = player.StaminaMax; } } player.Hp = player.Hp + 30; if(player.Hp > player.HpMax) { player.Hp = player.HpMax; equipable = false;}	
					
					if(itemName.equals("basictop")) {  if(player.SetUpper.equals("basictop")){ return; } else { AddItemBag(player.SetUpper); player.SetUpper = "basictop"; lstItem = player.Itens.split("-"); }}
					if(itemName.equals("basicbottom")) {  if(player.SetUpper.equals("basicbottom")){ return; } else { AddItemBag(player.SetUpper); player.SetUpper = "basicbottom"; lstItem = player.Itens.split("-"); }}
					if(itemName.equals("basicfooter")) {  if(player.SetUpper.equals("basicfooter")){ return; } else { AddItemBag(player.SetUpper); player.SetUpper = "basicfooter"; lstItem = player.Itens.split("-"); }}
						
					
					
					//aprendiz
					if(itemName.equals("basicknife")) {  
						if(player.Weapon.equals("basicknife")){ return; } 
						if(!player.Weapon.equals("basicknife")) { AddItemBag(player.Weapon); player.Weapon = "basic_knife"; lstItem = player.Itens.split("-"); }
						
						if(player.Weapon.equals("doubleedgeknife")){ return; } 
						if(!player.Weapon.equals("doubleedgeknife")) { AddItemBag(player.Weapon); player.Weapon = "doubleedgeknife"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("doubleedgeknife")) {  
						if(player.Weapon.equals("doubleedgeknife")){ return; } 
						if(!player.Weapon.equals("doubleedgeknife")) { AddItemBag(player.Weapon); player.Weapon = "doubleedgeknife"; lstItem = player.Itens.split("-"); }
					}
					//espadachim
					if(itemName.equals("woodsword")) {  
						if(!player.Job.equals("Espadachim")){ return; } 
						if(player.Weapon.equals("woodsword")){ return; } 
						if(!player.Weapon.equals("woodsword")) { AddItemBag(player.Weapon); player.Weapon = "woodsword"; lstItem = player.Itens.split("-"); }					
					}
					if(itemName.equals("sabersword")) {  
						if(!player.Job.equals("Espadachim")){ return; } 
						if(player.Weapon.equals("sabersword")){ return; } 
						if(!player.Weapon.equals("sabersword")) { AddItemBag(player.Weapon); player.Weapon = "sabersword"; lstItem = player.Itens.split("-"); }		
					}
					if(itemName.equals("venomsword")) {  
						if(!player.Job.equals("Espadachim")){ return; } 
						if(player.Weapon.equals("venomsword")){ return; } 
						if(!player.Weapon.equals("venomsword")) { AddItemBag(player.Weapon); player.Weapon = "venomsword"; lstItem = player.Itens.split("-"); }		
					}
					if(itemName.equals("edgesword")) {  
						if(!player.Job.equals("Espadachim")){ return; } 
						if(player.Weapon.equals("edgesword")){ return; } 
						if(!player.Weapon.equals("edgesword")) { AddItemBag(player.Weapon); player.Weapon = "edgesword"; lstItem = player.Itens.split("-"); }		
					}
					if(itemName.equals("knightsword")) {  
						if(!player.Job.equals("Espadachim")){ return; } 
						if(player.Weapon.equals("knightsword")){ return; } 
						if(!player.Weapon.equals("knightsword")) { AddItemBag(player.Weapon); player.Weapon = "knightsword"; lstItem = player.Itens.split("-"); }		
					}
					if(itemName.equals("ragesword")) {  
						if(!player.Job.equals("Espadachim")){ return; } 
						if(player.Weapon.equals("ragesword")){ return; } 
						if(!player.Weapon.equals("ragesword")) { AddItemBag(player.Weapon); player.Weapon = "ragesword"; lstItem = player.Itens.split("-"); }		
					}
					
					//Feiticeiro
					if(itemName.equals("stickrod")) { 
						if(!player.Job.equals("Feiticeiro") || !player.Job.equals("Curandeiro")){ return; } 
						if(player.Weapon.equals("stickrod")){ return; } 
						if(!player.Weapon.equals("stickrod")) { AddItemBag(player.Weapon); player.Weapon = "stickrod"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("gloomrod")) { 
						if(!player.Job.equals("Feiticeiro") || !player.Job.equals("Curandeiro")){ return; } 
						if(player.Weapon.equals("gloomrod")){ return; } 
						if(!player.Weapon.equals("gloomrod")) { AddItemBag(player.Weapon); player.Weapon = "gloomrod"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("gemrod")) { 
						if(!player.Job.equals("Feiticeiro") || !player.Job.equals("Curandeiro")){ return; } 
						if(player.Weapon.equals("gemrod")){ return; } 
						if(!player.Weapon.equals("gemrod")) { AddItemBag(player.Weapon); player.Weapon = "gemrod"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("lightwieldrod")) { 
						if(!player.Job.equals("Feiticeiro") || !player.Job.equals("Curandeiro")){ return; } 
						if(player.Weapon.equals("lightwieldrod")){ return; } 
						if(!player.Weapon.equals("lightwieldrod")) { AddItemBag(player.Weapon); player.Weapon = "lightwieldrod"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("serpentrod")) { 
						if(!player.Job.equals("Feiticeiro") || !player.Job.equals("Curandeiro")){ return; } 
						if(player.Weapon.equals("serpentrod")){ return; } 
						if(!player.Weapon.equals("serpentrod")) { AddItemBag(player.Weapon); player.Weapon = "serpentrod"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("clerigrod")) { 
						if(!player.Job.equals("Feiticeiro") || !player.Job.equals("Curandeiro")){ return; } 
						if(player.Weapon.equals("clerigrod")){ return; } 
						if(!player.Weapon.equals("clerigrod")) { AddItemBag(player.Weapon); player.Weapon = "clerigrod"; lstItem = player.Itens.split("-"); }
					}
					
					//Pistoleiro
					if(itemName.equals("basicpistol")) { 
						if(!player.Job.equals("Pistoleiro")){ return; } 
						if(player.Weapon.equals("basicpistol")){ return; } 
						if(!player.Weapon.equals("basicpistol")) {  AddItemBag(player.Weapon); player.Weapon = "basicpistol"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("revolverpistol")) { 
						if(!player.Job.equals("Pistoleiro")){ return; } 
						if(player.Weapon.equals("revolverpistol")){ return; } 
						if(!player.Weapon.equals("revolverpistol")) {  AddItemBag(player.Weapon); player.Weapon = "revolverpistol"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("lightpistol")) { 
						if(!player.Job.equals("Pistoleiro")){ return; } 
						if(player.Weapon.equals("lightpistol")){ return; } 
						if(!player.Weapon.equals("lightpistol")) {  AddItemBag(player.Weapon); player.Weapon = "lightpistol"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("turretpistol")) { 
						if(!player.Job.equals("Pistoleiro")){ return; } 
						if(player.Weapon.equals("turretpistol")){ return; } 
						if(!player.Weapon.equals("turretpistol")) {  AddItemBag(player.Weapon); player.Weapon = "turretpistol"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("riflepistol")) { 
						if(!player.Job.equals("Pistoleiro")){ return; } 
						if(player.Weapon.equals("riflepistol")){ return; } 
						if(!player.Weapon.equals("riflepistol")) {  AddItemBag(player.Weapon); player.Weapon = "riflepistol"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("shooterpistol")) { 
						if(!player.Job.equals("Pistoleiro")){ return; } 
						if(player.Weapon.equals("shooterpistol")){ return; } 
						if(!player.Weapon.equals("shooterpistol")) {  AddItemBag(player.Weapon); player.Weapon = "shooterpistol"; lstItem = player.Itens.split("-"); }
					}
					
					//Ladrao
					if(itemName.equals("basicdagger")) { 
						if(!player.Job.equals("Ladrao")){ return; } 
						if(player.Weapon.equals("basicdagger")){ return; } 
						if(!player.Weapon.equals("basicdagger")) { AddItemBag(player.Weapon); player.Weapon = "basicdagger"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("edgedagger")) { 
						if(!player.Job.equals("Ladrao")){ return; } 
						if(player.Weapon.equals("edgedagger")){ return; } 
						if(!player.Weapon.equals("edgedagger")) { AddItemBag(player.Weapon); player.Weapon = "edgedagger"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("poisondagger")) { 
						if(!player.Job.equals("Ladrao")){ return; } 
						if(player.Weapon.equals("poisondagger")){ return; } 
						if(!player.Weapon.equals("poisondagger")) { AddItemBag(player.Weapon); player.Weapon = "poisondagger"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("marinedagger")) { 
						if(!player.Job.equals("Ladrao")){ return; } 
						if(player.Weapon.equals("marinedagger")){ return; } 
						if(!player.Weapon.equals("marinedagger")) { AddItemBag(player.Weapon); player.Weapon = "marinedagger"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("triplodagger")) { 
						if(!player.Job.equals("Ladrao")){ return; } 
						if(player.Weapon.equals("triplodagger")){ return; } 
						if(!player.Weapon.equals("triplodagger")) { AddItemBag(player.Weapon); player.Weapon = "triplodagger"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("thunderdagger")) { 
						if(!player.Job.equals("Ladrao")){ return; } 
						if(player.Weapon.equals("thunderdagger")){ return; } 
						if(!player.Weapon.equals("thunderdagger")) { AddItemBag(player.Weapon); player.Weapon = "thunderdagger"; lstItem = player.Itens.split("-"); }
					}
					
					//Batedor
					if(itemName.equals("basicaxe")) { 
						if(!player.Job.equals("Batedor")){ return; } 
						if(player.Weapon.equals("basicaxe")){ return; } 
						if(!player.Weapon.equals("basicaxe")) { AddItemBag(player.Weapon); player.Weapon = "basicaxe"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("pickaxe")) { 
						if(!player.Job.equals("Batedor")){ return; } 
						if(player.Weapon.equals("pickaxe")){ return; } 
						if(!player.Weapon.equals("pickaxe")) { AddItemBag(player.Weapon); player.Weapon = "pickaxe"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("killeraxe")) { 
						if(!player.Job.equals("Batedor")){ return; } 
						if(player.Weapon.equals("killeraxe")){ return; } 
						if(!player.Weapon.equals("killeraxe")) { AddItemBag(player.Weapon); player.Weapon = "killeraxe"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("hammeraxe")) { 
						if(!player.Job.equals("Batedor")){ return; } 
						if(player.Weapon.equals("hammeraxe")){ return; } 
						if(!player.Weapon.equals("hammeraxe")) { AddItemBag(player.Weapon); player.Weapon = "hammeraxe"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("scytheaxe")) { 
						if(!player.Job.equals("Batedor")){ return; } 
						if(player.Weapon.equals("scytheaxe")){ return; } 
						if(!player.Weapon.equals("scytheaxe")) { AddItemBag(player.Weapon); player.Weapon = "scytheaxe"; lstItem = player.Itens.split("-"); }
					}
					if(itemName.equals("anchoraxe")) { 
						if(!player.Job.equals("Batedor")){ return; } 
						if(player.Weapon.equals("anchoraxe")){ return; } 
						if(!player.Weapon.equals("anchoraxe")) { AddItemBag(player.Weapon); player.Weapon = "anchoraxe"; lstItem = player.Itens.split("-"); }
					}
								
					//Hats
					
					if(itemName.equals("hatbunny")) { 
						if(player.Hat.equals("hatbunny")){ return; }
						if(player.Hat.equals("none")){ player.Hat = "hatbunny"; }
						if(!player.Hat.equals("hatbunny")) { AddItemBag(player.Hat); player.Hat = "hatbunny"; lstItem = player.Itens.split("-"); equipable = true; }	
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
					if(itemName.equals("blue_crystal_intextra") && !equipable) { UseCrystal(itemName); equipable = true;  }
					if(itemName.equals("blue_crystal_intextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("blue_crystal_intextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("green_crystal_lukextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("green_crystal_lukextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("green_crystal_lukextra") && !equipable) { UseCrystal(itemName); equipable = true; }
								
					if(itemName.equals("purple_crystal_vitextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("purple_crystal_vitextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("purple_crystal_vitextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("yellow_crystalgiextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("yellow_crystalgiextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("yellow_crystalgiextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("red_crystal_strextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("red_crystal_strextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("red_crystal_strextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("grey_crystal_dexextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("grey_crystal_dexextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("grey_crystal_dexextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					
					if(itemName.equals("orange_crystal_resextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("orange_crystal_resextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					if(itemName.equals("orange_crystal_resextra") && !equipable) { UseCrystal(itemName); equipable = true; }
					
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
						player.Itens = lstitensFinal;
					}
					else {
						itemName = "[" + itemName + "#" + qtd + "]"; 
						lstItem[numItem] = itemName;
						lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
						lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
						player.Itens = lstitensFinal;
					}			
				}
			}
			
			public void UnequipHat() {
				String nameHat = player.Hat;
				if(nameHat.equals("none")) { return; }
				player.Hat = "none";
				AddItemBag(nameHat);
			}
			
			
			public void UseCrystal(String item) {
				if(player.Crystal1.equals("none")) { player.Crystal1 = item; ApplyCrystals(item); return; }
				if(player.Crystal2.equals("none")) { player.Crystal2 = item; ApplyCrystals(item); return; }
				if(player.Crystal3.equals("none")) { player.Crystal3 = item; ApplyCrystals(item); return; }
				if(player.Crystal4.equals("none")) { player.Crystal4 = item; ApplyCrystals(item); return; }
			}
			
			public void ApplyCrystals(String item) {
				
				if(item.equals("blue_crystal_intextra")) { player.Wis = player.Wis + 2; player.MpMax = player.MpMax + 20; }	
				if(item.equals("blue_crystal_intextra")) { player.Wis = player.Wis + 5; player.MpMax = player.MpMax + 50; }
				if(item.equals("blue_crystal_intextra")) { player.Wis = player.Wis + 10; player.MpMax = player.MpMax + 100; }
				if(item.equals("blue_crystal_intextra_4")) { player.Wis = player.Wis + 15; player.MpMax = player.MpMax + 200; }
				
				if(item.equals("green_crystal_lukextra")) { player.Luk = player.Luk + 2;  }	
				if(item.equals("green_crystal_lukextra")) { player.Luk = player.Luk + 5;  }
				if(item.equals("green_crystal_lukextra")) { player.Luk = player.Luk + 10; }
				if(item.equals("green_crystal_lukextra_4")) { player.Luk = player.Luk + 15; }
				
				if(item.equals("purple_crystal_vitextra")) { player.Vit = player.Vit + 2; player.MpMax = player.HpMax + 20; }	
				if(item.equals("purple_crystal_vitextra")) { player.Vit = player.Vit + 5; player.MpMax = player.HpMax + 50; }
				if(item.equals("purple_crystal_vitextra")) { player.Vit = player.Vit + 10; player.MpMax = player.HpMax + 100; }
				if(item.equals("purple_crystal_vitextra_4")) { player.Vit = player.Vit + 15; player.MpMax = player.HpMax + 200; }
				
				if(item.equals("yellow_crystalgiextra")) { player.Agi = player.Agi + 2; player.AtkTimerMax = player.AtkTimerMax - 2; }	
				if(item.equals("yellow_crystalgiextra")) { player.Agi = player.Agi + 5; player.AtkTimerMax = player.AtkTimerMax - 4; }
				if(item.equals("yellow_crystalgiextra")) { player.Agi = player.Agi + 10; player.AtkTimerMax = player.AtkTimerMax - 6; }
				if(item.equals("yellow_crystalgiextra_4")) { player.Agi = player.Agi + 15; player.AtkTimerMax = player.AtkTimerMax - 8; }
				
				if(item.equals("red_crystal_strextra")) { player.Str = player.Str + 2; }	
				if(item.equals("red_crystal_strextra")) { player.Str = player.Str + 5; }
				if(item.equals("red_crystal_strextra")) { player.Str = player.Str + 10; }
				if(item.equals("red_crystal_strextra_4")) { player.Str = player.Str + 15; }
				
				if(item.equals("grey_crystal_dexextra")) { player.Dex = player.Dex + 2; }	
				if(item.equals("grey_crystal_dexextra")) { player.Dex = player.Dex + 5; }
				if(item.equals("grey_crystal_dexextra")) { player.Dex = player.Dex + 10; }
				if(item.equals("grey_crystal_dexextra_4")) { player.Dex = player.Dex + 15; }
				
				if(item.equals("orange_crystal_resextra")) { player.Res = player.Res + 2; player.StaminaMax = player.StaminaMax + 20; player.regenTimeMax = player.regenTimeMax - 200; }	
				if(item.equals("orange_crystal_resextra")) { player.Res = player.Res + 5; player.StaminaMax = player.StaminaMax + 50; player.regenTimeMax = player.regenTimeMax - 400; }
				if(item.equals("orange_crystal_resextra")) { player.Res = player.Res + 10; player.StaminaMax = player.StaminaMax + 100; player.regenTimeMax = player.regenTimeMax - 600; }
				if(item.equals("orange_crystal_resextra_4")) { player.Res = player.Res + 15; player.StaminaMax = player.StaminaMax + 200; player.regenTimeMax = player.regenTimeMax - 800; }
				
			}
			
			public void RemoveCrystals(int num) {
				
				if(num == 1 && player.Crystal1.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal2 = "none";return; }	
				if(num == 3 && player.Crystal3.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 2; player.MpMax = player.MpMax - 20; player.Crystal4 = "none"; return; }	
				
				if(num == 1 && player.Crystal1.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal2 = "none"; return; }	
				if(num == 3 && player.Crystal3.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 5; player.MpMax = player.MpMax - 50; player.Crystal4 = "none"; return; }
				
				if(num == 1 && player.Crystal1.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal2 = "none"; return; }	
				if(num == 3 && player.Crystal3.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("blue_crystal_intextra")) { AddItemBag("blue_crystal_intextra"); player.Wis = player.Wis - 10; player.MpMax = player.MpMax - 100; player.Crystal4 = "none"; return; }
				
				if(num == 1 && player.Crystal1.equals("blue_crystal_intextra_4")) { AddItemBag("blue_crystal_intextra_4"); player.Wis = player.Wis - 15; player.MpMax = player.MpMax - 200; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("blue_crystal_intextra_4")) { AddItemBag("blue_crystal_intextra_4"); player.Wis = player.Wis - 15; player.MpMax = player.MpMax - 200; player.Crystal2 = "none"; return; }	
				if(num == 3 && player.Crystal3.equals("blue_crystal_intextra_4")) { AddItemBag("blue_crystal_intextra_4"); player.Wis = player.Wis - 15; player.MpMax = player.MpMax - 200; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("blue_crystal_intextra_4")) { AddItemBag("blue_crystal_intextra_4"); player.Wis = player.Wis - 15; player.MpMax = player.MpMax - 200; player.Crystal4 = "none"; return; }
				
				
				if(num == 1 && player.Crystal1.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 2; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 2; player.Crystal2 = "none"; return; }	
				if(num == 3 && player.Crystal3.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 2; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 2; player.Crystal4 = "none"; return; }	
				
				if(num == 1 && player.Crystal1.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 5; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 5; player.Crystal2 = "none"; return; }	
				if(num == 3 && player.Crystal3.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 5; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk- 5;  player.Crystal4 = "none"; return; }
				
				if(num == 1 && player.Crystal1.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 10; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 10; player.Crystal2 = "none"; return; }	
				if(num == 3 && player.Crystal3.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 10; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("green_crystal_lukextra")) { AddItemBag("green_crystal_lukextra"); player.Luk = player.Luk - 10; player.Crystal4 = "none"; return; }
				
				if(num == 1 && player.Crystal1.equals("green_crystal_lukextra_4")) { AddItemBag("green_crystal_lukextra_4"); player.Luk = player.Luk - 15; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("green_crystal_lukextra_4")) { AddItemBag("green_crystal_lukextra_4"); player.Luk = player.Luk - 15; player.Crystal2 = "none"; return; }	
				if(num == 3 && player.Crystal3.equals("green_crystal_lukextra_4")) { AddItemBag("green_crystal_lukextra_4"); player.Luk = player.Luk - 15; player.Crystal3 = "none"; return; }	
				if(num == 4 && player.Crystal4.equals("green_crystal_lukextra_4")) { AddItemBag("green_crystal_lukextra_4"); player.Luk = player.Luk - 15; player.Crystal4 = "none"; return; }
				
				if(num == 1 && player.Crystal1.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 2; player.Crystal1 = "none"; player.MpMax = player.HpMax - 20; return; }	
				if(num == 2 && player.Crystal2.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 2; player.Crystal2 = "none";  player.MpMax = player.HpMax - 20; return; }	
				if(num == 3 && player.Crystal3.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 2; player.Crystal3 = "none";  player.MpMax = player.HpMax - 20; return; }	
				if(num == 4 && player.Crystal4.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 2; player.Crystal4 = "none";  player.MpMax = player.HpMax - 20; return; }	
				
				if(num == 1 && player.Crystal1.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 5; player.Crystal1 = "none";  player.MpMax = player.HpMax - 50; return; }	
				if(num == 2 && player.Crystal2.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 5; player.Crystal2 = "none";  player.MpMax = player.HpMax - 50; return; }	
				if(num == 3 && player.Crystal3.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 5; player.Crystal3 = "none";  player.MpMax = player.HpMax - 50; return; }	
				if(num == 4 && player.Crystal4.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 5; player.Crystal4 = "none";  player.MpMax = player.HpMax - 50; return; }
				
				if(num == 1 && player.Crystal1.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 10; player.Crystal1 = "none";  player.MpMax = player.HpMax - 100; return; }	
				if(num == 2 && player.Crystal2.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 10; player.Crystal2 = "none";  player.MpMax = player.HpMax - 100; return; }	
				if(num == 3 && player.Crystal3.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 10; player.Crystal3 = "none";  player.MpMax = player.HpMax - 100; return; }	
				if(num == 4 && player.Crystal4.equals("purple_crystal_vitextra")) { AddItemBag("purple_crystal_vitextra"); player.Vit = player.Vit - 10; player.Crystal4 = "none";  player.MpMax = player.HpMax - 100; return; }
				
				if(num == 1 && player.Crystal1.equals("purple_crystal_vitextra_4")) { AddItemBag("purple_crystal_vitextra_4"); player.Vit = player.Vit - 15; player.Crystal1 = "none";  player.MpMax = player.HpMax - 200; return; }	
				if(num == 2 && player.Crystal2.equals("purple_crystal_vitextra_4")) { AddItemBag("purple_crystal_vitextra_4"); player.Vit = player.Vit - 15; player.Crystal2 = "none";  player.MpMax = player.HpMax - 200; return; }	
				if(num == 3 && player.Crystal3.equals("purple_crystal_vitextra_4")) { AddItemBag("purple_crystal_vitextra_4"); player.Vit = player.Vit - 15; player.Crystal3 = "none";  player.MpMax = player.HpMax - 200; return; }	
				if(num == 4 && player.Crystal4.equals("purple_crystal_vitextra_4")) { AddItemBag("purple_crystal_vitextra_4"); player.Vit = player.Vit - 15; player.Crystal4 = "none";  player.MpMax = player.HpMax - 200; return; }
				
				
				if(num == 1 && player.Crystal1.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 2; player.Crystal1 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
				if(num == 2 && player.Crystal2.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 2; player.Crystal2 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
				if(num == 3 && player.Crystal3.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 2; player.Crystal3 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
				if(num == 4 && player.Crystal4.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 2; player.Crystal4 = "none";  player.AtkTimerMax = player.AtkTimerMax + 2; return; }	
				
				if(num == 1 && player.Crystal1.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 5; player.AtkTimerMax = player.AtkTimerMax + 4;  player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 10; player.AtkTimerMax= player.AtkTimerMax + 6; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 10; player.AtkTimerMax = player.AtkTimerMax + 6; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 10; player.AtkTimerMax = player.AtkTimerMax + 6; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("yellow_crystalgiextra")) { AddItemBag("yellow_crystalgiextra"); player.Agi = player.Agi - 10; player.AtkTimerMax = player.AtkTimerMax + 6; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("yellow_crystalgiextra_4")) { AddItemBag("yellow_crystalgiextra_4"); player.Agi = player.Agi - 15; player.AtkTimerMax = player.AtkTimerMax + 8; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("yellow_crystalgiextra_4")) { AddItemBag("yellow_crystalgiextra_4"); player.Agi = player.Agi - 15; player.AtkTimerMax = player.AtkTimerMax + 8; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("yellow_crystalgiextra_4")) { AddItemBag("yellow_crystalgiextra_4"); player.Agi = player.Agi - 15; player.AtkTimerMax = player.AtkTimerMax + 8; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("yellow_crystalgiextra_4")) { AddItemBag("yellow_crystalgiextra_4"); player.Agi = player.Agi - 15; player.AtkTimerMax = player.AtkTimerMax + 8; player.Crystal4 = "none";  return; }
				
				
				if(num == 1 && player.Crystal1.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 2; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 2; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 2; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 2; player.Crystal4 = "none";  return; }	
				
				if(num == 1 && player.Crystal1.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 5; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 5; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 5; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 5; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 10; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 10; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 10; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("red_crystal_strextra")) { AddItemBag("red_crystal_strextra"); player.Str = player.Str - 10; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("red_crystal_strextra_4")) { AddItemBag("red_crystal_strextra_4"); player.Str = player.Str - 15; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("red_crystal_strextra_4")) { AddItemBag("red_crystal_strextra_4"); player.Str = player.Str - 15; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("red_crystal_strextra_4")) { AddItemBag("red_crystal_strextra_4"); player.Str = player.Str - 15; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("red_crystal_strextra_4")) { AddItemBag("red_crystal_strextra_4"); player.Str = player.Str - 15; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 2; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 2; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 2; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 2; player.Crystal4 = "none";  return; }	
				
				if(num == 1 && player.Crystal1.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 5; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 5; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 5; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 5; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 10; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 10; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 10; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("grey_crystal_dexextra")) { AddItemBag("grey_crystal_dexextra"); player.Dex = player.Dex - 10; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("grey_crystal_dexextra_4")) { AddItemBag("grey_crystal_dexextra_4"); player.Dex = player.Dex - 15; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("grey_crystal_dexextra_4")) { AddItemBag("grey_crystal_dexextra_4"); player.Dex = player.Dex - 15; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("grey_crystal_dexextra_4")) { AddItemBag("grey_crystal_dexextra_4"); player.Dex = player.Dex - 15; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("grey_crystal_dexextra_4")) { AddItemBag("grey_crystal_dexextra_4"); player.Dex = player.Dex - 15; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 20; player.regenTimeMax = player.regenTimeMax + 200; player.Crystal1 = "none"; return; }	
				if(num == 2 && player.Crystal2.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 20; player.regenTimeMax = player.regenTimeMax + 200; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 20; player.regenTimeMax = player.regenTimeMax + 200; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 2; player.StaminaMax = player.StaminaMax - 20; player.regenTimeMax = player.regenTimeMax + 200; player.Crystal4 = "none";  return; }	
				
				if(num == 1 && player.Crystal1.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 400; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 400; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 400; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 5; player.StaminaMax = player.StaminaMax - 50; player.regenTimeMax = player.regenTimeMax + 400; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 600; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 600; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 600; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("orange_crystal_resextra")) { AddItemBag("orange_crystal_resextra"); player.Res = player.Res - 10; player.StaminaMax = player.StaminaMax - 100; player.regenTimeMax = player.regenTimeMax + 600; player.Crystal4 = "none";  return; }
				
				if(num == 1 && player.Crystal1.equals("orange_crystal_resextra_4")) { AddItemBag("orange_crystal_resextra_4"); player.Res = player.Res - 15; player.StaminaMax = player.StaminaMax - 200; player.regenTimeMax = player.regenTimeMax + 800; player.Crystal1 = "none";  return; }	
				if(num == 2 && player.Crystal2.equals("orange_crystal_resextra_4")) { AddItemBag("orange_crystal_resextra_4"); player.Res = player.Res - 15; player.StaminaMax = player.StaminaMax - 200; player.regenTimeMax = player.regenTimeMax + 800; player.Crystal2 = "none";  return; }	
				if(num == 3 && player.Crystal3.equals("orange_crystal_resextra_4")) { AddItemBag("orange_crystal_resextra_4"); player.Res = player.Res - 15; player.StaminaMax = player.StaminaMax - 200; player.regenTimeMax = player.regenTimeMax + 800; player.Crystal3 = "none";  return; }	
				if(num == 4 && player.Crystal4.equals("orange_crystal_resextra_4")) { AddItemBag("orange_crystal_resextra_4"); player.Res = player.Res - 15; player.StaminaMax = player.StaminaMax - 200; player.regenTimeMax = player.regenTimeMax + 800; player.Crystal4 = "none";  return; }
			}
			
			
			public Sprite SetWeapon(Player playerUse) {   
				
				//player.playerInBattle = "no";
				//playerInAttack = true;
				
				if(playerUse.Job.equals("Aprendiz")) { atlas_weapongeneric = atlas_nknifes; }
				if(playerUse.Job.equals("Espadachim")) { atlas_weapongeneric = atlas_swords; }
				if(playerUse.Job.equals("Feiticeiro")) { atlas_weapongeneric = atlas_rods; }
				if(playerUse.Job.equals("Batedor")) { atlas_weapongeneric = atlasxes; }
				if(playerUse.Job.equals("Pistoleiro")) { atlas_weapongeneric = atlas_pistols; }
				if(playerUse.Job.equals("Curandeiro")) { atlas_weapongeneric = atlas_rods; }
				if(playerUse.Job.equals("Ladrao")) { atlas_weapongeneric = atlas_daggers; }
					
				if(playerUse.playerInBattle.equals("yes")) {
					if(playerUse.Job.equals("Aprendiz")) {
						if(playerUse.Weapon.equals("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 13.5f, playerUse.PosY + 11f); }
						if(playerUse.Weapon.equals("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
					}
					if(playerUse.Job.equals("Espadachim")) {
						if(playerUse.Weapon.equals("woodsword")) { spr_master = atlas_nknifes.createSprite("wood_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("sabersword")) { spr_master = atlas_nknifes.createSprite("saber_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("venomsword")) { spr_master = atlas_nknifes.createSprite("venom_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("edgesword")) { spr_master = atlas_nknifes.createSprite("edge_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("knightsword")) { spr_master = atlas_nknifes.createSprite("knight_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("ragesword")) { spr_master = atlas_nknifes.createSprite("rage_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }						
					}
					
					if(playerUse.Job.equals("Feiticeiro")) {
						if(playerUse.Weapon.equals("stickrod")) { spr_master = atlas_nknifes.createSprite("stick_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 13.5f, playerUse.PosY + 11f); }
						if(playerUse.Weapon.equals("gloomrod")) { spr_master = atlas_nknifes.createSprite("gloom_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("gemrod")) { spr_master = atlas_nknifes.createSprite("gem_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("lightwieldrod")) { spr_master = atlas_nknifes.createSprite("lightwield_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("serpentrod")) { spr_master = atlas_nknifes.createSprite("serpent_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("clerigrod")) { spr_master = atlas_nknifes.createSprite("clerig_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }										
					}
					
					if(playerUse.Job.equals("Pistoleiro")) {
						if(playerUse.Weapon.equals("basicpistol")) { spr_master = atlas_nknifes.createSprite("basic_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 13.5f, playerUse.PosY + 11f); }
						if(playerUse.Weapon.equals("revolverpistol")) { spr_master = atlas_nknifes.createSprite("revolver_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("lightpistol")) { spr_master = atlas_nknifes.createSprite("light_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("turretpistol")) { spr_master = atlas_nknifes.createSprite("turret_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("riflepistol")) { spr_master = atlas_nknifes.createSprite("rifle_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("shooterpistol")) { spr_master = atlas_nknifes.createSprite("shooter_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }										
					}
					
					if(playerUse.Job.equals("Ladrao")) {
						if(playerUse.Weapon.equals("basicdagger")) { spr_master = atlas_nknifes.createSprite("basic_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 13.5f, playerUse.PosY + 11f); }
						if(playerUse.Weapon.equals("edgedagger")) { spr_master = atlas_nknifes.createSprite("edge_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("poisondagger")) { spr_master = atlas_nknifes.createSprite("poison_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("marinedagger")) { spr_master = atlas_nknifes.createSprite("marine_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("triplodagger")) { spr_master = atlas_nknifes.createSprite("triplo_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }
						if(playerUse.Weapon.equals("thunderdagger")) { spr_master = atlas_nknifes.createSprite("thunder_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 2.9f, playerUse.PosY + 6f); }										
					}
				}
				
				if(playerUse.playerInAttack.equals("yes")) {
					if(playerUse.Job.equals("Aprendiz")) {
						if(playerUse.Weapon.equals("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knifettack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
					}
					
					if(playerUse.Job.equals("Espadachim")) {
						if(playerUse.Weapon.equals("woodsword")) { spr_master = atlas_nknifes.createSprite("wood_swordttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("sabersword")) { spr_master = atlas_nknifes.createSprite("saber_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("venomsword")) { spr_master = atlas_nknifes.createSprite("venom_swordttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("edgesword")) { spr_master = atlas_nknifes.createSprite("edge_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("knightsword")) { spr_master = atlas_nknifes.createSprite("knight_swordttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("ragesword")) { spr_master = atlas_nknifes.createSprite("rage_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
					}
					
					if(playerUse.Job.equals("Feiticeiro")) {
						if(playerUse.Weapon.equals("stickrod")) { spr_master = atlas_nknifes.createSprite("stick_rodttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("gloomrod")) { spr_master = atlas_nknifes.createSprite("gloom_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("gemrod")) { spr_master = atlas_nknifes.createSprite("gem_rodttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("lightwieldrod")) { spr_master = atlas_nknifes.createSprite("lightwield_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("serpentrod")) { spr_master = atlas_nknifes.createSprite("serpent_rodttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("clerigrod")) { spr_master = atlas_nknifes.createSprite("clerig_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
					}
					
					if(playerUse.Job.equals("Pistoleiro")) {
						if(playerUse.Weapon.equals("basicpistol")) { spr_master = atlas_nknifes.createSprite("basic_pistolttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("revolverpistol")) { spr_master = atlas_nknifes.createSprite("revolver_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("lightpistol")) { spr_master = atlas_nknifes.createSprite("light_pistolttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("turretpistol")) { spr_master = atlas_nknifes.createSprite("turret_pistol_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("riflepistol")) { spr_master = atlas_nknifes.createSprite("rifle_pistolttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("shooterpistol")) { spr_master = atlas_nknifes.createSprite("shooter_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
					}
					
					if(playerUse.Job.equals("Ladrao")) {
						if(playerUse.Weapon.equals("basicdagger")) { spr_master = atlas_nknifes.createSprite("basic_daggerttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("edgedagger")) { spr_master = atlas_nknifes.createSprite("edge_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("poisondagger")) { spr_master = atlas_nknifes.createSprite("poison_daggerttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("marinedagger")) { spr_master = atlas_nknifes.createSprite("marine_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("triplodagger")) { spr_master = atlas_nknifes.createSprite("triplo_daggerttack_right"); spr_master.setSize(20, 30); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
						if(playerUse.Weapon.equals("thunderdagger")) { spr_master = atlas_nknifes.createSprite("thunder_dagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(playerUse.PosX - 4.8f, playerUse.PosY + 2f);  }
					}
				}
				
				return spr_master;
				
			}
			
			public void AddItemBag(String itemName) {
				String[] lstItem = player.Itens.split("-");
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
						player.Itens = listaItemFinal;
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
						player.Itens = listaItemFinal;
					}
				}
			}
			
			public void DiscartItem(int itemNum) {
				String[] lstItem = player.Itens.split("-");
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
				money = player.Money;
				if(money > 1500) { return; }
				int moneygave = randnumber.nextInt(5);
				while(moneygave < 2) { moneygave = randnumber.nextInt(5); }
				money = money + (moneygave * 2);
				player.Money = money;
				
				//Clean Item placebox
				lstItem[itemNum] = "[NONE]";
				listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
				listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
				player.Itens = listaItemFinal;	
			}
			
			//Shops
			//SHOP
			public String CheckBuyItemStreetsA(String shop, int num) {
				String SysMsg = "";
				if(shop.equals("refrishop")) {
					if(num == 1) {  
						if(player.Money >= 2) {  
							AddItemBag("hpcan"); 
							player.Money = player.Money - 2; 
							if(player.Money < 0) { player.Money = 0; } 	
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
			
			//[Exp/Drop]//
			//Give EXP
			public void GiveExp(int exp) {
				boolean levelup = false;
				if(player.Level >= 10) {
					ExpSharedOnline = exp;
					return;
				}
				
				if(player.Level == 50) {
					ExpSharedOnline = exp;
					return;
				}
				
				player.Exp = player.Exp + exp;
				ExpSharedOnline = exp;
				
				//Sewers   
				if(player.Level == 1 && player.Exp >= 100) {  player.Level = 2; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true; }
				if(player.Level == 2 && player.Exp >= 150) {  player.Level = 3; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 3 && player.Exp >= 250) {  player.Level = 4; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 4 && player.Exp >= 360) {  player.Level = 5; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 5 && player.Exp >= 430) {  player.Level = 6; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 6 && player.Exp >= 500) {  player.Level = 7; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 7 && player.Exp >= 730) {  player.Level = 8; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 8 && player.Exp >= 1000) {  player.Level = 9; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 9 && player.Exp >= 1450) {  player.Level = 10; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				
				//Watercave
				if(player.Level == 10 && player.Exp >= 1840) {  player.Level = 11; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 11 && player.Exp >= 3330) {  player.Level = 12; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 12 && player.Exp >= 5500) {  player.Level = 13; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 13 && player.Exp >= 7600) {  player.Level = 14; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 14 && player.Exp >= 9929) {  player.Level = 15; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 15 && player.Exp >= 12820) {  player.Level = 16; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 16 && player.Exp >= 15293) {  player.Level = 17; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 17 && player.Exp >= 17300) {  player.Level = 18; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 18 && player.Exp >= 22402) {  player.Level = 19; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 19 && player.Exp >= 26902) {  player.Level = 20; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				
				//Mines
				if(player.Level == 20 && player.Exp >= 34592) {  player.Level = 21; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 21 && player.Exp >= 46923) {  player.Level = 22; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 22 && player.Exp >= 75829) {  player.Level = 23; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 23 && player.Exp >= 90234) {  player.Level = 24; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 24 && player.Exp >= 153042) {  player.Level = 25; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 25 && player.Exp >= 179232) {  player.Level = 26; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 26 && player.Exp >= 221011) {  player.Level = 27; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 27 && player.Exp >= 259323) {  player.Level = 28; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 28 && player.Exp >= 279293) {  player.Level = 29; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 29 && player.Exp >= 383421) {  player.Level = 30; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				
				//Snowpalace
				if(player.Level == 30 && player.Exp >= 593421)  {  player.Level = 31; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 31 && player.Exp >= 814402)  {  player.Level = 32; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 32 && player.Exp >= 1534611) {  player.Level = 33; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 33 && player.Exp >= 1839770) {  player.Level = 34; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 34 && player.Exp >= 2433026) {  player.Level = 35; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 35 && player.Exp >= 2792074) {  player.Level = 36; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 36 && player.Exp >= 2931441) {  player.Level = 37; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 37 && player.Exp >= 3304900) {  player.Level = 38; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 38 && player.Exp >= 3588905) {  player.Level = 39; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 39 && player.Exp >= 4987320) {  player.Level = 40; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				
				//Tower												   
				if(player.Level == 40 && player.Exp >= 9188696000f) {  player.Level = 41; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 41 && player.Exp >= 9288526000f) {  player.Level = 42; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 42 && player.Exp >= 9488446000f) {  player.Level = 43; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 43 && player.Exp >= 9588316000f) {  player.Level = 44; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 44 && player.Exp >= 9688236000f) {  player.Level = 45; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 45 && player.Exp >= 9798126000f) {  player.Level = 46; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 46 && player.Exp >= 9828646000f) {  player.Level = 47; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 47 && player.Exp >= 9878756009f) {  player.Level = 48; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 48 && player.Exp >= 9888866009f) {  player.Level = 49; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				if(player.Level == 49 && player.Exp >= 9999999999f) {  player.Level = 50; player.Exp = 0; player.HpMax = player.HpMax + 10; player.StatusPoint = player.StatusPoint + 6; levelup = true;}
				
				if(levelup) {
					if(player.Job.equals("Aprendiz")) { player.HpMax = player.HpMax + 10; player.MpMax = player.MpMax + 10; player.Atk = player.Atk + 1; }
					
					if(player.Job.equals("Espadachim")) { player.HpMax = player.HpMax + 20; player.Atk = player.Atk + 5;}
					
					if(player.Job.equals("Feiticeiro")) { player.MpMax = player.MpMax + 15; player.Atk = player.Atk + 3;}
					
					if(player.Job.equals("Curandeiro")) { player.MpMax = player.MpMax + 10; player.Atk = player.Atk + 3;}
					
					if(player.Job.equals("Pistoleiro")) { player.HpMax = player.HpMax + 8; player.Atk = player.Atk + 3; player.AtkTimerMax = player.AtkTimerMax -2;}
					
					if(player.Job.equals("Ladrao")) { player.HpMax = player.HpMax + 10; player.Atk = player.Atk + 2; player.AtkTimerMax = player.AtkTimerMax -4;}		
				}	
				
				levelup = false;
			}
			
			public String ExpPercent() {
				
				float percent = player.Exp;
			    float totalExp = 0;
			    
			    if(player.Level == 1) { totalExp = 100; }
			    if(player.Level == 2) { totalExp = 150; }
			    if(player.Level == 3) { totalExp = 250; }
			    if(player.Level == 4) { totalExp = 360; }
			    if(player.Level == 5) { totalExp = 430; }
			    if(player.Level == 6) { totalExp = 500; }
			    if(player.Level == 7) { totalExp = 730; }
			    if(player.Level == 8) { totalExp = 1000; }
			    if(player.Level == 9) { totalExp = 1450; }
			    
			    if(player.Level == 10) { totalExp = 1840; }
			    if(player.Level == 11) { totalExp = 3330; }
			    if(player.Level == 12) { totalExp = 5500; }
			    if(player.Level == 13) { totalExp = 7600; }
			    if(player.Level == 14) { totalExp = 9929; }
			    if(player.Level == 15) { totalExp = 12820; }
			    if(player.Level == 16) { totalExp = 15293; }
			    if(player.Level == 17) { totalExp = 17300; }
			    if(player.Level == 18) { totalExp = 22402; }
			    if(player.Level == 19) { totalExp = 26902; }
			    
			    if(player.Level == 20) { totalExp = 34592; }
			    if(player.Level == 21) { totalExp = 46923; }
			    if(player.Level == 22) { totalExp = 75829; }
			    if(player.Level == 23) { totalExp = 90234; }
			    if(player.Level == 24) { totalExp = 153042; }
			    if(player.Level == 25) { totalExp = 179232; }
			    if(player.Level == 26) { totalExp = 221011; }
			    if(player.Level == 27) { totalExp = 259323; }
			    if(player.Level == 28) { totalExp = 279293; }
			    if(player.Level == 29) { totalExp = 383421; }
			    
			    if(player.Level == 30) { totalExp = 593421; }
			    if(player.Level == 31) { totalExp = 814402; }
			    if(player.Level == 32) { totalExp = 1534611; }
			    if(player.Level == 33) { totalExp = 1839770; }
			    if(player.Level == 34) { totalExp = 2433026; }
			    if(player.Level == 35) { totalExp = 2792074; }
			    if(player.Level == 36) { totalExp = 2931441; }
			    if(player.Level == 37) { totalExp = 3304900; }
			    if(player.Level == 38) { totalExp = 3588905; }
			    if(player.Level == 39) { totalExp = 4987320; }
			    
			    if(player.Level == 40) { totalExp = 6987320; }
			    if(player.Level == 41) { totalExp = 8987320; }
			    if(player.Level == 42) { totalExp = 92198732; }
			    if(player.Level == 43) { totalExp = 95883160f; }
			    if(player.Level == 44) { totalExp = 96882360f; }
			    if(player.Level == 45) { totalExp = 97981260f; }
			    if(player.Level == 46) { totalExp = 98286460f; }
			    if(player.Level == 47) { totalExp = 987875600f; }
			    if(player.Level == 48) { totalExp = 988886600f; }
			    if(player.Level == 49) { totalExp = 9999999999f; }
			    									      
			    float result = (float)((percent*100)/totalExp);
			    return String.valueOf(result);
			    
			}
			
			public String ItemDrop(String mob) {
				int chance = randnumber.nextInt(1000);
				
				if(mob.equals("slime")) {
					//if(chance <= 500) { AddItemBag("hpcan"); AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
					//if(chance >= 500 && chance <= 700) { AddItemBag("lootblop");  return "Adicionado Gosma"; }
					//if(chance >= 700 && chance <= 980) { AddItemBag("hatslime");  return "Adicionado Chapeu de Slime"; }
					//if(chance >= 980 && chance <= 1000) { AddItemBag("lootfragmentoazul"); return "Adicionado Fragmento Azul"; }
					
					if(chance <= 500) { AddItemBag("hpcan"); AddItemBag("hatbunny"); return "Adicionado Refrigerante de HP (P)"; }
					if(chance >= 500 && chance <= 700) { AddItemBag("hatbunny");  return "Adicionado Gosma"; }
					if(chance >= 700 && chance <= 980) { AddItemBag("hatbunny");  return "Adicionado Chapeu de Slime"; }
					if(chance >= 980 && chance <= 1000) { AddItemBag("hatbunny"); return "Adicionado Fragmento Azul"; }
				}
				return "";
			}
			
			//[online]//
			public String GetRetorno() {
				return onlineresponse;
			}
			
			public String OnlineManager(String operation, String subData, String extraData) {
				String operationresult = "";
				try {
					if(operation.equals("NewAccount")) {  
						operationresult = GerenciamentoOnlineHTML("NewAccount", subData, extraData);
					}
					if(operation.equals("LoadData")) {  
						operationresult = GerenciamentoOnlineHTML("LoadData", subData, extraData);
					}
					return operationresult;
				}
				
				catch(Exception ex) {
					return operationresult;
				}
			}
			
			
			public String GerenciamentoOnlineHTML(String tipoRequisicao, String subData, String extraData) throws UnsupportedEncodingException{
				
				onlineresponse = "";
				final CountDownLatch latch = new CountDownLatch(1);
				
				if(tipoRequisicao.equals("NewAccount")) 
				{
				
					Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
					request.setUrl("http://moonboltprojects.online/serverdeless/index.php");
					request.setContent("application/x-www-form-urlencoded");
	
					// Prepare the data to post
					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("lservername", lservername);
					parameters.put("lusername", lusername);
					parameters.put("lpassword", lpassword);
					parameters.put("ldbname", ldbname);
					parameters.put("lrequest", tipoRequisicao);
					parameters.put("ldataaccount", subData);
					
					// Convert the parameters into URL encoded form
					String content = "";
					for (Map.Entry<String, String> parameter : parameters.entrySet()) {
					    if (content.length() > 0) {
					        content += "&";
					    }
					    content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "=" + URLEncoder.encode(parameter.getValue(), "UTF-8");
					}
	
					request.setContent(content);
	
					Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
					    public void handleHttpResponse(Net.HttpResponse httpResponse) {
					        String response = httpResponse.getResultAsString();
					        // process the response
					        System.out.println(response);
					        onlineresponse = "success";
					        latch.countDown(); // Decrements the count of the latch, releasing all waiting threads
					    }
	
					    public void failed(Throwable t) {
					        String message = "Request failed";
					        System.out.println(message);
					        onlineresponse = "fail";
					        latch.countDown(); // Decrements the count of the latch, releasing all waiting threads
					    }
	
					    @Override
					    public void cancelled() {
					        String message = "Request cancelled";
					        System.out.println(message);
					        onlineresponse= "cancel";
					        latch.countDown(); // Decrements the count of the latch, releasing all waiting threads
					    }
					});
					
					try {
					    latch.await(); // Current thread waits here until latch count is zero
					} catch (InterruptedException e) {
					    e.printStackTrace();
					}
				
					return onlineresponse;
				}
				
				if(tipoRequisicao.equals("LoadData")) 
				{
				
					Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
					request.setUrl("http://moonboltprojects.online/serverdeless/index.php");
					request.setContent("application/x-www-form-urlencoded");
	
					// Prepare the data to post
					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("lservername", lservername);
					parameters.put("lusername", lusername);
					parameters.put("lpassword", lpassword);
					parameters.put("ldbname", ldbname);
					parameters.put("lrequest", tipoRequisicao);
					parameters.put("ldataaccount", subData);
					
					// Convert the parameters into URL encoded form
					String content = "";
					for (Map.Entry<String, String> parameter : parameters.entrySet()) {
					    if (content.length() > 0) {
					        content += "&";
					    }
					    content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "=" + URLEncoder.encode(parameter.getValue(), "UTF-8");
					}
	
					request.setContent(content);
	
					Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
					    public void handleHttpResponse(Net.HttpResponse httpResponse) {
					        String response = httpResponse.getResultAsString();
					        // process the response
					        System.out.println(response);
					        LoadPlayerObjectDataFromServer(response);
					        onlineresponse = "Conta carregada com sucesso";
					        latch.countDown(); // Decrements the count of the latch, releasing all waiting threads
					    }
	
					    public void failed(Throwable t) {
					        String message = "Request failed";
					        System.out.println(message);
					        onlineresponse = "fail";
					        latch.countDown(); // Decrements the count of the latch, releasing all waiting threads
					    }
	
					    @Override
					    public void cancelled() {
					        String message = "Request cancelled";
					        System.out.println(message);
					        onlineresponse= "cancel";
					        latch.countDown(); // Decrements the count of the latch, releasing all waiting threads
					    }
					});
					
					try {
					    latch.await(); // Current thread waits here until latch count is zero
					} catch (InterruptedException e) {
					    e.printStackTrace();
					}
				
					return onlineresponse;
				}
				
				return onlineresponse;
			}
			
			public void LoadPlayerObjectDataFromServer(String data) {
				String[] fields = data.split(":");
				Player playerload = new Player();

				playerload.AccountID = fields[2];
				playerload.Name = fields[4];
				playerload.Sex = fields[6];
				playerload.Hair = fields[8];
				playerload.Color = fields[10];
				playerload.Hat = fields[12];
				playerload.Job = fields[14];
				playerload.SetUpper = fields[16];
				playerload.SetBottom = fields[18];
				playerload.SetFooter = fields[20];
				playerload.Level = Integer.parseInt(fields[22]);
				playerload.Exp = Float.parseFloat(fields[24]);
				playerload.Map = fields[26];
				playerload.Hp = Integer.parseInt(fields[28]);
				playerload.Mp = Integer.parseInt(fields[30]);
				playerload.Money = Integer.parseInt(fields[32]);
				playerload.HpMax = Integer.parseInt(fields[34]);
				playerload.MpMax = Integer.parseInt(fields[36]);
				playerload.regenTime = Integer.parseInt(fields[38]);
				playerload.regenTimeMax = Integer.parseInt(fields[40]);
				playerload.PosX = Float.parseFloat(fields[42]);
				playerload.PosY = Float.parseFloat(fields[44]);
				playerload.Walk = fields[46];
				playerload.Frame = Integer.parseInt(fields[48]);
				playerload.countFrame = Integer.parseInt(fields[50]);
				playerload.breakwalk = fields[52];
				playerload.Target = fields[54];
				playerload.AtkTimer = Integer.parseInt(fields[56]);
				playerload.AtkTimerMax = Integer.parseInt(fields[58]);
				playerload.Casting = fields[60];
				playerload.Atk = Integer.parseInt(fields[62]);
				playerload.Def = Integer.parseInt(fields[64]);
				playerload.Evasion = Integer.parseInt(fields[66]);
				playerload.Side = fields[68];
				playerload.Weapon = fields[70];
				playerload.Crystal1 = fields[72];
				playerload.Crystal2 = fields[74];
				playerload.Crystal3 = fields[76];
				playerload.Crystal4 = fields[78];
				playerload.StatusPoint = Integer.parseInt(fields[80]);
				playerload.Str = Integer.parseInt(fields[82]);
				playerload.Agi = Integer.parseInt(fields[84]);
				playerload.Vit = Integer.parseInt(fields[86]);
				playerload.Dex = Integer.parseInt(fields[88]);
				playerload.Wis = Integer.parseInt(fields[90]);
				playerload.Luk = Integer.parseInt(fields[92]);
				playerload.Res = Integer.parseInt(fields[94]);
				playerload.Stamina = Integer.parseInt(fields[96]);
				playerload.StaminaMax = Integer.parseInt(fields[98]);
				playerload.Itens = fields[100];
				playerload.Quests = fields[102];
				playerload.hotkey1 = fields[104];
				playerload.hotkey2 = fields[106];
				playerload.buffA = fields[108];
				playerload.buffB = fields[110];
				playerload.buffC = fields[112];
				playerload.BuffTimeA = Integer.parseInt(fields[114]);
				playerload.BuffTimeB = Integer.parseInt(fields[116]);
				playerload.BuffTimeC = Integer.parseInt(fields[118]);
				playerload.party = fields[120];
				playerload.playerInBattle = fields[122];
				playerload.playerInAttack = fields[124];
				playerload.playerInCast = fields[126];
				playerload.playerSit = fields[128];
				playerload.SyncPlayerMob = fields[130];
				playerload.PlayerExpGet = fields[132];

				
			}
}