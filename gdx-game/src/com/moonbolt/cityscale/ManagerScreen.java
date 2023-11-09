package com.moonbolt.citymanager;
import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	
	public ManagerScreen(MainGame game){
		this.game = game;
		this.gameControl = new GameControl();	
	}
	
	public void screenSwitch(String tipo){
		
		if(tipo.equals("MainScreen")){	
			MainScreen mainScreen = new MainScreen(game, this);
			game.setScreen(mainScreen);
		}
	}
	
	public void atualizaComponentes(){
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
