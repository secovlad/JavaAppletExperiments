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
	private Color orange = Color.orange;
	private Color pink = Color.pink;
	private Color red = Color.red;
	private Color yellow = Color.yellow;
	
	private Color mainColor = green;
	
	public void start() {
		graphics = getGraphics();
		x = -100;
		y = -100;
		listener = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent event){
				x = event.getX();
				y = event.getY();
				paint(graphics);
			}
		};
		
		keylistener = new KeyAdapter(){
			public void keyPressed(KeyEvent e){		// not sure if this code does anything
				paint(graphics);					// adapted the previous MouseMotion code
			}
		};
		
		addMouseMotionListener(listener);
		addKeyListener(keylistener);
	}
	
	public void init(){
		addKeyListener(this); // this made moving the text actually work
	}
	
	public void stop(){
		removeMouseMotionListener(listener);
		removeKeyListener(keylistener);
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics graphics){
		Font font = new Font("Arial", Font.PLAIN, fontSize);
		graphics.setFont(font);
		
		graphics.setColor(Color.black);
		
		graphics.fillRect(0,  0, getWidth(), getHeight());
		graphics.setColor(mainColor);
		
		graphics.fillRect(x - 10, y - 10, 21, 21);
		
		if (x + 75 > getWidth())
			graphics.drawString("("+x+","+y+")", 10, getHeight() - 10);
		else
			graphics.drawString("("+x+","+y+")", x + 20, y);
		
		
		graphics.drawString("Fabulous Text!!", xstr, ystr);
		// to prevent the text from leaving the screen (this whole part)
		// the numbers are the result of manual tweaking (and it's still pretty buggy)
		if (right == true) { 
			xstr += textMoveSpeed; 
			if (xstr >= getWidth() - 75) 
				xstr = getWidth(); 
		}	
		else if (left == true) { 
			xstr -= textMoveSpeed; 
			if (xstr <= 0) 
				xstr = 0;	
		}
		else if (up == true) { 
			ystr -= textMoveSpeed; 
			if (ystr <= 10) 
				ystr = 10; 
		}	
		else if (down == true) { 				// TWEAK HERE WITH textMoveSpeed
			ystr += textMoveSpeed; 
			if (ystr >= getHeight() - 5) 
				ystr = getHeight();;
		}	
		
		if (fontSizeUp == true) {
			Font upFont = new Font("Arial", Font.PLAIN, fontSize += 2);
			textMoveSpeed += 2;
			graphics.setFont(upFont);
		}
		else if (fontSizeDown == true) {
			Font downFont = new Font("Arial", Font.PLAIN, fontSize -= 2);
			textMoveSpeed -= 2;
			graphics.setFont(downFont);
		}
		else if (colorChange == true){
			if (mainColor == blue) mainColor = cyan;
			else if (mainColor == cyan) mainColor = green;
			else if (mainColor == green) mainColor = orange;
			else if (mainColor == orange) mainColor = pink;
			else if (mainColor == pink) mainColor = red;		// just couldnt bear with lists
			else if (mainColor == red) mainColor = yellow;
			else if (mainColor == yellow) mainColor = magenta;
			else if (mainColor == magenta) mainColor = blue;
			graphics.setColor(mainColor);
		} 
		
	}

	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_RIGHT) { right = true; }
		else if (keyCode == KeyEvent.VK_LEFT) { left = true; }
		else if (keyCode == KeyEvent.VK_UP) { up = true; }
		else if (keyCode == KeyEvent.VK_DOWN) { down = true; }
		else if (keyCode == KeyEvent.VK_EQUALS) { fontSizeUp = true; } // instead of plus because of shift
		else if (keyCode == KeyEvent.VK_MINUS) { fontSizeDown = true; }
		else if (keyCode == KeyEvent.VK_C) { colorChange = true; }
		repaint();
	}


	public void keyReleased(KeyEvent e) {
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
