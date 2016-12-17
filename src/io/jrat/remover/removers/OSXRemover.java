package io.jrat.remover.removers;

import io.jrat.remover.Detection;
import io.jrat.remover.Frame;
import io.jrat.remover.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * OSX Remover
 */
public class OSXRemover extends Remover {

	public OSXRemover(Frame frame) {
		super(frame);
	}

	@Override
	public List<Detection> perform(boolean dryrun) {
		List<Detection> detections = new ArrayList<Detection>();
		List<File> files = new ArrayList<File>();
		List<File> launchagents = new ArrayList<File>();
				
		File dir = new File(System.getProperty("user.home") + "/Library/LaunchAgents/");
		
		for (File file : dir.listFiles()) {
			try {
				boolean add = false;
				String path = null;
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line;
				
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (line.contains("<string>-jar</string>") || line.startsWith("<string>" + System.getProperty("java.home"))) {
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
					detections.add(new Detection(file.getName(), path));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
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
		
		return detections;
	}

}
