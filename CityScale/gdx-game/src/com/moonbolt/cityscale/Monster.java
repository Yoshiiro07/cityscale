package com.moonbolt.cityscale;

import java.util.ArrayList;

public class Monster {
	
	public String mobID; 
	public float mobPosX;
	public float mobPosY;
	public String name;
	public boolean inCasting;
	public String target;
	public boolean dead;
	public int hp;
	public int mp;
	public int maxHP;
	public int maxMP;
	public int exp;
	
	
	public Monster () {
		
	}
	
	
	public ArrayList<Monster> LoadingMobs(String map) {		
		ArrayList<Monster> lstMobs = new ArrayList<Monster>();
		if(map.equals("Streets305")) {
			Monster mob = new Monster();
			mob.mobID = "SlimeA";
			mob.mobPosX = 100;
			mob.mobPosY = 100;
			mob.name = "Slime";
			mob.inCasting = false;
			mob.target = "none";
			mob.dead = false;
			mob.hp = 50;
			mob.mp = 50;
			mob.maxHP = 50;
			mob.maxMP = 50;
			mob.exp = 2;		
			lstMobs.add(mob);
		}
			
		return lstMobs;
		
	}
}
