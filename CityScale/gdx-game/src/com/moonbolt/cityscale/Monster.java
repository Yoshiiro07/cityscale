package com.moonbolt.cityscale;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Monster {
	
	//control
	private int count = 0;
	private int frame = 1;
	private int frequency = 0;
	private int countfq = 0;
	private int numberSelected = 0;
	private int changePath = 0;
	private int countDamaged = 0;
	private String sidePath = "right";
	private float mobX;
	private float mobY;
	private float mobSpeed;
	private Random randnumber;
	private boolean atkFrame = false;
	private boolean arearangeplayer = false;
	private float PXPlus;
	private float PXMinus;
	private float PYPlus;
	private float PYMinus;
	
	public String NAME;
	public String HP;
	public String MP;
	public String HPMAX;
	public String MPMAX;
	public String PX;
	public String PY;
	public String SIDE;
	public String HEIGHT;
	public String WIDTH;
	public String STATE;
	public String ATK;
	public String DEF;
	public String RESF;
	public String RESA;
	public String RESI;
	public String BATTLE;
	public String TARGET;
	public String EXP;
	public String LEVEL;
	public String MONEY;
	public String SKILL1;
	public String SKILL2;
	public String AUTOATK;
	public String AUTOSKILL1;
	public String AUTOSKILL2;
	public String AUTOATKBASE;
	public String AUTOSKILL1BASE;
	public String AUTOSKILL2BASE;
	public String ID;
	public String AGRESSIVE;
	public String RESPAWN;
	public String RESPAWNMAX;
	public String FRAME;
	public String FREQUENCY;
	public String SPEED;
	public String EVADE;
	public String ITEMA;
	public String ITEMB;
	public String ITEMC;
	public String ITEMD;
	public String ATKZONEXPLUS;
	public String ATKZONEXMINUS;
	public String ATKZONEYPLUS;
	public String ATKZONEYMINUS;
	public String LOCKDEATH;
	public String MAP;
	
	private TextureAtlas atlas_Mob;
	private TextureAtlas atlas_Forest;
	
	public Monster() {
		randnumber = new Random();
		
		atlas_Mob = new TextureAtlas(Gdx.files.internal("data/monsters/mobsForest.txt"));
		atlas_Forest = new TextureAtlas(Gdx.files.internal("data/monsters/mobsForest.txt"));
		
	}
	
	public Monster GetMonster(String name, String map) {
		Monster mobReturn =  new Monster();
		
		if(map.equals("Streets305")) {
			//Slime
			if(name.equals("slimeA")){
				mobReturn.NAME = "slime";//Name
				mobReturn.HP = "80";//HP
				mobReturn.MP = "10";//MP
				mobReturn.HPMAX = "80"; //HPMAX
				mobReturn.MPMAX = "10"; //MPMAX
				mobReturn.PX = "54";//PX
				mobReturn.PY = "-104";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "20"; // Height
				mobReturn.WIDTH = "15"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "5";//ATK
				mobReturn.DEF = "1";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "10"; //EXP
				mobReturn.LEVEL = "1"; //LEVEL
				mobReturn.MONEY = "1"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "300"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "250";
				mobReturn.ID = "SlimeA"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.2f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
	            mobReturn.MAP = "Streets305";
			}
		}
		
		
		if(map.equals("ForestArea")) {
			//Slime
			if(name.equals("slimeA")){
				mobReturn.NAME = "slime";//Name
				mobReturn.HP = "80";//HP
				mobReturn.MP = "10";//MP
				mobReturn.HPMAX = "80"; //HPMAX
				mobReturn.MPMAX = "10"; //MPMAX
				mobReturn.PX = "54";//PX
				mobReturn.PY = "-104";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "20"; // Height
				mobReturn.WIDTH = "15"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "5";//ATK
				mobReturn.DEF = "1";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "3"; //EXP
				mobReturn.LEVEL = "1"; //LEVEL
				mobReturn.MONEY = "1"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "300"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "250";
				mobReturn.ID = "slimeA"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.2f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
			}
			//Bee
			if(name.equals("beeA")){
				mobReturn.NAME = "bee";//Name
				mobReturn.HP = "200";//HP
				mobReturn.MP = "25";//MP
				mobReturn.HPMAX = "200"; //HPMAX
				mobReturn.MPMAX = "25"; //MPMAX
				mobReturn.PX = "172";//PX
				mobReturn.PY = "-117";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "30"; // Height
				mobReturn.WIDTH = "23"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "10";//ATK
				mobReturn.DEF = "1";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "15"; //EXP
				mobReturn.LEVEL = "3"; //LEVEL
				mobReturn.MONEY = "2"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "200"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "200";
				mobReturn.ID = "beeA"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.4f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
			}
			if(name.equals("willowA")){
				mobReturn.NAME = "willow";//Name
				mobReturn.HP = "520";//HP
				mobReturn.MP = "10";//MP
				mobReturn.HPMAX = "520"; //HPMAX
				mobReturn.MPMAX = "10"; //MPMAX
				mobReturn.PX = "56";//PX
				mobReturn.PY = "-256";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "40"; // Height
				mobReturn.WIDTH = "25"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "15";//ATK
				mobReturn.DEF = "1";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "100"; //EXP
				mobReturn.LEVEL = "1"; //LEVEL
				mobReturn.MONEY = "1"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "300"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "250";
				mobReturn.ID = "willowA"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.2f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
			}
			if(name.equals("poroA")){
				mobReturn.NAME = "poro";//Name
				mobReturn.HP = "450";//HP
				mobReturn.MP = "10";//MP
				mobReturn.HPMAX = "450"; //HPMAX
				mobReturn.MPMAX = "10"; //MPMAX
				mobReturn.PX = "61";//PX
				mobReturn.PY = "-244";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "26"; // Height
				mobReturn.WIDTH = "17"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "20";//ATK
				mobReturn.DEF = "3";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "40"; //EXP
				mobReturn.LEVEL = "5"; //LEVEL
				mobReturn.MONEY = "3"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "250"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "250";
				mobReturn.ID = "poroA"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.3f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
			}
			if(name.equals("poroB")){
				mobReturn.NAME = "poro";//Name
				mobReturn.HP = "450";//HP
				mobReturn.MP = "10";//MP
				mobReturn.HPMAX = "450"; //HPMAX
				mobReturn.MPMAX = "10"; //MPMAX
				mobReturn.PX = "336";//PX
				mobReturn.PY = "-6";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "26"; // Height
				mobReturn.WIDTH = "17"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "20";//ATK
				mobReturn.DEF = "3";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "40"; //EXP
				mobReturn.LEVEL = "5"; //LEVEL
				mobReturn.MONEY = "3"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "250"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "250";
				mobReturn.ID = "poroB"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.3f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
			}
			if(name.equals("oikplantA")){
				mobReturn.NAME = "oikplant";//Name
				mobReturn.HP = "890";//HP
				mobReturn.MP = "25";//MP
				mobReturn.HPMAX = "890"; //HPMAX
				mobReturn.MPMAX = "25"; //MPMAX
				mobReturn.PX = "332";//PX
				mobReturn.PY = "-280";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "30"; // Height
				mobReturn.WIDTH = "23"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "30";//ATK
				mobReturn.DEF = "1";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "200"; //EXP
				mobReturn.LEVEL = "6"; //LEVEL
				mobReturn.MONEY = "2"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "200"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "200";
				mobReturn.ID = "oikplantA"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.4f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
			}
			//Bee
			if(name.equals("beeB")){
				mobReturn.NAME = "bee";//Name
				mobReturn.HP = "200";//HP
				mobReturn.MP = "25";//MP
				mobReturn.HPMAX = "200"; //HPMAX
				mobReturn.MPMAX = "25"; //MPMAX
				mobReturn.PX = "172";//PX
				mobReturn.PY = "-117";//PY
				mobReturn.SIDE = "left"; //SIDE
				mobReturn.HEIGHT = "30"; // Height
				mobReturn.WIDTH = "23"; // Width
				mobReturn.STATE = "Walk1";//State
				mobReturn.ATK = "10";//ATK
				mobReturn.DEF = "1";//DEF
				mobReturn.RESF = "0";//RESF
				mobReturn.RESA = "0"; //RESA
				mobReturn.RESI = "0"; //RESI
				mobReturn.BATTLE = "no"; //BATTLE
				mobReturn.TARGET = "none";
				mobReturn.EXP = "40"; //EXP
				mobReturn.LEVEL = "3"; //LEVEL
				mobReturn.MONEY = "2"; //MONEY
				mobReturn.SKILL1 = "none"; //SKILL1
				mobReturn.SKILL2 = "none"; //SKILL2
				mobReturn.AUTOATK = "200"; //AUTOATK
				mobReturn.AUTOSKILL1 = "5"; //AUTOSKILL1
				mobReturn.AUTOSKILL2 = "0"; //AUTOSKILL2
				mobReturn.AUTOATKBASE = "200";
				mobReturn.ID = "beeB"; //ID
				mobReturn.AGRESSIVE = "no";
				mobReturn.RESPAWN = "100";
				mobReturn.RESPAWNMAX = "100";
				mobReturn.FRAME = "1";
				mobReturn.FREQUENCY = "10";
				mobReturn.SPEED = "0.4f";
				mobReturn.EVADE = "20";
				mobReturn.ITEMA = "Refrigerante";
				mobReturn.ITEMB = "Refrigerante";
				mobReturn.ITEMC = "Refrigerante";
				mobReturn.ITEMD = "Refrigerante";
	            mobReturn.ATKZONEXPLUS = "20";
	            mobReturn.ATKZONEXMINUS = "20";
	            mobReturn.ATKZONEYPLUS = "30";
	            mobReturn.ATKZONEYMINUS = "30";
	            mobReturn.LOCKDEATH = "no";
			}
		}
		
		return mobReturn;
	}
	
	public Monster FrameAndMovement(Monster mobSelected, float pX, float pY) {
		
		//Para Movimento	
		mobX = Float.parseFloat(mobSelected.PX);
		mobY = Float.parseFloat(mobSelected.PY);
		mobSpeed = Float.parseFloat(mobSelected.SPEED);
		numberSelected = randnumber.nextInt(20);
		
		changePath++;
		
		if(changePath > 50){
			if(numberSelected >= 0 && numberSelected <= 5){
				sidePath = "right";
			}
			if(numberSelected >= 6 && numberSelected <= 10){
				sidePath = "left";
			}
			if(numberSelected >= 11 && numberSelected <= 15){
				sidePath = "up";
			}
			if(numberSelected >= 15 && numberSelected <= 20){
				sidePath = "front";
			}	
			changePath = 0;
		}
		
		PXPlus = pX + 10;
		PXMinus = pX - 10;
		PYPlus = pY + 10;
		PYMinus = pY - 10;
		
		if(mobSelected.BATTLE.equals("no")){
	
		//Direita
		if(sidePath.equals("right")) {
			mobX = mobX + mobSpeed;
			mobSelected.PX = String.valueOf(mobX);
			mobSelected.SIDE = "right";
		}
		//Esquerda
		if(sidePath.equals("left")) {
			mobX = mobX - mobSpeed;
			mobSelected.PX = String.valueOf(mobX);
			mobSelected.SIDE = "left";
		}
		//Cima
		if(sidePath.equals("up")) {
			mobY = mobY + mobSpeed;
			mobSelected.PY = String.valueOf(mobY);
			mobSelected.SIDE = "right";
		}
		//Baixo
		if(sidePath.equals("front")) {
			mobY = mobY - mobSpeed;
			mobSelected.PY = String.valueOf(mobY);
			mobSelected.SIDE = "left";
		}
		}
		
		if(mobSelected.BATTLE.equals("yes")){
			if((PXPlus) > mobX) {
				mobX = mobX + mobSpeed;
				mobSelected.PX = String.valueOf(mobX);
				arearangeplayer = true;
				mobSelected.SIDE = "right";
			}
			if((PXMinus) < mobX){
				mobX = mobX - mobSpeed;
				mobSelected.PX = String.valueOf(mobX);
				arearangeplayer = true;
				mobSelected.SIDE = "left";
			}
			if((PYPlus) < mobY){
				mobY = mobY - mobSpeed;
				mobSelected.PY = String.valueOf(mobY);
				arearangeplayer = true;
				mobSelected.SIDE = "right";
			}
			if((PYMinus) > mobY){
				mobY = mobY + mobSpeed;
				mobSelected.PY = String.valueOf(mobY);
				arearangeplayer = true;
				mobSelected.SIDE = "left";
			}
		}
		
		if(mobSelected.FRAME.equals("4")) {
			frame = 4;
			return mobSelected;
		}
		
		if(mobSelected.FRAME.equals("5")) {
			countDamaged++;
			if(countDamaged > 30) {
				countDamaged = 0;
				mobSelected.FRAME = "1";
				frame = 1;
			}
			else {
				return mobSelected;
			}
		}
		
		//Para Frame
		count = Integer.parseInt(mobSelected.AUTOATK);
		frequency = Integer.parseInt(mobSelected.FREQUENCY);

		countfq++;
		if(countfq > frequency) {
			if(frame == 1) {
				mobSelected.FRAME = "1";
			}
			if(frame == 2) {
				mobSelected.FRAME = "2";
			}
			if(frame == 3) {
				mobSelected.FRAME = "3";
			}
			if(count < 1) {
				mobSelected.FRAME = "3";
			}
			
			if(frame > 3 && count > 1) {
				mobSelected.FRAME = "1";
				frame = 1;
			}
			
			frame++;
			countfq = 1;
		}
		
		
		return mobSelected;
	}
	
	public Monster FrameAndMovementAgressive(Monster mobSelected, float pX, float pY) {
		
		return mobSelected;
	}
	
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
	
	
	public void TriggerAttackFrame(boolean startFrame) {
		atkFrame = startFrame;
	}
}
