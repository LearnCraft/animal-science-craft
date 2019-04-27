package edu.learncraft.animalsciencecraft.gui.server;

import java.util.HashMap;

public class BookURL {
	
	private String endpoint;
	private HashMap<String, Object> arguments;
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public HashMap<String, Object> getArguments() {
		return arguments;
	}
	public void setArguments(HashMap<String, Object> arguments) {
		this.arguments = arguments;
	}

}
