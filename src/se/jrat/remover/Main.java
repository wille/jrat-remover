package se.jrat.remover;

import javax.swing.UIManager;

import se.jrat.remover.fixers.FixLinux;
import se.jrat.remover.fixers.FixMac;
import se.jrat.remover.fixers.FixWin;
import se.jrat.remover.fixers.Fixer;

import com.redpois0n.oslib.OperatingSystem;

public class Main {
	
	public static final String getVersion() {
		return "1.2";
	}

	public static void main(String[] args) throws Exception {	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		Frame frame = new Frame();
		
		Fixer fixer;

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			fixer = new FixWin(frame);
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			fixer = new FixMac(frame);
		} else if (OperatingSystem.getOperatingSystem().isUnix()) {
			fixer = new FixLinux(frame);
		}
		
		Utils.info("jRAT Remover", "Please make sure to manually terminate all running Java processes before proceeding");
		frame.setVisible(true);
	}
	
	public static void debug(Object obj) {
		if (obj == null) {
			obj = "null";
		}
		System.out.println(obj.toString());
	}

}
