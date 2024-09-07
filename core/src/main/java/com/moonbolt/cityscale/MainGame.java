package com.moonbolt.cityscale;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
	
	private boolean network = false;
	private int playernumber = 0;
	private String account = "";
	
	public MainGame(){};
	
	SpriteBatch batch;	
	public ManagerScreen loadingmanager;
	
	@Override
	public void create()
	{
		batch = new SpriteBatch();  
		loadingmanager = new ManagerScreen(this);
		this.Switch("SplashScreen",network, account, playernumber);
	}
	
	public void Switch(String screenName, boolean network,String accountAlt, int playernumAlt) {
		loadingmanager.screenSwitch(screenName, network,accountAlt, playernumAlt);
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
