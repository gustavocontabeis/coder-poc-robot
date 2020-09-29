package br.com.codersistemas.coderpocrobot.gui;

import java.awt.AWTException;
import java.awt.FlowLayout;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.codersistemas.coderpocrobot.CoderRobot;

public class ProfitChangeUser extends JFrame {

	private static final long serialVersionUID = -3556068906690980070L;

	public ProfitChangeUser() throws HeadlessException {
		super("Coder Profit Change User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		JButton btChangeLautenir = new JButton("Lautenir");
		btChangeLautenir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CoderRobot robot = new CoderRobot();
					File file = new File("/home/gustavo/lautenir-robot.txt");
					BufferedReader br = new BufferedReader(new FileReader(file));
					String st;
					while ((st = br.readLine()) != null) {
						System.out.println(st);
						if(st.startsWith("#") || "".equals(st))
							continue;
						String[] split = st.split("\\s");
						if("clicarEm".equals(split[0].trim())) {
							robot.clicarEm(new Integer(split[1]), new Integer(split[2]));
						} else if("aguardar".equals(split[0].trim())) {
							robot.aguardar(new Integer(split[1]));
						} else if("escrever".equals(split[0].trim())) {
							robot.escrever(st.substring(st.indexOf(" ")));
						} else if("enter".equals(split[0].trim())) {
							robot.executar(KeyEvent.VK_ENTER);
						} 
					}

//					robot.clicarEm(89, 1007);
//					robot.aguardar(3000);
//					robot.clicarEm(264, 108);
//					robot.aguardar(3000);
//					robot.escrever("google.com");
//					robot.aguardar(1000);
//					robot.executar(KeyEvent.VK_ENTER);
					
					System.out.println("Fim");
				} catch (AWTException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		panel.add(btChangeLautenir);
		this.getContentPane().add(panel);

		JButton btChangeGustavo = new JButton("Gustavo");
		btChangeGustavo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CoderRobot robot = new CoderRobot();
					robot.clicarEm(1296, 41);
					robot.aguardar(1000);
					robot.clicarEm(1390, 497);
				} catch (AWTException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btChangeGustavo);
		this.getContentPane().add(panel);

		JButton btListenMouse = new JButton("Posição Mouse");
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
		panel.add(btListenMouse);
		this.getContentPane().add(panel);

	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				ProfitChangeUser x = new ProfitChangeUser();
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
