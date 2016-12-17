package io.jrat.remover;

import io.jrat.remover.removers.OSXRemover;
import io.jrat.remover.removers.Remover;
import io.jrat.remover.removers.UnixRemover;
import io.jrat.remover.removers.WindowsRemover;

import javax.swing.UIManager;

import com.redpois0n.oslib.OperatingSystem;

public class Main {
	
	public static Remover remover;
	
	public static final String getVersion() {
		return "1.2";
	}

	public static void main(String[] args) throws Exception {	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		Frame frame = new Frame();
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			remover = new WindowsRemover(frame);
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			remover = new OSXRemover(frame);
		} else if (OperatingSystem.getOperatingSystem().isUnix()) {
			remover = new UnixRemover(frame);
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
