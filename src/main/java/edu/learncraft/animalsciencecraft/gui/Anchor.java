package edu.learncraft.animalsciencecraft.gui;

public class Anchor {
	private int page;
	private int subpage;
	public int getSubpage() {
		return subpage;
	}
	public void setSubpage(int subpage) {
		this.subpage = subpage;
	}
	private String name;
	public Anchor(int page_type, String name) {
		super();
		this.page = page_type;
		this.name = name;
	}
	public int getPage_type() {
		return page;
	}
	public void setPage_type(int page_type) {
		this.page = page_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
