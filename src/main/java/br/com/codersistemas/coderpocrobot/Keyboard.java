package br.com.codersistemas.coderpocrobot;

import static java.awt.event.KeyEvent.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keyboard {

    private Robot robot;
	private int DELAY = 100;

    public static void main(String... args) throws Exception {
        Keyboard keyboard = new Keyboard();
        keyboard.type("Hello there, how are you?");
    }

    public Keyboard() throws AWTException {
        this.robot = new Robot();
    }

    public Keyboard(Robot robot) {
        this.robot = robot;
    }
    
	public void click(int x, int y) {
		System.out.println("click "+x+" "+y);
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	};

	public void click2x(int x, int y) {
		System.out.println("click2x "+x+" "+y); 
		
		robot.mouseMove(x, y);
		
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	};

    public void type(CharSequence characters) {
    	String string = characters.toString();
    	System.out.println(string);
    	
    	if(string.startsWith("#")) {
    		return;
    	}
    	
    	robot.delay(DELAY);
		switch (string.toUpperCase()) {
		case "TAB":
			doType(KeyEvent.VK_TAB);
			break;
		case "ENTER":
			doType(KeyEvent.VK_ENTER);
			break;
		case "UP":
			doType(KeyEvent.VK_UP);
			break;
		case "DOWN":
			doType(KeyEvent.VK_DOWN);
			break;
		case "LEFT":
			doType(KeyEvent.VK_LEFT);
			break;
		case "RIGHT":
			doType(KeyEvent.VK_RIGHT);
			break;
		case "BACK":
			doType(KeyEvent.VK_BACK_SPACE);
			break;
		case "HOME":
			doType(KeyEvent.VK_HOME);
			break;
		case "END":
			doType(KeyEvent.VK_END);
			break;
		case "ESC":
			doType(KeyEvent.VK_ESCAPE);
			break;
		case "SPACE":
			doType(KeyEvent.VK_SPACE);
			break;
		default:
			//delay
			if(string.toUpperCase().matches("DELAY\\s*.?\\d*.?")) {
				Matcher matcher = Pattern.compile("\\d+").matcher(string);
				while(matcher.find()) {
					robot.delay(new Integer( matcher.group()));
				}
			} else 
			
			if(string.toUpperCase().matches("CLICK \\d*.? \\d*.?")) {
		    	Matcher matcher = Pattern.compile("CLICK \\d*.? \\d*.?").matcher(string.toUpperCase());
		    	while(matcher.find()) {
		    		String group = matcher.group();
		    		String[] split = group.split(" ");
					Integer x = new Integer(split[1]);
					Integer y = new Integer(split[2]);
					click(x, y);
		    	}
			} else 
			
			if(string.toUpperCase().matches("CLICK2X\\s*.?\\d*.?\\s*.?\\d*.?")) {
				System.out.println(string);
			} else {
				int length = characters.length();
				for (int i = 0; i < length; i++) {
					char character = characters.charAt(i);
					robot.delay(DELAY);
					type(character);
				}
			}
			
			break;
		}
    }

    public void type(char character) {
        switch (character) {
        case 'a': doType(VK_A); break;
        case 'á': doType(VK_A); break;
        case 'ã': doType(VK_A); break;
        case 'b': doType(VK_B); break;
        case 'c': doType(VK_C); break;
        case 'd': doType(VK_D); break;
        case 'e': doType(VK_E); break;
        case 'é': doType(VK_E); break;
        case 'ẽ': doType(VK_E); break;
        case 'f': doType(VK_F); break;
        case 'g': doType(VK_G); break;
        case 'h': doType(VK_H); break;
        case 'i': doType(VK_I); break;
        case 'j': doType(VK_J); break;
        case 'k': doType(VK_K); break;
        case 'l': doType(VK_L); break;
        case 'm': doType(VK_M); break;
        case 'n': doType(VK_N); break;
        case 'o': doType(VK_O); break;
        case 'ó': doType(VK_O); break;
        case 'õ': doType(VK_O); break;
        case 'p': doType(VK_P); break;
        case 'q': doType(VK_Q); break;
        case 'r': doType(VK_R); break;
        case 's': doType(VK_S); break;
        case 't': doType(VK_T); break;
        case 'u': doType(VK_U); break;
        case 'v': doType(VK_V); break;
        case 'w': doType(VK_W); break;
        case 'x': doType(VK_X); break;
        case 'y': doType(VK_Y); break;
        case 'z': doType(VK_Z); break;
        case 'A': doType(VK_SHIFT, VK_A); break;
        case 'Á': doType(VK_SHIFT, VK_A); break;
        case 'Ã': doType(VK_SHIFT, VK_A); break;
        case 'B': doType(VK_SHIFT, VK_B); break;
        case 'C': doType(VK_SHIFT, VK_C); break;
        case 'D': doType(VK_SHIFT, VK_D); break;
        case 'E': doType(VK_SHIFT, VK_E); break;
        case 'É': doType(VK_SHIFT, VK_E); break;
        case 'Ê': doType(VK_SHIFT, VK_E); break;
        case 'Ẽ': doType(VK_SHIFT, VK_E); break;
        case 'F': doType(VK_SHIFT, VK_F); break;
        case 'G': doType(VK_SHIFT, VK_G); break;
        case 'H': doType(VK_SHIFT, VK_H); break;
        case 'I': doType(VK_SHIFT, VK_I); break;
        case 'J': doType(VK_SHIFT, VK_J); break;
        case 'K': doType(VK_SHIFT, VK_K); break;
        case 'L': doType(VK_SHIFT, VK_L); break;
        case 'M': doType(VK_SHIFT, VK_M); break;
        case 'N': doType(VK_SHIFT, VK_N); break;
        case 'O': doType(VK_SHIFT, VK_O); break;
        case 'P': doType(VK_SHIFT, VK_P); break;
        case 'Q': doType(VK_SHIFT, VK_Q); break;
        case 'R': doType(VK_SHIFT, VK_R); break;
        case 'S': doType(VK_SHIFT, VK_S); break;
        case 'T': doType(VK_SHIFT, VK_T); break;
        case 'U': doType(VK_SHIFT, VK_U); break;
        case 'V': doType(VK_SHIFT, VK_V); break;
        case 'W': doType(VK_SHIFT, VK_W); break;
        case 'X': doType(VK_SHIFT, VK_X); break;
        case 'Y': doType(VK_SHIFT, VK_Y); break;
        case 'Z': doType(VK_SHIFT, VK_Z); break;
        case '`': doType(VK_BACK_QUOTE); break;
        case '0': doType(VK_0); break;
        case '1': doType(VK_1); break;
        case '2': doType(VK_2); break;
        case '3': doType(VK_3); break;
        case '4': doType(VK_4); break;
        case '5': doType(VK_5); break;
        case '6': doType(VK_6); break;
        case '7': doType(VK_7); break;
        case '8': doType(VK_8); break;
        case '9': doType(VK_9); break;
        case '-': doType(VK_MINUS); break;
        case '=': doType(VK_EQUALS); break;
        case '~': doType(VK_SHIFT, VK_BACK_QUOTE); break;
        //case '!': doType(VK_EXCLAMATION_MARK); break;
        case '!': doType(VK_SHIFT,VK_1); break;
        case '@': doType(VK_AT); break;
        case '#': doType(VK_NUMBER_SIGN); break;
        case '$': doType(VK_DOLLAR); break;
        case '%': doType(VK_SHIFT, VK_5); break;
        case '^': doType(VK_CIRCUMFLEX); break;
        case '&': doType(VK_AMPERSAND); break;
        case '*': doType(VK_ASTERISK); break;
        case '(': doType(VK_LEFT_PARENTHESIS); break;
        case ')': doType(VK_RIGHT_PARENTHESIS); break;
        case '_': doType(VK_UNDERSCORE); break;
        case '+': doType(VK_PLUS); break;
        case '\t': doType(VK_TAB); break;
        case '\n': doType(VK_ENTER); break;
        case '[': doType(VK_OPEN_BRACKET); break;
        case ']': doType(VK_CLOSE_BRACKET); break;
        case '\\': doType(VK_BACK_SLASH); break;
        case '{': doType(VK_SHIFT, VK_OPEN_BRACKET); break;
        case '}': doType(VK_SHIFT, VK_CLOSE_BRACKET); break;
        case '|': doType(VK_SHIFT, VK_BACK_SLASH); break;
        case ';': doType(VK_SEMICOLON); break;
        case ':': doType(VK_COLON); break;
        case '\'': doType(VK_QUOTE); break;
        case '"': doType(VK_QUOTEDBL); break;
        case ',': doType(VK_COMMA); break;
        case '<': doType(VK_SHIFT, VK_COMMA); break;
        case '.': doType(VK_PERIOD); break;
        case '>': doType(VK_SHIFT, VK_PERIOD); break;
        case '/': doType(VK_SLASH); break;
        case '?': doType(VK_SHIFT, VK_SLASH); break;
        case ' ': doType(VK_SPACE); break;
        default:
            throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    private void doType(int... keyCodes) {
        doType(keyCodes, 0, keyCodes.length);
    }

    private void doType(int[] keyCodes, int offset, int length) {
        if (length == 0) {
            return;
        }
        robot.keyPress(keyCodes[offset]);
        doType(keyCodes, offset + 1, length - 1);
        robot.keyRelease(keyCodes[offset]);
    }

}