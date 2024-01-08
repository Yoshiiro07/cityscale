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
	
	float coordsX = 0;
	float coordsY = 0;
	private float posX = 0;
	private float posY = 0;
	private int frame = 1;
	
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
	//[C] [INTERFACE]
	
	
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
	
	public void SetCharSelected(int num) {
		player.SetSelectedChar(num);
	}
	
	public Player GetPlayer() {
		return player;
	}
	
	public void SetPlayer(Player player) {
		this.player = player;
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
			
			if(sex.equals("M")) { spr_master = atlas_genericset.createSprite("u_male_front1");   }
			if(sex.equals("F")) { spr_master = atlas_genericset.createSprite("u_female_front1"); }
			
			return spr_master;
		}
		
		frame = Integer.parseInt(player.frame_A);
		
		if(countFrame >= 1 && countFrame <= 10) { frame = 2;  }
		if(countFrame >= 10 && countFrame <= 20) { frame = 1; }
		if(countFrame >= 20 && countFrame <= 30) { frame = 3; }
		if(countFrame >= 30 && countFrame <= 40) { frame = 1; }
		if(countFrame >= 40) { countFrame = 1; }
		if(player.walk_A.equals("yes")) { countFrame++; } else { frame = 1; }
		
		
		if(player.set_A.equals("basic")) { atlas_genericset = atlas_basicset; }
			
		coordsX = Float.parseFloat(player.coordX_A);
		coordsY = Float.parseFloat(player.coordY_A);
		if(player.walk_A.equals("yes") && player.side_A.equals("right")) { coordsX = coordsX + 0.6f; }
		if(player.walk_A.equals("yes") && player.side_A.equals("left")) { coordsX = coordsX - 0.6f; }
		if(player.walk_A.equals("yes") && player.side_A.equals("front")) { coordsY = coordsY - 0.6f; }
		if(player.walk_A.equals("yes") && player.side_A.equals("back")) { coordsY = coordsY + 0.6f; }
			
		player.coordX_A = String.valueOf(coordsX);
		player.coordY_A = String.valueOf(coordsY);
		
		String sex = "";
		if(player.sex_A.equals("M")) { sex = "male"; }
		if(player.sex_A.equals("F")) { sex = "female"; }
		
		//Create Sprite
		if(sex.equals("male")) {
			if(player.walk_A.equals("no")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");}
				if(player.side_A.equals("back") && frame == 1)  { spr_master = atlas_genericset.createSprite("u_" + sex + "_back1"); }
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right1"); }
				if(player.side_A.equals("left") && frame == 1)  { spr_master = atlas_genericset.createSprite("u_" + sex + "_left1"); }
			}
			if(player.walk_A.equals("yes")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front" + frame);}
				if(player.side_A.equals("front") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front" + frame);}
				if(player.side_A.equals("front") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front" + frame);}
				
				if(player.side_A.equals("back") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_back" + frame);  }
				if(player.side_A.equals("back") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_back" + frame);  }
				if(player.side_A.equals("back") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_back" + frame);  }
				
				if(player.side_A.equals("left") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_left" + frame);  }
				if(player.side_A.equals("left") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_left" + frame);  }
				if(player.side_A.equals("left") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_left" + frame);  }
				
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right" + frame);  }
				if(player.side_A.equals("right") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right" + frame);  }
				if(player.side_A.equals("right") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right" + frame);  }
				
				if(player.side_A.equals("left-front") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }
				if(player.side_A.equals("left-back") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");   }
				if(player.side_A.equals("right-back") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }
				if(player.side_A.equals("right-front") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1"); }
				if(player.side_A.equals("back-right") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }
				if(player.side_A.equals("back-left") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");   }
				if(player.side_A.equals("front-right") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1"); }
				if(player.side_A.equals("front-left") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }			
			}
		}
		
		if(sex.equals("female")) {
			if(player.walk_A.equals("no")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");}
				if(player.side_A.equals("back") && frame == 1)  { spr_master = atlas_genericset.createSprite("u_" + sex + "_back1"); }
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right1"); }
				if(player.side_A.equals("left") && frame == 1)  { spr_master = atlas_genericset.createSprite("u_" + sex + "_left1"); }
			}
			if(player.walk_A.equals("yes")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front" + frame);}
				if(player.side_A.equals("front") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front" + frame);}
				if(player.side_A.equals("front") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_front" + frame);}
				
				if(player.side_A.equals("back") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_back" + frame);  }
				if(player.side_A.equals("back") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_back" + frame);  }
				if(player.side_A.equals("back") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_back" + frame);  }
				
				if(player.side_A.equals("left") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_left" + frame);  }
				if(player.side_A.equals("left") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_left" + frame);  }
				if(player.side_A.equals("left") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_left" + frame);  }
				
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right" + frame);  }
				if(player.side_A.equals("right") && frame == 2) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right" + frame);  }
				if(player.side_A.equals("right") && frame == 3) { spr_master = atlas_genericset.createSprite("u_" + sex + "_right" + frame);  }
				
				if(player.side_A.equals("left-front") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }
				if(player.side_A.equals("left-back") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");   }
				if(player.side_A.equals("right-back") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }
				if(player.side_A.equals("right-front") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1"); }
				if(player.side_A.equals("back-right") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }
				if(player.side_A.equals("back-left") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");   }
				if(player.side_A.equals("front-right") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1"); }
				if(player.side_A.equals("front-left") ) {spr_master = atlas_genericset.createSprite("u_" + sex + "_front1");  }			
			}
		}
				
		spr_master.setPosition(coordsX+9.8f, coordsY+92);
		spr_master.setSize(9, 15);
		
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
		
		if(player.set_A.equals("basic")) { atlas_genericset = atlas_basicset; }
		
		String sex = "";
		if(player.sex_A.equals("M")) { sex = "male"; }
		if(player.sex_A.equals("F")) { sex = "female"; }
		
		
		
		//frame = 2;
		//Create Sprite
		if(sex.equals("male")) {
			if(player.walk_A.equals("no")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front1"); spr_master.setPosition(coordsX+10, coordsY+82.2f);}
				if(player.side_A.equals("back") && frame == 1)  { spr_master = atlas_genericset.createSprite("b_" + sex + "_back1"); spr_master.setPosition(coordsX+10, coordsY+82.2f);}
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right1"); spr_master.setPosition(coordsX+10, coordsY+82.7f);}
				if(player.side_A.equals("left") && frame == 1)  { spr_master = atlas_genericset.createSprite("b_" + sex + "_left1"); spr_master.setPosition(coordsX+10, coordsY+82.7f);}
			}
			if(player.walk_A.equals("yes")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front" + frame); spr_master.setPosition(coordsX+10, coordsY+82.2f);}
				if(player.side_A.equals("front") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front" + frame); spr_master.setPosition(coordsX+9.8f, coordsY+83.2f);}
				if(player.side_A.equals("front") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front" + frame); spr_master.setPosition(coordsX+9.8f, coordsY+83.2f);}
				
				if(player.side_A.equals("back") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_back" + frame); spr_master.setPosition(coordsX+10, coordsY+82.2f);  }
				if(player.side_A.equals("back") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_back" + frame); spr_master.setPosition(coordsX+10, coordsY+82.2f);  }
				if(player.side_A.equals("back") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_back" + frame); spr_master.setPosition(coordsX+10, coordsY+82.2f);  }
				
				if(player.side_A.equals("left") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_left" + frame); spr_master.setPosition(coordsX+10, coordsY+82.8f);  }
				if(player.side_A.equals("left") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_left" + frame); spr_master.setPosition(coordsX+10, coordsY+82.8f); }
				if(player.side_A.equals("left") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_left" + frame); spr_master.setPosition(coordsX+10, coordsY+82.8f); }
				
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right" + frame); spr_master.setPosition(coordsX+10, coordsY+82.8f); }
				if(player.side_A.equals("right") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right" + frame); spr_master.setPosition(coordsX+10, coordsY+82.8f); }
				if(player.side_A.equals("right") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right" + frame); spr_master.setPosition(coordsX+10, coordsY+82.8f); }
				
				if(player.side_A.equals("left-front") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }
				if(player.side_A.equals("left-back") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");   }
				if(player.side_A.equals("right-back") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }
				if(player.side_A.equals("right-front") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1"); }
				if(player.side_A.equals("back-right") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }
				if(player.side_A.equals("back-left") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");   }
				if(player.side_A.equals("front-right") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1"); }
				if(player.side_A.equals("front-left") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }		
			}
		}
		if(sex.equals("female")) {
			if(player.walk_A.equals("no")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front1"); spr_master.setPosition(coordsX+9.9f, coordsY+80.3f);}
				if(player.side_A.equals("back") && frame == 1)  { spr_master = atlas_genericset.createSprite("b_" + sex + "_back1"); spr_master.setPosition(coordsX+9.8f, coordsY+80.3f);}
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right1"); spr_master.setPosition(coordsX+10.2f, coordsY+80f);}
				if(player.side_A.equals("left") && frame == 1)  { spr_master = atlas_genericset.createSprite("b_" + sex + "_left1"); spr_master.setPosition(coordsX+10, coordsY+80);}
			}
			if(player.walk_A.equals("yes")) {
				if(player.side_A.equals("front") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front" + frame); spr_master.setPosition(coordsX+10, coordsY+80.3f);}
				if(player.side_A.equals("front") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front" + frame); spr_master.setPosition(coordsX+9.9f, coordsY+80.3f);}
				if(player.side_A.equals("front") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_front" + frame); spr_master.setPosition(coordsX+9.9f, coordsY+80.3f);}
				
				if(player.side_A.equals("back") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_back" + frame); spr_master.setPosition(coordsX+10, coordsY+80.3f);  }
				if(player.side_A.equals("back") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_back" + frame); spr_master.setPosition(coordsX+10, coordsY+80.3f);  }
				if(player.side_A.equals("back") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_back" + frame); spr_master.setPosition(coordsX+10, coordsY+80.3f);  }
				
				if(player.side_A.equals("left") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_left" + frame); spr_master.setPosition(coordsX+10, coordsY+80);  }
				if(player.side_A.equals("left") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_left" + frame); spr_master.setPosition(coordsX+10, coordsY+80); }
				if(player.side_A.equals("left") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_left" + frame); spr_master.setPosition(coordsX+10, coordsY+80); }
				
				if(player.side_A.equals("right") && frame == 1) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right" + frame); spr_master.setPosition(coordsX+10, coordsY+80); }
				if(player.side_A.equals("right") && frame == 2) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right" + frame); spr_master.setPosition(coordsX+10, coordsY+80); }
				if(player.side_A.equals("right") && frame == 3) { spr_master = atlas_genericset.createSprite("b_" + sex + "_right" + frame); spr_master.setPosition(coordsX+10, coordsY+80); }
				
				if(player.side_A.equals("left-front") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }
				if(player.side_A.equals("left-back") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");   }
				if(player.side_A.equals("right-back") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }
				if(player.side_A.equals("right-front") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1"); }
				if(player.side_A.equals("back-right") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }
				if(player.side_A.equals("back-left") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");   }
				if(player.side_A.equals("front-right") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1"); }
				if(player.side_A.equals("front-left") ) {spr_master = atlas_genericset.createSprite("b_" + sex + "_front1");  }		
			}
		}
				
		
		spr_master.setSize(9, 15);
		
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
		
		
		if(player.sex_A.equals("M")) {
			if(player.side_A.equals("front")) { spr_master = atlas_hairs.createSprite(player.hair_A); spr_master.setPosition(coordsX + 10.8f, coordsY +101.6f); spr_master.setSize(7,13);}
			if(player.side_A.equals("back")) { spr_master = atlas_hairs.createSprite(player.hair_A + "up"); spr_master.setPosition(coordsX + 10.5f, coordsY +101.6f); spr_master.setSize(8,11);}
			if(player.side_A.equals("right")) { spr_master = atlas_hairs.createSprite(player.hair_A + "right"); spr_master.setPosition(coordsX + 10.6f, coordsY +102.4f); spr_master.setSize(8,10);}
			if(player.side_A.equals("left")) { spr_master = atlas_hairs.createSprite(player.hair_A + "left"); spr_master.setPosition(coordsX + 10.6f, coordsY +102.4f); spr_master.setSize(8,10);}
		}
		
		if(player.sex_A.equals("F")) {
			if(player.side_A.equals("front")) { spr_master = atlas_hairs.createSprite(player.hair_A + "_f"); spr_master.setPosition(coordsX + 10.8f, coordsY +101.6f); spr_master.setSize(8,14);}
			if(player.side_A.equals("back")) { spr_master = atlas_hairs.createSprite(player.hair_A + "up_f"); spr_master.setPosition(coordsX + 10.5f, coordsY +101.6f); spr_master.setSize(8,14);}
			if(player.side_A.equals("right")) { spr_master = atlas_hairs.createSprite(player.hair_A + "right_f"); spr_master.setPosition(coordsX + 10.6f, coordsY +102.4f); spr_master.setSize(8,12);}
			if(player.side_A.equals("left")) { spr_master = atlas_hairs.createSprite(player.hair_A + "left_f"); spr_master.setPosition(coordsX + 10.6f, coordsY +102.4f); spr_master.setSize(8,12);}
		}
		
		return spr_master;
	}
	
	
	
	//[C] [INTERFACE] //
	
	
	
}
