package su.jrat.remover;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.UIManager;

import su.jrat.remover.fixers.FixLinux;
import su.jrat.remover.fixers.FixMac;
import su.jrat.remover.fixers.FixWin;
import su.jrat.remover.fixers.Fixer;
import su.jrat.remover.scanners.Scanner;
import su.jrat.remover.scanners.ScannerLinux;
import su.jrat.remover.scanners.ScannerMac;
import su.jrat.remover.scanners.ScannerWin;

public class Main {
	
	public static Fixer fixer;
	public static Scanner remover;
	
	public static final String getVersion() {
		return "1.2";
	}

	public static void main(String[] args) throws Exception {	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			fixer = new FixWin();
			remover = new ScannerWin();
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			fixer = new FixMac();
			remover = new ScannerMac();
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
			fixer = new FixLinux();
			remover = new ScannerLinux();
		} else {
			Util.err("Unavailable OS", "This program can only run on Mac OSX, Linux and Windows");
			System.exit(0);
		}
		
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
