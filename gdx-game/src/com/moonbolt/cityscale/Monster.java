package com.moonbolt.cityscale;

import java.util.ArrayList;

public class Monster {
	public String MobName;
	public int MobHp;
	public int MobMp;
	public int MobHpMax; 
	public int MobMpMax;
	public int MobFrame;
	public float MobPosX;
	public float MobPosY;
	public int MobExp;
	public String MobID;
	public int MobMoney;
	public int MobSizeY;
	public int MobSizeX;
	public String MobTarget;
	public String MobDead;
	public int MobTimeDead;
	public int MobSkillChance;
	public int MobAtk;
	public int MobEvade;
	public String MobPosition;
	public int MobRandomSt;
	public int MobTimerMov;
	public int MobFrameTime;
	public int MobAtkTimer;
	public int MobAtkTimerMax;
	public int MobLevel;
	public String Map;

	
	public ArrayList<Monster> LoadMonsters(String map){
		
		ArrayList<Monster> lstMobs = new ArrayList<Monster>();
		lstMobs.clear();
		
		if(map.equals("Sewers")) {
			Monster newMob1 = new Monster();
			newMob1.MobName = "slime"; 
			newMob1.MobHp = 100;
			newMob1.MobMp = 30;
			newMob1.MobHpMax = 100; 
			newMob1.MobMpMax = 30;
			newMob1.MobFrame = 1;
			newMob1.MobPosX = 0;
			newMob1.MobPosY = 0;
			newMob1.MobExp = 2;
			newMob1.MobID = "SlimeA";
			newMob1.MobMoney = 1;
			newMob1.MobSizeY = 17;
			newMob1.MobSizeX = 9;
			newMob1.MobTarget = "none";
			newMob1.MobDead = "no";
			newMob1.MobTimeDead = 0;
			newMob1.MobSkillChance = 5;
			newMob1.MobAtk = 5;
			newMob1.MobEvade = 10;
			newMob1.MobPosition = "L";
			newMob1.MobRandomSt = 1;
			newMob1.MobTimerMov = 50;
			newMob1.MobFrameTime = 40;
			newMob1.MobTimeDead = 250;
			newMob1.MobDead = "no";
			newMob1.MobAtkTimer = 250;
			newMob1.MobAtkTimerMax = 250;
			newMob1.MobLevel = 1;
			newMob1.Map = "Sewers";
			lstMobs.add(newMob1);
			
			Monster newMob2 = new Monster();
			newMob2.MobName = "slime"; 
			newMob2.MobHp = 100;
			newMob2.MobMp = 30;
			newMob2.MobHpMax = 100; 
			newMob2.MobMpMax = 30;
			newMob2.MobFrame = 1;
			newMob2.MobPosX = 15;
			newMob2.MobPosY = 10;
			newMob2.MobExp = 2;
			newMob2.MobID = "SlimeB";
			newMob2.MobMoney = 1;
			newMob2.MobSizeY = 17;
			newMob2.MobSizeX = 9;
			newMob2.MobTarget = "none";
			newMob2.MobDead = "no";
			newMob2.MobTimeDead = 0;
			newMob2.MobSkillChance = 5;
			newMob2.MobAtk = 5;
			newMob2.MobEvade = 2;
			newMob2.MobPosition = "L";
			newMob2.MobRandomSt = 2;
			newMob2.MobTimerMov = 60;
			newMob2.MobFrameTime = 40;
			newMob2.MobTimeDead = 250;
			newMob2.MobDead = "no";
			newMob2.MobAtkTimer = 250;
			newMob2.MobAtkTimerMax = 250;
			newMob2.MobLevel = 1;
			newMob2.Map = "Sewers";
			lstMobs.add(newMob2);
			
			Monster newMob3 = new Monster();
			newMob3.MobName = "oikplant"; 
			newMob3.MobHp = 150;
			newMob3.MobMp = 30;
			newMob3.MobHpMax = 150; 
			newMob3.MobMpMax = 30;
			newMob3.MobFrame = 1;
			newMob3.MobPosX = -35;
			newMob3.MobPosY = -45;
			newMob3.MobExp = 10;
			newMob3.MobID = "OikPlantA";
			newMob3.MobMoney = 2;
			newMob3.MobSizeY = 22;
			newMob3.MobSizeX = 11;
			newMob3.MobTarget = "none";
			newMob3.MobDead = "no";
			newMob3.MobTimeDead = 0;
			newMob3.MobSkillChance = 5;
			newMob3.MobAtk = 15;
			newMob3.MobEvade = 10;
			newMob3.MobPosition = "L";
			newMob3.MobRandomSt = 0;
			newMob3.MobTimerMov = 80;
			newMob3.MobFrameTime = 40;
			newMob3.MobTimeDead = 250;
			newMob3.MobDead = "no";
			newMob3.MobAtkTimer = 250;
			newMob3.MobAtkTimerMax = 250;
			newMob3.MobLevel = 1;
			newMob3.Map = "Sewers";
			lstMobs.add(newMob3);
			
			Monster newMob4 = new Monster();
			newMob4.MobName = "poro"; 
			newMob4.MobHp = 200;
			newMob4.MobMp = 30;
			newMob4.MobHpMax = 200; 
			newMob4.MobMpMax = 30;
			newMob4.MobFrame = 1;
			newMob4.MobPosX = -35;
			newMob4.MobPosY = -45;
			newMob4.MobExp = 25;
			newMob4.MobID = "PoroA";
			newMob4.MobMoney = 2;
			newMob4.MobSizeY = 17;
			newMob4.MobSizeX = 9;
			newMob4.MobTarget = "none";
			newMob4.MobDead = "no";
			newMob4.MobTimeDead = 0;
			newMob4.MobSkillChance = 5;
			newMob4.MobAtk = 35;
			newMob4.MobEvade = 15;
			newMob4.MobPosition = "L";
			newMob4.MobRandomSt = 3;
			newMob4.MobTimerMov = 100;
			newMob4.MobFrameTime = 40;
			newMob4.MobTimeDead = 250;
			newMob4.MobDead = "no";
			newMob4.MobAtkTimer = 320;
			newMob4.MobAtkTimerMax = 320;
			newMob4.MobLevel = 1;
			newMob4.Map = "Sewers";
			lstMobs.add(newMob4);
		}
		return lstMobs;
		
	}
	
	
}
