package snake;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {

	public static Snake snake;
	
	public final static int tiles = 15, tileSize = 50;

	public final static int WIDTH = tiles * tileSize + 5, HEIGHT = tiles * tileSize + 28;
	
	public static int appleX, appleY, xVel = 0, yVel = 0, headX, headY;
	
	public static Part[] parts = new Part[0];
	
	public Renderer renderer;

	public Snake() {
		JFrame frame = new JFrame();
		Timer timer = new Timer(60, this);

		renderer = new Renderer();

		frame.add(renderer);
		frame.setTitle("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		frame.addKeyListener(this);
		
		applePos();
		
		headX = rand(0, tiles - 1) * tileSize;
		headY = rand(0, tiles - 1) * tileSize;

		timer.start();
	}
	
	public int rand (int num1, int num2) {
		return (int) (Math.floor(Math.random() * (num2 - num1 + 1)) + num1);
	}
	
	public void applePos () {
		appleX = rand(0, tiles - 1) * tileSize;
		appleY = rand(0, tiles - 1) * tileSize;
		for (Part p : parts) {
			if (p.x == appleX && p.y == appleY) {
				applePos();
				break;
			}
		}
	}
	
	public void die () {
		headX = rand(0, tiles - 1) * tileSize;
		headY = rand(0, tiles - 1) * tileSize;
		parts = new Part[0];
	}
	
	public void moveSnake () {
		headX += xVel;
		headY += yVel;
	}
	
	public boolean apple () {
		if (headX == appleX && headY == appleY) {
			return true;
		}
		return false;
	}
	
	public void addPart () {
		int length = parts.length;
		if (length > 0) {
			Part[] parts2 = new Part[length + 1];
			for (int i = 0; i < length; i++) {
				parts2[i] = parts[i];
			}
			parts2[length] = new Part(parts[length - 1].x, parts[length - 1].y);
			parts = parts2;
		} else {
			Part[] parts2 = {new Part(headX, headY)};
			parts = parts2;
		}
	}
	
	public void moveParts () {
		if (parts.length > 1) {
			for (int i = parts.length - 1; i > 0; i--) {
				parts[i].x = parts[i - 1].x;
				parts[i].y = parts[i - 1].y;
			}
			parts[0].x = headX;
			parts[0].y = headY;
		} else if (parts.length == 1) {
			parts[0].x = headX;
			parts[0].y = headY;
		}
	}
	
	public int partCollide () {
		int pos = -1;
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].x == headX && parts[i].y == headY) {
				System.out.println(i);
				pos = i;
				break;
			}
		}
		return pos;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		renderer.repaint();
	}

	// drawing objects
	public void repaint(Graphics g) {
		
		moveParts();
		moveSnake();
		
		// drawing background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// drawing apple
		g.setColor(Color.RED);
		g.fillRect(appleX, appleY, tileSize, tileSize);
		
		// drawing snake
		g.setColor(Color.GREEN);
		g.fillRect(headX, headY, tileSize, tileSize);
		
		for (Part p : parts) {
			g.fillRect(p.x, p.y, tileSize, tileSize);
		}
		
		int pos = partCollide();
		if (pos != -1) {
			Part[] parts2 = new Part[pos];
			for (int i = 0; i < pos; i++) {
				parts2[i] = parts[i];
			}
			parts = parts2;
		}
		
		
		if (apple()) {
			addPart();
			applePos();
		}
		
		if (headX < 0 || headY < 0 || headX + tileSize > WIDTH || headY + tileSize > HEIGHT) {
			die();
			xVel = 0;
			yVel = 0;
			applePos();
		}
		
	}
	
	public static void main (String[] args) {
		snake = new Snake();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		xVel = 0;
		yVel = 0;
		switch (e.getKeyCode()) {
			case 87: yVel = -1 * tileSize;
					 break;
			case 83: yVel = 1 * tileSize;
			 		 break;
			case 65: xVel = -1 * tileSize;
			         break;
			case 68: xVel = 1 * tileSize;
			         break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}