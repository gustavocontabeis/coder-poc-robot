package br.com.codersistemas.coderpocrobot;

import java.awt.AWTException;

public class Main {
	public static void main(String[] args) throws AWTException, InterruptedException {
//		CoderRobot robot = new CoderRobot();
//		robot.clicarEm(1296, 41);
//		robot.aguardar(1000);
//		robot.clicarEm(1390, 497);
		
		Keyboard robot = new Keyboard();
		robot.type("DELAY 3000");
		String[] commands = {
				"Olá!", 
				"DOWN", 
				"Meu nome é Gustavo.",
				"ENTER",
				"E o seu?",
				"DOWN",
				"DOWN",
				"aqui",
				"UP",
				"HOME",
				"aaa",
				"",
				"",
				""
				};
		for (String command : commands) {
			robot.type(command);
		}
		
	}
}
