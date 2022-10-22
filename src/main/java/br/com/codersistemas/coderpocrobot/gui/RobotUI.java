package br.com.codersistemas.coderpocrobot.gui;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import br.com.codersistemas.coderpocrobot.Keyboard;

public class RobotUI extends JFrame implements XListener {

	private static final long serialVersionUID = -3556068906690980070L;
	private Border border;
	private JTextArea txtCommands;
	private JTextField txtEscrever;
	private Keyboard robot;
	private JTextField txtDelay;
	private PanelBuilder panelBuilder;

	public RobotUI() throws HeadlessException, AWTException {
		super("Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		robot = new Keyboard();
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

		panelBuilder = new PanelBuilder(this, 400, 600);
		JPanel panelButtons = panelBuilder
				.addText("Escrever", 12)
				.newLine().addButton("Tab", 3)
				.addButton("Home", 3)
				.addButton("Up", 3)
				.addButton("End", 3)
				.newLine()
				.addButton("Enter", 3)
				.addButton("Left", 3)
				.addButton("Down", 3)
				.addButton("Right", 3)
				.newLine()
				.addButton("Space", 12)
				.newLine()
				.addButton("Click", 6)
				.addButton("Db CLick", 6)
				.newLine()
				.addButton("Ctrl+C", 6)
				.addButton("Ctrl+V", 6)
				.newLine()
				.addButton("Shift Home", 6)
				.addButton("Shift End", 6)
				.newLine()
				.addText("Delay", 12)
				.newLine().
				addButton("Run!", 12)
				.newLine()
				.build();

		JPanel panelCommands = new JPanel(new GridLayout(1, 1));
		panelButtons.setBorder(border);
		txtCommands = new JTextArea();
		panelCommands.add(new JScrollPane(txtCommands));

		JPanel panelgrid = new JPanel(new GridLayout(1, 2));
		panelgrid.add(panelButtons);
		panelgrid.add(panelCommands);

		this.getContentPane().add(panelgrid);

	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				try {
					RobotUI x = new RobotUI();
				} catch (HeadlessException | AWTException e) {
					e.printStackTrace();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JButton) {
			JButton bt = (JButton) source;
			switch (bt.getText()) {
			case "Click":
				capturarClick();
				break;
			case "Db Click":
				capturarDbClick();
				break;
			case "Run!":
				executarRobot();
				break;
			default:
				appendCommand(bt.getText().toUpperCase());
				break;
			}
		} else if (source instanceof JTextField) {
			JTextField txt = (JTextField) source;

		}

	}

	public void keyTyped(KeyEvent e) {
		Object source = e.getSource();
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (source instanceof JTextField) {
				JTextField txt = (JTextField) source;
				if ("Escrever".equals(txt.getToolTipText())) {
					appendCommand(txt.getText());
				}
				if ("Delay".equals(txt.getToolTipText())) {
					appendCommand("DELAY " + txt.getText());
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	private void executarRobot() {
		String[] split = txtCommands.getText().split("\n");
		for (String string : split) {
			robot.type(string);
		}
	}

	private void capturarClick() {
		try {
			Thread.sleep(1000 * 3);
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			appendCommand("click " + x + " " + y);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private void capturarDbClick() {
		try {
			Thread.sleep(1000 * 3);
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			appendCommand("Dbclick " + x + " " + y);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private void appendCommand(String command) {
		String textCommands = txtCommands.getText();
		txtCommands.setText(textCommands + "\n" + command);
	}

}
