package se.jrat.remover;

public enum OperatingSystem {

	FREEBSD, OPENBSD, OSX, SOLARIS, LINUX, WINDOWS, UNKNOWN;

	public static OperatingSystem getOperatingSystem(String str) {		
		str = str.toLowerCase();
		
		OperatingSystem os;
		
		if (str.equalsIgnoreCase("freebsd")) {
			os = OperatingSystem.FREEBSD;
		} else if (str.equalsIgnoreCase("openbsd")) {
			os = OperatingSystem.OPENBSD;
		} else if (str.contains("mac")) {
			os = OperatingSystem.OSX;
		} else if (str.equalsIgnoreCase("solaris") || str.equalsIgnoreCase("sunos")) {
			os = OperatingSystem.SOLARIS;
		} else if (str.equalsIgnoreCase("linux")) {
			os = OperatingSystem.LINUX;
		} else if (str.contains("win")) {
			os = OperatingSystem.WINDOWS;
		} else {
			os = OperatingSystem.UNKNOWN;
		}
		
		return os;
	}
	
	public static OperatingSystem getOperatingSystem() {
		return OperatingSystem.getOperatingSystem(System.getProperty("os.name"));
	}

}
