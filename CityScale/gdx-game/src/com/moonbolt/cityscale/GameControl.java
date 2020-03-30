package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {
		
		//SUMMARY
		//Data Process//
		//Character Movement and Objects //
		// Scenario Objects //
		// Interfaces and Screens //
		// Monsters //
		// Battle Settings //
		// Itens Management //
		// Buffs and Debuffs //
		// NPCS and Quests //
		// Online Management//
	
		//Global Variables 
		private Json json;
		private FileHandle file;
		private Random randnumber;
		
		private String text;
		private String weapon;
		private String[] charData;
		private String[] itemUsage;
	    private String qtdItem;
	    private String textQuest = "";
	    private String nomeLoot = "";
	    
		
		private boolean inBattle = false;
		private boolean attackFrame = false;
		private boolean isCasting = false;
		private boolean castOver = false;
		
		private int showLootTime = 0;
		private int countA;
		private int countB;
		private int regenHPSP = 500;
		private int dmgWeapon;
		private int frameMove;
		private int pos;
		private int attackCooldown = 0;
		private int charNumActive;
		private int playerHP;
		private int playerHPMAX;
		private int playerMP;
		private int playerMPMAX;
		private int playerAtk;
		private int playerDef;
		private int playerStrenght;
		private int playerAgility;
		private int playerLucky;
		private int playerDextery;
		private int playerMind;
		private int playerAttackCooldown = 120;
		private int mobHP;
		private int mobAtk;
		private int mobDef;
		private int monsterEvade;
		private int mobAttackCooldown = 0;
		private int playerbattleframe = 0;
		private int playerManualAtkDelay = 40;
		private int spAttackMob = 0;
		private int delayTime = 0;
		private int castTime = 0;
		
	    private float npcframe = 1;
	    private float npcframe2 = 2;
	    private float npcframewalk = 1;
	    private float endright = 0;
	    private float endleft = 0;
		
		private float fX;
		private float fY;
		private float fUsable;
		private float mobX;
		private float mobY;
		private float mobAttackZoneXPlus;
		private float mobAttackZoneXMinus;
		private float mobAttackZoneYPlus;
		private float mobAttackZoneYMinus;
		private float pX;
		private float pY;
		private float pAttackZoneXPlus;
		private float pAttackZoneXMinus;
		private float pAttackZoneYPlus;
		private float pAttackZoneYMinus;
		private float posTouchSkillX;
		private float posTouchSkillY;
		
		private Player Character_Data;
		private Monster mobContainer;
		private Damage Dmg;
		private Skill skillContainer;
		private Skill skillUsed;
		
		private ArrayList<Monster> lstMonsters;
		private ArrayList<Damage> lstDamage;
		private ArrayList<Sprite> lstSpritesOnline;
		private ArrayList<Skill> lstSkills;
		private ArrayList<Buffs> lstBuffs;
		private ArrayList<Sprite> lstSprites;
		private ArrayList<String> lstNomes;
		private ArrayList<String> lstChats;
		
		private TextureAtlas atlas_hairs;
		private TextureAtlas atlas_gameplay_interface;
		private TextureAtlas atlas_btnskills;
		private TextureAtlas atlas_shop;
		private TextureAtlas atlas_objectsMetro;
		private TextureAtlas atlas_Mob;
		private TextureAtlas atlas_Usable;
		private TextureAtlas atlas_Loots;
		private TextureAtlas atlas_Npcs;
		private Sprite spr_master;
		private Texture tex_teste;
		
		//Map atlas
		private TextureAtlas atlas_Forest;
		
		//Weapons atlas
		private TextureAtlas atlas_nKnifes;
		private TextureAtlas atlas_swords;
		//Set atlas
		private TextureAtlas atlas_basic_male_set;
		private TextureAtlas atlas_basic_female_set;
		private TextureAtlas atlas_sets;
		
		
		//Online Variables
		private String sidePlayer = "";
		
		private boolean findplayerlist = false;
		
		private int posInjectorOnline;
		private int loopOnlineCheck = 0;
		
		
		
		private Player plOnline;
		private Monster mobOnline;
		private boolean PartyON = false;
		
		
		//Constructor//
		public GameControl(){
			charNumActive = 0;
			frameMove = 1;
			charData = new String[50];
			itemUsage = new String[2];
			
			
			
			tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
			spr_master = new Sprite(tex_teste);
			mobContainer = new Monster();
			skillContainer = new Skill();
			skillUsed = new Skill();
			
			//Instances of lists//
			lstMonsters = new ArrayList<Monster>();
			lstDamage = new ArrayList<Damage>();
			lstOnlinePlayers = new ArrayList<Player>();
			lstSkills = new ArrayList<Skill>();
			lstSprites = new ArrayList<Sprite>();
			lstNomes = new ArrayList<String>();
			lstBuffs = new ArrayList<Buffs>();
			lstChats = new ArrayList<String>();
			lstSpritesOnline = new ArrayList<Sprite>();
			
			//Online Instances//
			Player plOnline = new Player();
			Monster mobOnline = new Monster();
			
			////////Atlas Section//////
			//Character
			atlas_hairs = new TextureAtlas(Gdx.files.internal("data/characters/hair/hairs.txt"));
			atlas_basic_male_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_male/basic_set_male.txt"));
			atlas_basic_female_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_female/basic_female.txt"));
			
			//Gameplay
			atlas_gameplay_interface = new TextureAtlas(Gdx.files.internal("data/interface/gameplay/gameplay.txt"));
			atlas_objectsMetro = new TextureAtlas(Gdx.files.internal("data/assets/objects1.txt"));
			atlas_shop = new TextureAtlas(Gdx.files.internal("data/interface/shops/shops.txt"));
			atlas_btnskills = new TextureAtlas(Gdx.files.internal("data/interface/gameplay/skillicons.txt"));
			//Monsters
			atlas_Mob = new TextureAtlas(Gdx.files.internal("data/monsters/mobsForest.txt"));
			atlas_Forest = new TextureAtlas(Gdx.files.internal("data/monsters/mobsForest.txt"));
			
			//Itens
			atlas_Usable = new TextureAtlas(Gdx.files.internal("data/itens/Usables/Usables.txt"));		
			atlas_Loots = new TextureAtlas(Gdx.files.internal("data/itens/Loots/Loots.txt"));
			
			//NPCs
			atlas_Npcs = new TextureAtlas(Gdx.files.internal("data/characters/npcs/npcs.txt"));
			
			//Armas
			atlas_nKnifes = new TextureAtlas(Gdx.files.internal("data/itens/weapons/nknifes.txt"));
			atlas_swords = new TextureAtlas(Gdx.files.internal("data/itens/weapons/swords.txt"));
			
			//Equips
			atlas_sets = new TextureAtlas(Gdx.files.internal("data/itens/Sets/sets.txt"));
			////////Atlas Section//////
		}
		
		//[Data Process]//
		
		
		public Player AtualizaDadosPlayer() {
			return Character_Data;
		}
		
		
		
		
		
		//Character Movement and Objects //
		
		
		
		
		// Scenario Objects //
		public Sprite LoadObject(String name) {
			
			if(name.equals("metrobackword1")) {
				spr_master = atlas_objectsMetro.createSprite("metrobackword1");
			}
			if(name.equals("metrobackword2")) {
				spr_master = atlas_objectsMetro.createSprite("metrobackword2");
			}
			if(name.equals("metrobackword3")) {
				spr_master = atlas_objectsMetro.createSprite("metrobackword3");
			}
			
			if(name.equals("metroTV1")) {
				spr_master = atlas_objectsMetro.createSprite("metroTV1");
			}
			if(name.equals("metroTV2")) {
				spr_master = atlas_objectsMetro.createSprite("metroTV2");
			}
			if(name.equals("metroTV3")) {
				spr_master = atlas_objectsMetro.createSprite("metroTV3");
			}
			
			return spr_master;
		}
		
		
		// Interfaces and Screens //
		
		public void AssociaGrupo() {
			
		}
		
		public ArrayList<String> CarregaChats() {
			return lstChats;
		}
		
		public void InsereChat(String chatmsg) {
			lstChats.add(Character_Data.Name_A + ":" + chatmsg);
		}
		
		public ArrayList<Damage> ExibeDanos(){
			for(countA = 0; countA < lstDamage.size(); countA++){ 
				if(lstDamage.get(countA).time > 0){
			     lstDamage.get(countA).areaY = lstDamage.get(countA).areaY + 0.5f;
				 lstDamage.get(countA).time = lstDamage.get(countA).time - 1;
			     }
				 else {
					 lstDamage.remove(countA);
				 }
			}
			
			return lstDamage;
		}
		
		
		
		
		
		public void AtualizaCameraX(float cameraposX) {
			fX = cameraposX;
		}
		public void AtualizaCameraY(float cameraposY) {
			fY = cameraposY;
		}
		
		
		
		
		// Monsters //
		public void CarregaMonstrosMapa(String mapa) {
			lstMonsters.clear();
			
			if(mapa.equals("Streets305")) {
				lstMonsters.add(mobContainer.GetMonster("slimeA", "Streets305"));
			}
			
			if(mapa.equals("ForestArea")) {
				lstMonsters.add(mobContainer.GetMonster("slimeA", "ForestArea"));
				lstMonsters.add(mobContainer.GetMonster("beeA", "ForestArea"));
				lstMonsters.add(mobContainer.GetMonster("willowA", "ForestArea"));
				lstMonsters.add(mobContainer.GetMonster("oikplantA", "ForestArea"));
				lstMonsters.add(mobContainer.GetMonster("poroB", "ForestArea"));
				lstMonsters.add(mobContainer.GetMonster("beeB", "ForestArea"));
			}
		}
				
		public ArrayList<Sprite> ExibeMonstros(float playerX, float playerY) {
			
			lstSprites.clear();
			
			for(countA = 0; countA < lstMonsters.size(); countA++){	
				
				if(lstMonsters.get(countA).AGRESSIVE.equals("no")) { mobContainer = mobContainer.FrameAndMovement(lstMonsters.get(countA), pX, pY);}
				if(lstMonsters.get(countA).AGRESSIVE.equals("yes")) { mobContainer = mobContainer.FrameAndMovementAgressive(lstMonsters.get(countA), pX, pY);}
				
				mobX = Float.parseFloat(mobContainer.PX);
				mobY = Float.parseFloat(mobContainer.PY);
				
				if(mobX <= -48) { mobX = -48; }
				if(mobX >= 424) { mobX = 422; }
				if(mobY > 50) { mobY = 50; }
				if(mobY < -357) { mobY = -354; }
				
				
					if(mobContainer.NAME.equals("slime")) { atlas_Mob = atlas_Forest; }
					if(mobContainer.NAME.equals("bee")) { atlas_Mob = atlas_Forest; }
				
					if(lstMonsters.get(countA).SIDE.equals("right")) {
						
							if(mobContainer.FRAME.equals("1")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("2")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "2_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("3")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "3_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("4")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "4_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("5")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_damage_right");   
								spr_master.setPosition(mobX, mobY);
							}
						}
					
				        if(lstMonsters.get(countA).SIDE.equals("left")) {
							if(mobContainer.FRAME.equals("1")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("2")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "2_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("3")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "3_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("4")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "4_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("5")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_damage_left");   
								spr_master.setPosition(mobX, mobY);
							}
						}
					
					
					
					mobX = Float.parseFloat(mobContainer.WIDTH);
					mobY = Float.parseFloat(mobContainer.HEIGHT);
					
					spr_master.setSize(mobX, mobY);
					
					lstSprites.add(spr_master);
			}
			
			return lstSprites;
		}
		
		public ArrayList<String> ExibeNomesMonstros(){
			
			lstNomes.clear();
			
			for(countA = 0; countA < lstMonsters.size(); countA++){		
			
				lstNomes.add(lstMonsters.get(countA).NAME + "/" + lstMonsters.get(countA).HP + "/" + lstMonsters.get(countA).HPMAX + "/" + lstMonsters.get(countA).PX + "/" + lstMonsters.get(countA).PY);
			}
			
			return lstNomes;
		}
		
		
		
		//Respawn Monstros
		public void RespawnMonstros() {
			int respawn = 0;
			int respawnMax = 0;
			int HPMob = 0;
			int HPMAX = 0;
			int pXRandom = 0;
			int pYRandom = 0;
			float pYmob = 0;
			
			for(countA = 0; countA < lstMonsters.size(); countA++){
				HPMob = Integer.parseInt(lstMonsters.get(countA).HP);
				if(lstMonsters.get(countA).LOCKDEATH.equals("yes")){					
					lstMonsters.get(countA).PX = "-1200";
					lstMonsters.get(countA).PY = "-1000";
					respawn = Integer.parseInt(lstMonsters.get(countA).RESPAWN);
					respawnMax = Integer.parseInt(lstMonsters.get(countA).RESPAWNMAX);					
					respawn--;
					lstMonsters.get(countA).RESPAWN = String.valueOf(respawn);
					if(respawn < 0) {
						pXRandom = randnumber.nextInt(422);
						pYRandom = randnumber.nextInt(370);
						pYmob = pYRandom;
						pYmob = pYmob - (pYmob * 2);
						
						lstMonsters.get(countA).PX = String.valueOf(pXRandom);
						lstMonsters.get(countA).PY = String.valueOf(pYmob);
						lstMonsters.get(countA).RESPAWN = lstMonsters.get(countA).RESPAWNMAX;
						lstMonsters.get(countA).HP = lstMonsters.get(countA).HPMAX;
						lstMonsters.get(countA).MP = lstMonsters.get(countA).MPMAX;
						lstMonsters.get(countA).LOCKDEATH = "no";
						lstMonsters.get(countA).TARGET = "none";
					}
				}
			}
		}
		
		
		// Battle Settings //
		public void VerificaAttack(String AutoAttack, String ManualAttack) {
			
			pX = Float.parseFloat(Character_Data.PX_A);
			pY = Float.parseFloat(Character_Data.PY_A);
			playerHP  = Integer.parseInt(Character_Data.HP_A);
			playerAtk  = Integer.parseInt(Character_Data.Atk_A);
			playerDef  = Integer.parseInt(Character_Data.Def_A);
			playerStrenght  = Integer.parseInt(Character_Data.Strengh_A);
			playerAgility  = Integer.parseInt(Character_Data.Agility_A);
			playerLucky = Integer.parseInt(Character_Data.Lucky_A);
			playerDextery  = Integer.parseInt(Character_Data.Dextery_A);
			playerMind = Integer.parseInt(Character_Data.Mind_A);
			attackCooldown--;
			
			pX = pX + 7;
			pY = pY + 11;
			
			//Montando zona de attack do jogador
			if(Character_Data.Job_A.equals("Novice") ||
			   Character_Data.Job_A.equals("Swordman") ||
			   Character_Data.Job_A.equals("Merchant") ||
			   Character_Data.Job_A.equals("Thief") ||
			   Character_Data.Job_A.equals("Monk")) {
				
				pAttackZoneXPlus = pX + 20;
				pAttackZoneXMinus = pX - 20;
				pAttackZoneYPlus = pY + 30;
				pAttackZoneYMinus = pY - 30;		
			}
			
			if(Character_Data.Job_A.equals("Gunner") ||
			   Character_Data.Job_A.equals("Magician")) {
				pAttackZoneXPlus = pX + 60;
				pAttackZoneXMinus = pX - 60;
				pAttackZoneYPlus = pY + 70;
				pAttackZoneYPlus = pY - 70;
			}
			
			
			if(!isCasting){
			
			if(playerManualAtkDelay > 0) { playerManualAtkDelay--; }
			if(playerManualAtkDelay < 10) { attackFrame = false; }
			if(ManualAttack.equals("yes") && playerManualAtkDelay == 0){
				for(countA = 0; countA < lstMonsters.size(); countA++){ 		  //Verifica Monstro Target dentro da lista			
					if(lstMonsters.get(countA).ID.equals(Character_Data.Target_A)) {
						mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
						mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
						if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {						
								
							//Posiciona personagem baseado no monstro
							if(pX > mobX) { Character_Data.Battle_A = "yes_Left"; attackFrame = true; lstMonsters.get(countA).FRAME = "5"; }
							if(pX < mobX) { Character_Data.Battle_A = "yes_Right"; attackFrame = true; lstMonsters.get(countA).FRAME = "5"; }
							
							mobHP = Integer.parseInt(lstMonsters.get(countA).HP);		
							mobHP = mobHP - 1;		

							Damage danoMob = new Damage();
							danoMob.areaX = Math.round(mobX);
							danoMob.areaY = Math.round(mobY);
							danoMob.dano = String.valueOf("1");
							danoMob.time = 60;
							danoMob.Color = "Yellow";
							danoMob.Descritivo = "Ataque"; 
							lstDamage.add(danoMob);
                                
							if(mobHP <= 0 && lstMonsters.get(countA).LOCKDEATH.equals("no")) { 
								GiveExp(lstMonsters.get(countA));
								GiveLoot(lstMonsters.get(countA));
								GiveMoney(lstMonsters.get(countA));
								lstMonsters.get(countA).LOCKDEATH = "yes"; 
								Character_Data.Battle_A = "no";
								Character_Data.Target_A = "none"; 
								Character_Data.Battle_A = "none";
								inBattle = false; 
								lstMonsters.get(countA).BATTLE = "no";
							}
							lstMonsters.get(countA).HP = String.valueOf(mobHP);
							if(onlineCheck) { OperacaoOnline("Atk", lstMonsters.get(countA).ID + ":" + lstMonsters.get(countA).HP); }
							playerManualAtkDelay = 50;
							return;
				        }
					}
				}
			}
			
			
			if(AutoAttack.equals("no")) { Character_Data.Target_A = "none"; inBattle = false; Character_Data.Battle_A = "none"; playerAttackCooldown = 120; }
			
			if(AutoAttack.equals("yes")) {
				//Verifica se ainda estï¿½ em batalha
				if(Character_Data.Target_A.equals("none")) {  //Verifica os monstros na lista pra adicionar o target
					for(countA = 0; countA < lstMonsters.size(); countA++){ 
						mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
						mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
						if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {				  
							Character_Data.Target_A = lstMonsters.get(countA).ID;
							inBattle  = true;					
							lstMonsters.get(countA).BATTLE = "yes";
							lstMonsters.get(countA).TARGET = Character_Data.Name_A;
							
							//Calcula ATK Cooldown
							playerAttackCooldown = playerAttackCooldown - (playerAgility * 3);  //calcula o cooldown baseado na agi
						}						
					}
				}
				if(!Character_Data.Target_A.equals("none")) {
					playerAttackCooldown--;  //Diminui cooldown 
					
					//Reseta AutoAttack Frame
					if(playerAttackCooldown < -30) { 
						attackFrame = false; 
						playerAttackCooldown = 120; // 120
						playerAttackCooldown = playerAttackCooldown - (playerAgility * 3); 
					}
					
					for(countA = 0; countA < lstMonsters.size(); countA++){ 		  //Verifica Monstro Target dentro da lista			
						if(lstMonsters.get(countA).ID.equals(Character_Data.Target_A)) {
							mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
							mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
							if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {						
								
								//Posiciona personagem baseado no monstro
								if(pX > mobX) { Character_Data.Battle_A = "yes_Left";}
								if(pX < mobX) { Character_Data.Battle_A = "yes_Right";}
								
								//Liga AutoAttack
								if(playerAttackCooldown < 0) { 
									attackFrame = true; 
								}
								
								//Verifica Classe e atribui o attack
								if(playerAttackCooldown == 0) {
								dmgWeapon = DanoArma();
								
									if(Character_Data.Job_A.equals("Novice")) {
										playerAtk = playerAtk + (playerStrenght * 2);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);		
										
										Damage danoMob = new Damage();
										danoMob.areaX = Math.round(mobX);
										danoMob.areaY = Math.round(mobY);
										danoMob.dano = String.valueOf(playerAtk);
										danoMob.time = 60;
										danoMob.Color = "Yellow";
										danoMob.Descritivo = "Ataque";
										lstDamage.add(danoMob);
										
										
										if(mobHP <= 0 && lstMonsters.get(countA).LOCKDEATH.equals("no")) { 
											GiveExp(lstMonsters.get(countA));
											GiveLoot(lstMonsters.get(countA));
											GiveMoney(lstMonsters.get(countA));
											lstMonsters.get(countA).LOCKDEATH = "yes"; 
											Character_Data.Battle_A = "no";
											Character_Data.Target_A = "none"; 
											Character_Data.Battle_A = "none";
											inBattle = false; 
											lstMonsters.get(countA).BATTLE = "no";
										}
										lstMonsters.get(countA).HP = String.valueOf(mobHP);
										lstMonsters.get(countA).FRAME = "5";
									}
								
									if(Character_Data.Job_A.equals("Swordman")) {
										playerAtk = playerAtk + (playerStrenght * 3) + (playerDextery * 2) + (playerLucky);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);	
										
									}
									
									if(Character_Data.Job_A.equals("Magician")) {
										playerAtk = playerAtk + (playerMind * 4) + (playerDextery) + (playerLucky);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Gunner")) {
										playerAtk = playerAtk + (playerDextery * 3) + (playerLucky * 2) + (playerStrenght);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Thief")) {
										playerAtk = playerAtk + (playerStrenght * 2) + (playerDextery) + (playerLucky * 3);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Beater")) {
										playerAtk = playerAtk + (playerStrenght * 4) + (playerDextery * 2) + (playerLucky);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Juggle")) {
										playerAtk = playerAtk + (playerStrenght * 2) + (playerDextery) + (playerMind * 2) + (playerAgility);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(onlineCheck) { OperacaoOnline("Atk", lstMonsters.get(countA).ID + ":" + lstMonsters.get(countA).HP); }
									
								}
							}						
						}
					}
				}	
			}
			
			}// is casting
			
			//Para Monstros
			for(countA = 0; countA < lstMonsters.size(); countA++){ 		  //Verifica Monstro Target dentro da lista			
				if(lstMonsters.get(countA).TARGET.equals(Character_Data.Name_A)) {
					
					mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
					mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
					
					mobAttackZoneXPlus = mobX + Integer.parseInt(lstMonsters.get(countA).ATKZONEXPLUS);
					mobAttackZoneXMinus = mobX - Integer.parseInt(lstMonsters.get(countA).ATKZONEXMINUS);
					mobAttackZoneYPlus = mobY + Integer.parseInt(lstMonsters.get(countA).ATKZONEYPLUS);
					mobAttackZoneYMinus = mobY - Integer.parseInt(lstMonsters.get(countA).ATKZONEYMINUS);
					
					if((pX > mobAttackZoneXMinus && pX < mobAttackZoneXPlus) && (pY > mobAttackZoneYMinus && pY < mobAttackZoneYPlus) ) {						
								
					mobAttackCooldown = Integer.parseInt(lstMonsters.get(countA).AUTOATK);
					mobAttackCooldown--;
					lstMonsters.get(countA).AUTOATK = String.valueOf(mobAttackCooldown);
					if(mobAttackCooldown == 0) {
						lstMonsters.get(countA).FRAME = "4";
						mobAtk = Integer.parseInt(lstMonsters.get(countA).ATK);
						
						playerHP = Integer.parseInt(Character_Data.HP_A);
						playerHP = playerHP - mobAtk;
						Character_Data.HP_A = String.valueOf(playerHP);
						
					    Damage danoMob = new Damage();
						danoMob.areaX = Math.round(pX);
						danoMob.areaY = Math.round(pY);
						danoMob.dano = String.valueOf(mobAtk);
						danoMob.Color = "Red";
						danoMob.time = 60;
						danoMob.Descritivo = "Ataque";
						lstDamage.add(danoMob);
	     			}
					
					if(mobAttackCooldown < -20) { 
						lstMonsters.get(countA).AUTOATK = lstMonsters.get(countA).AUTOATKBASE; 
						lstMonsters.get(countA).FRAME = "1"; 
					}		
					
					}
				}
			}
		}
		
		public void GiveExp(Monster mob) {
			
			int exp = Integer.parseInt(mob.EXP);
			int expPlayer = Integer.parseInt(Character_Data.Exp_A);
			int levelPlayer = Integer.parseInt(Character_Data.Level_A);
			int pontosStatus = Integer.parseInt(Character_Data.StatusPoint_A);
			expPlayer = expPlayer + exp;
			
			
			if(levelPlayer == 5) { return; }
			
			if(expPlayer >= 100 && levelPlayer == 1) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
			if(expPlayer >= 350 && levelPlayer == 2) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
			if(expPlayer >= 520 && levelPlayer == 3) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
			if(expPlayer >= 780 && levelPlayer == 4) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 1220 && levelPlayer == 5) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 2500 && levelPlayer == 6) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 5600 && levelPlayer == 7) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 9500 && levelPlayer == 8) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 15200 && levelPlayer == 9) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 23400 && levelPlayer == 10) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 26200 && levelPlayer == 11) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 32000 && levelPlayer == 12) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 35000 && levelPlayer == 13) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 39000 && levelPlayer == 14) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 43000 && levelPlayer == 15) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 46400 && levelPlayer == 16) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 52000 && levelPlayer == 17) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 57000 && levelPlayer == 18) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 59000 && levelPlayer == 19) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 65000 && levelPlayer == 20) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 72000 && levelPlayer == 21) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 74000 && levelPlayer == 22) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 79000 && levelPlayer == 23) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 82000 && levelPlayer == 24) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 85000 && levelPlayer == 25) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 89000 && levelPlayer == 26) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 92340 && levelPlayer == 27) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 97420 && levelPlayer == 28) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 110342 && levelPlayer == 29) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 130020 && levelPlayer == 30) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 150000 && levelPlayer == 31) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 180900 && levelPlayer == 32) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 223100 && levelPlayer == 33) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 255221 && levelPlayer == 34) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 290111 && levelPlayer == 35) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 339999 && levelPlayer == 36) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 496554 && levelPlayer == 37) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 532312 && levelPlayer == 38) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 699992 && levelPlayer == 39) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 739231 && levelPlayer == 40) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 892312 && levelPlayer == 41) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 1324230 && levelPlayer == 42) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			if(expPlayer >= 1923120 && levelPlayer == 43) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			if(expPlayer >= 3245235 && levelPlayer == 44) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			if(expPlayer >= 5522332 && levelPlayer == 45) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			if(expPlayer >= 8023422 && levelPlayer == 46) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			if(expPlayer >= 11203245 && levelPlayer == 47) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			if(expPlayer >= 19064345 && levelPlayer == 48) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			if(expPlayer >= 36543199 && levelPlayer == 49) { levelPlayer++; pontosStatus = pontosStatus + 8; expPlayer = 0;}
			
			Character_Data.Exp_A = String.valueOf(expPlayer);
			Character_Data.Level_A = String.valueOf(levelPlayer);
			Character_Data.StatusPoint_A = String.valueOf(pontosStatus);
		}
		
		private void ResetaPontosStatus(){
			
			Character_Data.Strengh_A = "1";
			Character_Data.Vitality_A = "1";
			Character_Data.Agility_A = "1";
			Character_Data.Dextery_A = "1";
			Character_Data.Lucky_A = "1";
			Character_Data.Mind_A = "1";
			Character_Data.Resistence_A = "1";
			Character_Data.HPMAX_A = "100";
			Character_Data.MPMAX_A = "100";
			Character_Data.Stamina_A = "100";
			Character_Data.StatusPoint_A = "0";
			
			int levelPlayer = Integer.parseInt(Character_Data.Level_A);
			int pontosStatus = 0;
			
			if(levelPlayer >= 2){ pontosStatus = pontosStatus + 3; }
			if(levelPlayer >= 3){ pontosStatus = pontosStatus + 3; }
			if(levelPlayer >= 4){ pontosStatus = pontosStatus + 4; }
			if(levelPlayer >= 5){ pontosStatus = pontosStatus + 4;}
			if(levelPlayer >= 6){ pontosStatus = pontosStatus + 4;}
			if(levelPlayer >= 7){ pontosStatus = pontosStatus + 4;}
			if(levelPlayer >= 8){ pontosStatus = pontosStatus + 4;}
			if(levelPlayer >= 9){ pontosStatus = pontosStatus + 4;}
			if(levelPlayer >= 10){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 11){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 12){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 13){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 14){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 15){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 16){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 17){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 18){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 19){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 20){ pontosStatus = pontosStatus + 5;}
			if(levelPlayer >= 21){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 22){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 23){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 24){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 25){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 26){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 27){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 28){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 29){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 30){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 31){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 32){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 33){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 34){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 35){ pontosStatus = pontosStatus + 6;}
			if(levelPlayer >= 36){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 37){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 38){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 39){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 40){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 41){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 42){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 43){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 44){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 45){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 46){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 47){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 48){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 49){ pontosStatus = pontosStatus + 7;}
			if(levelPlayer >= 50){ pontosStatus = pontosStatus + 7;}
			
			
		}
		
		public int DanoArma() {
			text = Character_Data.Weapon_A;
			
			if(text.equals("Basic_Sword")) { return 2; }
			if(text.equals("Basic_Rod")) { return 2; }
			if(text.equals("Basic_Knife")) { return 2; }
			if(text.equals("Basic_Axe")) { return 2; }
			if(text.equals("Basic_Pistols")) { return 2; }
			if(text.equals("Basic_Knuckles")) { return 2; }
			
			return 0;
		}
		
		public int VerificaCooldown() {
			return playerAttackCooldown;
		}
		
		
		
		public int delayinfo() {
			return delayTime;
		}
		
		public void CalculaCooldown() {
			if(delayTime > 0) { delayTime--; }
			if(delayTime <= 0) { delayTime = 0; }
		}
		
		public boolean VerificaRangedSkill(int numSkill) {
			Skill skillUsed = new Skill();
			
			//Novice
			if(numSkill == 1 && Character_Data.Job_A.equals("Novice")) { skillUsed.IsRangedSkill("tripleattack"); }
			
			//Swordman
			if(numSkill == 1 && Character_Data.Job_A.equals("Swordman")) { skillUsed.IsRangedSkill("flysword"); }
			if(numSkill == 2 && Character_Data.Job_A.equals("Swordman")) { skillUsed.IsRangedSkill("healthboost"); }
			if(numSkill == 3 && Character_Data.Job_A.equals("Swordman")) { skillUsed.IsRangedSkill("havenblade"); }
			if(numSkill == 4 && Character_Data.Job_A.equals("Swordman")) { skillUsed.IsRangedSkill("ironshield"); }
			if(numSkill == 5 && Character_Data.Job_A.equals("Swordman")) { skillUsed.IsRangedSkill("protect"); }
			
			//Mage
			if(numSkill == 1 && Character_Data.Job_A.equals("Magician")) { skillUsed.IsRangedSkill("fireball"); }
			if(numSkill == 2 && Character_Data.Job_A.equals("Magician")) { skillUsed.IsRangedSkill("icecrystal"); }
			if(numSkill == 3 && Character_Data.Job_A.equals("Magician")) { skillUsed.IsRangedSkill("thundercloud"); }
			if(numSkill == 4 && Character_Data.Job_A.equals("Magician")) { skillUsed.IsRangedSkill("rockbound"); }
			if(numSkill == 5 && Character_Data.Job_A.equals("Magician")) { skillUsed.IsRangedSkill("soulclash"); }
			
			//Thief
			if(numSkill == 1 && Character_Data.Job_A.equals("Thief")) { skillUsed.IsRangedSkill("invisibility"); }
			if(numSkill == 2 && Character_Data.Job_A.equals("Thief")) { skillUsed.IsRangedSkill("poisonhit"); }
			if(numSkill == 3 && Character_Data.Job_A.equals("Thief")) { skillUsed.IsRangedSkill("dashkick"); }
			if(numSkill == 4 && Character_Data.Job_A.equals("Thief")) { skillUsed.IsRangedSkill("steal"); }
			if(numSkill == 5 && Character_Data.Job_A.equals("Thief")) { skillUsed.IsRangedSkill("doublehit"); }
			
			//Artist
			if(numSkill == 1 && Character_Data.Job_A.equals("Artist")) { skillUsed.IsRangedSkill("drawcard"); }
			if(numSkill == 2 && Character_Data.Job_A.equals("Artist")) { skillUsed.IsRangedSkill("spellstep"); }
			if(numSkill == 3 && Character_Data.Job_A.equals("Artist")) { skillUsed.IsRangedSkill("creditdance"); }
			if(numSkill == 4 && Character_Data.Job_A.equals("Artist")) { skillUsed.IsRangedSkill("malabarism"); }
			if(numSkill == 5 && Character_Data.Job_A.equals("Artist")) { skillUsed.IsRangedSkill("amplitude"); }
			
			//gunner
			if(numSkill == 1 && Character_Data.Job_A.equals("Gunner")) { skillUsed.IsRangedSkill("bulletrain"); }
			if(numSkill == 2 && Character_Data.Job_A.equals("Gunner")) { skillUsed.IsRangedSkill("healthboost"); }
			if(numSkill == 3 && Character_Data.Job_A.equals("Gunner")) { skillUsed.IsRangedSkill("precision"); }
			if(numSkill == 4 && Character_Data.Job_A.equals("Gunner")) { skillUsed.IsRangedSkill("mine"); }
			if(numSkill == 5 && Character_Data.Job_A.equals("Gunner")) { skillUsed.IsRangedSkill("fastshot"); }
			
			//Beater
			if(numSkill == 1 && Character_Data.Job_A.equals("Beater")) { skillUsed.IsRangedSkill("hammercrash"); }
			if(numSkill == 2 && Character_Data.Job_A.equals("Beater")) { skillUsed.IsRangedSkill("overpower"); }
			if(numSkill == 3 && Character_Data.Job_A.equals("Beater")) { skillUsed.IsRangedSkill("boundrage"); }
			if(numSkill == 4 && Character_Data.Job_A.equals("Beater")) { skillUsed.IsRangedSkill("berserk"); }
			if(numSkill == 5 && Character_Data.Job_A.equals("Beater")) { skillUsed.IsRangedSkill("impound"); }
			
			//Doctor
			if(numSkill == 1 && Character_Data.Job_A.equals("Medic")) { skillUsed.IsRangedSkill("heal"); }
			if(numSkill == 2 && Character_Data.Job_A.equals("Medic")) { skillUsed.IsRangedSkill("atkboost"); }
			if(numSkill == 3 && Character_Data.Job_A.equals("Medic")) { skillUsed.IsRangedSkill("defboost"); }
			if(numSkill == 4 && Character_Data.Job_A.equals("Medic")) { skillUsed.IsRangedSkill("regen"); }
			if(numSkill == 5 && Character_Data.Job_A.equals("Medic")) { skillUsed.IsRangedSkill("holyprism"); }
			
			
			
			return false;
		}
		
		public void VerificaSkillDano(Skill sk, float areaselectedX, float areaselectedY) {
			
			pX = Float.parseFloat(Character_Data.PX_A);
			pY = Float.parseFloat(Character_Data.PY_A);
			playerHP  = Integer.parseInt(Character_Data.HP_A);
			playerAtk  = Integer.parseInt(Character_Data.Atk_A);
			playerDef  = Integer.parseInt(Character_Data.Def_A);
			playerStrenght  = Integer.parseInt(Character_Data.Strengh_A);
			playerAgility  = Integer.parseInt(Character_Data.Agility_A);
			playerLucky = Integer.parseInt(Character_Data.Lucky_A);
			playerDextery  = Integer.parseInt(Character_Data.Dextery_A);
			playerMind = Integer.parseInt(Character_Data.Mind_A);
			int mobHP  = 0;
			int dmg = 0;
			attackCooldown--;
			
			pX = pX + 7;
			pY = pY + 11;
			
			//Montando zona de attack do jogador
			if(Character_Data.Job_A.equals("Novice") ||
			   Character_Data.Job_A.equals("Swordman") ||
			   Character_Data.Job_A.equals("Beater") ||
			   Character_Data.Job_A.equals("Thief") ||
			   Character_Data.Job_A.equals("Medic")) {
				
				pAttackZoneXPlus = pX + 20;
				pAttackZoneXMinus = pX - 20;
				pAttackZoneYPlus = pY + 30;
				pAttackZoneYMinus = pY - 30;		
			}
			
			if(Character_Data.Job_A.equals("Gunner") ||
			   Character_Data.Job_A.equals("Magician")) {
				pAttackZoneXPlus = pX + 60;
				pAttackZoneXMinus = pX - 60;
				pAttackZoneYPlus = pY + 70;
				pAttackZoneYPlus = pY - 70;
			}
			
			if(sk.isAreaSkill == true) {
				for(countA = 0; countA < lstMonsters.size(); countA++){ //Verifica Monstro Target dentro da �rea do range	
					mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
					mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
					
					
					pAttackZoneXMinus = (areaselectedX - sk.areaSpreadX);
					pAttackZoneXPlus = (areaselectedX + sk.areaSpreadX);
					pAttackZoneYMinus = (areaselectedY - sk.areaSpreadY);
					pAttackZoneYPlus = (areaselectedY + sk.areaSpreadY);
					
					
					if(pX > mobX) { Character_Data.Battle_A = "yes_Left";}
					if(pX < mobX) { Character_Data.Battle_A = "yes_Right";}	
					if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {
						
						//Posiciona personagem baseado no monstro
						if(pX > mobX) { Character_Data.Battle_A = "yes_Left";}
						if(pX < mobX) { Character_Data.Battle_A = "yes_Right";}
						
						sk.posX = (int) mobX;
						sk.posY = (int) mobY;
						mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
						dmg = sk.CalculaDanoSkill(sk, Character_Data);
						mobHP = mobHP - dmg;
						lstMonsters.get(countA).HP = String.valueOf(mobHP);
						
						Damage danoSkill = new Damage();
						danoSkill.areaX = Math.round(mobX);
						danoSkill.areaY = Math.round(mobY);
						danoSkill.dano = String.valueOf(dmg);
						danoSkill.Color = "Red";
						danoSkill.time = 60;
						danoSkill.Descritivo = "Ataque";
						lstDamage.add(danoSkill);
						lstSkills.add(sk);
						attackFrame = true;
						playerManualAtkDelay = 20;
						delayTime = sk.delay;						
					}					
				}
			}
			else {
				for(countA = 0; countA < lstMonsters.size(); countA++){ 		  //Verifica Monstro Target dentro da lista			
					if(lstMonsters.get(countA).ID.equals(Character_Data.Target_A)) {
						mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
						mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
						if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {						
							
							//Posiciona personagem baseado no monstro
							if(pX > mobX) { Character_Data.Battle_A = "yes_Left";}
							if(pX < mobX) { Character_Data.Battle_A = "yes_Right";}
							
							sk.posX = (int) mobX - 10;
							sk.posY = (int) mobY;
							mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
							dmg = sk.CalculaDanoSkill(sk, Character_Data);
							mobHP = mobHP - dmg;
							lstMonsters.get(countA).HP = String.valueOf(mobHP);
							
							Damage danoSkill = new Damage();
							danoSkill.areaX = Math.round(mobX);
							danoSkill.areaY = Math.round(mobY);
							danoSkill.dano = String.valueOf(dmg);
							danoSkill.Color = "Yellow";
							danoSkill.time = 60;
							danoSkill.Descritivo = "Ataque";
							lstDamage.add(danoSkill);
							lstSkills.add(sk);
							attackFrame = true;
							playerManualAtkDelay = 20;
							delayTime = sk.delay;
						}
					}
				}
			}
		}
		
		public ArrayList<Skill> RetornaSkills(){
			return lstSkills;
		}
		
		public Sprite ImageSkill(Skill sk) {			
			spr_master = skillContainer.CarregaEfeitoFrame(sk.nameSkill, sk.countFrameEffect);			
			return spr_master;
		}
		
		public void RemoveSkill(int num) {
			skillOnline = "";
			lstSkills.remove(num);
		}
		
		
		// Itens Management //
		public Sprite ItemImage(String item, int pos, float ccX, float ccY){
			
			itemUsage = item.split("#");
			item = itemUsage[0];
			text = item.replace("[","");
			
			if(text.equals("None")) { return spr_master; }
			
			if(text.equals("Refrigerante")) {
				spr_master = atlas_Usable.createSprite("Cola");
			}
			
			qtdItem = itemUsage[1].replace("]","");
			
			if(pos == 0 || pos == 12 || pos == 24 || pos == 36 || pos == 48) { spr_master.setPosition(ccX - 59, ccY - 3); }
			if(pos == 1 || pos == 13 || pos == 25 || pos == 37 || pos == 49) { spr_master.setPosition(ccX - 45, ccY - 3); }
			if(pos == 2 || pos == 14 || pos == 26 || pos == 38 || pos == 50) { spr_master.setPosition(ccX - 31, ccY - 3); }
			if(pos == 3 || pos == 15 || pos == 27 || pos == 39 || pos == 51) { spr_master.setPosition(ccX - 17, ccY - 3); }
			if(pos == 4 || pos == 16 || pos == 28 || pos == 40 || pos == 52) { spr_master.setPosition(ccX - 59, ccY - 29); }
			if(pos == 5 || pos == 17 || pos == 29 || pos == 41 || pos == 53) { spr_master.setPosition(ccX - 45, ccY - 29); }
			if(pos == 6 || pos == 18 || pos == 30 || pos == 42 || pos == 54) { spr_master.setPosition(ccX - 31, ccY - 29); }
			if(pos == 7 || pos == 19 || pos == 31 || pos == 43 || pos == 55) { spr_master.setPosition(ccX - 17, ccY - 29); }
			if(pos == 8 || pos == 20 || pos == 32 || pos == 44 || pos == 56) { spr_master.setPosition(ccX - 59, ccY - 55); }
			if(pos == 9 || pos == 21 || pos == 33 || pos == 45 || pos == 57) { spr_master.setPosition(ccX - 45, ccY - 55); }
			if(pos == 10 || pos == 22 || pos == 34 || pos == 46 || pos == 58) { spr_master.setPosition(ccX - 31, ccY - 55); }
			if(pos == 11 || pos == 23 || pos == 35 || pos == 47 || pos == 59) { spr_master.setPosition(ccX - 17, ccY - 55); }
			spr_master.setSize(15, 30);
			return spr_master;
		}
		
		public Sprite ItemEquipped(String item, float ccX, float ccY) {
			
			String set = Character_Data.Set_A;
			String weapon = Character_Data.Weapon_A;
			String hat = Character_Data.Hat_A;
			
			if(item.equals("weapon")) { 
				if(weapon.equals("basic_knife")) { spr_master = atlas_nKnifes.createSprite("basic_knife_right"); }			
				spr_master.setPosition(ccX - 7, ccY + 26);
				spr_master.setSize(25, 40);
			}
			
			if(item.equals("set")) { 
				if(set.equals("basic_set_male")) { spr_master = atlas_sets.createSprite("basicsetmale"); }
				if(set.equals("basic_set_female")) { spr_master = atlas_sets.createSprite("basicsetfemale"); }
				
				spr_master.setPosition(ccX + 9, ccY + 27);
				spr_master.setSize(20, 30);
			}
			
			if(item.equals("hat")) { 
				if(hat.equals("none")) { return spr_master = null; }
				
			}
			
			
			return spr_master;
		}
		
		public String ItemQuantidade() {	
			return qtdItem;
		}
		
		public String ItemDescritivo(String item){
			if(item.equals("Refrigerante")){ return "Restaura 20 de HP";}
			if(item.equals("SucoHP")){ return "Restaura 90 de HP";}
			if(item.equals("IoguteHP")){ return "Restaura 200 de HP";}
			if(item.equals("RefrigeranteMP")){ return "Restaura 20 de MP";}
			if(item.equals("SucoMP")){ return "Restaura 50 de MP";}
			if(item.equals("IoguteMP")){ return "Restaura 100 de MP";}
			return "";
		}
		
		public void VerificaItemSelecionado(int menuTab, int numItem) {
			
			//[SucoHP#3]-[SucoHP#3]
			
			String[] lstItem = Character_Data.Itens_A.split("-");
			String[] splitItem;
			String nomeItem = "";
			String qtdString = "";
			String tipoItem = "";
			String backItens = "";
			int itemSelecionado = numItem;
			int qtd;
			
			
			//Consolida o item
			if(lstItem[itemSelecionado].equals("[None]")) { return; }
			splitItem = lstItem[itemSelecionado].split("#");
			nomeItem = splitItem[0].replace("[", "");
			qtdString = splitItem[1].replace("]", "");
			qtd = Integer.parseInt(qtdString);
			tipoItem = VerificaTipo(nomeItem);
			if(tipoItem.equals("usable")) { 
				ItemEfeito(nomeItem);
				qtd = qtd -1;
				if(qtd == 0) { 
					lstItem[itemSelecionado] = "[None]"; 
					backItens = Arrays.toString(lstItem).replace(", ","-");
					backItens = backItens.substring(1, backItens.length() -1);
					Character_Data.Itens_A = backItens; 
					return;
				}
				if(qtd > 0) { 
					lstItem[itemSelecionado] = "[" + nomeItem + "#" + qtd + "]"; 
					backItens = Arrays.toString(lstItem).replace(", ","-");
					backItens = backItens.substring(1, backItens.length() -1);
					Character_Data.Itens_A = backItens; 
					return; 
				}						
			}
		}
		
		private String VerificaTipo(String item) {
			
			if(item.equals("Refrigerante")){
				return "usable";
			}
			
			return "";
		}
		
		private void EquipaItem() {
			
		}
		
		private void ItemEfeito(String item){
			//Consumivel
			if(item.equals("Refrigerante")){
				countA = Integer.parseInt(Character_Data.HP_A);
				countA = countA + 20;
				playerHPMAX = Integer.parseInt(Character_Data.HPMAX_A);
				if(countA >= playerHPMAX) { countA = playerHPMAX; } 
				Character_Data.HP_A = String.valueOf(countA);
				WriteDataCharacterActive();
			}
			if(item.equals("Soda")){
				countA = Integer.parseInt(Character_Data.MP_A);
				countA = countA + 50;
				playerMPMAX = Integer.parseInt(Character_Data.MPMAX_A);
				if(countA >= playerMPMAX) { countA = playerMPMAX; } 
				Character_Data.MP_A = String.valueOf(countA);
			}
			if(item.equals("IoguteMP")){
				countA = Integer.parseInt(Character_Data.MP_A);
				countA = countA + 100;
				Character_Data.MP_A = String.valueOf(countA);
			}
		}
		
		private void GiveLoot(Monster mobdeath) {		
			int sortie = 0;		
			
			sortie = randnumber.nextInt(100);
			
			if(sortie < 5) {
				AdicionaItemMochila(mobdeath.ITEMA);
				nomeLoot = mobdeath.ITEMA;
			}
			
			if(sortie >=  5 && sortie <= 25) {
				AdicionaItemMochila(mobdeath.ITEMB);
				nomeLoot = mobdeath.ITEMB;
			}
			
			if(sortie > 25 && sortie <= 50) {
				AdicionaItemMochila(mobdeath.ITEMC);
				nomeLoot = mobdeath.ITEMC;
			}
			
			if(sortie > 50 && sortie <= 100) {
				AdicionaItemMochila(mobdeath.ITEMD);
				nomeLoot = mobdeath.ITEMD;
			}
			
			showLootTime = 300;
		}
		
		private void GiveMoney(Monster mob) {
			int money = Integer.parseInt(mob.MONEY);
			int playerMoney = Integer.parseInt(Character_Data.Money_A);
			
			playerMoney = playerMoney + money;
			Character_Data.Money_A = String.valueOf(playerMoney);
			
		}
		
		public boolean ExibirMsgItem() {
			
			if(showLootTime > 0) {
				showLootTime--;
				return true;
			}
			else {
				return false;
			}
		}
		
		public String itemDrop() {
			if(showLootTime > 0) {
				showLootTime--;
				return nomeLoot;
			}
			else {
				return nomeLoot;
			}
		}
		
		public Sprite ItemDropImage(String item) {
			
			if(showLootTime > 0) {
				if(item.equals("Refrigerante")) {
					spr_master = atlas_Usable.createSprite("Cola");
					spr_master.setSize(15, 30);
				}
			}			
			return spr_master;
		}
		
		
		public void VerificaItemCompra(String nomeloja, int numeroItem) {
			
			int money = Integer.parseInt(Character_Data.Money_A);		
			if(nomeloja.equals("SodaMachine")) {
				if(numeroItem == 1) {
					if(money >= 10) { 
					AdicionaItemMochila("Refrigerante"); 
					money = money - 10;  
					Character_Data.Money_A = String.valueOf(money); 
					}
				}
			}
		}
		
		public void AdicionaItemMochila(String nomeItem) {
			String[] lstItem = Character_Data.Itens_A.split("-");
			String[] itemSplit;
			boolean exist = false;
			int qtd = 0;
			int posicaoItem = 0;
			String listaItemFinal;
			for(int i = 0; i < lstItem.length; i++) {
				if(lstItem[i].contains(nomeItem) && !exist) {
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
			Character_Data.Itens_A = listaItemFinal;
			}
			else {
				for(int i = 0; i < lstItem.length; i++) {
					if(lstItem[i].contains("None") && !exist) {
						posicaoItem = i;
						exist = true;
					}
				}
				
				if(exist) {
					lstItem[posicaoItem] = "[" + nomeItem + "#" + "1" + "]";
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					Character_Data.Itens_A = listaItemFinal;
				}
			}
		}
		
		
		// Buffs and Debuffs //
		public void VerificaBuffsDebuffs() {
			
			int timer = 0;		
			playerHP = Integer.parseInt(Character_Data.HP_A);
			playerMP = Integer.parseInt(Character_Data.MP_A);
			playerHPMAX = Integer.parseInt(Character_Data.HPMAX_A);
			playerMPMAX = Integer.parseInt(Character_Data.MPMAX_A);
			
			String[] statusPlayer;
			
			//Verifica Status
			
			
			
			//Equaliza HP
			if(playerHP >= playerHPMAX) { playerHP = playerHPMAX; }
			if(playerMP >= playerMPMAX) { playerMP = playerMPMAX; }
			Character_Data.HP_A = String.valueOf(playerHP);
			Character_Data.MP_A = String.valueOf(playerMP);
		}
		
		public ArrayList<Buffs> RetornaBuffs() {
			return lstBuffs;
		}
		
		//Regenera HP
		public void RegenerateHPTiming() {
			playerHP = Integer.parseInt(Character_Data.HP_A);
			playerMP = Integer.parseInt(Character_Data.MP_A);
			playerHPMAX = Integer.parseInt(Character_Data.HPMAX_A);
			playerMPMAX = Integer.parseInt(Character_Data.MPMAX_A);
						
			if(playerHP <= playerHPMAX) { playerHP = playerHP + 20; }		
			if(playerMP <= playerMPMAX) { playerMP = playerMP + 10; }
				
			if(playerHP >= playerHPMAX) { playerHP = playerHPMAX; }
			if(playerMP >= playerMPMAX) { playerMP = playerMPMAX; }
						
			if(playerHP < 0) { playerHP = 0;}
			if(playerMP < 0) { playerMP = 0;}
			
			Character_Data.HP_A = String.valueOf(playerHP);
			Character_Data.MP_A = String.valueOf(playerMP);
		}
		
		
		
		// NPCS and Quests //
		
		public int IniciaQuest(String nomeQuest) {	
			return 1;
		}
		
		public String ConfiguraQuest(String nomeQuest, int etapaTexto) {			
			return "";
		}
		
		public String NomeNPCTexto(String nomeQuest, int etapaTexto) {
			
			if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 1) {
				return "Rina";
			}
			
			return "";
		}
		
		public ArrayList<Sprite> ExibeNPCs(String map){
		
		   lstSprites.clear();
		   npcframe++;
		   npcframe2++;
		   npcframewalk++;
		   endright = endright - 0.4f;
		   endleft = endleft + 0.4f;
		   
		   if(npcframe > 300){
			   npcframe = 1;
		   }
		   if(npcframe2 > 120){
				npcframe2 = 1;
			}
		   
		   if(map.equals("MetroStation")){	   
			   if(endright < - 50){ endright = 300; }
			   if(endleft > 300){ endleft = -50; }
		   }
		   
			if(map.equals("MetroStation")){
			   //Guarda
			   if(npcframe >= 1 && npcframe <= 100){
			        spr_master = atlas_Npcs.createSprite("guard1");   
			        spr_master.setPosition(100, -15);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
			   }
			   if(npcframe >= 100 && npcframe <= 200){
					spr_master = atlas_Npcs.createSprite("guard2");   
					spr_master.setPosition(100, -15);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
			   }
			   if(npcframe >= 200 && npcframe <= 300){
					spr_master = atlas_Npcs.createSprite("guard3");   
					spr_master.setPosition(100, -15);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
			   }
				//lady 
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("lady1");   
			        spr_master.setPosition(50, -23);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("lady2");   
					spr_master.setPosition(50, -23);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("lady3");   
					spr_master.setPosition(50, -23);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//cooker
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("cooker1");   
			        spr_master.setPosition(170, -25);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("cooker2");   
					spr_master.setPosition(170, -25);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("cooker3");   
					spr_master.setPosition(170, -25);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//worker1
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("worker1");   
			        spr_master.setPosition(endleft, -55);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("worker2");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("worker1");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("worker3");   
			        spr_master.setPosition(endleft, -55);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("worker1");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("worker2");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//walker1
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("walker1");   
			        spr_master.setPosition(endleft - 105, -60);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("walker2");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("walker1");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("walker3");   
			        spr_master.setPosition(endleft - 105, -60);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("walker1");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("walker2");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				
				//SchoolerD
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerC2");   
			        spr_master.setPosition(endleft, -75);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerC1");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerC2");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerC3");   
			        spr_master.setPosition(endleft, -75);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerC2");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerC1");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//SchoolerC
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerD2");   
			        spr_master.setPosition(endright + 70, -30);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerD1");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerD2");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerD3");   
			        spr_master.setPosition(endright + 70, -30);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerD2");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerD1");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
			}
			
			
			if(map.equals("Streets305")){
				//SchoolerB
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("schoolerB1");   
			        spr_master.setPosition(209, -152);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("schoolerB1");   
					spr_master.setPosition(209, -152);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerB2");   
					spr_master.setPosition(209, -152);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//Cristal guy
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("crystalguy1");   
			        spr_master.setPosition(349, -80);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("crystalguy2");   
					spr_master.setPosition(349, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("crystalguy3");   
					spr_master.setPosition(349, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//Flower Girl
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("flowergirl1");   
			        spr_master.setPosition(62, -80);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("flowergirl2");   
					spr_master.setPosition(62, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("flowergirl3");   
					spr_master.setPosition(62, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//SchoolerA
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerA2");   
			        spr_master.setPosition(endleft, -131);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerA1");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerA2");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerA3");   
			        spr_master.setPosition(endleft, -131);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerA2");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerA1");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
			}
			
			return lstSprites;
		}
		
		
		
		
		public ArrayList<Player> InfoPlayerOnline() {
			return lstOnlinePlayers;
		}
		
		
		
		
		}
}
