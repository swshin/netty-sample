package com.nettysample.socket.model;

public class Item {

	private String name;
	private int length;
	private String value;
	
	public static Item create(String name, int length, String value) {
		Item item = new Item();
		item.setName(name);
		item.setLength(length);
		item.setValue(value);
		return item;
	}
	
	public String raw() {
		StringBuilder padded = new StringBuilder(this.value);
		while (padded.toString().getBytes().length < this.length) {
			padded.append(' ');
		}
		return padded.toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
