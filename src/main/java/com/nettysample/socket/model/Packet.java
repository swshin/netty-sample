package com.nettysample.socket.model;

import java.util.ArrayList;

public class Packet {
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public String raw() {
		StringBuilder result = new StringBuilder();
		for (Item item : items) {
			result.append(item.raw());
		}
		return result.toString();
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
}
