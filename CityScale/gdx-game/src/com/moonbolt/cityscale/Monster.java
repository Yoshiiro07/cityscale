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
	
	public Monster() {}
	
	
}
