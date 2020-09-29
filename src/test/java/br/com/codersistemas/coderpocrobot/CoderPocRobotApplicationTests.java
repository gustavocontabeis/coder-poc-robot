package br.com.codersistemas.coderpocrobot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoderPocRobotApplicationTests {
	@Test
	public void robot() throws IOException, AWTException, InterruptedException {
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Início");
		Robot r = new Robot();
		do {
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			int z = x % 2 == 0 ? 1 : -1;
			//System.out.println(x + " " + z + " " + x % 2);
			r.mouseMove(x+=z,y);
			Thread.sleep(1000*60*3);
		} while (LocalDateTime.now().getHour() > 8 && LocalDateTime.now().getHour() < 18);
		System.out.println("Fim");
	}
}

