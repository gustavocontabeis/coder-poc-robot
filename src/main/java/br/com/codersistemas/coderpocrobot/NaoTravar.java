package br.com.codersistemas.coderpocrobot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.time.LocalDateTime;

public class NaoTravar {
	public static void main(String[] args) throws AWTException {
		System.out.println("Nao tavar - inicio");
		Robot r = new Robot();
		do {
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			r.mouseMove(x+=(x%2==0?1:-1),y);
			r.delay(1000*60*1);
			//r.delay(1000);
			System.out.println("x");
		} while (LocalDateTime.now().getHour() < 19);
		System.out.println("Nao tavar - fim");
	}
}
