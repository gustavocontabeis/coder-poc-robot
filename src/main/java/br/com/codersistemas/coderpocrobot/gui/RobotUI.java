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

public class RobotUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = -3556068906690980070L;
	private Border border;
	private JTextArea txtCommands;
	private JTextField txtEscrever;
	private Keyboard robot;
	private JTextField txtDelay;

	public RobotUI() throws HeadlessException, AWTException {
		super("Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		robot = new Keyboard();
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);	
		this.getContentPane().setLayout(new GridLayout(1, 2));

		JPanel panelButtons = new JPanel(new GridLayout(15, 1));
		panelButtons.setBorder(border);

		txtEscrever = new JTextField(30);
		panelButtons.add(addFlowLayout(new JLabel("Texto"), txtEscrever));
		panelButtons.add(addFlowLayout(new JButton("TAB"), new JButton("ENTER")));
		panelButtons.add(addFlowLayout(new JButton("UP"), new JButton("DOWN")));
		panelButtons.add(addFlowLayout(new JButton("LEFT"), new JButton("RIGHT")));
		panelButtons.add(addFlowLayout(new JButton("BACK"), new JButton("DEL")));
		panelButtons.add(addFlowLayout(new JButton("ESC")));
		txtDelay = new JTextField(10);
		panelButtons.add(addFlowLayout(new JLabel("Delay"),txtDelay));
		JButton btListenMouse = new JButton("Clicar");
		panelButtons.add(addFlowLayout(btListenMouse));
		panelButtons.add(addFlowLayout(new JButton("ROBOT!")));
		
		JPanel panelCommands = new JPanel(new GridLayout(1,1));
		panelButtons.setBorder(border);
		txtCommands = new JTextArea();
		panelCommands.add(new JScrollPane(txtCommands));
		
		this.getContentPane().add(panelButtons);
		this.getContentPane().add(panelCommands);
		
		txtEscrever.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					String command = txtEscrever.getText();
					addCommand(command);
				}
			}
		});
		txtDelay.addKeyListener(new KeyAdapter() {//xxx
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					String command = txtDelay.getText();
					addCommand("DELAY "+command);
				}
			}
		});
		
		btListenMouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread.sleep(1000 * 3);
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					int x = (int) b.getX();
					int y = (int) b.getY();
					addCommand("click " + x + " " + y );
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	private Component addFlowLayout(JComponent...cp) {
		JPanel panel = new JPanel();
		panel.setBorder(border);
		panel.setLayout(new FlowLayout());
		for (JComponent jComponent : cp) {
			panel.add(jComponent);
			if(jComponent instanceof JButton) {
				JButton bt = (JButton) jComponent;
				bt.addActionListener(this);
			}
		}
		return panel;
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
		if(e.getSource() instanceof JButton) {
			JButton bt = (JButton) e.getSource();
			String command = bt.getText();
			if("Clicar".equals(command)) {
				return;
			}
			if("ROBOT!".equals(command)) {
				executarRobot();
			}else{
				addCommand(command);
			}
		}
	}
	
	private void executarRobot() {
		String[] split = txtCommands.getText().split("\n");
		for (String string : split) {
			robot.type(string);
		}
	}

	private void addCommand(String command) {
		String text = txtCommands.getText();
		txtCommands.setText(text+"\n"+command);
	}
}
