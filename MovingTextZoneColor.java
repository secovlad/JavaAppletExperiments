package movingText;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class MovingTextZoneColor extends Applet implements KeyListener{

	private static final long serialVersionUID = 1L; // auto generated ID ? 
	
	// magic incantations
	private Graphics graphics;
	private MouseMotionListener listener;
	private KeyListener keylistener;
	
	// handling key responses
	boolean up = false, left = false, down = false, right = false, fontSizeUp = false, fontSizeDown = false;

	// variables which will change with key presses or mouse movement
	private int x, y, xstr = 10, ystr = 20, fontSize = 14; 
	private double xVelocity = 0, yVelocity = 0; 
	
	private Color blue = Color.blue;
	private Color green = Color.green;	// 4 colors on 4 secttions of the screen
	private Color red = Color.red;
	private Color yellow = Color.yellow;
	
	public void start() {
		graphics = getGraphics();
		x = -100;
		y = -100;
		listener = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent event){
				x = event.getX();							// mouse initializer
				y = event.getY();
				paint(graphics);
			}
		};
		
		addMouseMotionListener(listener);
	}
	
	public void init(){
		addKeyListener(this); // this made moving the text actually work; this is the keyboard initializer
	}
	
	public void stop(){
		removeMouseMotionListener(listener);
		removeKeyListener(keylistener); // i don't know when this is called but it's pretty obvious how it works
	}
	
	public void update(Graphics g){
		paint(g); // keeps the paint running!
	}
	
	public void paint(Graphics graphics){
		Font font = new Font("Arial", Font.PLAIN, fontSize);
		graphics.setFont(font);
		
		graphics.setColor(Color.black);
		
		graphics.fillRect(0,  0, getWidth(), getHeight());
			
		if      (x > 0 			   && x < getWidth()/2 && y > 0 			&& y <= getHeight()/2) graphics.setColor(green);
		else if (x >= getWidth()/2 && x < getWidth()   && y > 0 			&& y <= getHeight()/2) graphics.setColor(blue);
		else if (x > 0 			   && x < getWidth()/2 && y > getHeight()/2 && y < getHeight())    graphics.setColor(red);	// or where we get the mouse to do its magic
		else if (x >= getWidth()/2 && x < getWidth()   && y > getHeight()/2 && y < getHeight())   graphics.setColor(yellow);
		graphics.fillRect(x - 10, y - 10, 21, 21);
		
		if (x + 75 > getWidth())
			graphics.drawString("("+x+","+y+")", 10, getHeight() - 10);
		else
			graphics.drawString("("+x+","+y+")", x + 20, y);		// code that draws the coordinates; handles going into the corner
		
		
		graphics.drawString("Fabulous Text!!", xstr, ystr);
		// to prevent the text from leaving the screen (this whole part)
		// the numbers are the result of manual tweaking (and it's still pretty buggy)
		
		if (right == true) { xVelocity = 1.5; yVelocity = 0; }
		else if (left == true) { xVelocity = -1.5; yVelocity = 0; }
		else if (up == true) { yVelocity = -1.5; xVelocity = 0; }			// how the text gains speed
		else if (down == true) { yVelocity = 1.5; xVelocity = 0; }
		
		xstr += xVelocity;
		ystr += yVelocity;		// how the text moves

		if (xstr >= getWidth() - 100) 
			xstr = getWidth() - 100; 
		if (xstr <= 0) 
			xstr = 0;
		if (ystr <= 20) 					// this keeps the text from moving out of bounds
			ystr = 20;
		if (ystr >= getHeight() - 5) 
			ystr = getHeight() - 10;
		
		if (fontSizeUp == true) {
			if (fontSize > 56) fontSize = 56; // setting some hard limits for text size
			Font upFont = new Font("Arial", Font.PLAIN, fontSize+=2);
			graphics.setFont(upFont);
		}
		else if (fontSizeDown == true) {
			if (fontSize < 10) fontSize = 10; // can't go too low
			Font downFont = new Font("Arial", Font.PLAIN, fontSize-=2);
			graphics.setFont(downFont);
		}
		
	}
	
	public void keyPressed(KeyEvent e) {		// key press handling code part 1
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_RIGHT) { right = true; }
		else if (keyCode == KeyEvent.VK_LEFT) { left = true; }
		else if (keyCode == KeyEvent.VK_UP) { up = true; }
		else if (keyCode == KeyEvent.VK_DOWN) { down = true; }
		else if (keyCode == KeyEvent.VK_EQUALS) { fontSizeUp = true; } // instead of plus because of shift
		else if (keyCode == KeyEvent.VK_MINUS) { fontSizeDown = true; }
		else if (keyCode == KeyEvent.VK_ESCAPE) { System.exit(0); }
		repaint();
	}


	public void keyReleased(KeyEvent e) {		// key press handling code part 2
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_RIGHT) { xstr++; right = false;}
		else if (keyCode == KeyEvent.VK_LEFT) { xstr--; left = false; }
		else if (keyCode == KeyEvent.VK_UP) { ystr--; up = false; }
		else if (keyCode == KeyEvent.VK_DOWN) { ystr++; down = false; }
		else if (keyCode == KeyEvent.VK_EQUALS) { fontSizeUp = false; } // instead of plus because of shift
		else if (keyCode == KeyEvent.VK_MINUS) { fontSizeDown = false; }
		repaint();	
	}

	public void keyTyped(KeyEvent e) {
		//Auto-generated method stub
	}
}
