package com.moonbolt.cityscale;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class UIManager {

	//Primitives
	private float objectPositionCenter;
	private float objectDivisibleWidth;
	private float objectDivisibleHeight;
	private Sprite spr_master;
	private ArrayList<Sprite> lstUIElements;
	private ArrayList<BitmapFont> lstUITextElements;
	private ArrayList<BitmapFont> lstUITextMonsterElements;
	
	//Textures
	TextureAtlas atlas_gameplay;
	Texture tex_placeholder;
	
	//Tips :  cc stands for CameraCoords from Render Class
	
	public UIManager() {
		
		tex_placeholder = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_master = new Sprite(tex_placeholder);
		
		lstUIElements = new ArrayList<Sprite>();
		atlas_gameplay = new TextureAtlas(Gdx.files.internal("data/interface/gameplay/gameplay.txt"));
	}
	
	public void UIManagerBase(GameControl gameControl, float touchPosX, float touchPosY, float ccX, float ccY) {
		
	}
	
	public ArrayList<Sprite> ShowUIManagerBase(){
		
		
		return lstUIElements;
	}
	
	public ArrayList<BitmapFont> ShowTextInfo(){
		
		return lstUITextElements;
	}
	
	public ArrayList<BitmapFont> ShowTextMonsterInfo(){
		
		return lstUITextMonsterElements;
	}
	
	public boolean UIManagerSelectOther(String type, GameControl gameControl, float touchPosX, float touchPosY) {
		
		//Main Menu
		if(type.equals("MainMenu")) {
		
			//Button Access
			objectPositionCenter = 75;
			objectDivisibleWidth = 25/2;
			//if(touchPosX > (75 - (25/2))  )
			
		}
		
		//Character Select
		if(type.equals("CharacterSelect")) {
		
		}
		
		return false;
	}
	
	public Sprite ShowUIElementOther(String type,String item, float ccX, float ccY) {
		
		//Main Menu
		if(type.equals("MainMenu")) {
			
			//Button Access
			if(item.equals("btnAccess")) {spr_master = atlas_gameplay.createSprite("btnAcessar"); spr_master.setPosition(75, 16);spr_master.setSize(25, 9);}
		}
		
		//Character Select
		if(type.equals("CharacterSelect")) {
		
		}
		
		return spr_master;
	}
}
