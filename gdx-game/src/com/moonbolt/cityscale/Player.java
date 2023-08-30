package com.moonbolt.cityscale;

import java.util.Random;

public class Player {
	
	public Random rand;

	public String accountID;
	public String charnumber;
	
	public String name;
	public String sex;
	public String job;
	public String weapon;
	public String level;
	public String stats;
	public String stamina;
	public String staminamax;
	public String set;
	public String hair;
	public String hat;
	public String exp;
	public String hp;
	public String mp;
	public String maxhp;
	public String maxmp;
	public String atk;
	public String def;
	public String money;
	public String cooldown;
	public String statusPoint;
	public String skillPoint;
	public String skills;
	public String buffsA;
	public String buffsB;
	public String buffsC;
	public String coordX;
	public String coordY;
	public String frame;
	public String side;
	public String walk;
	public String inBattle;
	public String target;
	public String itens;
	public String map;
	public String party;
	public String inCasting;
	public String quest;
	public String status;
	public String hotkey1;
	public String hotkey2;
	public String heal;
	public String expshared;
	public String crystalA;
	public String crystalB;
	public String crystalC;
	public String crystalD;
	public String itensHousing;
	
	
	public void CreateNewData() {
		
		rand = new Random();
		int numacc = rand.nextInt(999999);
		
		accountID = "";
		charnumber = "1";	
		name = "none";
		sex = "M";
		job = "novice";
		weapon = "basic";
		level = "1";
		stats = "1:1:1:1:1:1";
		stamina = "100";
		staminamax = "100";
		set = "basic";
		hair = "hair1"; 
		hat = "none";
		exp = "0";
		hp = "100";
		mp = "100";
		maxhp = "50";
		maxmp = "50";
		atk = "2";
		def = "1";
		money = "0";
		cooldown = "0";
		statusPoint = "0";
		buffsA = "none";
		buffsB = "none";
		buffsC = "none";
		coordX = "0";
		coordY = "0";
		frame = "1";
		side = "front";
		walk = "no";
		inBattle = "no";
		target = "none";
		itens = "none";
		map = "Station";
		party = "none";
		inCasting = "no";
		quest = "none";
		status = "none";
		hotkey1 = "none";
		heal = "0";
		expshared = "0";
		crystalA = "none";
		crystalB = "none";
		crystalC = "none";
		crystalD = "none";
	}
	
	public void FillData(String _name, String _sex, String _hair) {
		name = _name;
		sex = _sex;
		hair = _hair;
	}
}
