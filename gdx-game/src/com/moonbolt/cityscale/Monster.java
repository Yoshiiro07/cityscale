package com.moonbolt.cityscale;

import java.util.ArrayList;

public class Monster {
		//Monster
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
		public String MobSkillChance;
		public int MobAtk;
		public int MobEvade;
		public String MobPosition;
		public int MobRandomSt;
		public int MobTimerMov;
		public int MobFrameTime;
		public int MobAtkTimer;
		public int MobAtkTimerMax;
		public int MobLevel;
		public String MobMap;


		public ArrayList<Monster> LoadMonsterData(String map){
			ArrayList<Monster> listMonsters = new ArrayList<Monster>();
			

			if(map.equals("Sewers")){
			Monster MobA = new Monster();
			
			MobA.MobName = "slime";
			MobA.MobHp = 100;
			MobA.MobMp = 100;
			MobA.MobHpMax = 100;
			MobA.MobMpMax = 100;
			MobA.MobFrame = 1;
			MobA.MobPosX = 20;
			MobA.MobPosY = 20;
			MobA.MobExp = 2;
			MobA.MobID = "SlimeA";
			MobA.MobMoney = 10;
			MobA.MobSizeY = 15;
			MobA.MobSizeX = 10;
			MobA.MobTarget = "none";
			MobA.MobDead = "no";
			MobA.MobTimeDead = 500;
			MobA.MobSkillChance = "";
			MobA.MobAtk = 10;
			MobA.MobEvade = 20;
			MobA.MobPosition = "left";
			MobA.MobRandomSt = 1;
			MobA.MobTimerMov = 1;
			MobA.MobFrameTime = 1;
			MobA.MobAtkTimer = 300;
			MobA.MobAtkTimerMax = 300;
			MobA.MobLevel = 1;
			MobA.MobMap = "Sewers";

			Monster MobB = new Monster();
			MobB.MobName = "slime";
			MobB.MobHp = 100;
			MobB.MobMp = 100;
			MobB.MobHpMax = 100;
			MobB.MobMpMax = 100;
			MobB.MobFrame = 1;
			MobB.MobPosX = 20;
			MobB.MobPosY = 20;
			MobB.MobExp = 2;
			MobB.MobID = "SlimeB";
			MobB.MobMoney = 10;
			MobB.MobSizeY = 15;
			MobB.MobSizeX = 10;
			MobB.MobTarget = "none";
			MobB.MobDead = "no";
			MobB.MobTimeDead = 500;
			MobB.MobSkillChance = "";
			MobB.MobAtk = 10;
			MobB.MobEvade = 20;
			MobB.MobPosition = "left";
			MobB.MobRandomSt = 1;
			MobB.MobTimerMov = 1;
			MobB.MobFrameTime = 1;
			MobB.MobAtkTimer = 300;
			MobB.MobAtkTimerMax = 300;
			MobB.MobLevel = 1;
			MobB.MobMap = "Sewers";

			Monster MobC = new Monster();
			MobC.MobName = "oikplant";
			MobC.MobHp = 130;
			MobC.MobMp = 130;
			MobC.MobHpMax = 100;
			MobC.MobMpMax = 100;
			MobC.MobFrame = 1;
			MobC.MobPosX = 112.5f;
			MobC.MobPosY = -13;
			MobC.MobExp = 10;
			MobC.MobID = "OikplantA";
			MobC.MobMoney = 10;
			MobC.MobSizeY = 18;
			MobC.MobSizeX = 12;
			MobC.MobTarget = "none";
			MobC.MobDead = "no";
			MobC.MobTimeDead = 500;
			MobC.MobSkillChance = "";
			MobC.MobAtk = 10;
			MobC.MobEvade = 10;
			MobC.MobPosition = "left";
			MobC.MobRandomSt = 1;
			MobC.MobTimerMov = 1;
			MobC.MobFrameTime = 1;
			MobC.MobAtkTimer = 300;
			MobC.MobAtkTimerMax = 300;
			MobC.MobLevel = 1;
			MobC.MobMap = "Sewers";

			Monster MobD = new Monster();
			MobD.MobName = "rat";
			MobD.MobHp = 180;
			MobD.MobMp = 180;
			MobD.MobHpMax = 100;
			MobD.MobMpMax = 100;
			MobD.MobFrame = 1;
			MobD.MobPosX = 9.0f;
			MobD.MobPosY = -132;
			MobD.MobExp = 25;
			MobD.MobID = "RatoA";
			MobD.MobMoney = 10;
			MobD.MobSizeY = 20;
			MobD.MobSizeX = 20;
			MobD.MobTarget = "none";
			MobD.MobDead = "no";
			MobD.MobTimeDead = 500;
			MobD.MobSkillChance = "";
			MobD.MobAtk = 15;
			MobD.MobEvade = 10;
			MobD.MobPosition = "left";
			MobD.MobRandomSt = 1;
			MobD.MobTimerMov = 1;
			MobD.MobFrameTime = 1;
			MobD.MobAtkTimer = 300;
			MobD.MobAtkTimerMax = 300;
			MobD.MobLevel = 1;
			MobD.MobMap = "Sewers";

			Monster MobE = new Monster();
			MobE.MobName = "sapod";
			MobE.MobHp = 240;
			MobE.MobMp = 240;
			MobE.MobHpMax = 260;
			MobE.MobMpMax = 260;
			MobE.MobFrame = 1;
			MobE.MobPosX = 84.5f;
			MobE.MobPosY = -129.5f;
			MobE.MobExp = 60;
			MobE.MobID = "SapodA";
			MobE.MobMoney = 10;
			MobE.MobSizeY = 18;
			MobE.MobSizeX = 14;
			MobE.MobTarget = "none";
			MobE.MobDead = "no";
			MobE.MobTimeDead = 500;
			MobE.MobSkillChance = "";
			MobE.MobAtk = 25;
			MobE.MobEvade = 10;
			MobE.MobPosition = "left";
			MobE.MobRandomSt = 1;
			MobE.MobTimerMov = 1;
			MobE.MobFrameTime = 1;
			MobE.MobAtkTimer = 400;
			MobE.MobAtkTimerMax = 400;
			MobE.MobLevel = 1;
			MobE.MobMap = "Sewers";

			listMonsters.add(MobA);
			listMonsters.add(MobB);
			listMonsters.add(MobC);
			listMonsters.add(MobD);
			listMonsters.add(MobE);

			}

			return listMonsters;
		}
}
