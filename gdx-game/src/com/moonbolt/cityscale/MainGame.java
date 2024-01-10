package com.moonbolt.cityscale;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class MainGame extends Game {
	
	private boolean network = false;
	private GameControl gameControl;
	
public MainGame(){};
	
	SpriteBatch batch;	
	public ManagerScreen loadingmanager;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();  
		loadingmanager = new ManagerScreen(this);
		this.Switch("SplashScreen");
	}
	
	public void Switch(String screenName) {
		loadingmanager.UpdateComponents(gameControl, network);
		loadingmanager.screenSwitch(screenName);
	}
	
	public void UpdateComponents(GameControl gameObject, boolean netwrk) {
		this.gameControl = gameObject;
		this.network = netwrk;
	}

	@Override
	public void render()
	{   
	    super.render();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}
}
