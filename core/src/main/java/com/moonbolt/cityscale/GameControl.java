package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.moonbolt.cityscale.interfaces.HttpCallback;
import com.moonbolt.cityscale.models.Monster;
import com.moonbolt.cityscale.models.Player;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

public class GameControl {

	// Manager
	private Json json;
	private FileHandle file;
	private Random randnumber;

	// Player
	private ArrayList<Player> lstPlayers;
	private Player playerUse;
	private int FrameAtkPlayer = 0;
	private int charNumber = 0;
	private String playerAccount = "";
	private int selectedChar = 0;
	private boolean defTrigger = false;
	
	// Monster
	private ArrayList<Monster> lstMonsters;
	private Monster placeholderMonster;

	// Sprite
	private Sprite spr_master;
	private Sprite spr_skill;

	// Online
	private String lservername = "cityserver.mysql.uhserver.com";
	private String lusername = "citymaster";
	private String lpassword = "P@titos07";
	private String ldbname = "cityserver";

	// Online Lists
	private int ExpSharedOnline = 0;
	private ArrayList<String> lstChats;
	private String onlineResult = "";
	private ArrayList<Player> lstPlayerOnline;
	private String onlineGrab = "none";


	// Texture Atlas
	private TextureAtlas atlas_hairs1;
	private TextureAtlas atlas_hairs2;
	private TextureAtlas atlas_hairs3;
	private TextureAtlas atlas_hairs4;
	private TextureAtlas atlas_basicset;
	private TextureAtlas atlas_sportset;
	private TextureAtlas atlas_hats;
	private TextureAtlas atlas_ux;
	private TextureAtlas atlas_shops;
	private TextureAtlas atlas_genericsetTOP;
	private TextureAtlas atlas_genericsetBOTTOM;
	private TextureAtlas atlas_genericsetFOOTER;
	private TextureAtlas atlas_generichair;
	private TextureAtlas atlas_npcs;
	private TextureAtlas atlas_cards;
	private TextureAtlas atlas_mobSewers;
	private TextureAtlas atlas_mobForest;
	private TextureAtlas atlas_mobWatercave;
	private TextureAtlas atlas_mobDesert;
	private TextureAtlas atlas_mobMines;
	private TextureAtlas atlas_mobVulcano;
	private TextureAtlas atlas_mobSnowpalace;
	private TextureAtlas atlas_mobSwamp;
	
	private TextureAtlas atlas_castEffect;

	private TextureAtlas atlas_weapon;
	private TextureAtlas atlas_cloth;
	private TextureAtlas atlas_cristais;
	private TextureAtlas atlas_foods;
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
	private TextureAtlas atlas_perfectshot;
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

		// list players
		lstPlayers = new ArrayList<Player>();
		lstPlayerOnline = new ArrayList<Player>();

		// Chats
		lstChats = new ArrayList<String>();
		lstChats.add("");
		lstChats.add("");
		lstChats.add("");
		lstChats.add("");
		lstChats.add("");

		// Monster
		placeholderMonster = new Monster();
		lstMonsters = new ArrayList<Monster>();

		// Textures
		atlas_castEffect = new TextureAtlas(Gdx.files.internal("data/assets/skills/effect/casteffect.txt"));
				
		atlas_hairs1 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair1.txt"));
		atlas_hairs2 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair2.txt"));
		atlas_hairs3 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair3.txt"));
		atlas_hairs4 = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/hairs/hair4.txt"));
		atlas_hats = new TextureAtlas(Gdx.files.internal("data/assets/characters/hats/hats.txt"));

		atlas_genericsetTOP = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_genericsetBOTTOM = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_genericsetFOOTER = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/basic/basicset.txt"));
		atlas_sportset = new TextureAtlas(Gdx.files.internal("data/assets/characters/player/sport/sportset.txt"));
		atlas_ux = new TextureAtlas(Gdx.files.internal("data/assets/ux/ux.txt"));

		atlas_mobSewers = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobsewers.txt"));
		atlas_mobForest = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobforest.txt"));
		
		atlas_mobWatercave = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobwatercave.txt"));
		atlas_mobDesert = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobdesert.txt"));
		
		atlas_mobMines = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobmines.txt"));
		atlas_mobVulcano = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobvulcano.txt"));
		
		atlas_mobSnowpalace = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobsnowpalace.txt"));
		atlas_mobSwamp = new TextureAtlas(Gdx.files.internal("data/assets/characters/monsters/mobswamp.txt"));
		

		atlas_npcs = new TextureAtlas(Gdx.files.internal("data/assets/characters/npcs/npcs.txt"));
		atlas_cards = new TextureAtlas(Gdx.files.internal("data/assets/skills/cards.txt"));

		// atlas_items = new
		// TextureAtlas(Gdx.files.internal("data/assets/itens/itens/itens.txt"));
		atlas_weapon = new TextureAtlas(Gdx.files.internal("data/assets/itens/weapon.txt"));
		atlas_cloth = new TextureAtlas(Gdx.files.internal("data/assets/itens/cloth.txt"));
		atlas_cristais = new TextureAtlas(Gdx.files.internal("data/assets/itens/cristais.txt"));
		atlas_foods = new TextureAtlas(Gdx.files.internal("data/assets/itens/foods.txt"));
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
		atlas_perfectshot = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/perfectshot.txt"));
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
		atlas_heal = new TextureAtlas(Gdx.files.internal("data/assets/skills/skilleffect/heal.txt"));

	}
	
	/////////////////////// [ SUMMARY ] ///////////////////
	// [Account]//
	// [Interface]//
	// [Character]//
	// [Monsters]//
	// [Skills]//
	// [Online]//
	// [Exp/Drop]//
	
		public void GiveItemTEMPORARIO() {
			AddItemBag("hatbat");
		}
	
	
		public void LoadData(String data) {
	    String[] playerDataArray = data.split("@");

	    // Remove all occurrences of "#Success#"
	    data = data.replace("#Success#", "");

	    for (String playerData : playerDataArray) {
	        String[] splitData = playerData.split("\\|");

	        Player player = new Player();
	        player.AccountID = splitData[1]; // AccountID
	        player.AccountNumber = splitData[3]; // AccountNumber
	        player.Characternumber = splitData[5]; // CharacterNumber
	        player.Name = splitData[7]; // Name

	        if (!player.Name.equals("none")) {
	            player.Sex = splitData[9]; // Sex
	            player.Hair = splitData[11]; // Hair
	            player.Color = splitData[13]; // Color
	            player.Hat = splitData[15]; // Hat
	            player.Job = splitData[17]; // Job
	            player.SetUpper = splitData[19]; // SetUpper
	            player.SetBottom = splitData[21]; // SetBottom
	            player.SetFooter = splitData[23]; // SetFooter
	            player.Level = splitData[25]; // Level
	            player.Exp = splitData[27]; // Exp
	            player.Map = splitData[29]; // Map
	            player.Hp = splitData[31]; // Hp
	            player.Mp = splitData[33]; // Mp
	            player.Money = splitData[35]; // Money
	            player.HpMax = splitData[37]; // HpMax
	            player.MpMax = splitData[39]; // MpMax
	            player.regenTime = splitData[41]; // regenTime
	            player.regenTimeMax = splitData[43]; // regenTimeMax
	            player.PosX = splitData[45]; // PosX
	            player.PosY = splitData[47]; // PosY
	            player.Walk = splitData[49]; // Walk
	            player.Frame = splitData[51]; // Frame
	            player.countFrame = splitData[53]; // countFrame
	            player.breakwalk = splitData[55]; // breakwalk
	            player.Target = splitData[57]; // Target
	            player.AtkTimer = splitData[59]; // AtkTimer
	            player.AtkTimerMax = splitData[61]; // AtkTimerMax
	            player.Casting = splitData[63]; // CastTimer
	            player.Atk = splitData[65]; // Atk
	            player.Def = splitData[67]; // Def
	            player.Evasion = splitData[69]; // Evasion
	            player.Side = splitData[71]; // Side
	            player.Weapon = splitData[73]; // Weapon
	            player.Crystal1 = splitData[75]; // Crystal1
	            player.Crystal2 = splitData[77]; // Crystal2
	            player.Crystal3 = splitData[79]; // Crystal3
	            player.Crystal4 = splitData[81]; // Crystal4
	            player.StatusPoint = splitData[83]; // StatusPoints
	            player.Str = splitData[85]; // Str
	            player.Agi = splitData[87]; // Agi
	            player.Vit = splitData[89]; // Vit
	            player.Dex = splitData[91]; // Dex
	            player.Wis = splitData[93]; // Wis
	            player.Luk = splitData[95]; // Luk
	            player.Res = splitData[97]; // Res
	            player.Stamina = splitData[99]; // Stamina
	            player.StaminaMax = splitData[101]; // StaminaMax
	            player.Itens = splitData[103]; // Itens
	            player.Quests = splitData[105]; // Quests
	            player.hotkey1 = splitData[107]; // Hotkey1
	            player.hotkey2 = splitData[109]; // Hotkey2
	            player.buffA = splitData[111]; // BuffA
	            player.buffB = splitData[113]; // BuffB
	            player.buffC = splitData[115]; // BuffC
	            player.BuffTimeA = splitData[117]; // BuffTimeA
	            player.BuffTimeB = splitData[119]; // BuffTimeB
	            player.BuffTimeC = splitData[121]; // BuffTimeC
	            player.party = splitData[123]; // Party
	            player.playerInBattle = splitData[125]; // PlayerInBattle
	            player.playerInAttack = splitData[127]; // PlayerInAttack
	            player.playerInCast = splitData[129]; // PlayerInCast
	            player.playerSit = splitData[131]; // PlayerSit
	            player.SyncPlayerMob = splitData[133]; // SyncPlayerMob
	            player.PlayerExpGet = splitData[135]; // PlayerExpGet
	            player.MagicSync = splitData[137]; // MagicSync
	            player.StrExtra = splitData[139]; // StrExtra
	            player.AgiExtra = splitData[141]; // AgiExtra
	            player.VitExtra = splitData[143]; // VitExtra
	            player.DexExtra = splitData[145]; // DexExtra
	            player.WisExtra = splitData[147]; // WisExtra
	            player.LukExtra = splitData[149]; // LukExtra
	            player.ResExtra = splitData[151]; // ResExtra
	            player.pet = splitData[153]; // Pet
	            player.pethungry = splitData[155]; // PetHungry
	            player.petcare = splitData[157]; // PetCare
	            player.petTraining = splitData[159]; // PetTraining
	            player.petLevel = splitData[161]; // PetLevel
	        }

	        int characterNumber = Integer.parseInt(player.Characternumber);
	        if (characterNumber >= 1 && characterNumber <= 3) {
	            if (lstPlayers.size() < characterNumber) {
	                for (int i = lstPlayers.size(); i < characterNumber; i++) {
	                    lstPlayers.add(null);
	                }
	            }
	            lstPlayers.set(characterNumber - 1, player);
	        }
	    }
	}

	public ArrayList<Player> CleanListPlayers() {
		lstPlayers.clear();
		return lstPlayers;
	}
 
	public void UpdateControlPlayer(Player player) {
		playerUse = player;
	}
	
	///////////////////////////////////////////////// [Interface]///////////////////////////////////////////////
	public Sprite GetUX(String element, float cameraCoordsX, float cameraCoordsY) {
		switch (element) {
		case "bannerselect":
		case "bannercreate":
		case "bannerdelete":
			return createSpriteWithPositionAndSize(element, -60, 50, 50, 10);
		case "create":
			return createSpriteWithPositionAndSize(element, -60, -60, 120, 120);
		case "confirmtab":
			return createSpriteWithPositionAndSize(element, 15, 10, 50, 50);
		case "btnvoltar":
			return createSpriteWithPositionAndSize("btnback", 40, -60, 20, 10);
		case "btnexclude":
			return createSpriteWithPositionAndSize(element, 40, -60, 20, 10);
		case "btncreatenew":
			return createSpriteWithPositionAndSize(element, -60, -60, 20, 10);
		case "textbar":
			return atlas_ux.createSprite("textbar");
		case "playertag":
			return createSpriteWithPositionAndSize(element, cameraCoordsX - 99, cameraCoordsY + 56, 52, 42);
		case "innerpad":
			return createSpriteWithSize(element, 20, 35);
		case "battlezone":
			return createSpriteWithPositionAndSize(element, cameraCoordsX - 48, cameraCoordsY - 45, 90, 100);
		case "target":
			return atlas_ux.createSprite("target");
		case "menu":
			return createSpriteWithPositionAndSize("inventory", cameraCoordsX - 85, cameraCoordsY - 80, 170, 170);
		case "btnsit":
			return createSpriteWithPositionAndSize("sentar", cameraCoordsX - 45, cameraCoordsY + 87, 20, 10);
		case "hotkey1":
			return createSpriteWithPositionAndSize("placebox1", cameraCoordsX - 45, cameraCoordsY + 87, 20, 15);
		case "hotkey2":
			return createSpriteWithPositionAndSize("placebox1", cameraCoordsX - 45, cameraCoordsY + 87, 20, 10);
		case "descartar":
			return createSpriteWithPositionAndSize("placebox2", cameraCoordsX - 45, cameraCoordsY + 87, 30, 16);
		case "controlPC":
			return createSpriteWithPositionAndSize("off", cameraCoordsX - 45, cameraCoordsY + 87, 30, 16);
		case "login":
			return createSpriteWithPositionAndSize("login", cameraCoordsX - 45, cameraCoordsY + 87, 30, 16);
		case "partytag":
			return createSpriteWithPositionAndSize("partytag", cameraCoordsX - 45, cameraCoordsY + 87, 30, 16);
		case "battlezoneC":
			return createSpriteWithPositionAndSize("battlezoneC", cameraCoordsX - 48, cameraCoordsY - 92, 90, 190);
		case "energia1":
			return createSpriteWithPositionAndSize("energia1", cameraCoordsX - 48, cameraCoordsY - 92, 90, 190);
		case "energia2":
			return createSpriteWithPositionAndSize("energia2", cameraCoordsX - 48, cameraCoordsY - 92, 90, 190);
		case "energia3":
			return createSpriteWithPositionAndSize("energia3", cameraCoordsX - 48, cameraCoordsY - 92, 90, 190);
		case "energia4":
			return createSpriteWithPositionAndSize("energia4", cameraCoordsX - 48, cameraCoordsY - 92, 90, 190);
		default:
			return null;
		}
	}

	private Sprite createSpriteWithPositionAndSize(String element, float x, float y, float width, float height) {
		Sprite spr_master = atlas_ux.createSprite(element);
		spr_master.setPosition(x, y);
		spr_master.setSize(width, height);
		return spr_master;
	}

	private Sprite createSpriteWithSize(String element, float width, float height) {
		Sprite spr_master = atlas_ux.createSprite(element);
		spr_master.setSize(width, height);
		return spr_master;
	}
	
	public Sprite GetEffectCasting(String element, float width,float height) {
		Sprite spr_master = atlas_castEffect.createSprite(element);
		spr_master.setSize(width, height);
		return spr_master;
	}

	///////////////////////////////////////////////// [Character]///////////////////////////////////////////////
	public Sprite CharSelect(Player player, String type, int num) {
		// basictopM_front1
		if (type.equals("upper")) {
			if (num == 1) {
				if (lstPlayers.get(0).SetUpper.equals("basictop")) {   
					atlas_genericsetTOP = atlas_basicset;
				}
				if (lstPlayers.get(0).SetUpper.equals("sporttop")) {   
					atlas_genericsetTOP = atlas_sportset;
				}
				spr_master = atlas_genericsetTOP
						.createSprite(lstPlayers.get(0).SetUpper + lstPlayers.get(0).Sex + "_front1");
				if (lstPlayers.get(0).Sex.equals("M")) {
					spr_master.setPosition(-64, -36);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(0).Sex.equals("F")) {
					spr_master.setPosition(-64, -36);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
			if (num == 2) {
				if (lstPlayers.get(1).SetUpper.equals("basictop")) {
					atlas_genericsetTOP = atlas_basicset;
				}
				if (lstPlayers.get(1).SetUpper.equals("sporttop")) {   
					atlas_genericsetTOP = atlas_sportset;
				}
				spr_master = atlas_genericsetTOP
						.createSprite(lstPlayers.get(1).SetUpper + lstPlayers.get(1).Sex + "_front1");
				if (lstPlayers.get(1).Sex.equals("M")) {
					spr_master.setPosition(-25, -36);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(1).Sex.equals("F")) {
					spr_master.setPosition(-25, -36);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
			if (num == 3) {
				if (lstPlayers.get(2).SetUpper.equals("basictop")) {
					atlas_genericsetTOP = atlas_basicset;
				}
				if (lstPlayers.get(2).SetUpper.equals("sporttop")) {   
					atlas_genericsetTOP = atlas_sportset;
				}
				spr_master = atlas_genericsetTOP
						.createSprite(lstPlayers.get(2).SetUpper + lstPlayers.get(2).Sex + "_front1");
				if (lstPlayers.get(2).Sex.equals("M")) {
					spr_master.setPosition(10, -36);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(2).Sex.equals("F")) {
					spr_master.setPosition(10, -36);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
		}

		// basicbottomM_front1
		if (type.equals("bottom")) {
			if (num == 1) {
				if (lstPlayers.get(0).SetBottom.equals("basicbottom")) {
					atlas_genericsetBOTTOM = atlas_basicset;
				}
				if (lstPlayers.get(0).SetBottom.equals("sportbottom")) {
					atlas_genericsetBOTTOM = atlas_sportset;
				}
				spr_master = atlas_genericsetBOTTOM
						.createSprite(lstPlayers.get(0).SetBottom + lstPlayers.get(0).Sex + "_front1");
				if (lstPlayers.get(0).Sex.equals("M")) {
					spr_master.setPosition(-64, -48);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(0).Sex.equals("F")) {
					spr_master.setPosition(-64, -48);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
			if (num == 2) {
				if (lstPlayers.get(1).SetBottom.equals("basicbottom")) {
					atlas_genericsetBOTTOM = atlas_basicset;
				}
				if (lstPlayers.get(1).SetBottom.equals("sportbottom")) {
					atlas_genericsetBOTTOM = atlas_sportset;
				}
				spr_master = atlas_genericsetBOTTOM
						.createSprite(lstPlayers.get(1).SetBottom + lstPlayers.get(1).Sex + "_front1");
				if (lstPlayers.get(1).Sex.equals("M")) {
					spr_master.setPosition(-25f, -48);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(1).Sex.equals("F")) {
					spr_master.setPosition(-25f, -48);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
			if (num == 3) {
				if (lstPlayers.get(2).SetBottom.equals("basicbottom")) {
					atlas_genericsetBOTTOM = atlas_basicset;
				}
				if (lstPlayers.get(2).SetBottom.equals("sportbottom")) {
					atlas_genericsetBOTTOM = atlas_sportset;
				}
				spr_master = atlas_genericsetBOTTOM
						.createSprite(lstPlayers.get(2).SetBottom + lstPlayers.get(2).Sex + "_front1");
				if (lstPlayers.get(2).Sex.equals("M")) {
					spr_master.setPosition(10, -48);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(2).Sex.equals("F")) {
					spr_master.setPosition(10, -48);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
		}

		// basicfooterM_front1
		if (type.equals("footer")) {
			if (num == 1) {
				if (lstPlayers.get(0).SetFooter.equals("basicfooter")) {
					atlas_genericsetFOOTER = atlas_basicset;
				}
				if (lstPlayers.get(0).SetFooter.equals("sportfooter")) {
					atlas_genericsetFOOTER = atlas_sportset;
				}
				spr_master = atlas_genericsetFOOTER
						.createSprite(lstPlayers.get(0).SetFooter + lstPlayers.get(0).Sex + "_front1");
				if (lstPlayers.get(0).Sex.equals("M")) {
					spr_master.setPosition(-64, -55);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(0).Sex.equals("F")) {
					spr_master.setPosition(-64.5f, -60);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
			if (num == 2) {
				if (lstPlayers.get(1).SetFooter.equals("basicfooter")) {
					atlas_genericsetFOOTER = atlas_basicset;
				}
				if (lstPlayers.get(1).SetFooter.equals("sportfooter")) {
					atlas_genericsetFOOTER = atlas_sportset;
				}
				spr_master = atlas_genericsetFOOTER
						.createSprite(lstPlayers.get(1).SetFooter + lstPlayers.get(1).Sex + "_front1");
				if (lstPlayers.get(1).Sex.equals("M")) {
					spr_master.setPosition(-25f, -55);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(1).Sex.equals("F")) {
					spr_master.setPosition(-25.5f, -60);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
			if (num == 3) {
				if (lstPlayers.get(2).SetFooter.equals("basicfooter")) {
					atlas_genericsetFOOTER = atlas_basicset;
				}
				if (lstPlayers.get(2).SetFooter.equals("sportfooter")) {
					atlas_genericsetFOOTER = atlas_sportset;
				}
				spr_master = atlas_genericsetFOOTER
						.createSprite(lstPlayers.get(2).SetFooter + lstPlayers.get(2).Sex + "_front1");
				if (lstPlayers.get(2).Sex.equals("M")) {
					spr_master.setPosition(10, -55);
					spr_master.setScale(-0.3f, 0.5f);
				}
				if (lstPlayers.get(2).Sex.equals("F")) {
					spr_master.setPosition(10, -60);
					spr_master.setScale(-0.3f, 0.5f);
				}
				return spr_master;
			}
		}
		return spr_master;
	}

	public Sprite CharHairSelect(String sex, String hair, String color, int num) {
		if (hair.equals("hair1")) {
			spr_master = atlas_hairs1.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair2")) {
			spr_master = atlas_hairs2.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair3")) {
			spr_master = atlas_hairs3.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair4")) {
			spr_master = atlas_hairs4.createSprite(hair + "_front_" + color + "_" + sex);
		}

		spr_master.setScale(-0.3f, 0.5f);
		if (num == 1) {
			spr_master.setPosition(-59, -18);
		}
		if (num == 2) {
			spr_master.setPosition(-20, -18);
		}
		if (num == 3) {
			spr_master.setPosition(15, -18);
		}
		if (num == 99) {
			spr_master.setPosition(-62, 10);
		} // Block hair
		return spr_master;
	}

	public Sprite AllHairs(int num, String sex, String color) {
		if (sex.equals("M")) {
			if (num == 1) {
				spr_master = atlas_hairs1.createSprite("hair1_front_" + color + "_" + sex);
				spr_master.setPosition(-43, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
			if (num == 2) {
				spr_master = atlas_hairs2.createSprite("hair2_front_" + color + "_" + sex);
				spr_master.setPosition(-33, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
			if (num == 3) {
				spr_master = atlas_hairs3.createSprite("hair3_front_" + color + "_" + sex);
				spr_master.setPosition(-23.7f, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
			if (num == 4) {
				spr_master = atlas_hairs4.createSprite("hair4_front_" + color + "_" + sex);
				spr_master.setPosition(-13.9f, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
		}
		if (sex.equals("F")) {
			if (num == 1) {
				spr_master = atlas_hairs1.createSprite("hair1_front_" + color + "_" + sex);
				spr_master.setPosition(-43, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
			if (num == 2) {
				spr_master = atlas_hairs2.createSprite("hair2_front_" + color + "_" + sex);
				spr_master.setPosition(-33, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
			if (num == 3) {
				spr_master = atlas_hairs3.createSprite("hair3_front_" + color + "_" + sex);
				spr_master.setPosition(-23.7f, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
			if (num == 4) {
				spr_master = atlas_hairs4.createSprite("hair4_front_" + color + "_" + sex);
				spr_master.setPosition(-13.9f, -21);
				spr_master.setScale(-0.3f, 0.5f);
			}
		}
		return spr_master;
		}
	
		public Sprite GetHatChar(Player player,String type,float cameraX,float cameraY) {
		
		String side = player.Side;
		String hat = player.Hat;
		float posX = Float.parseFloat(player.PosX);
		float posY = Float.parseFloat(player.PosY);
		
		if(player.playerInCast.equals("yes")) {
			if(!player.Hat.equals("none")) { 
				spr_master = atlas_hats.createSprite(hat + "_front");
				spr_master.setPosition(posX - 19,posY + 17);
				if(hat.equals("hatheadset")) { spr_master.setPosition(posX - 19,posY + 13); }
				spr_master.setScale(0.23f,0.5f);
			}	
		}
		
		if(side.equals("front")) { 
			if(!player.Hat.equals("none")) { 
				spr_master = atlas_hats.createSprite(hat + "_front");
				spr_master.setPosition(posX - 19,posY + 17);
				if(hat.equals("hatheadset")) { spr_master.setPosition(posX - 19,posY + 13); }
				spr_master.setScale(0.23f,0.5f);
			}		
		}
		
		if(side.equals("right")) { 
			if(!player.Hat.equals("none")) { 
				spr_master = atlas_hats.createSprite(hat + "_right");
				spr_master.setPosition(posX - 19,posY + 15.5f);
				if(hat.equals("hatheadset")) { spr_master.setPosition(posX - 19,posY + 10); }
				spr_master.setScale(0.21f,0.5f);	
			}		
		}
		
		if(side.equals("left")) { 
			if(!player.Hat.equals("none")) { 
				spr_master = atlas_hats.createSprite(hat + "_left");
				spr_master.setPosition(posX - 20,posY + 15.5f);
				if(hat.equals("hatheadset")) { spr_master.setPosition(posX - 19,posY + 10); }
				spr_master.setScale(0.21f,0.5f);	
			}		
		}
		
		if(side.equals("back")) { 
			if(!player.Hat.equals("none")) { 
				spr_master = atlas_hats.createSprite(hat + "_up");
				spr_master.setPosition(posX - 19,posY + 17);
				if(hat.equals("hatheadset")) { spr_master.setPosition(posX - 19,posY + 14); }
				spr_master.setScale(0.23f,0.5f);
			}		
		}
		
		return spr_master;
	}
	
	public Sprite GetHatItem(Player player, float cameraX, float cameraY) {
		if(!player.Hat.equals("none")) {
			spr_master = atlas_hatsitem.createSprite(player.Hat);
			return spr_master;
		}
		
		return spr_master;
	}

	public Sprite CharacterCreate(String sex, String type) {
		if (sex.equals("M")) {
			if (type.equals("upper")) {
				spr_master = atlas_basicset.createSprite("basictopM_front1");
				spr_master.setPosition(-67, -8);
				spr_master.setScale(-0.3f, 0.5f);
				return spr_master;
			}
			if (type.equals("bottom")) {
				spr_master = atlas_basicset.createSprite("basicbottomM_front1");
				spr_master.setPosition(-67, -20);
				spr_master.setScale(-0.3f, 0.5f);
				return spr_master;
			}
			if (type.equals("footer")) {
				spr_master = atlas_basicset.createSprite("basicfooterM_front1");
				spr_master.setPosition(-67.2f, -27f);
				spr_master.setScale(-0.3f, 0.5f);
				return spr_master;
			}
		}

		if (sex.equals("F")) {
			if (type.equals("upper")) {
				spr_master = atlas_basicset.createSprite("basictopF_front1");
				spr_master.setPosition(-67, -8);
				spr_master.setScale(-0.3f, 0.5f);
				return spr_master;
			}
			if (type.equals("bottom")) {
				spr_master = atlas_basicset.createSprite("basicbottomF_front1");
				spr_master.setPosition(-67, -20);
				spr_master.setScale(-0.3f, 0.5f);
				return spr_master;
			}
			if (type.equals("footer")) {
				spr_master = atlas_basicset.createSprite("basicfooterF_front1");
				spr_master.setPosition(-67.5f, -31f);
				spr_master.setScale(-0.3f, 0.5f);
				return spr_master;
			}
		}
		return spr_master;
	}

	public Sprite HairCharacterCreate(String sex, String hair, String color, int num) {
		if (hair.equals("hair1")) {
			spr_master = atlas_hairs1.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair2")) {
			spr_master = atlas_hairs2.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair3")) {
			spr_master = atlas_hairs3.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair4")) {
			spr_master = atlas_hairs4.createSprite(hair + "_front_" + color + "_" + sex);
		}

		spr_master.setScale(-0.3f, 0.5f);
		if (num == 1) {
			spr_master.setPosition(-59, -18);
		}
		if (num == 2) {
			spr_master.setPosition(-20, -18);
		}
		if (num == 3) {
			spr_master.setPosition(15, -18);
		}
		if (num == 99) {
			spr_master.setPosition(-62, 10);
		} // Block hair
		return spr_master;
	}

	public Sprite HairCreateSprite(String sex, String hair, String color, int num) {
		if (hair.equals("hair1")) {
			spr_master = atlas_hairs1.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair2")) {
			spr_master = atlas_hairs2.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair3")) {
			spr_master = atlas_hairs3.createSprite(hair + "_front_" + color + "_" + sex);
		}
		if (hair.equals("hair4")) {
			spr_master = atlas_hairs4.createSprite(hair + "_front_" + color + "_" + sex);
		}

		spr_master.setScale(-0.3f, 0.5f);
		if (num == 1) {
			spr_master.setPosition(-59, -18);
		}
		if (num == 2) {
			spr_master.setPosition(-20, -18);
		}
		if (num == 3) {
			spr_master.setPosition(15, -18);
		}
		if (num == 99) {
			spr_master.setPosition(-62, 10);
		} // Block hair
		return spr_master;
	}
	
	public void SetSelectedChar(int _selectedChar) {
		selectedChar = _selectedChar;
	}
	
	public int GetSelectedChar() {
		return selectedChar;
	}
	
	public void UpdatePlayer(Player viewplayer){
		this.playerUse = viewplayer;
	}
	
	public Player ReturnPlayerUse() {
		return playerUse;
	}
	
	public void SetCharacterDefense(boolean defenseState) {
		this.defTrigger = defenseState;
	}
	
	public Sprite GetNPC(String npcname, int frame) {	
		if(npcname.equals("JobMaster")) {  
			spr_master = atlas_npcs.createSprite("NPCS");
			spr_master.setSize(-10, 38);
			spr_master.setPosition(102.5f, -34);
			return spr_master;
		}
		
		if(npcname.equals("CrystalTrader")) {  
			spr_master = atlas_npcs.createSprite("NPCF");
			spr_master.setSize(8, 38);
			spr_master.setPosition(7, -34);
			return spr_master;
		}
		
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
		
		if(npcname.equals("NPCW") && frame == 1) {  
			spr_master = atlas_npcs.createSprite("NPCW");
			spr_master.setSize(8, 33);
			spr_master.setPosition(-5, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCW") && frame == 2) {  
			spr_master = atlas_npcs.createSprite("NPCW2");
			spr_master.setSize(8, 33);
			spr_master.setPosition(-5, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCW") && frame == 3) {  
			spr_master = atlas_npcs.createSprite("NPCW3");
			spr_master.setSize(8, 33);
			spr_master.setPosition(-5, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCO") && frame == 1) {  
			spr_master = atlas_npcs.createSprite("NPCO");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCO") && frame == 2) {  
			spr_master = atlas_npcs.createSprite("NPCO2");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCO") && frame == 3) {  
			spr_master = atlas_npcs.createSprite("NPCO3");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCC") && frame == 1) {  
			spr_master = atlas_npcs.createSprite("NPCC");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCC") && frame == 2) {  
			spr_master = atlas_npcs.createSprite("NPCC2");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCC") && frame == 3) {  
			spr_master = atlas_npcs.createSprite("NPCC3");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCE") && frame == 1) {  
			spr_master = atlas_npcs.createSprite("NPCE");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCE") && frame == 2) {  
			spr_master = atlas_npcs.createSprite("NPCE2");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		if(npcname.equals("NPCE") && frame == 3) {  
			spr_master = atlas_npcs.createSprite("NPCE3");
			spr_master.setSize(8, 33);
			spr_master.setPosition(18, -123.5f);
			return spr_master;
		}
		
		return spr_master;
	}
	
	//[Monsters]//
	public ArrayList<Monster> LoadMonsters(String Map) {
		if(Map.equals("Sewers")) { lstMonsters = placeholderMonster.LoadMonsterDataSewers(Map); }
		if(Map.equals("Forest")) { lstMonsters = placeholderMonster.LoadMonsterDataForest(Map); }
		if(Map.equals("Watercave")) { lstMonsters = placeholderMonster.LoadMonsterDataWatercave(Map); }
		if(Map.equals("Desert")) { lstMonsters = placeholderMonster.LoadMonsterDataDesert(Map); }
		if(Map.equals("Vulcano")) { lstMonsters = placeholderMonster.LoadMonsterDataVulcano(Map); }
		if(Map.equals("Mines")) { lstMonsters = placeholderMonster.LoadMonsterDataMines(Map); }
		if(Map.equals("Snowpalace")) { lstMonsters = placeholderMonster.LoadMonsterDataSnowpalace(Map); }
		if(Map.equals("Swamp")) { lstMonsters = placeholderMonster.LoadMonsterDataSwamp(Map); }
		
		return lstMonsters;
	}

	public Sprite GetMonsterSewers(String mob, int mobframe, String side) {
		//Sewers
		if(mob.equals("poyo")){
			String mobdata = "poyo" + mobframe;
			spr_master = atlas_mobSewers.createSprite("poyo" + mobframe);
			return spr_master;
		}
		if(mob.equals("aranarc")){
			spr_master = atlas_mobSewers.createSprite("aranarc" + mobframe);
			return spr_master;
		}
		if(mob.equals("rat")){
			spr_master = atlas_mobSewers.createSprite("rat" + mobframe);
			return spr_master;
		}
		if(mob.equals("snake")){
			spr_master = atlas_mobSewers.createSprite("snake" + mobframe);
			return spr_master;
		}	

		return spr_master;
	}
	
	public Sprite GetMonsterForest(String mob, int mobframe, String side) {
		//Forest
		if(mob.equals("bee")){
			spr_master = atlas_mobForest.createSprite("bee" + mobframe);
			return spr_master;
		}
		if(mob.equals("goblin")){
			spr_master = atlas_mobForest.createSprite("goblin" + mobframe);
			return spr_master;
		}
		if(mob.equals("slime")){
			spr_master = atlas_mobForest.createSprite("slime" + mobframe);
			return spr_master;
		}
		if(mob.equals("enty")){
			spr_master = atlas_mobForest.createSprite("enty" + mobframe);
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite GetMonsterWatercave(String mob, int mobframe, String side) {
		//Forest
		if(mob.equals("fisko")){
			spr_master = atlas_mobWatercave.createSprite("fisko" + mobframe);
			return spr_master;
		}
		if(mob.equals("shelly")){
			spr_master = atlas_mobWatercave.createSprite("shelly" + mobframe);
			return spr_master;
		}
		if(mob.equals("marit")){
			spr_master = atlas_mobWatercave.createSprite("marit" + mobframe);
			return spr_master;
		}
		if(mob.equals("tencle")){
			spr_master = atlas_mobWatercave.createSprite("tencle" + mobframe);
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite GetMonsterDesert(String mob, int mobframe, String side) {
		//Forest
		if(mob.equals("cactus")){
			spr_master = atlas_mobDesert.createSprite("cactus" + mobframe);
			return spr_master;
		}
		if(mob.equals("harpia")){
			spr_master = atlas_mobDesert.createSprite("harpia" + mobframe);
			return spr_master;
		}
		if(mob.equals("golem")){
			spr_master = atlas_mobDesert.createSprite("golem" + mobframe);
			return spr_master;
		}
		if(mob.equals("brimworn")){
			spr_master = atlas_mobDesert.createSprite("brimworn" + mobframe);
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite GetMonsterVulcano(String mob, int mobframe, String side) {
		//Forest
		if(mob.equals("flare")){
			spr_master = atlas_mobVulcano.createSprite("flare" + mobframe);
			return spr_master;
		}
		if(mob.equals("salamander")){
			spr_master = atlas_mobVulcano.createSprite("salamander" + mobframe);
			return spr_master;
		}
		if(mob.equals("cerberus")){
			spr_master = atlas_mobVulcano.createSprite("cerberus" + mobframe);
			return spr_master;
		}
		if(mob.equals("hammertooth")){
			spr_master = atlas_mobVulcano.createSprite("hammertooth" + mobframe);
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite GetMonsterMines(String mob, int mobframe, String side) {
		//Forest
		if(mob.equals("ghost")){
			spr_master = atlas_mobMines.createSprite("ghost" + mobframe);
			return spr_master;
		}
		if(mob.equals("tipper")){
			spr_master = atlas_mobMines.createSprite("tipper" + mobframe);
			return spr_master;
		}
		if(mob.equals("urso")){
			spr_master = atlas_mobMines.createSprite("urso" + mobframe);
			return spr_master;
		}
		if(mob.equals("caveira")){
			spr_master = atlas_mobMines.createSprite("caveira" + mobframe);
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite GetMonsterSnowpalace(String mob, int mobframe, String side) {
		//Forest
		if(mob.equals("goblin")){
			spr_master = atlas_mobSnowpalace.createSprite("goblin" + mobframe);
			return spr_master;
		}
		if(mob.equals("pinguim")){
			spr_master = atlas_mobSnowpalace.createSprite("pinguim" + mobframe);
			return spr_master;
		}
		if(mob.equals("snowman")){
			spr_master = atlas_mobSnowpalace.createSprite("snowman" + mobframe);
			return spr_master;
		}
		if(mob.equals("yeti")){
			spr_master = atlas_mobSnowpalace.createSprite("yeti" + mobframe);
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite GetMonsterSwamp(String mob, int mobframe, String side) {
		//Forest
		if(mob.equals("eyeball")){
			spr_master = atlas_mobSwamp.createSprite("eyeball" + mobframe);
			return spr_master;
		}
		if(mob.equals("onulu")){
			spr_master = atlas_mobSwamp.createSprite("onulu" + mobframe);
			return spr_master;
		}
		if(mob.equals("cockatrix")){
			spr_master = atlas_mobSwamp.createSprite("cockatrix" + mobframe);
			return spr_master;
		}
		if(mob.equals("mantis")){
			spr_master = atlas_mobSwamp.createSprite("mantis" + mobframe);
			return spr_master;
		}
		
		return spr_master;
	}
	
	//[Char movement]//
	public void SetAttackFrame() {
		FrameAtkPlayer = 30;
	}
	
	public Player SetCharMov(Player _playerUse, String type) {  
		playerUse = _playerUse;
		if(playerUse.countFrame.equals("")) { playerUse.countFrame = "1"; }
		float posX = Float.parseFloat(playerUse.PosX);
		float posY = Float.parseFloat(playerUse.PosY);
		int countFrame = Integer.parseInt(playerUse.countFrame);
		
		//Check MovePosition
		if(playerUse.Walk.equals("walk")) {
			if(playerUse.Side.equals("front") && !playerUse.breakwalk.equals("front")) { posY = posY - 0.5f; playerUse.PosY = String.valueOf(posY); }
			if(playerUse.Side.equals("back") && !playerUse.breakwalk.equals("back")) { posY = posY + 0.5f; playerUse.PosY = String.valueOf(posY); }
			if(playerUse.Side.equals("left") && !playerUse.breakwalk.equals("left")) { posX = posX - 0.5f; playerUse.PosX = String.valueOf(posX); }
			if(playerUse.Side.equals("right") && !playerUse.breakwalk.equals("right")) { posX = posX + 0.5f; playerUse.PosX = String.valueOf(posX); }
		}
		
		//Check Frames
		if(!playerUse.Walk.equals("no")) { countFrame = countFrame + 1;  }
		//if(playerUse.Walk.equals("no")) { countFrame = 1;  }
		if(playerUse.playerInBattle.equals("yes")) { countFrame = countFrame + 1; }
		if(countFrame > 1 && countFrame <= 10) { playerUse.Frame = "2"; }
		if(countFrame >= 10 && countFrame <= 20) { playerUse.Frame = "1";  }
		if(countFrame >= 20 && countFrame <= 30) { playerUse.Frame = "3";  }
		if(countFrame >= 30 && countFrame <= 40) { playerUse.Frame = "1";  }
		if(countFrame >= 40) { countFrame = 1; playerUse.Frame = "1"; }
		playerUse.countFrame = String.valueOf(countFrame); 
				
		return playerUse;
	}
	
	public Sprite GetHairChar(Player player, String menu, float cameraX, float cameraY) {
		
		//hair1_front_green_M
		float posX = Float.parseFloat(player.PosX);
		float posY = Float.parseFloat(player.PosY);
		int frame = Integer.parseInt(player.Frame);
		
		if(defTrigger) {
			player.Side = "front";
		}
		
		if(player.Sex.equals("M")) {
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			
			if(player.Side.equals("front")) { 
				if(frame == 1) { spr_master.setPosition(posX -20, posY + 10); }
				if(frame == 2) { spr_master.setPosition(posX -20, posY + 9.7f); }
				if(frame == 3) { spr_master.setPosition(posX -20, posY + 10.3f); }				
			}
			
			if(player.Side.equals("back")) { 
				if(frame == 1) { spr_master.setPosition(posX -20, posY + 10);  }
				if(frame == 2) { spr_master.setPosition(posX -20, posY + 9.7f);  }
				if(frame == 3) { spr_master.setPosition(posX -20, posY + 10.3f);  }			 
			}
			
			if(player.Side.equals("left")) { 
				if(frame == 1) { spr_master.setPosition(posX -21, posY + 10); } 
				if(frame == 2) { spr_master.setPosition(posX -21.3f, posY + 9.7f); } 
				if(frame == 3) { spr_master.setPosition(posX -20.6f, posY + 10.3f); } 			
			}
			
			if(player.Side.equals("right")) { 
				if(frame == 1) { spr_master.setPosition(posX -19.5f, posY + 10);   } 
				if(frame == 2) { spr_master.setPosition(posX -19.8f, posY + 9.7f);   } 
				if(frame == 3) { spr_master.setPosition(posX -19.1f, posY + 10.3f);   } 
			}
			
			spr_master.setScale(0.2f,0.4f);	
		}
		if(player.Sex.equals("F")) {
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "_" + player.Side + "_" + player.Color + "_" + player.Sex); }
			
			if(player.Side.equals("front")) { 
				if(frame == 1) { spr_master.setPosition(posX -20, posY + 9.4f);    } 
				if(frame == 2) { spr_master.setPosition(posX -20, posY + 9f);    } 
				if(frame == 3) { spr_master.setPosition(posX -20, posY + 9.6f);     } 
			}
			if(player.Side.equals("back")) { 
				if(frame == 1) { spr_master.setPosition(posX -20, posY + 10);     } 
				if(frame == 2) { spr_master.setPosition(posX -20, posY + 10.2f);      } 
				if(frame == 3) { spr_master.setPosition(posX -20, posY + 9.8f);      } 				
			}
			if(player.Side.equals("left")) { 
				if(frame == 1) { spr_master.setPosition(posX -20.7f, posY + 8.8f);  } 
				if(frame == 2) { spr_master.setPosition(posX -21.7f, posY + 8.8f);  } 
				if(frame == 3) { spr_master.setPosition(posX -21.7f, posY + 8.8f);  } 				
			}
			if(player.Side.equals("right")) { 
				if(frame == 1) { spr_master.setPosition(posX -19.5f, posY + 8.8f);  } 
				if(frame == 2) { spr_master.setPosition(posX -18f, posY + 8.5f);  } 
				if(frame == 3) { spr_master.setPosition(posX -18.5f, posY + 8.5f);  } 				
			}
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
				spr_master.setPosition(cameraX -84.2f, cameraY + 33.5f);
				spr_master.setScale(0.4f,0.7f);
			}
		}

		if(player.playerInBattle.equals("yes") || player.playerInCast.equals("yes")){
			if(player.Hair.equals("hair1")) { spr_master = atlas_hairs1.createSprite(player.Hair + "_attack_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair2")) { spr_master = atlas_hairs2.createSprite(player.Hair + "_attack_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair3")) { spr_master = atlas_hairs3.createSprite(player.Hair + "_attack_" + player.Color + "_" + player.Sex); }
			if(player.Hair.equals("hair4")) { spr_master = atlas_hairs4.createSprite(player.Hair + "_attack_" + player.Color + "_" + player.Sex); }
			
			if(frame == 1) { spr_master.setPosition(posX -20, posY + 10);  }
			if(frame == 2) { spr_master.setPosition(posX -20, posY + 9.7f);  }
			if(frame == 3) { spr_master.setPosition(posX -20, posY + 10.3f);  }
			
			//spr_master.setPosition(posX -20, posY + 10);
			spr_master.setScale(0.2f,0.4f);
		}
		
		return spr_master;
	}
	
	public Sprite GetTopChar(Player player , String menu, float cameraX, float cameraY) {
		//Top 1
		if(player.SetUpper.equals("basictop")) { atlas_genericsetTOP = atlas_basicset; }
		if(player.SetUpper.equals("sporttop")) { atlas_genericsetTOP = atlas_sportset; }
		
		float posX = Float.parseFloat(player.PosX);
		float posY = Float.parseFloat(player.PosY);
		int frame = Integer.parseInt(player.Frame);
		
		//[MALE]
		if(player.Sex.equals("M")) {
			spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("left")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("right")) { spr_master.setPosition(posX -25,posY -5);  }
			spr_master.setScale(0.2f,0.4f);	
			
			if(player.playerInBattle.equals("yes")){
				if(frame == 3) { player.Frame = "2"; }
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_attack" + player.Frame);
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);							
			}
				
			if(player.playerInAttack.equals("yes")){
				if(FrameAtkPlayer > 0) {
				if(frame == 3) { player.Frame = "1"; }
					spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_attack4");
					spr_master.setPosition(posX -25,posY -5);
					spr_master.setScale(0.2f,0.4f);	
					FrameAtkPlayer--;
				}
				if(FrameAtkPlayer <= 0) {
					FrameAtkPlayer = 0;
					player.playerInAttack = "no";
					player.Frame = "1";
				}
			}
			
			if(player.playerInCast.equals("yes")) {
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_cast");
				spr_master.setPosition(posX -29,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			if(defTrigger) {
				player.Frame = "1";
				frame = 1;
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_defense1");
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = "1";
				player.Side = "front";
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_sit");
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		//[FEMALE]//
		if(player.Sex.equals("F")) {
			spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { spr_master.setPosition(posX -25,posY -4.5f);  }
			if(player.Side.equals("back")) { spr_master.setPosition(posX -25,posY -5);  }
			if(player.Side.equals("left")) { 
				if(frame == 1) { spr_master.setPosition(posX -25.2f,posY -4.5f);   }
				if(frame == 2) { spr_master.setPosition(posX -26,posY -4.5f);   }
				if(frame == 3) { spr_master.setPosition(posX -26,posY -5.2f);   }
			}
			if(player.Side.equals("right")) { 
				spr_master.setPosition(posX -25,posY -5);
				if(frame == 1) { spr_master.setPosition(posX -24.7f,posY -4.5f);  }
				if(frame == 2) { spr_master.setPosition(posX -23.7f,posY -5.5f);  }
				if(frame == 3) { spr_master.setPosition(posX -24,	posY -5.2f);  }
			}
			spr_master.setScale(0.2f,0.35f);	
			
			if(player.playerInBattle.equals("yes")){
				if(frame == 3) { player.Frame = "2"; }
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_attack" + player.Frame);
				if(frame == 1) { spr_master.setPosition(posX -25,posY -4);  }
				if(frame == 2) { spr_master.setPosition(posX -25,posY -4.3f);  }
				if(frame == 3) { spr_master.setPosition(posX -25,posY -3.7f);  }
				
				spr_master.setScale(0.2f,0.4f);
			}
			
			if(player.playerInAttack.equals("yes")){
				if(FrameAtkPlayer > 0) {
				if(frame == 3) { player.Frame = "1"; }
					spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_attack3");
					spr_master.setPosition(posX -25,posY -5);
					spr_master.setScale(0.2f,0.4f);	
					FrameAtkPlayer--;
				}
				if(FrameAtkPlayer <= 0) {
					FrameAtkPlayer = 0;
					player.playerInAttack = "no";
					player.Frame = "1";
				}
			}
			
			if(player.playerInCast.equals("yes")) {
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_cast");
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			if(defTrigger) {
				player.Frame = "1";
				frame = 1;
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_defense1");
				spr_master.setPosition(posX -25,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			//basictopF_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = "1";
				player.Side = "front";
				spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_sit");
				spr_master.setPosition(posX -25.2f,posY -5);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		if(menu.equals("yes")){
			player.Side = "front";
			spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_" + player.Side + 1);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(cameraX + 8, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side = "front";
			spr_master = atlas_genericsetTOP.createSprite(player.SetUpper + player.Sex + "_" + player.Side + 1);
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
		if(player.SetBottom.equals("basicbottom")) { atlas_genericsetBOTTOM = atlas_basicset; }
		if(player.SetBottom.equals("sportbottom")) { atlas_genericsetBOTTOM = atlas_sportset; }
		
		float posX = Float.parseFloat(player.PosX);
		float posY = Float.parseFloat(player.PosY);
		int frame = Integer.parseInt(player.Frame);
		
		/// [BOTTOM MALE] //////
		if(player.Sex.equals("M")) {
			spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { 
				if(frame == 1) {  spr_master.setPosition(posX -25,posY -15);    }
				if(frame == 2) {  spr_master.setPosition(posX -25,posY -15);    }
				if(frame == 3) {  spr_master.setPosition(posX -25,posY -15);    }
			}
			if(player.Side.equals("back")) { 
				if(frame == 1) { spr_master.setPosition(posX -25,posY -14.2f);	}
				if(frame == 2) { spr_master.setPosition(posX -25,posY -13f);  	}
				if(frame == 3) { spr_master.setPosition(posX -25,posY -13f);  	}		
			}
			
			if(player.Side.equals("right")) { 
				if(frame == 1) { spr_master.setPosition(posX -25,posY -16.5f);  }
				if(frame == 2) { spr_master.setPosition(posX -26,posY -16.5f);  }
				if(frame == 3) { spr_master.setPosition(posX -26,posY -16.5f);  }
			}
			
			if(player.Side.equals("left")) { 
				if(frame == 1) { spr_master.setPosition(posX -25,posY -16.5f);  }
				if(frame == 2) { spr_master.setPosition(posX -24,posY -16.5f);  }
				if(frame == 3) { spr_master.setPosition(posX -24,posY -16.5f);  }		
			}	
			
			spr_master.setScale(0.2f,0.4f);

			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_attack1");
				spr_master.setPosition(posX -24.5f,posY -15);
				spr_master.setScale(0.2f,0.4f);					
			}
			
			if(player.playerInCast.equals("yes")){
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_attack1");
				spr_master.setPosition(posX -24.5f,posY -15);
				spr_master.setScale(0.2f,0.4f);					
			}
			
			if(defTrigger) {
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_attack1");
				spr_master.setPosition(posX -24.5f,posY -15);
				spr_master.setScale(0.2f,0.4f);			
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = "1";
				player.Side = "front";
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_sit");
				spr_master.setPosition(posX -25,posY -15);
				spr_master.setScale(0.2f,0.4f);	
			}
		}
		
		/// [BOTTOM FEMALE] //////
		if(player.Sex.equals("F")) {
			spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) {
				if(frame == 1) { spr_master.setPosition(posX -25.2f,posY -14f);   }
				if(frame == 2) { spr_master.setPosition(posX -25.4f,posY -14f);   }
				if(frame == 3) { spr_master.setPosition(posX -24.6f,posY -14f);   }
			}
			if(player.Side.equals("back")) { 
				if(frame == 1) { spr_master.setPosition(posX -25,posY -14.2f);    }
				if(frame == 2) { spr_master.setPosition(posX -25,posY -14.2f);    }
				if(frame == 3) { spr_master.setPosition(posX -25,posY -14.2f);    }			
			}
			
			if(player.Side.equals("right")) { 
				if(frame == 1) { spr_master.setPosition(posX -25f,posY -13.5f);	  }
				if(frame == 2) { spr_master.setPosition(posX -24.8f,posY -15.4f);     }
				if(frame == 3) { spr_master.setPosition(posX -25.2f,posY -15.4f);   }
			}
			if(player.Side.equals("left")) { 	
				if(frame == 1) { spr_master.setPosition(posX -25f,posY -13.5f); 	}				
				if(frame == 2) { spr_master.setPosition(posX -24.6f,posY -15.4f);   }
				if(frame == 3) { spr_master.setPosition(posX -24.5f,posY -15.8f);   }
			}
			
			
			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_attack1");
				if(frame == 1) { spr_master.setPosition(posX -24.7f,posY -14.6f); 	}				
				if(frame == 2) { spr_master.setPosition(posX -24.7f,posY -14.8f);   }
				if(frame == 3) { spr_master.setPosition(posX -24.7f,posY -14.4f);   }
				
				spr_master.setScale(0.2f,0.4f);	
			}
			
			if(player.playerInCast.equals("yes")){
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_attack1");
				if(frame == 1) { spr_master.setPosition(posX -24.7f,posY -14.6f); 	}				
				if(frame == 2) { spr_master.setPosition(posX -24.7f,posY -14.8f);   }
				if(frame == 3) { spr_master.setPosition(posX -24.7f,posY -14.4f);   }
				
				spr_master.setScale(0.2f,0.4f);	
			}
			
			if(defTrigger) {
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_attack1");
				if(frame == 1) { spr_master.setPosition(posX -24.7f,posY -14.6f); 	}				
				if(frame == 2) { spr_master.setPosition(posX -24.7f,posY -14.8f);   }
				if(frame == 3) { spr_master.setPosition(posX -24.7f,posY -14.4f);   }
				
				spr_master.setScale(0.2f,0.4f);	
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = "1";
				player.Side = "front";
				spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_sit");
				spr_master.setPosition(posX -25.2f,posY -12);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			spr_master.setScale(0.2f,0.4f);
		}

		if(menu.equals("yes")){
			player.Side = "front";
			spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_" + player.Side + 1);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(cameraX - 5.5f, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side = "front";
			spr_master = atlas_genericsetBOTTOM.createSprite(player.SetBottom + player.Sex + "_" + player.Side + 1);
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
		if(player.SetFooter.equals("basicfooter")) { atlas_genericsetFOOTER = atlas_basicset; }
		if(player.SetFooter.equals("sportfooter")) { atlas_genericsetFOOTER = atlas_sportset; }
		
		float posX = Float.parseFloat(player.PosX);
		float posY = Float.parseFloat(player.PosY);
		int frame = Integer.parseInt(player.Frame);
		
		/// [FOOTER MALE ] //////
		if(player.Sex.equals("M")) {
			spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { 
				if(frame == 1) { spr_master.setPosition(posX -25,posY -21);  }
				if(frame == 2) { spr_master.setPosition(posX -24.8f,posY -21); }
				if(frame == 3) { spr_master.setPosition(posX -25,posY -20.5f); }
			}
			if(player.Side.equals("back")) {
				if(frame == 1) { spr_master.setPosition(posX -25,posY -21); }
				if(frame == 2) { spr_master.setPosition(posX -25,posY -19); }
				if(frame == 3) { spr_master.setPosition(posX -25,posY -19); }
			}	
			if(player.Side.equals("right")) { 
				if(frame == 1) { spr_master.setPosition(posX -24.8f,posY -19.2f); }
				if(frame == 2) { spr_master.setPosition(posX -25.5f,posY -20f);   }
				if(frame == 3) { spr_master.setPosition(posX -25.5f,posY -20f);   }
			}
			if(player.Side.equals("left")) { 
				if(frame == 1) { spr_master.setPosition(posX -25.4f,posY -19.5f); }
				if(frame == 2) { spr_master.setPosition(posX -24f,posY -20.5f);   }
				if(frame == 3) { spr_master.setPosition(posX -24.8f,posY -19.9f);   }
			}	
			spr_master.setScale(0.2f,0.4f);	
			
			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_front2");
				spr_master.setPosition(posX -25,posY -21);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			if(player.playerInCast.equals("yes")){
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_front2");
				spr_master.setPosition(posX -25,posY -21);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			if(defTrigger) {
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_front2");
				spr_master.setPosition(posX -25,posY -21);
				spr_master.setScale(0.2f,0.4f);	
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = "1";
				player.Side = "front";
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_sit");
				spr_master.setPosition(posX -25,posY -18);
				spr_master.setScale(0.2f,0.4f);	
			}
		}
		
		/// [FOOTER FEMALE ] //////
		if(player.Sex.equals("F")) {
			spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_" + player.Side + player.Frame);
			if(player.Side.equals("front")) { 
				if(frame == 1) { spr_master.setPosition(posX -25f,posY -22.5f); }
				if(frame == 2) { spr_master.setPosition(posX -25.2f,posY -21.5f);   }
				if(frame == 3) { spr_master.setPosition(posX -25f,posY -21.5f);   }
			}
			if(player.Side.equals("back")) { 
				if(frame == 1) { spr_master.setPosition(posX -25,posY -21);  }
				if(frame == 2) { spr_master.setPosition(posX -25,posY -19.5f);  }
				if(frame == 3) { spr_master.setPosition(posX -25,posY -20.9f);  }
			}	
			
			if(player.Side.equals("left")) { 		
				if(frame == 1) { spr_master.setPosition(posX -25.4f,posY -21.5f);    }
				if(frame == 2) { spr_master.setPosition(posX -25.5f,posY -22f);   }
				if(frame == 3) { spr_master.setPosition(posX -25.5f,posY -22.8f);   }
			}	
			if(player.Side.equals("right")) { 
				if(frame == 1) { spr_master.setPosition(posX -24.5f,posY -21.5f); }
				if(frame == 2) { spr_master.setPosition(posX -23.9f,posY -22f);   }
				if(frame == 3) { spr_master.setPosition(posX -24.2f,posY -21.6f);  }
			}
			spr_master.setScale(0.2f,0.4f);
			
			if(player.playerInBattle.equals("yes")){
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_attack1");
				spr_master.setPosition(posX -24.7f,posY -22.5f);
				spr_master.setScale(0.2f,0.4f);								
			}
			
			if(player.playerInCast.equals("yes")){
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_attack1");
				spr_master.setPosition(posX -24.7f,posY -22.5f);
				spr_master.setScale(0.2f,0.4f);								
			}
			
			if(defTrigger) {
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_attack1");
				spr_master.setPosition(posX -24.7f,posY -22.5f);
				spr_master.setScale(0.2f,0.4f);		
			}
			
			//basictopM_sit
			if(player.playerSit.equals("yes")) {
				player.Frame = "1";
				player.Side = "front";
				spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_sit");
				spr_master.setPosition(posX -25.2f,posY -12);
				spr_master.setScale(0.2f,0.4f);	
			}
		}

		if(menu.equals("yes")){
			player.Side = "front";
			spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_" + player.Side + 1);
			if(player.Side.equals("front")) { 
				spr_master.setPosition(cameraX + 22, cameraY + 27);
				spr_master.setScale(0.2f,0.4f);
			}
		}
		if(menu.equals("Show")){
			player.Side = "front";
			spr_master = atlas_genericsetFOOTER.createSprite(player.SetFooter + player.Sex + "_" + player.Side + 1);
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
	
	public Sprite SetWeapon(Player playerUse) {   
		
		//playerUse.playerInBattle = "yes";
		//playerInAttack = true;
		
		float posX = Float.parseFloat(playerUse.PosX);
		float posY = Float.parseFloat(playerUse.PosY);
		int frame = Integer.parseInt(playerUse.Frame);
		
		if(playerUse.Job.equals("Aprendiz")) { atlas_weapongeneric = atlas_nknifes; }
		if(playerUse.Job.equals("Espadachim")) { atlas_weapongeneric = atlas_swords; }
		if(playerUse.Job.equals("Feiticeiro")) { atlas_weapongeneric = atlas_rods; }
		if(playerUse.Job.equals("Batedor")) { atlas_weapongeneric = atlas_axes; }
		if(playerUse.Job.equals("Pistoleiro")) { atlas_weapongeneric = atlas_pistols; }
		if(playerUse.Job.equals("Curandeiro")) { atlas_weapongeneric = atlas_rods; }
		if(playerUse.Job.equals("Ladrao")) { atlas_weapongeneric = atlas_daggers; }
			
		if(playerUse.playerInBattle.equals("yes")) {
				if(playerUse.Weapon.contains("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 13.5f, posY + 11f); }
				if(playerUse.Weapon.contains("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 2.9f, posY + 6f); }
			
			if(playerUse.Job.equals("Espadachim") || playerUse.Job.equals("Cavaleiro") || playerUse.Job.equals("Protetor")) {
				if(playerUse.Weapon.contains("woodsword")) { spr_master = atlas_swords.createSprite("wood_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("ragesword")) { spr_master = atlas_swords.createSprite("rage_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("serpentsword")) { spr_master = atlas_swords.createSprite("venom_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("flamesword")) { spr_master = atlas_swords.createSprite("edge_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("cristalsword")) { spr_master = atlas_swords.createSprite("knight_sword_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }					
			}
			
			if(playerUse.Job.equals("Mago") || playerUse.Job.equals("Elementarista") || playerUse.Job.equals("Invocador") || playerUse.Job.equals("Curandeiro") || playerUse.Job.equals("Sacerdote") || playerUse.Job.equals("Lutador")) {
				if(playerUse.Weapon.contains("stickrod")) { spr_master = atlas_rods.createSprite("stick_rod_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16.8f, posY + 8f); }
				if(playerUse.Weapon.contains("gloomrod")) { spr_master = atlas_rods.createSprite("gloom_rod_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16.8f, posY + 8f); }
				if(playerUse.Weapon.contains("starrod")) { spr_master = atlas_rods.createSprite("gem_rod_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16.8f, posY + 8f); }
				if(playerUse.Weapon.contains("deathrod")) { spr_master = atlas_rods.createSprite("lightwield_rod_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16.8f, posY + 8f); }
				if(playerUse.Weapon.contains("butterflyrod")) { spr_master = atlas_rods.createSprite("serpent_rod_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16.8f, posY + 8f); }								
			}
			
			if(playerUse.Job.equals("Atirador") || playerUse.Job.equals("Especialista") || playerUse.Job.equals("Musico")) {
				if(playerUse.Weapon.contains("basicpistol")) { spr_master = atlas_pistols.createSprite("basic_pistol_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16f, posY + 4f); }
				if(playerUse.Weapon.contains("lightpistol")) { spr_master = atlas_pistols.createSprite("revolver_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16f, posY + 4f); }
				if(playerUse.Weapon.contains("shooterpistol")) { spr_master = atlas_pistols.createSprite("light_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16f, posY + 4f); }
				if(playerUse.Weapon.contains("sharkpistol")) { spr_master = atlas_pistols.createSprite("turret_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16f, posY + 4f); }
				if(playerUse.Weapon.contains("cannonpistol")) { spr_master = atlas_pistols.createSprite("rifle_pistol_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 16f, posY + 4f); }											
			}
			
			if(playerUse.Job.equals("Ladrao") || playerUse.Job.equals("Assassino") || playerUse.Job.equals("Vigarista")) {
				if(playerUse.Weapon.contains("basicdagger")) { spr_master = atlas_daggers.createSprite("basicdagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("flamebergdagger")) { spr_master = atlas_daggers.createSprite("edgedagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("marinedagger")) { spr_master = atlas_daggers.createSprite("poisondagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("winddagger")) { spr_master = atlas_daggers.createSprite("marinedagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }
				if(playerUse.Weapon.contains("thunderdagger")) { spr_master = atlas_daggers.createSprite("triploadagger_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 12.9f, posY + 11f); }									
			}			
			if(playerUse.Job.equals("Batedor") || playerUse.Job.equals("Mecanico") || playerUse.Job.equals("Alquimista") || playerUse.Job.equals("Lutador")) {
				if(playerUse.Weapon.contains("basicaxe")) { spr_master = atlas_axes.createSprite("basic_axe_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 13.5f, posY + 11f); }
				if(playerUse.Weapon.contains("hammeraxe")) { spr_master = atlas_axes.createSprite("hammer_axe_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 2.9f, posY + 6f); }
				if(playerUse.Weapon.contains("scytheaxe")) { spr_master = atlas_axes.createSprite("scythe_axe_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 2.9f, posY + 6f); }
				if(playerUse.Weapon.contains("anchoraxe")) { spr_master = atlas_axes.createSprite("anchor_axe_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 2.9f, posY + 6f); }
				if(playerUse.Weapon.contains("guitaraxe")) { spr_master = atlas_axes.createSprite("guitar_axe_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 2.9f, posY + 6f); }									
			}
		}
		
		if(playerUse.playerInAttack.equals("yes")) {
			if(playerUse.Job.equals("Aprendiz")) {
				if(playerUse.Weapon.contains("basicknife")) { spr_master = atlas_nknifes.createSprite("basic_knife_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("doubleedgeknife")) { spr_master = atlas_nknifes.createSprite("doubleedge_knife_attack_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
			}
			
			if(playerUse.Job.equals("Espadachim") || playerUse.Job.equals("Cavaleiro") || playerUse.Job.equals("Protetor")) {
				if(playerUse.Weapon.contains("woodsword")) { spr_master = atlas_swords.createSprite("wood_sword_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("ragesword")) { spr_master = atlas_swords.createSprite("rage_sword_attack_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("serpentsword")) { spr_master = atlas_swords.createSprite("serpent_sword_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("flamesword")) { spr_master = atlas_swords.createSprite("flame_sword_attack_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("cristalsword")) { spr_master = atlas_swords.createSprite("cristal_sword_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
			}
			
			if(playerUse.Job.equals("Mago") || playerUse.Job.equals("Elementarista") || playerUse.Job.equals("Invocador") || playerUse.Job.equals("Curandeiro") || playerUse.Job.equals("Sacerdote") || playerUse.Job.equals("Lutador")) {
				if(playerUse.Weapon.contains("stickrod")) { spr_master = atlas_rods.createSprite("stick_rod_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("gloomrod")) { spr_master = atlas_rods.createSprite("gloom_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("starrod")) { spr_master = atlas_rods.createSprite("star_rod_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("deathrod")) { spr_master = atlas_rods.createSprite("death_rod_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("butterflyrod")) { spr_master = atlas_rods.createSprite("butterfly_rod_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }			
			}
			
			if(playerUse.Job.equals("Atirador") || playerUse.Job.equals("Especialista") || playerUse.Job.equals("Musico")) {
				if(playerUse.Weapon.contains("basicpistol")) { spr_master = atlas_pistols.createSprite("basic_pistol_attack_left"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("lightpistol")) { spr_master = atlas_pistols.createSprite("light_pistol_attack_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("shooterpistol")) { spr_master = atlas_pistols.createSprite("shooter_pistol_attack_left"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("sharkpistol")) { spr_master = atlas_pistols.createSprite("shark_pistol_attack_left"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("cannonpistol")) { spr_master = atlas_pistols.createSprite("cannon_pistol_attack_left"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }			
			}
			
			if(playerUse.Job.equals("Ladrao") || playerUse.Job.equals("Assassino") || playerUse.Job.equals("Vigarista")) {
				if(playerUse.Weapon.contains("basicdagger")) { spr_master = atlas_daggers.createSprite("basicdagger_attack_right"); spr_master.setSize(18, 26); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("flamebergdagger")) { spr_master = atlas_daggers.createSprite("flamebergdagger_attack_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("marinedagger")) { spr_master = atlas_daggers.createSprite("marinedagger_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("winddagger")) { spr_master = atlas_daggers.createSprite("winddagger_attack_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("thunderdagger")) { spr_master = atlas_daggers.createSprite("thunderdagger_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }			
			}
			
			if(playerUse.Job.equals("Batedor") || playerUse.Job.equals("Mecanico") || playerUse.Job.equals("Alquimista") || playerUse.Job.equals("Lutador")) {
				if(playerUse.Weapon.contains("basicaxe")) { spr_master = atlas_axes.createSprite("basic_axe_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("hammeraxe")) { spr_master = atlas_axes.createSprite("hammer_axe_attack_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("scytheaxe")) { spr_master = atlas_axes.createSprite("scythe_axe_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("anchoraxe")) { spr_master = atlas_axes.createSprite("anchor_axe_attack_right"); spr_master.setSize(20, 28); spr_master.setPosition(posX - 4.8f, posY + 2f);  }
				if(playerUse.Weapon.contains("guitaraxe")) { spr_master = atlas_axes.createSprite("guitar_axe_attack_right"); spr_master.setSize(20, 30); spr_master.setPosition(posX - 4.8f, posY + 2f);  }			
			}
			
		}
		
		return spr_master;
		
	}
	
	
	public Sprite GetHairCharTag(Player player,float cameraCoordsX,float cameraCoordsY) {
		
		if(player.Hair.equals("hair1")) { atlas_generichair = atlas_hairs1;}
		if(player.Hair.equals("hair2")) { atlas_generichair = atlas_hairs2;}
		if(player.Hair.equals("hair3")) { atlas_generichair = atlas_hairs3;}
		if(player.Hair.equals("hair4")) { atlas_generichair = atlas_hairs4;}
		//if(player.Hair_A.equals("hair5")) { atlas_generichair = atlas_hairs5;}
		
		//hair1_front_green_M
		if(player.Sex.equals("M")) {
			spr_master = atlas_generichair.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);
			spr_master.setPosition(cameraCoordsX -115,cameraCoordsY + 71); 
			spr_master.setScale(0.2f,0.4f);		
		}
		if(player.Sex.equals("F")) {
			spr_master = atlas_generichair.createSprite(player.Hair + "_" + "front" + "_" + player.Color + "_" + player.Sex);
			spr_master.setPosition(cameraCoordsX -115,cameraCoordsY + 70); 
			spr_master.setScale(0.2f,0.4f);		
		}
		
		return spr_master;
	}
	
	public String ExpPercent(Player player) {
		
		float percent = Float.parseFloat(player.Exp);
	    float totalExp = 0;
	    int level = Integer.parseInt(player.Level);
	    
	    if(level == 1) { totalExp = 25; }
	    if(level == 2) { totalExp = 50; }
	    if(level == 3) { totalExp = 75; }
	    if(level == 4) { totalExp = 600; }
	    if(level == 5) { totalExp = 950; }
	    if(level == 6) { totalExp = 1200; }
	    if(level == 7) { totalExp = 3300; }
	    if(level == 8) { totalExp = 4200; }
	    if(level == 9) { totalExp = 5200; }
	    
	    if(level == 10) { totalExp = 7800; }
	    if(level == 11) { totalExp = 9200; }
	    if(level == 12) { totalExp = 13300; }
	    if(level == 13) { totalExp = 15400; }
	    if(level == 14) { totalExp = 17500; }
	    if(level == 15) { totalExp = 18600; }
	    if(level == 16) { totalExp = 22700; }
	    if(level == 17) { totalExp = 24800; }
	    if(level == 18) { totalExp = 26900; }
	    if(level == 19) { totalExp = 32000; }
	    
	    if(level == 20) { totalExp = 780000; }
	    if(level == 21) { totalExp = 911000; }
	    if(level == 22) { totalExp = 1320000; }
	    if(level == 23) { totalExp = 1530000; }
	    if(level == 24) { totalExp = 2540000; }
	    if(level == 25) { totalExp = 3650000; }
	    if(level == 26) { totalExp = 4200000; }
	    if(level == 27) { totalExp = 5200000; }
	    if(level == 28) { totalExp = 6280001; }
	    if(level == 29) { totalExp = 7100000; }
	    
	    if(level == 30) { totalExp = 9100000; }
	    if(level == 31) { totalExp = 15100000; }
	    if(level == 32) { totalExp = 18100000; }
	    if(level == 33) { totalExp = 24100000; }
	    if(level == 34) { totalExp = 32000000; }
	    if(level == 35) { totalExp = 42100000; }
	    if(level == 36) { totalExp = 51360000; }
	    if(level == 37) { totalExp = 67370000; }
	    if(level == 38) { totalExp = 72038000; }
	    if(level == 39) { totalExp = 82039000; }
	    
	    if(level == 40) { totalExp = 520390000; }
	    if(level == 41) { totalExp = 620390000; }
	    if(level == 42) { totalExp = 720390000; }
	    if(level == 43) { totalExp = 820390000; }
	    if(level == 44) { totalExp = 920390000; }
	    if(level == 45) { totalExp = 1320390000; }
	    if(level == 46) { totalExp = 1420390000; }
	    if(level == 47) { totalExp = 1520390000; }
	    if(level == 48) { totalExp = 1620390000; }
	    if(level == 49) { totalExp = 1999999999; }
	    									      
	    float result = (float)((percent*100)/totalExp);
	    int finalnumber = Math.round(result);
	    return String.valueOf(finalnumber);
	    
	}
	
	
	public Sprite GetItem(String nameItem, Player player) {  //here
		
		if(player.Sex.equals("M")) {
			if(nameItem.equals("basictop") || nameItem.equals("basictopM")) { spr_master = atlas_cloth.createSprite("basictopM"); return spr_master;}
			if(nameItem.equals("basicbottom") || nameItem.equals("basicbottomM")) { spr_master = atlas_cloth.createSprite("basicbottomM"); return spr_master; }
			if(nameItem.equals("basicfooter") || nameItem.equals("basicfooterM")) { spr_master = atlas_cloth.createSprite("basicfooterM"); return spr_master; }
			if(nameItem.equals("sporttop") || nameItem.equals("sporttopM")) { spr_master = atlas_cloth.createSprite("sporttopM"); return spr_master;}
			if(nameItem.equals("sportbottom") || nameItem.equals("sportbottomM")) { spr_master = atlas_cloth.createSprite("sportbottomM"); return spr_master; }
			if(nameItem.equals("sportfooter") || nameItem.equals("sportfooterM")) { spr_master = atlas_cloth.createSprite("sportfooterM"); return spr_master; }
		}
		
		if(player.Sex.equals("F")) {
			if(nameItem.equals("basictop") || nameItem.equals("basictopF")) { spr_master = atlas_cloth.createSprite("basictopF"); return spr_master; }
			if(nameItem.equals("basicbottom") || nameItem.equals("basicbottomF")) { spr_master = atlas_cloth.createSprite("basicbottomF"); return spr_master; }
			if(nameItem.equals("basicfooter") || nameItem.equals("basicfooterF")) { spr_master = atlas_cloth.createSprite("basicfooterF"); return spr_master; }
			if(nameItem.equals("sporttop") || nameItem.equals("sporttopF")) { spr_master = atlas_cloth.createSprite("sporttopF"); return spr_master; }
			if(nameItem.equals("sportbottom") || nameItem.equals("sportbottomF")) { spr_master = atlas_cloth.createSprite("sportbottomF"); return spr_master; }
			if(nameItem.equals("sportfooter") || nameItem.equals("sportfooterF")) { spr_master = atlas_cloth.createSprite("sportfooterF"); return spr_master; }
		}
		
		//Facas Aprendiz
		if(nameItem.contains("basicknife_a")) { spr_master = atlas_weapon.createSprite("basicknife_a"); return spr_master; }
		if(nameItem.contains("basicknife_b")) { spr_master = atlas_weapon.createSprite("basicknife_b"); return spr_master; }
		if(nameItem.contains("basicknife_c")) { spr_master = atlas_weapon.createSprite("basicknife_c"); return spr_master; }
		if(nameItem.contains("basicknife_s")) { spr_master = atlas_weapon.createSprite("basicknife_s"); return spr_master; }
		
		if(nameItem.contains("doubleedgeknife_a")) { spr_master = atlas_weapon.createSprite("doubleedgeknife_a"); return spr_master; }
		if(nameItem.contains("doubleedgeknife_b")) { spr_master = atlas_weapon.createSprite("doubleedgeknife_b"); return spr_master; }
		if(nameItem.contains("doubleedgeknife_c")) { spr_master = atlas_weapon.createSprite("doubleedgeknife_c"); return spr_master; }
		if(nameItem.contains("doubleedgeknife_s")) { spr_master = atlas_weapon.createSprite("doubleedgeknife_s"); return spr_master; }
		
		//Espadas
		if(nameItem.equals("woodsword_a")) { spr_master = atlas_weapon.createSprite("woodsword_a"); return spr_master;  }
		if(nameItem.equals("woodsword_b")) { spr_master = atlas_weapon.createSprite("woodsword_b"); return spr_master;  }
		if(nameItem.equals("woodsword_c")) { spr_master = atlas_weapon.createSprite("woodsword_c"); return spr_master;  }
		if(nameItem.equals("woodsword_s")) { spr_master = atlas_weapon.createSprite("woodsword_s"); return spr_master;  }
		
		if(nameItem.equals("ragesword_a")) { spr_master = atlas_weapon.createSprite("ragesword_a"); return spr_master;  }
		if(nameItem.equals("ragesword_b")) { spr_master = atlas_weapon.createSprite("ragesword_b"); return spr_master;  }
		if(nameItem.equals("ragesword_c")) { spr_master = atlas_weapon.createSprite("ragesword_c"); return spr_master;  }
		if(nameItem.equals("ragesword_s")) { spr_master = atlas_weapon.createSprite("ragesword_s"); return spr_master;  }
		
		if(nameItem.equals("serpentsword_a")) { spr_master = atlas_weapon.createSprite("serpentsword_a"); return spr_master;  }
		if(nameItem.equals("serpentsword_b")) { spr_master = atlas_weapon.createSprite("serpentsword_b"); return spr_master;  }
		if(nameItem.equals("serpentsword_c")) { spr_master = atlas_weapon.createSprite("serpentsword_c"); return spr_master;  }
		if(nameItem.equals("serpentsword_s")) { spr_master = atlas_weapon.createSprite("serpentsword_s"); return spr_master;  }
		
		if(nameItem.equals("flamesword_a")) { spr_master = atlas_weapon.createSprite("flamesword_a"); return spr_master;  }
		if(nameItem.equals("flamesword_b")) { spr_master = atlas_weapon.createSprite("flamesword_b"); return spr_master;  }
		if(nameItem.equals("flamesword_c")) { spr_master = atlas_weapon.createSprite("flamesword_c"); return spr_master;  }
		if(nameItem.equals("flamesword_s")) { spr_master = atlas_weapon.createSprite("flamesword_s"); return spr_master;  }
		
		if(nameItem.equals("cristalsword_a")) { spr_master = atlas_weapon.createSprite("cristalsword_a"); return spr_master;  }
		if(nameItem.equals("cristalsword_b")) { spr_master = atlas_weapon.createSprite("cristalsword_b"); return spr_master;  }
		if(nameItem.equals("cristalsword_c")) { spr_master = atlas_weapon.createSprite("cristalsword_c"); return spr_master;  }
		if(nameItem.equals("cristalsword_s")) { spr_master = atlas_weapon.createSprite("cristalsword_s"); return spr_master;  }
		
		//Cajados
		if(nameItem.equals("stickrod_a")) { spr_master = atlas_weapon.createSprite("stickrod_a"); return spr_master;  }
		if(nameItem.equals("stickrod_b")) { spr_master = atlas_weapon.createSprite("stickrod_b"); return spr_master;  }
		if(nameItem.equals("stickrod_c")) { spr_master = atlas_weapon.createSprite("stickrod_c"); return spr_master;  }
		if(nameItem.equals("stickrod_s")) { spr_master = atlas_weapon.createSprite("stickrod_s"); return spr_master;  }
		
		if(nameItem.equals("gloomrod_a")) { spr_master = atlas_weapon.createSprite("gloomrod_a"); return spr_master;  }
		if(nameItem.equals("gloomrod_b")) { spr_master = atlas_weapon.createSprite("gloomrod_b"); return spr_master;  }
		if(nameItem.equals("gloomrod_c")) { spr_master = atlas_weapon.createSprite("gloomrod_c"); return spr_master;  }
		if(nameItem.equals("gloomrod_s")) { spr_master = atlas_weapon.createSprite("gloomrod_s"); return spr_master;  }
		
		if(nameItem.equals("butterflyrod_a")) { spr_master = atlas_weapon.createSprite("butterflyrod_a"); return spr_master;  }
		if(nameItem.equals("butterflyrod_b")) { spr_master = atlas_weapon.createSprite("butterflyrod_b"); return spr_master;  }
		if(nameItem.equals("butterflyrod_c")) { spr_master = atlas_weapon.createSprite("butterflyrod_c"); return spr_master;  }
		if(nameItem.equals("butterflyrod_s")) { spr_master = atlas_weapon.createSprite("butterflyrod_s"); return spr_master;  }
				
		if(nameItem.equals("starrod_a")) { spr_master = atlas_weapon.createSprite("starrod_a"); return spr_master;  }
		if(nameItem.equals("starrod_b")) { spr_master = atlas_weapon.createSprite("starrod_b"); return spr_master;  }
		if(nameItem.equals("starrod_c")) { spr_master = atlas_weapon.createSprite("starrod_c"); return spr_master;  }
		if(nameItem.equals("starrod_s")) { spr_master = atlas_weapon.createSprite("starrod_s"); return spr_master;  }
		
		if(nameItem.equals("deathrod_a")) { spr_master = atlas_weapon.createSprite("deathrod_a"); return spr_master;  }
		if(nameItem.equals("deathrod_b")) { spr_master = atlas_weapon.createSprite("deathrod_b"); return spr_master;  }
		if(nameItem.equals("deathrod_c")) { spr_master = atlas_weapon.createSprite("deathrod_c"); return spr_master;  }
		if(nameItem.equals("deathrod_s")) { spr_master = atlas_weapon.createSprite("deathrod_s"); return spr_master;  }
		
		//Pistolas
		if(nameItem.equals("basicpistol_a")) { spr_master = atlas_weapon.createSprite("basicpistol_a"); return spr_master;  }
		if(nameItem.equals("basicpistol_b")) { spr_master = atlas_weapon.createSprite("basicpistol_b"); return spr_master;  }
		if(nameItem.equals("basicpistol_c")) { spr_master = atlas_weapon.createSprite("basicpistol_c"); return spr_master;  }
		if(nameItem.equals("basicpistol_s")) { spr_master = atlas_weapon.createSprite("basicpistol_s"); return spr_master;  }
		
		if(nameItem.equals("cannonpistol_a")) { spr_master = atlas_weapon.createSprite("cannonpistol_a"); return spr_master;  }
		if(nameItem.equals("cannonpistol_b")) { spr_master = atlas_weapon.createSprite("cannonpistol_b"); return spr_master;  }
		if(nameItem.equals("cannonpistol_c")) { spr_master = atlas_weapon.createSprite("cannonpistol_c"); return spr_master;  }
		if(nameItem.equals("cannonpistol_s")) { spr_master = atlas_weapon.createSprite("cannonpistol_s"); return spr_master;  }
		
		if(nameItem.equals("lightpistol_a")) { spr_master = atlas_weapon.createSprite("lightpistol_a"); return spr_master;  }
		if(nameItem.equals("lightpistol_b")) { spr_master = atlas_weapon.createSprite("lightpistol_b"); return spr_master;  }
		if(nameItem.equals("lightpistol_c")) { spr_master = atlas_weapon.createSprite("lightpistol_c"); return spr_master;  }
		if(nameItem.equals("lightpistol_s")) { spr_master = atlas_weapon.createSprite("lightpistol_s"); return spr_master;  }
		
		if(nameItem.equals("shooterpistol_a")) { spr_master = atlas_weapon.createSprite("shooterpistol_a"); return spr_master;  }
		if(nameItem.equals("shooterpistol_b")) { spr_master = atlas_weapon.createSprite("shooterpistol_b"); return spr_master;  }
		if(nameItem.equals("shooterpistol_c")) { spr_master = atlas_weapon.createSprite("shooterpistol_c"); return spr_master;  }
		if(nameItem.equals("shooterpistol_s")) { spr_master = atlas_weapon.createSprite("shooterpistol_s"); return spr_master;  }
		
		if(nameItem.equals("sharkpistol_a")) { spr_master = atlas_weapon.createSprite("sharkpistol_a"); return spr_master;  }
		if(nameItem.equals("sharkpistol_b")) { spr_master = atlas_weapon.createSprite("sharkpistol_b"); return spr_master;  }
		if(nameItem.equals("sharkpistol_c")) { spr_master = atlas_weapon.createSprite("sharkpistol_c"); return spr_master;  }
		if(nameItem.equals("sharkpistol_s")) { spr_master = atlas_weapon.createSprite("sharkpistol_s"); return spr_master;  }
		
		//Facas
		if(nameItem.equals("basicdagger_a")) { spr_master = atlas_weapon.createSprite("basicdagger_a"); return spr_master;  }
		if(nameItem.equals("basicdagger_b")) { spr_master = atlas_weapon.createSprite("basicdagger_b"); return spr_master;  }
		if(nameItem.equals("basicdagger_c")) { spr_master = atlas_weapon.createSprite("basicdagger_c"); return spr_master;  }
		if(nameItem.equals("basicdagger_s")) { spr_master = atlas_weapon.createSprite("basicdagger_s"); return spr_master;  }
		
		if(nameItem.equals("marinedagger_a")) { spr_master = atlas_weapon.createSprite("marinedagger_a"); return spr_master;  }
		if(nameItem.equals("marinedagger_b")) { spr_master = atlas_weapon.createSprite("marinedagger_b"); return spr_master;  }
		if(nameItem.equals("marinedagger_c")) { spr_master = atlas_weapon.createSprite("marinedagger_c"); return spr_master;  }
		if(nameItem.equals("marinedagger_s")) { spr_master = atlas_weapon.createSprite("marinedagger_s"); return spr_master;  }
		
		if(nameItem.equals("thunderdagger_a")) { spr_master = atlas_weapon.createSprite("thunderdagger_a"); return spr_master;  }
		if(nameItem.equals("thunderdagger_b")) { spr_master = atlas_weapon.createSprite("thunderdagger_b"); return spr_master;  }
		if(nameItem.equals("thunderdagger_c")) { spr_master = atlas_weapon.createSprite("thunderdagger_c"); return spr_master;  }
		if(nameItem.equals("thunderdagger_s")) { spr_master = atlas_weapon.createSprite("thunderdagger_s"); return spr_master;  }
		
		if(nameItem.equals("winddagger_a")) { spr_master = atlas_weapon.createSprite("winddagger_a"); return spr_master;  }
		if(nameItem.equals("winddagger_b")) { spr_master = atlas_weapon.createSprite("winddagger_b"); return spr_master;  }
		if(nameItem.equals("winddagger_c")) { spr_master = atlas_weapon.createSprite("winddagger_c"); return spr_master;  }
		if(nameItem.equals("winddagger_s")) { spr_master = atlas_weapon.createSprite("winddagger_s"); return spr_master;  }
		
		if(nameItem.equals("flamebergdagger_a")) { spr_master = atlas_weapon.createSprite("flamebergdagger_a"); return spr_master;  }
		if(nameItem.equals("flamebergdagger_b")) { spr_master = atlas_weapon.createSprite("flamebergdagger_b"); return spr_master;  }
		if(nameItem.equals("flamebergdagger_c")) { spr_master = atlas_weapon.createSprite("flamebergdagger_c"); return spr_master;  }
		if(nameItem.equals("flamebergdagger_s")) { spr_master = atlas_weapon.createSprite("flamebergdagger_s"); return spr_master;  }
		
		
		//Machados
		if(nameItem.equals("basicaxe_a")) { spr_master = atlas_weapon.createSprite("basicaxe_a"); return spr_master;  }
		if(nameItem.equals("basicaxe_b")) { spr_master = atlas_weapon.createSprite("basicaxe_b"); return spr_master;  }
		if(nameItem.equals("basicaxe_c")) { spr_master = atlas_weapon.createSprite("basicaxe_c"); return spr_master;  }
		if(nameItem.equals("basicaxe_s")) { spr_master = atlas_weapon.createSprite("basicaxe_s"); return spr_master;  }
		
		if(nameItem.equals("hammeraxe_a")) { spr_master = atlas_weapon.createSprite("hammeraxe_a"); return spr_master;  }
		if(nameItem.equals("hammeraxe_b")) { spr_master = atlas_weapon.createSprite("hammeraxe_b"); return spr_master;  }
		if(nameItem.equals("hammeraxe_c")) { spr_master = atlas_weapon.createSprite("hammeraxe_c"); return spr_master;  }
		if(nameItem.equals("hammeraxe_d")) { spr_master = atlas_weapon.createSprite("hammeraxe_s"); return spr_master;  }
		
		if(nameItem.equals("guitaraxe_a")) { spr_master = atlas_weapon.createSprite("guitaraxe_a"); return spr_master;  }
		if(nameItem.equals("guitaraxe_b")) { spr_master = atlas_weapon.createSprite("guitaraxe_b"); return spr_master;  }
		if(nameItem.equals("guitaraxe_c")) { spr_master = atlas_weapon.createSprite("guitaraxe_c"); return spr_master;  }
		if(nameItem.equals("guitaraxe_s")) { spr_master = atlas_weapon.createSprite("guitaraxe_s"); return spr_master;  }
		
		if(nameItem.equals("anchoraxe_a")) { spr_master = atlas_weapon.createSprite("anchoraxe_a"); return spr_master;  }
		if(nameItem.equals("anchoraxe_b")) { spr_master = atlas_weapon.createSprite("anchoraxe_b"); return spr_master;  }
		if(nameItem.equals("anchoraxe_c")) { spr_master = atlas_weapon.createSprite("anchoraxe_c"); return spr_master;  }
		if(nameItem.equals("anchoraxe_s")) { spr_master = atlas_weapon.createSprite("anchoraxe_s"); return spr_master;  }
		
		if(nameItem.equals("scytheaxe_a")) { spr_master = atlas_weapon.createSprite("scytheaxe_a"); return spr_master;  }
		if(nameItem.equals("scytheaxe_b")) { spr_master = atlas_weapon.createSprite("scytheaxe_b"); return spr_master;  }
		if(nameItem.equals("scytheaxe_c")) { spr_master = atlas_weapon.createSprite("scytheaxe_c"); return spr_master;  }
		if(nameItem.equals("scytheaxe_s")) { spr_master = atlas_weapon.createSprite("scytheaxe_s"); return spr_master;  }
		
		
		//Consumiveis
		if(nameItem.equals("hpcan")) { spr_master = atlas_foods.createSprite("hpcan"); return spr_master;  }
		if(nameItem.equals("hpbigcan")) { spr_master = atlas_foods.createSprite("hpbigcan"); return spr_master;  } //hpultracan
		if(nameItem.equals("hpultracan")) { spr_master = atlas_foods.createSprite("hpultracan"); return spr_master;  }
		if(nameItem.equals("mpcan")) { spr_master = atlas_foods.createSprite("mpcan"); return spr_master;  }
		if(nameItem.equals("mpbigcan")) { spr_master = atlas_foods.createSprite("mpbigcan"); return spr_master;  }
		if(nameItem.equals("mpultracan")) { spr_master = atlas_foods.createSprite("mpultracan"); return spr_master;  }
		if(nameItem.equals("stcan")) { spr_master = atlas_foods.createSprite("stcan"); return spr_master;  }
		if(nameItem.equals("stbigcan")) { spr_master = atlas_foods.createSprite("stbigcan"); return spr_master;  }	
		if(nameItem.equals("chipz")) { spr_master = atlas_foods.createSprite("chipz"); return spr_master;  }
		if(nameItem.equals("turkey")) { spr_master = atlas_foods.createSprite("turkey"); return spr_master;  }
		if(nameItem.equals("egg")) { spr_master = atlas_foods.createSprite("egg"); return spr_master;  }
		
		if(nameItem.equals("hatbat")) { spr_master = atlas_hatsitem.createSprite("hatbat"); return spr_master;  }
		if(nameItem.equals("hatbear")) { spr_master = atlas_hatsitem.createSprite("hatbear"); return spr_master;  }
		if(nameItem.equals("hatbunny")) { spr_master = atlas_hatsitem.createSprite("hatbunny"); return spr_master;  }
		if(nameItem.equals("hatcapoult")) { spr_master = atlas_hatsitem.createSprite("hatcapoult"); return spr_master;  }
		if(nameItem.equals("hatheadset")) { spr_master = atlas_hatsitem.createSprite("hatheadset"); return spr_master;  }
		if(nameItem.equals("hatmagician")) { spr_master = atlas_hatsitem.createSprite("hatmagician"); return spr_master;  }
		if(nameItem.equals("hatpirate")) { spr_master = atlas_hatsitem.createSprite("hatpirate"); return spr_master;  }
		if(nameItem.equals("hatsanta")) { spr_master = atlas_hatsitem.createSprite("hatsanta"); return spr_master;  }
		if(nameItem.equals("hatsunglasses")) { spr_master = atlas_hatsitem.createSprite("hatsunglasses"); return spr_master;  }
		if(nameItem.equals("hatslime")) { spr_master = atlas_hatsitem.createSprite("hatslime"); return spr_master;  }
		if(nameItem.equals("hattimerhattimer")) { spr_master = atlas_hatsitem.createSprite("hattimerhattimer"); return spr_master;  }
		
		if(nameItem.equals("lootfragmentoamarelo")) { spr_master = atlas_cristais.createSprite("lootfragmentoamarelo"); return spr_master;  }
		if(nameItem.equals("lootfragmentoazul")) { spr_master = atlas_cristais.createSprite("lootfragmentoazul"); return spr_master;  }
		if(nameItem.equals("lootfragmentoroxo")) { spr_master = atlas_cristais.createSprite("lootfragmentoroxo"); return spr_master;  }
		if(nameItem.equals("lootfragmentoverde")) { spr_master = atlas_cristais.createSprite("lootfragmentoverde"); return spr_master;  }
		if(nameItem.equals("lootfragmentovermelho")) { spr_master = atlas_cristais.createSprite("lootfragmentovermelho"); return spr_master;  }
		
		if(nameItem.equals("yellow_crystal_agiextra_1")) { spr_master = atlas_cristais.createSprite("lootcristalamarelo_C"); return spr_master;  }
		if(nameItem.equals("yellow_crystal_agiextra_2")) { spr_master = atlas_cristais.createSprite("lootcristalamarelo_B"); return spr_master;  }
		if(nameItem.equals("yellow_crystal_agiextra_3")) { spr_master = atlas_cristais.createSprite("lootcristalamarelo_A"); return spr_master;  }
		if(nameItem.equals("blue_crystal_intextra_1")) { spr_master = atlas_cristais.createSprite("lootcristalazul_C"); return spr_master;  }
		if(nameItem.equals("blue_crystal_intextra_2")) { spr_master = atlas_cristais.createSprite("lootcristalazul_B"); return spr_master;  }
		if(nameItem.equals("blue_crystal_intextra_3")) { spr_master = atlas_cristais.createSprite("lootcristalazul_A"); return spr_master;  }	
		if(nameItem.equals("green_crystal_lukextra_1")) { spr_master = atlas_cristais.createSprite("lootcristalverde_C"); return spr_master;  }
		if(nameItem.equals("green_crystal_lukextra_2")) { spr_master = atlas_cristais.createSprite("lootcristalverde_B"); return spr_master;  }
		if(nameItem.equals("green_crystal_lukextra_3")) { spr_master = atlas_cristais.createSprite("lootcristalverde_A"); return spr_master;  }	
		if(nameItem.equals("purple_crystal_vitextra_1")) { spr_master = atlas_cristais.createSprite("lootcristalroxo_C"); return spr_master;  }
		if(nameItem.equals("purple_crystal_vitextra_2")) { spr_master = atlas_cristais.createSprite("lootcristalroxo_B"); return spr_master;  }
		if(nameItem.equals("purple_crystal_vitextra_3")) { spr_master = atlas_cristais.createSprite("lootcristalroxo_A"); return spr_master;  }		
		if(nameItem.equals("red_crystal_strextra_1")) { spr_master = atlas_cristais.createSprite("lootcristalvermelho_C"); return spr_master;  }
		if(nameItem.equals("red_crystal_strextra_2")) { spr_master = atlas_cristais.createSprite("lootcristalvermelho_B"); return spr_master;  }
		if(nameItem.equals("red_crystal_strextra_3")) { spr_master = atlas_cristais.createSprite("lootcristalvermelho_A"); return spr_master;  }
		
		
		
		if(nameItem.equals("galhos")) { spr_master = atlas_lootmob.createSprite("galhos"); return spr_master;  }
		if(nameItem.equals("lootbanana")) { spr_master = atlas_lootmob.createSprite("lootbanana"); return spr_master;  }
		if(nameItem.equals("lootblop")) { spr_master = atlas_lootmob.createSprite("lootblop"); return spr_master;  }
		if(nameItem.equals("lootfang")) { spr_master = atlas_lootmob.createSprite("lootfang"); return spr_master;  }
		if(nameItem.equals("lootmoney")) { spr_master = atlas_lootmob.createSprite("lootmoney"); return spr_master;  }
		if(nameItem.equals("lootmushroom")) { spr_master = atlas_lootmob.createSprite("lootmushroom"); return spr_master;  }
		if(nameItem.equals("lootneedle")) { spr_master = atlas_lootmob.createSprite("lootneedle"); return spr_master;  }
		if(nameItem.equals("lootnuts")) { spr_master = atlas_lootmob.createSprite("lootnuts"); return spr_master;  }
		if(nameItem.equals("lootpaw")) { spr_master = atlas_lootmob.createSprite("lootpaw"); return spr_master;  }
		if(nameItem.equals("lootpoisonleaf")) { spr_master = atlas_lootmob.createSprite("lootpoisonleaf"); return spr_master;  }
		if(nameItem.equals("lootsilk")) { spr_master = atlas_lootmob.createSprite("lootsilk"); return spr_master;  }
		if(nameItem.equals("loottongue")) { spr_master = atlas_lootmob.createSprite("loottongue"); return spr_master;  }
		
		
		return spr_master;
	}
	
	
	
	public Sprite GetShops(String shopname, float cameraCoordsX, float cameraCoordsY) {
		if(shopname.equals("refrishop")){
			spr_master = atlas_shops.createSprite("lojamaquina");
			spr_master.setPosition(cameraCoordsX -65,cameraCoordsY -80);
			spr_master.setSize(130,160);
		}
		
		if(shopname.equals("weaponshop")){
			spr_master = atlas_shops.createSprite("lojaarmas");
			spr_master.setPosition(cameraCoordsX -65,cameraCoordsY -80);
			spr_master.setSize(130,160);
		}
		
		if(shopname.equals("jobmaster")){
			spr_master = atlas_ux.createSprite("jobmaster");
			spr_master.setPosition(cameraCoordsX -65,cameraCoordsY -80);
			spr_master.setSize(130,160);
		}
		
		if(shopname.equals("cristalized")){
			spr_master = atlas_ux.createSprite("crystal");
			spr_master.setPosition(cameraCoordsX -65,cameraCoordsY -80);
			spr_master.setSize(130,160);
		}
		
		if(shopname.equals("lojaroupas1")){
			spr_master = atlas_shops.createSprite("lojaroupas1");
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
		if(cardname.equals("cardhpplus")) {
			spr_master = atlas_cards.createSprite("cardhpplus");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		if(cardname.equals("cardhpultra")) {
			spr_master = atlas_cards.createSprite("cardhpultra");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		
		if(cardname.equals("cardmp")) {
			spr_master = atlas_cards.createSprite("cardmp");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		if(cardname.equals("cardmpplus")) {
			spr_master = atlas_cards.createSprite("cardmpplus");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		if(cardname.equals("cardmpultra")) {
			spr_master = atlas_cards.createSprite("cardmpultra");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		
		if(cardname.equals("cardchipz")) {
			spr_master = atlas_cards.createSprite("cardchipz");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardegg")) {
			spr_master = atlas_cards.createSprite("cardegg");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardturkey")) {
			spr_master = atlas_cards.createSprite("cardturkey");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		
		if(cardname.equals("cardatk")) {
			spr_master = atlas_cards.createSprite("cardatk");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("carddef")) {
			spr_master = atlas_cards.createSprite("carddef");
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
		
		if(cardname.equals("cardslash")) {
			spr_master = atlas_cards.createSprite("cardslash");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardhealthboost")) {
			spr_master = atlas_cards.createSprite("cardhealthboost");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardfireball")) {
			spr_master = atlas_cards.createSprite("cardfireball");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardicespike")) {
			spr_master = atlas_cards.createSprite("cardicespike");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardarrowrain")) {
			spr_master = atlas_cards.createSprite("cardarrowrain");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardperfectshot")) {
			spr_master = atlas_cards.createSprite("cardperfectshot");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardheal")) {
			spr_master = atlas_cards.createSprite("cardheal");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardholy")) {
			spr_master = atlas_cards.createSprite("cardholy");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardpoison")) {
			spr_master = atlas_cards.createSprite("cardpoison");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		if(cardname.equals("cardsteal")) {
			spr_master = atlas_cards.createSprite("cardsteal");
			spr_master.setSize(10,25);
			return spr_master;	
		}
		
		return spr_master;
	}
	
	
	public void GiveBuff(String buffname) {
		boolean setBuff = false;  
		String buff = "";
		int Atk = Integer.parseInt(playerUse.Atk);
		int Def = Integer.parseInt(playerUse.Def);
		
		int Str = Integer.parseInt(playerUse.Str);
		int Vit = Integer.parseInt(playerUse.Vit);
		int Agi = Integer.parseInt(playerUse.Agi);
		int Dex = Integer.parseInt(playerUse.Dex);
		int Luk = Integer.parseInt(playerUse.Luk);
		int Wis = Integer.parseInt(playerUse.Wis);
		
		int Hp = Integer.parseInt(playerUse.Hp);
		int HpMax = Integer.parseInt(playerUse.HpMax);
		
		int Mp = Integer.parseInt(playerUse.Mp);
		int MpMax = Integer.parseInt(playerUse.MpMax);
		
		int regenTimeMax = Integer.parseInt(playerUse.regenTimeMax);
		
		if(playerUse.buffA.equals(buffname)) { return; }
		if(playerUse.buffB.equals(buffname)) { return; }
		if(playerUse.buffC.equals(buffname)) { return; }
		
		if(playerUse.buffA.equals("none") && !setBuff) { playerUse.buffA = buffname; setBuff  = true; buff = "A"; }
		if(playerUse.buffB.equals("none") && !setBuff) { playerUse.buffB = buffname; setBuff  = true; buff = "B"; }
		if(playerUse.buffC.equals("none") && !setBuff) { playerUse.buffC = buffname; setBuff  = true; buff = "C"; }
		

		if(buffname.equals("turkey")) {
			if(buff.equals("A")) { playerUse.BuffTimeA = "20000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "20000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "20000"; }
		}
		
		if(buffname.equals("chipz")) {
			Atk = Atk + 15;
			playerUse.Atk = String.valueOf(Atk);
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "15000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "15000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "15000"; }
		}
		
		if(buffname.equals("egg")) {
			Def = Def + 50;
			playerUse.Def = String.valueOf(Def);
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "15000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "15000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "15000"; }
		}
				
		if(buffname.equals("defboost")) {
			
			Atk = Atk * 2;
			Def = Def * 2;
			
			playerUse.Atk = String.valueOf(Atk);
			playerUse.Def = String.valueOf(Def);
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "5000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "5000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "5000"; }
		}
		
		if(buffname.equals("ironshield")) {
			
			Def = Def * 4;
			
			playerUse.Def = String.valueOf(Def);
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "1000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "1000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "1000"; }
		}
		
		if(buffname.equals("healthboost")) {
			
			HpMax = HpMax * 3;
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "3000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "3000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "3000"; }
			
			playerUse.HpMax = String.valueOf(HpMax);
		}
		
		if(buffname.equals("perfectshot")) {
			Atk = Atk + 25;
			Dex = Dex * 3;
			Luk = Luk * 3;
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "3000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "3000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "3000"; }
			
			playerUse.Atk = String.valueOf(Atk);
			playerUse.Dex = String.valueOf(Dex);
			playerUse.Luk = String.valueOf(Luk);
		}
		
		if(buffname.equals("berserk")) {
			Str = Str * 3;
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "1000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "1000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "1000"; }
		}
		
		if(buffname.equals("regen")) {
			regenTimeMax = regenTimeMax - 3000;
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "2000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "2000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "2000"; }
		}
		
		if(buffname.equals("invisibility")) {
			if(buff.equals("A")) { playerUse.BuffTimeA = "1000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "1000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "1000"; }
		}
		
		if(buffname.equals("lockshot")) {
			Dex = Dex * 2;
			Luk = Luk * 2;
			
			if(buff.equals("A")) { playerUse.BuffTimeA = "1000"; }
			if(buff.equals("B")) { playerUse.BuffTimeB = "1000"; }
			if(buff.equals("C")) { playerUse.BuffTimeC = "1000"; }
		}		
	}
	
	public void RemoveBuffs(String buffname) {
		String buff = "";
		
		int Atk = Integer.parseInt(playerUse.Atk);
		int Def = Integer.parseInt(playerUse.Def);
		
		int Str = Integer.parseInt(playerUse.Str);
		int Vit = Integer.parseInt(playerUse.Vit);
		int Agi = Integer.parseInt(playerUse.Agi);
		int Dex = Integer.parseInt(playerUse.Dex);
		int Luk = Integer.parseInt(playerUse.Luk);
		int Wis = Integer.parseInt(playerUse.Wis);
		
		int Hp = Integer.parseInt(playerUse.Hp);
		int HpMax = Integer.parseInt(playerUse.HpMax);
		
		int Mp = Integer.parseInt(playerUse.Mp);
		int MpMax = Integer.parseInt(playerUse.MpMax);
		
		int regenTimeMax = Integer.parseInt(playerUse.regenTimeMax);
		
		if(playerUse.buffA.equals(buffname)) { buff = "A"; }
		if(playerUse.buffB.equals(buffname)) { buff = "B"; }
		if(playerUse.buffC.equals(buffname)) { buff = "C"; }
		
		if(buffname.equals("chipz")) {
			Atk = Atk - 15;
			if(Atk <= 0) { Atk = 2; } 
			playerUse.Atk = String.valueOf(Atk);
		}
		
		if(buffname.equals("egg")) {
			Def = Def - 50;
			if(Def <= 0) { Def = 1; } 
			playerUse.Def = String.valueOf(Def);
		}
		
		if(buffname.equals("boost")) {
			Atk = Atk / 2;
			Def = Def / 2;
			
			playerUse.Atk = String.valueOf(Atk);
			playerUse.Def = String.valueOf(Def);
		}
		
		if(buffname.equals("ironshield")) {
			Def = Def / 4;
			playerUse.Def = String.valueOf(Def);
		}
		
		if(buffname.equals("healthboost")) {
			HpMax = HpMax / 3;
			playerUse.HpMax = String.valueOf(HpMax);
		}
		
		if(buffname.equals("perfectshot")) {
			Atk = Atk - 25;
			Dex = Dex / 3;
			Luk = Luk / 3;
			playerUse.Atk = String.valueOf(Atk);
			playerUse.Dex = String.valueOf(Dex);
			playerUse.Luk = String.valueOf(Luk);
		}
		
		if(buffname.equals("berserk")) {
			Str = Str / 3;
			playerUse.Str = String.valueOf(Str);
		}
		
		if(buffname.equals("regen")) {
			regenTimeMax = regenTimeMax + 3000;			
			playerUse.regenTimeMax = String.valueOf(regenTimeMax);
		}
		
		if(buff.equals("A")) { playerUse.buffA = "none"; playerUse.BuffTimeA = "0"; }
		if(buff.equals("B")) { playerUse.buffB = "none"; playerUse.BuffTimeB = "0"; }
		if(buff.equals("C")) { playerUse.buffC = "none"; playerUse.BuffTimeC = "0"; }		
	}
	
	
	//[BAG]
	public void UseItem(int numItem) {
	String[] lstItem = playerUse.Itens.split("-");
	String[] itemSplit;
	String item = "";
	String itemName = "";
	String lstitensFinal;
	int qtd;
	String crystalUse = "no";
	boolean equipable = false;
	
	//variables
	int hp = Integer.parseInt(playerUse.Hp);
	int hpMax = Integer.parseInt(playerUse.HpMax);
	
	int mp = Integer.parseInt(playerUse.Mp);
	int mpMax = Integer.parseInt(playerUse.MpMax);
	
	int stamina = Integer.parseInt(playerUse.Stamina);
	int staminaMax = Integer.parseInt(playerUse.StaminaMax);
	
	
	item = lstItem[numItem];
	if(!item.equals("[NONE]")) {
		itemSplit = item.split("#");
		itemName = itemSplit[0].replace("[", "");
		qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
		
		//not usable
		if(itemName.equals("lootblop")) { return; }
		if(itemName.equals("lootfang")) { return; }
		if(itemName.equals("galhos")) { return; }
		if(itemName.equals("lootbanana")) { return; }
		if(itemName.equals("lootfang")) { return; }
		if(itemName.equals("lootmoney")) { return; }
		if(itemName.equals("lootmushroom")) { return; }
		if(itemName.equals("lootneedle")) { return; }
		if(itemName.equals("lootnuts")) { return; }
		if(itemName.equals("lootpaw")) { return; }
		if(itemName.equals("lootpoisonleaf")) { return; }
		if(itemName.equals("lootsilk")) { return; }
		if(itemName.equals("loottongue")) { return; }
		
		//Consumable
		if(itemName.equals("hpcan")) { 
			hp = hp + 15;
			if(hp > hpMax) { hp = hpMax; playerUse.Hp = String.valueOf(hp); } 
			playerUse.Hp = String.valueOf(hp);
			CheckRequiredItem(itemName,true,1);
			if(playerUse.hotkey1.equals(itemName) && qtd == 1) { playerUse.hotkey1 = "none"; }
			if(playerUse.hotkey2.equals(itemName) && qtd == 1) { playerUse.hotkey2 = "none"; }
			return;
		}	
		
		if(itemName.equals("hpbigcan")) {
			hp = hp + 50;
			if(hp > hpMax) { hp = hpMax; playerUse.Hp = String.valueOf(hp); } 
			playerUse.Hp = String.valueOf(hp);
			CheckRequiredItem(itemName,true,1);
			if(playerUse.hotkey1.equals(itemName) && qtd == 1) { playerUse.hotkey1 = "none"; }
			if(playerUse.hotkey2.equals(itemName) && qtd == 1) { playerUse.hotkey2 = "none"; }
			return;
		}
		
		if(itemName.equals("hpultracan")) {
			hp = hp + 200;
			if(hp > hpMax) { hp = hpMax; playerUse.Hp = String.valueOf(hp); } 
			playerUse.Hp = String.valueOf(hp);
			CheckRequiredItem(itemName,true,1);
			if(playerUse.hotkey1.equals(itemName) && qtd == 1) { playerUse.hotkey1 = "none"; }
			if(playerUse.hotkey2.equals(itemName) && qtd == 1) { playerUse.hotkey2 = "none"; }
			return;
		}
		
		if(itemName.equals("mpcan")) { 
			mp = mp + 25;
			if(mp > mpMax) { mp = mpMax; playerUse.Mp = String.valueOf(mp); } 
			playerUse.Mp = String.valueOf(mp);
			CheckRequiredItem(itemName,true,1);
			if(playerUse.hotkey1.equals(itemName) && qtd == 1) { playerUse.hotkey1 = "none"; }
			if(playerUse.hotkey2.equals(itemName) && qtd == 1) { playerUse.hotkey2 = "none"; }
			return;
		}
		
		if(itemName.equals("mpbigcan")) { 
			mp = mp + 50;
			if(mp > mpMax) { mp = mpMax; playerUse.Mp = String.valueOf(mp); } 
			playerUse.Mp = String.valueOf(mp);
			CheckRequiredItem(itemName,true,1);
			if(playerUse.hotkey1.equals(itemName) && qtd == 1) { playerUse.hotkey1 = "none"; }
			if(playerUse.hotkey2.equals(itemName) && qtd == 1) { playerUse.hotkey2 = "none"; }
			return;
		}
		
		if(itemName.equals("mpultracan")) {
			mp = mp + 120;
			if(mp > mpMax) { mp = mpMax; playerUse.Mp = String.valueOf(mp); } 
			playerUse.Mp = String.valueOf(mp);
			CheckRequiredItem(itemName,true,1);
			if(playerUse.hotkey1.equals(itemName) && qtd == 1) { playerUse.hotkey1 = "none"; }
			if(playerUse.hotkey2.equals(itemName) && qtd == 1) { playerUse.hotkey2 = "none"; }
			return;
		}
		
		if(itemName.equals("stcan")) { 
			stamina = stamina + 5000;
			if(stamina > staminaMax) { stamina = staminaMax; playerUse.Stamina = String.valueOf(stamina); } 
			playerUse.Stamina = String.valueOf(stamina);
			CheckRequiredItem(itemName,true,1);
			return;
		}
		
		if(itemName.equals("stbigcan")) { 
			stamina = stamina + 15000;
			if(stamina > staminaMax) { stamina = staminaMax; playerUse.Stamina = String.valueOf(stamina); } 
			playerUse.Stamina = String.valueOf(stamina);
			CheckRequiredItem(itemName,true,1);
			return;
		}
		
		if(itemName.equals("chipz")) {
			GiveBuff("chipz"); 
			CheckRequiredItem(itemName,true,1);
			return;
		}
		
		if(itemName.equals("turkey")) {
			GiveBuff("turkey"); 
			CheckRequiredItem(itemName,true,1);
			return;
		}
		
		if(itemName.equals("egg")) {
			GiveBuff("egg"); 
			CheckRequiredItem(itemName,true,1);
			return;
		}
		
		if(itemName.equals("basictopM") || itemName.equals("basictopF")) { itemName = "basictop"; }
		if(itemName.equals("basicbottomM") || itemName.equals("basicbottomF")) { itemName = "basicbottom"; }
		if(itemName.equals("basicfooterM") || itemName.equals("basicfooterF")) { itemName = "basicfooter"; }
		
		if(itemName.equals("sporttopM") || itemName.equals("sporttopF")) { itemName = "sporttop"; }
		if(itemName.equals("sportbottomM") || itemName.equals("sportbottomF")) { itemName = "sportbottom"; }
		if(itemName.equals("sportfooterM") || itemName.equals("sportfooterF")) { itemName = "sportfooter"; }
			
		if(itemName.equals("basictop")) {  if(playerUse.SetUpper.equals(itemName)){ return; } else { AddItemBag(playerUse.SetUpper); playerUse.SetUpper = "basictop"; CheckRequiredItem(itemName,true,1); return; }}
		if(itemName.equals("basicbottom")) {  if(playerUse.SetBottom.equals(itemName)){ return; } else { AddItemBag(playerUse.SetBottom); playerUse.SetBottom = "basicbottom"; CheckRequiredItem(itemName,true,1); return; }}
		if(itemName.equals("basicfooter")) {  if(playerUse.SetFooter.equals(itemName)){ return; } else { AddItemBag(playerUse.SetFooter); playerUse.SetFooter = "basicfooter"; CheckRequiredItem(itemName,true,1); return; }}
		
		if(itemName.equals("sporttop")) {  if(playerUse.SetUpper.equals("sporttop")){ return; } else { AddItemBag(playerUse.SetUpper); playerUse.SetUpper = "sporttop"; CheckRequiredItem(itemName,true,1); return; }}
		if(itemName.equals("sportbottom")) {  if(playerUse.SetBottom.equals("sportbottom")){ return; } else { AddItemBag(playerUse.SetBottom); playerUse.SetBottom = "sportbottom"; CheckRequiredItem(itemName,true,1); return; }}
		if(itemName.equals("sportfooter")) {  if(playerUse.SetFooter.equals("sportfooter")){ return; } else { AddItemBag(playerUse.SetFooter); playerUse.SetFooter = "sportfooter"; CheckRequiredItem(itemName,true,1); return; }}
		
		
		if(itemName.equals("anchoraxe_a") || itemName.equals("anchoraxe_b") || itemName.equals("anchoraxe_c") || itemName.equals("anchoraxe_s") || itemName.equals("basicaxe_a") ||
		   itemName.equals("basicaxe_b")  || itemName.equals("basicaxe_c")  || itemName.equals("basicaxe_s") ||  itemName.equals("basicdagger_a") || itemName.equals("basicdagger_b") ||
		   itemName.equals("basicdagger_c") || itemName.equals("basicdagger_s") || itemName.equals("basicknife_a") || itemName.equals("basicknife_b") || itemName.equals("basicknife_c") ||
		   itemName.equals("basicknife_s") ||  itemName.equals("basicpistol_a") || itemName.equals("basicpistol_b") || itemName.equals("basicpistol_c") || itemName.equals("basicpistol_s") ||
		   itemName.equals("butterflyrod_a") || itemName.equals("butterflyrod_b") || itemName.equals("butterflyrod_c") || itemName.equals("butterflyrod_s") || itemName.equals("cannonpistol_a") ||
		   itemName.equals("cannonpistol_b") || itemName.equals("cannonpistol_c") || itemName.equals("cannonpistol_s") || itemName.equals("cristalsword_a") || itemName.equals("cristalsword_b") ||
		   itemName.equals("cristalsword_c") || itemName.equals("cristalsword_s") || itemName.equals("deathrod_a") || itemName.equals("deathrod_b") || itemName.equals("deathrod_c") ||
		   itemName.equals("deathrod_s") || itemName.equals("doubleedgeknife_a") || itemName.equals("doubleedgeknife_b") || itemName.equals("doubleedgeknife_c") || itemName.equals("doubleedgeknife_s") ||
		   itemName.equals("flamebergdagger_a") || itemName.equals("flamebergdagger_b") || itemName.equals("flamebergdagger_c") || itemName.equals("flamebergdagger_s") || itemName.equals("flamesword_a") ||
		   itemName.equals("flamesword_b") || itemName.equals("flamesword_c") || itemName.equals("flamesword_s") || itemName.equals("gloomrod_a") || itemName.equals("gloomrod_b") ||
		   itemName.equals("gloomrod_c") || itemName.equals("gloomrod_s") || itemName.equals("guitaraxe_a") || itemName.equals("guitaraxe_b") || itemName.equals("guitaraxe_c") ||
		   itemName.equals("guitaraxe_s") || itemName.equals("hammeraxe_a") || itemName.equals("hammeraxe_b") || itemName.equals("hammeraxe_c") || itemName.equals("hammeraxe_s") ||
		   itemName.equals("lightpistol_a") || itemName.equals("lightpistol_b") || itemName.equals("lightpistol_c") || itemName.equals("lightpistol_s") || itemName.equals("marinedagger_a") ||
		   itemName.equals("marinedagger_b") || itemName.equals("marinedagger_c") || itemName.equals("marinedagger_s") || itemName.equals("ragesword_a") || itemName.equals("ragesword_b") ||
		   itemName.equals("ragesword_c") || itemName.equals("ragesword_s") || itemName.equals("scytheaxe_a") || itemName.equals("scytheaxe_b") || itemName.equals("scytheaxe_c") ||
		   itemName.equals("scytheaxe_s") ||itemName.equals("serpentsword_a") || itemName.equals("serpentsword_b") || itemName.equals("serpentsword_c") || itemName.equals("serpentsword_s") ||
           itemName.equals("sharkpistol_a") || itemName.equals("sharkpistol_b") || itemName.equals("sharkpistol_c") || itemName.equals("sharkpistol_s") || itemName.equals("shooterpistol_a") || 
           itemName.equals("shooterpistol_b") || itemName.equals("shooterpistol_c") || itemName.equals("shooterpistol_s") || itemName.equals("starrod_a") || itemName.equals("starrod_b") ||
		   itemName.equals("starrod_c") || itemName.equals("starrod_s") || itemName.equals("stickrod_a") || itemName.equals("stickrod_b") || itemName.equals("stickrod_c") || itemName.equals("stickrod_s") ||
		   itemName.equals("thunderdagger_a") || itemName.equals("thunderdagger_b") || itemName.equals("thunderdagger_c") || itemName.equals("thunderdagger_s") || itemName.equals("winddagger_a") ||
           itemName.equals("winddagger_b") || itemName.equals("winddagger_c") || itemName.equals("winddagger_s") || itemName.equals("woodsword_a") || itemName.equals("woodsword_b") || itemName.equals("woodsword_c") ||
		   itemName.equals("woodsword_s")) 
		{
			if(itemName.contains("knife") && playerUse.Job.equals("Aprendiz")) { 
				if(playerUse.Weapon.equals(itemName)){ return; } 
				if(!playerUse.Weapon.equals(itemName)) { AddItemBag(playerUse.Weapon); playerUse.Weapon = itemName; CheckRequiredItem(itemName,true,1); return; }
			}
			if(itemName.contains("sword") && playerUse.Job.equals("Espadachim")) { 
				if(playerUse.Weapon.equals(itemName)){ return; } 
				if(!playerUse.Weapon.equals(itemName)) { AddItemBag(playerUse.Weapon); playerUse.Weapon = itemName; CheckRequiredItem(itemName,true,1); return; }
			}
			if(itemName.contains("rod") && playerUse.Job.equals("Mago")) { 
				if(playerUse.Weapon.equals(itemName)){ return; } 
				if(!playerUse.Weapon.equals(itemName)) { AddItemBag(playerUse.Weapon); playerUse.Weapon = itemName; CheckRequiredItem(itemName,true,1); return; }
			}
			if(itemName.contains("rod") && playerUse.Job.equals("Curandeiro")) { 
				if(playerUse.Weapon.equals(itemName)){ return; } 
				if(!playerUse.Weapon.equals(itemName)) { AddItemBag(playerUse.Weapon); playerUse.Weapon = itemName; CheckRequiredItem(itemName,true,1); return; }
			}
			if(itemName.contains("dagger") && playerUse.Job.equals("Ladrao")) { 
				if(playerUse.Weapon.equals(itemName)){ return; } 
				if(!playerUse.Weapon.equals(itemName)) { AddItemBag(playerUse.Weapon); playerUse.Weapon = itemName; CheckRequiredItem(itemName,true,1); return; }
			}
			if(itemName.contains("pistol") && playerUse.Job.equals("Atirador")) { 
				if(playerUse.Weapon.equals(itemName)){ return; } 
				if(!playerUse.Weapon.equals(itemName)) { AddItemBag(playerUse.Weapon); playerUse.Weapon = itemName; CheckRequiredItem(itemName,true,1); return; }
			}
			if(itemName.contains("axe") && playerUse.Job.equals("Batedor")) { 
				if(playerUse.Weapon.equals(itemName)){ return; } 
				if(!playerUse.Weapon.equals(itemName)) { AddItemBag(playerUse.Weapon); playerUse.Weapon = itemName; CheckRequiredItem(itemName,true,1); return; }
			}		
		}
				
		//Hats
		if(itemName.equals("hatbanana") || itemName.equals("hatbat") || itemName.equals("hatbear") || itemName.equals("hatblackglass") || itemName.equals("hatbluecold")
				 || itemName.equals("hatbrazilflag") || itemName.equals("hatbrown") || itemName.equals("hatbunny") || itemName.equals("hatbutterfly") || itemName.equals("hatcapoult")
				 || itemName.equals("hatcooker") || itemName.equals("hateyepatch") || itemName.equals("hatfashionglasses") || itemName.equals("hatheadset") || itemName.equals("hatmagician")
				 || itemName.equals("hatpirate") || itemName.equals("hatredcap") || itemName.equals("hatsanta") || itemName.equals("hatslime") || itemName.equals("hatsunglasses")
				 || itemName.equals("hattimer")		
		) {
			if(playerUse.Hat.equals(itemName)){ return; }
			if(playerUse.Hat.equals("none")){ playerUse.Hat = itemName; equipable = true; return; }
			if(!playerUse.Hat.equals(itemName)) { AddItemBag(playerUse.Hat); playerUse.Hat = itemName; CheckRequiredItem(itemName,true,1); return; }
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
			playerUse.Itens = lstitensFinal;
		}
		else {
			itemName = "[" + itemName + "#" + qtd + "]"; 
			lstItem[numItem] = itemName;
			lstitensFinal = Arrays.toString(lstItem).replace(", ","-");
			lstitensFinal = lstitensFinal.substring(1, lstitensFinal.length() -1);
			playerUse.Itens = lstitensFinal;
				}			
			}
		}

		public void UnequipHat() {
			String nameHat = playerUse.Hat;
			if(nameHat.equals("none")) { return; }
			playerUse.Hat = "none";
			AddItemBag(nameHat);
		}


		public void UseCrystal(String item) {
			if(playerUse.Crystal1.equals("none")) { playerUse.Crystal1 = item; ApplyCrystals(item); return; }
			if(playerUse.Crystal2.equals("none")) { playerUse.Crystal2 = item; ApplyCrystals(item); return; }
			if(playerUse.Crystal3.equals("none")) { playerUse.Crystal3 = item; ApplyCrystals(item); return; }
			if(playerUse.Crystal4.equals("none")) { playerUse.Crystal4 = item; ApplyCrystals(item); return; }
		}

		public void ApplyCrystals(String item) {
			int StrExtra = Integer.parseInt(playerUse.StrExtra);
			int AgiExtra = Integer.parseInt(playerUse.AgiExtra);
			int DexExtra = Integer.parseInt(playerUse.DexExtra);
			int VitExtra = Integer.parseInt(playerUse.VitExtra);
			int WisExtra = Integer.parseInt(playerUse.WisExtra);
			int LukExtra = Integer.parseInt(playerUse.LukExtra);
			int ResExtra = Integer.parseInt(playerUse.ResExtra);
			
			int Hp = Integer.parseInt(playerUse.Hp);
			int HpMax = Integer.parseInt(playerUse.HpMax);
			
			int Mp = Integer.parseInt(playerUse.Mp);
			int MpMax = Integer.parseInt(playerUse.MpMax);
			
			int AtkTimerMax = Integer.parseInt(playerUse.AtkTimerMax);
			
			int StaminaMax = Integer.parseInt(playerUse.StaminaMax);
			int regenTimeMax = Integer.parseInt(playerUse.regenTimeMax);
			
			if(item.equals("blue_crystal_intextra_1")) { WisExtra = WisExtra + 1; }
			if(item.equals("blue_crystal_intextra_2")) { WisExtra = WisExtra + 3; }
			if(item.equals("blue_crystal_intextra_3")) { WisExtra = WisExtra + 5; }
			
			if(item.equals("green_crystal_lukextra_1")) { LukExtra = LukExtra + 1; }
			if(item.equals("green_crystal_lukextra_2")) { LukExtra = LukExtra + 3; }
			if(item.equals("green_crystal_lukextra_3")) { LukExtra = LukExtra + 5; }
			
			if(item.equals("purple_crystal_vitextra_1")) { HpMax = HpMax + 25; MpMax = MpMax + 25; StaminaMax = StaminaMax + 10000; VitExtra = VitExtra + 1; }
			if(item.equals("purple_crystal_vitextra_2")) { HpMax = HpMax + 50; MpMax = MpMax + 50; StaminaMax = StaminaMax + 20000; VitExtra = VitExtra + 3; }
			if(item.equals("purple_crystal_vitextra_3")) { HpMax = HpMax + 100; MpMax = MpMax + 100; StaminaMax = StaminaMax + 30000; VitExtra = VitExtra + 5; }
			
			if(item.equals("yellow_crystal_agiextra_1")) { AgiExtra = AgiExtra + 1; AtkTimerMax = AtkTimerMax - 5; }
			if(item.equals("yellow_crystal_agiextra_2")) { AgiExtra = AgiExtra + 3; AtkTimerMax = AtkTimerMax - 15; }
			if(item.equals("yellow_crystal_agiextra_3")) { AgiExtra = AgiExtra + 5; AtkTimerMax = AtkTimerMax - 30; }
			
			if(item.equals("red_crystal_strextra_1")) { StrExtra = StrExtra + 1;  }
			if(item.equals("red_crystal_strextra_2")) { StrExtra = StrExtra + 3;  }
			if(item.equals("red_crystal_strextra_3")) { StrExtra = StrExtra + 5;  }
			
			playerUse.StrExtra = String.valueOf(StrExtra);
			playerUse.VitExtra = String.valueOf(VitExtra);
			playerUse.AgiExtra = String.valueOf(AgiExtra);
			playerUse.DexExtra = String.valueOf(DexExtra);
			playerUse.LukExtra = String.valueOf(LukExtra);
			playerUse.ResExtra = String.valueOf(ResExtra);
			
			playerUse.HpMax = String.valueOf(HpMax);
			playerUse.MpMax = String.valueOf(MpMax);
			playerUse.AtkTimerMax = String.valueOf(AtkTimerMax);	
			playerUse.StaminaMax = String.valueOf(StaminaMax);
		}

		public void RemoveCrystals(int num) {
			String CrystalEquipped = "";
					
			if(num == 1) { CrystalEquipped = playerUse.Crystal1; }
			if(num == 2) { CrystalEquipped = playerUse.Crystal2; }
			if(num == 3) { CrystalEquipped = playerUse.Crystal3; }
			if(num == 4) { CrystalEquipped = playerUse.Crystal4; }
			
			int StrExtra = Integer.parseInt(playerUse.StrExtra);
			int AgiExtra = Integer.parseInt(playerUse.AgiExtra);
			int DexExtra = Integer.parseInt(playerUse.DexExtra);
			int VitExtra = Integer.parseInt(playerUse.VitExtra);
			int WisExtra = Integer.parseInt(playerUse.WisExtra);
			int LukExtra = Integer.parseInt(playerUse.LukExtra);
			int ResExtra = Integer.parseInt(playerUse.ResExtra);
			
			int Hp = Integer.parseInt(playerUse.Hp);
			int HpMax = Integer.parseInt(playerUse.HpMax);
			
			int Mp = Integer.parseInt(playerUse.Mp);
			int MpMax = Integer.parseInt(playerUse.MpMax);
			
			int AtkTimerMax = Integer.parseInt(playerUse.AtkTimerMax);
			
			int Res = Integer.parseInt(playerUse.Res);
			
			int StaminaMax = Integer.parseInt(playerUse.StaminaMax);
			int regenTimeMax = Integer.parseInt(playerUse.regenTimeMax);
			
			if(CrystalEquipped.equals("blue_crystal_intextra_1")) { WisExtra = WisExtra - 1; }
			if(CrystalEquipped.equals("blue_crystal_intextra_2")) { WisExtra = WisExtra - 3; }
			if(CrystalEquipped.equals("blue_crystal_intextra_3")) { WisExtra = WisExtra - 5; }
			
			if(CrystalEquipped.equals("green_crystal_lukextra_1")) { LukExtra = LukExtra - 1; }
			if(CrystalEquipped.equals("green_crystal_lukextra_2")) { LukExtra = LukExtra - 3; }
			if(CrystalEquipped.equals("green_crystal_lukextra_3")) { LukExtra = LukExtra - 5; }
			
			if(CrystalEquipped.equals("purple_crystal_vitextra_1")) { HpMax = HpMax - 25; MpMax = MpMax - 25; StaminaMax = StaminaMax - 10000; VitExtra = VitExtra - 1; }
			if(CrystalEquipped.equals("purple_crystal_vitextra_2")) { HpMax = HpMax - 50; MpMax = MpMax - 50; StaminaMax = StaminaMax - 20000; VitExtra = VitExtra - 3; }
			if(CrystalEquipped.equals("purple_crystal_vitextra_3")) { HpMax = HpMax - 100; MpMax = MpMax - 100; StaminaMax = StaminaMax - 30000; VitExtra = VitExtra - 5; }
			
			if(CrystalEquipped.equals("yellow_crystal_agiextra_1")) { AgiExtra = AgiExtra - 1; AtkTimerMax = AtkTimerMax + 5; }
			if(CrystalEquipped.equals("yellow_crystal_agiextra_2")) { AgiExtra = AgiExtra - 3; AtkTimerMax = AtkTimerMax + 15; }
			if(CrystalEquipped.equals("yellow_crystal_agiextra_3")) { AgiExtra = AgiExtra - 5; AtkTimerMax = AtkTimerMax + 30; }
			
			if(CrystalEquipped.equals("red_crystal_strextra_1")) { StrExtra = StrExtra - 1;  }
			if(CrystalEquipped.equals("red_crystal_strextra_2")) { StrExtra = StrExtra - 3;  }
			if(CrystalEquipped.equals("red_crystal_strextra_3")) { StrExtra = StrExtra - 5;  }
			
			playerUse.StrExtra = String.valueOf(StrExtra);
			playerUse.VitExtra = String.valueOf(VitExtra);
			playerUse.AgiExtra = String.valueOf(AgiExtra);
			playerUse.DexExtra = String.valueOf(DexExtra);
			playerUse.LukExtra = String.valueOf(LukExtra);
			
			playerUse.HpMax = String.valueOf(HpMax);
			playerUse.MpMax = String.valueOf(MpMax);
			playerUse.AtkTimerMax = String.valueOf(AtkTimerMax);
			playerUse.StaminaMax = String.valueOf(StaminaMax);
						
			AddItemBag(CrystalEquipped);
			switch (num) {
				case 1:
					playerUse.Crystal1 = "none";
					break;
				case 2:
					playerUse.Crystal2 = "none";
					break;
				case 3:
					playerUse.Crystal3 = "none";
					break;
				case 4:
					playerUse.Crystal4 = "none";
					break;
			}
		}
		
	

	//Give EXP
	public void GiveExp(float exp, int moblevel) {
		boolean levelup = false;
		int level = Integer.parseInt(playerUse.Level);
		float expPlayer = Float.parseFloat(playerUse.Exp);
		int HpMax = Integer.parseInt(playerUse.HpMax);
		int MpMax = Integer.parseInt(playerUse.MpMax);
		int StatusPoint = Integer.parseInt(playerUse.StatusPoint);
		int Atk = Integer.parseInt(playerUse.Atk);
		int Def = Integer.parseInt(playerUse.Def);
		int AtkTimerMax = Integer.parseInt(playerUse.AtkTimerMax);
		
		int strExtra = Integer.parseInt(playerUse.StrExtra);
		int vitExtra = Integer.parseInt(playerUse.VitExtra);
		int wisExtra = Integer.parseInt(playerUse.WisExtra);
		int agiExtra = Integer.parseInt(playerUse.AgiExtra);
		int dexExtra = Integer.parseInt(playerUse.DexExtra);
		int lukExtra = Integer.parseInt(playerUse.LukExtra);
		
		if(level >= 30) {
			return;
		}
		
		//underlevel
		if(level < 10 && (moblevel == 2 || moblevel == 3 || moblevel == 4 || moblevel == 5)) {
			exp = 1;
		}
		if(level < 20 && (moblevel == 3 || moblevel == 4 || moblevel == 5)) {
			exp = 1;
		}
		if(level < 30 && (moblevel == 4 || moblevel == 5)) {
			exp = 1;
		}
		if(level < 40 && moblevel == 5) {
			exp = 1;
		}
		
		//upperlevel
		if(level > 10 && moblevel == 1) {
			exp = 1;
		}
		if(level > 20 && moblevel == 2) {
			exp = 1;
		}
		if(level > 30 && moblevel == 3) {
			exp = 1;
		}
		if(level > 40 && moblevel == 4) {
			exp = 1;
		}
		
		
		if(moblevel == 99) {
			if(level > 0 && level < 10 ) {
				exp = 1;
			}
			if(level > 10 && level < 20 ) {
				exp = 2;
			}
			if(level > 10 && level < 20 ) {
				exp = 5;
			}
			if(level > 20 && level < 30 ) {
				exp = 10;
			}
			if(level > 30 && level < 40 ) {
				exp = 20;
			}
			if(level > 40 && level < 50 ) {
				exp = 50;
			}
		}
		
		if(playerUse.buffA.equals("turkey")) { exp = exp * 2; }
		if(playerUse.buffB.equals("turkey")) { exp = exp * 2; }
		if(playerUse.buffC.equals("turkey")) { exp = exp * 2; }
		expPlayer = expPlayer + exp;
		
		//Sewers   / Forest
		if(level == 1 && expPlayer >= 25) {  level = 2; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 2 && expPlayer >= 50) {  level = 3; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 3 && expPlayer >= 75) {  level = 4; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 4 && expPlayer >= 600) {  level = 5; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 5 && expPlayer >= 950) {  level = 6; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 6 && expPlayer >= 1200) {  level = 7; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 7 && expPlayer >= 3300) {  level = 8; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 8 && expPlayer >= 4200) {  level = 9; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 9 && expPlayer >= 5200) {  level = 10; expPlayer = 0; HpMax = HpMax + 10; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		
		//Watercave / Desert
		if(level == 10 && expPlayer >= 7800) {  level = 11; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 11 && expPlayer >= 9200) {  level = 12; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 12 && expPlayer >= 13300) {  level = 13; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 13 && expPlayer >= 15400) {  level = 14; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 14 && expPlayer >= 17500) {  level = 15; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 15 && expPlayer >= 18600) {  level = 16; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 16 && expPlayer >= 22700) {  level = 17; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 17 && expPlayer >= 24800) {  level = 18; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 18 && expPlayer >= 26900) {  level = 19; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 19 && expPlayer >= 32000) {  level = 20; expPlayer = 0; HpMax = HpMax + 20; MpMax = MpMax + 10; StatusPoint = StatusPoint + 6; levelup = true;}
		
		//Mines
		if(level == 20 && expPlayer >= 780000) {  level = 21; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 21 && expPlayer >= 911000) {  level = 22; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 22 && expPlayer >= 1320000) {  level = 23; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 23 && expPlayer >= 1530000) {  level = 24; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 24 && expPlayer >= 2540000) {  level = 25; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 25 && expPlayer >= 3650000) {  level = 26; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 26 && expPlayer >= 4200000) {  level = 27; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 27 && expPlayer >= 5200000) {  level = 28; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 28 && expPlayer >= 6280001) {  level = 29; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 29 && expPlayer >= 7100000) {  level = 30; expPlayer = 0; HpMax = HpMax + 30; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		
		//Snowpalace
		if(level == 30 && expPlayer >= 9100000)  {  level = 31; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 31 && expPlayer >= 15100000)  {  level = 32; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 32 && expPlayer >= 18100000) {  level = 33; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 33 && expPlayer >= 24100000) {  level = 34; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 34 && expPlayer >= 32000000) {  level = 35; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 35 && expPlayer >= 42100000) {  level = 36; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 36 && expPlayer >= 51360000) {  level = 37; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 37 && expPlayer >= 67370000) {  level = 38; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 38 && expPlayer >= 72038000) {  level = 39; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 39 && expPlayer >= 82039000) {  level = 40; expPlayer = 0; HpMax = HpMax + 60; MpMax = MpMax + 20; StatusPoint = StatusPoint + 6; levelup = true;}
		
		//Tower												   
		if(level == 40 && expPlayer >= 520390000) {  level = 41; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 41 && expPlayer >= 620390000) {  level = 42; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 42 && expPlayer >= 720390000) {  level = 43; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 43 && expPlayer >= 820390000) {  level = 44; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 44 && expPlayer >= 920390000) {  level = 45; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 45 && expPlayer >= 1320390000) {  level = 46; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 46 && expPlayer >= 1420390000) {  level = 47; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 47 && expPlayer >= 1520390000) {  level = 48; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 48 && expPlayer >= 1620390000) {  level = 49; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		if(level == 49 && expPlayer >= 1999999999) {  level = 50; expPlayer = 0; HpMax = HpMax + 80; MpMax = MpMax + 30; StatusPoint = StatusPoint + 6; levelup = true;}
		
		playerUse.Level = String.valueOf(level);
		playerUse.Exp = String.valueOf(expPlayer);
		playerUse.StatusPoint = String.valueOf(StatusPoint);
		
		if(levelup) {
			if(playerUse.Job.equals("Aprendiz")) { 
				HpMax = HpMax + 10; MpMax = MpMax + 10; Atk = Atk + 1;
				playerUse.HpMax = String.valueOf(HpMax); playerUse.MpMax = String.valueOf(MpMax); playerUse.Atk = String.valueOf(Atk);
			}
			
			if(playerUse.Job.equals("Espadachim")) { 
				HpMax = HpMax + 20; Atk = Atk + 5;
				playerUse.HpMax = String.valueOf(HpMax); playerUse.Atk = String.valueOf(Atk);
			}
			
			if(playerUse.Job.equals("Mago")) { 
				HpMax = HpMax + 10; Atk = Atk + 2;
				MpMax = MpMax + 20;
				playerUse.HpMax = String.valueOf(HpMax); playerUse.Atk = String.valueOf(Atk); playerUse.MpMax = String.valueOf(MpMax);
			}
			
			if(playerUse.Job.equals("Curandeiro")) { 
				HpMax = HpMax + 15; 
				Atk = Atk + 1;
				MpMax = MpMax + 15;
				playerUse.HpMax = String.valueOf(HpMax); playerUse.MpMax = String.valueOf(MpMax); playerUse.Atk = String.valueOf(Atk);
			}
			
			if(playerUse.Job.equals("Atirador")) { 
				HpMax = HpMax + 8; 
				playerUse.Atk = playerUse.Atk + 3; 
				AtkTimerMax = AtkTimerMax -2;
				playerUse.AtkTimerMax = String.valueOf(AtkTimerMax); playerUse.Atk = String.valueOf(Atk); playerUse.HpMax = String.valueOf(HpMax);
			}
			
			if(playerUse.Job.equals("Ladrao")) { 
				HpMax = HpMax + 10; 
				Atk = Atk + 2; 
				AtkTimerMax = AtkTimerMax -4;
				playerUse.AtkTimerMax = String.valueOf(AtkTimerMax); playerUse.Atk = String.valueOf(Atk); playerUse.HpMax = String.valueOf(HpMax);
			}
			
			//Job 2.1
			if(playerUse.Job.equals("Cavaleiro")) { 
				HpMax = HpMax + 10; 
				Atk = Atk + 6; 
				strExtra = strExtra + 10;
				vitExtra = vitExtra + 5;
				playerUse.Atk = String.valueOf(Atk);
				playerUse.StrExtra = String.valueOf(strExtra);
				playerUse.VitExtra = String.valueOf(vitExtra);
				
			}
			if(playerUse.Job.equals("Elementarista")) { 
				HpMax = HpMax + 5; 
				MpMax = MpMax + 25;
				Atk = Atk + 1;
				wisExtra = wisExtra + 8;
				
				playerUse.HpMax = String.valueOf(HpMax); 
				playerUse.Atk = String.valueOf(Atk); 
				playerUse.MpMax = String.valueOf(MpMax);
				playerUse.WisExtra = String.valueOf(wisExtra);
			}
			if(playerUse.Job.equals("Especialista")) { 
				HpMax = HpMax + 10; 
				Atk = Atk + 2; 
				dexExtra = dexExtra + 10;
				vitExtra = vitExtra + 5;
				playerUse.Atk = String.valueOf(Atk);
				playerUse.DexExtra = String.valueOf(strExtra);
				playerUse.VitExtra = String.valueOf(vitExtra);
			}
			if(playerUse.Job.equals("Sacerdote")) { 
				HpMax = HpMax + 10; 
				MpMax = MpMax + 30;
				Atk = Atk + 1;
				wisExtra = wisExtra + 5;
				
				playerUse.HpMax = String.valueOf(HpMax); 
				playerUse.Atk = String.valueOf(Atk); 
				playerUse.MpMax = String.valueOf(MpMax);
				playerUse.WisExtra = String.valueOf(wisExtra);
			}
			if(playerUse.Job.equals("Assassino")) { 
				HpMax = HpMax + 10; 
				Atk = Atk + 4; 
				strExtra = strExtra + 3;
				vitExtra = vitExtra + 5;
				lukExtra = lukExtra + 10;
				AtkTimerMax = AtkTimerMax -4;
				playerUse.Atk = String.valueOf(Atk);
				playerUse.StrExtra = String.valueOf(strExtra);
				playerUse.VitExtra = String.valueOf(vitExtra);
				playerUse.LukExtra = String.valueOf(vitExtra);
				playerUse.AtkTimerMax = String.valueOf(AtkTimerMax);
			}
			//Job 2.2
			if(playerUse.Job.equals("Protetor")) { 
				HpMax = HpMax + 20; 
				Atk = Atk + 4; 
				strExtra = strExtra + 5;
				vitExtra = vitExtra + 10;
				playerUse.Atk = String.valueOf(Atk);
				playerUse.StrExtra = String.valueOf(strExtra);
				playerUse.VitExtra = String.valueOf(vitExtra);
			}
			if(playerUse.Job.equals("Invocador")) { 
				HpMax = HpMax + 10; 
				MpMax = MpMax + 10;
				Atk = Atk + 2;
				wisExtra = wisExtra + 8;
				
				playerUse.HpMax = String.valueOf(HpMax);
				playerUse.MpMax = String.valueOf(MpMax);
				playerUse.Atk = String.valueOf(Atk);
				playerUse.WisExtra = String.valueOf(wisExtra);
			}
			if(playerUse.Job.equals("Musico")) { 
				HpMax = HpMax + 10; 
				Atk = Atk + 2; 
				AtkTimerMax = AtkTimerMax -4;
				playerUse.AtkTimerMax = String.valueOf(AtkTimerMax); playerUse.Atk = String.valueOf(Atk); playerUse.HpMax = String.valueOf(HpMax);
			}
			if(playerUse.Job.equals("Lutador")) { 
				HpMax = HpMax + 10; 
				MpMax = MpMax + 5; 
				Atk = Atk + 12; 
				strExtra = strExtra + 10;
				playerUse.StrExtra = String.valueOf(strExtra);
				playerUse.Atk = String.valueOf(Atk); 
				playerUse.HpMax = String.valueOf(HpMax);
			}
			if(playerUse.Job.equals("Vigarista")) { 
				HpMax = HpMax + 10; 
				Atk = Atk + 2; 
				AtkTimerMax = AtkTimerMax -4;
				playerUse.AtkTimerMax = String.valueOf(AtkTimerMax); playerUse.Atk = String.valueOf(Atk); playerUse.HpMax = String.valueOf(HpMax);
			}
		}	
		
		levelup = false;
		
	}
	
	public Sprite GetSpriteSkill(String skillname, int num) {
		
		if(skillname.equals("tripleattack")) { spr_skill = atlas_tripleattack.createSprite("tripleattack"+num); }
		if(skillname.equals("steal")) { spr_skill = atlas_steal.createSprite("steal"+num); }
		if(skillname.equals("soulclash")) { spr_skill = atlas_soulclash.createSprite("soulclash"+num); }
		if(skillname.equals("ravenblade")) { spr_skill = atlas_ravenblade.createSprite("ravenblade"+num); }
		if(skillname.equals("ragebound")) { spr_skill = atlas_ragebound.createSprite("ragebound"+num); }
		if(skillname.equals("thundercloud")) { spr_skill = atlas_thundercloud.createSprite("thundercloud"+num); }
		if(skillname.equals("perfectshot")) { spr_skill = atlas_perfectshot.createSprite("perfectshot"+num); }
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
	
	
	
	
	public String ItemDrop(String mob) {  
		int chance = randnumber.nextInt(1000);
		
		//Sewers
		if(mob.equals("poyo")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootnuts");  return "Adicionado Noz"; }
			if(chance >= 700 && chance <= 950) { AddItemBag("hatbat");  return "Adicionado Chapeu de Morcego"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoamarelo"); return "Adicionado Fragmento Amarelo"; }
		}
		
		if(mob.equals("aranarc")) {
			if(chance <= 500) { AddItemBag("lootsilk"); return "Adicionado Seda"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootfang");  return "Adicionado Dente"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("mpcan");  return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoazul"); return "Adicionado Fragmento Azul"; }
		}
		if(mob.equals("rat")) {
			if(chance <= 500) { AddItemBag("mpcan"); return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmushroom");  return "Adicionado Cogumelo"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("hatbunny");  return "Adicionado Orelhas de Coelho"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoroxo"); return "Adicionado Fragmento Roxo"; }
		}
		if(mob.equals("snake")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootfang");  return "Adicionado Dente"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoazul"); return "Adicionado Fragmento Azul"; }
		}
		
		//Floresta
		if(mob.equals("slime")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootnuts");  return "Adicionado Noz"; }
			if(chance >= 700 && chance <= 950) { AddItemBag("hatbat");  return "Adicionado Chapeu de Morcego"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoamarelo"); return "Adicionado Fragmento Amarelo"; }
		}
		
		if(mob.equals("bee")) {
			if(chance <= 500) { AddItemBag("lootsilk"); return "Adicionado Seda"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootfang");  return "Adicionado Dente"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("mpcan");  return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoazul"); return "Adicionado Fragmento Azul"; }
		}
		if(mob.equals("goblin")) {
			if(chance <= 500) { AddItemBag("mpcan"); return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmushroom");  return "Adicionado Cogumelo"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("hatheadset");  return "Adicionado headset"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoroxo"); return "Adicionado Fragmento Roxo"; }
		}
		if(mob.equals("enty")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("galhos");  return "Adicionado Galho"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentovermelho"); return "Adicionado Fragmento Vermelho"; }
		}
		
		//Watercave
		if(mob.equals("fisko")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootnuts");  return "Adicionado Noz"; }
			if(chance >= 700 && chance <= 950) { AddItemBag("hatbat");  return "Adicionado Chapeu de Morcego"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoamarelo"); return "Adicionado Fragmento Amarelo"; }
		}
		
		if(mob.equals("shelly")) {
			if(chance <= 500) { AddItemBag("lootsilk"); return "Adicionado Seda"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootfang");  return "Adicionado Dente"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("mpcan");  return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoazul"); return "Adicionado Fragmento Azul"; }
		}
		if(mob.equals("marit")) {
			if(chance <= 500) { AddItemBag("mpcan"); return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmushroom");  return "Adicionado Cogumelo"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("hatsanta");  return "Adicionado Chapeu de papai noel"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoverde"); return "Adicionado Fragmento Verde"; }
		}
		if(mob.equals("tencle")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootpaw");  return "Adicionado Patas"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoroxo"); return "Adicionado Fragmento Roxo"; }
		}
		
		//Desert
		if(mob.equals("cactus")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootnuts");  return "Adicionado Noz"; }
			if(chance >= 700 && chance <= 950) { AddItemBag("hatbat");  return "Adicionado Chapeu de Morcego"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoamarelo"); return "Adicionado Fragmento Amarelo"; }
		}
		
		if(mob.equals("harpia")) {
			if(chance <= 500) { AddItemBag("lootsilk"); return "Adicionado Seda"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootfang");  return "Adicionado Dente"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("mpcan");  return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoazul"); return "Adicionado Fragmento Azul"; }
		}
		if(mob.equals("golem")) {
			if(chance <= 500) { AddItemBag("mpcan"); return "Adicionado Refrigerante de MP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmushroom");  return "Adicionado Cogumelo"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("hatsanta");  return "Adicionado Chapeu de papai noel"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoverde"); return "Adicionado Fragmento Verde"; }
		}
		if(mob.equals("brimworn")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootpaw");  return "Adicionado Patas"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 950 && chance <= 1000) { AddItemBag("lootfragmentoroxo"); return "Adicionado Fragmento Roxo"; }
		}
		
		//Mines
		if(mob.equals("ghost")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("hatslime");  return "Adicionado Chapeu de slime"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentovermelho");  return "Adicionado Fragmento Vermelho"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("hatheadset"); return "Adicionado Fones"; }
		}
		
		if(mob.equals("tipper")) {
			if(chance <= 500) { AddItemBag("lootsilk"); return "Adicionado Seda"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentoroxo");  return "Adicionado Fragmento Roxo"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("mpcan"); return "Adicionado Refrigerante de MP (P)"; }
		}
		if(mob.equals("urso")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmushroom");  return "Adicionado Cogumelo"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentoamarelo");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("hatcapoult"); return "Adicionado capoult"; }
		}
		if(mob.equals("caveira")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("galhos");  return "Adicionado Galho"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("lootfragmentoverde"); return "Adicionado Fragmento Verde"; }
		}
		
		//Mines
		if(mob.equals("flare")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("hatslime");  return "Adicionado Chapeu de slime"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentovermelho");  return "Adicionado Fragmento Vermelho"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("hatheadset"); return "Adicionado Fones"; }
		}
		
		if(mob.equals("salamander")) {
			if(chance <= 500) { AddItemBag("lootsilk"); return "Adicionado Seda"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentoverde");  return "Adicionado Fragmento Verde"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("mpcan"); return "Adicionado Refrigerante de MP (P)"; }
		}
		if(mob.equals("cerberus")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmushroom");  return "Adicionado Cogumelo"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentoamarelo");  return "Adicionado Fragmento Amarelo"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("hatcapoult"); return "Adicionado capoult"; }
		}
		if(mob.equals("hammertooth")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("galhos");  return "Adicionado Galho"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("lootfragmentoverde"); return "Adicionado Fragmento Verde"; }
		}
		
		//Mines
		if(mob.equals("goblin")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("hatslime");  return "Adicionado Chapeu de slime"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentovermelho");  return "Adicionado Fragmento Vermelho"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("hatheadset"); return "Adicionado Fones"; }
		}
		
		if(mob.equals("pinguim")) {
			if(chance <= 500) { AddItemBag("lootsilk"); return "Adicionado Seda"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentoverde");  return "Adicionado Fragmento Verde"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("mpcan"); return "Adicionado Refrigerante de MP (P)"; }
		}
		if(mob.equals("snowman")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("lootmushroom");  return "Adicionado Cogumelo"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootfragmentoamarelo");  return "Adicionado Fragmento Amarelo"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("hatcapoult"); return "Adicionado capoult"; }
		}
		if(mob.equals("yeti")) {
			if(chance <= 500) { AddItemBag("hpcan"); return "Adicionado Refrigerante de HP (P)"; }
			if(chance >= 500 && chance <= 700) { AddItemBag("galhos");  return "Adicionado Galho"; }
			if(chance >= 700 && chance <= 980) { AddItemBag("lootmoney");  return "Adicionado Saco de dinheiro"; }
			if(chance >= 980 && chance <= 1000) { AddItemBag("lootfragmentoverde"); return "Adicionado Fragmento Verde"; }
		}
		
		return "";
	}
	
	public void CrystalExchanged(String color) {
			int chance = randnumber.nextInt(10000);
			
			if(color.equals("yellow")) {
				if(CheckRequiredItem("lootfragmentoamarelo",true,15)) {
					if(chance <= 5000) { AddItemBag("yellow_crystal_agiextra_1"); }
					if(chance >= 5000 && chance <= 9900) { AddItemBag("yellow_crystal_agiextra_2"); }  
					if(chance >= 9900 && chance <= 10000) { AddItemBag("yellow_crystal_agiextra_3"); }
				}
			}
			
			if(color.equals("blue")) {
				if(CheckRequiredItem("lootfragmentoazul",true,15)) {
					if(chance <= 5000) { AddItemBag("blue_crystal_intextra_1"); }
					if(chance >= 5000 && chance <= 9900) { AddItemBag("blue_crystal_intextra_2"); }
					if(chance >= 9900 && chance <= 10000) { AddItemBag("blue_crystal_intextra_3"); }
				}
			}
			
			if(color.equals("purple")) {
				if(CheckRequiredItem("lootfragmentoroxo",true,15)) {
					if(chance <= 5000) { AddItemBag("purple_crystal_vitextra_1"); }
					if(chance >= 5000 && chance <= 9900) { AddItemBag("purple_crystal_vitextra_2"); }
					if(chance >= 9900 && chance <= 10000) { AddItemBag("purple_crystal_vitextra_3"); }
				}
			}
					
			if(color.equals("green")) {
				if(CheckRequiredItem("lootfragmentoverde",true,15)) {
					if(chance <= 5000) { AddItemBag("green_crystal_lukextra_1"); }
					if(chance >= 5000 && chance <= 9900) { AddItemBag("green_crystal_lukextra_2"); }
					if(chance >= 9900 && chance <= 10000) { AddItemBag("green_crystal_lukextra_3"); }
				}
			}
				
			if(color.equals("red")) {
				if(CheckRequiredItem("lootfragmentovermelho",true,15)) {
					if(chance <= 5000) { AddItemBag("red_crystal_strextra_1"); }
					if(chance >= 5000 && chance <= 9900) { AddItemBag("red_crystal_strextra_2"); }
					if(chance >= 9900 && chance <= 10000) { AddItemBag("red_crystal_strextra_3"); }
				}
			}		
	}
	
	public boolean CheckRequiredItem(String item, boolean isRemove, int quantity) {
		String[] lstItem = playerUse.Itens.split("-");
		String[] itemSplit;
		boolean exist = false;
		int qtd = 0;
		int posicaoItem = 0;
		String listaItemFinal;
		
		for(int i = 0; i < lstItem.length; i++) {
			if(lstItem[i].contains(item) && !exist) {
				posicaoItem = i;
				exist = true;
			}
		}
		
		if(exist) {
			if(!isRemove) { return true; }
			
			itemSplit = lstItem[posicaoItem].split("#");
			qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
			
			if(isRemove) {
				if(qtd < quantity) {
					return false;
				}
				
				qtd = qtd - quantity;
				if(qtd <= 0) { 
					lstItem[posicaoItem] = "[NONE]";
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					playerUse.Itens = listaItemFinal;
					return true;
				}
				if(qtd > 0) {
					lstItem[posicaoItem] = "[" + itemSplit[0].replace("[", "") + "#" + qtd + "]";
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					playerUse.Itens = listaItemFinal;
					return true;
				}
			}	
		}
		
		return false;
	}
	
	public void AddItemBag(String itemName) {
		String[] lstItem = playerUse.Itens.split("-");
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
			if(qtd >= 99) { return; }
				lstItem[posicaoItem] = "[" + itemSplit[0].replace("[", "") + "#" + String.valueOf(qtd) + "]";
				listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
				listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
				playerUse.Itens = listaItemFinal;
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
				playerUse.Itens = listaItemFinal;
			}
		}
	}
	
	public void DiscartItem(int itemNum) {
		String[] lstItem = playerUse.Itens.split("-");
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
		money = Integer.parseInt(playerUse.Money);
		if(money > 5000) { return; }
		int moneygave = randnumber.nextInt(5);
		while(moneygave < 2) { moneygave = randnumber.nextInt(5); }
		money = money + (moneygave * 2);
		playerUse.Money = String.valueOf(money);
		
		//Clean Item placebox
		lstItem[itemNum] = "[NONE]";
		listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
		listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
		playerUse.Itens = listaItemFinal;	
	}
	
	//SHOP
	public String CheckBuyItemStreetsA(String shop, int num) {
		int Money = Integer.parseInt(playerUse.Money);
		String SysMsg = "";
		
		if(shop.equals("refrishop")) {
			if(num == 1) {  
				if(Money >= 2) {  
					AddItemBag("hpcan"); 
					Money = Money - 2; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 2) {  
				if(Money >= 10) {  
					AddItemBag("hpbigcan"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					SysMsg = "Dinheiro insuficiente";
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 3) {  
				if(Money >= 30) {  
					AddItemBag("hpultracan");
					Money = Money - 30; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 4) {  
				if(Money >= 10) {  
					AddItemBag("mpcan"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 5) {  
				if(Money >= 20) {  
					AddItemBag("mpbigcan"); 
					Money = Money - 20; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 6) {  
				if(Money >= 40) {  
					AddItemBag("mpultracan"); 
					Money = Money - 40; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 7) {  
				if(Money >= 2) {  
					AddItemBag("stcan"); 
					Money = Money - 2; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 8) {  
				if(Money >= 20) {  
					AddItemBag("stbigcan"); 
					Money = Money - 20; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 9) {  
				if(Money >= 20) {  
					AddItemBag("chipz"); 
					Money = Money - 20; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 10) {  
				if(Money >= 20) {  
					AddItemBag("turkey"); 
					Money = Money - 20; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 11) {  
				if(Money >= 20) {  
					AddItemBag("egg"); 
					Money = Money - 20; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
		}
		if(shop.equals("lojaroupas1")) {
			
			if(num == 1) {  
				if(Money >= 25) {  
					AddItemBag("hatbear"); 
					Money = Money - 25; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 2) {  
				if(Money >= 30) {  
					AddItemBag("hatbunny"); 
					Money = Money - 30; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 3) {  
				if(Money >= 30) {  
					AddItemBag("hatheadset"); 
					Money = Money - 30; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 4) {  
				if(Money >= 10 && playerUse.Sex.equals("M")) {  
					AddItemBag("basictopM");
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 5) {  
				//Vazio
			}
			if(num == 6) {  
				if(Money >= 10 && playerUse.Sex.equals("F")) {  
					AddItemBag("basictopF");
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 7) {  
				//Vazio
			}
			
			
			
			//FILA 2
			if(num == 8) {  
				if(Money >= 50) {  
					AddItemBag("hatmagician"); 
					Money = Money - 50; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 9) {  
				if(Money >= 50) {  
					AddItemBag("hatpirate"); 
					Money = Money - 50; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 10) {  
				if(Money >= 50) {  
					AddItemBag("hatsanta"); 
					Money = Money - 50; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 11) {  
				if(Money >= 10 && playerUse.Sex.equals("M")) {  
					AddItemBag("basicbottomM"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 12) {  
				if(Money >= 10 && playerUse.Sex.equals("M")) {  
					AddItemBag("basicfooterM"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 13) {  
				if(Money >= 10 && playerUse.Sex.equals("F")) {  
					AddItemBag("basicbottomM"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 14) {  
				if(Money >= 10 && playerUse.Sex.equals("F")) {  
					AddItemBag("basicfooterM"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			
			
			
			
			//FILA 2
			if(num == 15) {  
				if(Money >= 5) {  
					AddItemBag("hatslime"); 
					Money = Money - 5; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 16) {  
				//vazio
			}
			if(num == 17) {  
				//vazio	
			}
			if(num == 18) {  
				if(Money >= 10 && playerUse.Sex.equals("M")) {  
					AddItemBag("sporttopM"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 19) {  
				//vazio	
			}
			if(num == 20) {  
				if(Money >= 10 && playerUse.Sex.equals("F")) {  
					AddItemBag("sporttopF"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 21) {  
				//vazio	
			}
			
			
			//FILA 4
			if(num == 22) {  
				//vazio	
			}
			if(num == 23) {  
				//vazio
			}
			if(num == 24) {  
				//vazio	
			}
			if(num == 25) {  
				if(Money >= 10 && playerUse.Sex.equals("M")) {  
					AddItemBag("sportbottomM"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 26) {  
				if(Money >= 10 && playerUse.Sex.equals("M")) {  
					AddItemBag("sportfooterM"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}	
			}
			if(num == 27) {  
				if(Money >= 10 && playerUse.Sex.equals("F")) {  
					AddItemBag("sportbottomF"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}		
			}
			if(num == 28) {  
				if(Money >= 10 && playerUse.Sex.equals("F")) {  
					AddItemBag("sportfooterF"); 
					Money = Money - 10; 
					if(Money < 0) { Money = 0; } 	
					SysMsg = "Comprado!";
					playerUse.Money = String.valueOf(Money);
					return SysMsg;
				}
				else {
					SysMsg = "Dinheiro insuficiente"; 
					return SysMsg;
				}
			}	
		}
		
		
		
		return SysMsg;
	}
	
	public String CheckBuyWeapon() {
		
		Random numRandom = new Random();
		int Money = Integer.parseInt(playerUse.Money);
		String weapon = "knife";
		String SysMsg = "";
		
		String weaponName = "";
		String rank = "";
		String type = "";
		
		int rankNum = numRandom.nextInt(30000);
		int wpNum = numRandom.nextInt(30000);
		int typeNum = numRandom.nextInt(30);
		
		//Get Ranked
		if(rankNum >= 0 && rankNum <= 15000) { rank = "c"; }
		if(rankNum >= 15000 && rankNum <= 26000) { rank = "b"; }
		if(rankNum >= 26000 && rankNum <= 29900) { rank = "a"; }
		if(rankNum >= 29900 && rankNum <=  30000) { rank = "s"; }
		
		//Get Weapon
		if(typeNum >= 0 && typeNum <= 5) { type = "knife"; }
		if(typeNum >= 5 && typeNum <= 10) { type = "dagger"; }
		if(typeNum >= 10 && typeNum <= 15) { type = "sword"; }
		if(typeNum >= 15 && typeNum <= 20) { type = "pistol"; }
		if(typeNum >= 20 && typeNum <= 25) { type = "axe"; }
		if(typeNum >= 25 && typeNum <= 30) { type = "rod"; }
		
		if(type.equals("knife")) {
			if(rankNum >= 0 && rankNum <= 15000) { weaponName = "basic"; }
			if(rankNum >= 15000 && rankNum <= 30000) { weaponName = "doubleedge"; }
		}
		
		if(type.equals("sword")) {  
			if(rankNum >= 0 && rankNum <= 15000) { weaponName = "wood"; }
			if(rankNum >= 15000 && rankNum <= 25000) { weaponName = "rage"; }
			if(rankNum >= 25000 && rankNum <= 27000) { weaponName = "serpent"; }
			if(rankNum >= 27000 && rankNum <= 29000) { weaponName = "flame"; }
			if(rankNum >= 29000 && rankNum <= 30000) { weaponName = "cristal"; }
		}
		
		if(type.equals("rod")) {  
			if(rankNum >= 0 && rankNum <=  15000) { weaponName = "stick"; }
			if(rankNum >= 15000 && rankNum <=  25000) { weaponName = "gloom"; }
			if(rankNum >= 25000 && rankNum <=  27000) { weaponName = "star"; }
			if(rankNum >= 27000 && rankNum <=  29000) { weaponName = "death"; }
			if(rankNum >= 29000 && rankNum <=  30000) { weaponName = "butterfly"; }
		}
		
		if(type.equals("pistol")) {  
			if(rankNum >= 0 && rankNum <=  15000) { weaponName = "basic"; }
			if(rankNum >= 15000 && rankNum <=  25000) { weaponName = "light"; }
			if(rankNum >= 25000 && rankNum <=  27000) { weaponName = "cannon"; }
			if(rankNum >= 27000 && rankNum <=  29000) { weaponName = "shooter"; }
			if(rankNum >= 29000 && rankNum <=  30000) { weaponName = "shark"; }
		}
		
		if(type.equals("axe")) {  
			if(rankNum >= 0 && rankNum <= 15000) { weaponName = "basic"; }
			if(rankNum >= 15000 && rankNum <= 25000) { weaponName = "hammer"; }
			if(rankNum >= 25000 && rankNum <= 27000) { weaponName = "scythe"; }
			if(rankNum >= 27000 && rankNum <= 29000) { weaponName = "anchor"; }
			if(rankNum >= 29000 && rankNum <= 30000) { weaponName = "guitar"; }
		}
		
		if(type.equals("dagger")) {  
			if(rankNum >= 0 && rankNum <= 15000) { weaponName = "basic"; }
			if(rankNum >= 15000 && rankNum <= 25000) { weaponName = "wind"; }
			if(rankNum >= 25000 && rankNum <= 27000) { weaponName = "marine"; }
			if(rankNum >= 27000 && rankNum <= 29000) { weaponName = "flame"; }
			if(rankNum >= 29000 && rankNum <= 30000) { weaponName = "thunder"; }
		}
		
		
		String weaponGot = weaponName + type + "_" + rank;
		
		
		if(Money >= 200) {
			AddItemBag(weaponGot); 
			Money = Money - 200; 
			if(Money < 0) { Money = 0; } 	
			SysMsg = "Comprado!";
			playerUse.Money = String.valueOf(Money);
		}
		else {
			SysMsg = "Dinheiro insuficiente"; 
		}	
			
		
		return SysMsg;
	}
	
	///////////////////////////////////////////////// [Online HTML] /////////////////////////////////
	public String GetResult() {
		return onlineResult;
	}

	public String GetAccount() {
		return playerAccount;
	}

	public ArrayList<Player> GetPlayers() {
		return lstPlayers;
	}
	
	public void SetPlayers(ArrayList<Player> _lstPlayers) {
		lstPlayers = _lstPlayers;
	}
	
	public ArrayList<Player> RecoverOnlineList(){
		return lstPlayerOnline;
	}
	
	public ArrayList<String> RecoverOnlineChat(){
		return lstChats;
	}
	
	public ArrayList<Monster> RecoverMonsterList() {
		return lstMonsters;
	}
	
	public String GetOnlineGrab() {
		return onlineGrab;
	}
	public void SetOnlineGrab() {
		onlineGrab = "none";
	}
	
	public void GiveExpGetFromServer(String line) {
	    if (line.contains("Unavailable")) {
	        //System.out.println("Line: indisponivel exp");
	        return;
	    }

	    // Split the line into individual exp data strings
	    String[] expDataArray = line.split("@");

	    // Iterate over each exp data string
	    for (String expData : expDataArray) {
	        // Split the exp data string into individual data fields
	        String[] expDataFields = expData.split("\\|");

	        // Check if expDataFields has the expected length
	        if (expDataFields.length < 9) { // Adjust the length based on the number of expected fields
	            //System.out.println("Skipping incomplete exp data: " + expData);
	            continue;
	        }

	        float ExpSended = Float.parseFloat(expDataFields[8]);
	        GiveExp(ExpSended, 99);
	    }
	}
	
	public void UpdateOnlinePlayers(String line) {
		
	    if (line.contains("Unavailable")) {
	        //System.out.println("Line: indisponivel player");
	        return;
	    }

	    // Split the line into individual player data strings
	    String[] playerDataArray = line.split("@");

	    // Iterate over each player data string, excluding the last one
	    for (String playerData : playerDataArray) {
	        // Split the player data string into individual data fields
	        String[] playerDataFields = playerData.split(":");
	        
	        
	        // Check if playerData has the expected length
 			if (playerDataFields.length < 5) { // Adjust the length based on the number of expected fields
 				//System.out.println("Skipping incomplete mob data: " + playerData);
 				return;
 			}
	        
	        // Check if mobDataFields is null or empty
 			if (playerDataFields == null || playerDataFields.length == 0) {
 				//System.out.println("playerDataFields is null or empty");
 				return;
 			}

	        // Create a new Player object and set its fields
	        Player playerOnline = new Player();
	        playerOnline.Name = playerDataFields[2];
	        playerOnline.AccountID = playerDataFields[4];
	        playerOnline.Level = playerDataFields[6];
	        playerOnline.Map = playerDataFields[8];
	        playerOnline.Hp = playerDataFields[10];
	        playerOnline.Mp = playerDataFields[12];
	        playerOnline.PosX = playerDataFields[14];
	        playerOnline.PosY = playerDataFields[16];
	        playerOnline.Walk = playerDataFields[18];
	        playerOnline.Weapon = playerDataFields[20];
	        playerOnline.Frame = playerDataFields[22];
	        playerOnline.SyncPlayerMob = playerDataFields[24];
	        playerOnline.SetUpper = playerDataFields[26];
	        playerOnline.SetBottom = playerDataFields[28];
	        playerOnline.SetFooter = playerDataFields[30];
	        playerOnline.Hair = playerDataFields[32];
	        playerOnline.Sex = playerDataFields[34];
	        playerOnline.Color = playerDataFields[36];
	        playerOnline.Hat = playerDataFields[38];
	        playerOnline.Side = playerDataFields[40];
	        playerOnline.Job = playerDataFields[42];
	        playerOnline.playerInBattle = playerDataFields[44];
	        playerOnline.playerInAttack = playerDataFields[46];
	        playerOnline.playerInCast = playerDataFields[48];
	        playerOnline.playerSit = playerDataFields[50];
	        playerOnline.party = playerDataFields[52];
	        playerOnline.isPlayerOnline = playerDataFields[54];
	        playerOnline.MagicSync = playerDataFields[56];
	        
	        if(!playerUse.party.equals("none") && playerUse.party.equals(playerOnline.party) && !playerUse.Name.equals(playerOnline.Name) && !playerOnline.MagicSync.equals("none")){
	        	onlineGrab = playerOnline.MagicSync;
	        }
	        
	        // Check if playerOnline already exists in lstPlayerOnline and replace with new data
	        boolean exists = false;
	        for (int i = 0; i < lstPlayerOnline.size(); i++) {
	            if (lstPlayerOnline.get(i).Name.equals(playerOnline.Name)) {
	                lstPlayerOnline.set(i, playerOnline);
	                exists = true;
	                break;
	            }
	        }

	        // Add playerOnline if it doesn't already exist
	        if (!exists) {
	            lstPlayerOnline.add(playerOnline);
	        }
	    } // end of loop with online data
	    
	    //Check if it's the sync player
	    if(lstPlayerOnline.get(0).Name.equals(playerUse.Name)) {
	    	playerUse.SyncPlayerMob = playerUse.Map;
	    }

	    // Remove the player with playerUse.Name
	    lstPlayerOnline.removeIf(player -> player.Name.equals(playerUse.Name));
	    
	}
	
	public void UpdateOnlineMobs(String line) {
		if (line.contains("Unavailable")) {
			//System.out.println("Line: indisponivel player");
			return;
		}
	
		// Split the line into individual mob data strings
		String[] mobDataArray = line.split("@");
	
		// Iterate over each mob data string, excluding the last one
		for (String mobData : mobDataArray) {
			// Split the mob data string into individual data fields
			String[] mobDataFields = mobData.split(":");
	
			// Check if mobDataFields has the expected length
			if (mobDataFields.length < 5) { // Adjust the length based on the number of expected fields
				//System.out.println("Skipping incomplete mob data: " + mobData);
				continue;
			}
	
			// Create a new Mob object and set its fields
			Monster mobOnline = new Monster();
			mobOnline.MobID = mobDataFields[2];
			mobOnline.MobHp = Integer.parseInt(mobDataFields[4]);
			mobOnline.MobMp = Integer.parseInt(mobDataFields[6]);
			//mobOnline.MobPosX = Float.parseFloat(mobDataFields[8]);
			//mobOnline.MobPosY = Float.parseFloat(mobDataFields[10]);
			mobOnline.MobTarget = mobDataFields[12];
			mobOnline.MobDead = mobDataFields[14];		
			mobOnline.MobPosXFinal = Float.parseFloat((String) mobDataFields[16]);
			mobOnline.MobPosYFinal = Float.parseFloat((String) mobDataFields[18]);
			mobOnline.GotFinal = Boolean.parseBoolean((String) mobDataFields[20]);
			mobOnline.MobMap = mobDataFields[22];
	
			// Check if mobOnline already exists in lstMobOnline
			boolean exists = false;
			for (int i = 0; i < lstMonsters.size(); i++) {
				if (lstMonsters.get(i).MobID.equals(mobOnline.MobID)) {
					// Replace value
					lstMonsters.get(i).MobHp = Integer.parseInt(mobDataFields[4]);
					lstMonsters.get(i).MobMp = Integer.parseInt(mobDataFields[6]);
					//lstMonsters.get(i).MobPosX = Float.parseFloat(mobDataFields[8]);
					//lstMonsters.get(i).MobPosY = Float.parseFloat(mobDataFields[10]);
					lstMonsters.get(i).MobTarget = mobDataFields[12];
					lstMonsters.get(i).MobDead = mobDataFields[14];				
					lstMonsters.get(i).MobPosXFinal = Float.parseFloat((String) mobDataFields[16]);
					lstMonsters.get(i).MobPosYFinal = Float.parseFloat((String) mobDataFields[18]);
					lstMonsters.get(i).GotFinal = Boolean.parseBoolean((String) mobDataFields[20]);
					lstMonsters.get(i).MobMap = mobDataFields[22];
					exists = true;
					break;
				}
			}
	
			// Add mobOnline if it doesn't already exist
			if (!exists) {
				lstMonsters.add(mobOnline);
			}
		}
	}
	
	public void ProcessChatList(String message) {
		if(message.contains("Unavailable")) { 
			//System.out.println("Line: indisponivel chat"); 
			return; 
		}
		
        // Split the message by the '@' character to get each part
        String[] parts = message.split("@");

        // Create an ArrayList to store the parts
        ArrayList<String> partsList = new ArrayList<>();

        // Iterate over the parts starting from the second part (ignoring the first part)
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            // Trim any leading or trailing whitespace
            part = part.trim();
            // Skip empty parts
            if (!part.isEmpty()) {
                // Extract the name and message from the part
                String name = extractValue(part, "Name");
                String msg = extractValue(part, "Msg");
                // Format the part as "Name:Message"
                String formattedPart = name + ":" + msg;
                partsList.add(formattedPart);
            }
        }

        lstChats = partsList;
    }

    private String extractValue(String part, String key) {
        String[] tokens = part.split(":");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].trim().equals(key) && i + 1 < tokens.length) {
                return tokens[i + 1].trim();
            }
        }
        return "";
    }
	
	public void CreateAccountOnline(String tipoRequisicao, HttpCallback callback) throws UnsupportedEncodingException {

		int accNumber = randnumber.nextInt(99999999);
		while (accNumber < 1000000) {
			accNumber = randnumber.nextInt(99999999);
		}

		String account = String.valueOf(accNumber);
		// Prepare the data to post
		Map<String, String> parameters = new HashMap<>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest()
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();
				
		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(() -> {
					onlineResult = "success";
					playerAccount = account;
					callback.onSuccess(responseText);
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(() -> {
					// Call the failure method on the callback
					callback.onFailure(t);
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(() -> {
					// Call the failure method on the callback with a cancellation message
					callback.onFailure(new Exception("Request cancelled"));
				});
			}
		});
	}
	

	public void CheckAccount(String tipoRequisicao, String account, HttpCallback callback)
			throws UnsupportedEncodingException {

		int accNumber = randnumber.nextInt(99999999);
		while (accNumber < 1000000) {
			accNumber = randnumber.nextInt(99999999);
		}

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						// Update the game state or UI based on the response
						//System.out.println("Response: " + responseText);
						onlineResult = responseText;
						playerAccount = account;
						callback.onSuccess(responseText);
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	public void SaveChar(String tipoRequisicao, String account, String charnumber, HttpCallback callback)
			throws UnsupportedEncodingException {

		float posXfloat = Float.parseFloat(playerUse.PosX);
		float posYfloat = Float.parseFloat(playerUse.PosY);
		
		int posX = Math.round(posXfloat);
		int posY = Math.round(posYfloat);

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", charnumber);

		parameters.put("AccountID", playerUse.AccountID);
		parameters.put("AccountNumber", playerUse.AccountNumber);
		parameters.put("Characternumber", playerUse.Characternumber);
		parameters.put("Name", playerUse.Name);
		parameters.put("Sex", playerUse.Sex);
		parameters.put("Hair", playerUse.Hair);
		parameters.put("Color", playerUse.Color);
		parameters.put("Hat", playerUse.Hat);
		parameters.put("Job", playerUse.Job);
		parameters.put("SetUpper", playerUse.SetUpper);
		parameters.put("SetBottom", playerUse.SetBottom);
		parameters.put("SetFooter", playerUse.SetFooter);
		parameters.put("Level", playerUse.Level);
		parameters.put("Exp", playerUse.Exp);
		parameters.put("Map", playerUse.Map);
		parameters.put("Hp", playerUse.Hp);
		parameters.put("Mp", playerUse.Mp);
		parameters.put("Money", playerUse.Money);
		parameters.put("HpMax", playerUse.HpMax);
		parameters.put("MpMax", playerUse.MpMax);
		parameters.put("RegenTime", playerUse.regenTime);
		parameters.put("RegenTimeMax", playerUse.regenTimeMax);
		parameters.put("PosX", String.valueOf(posX));
		parameters.put("PosY", String.valueOf(posY));
		parameters.put("Walk", playerUse.Walk);
		parameters.put("Frame", playerUse.Frame);
		parameters.put("CountFrame", playerUse.countFrame);
		parameters.put("Breakwalk", playerUse.breakwalk);
		parameters.put("Target", playerUse.Target);
		parameters.put("AtkTimer", playerUse.AtkTimer);
		parameters.put("AtkTimerMax", playerUse.AtkTimerMax);
		parameters.put("Casting", playerUse.Casting);
		parameters.put("Atk", playerUse.Atk);
		parameters.put("Def", playerUse.Def);
		parameters.put("Evasion", playerUse.Evasion);
		parameters.put("Side", playerUse.Side);
		parameters.put("Weapon", playerUse.Weapon);
		parameters.put("Crystal1", playerUse.Crystal1);
		parameters.put("Crystal2", playerUse.Crystal2);
		parameters.put("Crystal3", playerUse.Crystal3);
		parameters.put("Crystal4", playerUse.Crystal4);
		parameters.put("StatusPoint", playerUse.StatusPoint);
		parameters.put("Str", playerUse.Str);
		parameters.put("Agi", playerUse.Agi);
		parameters.put("Vit", playerUse.Vit);
		parameters.put("Dex", playerUse.Dex);
		parameters.put("Wis", playerUse.Wis);
		parameters.put("Luk", playerUse.Luk);
		parameters.put("Res", playerUse.Res);
		parameters.put("Stamina", playerUse.Stamina);
		parameters.put("StaminaMax", playerUse.StaminaMax);
		parameters.put("Itens", playerUse.Itens);
		parameters.put("Quests", playerUse.Quests);
		parameters.put("Hotkey1", playerUse.hotkey1);
		parameters.put("Hotkey2", playerUse.hotkey2);
		parameters.put("BuffA", playerUse.buffA);
		parameters.put("BuffB", playerUse.buffB);
		parameters.put("BuffC", playerUse.buffC);
		parameters.put("BuffTimeA", playerUse.BuffTimeA);
		parameters.put("BuffTimeB", playerUse.BuffTimeB);
		parameters.put("BuffTimeC", playerUse.BuffTimeC);
		parameters.put("Party", playerUse.party);
		parameters.put("PlayerInBattle", playerUse.playerInBattle);
		parameters.put("PlayerInAttack", playerUse.playerInAttack);
		parameters.put("PlayerInCast", playerUse.playerInCast);
		parameters.put("PlayerSit", playerUse.playerSit);
		parameters.put("SyncPlayerMob", playerUse.SyncPlayerMob);
		parameters.put("PlayerExpGet", playerUse.PlayerExpGet);
		parameters.put("Pet", playerUse.pet);
		parameters.put("Pethungry", playerUse.pethungry);
		parameters.put("Petcare", playerUse.petcare);
		parameters.put("PetTraining", playerUse.petTraining);
		parameters.put("PetLevel", playerUse.petLevel);
		parameters.put("MagicSync", playerUse.MagicSync);
		parameters.put("StrExtra", playerUse.StrExtra);
		parameters.put("VitExtra", playerUse.VitExtra);
		parameters.put("WisExtra", playerUse.WisExtra);
		parameters.put("AgiExtra", playerUse.AgiExtra);
		parameters.put("DexExtra", playerUse.DexExtra);
		parameters.put("LukExtra", playerUse.LukExtra);
		parameters.put("ResExtra", playerUse.ResExtra);
		parameters.put("isPlayerOnline", "online");

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						// Update the game state or UI based on the response
						//System.out.println("Response: " + responseText);
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}

	public void DeleteChar(String tipoRequisicao, String account, String charnumber, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", charnumber);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						// Update the game state or UI based on the response
						//System.out.println("Response: " + responseText);
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}

	public void GetAccountOnline(String tipoRequisicao, String account, String accountnumber, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", accountnumber);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						// Update the game state or UI based on the response
						//System.out.println("Response: " + responseText);
						if(responseText.contains("#Success#")) {
							LoadData(responseText);
							callback.onSuccess("success");
						}
						else {
							callback.onSuccess("fail");
						}
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}

	public void CreateCharOnline(String tipoRequisicao, String accountnumber, String name,String sex, String hair, String color, HttpCallback callback) throws UnsupportedEncodingException {

		String itensList = "";
		for (int i = 0; i < 16; i++) {
			if (i == 0) {
				itensList = itensList + "[hpcan#20]-";
			}
			if (i > 0) {
				itensList = itensList + "[NONE]-";
			}
		}
		
		String charnumber = "0";
		boolean checkchar = false;
			
		if(lstPlayers.get(0).Name.equals("none") && !checkchar) { charnumber = "1"; checkchar = true; }
		if(lstPlayers.get(1).Name.equals("none") && !checkchar) { charnumber = "2"; checkchar = true; }
		if(lstPlayers.get(2).Name.equals("none") && !checkchar) { charnumber = "3"; checkchar = true; }

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", accountnumber);
		parameters.put("Charnumber", charnumber);

		parameters.put("Name", name);
		parameters.put("Sex", sex);
		parameters.put("Hair", hair);
		parameters.put("Color", color);
		parameters.put("Itens", itensList);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Response: " + responseText);
						if(responseText.contains("updated")) {
							callback.onSuccess("success");
						}
						else {
							callback.onSuccess("fail");
						}
						
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	public void SendAtk(String tipoRequisicao, String account, String charnumber, String MobHP, String MobTarget, String MobDead,String MobID, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", charnumber);
		parameters.put("MobHP", MobHP);
		parameters.put("MobTarget", playerUse.Name);
		parameters.put("MobDead", MobDead);
		parameters.put("MobID", MobID);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request Atk: " + responseText);
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	
	public void SendExpBank(String tipoRequisicao, String account, String charnumber,String Name, String ExpSended,String DateExp, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("AccountID", account);
		parameters.put("Charnumber", charnumber);
		parameters.put("Name", Name);
		parameters.put("DateExp", DateExp);
		parameters.put("ExpSended", ExpSended);
		
		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request Atk: " + responseText);
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	public void GetExpBank(String tipoRequisicao, String account, String charnumber,String Name, String DateExpGet, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("AccountID", account);
		parameters.put("Charnumber", charnumber);
		parameters.put("PlayerExpGet", DateExpGet);
		parameters.put("Name", Name);
		
		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println(responseText);
						GiveExpGetFromServer(responseText);
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	
	public void SendMobData(String tipoRequisicao, String account, String charnumber, HttpCallback callback)
            throws UnsupportedEncodingException {

        // Prepare the data to post
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Servername", lservername);
        parameters.put("Username", lusername);
        parameters.put("Password", lpassword);
        parameters.put("Dbname", ldbname);
        parameters.put("Request", tipoRequisicao);
        parameters.put("Dataaccount", account);
        parameters.put("Charnumber", charnumber);
        parameters.put("Name", playerUse.Name);
        
        // Loop through lstMonsters to get mob data where MobMap equals playerUse.Map
        int mobIndex = 0;
        for (Monster monster : lstMonsters) {
            if (monster.MobMap.equals(playerUse.Map)) {
                parameters.put("MobID" + mobIndex, String.valueOf(monster.MobID));
                parameters.put("MobHp" + mobIndex, String.valueOf(monster.MobHp));
                parameters.put("MobMp" + mobIndex, String.valueOf(monster.MobMp));
                parameters.put("MobPosX" + mobIndex, String.valueOf(monster.MobPosX));
                parameters.put("MobPosY" + mobIndex, String.valueOf(monster.MobPosY));
                parameters.put("MobTarget" + mobIndex, String.valueOf(monster.MobTarget));
                parameters.put("MobDead" + mobIndex, String.valueOf(monster.MobDead));
                parameters.put("MobMap" + mobIndex, String.valueOf(monster.MobMap));
                mobIndex++;
            }
        }

        String content = "";
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (content.length() > 0) {
                content += "&";
            }
            content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(parameter.getValue(), "UTF-8");
        }

        // Create the HTTP request
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        HttpRequest httpRequest = requestBuilder.newRequest()
                .method(Net.HttpMethods.POST)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url("https://moonboltprojects.online/connect.php")
                .content(content)
                .build();

        // Send the HTTP request
        Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                // Handle the response from the PHP backend
                String responseText = httpResponse.getResultAsString();
                Gdx.app.postRunnable(() -> {
                	//System.out.println("Response: " + responseText);
                    callback.onSuccess("success");
                });
            }

            @Override
            public void failed(Throwable t) {
                // Handle the failure
                Gdx.app.postRunnable(() -> {
                    //System.out.println("Request failed: " + t.getMessage());
                    callback.onFailure(t);
                });
            }

            @Override
            public void cancelled() {
                // Handle the cancellation
                Gdx.app.postRunnable(() -> {
                    //System.out.println("Request cancelled");
                    callback.onFailure(new Exception("Request cancelled"));
                });
            }
        });
    }
	
	
	public void SendChat(String tipoRequisicao, String account, String charnumber, String Message, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", charnumber);
		parameters.put("Name", playerUse.Name);
		parameters.put("Chat", Message);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						if(!responseText.equals("fail") && !responseText.equals("")) { ProcessChatList(responseText); }
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	public void SyncPlayers(String tipoRequisicao, String account, String charnumber, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", charnumber);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						// Update the game state or UI based on the response
						//System.out.println("Response: " + responseText);
						if(!responseText.equals("fail") && !responseText.equals("")) { 
							UpdateOnlinePlayers(responseText); 
						}	
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	public void SyncChats(String tipoRequisicao, String account, String charnumber, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", charnumber);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						if(!responseText.equals("fail") && !responseText.equals("")) { ProcessChatList(responseText); }
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}
	
	public void SyncMobs(String tipoRequisicao, String account, String charnumber, HttpCallback callback)
			throws UnsupportedEncodingException {

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Servername", lservername);
		parameters.put("Username", lusername);
		parameters.put("Password", lpassword);
		parameters.put("Dbname", ldbname);
		parameters.put("Request", tipoRequisicao);
		parameters.put("Dataaccount", account);
		parameters.put("Charnumber", charnumber);
		parameters.put("PlayerMapMobSync", playerUse.Map);

		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		// Create the HTTP request
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST)
				.header("Content-Type", "application/x-www-form-urlencoded").method(Net.HttpMethods.POST)
				.url("https://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						String mobs = responseText;
						UpdateOnlineMobs(mobs);
						callback.onSuccess("success");
					}
				});
			}

			@Override
			public void failed(Throwable t) {
				// Handle the failure
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request failed: " + t.getMessage());
						callback.onFailure(t);
					}
				});
			}

			@Override
			public void cancelled() {
				// Handle the cancellation
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						//System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}

}
