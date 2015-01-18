package se.jrat.remover.fixers;

import java.io.File;

import se.jrat.remover.Main;
import se.jrat.remover.scanners.ScannerLinux;

public class FixLinux implements Fixer {

	@Override
	public void fix() {
		for (int i = 0; i < ScannerLinux.files.size() && i < ScannerLinux.desktopentries.size(); i++) {
			File file = ScannerLinux.files.get(i);
			File launchagent = ScannerLinux.desktopentries.get(i);
			
			Main.debug("Deleting Stub: " + file.getAbsolutePath());
			Main.debug("Deleting Desktop File: " + launchagent.getAbsolutePath());
			
			if (file.exists()) {
				file.delete();
			}
			
			if (launchagent.exists()) {
				launchagent.delete();
			}
		}
		
		Main.remover.scan();
	}

}
