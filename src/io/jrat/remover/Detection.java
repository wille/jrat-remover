package io.jrat.remover;

public class Detection {
	
	private String key;
	private String value;
	
	public Detection(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}

}
