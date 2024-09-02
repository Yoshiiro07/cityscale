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
import com.moonbolt.cityscale.models.Monster;
import com.moonbolt.cityscale.models.Player;
import com.badlogic.gdx.files.FileHandle;

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
		String[] splitData = data.split(":");

		Player player = new Player();
		player.AccountID = splitData[2]; // AccountID;
		player.AccountNumber = splitData[4]; // AccountNumber;
		player.Characternumber = splitData[6]; // CharacterNumber;
		player.Name = splitData[8]; // Name;

		if (!splitData[12].equals("")) {
			player.Sex = splitData[10]; // Sex
			player.Hair = splitData[12]; // Hair
			player.Color = splitData[14]; // Color
			player.Hat = splitData[16]; // Hat
			player.Job = splitData[18]; // Job
			player.SetUpper = splitData[20]; // SetUpper
			player.SetBottom = splitData[22]; // SetBottom
			player.SetFooter = splitData[24]; // SetFooter
			player.Level = splitData[26]; // Level
			player.Exp = splitData[28]; // Exp
			player.Map = splitData[30]; // Map
			player.Hp = splitData[32]; // Hp
			player.Mp = splitData[34]; // Mp
			player.Money = splitData[36]; // Money
			player.HpMax = splitData[38]; // HpMax
			player.MpMax = splitData[40]; // MpMax
			player.regenTime = splitData[42]; // regenTime
			player.regenTimeMax = splitData[44]; // regenTimeMax
			player.PosX = splitData[46]; // posX
			player.PosY = splitData[48]; // posY
			player.Walk = splitData[50]; // Walk
			player.Frame = splitData[52]; // Frame
			player.countFrame = splitData[54]; // countFrame
			player.breakwalk = splitData[56]; // breakwalk
			player.Target = splitData[58]; // Target
			player.AtkTimer = splitData[60]; // AtkTimer
			player.AtkTimerMax = splitData[62]; // AtkTimerMax
			player.Casting = splitData[64]; // CastTimer
			player.Atk = splitData[66]; // Atk
			player.Def = splitData[68]; // Def
			player.Evasion = splitData[70]; // Evasion
			player.Side = splitData[72]; // Side
			player.Weapon = splitData[74]; // Weapon
			player.Crystal1 = splitData[76]; // Crystal1
			player.Crystal2 = splitData[78]; // Crystal2
			player.Crystal3 = splitData[80]; // Crystal3
			player.Crystal4 = splitData[82]; // Crystal4
			player.StatusPoint = splitData[84]; // StatusPoints
			player.Str = splitData[86]; // Str
			player.Agi = splitData[88]; // Agi
			player.Vit = splitData[90]; // Vit
			player.Dex = splitData[92]; // Dex
			player.Wis = splitData[94]; // Wis
			player.Luk = splitData[96]; // Luk
			player.Res = splitData[98]; // Res
			player.Stamina = splitData[100]; // Stamina
			player.StaminaMax = splitData[102]; // StaminaMax
			player.Itens = splitData[104]; // Itens
			player.Quests = splitData[106]; // Quests
			player.hotkey1 = splitData[108]; // Hotkey1
			player.hotkey2 = splitData[110]; // Hotkey2
			player.buffA = splitData[112]; // BuffA
			player.buffB = splitData[114]; // BuffB
			player.buffC = splitData[116]; // BuffC
			player.BuffTimeA = splitData[118]; // BuffTimeA
			player.BuffTimeB = splitData[120]; // BuffTimeB
			player.BuffTimeC = splitData[122]; // BuffTimeC
			player.party = splitData[124]; // Party
			player.playerInBattle = splitData[126]; // PlayerInBattle
			player.playerInAttack = splitData[128]; // PlayerInAttack
			player.playerInCast = splitData[130]; // PlayerInCast
			player.playerSit = splitData[132]; // PlayerSit
			player.SyncPlayerMob = splitData[134]; // SyncPlayerMob
			player.PlayerExpGet = splitData[136]; // PlayerExpGet
			player.pet = splitData[138]; // Pet
			player.pethungry = splitData[140]; // PetHungry
			player.petcare = splitData[142]; // PetCare
			player.petTraining = splitData[144]; // PetTraining
			player.petBath = splitData[146]; // PetBath
			player.petLevel = splitData[148]; // PetLevel

		}

		lstPlayers.add(player);
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
	public String GetResult() {
		return onlineResult;
	}

	public String GetAccount() {
		return playerAccount;
	}

	public ArrayList<Player> GetPlayers() {
		return lstPlayers;
	}

	public void CreateAccountOnline() throws UnsupportedEncodingException {

		int accNumber = randnumber.nextInt(99999999);
		while (accNumber < 1000000) {
			accNumber = randnumber.nextInt(99999999);
		}

		String account = String.valueOf(accNumber);

		final CountDownLatch latch = new CountDownLatch(1);

		Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
		request.setUrl("http://moonboltprojects.online/serverdeless/index.php");
		request.setContent("application/x-www-form-urlencoded");

		// Prepare the data to post
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("lservername", lservername);
		parameters.put("lusername", lusername);
		parameters.put("lpassword", lpassword);
		parameters.put("ldbname", ldbname);
		parameters.put("lrequest", "NewAccount");
		parameters.put("ldataaccount", account);

		// Convert the parameters into URL encoded form
		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		request.setContent(content);

		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String response = httpResponse.getResultAsString();
				// process the response
				System.out.println(response);
				onlineResult = "success";
				playerAccount = account;
				latch.countDown();
			}

			public void failed(Throwable t) {
				String message = "Request failed";
				System.out.println(message);
				onlineResult = "fail";
				latch.countDown();
			}

			@Override
			public void cancelled() {
				String message = "Request cancelled";
				System.out.println(message);
				onlineResult = "cancel";
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void GetAccountOnline(String tipoRequisicao, String account, String accountnumber)
			throws UnsupportedEncodingException {
		final CountDownLatch latch = new CountDownLatch(1);

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
		parameters.put("ldataaccount", account);
		parameters.put("lcharnumber", accountnumber);

		// Convert the parameters into URL encoded form
		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		request.setContent(content);

		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String response = httpResponse.getResultAsString();
				// process the response
				System.out.println(response);
				onlineResult = "success";
				LoadData(response);
				latch.countDown();
			}

			public void failed(Throwable t) {
				String message = "Request failed";
				System.out.println(message);
				onlineResult = "fail";
				latch.countDown();
			}

			@Override
			public void cancelled() {
				String message = "Request cancelled";
				System.out.println(message);
				onlineResult = "cancel";
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void CreateCharOnline(String tipoRequisicao, String accountnumber, String charnumber, String name,
			String sex, String hair, String color) throws UnsupportedEncodingException {
		final CountDownLatch latch = new CountDownLatch(1);

		String itensList = "";
		for (int i = 0; i < 16; i++) {
			if (i == 0) {
				itensList = itensList + "[hpcan#20]-";
			}
			if (i > 0) {
				itensList = itensList + "[NONE]-";
			}
		}

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
		parameters.put("ldataaccount", accountnumber);
		parameters.put("lcharnumber", charnumber);

		parameters.put("lname", name);
		parameters.put("lsex", sex);
		parameters.put("lhair", hair);
		parameters.put("lcolor", color);
		parameters.put("litensList", itensList);

		// Convert the parameters into URL encoded form
		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		request.setContent(content);

		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String response = httpResponse.getResultAsString();
				// process the response
				System.out.println(response);
				onlineResult = "success";
				latch.countDown();
			}

			public void failed(Throwable t) {
				String message = "Request failed";
				System.out.println(message);
				onlineResult = "fail";
				latch.countDown();
			}

			@Override
			public void cancelled() {
				String message = "Request cancelled";
				System.out.println(message);
				onlineResult = "cancel";
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void CheckAccount(String tipoRequisicao, String account)
			throws UnsupportedEncodingException {
		final CountDownLatch latch = new CountDownLatch(1);

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
		parameters.put("ldataaccount", account);
		
		// Convert the parameters into URL encoded form
		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		request.setContent(content);

		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String response = httpResponse.getResultAsString();
				// process the response
				System.out.println(response);
				onlineResult = "success";
				latch.countDown();
			}

			public void failed(Throwable t) {
				String message = "Request failed";
				System.out.println(message);
				onlineResult = "fail";
				latch.countDown();
			}

			@Override
			public void cancelled() {
				String message = "Request cancelled";
				System.out.println(message);
				onlineResult = "cancel";
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void DeleteChar(String tipoRequisicao, String account, String accountnumber)
			throws UnsupportedEncodingException {
		final CountDownLatch latch = new CountDownLatch(1);

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
		parameters.put("ldataaccount", account);
		parameters.put("lcharnumber", accountnumber);
		
		// Convert the parameters into URL encoded form
		String content = "";
		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			if (content.length() > 0) {
				content += "&";
			}
			content += URLEncoder.encode(parameter.getKey(), "UTF-8") + "="
					+ URLEncoder.encode(parameter.getValue(), "UTF-8");
		}

		request.setContent(content);

		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String response = httpResponse.getResultAsString();
				// process the response
				System.out.println(response);
				onlineResult = "success";
				latch.countDown();
			}

			public void failed(Throwable t) {
				String message = "Request failed";
				System.out.println(message);
				onlineResult = "fail";
				latch.countDown();
			}

			@Override
			public void cancelled() {
				String message = "Request cancelled";
				System.out.println(message);
				onlineResult = "cancel";
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
}