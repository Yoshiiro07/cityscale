package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CharacterSelect implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	////MAINLY///
	private MainGame game;
	private GameControl gameControl;
	private Player activeplayer;
	private String[] config;
	private String platform;
	private String networkState; 
	
	//Primitives
	private String touchResult = "";
	private String screenShow = "CharacterScreenMain";
	private String text = "";
	private String sex = "M";
	private String hair = "";
	private String name = "";
	private boolean changeScreen = false;
	private float movBackground = 0;
	
	float posTouchX = 0;
	float posTouchY = 0;
	
	//Camera
	private OrthographicCamera camera;
    private Viewport viewport;

	//Sprite 
	private Sprite spr_Background;
	private Sprite spr_titleTop;
	private Sprite spr_btnCreate;
	private Sprite spr_btnDelete;
	private Sprite spr_boardCreate;
	private Sprite spr_characterSet;
	private Sprite spr_hair;
	
	//Texture
	private Texture tex_Background;
	
	//fonts
	private BitmapFont font_master;
	
	//teste
	Sprite spr_teste;
	Texture tex_teste;
	
	public CharacterSelect(MainGame gameAlt,GameControl gameControl, String[] configAlt, String platformAlt){
		this.game = gameAlt;
		this.gameControl = gameControl;
		this.config = configAlt;
		this.platform = platformAlt;
		this.networkState = "no";
		this.activeplayer = gameControl.LoadPlayer();
		
		//Camera and Inputs
		camera = new OrthographicCamera();
	    viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.11f,0.23f);
		font_master.setUseIntegerPositions(false);	
		
		//Sprites
		tex_Background = new Texture(Gdx.files.internal("data/maps/characterselect.png"));
		spr_Background = new Sprite(tex_Background);		
	}

	@Override
	public void render(float p1)
	{	
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		//Sprites
		MovBackground();
		spr_Background.setPosition(0, movBackground);
		spr_Background.setSize(100, 100);
		spr_Background.draw(game.batch);
		
		//Main Screen Character Select
		if(screenShow.equals("CharacterScreenMain")) {
			spr_titleTop = gameControl.LoadInterfaceCreate("titleTopSelect");
			spr_titleTop.draw(game.batch);
			
			spr_btnCreate = gameControl.LoadInterfaceCreate("btnCreate");
			spr_btnCreate.draw(game.batch);
			
			spr_btnDelete = gameControl.LoadInterfaceCreate("btnDelete");
			spr_btnDelete.draw(game.batch);
				
		}
		
		//Create Character Screen
		if(screenShow.equals("CharacterScreenCreate")) {
			spr_titleTop = gameControl.LoadInterfaceCreate("titleTopCreate");
			spr_titleTop.draw(game.batch);
			
			spr_boardCreate = gameControl.LoadInterfaceCreate("boardCreate");
			spr_boardCreate.draw(game.batch);
			
			font_master.setColor(Color.WHITE);
			font_master.getData().setScale(0.11f,0.12f);
			font_master.setUseIntegerPositions(false);	
			font_master.draw(game.batch, text, 37.2f,73);	
			
			
			spr_characterSet = gameControl.ShowCharacterMenu(sex); 
			
		}
		
		
		//Delete Character Screen
		if(screenShow.equals("CharacterScreenDelete")) {
			spr_titleTop = gameControl.LoadInterfaceCreate("titleTopDelete");
			spr_titleTop.draw(game.batch);		
		}
		
		
		
		//Change Screen
		if(changeScreen){		
		    game.AtualizaElementos(game,gameControl, config, platform, networkState);
		    game.Switch("CharacterSelect");			
		}
		
	
		//Test
		font_master.draw(game.batch, "X:" + posTouchX,posTouchX, posTouchY);
		//font_master.draw(game.batch, "Y:" + posTouchY,posTouchX, posTouchY);
				
		
		game.batch.end();
	}
	
	
	
	private void MovBackground() {
		movBackground = movBackground + 0.01f;
		
		if(movBackground > 0.5f) {
			movBackground = 0;
		}
	}

	@Override
	public void resize(int p1, int p2)
	{
		viewport.update(p1,p2);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}	
	
	//TOUCH RESPONSE	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));
		
		if(screenShow.equals("CharacterScreenMain")) {
			touchResult = gameControl.TouchVerify(coordsTouch.x, coordsTouch.y, "CharacterScreenMain");
		}
		
		if(screenShow.equals("CharacterScreenCreate")) {
			touchResult = gameControl.TouchVerify(coordsTouch.x, coordsTouch.y, "CharacterScreenCreate");
		}
		
		posTouchX = coordsTouch.x;
		posTouchY = coordsTouch.y;
		
		if(touchResult.equals("ChangeScreen")) { changeScreen = true; }
		if(touchResult.equals("CharacterScreenCreate")) { screenShow = "CharacterScreenCreate"; }
		if(touchResult.equals("CharacterScreenDelete")) { screenShow = "CharacterScreenDelete"; }
		
		if(touchResult.equals("NameSelect")) { Gdx.input.getTextInput(this,"Digite o nome","","");  }
		
		return false;
	}
	
	@Override
	public void input(String input){
		text = input;	
		name = text;
		//gameControl.OperacaoOnline("Download", text);
	}
	
	@Override
	public void canceled(){
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
	public void hide()
	{
		// TODO: Implement this method
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
		//gameControl = null;
		camera = null;
		viewport = null;
		game.dispose();
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
	public boolean keyUp(int p1)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean keyDown(int p1)
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
}
