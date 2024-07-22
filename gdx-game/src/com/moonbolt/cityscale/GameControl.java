package com.moonbolt.cityscale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.files.FileHandle;

public class GameControl {
	
	//Manager
	private Json json;
	private FileHandle file;
	private Random randnumber;
	private GameObject player;
	
	public GameControl() {
		
		json = new Json();
		randnumber = new Random();
	}
	
	/////////////////////// [ SUMMARY ] ///////////////////
	//[Account]//
	//[Interface]//
	//[Character]//
	//[Monsters]//
	
	
			
}