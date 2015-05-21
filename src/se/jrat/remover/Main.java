package se.jrat.remover;

import javax.swing.UIManager;

import se.jrat.remover.removers.UnixRemover;
import se.jrat.remover.removers.OSXRemover;
import se.jrat.remover.removers.WindowsRemover;
import se.jrat.remover.removers.Remover;

import com.redpois0n.oslib.OperatingSystem;

public class Main {
	
	public static final String getVersion() {
		return "1.2";
	}

	public static void main(String[] args) throws Exception {	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		Frame frame = new Frame();
		
		Remover fixer;

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			fixer = new WindowsRemover(frame);
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			fixer = new OSXRemover(frame);
		} else if (OperatingSystem.getOperatingSystem().isUnix()) {
			fixer = new UnixRemover(frame);
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
