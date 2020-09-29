package br.com.codersistemas.coderpocrobot;

import java.awt.AWTException;

public class Main {
	public static void main(String[] args) throws AWTException, InterruptedException {
		CoderRobot robot = new CoderRobot();
		robot.clicarEm(1296, 41);
		robot.aguardar(1000);
		robot.clicarEm(1390, 497);
	}
}
