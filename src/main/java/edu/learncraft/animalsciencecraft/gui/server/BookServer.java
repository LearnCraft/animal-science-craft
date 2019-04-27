package edu.learncraft.animalsciencecraft.gui.server;

import java.util.HashMap;

public class BookServer {
	HashMap<String, BookPage> routes;
	
	public BookPage route(BookURL url) {
		BookPage currentPage;
		if (routes.containsKey(url.getEndpoint())) {
			currentPage = routes.get(url.getEndpoint());
		} else if (routes.containsKey("not_found")) {
			currentPage = routes.get("not_found");
		} else {
			// Should never happen.
			currentPage = new BookPage();
		}
		return currentPage.load(url.getArguments());
	}
	
	public void addRoute(String endpoint, BookPage page) {
		routes.put(endpoint, page);
	}
	
	public void addDefaultRoutes() {
		this.addRoute("not_found", new BookPage());
	}
}
