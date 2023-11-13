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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MapScreen implements Screen, ApplicationListener, InputProcessor, TextInputListener {
	private MainGame game;
	private ManagerScreen screen;
	private OrthographicCamera camera;
    private Viewport viewport;
    private GameControl gameControl; 
    private BitmapFont font_master;
   
	
	
	public MapScreen(MainGame game, ManagerScreen screen){
		this.screen = screen;
		this.game = game;
		
		camera = new OrthographicCamera();
		viewport = new StretchViewport(100,100,camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		Gdx.input.setInputProcessor(this);
		
		gameControl = new GameControl();
		
		tex_testdot = new Texture(Gdx.files.internal("data/assets/testdot.png"));
		spr_testdot = new Sprite(tex_testdot);
		
		//font
		font_master = new BitmapFont(Gdx.files.internal("data/font/impact.fnt"),Gdx.files.internal("data/font/impact.png"), false);
		font_master.setColor(Color.RED);
		font_master.getData().setScale(0.13f,0.08f);
		font_master.setUseIntegerPositions(false);	
			
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float p1)
	{
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
	    game.batch.setProjectionMatrix(camera.combined);
			
		game.batch.begin();
		
		spr_background.setPosition(0,0);
		spr_background.setSize(100,100);
		spr_background.draw(game.batch);
		
		
		spr_testdot.setPosition(50,57);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
		
		spr_testdot.setPosition(67,44);
		spr_testdot.setSize(1,1);
		spr_testdot.draw(game.batch);
			
		game.batch.end();
	}
	
	@Override
	public boolean touchDown(int p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
		Vector3 coordsTouch = camera.unproject(new Vector3(p1,p2,0));

		if(state.equals("MainMenu")) {
			//Recuperar Conta
			if(coordsTouch.x >= 70 && coordsTouch.x <= 99 && coordsTouch.y >= 12 && coordsTouch.y <= 27){
				state = "GetAccount";
			}
		}
			
		return false;
	}
	
	@Override
	public void input(String input) {
		accountNumber = input;			
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
