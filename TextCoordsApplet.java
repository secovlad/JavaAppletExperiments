package movingText;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TextCoordsApplet  extends Applet implements MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L; // auto generated ID

	int x = 0, y = 0, xa, ya; 
	
	int xpos, ypos;
	boolean mouseEntered, clicked, moved; // mouse listener related
	
	public void init(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	
	public void paint(Graphics g) {
		int x, y; String s = "Fabulous Text!";

		setBackground(Color.lightGray);
		
		// obtain applet size using getSize method
		Dimension d = getSize();
		Font f = new Font("Arial", Font.BOLD,20);
		g.setFont(f);
		
		// determine coordinates for the center of the screen
		FontMetrics fm = g.getFontMetrics();
		x = d.width/2 - fm.stringWidth(s)/2;
		y = d.height/2 - fm.getHeight();
		
		xa = x; ya = y; // pass center coordinates
		
		// Draw a rectangle around the applet's display area
		g.drawRect(15, 15 , getSize().width - 30, getSize().height - 30);
		
		// Draw the current string on the center of the screen
		g.drawString(s, x, y);
		
		g.drawString("("+xpos+","+ypos+")", xpos, ypos);
		
		if (clicked) { setBackground(Color.green); }
		else setBackground(Color.lightGray);
		
		if (mouseEntered) g.drawString("Mouse is in the applet area", 50, 50);
		else g.drawString("Mouse is outside the applet area", 50, 50);
	}
	
	public void mouseClicked(MouseEvent me){}
	
	public void mousePressed (MouseEvent me){		
		clicked = true;
		repaint();
	}
	
	public void mouseReleased (MouseEvent me){}
	
	public void mouseEntered(MouseEvent me){
		mouseEntered = true;
		repaint();
	}
	
	public void mouseExited(MouseEvent me){
		mouseEntered = false;
		repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent me) {
		xpos = me.getX(); ypos = me.getY();
		moved = true;
		repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}
	
}
