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
	private int FrameAtkPlayer = 0;
	private int charNumber = 0;
	private String playerAccount = "";

	// Sprite
	private Sprite spr_master;

	// Online
	private String lservername = "cityserver.mysql.uhserver.com";
	private String lusername = "citymaster";
	private String lpassword = "P@titos07";
	private String ldbname = "cityserver";

	// Online Lists
	private int ExpSharedOnline = 0;
	private ArrayList<String> lstChats;
	private String onlineResult = "";

	// Texture Atlas
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

		// list players
		lstPlayers = new ArrayList<Player>();

		// Chats
		lstChats = new ArrayList<String>();
		lstChats.add("");
		lstChats.add("");
		lstChats.add("");
		lstChats.add("");
		lstChats.add("");

		// Monster
		// placeholderMonster = new Monster();
		// lstMonsters = new ArrayList<Monster>();

		// Textures
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

		// atlas_items = new
		// TextureAtlas(Gdx.files.internal("data/assets/itens/itens/itens.txt"));
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
	// [Account]//
	// [Interface]//
	// [Character]//
	// [Monsters]//
	// [Skills]//
	// [Online]//
	// [Exp/Drop]//

	///////////////////////////////////////////////// [Account]///////////////////////////////////////////////
	public void LoadData(String data) {
		String[] playerDataArray = data.split("@");
		
		// Remove all occurrences of "@sucess@"
	    data = data.replace("#Success#", "");

		for (String playerData : playerDataArray) {
			
			String[] splitData = playerData.split(":");

			Player player = new Player();
			player.AccountID = splitData[1]; // AccountID;
			player.AccountNumber = splitData[3]; // AccountNumber;
			player.Characternumber = splitData[5]; // CharacterNumber;
			player.Name = splitData[7]; // Name;

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
			    player.PosX = splitData[45]; // posX
			    player.PosY = splitData[47]; // posY
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
			    player.pet = splitData[137]; // Pet
			    player.pethungry = splitData[139]; // PetHungry
			    player.petcare = splitData[141]; // PetCare
			    player.petTraining = splitData[143]; // PetTraining
			    player.petBath = splitData[145]; // PetBath
			    player.petLevel = splitData[147]; // PetLevel
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
		case "battlezoneA":
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

	///////////////////////////////////////////////// [Character]///////////////////////////////////////////////
	public Sprite CharSelect(Player player, String type, int num) {
		// basictopM_front1
		if (type.equals("upper")) {
			if (num == 1) {
				if (lstPlayers.get(0).SetUpper.equals("basictop")) {
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
				if (lstPlayers.get(0).SetUpper.equals("basicbottom")) {
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
				if (lstPlayers.get(1).SetUpper.equals("basicbottom")) {
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
				if (lstPlayers.get(2).SetUpper.equals("basicbottom")) {
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
				if (lstPlayers.get(0).SetUpper.equals("basicfooter")) {
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
				if (lstPlayers.get(1).SetUpper.equals("basicfooter")) {
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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
				if (lstPlayers.get(2).SetUpper.equals("basicfooter")) {
					atlas_genericset = atlas_basicset;
				}
				spr_master = atlas_genericset
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

	///////////////////////////////////////////////// [Online]///////////////////////////////////////////////
	/*
	*/
	///////////////////////////////////////////////// [Online HTML]
	///////////////////////////////////////////////// ///////////////////////////////////////////////
	public String GetResult() {
		return onlineResult;
	}

	public String GetAccount() {
		return playerAccount;
	}

	public ArrayList<Player> GetPlayers() {
		return lstPlayers;
	}

	public void CreateAccountOnline(String tipoRequisicao, HttpCallback callback) throws UnsupportedEncodingException {

		int accNumber = randnumber.nextInt(99999999);
		while (accNumber < 1000000) {
			accNumber = randnumber.nextInt(99999999);
		}

		String account = String.valueOf(accNumber);
		// Prepare the data to post
		Map<String, String> parameters = new HashMap<>();
		parameters.put("lservername", lservername);
		parameters.put("lusername", lusername);
		parameters.put("lpassword", lpassword);
		parameters.put("ldbname", ldbname);
		parameters.put("lrequest", tipoRequisicao);
		parameters.put("ldataaccount", account);

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
				.url("http://moonboltprojects.online/connect.php").content(content).build();

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
		parameters.put("lservername", lservername);
		parameters.put("lusername", lusername);
		parameters.put("lpassword", lpassword);
		parameters.put("ldbname", ldbname);
		parameters.put("lrequest", tipoRequisicao);
		parameters.put("ldataaccount", account);

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
				.url("http://moonboltprojects.online/connect.php").content(content).build();

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
						System.out.println("Response: " + responseText);
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
						System.out.println("Request failed: " + t.getMessage());
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
						System.out.println("Request cancelled");
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
		parameters.put("lservername", lservername);
		parameters.put("lusername", lusername);
		parameters.put("lpassword", lpassword);
		parameters.put("ldbname", ldbname);
		parameters.put("lrequest", tipoRequisicao);
		parameters.put("ldataaccount", account);
		parameters.put("lcharnumber", charnumber);

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
				.url("http://moonboltprojects.online/connect.php").content(content).build();

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
						System.out.println("Response: " + responseText);
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
						System.out.println("Request failed: " + t.getMessage());
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
						System.out.println("Request cancelled");
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
		parameters.put("lservername", lservername);
		parameters.put("lusername", lusername);
		parameters.put("lpassword", lpassword);
		parameters.put("ldbname", ldbname);
		parameters.put("lrequest", tipoRequisicao);
		parameters.put("ldataaccount", account);
		parameters.put("lcharnumber", accountnumber);

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
				.url("http://moonboltprojects.online/connect.php").content(content).build();

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
						System.out.println("Response: " + responseText);
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
						System.out.println("Request failed: " + t.getMessage());
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
						System.out.println("Request cancelled");
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
		parameters.put("lservername", lservername);
		parameters.put("lusername", lusername);
		parameters.put("lpassword", lpassword);
		parameters.put("ldbname", ldbname);
		parameters.put("lrequest", tipoRequisicao);
		parameters.put("ldataaccount", accountnumber);
		parameters.put("lcharnumber", charnumber);

		parameters.put("lname", name);
		parameters.put("lsex", sex);
		parameters.put("lhair", hair);
		parameters.put("lcolor", color);
		parameters.put("litensList", itensList);

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
				.url("http://moonboltprojects.online/connect.php").content(content).build();

		// Send the HTTP request
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				// Handle the response from the PHP backend
				String responseText = httpResponse.getResultAsString();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						System.out.println("Response: " + responseText);
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
						System.out.println("Request failed: " + t.getMessage());
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
						System.out.println("Request cancelled");
						callback.onFailure(new Exception("Request cancelled"));
					}
				});
			}
		});
	}

}