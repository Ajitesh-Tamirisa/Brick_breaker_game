package game;
/*import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

import javax.swing.JPanel;
public class Gameplay extends JPanel implements KeyListener, ActionListener {
	//KeyListener-detecting arrow keys being pressed
	//ActionListener-for moving the ball
	private int score=0;
	private boolean play=false;
	
	private int totalBricks =21;
	
	private Timer timer;//speed of ball 
	private int delay = 8;
	
	private int playerX = 310;//starting position of slider
	
	private int ballposX = 120;//starting position of ball
	private int ballposY = 350;//starting position of ball
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this); 
		timer.start();
	}
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.green);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		//paddle
		g.setColor(Color.yellow);
		g.fillRect(playerX, 550, 100, 10);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//when player looses the game
		if(ballposY>570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("GAME OVER" , 190, 270);
			
			g.setColor(Color.yellow);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("SCORE : " , 230, 310);
			
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString(""+score , 270, 350);
			
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Press ENTER to restart ", 190, 400);
					
		}
		
		//when player completes the game
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.yellow);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("You Win!" , 190, 270);
			
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Press ENTER to restart ", 190, 400);
		}
		
		g.dispose();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir=-ballYdir;
			}
		A: for(int i=0;i < map.map.length;i++) {
				for(int j=0;j < map.map[i].length;j++){
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0,i,j);
							totalBricks--;
							score+=5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}
							else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
				
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX<0) {
				ballXdir = -ballXdir;
			}
			if(ballposY<0) {
				ballYdir = -ballYdir;
			}
			if(ballposX>670) {
				ballXdir = -ballXdir;
			}
		}
		repaint(); 
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {//setting slider movements upon pressing arrow keys
			if(playerX>=600) {
				playerX=600;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {//setting slider movements upon pressing arrow keys
			if(playerX<10) {
				playerX=10;
			}
			else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
				
			}
		}
		
	}
	public void moveRight() {
		play = true;
		playerX+=20;
	}
	public void moveLeft() {
		play = true;
		playerX-=20;
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
