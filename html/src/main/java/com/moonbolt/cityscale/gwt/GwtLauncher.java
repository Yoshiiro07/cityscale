package com.moonbolt.cityscale.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.moonbolt.cityscale.Main;
import com.moonbolt.cityscale.MainGame;
import com.moonbolt.cityscale.interfaces.DateTimeProvider;
import com.moonbolt.cityscale.services.DesktopDateTimeProvider;
import com.moonbolt.cityscale.services.HtmlDateTimeProvider;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
        @Override
        public GwtApplicationConfiguration getConfig () {
            // Resizable application, uses available space in browser with no padding:
            GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(true);
            cfg.padVertical = 0;
            cfg.padHorizontal = 0;
            return cfg;
            // If you want a fixed size application, comment out the above resizable section,
            // and uncomment below:
            //return new GwtApplicationConfiguration(640, 480);
        }

        @Override
        public ApplicationListener createApplicationListener () {
        	DateTimeProvider dateTimeProvider = new HtmlDateTimeProvider();
            return new MainGame(dateTimeProvider);
        }
}
