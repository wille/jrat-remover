package se.jrat.remover;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Updater {
	
	public static boolean isAvailable() throws Exception {
		return !Main.getVersion().equals(new BufferedReader(new InputStreamReader(new URL("http://redpois0n.com/misc/version/getversion.php?proj=remover").openStream())).readLine());
	}

}
