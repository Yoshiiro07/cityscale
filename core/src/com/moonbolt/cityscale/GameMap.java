package com.moonbolt.cityscale;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class GameMap implements Screen, ApplicationListener, InputProcessor, TextInputListener  {

	private MainGame game;
	private ManagerScreen screen;
	private GameControl gameControl;
	private OrthographicCamera camera;
    private Viewport viewport;
    private Player player;
    private String systemMsg = "";
    private BitmapFont font_master;
    private String State = "Main";
    private boolean movement = false;
    private float padX = -60;
    private float padY = -55;
    private String breakwalk = "";
    private int countFrame = 1;
    private boolean playerDead = false;
    
    private float playerX = 0;
    private float playerY = 0;
    
    
	private Sprite spr_master;
	
	private Sprite spr_metro;
	private Texture tex_metro;
	private float metroposX = -100;
	
	private Sprite spr_background;
	private Texture tex_background;
	
	private Texture tex_testdot;
	private Sprite spr_testdot;
	
	//Controller
    private final IntSet downKeys = new IntSet(20);	
	
	public GameMap(MainGame gameAlt,ManagerScreen screenAlt,GameControl gameControl, boolean network) {
		this.game = gameAlt;	
		this.screen = screenAlt;
		this.gameControl = gameControl;
		
		//test dot
		tex_testdot = new Texture(Gdx.files.internal("data/assets/misc/etc/testdot.png"));
		spr_testdot = new Sprite(tex_testdot);
		
		//background
		tex_background = new Texture(Gdx.files.internal("data/assets/maps/metrostation.png"));
		spr_background = new Sprite(tex_background);
		
		//metro
		tex_metro = new Texture(Gdx.files.internal("data/assets/misc/etc/metro.png"));
		spr_metro = new Sprite(tex_metro);
		
		//Load Player Data
		player = gameControl.GetPlayer();
		
		//Camera and Inputs
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(135,135,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);

		//font
		font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.07f,0.11f);
		font_master.setUseIntegerPositions(false);
		
	}
	
	
	@Override
	public void render(float delta) {
		try 
		{
			Gdx.gl.glClearColor(1,1,1,1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			camera.update();
		    game.batch.setProjectionMatrix(camera.combined);
				
			game.batch.begin();
				
			spr_background.setPosition(0, 0);
			spr_background.setSize(140,140);
			spr_background.draw(game.batch);
			
			metroposX++;
			if(metroposX > 300) { metroposX = -104; }
			spr_metro.setPosition(metroposX, 106.3f);
			spr_metro.setSize(90, 40);
			spr_metro.draw(game.batch);
			
			//Show Character
			spr_master = gameControl.LoadCharUp(player,"no",0);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.LoadCharBottom(player,"no",0);
			spr_master.draw(game.batch);
			
			spr_master = gameControl.LoadCharHair(player,"no",0);
			spr_master.draw(game.batch);
			
			CheckMapChange();
			
			spr_testdot.setPosition(100,2f);
			spr_testdot.setSize(1,1);
			spr_testdot.draw(game.batch);
			
			spr_testdot.setPosition(120,40);
			spr_testdot.setSize(1,1);
			spr_testdot.draw(game.batch);
			
			game.batch.end();
			
		}
		
		catch(Exception ex) {
			Gdx.app.exit();
		}
		
	}
	
	public void CheckMapChange() {
		playerX = Float.parseFloat(player.coordX_A);
		playerY = Float.parseFloat(player.coordY_A);
		
		//change to city
		if(playerX > 100 && playerX < 120 && playerY > 2f && playerY < 40) {
			game.UpdateComponents(gameControl, false);
			game.Switch("MetroStation");
		}
		
	}

	@Override
	public void input(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		
		
		
		if(playerDead) { return false; }
		
		if(State.equals("Main")) {
			movement = true;		
			downKeys.add(keycode);
	        if (downKeys.size >= 2){
	            onMultipleKeysDown(keycode);
	        }
	        if(downKeys.size == 1) {
	        	if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
	        		player.side_A = "left";
	        		player.walk_A = "yes"; 
	        		padX = -66;
	        		player.inBattle_A = "no";
	        		gameControl.SetPlayer(player);
	        		return false;
	            }
	    		
	    		if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {			
	    			player.side_A = "back";
	    			player.walk_A = "yes"; 
	    			padY = -50;
	    			player.inBattle_A = "no";
	    			gameControl.SetPlayer(player);
	    			return false;
	            }
	    		
	    		if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {	
	    			player.side_A = "front";
	    			player.walk_A = "yes"; 
	    			padY = -60;
	    			player.inBattle_A = "no";
	    			gameControl.SetPlayer(player);
	    			return false;
	            }
	    		
	    		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
	    			player.side_A = "right";
	    			player.walk_A = "yes"; 
	    			padX = -55;
	    			player.inBattle_A = "no";
	    			gameControl.SetPlayer(player);
	    			return false;
	            } 
	    		if (keycode == Input.Keys.NUM_1) {
	    			//if(skillUsed > 0) { return false; }
	    			//skillUsed = 200;
	    			//CheckSkill(1);
					return false;
	            }
	    		if (keycode == Input.Keys.NUM_2) {
	    			//if(skillUsed > 0) { return false; }
	    			//skillUsed = 200;
	    			//CheckSkill(2);
					return false;
	            }
	    		if (keycode == Input.Keys.NUM_3) {
	    			//if(skillUsed > 0) { return false; }
	    			//skillUsed = 200;
	    			//CheckSkill(3);
					return false;
	            }
	    		if (keycode == Input.Keys.TAB) {
	    			//ChangeTarget();
					return false;
	            }
	    		if (keycode == Input.Keys.NUM_4) {
	    			//CheckAction();
					return false;
	            }
	    		if (keycode == Input.Keys.H) {
	    			//if(!player.hotkey1.equals("none")) {
	    			//	UseItem(hotketcountitem);
	    			//}
	            }
	    		if (keycode == Input.Keys.C) {
	    			Gdx.input.getTextInput(this,"Mensagem:","","");
					return false;
	            }
	    		if (keycode == Input.Keys.M) {
	    			//if(state.equals("main")) { state = "menu"; }
					return false;
	            }
	        }
		}
		
		return false;
	}
	
	
	
	private void onMultipleKeysDown (int mostRecentKeycode){
		
		if(State.equals("menu")) { return; }
		
		//For multiple key presses
	    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
	        	player.side_A = "left-front";
	        	player.walk_A = "yes"; 
	        	padX = -66;
	        	padY = -60;
	        	player.inBattle_A = "no";
	        }
	    }
	    if (downKeys.contains(Input.Keys.LEFT) || downKeys.contains(Input.Keys.A)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
	        	player.side_A = "left-back";
	        	player.walk_A = "yes"; 
	        	padX = -66;
	        	padY = -50; 
	        	player.inBattle_A = "no";
	        }
	    }
	    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
	    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.UP) || mostRecentKeycode == Input.Keys.W)){
	    		player.side_A = "right-back";
	    		player.walk_A = "yes"; 
	    		padX = -55;
	    		padY = -50;
	    		player.inBattle_A = "no";
	        }
	    }
	    if (downKeys.contains(Input.Keys.RIGHT) || downKeys.contains(Input.Keys.D)){
	    	if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.DOWN) || mostRecentKeycode == Input.Keys.S)){
	    		player.side_A = "right-front";
	    		player.walk_A = "yes"; 
	    		padX = -55;
	    		padY = -60;
	    		player.inBattle_A = "no";
	        }
	    }
	    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
	        	player.side_A = "back-right";
	        	player.walk_A = "yes"; 
	        	padX = -55;
	        	padY = -50;
	        	player.inBattle_A = "no";
	        }
	    }
	    if (downKeys.contains(Input.Keys.UP) || downKeys.contains(Input.Keys.W)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
	        	player.side_A = "back-left";
	        	player.walk_A = "yes"; 
	        	padX = -66;
	        	padY = -50;
	        	player.inBattle_A = "no";
	        }
	    }
	    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.RIGHT) || mostRecentKeycode == Input.Keys.D)){
	        	player.side_A = "front-right";
	        	player.walk_A = "yes"; 
	        	padX = -55;
	        	padY = -60;
	        	player.inBattle_A = "no";
	        }
	    }
	    if (downKeys.contains(Input.Keys.DOWN) || downKeys.contains(Input.Keys.S)){
	        if (downKeys.size == 2 && ((mostRecentKeycode == Input.Keys.LEFT) || mostRecentKeycode == Input.Keys.A)){
	        	player.side_A = "front-left";
	        	player.walk_A = "yes"; 
	        	padX = -66;
	        	padY = -60;
	        	player.inBattle_A = "no";
	        }
	    }
	}	

	@Override
	public boolean keyUp(int keycode) {
		movement = false;
		downKeys.remove(keycode);
		player.walk_A = "no";
		player.frame_A = "1";
		countFrame = 1;
		padX = -60;
		padY = -55;
		breakwalk = "";
		
		if(player.side_A.equals("left-front")) { player.side_A = "front"; }
		if(player.side_A.equals("left-back")) { player.side_A = "front";}
		if(player.side_A.equals("right-back")) { player.side_A = "front";}
		if(player.side_A.equals("right-front")) { player.side_A = "front";}
		if(player.side_A.equals("back-right")) { player.side_A = "front";}
		if(player.side_A.equals("back-left")) { player.side_A = "front";}
		if(player.side_A.equals("front-right")) { player.side_A = "front";}
		if(player.side_A.equals("front-left")) { player.side_A = "front"; }
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
