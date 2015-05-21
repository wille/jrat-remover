package se.jrat.remover.fixers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.jrat.remover.Frame;
import se.jrat.remover.Main;
import se.jrat.remover.Utils;

public class FixLinux extends Fixer {

	public FixLinux(Frame frame) {
		super(frame);
	}

	@Override
	public void perform(boolean dryrun) {
		List<File> files = new ArrayList<File>();
		List<File> desktopentries = new ArrayList<File>();
		
		File dir = new File(System.getProperty("user.home") + "/.config/autostart/");
		
		if (!dir.exists()) {
			Utils.err("jRAT Remover", "No autostart directory found");
			return;
		}
		
		for (File file : dir.listFiles()) {
			try {
				boolean add = false;
				String name = null;
				String path = null;
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line;
				
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					
					if (line.startsWith("Name=")) {
						name = line.split("=")[1];
					} else if (line.startsWith("Exec=")) {
						String command = line.split("=")[1];
						path = command.substring(12);
						
						if (command.contains("java") && command.contains("-jar")) {
							add = true;
						}
					}
				}
				
				reader.close();
				
				if (add && path != null && name != null) {
					files.add(new File(path));
					desktopentries.add(file);
					Frame.instance.getModel().addRow(new Object[] { name, path });
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		Frame.instance.getFixButton().setEnabled(Frame.instance.getModel().getRowCount() > 0);
		
		if (Frame.instance.getModel().getRowCount() == 0) {
			Utils.info("jRAT Remover", "No results found when scanning!");
		} else {
			Utils.err("jRAT Remover", "Found " + Frame.instance.getModel().getRowCount() + " stubs while scanning!");
		}
		
		if (!dryrun) {
			for (int i = 0; i < files.size() && i < desktopentries.size(); i++) {
				File file = files.get(i);
				File launchagent = desktopentries.get(i);
				
				Main.debug("Deleting Stub: " + file.getAbsolutePath());
				Main.debug("Deleting Desktop File: " + launchagent.getAbsolutePath());
				
				if (file.exists()) {
					file.delete();
				}
				
				if (launchagent.exists()) {
					launchagent.delete();
				}
			}
		}
	}

}
