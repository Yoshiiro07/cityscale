package com.moonbolt.cityscale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moonbolt.cityscale.models.Damage;
import com.moonbolt.cityscale.models.Monster;
import com.moonbolt.cityscale.models.Player;
import com.moonbolt.cityscale.models.Skill;


public class GameMapHTML implements Screen, ApplicationListener, InputProcessor, TextInputListener {

		//Objects
	    private MainGame game;
	    private ManagerScreen screen;
	    private GameControlHTML gameControlHTML;
	    private String state = "Main";
	    private String account = "";
	    private int playernumber = 0;
	    private Sprite spr_master;
		private Random randnumber;
		private boolean network = false;
		private String shopname = "";
	    private String menuoption = "";
	    private int playernum = 0;
	    private String controlstate = "Mobile";
	    private int flipzone = 0;

		//Fonts
		private BitmapFont font_master;

	    //Camera
	    private OrthographicCamera camera;
	    private Viewport viewport;
	    private float cameraCoordsX = 0;
	    private float cameraCoordsY = 0;

	    //Player
	    public Player player;
	    private float playerPosX;
	    private float playerPosY;
	    private Sprite spr_playerTagHair;
	    private Sprite spr_playerhatmenu;
	    private Sprite spr_playerhat;
	    private Sprite spr_playerhair;
	    private Sprite spr_playertop;
	    private Sprite spr_playerbottom;
	    private Sprite spr_playerfooter;
	    private Sprite spr_playerweapon;
	    private boolean playerDead = false;
	    private boolean movement = false;
		private boolean autoattack = false;
		private Sprite spr_target;
		private int countDead = 100;
		private String itemEquipped = "";
		private Sprite spr_item;
		private String msgShowMenu = "Atualizado com sucesso";
		private int msgShowTime = 0;
		private int hotketcountitem1;
		private int hotketcountitem2;
		private int skillUsed = 0;
		private boolean notmp = false;
		private boolean rangedAttack = false;
	    private boolean skillEffect = false;
	    private String skillname = "";
	    private float touchSkillX;
	    private float touchSkillY;
	    private boolean selectAreaRanged = false;
	    private Sprite spr_sit;
	    private int regenTime = 0;

		//Monster
		private ArrayList<Monster> listMonsters;
		private Sprite spr_monster;
		private int mobFrame = 1;
	    private float mobPositionCoordX = 0;
	    private float mobPositionCoordY = 0;
	    private int mobRandomSt = 0;
	    private int mobTimerMov = 100;
	    private int mobTimerFrame = 40;
		private int countSwitchTarget;

		//Misc
		private ArrayList<Damage> listDamage;
		private ArrayList<Skill> listSkills;
		private ArrayList<String> lstChats;
		private String itemdropname;
		private int SysMsgCount = 0;
	    private int savedataTime = 500;
		private int showDropMsg;
		private Sprite spr_shop;
		private String showbuymsg = "";
		private int showbuymsgtime = 2000;
		private boolean showZone = false;
	    private int countZoneSkill = 0;

		//Online
		private ArrayList<Player> lstOnlinePlayers;
		private Sprite spr_playerTopOnline;
	    private Sprite spr_playerBottomOnline;
	    private Sprite spr_playerFooterOnline;
	    private Sprite spr_playerHairOnline;
	    private Sprite spr_playerHatOnline;
	    private Sprite spr_weaponOnline;
	    private boolean onlineAuth = false;
	    private String onlineresponse = "";
	    private boolean SyncStart = false;


	    //Sprite NPC
	    private Sprite spr_npc;

	    //UX
	    private float padmoveX = -56;
	    private float padmoveY = -50;
	    private Sprite spr_playerTag;

	    //Sprites Background
	    private Sprite spr_Background;
	    private Texture tex_Background;
	    private Texture tex_BackgroundOver;

	    //Teste Dot
	    private Sprite spr_testeDot;
	    private Texture tex_testeDot;

	    //Controller
	    private final IntSet downKeys = new IntSet(20);

		public GameMapHTML(MainGame gameAlt,ManagerScreen screenAlt, boolean networkAlt,String accountAlt, int playernumberAlt) {
			this.game = gameAlt;
			this.screen = screenAlt;
			this.randnumber = new Random();
			this.network = networkAlt;
			this.account = accountAlt;
			this.playernum = playernumberAlt;

			//Load Player Data
			this.gameControlHTML = new GameControlHTML();
			//player = gameControlHTML.LoadData();
			gameControlHTML.SetCharNumber(playernum);

			if(player.Map.equals("StreetsA")) { tex_Background = new Texture(Gdx.files.internal("data/assets/maps/streetsA.png")); }
			if(player.Map.equals("Sewers")) {
				tex_Background = new Texture(Gdx.files.internal("data/assets/maps/sewers.png"));
				tex_BackgroundOver = new Texture(Gdx.files.internal("data/assets/maps/sewersB.png"));
			}
			spr_Background = new Sprite(tex_Background);

			//Mobs
			if(player.Map.equals("Sewers")) { listMonsters = gameControlHTML.LoadMonsters("Sewers"); }

			//Damage/skill Stance
			listDamage = new ArrayList<Damage>();
			listSkills = new ArrayList<Skill>();

			//Camera and Inputs
			camera = new OrthographicCamera();
		    viewport = new StretchViewport(195,195,camera);
			viewport.apply();
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
			Gdx.input.setInputProcessor(this);

			//Etc
			lstChats = new ArrayList<String>();
			lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add(""); lstChats.add("");

			//Network
			lstOnlinePlayers = new ArrayList<Player>();

			//font
			font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.07f,0.11f);
			font_master.setUseIntegerPositions(false);

			//test dot
			tex_testeDot = new Texture(Gdx.files.internal("data/assets/etc/testdot.png"));
			spr_testeDot = new Sprite(tex_testeDot);
		}

		@Override
		public void render(float delta) {
			//try {
				//Just for coloring
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

				//Camera Ajustments
				cameraCoordsX = player.PosX;
				cameraCoordsY = player.PosY;

				//Follow camera
				if(player.PosX <= 18.5f) 	{ cameraCoordsX = 18.5f; 	 }
				if(player.PosX >= 93) 	{ cameraCoordsX = 93; 	 }
				if(player.PosY >= -22f) { cameraCoordsY = -22f; }
				if(player.PosY <= -97) 	{ cameraCoordsY = -97;  }

				//Update camera and start drawling
				camera.position.set(cameraCoordsX -2,cameraCoordsY+1,0);
				camera.update();
			    game.batch.setProjectionMatrix(camera.combined);
				game.batch.begin();

				//Save
				savedataTime--;
				if(savedataTime < 0) {
					savedataTime = 500;
					gameControlHTML.SetSave(playernum);
					//gameControlHTML.SaveData(player);
				}

				//Check Regen
				player.regenTime--;
				if(player.regenTime < 0) {
					player.Hp = player.Hp + 10;
					player.Mp = player.Mp + 10;

					if(player.Hp >= player.HpMax) { player.Hp = player.HpMax; }
					if(player.Mp >= player.MpMax) { player.Mp = player.MpMax; }

					player.regenTime = player.regenTimeMax;
				}

				if(player.Map.equals("StreetsA")) {
					spr_Background.setPosition(-81,-194);
					spr_Background.setSize(270, 270);
					spr_Background.draw(game.batch);
				}

				//Background
				if(player.Map.equals("Sewers")) {
					flipzone++;
					if (flipzone >= 0 && flipzone <= 20) {
						spr_Background.setPosition(-81, -194);
						spr_Background.setSize(270, 270);
						spr_Background.draw(game.batch);
					}
					if (flipzone >= 20 && flipzone <= 40) {
						spr_Background.setPosition(-81, -194);
						spr_Background.setSize(270, 270);
						spr_Background.draw(game.batch);
					}
					if (flipzone >= 40) { flipzone = 0; }
				}

				//npcs
				ShowOnlinePlayers();
				ShowNPCs();

				if(network) {
					if(!onlineAuth) {
						onlineresponse = gameControlHTML.OnlineManager("CheckVersion","","");
						if(onlineresponse.equals("Autorizado")) {
							onlineAuth = true;
						}
					}
					else {
						if(!SyncStart) {
							gameControlHTML.OnlineManager("SyncChats","","");
							gameControlHTML.OnlineManager("SyncPlayer","","");
							SyncStart = true;
						}
					}
				}

				//Char
				player = gameControlHTML.SetCharMov(player, player.breakwalk);

				spr_playerfooter = gameControlHTML.GetFooterChar(player, "no",0,0);
				spr_playerfooter.draw(game.batch);

				spr_playerbottom = gameControlHTML.GetBottomChar(player, "no",0,0);
				spr_playerbottom.draw(game.batch);

				spr_playertop = gameControlHTML.GetTopChar(player, "no", 0,0);
				spr_playertop.draw(game.batch);

				spr_playerhair = gameControlHTML.GetHairChar(player, "no",0,0);
				spr_playerhair.draw(game.batch);

				if(!player.Hat.equals("none")) {
					spr_playerhat = gameControlHTML.GetHatChar(player,"",0,0);
					spr_playerhat.draw(game.batch);
				}


				if(player.playerInBattle.equals("yes") || player.playerInBattle.equals("yes") || player.playerInBattle.equals("yes")) {
					spr_playerweapon = gameControlHTML.SetWeapon(player);
					spr_playerweapon.draw(game.batch);
				}

				//Show Mobs
				if(player.Map.equals("Sewers")){ ShowMobs(); }

				//UX
				spr_playerTag = gameControlHTML.GetUX("playertag",cameraCoordsX, cameraCoordsY);
				spr_playerTag.draw(game.batch);

				spr_playerTagHair = gameControlHTML.GetHairCharTag(player, cameraCoordsX, cameraCoordsY);
				spr_playerTagHair.draw(game.batch);


				font_master.getData().setScale(0.15f,0.26f);
				font_master.draw(game.batch, "X:" + player.PosX, cameraCoordsX - 98f, cameraCoordsY + 53.7f);
				font_master.draw(game.batch, "Y:" + player.PosY, cameraCoordsX - 78f, cameraCoordsY + 53.7f);

				spr_sit = gameControlHTML.GetUX("btnsit", cameraCoordsX, cameraCoordsY);
				spr_sit.draw(game.batch);


				font_master.draw(game.batch, player.Name, cameraCoordsX - 88f, cameraCoordsY + 93.7f);
				font_master.draw(game.batch, String.valueOf(player.Hp + "/" + player.HpMax), cameraCoordsX - 85f, cameraCoordsY + 82f);
				font_master.draw(game.batch, String.valueOf(player.Mp + "/" + player.MpMax), cameraCoordsX - 85f, cameraCoordsY + 73.7f);
				font_master.draw(game.batch, String.valueOf(player.Level), cameraCoordsX - 88f, cameraCoordsY + 64f);
				font_master.draw(game.batch, gameControlHTML.ExpPercent() + "%", cameraCoordsX - 72f, cameraCoordsY + 64f);

				if(!controlstate.equals("PC")) {
				spr_master = gameControlHTML.GetUX("innerpad", cameraCoordsX, cameraCoordsY);
				spr_master.setPosition(cameraCoordsX + padmoveX,cameraCoordsY + padmoveY);
				spr_master.draw(game.batch);
				}

				//Ranged Skill
				if(selectAreaRanged) {
					spr_master = gameControlHTML.GetUX("textbar", 0, 0);
					spr_master.setPosition(cameraCoordsX - 33, cameraCoordsY + 35);
					spr_master.setSize(60, 15);
					spr_master.draw(game.batch);
					font_master.draw(game.batch, "Selecione a Area",cameraCoordsX - 20, cameraCoordsY + 46);
				}
				if(showZone) {
					if(countZoneSkill > 0) {
						spr_master = gameControlHTML.GetUX("target", 0, 0);
						spr_master.setPosition(touchSkillX -5, touchSkillY - 8);
						spr_master.setSize(10,20);
						spr_master.draw(game.batch);
						countZoneSkill--;
					}
					else {
						showZone = false;
						countZoneSkill = 0;
					}
				}


				try {
				if(network) { } //stChats = gameControlHTML.GetChatList(); }
				font_master.draw(game.batch, "Chats:", cameraCoordsX - 40f, cameraCoordsY - 40f);
				if(lstChats.size() > 4) {
					for(int i = 0; i < lstChats.size(); i++) {
						if(i == 0) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 50f); }
						if(i == 1) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 60f); }
						if(i == 2) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 70f); }
						if(i == 3) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 80f); }
						if(i == 4) { font_master.draw(game.batch, lstChats.get(i), cameraCoordsX - 36f, cameraCoordsY - 90f); }
					}
				}
				}

				catch(Exception ex) {}

				if(network) {
					font_master.draw(game.batch, "Online Ativo", cameraCoordsX - 18, cameraCoordsY + 96);
				}

				//Checks e Cards
				ShowCards();
				CheckColision();
				CheckAutoAttack();
				CheckMobAutoAttack();
				CheckMobDeadRespawn();
				ShowDamage();
				ShowSkill();

				if(playerDead) { ShowPlayerDead(); }

				//Item Drop
				if(showDropMsg > 0) {
					spr_master = gameControlHTML.GetUX("textbar", cameraCoordsX, cameraCoordsY);
					spr_master.setPosition(cameraCoordsX - 40, cameraCoordsY + 29);
					spr_master.setSize(80, 20);
					spr_master.draw(game.batch);
					font_master.draw(game.batch, itemdropname, cameraCoordsX - 38, cameraCoordsY + 42);
					showDropMsg--;
				}

				if(state.equals("Menu")){
					spr_master = gameControlHTML.GetUX("menu", cameraCoordsX, cameraCoordsY);
					spr_master.draw(game.batch);
					//Status
					font_master.draw(game.batch, String.valueOf(player.Str), cameraCoordsX - 76f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Vit), cameraCoordsX - 62f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Agi), cameraCoordsX - 48f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Wis), cameraCoordsX - 34f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.Dex), cameraCoordsX - 21f, cameraCoordsY - 50f);
					font_master.draw(game.batch, String.valueOf(player.StatusPoint), cameraCoordsX - 43f, cameraCoordsY - 67f);

					font_master.draw(game.batch,"Dinheiro:" + String.valueOf(player.Money),cameraCoordsX - 10f, cameraCoordsY - 67f);
					font_master.draw(game.batch,"Exp:" + String.valueOf(player.Exp),cameraCoordsX + 20f, cameraCoordsY - 67f);

					//CharacterShow
					spr_playerhair = gameControlHTML.GetHairChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerhair.draw(game.batch);

					spr_playerfooter = gameControlHTML.GetFooterChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerfooter.draw(game.batch);

					spr_playerbottom = gameControlHTML.GetBottomChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playerbottom.draw(game.batch);

					spr_playertop = gameControlHTML.GetTopChar(player, "Show", cameraCoordsX, cameraCoordsY);
					spr_playertop.draw(game.batch);

					if(!player.Hat.equals("none")) {
						spr_playerhatmenu = gameControlHTML.GetHatChar(player,"",cameraCoordsX, cameraCoordsY);
						spr_playerhatmenu.setScale(0.5f, 0.7f);
						spr_playerhatmenu.setPosition(cameraCoordsX - 84, cameraCoordsY + 42);
						spr_playerhatmenu.draw(game.batch);
					}


					//Show Character
					//Itens Equipped
					spr_playerfooter = gameControlHTML.GetItem(player.SetFooter);
					spr_playerfooter.setPosition(cameraCoordsX + 13, cameraCoordsY + 41);
					spr_playerfooter.setSize(13, 22);
					spr_playerfooter.draw(game.batch);

					spr_playerbottom = gameControlHTML.GetItem(player.SetBottom);
					spr_playerbottom.setPosition(cameraCoordsX + 27, cameraCoordsY + 41);
					spr_playerbottom.setSize(13, 22);
					spr_playerbottom.draw(game.batch);

					spr_playertop = gameControlHTML.GetItem(player.SetUpper);
					spr_playertop.setPosition(cameraCoordsX + 41, cameraCoordsY + 41);
					spr_playertop.setSize(13, 22);
					spr_playertop.draw(game.batch);

					spr_playerweapon = gameControlHTML.GetItem(player.Weapon);
					spr_playerweapon.setPosition(cameraCoordsX + 54.4f, cameraCoordsY + 41);
					spr_playerweapon.setSize(13, 22);
					spr_playerweapon.draw(game.batch);

					if(!player.Hat.equals("none")) {
						spr_playerhat = gameControlHTML.GetHatItem(player,0,0);
						spr_playerhat.setPosition(cameraCoordsX + 67.8f, cameraCoordsY + 41);
						spr_playerhat.setSize(13, 22);
						spr_playerhat.draw(game.batch);
					}

					ShowBag();
					if(msgShowTime > 0) {
						msgShowTime--;
						font_master.draw(game.batch, msgShowMenu, cameraCoordsX + 14, cameraCoordsY - 38f);
						if(msgShowTime < 0) {
							msgShowTime = 0;
						}
					}

					if(menuoption.equals("hotkey1")) { spr_master = gameControlHTML.GetUX("hotkey1",cameraCoordsX + 16, cameraCoordsY - 23); spr_master.draw(game.batch); }
					if(menuoption.equals("hotkey2")) { spr_master = gameControlHTML.GetUX("hotkey1",cameraCoordsX + 36, cameraCoordsY - 23); spr_master.draw(game.batch); }
					if(menuoption.equals("descartar")) {
						spr_master = gameControlHTML.GetUX("descartar",cameraCoordsX, cameraCoordsY);
						spr_master.setPosition(cameraCoordsX - 12, cameraCoordsY - 63);
						spr_master.draw(game.batch);
					}

					if (controlstate.equals("PC")) {
						spr_master = gameControlHTML.GetUX("controlPC", cameraCoordsX, cameraCoordsY);
						spr_master.setPosition(cameraCoordsX + 14, cameraCoordsY - 34);
						spr_master.setSize(12, 22);
						spr_master.draw(game.batch);
					}


				}

				if(state.equals("Shop")) {

					if(shopname.equals("refrishop")) {
						spr_shop = gameControlHTML.GetShops("refrishop",cameraCoordsX, cameraCoordsY);
						spr_shop.draw(game.batch);
						font_master.draw(game.batch, String.valueOf(player.Money), cameraCoordsX - 25, cameraCoordsY - 37);
					}

					if(!showbuymsg.equals("")) {
						showbuymsgtime--;
						font_master.draw(game.batch, showbuymsg, cameraCoordsX + 15, cameraCoordsY - 37);
						if(showbuymsgtime <= 0) {
							showbuymsg = "";
							showbuymsgtime = 2000;
						}
					}
				}

				if(state.equals("DungeonSelect")) {
					spr_master = gameControlHTML.GetUX("battlezoneA", cameraCoordsX, cameraCoordsY);
					spr_master.draw(game.batch);
				}

				spr_testeDot.setPosition(cameraCoordsX + 68,cameraCoordsY + 62);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				spr_testeDot.setPosition(cameraCoordsX + 80,cameraCoordsY + 40);
				spr_testeDot.setSize(1, 1);
				spr_testeDot.draw(game.batch);

				game.batch.end();
			}

			//catch(Exception ex) {
			//	Gdx.app.exit();
			//}

		public void ShowOnlinePlayers() {
			try {
			//lstOnlinePlayers = gameControlHTML.GetListOnlinePlayers();

				if(lstOnlinePlayers.size() > 0) {
					for(int i = 0; i < lstOnlinePlayers.size(); i++) {
						spr_playerhair = gameControlHTML.GetHairChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerhair.draw(game.batch);

						spr_playerfooter = gameControlHTML.GetFooterChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerfooter.draw(game.batch);

						spr_playerbottom = gameControlHTML.GetBottomChar(lstOnlinePlayers.get(i), "no",0,0);
						spr_playerbottom.draw(game.batch);

						spr_playertop = gameControlHTML.GetTopChar(lstOnlinePlayers.get(i), "no", 0,0);
						spr_playertop.draw(game.batch);
					}
				}
			}

			catch(Exception ex) {}
		}

		public void CheckAutoAttack() {

			if(player.Map.equals("Sewers") && autoattack) {
				for(int i = 0; i < listMonsters.size(); i++) {

					if(player.Target.equals(listMonsters.get(i).MobID)) {

						if((listMonsters.get(i).MobPosX + 5) > (player.PosX - 5) && (listMonsters.get(i).MobPosX + 5) < (player.PosX + 15)
						   && (listMonsters.get(i).MobPosY + 7) > (player.PosY - 7) && (listMonsters.get(i).MobPosY + 5) < (player.PosY + 18)) {
							player.playerInBattle = "yes";
							player.AtkTimer--;

							if(player.AtkTimer <= 0) {
								int atkweapon = CheckWeapon();
								int mobhp = listMonsters.get(i).MobHp; //CheckDamageDifer(listMonsters.get(i).MobHpMax, 1);
								int damagehit = player.Atk + atkweapon + player.Str;
								player.playerInAttack = "yes";
								player.AtkTimer = player.AtkTimerMax;
								listMonsters.get(i).MobTarget = player.Name;
								gameControlHTML.SetAttackFrame();

								if(CheckMobEvade()) {
									Damage damage = new Damage();
									damage.DamagePosX = listMonsters.get(i).MobPosX;
									damage.DamagePosY = listMonsters.get(i).MobPosY;
									damage.DamageTime = 100;
									damage.DamageType = "mob";
									damage.DamageValue = 0;
									listDamage.add(damage);
									return;
								}

								int st = player.Stamina;
								if(st > 0) { mobhp = mobhp - damagehit;  } else {  mobhp = mobhp - 5; }
								if(mobhp < 0) { mobhp = 0; }
								listMonsters.get(i).MobHp = mobhp;

								if(listMonsters.get(i).MobHp <= 0) {
								    if(network) {
								    	onlineresponse = gameControlHTML.OnlineManager("ExpSharedSend",String.valueOf(listMonsters.get(i).MobExp),"");
								    	MobDead(i);
								    }
								    else {
								    	MobDead(i);
								    }

								    return;
								}

								Damage damage = new Damage();
								damage.DamagePosX = listMonsters.get(i).MobPosX;
								damage.DamagePosY = listMonsters.get(i).MobPosY;
								damage.DamageTime = 100;
								damage.DamageType = "mob";
								damage.DamageValue = damagehit;
								listDamage.add(damage);
							}
						}
						else {
							player.playerInBattle = "no";
						}
					}
				}
			}
		}

		public void CheckMobAutoAttack() {
				if(player.Map.equals("Sewers")) {
					for(int i = 0; i < listMonsters.size(); i++) {
						if(listMonsters.get(i).MobTarget.equals(player.Name)) {
							if(player.PosX > (listMonsters.get(i).MobPosX - 5) && player.PosX < (listMonsters.get(i).MobPosX + 15)
								&& player.PosY > (listMonsters.get(i).MobPosY - 7) && player.PosY < (listMonsters.get(i).MobPosY + 18)) {

									listMonsters.get(i).MobAtkTimer--;
									if(listMonsters.get(i).MobAtkTimer <= 0) {
										int mobluck = randnumber.nextInt(100);
										if(mobluck > 5 && mobluck < 20) {
											player.Hp = player.Hp - ((listMonsters.get(i).MobAtk * 2) - player.Def);
										}
										if(mobluck >= 0 && mobluck < 5) {
											player.Hp = player.Hp - ((listMonsters.get(i).MobAtk * 3) - player.Def);
										}
										if(mobluck > 10) {
										{
											player.Hp = player.Hp - (listMonsters.get(i).MobAtk - player.Def);
										}
										listMonsters.get(i).MobAtkTimer = listMonsters.get(i).MobAtkTimerMax;
										Damage damage = new Damage();
										damage.DamagePosX = listMonsters.get(i).MobPosX;
										damage.DamagePosY = listMonsters.get(i).MobPosY;
										damage.DamageTime = 100;
										damage.DamageType = "player";
										damage.DamageValue = listMonsters.get(i).MobAtk;
										listDamage.add(damage);
									}
									if(player.Hp <= 0) {
										playerDead = true;
									}
							}
						}
					}
				}
			}
		}

		public void MobDead(int mobindex) {
			player.Target = "none";
			player.AtkTimer = player.AtkTimerMax;
			player.playerInBattle = "no";
		    player.playerInAttack = "no";
		    player.playerInCast = "no";
		    autoattack = false;

		    showDropMsg = 100;
		    itemdropname = gameControlHTML.ItemDrop(listMonsters.get(mobindex).MobName);
		    gameControlHTML.GiveExp(listMonsters.get(mobindex).MobExp);
		    if(player.Money > 1500) { return; }
		    player.Money = player.Money + 2;

		}

		public void MapChange(String map) {
			if(map.equals("Sewers")) {
				player.Map = "Sewers";
				player.PosX = 44.5f;
				player.PosY = -4.5f;
				//gameControlHTML.SaveData(player);
				this.screen.screenSwitch("LoadingScreen",network,account,playernumber);
				dispose();
			}
			if(map.equals("StreetsAFromSewers")) {
				player.Map = "StreetsA";
				player.PosX = 112.5f;
				player.PosY = -142f;
				//gameControlHTML.SaveData(player);
				this.screen.screenSwitch("LoadingScreen",network,account,playernumber);
				dispose();
			}
		}

				public void ShowBag() {
				//Common Itens
				for (int i = 0; i < 16; i++) {
					spr_item = ShowItem(i);
					if(spr_item != null) {
						spr_item.draw(game.batch);
					}
				}
				for (int i = 0; i < 16; i++) {
					spr_item = ShowItem(i);
					if(spr_item != null) {
						font_master.draw(game.batch, ShowQuantityItem(i), spr_item.getX() + 9,spr_item.getY() + 7);
					}
				}

				//Crystal Itens
				//slot 1
				if(!player.Crystal1.equals("none")) {
					spr_item = gameControlHTML.GetItem(player.Crystal1);
					spr_item.setPosition(1.5f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch);
				}

				if(!player.Crystal2.equals("none")) {
					spr_item = gameControlHTML.GetItem(player.Crystal1);
					spr_item.setPosition(10.5f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch);
				}

				//slot 3
				if(!player.Crystal3.equals("none")) {
					spr_item = gameControlHTML.GetItem(player.Crystal1);
					spr_item.setPosition(19.5f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch);
				}

				//slot 4
				if(!player.Crystal4.equals("none")) {
					spr_item = gameControlHTML.GetItem(player.Crystal1);
					spr_item.setPosition(29f, 25);
					spr_item.setSize(9, 14);
					//spr_item.draw(game.batch);
				}
		}

		public void CheckStatus(String status) {
			if(player.StatusPoint >= 1) {
				if(status.equals("Str")) {
					player.Str = player.Str + 1;
					player.StatusPoint = player.StatusPoint - 1;
					player.Atk = player.Atk + 2;
				}
				if(status.equals("Vit")) {
					player.Vit = player.Vit + 1;
					player.StatusPoint = player.StatusPoint - 1;
					player.Def = player.Def + 2;
					player.HpMax = player.HpMax + 5;
				}
				if(status.equals("Agi")) {
					player.Agi = player.Agi + 1;
					player.StatusPoint = player.StatusPoint - 1;
					player.Def = player.Def + 1;
					player.AtkTimerMax = player.AtkTimerMax - 1;
				}
				if(status.equals("Wis")) {
					player.Wis = player.Wis + 1;
					player.StatusPoint = player.StatusPoint - 1;
					player.Def = player.Def + 1;
					player.MpMax = player.MpMax + 5;
				}
				if(status.equals("Dex")) {
					player.Dex = player.Dex + 1;
					player.StatusPoint = player.StatusPoint - 1;
					player.Atk = player.Atk + 1;
				}

			}
		}

		public Sprite ShowItem(int num) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit;
			String item;

			item = lstItem[num];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				item = itemSplit[0].replace("[", "");
				spr_item = gameControlHTML.GetItem(item);

				if(num == 0){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 1){ spr_item.setPosition(cameraCoordsX - 30.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 2){ spr_item.setPosition(cameraCoordsX - 16.5f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 3){ spr_item.setPosition(cameraCoordsX - 2.6f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 4){ spr_item.setPosition(cameraCoordsX - 44.3f,cameraCoordsY + 17.7f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 5){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 6){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 7){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 8){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 9){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23);  return spr_item;}
				if(num == 10){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 11){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 12){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 13){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 14){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}
				if(num == 15){ spr_item.setPosition(cameraCoordsX - 0.3f,cameraCoordsY + 41.6f); spr_item.setSize(13, 23); return spr_item;}

			}

			return spr_item;
		}

		public String ShowQuantityItem(int num) {
			//Structure: [HPCAN#3]
			String qtd = "";
			String item = "";
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit;

			item = lstItem[num];
			if(!item.equals("[NONE]")) {
				itemSplit = item.split("#");
				item = itemSplit[1].replace("]", "");
				qtd = item;
			}
			else {
				qtd = "";
			}
			return qtd;
		}

		public void ShowNPCs() {
			//NPCs
			if(player.Map.equals("StreetsA")) {
				spr_npc = gameControlHTML.GetNPC("DungeonMaster", 0);
				spr_npc.draw(game.batch);

				spr_npc = gameControlHTML.GetNPC("ExpGiver", 0);
				spr_npc.draw(game.batch);

				spr_master = gameControlHTML.GetUX("textbar", 0, 0);
				spr_master.setSize(20,10);
				spr_master.setPosition(102, -90);
				spr_master.draw(game.batch);

				spr_master = gameControlHTML.GetUX("textbar", 0, 0);
				spr_master.setSize(20,10);
				spr_master.setPosition(-8, -88.5f);
				spr_master.draw(game.batch);

				font_master.draw(game.batch, "Arenas", 105, -81);
				font_master.draw(game.batch, "Doadora", -7f, -80f);
			}
		}

		public void ShowPlayerDead() {
			countDead--;

			player.Target = "none";
			player.playerInBattle = "no";
			player.playerInAttack = "no";
			player.playerInCast = "no";
			autoattack = false;
			spr_master = gameControlHTML.GetUX("textbar", 0, 0);
			spr_master.setPosition(cameraCoordsX -32f,cameraCoordsY -10);
			spr_master.setSize(60, 30);
			spr_master.draw(game.batch);
			font_master.getData().setScale(0.10f,0.15f);
			font_master.setUseIntegerPositions(false);
			font_master.draw(game.batch, "Voce morreu, retornando...",cameraCoordsX - 28,cameraCoordsY + 8);

			if(countDead <= 0) {
				player.Hp = 10;
				player.Mp = 10;
				player.Map = "MetroStation";
				player.PosX = 0;
				player.PosY = 0;
				//gameControlHTML.SaveData(player);
				this.screen.screenSwitch("LoadingScreen",network,account,playernumber);
			}
		}

		public void CheckColision() {
			if(player.Map.equals("StreetsA")) {
				if(player.PosY < -192.5f) {
					player.breakwalk = "front";
				}
				if(player.PosX > 184.5f) {
					player.breakwalk = "right";
				}
				if(player.PosX < -77) {
					player.breakwalk = "left";
				}
			}
			if(player.Map.equals("Sewers")) {
				if(player.PosX > 40f && player.PosX < 53 && player.PosY > 5 && player.PosY < 21.5f) {
					MapChange("StreetsAFromSewers");
				}
			}
		}

		public void CheckAction() {
			if(player.Map.equals("StreetsA")) {
				if(player.PosX >= 103.5f && player.PosX <= 122 && player.PosY >= -142 && player.PosY <= -128.5f ) {
					state = "DungeonSelect";
				}

				//Exp Giver
				if(player.PosX >= -8f && player.PosX <= 10.5f && player.PosY >= -145 && player.PosY <= -112.5f ) {
					onlineresponse = gameControlHTML.OnlineManager("ExpGiver","","");
				}

				if(player.PosX >= 127 && player.PosX <= 143 && player.PosY >= -140 && player.PosY <= -124 ) {
					state = "Shop";
					shopname = "refrishop";
				}
			}
		}

		public boolean CheckMobEvade() {
			int nextint = randnumber.nextInt(100);

			if(nextint < 10) {
				return true;
			}
			else {
				return false;
			}
		}

		public void CheckMobDeadRespawn() {
			if(player.Map.equals("Sewers")) {
				for(int num = 0; num < listMonsters.size(); num++) {

					if(listMonsters.get(num).MobHp <= 0) {
						listMonsters.get(num).MobHp = 0;
						listMonsters.get(num).MobDead = "yes";
					}

					if(listMonsters.get(num).MobDead.equals("yes")) {
						listMonsters.get(num).MobPosX = 200;
						listMonsters.get(num).MobPosY = 200;
						listMonsters.get(num).MobTimeDead--;

						if(listMonsters.get(num).MobTimeDead <= 0) {
							listMonsters.get(num).MobTimeDead = 250;
							listMonsters.get(num).MobDead = "no";
							listMonsters.get(num).MobHp = listMonsters.get(num).MobHpMax;
							listMonsters.get(num).MobMp = listMonsters.get(num).MobMpMax;
							listMonsters.get(num).MobTarget = "none";
							listMonsters.get(num).MobPosX = 0;
							listMonsters.get(num).MobPosY = 0;
						}
					}
				}
		    }
		}

		public void ChangeTarget() {

			if(player.Map.equals("Sewers")) {

				String playerTarget = player.Target;
				for(int i = 0; i < listMonsters.size(); i++) {

					if(countSwitchTarget == 0) {
						countSwitchTarget = i;
					}

					if(countSwitchTarget >= 0) {
						if(countSwitchTarget <= listMonsters.size()) {
							countSwitchTarget++;
							if(countSwitchTarget >= listMonsters.size()) { countSwitchTarget = 0; }
							if(!playerTarget.equals(listMonsters.get(countSwitchTarget).MobID)) {
								player.Target = listMonsters.get(countSwitchTarget).MobID;
								return;
							}
						}
						else {
							countSwitchTarget = 0;
						}
					}
				}
			}
		}

		public int CheckWeapon() {

			if(player.Weapon.equals("basic_knife")) { return 2;}
			if(player.Weapon.equals("doubleedgeknife")) { return 5; }
			if(player.Weapon.equals("woodsword")) { return 10;}
			if(player.Weapon.equals("basicpistol")) { return 8;}
			if(player.Weapon.equals("basicdagger")) { return 8;}
			if(player.Weapon.equals("stickrod")) { return 6;}
			if(player.Weapon.equals("basicaxe")) { return 12;}

			return 0;
		}

		public int CheckCritical() {
			int chance = randnumber.nextInt(100);
			if(player.Luk > 90) {
				if(chance <= (player.Luk - 5)) {
					return 30;
				}
			}
			else {
				if(chance <= player.Luk) {
					return 30;
				}
			}
			return 0;
		}

		public void ShowDamage() {

			if(listDamage.size() == 0) {
				return;
			}

			for(int i = 0; i < listDamage.size(); i++) {
				listDamage.get(i).DamagePosY = listDamage.get(i).DamagePosY + 0.4f;
				listDamage.get(i).DamageTime = listDamage.get(i).DamageTime - 1;

				font_master.getData().setScale(0.30f,0.35f);
				font_master.setUseIntegerPositions(false);
				if(listDamage.get(i).DamageType.equals("mob")) { font_master.setColor(Color.YELLOW); }
				if(listDamage.get(i).DamageType.equals("player")) { font_master.setColor(Color.RED); }
				if(listDamage.get(i).DamageType.equals("heal")) { font_master.setColor(Color.GREEN); }

				font_master.draw(game.batch, String.valueOf(listDamage.get(i).DamageValue), listDamage.get(i).DamagePosX, listDamage.get(i).DamagePosY);

				if(listDamage.get(i).DamageTime < 0) {
					listDamage.remove(listDamage.get(i));
				}

				font_master.setColor(Color.WHITE);
			}
		}

		public void ShowMobs() {
			if(player.Map.equals("Sewers") || player.Map.equals("Watercave") || player.Map.equals("Mines") || player.Map.equals("Snowpalace") || player.Map.equals("Tower")) {
				for(int i = 0; i < listMonsters.size(); i++) {

						//Target do player
						if(player.Target.equals(listMonsters.get(i).MobID)) {
							spr_target = gameControlHTML.GetUX("target", 0, 0);
							spr_target.setPosition(listMonsters.get(i).MobPosX + 2, listMonsters.get(i).MobPosY + 16);
							spr_target.setSize(4,8);
							spr_target.draw(game.batch);
						}

						mobTimerFrame = listMonsters.get(i).MobFrameTime;
						if(mobTimerFrame > 0) {
							mobTimerFrame--;
							listMonsters.get(i).MobFrameTime = mobTimerFrame;
						}
						if(mobTimerFrame <= 0) {
							mobTimerFrame = 40;
							mobFrame = listMonsters.get(i).MobFrame;
							mobFrame++;
							if(mobFrame > 3) { mobFrame = 1;}
							listMonsters.get(i).MobFrame = mobFrame;
							listMonsters.get(i).MobFrameTime = 40;
						}

						//Sem Target
						if(listMonsters.get(i).MobTarget.equals("none")) {
							mobTimerMov = listMonsters.get(i).MobTimerMov;
							mobRandomSt = listMonsters.get(i).MobRandomSt;
							if(mobTimerMov >= 0) {
								mobTimerMov--;
								listMonsters.get(i).MobTimerMov = mobTimerMov;
							}
							if(mobTimerMov < 0) {
								mobRandomSt = randnumber.nextInt(4);
								mobTimerMov = 100;
								listMonsters.get(i).MobTimerMov = mobTimerMov;
								listMonsters.get(i).MobRandomSt = mobRandomSt;
							}

							if(mobRandomSt == 0) {
								mobPositionCoordX = listMonsters.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX + 0.07f;
								listMonsters.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 1) {
								mobPositionCoordX = listMonsters.get(i).MobPosX;
								mobPositionCoordX = mobPositionCoordX - 0.07f;
								listMonsters.get(i).MobPosX = mobPositionCoordX;
							}
							if(mobRandomSt == 2) {
								mobPositionCoordY = listMonsters.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY + 0.07f;
								listMonsters.get(i).MobPosY = mobPositionCoordY;
							}
							if(mobRandomSt == 3) {
								mobPositionCoordY = listMonsters.get(i).MobPosY;
								mobPositionCoordY = mobPositionCoordY - 0.07f;
								listMonsters.get(i).MobPosY = mobPositionCoordY;
							}
						}

						if(listMonsters.get(i).MobTarget.equals(player.Name)) {
							if(listMonsters.get(i).MobPosX < player.PosX + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX + 0.07f; }
							if(listMonsters.get(i).MobPosX > player.PosX + 3) { listMonsters.get(i).MobPosX = listMonsters.get(i).MobPosX - 0.07f; }
							if(listMonsters.get(i).MobPosY < player.PosY - 6 ) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY + 0.07f; }
							if(listMonsters.get(i).MobPosY > player.PosY - 6) { listMonsters.get(i).MobPosY = listMonsters.get(i).MobPosY - 0.07f; }
						}

						//Limit screen
						if(mobPositionCoordY >= 18f && listMonsters.get(i).MobDead.equals("no"))
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 96.5f;
						}
						if(mobPositionCoordY <= -190.5f && listMonsters.get(i).MobDead.equals("no"))
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 67;
						}
						if(mobPositionCoordX >= 185 && listMonsters.get(i).MobDead.equals("no"))
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 65;
						}
						if(mobPositionCoordX <= -77f && listMonsters.get(i).MobDead.equals("no"))
						{
							listMonsters.get(i).MobPosY = -112f;
							listMonsters.get(i).MobPosX = 65;
						}

						if(player.Map.equals("Sewers")) { spr_monster = gameControlHTML.GetMonster(listMonsters.get(i).MobName, listMonsters.get(i).MobFrame, "L");}
						//if(player.Map.equals("Watercave")) { spr_monster = atlas_mobWatercave.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map.equals("Mines")) { spr_monster = atlas_mobMines.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map.equals("Snowpalace")) { spr_monster = atlas_mobSnowpalace.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						//if(player.Map.equals("Tower")) { spr_monster = atlas_mobTower.createSprite(listMonsters.get(i).MobName + listMonsters.get(i).MobFrame + "L"); }
						spr_monster.setPosition(listMonsters.get(i).MobPosX, listMonsters.get(i).MobPosY);
						spr_monster.setSize(listMonsters.get(i).MobSizeX, listMonsters.get(i).MobSizeY);
						spr_monster.draw(game.batch);

						mobPositionCoordX = listMonsters.get(i).MobPosX;
						mobPositionCoordY = listMonsters.get(i).MobPosY;
						mobPositionCoordY = mobPositionCoordY - 0.2f;
						font_master.getData().setScale(0.10f,0.15f);
						font_master.setUseIntegerPositions(false);
						font_master.draw(game.batch, listMonsters.get(i).MobName,mobPositionCoordX, mobPositionCoordY);
						font_master.draw(game.batch, " HP :" + listMonsters.get(i).MobHp + "/" + listMonsters.get(i).MobHpMax ,mobPositionCoordX - 4, mobPositionCoordY - 8);

					}
			}
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


		public void ShowCards() {
			//Basic Cards
			//Hotkey 1 / 2
			if(player.hotkey1.equals("none")) {
				spr_master = gameControlHTML.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}

			if(player.hotkey2.equals("none")) {
				spr_master = gameControlHTML.GetCard("cardempty");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}

			if(player.hotkey1.equals("hpcan")) {
				spr_master = gameControlHTML.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 30);
				spr_master.draw(game.batch);
			}

			if(player.hotkey2.equals("hpcan")) {
				spr_master = gameControlHTML.GetCard("cardhp");
				spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY + 0);
				spr_master.draw(game.batch);
			}


			if(!player.Map.equals("Sewers")){
				spr_master = gameControlHTML.GetCard("cardaction");
				spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
				spr_master.draw(game.batch);
			}

			if(player.Map.equals("Sewers")){ //cardactionON
				if(autoattack){
					spr_master = gameControlHTML.GetCard("cardactionON");
					spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
					spr_master.draw(game.batch);
				}
				else{
					spr_master = gameControlHTML.GetCard("cardaction");
					spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 60);
					spr_master.draw(game.batch);
				}
			}

			spr_master = gameControlHTML.GetCard("cardtarget");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 60);
			spr_master.draw(game.batch);

			//Novice Cards
			spr_master = gameControlHTML.GetCard("cardcutbreak");
			spr_master.setPosition(cameraCoordsX + 47, cameraCoordsY - 90);
			spr_master.draw(game.batch);

			spr_master = gameControlHTML.GetCard("cardrockbound");
			spr_master.setPosition(cameraCoordsX + 63, cameraCoordsY - 90);
			spr_master.draw(game.batch);

			spr_master = gameControlHTML.GetCard("cardempty");
			spr_master.setPosition(cameraCoordsX + 79, cameraCoordsY - 90);
			spr_master.draw(game.batch);
		}



		//SKILL
		public void CheckSkill(int num) {

			if(num == 1 && player.Job.equals("Aprendiz")) { SetUseSkill("tripleattack"); }
			if(num == 1 && player.Job.equals("Espadachim")) { SetUseSkill("flysword"); }
			if(num == 1 && player.Job.equals("Feiticeiro")) { SetUseSkill("fireball"); }
			if(num == 1 && player.Job.equals("Batedor")) { SetUseSkill("hammercrash"); }
			if(num == 1 && player.Job.equals("Pistoleiro")) { SetUseSkill("bulletrain"); }
			if(num == 1 && player.Job.equals("Curandeiro")) { SetUseSkill("heal"); }
			if(num == 1 && player.Job.equals("Ladrao")) { SetUseSkill("poisonhit"); }

			if(num == 2 && player.Job.equals("Aprendiz")) { SetUseSkill("rockbound"); }
			if(num == 2 && player.Job.equals("Espadachim")) { SetUseSkill("ironshield"); }
			if(num == 2 && player.Job.equals("Feiticeiro")) { SetUseSkill("thundercloud"); }
			if(num == 2 && player.Job.equals("Batedor")) { SetUseSkill("overpower"); }
			if(num == 2 && player.Job.equals("Pistoleiro")) { SetUseSkill("mine"); }
			if(num == 2 && player.Job.equals("Curandeiro")) { SetUseSkill("holyprism"); }
			if(num == 2 && player.Job.equals("Ladrao")) { SetUseSkill("invisibility"); }

			if(num == 3 && player.Job.equals("Espadachim")) { SetUseSkill("healthboost"); }
			if(num == 3 && player.Job.equals("Feiticeiro")) { SetUseSkill("icecrystal"); }
			if(num == 3 && player.Job.equals("Batedor")) { SetUseSkill("berserk"); }
			if(num == 3 && player.Job.equals("Pistoleiro")) { SetUseSkill("lockshot"); }
			if(num == 3 && player.Job.equals("Curandeiro")) { SetUseSkill("defboost"); }
			if(num == 3 && player.Job.equals("Ladrao")) { SetUseSkill("steal"); }
		}


		public void SetUseSkill(String skill) {
			//Cost
			if(skill.equals("tripleattack") && player.Mp < 5) { notmp = true; return; }
			if(skill.equals("rockbound") && player.Mp < 5) { notmp = true; return; }
			if(skill.equals("regen") && player.Mp < 2) { notmp = true; return; }

			if(skill.equals("flysword") && player.Mp < 45) { notmp = true; return; }
			if(skill.equals("ironshield") && player.Mp < 30) { notmp = true; return; }
			if(skill.equals("healthboost") && player.Mp < 40) { notmp = true; return; }

			if(skill.equals("fireball") && player.Mp < 30) { notmp = true; return; }
			if(skill.equals("thundercloud") && player.Mp < 60) { notmp = true; return; }
			if(skill.equals("icecrystal") && player.Mp < 100) { notmp = true; return; }

			if(skill.equals("heal") && player.Mp < 20) { notmp = true; return; }
			if(skill.equals("holyprism") && player.Mp < 5) { notmp = true; return; }
			if(skill.equals("defboost") && player.Mp < 40) { notmp = true; return; }

			if(skill.equals("poisonhit") && player.Mp < 25) { notmp = true; return; }
			if(skill.equals("steal") && player.Mp < 10) { notmp = true; return; }
			if(skill.equals("invisibility") && player.Mp < 15) { notmp = true; return; }

			if(skill.equals("berserk") && player.Mp < 25) { notmp = true; return; }
			if(skill.equals("overpower") && player.Mp < 50) { notmp = true; return; }
			if(skill.equals("hammercrash") && player.Mp < 20) { notmp = true; return; }

			if(skill.equals("lockshot") && player.Mp < 15) { notmp = true; return; }
			if(skill.equals("mine") && player.Mp < 20) { notmp = true; return; }
			if(skill.equals("bulletrain") && player.Mp < 40) { notmp = true; return; }

			if(skill.equals("tripleattack")) { player.Mp = player.Mp - 5; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("rockbound")) { player.Mp = player.Mp - 5; if(player.Mp <= 0) { player.Mp = player.Mp = 0;}}
			if(skill.equals("regen")) { player.Mp = player.Mp - 2; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }

			if(skill.equals("flysword")) { player.Mp = player.Mp - 25; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("ironshield")) { player.Mp = player.Mp - 15; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("healthboost")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }

			if(skill.equals("fireball")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("thundercloud")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("icecrystal")) { player.Mp = player.Mp - 100; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }

			if(skill.equals("heal")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("holyprism")) { player.Mp = player.Mp - 5; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("defboost")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }

			if(skill.equals("poisonhit")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("steal")) { player.Mp = player.Mp - 10; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("invisibility")) { player.Mp = player.Mp - 15; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }

			if(skill.equals("berserk")) { player.Mp = player.Mp - 25; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("overpower")) { player.Mp = player.Mp - 50; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("hammercrash")) { player.Mp = player.Mp - 20; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }

			if(skill.equals("lockshot")) { player.Mp = player.Mp - 15; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("mine")) { player.Mp = player.Mp - 20; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }
			if(skill.equals("bulletrain")) { player.Mp = player.Mp - 40; if(player.Mp <= 0) { player.Mp = player.Mp = 0;} }


			//Ranged?
			if(skill.equals("tripleattack")) { rangedAttack = false; }
			if(skill.equals("regen")) { rangedAttack = true; }
			if(skill.equals("rockbound")) { rangedAttack = true; }

			if(skill.equals("flysword")) { rangedAttack = false; }
			if(skill.equals("healthboost")) { rangedAttack = true; }
			if(skill.equals("ironshield")) { rangedAttack = false; }

			if(skill.equals("fireball")) { rangedAttack = true; }
			if(skill.equals("icecrystal")) { rangedAttack = true; }
			if(skill.equals("thundercloud")) { rangedAttack = true; }

			if(skill.equals("heal")) { rangedAttack = true; }
			if(skill.equals("defboost")) { rangedAttack = true; }
			if(skill.equals("holyprism")) { rangedAttack = true; }

			if(skill.equals("hammercrash")) { rangedAttack = false; }
			if(skill.equals("overpower")) { rangedAttack = false; }
			if(skill.equals("berserk")) { rangedAttack = false; }


			if(skill.equals("lockshot")) { rangedAttack = true; }
			if(skill.equals("mine")) { rangedAttack = true; }
			if(skill.equals("bulletrain")) { rangedAttack = true; }

			if(skill.equals("steal")) { rangedAttack = false; }
			if(skill.equals("poisonhit")) { rangedAttack = false; }
			if(skill.equals("invisibility")) { rangedAttack = true; }


			skillname = skill;
			if(!rangedAttack) {
				CheckAreaRangedSkill();
			}
			else {
				selectAreaRanged = true;
			}

		}

		public void CheckAreaRangedSkill() {
			if(player.Map.equals("Sewers") && autoattack) {
				for(int i = 0; i < listMonsters.size(); i++) {

					//Close Ranged
					if(player.Target.equals(listMonsters.get(i).MobID) && !rangedAttack) {
						if((listMonsters.get(i).MobPosX + 5) > (player.PosX - 5) && (listMonsters.get(i).MobPosX + 5) < (player.PosX + 15)
						   && (listMonsters.get(i).MobPosY + 5) > (player.PosY - 7) && (listMonsters.get(i).MobPosY + 5) < (player.PosY + 18)) {
							player.playerInBattle = "yes";
							listMonsters.get(i).MobTarget = player.Name;
							//Aprendiz
							if(skillname.equals("tripleattack")) {
								int atkweapon = CheckWeapon();
								int totaldmg = player.Atk + ((player.Str * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX +5;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY + 5;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX + 5;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY + 5;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
							if(skillname.equals("hammercrash")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Str * 2) + (player.Vit * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
							if(skillname.equals("flysword")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Str * 3) + (player.Agi * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
							if(skillname.equals("poisonhit")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Luk * 2)+ (player.Str * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "tripleattack";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
							if(skillname.equals("steal")) {

							}
							if(skillname.equals("overpower")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Vit * 3) + (player.Str * 5) + (player.Luk * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "overpower";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
							}
						}
					}

					//Long Ranged
					if(rangedAttack) {

						if(skillname.equals("heal")) {
							player.Hp = player.Hp + (player.Wis * 3);
							if(player.Hp > player.HpMax) {player.Hp = player.HpMax; }
							rangedAttack = false;
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "heal";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return;
						}

						if(skillname.equals("defboost")) {
							GiveBuff("defboost");
							rangedAttack = false;
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "defboost";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return;
						}
						if(skillname.equals("healthboost")) { GiveBuff("healthboost"); rangedAttack = false; return; }
						if(skillname.equals("regen")) {
							GiveBuff("regen");
							rangedAttack = false;
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "regen";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return;
						}
						if(skillname.equals("ironshield")) {
							GiveBuff("ironshield");
							rangedAttack = false;
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "ironshield";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return;
						}
						if(skillname.equals("invisibility")) {
							GiveBuff("invisibility");
							rangedAttack = false;
							skillEffect = true;
							Skill skillInUse = new Skill();
							Damage damageSkill = new Damage();
							skillInUse.SkillName = "invisibility";
							skillInUse.SkillPosX = player.PosX;
							skillInUse.SkillPosY = player.PosY;
							damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
							damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
							skillInUse.SkillTime = 100;
							damageSkill.DamageTime = 100;
							damageSkill.DamageType = "heal";
							damageSkill.DamageValue = 0;
							return;
						}


						if((listMonsters.get(i).MobPosX + 5) > (touchSkillX - 5) && (listMonsters.get(i).MobPosX + 5) < (touchSkillX + 5)
						   && (listMonsters.get(i).MobPosY + 5) > (touchSkillY - 10) && (listMonsters.get(i).MobPosY + 5) < (touchSkillY + 10)) {
							player.playerInBattle = "yes";
							listMonsters.get(i).MobTarget = player.Name;

							if(skillname.equals("rockbound")) {
								int atkweapon = CheckWeapon();
								int totaldmg = player.Atk + ((player.Wis * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "rockbound";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}

							if(skillname.equals("fireball")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								listMonsters.get(i).MobHp = mobHP;
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "fireball";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}

							if(skillname.equals("icecrystal")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis * 6) + (player.Dex * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "icecrystal";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
							if(skillname.equals("thundercloud")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis * 3) + (player.Agi * 2) + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "thundercloud";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}

							if(skillname.equals("bulletrain")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Dex * 2) + (player.Agi * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "bulletrain";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
							if(skillname.equals("holyprism")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Wis) + player.Luk + atkweapon);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "holyprism";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
							if(skillname.equals("mine")) {
								int atkweapon = CheckWeapon();
								int totaldmg = ((player.Dex * 2) + 10);
								int mobHP = listMonsters.get(i).MobHp;
								mobHP = mobHP - totaldmg;
								if(network) { gameControlHTML.OnlineManager("Atk",String.valueOf(i),String.valueOf(mobHP)); } else { listMonsters.get(i).MobHp = mobHP; }
								skillEffect = true;
								Skill skillInUse = new Skill();
								Damage damageSkill = new Damage();
								skillInUse.SkillName = "mine";
								skillInUse.SkillPosX = listMonsters.get(i).MobPosX;
								skillInUse.SkillPosY = listMonsters.get(i).MobPosY;
								damageSkill.DamagePosX = listMonsters.get(i).MobPosX;
								damageSkill.DamagePosY = listMonsters.get(i).MobPosY;
								skillInUse.SkillTime = 100;
								damageSkill.DamageTime = 100;
								damageSkill.DamageType = "mob";
								damageSkill.DamageValue = totaldmg;
								listSkills.add(skillInUse);
								listDamage.add(damageSkill);
								rangedAttack = false;
								if(mobHP <= 0) { MobDead(i); }
								return;
							}
						}
					}
				}
			}
		}

		public void GiveBuff(String buffname) {
			boolean setBuff = false;
			String buff = "";

			if(player.buffA.equals(buffname)) { return; }
			if(player.buffB.equals(buffname)) { return; }
			if(player.buffC.equals(buffname)) { return; }

			if(player.buffA.equals("none") && !setBuff) { player.buffA = buffname; setBuff  = true; buff = "A"; }
			if(player.buffB.equals("none") && !setBuff) { player.buffB = buffname; setBuff  = true; buff = "B"; }
			if(player.buffC.equals("none") && !setBuff) { player.buffC = buffname; setBuff  = true; buff = "C"; }

			if(buffname.equals("defboost")) {
				player.Atk = player.Atk + player.Atk * 2;
				player.Def = player.Def + player.Def * 2;
				player.HpMax = player.HpMax + 30;
				player.MpMax = player.MpMax + 30;

				if(buff.equals("A")) { player.BuffTimeA = 5000; }
				if(buff.equals("B")) { player.BuffTimeB = 5000; }
				if(buff.equals("C")) { player.BuffTimeC = 5000; }
			}

			if(buffname.equals("ironshield")) {
				player.Def = player.Def * 4;

				if(buff.equals("A")) { player.BuffTimeA = 2500; }
				if(buff.equals("B")) { player.BuffTimeB = 2500; }
				if(buff.equals("C")) { player.BuffTimeC = 2500; }
			}

			if(buffname.equals("healthboost")) {
				player.HpMax = player.HpMax * 3;

				if(buff.equals("A")) { player.BuffTimeA = 4500; }
				if(buff.equals("B")) { player.BuffTimeB = 4500; }
				if(buff.equals("C")) { player.BuffTimeC = 4500; }
			}

			if(buffname.equals("berserk")) {
				player.Str = player.Str * 3;

				if(buff.equals("A")) { player.BuffTimeA = 2500; }
				if(buff.equals("B")) { player.BuffTimeB = 2500; }
				if(buff.equals("C")) { player.BuffTimeC = 2500; }
			}

			if(buffname.equals("regen")) {
				player.regenTimeMax = player.regenTimeMax - 3000;

				if(buff.equals("A")) { player.BuffTimeA = 2000; }
				if(buff.equals("B")) { player.BuffTimeB = 2000; }
				if(buff.equals("C")) { player.BuffTimeC = 2000; }
			}

			if(buffname.equals("invisibility")) {
				if(buff.equals("A")) { player.BuffTimeA = 1000; }
				if(buff.equals("B")) { player.BuffTimeB = 1000; }
				if(buff.equals("C")) { player.BuffTimeC = 1000; }
			}

			if(buffname.equals("lockshot")) {
				player.Dex = player.Dex * 2;
				player.Luk = player.Luk * 2;

				if(buff.equals("A")) { player.BuffTimeA = 3000; }
				if(buff.equals("B")) { player.BuffTimeB = 3000; }
				if(buff.equals("C")) { player.BuffTimeC = 3000; }
			}
		}

		public void RemoveBuffs(String buffname) {
			String buff = "";

			if(player.buffA.equals(buffname)) { buff = "A"; }
			if(player.buffB.equals(buffname)) { buff = "B"; }
			if(player.buffC.equals(buffname)) { buff = "C"; }

			if(buffname.equals("boost")) {
				player.Atk = player.Atk + player.Atk / 2;
				player.Def = player.Def + player.Def / 2;
				player.HpMax = player.HpMax - 30;
				player.MpMax = player.MpMax - 30;
			}

			if(buffname.equals("ironshield")) {
				player.Def = player.Def / 4;
			}

			if(buffname.equals("healthboost")) {
				player.HpMax = player.HpMax / 3;
			}

			if(buffname.equals("berserk")) {
				player.Str = player.Str / 3;
			}

			if(buffname.equals("regen")) {
				player.regenTimeMax = player.regenTimeMax + 3000;
			}

			if(buffname.equals("lockshot")) {
				player.Dex = player.Dex / 2;
				player.Luk = player.Luk / 2;
			}

			if(buff.equals("A")) { player.buffA = "none"; player.BuffTimeA = 0; }
			if(buff.equals("B")) { player.buffB = "none"; player.BuffTimeB = 0; }
			if(buff.equals("C")) { player.buffC = "none"; player.BuffTimeC = 0; }

		}

		public void ShowBuffs() {

			if(!player.buffA.equals("none")) {
				if(player.buffA.equals("defboost")) { spr_master = gameControlHTML.GetCard("cardboost"); }
				if(player.buffA.equals("ironshield")) { spr_master = gameControlHTML.GetCard("cardironshield"); }
				if(player.buffA.equals("healthboost")) { spr_master = gameControlHTML.GetCard("cardhealthboost"); }
				if(player.buffA.equals("berserk")) {  spr_master = gameControlHTML.GetCard("cardberserk");}
				if(player.buffA.equals("regen")) { spr_master = gameControlHTML.GetCard("cardregen"); }
				if(player.buffA.equals("invisibility")) { spr_master = gameControlHTML.GetCard("cardinvisibility"); }
				if(player.buffA.equals("lockshot")) { spr_master = gameControlHTML.GetCard("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-50, 30);
				spr_master.draw(game.batch);

				player.BuffTimeA = player.BuffTimeA - 1;
				if(player.BuffTimeA <= 0) {
					RemoveBuffs(player.buffA);
				}
			}
			if(!player.buffB.equals("none")) {
				if(player.buffB.equals("defboost")) { spr_master = gameControlHTML.GetCard("cardboost"); }
				if(player.buffB.equals("ironshield")) { spr_master = gameControlHTML.GetCard("cardironshield"); }
				if(player.buffB.equals("healthboost")) { spr_master = gameControlHTML.GetCard("cardhealthboost"); }
				if(player.buffB.equals("berserk")) {  spr_master = gameControlHTML.GetCard("cardberserk");}
				if(player.buffB.equals("regen")) { spr_master = gameControlHTML.GetCard("cardregen"); }
				if(player.buffB.equals("invisibility")) { spr_master = gameControlHTML.GetCard("cardinvisibility"); }
				if(player.buffB.equals("lockshot")) { spr_master = gameControlHTML.GetCard("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-45, 30);
				spr_master.draw(game.batch);

				player.BuffTimeB = player.BuffTimeB - 1;
				if(player.BuffTimeB <= 0) {
					RemoveBuffs(player.buffB);
				}
			}
			if(!player.buffC.equals("none")) {
				if(player.buffC.equals("defboost")) { spr_master = gameControlHTML.GetCard("cardboost"); }
				if(player.buffC.equals("ironshield")) { spr_master = gameControlHTML.GetCard("cardironshield"); }
				if(player.buffC.equals("healthboost")) { spr_master = gameControlHTML.GetCard("cardhealthboost"); }
				if(player.buffC.equals("berserk")) {  spr_master = gameControlHTML.GetCard("cardberserk");}
				if(player.buffC.equals("regen")) { spr_master = gameControlHTML.GetCard("cardregen"); }
				if(player.buffC.equals("invisibility")) { spr_master = gameControlHTML.GetCard("cardinvisibility"); }
				if(player.buffC.equals("lockshot")) { spr_master = gameControlHTML.GetCard("cardlockshot"); }
				spr_master.setSize(3, 8);
				spr_master.setPosition(-40, 30);
				spr_master.draw(game.batch);

				player.BuffTimeC = player.BuffTimeC - 1;
				if(player.BuffTimeC <= 0) {
					RemoveBuffs(player.buffC);
				}
			}
		}

		public void ShowSkill() {

			if(skillUsed > 0) {
				skillUsed--;
				if(skillUsed < 0) { skillUsed = 0; }
			}

			if(listSkills.size() == 0) {
				return;
			}

			for(int i = 0; i < listSkills.size(); i++) {

				int time = listSkills.get(i).SkillTime;
				listSkills.get(i).SkillTime = time - 1;

				if(listSkills.get(i).SkillTime >= 80 && listSkills.get(i).SkillTime <= 100) {
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControlHTML.GetSpriteSkill("tripleattack",6); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControlHTML.GetSpriteSkill("steal",6); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControlHTML.GetSpriteSkill("soulclash",6); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControlHTML.GetSpriteSkill("ravenblade",6); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControlHTML.GetSpriteSkill("ragebound",6); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControlHTML.GetSpriteSkill("thundercloud",6); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControlHTML.GetSpriteSkill("lockshot",6); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControlHTML.GetSpriteSkill("mine",6); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControlHTML.GetSpriteSkill("overpower",6); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControlHTML.GetSpriteSkill("poisonhit",6); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControlHTML.GetSpriteSkill("precision",6); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControlHTML.GetSpriteSkill("protect",6); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControlHTML.GetSpriteSkill("healthboost",6); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControlHTML.GetSpriteSkill("holyprism",6); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControlHTML.GetSpriteSkill("icecrystal",6); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControlHTML.GetSpriteSkill("impound",6); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControlHTML.GetSpriteSkill("invisibility",6); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControlHTML.GetSpriteSkill("ironshield",6); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControlHTML.GetSpriteSkill("doublehit",6); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControlHTML.GetSpriteSkill("fastshot",6); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControlHTML.GetSpriteSkill("fireball",6); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControlHTML.GetSpriteSkill("flysword",6); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControlHTML.GetSpriteSkill("heal",6); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControlHTML.GetSpriteSkill("defboost",6); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControlHTML.GetSpriteSkill("berserk",6); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControlHTML.GetSpriteSkill("bulletrain",6); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControlHTML.GetSpriteSkill("dashkick",6); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControlHTML.GetSpriteSkill("regen",6); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControlHTML.GetSpriteSkill("rockbound",6); }

					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}

				if(listSkills.get(i).SkillTime >= 60 && listSkills.get(i).SkillTime <= 80) {
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControlHTML.GetSpriteSkill("tripleattack",5); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControlHTML.GetSpriteSkill("steal",5); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControlHTML.GetSpriteSkill("soulclash",5); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControlHTML.GetSpriteSkill("ravenblade",5); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControlHTML.GetSpriteSkill("ragebound",5); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControlHTML.GetSpriteSkill("thundercloud",5); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControlHTML.GetSpriteSkill("lockshot",5); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControlHTML.GetSpriteSkill("mine",5); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControlHTML.GetSpriteSkill("overpower",5); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControlHTML.GetSpriteSkill("poisonhit",5); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControlHTML.GetSpriteSkill("precision",5); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControlHTML.GetSpriteSkill("protect",5); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControlHTML.GetSpriteSkill("healthboost",5); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControlHTML.GetSpriteSkill("holyprism",5); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControlHTML.GetSpriteSkill("icecrystal",5); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControlHTML.GetSpriteSkill("impound",5); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControlHTML.GetSpriteSkill("invisibility",5); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControlHTML.GetSpriteSkill("ironshield",5); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControlHTML.GetSpriteSkill("doublehit",5); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControlHTML.GetSpriteSkill("fastshot",5); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControlHTML.GetSpriteSkill("fireball",5); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControlHTML.GetSpriteSkill("flysword",5); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControlHTML.GetSpriteSkill("heal",5); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControlHTML.GetSpriteSkill("defboost",5); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControlHTML.GetSpriteSkill("berserk",5); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControlHTML.GetSpriteSkill("bulletrain",5); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControlHTML.GetSpriteSkill("dashkick",5); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControlHTML.GetSpriteSkill("regen",5); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControlHTML.GetSpriteSkill("rockbound",5); }

					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}

				if(listSkills.get(i).SkillTime >= 40 && listSkills.get(i).SkillTime <= 60) {
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControlHTML.GetSpriteSkill("tripleattack",4); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControlHTML.GetSpriteSkill("steal",4); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControlHTML.GetSpriteSkill("soulclash",4); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControlHTML.GetSpriteSkill("ravenblade",4); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControlHTML.GetSpriteSkill("ragebound",4); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControlHTML.GetSpriteSkill("thundercloud",4); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControlHTML.GetSpriteSkill("lockshot",4); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControlHTML.GetSpriteSkill("mine",4); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControlHTML.GetSpriteSkill("overpower",4); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControlHTML.GetSpriteSkill("poisonhit",4); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControlHTML.GetSpriteSkill("precision",4); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControlHTML.GetSpriteSkill("protect",4); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControlHTML.GetSpriteSkill("healthboost",4); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControlHTML.GetSpriteSkill("holyprism",4); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControlHTML.GetSpriteSkill("icecrystal",4); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControlHTML.GetSpriteSkill("impound",4); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControlHTML.GetSpriteSkill("invisibility",4); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControlHTML.GetSpriteSkill("ironshield",4); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControlHTML.GetSpriteSkill("doublehit",4); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControlHTML.GetSpriteSkill("fastshot",4); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControlHTML.GetSpriteSkill("fireball",4); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControlHTML.GetSpriteSkill("flysword",4); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControlHTML.GetSpriteSkill("heal",4); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControlHTML.GetSpriteSkill("defboost",4); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControlHTML.GetSpriteSkill("berserk",4); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControlHTML.GetSpriteSkill("bulletrain",4); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControlHTML.GetSpriteSkill("dashkick",4); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControlHTML.GetSpriteSkill("regen",4); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControlHTML.GetSpriteSkill("rockbound",4); }

					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}

				if(listSkills.get(i).SkillTime >= 20 && listSkills.get(i).SkillTime <= 40) {
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControlHTML.GetSpriteSkill("tripleattack",3); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControlHTML.GetSpriteSkill("steal",3); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControlHTML.GetSpriteSkill("soulclash",3); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControlHTML.GetSpriteSkill("ravenblade",3); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControlHTML.GetSpriteSkill("ragebound",3); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControlHTML.GetSpriteSkill("thundercloud",3); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControlHTML.GetSpriteSkill("lockshot",3); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControlHTML.GetSpriteSkill("mine",3); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControlHTML.GetSpriteSkill("overpower",3); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControlHTML.GetSpriteSkill("poisonhit",3); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControlHTML.GetSpriteSkill("precision",3); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControlHTML.GetSpriteSkill("protect",3); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControlHTML.GetSpriteSkill("healthboost",3); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControlHTML.GetSpriteSkill("holyprism",3); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControlHTML.GetSpriteSkill("icecrystal",3); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControlHTML.GetSpriteSkill("impound",3); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControlHTML.GetSpriteSkill("invisibility",3); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControlHTML.GetSpriteSkill("ironshield",3); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControlHTML.GetSpriteSkill("doublehit",3); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControlHTML.GetSpriteSkill("fastshot",3); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControlHTML.GetSpriteSkill("fireball",3); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControlHTML.GetSpriteSkill("flysword",3); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControlHTML.GetSpriteSkill("heal",3); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControlHTML.GetSpriteSkill("defboost",3); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControlHTML.GetSpriteSkill("berserk",3); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControlHTML.GetSpriteSkill("bulletrain",3); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControlHTML.GetSpriteSkill("dashkick",3); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControlHTML.GetSpriteSkill("regen",3); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControlHTML.GetSpriteSkill("rockbound",3); }

					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}

				if(listSkills.get(i).SkillTime >= 10 && listSkills.get(i).SkillTime <= 20) {
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControlHTML.GetSpriteSkill("tripleattack",2); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControlHTML.GetSpriteSkill("steal",2); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControlHTML.GetSpriteSkill("soulclash",2); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControlHTML.GetSpriteSkill("ravenblade",2); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControlHTML.GetSpriteSkill("ragebound",2); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControlHTML.GetSpriteSkill("thundercloud",2); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControlHTML.GetSpriteSkill("lockshot",2); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControlHTML.GetSpriteSkill("mine",2); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControlHTML.GetSpriteSkill("overpower",2); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControlHTML.GetSpriteSkill("poisonhit",2); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControlHTML.GetSpriteSkill("precision",2); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControlHTML.GetSpriteSkill("protect",2); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControlHTML.GetSpriteSkill("healthboost",2); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControlHTML.GetSpriteSkill("holyprism",2); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControlHTML.GetSpriteSkill("icecrystal",2); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControlHTML.GetSpriteSkill("impound",2); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControlHTML.GetSpriteSkill("invisibility",2); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControlHTML.GetSpriteSkill("ironshield",2); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControlHTML.GetSpriteSkill("doublehit",2); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControlHTML.GetSpriteSkill("fastshot",2); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControlHTML.GetSpriteSkill("fireball",2); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControlHTML.GetSpriteSkill("flysword",2); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControlHTML.GetSpriteSkill("heal",2); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControlHTML.GetSpriteSkill("defboost",2); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControlHTML.GetSpriteSkill("berserk",2); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControlHTML.GetSpriteSkill("bulletrain",2); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControlHTML.GetSpriteSkill("dashkick",2); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControlHTML.GetSpriteSkill("regen",2); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControlHTML.GetSpriteSkill("rockbound",2); }

					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}

				if(listSkills.get(i).SkillTime >= 0 && listSkills.get(i).SkillTime <= 10) {
					if(listSkills.get(i).SkillName.equals("tripleattack")) { spr_master = gameControlHTML.GetSpriteSkill("tripleattack",1); }
					if(listSkills.get(i).SkillName.equals("steal")) { spr_master = gameControlHTML.GetSpriteSkill("steal",1); }
					if(listSkills.get(i).SkillName.equals("soulclash")) { spr_master = gameControlHTML.GetSpriteSkill("soulclash",1); }
					if(listSkills.get(i).SkillName.equals("ravenblade")) { spr_master = gameControlHTML.GetSpriteSkill("ravenblade",1); }
					if(listSkills.get(i).SkillName.equals("ragebound")) { spr_master = gameControlHTML.GetSpriteSkill("ragebound",1); }
					if(listSkills.get(i).SkillName.equals("thundercloud")) { spr_master = gameControlHTML.GetSpriteSkill("thundercloud",1); }
					if(listSkills.get(i).SkillName.equals("lockshot")) { spr_master = gameControlHTML.GetSpriteSkill("lockshot",1); }
					if(listSkills.get(i).SkillName.equals("mine")) { spr_master = gameControlHTML.GetSpriteSkill("mine",1); }
					if(listSkills.get(i).SkillName.equals("overpower")) { spr_master = gameControlHTML.GetSpriteSkill("overpower",1); }
					if(listSkills.get(i).SkillName.equals("poisonhit")) { spr_master = gameControlHTML.GetSpriteSkill("poisonhit",1); }
					if(listSkills.get(i).SkillName.equals("precision")) { spr_master = gameControlHTML.GetSpriteSkill("precision",1); }
					if(listSkills.get(i).SkillName.equals("protect")) { spr_master = gameControlHTML.GetSpriteSkill("protect",1); }
					if(listSkills.get(i).SkillName.equals("healthboost")) { spr_master = gameControlHTML.GetSpriteSkill("healthboost",1); }
					if(listSkills.get(i).SkillName.equals("holyprism")) { spr_master = gameControlHTML.GetSpriteSkill("holyprism",1); }
					if(listSkills.get(i).SkillName.equals("icecrystal")) { spr_master = gameControlHTML.GetSpriteSkill("icecrystal",1); }
					if(listSkills.get(i).SkillName.equals("impound")) { spr_master = gameControlHTML.GetSpriteSkill("impound",1); }
					if(listSkills.get(i).SkillName.equals("invisibility")) { spr_master = gameControlHTML.GetSpriteSkill("invisibility",1); }
					if(listSkills.get(i).SkillName.equals("ironshield")) { spr_master = gameControlHTML.GetSpriteSkill("ironshield",1); }
					if(listSkills.get(i).SkillName.equals("doublehit")) { spr_master = gameControlHTML.GetSpriteSkill("doublehit",1); }
					if(listSkills.get(i).SkillName.equals("fastshot")) { spr_master = gameControlHTML.GetSpriteSkill("fastshot",1); }
					if(listSkills.get(i).SkillName.equals("fireball")) { spr_master = gameControlHTML.GetSpriteSkill("fireball",1); }
					if(listSkills.get(i).SkillName.equals("flysword")) { spr_master = gameControlHTML.GetSpriteSkill("flysword",1); }
					if(listSkills.get(i).SkillName.equals("heal")) { spr_master = gameControlHTML.GetSpriteSkill("heal",1); }
					if(listSkills.get(i).SkillName.equals("defboost")) { spr_master = gameControlHTML.GetSpriteSkill("defboost",1); }
					if(listSkills.get(i).SkillName.equals("berserk")) { spr_master = gameControlHTML.GetSpriteSkill("berserk",1); }
					if(listSkills.get(i).SkillName.equals("bulletrain")) { spr_master = gameControlHTML.GetSpriteSkill("bulletrain",1); }
					if(listSkills.get(i).SkillName.equals("dashkick")) { spr_master = gameControlHTML.GetSpriteSkill("dashkick",1); }
					if(listSkills.get(i).SkillName.equals("regen")) { spr_master = gameControlHTML.GetSpriteSkill("regen",1); }
					if(listSkills.get(i).SkillName.equals("rockbound")) { spr_master = gameControlHTML.GetSpriteSkill("rockbound",1); spr_master.setSize(60,60); }

					spr_master.setPosition(listSkills.get(i).SkillPosX -10, listSkills.get(i).SkillPosY);
					spr_master.setSize(40,40);
					spr_master.draw(game.batch);
				}

				if(listSkills.get(i).SkillTime < 0) {
					listSkills.remove(listSkills.get(i));
				}
			}
		}

		//Hotkey
		public void HotKeyItem(int itemNum, int hotkeynum) {
			String[] lstItem = player.Itens.split("-");
			String[] itemSplit = lstItem[itemNum].split("#");
			String item = "";
			//hotketcountitem = itemNum;


			if(hotkeynum == 1) {
				hotketcountitem1 = itemNum;
			}

			if(hotkeynum == 2) {
				hotketcountitem2 = itemNum;
			}

			item = itemSplit[0].replace("[", "");

			if(hotkeynum == 1) {
				if(item.equals("hpcan")) { player.hotkey1 = item; return; }
				if(item.equals("garrafadrink")) { player.hotkey1 = item; return; }
				if(item.equals("mpcan")) { player.hotkey1 = item; return; }
				if(item.equals("garrafamagica")) { player.hotkey1 = item; return; }
				if(item.equals("stcan")) { player.hotkey1 = item; return; }
				if(item.equals("garrafasuco")) { player.hotkey1 = item; return; }
			}
			if(hotkeynum == 2) {
				if(item.equals("hpcan")) { player.hotkey2 = item; return; }
				if(item.equals("garrafadrink")) { player.hotkey2 = item; return; }
				if(item.equals("mpcan")) { player.hotkey2 = item; return; }
				if(item.equals("garrafamagica")) { player.hotkey2 = item; return; }
				if(item.equals("stcan")) { player.hotkey2 = item; return; }
				if(item.equals("garrafasuco")) { player.hotkey2 = item; return; }
			}
		}

		@Override
		public void input(String input) {

			if(!network) {
				lstChats.add(0, input);
				if(lstChats.size() > 10)
					lstChats.remove(lstChats.size() - 1);
			}
			else
			{
				if(input.contains(":")) { return; }
				if(input.contains("none")) { return; }
				String text = input;
				onlineresponse = gameControlHTML.OnlineManager("Chat",text,"");
			}
		}

		@Override
		public void canceled() {

		}

		@Override
		public boolean keyDown(int keycode) {
			if(playerDead) { return false; }
			if(player.playerSit.equals("yes")){ return false; }

			if(state.equals("Main")) {
				movement = true;
				downKeys.add(keycode);
		        if (downKeys.size >= 2){
		            onMultipleKeysDown(keycode);
		        }
		        if(downKeys.size == 1) {
		        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
		        		player.Side = "left";
		        		player.Walk = "walk";
		        		padmoveX = -85;
		        		player.playerInBattle = "no";
		        		return false;
		            }

		    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
		    			player.Side = "back";
		    			player.Walk = "walk";
		    			padmoveY = -65;
		    			player.playerInBattle = "no";
		    			return false;
		            }

		    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
		    			player.Side = "front";
		    			player.Walk = "walk";
		    			padmoveY = -85;
		    			player.playerInBattle = "no";
		    			return false;
		            }

		    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
		    			player.Side = "right";
		    			player.Walk = "walk";
		    			padmoveX = -75;
		    			player.playerInBattle = "no";
		    			return false;
		            }
		        }
			}



			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			movement = false;
			downKeys.remove(keycode);
			player.Walk = "no";
			player.Frame = 1;
			padmoveX = -80;
			padmoveY = -75;
			player.breakwalk = "";

			gameControlHTML.UpdatePlayer(player);

			if(player.Side.equals("left-front")) { player.Side = "front"; }
			if(player.Side.equals("left-back")) { player.Side = "front";}
			if(player.Side.equals("right-back")) { player.Side = "front";}
			if(player.Side.equals("right-front")) { player.Side = "front";}
			if(player.Side.equals("back-right")) { player.Side = "front";}
			if(player.Side.equals("back-left")) { player.Side = "front";}
			if(player.Side.equals("front-right")) { player.Side = "front";}
			if(player.Side.equals("front-left")) { player.Side = "front"; }

			return false;
		}

		private void onMultipleKeysDown (int mostRecentKeycode){

			if(state.equals("Menu")) { return; }

			//For multiple key presses
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		        	//player.Side = "left-front";
		        	player.Side = "left";
		        	player.Walk = "walk";
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		        	//player.Side = "left-back";
		        	player.Side = "left";
		        	player.Walk = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
		    		//player.Side = "right-back";
		    		player.Side = "right";
		    		player.Walk = "walk";
		    		padmoveX = -55;
		    		padmoveY = -50;
		    		player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
		    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
		    		//player.Side = "right-front";
		    		player.Side = "right";
		    		player.Walk = "walk";
		    		padmoveX = -55;
		    		padmoveY = -60;
		    		player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side = "back-right";
		        	player.Side = "back";
		        	player.Walk = "walk";
		        	padmoveX = -55;
		        	padmoveY = -50;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side = "back-left";
		        	player.Side = "back";
		        	player.Walk = "walk";
		        	padmoveX = -66;
		        	padmoveY = -50;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
		        	//player.Side = "front-right";
		        	player.Side = "front";
		        	player.Walk = "walk";
		        	padmoveX = -55;
		        	padmoveY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
		        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
		        	//player.Side = "front-left";
		        	player.Side = "front";
		        	player.Walk = "walk";
		        	padmoveX = -66;
		        	padmoveY = -60;
		        	player.playerInBattle = "no";
		        }
		    }
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int p1, int p2, int pointer, int button) {

			if(playerDead) { return false; }

			Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

			//Main
			//[Main State]//
			if(state.equals("Main")) {
				if(player.playerInCast.equals("no")) { movement = true; } else { movement = false; }

				if(selectAreaRanged) {
					touchSkillX = coordsTouch.x;
					touchSkillY = coordsTouch.y;
					selectAreaRanged = false;
					rangedAttack = true;
					CheckAreaRangedSkill();
					showZone = true;
					countZoneSkill = 40;
				}

				//Menu
				if(coordsTouch.x > cameraCoordsX - 99 && coordsTouch.x < cameraCoordsX - 61 && coordsTouch.y > cameraCoordsY + 57 && coordsTouch.y < cameraCoordsY + 96) {
					state = "Menu";
					return false;
				}

				//Action
				if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35)  {
					CheckAction();
					if(autoattack){
						autoattack = false;
					}
					else
					{
						autoattack = true;
					}
					return false;
				}
				//Block
				//if(coordsTouch.x > cameraCoordsX + 62 && coordsTouch.x < cameraCoordsX + 73 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
				//	return false;
				//}
				//Target
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY -60 && coordsTouch.y < cameraCoordsY -35) {
					ChangeTarget();
					return false;
				}
				//Skill 1
				if(coordsTouch.x > cameraCoordsX + 47 && coordsTouch.x < cameraCoordsX + 56 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(1);
					return false;
				}
				//Skill 2
				if(coordsTouch.x > cameraCoordsX + 63 && coordsTouch.x < cameraCoordsX + 72 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					if(skillUsed > 0) { return false; }
					skillUsed = 200;
					CheckSkill(2);
					return false;
				}
				//Skill 3
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 90 && coordsTouch.y < cameraCoordsY - 66) {
					return false;
				}
				//Sit
				if(coordsTouch.x > cameraCoordsX - 45 && coordsTouch.x < cameraCoordsX - 25 && coordsTouch.y > cameraCoordsY + 86 && coordsTouch.y < cameraCoordsY + 97) {
					if(!player.playerInBattle.equals("yes")) {
						if(player.playerSit.equals("none")) {
							player.playerSit = "yes";
						}
						else {
							player.playerSit = "none";
						}
					}
					return false;
				}

				//hotkey1
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY - 30 && coordsTouch.y < cameraCoordsY - 6) {
					if(!player.hotkey1.equals("none")) {
						gameControlHTML.UseItem(hotketcountitem1);
					}
					return false;
				}

				//hotkey2
				if(coordsTouch.x > cameraCoordsX + 79 && coordsTouch.x < cameraCoordsX + 89 && coordsTouch.y > cameraCoordsY + 1 && coordsTouch.y < cameraCoordsY + 24) {
					if(!player.hotkey2.equals("none")) {
						gameControlHTML.UseItem(hotketcountitem2);
					}
					return false;
				}

				//Descartar
				if(coordsTouch.x > cameraCoordsX - 12 && coordsTouch.x < cameraCoordsX + 17 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 48) {
					menuoption = "descartar";
					return false;
				}

				//Chat
				if(coordsTouch.x > cameraCoordsX - 60 && coordsTouch.x < cameraCoordsX - 49 && coordsTouch.y > cameraCoordsY + 77 && coordsTouch.y < cameraCoordsY + 97) {
					Gdx.input.getTextInput(this,"Digite sua mensagem","","");
					return false;
				}
			}

			if(state.equals("DungeonSelect")) {
				//Dungeon 1
				if(coordsTouch.x > cameraCoordsX - 47 && coordsTouch.x < cameraCoordsX + 32 && coordsTouch.y > cameraCoordsY + 17 && coordsTouch.y < cameraCoordsY + 34) {
					MapChange("Sewers");
					return false;
				}
				//Voltar
				if(coordsTouch.x > cameraCoordsX - 21 && coordsTouch.x < cameraCoordsX + 12 && coordsTouch.y > cameraCoordsY - 42 && coordsTouch.y < cameraCoordsY - 24) {
					state = "Main";
					return false;
				}
			}
			//[Menu State]//
			if(state.equals("Menu")) {

				//Voltar
				if(coordsTouch.x > cameraCoordsX + 68 && coordsTouch.x < cameraCoordsX + 82 && coordsTouch.y > cameraCoordsY + 69 && coordsTouch.y < cameraCoordsY + 84) {
					state = "Main";
					menuoption = "";
					return false;
				}

				//Hotkey 1
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 10 && coordsTouch.y > cameraCoordsY + 65 && coordsTouch.y < cameraCoordsY + 78) {
					menuoption = "hotkey1";
					return false;
				}

				//Hotkey 2
				if(coordsTouch.x > cameraCoordsX - 8 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 65 && coordsTouch.y < cameraCoordsY + 78) {
					menuoption = "hotkey2";
					return false;
				}

				//Descartar
				if(coordsTouch.x > cameraCoordsX - 12 && coordsTouch.x < cameraCoordsX + 17 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 48) {
					menuoption = "descartar";
					return false;
				}


				//Desligar touch
				if(coordsTouch.x > cameraCoordsX + 13 && coordsTouch.x < cameraCoordsX + 25 && coordsTouch.y > cameraCoordsY - 35 && coordsTouch.y < cameraCoordsY - 13) {
					if(controlstate.equals("PC")) { controlstate = "Mobile"; }
					else { controlstate = "PC"; }
					return false;
				}


				//Item 1
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(0,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(0,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(0); menuoption = ""; return false; }
					gameControlHTML.UseItem(0);
					return false;
				}

				//Item 2
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(1,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(1,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(1); menuoption = ""; return false; }
					gameControlHTML.UseItem(1);
					return false;
				}

				//Item 3
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(2,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(2,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(2); menuoption = ""; return false; }
					gameControlHTML.UseItem(2);
					return false;
				}

				//Item 4
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 42 && coordsTouch.y < cameraCoordsY + 64) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(3,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(3,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(3); menuoption = ""; return false; }
					gameControlHTML.UseItem(3);
					return false;
				}



				//Item 5
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(4,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(4,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(4); menuoption = ""; return false; }
					gameControlHTML.UseItem(4);
					return false;
				}

				//Item 6
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(5,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(5,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(5); menuoption = ""; return false; }
					gameControlHTML.UseItem(5);
					return false;
				}

				//Item 7
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(6,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(6,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(6); menuoption = ""; return false; }
					gameControlHTML.UseItem(6);
					return false;
				}

				//Item 8
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY + 19 && coordsTouch.y < cameraCoordsY + 39) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(7,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(7,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(7); menuoption = ""; return false; }
					gameControlHTML.UseItem(7);
					return false;
				}

				//Item 9
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(8,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(8,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(8); menuoption = ""; return false; }
					gameControlHTML.UseItem(8);
					return false;
				}

				//Item 10
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(9,1); menuoption = ""; return false; }
					if(menuoption.equals("hotkey2")) { HotKeyItem(9,2); menuoption = ""; return false; }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(9); menuoption = ""; return false; }
					gameControlHTML.UseItem(9);
					return false;
				}

				//Item 11
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(10,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(10,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(10); menuoption = ""; return false; }
					gameControlHTML.UseItem(10);
					return false;
				}

				//Item 12
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY - 5 && coordsTouch.y < cameraCoordsY + 15) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(11,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(11,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(11); menuoption = ""; return false; }
					gameControlHTML.UseItem(11);
					return false;
				}

				//Item 13
				if(coordsTouch.x > cameraCoordsX - 44 && coordsTouch.x < cameraCoordsX - 32 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(12,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(12,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(12); menuoption = ""; return false; }
					gameControlHTML.UseItem(12);
					return false;
				}

				//Item 14
				if(coordsTouch.x > cameraCoordsX - 30 && coordsTouch.x < cameraCoordsX - 18 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(13,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(13,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(13); menuoption = ""; return false; }
					gameControlHTML.UseItem(13);
					return false;
				}

				//Item 15
				if(coordsTouch.x > cameraCoordsX - 16 && coordsTouch.x < cameraCoordsX - 4 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(14,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(14,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(14); menuoption = ""; return false; }
					gameControlHTML.UseItem(14);
					return false;
				}

				//Item 16
				if(coordsTouch.x > cameraCoordsX - 3 && coordsTouch.x < cameraCoordsX + 10 && coordsTouch.y > cameraCoordsY - 8 && coordsTouch.y < cameraCoordsY - 30) {
					if(menuoption.equals("hotkey1")) { HotKeyItem(15,1); menuoption = ""; return false;  }
					if(menuoption.equals("hotkey2")) { HotKeyItem(15,2); menuoption = ""; return false;  }
					if(menuoption.equals("descartar")) { gameControlHTML.DiscartItem(15); menuoption = ""; return false; }
					gameControlHTML.UseItem(15);
					return false;
				}


				//Status point
				//STR
				if(coordsTouch.x > cameraCoordsX - 81 && coordsTouch.x < cameraCoordsX - 69 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Str");
					return false;
				}
				//VIT
				if(coordsTouch.x > cameraCoordsX - 67 && coordsTouch.x < cameraCoordsX - 55 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Vit");
					return false;
				}
				//AGI
				if(coordsTouch.x > cameraCoordsX - 54 && coordsTouch.x < cameraCoordsX - 41 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Agi");
					return false;
				}
				//WIS
				if(coordsTouch.x > cameraCoordsX - 40 && coordsTouch.x < cameraCoordsX - 27 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Wis");
					return false;
				}
				//DES
				if(coordsTouch.x > cameraCoordsX - 26 && coordsTouch.x < cameraCoordsX - 14 && coordsTouch.y > cameraCoordsY - 64 && coordsTouch.y < cameraCoordsY - 42) {
					CheckStatus("Dex");
					return false;
				}

				//unequip
				if(coordsTouch.x > cameraCoordsX + 68 && coordsTouch.x < cameraCoordsX + 80 && coordsTouch.y > cameraCoordsY + 40 && coordsTouch.y < cameraCoordsY + 62) {
					gameControlHTML.UnequipHat();
					return false;
				}

				//config
				if(coordsTouch.x > cameraCoordsX + 28 && coordsTouch.x < cameraCoordsX + 40 && coordsTouch.y > cameraCoordsY - 35 && coordsTouch.y < cameraCoordsY - 13) {
					onlineresponse = gameControlHTML.OnlineManager("Upload","","");
					if(onlineresponse.equals("Atualizado")) {
						msgShowTime = 100;
					}
					return false;
				}
			}

			//Shops
			if(state.equals("Shop")) {
				if(shopname.equals("refrishop")) {
					if(coordsTouch.x > cameraCoordsX - 61 && coordsTouch.x < cameraCoordsX - 47 && coordsTouch.y > cameraCoordsY + 37 && coordsTouch.y < cameraCoordsY + 59) {
						showbuymsg = gameControlHTML.CheckBuyItemStreetsA("refrishop", 1);
						return false;
					}
				}

				if(coordsTouch.x > cameraCoordsX + 51 && coordsTouch.x < cameraCoordsX + 61 && coordsTouch.y > cameraCoordsY + 60 && coordsTouch.y < cameraCoordsY + 75) {
					state = "Main";
					return false;
				}
			}

			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			movement = false;
			player.breakwalk = "";
			player.Walk = "no";
			padmoveX = -80;
			padmoveY = -75;

			if(player.Side.equals("left-front")) { player.Side = "front"; }
			if(player.Side.equals("left-back")) { player.Side = "front";}
			if(player.Side.equals("right-back")) { player.Side = "front";}
			if(player.Side.equals("right-front")) { player.Side = "front";}
			if(player.Side.equals("back-right")) { player.Side = "front";}
			if(player.Side.equals("back-left")) { player.Side = "front";}
			if(player.Side.equals("front-right")) { player.Side = "front";}
			if(player.Side.equals("front-left")) { player.Side = "front"; }
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			if(playerDead) { return false; }
			if(player.playerSit.equals("yes")){ return false; }

			Vector3 coordsTouch = camera.unproject(new Vector3(screenX,screenY,0));

			if(movement){
				//Right
     				if(coordsTouch.x >= cameraCoordsX -70 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side = "right";
					player.Walk = "walk";
					padmoveX = -75;
					player.playerInBattle = "no";
					return false;
				}
				//Left
     				if(coordsTouch.x >= cameraCoordsX -90 && coordsTouch.x <= cameraCoordsX -50 && coordsTouch.y > cameraCoordsY - 70 && coordsTouch.y < cameraCoordsY - 48) {
					player.Side = "left";
					player.Walk = "walk";
					padmoveX = -85;
					player.playerInBattle = "no";
					return false;
				}
				//Front
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 94 && coordsTouch.y < cameraCoordsY - 58) {
					player.Side = "front";
					player.Walk = "walk";
					padmoveY = -85;
					player.playerInBattle = "no";
					return false;
				}
				//Back
				if(coordsTouch.x > cameraCoordsX -80 && coordsTouch.x < cameraCoordsX -60 && coordsTouch.y > cameraCoordsY - 58 && coordsTouch.y < cameraCoordsY - 24) {
					player.Side = "back";
					player.Walk = "walk";
					padmoveY = -65;
					player.playerInBattle = "no";
					return false;
				}
			}
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void create() {
			// TODO Auto-generated method stub

		}

		@Override
		public void render() {
			// TODO Auto-generated method stub

		}

		@Override
		public void show() {
			// TODO Auto-generated method stub

		}

		@Override
		public void resize(int p1, int p2) {
			viewport.update(p1,p2);
			camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub

		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub

		}

		@Override
		public void hide() {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
		}

		@Override
		public boolean scrolled(float amountX, float amountY) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
		}



}
