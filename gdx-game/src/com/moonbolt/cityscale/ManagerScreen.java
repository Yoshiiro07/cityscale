package com.moonbolt.cityscale;
import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	private boolean network = false;
	
	public ManagerScreen(MainGame game){
		this.game = game;
	}
	
	public void screenSwitch(String tipo){
		
		if(tipo.equals("MainScreen"))
		{	
			TitleScreen titleScreen = new TitleScreen(game, this);
			game.setScreen(titleScreen);
		}
		
		if(tipo.equals("SplashScreen"))
		{	
			SplashScreen splashScreen = new SplashScreen(game, this);
			game.setScreen(splashScreen);
		}
		
		if(tipo.equals("CharacterSelect"))
		{	
			CharacterSelect characterSelect = new CharacterSelect(game, this);
			game.setScreen(characterSelect);
		}
		
		if(tipo.equals("MetroStation"))
		{	
			MetroStation metroScreen = new MetroStation(game, this, gameControl, network);
			game.setScreen(metroScreen);
		}
		
		if(tipo.equals("GameMap"))
		{	
			MetroStation metroScreen = new MetroStation(game, this, gameControl, network);
			game.setScreen(metroScreen);
		}
	}
	
	public void UpdateComponents(GameControl gameobj, boolean network)
	{
		this.network = network;
		this.gameControl = gameobj;
	}

	@Override
	public void show()
	{
		// TODO: Implement this method
	}

	@Override
	public void render(float p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void resize(int p1, int p2)
	{
		// TODO: Implement this method
	}

	@Override
	public void pause()
	{
		// TODO: Implement this method
	}

	@Override
	public void resume()
	{
		// TODO: Implement this method
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void dispose()
	{
		// TODO: Implement this method
	}
}
