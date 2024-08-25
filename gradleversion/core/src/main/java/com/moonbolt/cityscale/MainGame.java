package com.moonbolt.cityscale;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class MainGame extends Game {
	
	private boolean network = false;
	private int playernum = 0;
	
	public MainGame(){};
	
	SpriteBatch batch;	
	public ManagerScreen loadingmanager;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();  
		loadingmanager = new ManagerScreen(this);
		this.Switch("SplashScreen",network, playernum);
	}
	
	public void Switch(String screenName, boolean network,int playernumAlt) {
		loadingmanager.screenSwitch(screenName, network, playernumAlt);
	}
	
	public void AtualizaElementos(MainGame mainGameAlt,GameControl gameControlAtl, boolean networkAlt) {
		loadingmanager.atualizaComponentes(mainGameAlt,gameControlAtl, networkAlt, 0);
	}

	@Override
	public void render() { super.render(); }

	@Override
	public void dispose() { super.dispose(); }

	@Override
	public void resize(int width, int height) { super.resize(width, height); }

	@Override
	public void pause() { super.pause(); }

	@Override
	public void resume() { super.resume(); }
}
