package edu.learncraft.animalsciencecraft.gui.pages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import joptsimple.internal.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Page {
	public static final int FLAG_HOME = 0;
	public static final int FLAG_TUTORIALS = 1;
	public static final int FLAG_REFERENCES = 2;
	public static final int FLAG_OTHER = 3;
	public static final int FLAG_ERROR = 4;

	public Page(Page parent, List<Page> children, String name,
			Vector<PageContent> body, int depth, int flag) {
		this.parent = parent;
		this.children = children;
		this.name = name;
		this.body = body;
		this.depth = depth;
		this.flag = flag;
		this.path = new Integer[0];
	}

	public Page parent;
	public List<Page> children;
	public String name;
	public Vector<PageContent> body;
	public int depth;
	public int flag;
	private Integer[] path;

	public Page() {
		this(null, new ArrayList<Page>(), "Error", new Vector<PageContent>(),
				0, FLAG_ERROR);
	}

	private static int countLeadingTabs(String s) {
		int count = 0;
		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			if (c != ' ') {
				return count;
			}
			count += 1;
		}
		return count;
	}

	public Page(ResourceLocation location, Page parent, String name, int flag)
			throws IOException {
		this.parent = parent;
		this.children = new ArrayList<Page>();
		this.name = name;
		this.body = new Vector<PageContent>();
		this.depth = (parent == null) ? 0 : parent.depth + 1;
		this.flag = flag;
		this.path = (parent == null) ? new Integer[0] : new Integer[] {parent.children.size()};

		InputStream fileIS = Minecraft.getMinecraft().getResourceManager()
				.getResource(location).getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(fileIS));
		Stack<Page> pageStack = new Stack<Page>();
		pageStack.push(this);
		for (String line; (line = br.readLine()) != null;) {
			if (line.trim().isEmpty()) {
				continue;
			} else {
				Page current = pageStack.peek();
				int newDepth = 1+1 + (countLeadingTabs(line))/4;
				boolean isNewLeaf = !line.trim().startsWith("*");
				String newContent = isNewLeaf ? line.trim() : line.trim()
						.substring(1);
				if (isNewLeaf) {
					System.out.println("Adding a new leaf node. Top of stack:"+current.name);
					current.body.add(PageContent.fromString(newContent));
				} else {
					System.out.println("Non-leaf node. Top of stack:"+current.name);
					System.out.println("\tTop Depth: "+current.depth);
					System.out.println("\tNew Node: "+newContent);
					System.out.println("\tNew Depth: "+newDepth);
					while (current != this && current.depth >= newDepth) {
						System.out.println("\tRemoving:"+current.name);
						pageStack.pop();
						current = pageStack.peek();
					}
					System.out.println("\tNow at:"+current.name);
					Page newPage = new Page(current, newContent, FLAG_OTHER);
					current.addPage(newPage);
					pageStack.push(newPage);
				}
			}
		}
	}

	public void printTree() {
		System.out.println(Strings.repeat('\t', this.depth)
				+ Arrays.toString(this.path) + ">>" + this.name);
		for (Page child : this.children) {
			child.printTree();
		}
	}

	public Page(String name, int depth, int flag) {
		this.parent = null;
		this.children = new ArrayList<Page>();
		this.name = name;
		this.body = new Vector<PageContent>();
		this.depth = depth;
		this.flag = flag;
		this.path = new Integer[0];
	}

	public Page(Page parent, String name, int flag) {
		this.parent = parent;
		this.children = new ArrayList<Page>();
		this.name = name;
		this.body = new Vector<PageContent>();
		this.depth = (parent == null) ? 0 : parent.depth + 1;
		this.flag = flag;
		this.path = new Integer[0];
	}

	public Integer[] getPath(int i) {
		Integer[] longer_path = new Integer[path.length + 1];
		for (int j = 0; j < path.length; j += 1) {
			longer_path[j] = path[j];
		}
		longer_path[path.length] = i;
		return longer_path;
	}

	public void addPage(Page aPage) {
		this.children.add(aPage);
		aPage.setPath(getPath(children.size()-1));
	}

	public void setPath(Integer[] path) {
		this.path = path;
	}

	public Page follow(Integer[] integers) {
		Page current = this;
		for (int i : integers) {
			current = current.children.get(i);
		}
		return current;
	}

	public Integer[] getPath() {
		return this.path;
	}
}
