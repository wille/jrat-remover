package se.jrat.remover.removers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.jrat.remover.Frame;
import se.jrat.remover.Main;
import se.jrat.remover.Utils;

/**
 * Linux and BSD remover
 */
public class UnixRemover extends Remover {

	public UnixRemover(Frame frame) {
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
						String command = line.substring(line.indexOf("=") + 1, line.length());
						
						if (command.contains("java") && command.contains("-jar")) {
							add = true;
							path = command.replace("java", "").replace("-jar", "").replace("\"", "").replace("'", "").trim();
						}
					}
				}
				
				reader.close();
				
				if (add && path != null && name != null) {
					files.add(new File(path));
					desktopentries.add(file);
					frame.getModel().addRow(new Object[] { name, path });
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		frame.getFixButton().setEnabled(frame.getModel().getRowCount() > 0);
		
		if (frame.getModel().getRowCount() == 0) {
			Utils.info("jRAT Remover", "No results found when scanning!");
		} else {
			Utils.err("jRAT Remover", "Found " + frame.getModel().getRowCount() + " stubs while scanning!");
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
