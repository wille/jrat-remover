package com.redpois0n;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.UIManager;

public class Main {
	
	public static Fixer fixer;
	public static Scanner remover;
	
	public static final String getVersion() {
		return "1.1";
	}

	public static void main(String[] args) throws Exception {		
		if (Util.getOS() == Util.OS_WIN) {
			fixer = new FixWin();
			remover = new ScannerWin();
		} else if (Util.getOS() == Util.OS_MAC) {
			fixer = new FixMac();
			remover = new ScannerMac();
		} else {
			System.exit(0);
		}
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Util.info("jRAT Remover", "Please make sure all java processes are shut down except this one");
		Frame frame = new Frame();
		frame.setVisible(true);
		
		if (Updater.isAvailable()) {
			Util.info("jRAT Remover", "A new version is available. Proceeding to redpois0n.com to download");
			Desktop.getDesktop().browse(new URI("http://redpois0n.com"));
		}
	}
	
	public static void debug(Object obj) {
		if (obj == null) {
			obj = "null";
		}
		System.out.println(obj.toString());
	}

}
