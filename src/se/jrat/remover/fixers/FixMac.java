package se.jrat.remover.fixers;

import java.io.File;

import se.jrat.remover.Main;
import se.jrat.remover.scanners.ScannerMac;

public class FixMac implements Fixer {

	@Override
	public void fix() {
		for (int i = 0; i < ScannerMac.files.size() && i < ScannerMac.launchagents.size(); i++) {
			File file = ScannerMac.files.get(i);
			File launchagent = ScannerMac.launchagents.get(i);
			
			Main.debug("Deleting Stub: " + file.getAbsolutePath());
			Main.debug("Deleting Launch Agent: " + launchagent.getAbsolutePath());
			
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
