package se.jrat.remover;

import javax.swing.UIManager;

import se.jrat.remover.fixers.FixLinux;
import se.jrat.remover.fixers.FixMac;
import se.jrat.remover.fixers.FixWin;
import se.jrat.remover.fixers.Fixer;
import se.jrat.remover.scanners.Scanner;
import se.jrat.remover.scanners.ScannerLinux;
import se.jrat.remover.scanners.ScannerMac;
import se.jrat.remover.scanners.ScannerWin;

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
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX || OperatingSystem.getOperatingSystem() == OperatingSystem.FREEBSD || OperatingSystem.getOperatingSystem() == OperatingSystem.OPENBSD || OperatingSystem.getOperatingSystem() == OperatingSystem.SOLARIS) {
			fixer = new FixLinux();
			remover = new ScannerLinux();
		} else {
			Utils.err("Unavailable OS", "This program can only run on Windows, Mac OS X, Linux, Solaris, FreeBSD and OpenBSD (only by XDG-compliant desktop environments)");
			System.exit(0);
		}
		
		Utils.info("jRAT Remover", "Please make sure to manually terminate all running Java processes before proceeding");
		Frame frame = new Frame();
		frame.setVisible(true);
	}
	
	public static void debug(Object obj) {
		if (obj == null) {
			obj = "null";
		}
		System.out.println(obj.toString());
	}

}
