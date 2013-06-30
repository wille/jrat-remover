package pro.jrat.remover;

import javax.swing.JOptionPane;

public class Util {
	
	public static boolean yesNo(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}
	
	public static void err(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void info(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
