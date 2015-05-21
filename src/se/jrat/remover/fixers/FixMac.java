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

public class FixMac extends Fixer {

	public FixMac(Frame frame) {
		super(frame);
	}

	@Override
	public void perform(boolean dryrun) {
		List<File> files = new ArrayList<File>();
		List<File> launchagents = new ArrayList<File>();
		
		while (frame.getModel().getRowCount() > 0) {
			frame.getModel().removeRow(0);
		}
		
		File dir = new File(System.getProperty("user.home") + "/Library/LaunchAgents/");
		
		for (File file : dir.listFiles()) {
			try {
				boolean add = false;
				String path = null;
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line;
				
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (line.contains("<string>-jar</string>") || line.startsWith("<string>" + System.getProperty("java.home").replace("%20", " "))) {
						add = true;
					}
					
					if (line.startsWith("<string>")) {
						path = line.replace("<string>", "").replace("</string>", "");
					}
				}
				
				reader.close();
				
				if (add && path != null) {
					files.add(new File(path));
					launchagents.add(file);
					frame.getModel().addRow(new Object[] { file.getName(), path });
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
			for (int i = 0; i < files.size() && i < launchagents.size(); i++) {
				File file = files.get(i);
				File launchagent = launchagents.get(i);
				
				Main.debug("Deleting Stub: " + file.getAbsolutePath());
				Main.debug("Deleting Launch Agent: " + launchagent.getAbsolutePath());
				
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
