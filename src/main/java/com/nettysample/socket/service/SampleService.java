package com.nettysample.socket.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.nettysample.socket.model.Item;
import com.nettysample.socket.model.Packet;

public class SampleService {

	public String checkWithdrawal(String msg) {
		
		ArrayList<Item> items = new ArrayList<>();
		items.add(Item.create("channelId", 4, "ch01"));
		items.add(Item.create("returnCode", 4, "01"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		items.add(Item.create("sendDate", 20, sdf.format(new Date())));
		items.add(Item.create("msg", 30, msg));
		
		Packet packet = new Packet();
		packet.setItems(items);
		
		return packet.raw();
	}
	
}
