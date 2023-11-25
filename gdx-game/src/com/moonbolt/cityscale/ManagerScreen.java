package com.moonbolt.cityscale;
import java.util.ArrayList;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class ManagerScreen implements Screen{
	private MainGame game;
	private GameControl gameControl;
	private String lservername = "cityserver.mysql.uhserver.com";
	private String lusername = "citymaster";
	private String lpassword = "City@key90";
	private String ldbname = "cityserver";
	
	public ManagerScreen(MainGame game){
		this.game = game;
		this.gameControl = new GameControl();	
	}
	
	public void screenSwitch(String tipo){
		
		if(tipo.equals("TitleScreen")) {
			TitleScreen titleScreen = new TitleScreen(game, this);
			game.setScreen(titleScreen);
		}
		
		if(tipo.equals("MapScreen")){	
			MapScreen mainScreen = new MapScreen(game, this);
			game.setScreen(mainScreen);
		}
		
	}
	
	public void AtualizaComponentes(){
	}

	@Override
	public void show(){}

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
