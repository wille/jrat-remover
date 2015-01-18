package se.jrat.remover.scanners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.jrat.remover.Frame;
import se.jrat.remover.Util;

public class ScannerMac implements Scanner {

	public static final List<File> files = new ArrayList<File>();
	public static final List<File> launchagents = new ArrayList<File>();
	
	@Override
	public void scan() {
		files.clear();
		launchagents.clear();
		while (Frame.instance.getModel().getRowCount() > 0) {
			Frame.instance.getModel().removeRow(0);
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
					Frame.instance.getModel().addRow(new Object[] { file.getName(), path });
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		Frame.instance.getFixButton().setEnabled(Frame.instance.getModel().getRowCount() > 0);
		
		if (Frame.instance.getModel().getRowCount() == 0) {
			Util.info("jRAT Remover", "No results found when scanning!");
		} else {
			Util.err("jRAT Remover", "Found " + Frame.instance.getModel().getRowCount() + " servers while scanning!");
		}
	}

}
