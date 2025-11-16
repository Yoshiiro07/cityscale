package com.moonbolt.cityscale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
	
	SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		// TODO: Initialize game screens
	}
	
	@Override
	public void render() { 
		super.render(); 
	}

	@Override
	public void dispose() { 
		super.dispose(); 
	}

	@Override
	public void resize(int width, int height) { 
		super.resize(width, height); 
	}

	@Override
	public void pause() { 
		super.pause(); 
	}

	@Override
	public void resume() { 
		super.resume(); 
	}
}
