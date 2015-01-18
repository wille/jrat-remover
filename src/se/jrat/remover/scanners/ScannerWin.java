package se.jrat.remover.scanners;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.jrat.remover.Frame;
import se.jrat.remover.Utils;

public class ScannerWin implements Scanner {
	
	public static final String JAVA_HOME = System.getProperty("java.home");
	public static List<File> files = new ArrayList<File>();
	public static List<String> regkeys = new ArrayList<String>();

	public void scan() {
		while (Frame.instance.getModel().getRowCount() > 0) {
			Frame.instance.getModel().removeRow(0);
		}
		files.clear();
		regkeys.clear();
		try {
			Map<String, String> reg = getRegistryEntries();
			
			for (Object obj : reg.keySet().toArray()) {
				String str = reg.get(obj);
				if (str.toLowerCase().contains("java") && str.toLowerCase().contains("bin") && str.toLowerCase().contains("-jar")) {
					File file = new File(str.substring(str.toLowerCase().lastIndexOf("-jar \"") + 6, str.length() - 1));
					files.add(file);
					Frame.instance.getModel().addRow(new Object[] { obj, str });
					regkeys.add(obj.toString());
				}
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Frame.instance.getFixButton().setEnabled(Frame.instance.getModel().getRowCount() > 0);
		if (Frame.instance.getModel().getRowCount() == 0) {
			Utils.info("jRAT Remover", "No results found when scanning!");
		} else {
			Utils.err("jRAT Remover", "Found " + regkeys.size() + " stubs while scanning!");
		}
	}
	
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
