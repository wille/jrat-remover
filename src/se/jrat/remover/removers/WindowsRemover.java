package se.jrat.remover.removers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.jrat.remover.Detection;
import se.jrat.remover.Frame;
import se.jrat.remover.Main;
import se.jrat.remover.Utils;

/**
 * Windows Remover
 */
public class WindowsRemover extends Remover {

	private static final String REGISTRY_LOCATION = "HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\\";

	public WindowsRemover(Frame frame) {
		super(frame);
	}

	public List<Detection> perform(boolean dryrun) {
		List<Detection> detections = new ArrayList<Detection>();
		List<File> files = new ArrayList<File>();
		List<String> regkeys = new ArrayList<String>();
		
		try {
			Map<String, String> reg = getRegistryEntries();
			
			for (Object key : reg.keySet().toArray()) {
				String value = reg.get(key);
				if (value.toLowerCase().contains("java") && value.toLowerCase().contains("bin") && value.toLowerCase().contains("-jar")) {
					File file = new File(value.substring(value.toLowerCase().lastIndexOf("-jar \"") + 6, value.length() - 1));
					files.add(file);
					detections.add(new Detection(key.toString(), value));
					regkeys.add(key.toString());
				}
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (!dryrun) {
			String msg = "Are you sure you want to delete these jRAT servers and the registry keys?\n\n";
			for (File file : files) {
				msg += file.getAbsolutePath() + "\n";
			}
				
			if (!Utils.yesNo(msg)) {
				return;
			}
			
			try {
				for (int i = 0; i < files.size() && i < regkeys.size(); i++) {
					File file = files.get(i);
					file.delete();
					Main.debug("Deleted: " + file.getAbsolutePath());
					
					String value = regkeys.get(i);
					
					try {
						delete(value);
						Main.debug("Executed reg delete");
					} catch (Exception ex) {
						ex.printStackTrace();
						Main.debug("Failed executing reg delete: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return detections;
	}
	
	/**
	 * Removes registry value
	 * @param value
	 * @throws Exception
	 */
	private static void delete(String value) throws Exception {
		String[] query = new String[] { "reg", "delete", REGISTRY_LOCATION, "/v", value, "/f" };
	
		Runtime.getRuntime().exec(query);
	}
	
	/**
	 * Returns registry keys and values in map from HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Run
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> getRegistryEntries() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		
		Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hkcu\\software\\microsoft\\windows\\currentversion\\run\\", "/s" });
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			String[] args = line.split("    ");
			
			if (args.length == 3) {			
				map.put(args[0], args[2]);
			}
		}
		reader.close();
		
		return map;
	}
	

}
