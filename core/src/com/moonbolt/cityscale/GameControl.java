package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {

	//Variables
	private Json json;
	private FileHandle file;
	private Player player;
	private Random random;
	
	//Player
	private int countFrame = 1;
	private String breakwalk = "";
	
	private TextureAtlas atlas_genericset;
	private TextureAtlas atlas_basicset;
	private TextureAtlas atlas_hairs;
	
	private Sprite spr_master;
	
	public GameControl() {	
		json = new Json();
		player = new Player();
		random = new Random();
		
		atlas_genericset = new TextureAtlas(Gdx.files.internal("data/assets/chars/basic/basic.txt"));
		atlas_basicset = new TextureAtlas(Gdx.files.internal("data/assets/chars/basic/basic.txt"));
		
		atlas_hairs = new TextureAtlas(Gdx.files.internal("data/assets/chars/hairs/hairs.txt"));
	}
	
	//Summary
	//[A] [DATAMANAGER]
	//[B] [CHARACTER]
	
	
	//[A] [DATAMANAGER] //
	public void CheckData() {
		file = Gdx.files.local("SaveData/save.json");		
		if(!file.exists()) { 
			CreateNewData(); 
		}
	}
	
	public void CreateNewData() {
		player = new Player();
		int accountnumber = random.nextInt(999999);
		while (accountnumber < 100000) {
			accountnumber = random.nextInt(999999);
		}
		player.accountID = String.valueOf(accountnumber);
		player.name_1 = "none";
		player.name_2 = "none";
		player.name_3 = "none";
		
		SaveData(player);
	}
	
	public void SaveData(Player playerInfo) {		
		file = Gdx.files.local("SaveData/SvDT.json");
		file.writeString(Base64Coder.encodeString(json.prettyPrint(playerInfo)),false);
	}
	
	public void LoadData() {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		player = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));	
	}
	
	public void CreateNewChar(String name, String sex, String hair) {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		player = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));
		player.CreateNew(name, sex, hair);
		SaveData(player);
	}
	
	public void DeleteChar(int num) {
		FileHandle file = Gdx.files.local("SaveData/SvDT.json");
		player = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));
		player.DeleteChar(num);
		SaveData(player);	
	}
	
	public Player GetPlayer() {
		return player;
	}
	
	
	//[B] [CHARACTER]
	public Sprite LoadCharUpMenu(Player player,String type) {
		
		//CreateMenu male
		if(type.equals("malecreate")) {
			spr_master = atlas_basicset.createSprite("u_male_front1");
			spr_master.setPosition(17.5f, 50);
			spr_master.setSize(10, 20);
			return spr_master;
		}
		//CreateMenu female
		if(type.equals("femalecreate")) {
			spr_master = atlas_basicset.createSprite("u_female_front1");
			spr_master.setPosition(17.5f, 50);
			spr_master.setSize(10, 20);
			return spr_master;
		}
		return spr_master;
	}

	public Sprite LoadCharBottomMenu(Player player,String type){
		//CreateMenu male
		if(type.equals("malecreate")) {
			spr_master = atlas_basicset.createSprite("b_male_front1");
			spr_master.setPosition(17.7f, 38);
			spr_master.setSize(10, 20);
			return spr_master;
		}
		//CreateMenu female
		if(type.equals("femalecreate")) {
			spr_master = atlas_basicset.createSprite("b_female_front1");
			spr_master.setPosition(17.7f, 38);
			spr_master.setSize(10, 20);
			return spr_master;
		}

		return spr_master;
	}
	
	public Sprite LoadCharHairsMenu(String sex, String hair) {
		//CreateMenu male
		if(hair.equals("hair1") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair1"); }
		if(hair.equals("hair2") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair2"); }
		if(hair.equals("hair3") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair3"); }
		if(hair.equals("hair4") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair4"); }
		if(hair.equals("hair5") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair5"); }
		if(hair.equals("hair6") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair6"); }
		if(hair.equals("hair7") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair7"); }
		if(hair.equals("hair8") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair8"); }
		
		//CreateMenu female
		if(hair.equals("hair1") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair1_f");}
		if(hair.equals("hair2") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair2_f");}
		if(hair.equals("hair3") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair3_f");}
		if(hair.equals("hair4") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair4_f");}
		if(hair.equals("hair5") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair5_f");}
		if(hair.equals("hair6") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair6_f");}
		if(hair.equals("hair7") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair7_f");}
		if(hair.equals("hair8") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair8_f");}
		
		spr_master.setPosition(18.5f,62.8f);
		spr_master.setSize(8, 15);
		return spr_master;
		
	}
	
	public Sprite HairPallete(String hairName, String sex) {
		if(hairName.equals("hair1") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair1"); spr_master.setPosition(36.7f, 33);}
		if(hairName.equals("hair2") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair2"); spr_master.setPosition(43f, 33);}
		if(hairName.equals("hair3") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair3"); spr_master.setPosition(49f, 33);}
		if(hairName.equals("hair4") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair4"); spr_master.setPosition(55.3f, 33);}
		if(hairName.equals("hair5") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair5"); spr_master.setPosition(61.5f, 33);}
		if(hairName.equals("hair6") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair6"); spr_master.setPosition(67.6f, 33);}
		if(hairName.equals("hair7") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair7"); spr_master.setPosition(73.7f, 33);}
		if(hairName.equals("hair8") && sex.equals("M")) { spr_master = atlas_hairs.createSprite("hair8"); spr_master.setPosition(80f, 33);}
		
		if(hairName.equals("hair1") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair1_f"); spr_master.setPosition(36.7f, 33);}
		if(hairName.equals("hair2") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair2_f"); spr_master.setPosition(43f, 33);}
		if(hairName.equals("hair3") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair3_f"); spr_master.setPosition(49f, 33);}
		if(hairName.equals("hair4") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair4_f"); spr_master.setPosition(55.3f, 33);}
		if(hairName.equals("hair5") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair5_f"); spr_master.setPosition(61.5f, 33);}
		if(hairName.equals("hair6") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair6_f"); spr_master.setPosition(67.6f, 33);}
		if(hairName.equals("hair7") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair7_f"); spr_master.setPosition(73.7f, 33);}
		if(hairName.equals("hair8") && sex.equals("F")) { spr_master = atlas_hairs.createSprite("hair8_f"); spr_master.setPosition(80f, 33);}
		spr_master.setSize(8, 15);
		return spr_master;
	}
	
	
	public Sprite LoadCharUp(Player player, String Menu, int numchar) {
		//Select
		if(Menu.equals("yes")) {
			String set = "";
			String sex = "";
			if(numchar == 1) { set = player.set_1; sex = player.sex_1; }
			if(numchar == 2) { set = player.set_2; sex = player.sex_2; }
			if(numchar == 3) { set = player.set_3; sex = player.sex_3; }
			
			if(set.equals("basic")) { atlas_genericset = atlas_basicset; }
			
			if(sex.equals("M")) { spr_master = atlas_genericset.createSprite("u_male_front1"); }
			if(sex.equals("F")) { spr_master = atlas_genericset.createSprite("u_female_front1"); }
			
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite LoadCharBottom(Player player, String Menu, int numchar) {
		//Select
		if(Menu.equals("yes")) {
			String set = "";
			String sex = "";
			if(numchar == 1) { set = player.set_1; sex = player.sex_1; }
			if(numchar == 2) { set = player.set_2; sex = player.sex_2; }
			if(numchar == 3) { set = player.set_3; sex = player.sex_3; }
			
			if(set.equals("basic")) { atlas_genericset = atlas_basicset; }
			
			if(sex.equals("M")) { spr_master = atlas_genericset.createSprite("b_male_front1"); }
			if(sex.equals("F")) { spr_master = atlas_genericset.createSprite("b_female_front1"); }
			
			return spr_master;
		}
		
		return spr_master;
	}
	
	public Sprite LoadCharHair(Player player, String Menu, int numchar) {
		
		spr_master = atlas_hairs.createSprite("hair1");
		
		//Select
		if(Menu.equals("yes")) {
			String sex = "";
			String hair = "";
			if(numchar == 1) { hair = player.hair_1; sex = player.sex_1; }
			if(numchar == 2) { hair = player.hair_2; sex = player.sex_2; }
			if(numchar == 3) { hair = player.hair_3; sex = player.sex_3; }
				
			if(sex.equals("M")) { spr_master = atlas_hairs.createSprite(hair); }
			if(sex.equals("F")) { spr_master = atlas_hairs.createSprite(hair + "_f"); }
			
			return spr_master;
		}
		
		return spr_master;
	}
}
