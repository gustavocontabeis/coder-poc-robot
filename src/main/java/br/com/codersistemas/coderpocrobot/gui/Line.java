package br.com.codersistemas.coderpocrobot.gui;

import java.util.ArrayList;
import java.util.List;

public class Line {

	private List<DataField> components = new ArrayList<>();

	public void add(DataField component) {
		this.components.add(component);
	}
	
	public List<DataField> getComponents() {
		return components;
	}

}
