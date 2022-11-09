package br.com.codersistemas.coderpocrobot.gui;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class PanelBuilder {

	private static final int HEIGTH_DEFAULT = 25;
	private int padding = 5;
	private int width;
	private int height;
	private List<Line>lines=new ArrayList<>();
	private Line line;
	private Map<String, JButton>buttons = new HashMap<>();
	private Map<String, JComponent>texts = new HashMap<>();
	private RobotListener listeners;

	public PanelBuilder(RobotListener actionListener, int width, int height) {
		this.listeners = actionListener;
		this.width = width;
		this.height = height;
		this.line = new Line();
		lines.add(line);
	}

	public PanelBuilder addText(String label, int columns) {
		JTextField component = new JTextField();
		component.addKeyListener(listeners);
		component.setToolTipText(label);
		component.setBounds(0, 0, 0, HEIGTH_DEFAULT);
		DataField dataField = new DataField(this, component, label, columns);
		line.add(dataField);
		texts.put(label, component);
		return this;
	}

	public PanelBuilder addDate(String label, int columns) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		JFormattedTextField component = new JFormattedTextField(df);
		DataField dataField = new DataField(this, component, label, columns);
		line.add(dataField);
		return this;
	}

	public PanelBuilder newLine() {
		this.line = new Line();
		lines.add(line);
		return this;
	}

	public PanelBuilder addText(String label, int columns, String mask) {
		JTextField component = new JTextField();
		DataField dataField = new DataField(this, component, label, columns);
		line.add(dataField);
		texts.put(label, component);
		return this;
	}

	public PanelBuilder addInteger(String label, int columns) {
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setGroupingUsed(true);
		numberInstance.setMinimumFractionDigits(0);
		numberInstance.setMaximumFractionDigits(0);
		JFormattedTextField component = new JFormattedTextField(numberInstance);
		DataField dataField = new DataField(this, component, label, columns);
		line.add(dataField);
		texts.put(label, component);
		return this;
	}

	public PanelBuilder addFloat(String label, int columns) {
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setGroupingUsed(true);
		numberInstance.setMinimumFractionDigits(2);
		numberInstance.setMaximumFractionDigits(2);
		JFormattedTextField component = new JFormattedTextField(numberInstance);
		DataField dataField = new DataField(this, component, label, columns);
		line.add(dataField);
		texts.put(label, component);
		return this;
	}

	public PanelBuilder addRadio(String label, int columns, String...itens) {
		return this;
	}

	public PanelBuilder addCheck(String label, int columns, String...itens) {
		JCheckBox component = new JCheckBox();
		component.setBounds(0, 0, 0, HEIGTH_DEFAULT);
		DataField dataField = new DataField(this, component, label, columns);
		line.add(dataField);
		return this;
	}

	public PanelBuilder addTextArea(String label, int columns, int lines) {
		JTextArea textArea = new JTextArea(lines, 200);
		textArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        textArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
		JScrollPane sp = new JScrollPane(textArea);
		sp.setBounds(0, 0, 0, lines*20);
		DataField dataField = new DataField(this, sp, label, columns);
		line.add(dataField);
		texts.put(label, textArea);
		return this;
	}

	public PanelBuilder addButton(String label, int columns) {
		JButton component = new JButton(label);
		component.addActionListener(listeners);
		component.setToolTipText(label);
		DataField dataField = new DataField(this, component, label, columns);
		line.add(dataField);
		buttons.put(label, component);
		return this;
	}

	public PanelBuilder addList(JList<String> component, int columns) {
		DataField dataField = new DataField(this, component, "", columns);
		line.add(dataField);
		
		return this;
	}
	
	public JPanel build() {
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(width, height);
		
		int y = 5;
		for (Line line : lines) {
			System.out.println("-----------------------------");
			List<DataField> components = line.getComponents();
			JLabel jlabel = null;
			DataField previous = null;
			int x = 0;
			int maxComponentHeight = 0;
			boolean withLabel = line.isWithLabel();
			for (DataField dataField : components) {
				if(previous == null) {
					x = 5;
					maxComponentHeight = 0;
				}
				if(withLabel) {
					JComponent component = dataField.getComponent();
					boolean hasLabel = !(component instanceof JButton); 
					jlabel = new JLabel(hasLabel?dataField.getLabel():"");
					int widthCalculated = dataField.getWidthCalculated();
					int componentHeight = component.getHeight() != 0 ? component.getHeight() : HEIGTH_DEFAULT;
					jlabel.setBounds(x, y, widthCalculated, HEIGTH_DEFAULT);
					component.setBounds(x, jlabel.getY()+jlabel.getHeight() , widthCalculated, componentHeight);
					System.out.printf("%-30s - %6d - %6d - %6d - %6d \n", dataField.getLabel(), component.getX(), component.getY(), widthCalculated, componentHeight);
					x += (padding + component.getWidth());
					previous = dataField;
					if(maxComponentHeight < componentHeight) {
						maxComponentHeight = componentHeight;
					}
					panel.add(jlabel);
					panel.add(component);
				} else {
					JComponent component = dataField.getComponent();
					int widthCalculated = dataField.getWidthCalculated();
					int componentHeight = component.getHeight() != 0 ? component.getHeight() : HEIGTH_DEFAULT;
					component.setBounds(x, y, widthCalculated, componentHeight);
					System.out.printf("%-30s - %6d - %6d - %6d - %6d \n", dataField.getLabel(), component.getX(), component.getY(), widthCalculated, componentHeight);
					x += (padding + component.getWidth());
					previous = dataField;
					if(maxComponentHeight < componentHeight) {
						maxComponentHeight = componentHeight;
					}
					panel.add(component);
				}
			}
			y += (jlabel != null ? jlabel.getHeight() : 0) + padding + maxComponentHeight;
		}
		
		return panel;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}
	
	public JButton getButton(String label) {
		return buttons.get(label);
	}

	public JTextComponent getText(String label) {
		return (JTextComponent) texts.get(label);
	}

}