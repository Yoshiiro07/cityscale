package com.moonbolt.cityscale.models;

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
			
			MobA.MobName = "slime";      //MobID + MobHp + MobMp + MobPosX + MobPosY + MobTarget + MobDead
			MobA.MobHp = 30;
			MobA.MobMp = 30;
			MobA.MobHpMax = 30;
			MobA.MobMpMax = 30;
			MobA.MobFrame = 1;
			MobA.MobPosX = 20;
			MobA.MobPosY = 20;
			MobA.MobExp = 10;
			MobA.MobID = "SlimeA";
			MobA.MobMoney = 2;
			MobA.MobSizeY = 15;
			MobA.MobSizeX = 10;
			MobA.MobTarget = "none";
			MobA.MobDead = "no";
			MobA.MobTimeDead = 500;
			MobA.MobSkillChance = "";
			MobA.MobAtk = 1;
			MobA.MobEvade = 20;
			MobA.MobPosition = "left";
			MobA.MobRandomSt = 1;
			MobA.MobTimerMov = 1;
			MobA.MobFrameTime = 1;
			MobA.MobAtkTimer = 500;
			MobA.MobAtkTimerMax = 500;
			MobA.MobLevel = 1;
			MobA.MobMap = "Sewers";

			Monster MobB = new Monster();
			MobB.MobName = "slime";
			MobB.MobHp = 30;
			MobB.MobMp = 30;
			MobB.MobHpMax = 30;
			MobB.MobMpMax = 30;
			MobB.MobFrame = 1;
			MobB.MobPosX = 20;
			MobB.MobPosY = 20;
			MobB.MobExp = 10;
			MobB.MobID = "SlimeB";
			MobB.MobMoney = 2;
			MobB.MobSizeY = 15;
			MobB.MobSizeX = 10;
			MobB.MobTarget = "none";
			MobB.MobDead = "no";
			MobB.MobTimeDead = 500;
			MobB.MobSkillChance = "";
			MobB.MobAtk = 1;
			MobB.MobEvade = 20;
			MobB.MobPosition = "left";
			MobB.MobRandomSt = 1;
			MobB.MobTimerMov = 1;
			MobB.MobFrameTime = 1;
			MobB.MobAtkTimer = 500;
			MobB.MobAtkTimerMax = 500;
			MobB.MobLevel = 1;
			MobB.MobMap = "Sewers";

			Monster MobC = new Monster();
			MobC.MobName = "oikplant";
			MobC.MobHp = 65;
			MobC.MobMp = 65;
			MobC.MobHpMax = 65;
			MobC.MobMpMax = 65;
			MobC.MobFrame = 1;
			MobC.MobPosX = 112.5f;
			MobC.MobPosY = -13;
			MobC.MobExp = 20;
			MobC.MobID = "OikplantA";
			MobC.MobMoney = 2;
			MobC.MobSizeY = 18;
			MobC.MobSizeX = 12;
			MobC.MobTarget = "none";
			MobC.MobDead = "no";
			MobC.MobTimeDead = 500;
			MobC.MobSkillChance = "";
			MobC.MobAtk = 5;
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
			MobD.MobHp = 100;
			MobD.MobMp = 100;
			MobD.MobHpMax = 100;
			MobD.MobMpMax = 100;
			MobD.MobFrame = 1;
			MobD.MobPosX = 9.0f;
			MobD.MobPosY = -132;
			MobD.MobExp = 30;
			MobD.MobID = "RatoA";
			MobD.MobMoney = 2;
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
			MobE.MobHp = 150;
			MobE.MobMp = 150;
			MobE.MobHpMax = 200;
			MobE.MobMpMax = 200;
			MobE.MobFrame = 1;
			MobE.MobPosX = 84.5f;
			MobE.MobPosY = -129.5f;
			MobE.MobExp = 40;
			MobE.MobID = "SapodA";
			MobE.MobMoney = 2;
			MobE.MobSizeY = 18;
			MobE.MobSizeX = 14;
			MobE.MobTarget = "none";
			MobE.MobDead = "no";
			MobE.MobTimeDead = 500;
			MobE.MobSkillChance = "";
			MobE.MobAtk = 22;
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
