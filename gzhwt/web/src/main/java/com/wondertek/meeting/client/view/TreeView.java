package com.wondertek.meeting.client.view;

import java.util.List;

public class TreeView {

	private String text;
	private String id;

	private String cls;
	
	private String expanded;

	private String leaf;
	
	private List<TreeView> children;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public List<TreeView> getChildren() {
		return children;
	}

	public void setChildren(List<TreeView> children) {
		this.children = children;
	}
}
