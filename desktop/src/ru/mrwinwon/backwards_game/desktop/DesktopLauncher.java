package ru.mrwinwon.backwards_game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.mrwinwon.backwards_game.BackwardsBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","seconduser");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BackwardsBird.WIDTH;
		config.height = BackwardsBird.HEIGHT;
		config.title = BackwardsBird.TITLE;
		new LwjglApplication(new BackwardsBird(), config);
	}
}
