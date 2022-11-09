package br.com.codersistemas.coderpocrobot.gui;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.codersistemas.coderpocrobot.Keyboard;
import br.com.codersistemas.libs.utils.FileUtil;

public class RobotUI extends JFrame implements RobotListener {

	private static final long serialVersionUID = -3556068906690980070L;
	private Border border;
	private JTextArea txtCommands;
	private JTextField txtEscrever;
	private Keyboard robot;
	private JTextField txtDelay;
	private PanelBuilder panelBuilder;
	private List<File>files;
	private JList<String>listFiles;

	public RobotUI() throws HeadlessException, AWTException {
		super("Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		robot = new Keyboard();
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loadFiles();
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
				.newLine()
				.addButton("Run!", 12)
				.newLine()
				.addList(listFiles, 12)
				.newLine()
				.addText("file", 6)
				.addButton("Salvar", 3)
				.addButton("Excluir", 3)
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

	private void loadFiles() {
		files = new ArrayList<>();
		File dir = new File(".");
		System.out.println(dir.getAbsolutePath());
		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			if(file.getName().toLowerCase().endsWith(".robot")) {
				files.add(file);
			}
		}
		Collections.sort(files);
		String[] filesNames = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			filesNames[i] = files.get(i).getName();
		}
		
		if(this.listFiles == null) {
			this.listFiles = new JList<String>(filesNames);
			this.listFiles.setBounds(0, 0, 0, 150);
		}else {
			this.listFiles.setListData(filesNames);
		}
		
		this.listFiles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(e.getFirstIndex()+" - "+e.getFirstIndex());
				loadFileContentByIndex();
			}
		});
	}
	
	private void loadFileContentByIndex() {
		int selectedIndex = this.listFiles.getSelectedIndex();
		if( selectedIndex >= 0) {
			File file = this.files.get(selectedIndex);
			try {
				String string = new String(Files.readAllBytes(Paths.get(file.getName())), Charset.defaultCharset());
				txtCommands.setText(string);
				panelBuilder.getText("file").setText(listFiles.getSelectedValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			case "Salvar":
				salvarArquivo();
				break;
			case "Excluir":
				excluirArquivo();
				break;
			default:
				appendCommand(bt.getText().toUpperCase());
				break;
			}
		} else if (source instanceof JTextField) {
			JTextField txt = (JTextField) source;
		}

	}

	private void excluirArquivo() {
		int selectedIndex = this.listFiles.getSelectedIndex();
		File file = this.files.get(selectedIndex);
		try {
			Files.delete(Paths.get(file.getName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadFiles();
	}

	private void salvarArquivo() {
		
		int selectedIndex = this.listFiles.getSelectedIndex();
		String fileText = panelBuilder.getText("file").getText();
		File file = fileText != "" ? new File(fileText + (fileText.endsWith("robot") ? "" : ".robot")) :this.files.get(selectedIndex);
		try {
			Files.write(Paths.get(file.getName()), txtCommands.getText().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		loadFiles();
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
		System.out.println("Fim!");
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

}
