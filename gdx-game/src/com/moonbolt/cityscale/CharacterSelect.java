package com.moonbolt.cityscale;

import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	private MainGame game;
	private ManagerScreen screen;
	private GameControl gameControl;
	private OrthographicCamera camera;
    private Viewport viewport;
    private Player player;
    private String charname = "";
    private String systemMsg = "";
    private String sex = "M";
    
    private String state = "Main";
    
    private Sprite spr_background;
	private Texture tex_background;
	private float posbackground = 0;
	
	private TextureAtlas atlas_ui;
	private Sprite spr_mainmenu;
	
	private Sprite spr_master;
	private Texture tex_master;
	
	private Sprite spr_testdot;
	private Texture tex_testdot;
	
	private BitmapFont font_master;
	
	public CharacterSelect(MainGame game, ManagerScreen screen, GameControl gameControl){
		this.screen = screen;
		this.game = game;
		this.gameControl = gameControl;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		player = new Player();
				
		tex_background = new Texture(Gdx.files.internal("data/assets/maps/characterselect.png"));
		spr_background = new Sprite(tex_background);

		tex_testdot = new Texture(Gdx.files.internal("data/assets/misc/etc/testdot.png"));
		spr_testdot = new Sprite(tex_testdot);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/assets/font/impact.fnt"),Gdx.files.internal("data/assets/font/impact.png"), false);
		font_master.setColor(Color.WHITE);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);
		
		//Atlas
		atlas_ui = new TextureAtlas(Gdx.files.internal("data/assets/ui/ui.txt"));
		spr_mainmenu = new Sprite(tex_testdot);
	}

	@Override
	public void render(float p1)
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
			
		game.batch.begin();
		
		TrainMove();
		
		if(state.equals("Main")) {
			spr_master = atlas_ui.createSprite("btncriar");
			spr_master.setPosition(5, 4);
			spr_master.setSize(17,10);
			spr_master.draw(game.batch);
			
			spr_master = atlas_ui.createSprite("btnexcluir");
			spr_master.setPosition(80, 4);
			spr_master.setSize(17,10);
			spr_master.draw(game.batch);
			
			spr_master = atlas_ui.createSprite("selecionepersonagem");
			spr_master.setPosition(5, 85);
			spr_master.setSize(45,8);
			spr_master.draw(game.batch);
		}
		
		if(state.equals("Create")) {
			spr_master = atlas_ui.createSprite("createmenu");
			spr_master.setPosition(11, 10);
			spr_master.setSize(80,80);
			spr_master.draw(game.batch);
			
			if(!charname.equals("")){
				font_master.setColor(Color.WHITE);
				font_master.getData().setScale(0.09f,0.16f);
				font_master.setUseIntegerPositions(false);
				font_master.draw(game.batch, charname , 39, 70);
			}
			
			if(sex.equals("M")) { spr_master = gameControl.LoadCharUp(player, "malecreate"); spr_master.draw(game.batch); }
			if(sex.equals("F")) { spr_master = gameControl.LoadCharUp(player, "femalecreate"); spr_master.draw(game.batch); }
			
			font_master.draw(game.batch, charname , 39, 70);		
		}
		
			
		spr_testdot.setPosition(37,72);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
		
		spr_testdot.setPosition(63,63);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
			
		game.batch.end();
	}
	
	public void TrainMove() {
		posbackground += 0.02f;
		
		if(posbackground >= 1) {
			posbackground = -1f;
		}
		
		spr_background.setPosition(0,posbackground);
		spr_background.setSize(100,100);
		spr_background.draw(game.batch);
		
	}
	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

		if(state.equals("Main")) {
			if(coordsTouch.x >= 5 && coordsTouch.x <= 21 && coordsTouch.y >= 4 && coordsTouch.y <= 13){
				//Criar
				state = "Create";
				return false;
			}
		}
		
		if(state.equals("Create")) {
			if(coordsTouch.x >= 37 && coordsTouch.x <= 63 && coordsTouch.y >= 63 && coordsTouch.y <= 72){
				//Nome
				Gdx.input.getTextInput(this,"Digite o nome","","");
				return false;
			}
		}
			
		return false;
	}
	
	public boolean CheckName() {
		
		if(charname.equals("none")){ systemMsg = "Insira um nome"; return false;}
		if(charname.equals("")) { systemMsg = "Insira um nome"; return false; }
		if(charname.contains(".")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(";")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("'")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("~")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(":")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("?")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("!")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("-")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("*")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("@")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("#")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("$")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("%")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("&")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("(")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(")")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("=")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("/")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("\\")){ systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains("<")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.contains(">")) { systemMsg = "Nome com caracters especiais"; return false; }
		if(charname.length() > 10) { systemMsg = "Ate 10 letras"; return false; }
		if(charname.length() < 2) { systemMsg = "Menos de 2 letras"; return false; }
		
		return false;
	}
	
	@Override
	public void input(String input) {
		if(state.equals("Create")) {
			if(CheckName()) {
				charname = input;
			}	
		}	
	}
	
	
	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void show()
	{
		
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void dispose()
	{
		game.dispose();
	}
	
	@Override
	public void create()
	{
		// TODO: Implement this method
	}

	@Override
	public void render()
	{
		// TODO: Implement this method
	}
	
	@Override
	public boolean keyDown(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyUp(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyTyped(char p1)
	{
		// TODO: Implement this method
		return false;
	}
	
	@Override
	public boolean touchUp(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean touchDragged(int p1, int p2, int p3)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean mouseMoved(int p1, int p2)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean scrolled(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

	
}
