package io.jrat.remover;

import iconlib.IconUtils;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Utils {
	
	public static boolean yesNo(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}
	
	public static void err(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void info(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static List<Image> getIcons() {
		List<Image> list = new ArrayList<Image>();
		list.add(IconUtils.getIcon("icon-128x128").getImage());
		list.add(IconUtils.getIcon("icon-64x64").getImage());
		list.add(IconUtils.getIcon("icon-16x16").getImage());
		return list;
	}
}
