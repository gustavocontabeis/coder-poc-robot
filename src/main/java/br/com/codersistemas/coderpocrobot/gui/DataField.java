package br.com.codersistemas.coderpocrobot.gui;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;

public class DataField { 
	
	private PanelBuilder panelBuilder;
	private JComponent component;
	private int columns;
	private String label;
	
	public DataField(PanelBuilder panelBuilder, JComponent component, String label, int columns) {
		super();
		this.panelBuilder = panelBuilder;
		this.component = component;
		this.columns = columns;
		this.label = label;
	}

	public int getWidthCalculated() {
		int withPanel = panelBuilder.getWidth() - (panelBuilder.getPadding()*2);
		int col = withPanel/12;
		int withComp = col * columns;
		int withCompWithPadding = withComp - panelBuilder.getPadding();
		return withCompWithPadding;
	}
	
	public PanelBuilder getPanelBuilder() {
		return panelBuilder;
	}

	public void setPanelBuilder(PanelBuilder panelBuilder) {
		this.panelBuilder = panelBuilder;
	}

	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isWithLabel() {
		return !(component instanceof JButton || component instanceof JList);
	}

}
