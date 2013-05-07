package com.redpois0n;

import javax.swing.JOptionPane;

public class Util {
	
	public static final int OS_WIN = 0;
	public static final int OS_MAC = 1;
	
	public static boolean yesNo(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}
	
	public static void err(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void info(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int getOS() {
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.contains("win")) {
			return OS_WIN;
		} else if (os.contains("mac")) {
			return OS_MAC;
		} else {
			return -1;
		}
	}
}
