package com.moonbolt.cityscale;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		//Texture Packer
		/*String inputDir = "";
		String outputDir = "";
		String packFileName = "";
		
		TexturePacker.process(inputDir, outputDir, packFileName); */
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        
        initialize(new MainGame(), cfg);
    }
}
