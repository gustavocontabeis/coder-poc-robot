package br.com.codersistemas.coderpocrobot.gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
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
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import br.com.codersistemas.coderpocrobot.CoderRobot;

public class RobotUI extends JFrame {

	private static final long serialVersionUID = -3556068906690980070L;
	private Border border;

	public RobotUI() throws HeadlessException {
		super("Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);	
		this.getContentPane().setLayout(new GridLayout(1, 2));

		JPanel panelButtons = new JPanel(new GridLayout(15, 1));
		panelButtons.setBorder(border);

		panelButtons.add(addFlowLayout(new JLabel("Texto"),new JTextField(30)));
		panelButtons.add(addFlowLayout(new JButton("TAB"), new JButton("ENTER")));
		panelButtons.add(addFlowLayout(new JButton("UP"), new JButton("DOWN")));
		panelButtons.add(addFlowLayout(new JButton("LEFT"), new JButton("RIGHT")));
		panelButtons.add(addFlowLayout(new JButton("BACK"), new JButton("DEL")));
		panelButtons.add(addFlowLayout(new JButton("ESC")));
		panelButtons.add(addFlowLayout(new JLabel("Delay"),new JTextField(10)));
		JButton btListenMouse = new JButton("Clicar");
		panelButtons.add(addFlowLayout(btListenMouse));
		
		JPanel panelCommands = new JPanel(new GridLayout(1,1));
		panelButtons.setBorder(border);
		JTextArea btChangeaaa = new JTextArea("");
		panelCommands.add(new JScrollPane(btChangeaaa));
		
		this.getContentPane().add(panelButtons);
		this.getContentPane().add(panelCommands);
		
		btListenMouse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Robot r = new Robot();
					int i = 0;
					do {
						PointerInfo a = MouseInfo.getPointerInfo();
						Point b = a.getLocation();
						int x = (int) b.getX();
						int y = (int) b.getY();
						System.out.println("clicarEm " + x + " " + y );
						Thread.sleep(1000 * 1);
					} while (i++ < 10);
				} catch (AWTException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		//panelButtons.add(btListenMouse);
		//this.getContentPane().add(panelButtons);

	}

	private Component addFlowLayout(JComponent...cp) {
		JPanel panel = new JPanel();
		panel.setBorder(border);
		panel.setLayout(new FlowLayout());
		for (JComponent jComponent : cp) {
			panel.add(jComponent);
		}
		return panel;
	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				RobotUI x = new RobotUI();
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
