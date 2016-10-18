package movingText;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class MovingTextApplet extends Applet implements KeyListener{

	private static final long serialVersionUID = 1L; // auto generated ID ? 
	
	// magic incantations
	private Graphics graphics;
	private MouseMotionListener listener;
	private KeyListener keylistener;
	
	// handling key responses
	boolean up = false, left = false, down = false, 
			right = false, fontSizeUp = false, fontSizeDown = false, colorChange = false;

	// variables which will change with key presses or mouse movement
	private int x, y, xstr = 10, ystr = 20, fontSize = 14, textMoveSpeed = 4; 
	
	
	private Color blue = Color.blue;
	private Color cyan = Color.cyan;
	private Color green = Color.green;
	private Color magenta = Color.magenta;		// biggest overkill possible
	private Color orange = Color.orange;	// but it works sort of as intended
	private Color pink = Color.pink;
	private Color red = Color.red;
	private Color yellow = Color.yellow;
	
	private Color mainColor = green; // start off with green
	
	public void start() {
		graphics = getGraphics();
		x = -100;
		y = -100;
		listener = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent event){	// initializes the mouse listener
				x = event.getX();
				y = event.getY();
				paint(graphics);
			}
		};
		
		addMouseMotionListener(listener);
	}
	
	public void init(){
		addKeyListener(this); // this made moving the text actually work
	}
	
	public void stop(){
		removeMouseMotionListener(listener);	// i don't know when this gets called but it's pretty obvious what it does
		removeKeyListener(keylistener);
	}
	
	public void update(Graphics g){
		paint(g);	// to keep the paint running
	}
	
	public void paint(Graphics graphics){
		Font font = new Font("Arial", Font.PLAIN, fontSize);
		graphics.setFont(font);	// setting the beginning font
		
		graphics.setColor(Color.black);	// making the background black
		
		graphics.fillRect(0,  0, getWidth(), getHeight());  // uncomment this line for interestingness - keeps everything from bleeding into everything
		graphics.setColor(mainColor);	// whichever was cycled using C	
		
		graphics.fillRect(x - 10, y - 10, 21, 21); // rectangle around the mouse cursor
		
		if (x + 75 > getWidth())
			graphics.drawString("("+x+","+y+")", 10, getHeight() - 10);	// this displays the coordinates of the cursor, moves them out of the way when they exit bounds
		else
			graphics.drawString("("+x+","+y+")", x + 20, y);
		
		
		graphics.drawString("Fabulous Text!!", xstr, ystr);
		
		// below is the code for moving the text, and preventing it from exiting the boundaries of the applet
		// the numbers are the result of manual tweaking (and it's still pretty buggy)
		
		if (right == true) { 
			xstr += textMoveSpeed; 		// this moves the text by textMoveSpeed, which gets changed just below
			if (xstr >= getWidth() - 100) 	// since it would only be going through that boundary when moving in that direction
				xstr = getWidth() - 100; 	// it's not needed to move this if outside of if (right == true)
		}	
		else if (left == true) { 
			xstr -= textMoveSpeed; 
			if (xstr <= 0) 			// same thing here, and with the other ones
				xstr = 0;	
		}
		else if (up == true) { 
			ystr -= textMoveSpeed; 
			if (ystr <= 20) 
				ystr = 20; 
		}	
		else if (down == true) { 				
			ystr += textMoveSpeed; 
			if (ystr >= getHeight() - 5) 
				ystr = getHeight();
		}	
		
		if (fontSizeUp == true) {
			if (fontSize > 56) fontSize = 56; // setting some hard limits
			Font upFont = new Font("Arial", Font.PLAIN, fontSize+=2);
			textMoveSpeed ++;	// to adjust the speed when text gets bigger
			graphics.setFont(upFont);
		}
		else if (fontSizeDown == true) {
			if (fontSize < 10) fontSize = 10; // hard limits
			Font downFont = new Font("Arial", Font.PLAIN, fontSize-=2);
			textMoveSpeed --;	// to adjut the speed when text gets smaller
			graphics.setFont(downFont);
		}
		else if (colorChange == true){
			if (mainColor == blue) mainColor = cyan;
			else if (mainColor == cyan) mainColor = green;
			else if (mainColor == green) mainColor = orange;
			else if (mainColor == orange) mainColor = pink;
			else if (mainColor == pink) mainColor = red;		// just couldnt bear with lists
			else if (mainColor == red) mainColor = yellow;		// it was 2am and i don't know how to do it in java
			else if (mainColor == yellow) mainColor = magenta;  // might be completely retarded but it works (sort of) as intended
			else if (mainColor == magenta) mainColor = blue;
			graphics.setColor(mainColor);
		} 
		
	}

	
	public void keyPressed(KeyEvent e) { // keyboard input handling code part 1
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_RIGHT) { right = true; }
		else if (keyCode == KeyEvent.VK_LEFT) { left = true; }
		else if (keyCode == KeyEvent.VK_UP) { up = true; }
		else if (keyCode == KeyEvent.VK_DOWN) { down = true; }
		else if (keyCode == KeyEvent.VK_EQUALS) { fontSizeUp = true; } // instead of plus because of shift
		else if (keyCode == KeyEvent.VK_MINUS) { fontSizeDown = true; }
		else if (keyCode == KeyEvent.VK_C) { colorChange = true; }
		else if (keyCode == KeyEvent.VK_ESCAPE) { System.exit(0); }
		repaint();
	}


	public void keyReleased(KeyEvent e) { // keyboard input handling code part 2
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_RIGHT) { xstr++; right = false;}
		else if (keyCode == KeyEvent.VK_LEFT) { xstr--; left = false; }
		else if (keyCode == KeyEvent.VK_UP) { ystr--; up = false; }
		else if (keyCode == KeyEvent.VK_DOWN) { ystr++; down = false; }
		else if (keyCode == KeyEvent.VK_EQUALS) { fontSizeUp = false; } // instead of plus because of shift
		else if (keyCode == KeyEvent.VK_MINUS) { fontSizeDown = false; }
		else if (keyCode == KeyEvent.VK_C) { colorChange = false; }
		repaint();	
	}

	public void keyTyped(KeyEvent e) {
		//Auto-generated method stub
		
	}
}
