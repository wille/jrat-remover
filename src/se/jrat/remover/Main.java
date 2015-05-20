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

import com.redpois0n.oslib.OperatingSystem;

public class Main {
	
	public static Fixer fixer;
	public static Scanner remover;
	
	public static final String getVersion() {
		return "1.2";
	}

	public static void main(String[] args) throws Exception {	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			fixer = new FixWin();
			remover = new ScannerWin();
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			fixer = new FixMac();
			remover = new ScannerMac();
		} else if (OperatingSystem.getOperatingSystem().isUnix()) {
			fixer = new FixLinux();
			remover = new ScannerLinux();
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
