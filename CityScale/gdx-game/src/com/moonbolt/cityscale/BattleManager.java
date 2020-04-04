package com.moonbolt.cityscale;

import java.util.ArrayList;
import java.util.Random;

public class BattleManager
{
	//Main
	private Player Character_Data;
	private Random randnumber;
	
	//Objects
	private ArrayList<Monster> lstMonsters;
	private ArrayList<Damage> lstDamage;
	private ArrayList<Skill> lstSkills;
	private Skill skillUsed;
	
	
	//Primitives
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
	private float playerHP;
	private float playerMP;
	private float playerHPMAX;
	private float playerMPMAX;
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
	private int attackCooldown;
	private int countA;
	private int dmgWeapon;
	private int showLootTime;
	private String nomeLoot;
	private String text;
	private String skillOnline;
	private boolean attackFrame;
	private boolean onlineCheck;
	private boolean isCasting;
	private boolean inBattle;
	private boolean castOver;
	
	public BattleManager(){
		lstMonsters = new ArrayList<Monster>();
		lstDamage = new ArrayList<Damage>();
		lstSkills = new ArrayList<Skill>();
		
		randnumber = new Random();
		skillUsed = new Skill();
		skillOnline = "";
	}
	
	public void LoadCharacterData(Player playerActive) {
		this.Character_Data = playerActive;
	}
	
	public int GetCooldown() {
		return playerAttackCooldown;
	}
	
	public void EqualizeHPMP() {
	//Equaliza HP
	if(playerHP >= playerHPMAX) { playerHP = playerHPMAX; }
	if(playerMP >= playerMPMAX) { playerMP = playerMPMAX; }
	Character_Data.HP_A = String.valueOf(playerHP);
	Character_Data.MP_A = String.valueOf(playerMP);
	}
	
	//Auto Regen HP
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
	
	public void CalculaCooldown() {
		if(delayTime > 0) { delayTime--; }
		if(delayTime <= 0) { delayTime = 0; }
	}
	
	public ArrayList<Damage> ShowDamage(){
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
	
	
	public void VerifyAttack(String AutoAttack, String ManualAttack, InterfaceManager interfaceManager, OnlineManager onlineManager) {
		
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
							interfaceManager.GiveExp(lstMonsters.get(countA));
							GiveLoot(lstMonsters.get(countA), interfaceManager);
							GiveMoney(lstMonsters.get(countA));
							lstMonsters.get(countA).LOCKDEATH = "yes"; 
							Character_Data.Battle_A = "no";
							Character_Data.Target_A = "none"; 
							Character_Data.Battle_A = "none";
							inBattle = false; 
							lstMonsters.get(countA).BATTLE = "no";
						}
						lstMonsters.get(countA).HP = String.valueOf(mobHP);
						if(onlineCheck) { onlineManager.OperacaoOnline("Atk", lstMonsters.get(countA).ID + ":" + lstMonsters.get(countA).HP); }
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
										interfaceManager.GiveExp(lstMonsters.get(countA));
										GiveLoot(lstMonsters.get(countA), interfaceManager);
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
								
								if(onlineCheck) { onlineManager.OperacaoOnline("Atk", lstMonsters.get(countA).ID + ":" + lstMonsters.get(countA).HP); }
								
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
	
	public boolean VerifyRangedSkill(int numSkill) {
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
	
	public void VerifySkillDamage(Skill sk, float areaselectedX, float areaselectedY) {
		
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
	
	private void GiveLoot(Monster mobdeath, InterfaceManager interfaceManager) {		
		int sortie = 0;		
		
		sortie = randnumber.nextInt(100);
		
		if(sortie < 5) {
			interfaceManager.AddItemMochila(mobdeath.ITEMA);
			nomeLoot = mobdeath.ITEMA;
		}
		
		if(sortie >=  5 && sortie <= 25) {
			interfaceManager.AddItemMochila(mobdeath.ITEMB);
			nomeLoot = mobdeath.ITEMB;
		}
		
		if(sortie > 25 && sortie <= 50) {
			interfaceManager.AddItemMochila(mobdeath.ITEMC);
			nomeLoot = mobdeath.ITEMC;
		}
		
		if(sortie > 50 && sortie <= 100) {
			interfaceManager.AddItemMochila(mobdeath.ITEMD);
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
	
	public void SetaSkillSolo(int numSkill) {

		if(delayTime > 0) { return; }

		int mpPlayer = Integer.parseInt(Character_Data.MP_A);
		if(Character_Data.Job_A.equals("Novice")) {			
			//tripleattack
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("tripleattack", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("tripleattack",mpPlayer)) { VerificaSkillDano(skillUsed, 0,0);}
			}			
		}		
	}

	public void SetaSkillArea(int numSkill, float posXSelect,float posYSelect){
		if(delayTime > 0) { return; }
		if(isCasting) { return; }

		int mpPlayer = Integer.parseInt(Character_Data.MP_A);
		Skill skillUsed = new Skill();

		posTouchSkillX = posXSelect;
		posTouchSkillY = posYSelect;

		if(Character_Data.Job_A.equals("Swordman")) {
			//Protect
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("protect", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("Protect",mpPlayer)) { isCasting = true; castOver = false; }
			}		
		}

		if(Character_Data.Job_A.equals("Magician")) {			
			//Fireball
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("fireball", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("fireball",mpPlayer)) { isCasting = true; castOver = false; }
			}		
			//IceCrystal
			if(numSkill == 2) {
				skillUsed = Skill.RetornaDadosSKill("icecrystal", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("icecrystal",mpPlayer)) { isCasting = true; castOver = false; }
			}			
			//Thundercloud
			if(numSkill == 3) {
				skillUsed = Skill.RetornaDadosSKill("thundercloud", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("thundercloud",mpPlayer)) { isCasting = true; castOver = false; }
			}		
			//Rockbound
			if(numSkill == 4) {
				skillUsed = Skill.RetornaDadosSKill("rockbound", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("rockbound",mpPlayer)) { isCasting = true; castOver = false; }
			}		
			//Soulclash
			if(numSkill == 5) {
				skillUsed = Skill.RetornaDadosSKill("soulclash", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("soulclash",mpPlayer)) { isCasting = true; castOver = false; }
			}		
		}
		if(Character_Data.Job_A.equals("Medic")) {	
			//Heal
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("heal", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("heal",mpPlayer)) { isCasting = true; castOver = false; }
			}		
			//defboost
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("defboost", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("defboost",mpPlayer)) { isCasting = true; castOver = false;}
			}	
			//atkboost
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("atkboost", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("atkboost",mpPlayer)) { isCasting = true; castOver = false; }
			}	
			//regen
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("regen", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("regen",mpPlayer)) { isCasting = true; castOver = false; }
			}	
			//holyprism
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("holyprism", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("holyprims",mpPlayer)) { isCasting = true; castOver = false; }
			}	
		}

		if(Character_Data.Job_A.equals("Gunner")) {	
			//bulletrain
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("bulletrain", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("bulletrain",mpPlayer)) { isCasting = true; castOver = false; }
			}	
			//lockshot
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("lockshot", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("lockshot",mpPlayer)) { isCasting = true; castOver = false; }
			}	
			//mine
			if(numSkill == 1) {
				skillUsed = Skill.RetornaDadosSKill("mine", Character_Data.Name_A);
				skillOnline = skillUsed.nameSkill + "|" + String.valueOf(skillUsed.countFrameEffect);
				if(Skill.CheckMP("mine",mpPlayer)) { isCasting = true; }
			}	
		}
	}

	public void CastTime(){

		castTime = skillUsed.castTime;
		playerDextery = Integer.parseInt(Character_Data.Dextery_A);
		playerMind = Integer.parseInt(Character_Data.Mind_A);

		castTime--;
		castTime = castTime - ((playerDextery + playerMind) / 20);

		if(castTime < 0) {
			VerificaSkillDano(skillUsed,posTouchSkillX,posTouchSkillY);
			posTouchSkillX = 0;
			posTouchSkillY = 0;
		}		
	}
}
