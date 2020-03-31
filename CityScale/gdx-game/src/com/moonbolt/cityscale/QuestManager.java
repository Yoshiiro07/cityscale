package com.moonbolt.cityscale;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class QuestManager {

	//Main
	private Sprite spr_master;
	
	//Atlas
	private TextureAtlas atlas_Npcs;
	
	//Primitives
	private float endright;
	private float endleft;
	private int npcframewalk;
	private int npcframe;
	private int npcframe2;
	private ArrayList<Sprite> lstSprites;
	
	public QuestManager() {
		ArrayList<Sprite> lstSprites = new ArrayList<Sprite>();
		
		atlas_Npcs = new TextureAtlas(Gdx.files.internal("data/characters/npcs/npcs.txt"));
	}
	
	public ArrayList<Sprite> ShowNPCS(String map){
		
		   lstSprites.clear();
		   npcframe++;
		   npcframe2++;
		   npcframewalk++;
		   endright = endright - 0.4f;
		   endleft = endleft + 0.4f;
		   
		   if(npcframe > 300){
			   npcframe = 1;
		   }
		   if(npcframe2 > 120){
				npcframe2 = 1;
			}
		   
		   if(map.equals("MetroStation")){	   
			   if(endright < - 50){ endright = 300; }
			   if(endleft > 300){ endleft = -50; }
		   }
		   
			if(map.equals("MetroStation")){
			   //Guarda//
			   if(npcframe >= 1 && npcframe <= 100){ spr_master = atlas_Npcs.createSprite("guard1"); }
			   if(npcframe >= 100 && npcframe <= 200){ spr_master = atlas_Npcs.createSprite("guard2"); }
			   if(npcframe >= 200 && npcframe <= 300){ spr_master = atlas_Npcs.createSprite("guard3"); }
			   spr_master.setPosition(100, -15);
			   spr_master.setSize(16, 40);
			   lstSprites.add(spr_master);
			   //Guarda//	
			 
			   //lady 
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("lady1");   
			        spr_master.setPosition(50, -23);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("lady2");   
					spr_master.setPosition(50, -23);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("lady3");   
					spr_master.setPosition(50, -23);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//cooker
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("cooker1");   
			        spr_master.setPosition(170, -25);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("cooker2");   
					spr_master.setPosition(170, -25);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("cooker3");   
					spr_master.setPosition(170, -25);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//worker1
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("worker1");   
			        spr_master.setPosition(endleft, -55);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("worker2");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("worker1");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("worker3");   
			        spr_master.setPosition(endleft, -55);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("worker1");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("worker2");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//walker1
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("walker1");   
			        spr_master.setPosition(endleft - 105, -60);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("walker2");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("walker1");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("walker3");   
			        spr_master.setPosition(endleft - 105, -60);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("walker1");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("walker2");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				
				//SchoolerD
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerC2");   
			        spr_master.setPosition(endleft, -75);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerC1");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerC2");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerC3");   
			        spr_master.setPosition(endleft, -75);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerC2");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerC1");   
					spr_master.setPosition(endleft, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//SchoolerC
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerD2");   
			        spr_master.setPosition(endright + 70, -30);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerD1");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerD2");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerD3");   
			        spr_master.setPosition(endright + 70, -30);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerD2");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerD1");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
			}
			
			
			if(map.equals("Streets305")){
				//SchoolerB
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("schoolerB1");   
			        spr_master.setPosition(209, -152);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("schoolerB1");   
					spr_master.setPosition(209, -152);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerB2");   
					spr_master.setPosition(209, -152);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//Cristal guy
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("crystalguy1");   
			        spr_master.setPosition(349, -80);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("crystalguy2");   
					spr_master.setPosition(349, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("crystalguy3");   
					spr_master.setPosition(349, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//Flower Girl
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("flowergirl1");   
			        spr_master.setPosition(62, -80);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("flowergirl2");   
					spr_master.setPosition(62, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("flowergirl3");   
					spr_master.setPosition(62, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//SchoolerA
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerA2");   
			        spr_master.setPosition(endleft, -131);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerA1");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerA2");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerA3");   
			        spr_master.setPosition(endleft, -131);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerA2");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerA1");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
			}
			
			return lstSprites;
		}
	
}
