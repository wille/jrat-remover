package se.jrat.remover.scanners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.jrat.remover.Frame;
import se.jrat.remover.Util;

public class ScannerLinux implements Scanner {

	public static final List<File> files = new ArrayList<File>();
	public static final List<File> desktopentries = new ArrayList<File>();
	
	@Override
	public void scan() {
		files.clear();
		desktopentries.clear();
		
		while (Frame.instance.getModel().getRowCount() > 0) {
			Frame.instance.getModel().removeRow(0);
		}
		
		File dir = new File(System.getProperty("user.home") + "/.config/autostart/");
		
		if (!dir.exists()) {
			Util.err("jRAT Remover", "No autostart directory found");
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
			Util.info("jRAT Remover", "No results found when scanning!");
		} else {
			Util.err("jRAT Remover", "Found " + Frame.instance.getModel().getRowCount() + " servers while scanning!");
		}
	}
}